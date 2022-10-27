<template>
  <div class="content">
    <div class="contentItemBox">
      <div class="contentItemFilter">
        <div class="FilterLeft">
          <img class="filterImg" src="@/assets/images/market/filter.png" />
          <div class="filterTit" @click="$refs.cardFilter.visible = true">FILTER</div>
        </div>
        <div class="FilterRight">
          <div class="tip">* {{ total }} {{ nftTypeName }} FOUNDED:</div>
          <card-filter-result :filter="filter" @clear="onFilterClear" @delete="onFilterDelete" />
        </div>
      </div>

      <div class="contentItems">
        <card-filter
          ref="cardFilter"
          @toggle="onToggle"
          @selected="onSelected"
          @clear="onFilterClear"
        />
        <div class="contentItemRight"
          v-loading="loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
        >
          <CardCustom
            v-for="card in cardList"
            :key="`cc + ${card.tokenId || card.token_id}`"
            :card="card"
            :nftType="nftType"
            model="collect"
            @cancel="cancel(card)"
          />
        </div>
      </div>
    </div>
    <div class="page">
      <pagination
        :page-no.sync="pageInfo.pageNum"
        :page-size.sync="pageInfo.pageSize"
        :total-count="total"
        @on-change="onChangePage"
        @prev-click="onChangePage"
        @next-click="onChangePage"
      />
    </div>
  </div>
</template>
<script>
import pagination from '../../../components/pagination/index';
import http from '../../../config/http';
import constant from '../../../common/constant';
import CardFilter from '../../../components/CardFilter2';
import CardFilterResult from '../../../components/CardFilterResult';
import query from '../../../mixin/query';
import Card from '../../../components/Card';
import CardHalloween from '../../../components/CardHalloween';
import CardEgse from '../../../components/CardEgse';
import CardPse from '../../../components/CardPse';
import CardSpaceShip from '@/components/CardSpaceShip';
import CardLootBox from '@/components/CardLootBox';
import CardMaterial from '@/components/CardMaterial';
import CardEgg from "@/components/CardEgg";
import CardEquipment from "@/components/CardEquipment";
import CardWeapon from "@/components/CardWeapon";
import CardCustom from "@/components/CardCustom.vue";

export default {
  components: {
    CardEgse,
    CardPse,
    CardHalloween,
    pagination,
    Card,
    CardFilter,
    CardFilterResult,
    CardSpaceShip,
    CardLootBox,
    CardMaterial,
    CardEgg,
    CardEquipment,
    CardWeapon,
    CardCustom,
  },
  mixins: [query],
  data() {
    return {
      loading: false,
      constant: constant,
    };
  },
  computed: {
    nftTypeName() {
      const target = Object.keys(constant.cardType).find(key => constant.cardType[key] === this.nftType)
      if(this.total > 1) {
        // return `${target}s`
        switch(target) {
          case 'lootBox':
            return 'lootBoxes'
          default:
            return `${target}s`
        }
      }
      return target || ''
    },
    nftType() {
      return this.$route.query.nftType && (this.$route.query.nftType - 0) || 0
    },
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      // 取消之前的请求
      this.cancelHttp && this.cancelHttp()
      // 生成取消token
      const CancelToken = http.CancelToken;
      this.loading = true;
      this.cardList = [];
      let query = this.getQueryFilter();
      http
        .get('/api/v1/informationCenter/myFavorite', {
          params: query,
          cancelToken: new CancelToken((c) => {
            // executor 函数接收一个 cancel 函数作为参数
            this.cancelHttp = c;
          })
        })
        .then(res => {
          this.loading = false;
          this.cardList = res.data.data.records;
          this.total = res.data.data.total;
          this.cardList.forEach(ele => {
            ele.isSale = ele.hasOwnProperty('price');
            ele.isFavorite = true;
          });
        })
        .catch(() => {
          this.cardList = [];
          this.total = 0;
          this.loading = false;
        })
        .finally(() => {
          this.loading = false;
        })
    },
    onLink(e) {
      this.$router.push({
        path: '/token',
        query: {
          id: e.tokenId || e.token_id,
          type: this.nftType,
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.act {
  background: linear-gradient(180deg, #07d4ff 0%, #514483 100%) !important;
}

.contentItemBox {
  margin: 14px 30px;

  .contentItemFilter {
    margin-top: 32px;
    display: flex;

    .FilterLeft {
      width: 385.61px;

      display: flex;
      align-items: center;

      .filterImg {
        width: 33px;
        height: 24px;
      }

      .filterTit {
        font-size: 30px;
        font-family: Shentox TRIAL;
        font-weight: bold;
        line-height: 0px;
        color: #ffffff;
        margin-left: 6px;
      }
    }

    .FilterRight {
      display: flex;
      align-items: center;
      margin-left: 30px;
      color: #ffffff;

      .tip {
        margin-right: 30px;
        font-size: 30px;
        font-family: Shentox TRIAL;
        font-weight: 400;
        text-transform: uppercase;
      }

      .selitem {
        cursor: pointer;
        padding: 5px 13px 5px 13px;
        background: linear-gradient(180deg, #07d4ff 0%, #514483 100%);
        opacity: 1;
        text-align: center;
        border-radius: 6px;
        font-size: 20px;
        font-family: Shentox TRIAL;
        font-weight: 400;
        color: #ffffff;
        margin-right: 22px;

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
          width: 13px;
          height: 13px;
        }
      }
    }
  }

  .contentItems {
    margin-top: 26px;
    display: flex;

    .contentItemLeft {
      width: 379.61px;
      border-radius: 11px;
      border: 3px solid rgba(144, 170, 246, 0.3);
      background-color: rgba(41, 50, 103, 0.3);
      // padding:45px 35px;

      .leftsellist {
        margin: 45px 35px;

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
              height: 33px;
              line-height: 33px;
              overflow: hidden;
              white-space: nowrap;
              text-overflow: ellipsis;
              background: linear-gradient(
                180deg,
                rgba(7, 212, 255, 0.15),
                rgba(81, 68, 131, 0.15)
              );
              opacity: 1;
              text-align: center;
              border-radius: 6px;
              font-size: 14px;
              font-family: Shentox TRIAL;
              font-weight: 400;
              color: #ffffff;
              margin-top: 12px;

              &:nth-child(even) {
                margin-left: 14px;
              }
            }
          }

          .left_selitem_level {
            display: flex;
            flex-wrap: wrap;
            flex-direction: row;
            justify-content: space-between;
            margin-bottom: 25px;

            span {
              cursor: pointer;
              flex: 1;
              width: 33px;
              height: 33px;
              line-height: 33px;

              background: linear-gradient(
                180deg,
                rgba(7, 212, 255, 0.15),
                rgba(81, 68, 131, 0.15)
              );
              opacity: 1;
              text-align: center;
              border-radius: 6px;
              font-size: 14px;
              font-family: Shentox TRIAL;
              font-weight: 400;
              color: #ffffff;
              margin-top: 12px;
              margin-left: 36px;

              &:nth-child(1) {
                margin-left: 0px;
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
                background-image: url(~@/assets/images/market/inputbg.png);
                background-size: 100% 100%;

                img {
                  width: 1.89px;
                  height: 16.02px;
                  margin-left: 13px;
                }

                input {
                  width: 100px;
                  background-color: none;
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
                line-height: 0px;
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

    .contentItemRight {
      flex: 1;
      display: flex;
      flex-wrap: wrap;
      flex-direction: row;
      height: 100%;

      .items {
        width: 250.25px;
        height: 478.87px;
        background-size: 100% 100%;
        position: relative;
        margin-left: 38px;
        margin-bottom: 50px;

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
          color: #b6b6b6;
          position: absolute;
          left: 9px;
          top: 4px;
          font-style: oblique;
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

        .imgDiv {
          width: 205px;
          height: 290.61px;
          position: absolute;
          left: 24px;
          top: 17px;
        }

        .imgDiv img {
          width: 100%;
          height: 100%;
        }

        .imgDiv img:hover {
          // transform: scale(1.1);
        }

        .card_title {
          font-size: 17px;
          font-family: Shentox TRIAL;
          font-weight: 400;
          line-height: 0px;
          color: #ffffff;
          text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
          position: absolute;
          left: 7px;
          top: 338px;
        }

        .card_val {
          font-size: 38px;
          font-family: Shentox TRIAL;
          font-weight: 400;
          line-height: 0px;
          color: #ffffff;
          text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
          position: absolute;
          left: 169px;
          top: 328px;
        }

        .card_info {
          position: absolute;
          left: 18px;
          top: 361px;
          width: 212.25px;

          ul {
            width: 100%;
            flex-wrap: wrap;
            flex-direction: row;
            display: flex;
            justify-content: space-between;

            li {
              list-style: none;
              font-size: 12px;
              font-family: Shentox TRIAL;
              font-weight: 500;
              color: #ffffff;
              margin-right: 5%;
              width: 30%;
              line-height: 16px;
              padding: 2px 0;

              &:nth-child(3n + 0) {
                margin-right: 0px;
                text-align: right;
              }

              &:nth-child(8) {
                margin-right: 0px;
                width: 50%;
                text-align: right;
              }

              span {
                color: #b6b6b6;
              }
            }
          }
        }

        .coinDiv {
          position: absolute;
          left: 21px;
          bottom: 20px;
          width: 212.25px;
          display: flex;
          align-items: center;
          justify-content: center;

          span {
            font-size: 28px;
            font-family: Shentox TRIAL;
            font-weight: 500;
            line-height: 23px;
            color: #ffffff;
          }

          .coin_img {
            width: 29px;
            height: 28px;
            border-radius: 0px;
            margin-left: 10px;

            img {
              width: 100%;
              height: 100%;
            }
          }

          .buynow {
            cursor: pointer;
            width: 117.89px;
            height: 32.91px;
            /* background-image: url(~@/assets/images/market/buynow.png);
            background-size: 100% 100%; */
            background: #34498f;
            border-radius: 6px;
            font-size: 16px;
            font-family: Shentox TRIAL;
            font-weight: 500;
            color: #ffffff;
            text-align: center;
            line-height: 30.91px;
          }
        }
      }
    }
  }
}

.page {
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
