<template>
  <div>
    <div class="content-box">
      <p class="content-title">TOP NFTS</p>
      <p class="content-subtitle">
        TRANSACTION DATA STATISTICS FOR CARDS AND STARSHIPS IN THE TRADING MARKET
      </p>
      <div class="content-box-select">
        <ng-select
          :default-value="cardType"
          @filter="onCardChange"
          :options="cardTypeOption"
          place="ASSET TYPE"
          key="ASSETTYPE"
        />
        <ng-select
          :default-value="camp"
          v-if="constant.cardType.card == cardType"
          :options="campOption"
          @filter="onCampChange"
          place="CAMP"
          key="CAMP"
        />
        <ng-select
          :default-value="rarity"
          v-if="constant.cardType.card == cardType"
          :options="rarityOption"
          @filter="onRarityChange"
          place="RARITY"
          key="RARITY"
        />
        <ng-select
          :default-value="pseType"
          v-if="constant.cardType.pse == cardType"
          :options="pseTypeOption"
          @filter="onPseChange"
          place="TYPE"
          key="TYPE"
        />
        <ng-select
          :default-value="egseType"
          v-if="constant.cardType.egse == cardType"
          :options="egseTypeOption"
          @filter="onEgseChange"
          place="TYPE"
          key="TYPE"
        />
        <!-- 飞船过滤条件 start -->
        <ng-select
          :default-value="spaceShipType"
          v-if="constant.cardType.spaceship == cardType"
          :options="spaceShipTypeOption"
          @filter="onSpaceShipTypeChange"
          place="FACTION"
          key="FACTION"
        />

        <ng-select
          :default-value="spaceShipLevel"
          v-if="constant.cardType.spaceship == cardType"
          :options="spaceshipLevelOption"
          @filter="spaceShipLevelChange"
          place="CARD LEVEL"
          key="CARDLEVEL"
        />

        <!-- 飞船过滤条件 end -->
      </div>
      <div class="content-box-title">
        <p class="content-box-title-item">COLLECTION</p>

        <div class="content-box-title-item-box mar-ng2">
          <p class="content-box-title-item">TRANSACTION AMOUNT</p>
        </div>
        <div class="content-box-title-item-box mar-ng3">
          <p class="content-box-title-item">OWNERS</p>
        </div>
      </div>

      <div class="content-box-list">
        <ul class="content-box-list-box">
          <li v-for="(item, index) in list" :key="index">
            <div class="content-box-list-box-idd">{{ index + 1 }}</div>
            <img
              :src="item.image"
              class="content-box-list-box-img"
              @click="
                $router.push({
                  path: '/token',
                  query: { id: item.tokenId, type: item.type },
                })
              "
            />
            <!-- <div
              @click="
                $router.push({
                  path: '/token',
                  query: { id: item.tokenId, type: item.type },
                })
              "
              class="content-box-list-box-img"
              :style="{
                backgroundImage: 'url(' + item.image + ')',
                cursor: 'pointer',
              }"
            ></div> -->
            <div class="content-box-list-box-tran">
              <div class="content-box-list-box-tran-iid">{{ item.price }}</div>
              <div class="content-box-list-box-tran-img">
                <img src="../../../assets/images/market/priceicon4k.png" alt="" />
              </div>
            </div>
            <div class="content-box-list-box-idd2">{{ item.address }}</div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import NgSelect from "../../../components/select/index";
import http from "../../../config/http";
import rank from "../../../mixin/rank";

export default {
  name: "TopNfts",
  components: {
    NgSelect,
  },
  mixins: [rank],
  data() {
    return {};
  },
  created() {
    this.init();
    this.load();
  },
  methods: {
    load() {
      let query = this.getQueryParameter();
      http
        .get("/api/v1/statistics/topNft", {
          params: query,
        })
        .then((res) => {
          this.list = res.data.data.records.map((ele) => {
            let addr = `${ele.ownerAddress.substring(
              0,
              12
            )}...${ele.ownerAddress.substring(ele.ownerAddress.length - 10)}`;
            const ret= {
              tokenId: ele.tokenId,
              address: addr,
              price: ele.price,
              type: ele.nftType,
              image:  ele.imageHead,
            };
            if(this.nftTypeIs('spaceship') && ele.model){
              ret.image = ele.model
            }
            return ret
          });
        });
    },
    priceFormat(value) {
      if (value < 1000) {
        return value;
      }
      return `${(value / 1000).toFixed(3)} K`;
    },
  },
};
</script>

<style lang="scss" scoped>
.content-box {
  margin: 44px auto 0 auto;

  .content-box-title-item-box {
    @include flexsp;
    align-items: center;
  }

  .content-title {
    font-size: 35px;
    font-family: Shentox TRIAL serif;
    font-weight: bold;
    text-align: center;
  }

  .content-subtitle {
    margin-top: 28px;
    font-size: 20px;
    font-family: Shentox TRIAL serif;
    font-weight: 400;
    text-align: center;
  }

  &-select {
    // @include flex;
    display: flex;
    margin-left: 360px;
    margin-top: 55px;
  }

  &-title {
    display: flex;
    margin-left: 360px;
    margin-top: 109px;

    &-item {
      font-size: 17px;
      font-family: Shentox TRIAL serif;
      font-weight: 400;

      &-san {
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin-left: 15px;
        cursor: pointer;
      }
    }
  }

  &-list {
    margin-top: 50px;
    margin-left: 292px;
    padding-bottom: 150px;
    min-height: calc(100vh - 200px);

    &-box {
      li {
        // @include flex;
        display: flex;
        align-items: center;
        margin-top: 37px;

        &:nth-child(1) {
          margin-top: 0;
        }
      }

      &-idd {
        min-width: 33px;
        font-size: 19px;
        font-family: Shentox TRIAL serif;
        font-weight: bold;
        text-align: left;
      }

      &-img {
        width: 382px;
        height: 52px;
        // @include img;
        margin-left: 33px;
        @include bgc;
        font-size: 20px;
        font-family: Shentox TRIAL serif;
        font-weight: 400;
        line-height: 52px;
        /* padding-left: 18px; */
        cursor: pointer;
        /* border: 3px solid rgba(100, 134, 154, 1); */
      }

      &-idd2 {
        font-size: 35px;
        font-family: Shentox TRIAL serif;
        font-weight: 500;
        margin-left: 150px;
      }

      &-idd3 {
        font-size: 35px;
        font-family: Shentox TRIAL serif;
        font-weight: 500;
        margin-left: 180px;
      }

      &-idd4 {
        font-size: 35px;
        font-family: Shentox TRIAL serif;
        font-weight: 500;
        margin-left: 160px;
      }

      &-tran {
        // @include flex;
        display: flex;
        align-items: center;
        margin-left: 90px;
        max-width: 150px;

        &-iid {
          width: 90px;
          font-size: 35px;
          font-family: Shentox TRIAL serif;
          font-weight: 500;
        }

        &-img {
          width: 35px;
          height: 29px;
          @include img;
          margin-left: 21px;
        }
      }
    }
  }
}

.mar-ng {
  margin-left: 251px;
}

.mar-ng2 {
  margin-left: 380px;
}

.mar-ng3 {
  margin-left: 120px;
}

.mar-ng4 {
  margin-left: 95px;
}

.mar-ng5 {
  margin-left: 127px;
}

@import "@/assets/scss/count.scss";
</style>
