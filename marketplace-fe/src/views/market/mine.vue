<template>
  <div>
    <!-- <div class="contentItemCat">
      <div
        class="card"
        :class="flag === index ? 'spaceship' : ''"
        @click="btnClick(index)"
        v-for="(item, index) of btnList"
        :key="index"
      >
        {{ item }}
      </div>
    </div> -->
    <card/>
    <!-- <card v-if="flag === 0" /> -->
    <!-- <spaceship v-if="flag === 1" /> -->
  </div>
</template>

<script>
import card from './index';
// import spaceship from './spaceship/index';
// import xingjian from './xingjian';
export default {
  components: {
    card,
    // xingjian,
    // spaceship,
  },
  data() {
    return {
      // flag: 0,
      // btnList: ['CARD'],
    };
  },

  mounted() {},
  methods: {
    // btnClick(index) {
    //   this.flag = index;
    // },
  },
};
</script>
<style lang="scss" scoped>
.contentItemCat {
  display: flex;
  margin-top: 20px;

  .card {
    /* width: 178px; */
    /* height: 34px; */
    /* line-height: 34px; */
    padding: 10px 48px;
    text-align: center;
    font-size: 20px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    color: #ffffff;
    opacity: 1;
    margin-left: 30px;
    cursor: pointer;
    background-image: url(~@/assets/images/card-type-btn-repeat.png);
    @include bgc;
    background-repeat: repeat-y;
    border-radius: 10px;
    /* &:nth-child(2) {
      margin-left: 29px;
    } */
  }

  .spaceship {
    background-image: url(~@/assets/images/card-type-btn-actived-repeat.png);
  }
}
</style>
