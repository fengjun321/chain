import http from "../config/http";
import constant from "@/common/constant.js";

export default {
  data() {
    return {
      cancelSaleWrapVisible: false,
    };
  },
  computed: {
    cardType() {
      return this.$route.query.nftType ? this.$route.query.nftType - 0 : 0;
    },
    nftTypeIs() {
      return (type) => {
        if (typeof type === "string" || typeof type === "number") {
          return this.cardType === constant.cardType[type];
        } else if (Array.isArray(type)) {
          return type.some((t) => this.cardType === constant.cardType[t]);
        }
        // edge case
        return false;
      };
    },
  },
  methods: {
    onCancel() {
      this.cancelSaleWrapVisible = true;
    },
    onConfirm(e) {
      console.log("onConfirm", e);
      let tokenId = e.tokenId || e.token_id
      if(this.nftTypeIs('material')){
        tokenId = e.id || 0
      }
      let msg = this.$web3.utils.fromUtf8(
        `tokenId=${tokenId}&address=${this.$store.state.address}`
      );
      this.$web3.eth.personal
        .sign(msg, this.$store.state.address)
        .then((sign) => {
          const postData =  {
              address: this.$store.state.address,
              tokenId: tokenId,
              nftType: this.nftType,
              signature: sign,
            }
            if(this.nftTypeIs('material')){
              Object.assign(postData,{
                tokenId:0,
                // cnt: this.sellCnt,
                // id: this.commentsCard.id,
              })
              if(e.good_id){
                Object.assign(postData,{
                  goodId: e.good_id - 0,
                })
              }
            }
          http
            .post("/api/v1/informationCenter/cancelSell", postData)
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success("Cancel Success!");
                this.cancelSaleWrapVisible = false;
                this.load();
              }
            });
        });
    },
    onClose() {
      this.cancelSaleWrapVisible = false;
    },
  },
};
