// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/math/SafeMath.sol";
import "@openzeppelin/contracts/utils/structs/EnumerableSet.sol";
import "./ILinkRouter.sol";

contract LSELottry is Ownable{

    using SafeMath for uint256;

    ILinkRouter private router;
    //EnumerableSet.AddressSet private addrSet;
    uint256 private reqSequence;
    bool private waitResult;
    uint256 private randResult;
    //uint8 private maxRandNum;

    constructor(address routerAddr)  {
        router = ILinkRouter(routerAddr);
        waitResult = false;


    }

    function changeLinkRouter(address newRouter) public onlyOwner{
        router = ILinkRouter(newRouter);
    }

 
    function generateRandom() public onlyOwner returns (bool){
        require(waitResult== false,"wait for random result");

        reqSequence.tryAdd(1);
        waitResult = true;       
        randResult = 0;
        return router.sendRequest(reqSequence);
    }
    //equire  result from router and store in state
    function requireRandFromRouter() public onlyOwner returns (uint256 result) {
        result = router.getRandom(reqSequence);
        if(result != 0){
            waitResult = false;
            randResult = result;
        }
        return result;
    }

    function getRandomResult() public view returns(uint256 result){
        return randResult;
    }

    function genLottryList(uint8 listNum,uint256 maxRandSize,uint256 maxRandPrice) 
        public   
        view
        onlyOwner 
        returns 
        (uint256[] memory addrIndex,uint256[] memory priceList){
        
        uint256 result = 0;
        
        require(maxRandSize >0,"RandSize should bigger than 0");

        //first to Require rand Data from router
        result = router.getRandom(reqSequence);
        
        if(result != 0){
            
            addrIndex = new uint256[](listNum);
            priceList = new uint256[](listNum);

            uint256[] memory expandedValues =  expand(randResult,listNum*2);
            uint256 i = 0;
            for( ;i < expandedValues.length/2;i++){
                addrIndex[i] = expandedValues[i] % maxRandSize;
            }
            for(;i< expandedValues.length;i++){
                priceList[i-expandedValues.length/2] = expandedValues[i] % maxRandPrice + 1;
            }
           // randResult = 0;
            
        }
        return (addrIndex,priceList);
    }
    function expand(uint256 randomValue, uint256 n) public pure returns (uint256[] memory expandedValues) {
        expandedValues = new uint256[](n);
        for (uint256 i = 0; i < n; i++) {
            expandedValues[i] = uint256(keccak256(abi.encode(randomValue, i)));
        }
        return expandedValues;
    }
    function resetStatus() public onlyOwner {
        waitResult = false;
        reqSequence = 0;
        randResult = 0;
    }

}