# loot
loot for sparkera<br/>
## PSE合约与PSEBlackList合约的初始化
1. 先部署PSE合约
2. 部署PSEBlackSmith合约
3. 调用PSE合约的mutateForgerAddr(address , bool )方法,传入PSEBlackSmith合约的地址，第二个参数true（表示允许blacksmith合约去调用forge函数）
4. LINKRouter合约给PSEBlackSmith合约开调用权限

## user manual for LinkRouter<br/>
contracts should pay the LINK,when call link VRF ,so we implement a LINK router that alawys call VRF and other smart contracts get the random data via the LinkRouter.
so ,there are the steps:<br/>
1.set a ILinkRouter when smart contracts constructor() on deploy<br/>
2.call router.sendRequest(reqSequence) request generate a random data, and should maintance the sequence for each request<br/>
3.get the result by call router.getRandom(reqSequence) after Request call about 10 blocks about 10-15s<br/>

and to get call success ,should addwhitelist in LinkRouter polygon mainnet(0xe28647ac592E5CC81Eca240DF3923B19b6196182).
