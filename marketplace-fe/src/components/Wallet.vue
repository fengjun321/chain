<template>
  <div @click="connect" class="wallet">{{ addrText }}</div>
</template>

<script>
export default {
  name: "Wallet",
  data() {
    return {
      walletAddress: this.$store.state.address
    }
  },
  computed: {
    addrText() {
      if (this.$store.state.address.length === 0) return 'Wallet'
      return this.$store.state.address.substring(0, 6) + "..." + this.$store.state.address.substring(this.$store.state.address.length - 6)
    }
  },
  created() {
    if (typeof window.ethereum === 'undefined') {
      this.$message.error({
        title: 'Network Error',
        message: 'MetaMask is installed!',
      });
      return
    }
    console.log('window.ethereum.chainId', window.ethereum.chainId);
    // if (window.ethereum.chainId !== process.env.VUE_APP_CHAIN_ID) {
    //   this.$notify.error({
    //     title: 'Network Error,Change to the BSC mainnet.',
    //     message: 'Please change to the BSC main network.',
    //     type: 'sucess'
    //   })
    //   return
    // }

    this.connect()

    ethereum.on('chainChanged', chainId => {
      console.log('chainChanged', chainId);
      if (process.env.VUE_APP_CHAIN_ID !== chainId) {
        this.$notify.error({
          title: 'Network Error,Change to the BSC mainnet.',
          message: 'Please change to the BSC main network.',
          color: '#ad0000', background: '#ffe1e1',
          type: 'error'
        })
        this.disconnect()
      } else {
        this.connect()
      }
    })

    ethereum.on('accountsChanged', () => {
      this.connect().then(() => {
        // 目前只需要在账户切换时刷新列表，可能会有其他edge case
        this.$EventBus.$emit('reload')
      })
    })

    ethereum.on('disconnect', error => {
      this.disconnect()
    })
    ethereum.on('connect', () => {
      this.connect()
    })
  },
  methods: {
    connect() {
      return ethereum.request({ method: 'eth_requestAccounts' }).then(res => {
        this.$store.commit('updateAddress', res[0])
        this.$store.commit('updateWallet', true)
      })
    },
    disconnect() {
      this.$store.commit('updateWallet', false)
      this.$store.commit('updateAddress', '')
    }
  }
}
</script>

<style scoped>
.wallet {
  margin-left: 41px;
  cursor: pointer;
}
</style>
