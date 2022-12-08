// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

interface ILSEExplain {
    function tokenURI(uint256 tokenId, uint256 tokenData)
        external
        view
        returns (string memory);
}
