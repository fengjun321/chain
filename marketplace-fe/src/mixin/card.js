export default {
  computed: {
    url() {
      return `/token?id=${this.card.tokenId || this.card.token_id}&type=${
        this.nftType
      }`;
    },
  },
};
