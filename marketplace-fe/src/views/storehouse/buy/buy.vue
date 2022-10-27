<template>
  <div
    class="selldetails"
    v-loading="loadingDetail"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <div class="pic-heard">
      <!-- 判断图片显示 -->

      <div class="pic-heard-left" :class="{ isShip: cardType === 4 }">
        <!-- {{ console.log($route.query.type) }} -->
        <video :src="image" v-if="imageIsMp4" autoplay muted loop></video>
        <img :src="image" v-else :style="nftTypeIs('lootbox') ? 'width:75%' : ''"  />
        <!-- <img v-if="cardType !== 4" :src="image" alt /> -->

        <!-- <div class="pic-heard-ship" v-else>
          <iframe
            :src="image"
            frameborder="0"
            title="Neptune"
            allowfullscreen
            mozallowfullscreen="true"
            webkitallowfullscreen="true"
            allow="autoplay; fullscreen; xr-spatial-tracking"
            xr-spatial-tracking
            execution-while-out-of-viewport
            execution-while-not-rendered
            web-share
            class="iframe"
          ></iframe>
        </div>-->
      </div>

      <div class="pic-heard-right">
        <div class="pic-heard-right-heard">
          <h2>{{ name || "" }}</h2>
          <div class="level" v-if="nftTypeIs(['card', 'spaceship'])">{{ cardLevel }}</div>
          <div
            :class="`rarity ${rarityName}`"
            v-if="nftTypeIs(['card', 'equipment', 'spaceship'])"
          >{{ rarityName }}</div>
          
            <div v-if="commentsCard.heroType === 'Psionic'" class="psionic-tag">
              Psionic Hero
            </div>
            <div class="physical-tag" v-if="commentsCard.heroType === 'Physical'">Physical Hero</div>
          <div
            v-if="isOwner === false"
            class="pic-heard-right-heard-img"
            v-loading="loadingCollection"
            element-loading-background="rgba(0, 0, 0, 0.5)"
          >
            <img
              @click="onCancelCollection"
              v-if="isCollection"
              src="../../../assets/images/love4k.png"
            />
            <img @click="onAddCollection" v-else src="../../../assets/images/not_love4k.png" />
          </div>
          <div class="pic-heard-right-heard-img">
            <img
              v-clipboard:copy="url"
              v-clipboard:success="onCopy"
              src="../../../assets/images/sellicon24k.png"
            />
          </div>
        </div>
        <!-- 预览、收藏 -->
        <div class="pic-heard-right-lable">
          <div class="pic-heard-right-lable-left">
            <div class="pic-heard-right-lable-left-a">
              <div class="pic-heard-right-lable-left-a-a eye">
                <img src="@/assets/images/sellicon3.png" />
              </div>
              <p class="pic-heard-right-lable-left-a-b">{{ view }} views</p>
            </div>
          </div>

          <div class="pic-heard-right-lable-left">
            <div class="pic-heard-right-lable-left-a">
              <div class="pic-heard-right-lable-left-a-a heart" style="width: 17px; height: 16px">
                <img src="@/assets/images/sellicon4.png" />
              </div>
              <p class="pic-heard-right-lable-left-a-b">{{ favorites }} favorites</p>
            </div>
          </div>
        </div>
        <!-- 飞船模块 -->
        <!-- <div v-if="cardType === constant.cardType.spaceship">
          <SpaceShip :card="commentsCard" />
        </div>-->
        <SpaceShip ref="$SpaceShip" :card.sync="commentsCard" />
        <!-- 简介 -->
        <!-- <div class="pic-heard-right-introduce">
          {{ description }}
        </div>-->

        <!-- 卡片模块 -->
        <!-- 旧卡片属性 -->
        <!-- <div
          v-if="cardType === constant.cardType.card"
          class="pic-heard-right-card"
        >
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img src="../../../assets/images/card_attr_camp.png" alt="" />
            </div>
            <p class="pic-heard-right-card-item-title">{{ camp }}</p>
          </div>
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img src="../../../assets/images/card_attr_material.png" alt="" />
            </div>
            <p class="pic-heard-right-card-item-title">{{ material }}</p>
          </div>
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img
                :src="
                  require(`../../../assets/images/lv${
                    level > 6 ? 6 : level
                  }.png`)
                "
                alt=""
              />
            </div>
            <p class="pic-heard-right-card-item-title">LV {{ level }}</p>
          </div>
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img src="../../../assets/images/card_attr_rarity.png" alt="" />
            </div>
            <p class="pic-heard-right-card-item-title">
              {{ constant.rarity[rarity] }}
            </p>
          </div>
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img
                src="../../../assets/images/card_attr_profession.png"
                alt=""
              />
            </div>
            <p class="pic-heard-right-card-item-title">
              {{ constant.profession[profession] }}
            </p>
          </div>
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img
                :src="
                  require(`../../../assets/images/card_attr_identity_${constant.identityImages[identity]}.png`)
                "
                alt=""
              />
            </div>
            <p class="pic-heard-right-card-item-title">
              {{ constant.identity[identity] }}
            </p>
          </div>
        </div>-->

        <!-- 旧pse属性 -->
        <!-- <div v-if="cardType === constant.cardType.pse" class="pic-heard-right-card">
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img src="../../../assets/images/pse_attr_type.png" alt />
            </div>
            <p class="pic-heard-right-card-item-title">{{ pseAttr.type }}</p>
          </div>
        </div>-->

        <!-- 旧egse属性 -->
        <div v-if="cardType === constant.cardType.egse" class="pic-heard-right-card">
          <div class="pic-heard-right-card-item">
            <div class="pic-heard-right-card-item-img">
              <img src="../../../assets/images/egse_attr_type.png" alt />
            </div>
            <p class="pic-heard-right-card-item-title">{{ egseAttr.type }}</p>
          </div>
        </div>

        <!-- 价格 -->
        <template v-if="isShowPrice">
          <div class="pic-heard-right-footer">
            <div v-if="nftTypeIs('material')" style="display: flex; align-items: center; margin-bottom: 20px">
              <div class="pic-heard-right-footer-content">
                <p v-if="tradeType === 'SELL'">Qty( Max: {{commentsCard.cnt}} )</p>
                <p v-else>Qty: {{commentsCard.cnt}}</p>
              </div>

              <div v-if="tradeType === 'SELL'" class="pic-heard-right-footer-item-sell">
                <div class="pic-heard-right-footer-item-sell-left">
                  <input type="text" v-model="sellCnt" placeholder="PRICE" class="pit" />
                </div>
              </div>
            </div>

            <div style="display: flex; align-items: center; margin-bottom: 20px">
              <div class="pic-heard-right-footer-content">
                <p>CURRENT PRICE</p>
                <div v-if="tradeType !== 'SELL'" class="pic-heard-right-footer-item-buy">
                  <p class="pic-heard-right-footer-item-buy-p">{{ price }}</p>
                  <div class="pic-heard-right-footer-item-buy-right-img">
                    <img src="@/assets/images/market/priceicon4k.png" alt />
                  </div>
                </div>
              </div>

              <div v-if="tradeType === 'SELL'" class="pic-heard-right-footer-item-sell">
                <div class="pic-heard-right-footer-item-sell-left">
                  <input type="text" v-model="sellPrice" placeholder="PRICE" class="pit" />
                </div>
                <div class="pic-heard-right-footer-item-sell-right">
                  <div class="pic-heard-right-footer-item-sell-right-img">
                    <img src="@/assets/images/market/priceicon4k.png" alt />
                  </div>
                  <div class="pic-heard-right-footer-item-sell-right-img2">
                    <img src="@/assets/images/sellicon15.png" alt />
                  </div>
                </div>
              </div>
            </div>

            <div
              class="pic-heard-right-footer-item2"
              :class="tradeType"
              v-loading="loading"
              element-loading-background="rgba(0, 0, 0, 0.8)"
            >
              <p @click="onAskForSs" v-if="tradeType === 'BUY'">BUY NOW</p>
              <p @click="onCancel" v-if="tradeType === 'CANCEL' && isOwner">CANCEL</p>
              <p @click="onSell" v-if="tradeType === 'SELL'">SELL</p>
            </div>
          </div>
          <!-- <div v-if="tradeType === 'SELL'" class="fee">
            <div>Service Fee: {{ 100 * fee }}%</div>
            <div></div>
          </div>-->
        </template>
      </div>
    </div>
    <!-- v3版本要求暂时不需要这个模块 -->
    <!-- <div v-if="constant.cardType.card === cardType" class="pic-content">
      <div class="pic-content-right">
        <div class="pic-content-right-img1">
          <img src="@/assets/images/sellicon11.png" alt="" />
        </div>
        <div id="main" class="ecahrtsss"></div>
      </div>
    </div>-->
    <div class="ss-dialog" v-show="ssDialogVisible">
      <div class="dialog-content">
        <div class="head">
          <img src="@/assets/images/warning-shadow.png" />
          <img src="@/assets/images/warning.png" />
        </div>
        <div class="body">
          <p>
            Please note that this spaceship will reach its maximum mining amount
            <span
              class="red"
            >{{ errorMsg }}</span>
            . If you continue mining, the spaceship will be destroyed permamentlyl. Are
            you sure to buy?
          </p>
        </div>
        <div class="foot">
          <div class="btn grey" @click="onAsk()">still continue</div>
          <div class="btn" @click="ssDialogVisible = false">close</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import Base64 from "js-base64";
import http from "../../../config/http";
// import * as echarts from "echarts";
import constant from "../../../common/constant";
import SpaceShip from "./SpaceShip";

export default {
  components: { SpaceShip },
  data() {
    return {
      loadingDetail: true,
      loadingCollection: false,
      loading: false,
      erc721: null,
      id: null,
      option: {
        legend: {
          bottom: 5,
          data: ["Guangzhou"],
          itemGap: 20,
          textStyle: {
            color: "#fff",
            fontSize: 14,
          },
          selectedMode: "single",
        },
        radar: {
          indicator: [],
          axisName: {
            color: "#ffffff",
            fontSize: 16,
          },
          splitLine: {
            lineStyle: {
              color: [
                "rgba(238, 197, 102, 0)",
                "rgba(238, 197, 102, 0)",
                "rgba(238, 197, 102, 0)",
                "rgba(238, 197, 102, 0)",
                "rgba(238, 197, 102, 0)",
                "rgba(238, 197, 102, 0)",
              ].reverse(),
            },
          },
          labelLine: {
            length: 30,
          },
          splitArea: {
            show: false,
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: "rgba(199, 248, 255, .15)",
              width: 2,
            },
          },
        },
        series: [
          {
            type: "radar",
            data: [],
            symbol: "circle",
            symbolSize: 10,
            lineStyle: {
              color: "rgba(129, 153, 255, .4)",
              opacity: 0.5,
              width: 5,
            },
            itemStyle: {
              width: 4,
              color: "rgba(199, 248, 255, .5)",
            },
            areaStyle: {
              opacity: 0.1,
            },
          },
        ],
      },
      commentsCard: {},
      // cardType: 0,
      constant: constant,
      view: 0,
      favorites: 0,
      egseAttr: {
        type: "",
      },
      pseAttr: {
        type: "",
      },
      url: window.location.href,
      isCollection: false,
      isOwner: false,
      description: "",
      price: 0.0,
      sellCnt: 1,
      level: 1,
      image: "",
      camp: "",
      identity: 0,
      material: "",
      profession: 0,
      rarity: 0,
      name: "",
      ownerAddress: "",
      sellPrice: 0,
      tradeType: "",
      isShowPrice: true,
      fee: 0,
      imgList: [
        {
          src: require("@/assets/images/card_attr_profession.png"),
          title: "PSIONICIST",
        },
        {
          src: require("@/assets/images/sell6.png"),
          title: "SCIENTIST",
        },
      ],
      ssDialogVisible: false,
      errorMsg: "",
    };
  },
  computed: {
    imageIsMp4(){
      return this.image.endsWith('.mp4')
    },
    cardType() {
      return this.$route.query.type ? this.$route.query.type - 0 : 0;
    },
    nftTypeIs() {
      return (type) => {
        if (typeof type === "string" || typeof type === "number") {
          return this.cardType === constant.cardType[type];
        } else if (Array.isArray(type)) {
          return type.some((t) => this.cardType === constant.cardType[t]);
        }
        // edge case
        return false;
      };
    },
    rarityName() {
      if (this.nftTypeIs(["spaceship", "equipment"])) {
        let str = this.commentsCard.rank || "";
        if (typeof str === "string" && str[0]) {
          str = str.toLowerCase().split("");
          str[0] = str[0].toUpperCase();
          str = str.join("");
        }
        return str;
      }
      return constant.rarity[this.rarity];
    },
    cardLevel() {
      const level = this.commentsCard.level || 0;
      return "Lv." + ("00" + level).slice(-2);
    },
  },
  created() {
    this.id = this.$route.query.id;
    // this.cardType = parseInt(this.$route.query.type);
    // console.log('this.cardType = parseInt(this.$route.query.type)');
    this.loadFee();
    this.load();
    this.addView();
    this.initErc721();
    this.$EventBus.$on("reload", this.load);
    this.$on("hook:beforeDestroy", () => {
      this.$EventBus.$off("reload", this.load);
    });


    // this.erc721.methods.getDataByNftId(this.$route.query.id).call({
    //   from: this.$store.state.address
    // }).then(data => {
    //   console.log('getDataByNftId', data);
    // })
  },
  methods: {
    drawLine(card) {
      // this.option.series[0].data.push([
      //   card.endurance,
      //   card.strength,
      //   card.agility,
      //   card.armor,
      //   card.crit,
      //   card.psionic,
      //   card.spirit,
      // ]);
      // this.option.radar.indicator = [
      //   { text: `ENDURANCE (${card.endurance})`, max: 100 },
      //   { text: `STRENGTH (${card.strength})`, max: 100 },
      //   { text: `AGILITY (${card.agility})`, max: 100 },
      //   { text: `ARMOR (${card.armor})`, max: 100 },
      //   { text: `CRIT (${card.crit})`, max: 100 },
      //   { text: `PSIONIC (${card.psionic})`, max: 100 },
      //   { text: `SPIRIT (${card.spirit})`, max: 100 },
      // ];
      // let myChart = echarts.init(document.getElementById('main'));
      // this.option && myChart.setOption(this.option);
    },
    load() {
      this.loadingDetail = true;
      const params = {
        tokenId: this.$route.query.id,
        nftType: this.cardType,
        address: this.$store.state.address,
      }
      if(this.nftTypeIs('material')){
        Object.assign(params,{
          ownerAddress: this.$store.state.address
          // tokenId: 0,
          // id: this.commentsCard.id,
        })
        if(this.$route.query.goodId){
          Object.assign(params,{
            goodId: this.$route.query.goodId - 0,
            ownerAddress:  this.$route.query.owner
          })
        }
      }
      http
        .get("/api/v1/informationCenter/tokenDetailByTokenId", {
          params,
        })
        .then((res) => {
          let card = res.data.data;
          this.image = res.data.data.image;
          // if (this.cardType === 4) {
          //   const iframeHTML = require('js-base64').Base64.decode(
          //     res.data.data.model
          //   );
          //   const Regx = /(?<=(src="))[^"]*?(?=")/gims;
          //   const iframeUrlArray = iframeHTML.match(Regx);

          //   this.image = iframeUrlArray[0];
          // } else {
          //   this.image = res.data.data.image;
          // }

          this.view = res.data.data.views;
          this.favorites = res.data.data.favorites;
          this.camp = res.data.data.attrCamp;
          this.price = res.data.data.price;
          this.level = res.data.data.level;
          this.rarity = res.data.data.rarity;
          this.identity = res.data.data.identity;
          this.profession = res.data.data.occupation;
          this.material = res.data.data.attrMaterial;

          switch (this.cardType) {
            case this.constant.cardType.card:
              this.name = res.data.data.attrName;
              break;
            case this.constant.cardType.spaceship:
              this.name = res.data.data.ship_name;
              break;
            case this.constant.cardType.lootbox:
              this.name = res.data.data.type;
              break;
            case this.constant.cardType.pse:
              this.name = res.data.data.attrType || res.data.data.name;
              break;
            case this.constant.cardType.equipment:
              this.name = res.data.data.arms_name;
              break;
            case this.constant.cardType.egg:
              this.name = res.data.data.type;
              break;

            default:
              this.name = res.data.data.name;
              break;
          }
          this.ownerAddress = res.data.data.ownerAddress || res.data.data.owner_address;
          this.isCollection = res.data.data.isFavorite === 1;
          this.isOwner = this.ownerAddress === this.$store.state.address;
          // console.log(card.price , this.isOwner , card.isSelling);
          if (!card.price && this.isOwner && !card.isSelling) {
            this.tradeType = "SELL";
          } else if (card.price && this.isOwner && card.isSelling) {
            this.tradeType = "CANCEL";
          } else {
            this.tradeType = "BUY";
          }

          if (res.data.data.price === undefined && this.isOwner === false) {
            this.isShowPrice = false;
          }
          if (this.cardType === constant.cardType.egse) {
            this.egseAttr.type = constant.egseType[card.type];
          }
          if (this.cardType === constant.cardType.pse) {
            this.pseAttr.type = constant.pseType[card.type];
          }
          if (this.cardType === constant.cardType.card) {
            this.drawLine(card);
          }
          
          if (this.nftTypeIs("card") && card.tokenURI){
            card.heroType =  ''
            if([1,4].includes(this.profession)){
              card.heroType =  'Psionic'
            } else if([0,2].includes(this.profession)){
              card.heroType =  'Physical'
            } else {
            // 获取人物卡片的属性是灵能还是法系
              http
                .get(card.tokenURI)
                .then((res) => {
                  let data = res.data;
                  // console.log(data);
                   const Pisonic = data.attributes[12].value 
                   const Power = data.attributes[14].value 
                   if(Pisonic >= Power){
                    this.commentsCard.heroType =  'Physical'
                   } else{
                    this.commentsCard.heroType =  'Psionic'
                   }
                  // card.Location = data.attributes[14].value > data.attributes[12].value ? 0 : 1
                })
            }
          }
          
          // if(card.tokenURI) {
          //   this.getTokenDetailByTokenURI(card.tokenURI)
          // }

          this.commentsCard = card;
        })
        .finally(() => {
          this.loadingDetail = false;
        });
    },
    loadFee() {
      http.get("/api/v1/marketplace/tradeFee").then((res) => {
        this.fee = res.data.data;
      });
    },
    addView() {
      let tokenId = this.$route.query.id
      if(this.nftTypeIs('material') && this.$route.query.owner){
        const ownerAddress = this.$route.query.owner
        http.post(
          `/api/v1/marketplace/token/views/add?tokenId=${tokenId}&nftType=${this.cardType}&ownerAddress=${ownerAddress}`
        );
      } else {
        http.post(
          `/api/v1/marketplace/token/views/add?tokenId=${tokenId}&nftType=${this.cardType}`
        );
      }
    },
    onAddCollection() {
      this.loadingCollection = true;
      let tokenId = this.$route.query.id
      if(this.nftTypeIs('material')){
        tokenId = this.$route.query.goodId
      }
      http
        .post(
          `/api/v1/informationCenter/favorite/add?address=${this.$store.state.address}&tokenId=${tokenId}&nftType=${this.cardType}`
        )
        .then(() => {
          this.isCollection = true;
        })
        .finally(() => {
          this.loadingCollection = false;
        });
    },
    onCancelCollection() {
      this.loadingCollection = true;
      let tokenId = this.$route.query.id
      if(this.nftTypeIs('material')){
        tokenId = this.$route.query.goodId
      }
      http
        .post(
          `/api/v1/informationCenter/favorite/remove?address=${this.$store.state.address}&tokenId=${tokenId}&nftType=${this.cardType}`
        )
        .then(() => {
          this.isCollection = false;
        })
        .finally(() => {
          this.loadingCollection = false;
        });
    },
    initErc721() {
      if (typeof window.ethereum === "undefined") {
        this.$message.error({
          title: "Network Error",
          message: "MetaMask is installed!",
        });
        return;
      }

      this.erc721 = null;
      let type = parseInt(this.$route.query.type);
      if (type === constant.cardType.card) {
        this.erc721 = this.$erc721;
      }
      if (type === constant.cardType.egse) {
        this.erc721 = this.$erc721EGSE;
      }
      if (type === constant.cardType.pse) {
        this.erc721 = this.$erc721PSE;
      }
      if (type === constant.cardType.halloween) {
        this.erc721 = this.$erc721HSE;
      }
      if (type === constant.cardType.spaceship) {
        this.erc721 = this.$erc721SHIP;
      }
      if (type === constant.cardType.egg) {
        this.erc721 = this.$erc721EGG;
      }
      if (type === constant.cardType.equipment) {
        this.erc721 = this.$erc721ASE;
      }
      if (type === constant.cardType.lootbox) {
        this.erc721 = this.$erc721MBSE;
      }
      if (type === constant.cardType.material) {
        this.erc721 = this.$erc1155;
      }
    },
    onSell() {
      if (typeof window.ethereum === "undefined") {
        this.$message.error({
          title: "Network Error",
          message: "MetaMask is installed!",
        });
        return;
      }

      // 价格为0时，不能上架
      if (this.sellPrice - 0 <= 0) {
        this.$message.error({
          message: "Price must be greater than 0",
        });
        return;
      }
      //判断是否是有效金额
      // const isValidPrice = val =>  /(?:^[1-9]([0-9]+)?(?:\.[0-9]{1,2})?$)|(?:^(?:0)$)|(?:^[0-9]\.[0-9](?:[0-9])?$)/.test(val)
      const isValidPrice = val =>  /^[0-9]*([\.][0-9]*)?$/.test(val)
      if(!isValidPrice(this.sellPrice)){
        this.$message.error({
          message: "Please enter a valid Price",
        });
        return;
      }

      // 材料表单验证
      if(this.nftTypeIs('material')){
        // 验证正整数
        const isValidCnt = val=> /^[1-9]\d*$/.test(val)
        if(!isValidCnt(this.sellCnt)){
          this.$message.error({
            message: "Please enter a valid Qty",
          });
          return;
        }
        if(this.sellCnt > this.commentsCard.cnt){
          this.$message.error({
            message: "Qty can not be greater than Max",
          });
          return;
        }
      }

      this.loading = true;

      this.erc721.methods
        .isApprovedForAll(this.$store.state.address, process.env.VUE_APP_TARGET)
        .call()
        .then((isAllow) => {
          console.log(`是否授权物品：`, isAllow);
          if (isAllow) {
            this.addBidOrder();
          } else {
            this.erc721.methods
              .setApprovalForAll(process.env.VUE_APP_TARGET, true)
              .send({
                from: this.$store.state.address,
              })
              .then((hash) => {
                console.log(`setApprovalForAll`, hash);
                this.addBidOrder();
              })
              .catch((e) => {
                console.log("approvaledForALL", e);
              });
          }
        })
        .catch(err => {
          this.loading = false;
        })

    },
    onAskForSs() {
      if (this.nftTypeIs("spaceship")) {
        const ss = this.$refs.$SpaceShip && this.$refs.$SpaceShip.ss;
        // const ss = {
        //   Quark: 0,
        //   Fire: 11000,
        // };
        if (!ss) {
          this.onAsk();
        }
        this.errorMsg = "";
        const numberAddDot = (n) => n.toString().replace(/(\d)(?=(?:\d{3})+$)/g, "$1,");
        const thr = 10000;
        const quarkLimit = 300000;
        const fireLimit = 20000;
        if (ss.Quark + thr > quarkLimit) {
          this.errorMsg += `(${numberAddDot(ss.Quark)} / ${numberAddDot(
            quarkLimit
          )} QUARK)`;
        }
        if (ss.Fire + thr > fireLimit) {
          this.errorMsg += `(${numberAddDot(ss.Fire)} / ${numberAddDot(fireLimit)} FIRE)`;
        }
        // console.log("this.errorMsg", this.errorMsg);
        if (this.errorMsg) {
          this.ssDialogVisible = true;
        } else {
          this.onAsk();
        }
      } else {
        this.onAsk();
      }
    },
    async onAsk() {
      this.ssDialogVisible = false;
      if (typeof window.ethereum === "undefined") {
        this.$message.error({
          title: "Network Error",
          message: "MetaMask is installed!",
        });
        return;
      }

      // 订单金额
      let bidPrice = this.$web3.utils.toBN(this.toDecimalString(this.price));
      try {
        this.loading = true;
        // 查询账户余额
        const balance = await this.$erc20.methods
          .balanceOf(this.$store.state.address)
          .call({ from: this.$store.state.address });
        // console.log(balance);
        console.log(`账户余额${balance / 10 ** 18}`);
        if (this.$web3.utils.toBN(balance).lt(bidPrice)) {
          // 余额不足，提示用户，拒绝交易
          this.$message.error({
            message: "Transaction failed. You do not have enough balance.",
            duration: 10 * 1000,
          });
          // 有内鬼，终止交易
          this.loading = false;
          return;
        }

        // 代币授权检查
        const amount = await this.$erc20.methods
          .allowance(this.$store.state.address, process.env.VUE_APP_TARGET)
          .call({ from: this.$store.state.address });
        // console.log(amount);
        console.log(`已授权额度：${amount / 10 ** 18}，订单金额：${this.price}`);
        if (this.$web3.utils.toBN(amount).lt(bidPrice)) {
          // 代币授权不足，重新授权
          await this.$erc20.methods
            .approve(process.env.VUE_APP_TARGET, bidPrice)
            .send({ from: this.$store.state.address });
        }

        // erc721合约授权检查
        const isAllow = await this.erc721.methods
          .isApprovedForAll(this.$store.state.address, process.env.VUE_APP_TARGET)
          .call();
        console.log(`是否授权物品：`, isAllow);
        if (!isAllow) {
          // 重新授权erc721
          await this.erc721.methods
            .setApprovalForAll(process.env.VUE_APP_TARGET, true)
            .send({ from: this.$store.state.address });
        }
        // 发起交易
        this.addAskOrder();
      } catch (error) {
        this.loading = false;
        console.log("交易失败:", error);
        if (error && error.message) {
          this.$message.error({
            message: error.message,
            duration: 10 * 1000,
          });
        }
      }
    },
    onCancel() {
      this.loading = true;
      // const tokenId = !this.nftTypeIs('material') ? this.$route.query.id : 0;
      const tokenId = this.$route.query.id
      const msgStr = `tokenId=${tokenId}&address=${this.$store.state.address}`
      let msg = this.$web3.utils.fromUtf8(msgStr);
      this.$web3.eth.personal
        .sign(msg, this.$store.state.address)
        .then((sign) => {
        const postData =  {
            // "address": "string",
            // "goodId": 0,
            // "message": "string",
            // "nftType": 0,
            // "ownerAddress": "string",
            // "signature": "string",
            // "tokenId": 0
            address: this.$store.state.address,
            tokenId: tokenId,
            nftType: this.cardType,
            signature: sign,
          }
          if(this.nftTypeIs('material')){
            Object.assign(postData,{
              tokenId:0,
              // cnt: this.sellCnt,
              // id: this.commentsCard.id,
            })
            if(this.$route.query.goodId){
              Object.assign(postData,{
                goodId: this.$route.query.goodId - 0,
              })
            }
          }
          http
            .post("/api/v1/informationCenter/cancelSell", postData)
            .then((res) => {
              // this.loading = false;
              if (res.data.code === 200) {
                this.$message.success({
                  message: "Cancel Success!",
                  onClose: () => {
                    // this.$router.push('/market');
                    this.$router.back();
                  },
                });
              }
            })
            .catch(err => {
              this.loading = false;
            })
        })
        .catch(err => {
          this.loading = false;
        })
    },
    addBidOrder() {
      // const tokenId = !this.nftTypeIs('material') ? this.$route.query.id : 0
      const tokenId = this.$route.query.id
      const msgStr = `tokenId=${tokenId}&address=${this.$store.state.address}`
      let msg = this.$web3.utils.fromUtf8(msgStr);
      this.$web3.eth.personal.sign(msg, this.$store.state.address).then((res) => {
        const postData =  {
            tokenId: this.$route.query.id,
            nftType: this.cardType,
            ownerAddress: this.$store.state.address,
            address: this.$store.state.address,
            price: this.sellPrice,
            signature: res,
          }
          if(this.nftTypeIs('material')){
            Object.assign(postData,{
              tokenId:0,
              cnt: this.sellCnt,
              id: this.commentsCard.id,
            })
          }
        http
          .post("/api/v1/informationCenter/sellToken",postData)
          .then((res) => {
            this.loading = false;
            this.$message.success("Transaction Complete!");
            // this.$router.push({
            //   path: '/storehouse',
            // });
            this.$router.back();
          })
          .catch(err => {
            this.loading = false;
          })
      })
      .catch(err => {
        this.loading = false;
      })
    },
    addAskOrder() {
      // const tokenId = !this.nftTypeIs('material') ? this.$route.query.id : 0
      const tokenId = this.$route.query.id
      const msgStr = `tokenId=${tokenId}&address=${this.$store.state.address}`
      let msg = this.$web3.utils.fromUtf8(msgStr);
      this.$web3.eth.personal.sign(msg, this.$store.state.address).then((sign) => {
        const postData = {
          address: this.$store.state.address,
          tokenId: this.$route.query.id,
          nftType: this.cardType,
          price: this.price,
          signature: sign,
        }
        if(this.nftTypeIs('material')){
          Object.assign(postData,{
            tokenId: 0,
            goodId: this.$route.query.goodId - 0,
            cnt: this.commentsCard.cnt,
            id: this.commentsCard.id,
          })
        }
        http
          .post("/api/v1/informationCenter/buyToken", postData)
          .then((res) => {
            this.loading = false;
            // console.log('buyToken', res);

            if (res.data.code === 200) {
              this.$message.success({
                message:
                  "card purchase sucess，it will shows in  your storage till the transfer finish on the blockchain",
                // onClose: () => {
                //   this.$router.push({
                //     path: '/market',
                //   });
                // },
              });
              // this.$router.push({
              //   path: '/market',
              // });
              this.$router.back();
            } else {
              // edge case handler
              const errorMsg = res.data.msg || "";
              this.$message.error({
                message: "transaction failed" + errorMsg,
              });
            }
          })
          .catch((e) => {
            console.log(e);
            this.loading = false;
          });
      })
      .catch(err => {
        this.loading = false;
      })
    },
    toDecimalString(value) {
      let str = value.toString();
      let index = str.indexOf(".");
      let len = parseInt(process.env.VUE_APP_TOKEN_DECIMALS);
      if (index > -1) {
        len -= str.substring(index + 1).length;
      }
      for (let i = 0; i < len; i++) {
        str += "0";
      }
      return str.replace(".", "");
    },
    onCopy() {
      this.$message.success("Copy Url Success");
    },
    getTokenDetailByTokenURI(tokenURI){
      http
        .get(tokenURI)
        .then((res) => {
          let data = res.data;
          console.log('链上数据：',data);
        })
    }
  },
  // watch: {
  //   cardType: preValue => {
  //     if (preValue === 4) {
  //       const iframe = document.getElementsByTagName('iframe')[0];
  //       if (iframe) {
  //         console.log(iframe);
  //         iframe.style.width = '500px';
  //         iframe.style.height = '260px';
  //       }
  //     }
  //   },
  // },
};
</script>

<style lang="scss" scoped>
.selldetails {
  /* min-height: calc(100vh - 190px); */
}

.pic-heard {
  /* width: 100%; */
  width: 60vw;
  min-height: 30vw;
  /* margin: 69px auto; */
  padding: 50px;
  margin: 60px auto;
  /* @include flex; */
  display: flex;
  align-items: stretch;

  $w: 50px;
  $bg: rgba(
    $color: #2f3a77,
    $alpha: 0.25
  );
  $border: 4px solid
    rgba(
      $color: #568eff,
      $alpha: 0.25
    );
  background-color: $bg;
  border: $border;
  position: relative;
  clip-path: polygon(
    0 0,
    calc(100% - $w) 0,
    100% $w,
    100% 100%,
    100% 100%,
    $w 100%,
    0 calc(100% - $w),
    0 0
  );
  &::before {
    content: "";
    position: absolute;
    top: 0;
    right: 0;
    border: $border;
    border-width: calc($w / 2 - 1px);
    border-left-color: transparent;
    border-bottom-color: transparent;
  }
  &::after {
    content: "";
    position: absolute;
    bottom: 0;
    left: 0;
    border: $border;
    border-width: calc($w / 2 - 1px);
    border-top-color: transparent;
    border-right-color: transparent;
  }
  /* box-sizing: border-box; */

  &-left {
    width: 38.2%;
    text-align: center;
    &.isShip {
      width: 60%;
    }
    /* width: 468px; */
    /* height: 664px; */
    margin-right: 32px;
    @include img;
    img {
      width: 100%;
      height: 100%;
      max-height: 40vw;
      object-fit: contain;
    }
    video{
      width: 100%;
      height: 100%;
      max-height: 40vw;
      // object-fit: contain;
    }

    iframe {
      width: 60vw;
      height: 30vw;
    }
  }

  &-left2 {
    width: 690px;
    height: 613px;
    @include img;
    margin-right: 69px;
  }

  &-left3 {
    width: 372px;
    height: 588px;
    @include img;
    margin-right: 209px;
  }

  &-left4 {
    width: 536px;
    height: 617px;
    @include img;
    margin-right: 161px;
  }

  /* 右侧 */
  &-right {
    /* width: 50%; */
    flex: 1;
    display: flex;
    flex-direction: column;
    /* justify-content: space-between; */

    /* 标题行 */
    &-heard {
      display: flex;
      flex-wrap: wrap;
      align-items: flex-end;
      margin-bottom: 12px;
      line-height: 1.5;

      h2 {
        margin: 0;
        padding: 0;
        font-size: 55px;
        line-height: 50px;
        font-family: Shentox TRIAL;
        font-weight: bold;
        text-transform: capitalize;
        font-style: italic;
        color: #ffffff;
        margin-right: 20px;
      }
      div {
        margin-right: 20px;
      }
      .level {
        font-size: 18px;
        font-family: Shentox TRIAL;
        // text-transform: uppercase;
        font-style: italic;
        color: #ffffff;
      }
      .psionic-tag,
      .physical-tag{
        padding: 6px 20px;
        text-align: center;
        font-size: 20px;
        line-height: 20px;
        font-family: Shentox TRIAL;
        text-transform: capitalize;
        border-radius: 0% 4px 0% 0%;
        padding: 2px 5px;
        background: #529bf0;
      }
      .physical-tag{
        background: #ef4b70;
      }
      .rarity {
        padding: 6px 20px;
        text-align: center;
        /* width: 120px; */
        font-size: 20px;
        line-height: 20px;
        font-family: Shentox TRIAL;
        text-transform: capitalize;
        font-style: italic;
        font-weight: bolder;
        color: #fff;
        color: #000;
        $w: 10px;
        clip-path: polygon(
          0 0,
          calc(100% - $w) 0,
          100% $w,
          100% 100%,
          100% 100%,
          $w 100%,
          0 calc(100% - $w),
          0 0
        );
        background-color: rgba(154, 154, 154, 1);
        &.Rare {
          background-color: rgba(0, 119, 255, 1);
        }
        &.Epic {
          background-color: rgba(135, 0, 255, 1);
        }
        &.Legend,
        &.Legendary {
          background-color: rgba(255, 216, 0, 1);
        }
      }

      &-img {
        width: 42px;
        height: 42px;
        @include img;
      }
    }

    &-lable {
      display: flex;
      margin-bottom: 12px;

      &-left {
        &-a {
          @include flex;
          align-items: center;

          &-a {
            width: 26px;
            height: 15px;
            @include img;
          }

          &-b {
            font-size: 20px;
            font-family: Shentox TRIAL;
            font-weight: 400;
            color: #ffffff;
            margin-left: 15px;
          }
        }

        &:nth-child(2) {
          margin-left: 48px;
        }
      }
    }

    &-introduce {
      font-weight: 400;
      font-size: 20px;
      font-family: Shentox TRIAL;

      line-height: 30px;
    }

    &-footer {
      /* position: absolute; */
      /* bottom: 50px; */
      /* right: 100px; */
      /* width: 20vw; */
      flex: 1;
      display: flex;
      justify-content: flex-end;
      align-items: flex-end;
      flex-direction: column;
      p {
        font-size: 20px;
        font-family: Shentox TRIAL;
        font-weight: bold;
        color: #ffffff;
      }

      &-content {
        display: flex;
        align-items: center;
        /* margin-bottom: 20px; */
        justify-content: space-between;
        width: 100%;
        /* margin-bottom: 20px; */
      }

      &-item-buy {
        /* width: 234.6px;
        height: 65.5px; */
        @include bgc;
        @include flex;
        align-items: center;
        padding: 0px 10px 0 15px;
        text-align: center;

        /* margin-left: 23px; */

        &-p {
          margin-right: 10px;
          font-size: 36px !important;
        }

        // box-sizing: border-box;
        &-left {
          width: 2px;
          height: 19px;
          background: #f9feff;
        }

        &-right {
          display: flex;
          align-items: center;

          &-img {
            width: 35px;
            height: 35px;
            @include img;
          }

          &-img2 {
            width: 21px;
            height: 32px;
            @include img;
            margin-left: 5px;
          }
        }
      }

      &-item-sell {
        width: 234.6px;
        height: 65.5px;
        background: url(~@/assets/images/textbg1.png);
        @include bgc;
        @include flexsp;
        align-items: center;
        padding: 0px 30px 0 35px;
        text-align: center;
        margin-left: 23px;

        // box-sizing: border-box;
        &-left {
          .pit {
            border: none !important;
            background-color: transparent;
            width: 80px;
            font-size: 18px;
          }
          input[type=number]::-webkit-inner-spin-button, 
          input[type=number]::-webkit-outer-spin-button { 
            -webkit-appearance: none;
            margin: 0; 
          }
          input[type=number] {
              -moz-appearance:textfield;
          }
        }

        &-right {
          display: flex;
          align-items: center;

          &-img {
            width: 35px;
            height: 29px;
            @include img;
          }

          &-img2 {
            width: 21px;
            height: 32px;
            @include img;
            margin-left: 5px;
          }
        }
      }

      &-item2 {
        width: 454.6px;
        height: 65.5px;
        margin-left: 80px;
        cursor: pointer;
        /* background: url(../../../assets/images/textbg.png); */
        /* @include bgc; */
        background: linear-gradient(to right, #4e7da6 0%, #20294f 100%);
        line-height: 65.5px;
        text-align: center;
        border-radius: 10px;
        &.BUY {
          background: linear-gradient(
            to right,
            RGBA(79, 127, 173, 1) 0%,
            RGBA(60, 75, 149, 1) 100%
          );
        }
        &.CANCEL {
          background: linear-gradient(
            to right,
            RGBA(167, 7, 13, 1) 0%,
            RGBA(167, 29, 34, 1) 100%
          );
        }
        &.SELL {
          background: linear-gradient(
            to right,
            RGBA(128, 31, 179, 1) 0%,
            RGBA(128, 31, 179, 1) 100%
          );
        }

        p {
          font-size: 35px;
          font-family: Shentox TRIAL;
          font-weight: 400;
          color: #ffffff;
        }
      }
    }

    &-content {
      display: flex;
      align-content: center;
    }
  }
}

.pic-content {
  margin-top: 68px;
  margin-left: 237px;
  display: flex;
  align-items: center;
  justify-content: end;

  &-left {
    &-heard {
      // @include flex;
      display: flex;
      align-items: center;
      font-size: 20px;
      font-family: Shentox TRIAL;
      font-weight: bold;
      color: #ffffff;

      &-img {
        width: 20px;
        height: 15px;
        @include img;
      }
    }

    &-box {
      display: flex;
      margin-top: 38px;

      &-item {
        width: 275px;
        height: 158px;
        background: url(../../../assets/images/selliconbg4k.png);
        @include bgc;
        background-size: 100% 100%;

        &-img {
          width: 55.59px;
          height: 62.96px;
          margin-left: 33px;
          margin-top: 22px;
          @include img;
        }

        p {
          font-size: 20px;
          font-family: Shentox TRIAL;
          font-weight: 400;
          color: #ffffff;
          margin-left: 33px;
          margin-top: 22px;
        }

        &:nth-child(2) {
          margin-left: 28px;
        }
      }
    }
  }

  &-right {
    width: 920px;
    min-height: 900px;
    // background: url(~@/assets/images/sellicon11.png);
    @include bgc;
    position: relative;
    margin-top: 33px;
    margin-right: 150px;

    &-img1 {
      width: 540px;
      height: 540px;
      @include img;
      @include psa;
    }

    &-img2 {
      width: 432px;
      height: 425px;
      @include img;
      // @include psa;
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-43%, -52.2%);
    }

    &-img3 {
      width: 18px;
      height: 18px;
      @include img;
      position: absolute;
      left: 49%;
      top: 13%;
    }

    &-p {
      font-size: 20px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      position: absolute;
      left: 50%;
      top: -3%;
    }
  }
}

.pic-heard-right-card {
  width: 70%;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  min-height: 190px;

  &-item {
    width: 30%;
    background-image: url(~@/assets/images/tokenbg.png);
    background-size: 100% 100%;
    height: 116.6px;
    margin-bottom: 22px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;

    &-img {
      width: 60px;
      height: 68px;
      @include img;
    }

    &-title {
      margin-top: 10px;
    }

    &:nth-child(4),
    &:nth-child(5),
    &:nth-child(6) {
      margin-bottom: 0;
    }
  }
}

.pic-heard-right-heard-img {
  /* margin-left: 25px !important; */
  width: 28px;
  height: 28px;
  img {
    cursor: pointer;
    width: 28px;
    height: 28px;
    object-fit: contain;
  }
  &::v-deep .el-loading-spinner {
    transform: scale(0.5);
  }
}

.fee {
  display: flex;
  justify-content: space-between;
  font-size: 20px;
}

.buya {
  width: 45px;
  height: 63px;
}

.buyb {
  width: 45px;
  height: 63px;
}

.buyc {
  width: 61px;
  height: 60px;
}

.buyd {
  width: 61px;
  height: 63px;
}

#main {
  width: 100%;
  height: 800px;
}

.ecahrtsss {
  @include psa;
}

@media screen and (max-width: 768px) {
  .selldetails {
    height: auto;
    .pic-heard {
      margin: 0;
      padding: 0 0.65rem;
      flex-direction: column;
      align-items: center;
      padding-top: 1.4rem;
    }
    .pic-heard-left {
      width: 4.36rem;
      height: 6.18rem;
      margin-right: 0;
    }
    .pic-heard-right {
      margin: 0;
      width: 100%;
      .pic-heard-right-heard {
        font-size: inherit;
        position: relative;
        display: block;
        padding-top: 0.5rem;
        p {
          font-size: 0.5rem;
          text-align: center;
        }
        > div {
          margin-left: 1.07rem !important;
          position: absolute;
          top: -7rem;
          .pic-heard-right-heard-img {
            width: 0.42rem;
            height: 0.42rem;
            &:nth-child(2) {
              margin-left: 0.5rem;
            }
          }
        }
      }
      .pic-heard-right-lable {
        margin-top: 0.3rem;
        justify-content: space-around;
        .pic-heard-right-lable-left:nth-child(2) {
          margin-left: 0;
        }
        .eye {
          width: 0.42rem;
          height: 0.26rem;
        }
        .heart {
          width: 0.3rem !important;
          height: 0.26rem !important;
        }
        .pic-heard-right-lable-left-a-b {
          font-size: 0.24rem;
          margin-left: 0.12rem;
        }
      }
      .pic-heard-right-introduce {
        font-size: 0.24rem;
        margin-top: 0.6rem;
      }
      .pic-heard-right-footer {
        display: flex;
        flex-direction: column;
        align-content: flex-end;
        margin-top: 0.6rem;
        > p {
          font-size: 0.2rem;
        }
        .pic-heard-right-footer-item-buy {
          height: 0.8rem;
          margin-left: 0;
          padding: 0 0.15rem;
          width: auto;
          .pic-heard-right-content {
            display: flex;
            align-content: center;
            > p {
              font-size: 0.4rem !important;
            }
            > div {
              width: 0.6rem;
              height: 0.6rem;
            }
          }
        }
        .pic-heard-right-footer-item2 {
          /* margin-left: 0.2rem; */
          width: 100%;
          height: 0.84rem;
          user-select: none;
          > p {
            font-size: 0.4rem;
            line-height: 0.84rem;
          }
        }
      }
    }
    .pic-content {
      margin: 0;
      width: 100%;
      flex-direction: column;
      .pic-content-left {
        width: 100%;
        padding: 0 0.65rem;
        .pic-content-left-heard {
          font-size: inherit;
          margin-top: 1rem;
          .pic-content-left-heard-img {
            width: 0.2rem;
            height: 0.15rem;
          }
          > p {
            font-size: 0.2rem;
          }
        }
        .pic-content-left-box {
          margin-top: 0.35rem;
          justify-content: space-between;
          .pic-content-left-box-item {
            width: 2.95rem;
            height: 1.7rem;
            &:nth-child(2) {
              margin-left: 0;
            }
            .pic-content-left-box-item-img {
              width: 0.6rem;
              height: 0.78rem;
              margin-top: 0.24rem;
              margin-left: 0.35rem;
            }
            > p {
              font-size: 0.2rem;
              margin-top: 0.24rem;
              margin-left: 0.35rem;
            }
          }
        }
      }
      .pic-content-right {
        margin-top: 1.5rem;
        margin-left: 0;
        width: 7rem;
        height: 4.56rem;
        .pic-content-right-img1 {
          width: 4rem;
          height: 4rem;
        }
      }
    }

    .pic-heard-right-footer {
      margin-bottom: 20px;
    }

    .pic-heard-right-footer-content {
      width: 100%;

      p {
        font-size: 16px;
      }
    }
  }
  .ecahrtsss {
  }
  #main {
    height: 4.56rem;
  }
}

.ss-dialog {
  position: fixed;
  z-index: 2000;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  /* width: 100vw;
  height: 100vh; */
  /* background-color: transparent; */
  /* transition: background 1000ms; */
  background-color: rgba($color: #000000, $alpha: 0.2);
  backdrop-filter: blur(10px);
  display: flex;
  justify-content: center;
  align-items: center;
  .dialog-content {
    width: 852px;
    height: 512px;
    /* background-color: orange; */
    background-image: url("../../../assets/images/buy-dialog-bg.png");
    background-size: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    .head {
      /* background-color: orange; */
      margin-top: 24px;
      height: 100px;
      width: 100%;
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
      img {
        position: absolute;
      }
      /* background-image: url("../../../assets/images/warning.png"),
        url("../../../assets/images/warning-shadow.png");
      background-size: 100%;
      background-position: center;
      background-repeat: no-repeat; */
    }
    .body {
      flex: 1;
      padding: 24px 48px;
      font-size: 32px;
      line-height: 1.5;
      text-align: center;
      .red {
        color: rgba(239, 75, 112, 1);
      }
    }
    .foot {
      padding: 24px;
      padding-bottom: 48px;
      display: flex;
      justify-content: center;
      .btn {
        background-color: RGBA(54, 90, 183, 1);
        color: #fff;
        padding: 12px 24px;
        min-width: 200px;
        font-size: 32px;
        line-height: 32px;
        text-transform: uppercase;
        white-space: nowrap;
        text-align: center;
        user-select: none;
        cursor: pointer;
        &.grey {
          background-color: RGBA(138, 142, 149, 1);
        }
        + .btn {
          margin-left: 24px;
        }
      }
    }
  }
}
</style>
