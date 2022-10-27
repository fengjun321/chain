<template>
  <div>
    <div class="content-box">
      <p class="content-title">ACTIVITY</p>
      <p class="content-subtitle">
        THE LATEST 100 TRANSACTION INFORMATION ON THE SPARK ERA TRADING MARKET
      </p>
      <div class="content-box-select">
        <ng-select
          :default-value="cardType"
          key="cardType"
          @filter="onCardChange"
          :options="cardTypeOption"
          place="ASSET TYPE"
        />
        <ng-select
          :default-value="camp"
          key="camp"
          v-if="constant.cardType.card === cardType"
          :options="campOption"
          @filter="onCampChange"
          place="CAMP"
        />
        <ng-select
          :default-value="rarity"
          key="rarity"
          v-if="constant.cardType.card === cardType"
          :options="rarityOption"
          @filter="onRarityChange"
          place="RARITY"
        />
        <ng-select
          :default-value="pseType"
          key="pseType"
          v-if="constant.cardType.pse === cardType"
          :options="pseTypeOption"
          @filter="onPseChange"
          place="TYPE"
        />
        <ng-select
          :default-value="egseType"
          key="egseType"
          v-if="constant.cardType.egse === cardType"
          :options="egseTypeOption"
          @filter="onEgseChange"
          place="TYPE"
        />
        <!-- 飞船过滤条件 start -->
        <ng-select
          :default-value="spaceShipType"
          key="spaceShipType"
          v-if="constant.cardType.spaceship === cardType"
          :options="spaceShipTypeOption"
          @filter="onSpaceShipTypeChange"
          place="FACTION"
        />

        <ng-select
          :default-value="spaceShipLevel"
          key="spaceShipLevel"
          v-if="constant.cardType.spaceship === cardType"
          :options="spaceshipLevelOption"
          @filter="spaceShipLevelChange"
          place="CARD LEVEL"
        />

        <!-- 飞船过滤条件 end -->
      </div>
      <div class="content-box-title">
        <p class="content-box-title-item">ITEM</p>

        <div class="content-box-title-item-box mar-ng2">
          <p class="content-box-title-item">PRICE</p>
        </div>

        <div class="content-box-title-item-box mar-ng3">
          <p class="content-box-title-item">FROM</p>
        </div>

        <div class="content-box-title-item-box mar-ng4">
          <p class="content-box-title-item">TO</p>
        </div>

        <div class="content-box-title-item-box mar-ng5">
          <p class="content-box-title-item">TIME</p>
        </div>
      </div>

      <div class="content-box-list">
        <ul class="content-box-list-box">
          <li v-for="(item, index) in list" :key="index">
            <div class="content-box-list-box-idd">{{ index + 1 }}</div>
            <div
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
            ></div>
            <div class="content-box-list-box-tran">
              <div class="content-box-list-box-tran-iid">{{ item.price }}</div>
              <div class="content-box-list-box-tran-img">
                <img src="../../../assets/images/market/priceicon4k.png" alt="" />
              </div>
            </div>
            <div class="content-box-list-box-idd2">{{ item.from }}</div>
            <div class="content-box-list-box-idd3">{{ item.to }}</div>
            <div class="content-box-list-box-idd4">{{ item.time }}</div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import NgSelect from "@/components/select/index";
import http from "../../../config/http";
import rank from "../../../mixin/rank";

export default {
  name: "Active",
  components: {
    NgSelect,
  },
  mixins: [rank],
  data() {
    return {
      list: [],
      camp: -1,
    };
  },
  created() {
    this.init();
    this.load();
  },
  methods: {
    load() {
      let query = this.getQueryParameter();
      http
        .get("/api/v1/statistics/activity", {
          params: query,
        })
        .then((res) => {
          this.list = res.data.data.records.map((ele) => {
            let to =
              ele.toAddress.substring(0, 6) +
              "..." +
              ele.toAddress.substring(ele.toAddress.length - 6);
            let from =
              ele.fromAddress.substring(0, 6) +
              "..." +
              ele.fromAddress.substring(ele.fromAddress.length - 6);
            return {
              tokenId: ele.tokenId,
              from: from,
              to: to,
              price: ele.price,
              type: ele.nftType,
              time: this.timestampFormat(ele.tradeTimestamp),
              image: ele.imageHead,
            };
          });
        });
    },
    timestampFormat(timestamp) {
      let current = new Date().valueOf();
      let second = 1000;
      let minute = second * 60;
      let hour = minute * 60;
      let day = hour * 24;
      let result = current - timestamp;
      if (result < second) {
        return `${this.toNumberStr(result / second)} SEC AGO`;
      } else if (result < hour) {
        return `${this.toNumberStr(result / minute)} MINS AGO`;
      } else if (result < day) {
        let h = this.toNumberStr(result / hour);
        let m = this.toNumberStr((result % hour) / minute);
        return `${h} HRS ${m} MINS AGO`;
      } else {
        let d = this.toNumberStr(result / day);
        let h = this.toNumberStr((result % day) / hour);
        return `${d} DAY ${h} HRS AGO`;
      }
    },
    toNumberStr(value) {
      let index = value.toString().indexOf(".");
      if (index === -1) {
        return value.toString();
      }
      return value.toString().substring(0, index);
    },
    priceFormat(value) {
      if (value < 1000) {
        return this.toNumberStr(value);
      }
      return `${(value / 1000).toFixed(3)} K`;
    },
  },
};
</script>

<style lang="scss" scoped>
.content-box {
  margin: 44px auto 0px auto;

  .content-box-title-item-box {
    @include flexsp;
    align-items: center;
  }

  .content-title {
    font-size: 35px;
    font-family: Shentox TRIAL;
    font-weight: bold;
    text-align: center;
  }

  .content-subtitle {
    margin-top: 28px;
    font-size: 20px;
    font-family: Shentox TRIAL;
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
    margin-left: 307px;
    margin-top: 109px;

    &-item {
      font-size: 17px;
      font-family: Shentox TRIAL;
      font-weight: 400;

      &-san {
        display: flex;
        flex-direction: column;
        justify-content: center;
        margin-left: 15px;
      }
    }
  }

  &-list {
    margin-top: 50px;
    margin-left: 240px;
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
        font-family: Shentox TRIAL;
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
        font-family: Shentox TRIAL;
        font-weight: 400;
        line-height: 52px;
        /* padding-left: 18px; */
        cursor: pointer;
        /* border: 3px solid rgba(100, 134, 154, 1); */
      }

      &-idd2 {
        font-size: 30px;
        font-family: Shentox TRIAL;
        font-weight: 500;
        margin-left: 76px;
        min-width: 215px;
        text-align: left;
      }

      &-idd3 {
        font-size: 30px;
        font-family: Shentox TRIAL;
        font-weight: 500;
        margin-left: 90px;
        min-width: 207px;
        text-align: left;
      }

      &-idd4 {
        font-size: 30px;
        font-family: Shentox TRIAL;
        font-weight: 500;
        margin-left: 84px;
        text-align: left;
      }

      &-tran {
        // @include flex;
        display: flex;
        align-items: center;
        margin-left: 114px;
        text-align: left;
        width: 135px;
        min-width: 135px;

        &-iid {
          min-width: 36px;
          font-size: 30px;
          font-family: Shentox TRIAL;
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

.mar-ng2 {
  margin-left: 466px;
}

.mar-ng3 {
  margin-left: 163px;
}

.mar-ng4 {
  margin-left: 263px;
}

.mar-ng5 {
  margin-left: 273px;
}

@import "@/assets/scss/count.scss";
</style>
