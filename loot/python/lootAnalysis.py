#coding:utf-8
#from web3.auto.infura.mumbai import w3
#from web3.auto.infura.polygon import w3
#from web3.auto.infura.bsc_main import w3
from web3.auto.infura.bsc_testnet import w3
#from web3.auto.infura.ankr_polygon import w3
from pathlib import Path
from csv import reader
import json
import time
from requests import request
from web3 import Web3
from eth_account.messages import encode_defunct



'''
读取链上数据
'''
def readLootDataInfo():
    acct = w3.eth.account.create("viewAccout")

    with open('abi.json','r') as f:
        abi = json.load(f)
        loot = w3.eth.contract(address=w3.toChecksumAddress('0x16b558dd9eb6e057b8f0e7d146f1ddc567538ac9'),abi=abi)
        file = 'data.txt'
        with open(file, 'a+') as dataFile:
            for num in range(1,50000):
                data = loot.functions.getTokenData(num).call()
                dataFile.write("ID: "+ str(num) +" Token Data:"+str(data)+"\n")

def readFhcDataInfo():
    #acct = w3.eth.account.create("viewAccout")

    with open('abi/fhc.json','r') as f:
        abi = json.load(f)
        fhc = w3.eth.contract(address=w3.toChecksumAddress('0xe28647ac592E5CC81Eca240DF3923B19b6196182'),abi=abi)
        file = 'fhc.txt'
        with open(file, 'a+') as dataFile:
            for num in range(1,164):
                data = fhc.functions.getDataByNftId(num).call()
                print("ID: "+ str(num) +" Token Data:"+str(data)+"\n")
                dataFile.write(str(num) +","+str(data)+"\n")
#获取PSE的所有数据
def getAllPseData():
    #datalist = []
    pse = getContract('0x7bC854cD4A8a9ae13Fc32D8FAA586441B08490A5','abi/pse.json')
    total = pse.functions.totalSupply().call()
    print("PSE total num :{} \n",total)

    with open('pse_polygon_.csv', 'a+') as dataFile:
        for i in range(total):
            data = pse.functions.getItemDataByNFTId(i).call()
            print("PSE get Id{} data :{} \n",i,data)
            #datalist.append(data)
            dataFile.write(str(i)+','+str(data)+'\n')

def getAccount(mainnet = 1):
    w3.eth.account.enable_unaudited_hdwallet_features()
    ''' from private
    f = Path("nomic.txt")
    if (f.exists == False) :
        acct,monic = w3.eth.account.create_with_mnemonic()
        fMonic = open("nomic.txt","w")
        fMonic.writelines(monic)
        fMonic.close()
    else :
        fMonic = open("nomic.txt","r")
        monic = fMonic.read()
        acct = w3.eth.account.from_mnemonic(monic.strip('\n'))
        fMonic.close()
    '''
    if mainnet == 1:
        pk = open("9359p.key","r")
    else:
        pk = open("testOwner.txt","r")
    #pk = open("private.key","r")
    return w3.eth.account.from_key(pk.read().strip('\n'));



def loadAddressLst( file):
    addrList = []
    idList = []
    with open(file, 'r') as dataFile:
        csv_reader = reader(dataFile)
        for row in csv_reader :
            addrList.append(w3.toChecksumAddress(row[0].strip()))
            idList.append(int(row[1]))
    return addrList,idList

def getContract(addr,abijson):
    eg =  open(abijson,'r') 
    egse = json.load(eg)
    contEg = w3.eth.contract(address=w3.toChecksumAddress(addr),abi=egse)
    return contEg

def sendTrans(idList,AddrList,contEg,acct,batchCnt):
        trans = contEg.functions.adminAirdropBatchClaim(idList,AddrList).buildTransaction({
            'gas': 170000*batchCnt,
            'gasPrice': w3.toWei('30', 'gwei'),
            'from': acct.address,
            'nonce':w3.eth.getTransactionCount(acct.address)
            })
        signed_txn  = acct.signTransaction(trans)
        txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
        print("sendTrans :" + w3.toHex(txn_hash)+ "\n")

def generateRandom(contEg,acct):
        trans = contEg.functions.generateRandom().buildTransaction({
            'gas': 300000,
            'gasPrice': w3.toWei('30', 'gwei'),
            'from': acct.address,
            'nonce':w3.eth.getTransactionCount(acct.address)
            })
        signed_txn  = acct.signTransaction(trans)
        txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
        print("generateRandom txHash: " + w3.toHex(txn_hash) +"\n")

def requireRandom(contEg,acct):
        trans = contEg.functions.requireRandFromRouter().buildTransaction({
            'gas': 70000,
            'gasPrice': w3.toWei('30', 'gwei'),
            'from': acct.address,
            'nonce':w3.eth.getTransactionCount(acct.address)
            })
        signed_txn  = acct.signTransaction(trans)
        txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
        print("requireRandom txHash: " + w3.toHex(txn_hash) +"\n")
def fillAddrandIds(addrs,sourceIds,each):
    newAddrs = []
    newIds = []
    for index in range(0,len(addrs)):
        for eachIndex in range(0,each):
            newAddrs.append(addrs[index] )
            newIds.append(sourceIds[index]+ eachIndex) 
    return newAddrs,newIds
'''
#1.读取账户
#2.循环读取excel地址to list
#3.组装trans
# '''
'''
    mumbai EGSE addr:0x381d931c9B064c83249E1f50EBf692751376CfFA
    polygon EGSE addr:0x77bbfCED92aEEdDb1EfbC4a062447a7c45155a22
'''
def egseAirDrop(dropFile,stage,each=1,mainnet = 1):
    batchCnt = 60
    
    acc = getAccount(mainnet)
    print("the account address:"+str(acc.address))
    contEg = getContract('0x77bbfCED92aEEdDb1EfbC4a062447a7c45155a22','egse.json')
    print("load Contract success \n")
    addrs,ids = loadAddressLst(dropFile)
    print("load address list"+str(len(addrs))+"\n")
    idx = 0
    while(idx < len(addrs)):
        dropAddrs,dropIds = fillAddrandIds(addrs[idx:idx + int(batchCnt/each)],ids[idx:idx + int(batchCnt/each)],each)
        try:
            if(stage <= 1 ):
            #1. get random first
                affCnt = w3.eth.getTransactionCount(acc.address)
                ret = generateRandom(contEg,acc)
                #nonce = nonce+1
                print("generateRandom returns " + str(ret)+"\n")
                while(w3.eth.getTransactionCount(acc.address) == affCnt):
                    time.sleep(60)
        except Exception as e:
                stage = 1
                print(e)
                continue
        try:
            if(stage <=2):
            #2.require random result 
                affCnt = w3.eth.getTransactionCount(acc.address)
                result = requireRandom(contEg,acc)
                #nonce = nonce+1
                print("require random returns random :\n")
                while(w3.eth.getTransactionCount(acc.address) == affCnt):
                    time.sleep(60)
        except Exception as e:
            stage = 2
            print(e)
            continue

        try:
            #3.send airdrop request
            if(stage <=3):
                print("start to send trans with dropIds:"+str(dropIds[0])+"\n")
                affCnt = w3.eth.getTransactionCount(acc.address)
                sendTrans(dropIds,dropAddrs,contEg,acc,batchCnt)
                while(w3.eth.getTransactionCount(acc.address) == affCnt):
                    time.sleep(60)
                
        except Exception as e:
            stage = 3
            print(e)
            continue
        stage = 0
        idx += int(batchCnt/each)
    #if(addrs.lenth % 100 )

def bsGetRandAndProcess(pseBsCont,acct,num):
    trans = pseBsCont.functions.getRandomAndProcess(num).buildTransaction({
                'gas': 260000*num,
                'gasPrice': w3.toWei('30', 'gwei'),
                'from': acct.address,
                'nonce':w3.eth.getTransactionCount(acct.address)
                })
    signed_txn  = acct.signTransaction(trans)
    txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
    print("getRandomAndProcess txHash: " + w3.toHex(txn_hash) +"\n")
    '''
    BlackSmith add LSE blacklist 
    '''
def bsAddBlackList(pseBsCont,acct,tokenIdLst):
    trans = pseBsCont.functions.addBlackList(tokenIdLst).buildTransaction({
                'gas': 4560000,
                'gasPrice': w3.toWei('30', 'gwei'),
                'from': acct.address,
                'nonce':w3.eth.getTransactionCount(acct.address)
                })
    signed_txn  = acct.signTransaction(trans)
    txn_hash = w3.eth.sendRawTransaction(signed_txn.rawTransaction)
    print("bsAddBlackList txHash: " + w3.toHex(txn_hash) +"\n")

def pseBlackSmithProcessTimer(num):
    #1.check if have flight to wait randonness
    #2.revoke the request random and process 
    acc = getAccount()
    print("the account address:"+str(acc.address))
    pseBsCont = getContract('0x6B351dcEAd26a7241CaDCfA0e61d1CF597ed68D4','abi/pseBlacksmith.json')
    tick = 0
    while(1):
        try:
            reqIds = pseBsCont.functions.getInFlightReqIdList().call()
            print("getInFlightReqIdList getListsize: " + str(len(reqIds)) +"\n")
            if(len(reqIds) >10 or tick >=10):
                if(len(reqIds)>10):
                    reqNum = 10
                else:
                    reqNum = len(reqIds)
                if(reqNum != 0):
                    bsGetRandAndProcess(pseBsCont,acc,reqNum )
                tick = 0
            else:
                tick = tick+1
            time.sleep(60)
        except:
            print("exception occur")

def pseBlackSmithTimer(idfile,batchNum):
    #1.check if have flight to wait randonness
    #2.revoke the request random and process 
    acc = getAccount()
    idx = 0
    print("the account address:"+str(acc.address))
    pseBsCont = getContract('0x6B351dcEAd26a7241CaDCfA0e61d1CF597ed68D4','abi/pseBlacksmith.json')
    addrs,ids = loadAddressLst(idfile)
    print("load address list"+str(len(addrs))+"\n")

    while(idx < len(ids)):
        try:
            fillIds = ids[idx:idx+batchNum]
            bsAddBlackList(pseBsCont,acc,fillIds)           
        except Exception  as e:
            print(e)
        idx += batchNum
        time.sleep(60)
def fhcAddPower():
    # 1.read data from chain
    # 2.rewrite the power data and batch write data on chain
    acc = getAccount()
    idx = 0
    print("the account address:"+str(acc.address))
    fhcCont = getContract('0x8b1222d248c598074544c0e4507ae3d1d788008e','abi/fhc.json')
    totalNum = fhcCont.functions.totalSupply().call()
    count = 0
    while( count < totalNum):
        data = fhcCont.funtions.getDataByNftId(count).call()
        data = data&FFFFFFFFFFFFFFFFFFFFFFFFFF



def getSign(content):
    acc = getAccount()
    signable = encode_defunct(hexstr=content)
    print("signable head" ,signable.header)
    sign_msg = acc.sign_message(encode_defunct(hexstr=content))
    return Web3.toHex(sign_msg.signature),Web3.toHex(sign_msg.messageHash),to_32byte_hex(sign_msg.r),to_32byte_hex(sign_msg.s),sign_msg.v

def checkSign(content,sign):
    message = encode_defunct(hexstr=content)
    addr = w3.eth.account.recover_message(message, signature=sign)
    return addr
def to_32byte_hex(val):
    return Web3.toHex(Web3.toBytes(val).rjust(32, b'\0'))
