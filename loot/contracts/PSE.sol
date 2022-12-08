// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

import "@openzeppelin/contracts/utils/Strings.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Burnable.sol";

/**
 PSE NFT impl
 */
contract PSE is ERC721Enumerable, ERC721Burnable, Ownable {
    using Counters for Counters.Counter;
    using Strings for uint256;

    Counters.Counter private _tokenIdCounter;
    string private theBaseUri;
    mapping(uint256 => uint256) private itemDataMapping;

    mapping(uint256 => bool) private blackList;
    mapping(address => bool) private allowMintAddress;

    constructor() ERC721("Passport of Spark Era", "PSE") {
        theBaseUri="https://api.firework.games/pse/metadata/";
    }

    event PSEMint(address to, uint256 nftId, uint256 itemData);

    function forge(address to, uint256 itemData) public returns (uint256) {
        require(
            allowMintAddress[msg.sender],
            "You're not allow to call this funtion"
        );
        return doMint(to, itemData);
    }

    function doMint(address to, uint256 itemData) internal returns (uint256) {
        uint256 tokenIdCurrent = _tokenIdCounter.current();

        _safeMint(to, _tokenIdCounter.current());
        itemDataMapping[_tokenIdCounter.current()] = itemData;

        _tokenIdCounter.increment();
        emit PSEMint(to, tokenIdCurrent, itemData);
        return tokenIdCurrent;
    }

    /**let new card pack merchant to mint our nft */
    function mutateForgerAddr(address forgerAddr, bool op) public onlyOwner {
        allowMintAddress[forgerAddr] = op;
    }

    function setBaseUri(string memory newBaseUri) public onlyOwner {
        theBaseUri = newBaseUri;
    }

    function _baseURI() internal view override returns (string memory) {
        return theBaseUri;
    }

    function tokenURI(uint256 tokenId)
        public
        view
        override
        returns (string memory)
    {
        require(
            _exists(tokenId),
            "ERC721Metadata: URI query for nonexistent token"
        );

        string memory baseURI = _baseURI();
        return
            bytes(baseURI).length > 0
                ? string(
                    abi.encodePacked(
                        baseURI,
                        tokenId.toString(),
                        "/",
                        itemDataMapping[tokenId].toString()
                    )
                )
                : "";
    }

    //===============BLACK LIST=============

    function addBlackList(uint256[] calldata tokenIdList) external onlyOwner {
        for (uint256 i = 0; i < tokenIdList.length; i++) {
            blackList[tokenIdList[i]] = true;
        }
    }

    function removeBlackList(uint256[] calldata tokenIdList) external onlyOwner {
        for (uint256 i = 0; i < tokenIdList.length; i++) {
            blackList[tokenIdList[i]] = false;
        }
    }

    function isInBlackList(uint256[] memory tobeTest)
        public
        view
        returns (bool)
    {
        for (uint256 i = 0; i < tobeTest.length; i++) {
            if (blackList[tobeTest[i]]) {
                return true;
            }
        }
        return false;
    }

    function isInBlackList(uint256 tobeTest) public view returns (bool) {
       return blackList[tobeTest];
    }

   
    function _beforeTokenTransfer(
        address from,
        address to,
        uint256 tokenId
    ) internal override(ERC721, ERC721Enumerable) {
        //check whether this token is in black list
        if (to != address(0) && isInBlackList(tokenId)) {
            revert("This tokenId is in black list,You can not transfer it");
        }
        super._beforeTokenTransfer(from, to, tokenId);
    }
    
    function supportsInterface(bytes4 interfaceId)
        public
        view
        override(ERC721, ERC721Enumerable)
        returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }

    /**get All TokenId By Address   */

    function getAllTokenIdByAddress(address user)
        public
        view
        returns (uint256[] memory)
    {
        uint256 userBalance = balanceOf(user);
        uint256[] memory tokens = new uint256[](userBalance);
        for (uint256 i = 0; i < userBalance; i++) {
            tokens[i] = tokenOfOwnerByIndex(user, i);
        }
        return tokens;
    }

    /** get all item id by address */
    function getAllitemDataByAddress(address user)
        public
        view
        returns (uint256[] memory)
    {
        uint256 userBalance = balanceOf(user);
        uint256[] memory items = new uint256[](userBalance);
        for (uint256 i = 0; i < userBalance; i++) {
            items[i] = itemDataMapping[tokenOfOwnerByIndex(user, i)];
        }
        return items;
    }

     /** get all item id by address */
    function getItemDataByNFTId(uint256 tokenId)
        public
        view
        returns (uint256 )
    {
        return  itemDataMapping[tokenId];        
    }

}
