<template>
  <div :class="`card-custom ${rarityName} ${nftTypeName}`">
    <div class="head">
      <span class="level" v-if="nftTypeIs('card')">{{ cardLevel }}</span>
      <img class="collect" src="../assets/images/collect.png" v-if="model === 'market' && card.isFavorite" />
    </div>
    <div class="spaceship-infos" v-if="nftTypeIs('spaceship')">
      <div class="left" v-loading="loadingQuark" element-loading-background="rgba(0, 0, 0, 0.3)">
        <span class="label">Mined Tokens</span>
        <span>
          <img src="@/assets/images/mined-tokens-1.png" />
          {{ ss.Quark }}/300000
        </span>
        <span>
          <img src="@/assets/images/mined-tokens-2.png" />
          {{ ss.Fire }}/50000
        </span>
      </div>
      <div
        class="right"
        v-loading="loadingGrindPower"
        element-loading-background="rgba(0, 0, 0, 0.3)"
      >
        <span class="label">Spaceship shield</span>
        <span class="shield" :style="shieldFontColor(ss.GrindPower)">{{ ss.GrindPower }}</span>
      </div>
    </div>
    <div class="image" @click="gotoDetail">
      <img :src="card.image" :style="nftTypeIs('lootbox') ? 'width:75%' : ''" />
    </div>
    <div :class="{ title: true, 'no-power': !showPower }">
      <span ref="$cardTitle">{{ cardTitle }}</span>
      <div class="color-block"></div>
      <span ref="$power" class="power">{{ card.power }}</span>
      <div class="ship-title" v-if="nftTypeIs('spaceship')">
        <div class="name">{{ card.ship_name }}</div>
        <div class="level">{{ cardLevel }} {{ card.rank }}</div>
      </div>
    </div>
    <div class="description hero-card" v-if="nftTypeIs('card')">
      <div class="item" v-for="(item, index) in heroCardDescList" :key="item.label">
        {{ item.label }}:
        <span class="value" :style="heroCardAttributeColor(item)">{{ item.value }}</span>
      </div>
    </div>
    <div class="description spaceship" v-if="nftTypeIs('spaceship')">
      <ul>
        <li v-if="card.commander_current">
          <img src="../assets/images/spaceship/zhihuiguan.png" alt />
          <div>X&nbsp;{{ card.commander_current }}</div>
        </li>
        <li v-if="card.scientist_current">
          <img src="../assets/images/spaceship/lingdaozhe.png" alt />
          <div>X&nbsp;{{ card.scientist_current }}</div>
        </li>
        <li v-if="card.start_soldier_current">
          <img src="../assets/images/spaceship/xingjizhanshi.png" alt />
          <div>X&nbsp;{{ card.start_soldier_current }}</div>
        </li>
        <li v-if="card.common_soldier_current">
          <img src="../assets/images/spaceship/putongzhanshi.png" alt />
          <div>X&nbsp;{{ card.common_soldier_current }}</div>
        </li>
        <li style="height: 20px; width:20px;"></li>
      </ul>
    </div>
    <div class="foot">
      <div class="price" v-if="model !== 'sale' || card.isSelling">
        <span>{{ card.price }}</span>
        <img src="@/assets/images/market/priceicon4k.png" />
      </div>

      <template v-if="model === 'market'">
        <div class="btn cancel" @click="handleCancel" v-if="card.isOnwer">cancel</div>
        <div class="btn" @click="gotoDetail" v-else>buy now</div>
      </template>
      <template v-if="model === 'sale'">
        <div class="btn cancel" @click="handleCancel" v-if="card.isSelling">cancel</div>
        <div class="btn sell" @click="handleSell" v-else>sell</div>
      </template>
      <template v-if="model === 'collect'">
        <div class="btn" @click="gotoDetail" v-if="card.isSale">buy now</div>
      </template>
      <template v-if="model === 'warehouse'">
        <div class="btn cancel" @click="handleCancel">cancel</div>
      </template>
      <!-- <div class="btn cancel" @click="handleCancel" v-if="showBtnCancel">cancel</div> -->
      <!-- <div class="btn sell" @click="gotoDetail" v-if="showBtnSell">sell</div> -->
      <!-- <div class="btn" @click="gotoDetail" v-if="showBtnBuy">buy now</div> -->
    </div>
  </div>
</template>

<script>
import constant from '@/common/constant';
// import card from '../mixin/card';
// import Web3 from 'web3';

export default {
  name: "CardCustom",
  props: {
    card: {
      type: Object,
      default: () => { },
      require: true
    },
    nftType: {
      type: [String, Number],
      default: 0,
      require: true
    },
    model: String
  },
  data() {
    return {
      ss: {
        Quark: 0,
        Fire: 0,
        GrindPower: 100,
      },
      loadingQuark: true,
      loadingGrindPower: true,
    }
  },
  computed: {
    shieldFontColor() {
      return n => {
        if (n >= 90) {
          return { color: '#27d371' }
        } else if (n >= 60) {
          return { color: '#d3b627' }
        } else if (n >= 40) {
          return { color: '#d37227' }
        }
        return { color: '#d32727' }
      }
    },
    showPower() {
      return this.nftTypeIs(['card', 'equipment', 'spaceship'])
    },
    nftTypeIs() {
      //   card: 0,
      //   halloween: 1,
      //   pse: 2,
      //   egse: 3,
      //   spaceship: 4,
      //   lootbox: 6,
      //   material: 7,
      //   egg: 5,
      //   equipment: 9,
      //   weapon: 10,
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
    cardLevel() {
      const level = this.card.level || 0
      return "Lv." + ("00" + level).slice(-2)
    },
    rarityName() {
      if (this.nftTypeIs('spaceship')) {
        return this.card.rank
      }

      const map = { 0: 'common', 1: 'rare', 2: 'epic', 3: 'legend', }
      return map[this.card.rarity]
    },
    heroCardDescList() {
      const list = ['endurance', 'strength', 'agility', 'armor', 'crit', 'psionic', 'spirit', 'totalAttrs']
      return list.map(label => ({ label: label.slice(0, 3), value: this.card[label] }))
    },
    heroCardAttributeColor() {
      return item => {
        if (item.label === 'tot') return {}
        else if (item.value > 0 && item.value <= 30) return {
          color: '#FFFFFF'
        }
        else if (item.value > 30 && item.value <= 60) return {
          color: '#1C86EF'
        }
        else if (item.value > 60 && item.value <= 85) return {
          color: '#9c07ff'
        }
        else return {
          color: '#FF8000'
        }
      }
    },
    cardTitle() {
      if (this.nftTypeIs('card')) {
        return constant.profession[this.card.occupation]
      } else if (this.nftTypeIs('spaceship')) {
        return 'COMPUTING POWER'
      } else if (this.nftTypeIs('pse')) {
        return this.card.attrType
      } else if (this.nftTypeIs('equipment')) {
        return this.card.arms_name
      } else if (this.nftTypeIs('lootbox')) {
        return this.card.type
      } else if (this.nftTypeIs('egg')) {
        return this.card.type
      }

      // edge case
      return this.card.name || 'nameless'
    },
    nftTypeName() {
      const target = Object.keys(constant.cardType).find(key => constant.cardType[key] === this.nftType)
      return target || ''
    }
  },
  methods: {
    getQuarkAndFire() {
      if (!this.nftTypeIs('spaceship')) return
      let _web3 = new Web3(window.web3.currentProvider);
      this.loadingQuark = true
      this.$IsTarship.methods.queryStarShipMinedQuarkAndFireAndDurability(this.card.token_id).call({
        from: this.$store.state.address
      }).then(data => {
        // console.log(data);
        let Q = _web3.utils.fromWei(data[0], 'ether');
        Q = Math.floor(Q * 100) / 100;
        let F = _web3.utils.fromWei(data[1], 'ether');
        F = Math.floor(F * 100) / 100;
        // console.log(Q, F);
        this.ss.Quark = Q
        this.ss.Fire = F
      }).finally(() => {
        this.loadingQuark = false
      })


      this.loadingGrindPower = true
      this.$IsTarship.methods.queryDuration(this.card.token_id).call({
        from: this.$store.state.address
      }).then(data => {
        let Grind = Math.round(data / 100);
        // console.log('Grind',Grind);
        this.ss.GrindPower = Grind;
      }).finally(() => {
        this.loadingGrindPower = false
      })
    },
    gotoDetail() {
      let id = this.card.tokenId || this.card.token_id
      const type = this.nftType
      const query= { id, type }
      
      // 单独处理material
      let goodId =  this.card.good_id 
      let owner =  this.card.owner_address 
      if(this.nftTypeIs('material')){
        query.id  = this.card.id
        if(this.card.isSelling && goodId ){
          query.goodId = goodId
          query.owner = owner
        }
      }
      this.$router.push({
        path: "/token",
        query,
      });
    },
    handleCancel() {
      this.$emit('cancel')
    },
    handleSell() {
      this.gotoDetail()
      // this.$emit('sell')
    }
  },
  created() {
    this.getQuarkAndFire()
  },
  mounted() {
    const titleElem = this.$refs.$cardTitle
    const powerElem = this.$refs.$power
    // console.log(powerElem);
    const titleWidth = titleElem.offsetWidth
    let targetWidth = powerElem.offsetLeft || titleElem.parentElement.offsetWidth
    // 去除两个padding
    targetWidth -= 24
    // console.log(titleWidth, targetWidth);
    if (titleWidth > targetWidth) {
      const scale = titleWidth / targetWidth
      const transx = (titleWidth - targetWidth) / 2
      titleElem.style.transform = `scale(${1 / scale}) translateX(-${transx}px)`
      titleElem.style.display = `block`
    }
  },
}
</script>


<style lang="scss" scoped>
.card-custom {
  background-color: rgba($color: #282f65, $alpha: 0.3);
  border: 0.08rem solid rgba($color: #8ea9f5, $alpha: 0.3);
  border-radius: 10px;
  margin-left: 24px;
  margin-bottom: 24px;
  overflow: hidden;
  /* width: 16%; */
  width: 13vw;
  position: relative;
  &.spaceship {
    width: 23vw;
  }
  .spaceship-infos {
    position: relative;
    height: 0;
    .label {
      text-transform: uppercase;
    }
    span {
      height: 1.5;
      img {
        width: 16px;
        height: 16px;
        object-fit: contain;
      }
    }
    .left,
    .right {
      position: absolute;
      top: 12px;
      padding: 12px;
      display: flex;
      flex-direction: column;
    }
    .left {
      left: 12px;
      border-radius: 6px;
      background-color: rgba($color: #384969, $alpha: 0.25);
    }
    .right {
      right: 12px;
      align-items: flex-end;
      .shield {
        font-size: 48px;
        font-weight: bolder;
        /* color: #27d371;
        color: #d3b627;
        color: #d37227;
        color: #d32727; */
      }
    }
  }
  .head {
    padding: 6px 12px 0;
    position: relative;
    .level {
      font-weight: bolder;
      font-style: italic;
      // text-transform: capitalize;
      font-size: 12px;
      color: #b6b6b6;
    }
    img.collect {
      position: absolute;
      top: 6px;
      right: 6px;
      width: 22px;
      height: 22px;
      object-fit: contain;
    }
  }
  .image {
    /* padding: 12px; */
    cursor: pointer;
    text-align: center;
    img {
      width: 100%;
      /* width: 13vw; */
      height: 16vw;
      object-fit: contain;
    }
  }
  .title {
    margin-top: 20px;
    background-color: rgba($color: #8ea9f5, $alpha: 0.3);
    line-height: 30px;
    font-size: 18px;
    text-transform: capitalize;
    position: relative;
    padding: 0 12px;
    white-space: nowrap;
    &.no-power {
      margin-top: 0;
      .power,
      .color-block {
        display: none;
      }
    }
    .ship-title {
      position: absolute;
      left: 0;
      bottom: 30px;
      .name {
        padding: 0 12px;
        font-family: Shentox TRIAL;
        font-size: 24px;
        line-height: 24px;
        font-weight: bold;
      }
      .level {
        padding: 0 12px;
        line-height: 30px;
        font-size: 18px;
        color: #8696c0;
      }
    }
    .power {
      position: absolute;
      bottom: 0;
      right: 20px;
      width: 80px;
      line-height: 46px;
      font-size: 36px;
      /* background-color: orange; */
      text-align: center;
    }
    .color-block {
      content: "";
      border: 20px solid transparent;
      border-bottom-color: rgba($color: #8ea9f5, $alpha: 0.3);
      border-right-width: 10px;
      border-left-width: 15px;
      border-top-width: 0;
      width: 80px;
      position: absolute;
      box-sizing: content-box;
      bottom: 30px;
      right: 10px;
    }
  }
  .description {
    /* width: 13vw; */
    padding: 12px;
    padding-bottom: 0;
    &.hero-card {
      display: flex;
      flex-wrap: wrap;
      .item {
        display: inline-block;
        margin-right: 24px;
        color: rgba(182, 182, 182, 1);
        text-transform: uppercase;
        line-height: 1.5;
        .value {
          color: #fff;
        }
      }
    }

    &.spaceship {
      ul {
        width: 100%;
        font-size: 34px;
        display: flex;
        justify-content: space-between;
        li {
          display: flex;
          align-items: center;
          margin-right: 12px;
          img {
            width: 20px;
            height: 27px;
          }
          div {
            margin: 5px 0 0 5px;
            color: #6878b3;
            font-size: 18px;
            // font-family: "fangsong" !important;
          }
        }
      }
    }
  }
  .foot {
    padding: 20px 12px;
    display: flex;
    flex-wrap: nowrap;

    .price {
      display: flex;
      align-items: center;
      font-size: 30px;
      line-height: 30px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      min-width: 50%;
      img {
        width: 28px;
        height: 28px;
        margin-left: 8px;
        margin-right: 8px;
        object-fit: contain;
      }
    }
    .btn {
      flex: 1;
      height: 32px;
      // padding: 0px 12px;
      white-space: nowrap;
      line-height: 32px;
      background-color: RGBA(52, 73, 143, 1);
      border-radius: 6px;
      font-size: 16px;
      text-transform: uppercase;
      text-align: center;
      user-select: none;
      cursor: pointer;
      transition: background 150ms;
      &:hover {
        background-color: RGBA(52, 73, 143, 0.8);
      }

      &.cancel {
        /* background-color: rgba(212, 136, 6, 1); */
        background-color: rgba($color: #a70a0f, $alpha: 1);
        &:hover {
          background-color: rgba($color: #a70a0f, $alpha: 0.8);
        }
      }
      &.sell {
        /* background-color: rgba(212, 136, 6, 1); */
        background-color: rgba($color: #801fb3, $alpha: 1);
        &:hover {
          background-color: rgba($color: #801fb3, $alpha: 0.8);
        }
      }
    }
  }

  &.rare {
    border-color: rgba($color: #1c86ef, $alpha: 0.5);
    .title {
      margin-top: 20px;
      background-color: rgba($color: #1c86ef, $alpha: 0.5);
      .color-block {
        border-bottom-color: rgba($color: #1c86ef, $alpha: 0.5);
      }
      .ship-title .level {
        color: #1c86ef;
      }
    }
  }
  &.epic {
    border-color: rgba($color: #9c07ff, $alpha: 0.5);
    .title {
      margin-top: 20px;
      background-color: rgba($color: #9c07ff, $alpha: 0.5);
      .color-block {
        border-bottom-color: rgba($color: #9c07ff, $alpha: 0.5);
      }
      .ship-title .level {
        color: #9c07ff;
      }
    }
  }
  &.legend {
    border-color: rgba($color: #ff8000, $alpha: 0.5);
    .title {
      margin-top: 20px;
      background-color: rgba($color: #ff8000, $alpha: 0.5);
      .color-block {
        border-bottom-color: rgba($color: #ff8000, $alpha: 0.5);
      }
      .ship-title .level {
        color: #ff8000;
      }
    }
  }

  &.spaceship {
    /* .image {
      img {
        width: 23vw;
      }
    } */
    .description {
      width: 23vw;
    }
  }
}
</style>