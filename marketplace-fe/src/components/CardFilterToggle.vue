<template>
  <div class="card-filter-toggle">
    <div class="card-filter-toggle-item">
      <span
        v-for="(item, index) in list"
        :key="index"
        @click="onTypeChange(cardTypes[item.key])"
        :class="{ active: current === cardTypes[item.key] }"
      >{{ item.label }}</span>
    </div>
  </div>
</template>

<script>
import constant from '../common/constant';

export default {
  name: 'CardFilterToggle',
  data() {
    return {
      cardTypes: constant.cardType,
      current: 0,
      list: [
        { key: 'card', label: 'CARD' },
        // { key: 'halloween', label: 'Halloween Mesery Box' },
        // { key: 'egse', label: 'EGSE' },
        { key: 'spaceship', label: 'SPACESHIP' },
        { key: 'lootbox', label: 'LOOT BOX' },
        { key: 'equipment', label: 'equipment/weapon' },
        { key: 'material', label: 'Material' },
        { key: 'pse', label: 'PSE' },
        { key: 'egg', label: 'Egg' },
      ],
    };
  },
  methods: {
    onTypeChange(value) {
      this.current = value;
      this.$emit('change', this.current);
      this.$router.replace({
        path: this.$route.path,
        query: {
          ...this.$route.query,
          current: value,
        },
      });
      // .catch(err => { err })
    },
  },
  created() {
    if (this.$route.query.current) {
      this.current = this.$route.query.current - 0;
      this.$emit('change', this.current);
    }
  },
};
</script>

<style lang="scss" scoped>
.card-filter-toggle {
  .card-filter-toggle-item {
    cursor: pointer;
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: space-between;
    margin-bottom: 25px;
    text-transform: uppercase;

    span {
      width: 100%;
      height: 40px;
      line-height: 40px;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      /* background: linear-gradient(
              180deg,
              rgba(7, 212, 255, 0.15),
              rgba(81, 68, 131, 0.15)
      ); */
      background-color: RGBA(52, 73, 143, 0.4);
      opacity: 1;
      text-align: center;
      /* border-radius: 6px; */
      font-size: 14px;
      font-family: Shentox TRIAL serif;
      font-weight: 400;
      color: #ffffff;
      margin-top: 12px;
      transition: background 150ms;

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
