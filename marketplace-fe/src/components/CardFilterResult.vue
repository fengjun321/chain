<template>
  <div class="card-filter-result">
    <div v-for="ele in filter" class="selitem">
      <span>{{ ele.text }}</span>
      <img @click="onDelete(ele)" class="del" src="../assets/images/market/del.png" />
    </div>
    <div v-if="filter.length > 0" @click="onClear()" class="clearall">
      <span>CLEAR ALL</span>
      <img class="clearall_inco" src="../assets/images/market/clearall4k.png" />
    </div>
  </div>
</template>

<script>
export default {
  name: "CardFilterResult",
  props: {
    // filter: Array,
  },
  computed: {
    filter() {
      return this.$store.state.filter.filter(item => !['nftType', 'flag'].includes(item.name))
      // const f = 
      // const object = this.$route.query
      // for (const key in object) {
      //   if (Object.hasOwnProperty.call(object, key)) {
      //     const element = object[key];
      //     // 跳过部分筛选条件
      //     if (['nftType', 'flag'].includes(key)) {
      //       continue
      //     }
      //     // 转换显示内容
      //     if (element && element.includes(',')) {
      //       const [min, max] = element.split(',');
      //       f.push({ name: key, value: `${min}-${max}` })
      //     } else if (key === 'level') {
      //     } else {
      //       // f.push({ name: key })
      //     }
      //   }
      // }
      return f
    }
  },
  methods: {
    onDelete(e) {
      console.log('onDelete', e);
      // this.$emit("delete", e);
      this.$store.commit('removeFilter', { name: e.name })
      const { ...params } = this.$route.query
      e.name && delete params[e.name]
      const query = { ...params }
      this.$router.replace({
        path: this.$route.path,
        query,
      });
    },
    onClear() {
      // this.$emit("clear");
      this.$store.commit('clearFilter')
      // this.$router.replace({
      //   path: this.$route.path,
      // });
      const query = { nftType: this.$route.query.nftType, flag: this.$route.query.flag }
      this.$router.replace({
        path: this.$route.path,
        query,
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.card-filter-result {
  display: flex;

  .selitem {
    cursor: pointer;
    padding: 5px 13px 5px 13px;
    /* background: linear-gradient(180deg, #07d4ff 0%, #514483 100%); */
    background-color: RGBA(52, 73, 143, 1);
    opacity: 1;
    text-align: center;
    border-radius: 6px;
    font-size: 20px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    line-height: 20px;
    color: #ffffff;
    margin-right: 22px;
    text-transform: uppercase;

    .del {
      margin-left: 22px;
      width: 13px;
      height: 13px;
    }
  }

  .clearall {
    cursor: pointer;
    padding: 5px 13px 5px 13px;
    background: linear-gradient(
      180deg,
      rgba(7, 212, 255, 0.15),
      rgba(81, 68, 131, 0.15)
    );
    display: flex;
    align-items: center;
    opacity: 1;
    text-align: center;
    border-radius: 6px;
    font-size: 20px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    color: #ffffff;
    margin-right: 22px;

    .clearall_inco {
      margin-left: 22px;
      width: 20px;
      height: 20px;
    }
  }
}
</style>
