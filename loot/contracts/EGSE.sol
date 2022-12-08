// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

import "@openzeppelin/contracts/utils/math/SafeMath.sol";
import "@openzeppelin/contracts/utils/Strings.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Burnable.sol";
import "./ILinkRouter.sol";
/**
 EGSE NFT impl
 */
contract EGSE is ERC721Enumerable, ERC721Burnable, Ownable {
    using Counters for Counters.Counter;
    using Strings for uint256;
    using SafeMath for uint256;
    //Counters.Counter private _tokenIdCounter;
    string private theBaseUri;
    //mapping(uint256 => uint256) private itemDataMapping;
    ILinkRouter private router;
    uint256 private reqSequence;
    bool private waitResult;
    uint256 private randResult;

    mapping(uint256 => bool) private blackList;
    //mapping(address => bool) private allowMintAddress;
    mapping(address => uint256) private userHoldsMapping;
    mapping(uint256 => uint256) private tokenDataMapping;
    mapping(address => bool) private allowMintAddress;

    event EGSEMint(address to, uint256 nftId,uint256 data);

    uint16 normal;
    uint16 rare;
    uint16 epic;
    uint16 round; 
 
    constructor(address routerAddr) ERC721("Epic Giveaway of Spark Era", "EGSE") {
        theBaseUri="https://api.firework.games/egse/metadata/";
        router = ILinkRouter(routerAddr);
        normal = 60;
        rare = 90;
        epic = 100;
        round = 100;
    }

    //function forge(address to, uint256 itemData) public returns (uint256) {
    //    require(
     //       allowMintAddress[msg.sender],
    //        "You're not allow to call this funtion"
   //     );
   //     return doMint(to, itemData);
   // }
    function changeLinkRouter(address newRouter) public onlyOwner{
        router = ILinkRouter(newRouter);
    }
    /**let new card pack merchant to mint our nft */
    function mutateForgerAddr(address forgerAddr, bool op) public onlyOwner {
        allowMintAddress[forgerAddr] = op;
    }
 
    function generateRandom() public  returns (bool){
        require(waitResult== false,"wait for random result");
        require(
           allowMintAddress[msg.sender],
            "You're not allow to call this funtion"
        );
        reqSequence.tryAdd(1);
        waitResult = true;       
        randResult = 0;
        return router.sendRequest(reqSequence);
    }
    //equire  result from router and store in state
    function requireRandFromRouter() public  returns (uint256 result) {
        require(
           allowMintAddress[msg.sender],
            "You're not allow to call this funtion"
        );
        result = router.getRandom(reqSequence);
        if(result != 0){
            waitResult = false;
            randResult = result;
        }
        return result;
    }
    function safeMintInterval(
        address to,
        uint256 tokenId,
        uint256 theTokenData
    ) private {
        require(
            tokenId > 0 && tokenId <= 50000,
            "Token Id range is between 1 and 50000"
        );
        require(!_exists(tokenId), "Token has already been minted");
        _safeMint(to, tokenId);

        emit EGSEMint(to,tokenId,theTokenData);

        userHoldsMapping[to] = userHoldsMapping[to] + 1;
        tokenDataMapping[tokenId] = theTokenData;

    }
    function adminAirdropBatch(uint256[] memory tokenIds, address[] memory receivers) public   {
        //require(tokenIds.length <= 60);
        require(
           allowMintAddress[msg.sender],
            "You're not allow to call this funtion"
        );
        require(tokenIds.length == receivers.length);
        require(randResult !=0, "should wait for random result first");
        uint256[] memory expandedValues =  expand(randResult,receivers.length);

        for (uint256 i = 0; i < tokenIds.length; i++) {
            uint256 tokenId = tokenIds[i];
            address receiver = receivers[i];
           // require(tokenId > 49000 && tokenId <= 50000, "Token ID invalid");
            uint256 tokenData = genTokenData(expandedValues[i]);
            safeMintInterval(receiver,tokenId,tokenData);
        }
        randResult = 0;
        
    }
    function adminBatchClaim(uint256[] memory tokenIds, address[] memory receivers,uint256[] memory tokenDataLst) public   {
        //require(tokenIds.length <= 60);
        require(
           allowMintAddress[msg.sender],
            "You're not allow to call this funtion"
        );
        require(tokenIds.length == receivers.length);

        for (uint256 i = 0; i < tokenIds.length; i++) {
            uint256 tokenId = tokenIds[i];
            address receiver = receivers[i];
           // require(tokenId > 49000 && tokenId <= 50000, "Token ID invalid");
            uint256 tokenData = tokenDataLst[i];
            safeMintInterval(receiver,tokenId,tokenData);
        }
        randResult = 0;
        
    }


    function genTokenData(uint256 rnd) internal view onlyOwner returns (uint256 result){
        //uint256 result = 0;
        //uint16 rnd = rnd1(0 + tokenId, 100);
        rnd = rnd % round;
        if (rnd >= 0 && rnd < normal) {
            result =  1;
        } else if (rnd >= normal && rnd < rare) {
            result = 2;
        } else if (rnd >= rare && rnd < epic) {
            result = 3;
        } 
        return result;
    }
    function changeRatio(uint16 nor,uint16 rar,uint16 ep,uint16 r) public onlyOwner{
        normal = nor;
        rare = rar;
        epic = ep;
        round = r;
    }
    function expand(uint256 randomValue, uint256 n) public pure returns (uint256[] memory expandedValues) {
        expandedValues = new uint256[](n);
        for (uint256 i = 0; i < n; i++) {
            expandedValues[i] = uint256(keccak256(abi.encode(randomValue, i)));
        }
        return expandedValues;
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
                        tokenDataMapping[tokenId].toString()
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


}
