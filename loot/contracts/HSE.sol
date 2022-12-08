// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "@openzeppelin/contracts/utils/Strings.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Burnable.sol";

contract HSE is ERC721Enumerable, ERC721Burnable, Ownable {

    using Counters for Counters.Counter;
    using Strings for uint256;
    
    string private theBaseUri;
    mapping(uint256 => bool) blackList;
    uint8 private maxNumber = 10;
    uint256 private claimPriceCent =10*10**16; //0.1bnb
    Counters.Counter private _tokenIdCounter;

    uint256 private maxSupply = 500;

 

    event HSEMint(address to, uint256 nftId);
    constructor() ERC721("Halloween of Spark Era", "HSE") {
        theBaseUri="https://api.firework.games/hse/metadata/";
        maxNumber = 10;
        maxSupply = 500;
    }

    /* this area is for the admin zone */

    function addBlackList(uint256[] calldata tokenIdList) external onlyOwner {
        for (uint256 i = 0; i < tokenIdList.length; i++) {
            blackList[tokenIdList[i]] = true;
        }
    }

    function removeBlackList(uint256[] calldata tokenIdList)
        external
        onlyOwner
    {
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
    function setBaseUri(string memory newBaseUri) public onlyOwner {
        theBaseUri = newBaseUri;
    }
    
    function getMaxSupply() public view returns  (uint256 max){
        return maxSupply;
    } 
    function setMaxSupply(uint256 max) public onlyOwner{

        require(max > totalSupply(),"must bigger than the supply now.");

        maxSupply = max;
    }

    /* admin zone end*/
    function _baseURI() internal view override returns (string memory) {
        return theBaseUri;
    }

    function tokenURI(uint256 tokenId)
        public
        view
        override
        returns (string memory)
    {
        uint256 tag = 1;
        require(
            _exists(tokenId),
            "ERC721Metadata: URI query for nonexistent token"
        );

        string memory baseURI = _baseURI();
        uint256[] memory tokenList = new uint256[](1);
        tokenList[0] = tokenId;

        if(isInBlackList(tokenList)){
            tag = 0xFF;
        }
        return
            bytes(baseURI).length > 0
                ? string(
                    abi.encodePacked(
                        baseURI,
                        tokenId.toString(),
                       "/",
                       tag.toString()
                   )
                )
                : "";
    }
//
   function _beforeTokenTransfer(
        address from,
        address to,
        uint256 tokenId
    ) internal override(ERC721, ERC721Enumerable) {
        //check whether this token is in black list
        //if (to != address(0) && isInBlackList(tokenId)) {
        //    revert("This tokenId is in black list,You can not transfer it");
        //}
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


    ///claim interface for user
    function hseClaim(uint256 number) payable public {
        //require(tokenIds.length <= 60);

        require(number + balanceOf(msg.sender) <= maxNumber,"claim number is exceed the setting.");
        require(number + totalSupply() <= maxSupply,"MaxSupply is exceed.");
        require(msg.value>= number*claimPriceCent,"not enough BNB for the claim.");

        doMint(msg.sender, number);

        
    }

    function hseAdminClaim(  address[] memory addList) public onlyOwner {

        //require(number + balanceOf(msg.sender) <= maxNumber,"claim number is exceed the setting.");
        uint256 tokenIdCurrent = _tokenIdCounter.current();
        for(uint i = 0; i< addList.length; i++){  
            _safeMint(addList[i], _tokenIdCounter.current());
            _tokenIdCounter.increment();
            emit HSEMint(addList[i], tokenIdCurrent);
        }
    }
    function doMint(address to, uint256 itemNumber) internal returns (uint256) {
        
        uint256 tokenIdCurrent = _tokenIdCounter.current();
        for(uint i = 0; i< itemNumber; i++){
        _safeMint(to, _tokenIdCounter.current());       
        _tokenIdCounter.increment();
        emit HSEMint(to, tokenIdCurrent);
        
        }
        return _tokenIdCounter.current();
    }
    function transferOutEth( address payable  to)  public onlyOwner {
       to.transfer(address(this).balance);
    }
}