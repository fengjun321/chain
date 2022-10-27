<template>
  <div
    class="space-ship-card"
    :style="{
      backgroundImage: 'url(' + require('../assets/images/spaceship/bg.png'),
    }"
  >
    <div v-if="card.isFavorite" class="collect">
      <img src="../assets/images/collect.png" />
    </div>
    <div class="imgDiv" @click="onLink(card)">
      <img :src="card.image" />
    </div>
    <p class="card_title">{{ card.ship_name }}</p>
    <span class="number">
      <span
        :style="{
          color: `${constant.spaceshipTypeColor[card.rank.toUpperCase()]}`,
          'margin-right': '10px',
        }"
        >LV.0{{ card.level }}</span
      >
      <span
        :style="{
          color: `${constant.spaceshipTypeColor[card.rank.toUpperCase()]}`,
        }"
      >
        {{ card.rank }}
      </span>
    </span>
    <div class="card_content" style="display: flex; align-item: center">
      <span class="card_subtitle">COMPUTING POWER</span>
      <span class="card_val">{{ card.power || 0 }}</span>
    </div>
    <div class="card_info">
      <ul>
        <li v-if="card.commander_current">
          <img src="../assets/images/spaceship/zhihuiguan.png" alt="" />
          <div>X&nbsp;{{ card.commander_current }}</div>
        </li>
        <li v-if="card.scientist_current">
          <img src="../assets/images/spaceship/lingdaozhe.png" alt="" />
          <div>X&nbsp;{{ card.scientist_current }}</div>
        </li>
        <li v-if="card.start_soldier_current">
          <img src="../assets/images/spaceship/xingjizhanshi.png" alt="" />
          <div>X&nbsp;{{ card.start_soldier_current }}</div>
        </li>
        <li v-if="card.common_soldier_current">
          <img src="../assets/images/spaceship/putongzhanshi.png" alt="" />
          <div>X&nbsp;{{ card.common_soldier_current }}</div>
        </li>
      </ul>
    </div>
    <slot name="bottom"></slot>
  </div>
</template>

<script>
import constant from '../common/constant';
import CardAttributeColor from './CardAttributeColor';
import card from '../mixin/card';

export default {
  name: 'Card',
  props: {
    card: Object,
  },
  mixins: [card],

  components: {
    CardAttributeColor,
  },
  methods: {
    onLink(e) {
      this.$router.push({
        path: '/token',
        query: {
          id: e.tokenId || e.token_id,
          type: constant.cardType.spaceship,
        },
      });
    },
  },
  data() {
    return {
      nftType: constant.cardType.spaceship,
      constant: constant,
    };
  },
};
</script>

<style lang="scss" scoped>
.space-ship-card {
  width: 321px;
  height: 643px;
  background-size: 100% 100%;
  position: relative;
  margin-left: 38px;
  margin-bottom: 50px;
  display: block;

  &:nth-child(11),
  &:nth-child(12),
  &:nth-child(13),
  &:nth-child(14),
  &:nth-child(15) {
    margin-bottom: 0;
  }

  .number {
    font-size: 22px;
    font-family: Shentox TRIAL;
    font-weight: bold;
    /* color: #b6b6b6; */
    position: absolute;
    left: 40px;
    top: 350px;
    font-style: oblique;

    img {
      display: inline-block;
      width: 26px;
      height: 26px;
    }
  }

  .imgDiv {
    width: 292px;
    height: 241px;
    position: absolute;
    /* left: 60px; */
    top: 27px;
  }

  .imgDiv img {
    width: 100%;
    /* height: 100%; */
    cursor: pointer;
  }
  .card_title {
    position: absolute;
    left: 40px;
    top: 280px;
    font-size: 38px;
  }
  .card_subtitle {
    font-size: 20px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    line-height: 0px;
    color: #ffffff;
    text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
    position: absolute;
    left: 20px;
    top: 450px;
  }

  .card_val {
    font-size: 38px;
    font-family: Shentox TRIAL;
    font-weight: 400;
    /* line-height: 0px; */
    color: #ffffff;
    text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
    position: absolute;
    right: 40px;
    top: 410px;
  }

  .card_info {
    position: absolute;
    left: 0;
    top: 480px;
    /* width: 212.2px; */
    width: 100%;

    ul {
      width: 100%;
      /* flex-wrap: wrap;
      flex-direction: row;
      display: flex;
      justify-content: space-around; */
      font-size: 34px;

      li {
        float: left;
        display: flex;
        align-items: center;
        /* width: 20%; */
        padding-left: 5%;
        /* width: 20px;
        height: 26px; */

        img {
          width: 20px;
          height: 27px;
        }

        div {
          margin: 5px 0 0 5px;
          color: #6878b3;
          font-size: 18px;
          font-family: 'fangsong' !important;
        }
      }
    }
  }

  .coinDiv {
    position: absolute;
    /* left: 15px; */

    bottom: 20px;
    /* margin: 0 20px; */
    /* width: 212.25px; */
    width: 100%;

    display: flex;
    align-items: center;
    justify-content: space-between;

    div {
      width: 90%;
    }

    span {
      font-size: 28px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      /* line-height: 23px; */
      color: #ffffff;
    }

    .coin_img {
      width: 32px;
      height: 32px;
      border-radius: 0px;
      margin-left: 10px;

      img {
        width: 100%;
        /* height: 100%; */
      }
    }

    .buynow {
      cursor: pointer;
      margin: 0 18px;
      /* width: 117.89px; */
      height: 32.91px;
      padding: 0 10px;
      /* background-image: url(../assets/images/market/buynow.png);
      background-size: 100% 100%; */
      background: #34498f;
      border-radius: 6px;
      font-size: 16px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      color: #ffffff;
      text-align: center;
      line-height: 34.91px;
    }
    .buynow:nth-last-child(1) {
      margin-right: 0;
    }

    .sell {
      height: 32.91px;
      background-image: url(../assets/images/market/buynow.png);
      background-size: 100% 100%;
      font-size: 14px;
      font-family: Shentox TRIAL;
      cursor: pointer;
      font-weight: 500;
      color: #ffffff;
      text-align: center;
      line-height: 30.91px;
      padding: 0 10px;
      width: 80px;
    }

    .quick_sale {
      height: 32.91px;
      background-image: url(../assets/images/market/buynow.png);
      background-size: 100% 100%;
      font-size: 14px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      color: #ffffff;
      text-align: center;
      cursor: pointer;
      line-height: 30.91px;
      padding: 0 4px;
      flex: 1;
      margin-left: 6px;
    }
  }

  .collect {
    position: absolute;
    right: 11px;
    top: 10px;
    width: 22.91px;
    height: 20.11px;

    img {
      width: 100%;
      height: 100%;
    }
  }
}

@media screen and (max-width: 768px) {
  .box .contentItemCat {
    flex-direction: column;
    padding: 0 1.4rem;
    border-bottom: 2px solid #0a3055;
    .card {
      height: 0.78rem !important;
      line-height: 0.78rem !important;
      width: 100% !important;
      margin-left: 0 !important;
      margin-bottom: 0.4rem;
      font-size: 0.4rem !important;
    }
  }
  .contentItemBox {
    margin: 0 !important;
    padding: 0.45rem 0.5rem !important;
    .contentItemFilter {
      margin-top: 0 !important;
      flex-direction: column;
      .FilterLeft {
        width: 100% !important;
        height: 0.7rem;
        background: linear-gradient(180deg, #07d2fd 0%, #504584 100%);
        border-radius: 5px;
        justify-content: center;
        img {
          width: 0.32rem !important;
          height: 0.24rem !important;
        }
        .filterTit {
          line-height: 100% !important;
          font-size: 0.4rem !important;
          margin-left: 0.3rem !important;
          font-weight: 500;
        }
      }
      .FilterRight {
        flex-direction: column;
        margin-top: 0.18rem;
        margin-left: 0 !important;
        align-items: initial !important;
        .tip {
          margin: 0 !important;
          font-size: 0.3rem !important;
        }
        .card-filter-result {
          flex-wrap: wrap;
          .selitem,
          .clearall {
            font-size: 0.2rem;
            padding: 0.1rem 0.2rem !important;
            display: flex;
            align-items: center;
            justify-content: space-between;
            min-width: 2rem;
            margin: 0.15rem 0.15rem 0 0;
            img {
              width: 0.2rem;
              height: 0.2rem;
              margin-left: 0.2rem;
            }
          }
        }
      }
    }
    .contentItems {
      margin-top: 0.7rem !important;
      .contentItemRight {
        justify-content: space-between;
        .space-ship-card {
          margin-left: 0;
          margin-bottom: 0.5rem;
          width: 6.2rem;
          height: 6rem;
          position: relative;
          .number {
            font-size: 0.26rem;
            position: static;
            margin-top: 0.2rem;
            padding-left: 0.15rem;

            img {
              display: inline-block;
              width: 0.2rem;
              height: 0.2rem;
            }
          }
          .collect {
            width: 0.3rem;
            height: 0.25rem;
            top: 0.15rem;
            right: 0.15rem;
          }
          .imgDiv {
            width: 4.5rem;
            height: 2rem;
            position: static;
            margin: 0.3rem auto 0;
          }

          /* .card_content {
            position: static;
            top: 
          } */
          .card_title {
            position: static;
            font-size: 0.2rem;
            line-height: 0.2rem;
            display: inline-block;
            margin: 0.17rem 0;
            padding-left: 0.15rem;
            width: 100%;
          }

          .card_subtitle {
            position: static;
            font-size: 0.1rem;
            line-height: 0.2rem;
            display: inline-block;
            margin-top: 0.95rem;
            padding-left: 0.15rem;
            width: 70%;
          }

          .card_val {
            position: static;
            font-size: 0.3rem;
            height: 0.48rem;
            width: 30%;
            line-height: 0.48rem;
            /* margin-left: 1.5rem; */
            margin-top: 0.6rem;
            text-shadow: 0.03rem 0.07rem 0px rgba(0, 0, 0, 0.16);
            display: inline-block;
            text-align: left;
          }
          .card_info {
            position: static;
            width: 100%;
            /* height: 1.4rem; */
            margin-top: 0.1rem;
            margin-bottom: 0.2rem;
            padding: 0.15rem 0.2rem;

            ul {
              li {
                font-size: 0.14rem;
                height: 0.18rem;
                line-height: 0.18rem;
                padding: 0;
                margin-bottom: 0.05rem;
                margin-top: 0.2rem;

                img {
                  width: 8px;
                  height: 15px;
                }
              }
            }
          }
          .coinDiv {
            position: static;
            width: 100%;
            margin-top: 0.6rem;
            padding: 0 0.2rem;
            span {
              font-size: 0.2rem !important;
            }
            .coin_img {
              width: 0.35rem;
              height: 100%;
              margin-left: 0;
              /* margin-top: 0.15rem; */
            }
            .buynow {
              /* width: 1.45rem; */
              height: 0.4rem;
              padding: 0 0.2rem;
              line-height: 0.4rem;
              font-size: 0.23rem;
              margin-left: 0;
            }
          }
        }
      }
    }
  }
}
</style>
