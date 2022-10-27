<template>
  <div class="content">
    <div class="contentItemBox">
      <div class="contentItemFilter">
        <div class="FilterLeft">
          <img class="filterImg" src="../../assets/images/market/filter4k.png" />
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
          @delete="onFilterDelete"
        />
        <div class="contentItemRight"
          v-loading="loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
        >
          <CardCustom
            v-for="card in cardList"
            :key="`cc + ${card.tokenId || card.token_id || card.good_id || card.data || card.id }`"
            :card="card"
            :nftType="nftType"
            model="market"
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
import pagination from "../../components/pagination/index";
import http from "../../config/http";
import constant from "../../common/constant";
import CardFilter from "../../components/CardFilter2";
import CardFilterResult from "../../components/CardFilterResult";
import query from "../../mixin/query";
import tx from "../../mixin/transaction";
import Card from "../../components/Card";
import CardHalloween from "../../components/CardHalloween";
import CardEgse from "../../components/CardEgse";
import CardPse from "../../components/CardPse";
import CardMaterial from "../../components/CardMaterial";
import CardEgg from "../../components/CardEgg";
import CardEquipment from "../../components/CardEquipment";
import CardWeapon from "../../components/CardWeapon";
import CardSpaceShip from "@/components/CardSpaceShip";
import CardLootBox from "@/components/CardLootBox";
import CancelSaleWrap from "@/components/CancelSaleWrap";
import CardCustom from "../../components/CardCustom.vue";


export default {
  name: 'market',
  components: {
    pagination,
    Card,
    CardFilter,
    CardFilterResult,
    CardHalloween,
    CardEgse,
    CardPse,
    CardMaterial,
    CardEgg,
    CardEquipment,
    CardWeapon,
    CardSpaceShip,
    CardLootBox,
    CancelSaleWrap,
    CardCustom,
  },
  mixins: [query, tx],
  data() {
    return {
      constant: constant,
      cardDetail: null,
      cancelHttp: null,
      total: 0,
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
      http
        .get("/api/v1/marketplace/tradingMarket", {
          params: this.getQueryFilter(),
          cancelToken: new CancelToken((c) => {
            // executor 函数接收一个 cancel 函数作为参数
            this.cancelHttp = c;
          })
        })
        .then((res) => {
          // console.log(res);
          this.cardList = res.data.data.records;
          this.total = res.data.data.total;
          this.cardList.forEach((ele) => {
            ele.isOnwer =
              (ele.ownerAddress || ele.owner_address) === this.$store.state.address;
          });
          // console.log(this.cardList);
        })
        .catch(() => {
          this.cardList = [];
          this.total = 0;
        })
        .finally(() => {
          this.loading = false;
        })
    },
    onLink(e) {
      this.$router.push({
        path: "/token",
        query: {
          id: e.tokenId || e.token_id,
          type: this.nftType,
        },
      });
    },
    cancel(card) {
      this.cancelSaleWrapVisible = true;
      this.cardDetail = card;
    },
  },
};
</script>
<style lang="scss" scoped>
.content {
  min-height: calc(100vh - 200px);
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
    }
  }

  .contentItems {
    margin-top: 26px;
    display: flex;

    .contentItemRight {
      flex: 1;
      display: flex;
      flex-wrap: wrap;
      flex-direction: row;
      /* justify-content: space-between; */
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
