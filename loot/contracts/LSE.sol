// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import "@openzeppelin/contracts/token/ERC721/extensions/ERC721Enumerable.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "./ILSEExplain.sol";

contract LSE is ERC721Enumerable, Ownable {
    mapping(address => uint256) private userHoldsMapping;
    mapping(uint256 => uint256) private tokenDataMapping;
    
    ILSEExplain private explain ;

    constructor(address explainAddr) ERC721("Loot for Spark Era", "LSE") {
        explain = ILSEExplain(explainAddr);

    }

    function changeExplain(address newExplain) public onlyOwner{
        explain = ILSEExplain(newExplain);
    }


    function claim(uint256 tokenId, uint8 sex) public returns (uint256) {
        require(
            userHoldsMapping[msg.sender] < 5,
            "One can only hold no more than 5 tokens"
        );
        require(
            tokenId > 0 && tokenId <= 49000,
            "Only token Id between 1 and 49000 can be mint"
        );
        uint256 tokenData = genTokenData(tokenId, sex);
        safeMintInterval(msg.sender, tokenId, tokenData);
        return tokenData;
    }

    function ownerClaim(
        uint256 tokenId,
        address to,
        uint8 sex
    ) public onlyOwner returns (uint256) {
        require(
            tokenId > 49000 && tokenId <= 50000,
            "Only token Id between 49000 and 50000 can be mint"
        );

        uint256 tokenData = genTokenData(tokenId, sex);
        safeMintInterval(to, tokenId, tokenData);
        return tokenData;
    }
    function ownerAirdropBatchClaim(uint256[] memory tokenIds, address[] memory receivers) public  onlyOwner {
        //require(tokenIds.length <= 60);
        require(tokenIds.length == receivers.length);
        for (uint256 i = 0; i < tokenIds.length; i++) {
            uint256 tokenId = tokenIds[i];
            address receiver = receivers[i];
            require(tokenId > 49000 && tokenId <= 50000, "Token ID invalid");
            uint256 tokenData = genTokenData(tokenId, 0);
            safeMintInterval(receiver, tokenId, tokenData);
        }
        
    }
    

    function isTokenExist(uint256 tokenId) public view returns (bool) {
        return _exists(tokenId);
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
        userHoldsMapping[to] = userHoldsMapping[to] + 1;
        tokenDataMapping[tokenId] = theTokenData;
    }

    //random function
    function rnd1(uint256 salt, uint16 upperLimit)
        internal
        view
        returns (uint16 r1)
    {
        uint256 r = uint256(
            keccak256(
                abi.encodePacked(
                    block.timestamp,
                    msg.sender,
                    salt,
                    msg.sender.balance
                )
            )
        );
        r1 = uint16(r % upperLimit);
    }

    //Generate token data
    function genTokenData(uint256 tokenId, uint8 sex)
        public
        view
        returns (uint256)
    {
        uint256 data = sex;
        uint256 camp = genCampItemData(genCampValue(tokenId));
        uint256 occupation = genOccupationItemData(genOccupationValue(tokenId));
        uint256 level = genLevelItemData(genLevelValue(tokenId));
        uint256 special = genSpecialItemData(genSpecialValue(tokenId));
        uint256 model = genModelItemData(genModelValue(tokenId));
        uint256 health = genHealthItemData(genHealthValue(tokenId));
        uint256 power = genPowerItemData(genPowerValue(tokenId));
        uint256 armor = genArmorItemData(genArmorValue(tokenId));
        uint256 specialArmor = genSpecialWeaponsItemData(
            genSpecialWeaponsValue(tokenId)
        );

        data |= camp;
        data |= occupation;
        data |= level;
        data |= special;
        data |= model;
        data |= health;
        data |= power;
        data |= armor;
        data |= specialArmor;

        return data;
    }

    function getTokenData(uint256 tokenId) public view returns (uint256) {
        return tokenDataMapping[tokenId];
    }

    function tokenURI(uint256 tokenId)
        public
        view
        override
        returns (string memory)
    {
        uint256 data = tokenDataMapping[tokenId];
        require(data != 0, "There is no data for this token");

        return explain.tokenURI(tokenId, data);
    }

    // The following functions are overrides required by Solidity.

    function _beforeTokenTransfer(
        address from,
        address to,
        uint256 tokenId
    ) internal override(ERC721Enumerable) {
        super._beforeTokenTransfer(from, to, tokenId);
    }

    function _burn(uint256 tokenId) internal override(ERC721) {
        super._burn(tokenId);
    }

    function supportsInterface(bytes4 interfaceId)
        public
        view
        override(ERC721Enumerable)
        returns (bool)
    {
        return super.supportsInterface(interfaceId);
    }
    
    //function updateCamp

    //GENERATED CODE

    //begin of getPartByItemData
    function getCampValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 8) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genCampItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 8;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genCampValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(0 + nftId, 1000);
        if (rnd >= 0 && rnd < 250) {
            return 1;
        } else if (rnd >= 250 && rnd < 500) {
            return 2;
        } else if (rnd >= 500 && rnd < 750) {
            return 3;
        } else if (rnd >= 750 && rnd < 1000) {
            return 4;
        }
        return 0;
    }

    //begin of getPartByItemData
    function getOccupationValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 16) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genOccupationItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 16;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genOccupationValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(1 + nftId, 1000);
        if (rnd >= 0 && rnd < 15) {
            return 1;
        } else if (rnd >= 15 && rnd < 30) {
            return 2;
        } else if (rnd >= 30 && rnd < 50) {
            return 3;
        } else if (rnd >= 50 && rnd < 110) {
            return 4;
        } else if (rnd >= 110 && rnd < 170) {
            return 5;
        } else if (rnd >= 170 && rnd < 350) {
            return 6;
        } else if (rnd >= 350 && rnd < 530) {
            return 7;
        } else if (rnd >= 530 && rnd < 650) {
            return 8;
        } else if (rnd >= 650 && rnd < 1000) {
            return 9;
        }
        return 0;
    }

    //end of genXXXValue

    //begin of getPartByItemData
    function getLevelValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 24) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genLevelItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 24;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genLevelValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(2 + nftId, 1000);
        if (rnd >= 0 && rnd < 50) {
            return uint8(rnd1(3 + nftId, 10) + 90);
        } else if (rnd >= 50 && rnd < 170) {
            return uint8(rnd1(4 + nftId, 29) + 60);
        } else if (rnd >= 170 && rnd < 530) {
            return uint8(rnd1(5 + nftId, 19) + 40);
        } else if (rnd >= 530 && rnd < 1000) {
            return uint8(rnd1(6 + nftId, 39) + 0);
        }
        return 0;
    }

    //end of genXXXValue

    //end of getValueString

    //begin of getPartByItemData
    function getSpecialValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 32) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genSpecialItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 32;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genSpecialValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(7 + nftId, 1000);
        if (rnd >= 0 && rnd < 15) {
            return uint8(rnd1(8 + nftId, 9) + 1);
        }
        return 0;
    }

    //end of genXXXValue

    //begin of getPartByItemData
    function getModelValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 40) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genModelItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 40;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genModelValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(9 + nftId, 1000);
        if (rnd >= 0 && rnd < 10) {
            return 1;
        } else if (rnd >= 10 && rnd < 20) {
            return 2;
        } else if (rnd >= 20 && rnd < 35) {
            return 3;
        } else if (rnd >= 35 && rnd < 45) {
            return 4;
        } else if (rnd >= 45 && rnd < 50) {
            return 5;
        } else if (rnd >= 50 && rnd < 60) {
            return 6;
        } else if (rnd >= 60 && rnd < 80) {
            return 7;
        } else if (rnd >= 80 && rnd < 95) {
            return 8;
        } else if (rnd >= 95 && rnd < 110) {
            return 9;
        } else if (rnd >= 110 && rnd < 130) {
            return 10;
        } else if (rnd >= 130 && rnd < 150) {
            return 11;
        } else if (rnd >= 150 && rnd < 170) {
            return 12;
        } else if (rnd >= 170 && rnd < 220) {
            return 13;
        } else if (rnd >= 220 && rnd < 260) {
            return 14;
        } else if (rnd >= 260 && rnd < 310) {
            return 15;
        } else if (rnd >= 310 && rnd < 360) {
            return 16;
        } else if (rnd >= 360 && rnd < 400) {
            return 17;
        } else if (rnd >= 400 && rnd < 450) {
            return 18;
        } else if (rnd >= 450 && rnd < 530) {
            return 19;
        } else if (rnd >= 530 && rnd < 630) {
            return 20;
        } else if (rnd >= 630 && rnd < 720) {
            return 21;
        } else if (rnd >= 720 && rnd < 800) {
            return 22;
        } else if (rnd >= 800 && rnd < 880) {
            return 23;
        } else if (rnd >= 880 && rnd < 940) {
            return 24;
        } else if (rnd >= 940 && rnd < 1000) {
            return 25;
        }
        return 0;
    }

    //end of genXXXValue

    //end of getcolor

    //begin of getPartByItemData
    function getHealthValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 48) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genHealthItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 48;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genHealthValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(10 + nftId, 1000);
        if (rnd >= 0 && rnd < 50) {
            return uint8(rnd1(11 + nftId, 10) + 90);
        } else if (rnd >= 50 && rnd < 170) {
            return uint8(rnd1(12 + nftId, 29) + 60);
        } else if (rnd >= 170 && rnd < 530) {
            return uint8(rnd1(13 + nftId, 19) + 40);
        } else if (rnd >= 530 && rnd < 1000) {
            return uint8(rnd1(14 + nftId, 39) + 0);
        }
        return 0;
    }

    //end of genXXXValue

    //end of getcolor

    //begin of getPartByItemData
    function getPowerValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 56) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genPowerItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 56;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genPowerValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(15 + nftId, 1000);
        if (rnd >= 0 && rnd < 50) {
            return uint8(rnd1(16 + nftId, 10) + 90);
        } else if (rnd >= 50 && rnd < 170) {
            return uint8(rnd1(17 + nftId, 29) + 60);
        } else if (rnd >= 170 && rnd < 530) {
            return uint8(rnd1(18 + nftId, 19) + 40);
        } else if (rnd >= 530 && rnd < 1000) {
            return uint8(rnd1(19 + nftId, 39) + 0);
        }
        return 0;
    }

    //end of getcolor

    //begin of getPartByItemData
    function getArmorValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 64) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genArmorItemData(uint8 value) public pure returns (uint256) {
        uint256 r = value;
        r = r << 64;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genArmorValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(20 + nftId, 1000);
        if (rnd >= 0 && rnd < 50) {
            return uint8(rnd1(21 + nftId, 10) + 90);
        } else if (rnd >= 50 && rnd < 170) {
            return uint8(rnd1(22 + nftId, 29) + 60);
        } else if (rnd >= 170 && rnd < 530) {
            return uint8(rnd1(23 + nftId, 19) + 40);
        } else if (rnd >= 530 && rnd < 1000) {
            return uint8(rnd1(24 + nftId, 39) + 0);
        }
        return 0;
    }

    //end of genXXXValue

    //end of getValueString

    //begin of getPartByItemData
    function getSpecialWeaponsValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 72) & 0xFF;
    }

    //end of getPartByItemData

    //begin of genPartByItemData
    function genSpecialWeaponsItemData(uint8 value)
        public
        pure
        returns (uint256)
    {
        uint256 r = value;
        r = r << 72;
        return r;
    }

    //end of genPartByItemData

    //begin of genXXXValue
    function genSpecialWeaponsValue(uint256 nftId) public view returns (uint8) {
        uint16 rnd = rnd1(25 + nftId, 1000);
        if (rnd >= 0 && rnd < 15) {
            return uint8(rnd1(26 + nftId, 9) + 1);
        }
        return 0;
    }
    //end of genXXXValue
}
