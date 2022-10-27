<template>
  <div class="box">
    <div class="header">
      <Header @change="change" />
    </div>

    <router-view />
  </div>
</template>

<script>
import Header from "../components/header/index";

export default {
  name: "index",
  components: {
    Header,
  },
  data() {
    return {
      tabbar: 0,
    };
  },
  mounted() {
    // this.$message.success("首页");
  },
  methods: {
    handleSizeChange() { },
    change(index) {
      if (index === 0) {
        this.$router.push("market").catch(err => { });
      } else if (index === 1) {
        this.$router.push("storehouse").catch(err => { });
      } else if (index === 2) {
        this.$router.push("count").catch(err => { });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.box {
  width: 100%;
  background-color: #06090b;
}
.header {
  height: 190px !important;
}
@media screen and (max-width: 768px) {
  .box {
    min-height: 100vh;
  }
  .header {
    height: auto !important;
  }
}
</style>
