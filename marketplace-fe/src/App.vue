<template>
  <div style="height: 100%" id="app">
    <router-view/>
  </div>
</template>
<script>
export default {
  watch: {
    '$route': function () {
      this.$store.commit('updateSearch', '')
      this.$store.commit('updateOrderType', 0)
      this.$store.commit('updateOrderFiled', 0)
    }
  },
  created() {
    window.addEventListener('beforeunload', e => {
      this.$store.commit('updateSearch', '')
      this.$store.commit('updateOrderType', 0)
      this.$store.commit('updateOrderFiled', 0)
    })
  }
}
</script>
<style lang="scss">
html {
  background-color: #07090b;
}

@media screen and (max-width: 768px) {
  html {
    font-size: 13.33333vw !important;
  }

  .el-notification {
    width: 90%;
    &.right {
      right: 5%;
    }
    .el-notification__content p {
      color: #606266;
    }
  }
}
</style>
