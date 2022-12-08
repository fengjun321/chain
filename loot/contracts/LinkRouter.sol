// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/structs/BitMaps.sol";
import "./ILinkRouter.sol";
import "@chainlink/contracts/src/v0.8/VRFConsumerBase.sol";
import "@openzeppelin/contracts/utils/structs/EnumerableSet.sol";

//using BitMaps.bitmap for
contract LinkRouter is ILinkRouter, VRFConsumerBase,Ownable {
    struct linkRequstData {
        address conAddr;
        uint256 reqId;
    }
    EnumerableSet.AddressSet private addrSet;
    bytes32 internal keyHash;
    uint256 internal fee;
    uint256 internal reqIdRound;//the storage of ReqIdRound,can be reset by owner
    uint256 internal randNess;
    bytes32 internal _linkReqId;
    //mapping(address => BitMaps.BitMap) addr_trans_req;
    mapping(address => mapping(uint256 => uint256)) addr_trans_result; //addr->map(srcTransId->random result)
    mapping(bytes32 => linkRequstData) linkReq_addr; //LinkRequstId->(addr=>transId)


    constructor()
        public
        VRFConsumerBase(
            0x8C7382F9D8f56b33781fE506E897a4F1e2d17255, // polgon testnet VRF Coordinator
            0x326C977E6efc84E512bB9C30f76E30c160eD06FB // LINK Token
        )
    {
        keyHash = 0x6e75b569a01ef56d18cab6a8e71e6600d6ce853834d4a5748b720d06f878b3a4;
        fee = 0.0001 * 10**18; // 0.1 LINK (Varies by network)
        reqIdRound = 10000;
    }

    function sendRequest(uint256 reqId)  override external returns (bool) {
        
        uint256 actReqId = reqId % reqIdRound;

       // require(
       //     BitMaps.get(addr_trans_req[msg.sender], actReqId) == false,
        //    "Redundancy reqId from contract "
       // );

       require(isExistInWhiteList(msg.sender),"the caller address is not in whitelist");

        require(
            LINK.balanceOf(address(this)) >= fee,
            "Not enough LINK - fill contract with faucet"
        );
        bytes32 linkReqId = requestRandomness(keyHash, fee);

        _linkReqId = linkReqId;
        if (linkReqId != 0) {
            linkReq_addr[linkReqId].conAddr = msg.sender;
            linkReq_addr[linkReqId].reqId = actReqId;

           // BitMaps.set(addr_trans_req[msg.sender], actReqId);
            return true;
        }
        

        return false;
    }

    function queryRandDatabyAddr(address addr,uint reqId) view internal returns (uint256) {
        uint256 actReqId = reqId % reqIdRound;

        return addr_trans_result[addr][actReqId];
    }
    function getRandom(uint256 reqId)  override external view returns (uint256 ){
        return queryRandDatabyAddr(msg.sender,reqId);

    }
    function getRandomList(uint256[] memory reqIdLst) override external view returns (uint256[] memory results ){

        results = new uint256[](reqIdLst.length);

        for(uint8 i =0;i<reqIdLst.length;i++){
            results[i] =queryRandDatabyAddr(msg.sender,reqIdLst[i]);
        }
        return results;
    }
    
    function fulfillRandomness(bytes32 requestId, uint256 randomness)
        internal
        override
    {
        randNess = randomness;
        addr_trans_result[linkReq_addr[requestId].conAddr][
            linkReq_addr[requestId].reqId
        ] = randomness;

    }

    function resetMapingRound(uint256 round)  public onlyOwner {
        reqIdRound = round;
    }

    function getMapingRound()  public view onlyOwner returns (uint256) {
        return reqIdRound;
    }

    function getRandomData(address contAddr,uint256 reqId) public view onlyOwner returns (uint256){
        return queryRandDatabyAddr(contAddr,reqId);
    }
    function getRandomListData(address contAddr,uint256[] memory reqIdLst) public view onlyOwner returns (uint256[] memory results){
        
        results = new uint256[](reqIdLst.length);

        for(uint8 i =0;i<reqIdLst.length;i++){

            results[i] = queryRandDatabyAddr(contAddr,reqIdLst[i]);
        }
        return results;
    }

    function getRandness() onlyOwner public view returns(uint256 rand,bytes32 reqId){
        return (randNess,_linkReqId);
    }
    //callable address management
    function addWhiteList(address[] memory addrLst) public onlyOwner returns (uint256) {
        for(uint256 i = 0; i< addrLst.length;i++){
             if(!EnumerableSet.contains(addrSet, addrLst[i])){
                EnumerableSet.add(addrSet, addrLst[i]);
                }
        }
        return EnumerableSet.length(addrSet);

    }

    function queryWhiteList() public view onlyOwner returns (address[] memory addrLst)  {
        uint256 length = EnumerableSet.length(addrSet);
        addrLst = new address[](length);

        for(uint256 i = 0;i< length;i++){
            addrLst[i] = EnumerableSet.at(addrSet, i);
        }
        return addrLst;
    }

    
    
    function removeWhiteList(address[] memory addrLst) public onlyOwner returns (uint256){
        //uint256 counter = 0;
        for(uint256 i = 0; i< addrLst.length;i++){
             EnumerableSet.remove(addrSet, addrLst[i]);
        }
        return EnumerableSet.length(addrSet);
    }

    function isExistInWhiteList(address addr) internal view returns (bool){
        return EnumerableSet.contains(addrSet, addr);
    }
    /*
    function getLinkAddrInfo(bytes32 linkReqId ) public view returns(string memory info ){
  
        info = string(
            abi.encodePacked(
                 'linkReq_addr:[',string(linkReqId),'].linkRequstData.addr:',
                 string(linkReq_addr[linkReqId].conAddr),' ,.reqId = ',
                 string(linkReq_addr[linkReqId].reqId)
                 )
                );

        return info;
    }*/
}
