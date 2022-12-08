// SPDX-License-Identifier: MIT
pragma solidity ^0.8.7;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import "@openzeppelin/contracts/token/ERC721/IERC721Receiver.sol";
import "@openzeppelin/contracts/token/ERC721/extensions/IERC721Enumerable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "./LSE.sol";
import "./LSEExplain.sol";
import "./ILinkRouter.sol";
import "./PSE.sol";

contract PSEBlackSmith is Ownable, IERC721Receiver {
    IERC20 private usdt;
    LSE private lse;
    LSEExplain private lseExplain;
    PSE private pse;
    ILinkRouter private linkRouter;
    uint256 lastUncompleteIndex=0;

    using Counters for Counters.Counter;
    Counters.Counter private requestIdCounter;
    uint256 public  price_in_cent = 10**4;
    uint256 private forgePrice = 999;
    bool private isGoldDiggerMint = false;

    struct Ctx {
        uint256 reqId;
        uint256 rnd;
        address to;
        bool finished;
    }
    /** all request */
    Ctx[] allRequest;
    /* reqId  => index of allRequest */
    mapping(uint256 => uint256) asyncCtxMapping;

    mapping(uint256 => bool) blackList;

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

    constructor(
        address lesAddress,
        address lesExplainAddress,
        address pseAddr,
        address usdtAddr,
        address theLinkRouter
    ) {
        lse = LSE(lesAddress);
        lseExplain = LSEExplain(lesExplainAddress);
        pse = PSE(pseAddr);
        usdt = IERC20(usdtAddr);
        linkRouter = ILinkRouter(theLinkRouter);
    }

    function getERC20TokenAddr() public view returns (address) {
        return address(usdt);
    }

    function transferOutERC20Token(address to) public onlyOwner {
        usdt.transfer(to, usdt.balanceOf(address(this)));
    }

    function changePayment(address erc20, uint256 price,uint256 pIncent) public onlyOwner {
        usdt = IERC20(erc20);
        forgePrice = price;
        price_in_cent = pIncent;
    }

    /**
    need for nft receive
    */
    function onERC721Received(
        address operator,
        address from,
        uint256 tokenId,
        bytes calldata data
    ) external override returns (bytes4) {
        //lse dont impl burn , so we can only transfer to 0xdead
        lse.safeTransferFrom(address(this), address(0xdead), tokenId);
        return IERC721Receiver.onERC721Received.selector;
    }

    /**
        Forge by 3 LSE 
    */
    function forgeBy3(uint256[] calldata nftIdList) public {
        //check black list
        require(nftIdList.length == 3, "nftIdList.length Should be 3");
        bool inBlackList = checkInBlackList(nftIdList);
        require(!inBlackList, "One of your token is in black list");
        //transfer erc20
        usdt.transferFrom(
            msg.sender,
            address(this),
            forgePrice * price_in_cent
        );
        //transfer nft
        transferTokenToThisContract(nftIdList);
        //make ctx and ctxId
        Ctx memory asyncCtx = constructAsyncContext(msg.sender);
        allRequest.push(asyncCtx);
        uint256 elementIdx = allRequest.length - 1;
        asyncCtxMapping[asyncCtx.reqId] = elementIdx;
        //call random
        bool reqOk = linkRouter.sendRequest(asyncCtx.reqId);
        require(reqOk, "Link Random reqeust failed");
    }

    function forgeByReqId(
        uint256[] memory reqIdList,
        uint256[] memory randomList
    ) public onlyOwner {
        require(
            reqIdList.length == randomList.length,
            "reqIdList.length != randomList.length"
        );
        for (uint256 i = 0; i < reqIdList.length; i++) {
            uint256 reqId = reqIdList[i];
            uint256 rnd = randomList[i] % 100_000;

            Ctx memory ctx = allRequest[asyncCtxMapping[reqId]];
            if (ctx.finished) {
                continue;
            }

            address to = ctx.to;
            uint256 itemData = getItemDataByRnd(rnd);
            if(itemData == 7 && isGoldDiggerMint){
                itemData = 6;
            }
            pse.forge(to, itemData);
            if(itemData == 7){
                isGoldDiggerMint = true;
            }
            ctx.finished = true;
            ctx.rnd = randomList[i];
            allRequest[asyncCtxMapping[reqId]] = ctx;

        }
        
    }

    function getInFlightReqIdList() public view returns (uint256[] memory) {
        uint256 unfinishedCount = 0;

        for (uint256 i = 0; i < allRequest.length; i++) {
            if (!allRequest[i].finished) {
                unfinishedCount++;
            }
        }
        uint256[] memory reqIdList = new uint256[](unfinishedCount);
        uint256 uidx = 0;

        for (uint256 i = 0; i < allRequest.length; i++) {
            if (!allRequest[i].finished) {
                reqIdList[uidx] = allRequest[i].reqId;
                uidx++;
            }
        }
        return reqIdList;
    }

    function getRandomAndProcess(uint8 maxCnt) public {
        uint256 unfinishedCount = 0;
        uint256 newUnfiniedIndex=0;

        for (uint256 i = lastUncompleteIndex; i < allRequest.length; i++) {
            if (!allRequest[i].finished) {
                unfinishedCount++;
                if(newUnfiniedIndex==0){
                    newUnfiniedIndex=i;
                }
            }
        }
        lastUncompleteIndex=newUnfiniedIndex;

        uint256[] memory reqIdList = new uint256[](unfinishedCount);
        uint256[] memory resultList = new uint256[](unfinishedCount);
        uint256 uidx = 0;
        uint256 result = 0;

        for (uint256 i = lastUncompleteIndex; i < allRequest.length; i++) {
            if (!allRequest[i].finished) {
                result = linkRouter.getRandom(allRequest[i].reqId);
                if (result != 0) {
                    reqIdList[uidx] = allRequest[i].reqId;
                    resultList[uidx] = result;
                    uidx++;
                    if (uidx > maxCnt) break;
                }
            }
        }
        return forgeByReqId(reqIdList, resultList);

    }

    function getItemDataByRnd(uint256 rnd) public pure returns (uint256) {
        if (rnd >= 0 && rnd < 90_000) {
            return 1;//TypeI
        } else if (rnd >= 90_000 && rnd < 99_675) {
            return 2;//TypeII
        } else if (rnd >= 99_675 && rnd < 99_834) {
            return 3;//Star
        } else if (rnd >= 99_834 && rnd < 99_874) {
            return 4;//Galaxy
        } else if (rnd >= 99_874 && rnd < 99_899) {
            return 5;//Eternal Envoy
        } else if (rnd >= 99_899 && rnd < 99_999) {
            return 6;//Black Box
        } else if (rnd >= 99_999 && rnd < 100_000) {
            return 7;//Gold digger
        }
        revert("invalid random range!");
    }

    function constructAsyncContext(address toAddr)
        internal
        returns (Ctx memory)
    {
        uint256 asyncReqId = requestIdCounter.current();
        requestIdCounter.increment();
        Ctx memory asyncCtx = Ctx({
            to: toAddr,
            reqId: asyncReqId,
            rnd: 0,
            finished: false
        });
        return asyncCtx;
    }

    function forgeBy10() public returns (uint256) {
        // find first 10 nft
         require(
            lse.balanceOf(msg.sender) >= 1,
            "You need at least 1 LSE tokens"
        );
        
        uint256[] memory sorted = getSortedTokenId();

        uint256 len = sorted.length>10?10:sorted.length;
        

        uint256[] memory tokenIdList = new uint256[](len);
        for (uint256 i = 0; i < len; i++) {
            tokenIdList[i] = sorted[i];
        }
        // check black list
        bool inBlackList = checkInBlackList(tokenIdList);
        require(!inBlackList, "One of your token is in the black list");
        // calc score
        uint256 score = calcScoreByLSEId(tokenIdList);
        // transer nft to this contract
        transferTokenToThisContract(tokenIdList);
        //mint optinal pioneer and new nft
        bool isPioneer = mayForgePioneer(tokenIdList);
        if (isPioneer) {
            mintPioneer();
        }
        return mintWithScore(score);
    }
    function ownerMintPioneer(address[] memory addrList) public onlyOwner {
        for(uint256 i = 0;i < addrList.length;i++){
            pse.forge(addrList[i], 8);
        }

    }
    function getIsGoldDiggerMint() public view returns(bool){
        return isGoldDiggerMint;
    }
    function mayForgePioneer(uint256[] memory tokenIdList)
        public
        returns (bool)
    {
        for (uint256 i = 0; i < tokenIdList.length; i++) {
            uint256 data = lse.getTokenData(tokenIdList[i]);
            if (meetPioneerRequirement(data)) {
                return true;
            }
        }
        return false;
    }

    function meetPioneerRequirement(uint256 tokenData) public returns (bool) {
        uint256 orangeCount = 0;
        uint256 withoutSpecialOrange = 0;
        bool hasSpecialValueOrWeapon = false;

        {
            string memory armorColor = lseExplain.getArmorColor(
                lseExplain.getArmorValueByItemData(tokenData)
            );
            if (strEquals(armorColor, lseExplain.ORANGE())) {
                orangeCount++;
                withoutSpecialOrange++;
            }

            string memory campColor = lseExplain.getCampColor(
                lseExplain.getCampValueByItemData(tokenData)
            );
            if (strEquals(campColor, lseExplain.ORANGE())) {
                orangeCount++;
                withoutSpecialOrange++;
            }

            string memory healthColor = lseExplain.getHealthColor(
                lseExplain.getHealthValueByItemData(tokenData)
            );
            if (strEquals(healthColor, lseExplain.ORANGE())) {
                orangeCount++;
                withoutSpecialOrange++;
            }
        }
        string memory levelColor = lseExplain.getLevelColor(
            lseExplain.getLevelValueByItemData(tokenData)
        );
        if (strEquals(levelColor, lseExplain.ORANGE())) {
            orangeCount++;
            withoutSpecialOrange++;
        }

        string memory modelColor = lseExplain.getModelColor(
            lseExplain.getModelValueByItemData(tokenData)
        );
        if (strEquals(modelColor, lseExplain.ORANGE())) {
            orangeCount++;
            withoutSpecialOrange++;
        }

        string memory occupationColor = lseExplain.getOccupationColor(
            lseExplain.getOccupationValueByItemData(tokenData)
        );
        if (strEquals(occupationColor, lseExplain.ORANGE())) {
            orangeCount++;
            withoutSpecialOrange++;
        }

        string memory powerColor = lseExplain.getPowerColor(
            lseExplain.getPowerValueByItemData(tokenData)
        );
        if (strEquals(powerColor, lseExplain.ORANGE())) {
            orangeCount++;
            withoutSpecialOrange++;
        }     
       
        if(lseExplain.getSpecialValueByItemData(tokenData)!=0){
            orangeCount++;
            hasSpecialValueOrWeapon = true;
            withoutSpecialOrange++;
        }
      
        if(lseExplain.getSpecialWeaponsValueByItemData(tokenData)!=0){
            orangeCount++;
            hasSpecialValueOrWeapon = true;
            withoutSpecialOrange++;
        }
        return (orangeCount == 3 ||
            (hasSpecialValueOrWeapon && withoutSpecialOrange >= 2));
    }

    function mintPioneer() internal {
        pse.forge(msg.sender, 8);//blazers
    }

    //---------FORGE ENDS

    function mintWithScore(uint256 score) private returns (uint256) {
        uint256 data = 0;

        if (score >= 25 && score <= 209) {
            data = 1;//TypeI
        } else if (score >= 210 && score <= 284) {
            data = 2;//TypeII
        } else if (score >= 285 && score <= 374) {
            data = 3;//Star
        } else if (score >= 375 && score <= 409) {
            data = 4;//Galaxy
        } else if (score >= 410) {
            data = 5;//Eternal Envoy
        }

        require(data != 0, "your score is out of range");

        return pse.forge(msg.sender, data);
    }

    function transferTokenToThisContract(uint256[] memory tokenIdList)
        internal
    {
        for (uint256 i = 0; i < tokenIdList.length; i++) {
            lse.safeTransferFrom(msg.sender, address(this), tokenIdList[i]);
        }
    }

    function checkInBlackList(uint256[] memory tokenIdList)
        public
        view
        returns (bool)
    {
        for (uint256 i = 0; i < tokenIdList.length; i++) {
            if (blackList[tokenIdList[i]]) {
                return true;
            }
        }
        return false;
    }

    function getSortedTokenId() internal view returns (uint256[] memory) {
        uint256 balance = lse.balanceOf(msg.sender);
        if (balance > 100) {
            balance = 100;
        }
        uint256[] memory tokenIdList = new uint256[](balance);

        for (uint256 i = 0; i < balance; i++) {
            tokenIdList[i] = lse.tokenOfOwnerByIndex(msg.sender, i);
        }

        sort(tokenIdList);

        return tokenIdList;
    }

    function calcScoreByLSEId(uint256[] memory lseIdList)
        public
        view
        returns (uint256)
    {
        uint256 sum = 0;
        for (uint256 i = 0; i < lseIdList.length; i++) {
            uint256 tokenData = lse.getTokenData(lseIdList[i]);
            uint256 score = calcScoreByTokenData(tokenData);
            sum += score;
        }
        return sum;
    }

    function calcScoreByTokenData(uint256 tokenData)
        public
        view
        returns (uint256)
    {
        uint256 score = 0;

        string memory armorColor = lseExplain.getArmorColor(
            lseExplain.getArmorValueByItemData(tokenData)
        );
        score += getScoreByColor(armorColor);

        string memory campColor = lseExplain.getCampColor(
            lseExplain.getCampValueByItemData(tokenData)
        );
        score += getScoreByColor(campColor);

        string memory healthColor = lseExplain.getHealthColor(
            lseExplain.getHealthValueByItemData(tokenData)
        );
        score += getScoreByColor(healthColor);

        string memory levelColor = lseExplain.getLevelColor(
            lseExplain.getLevelValueByItemData(tokenData)
        );
        score += getScoreByColor(levelColor);

        //model value ==5  --> spark
        uint8 modelValue = lseExplain.getModelValueByItemData(tokenData);
        if (modelValue == 5) {
            score += 50;
        } else {
            string memory modelColor = lseExplain.getModelColor(modelValue);
            score += getScoreByColor(modelColor);
        }

        string memory occupationColor = lseExplain.getOccupationColor(
            lseExplain.getOccupationValueByItemData(tokenData)
        );
        score += getScoreByColor(occupationColor);

        string memory powerColor = lseExplain.getPowerColor(
            lseExplain.getPowerValueByItemData(tokenData)
        );
        score += getScoreByColor(powerColor);

        
        uint256 sv= lseExplain.getSpecialValueByItemData(tokenData);       
        if (sv!=0) {
            score += 25;
        }else{
            score += 1;
        }
        
        uint256 sw= lseExplain.getSpecialWeaponsValueByItemData(tokenData);      
        if (sw!=0) {
            score += 15;
        }else{
             score += 1;
        }

        return score;
    }

    function getScoreByColor(string memory color)
        public
        view
        returns (uint256)
    {
        if (strEquals(color, lseExplain.GRAY())) {
            return 1;
        }
        if (strEquals(color, lseExplain.BLUE())) {
            return 3;
        }
        if (strEquals(color, lseExplain.PURPLE())) {
            return 5;
        }
        if (strEquals(color, lseExplain.ORANGE())) {
            return 10;
        }

        return 0;
    }

    function strEquals(string memory s1, string memory s2)
        internal
        pure
        returns (bool)
    {
        return
            keccak256(abi.encodePacked(s1)) == keccak256(abi.encodePacked(s2));
    }

    //===============QUICK SORT ==============================
    function sort(uint256[] memory data)
        public
        pure
        returns (uint256[] memory)
    {
        quickSort(data, int256(0), int256(data.length - 1));
        return data;
    }

    function quickSort(
        uint256[] memory arr,
        int256 left,
        int256 right
    ) internal pure {
        int256 i = left;
        int256 j = right;
        if (i == j) return;
        uint256 pivot = arr[uint256(left + (right - left) / 2)];
        while (i <= j) {
            while (arr[uint256(i)] < pivot) i++;
            while (pivot < arr[uint256(j)]) j--;
            if (i <= j) {
                (arr[uint256(i)], arr[uint256(j)]) = (
                    arr[uint256(j)],
                    arr[uint256(i)]
                );
                i++;
                j--;
            }
        }
        if (left < j) quickSort(arr, left, j);
        if (i < right) quickSort(arr, i, right);
    }
}
