## .env.production 开发/生产环境配置

```
VUE_APP_API_URL=服务端URL
```

```
VUE_APP_LEVEL_MAX=等级上限
```

```
VUE_APP_ERC20=ERC20地址
```

```
VUE_APP_ERC721=ERC721地址
```

```
VUE_APP_TARGET=交易合约地址
```

```
VUE_APP_TOKEN_DECIMALS = 代币小数位 填写数字即可
```

```
VUE_APP_ISTARSHIP = 市场支持飞船资产交易
```

```
VUE_APP_CHAIN_ID=0x61  链接的网络
```

## 合约 ABI 文件位置

```
/src/config/contract/erc20.json ERC20代币
```

```
/src/config/contract/erc721.json ERC721代币
```

### Compiles and hot-reloads for development

```
npm run serve
```

### Compiles and minifies for production

```
npm run build
```

### 组件文档

| 组件                               | 说明                     |
| ---------------------------------- | ------------------------ |
| /components/Card.vue               | 卡牌组件                 |
| /components/CardAttributeColor.vue | 卡牌数值颜色组件         |
| /components/CardFilter.vue         | 卡牌页面查询左侧过滤组件 |
| /components/CardFilteBetween.vue   | Min-Max 过滤组件         |
| /components/CardFilteResult.vue    | 过滤条件显示组件         |
| /components/QuickSellDialog.vue    | 快速回收组件             |
| /components/Wallet.vue             | 钱包链接组件             |

### API 接口说明

| 接口地址                                       | 说明                     |
| ---------------------------------------------- | ------------------------ |
| /api/v1/informationCenter/buyToken             | 购买卡                   |
| /api/v1/informationCenter/cancelSell           | 取消售卖                 |
| /api/v1/informationCenter/sellToken            | 售卖卡牌                 |
| /api/v1/informationCenter/favorite/add         | 收藏                     |
| /api/v1/informationCenter/favorite/remove      | 取消收藏                 |
| /api/v1/informationCenter/myFavorite           | 获取我的收藏列表         |
| /api/v1/informationCenter/myStorage            | 获取我的卡牌列表         |
| /api/v1/informationCenter/selling              | 获取我的售卖中的卡牌列表 |
| /api/v1/informationCenter/tokenDetailByTokenId | 获取 token 详情          |
| /api/v1/marketplace/tradingMarket              | 获取交易市场的售卖卡牌   |
| /api/v1/marketplace/token/views/add            | 预览卡牌埋点             |
| /api/v1/informationCenter/quickSale            | 快速售卖                 |
| /api/v1/statistics/activity                    | activity 排行榜          |
| /api/v1/statistics/topNft                      | TOP 排行榜               |
