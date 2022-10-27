export default {
  computed: {
    url() {
      return `/token?id=${this.spaceship.tokenId}&type=${this.nftType}`;
    },
  },
};
