<template>
  <div class="contentLeft">
    <transition-group name="list">
      <CustomFilter
        key="nftType"
        :options="nftTypeList"
        :column="1"
        :round="false"
        syncWithUrl="nftType"
        defaultValue="0"
        :showAll="false"
      />
      <CustomFilter
        v-if="nftTypeIs('card')"
        key="faction"
        :options="faction"
        syncWithUrl="faction"
        :title="true"
      />
      <!-- 飞船品质筛选 -->
      <CustomFilter
        v-if="nftTypeIs('spaceship')"
        key="spaceshipType"
        :options="spaceshipTypeList"
        syncWithUrl="rank"
        title="Rarity Attributes"
      />
      <!-- 装备品质筛选 -->
      <CustomFilter
        v-if="nftTypeIs('equipment')"
        key="equipType"
        :options="equipTypeList"
        syncWithUrl="rank"
        title="Rarity Attributes"
      />
      <!-- 盲盒类型 -->
      <CustomFilter
        v-if="nftTypeIs('lootbox')"
        key="lootBoxType"
        :options="lootBoxTypeList"
        syncWithUrl="lootBoxType"
        title="types"
      />
      <!-- pse类型 -->
      <CustomFilter
        v-if="nftTypeIs('pse')"
        key="pseType"
        :options="pseTypeList"
        syncWithUrl="type"
        title="types"
      />
      <!-- 宠物蛋类型 -->
      <CustomFilter
        v-if="nftTypeIs('egg')"
        key="eggType"
        :options="eggTypeList"
        syncWithUrl="type"
        title="types"
      />
      <!-- 稀有度 -->
      <CustomFilter
        v-if="nftTypeIs(['card',  'material'])"
        key="rarity"
        :options="rarity"
        syncWithUrl="rarity"
        title="Rarity Attributes"
      />
      <CustomFilter
        v-if="nftTypeIs('card')"
        key="profession"
        :options="profession"
        syncWithUrl="profession"
        title="Professional System"
      />
      <CustomFilter
        v-if="nftTypeIs('card')"
        key="identity"
        :options="identity"
        syncWithUrl="identity"
        title="Identity System"
      />
      <CardFilterLevels v-if="nftTypeIs(['card', 'spaceship'])" key="level" syncWithUrl="level" />
      <CardFilterBetween
        v-if="nftTypeIs('spaceship') && false"
        @change="onBetweenChange('Shield', $event)"
        key="spaceship shield"
        title="spaceship shield"
        :min="1"
        :max="100"
      />
      <template v-if="nftTypeIs('card')">
        <CardFilterBetween
          @change="onBetweenChange('Endurance', $event)"
          key="endurance"
          title="endurance"
          :min="1"
          :max="100"
        />
        <CardFilterBetween
          @change="onBetweenChange('Strength', $event)"
          key="strength"
          title="strength"
          :min="1"
          :max="100"
        />
        <CardFilterBetween
          @change="onBetweenChange('Agility', $event)"
          key="agility"
          title="agility"
          :min="1"
          :max="100"
        />
        <CardFilterBetween
          @change="onBetweenChange('Crit', $event)"
          key="crit"
          title="crit"
          :min="1"
          :max="100"
        />
        <CardFilterBetween
          @change="onBetweenChange('Psionic', $event)"
          key="psionic"
          title="psionic"
          :min="1"
          :max="100"
        />
        <CardFilterBetween
          @change="onBetweenChange('Spirit', $event)"
          key="spirit"
          title="spirit"
          :min="1"
          :max="100"
        />
        <CardFilterBetween
          @change="onBetweenChange('Power', $event)"
          key="power"
          title="power"
          :min="1"
          :max="400"
        />
      </template>
    </transition-group>
  </div>
</template>

<script>
import constant from "../common/constant"
import CustomFilter from "./CustomFilter.vue"
import CardFilterBetween from './CardFilterBetween';
import CardFilterLevels from './CardFilterLevels.vue';

export default {
  components: { CustomFilter, CardFilterBetween, CardFilterLevels },
  data() {
    return {
      // nftType: 0,
      nftTypeList: [
        { key: 'card', label: 'CARD' },
        // { key: 'halloween', label: 'Halloween Mesery Box' },
        // { key: 'egse', label: 'EGSE' },
        { key: 'spaceship', label: 'SPACESHIP' },
        { key: 'lootbox', label: 'LOOT BOX' },
        { key: 'equipment', label: 'equipment/weapon' },
        { key: 'material', label: 'Material' },
        { key: 'pse', label: 'PSE' },
        { key: 'egg', label: 'Egg' },
      ].map(item => ({ ...item, value: constant.cardType[item.key] })),
      faction: Object.keys(constant.camp).map(key => ({
        value: key, label: constant.camp[key]
      })),
      rarity: Object.keys(constant.rarity).map(key => ({
        value: key, label: constant.rarity[key]
      })),
      profession: Object.keys(constant.profession).map(key => ({
        value: key, label: constant.profession[key]
      })),
      identity: Object.keys(constant.identity).map(key => ({
        value: key, label: constant.identity[key]
      })),
      spaceshipTypeList: Object.keys(constant.spaceshipType).map(key => ({
        value: constant.spaceshipType[key], label: constant.spaceshipType[key]
      })),
      equipTypeList: Object.keys(constant.equipType).map(key => ({
        value: constant.equipType[key], label: constant.equipType[key]
      })),
      lootBoxTypeList: Object.keys(constant.lootBoxType).map(key => ({
        value: constant.lootBoxType[key], label: constant.lootBoxType[key]
      })),
      pseTypeList: Object.keys(constant.pseType).map(key => ({
        value: key, label: constant.pseType[key]
      })),
      eggTypeList: Object.keys(constant.eggType).map(key => ({
        value: key, label: constant.eggType[key]
      })),
    }
  },
  mounted() {
    // this.$watch(
    //   () => this.$route.query,
    //   () => {
    //     this.nftType = this.$route.query.nftType - 0
    //   }, {
    //   immediate: true
    // })
  },
  computed: {
    nftType() {
      return this.$route.query.nftType && (this.$route.query.nftType - 0) || 0
    },
    nftTypeIs() {
      return type => {
        if (typeof type === 'string' || typeof type === 'number') {
          return this.nftType === constant.cardType[type]
        } else if (Array.isArray(type)) {
          return type.some(t => this.nftType === constant.cardType[t])
        }
        // edge case
        return false
      }
    },
  },
  methods: {
    onChange({ key, value, name }) {
      // console.log('onChange', ...arguments);
      // switch (key) {
      //   case 'nftType':
      //     // this.$emit('toggle', value);
      //     this.nftType = value
      //     break;

      //   default:
      //     // this.$emit('selected', {
      //     //   type: key,
      //     //   value: value,
      //     //   name: name,
      //     // });
      //     break;
      // }
    },
    onBetweenChange(name, value) {
      // console.log('onBetweenChange', ...arguments);
      if (value.min && value.max) {
        // 变更vuex filter
        this.$store.commit('updateFilter', {
          name: name,
          value: '',
          text: `${name} ${value.min}-${value.max}`,
        })
        const { ...params } = this.$route.query
        delete params[name]
        const query = { ...params, [name]: [value.min, value.max].join() }
        this.$router.replace({
          path: this.$route.path,
          query,
        });
      }
      // this.pageInfo.pageNum = 1;
      // this.$emit('selected', {
      //   type: 'between',
      //   min: value.min,
      //   max: value.max,
      //   typeName: name,
      //   name: `${name}：${value.min} - ${value.max}`,
      // });
    },

    // todo: 待优化
    resetField(name) {
      if (name) {
        const { ...params } = this.$route.query
        delete params[name]
        const query = { ...params }
        this.$router.replace({
          path: this.$route.path,
          query,
        });
        // .catch(err => { err })
      }
    },
    resetFieldAll() {
      this.$router.replace({
        path: this.$route.path,
      });
    }
  },
}
</script>

<style lang="scss" scoped>
.contentLeft {
  width: 20vw;
  background-color: rgba($color: #293267, $alpha: 0.3);
  border: 3px solid rgba($color: #90aaf6, $alpha: 0.3);
  border-radius: 8px;
  padding: 36px;
}

.list-move, /* 对移动中的元素应用的过渡 */
.list-enter-active,
.list-leave-active {
  transition: all 200ms ease;
}

.list-enter-from,
.list-leave-to {
  opacity: 0;
  /* transform: translateX(30px); */
}

/* 确保将离开的元素从布局流中删除
  以便能够正确地计算移动的动画。 */
.list-leave-active {
  position: absolute;
}
</style>