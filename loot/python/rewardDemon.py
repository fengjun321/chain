#coding:utf-8
#from web3.auto.infura.mumbai import w3
#from web3.auto.infura.polygon import w3
#from web3.auto.infura.bsc_main import w3
from web3.auto.infura.bsc_testnet import w3
from csv import reader
import json
import time
import sys

stackRewardAddress = '0x4Bf8F51CdaC515FBA2dF4E2e2E68B3BdfB68D4Ec'
powerCalcAddress = '0xDA760892033d5cB8B1B003A1dc3aA5Ffa201617e'
fireAddress = '0x4cCbCEB7AcF13cb78C25C736c6D9A49894C3ee2b'
quarkAddress = '0x00A44186Bb8C7F8EEc5d64d250Fee8f5Cc6A0457'
fireNumber = 10**23
quarkNumber = 10**23
queryInterval = 1800


def getAccount(mainnet = 1):
    w3.eth.account.enable_unaudited_hdwallet_features()

    if mainnet == 1:
        pk = open("9359p.key","r")
    else:
        pk = open("testOwner.txt","r")
    #pk = open("private.key","r")
    return w3.eth.account.from_key(pk.read().strip('\n'));





def getContract(addr,abijson):
    eg =  open(abijson,'r') 
    egse = json.load(eg)
    contEg = w3.eth.contract(address=w3.toChecksumAddress(addr),abi=egse)
    return contEg
'''
1.检查矿池是否有出征到期
2.到期算力清零,调用batchZero
3.检查矿池剩余时间，低于半小时则给矿池转账,并通知矿池
'''
def sendRewardToken(acc,reward,address,json):
    #Transfer Award Token
    nonce = w3.eth.getTransactionCount(acc.address)
    erc20 = getContract(address,json)#TESTQUARK
    trans = erc20.functions.transfer(reward.address,10**23).buildTransaction({
                #'gas': 80000,
                'gasPrice': w3.toWei('30', 'gwei'),
                'from': acc.address,
                'nonce':nonce
                })
    signed_txn  = acc.signTransaction(trans)
    txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
    print("transfer erc20 txHash: " + w3.toHex(txn_hash) +"\n")
    while(w3.eth.getTransactionCount(acc.address) == nonce):
            time.sleep(60)

def getOvertimeFleets(contReward):
    fleetList = [] 
    fleet_ids = []
    
    fleetList = contReward.functions.queryAllStackedFleetStakingTime().call()
    print("start to call queryAllStackedFleetStakingTime with ids {} ".format(fleetList))
    for i in range(int(len(fleetList)/2)):
        fleet_time = fleetList[2*i+1]&0xFFFFFFFFFFFFFFFF
        #print("fleettime is {}".format(fleet_time))
        if time.time() - fleet_time >= 24*3600:
            fleet_ids.append(fleetList[2*i])

    print(">24 hours ids ")
    print(*fleet_ids,sep=',')
    return fleet_ids

def v1_fleet_checker(acc,contReward,stage):
    nonce = 0
    fleet_ids = []   
    if stage == 0:#check the staking time  
        fleet_ids = getOvertimeFleets(contReward)
        if len(fleet_ids) > 0:
            print("start to call batchZero with ids ")
            nonce = w3.eth.getTransactionCount(acc.address)
            trans = contReward.functions.batchZero(fleet_ids).buildTransaction({
                        #'gas': 100000*len(fleet_ids),
                        'gasPrice': w3.toWei('30', 'gwei'),
                        'from': acc.address,
                        'nonce':nonce
                        })
            signed_txn  = acc.signTransaction(trans)
            txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
            print("batchZero txHash: " + w3.toHex(txn_hash) +"\n")
            while(w3.eth.getTransactionCount(acc.address) == nonce):
                    time.sleep(60)
    elif stage == 1:
        #需要根据矿池列表配置地址和数量列表
        leftTime = contReward.functions.endTimeInSeconds().call()
        print("check mining Pool left time  {}: \n".format(leftTime))
        if leftTime == 0:
            #Transfer Award Token
            sendRewardToken(acc,contReward,fireAddress,'abi/IERC20.json')#testFire
            sendRewardToken(acc,contReward,quarkAddress,'abi/IERC20.json')#testQuark

            print("start to send notifyRewardAmount : \n")  
            #After tranfer , Notify the mining pool
            nonce = w3.eth.getTransactionCount(acc.address)
            trans = contReward.functions.notifyRewardAmount([10**23,10**23]).buildTransaction({
                    #'gas': 160000,
                    'gasPrice': w3.toWei('30', 'gwei'),
                    'from': acc.address,
                    'nonce':nonce                    
                    })
            signed_txn  = acc.signTransaction(trans)
            txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
            print("notifyRewardAmount hash: " + w3.toHex(txn_hash) +"\n")                   

def runDemon(acc,contReward):
    while  True:
        try:
            v1_fleet_checker(acc,contReward,0)
            v1_fleet_checker(acc,contReward,1)
            time.sleep(queryInterval)#check half an hour
        except Exception as e:
            print("exception orrur {}".format(e))
            time.sleep(queryInterval)#check half an hour

def getFleetsAndPower(acc,contReward):
    fleet_ids = []

    #获取所有质押的舰队列表
    fleetList = contReward.functions.queryAllStackedFleetStakingTime().call()
    print('fleetList {} '.format(fleetList))

    contCalc = getContract(powerCalcAddress,'abi/powercalc.json')
    for i in range(int(len(fleetList)/2)):
        fleet_ids.append(fleetList[2*i])
        p0  = contCalc.functions.calculateFleetPower(fleet_ids[i]).call()
        p1 = contCalc.functions.calculateFleetPowerAfterConsideredDuration(fleet_ids[i]).call()
        print('FleetId {} original Power {} after mining Deduct Power {} '.format(fleet_ids[i],p0,p1))
        
def getMiningPoolInfo(contReward):
    total = contReward.functions.totalSupply().call()
    tokenpPower = contReward.functions.rewardPerToken().call()
    leftTime = contReward.functions.endTimeInSeconds().call()
    addr =contReward.functions.queryRewardTokenAddr().call()
    print('Mining Pool Total is {}, token per power is {},leftTime is {},reward token addr lst {} \n'.format(total,tokenpPower,leftTime,addr))

def main(argv):
    acc = getAccount(0)
    print("the account address:"+str(acc.address))
    contReward = getContract(stackRewardAddress,'abi/stakingreward.json')

    if argv[1] == 'run':
        runDemon(acc,contReward)
    elif argv[1] == 'shell':
        getFleetsAndPower(acc,contReward)
        getMiningPoolInfo(contReward)

if __name__ == '__main__':
    main(sys.argv)


