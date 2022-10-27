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
          <card-filter-result
            :filter="filter"
            @toggle="onToggle"
            @clear="onFilterClear"
            @delete="onFilterDelete"
          />
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
            model="warehouse"
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
    <cancel-sale-wrap
      @onClose="onClose"
      @onConfirm="() => onConfirm(cardDetail)"
      :visible="cancelSaleWrapVisible"
      :card="cardDetail"
    />
  </div>
</template>
<script>
import pagination from '@/components/pagination/index';
import http from '../../../config/http';
import constant from '../../../common/constant';
import CardFilter from '../../../components/CardFilter2';
import CardFilterResult from '../../../components/CardFilterResult';
import query from '../../../mixin/query';
import tx from '../../../mixin/transaction';
import Card from '../../../components/Card';
import CardHalloween from '../../../components/CardHalloween';
import CardEgse from '../../../components/CardEgse';
import CardPse from '../../../components/CardPse';
import CardSpaceShip from '@/components/CardSpaceShip';
import CardLootBox from '@/components/CardLootBox';
import CardMaterial from '@/components/CardMaterial';
import CancelSaleWrap from '@/components/CancelSaleWrap';
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
    CancelSaleWrap,
    CardEgg,
    CardEquipment,
    CardWeapon,
    CardCustom,
  },
  mixins: [query, tx],
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 15,
      },
      total: 0,
      cardList: [],
      filter: [],
      constant: constant,
      cardDetail: null,
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
        .get('/api/v1/informationCenter/selling', {
          params: query,
          cancelToken: new CancelToken((c) => {
            // executor 函数接收一个 cancel 函数作为参数
            this.cancelHttp = c;
          })
        })
        .then(res => {
          this.cardList = res.data.data.records;
          this.total = res.data.data.total;
        })
        .catch(() => {
          this.cardList = [];
          this.total = 0;
        })
        .finally(() => {
          this.loading = false;
        })
    },
    cancel(card) {
      this.cancelSaleWrapVisible = true;
      this.cardDetail = card;
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
