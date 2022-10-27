<template>
  <div class="halloween">
    <div class="halloween-img-box" @click="onLink(card)">
      <img :src="card.image" alt="" />
    </div>
    <p class="halloween-title">Spark Era</p>
    <slot name="bottom"></slot>
  </div>
</template>

<script>
import constant from '../common/constant';
import card from '../mixin/card';

export default {
  props: {
    card: Object,
  },
  mixins: [card],
  data() {
    return {
      nftType: constant.cardType.lootbox,
      type: constant.cardType.lootbox,
    };
  },
  methods: {
    onLink(e) {
      this.$router.push({
        path: '/token',
        query: {
          id: e.tokenId || e.token_id,
          type: constant.cardType.lootbox,
        },
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.halloween {
  width: 255px;
  height: 321px;
  position: relative;
  display: block;
  margin-left: 38px;
  margin-bottom: 50px;
  background-size: 100% 100%;
  background-image: url(~@/assets/images/lootbox/bg.png);
  cursor: pointer;

  &-img-box {
    width: 180px;
    height: 205px;
    @include img;
    margin: 24px 37px 0 37px;
  }

  &-title {
    position: absolute;
    bottom: 50px;
    /* left: 30px; */
    width: 100%;
    padding: 0 10px;
    margin: 24px 0 10px 0;
    font-size: 16px;
    line-height: 1.5;
    color: #ffffff;
    background-color: #90aaf6;
    text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
  }

  &:nth-child(4),
  &:nth-child(5),
  &:nth-child(6) {
    margin-bottom: 0;
  }
}

.coinDiv {
  position: absolute;
  left: 30px;
  bottom: 20px;
  width: calc(100% - 30px);
  display: flex;
  align-items: center;
  justify-content: space-between;

  &-left {
    display: flex;
    align-items: center;
    justify-content: center;
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
    margin-left: 30px;

    img {
      width: 100%;
      height: 100%;
    }
  }

  .buynow {
    cursor: pointer;
    // margin-left: 18px;
    margin-right: 60px;
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
</style>
