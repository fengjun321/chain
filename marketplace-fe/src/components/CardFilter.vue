<template>
  <div class="contentItemLeft" v-show="visible || $store.state.isPc">
    <div class="card-filter-topbar">
      <span @click="clearAll">CLEAR ALL</span>
      <span @click="visible = false">DOWN</span>
    </div>
    <div class="leftsellist">
      <transition-group name="list" >
    
      <card-filter-toggle @change="onCardTypeChange" key="search-1" />
      <div v-if="currentCardType === cardTypes.card" class="left_selitem_box" key="search-2">
        <div class="left_selitem_tit">Faction:</div>
        <div class="left_selitem">
          <template v-for="ele in faction">
            <span
              @click="onFactionChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              transition="false"
              :disabled="!(ele.key == 1 || ele.key == 2 || ele.key == 3)"
            >
              <span @click="onFactionChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>

      <div v-if="currentCardType === cardTypes.pse" class="left_selitem_box"  key="search-3">
        <div class="left_selitem_tit">TYPE:</div>
        <div class="left_selitem">
          <template v-for="ele in pseType">
            <span
              @click="onPseChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              transition="false"
              disabled
            >
              <span @click="onPseChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>

      <div v-if="currentCardType === cardTypes.egse" class="left_selitem_box"  key="search-4">
        <div class="left_selitem_tit">TYPE:</div>
        <div class="left_selitem">
          <template v-for="ele in egse">
            <span
              @click="onEgseChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              transition="false"
              disabled
            >
              <span @click="onEgseChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>

      <div v-if="currentCardType === cardTypes.card || currentCardType === cardTypes.equipment || currentCardType === cardTypes.material" class="left_selitem_box"  key="search-5">
        <div class="left_selitem_tit">Rarity Attributes:</div>
        <div class="left_selitem">
          <template v-for="ele in quality">
            <span
              @click="onQualityChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              transition="false"
              disabled
            >
              <span @click="onQualityChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>

      <div v-if="currentCardType === cardTypes.equipment" class="left_selitem_box"  key="search-5">
        <div class="left_selitem_tit">Equipment Type:</div>
        <div class="left_selitem">
          <template v-for="ele in equipmentType">
            <span
              @click="onEquipmentChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              transition="false"
              disabled
            >
              <span @click="onEquipmentChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>

      <div v-if="currentCardType === cardTypes.card" class="left_selitem_box"  key="search-6">
        <div class="left_selitem_tit">Professional System:</div>
        <div class="left_selitem">
          <template v-for="ele in profession">
            <span
              @click="onProfessionChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              transition="false"
              disabled
            >
              <span @click="onProfessionChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>

      <div v-if="currentCardType === cardTypes.card" class="left_selitem_box"  key="search-7">
        <div class="left_selitem_tit">Identity System:</div>
        <div class="left_selitem">
          <template v-for="ele in identity">
            <span
              @click="onIdentityChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              :enterable="false"
              transition="false"
              :disabled="!(ele.key == 4)"
            >
              <span @click="onIdentityChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>

      <div v-if="currentCardType === cardTypes.card" class="left_selitem_box"  key="search-8">
        <div class="left_selitem_tit">Card Level:</div>
        <div class="left_selitem_level">
          <span
            @click="onLevelChange(ele)"
            v-for="ele in levels"
            :key="ele.level"
            :class="{ active: ele.selected }"
            >{{ ele.level }}</span
          >
        </div>
      </div>
      <!-- 飞船过滤条件 start -->
      <div
        v-if="currentCardType === cardTypes.spaceship"
        class="left_selitem_box"
         key="search-9"
      >
        <div class="left_selitem_tit">FACTION:</div>
        <div class="left_selitem">
          <template v-for="ele in spaceshipType">
            <span
              @click="onSpaceShipTypeChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              :enterable="false"
              transition="false"
              :disabled="!(ele.key == 4)"
            >
              <span @click="onSpaceShipTypeChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>
      <div
        v-if="currentCardType === cardTypes.spaceship"
        class="left_selitem_box"
         key="search-10"
      >
        <div class="left_selitem_tit">CARD LEVEL:</div>
        <div class="left_selitem_level">
          <span
            @click="onSpaceShipLevelChange(ele)"
            v-for="ele in spaceshipLevels"
            :key="ele.level"
            :class="{ active: ele.selected }"
            >{{ ele.level }}</span
          >
        </div>
      </div>
      <!-- 飞船过滤条件 end -->
      <!-- 盲盒 start -->
      <div
        v-if="currentCardType === cardTypes.lootbox"
        class="left_selitem_box"
         key="search-11"
      >
        <div class="left_selitem_tit">TYPE:</div>
        <div class="left_selitem">
          <template v-for="ele in lootBoxType">
              <span @click="onLootBoxTypeChange(ele)" :class="{ active: ele.selected }">{{
                ele.value
              }}</span>
          </template>
        </div>
      </div>
      <!-- 盲盒 end -->
      <!-- 材料 start -->
      <div
        v-if="currentCardType === cardTypes.material && false"
        class="left_selitem_box"
         key="search-12"
      >
        <div class="left_selitem_tit">TYPE:</div>
        <div class="left_selitem">
          <template v-for="ele in materialType">
            <span
              @click="onMaterialTypeChange(ele)"
              :key="ele.key"
              v-if="ele.key === -1"
              :class="{ active: ele.selected }"
              >ALL</span
            >
            <el-tooltip
              :key="ele.key"
              v-else
              class="item"
              :class="{ active: ele.selected }"
              effect="dark"
              :content="ele.value"
              placement="top-start"
              :enterable="false"
              transition="false"
              :disabled="!(ele.key == 4)"
            >
              <span @click="onMaterialTypeChange(ele)">{{
                ele.value
              }}</span>
            </el-tooltip>
          </template>
        </div>
      </div>
      <!-- 材料 end -->
      <div v-if="currentCardType === cardTypes.card" class="left_selitem_box"  key="search-13">
        <card-filter-between
          @change="onBetweenChange('endurance', $event)"
          title="endurance"
          :min="1"
          :max="100"
        />
        <card-filter-between
          @change="onBetweenChange('strength', $event)"
          title="strength"
          :min="1"
          :max="100"
        />
        <card-filter-between
          @change="onBetweenChange('agility', $event)"
          title="agility"
          :min="1"
          :max="100"
        />
        <card-filter-between
          @change="onBetweenChange('crit', $event)"
          title="crit"
          :min="1"
          :max="100"
        />
        <card-filter-between
          @change="onBetweenChange('psionic', $event)"
          title="psionic"
          :min="1"
          :max="100"
        />
        <card-filter-between
          @change="onBetweenChange('spirit', $event)"
          title="spirit"
          :min="1"
          :max="100"
        />
        <card-filter-between
          @change="onBetweenChange('power', $event)"
          title="power"
          :min="1"
          :max="400"
        />
      </div>
      </transition-group>
    </div>
  </div>
</template>

<script>
import constant from '../common/constant';
import CardFilterBetween from './CardFilterBetween';
import CardFilterToggle from './CardFilterToggle';

export default {
  name: 'CardFilter',
  components: {
    CardFilterBetween,
    CardFilterToggle,
  },
  data() {
    return {
      visible: false,
      faction: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ],
      quality: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ],
      profession: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ],
      identity: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ],
      levels: [],
      spaceshipType: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ], // 飞船类型
      spaceshipLevels: [], // 飞船等级
      lootBoxType: [{ key: -1, value: 'ALL', selected: true }], // 盲盒类型
      pseType: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ],
      egse: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ],
      materialType: [
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ], // 材料类型
      cardTypes: constant.cardType,
      currentCardType: constant.cardType.card,
      equipmentType:[
        {
          key: -1,
          value: 'ALL',
          selected: true,
        },
      ],
    };
  },
  created() {
    this.initData();
  },
  methods: {
    clearAll() {
      this.$emit('clear');
    },
    initData() {
      for (const ele in constant.camp) {
        // console.log(typeof ele);
        this.faction.push({
          key: ele,
          value: constant.camp[ele],
          selected: false,
        });
      }
      for (const ele in constant.rarity) {
        this.quality.push({
          key: ele,
          value: constant.rarity[ele],
          selected: false,
        });
      }
      for (const ele in constant.profession) {
        this.profession.push({
          key: ele,
          value: constant.profession[ele],
          selected: false,
        });
      }
      for (const ele in constant.identity) {
        this.identity.push({
          key: ele,
          value: constant.identity[ele],
          selected: false,
        });
      }
      for (const ele in constant.pseType) {
        this.pseType.push({
          key: ele,
          value: constant.pseType[ele],
          selected: false,
        });
      }
      for (const ele in constant.egseType) {
        this.egse.push({
          key: ele,
          value: constant.egseType[ele],
          selected: false,
        });
      }
      let level = parseInt(process.env.VUE_APP_LEVEL_MAX);
      for (let i = 1; i <= level; i++) {
        this.levels.push({
          level: i,
          selected: false,
        });
      }

      // 飞船模块初始化过滤条件
      for (const ele in constant.spaceshipType) {
        this.spaceshipType.push({
          key: ele,
          value: constant.spaceshipType[ele],
          selected: false,
        });
      }
      let spaceshipLevel = 10;
      for (let i = 1; i < spaceshipLevel; i++) {
        this.spaceshipLevels.push({
          level: i,
          selected: false,
        });
      }

      // 盲盒模块过滤条件加载
      for (const ele in constant.lootBoxType) {
        this.lootBoxType.push({
          key: ele,
          value: constant.lootBoxType[ele],
          selected: false,
        });
      }

      // 材料
      for (const ele in constant.materialType) {
        this.materialType.push({
          key: ele,
          value: constant.materialType[ele],
          selected: false,
        });
      }

      // 装备
      for (const ele in constant.equipmentType) {
        this.equipmentType.push({
          key: ele,
          value: constant.equipmentType[ele],
          selected: false,
        });
      }
    },
    onFactionChange(e) {
      this.faction.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'camp',
        value: e.key,
        name: e.value,
      });
    },
    onQualityChange(e) {
      this.quality.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'rarity',
        value: e.key,
        name: e.value,
      });
    },
    onEquipmentChange(e) {
      this.equipmentType.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'equipmentType',
        value: e.key,
        name: e.value,
      });
    },
    onProfessionChange(e) {
      this.profession.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'profession',
        value: e.key,
        name: e.value,
      });
    },
    onIdentityChange(e) {
      this.identity.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'identity',
        value: e.key,
        name: e.value,
      });
    },
    onLevelChange(e) {
      this.levels.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'level',
        value: e.level,
        name: 'LV：' + e.level,
      });
    },
    onPseChange(e) {
      this.pseType.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'pse',
        value: e.key,
        name: e.value,
      });
    },
    onEgseChange(e) {
      this.egse.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'egse',
        value: e.key,
        name: e.value,
      });
    },
    onSpaceShipLevelChange(e) {
      this.spaceshipLevels.forEach(ele => (ele.selected = false));
      e.selected = true;

      this.$emit('selected', {
        type: 'spaceshipLevel',
        value: e.level,
        name: 'LV：' + e.level,
      });
    },
    onSpaceShipTypeChange(e) {
      this.spaceshipType.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'spaceshipType',
        value: e.key,
        name: e.value,
      });
    },
    onLootBoxTypeChange(e) {
      this.lootBoxType.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'lootBoxType',
        value: e.value,
        name: e.value,
      });
    },
    onMaterialTypeChange(e) {
      this.materialType.forEach(ele => (ele.selected = false));
      e.selected = true;
      this.$emit('selected', {
        type: 'materialType',
        value: e.key,
        name: e.value,
      });
    },
    resetFieldAll() {
      this.levels.forEach(ele => (ele.selected = false));
      this.spaceshipType.forEach((ele, i) =>
        i ? (ele.selected = false) : (ele.selected = true)
      );
      this.spaceshipLevels.forEach(ele => (ele.selected = false));
      this.lootBoxType.forEach((ele, i) =>
        i ? (ele.selected = false) : (ele.selected = true)
      );
      this.materialType.forEach((ele, i) =>
        i ? (ele.selected = false) : (ele.selected = true)
      );
      this.faction.forEach(ele => (ele.selected = false));
      this.quality.forEach(ele => (ele.selected = false));
      this.profession.forEach(ele => (ele.selected = false));
      this.identity.forEach(ele => (ele.selected = false));
      this.faction[0].selected = true;
      this.quality[0].selected = true;
      this.profession[0].selected = true;
      this.identity[0].selected = true;
      this.pseType[0].selected = true;
    },
    resetField(name) {
      if (name === 'level') {
        this.levels.forEach(ele => (ele.selected = false));
      }
      if (name === 'camp') {
        this.faction.forEach(ele => (ele.selected = false));
        this.faction[0].selected = true;
      }
      if (name === 'rarity') {
        this.quality.forEach(ele => (ele.selected = false));
        this.quality[0].selected = true;
      }
      if (name === 'profession') {
        this.profession.forEach(ele => (ele.selected = false));
        this.profession[0].selected = true;
      }
      if (name === 'identity') {
        this.identity.forEach(ele => (ele.selected = false));
        this.identity[0].selected = true;
      }
      if (name === 'pse') {
        this.pseType.forEach(ele => (ele.selected = false));
        this.pseType[0].selected = true;
      }
      if (name === 'egse') {
        this.egse.forEach(ele => (ele.selected = false));
        this.egse[0].selected = true;
      }

      if (name === 'spaceshipLevel') {
        this.spaceshipLevels.forEach((ele, i) => (ele.selected = false));
      }

      if (name === 'spaceshipType') {
        this.spaceshipType.forEach((ele, i) =>
          i ? (ele.selected = false) : (ele.selected = true)
        );
      }
      if (name === 'lootBoxType') {
        this.lootBoxType.forEach((ele, i) =>
          i ? (ele.selected = false) : (ele.selected = true)
        );
      }
      if (name === 'materialType') {
        this.materialType.forEach((ele, i) =>
          i ? (ele.selected = false) : (ele.selected = true)
        );
      }
    },
    onBetweenChange(name, value) {
      this.$emit('selected', {
        type: 'between',
        min: value.min,
        max: value.max,
        typeName: name,
        name: `${name}：${value.min} - ${value.max}`,
      });
    },
    onCardTypeChange(value) {
      this.currentCardType = value;
      this.resetFieldAll();
      this.$emit('toggle', this.currentCardType);
    },
  },
};
</script>

<style lang="scss" scoped>
.contentItemLeft {
  width: 379.61px;
  border-radius: 11px;
  border: 3px solid rgba(144, 170, 246, 0.3);
  background-color: rgba(41, 50, 103, 0.3);

  .leftsellist {
    margin: 45px 35px;
    position: relative;

    .left_selitem_box {
      .left_selitem_tit {
        align-items: center;
        display: flex;
        font-size: 20px;
        font-family: Shentox TRIAL;
        font-weight: 400;
        color: #ffffff;
        margin-bottom: 5px;
      }

      .left_selitem {
        cursor: pointer;
        display: flex;
        flex-wrap: wrap;
        flex-direction: row;
        justify-content: space-between;
        margin-bottom: 25px;

        span {
          width: 140px;
          height: 40px;
          line-height: 40px;
          overflow: hidden;
          white-space: nowrap;
          text-overflow: ellipsis;
          text-transform: uppercase;
          /* background: linear-gradient(
                  180deg,
                  rgba(7, 212, 255, 0.15),
                  rgba(81, 68, 131, 0.15)
          ); */
          background-color: RGBA(52, 73, 143, 0.4);
          transition: background 150ms;
          opacity: 1;
          text-align: center;
          /* border-radius: 6px; */
          font-size: 14px;
          font-family: Shentox TRIAL;
          font-weight: 400;
          color: #ffffff;
          margin-top: 12px;

          &:nth-child(even) {
            margin-left: 14px;
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

      .left_selitem_level {
        display: flex;
        flex-wrap: wrap;
        flex-direction: row;
        /* justify-content: space-between;*/
        margin-bottom: 25px;

        span {
          cursor: pointer;
          /*flex: 1;*/
          width: 40px;
          height: 40px;
          line-height: 40px;

          /* background: linear-gradient(
                  180deg,
                  rgba(7, 212, 255, 0.15),
                  rgba(81, 68, 131, 0.15)
          ); */
          background-color: RGBA(52, 73, 143, 0.4);
          opacity: 1;
          text-align: center;
          border-radius: 6px;
          font-size: 14px;
          font-family: Shentox TRIAL;
          font-weight: 400;
          color: #ffffff;
          margin-top: 12px;
          margin-left: 20px;

          &:nth-child(1) {
            margin-left: 0;
          }

          &:nth-child(6) {
            margin-left: 0;
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

      .left_power {
        .left_power_item {
          display: flex;
          align-items: center;
          margin-top: 10px;
          margin-bottom: 15px;

          .inp {
            display: flex;
            align-items: center;
            width: 122.78px;
            height: 33.13px;
            background-image: url('../assets/images/market/inputbg.png');
            background-size: 100% 100%;

            img {
              width: 1.89px;
              height: 16.02px;
              margin-left: 13px;
            }

            input {
              width: 100px;
              outline: none;
              background: none;
              border: none;
              text-align: center;
            }
          }

          .inpbr {
            width: 16.62px;
            height: 4.89px;
            margin: 0 21px;
          }

          span {
            font-size: 19px;
            font-family: Shentox TRIAL;
            font-weight: 500;
            line-height: 0;
            color: #ffffff;
          }

          img {
            width: 20px;
            height: 21px;
          }
        }
      }
    }
  }
}

.card-filter-topbar {
  display: none;
}
@media screen and (max-width: 768px) {
  .card-filter-topbar {
    display: flex;
    justify-content: space-between;
    border-bottom: 2px solid #062c46;
    padding: 0 0.6rem;
    span {
      cursor: pointer;
      font-size: 0.4rem;
      height: 1rem;
      line-height: 1rem;
    }
  }
  .contentItemLeft {
    position: fixed;
    top: 0;
    left: 0;
    width: 100vw !important;
    height: 100vh;
    z-index: 2;
    background: rgba(0, 0, 0, 0.4);
    backdrop-filter: blur(0.4rem);
    border: none !important;
    z-index: 1000;
    border-radius: 0 !important;
    .leftsellist {
      margin: 0;
      padding: 0.2rem 0.6rem;
      overflow-y: auto;
      height: calc(100vh - 1rem);
      .left_selitem_box {
        .left_selitem_tit {
          font-size: 0.4rem;
          margin-bottom: 0.2rem;
        }
        .left_selitem {
          > span {
            width: 3rem;
            height: 0.65rem;
            line-height: 0.65rem;
            margin-top: 0.24rem;
            font-size: 0.3rem;
            margin-left: 0 !important;
          }
        }
        .left_selitem_level {
          margin-bottom: 0.5rem;
          justify-content: space-around;
          span {
            font-size: 0.3rem;
            width: 0.66rem;
            height: 0.66rem;
            margin-top: 0;
            margin-left: 0;
          }
        }
      }
    }
  }
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
