<template>
  <div class="content">
    <div class="contentItemBox">
      <div class="contentItemFilter"></div>

      <div class="contentItems">
        <space-ship-filter
          ref="cardFilter"
          @toggle="onToggle"
          @selected="onSelected"
          @clear="onFilterClear"
        />
        <div class="contentItemRight">
          <template>
            <space-ship-card
              v-for="card in cardList"
              :key="card.tokenId"
              :card="card"
            >
            </space-ship-card>
          </template>
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
import pagination from '@/components/pagination/index';
import http from '@/config/http';
import constant from '@/common/constant';
import SpaceShipFilter from '@/components/SpaceShipFilter';
// import CardFilterResult from '@/components/CardFilterResult';
import query from '@/mixin/query';
import tx from '@/mixin/transaction';
import SpaceShipCard from '@/components/SpaceShipCard';
// import CardHalloween from '@/components/CardHalloween';

export default {
  components: {
    pagination,
    SpaceShipCard,
    SpaceShipFilter,
    // CardFilterResult,
    // CardHalloween,
    // CardEgse,
    // CardPse,
  },
  mixins: [query, tx],
  data() {
    return {
      constant: constant,
    };
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      http
        .get('/api/v1/marketplace/tradingMarket', {
          params: this.getQueryFilter(),
        })
        .then(res => {
          this.cardList = res.data.data.records;
          this.total = res.data.total;
          this.cardList.forEach(ele => {
            ele.isOnwer = ele.ownerAddress === this.$store.state.address;
          });
        })
        .catch(() => {
          this.cardList = [];
          this.total = 0;
        });
    },
    onLink(e) {
      this.$router.push({
        path: '/token',
        query: {
          id: e.tokenId,
          type: this.currentCardType,
        },
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.content {
  min-height: calc(100vh - 200px);

  .coin_img {
    display: inline-flex;
    width: 48px;
    height: 40px;
  }
  .coinDiv {
    width: 560px;
  }

  .buynow {
    width: 191px;
    height: 53px;
    font-size: 36px;
    line-height: 53px;
  }
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
