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
        <div
          class="contentItemRight"
          v-loading="loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
        >
          <CardCustom
            v-for="card in cardList"
            :key="`cc + ${card.tokenId || card.token_id}`"
            :card="card"
            :nftType="nftType"
            model="sale"
            @cancel="cancel(card)"
            @sell="onSellLink(card)"
          />
        </div>
      </div>
    </div>
    <div class="page">
      <pagination
        :pageNo.sync="pageInfo.pageNum"
        :pageSize.sync="pageInfo.pageSize"
        :total-count="total"
        @on-change="onChangePage"
        @prev-click="onChangePage"
        @next-click="onChangePage"
      />
    </div>
    <quick-sell-dialog
      @confirm="onQuickSellConfirm"
      @cancel="quickSellDialogVisible = false"
      :visible="quickSellDialogVisible"
      :image="currentCard.image"
    />
    <cancel-sale-wrap
      @onClose="onClose"
      @onConfirm="() => onConfirm(cardDetail)"
      :visible="cancelSaleWrapVisible"
      :card="cardDetail"
    />
  </div>
</template>
<script>
import pagination from '../../../components/pagination/index';
import http from '../../../config/http';
import constant from '../../../common/constant';
import CardFilter from '../../../components/CardFilter2';
import CardFilterResult from '../../../components/CardFilterResult';
import QuickSellDialog from '../../../components/QuickSellDialog';
import CancelSaleWrap from '@/components/CancelSaleWrap';
import query from '../../../mixin/query';
import tx from '../../../mixin/transaction';
import Card from '../../../components/Card';
import CardHalloween from '../../../components/CardHalloween';
import CardEgse from '../../../components/CardEgse';
import CardPse from '../../../components/CardPse';
import CardSpaceShip from '@/components/CardSpaceShip';
import CardLootBox from '@/components/CardLootBox';
import CardMaterial from '@/components/CardMaterial';
import CardEgg from '@/components/CardEgg';
import CardEquipment from '@/components/CardEquipment';
import CardWeapon from '@/components/CardWeapon';
import CardCustom from "@/components/CardCustom.vue";

export default {
  components: {
    CardEgse,
    CardPse,
    pagination,
    Card,
    CardHalloween,
    CardFilter,
    CardFilterResult,
    QuickSellDialog,
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
      constant: constant,
      quickSellDialogVisible: false,
      currentCard: {
        image: '',
      },
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
        .get('/api/v1/informationCenter/myStorage', {
          params: query,
          cancelToken: new CancelToken((c) => {
            // executor 函数接收一个 cancel 函数作为参数
            this.cancelHttp = c;
          })
        })
        .then(res => {
          this.cardList = res.data.data.records;
          this.cardList.forEach(ele => {
            ele.isSale = ele.hasOwnProperty('price');
            ele.isOwner = ele.owner_address === this.$store.state.address;
          });
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
    onLink(e) {
      this.$router.push({
        path: '/token',
        query: {
          id: e.tokenId || e.token_id,
        },
      });
    },
    onSellLink(e) {
      this.$router.push({
        path: '/token',
        query: {
          id: e.tokenId || e.token_id,
          type: this.nftType,
        },
      });
    },
    onRecovery(e) {
      this.currentCard = e;
      this.quickSellDialogVisible = true;
    },
    onQuickSellConfirm() {
      let msg = this.$web3.utils.fromUtf8(
        `tokenId=${this.currentCard.tokenId}&address=${this.$store.state.address}`
      );

      this.$web3.eth.personal
        .sign(msg, this.$store.state.address)
        .then(sign => {
          http
            .post('/api/v1/informationCenter/quickSale', {
              address: this.$store.state.address,
              price: this.price,
              tokenId: this.currentCard.tokenId || this.currentCard.token_id,
              nftType: this.nftType,
              signature: sign,
            })
            .then(res => {
              this.quickSellDialogVisible = false;
              if (res.data.code === 200) {
                this.$message.success('Transaction Success!');
                this.load();
              }
            });
        });
    },

    cancel(detail) {
      this.cancelSaleWrapVisible = true;
      this.cardDetail = detail;
    },
  },
};
</script>
<style lang="scss" scoped>
.content {
  position: relative;
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
    align-items: flex-start;

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
