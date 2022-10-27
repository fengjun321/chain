<template>
  <div class="halloween">
    <div class="halloween-img-box">
      <img :src="card.image" alt="" />
    </div>
    <p class="halloween-title">{{ name }}</p>
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
      nftType: constant.cardType.pse,
      name: constant.pseType[this.card.type],
    };
  },
  created() {
    // console.log(this.card);
  },
};
</script>

<style lang="scss" scoped>
.halloween {
  width: 250px;
  height: 434px;
  position: relative;
  margin-left: 38px;
  display: block;
  margin-bottom: 44px;
  background-size: 100% 100%;
  background-image: url(~@/assets/images/psebg.png);
  cursor: pointer;

  &-img-box {
    width: 171px;
    height: 270px;
    @include img;
    margin: 26px 40px 0 40px;
  }

  &-title {
    position: absolute;
    bottom: 85px;
    left: 20px;
    font-size: 17px;
    color: #ffffff;
    text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
  }

  &:nth-child(11),
  &:nth-child(12),
  &:nth-child(13),
  &:nth-child(14),
  &:nth-child(15) {
    margin-bottom: 0;
  }
}

.coinDiv {
  position: absolute;
  left: 20px;
  bottom: 20px;
  width: calc(100% - 20px);
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
    font-family: Shentox TRIAL serif;
    font-weight: 500;
    line-height: 23px;
    color: #ffffff;
  }

  .coin_img {
    width: 29px;
    height: 28px;
    border-radius: 0px;
    margin-left: 5px;

    img {
      width: 100%;
      height: 100%;
    }
  }

  .buynow {
    cursor: pointer;
    // margin-left: 18px;
    margin-right: 40px;
    /* width: 117.89px; */
    height: 32.91px;
    padding: 0 10px;
    /* background-image: url(../assets/images/market/buynow.png);
    background-size: 100% 100%; */
    background: #34498f;
    border-radius: 6px;
    font-size: 16px;
    font-family: Shentox TRIAL serif;
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
    font-family: Shentox TRIAL serif;
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
    font-family: Shentox TRIAL serif;
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
