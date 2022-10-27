<template>
  <div>
    <div class="contentItemCat">
      <div
        class="card"
        :class="flag === index ? 'spaceship' : ''"
        @click="btnClick(index)"
        v-for="(item, index) of btnList"
        :key="index"
      >{{ item }}</div>
    </div>

    <saleing v-if="flag === 0" />
    <warehouse v-if="flag === 1" />
    <collect v-if="flag === 2" />
  </div>
</template>

<script>
import saleing from "./list/saleing";
import warehouse from "./list/warehouse";
import collect from "./list/collect";

export default {
  components: {
    saleing,
    warehouse,
    collect,
  },
  data() {
    return {
      flag: 0,
      btnList: ["MY STORAGE", "SELLING NOW", "MY FAVORITE"],
    };
  },
  beforeMount() {
    if (this.$route.query.flag) {
      this.flag = this.$route.query.flag - 0
    }
  },
  mounted() {

  },
  methods: {
    btnClick(index) {
      this.flag = index;
      this.$router.replace({
        path: this.$route.path,
        query: {
          ...this.$route.query,
          flag: index
        }
      })
      // .catch(err => { err })
    },
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
    transition: background-image 300ms;
    /* &:nth-child(2) {
      margin-left: 29px;
    } */

    &:hover,
    &.spaceship {
      background-image: url(~@/assets/images/card-type-btn-actived-repeat.png);
    }
  }
}
</style>
