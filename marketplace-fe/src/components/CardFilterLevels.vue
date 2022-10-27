<template>
  <div class="filter-box">
    <h4 class="title">{{ title }}</h4>
    <div class="select-box">
      <div
        v-for="(item) in itemLimits"
        :key="item"
        :class="{ 'select-item': true, active: current - 0 === item - 0 }"
        @click="onChange(item)"
      >{{ item }}</div>
    </div>
  </div>
</template>

<script>


export default {
  name: 'CardFilterLevels',
  props: {
    title: {
      type: String,
      default: "level"
    },
    syncWithUrl: {
      type: String,
      default: false
    },
  },
  data() {
    return {
      current: -1,
      itemLimits: process.env.VUE_APP_LEVEL_MAX - 0
    }
  },
  created() {
    this.$watch(() => this.$route.query,
      () => {
        this.current = this.$route.query[this.syncWithUrl] || this.default || -1
        if (this.current !== -1) {
          this.updateVuex(this.current)
        }
      }, { immediate: true }
    )
  },
  methods: {
    onChange(value) {
      this.current = value;

      // 变更url参数
      if (this.syncWithUrl) {
        const { ...params } = this.$route.query
        delete params[this.syncWithUrl]
        const query = { ...params }
        // 变更all时清空筛选条件
        if (value !== -1) {
          query[this.syncWithUrl] = value
        }
        this.$router.replace({
          path: this.$route.path,
          query,
        });
        // .catch(err => { err })
      }

      this.updateVuex(value)
    },
    updateVuex(value) {
      // 变更vuex filter
      let text = "Lv." + ("00" + value).slice(-2)
      this.$store.commit('updateFilter', {
        name: this.syncWithUrl,
        value,
        text,
      })
    }
  },
}
</script>

<style lang="scss" scoped>
.filter-box {
  &:not(:last-child) {
    margin-bottom: 15px;
  }
  .title {
    font-size: 24px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    line-height: 24px;
    text-transform: capitalize;
    margin-bottom: 12px;
    &::after {
      content: ":";
    }
  }

  .select-box {
    display: flex;
    flex-wrap: wrap;
    margin-right: -12px;
    margin-bottom: -12px;
    .select-item {
      &:not(:last-child) {
        margin-right: 12px;
        margin-bottom: 12px;
      }
      cursor: pointer;
      width: 40px;
      height: 40px;
      line-height: 40px;
      border-radius: 6px;
      text-align: center;
      font-size: 20px;
      font-weight: 400;
      text-transform: uppercase;
      color: #ffffff;
      transform: background 150ms;
      /* background-color: rgba($color: #27305d, $alpha: 0.3); */
      background-color: RGBA(52, 73, 143, 0.4);
      &:hover {
        /* background: linear-gradient(180deg, #07d4ff, #514483) !important; */
        background-color: RGBA(52, 73, 143, 0.7) !important;
      }
      &.active {
        /* background: linear-gradient(180deg, #07d4ff, #514483) !important; */
        background-color: RGBA(52, 73, 143, 1) !important;
      }
    }
  }
}
</style>