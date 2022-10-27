<template>
  <div>
    <div class="space-power" v-if="showPower">Power: {{ card.power }}</div>
    <div class="space-content" v-if="nftTypeIs('card')">
      <span>{{ identity }}</span>
      <span>{{ profession }}</span>
      <span>{{ camp }}</span>
    </div>
    <!-- <div class="space-content" v-if="showPower">
      <span :style="{ color: rankColor }">{{ rank }}</span>
      <span :style="{ color: rankColor }">{{ cardLevel }}</span>
    </div>-->
    <div class="space-description" v-if="!nftTypeIs(['pse', 'lootbox'])">{{ cardDescription }}</div>
    <template v-if="nftTypeIs(['pse', 'lootbox'])" >
      <div v-html="cardDescription" v-if="descIsHtml"></div>
      <div class="space-description" v-else>{{ cardDescription }}</div>
    </template>
    <!-- <div class="space-description" v-if="nftTypeIs('pse')"></div> -->
    <div class="spaceship-info" v-if="nftTypeIs('spaceship')">
      <p>Details:</p>
      <p>EQUIPPABLE COCKPIT:</p>
      <div>
        <ul>
          <li v-if="card.commander_current">
            <img src="@/assets/images/spaceship/zhihuiguan_dark.png" />
            <div>X&nbsp;{{ card.commander_current }}</div>
          </li>

          <li v-if="card.scientist_current">
            <img src="@/assets/images/spaceship/lingdaozhe_dark.png" />
            <div>X&nbsp;{{ card.scientist_current }}</div>
          </li>
          <li v-if="card.start_soldier_current">
            <img src="@/assets/images/spaceship/xingjizhanshi_dark.png" />
            <div>X&nbsp;{{ card.start_soldier_current }}</div>
          </li>
          <li v-if="card.common_soldier_current">
            <img src="@/assets/images/spaceship/putongzhanshi_dark.png" />
            <div>X&nbsp;{{ card.common_soldier_current }}</div>
          </li>
        </ul>
      </div>

      <div class="Quark-infos">
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
          <span class="effect">
            Computing Power
            <span class="red">{{ ss.effect }}</span>
          </span>
        </div>
      </div>
      <div class="tips">
        *Please Note: When purchasing a starship, pay attention to whether the starship has reached the maximum ore mining volume.
        When a starship reaches any ore mined, it may be forcibly destroyed!
        Starships greater than or equal to Lv.05 will not have a maximum ore mining limit
      </div>
    </div>
    <div class="card-attr-list" v-if="nftTypeIs('card')">
      <div class="card-attr-item" v-for="(item, index) in cardAttrList" :key="index">
        <div>{{ `${item}: ${card[item.toLowerCase()] || 'unknown'}` }}</div>
        <el-progress
          :percentage="card[item.toLowerCase()]"
          :stroke-width="8"
          color="#88BFFF"
          :show-text="false"
        ></el-progress>
      </div>
    </div>
    <div class="pse-attr" v-if="nftTypeIs('equipment') && equipAttrs.length">
      <h4>Function:</h4>
        <ul>
          <li v-for="item in equipAttrs" :key="item.label">
            {{`${item.label} +${item.value}`}}
          </li>
        </ul>
    </div>
    <!-- <div class="pse-attr" v-if="nftTypeIs('pse')">
      <h4>Function:</h4>
      <template v-if="card.attrType === 'galxy'">
        <ul>
          <li>
            If you have aPassport_Galaxy(Swapped) you will enjoy the corresponding purchase discount.
            <span>Passport_Eternal 90% OFF(The maximum number of loot boxes that can be purchased at discount one day is 25.)</span>
          </li>
          <li>Galaxy Spaceship with 12 drive slots.</li>
          <li>l-stage Interstellar Pet.</li>
          <li>Epic loot box [worth $165] every week.</li>
        </ul>
      </template>
      <template v-if="card.attrType === 'blackbox'">
        <ul>
          <li>
            If you have aPassport_Galaxy(Swapped) you will enjoy the corresponding purchase discount.
            <span>Passport_Eternal 90% OFF(The maximum number of loot boxes that can be purchased at discount one day is 25.)</span>
          </li>
          <li>Galaxy Spaceship with 12 drive slots.</li>
          <li>l-stage Interstellar Pet.</li>
          <li>Epic loot box [worth $165] every week.</li>
        </ul>
      </template>
    </div> -->
  </div>
</template>

<script>
import constant from '@/common/constant';
// import card from "../../../mixin/card";
// import Web3 from 'web3';
import { encode, decode , isValid} from 'js-base64';
import http from '@/config/http';

export default {
  data() {
    return {
      constant,
      cardAttrList: [
        'ENDURANCE',
        'STRENGTH',
        'AGILITY',
        'ARMOR',
        'CRIT',
        'PSIONIC',
        'SPIRIT',
      ],
      ss: {
        Quark: 0,
        Fire: 0,
        GrindPower: 100,
        effect: '',
      },
      loadingQuark: true,
      loadingGrindPower: true,
      descIsHtml: false,
      equipAttrList:[
        'Endurance',
        'Strength',
        'Agility',
        'Armor',
        'Crit',
        'Pisonic',
        'Spirit',
      ],
      equipAttrs:[],
    };
  },
  props: {
    card: {
      type: Object,
      default: () => { },
    },
  },
  computed: {
    cardLevel() {
      const level = this.card.level || 0
      return "Lv." + ("00" + level).slice(-2)
    },
    rank() {
      return this.card.rank || this.card.attrRank || '';
    },
    rankColor() {
      // return (rank) => this.constant.spaceshipTypeColor[rank] || '#fff'
      return (
        this.constant.spaceshipTypeColor[this.rank.toUpperCase()] || '#fff'
      );
      // spaceshipTypeColor: {
      //   COMMON: '#8696C0',
      //   RARE: '#29BDFF',
      //   EPIC: '#9D00FF',
      //   LEGENDARY: '#FFD800',
      // },
    },
    cardType() {
      return this.$route.query.type - 0;
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
          return this.cardType === constant.cardType[type]
        } else if (Array.isArray(type)) {
          return type.some(t => this.cardType === constant.cardType[t])
        }
        // edge case
        return false
      }
    },
    cardDescription() {
      if (this.nftTypeIs(['pse','lootbox'])) {
        try {
          let descriptionHtml = ''
          if(isValid(this.card.description)){
            this.descIsHtml = true
            descriptionHtml = decode(this.card.description);
          } else {
            this.descIsHtml = false
            descriptionHtml = this.card.description
          }
          console.log('descriptionHtml', descriptionHtml);
          return descriptionHtml
        } catch (error) {
          console.log('decode html error', error);
        }
      }
      return this.card.description
    },
    identity() {
      return constant.identity[this.card.identity]
    },
    profession() {
      return constant.profession[this.card.occupation]
    },
    camp() {
      return constant.camp[this.card.camp]
    },
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
  },
  watch: {
    'card.token_id'(val) {
      if (val) {
        if (this.nftTypeIs('spaceship')) {
          this.getQuarkAndFire()
        }
      }
    },
    'card.tokenURI'(val){
      if(val) {
        this.getTokenDetailByTokenURI(val)
      }
    }
  },
  methods: {
    getTokenDetailByTokenURI(tokenURI){
     return http
        .get(tokenURI)
        .then((res) => {
          let data = res.data;
          console.log('链上数据：',data);

          // 获取card属性 Function:
          if(this.nftTypeIs('equipment')){
            if(data && data.attributes && Array.isArray(data.attributes)){
              data.attributes.forEach(item=>{
                if(this.equipAttrList.includes(item.trait_type) && item.value > 0){
                  this.equipAttrs.push({
                    label:item.trait_type,
                    value: item.value
                  })
                }
              })

              const l = Array.from(new Set(this.equipAttrs.map(({value})=>value)))
              // console.log(l);
              if(this.equipAttrs.length === this.equipAttrList.length && l.length === 1){
                this.equipAttrs = [{
                    label:'All Hero Attributes',
                    value: l[0]
                  }]
              }
            }

            if(!this.card.description){
              const obj = {...this.card, description:data.description}
              this.$emit("update:card", obj)
            }
          }

        })
    },
    getQuarkAndFire() {
      // console.log(' this.$IsTarship', this.$IsTarship);
      // console.log(' this.card.token_id', this.card.token_id);
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
        if (Grind > 90) this.ss.effect = 'no effect'
        else if (Grind >= 80) this.ss.effect = '-3%'
        else if (Grind >= 70) this.ss.effect = '-5%'
        else if (Grind >= 60) this.ss.effect = '-8%'
        else if (Grind >= 50) this.ss.effect = '-13%'
        else if (Grind >= 40) this.ss.effect = '-20%'
        else this.ss.effect = 'can not mining'
      }).finally(() => {
        this.loadingGrindPower = false
      })
    },
  },
};
</script>

<style lang="scss" scoped>
.space-power {
  /* color: #ef4b70; */
  font-size: 24px;
  line-height: 24px;
  font-weight: 900;
  margin-bottom: 12px;
}
.space-content {
  /* display: flex; */
  /* align-content: center; */
  /* justify-content: space-between; */
  /* width: 420px; */
  margin-bottom: 12px;
  span {
    display: inline-block;
    padding: 6px 24px;
    background-color: rgba(75, 98, 175, 1);
    /* border-radius: 10px; */
    font-size: 18px;
    font-weight: 900;
    text-transform: capitalize;
    margin-right: 12px;
    font-style: italic;
    $w: 6px;
    clip-path: polygon(
      $w 0,
      calc(100% - $w) 0,
      100% $w,
      100% calc(100% - $w),
      calc(100% - $w) 100%,
      $w 100%,
      0 calc(100% - $w),
      0 $w
    );
  }
}

.space-description {
  margin-top: 24px;
  margin-bottom: 36px;
  font-size: 16px;
  color: #fff;
}

.spaceship-info {
  margin-top: 24px;
  margin-bottom: 36px;
  p {
    font-size: 16px;
    color: #7bf5ff;
    margin-bottom: 12px;
    &:first-child {
      color: #fff;
      font-weight: bolder;
    }
  }
  ul {
    /* width: 400px; */
    flex-wrap: wrap;
    flex-direction: row;
    display: flex;
    /* justify-content: space-between; */
    font-size: 34px;
    margin-bottom: 12px;

    li {
      display: flex;
      align-items: center;
      /* width: 30px; */
      /* height: 36px; */

      &:not(:last-child) {
        margin-right: 36px;
      }

      img {
        /* width: 100%; */
        width: 30px;
        height: 36px;
        object-fit: contain;
      }

      div {
        /* margin: 5px 0 0 10px; */
        margin-left: 12px;
        color: #7bf5ff;
        font-family: "Arial" !important;
        font-size: 0.6rem;
      }
    }
  }

  .Quark-infos {
    display: flex;
    margin-bottom: 12px;
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
      width: 50%;
      display: flex;
      flex-direction: column;
      padding: 12px;
      border-radius: 6px;
      background-color: rgba(56, 73, 105, 0.25);
    }
    .left {
      width: 40%;
      /* border-radius: 6px; */
      margin-right: 12px;
      line-height: 2;
    }
    .right {
      /* align-items: flex-end; */
      .shield {
        font-size: 48px;
        font-weight: bolder;
      }
      .effect {
        .red {
          color: rgba(239, 75, 112, 1);
        }
      }
    }
  }
  .tips {
    color: rgba(239, 75, 112, 1);
  }
}

.pse-attr {
  h4 {
    font-size: 24px;
    font-weight: bolder;
    font-family: Shentox TRIAL;
    margin-top: 16px;
    margin-bottom: 12px;
  }
  ul {
    li {
      /* list-style-type: armenian; */
      list-style-image: url("../../../assets/images/list-image.png");
      color: #7bf5ff;
      margin-bottom: 4px;
      span {
        color: #bd7bff;
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .space-content {
    display: flex;
    align-content: center;
    justify-content: space-between;
    width: 100%;
    font-size: 16px;
    font-weight: 900;
  }

  .spaceship-info {
    ul {
      width: 100%;
      font-size: 14px;
      justify-content: space-around;

      li {
        width: 15px;
        height: 21px;
      }
    }
  }
}

.card-attr-list {
  /* margin-top: 20px; */
  /* position: absolute; */
  /* height: 100px; */
  .el-progress {
    width: 12vw;
    min-width: 100px;
    &::v-deep {
      .el-progress-bar__outer,
      .el-progress-bar__inner {
        border-radius: unset;
      }
      .el-progress-bar__outer {
        background-color: transparent;
        border: 1px solid rgba(255, 255, 255, 0.3);
      }
    }
  }
  .card-attr-item {
    &:not(:last-child) {
      margin-bottom: 12px;
    }
  }
}
</style>
