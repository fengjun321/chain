// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import "./Util.sol";
import "./ILSEExplain.sol";

contract LSEExplain is ILSEExplain  {
    

    string public constant GRAY = "#9d9d9d";
    string public constant BLUE = "#0070dd";
    string public constant PURPLE = "#a335ee";
    string public constant ORANGE = "#ff8000";

    constructor()  {}

   
    function tokenURI(uint256 tokenId,uint256 tokenData)
        public
        override
        view
       
        returns (string memory)
    {
        uint256 data = tokenData;
        require(data != 0, "There is no data for this token");

        string[11] memory parts;
        parts[
            0
        ] = '<svg xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMinYMin meet" viewBox="0 0 300 300"><style>.base{font-size:14px;}</style> <rect width="100%" height="100%"/> <image width="300" height="300" href="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAAACXBIWXMAAAsTAAALEwEAmpwYAAAHxmlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNi4wLWMwMDMgNzkuMTY0NTI3LCAyMDIwLzEwLzE1LTE3OjQ4OjMyICAgICAgICAiPiA8cmRmOlJERiB4bWxuczpyZGY9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkvMDIvMjItcmRmLXN5bnRheC1ucyMiPiA8cmRmOkRlc2NyaXB0aW9uIHJkZjphYm91dD0iIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iIHhtbG5zOmRjPSJodHRwOi8vcHVybC5vcmcvZGMvZWxlbWVudHMvMS4xLyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgMjIuMSAoV2luZG93cykiIHhtcDpDcmVhdGVEYXRlPSIyMDIxLTA5LTA5VDIzOjM1OjE4KzA4OjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDIxLTA5LTEwVDAwOjA2OjM3KzA4OjAwIiB4bXA6TW9kaWZ5RGF0ZT0iMjAyMS0wOS0xMFQwMDowNjozNyswODowMCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo5Y2JjOGIxNS1iMTMzLTVkNDQtOTJkOS1lNjVjNzlkYjgyMjYiIHhtcE1NOkRvY3VtZW50SUQ9ImFkb2JlOmRvY2lkOnBob3Rvc2hvcDo2NWRkMGYxOC1lZjU5LTJiNGMtYjljZS1mY2Y1ZTJkNTViNDEiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDphMmJlYWI5OC0wNzUxLWMzNDQtYWY0My04ZTFhNTkyYTY4MDciIHBob3Rvc2hvcDpDb2xvck1vZGU9IjAiIGRjOmZvcm1hdD0iaW1hZ2UvcG5nIj4gPHhtcE1NOkhpc3Rvcnk+IDxyZGY6U2VxPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0iY3JlYXRlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDphMmJlYWI5OC0wNzUxLWMzNDQtYWY0My04ZTFhNTkyYTY4MDciIHN0RXZ0OndoZW49IjIwMjEtMDktMDlUMjM6MzU6MTgrMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCAyMi4xIChXaW5kb3dzKSIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6OWNiYzhiMTUtYjEzMy01ZDQ0LTkyZDktZTY1Yzc5ZGI4MjI2IiBzdEV2dDp3aGVuPSIyMDIxLTA5LTEwVDAwOjA2OjM3KzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgMjIuMSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDxwaG90b3Nob3A6RG9jdW1lbnRBbmNlc3RvcnM+IDxyZGY6QmFnPiA8cmRmOmxpPmFkb2JlOmRvY2lkOnBob3Rvc2hvcDo1ZmMwYTBkNS00Njk0LWFiNDQtYTRkMC1hYzNkOWExMGFhZWI8L3JkZjpsaT4gPHJkZjpsaT5hZG9iZTpkb2NpZDpwaG90b3Nob3A6NzU0ODBhYWMtNmRhYy1jNDQzLWJhMzUtM2VmYjk4ODM0NDk4PC9yZGY6bGk+IDxyZGY6bGk+YWRvYmU6ZG9jaWQ6cGhvdG9zaG9wOmEzOThjNTc5LWYwZWQtYWU0NC1hNDNiLTZlOGQzMjhhMWI1NDwvcmRmOmxpPiA8cmRmOmxpPmFkb2JlOmRvY2lkOnBob3Rvc2hvcDphNmFjZjE5Yi0xOTJkLTNhNDUtYjVlNy1mYWVjNWRkZWNkOGM8L3JkZjpsaT4gPHJkZjpsaT54bXAuZGlkOjZhZjczMzdhLTgzNzctNmI0Ny1hNjAyLWY3ODM5MzcwNjZkZDwvcmRmOmxpPiA8cmRmOmxpPnhtcC5kaWQ6OTllYWRlYTEtNWNhNC00ZDAyLWJiZDctZTQxOWU2NDA1YjQ2PC9yZGY6bGk+IDwvcmRmOkJhZz4gPC9waG90b3Nob3A6RG9jdW1lbnRBbmNlc3RvcnM+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+dZzxBwAAA81JREFUaIHtmj2S2zYYhh9yObMsPBImVQrPhkdwqcpmmWPkCCrdGcqk8Dlyg3QpXCgzKfYILmV7i5TUjIvdGYZMQYq/APiRIrWKk7cRB3zwvQAIAgQgkGjlizD+hdh2UVOZZmveGy3CLNHi+lLLQtQ6rwrfy7CJEpUtmjUa3SpsZJgw2hgZctZVUFVaNtlgqEH8zq9FpwJc0+CAAl4NYwnw42jTG8e9r3+drm5lwb7hIXpxLJBh6aym4zFrpvYN65vsNtWSAtW6fH9TskhcvmzBAKaKH0n/Oen/11mChTLscVbT2TCvc/u2iykA8qFoyRjTrp5kmDDaIOZpEebHc5pme1kAGlOB+wUsXuWty7StAovF5XDqGx0clCynQdMaRE/2O8N0PNYeJqxYe5g4e84qlQ5jvuHKoPqzxIfB9da2xA5ubMh0rAzPVM8azaj/MhYyuYkv27yrWaON0/N3JNW4Nnx1Dev5q3A9mCfD8rNMvb0IE0YbLVVfrqxQS40eEp9tf15/ixYyjUdH0xNLYo4m0aKYbejoYL0F26ZABKaZDJNrWrvFz2E6M2bbFutgtm2Ni1VBD+eukQCmjEiVTD3YgBm2HEbVdJj1QbJrv/RT0LKMprzmTt4/BxizrBhVDNe+bwNz7dud2bwvx0c7nG36IMve1kVHpGrJ68aqJe8Mpo2+4MIafeFCDRLJ8rc06mFd31ATyLB0VlMrpmVZSzWmlplfGaH/UpO485CsxpyHHLaaxq5MNhma1xi/n2jsytfwxTUJq/cHnVi9PzjB9GC4r+zRto3r5IRpma07WqEzvt9MWghzHytXmPtYcMbVhyHVvElgbbfOiZUNs51YOSM6H1Yj4tLP9JJjiPu/HQvV9MlFXcf4thdh3fPTPlZ070gUzVbUbrqycG3dJsbkld8uTmjG8DtD7gNenr3Jd1/yY3K7J8/e5Xf9sql7wCuTy4Wq18ei75op++JH92u6A3hSL9jBKw08KeIe9iaCP7zyMymHow9EFXb645suALXGS3gNwBrVi5YdIIK/7/BPX153edjFPJKYH1J+SuGwTmGdkfJLhd2XXKoj8Ek3BPtySf47fjea723I8HirCXVpkEAXCx5RuQYvZF3vA+wqTJUpyUvYRWwDjhwDOPq5An3CEgCi0C9C/PqZR83PALz3krgy3QAc1MMqAAh+I43LZ/UnH6Pewwq5yQ+EbyHiE6wzfD6oDhbd56eETJFogE9kYYUVDRerQEPOOiUPy3kj0e+Crqn2Pci4y0DzNQJ4zMXz/AKzs/VflpOizaaZa/oPV7qj5gMOlVoAAAAASUVORK5CYII="/>';
        
        parts[1] = getCampText(data);
        parts[2] = getOccupationText(data);
        parts[3] = getLevelText(data);
        parts[4] = getSpecialText(data);
        parts[5] = getModelText(data);
        parts[6] = getHealText(data);
        parts[7] = getPowerText(data);
        parts[8] = getArmorText(data);
        parts[9] = getSpecialWeaponText(data);

        parts[10] = "</svg>";

        string memory output = string(
            abi.encodePacked(
                Util.concat4(parts[0], parts[1], parts[2], parts[3]),
                Util.concat4(parts[4], parts[5], parts[6], parts[7]),
                Util.concat2(parts[8], parts[9]),
                parts[10]
            )
        );

        string memory json = Util.b64encode(
            bytes(
                string(
                    abi.encodePacked(
                        '{"name": "LSE #',
                        Util.toString(tokenId),
                        '", "description":"Loot for Spark Era(LSE) is a free and innovative sanctuary. The NFTs are randomly minted and stored on chain ,hence the NFT spaceships can play in the GameFi project [Spark Era]. Check out our game at: https://www.firework.games/SparkEra", "image": "data:image/svg+xml;base64,',
                        Util.b64encode(bytes(output)),
                        '"}'
                    )
                )
            )
        );
        output = string(
            abi.encodePacked("data:application/json;base64,", json)
        );

        return output;
    }

    function getCampText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getCampValueByItemData(tokenData);
        string memory color = getCampColor(value);
        string memory vs = getCampValueString(value);
        return getText(Util.concat2("Camp: ", vs), 10, 20, color);
    }

    function getOccupationText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getOccupationValueByItemData(tokenData);
        string memory color = getOccupationColor(value);
        string memory vs = getOccupationValueString(value);
        return getText(Util.concat2("Occupation: ", vs), 10, 40, color);
    }

    function getLevelText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getLevelValueByItemData(tokenData);
        string memory color = getLevelColor(value);
        return
            getText(
                Util.concat2("Level: ", Util.toString(value)),
                10,
                60,
                color
            );
    }

    //SpecialInformation
    function getSpecialText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getSpecialValueByItemData(tokenData);
        string memory color = value == 0 ? GRAY : getSpecialColor(value);
        string memory vs = value == 0 ? "None" : getSpecialValueString(value);
        return
            getText(Util.concat2("Special Information: ", vs), 10, 80, color);
    }

    //Model
    function getModelText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getModelValueByItemData(tokenData);
        string memory color = getModelColor(value);
        string memory vs = getModelValueString(value);
        return getText(Util.concat2("Model: ", vs), 10, 100, color);
    }

    //Star Ship Health
    function getHealText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getHealthValueByItemData(tokenData);
        string memory color = getHealthColor(value);
        return
            getText(
                Util.concat2("Star Ship Health: ", Util.toString(value)),
                10,
                120,
                color
            );
    }

    function getPowerText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getPowerValueByItemData(tokenData);
        string memory color = getPowerColor(value);
        return
            getText(
                Util.concat2("Star Ship Power: ", Util.toString(value)),
                10,
                140,
                color
            );
    }

    function getArmorText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getArmorValueByItemData(tokenData);
        string memory color = getArmorColor(value);
        return
            getText(
                Util.concat2("Armor Value: ", Util.toString(value)),
                10,
                160,
                color
            );
    }

    function getSpecialWeaponText(uint256 tokenData)
        public
        pure
        returns (string memory)
    {
        uint8 value = getSpecialWeaponsValueByItemData(tokenData);
        string memory color = value == 0 ? GRAY : getSpecialWeaponsColor(value);
        string memory vs = value == 0
            ? "None"
            : getSpecialWeaponsValueString(value);
        return getText(Util.concat2("Weapon: ", vs), 10, 180, color);
    }

    //SVG Text generation
    function getText(
        string memory text,
        uint256 x,
        uint256 y,
        string memory color
    ) internal pure returns (string memory) {
        string memory output;
        string[7] memory header;
        header[0] = '<text class="base" x="';
        header[1] = Util.toString(x);
        header[2] = '" y="';
        header[3] = Util.toString(y);
        header[4] = '" fill="';
        header[5] = color;
        header[6] = '">';
        string memory tail = "</text>";
        output = string(
            abi.encodePacked(
                header[0],
                header[1],
                header[2],
                header[3],
                header[4],
                header[5],
                header[6],
                text,
                tail
            )
        );
        return output;
    }

     

    //GENERATED CODE

    //start of getcolor
    function getCampColor(uint16 value) public pure returns (string memory) {
        if (value == 1) {
            return GRAY;
        } else if (value == 2) {
            return GRAY;
        } else if (value == 3) {
            return GRAY;
        } else if (value == 4) {
            return GRAY;
        }
        revert("Illegal Value for Camp");
    }

    //end of getcolor

    //start of getValueString
    function getCampValueString(uint16 value)
        public
        pure
        returns (string memory)
    {
        if (value == 1) {
            return "Confedederation of Earth";
        } else if (value == 2) {
            return "Omota Empire";
        } else if (value == 3) {
            return "Eternal Guardian";
        } else if (value == 4) {
            return "Osmen Empire";
        }
        revert("Illegal Value for Camp");
    }

    //end of getValueString

    //begin of getPartByItemData
    function getCampValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 8) & 0xFF;
    }

   

 

    //start of getcolor
    function getOccupationColor(uint16 value)
        public
        pure
        returns (string memory)
    {
        if (value == 1) {
            return ORANGE;
        } else if (value == 2) {
            return ORANGE;
        } else if (value == 3) {
            return ORANGE;
        } else if (value == 4) {
            return PURPLE;
        } else if (value == 5) {
            return PURPLE;
        } else if (value == 6) {
            return BLUE;
        } else if (value == 7) {
            return BLUE;
        } else if (value == 8) {
            return GRAY;
        } else if (value == 9) {
            return GRAY;
        }
        revert("Illegal Value for Occupation");
    }

    //end of getcolor

    //start of getValueString
    function getOccupationValueString(uint16 value)
        public
        pure
        returns (string memory)
    {
        if (value == 1) {
            return "Black market trader";
        } else if (value == 2) {
            return "Preacher";
        } else if (value == 3) {
            return "Scholars";
        } else if (value == 4) {
            return "Diplomat";
        } else if (value == 5) {
            return "Space Marines";
        } else if (value == 6) {
            return "Trade Businessman";
        } else if (value == 7) {
            return "The Soldier";
        } else if (value == 8) {
            return "Pauper";
        } else if (value == 9) {
            return "Vagrant";
        }
        revert("Illegal Value for Occupation");
    }

    //end of getValueString

    //begin of getPartByItemData
    function getOccupationValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 16) & 0xFF;
    }

    //end of getPartByItemData

 

    //start of getcolor
    function getLevelColor(uint16 value) public pure returns (string memory) {
        if (value >= 90 && value < 100) {
            return ORANGE;
        } else if (value >= 60 && value < 89) {
            return PURPLE;
        } else if (value >= 40 && value < 59) {
            return BLUE;
        } else if (value >= 0 && value < 39) {
            return GRAY;
        }
        revert("Illegal Value for Level");
    }

    //end of getcolor

    //begin of getPartByItemData
    function getLevelValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 24) & 0xFF;
    }

    //end of getPartByItemData

   
 
    //start of getcolor
    function getSpecialColor(uint16 value) public pure returns (string memory) {
        return ORANGE;
    }

    //end of getcolor

    //start of getValueString
    function getSpecialValueString(uint16 value)
        public
        pure
        returns (string memory)
    {
        if (value == 1) {
            return "Psionicist";
        }
        if (value == 2) {
            return "Soul Eater";
        }
        if (value == 3) {
            return "Fearless Vanguard";
        }
        if (value == 4) {
            return "The Prophet";
        }
        if (value == 5) {
            return "Decider";
        }
        if (value == 6) {
            return "Thinker";
        }
        if (value == 7) {
            return "Preacher";
        }
        if (value == 8) {
            return "Old Ruler";
        }
        if (value == 9) {
            return "Fanatical Dictator";
        }
        revert("Illegal Value for Special");
    }

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
 

    //start of getcolor
    function getModelColor(uint16 value) public pure returns (string memory) {
        if (value == 1) {
            return ORANGE;
        } else if (value == 2) {
            return ORANGE;
        } else if (value == 3) {
            return ORANGE;
        } else if (value == 4) {
            return ORANGE;
        } else if (value == 5) {
            return ORANGE;
        } else if (value == 6) {
            return PURPLE;
        } else if (value == 7) {
            return PURPLE;
        } else if (value == 8) {
            return PURPLE;
        } else if (value == 9) {
            return PURPLE;
        } else if (value == 10) {
            return PURPLE;
        } else if (value == 11) {
            return PURPLE;
        } else if (value == 12) {
            return PURPLE;
        } else if (value == 13) {
            return BLUE;
        } else if (value == 14) {
            return BLUE;
        } else if (value == 15) {
            return BLUE;
        } else if (value == 16) {
            return BLUE;
        } else if (value == 17) {
            return BLUE;
        } else if (value == 18) {
            return BLUE;
        } else if (value == 19) {
            return BLUE;
        } else if (value == 20) {
            return GRAY;
        } else if (value == 21) {
            return GRAY;
        } else if (value == 22) {
            return GRAY;
        } else if (value == 23) {
            return GRAY;
        } else if (value == 24) {
            return GRAY;
        } else if (value == 25) {
            return GRAY;
        }
        revert("Illegal Value for Model");
    }

    //end of getcolor

    //start of getValueString
    function getModelValueString(uint16 value)
        public
        pure
        returns (string memory)
    {
        if (value == 1) {
            return "Stars";
        } else if (value == 2) {
            return "Eternity";
        } else if (value == 3) {
            return "Revelator";
        } else if (value == 4) {
            return "Brave Heart";
        } else if (value == 5) {
            return "Spark";
        } else if (value == 6) {
            return "Glorious";
        } else if (value == 7) {
            return "Peace";
        } else if (value == 8) {
            return "Peak";
        } else if (value == 9) {
            return "Nightmare";
        } else if (value == 10) {
            return "Vagrant";
        } else if (value == 11) {
            return "Destiny";
        } else if (value == 12) {
            return "Titan";
        } else if (value == 13) {
            return "Pearl";
        } else if (value == 14) {
            return "The Fearless";
        } else if (value == 15) {
            return "The Earthshaker";
        } else if (value == 16) {
            return "Slayer";
        } else if (value == 17) {
            return "The Dark Night";
        } else if (value == 18) {
            return "The Dawn";
        } else if (value == 19) {
            return "Galactica";
        } else if (value == 20) {
            return "Type I Star Ship";
        } else if (value == 21) {
            return "Type II Star Ship";
        } else if (value == 22) {
            return "Type III Star Ship";
        } else if (value == 23) {
            return "Type IV Star Ship";
        } else if (value == 24) {
            return "Type V Star Ship";
        } else if (value == 25) {
            return "Sunset Star Ship";
        }
        revert("Illegal Value for Model");
    }

    //end of getValueString

    //begin of getPartByItemData
    function getModelValueByItemData(uint256 itemData)
        public
        pure
        returns (uint8)
    {
        return uint8(itemData >> 40) & 0xFF;
    }

    //end of getPartByItemData

 

    //start of getcolor
    function getHealthColor(uint16 value) public pure returns (string memory) {
        if (value >= 90 && value < 100) {
            return ORANGE;
        } else if (value >= 60 && value < 89) {
            return PURPLE;
        } else if (value >= 40 && value < 59) {
            return BLUE;
        } else if (value >= 0 && value < 39) {
            return GRAY;
        }
        revert("Illegal Value for Health");
    }

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

 

    //start of getcolor
    function getPowerColor(uint16 value) public pure returns (string memory) {
        if (value >= 90 && value < 100) {
            return ORANGE;
        } else if (value >= 60 && value < 89) {
            return PURPLE;
        } else if (value >= 40 && value < 59) {
            return BLUE;
        } else if (value >= 0 && value < 39) {
            return GRAY;
        }
        revert("Illegal Value for Power");
    }

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

 

    //start of getcolor
    function getArmorColor(uint16 value) public pure returns (string memory) {
        if (value >= 90 && value < 100) {
            return ORANGE;
        } else if (value >= 60 && value < 89) {
            return PURPLE;
        } else if (value >= 40 && value < 59) {
            return BLUE;
        } else if (value >= 0 && value < 39) {
            return GRAY;
        }
        revert("Illegal Value for Armor");
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

  

    //start of getcolor
    function getSpecialWeaponsColor(uint16 value)
        public
        pure
        returns (string memory)
    {
        return ORANGE;
    }

    //end of getcolor

    //start of getValueString
    function getSpecialWeaponsValueString(uint16 value)
        public
        pure
        returns (string memory)
    {
        if (value == 1) {
            return "Special Operations Computers";
        }
        if (value == 2) {
            return "Shield Capacitance";
        }
        if (value == 3) {
            return "Antimatter Energy";
        }
        if (value == 4) {
            return "Dark Matter Deflection Shield";
        }
        if (value == 5) {
            return "Gamma Laser Cannon";
        }
        if (value == 6) {
            return "Phase Cracker";
        }
        if (value == 7) {
            return "High-dimensional Weapons";
        }
        if (value == 8) {
            return "Fission Blow";
        }
        if (value == 9) {
            return "Psionic Transition Engine";
        }
        revert("Illegal Value for SpecialWeapons");
    }

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
 
}
