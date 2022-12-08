#coding:utf-8
#from web3.auto.infura.mumbai import w3
from web3.auto.infura.rpc_mumbai import w3
#from web3.auto.infura.polygon import w3
#from web3.auto.infura.bsc_main import w3
from web3.auto.infura.bsc_testnet import w3 as w3_bsc
#from web3.auto.infura.ankr_polygon import w3
from csv import reader
from lootAnalysis import getContract
import json
import time
import math
import struct


'''
数据配置区域
'''
pseMigrateAddress = '0x4fF414589FaF8Be96662C2F6a7e9F2b36261803d'
psePolygon = '0x574FdFE13b210784DdA48d59f7Be808fcb6f4248'
bscPseBlackSmith = '0x6BB3B5e65A755043216b5e7D292A50525910437d'
topics = ['0x89f29ded5446381136155fea5021971b248f61c84a3068c754f6664881c9f073']
fromblock = 25290179
blockLen = 2000
batchNum = 150
'''
读取PSE链上数据
'''
def getContract(addr,abijson):
    eg =  open(abijson,'r') 
    egse = json.load(eg)
    contEg = w3.eth.contract(address=w3.toChecksumAddress(addr),abi=egse)
    return contEg

def devide(s):
    s1 = s[2:len(s)//2+1]
    s2 = s[len(s)//2+1:]
    return s1,s2

def getMetaData(conPse,pseId):
    print("begin to call getItemDataByNFTId {}".format(pseId))
    data = conPse.functions.getItemDataByNFTId(int(pseId)).call()
    return int(data)

def getData(s,conPse):
    #print("the data is {} {}\n".format(s,type(s)))
    s1,s2=devide(s)
    #NftId,toAddress,MetaData
    #file.write()
    tokenID = '0x'+s1[len(s1)-8:]
    address = w3.toChecksumAddress('0x' +s2[len(s2)-40:])
    print('the tokenId address is ',tokenID,address)
    return int(tokenID,16),address, getMetaData(conPse,int(tokenID,16))
def getForged(pseid):
    conForge = getContractbyWeb3(w3_bsc,bscPseBlackSmith,'abi/pseblacksmith.json')
    return conForge.functions.queryForged(pseid).call()

def read_pseMigrate(fromblock,toblock,conPse):
    pseIdLst = []
    addressLst = []
    metaDataLst = []
    filter = w3.eth.filter({'fromBlock': fromblock, 'toBlock': toblock, 'address': w3.toChecksumAddress(pseMigrateAddress),'topics':topics})
    print("begin to read block no.{} to {} pse destroyed data.\n".format(fromblock,fromblock+blockLen))
    for event in filter.get_all_entries():
        pseid,addr,meta = getData(event['data'],conPse) 
        if getForged(pseid) == 0:
            pseIdLst.append(pseid)
            addressLst.append(addr)
            metaDataLst.append(meta)

    return pseIdLst,addressLst,metaDataLst

            
def pse_destroyReadProc(fromblock,latest):
    pseLst,addrLst,metaLst = [],[],[]
    conPse = getContract(psePolygon,'abi/pse.json')
    while fromblock < latest:
        if fromblock + blockLen > latest:
            toBlock = latest
        else:
            toBlock = fromblock + blockLen
        pseIdLst,addressLst,metaDataLst = read_pseMigrate(fromblock,toBlock,conPse)
        fromblock += blockLen
        pseLst.extend(pseIdLst)
        addrLst.extend(addressLst)
        metaLst.extend(metaDataLst)
        fromblock = toBlock
    
    #record all data to file
    writeToFile(pseLst,addrLst,metaLst,toBlock)
    #submit to BSC network to merge
    round =  (len(pseLst) + batchNum -1 )//batchNum
    print('round is ',round)
    for i in range(round):
        pseIdLst =    pseLst[ i*batchNum : (i+1)*batchNum if (i+1)*batchNum <len(pseIdLst) else len(pseLst)]
        addressLst =  addrLst[i*batchNum : (i+1)*batchNum if (i+1)*batchNum <len(addrLst) else len(addrLst)]
        metaDataLst = metaLst[i*batchNum : (i+1)*batchNum if (i+1)*batchNum <len(metaLst) else len(metaLst)]
        forgeBscPse(pseIdLst,addressLst,metaDataLst)
    return latest


def writeToFile(pseLst,addrLst,metaLst,toBlock):
    with open('pse_record.csv', 'a+') as dataFile:
        for i in range(len(pseLst)):
            dataFile.write(str(pseLst[i])+","+str(addrLst[i])+","+str(metaLst[i])+","+str(toBlock)+"\n")
    dataFile.close()

def procHistoryData():
    maxblock = w3.eth.get_block_number()
    last = pse_destroyReadProc(fromblock,maxblock)
    return last



def getContractbyWeb3(web3,addr,abijson):
    contabi =  json.load(open(abijson,'r') )
    contEg = web3.eth.contract(address=web3.toChecksumAddress(addr),abi=contabi)
    return contEg


def getAccount(mainnet = 1):
    w3_bsc.eth.account.enable_unaudited_hdwallet_features()
    if mainnet == 1:
        pk = open("9359p.key","r")
    else:
        pk = open("testOwner.txt","r")
    #pk = open("private.key","r")
    return w3_bsc.eth.account.from_key(pk.read().strip('\n'));

def forgeBscPse(pseIdLst,addtoList,metaDataList):
    print("begin to forgeBscPse with lst {} ,{},{}".format(pseIdLst,addtoList,metaDataList))
    acc = getAccount(0)
    nonce = w3_bsc.eth.getTransactionCount(acc.address)
    conForge = getContractbyWeb3(w3_bsc,bscPseBlackSmith,'abi/pseblacksmith.json')
    trans = conForge.functions.forgeOnBscBatch(pseIdLst,addtoList,metaDataList).buildTransaction({
                #'gas': 80000,
                'gasPrice': w3.toWei('30', 'gwei'),
                'from': acc.address,
                'nonce':nonce
                })
    signed_txn  = acc.signTransaction(trans)
    txn_hash = w3_bsc.eth.sendRawTransaction(signed_txn.rawTransaction)
    print(" forgeBscPse txHash: " + w3_bsc.toHex(txn_hash) +"\n")
    while(w3_bsc.eth.getTransactionCount(acc.address) == nonce):
        time.sleep(60)

if __name__ == '__main__':
    while True:
        fromblock = procHistoryData()
        time.sleep(300)