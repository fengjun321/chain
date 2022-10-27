<template>
  <div class="filter-box">
    <h4 class="title" v-if="title">{{ title === true ? syncWithUrl : title }}</h4>
    <el-row :gutter="10" class="filter-box">
      <el-col :span="24 / column" v-for="(item, index) in optionsFilter" :key="index">
        <el-tooltip
          effect="dark"
          :content="item.label"
          placement="top"
          v-if="item.label.length > 10"
        >
          <div
            :class="{ 'filter-item': true, round, active: current == item.value }"
            @click="onChange(item.value)"
          >
            {{ item.label }}
            <!-- {{ item.label.split(' ')[0] }} -->
          </div>
        </el-tooltip>
        <div
          :class="{ 'filter-item': true, round, active: current == item.value }"
          @click="onChange(item.value)"
          v-else
        >{{ item.label }}</div>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  props: {
    options: {
      type: Array,
      default: () => []
    },
    column: {
      type: Number,
      default: 2
    },
    round: {
      type: Boolean,
      default: true
    },
    syncWithUrl: {
      type: String,
      default: ''
    },
    title: [Boolean | String],
    showAll: {
      type: Boolean,
      default: true
    },
    defaultValue: {
      type: String,
      default: ''
    }
  },
  computed: {
    optionsFilter() {
      if (this.showAll) {
        return [{ label: "All", value: -1, }, ...this.options]
      }
      return this.options
    }
  },
  data() {
    return {
      current: this.defaultValue || -1
    }
  },
  created() {
    this.$watch(() => this.$route.query,
      () => {
        this.current = this.$route.query[this.syncWithUrl] || this.defaultValue || -1
        if(this.current !== -1){
          this.updateVuex()
        }
      }, { immediate: true }
    )
    // if (this.$route.query[this.syncWithUrl]) {
    //   this.current = this.$route.query[this.syncWithUrl] - 0;
    //   // this.$emit('change', this.syncWithUrl, this.current);
    //   // const target = this.options.find(item => item.value === this.current)
    //   // this.$emit('change', { key: this.syncWithUrl, value: this.current, name: target && target.label || this.current });
    // }
  },
  methods: {
    onChange(value) {
      this.current = value;

      // this.$emit('change', this.current);
      // this.$emit('change', this.syncWithUrl, this.current);
      // const target = this.options.find(item => item.value === this.current)
      // this.$emit('change', { key: this.syncWithUrl, value: this.current, name: target && target.label || this.current });

      // 变更url参数
      if (this.syncWithUrl) {
        const { ...params } = this.$route.query
        delete params[this.syncWithUrl]
        const query = { ...params }
        // 变更all时清空筛选条件
        if (value !== -1) {
          query[this.syncWithUrl] = value
        }
        if (this.syncWithUrl === 'nftType') {
          //  变更nftType时，清空其他筛选条件
          this.$store.commit('clearFilter')
          const query = { nftType: this.current, flag: this.$route.query.flag }
          this.$router.replace({
            path: this.$route.path,
            query,
          });
        } else {
          this.updateVuex()
          this.$router.replace({
            path: this.$route.path,
            query,
          });
        }
        // .catch(err => { err })
      }

    },
    updateVuex() {
      // 变更vuex filter
      let text = ''
      const target = this.options.find(item => item.value === this.current)
      // console.log('target',target);
      if (!target) return
      text = target.label
      this.$store.commit('updateFilter', {
        name: this.syncWithUrl,
        value: this.current,
        text,
      })
    },

    cardLevel(level = 0) {
      return "Lv." + ("00" + level).slice(-2)
    },
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
  .filter-item {
    height: 40px;
    line-height: 40px;
    padding: 0 12px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    background-color: RGBA(52, 73, 143, 0.4);
    opacity: 1;
    text-align: center;
    font-size: 14px;
    font-family: Shentox TRIAL serif;
    font-weight: 400;
    text-transform: uppercase;
    color: #ffffff;
    margin-bottom: 12px;
    transition: background 150ms;
    cursor: pointer;
    &.round {
      border-radius: 6px;
    }
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
</style>