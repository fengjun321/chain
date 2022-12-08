// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;


interface ILinkRouter {
    function sendRequest(uint256 reqId)
        external
        returns (bool);


    function getRandom(uint256 reqId)
        external
        view
        returns (uint256 );
    
    function getRandomList(uint256[] memory reqIdLst)  external view returns (uint256[] memory results );
}