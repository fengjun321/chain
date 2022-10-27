<template>
  <div
    class="card"
    :style="{
      backgroundImage:
        'url(' +
        require('../assets/images/market' + constant.rarityImage[card.rarity]) +
        ')',
    }"
  >
    <span class="number">LV:{{ card.level }}</span>
    <div v-if="card.isFavorite" class="collect">
      <img src="../assets/images/collect.png" />
    </div>
    <div class="imgDiv" @click="onLink(card)">
      <img :src="card.image" />
    </div>
    <span class="card_title">{{ constant.profession[card.occupation] }}</span>
    <span class="card_val">{{ card.power }}</span>
    <div class="card_info">
      <ul>
        <li>
          <span>END:</span>
          <card-attribute-color :number="card.endurance" />
        </li>
        <li style="text-align: center">
          <span>STR:</span>
          <card-attribute-color :number="card.strength" />
        </li>
        <li>
          <span>AGI:</span>
          <card-attribute-color :number="card.agility" />
        </li>
        <li>
          <span>ARM:</span>
          <card-attribute-color :number="card.armor" />
        </li>
        <li style="text-align: center">
          <span>CRIT:</span>
          <card-attribute-color :number="card.crit" />
        </li>
        <li>
          <span>PSI:</span>
          <card-attribute-color :number="card.psionic" />
        </li>
        <li>
          <span>SPI:</span>
          <card-attribute-color :number="card.spirit" />
        </li>
        <li>
          <span>TOT:</span>
          {{ card.totalAttrs }}
        </li>
      </ul>
    </div>
    <slot name="bottom"></slot>
  </div>
</template>

<script>
import constant from '../common/constant';
import CardAttributeColor from './CardAttributeColor';
import card from '../mixin/card';

export default {
  name: 'Card',
  props: {
    card: Object,
  },
  mixins: [card],
  components: {
    CardAttributeColor,
  },
  methods: {
    onLink(e) {
      this.$router.push({
        path: '/token',
        query: {
          id: e.tokenId,
          type: constant.cardType.card,
        },
      });
    },
  },
  data() {
    return {
      nftType: constant.cardType.card,
      constant: constant,
    };
  },
};
</script>

<style lang="scss" scoped>
.card {
  width: 250.25px;
  height: 478.87px;
  background-size: 100% 100%;
  position: relative;
  margin-left: 38px;
  margin-bottom: 50px;
  display: block;

  &:nth-child(11),
  &:nth-child(12),
  &:nth-child(13),
  &:nth-child(14),
  &:nth-child(15) {
    margin-bottom: 0;
  }

  .number {
    font-size: 22px;
    font-family: Shentox TRIAL;
    font-weight: bold;
    color: #b6b6b6;
    position: absolute;
    left: 9px;
    top: 4px;
    font-style: oblique;
  }

  .imgDiv {
    width: 205px;
    height: 290.61px;
    position: absolute;
    left: 24px;
    top: 17px;
  }

  .imgDiv img {
    width: 100%;
    height: 100%;
    cursor: pointer;
  }

  .imgDiv img:hover {
    // transform: scale(1.1);
  }

  .card_title {
    font-size: 17px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    line-height: 0px;
    color: #ffffff;
    text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
    position: absolute;
    left: 7px;
    top: 338px;
  }

  .card_val {
    font-size: 38px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    line-height: 0px;
    color: #ffffff;
    text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
    position: absolute;
    left: 169px;
    top: 328px;
  }

  .card_info {
    position: absolute;
    left: 18px;
    top: 361px;
    width: 212.25px;

    ul {
      width: 100%;
      flex-wrap: wrap;
      flex-direction: row;
      display: flex;
      justify-content: space-between;

      li {
        list-style: none;
        font-size: 12px;
        font-family: Shentox TRIAL;
        font-weight: 500;
        color: #ffffff;
        margin-right: 5%;
        width: 30%;
        line-height: 16px;
        padding: 2px 0;

        &:nth-child(3n + 0) {
          margin-right: 0px;
          text-align: right;
        }

        &:nth-child(8) {
          margin-right: 0px;
          width: 50%;
          text-align: right;
        }

        span {
          color: #b6b6b6;
        }
      }
    }
  }

  .coinDiv {
    position: absolute;
    left: 15px;
    bottom: 20px;
    width: 212.25px;
    display: flex;
    align-items: center;
    justify-content: space-between;

    > div {
      width: 90%;
    }

    span {
      font-size: 28px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      line-height: 23px;
      color: #ffffff;
    }

    .coin_img {
      width: 29px;
      height: 28px;
      border-radius: 0px;
      margin-left: 10px;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .buynow {
      cursor: pointer;
      margin-left: 18px;
      /* width: 117.89px; */
      height: 32.91px;
      padding: 0 10px;
      /* background-image: url(../assets/images/market/buynow.png);
      background-size: 100% 100%; */
      background: #34498f;
      border-radius: 6px;
      font-size: 16px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      color: #ffffff;
      text-align: center;
      line-height: 34.91px;
    }

    .sell {
      height: 32.91px;
      background-image: url(../assets/images/market/buynow.png);
      background-size: 100% 100%;
      font-size: 14px;
      font-family: Shentox TRIAL;
      cursor: pointer;
      font-weight: 500;
      color: #ffffff;
      text-align: center;
      line-height: 30.91px;
      padding: 0 10px;
      width: 80px;
    }

    .quick_sale {
      height: 32.91px;
      background-image: url(../assets/images/market/buynow.png);
      background-size: 100% 100%;
      font-size: 14px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      color: #ffffff;
      text-align: center;
      cursor: pointer;
      line-height: 30.91px;
      padding: 0 4px;
      flex: 1;
      margin-left: 6px;
    }
  }

  .collect {
    position: absolute;
    right: 11px;
    top: 10px;
    width: 22.91px;
    height: 20.11px;

    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
