<template>
  <div class="selectbox">
    <el-select
      @change="onChange"
      v-model="value"
      :placeholder="place"
      :popper-append-to-body="false"
    >
      <el-option
        v-for="item in options"
        :key="item.key"
        :label="item.value"
        :value="item.key"
        class="optionbg"
      ></el-option>
    </el-select>
  </div>
</template>

<script>
import constant from "../../common/constant";

export default {
  props: {
    defaultValue: [String | Number],
    place: {
      type: String,
      default: "CYCLE",
    },
    options: {
      type: Array,
      default: () => {
        return [];
      },
    },
  },
  data() {
    return {
      value: "",
    };
  },
  created() {
    if (this.defaultValue !== undefined) {
      this.value = this.defaultValue;
    }
  },
  methods: {
    onChange(e) {
      this.$emit("filter", e);
    },
  },
};
</script>

<style lang="scss" scoped>
.selectbox {
  margin-right: 30px;
}

.selectbox ::v-deep .el-input__inner {
  width: 300px;
  padding: 5px;
  background-color: $bgc;
  border: none !important;
  border-radius: 9px;
  text-align: center;
  text-transform: uppercase;
  font-size: 20px;
  color: #fff;
}

.selectbox ::v-deep .el-input__suffix-inner {
  display: none;
}

.selectbox ::v-deep .el-input__suffix {
  width: 12px;
  height: 30px;
  //   background-color: red;
  background: url(~@/assets/images/iconbottom.png);
  @include bgc;
  margin-right: 10px;
}

.selectbox ::v-deep .el-select-dropdown {
  background-color: rgb(9, 21, 24) !important;
  // padding: 10px 15px !important;
  text-align: center !important;
  border: none !important;
  border-radius: 10px !important;
  overflow: hidden !important;
  color: #fff !important;
}

.selectbox ::v-deep .el-select-dropdown__item > span {
  color: #fff !important;
  font-family: Shentox TRIAL serif;
  text-transform: uppercase;
}

.selectbox ::v-deep .el-select-dropdown__item.hover {
  background-color: rgba(7, 211, 254, 0.1) !important;

  &:hover {
    background-color: rgba(209, 247, 255, 0.1);
  }
}

.selectbox ::v-deep .el-select-dropdown__item.hover > span {
  color: #fff !important;
}

@media screen and (max-width: 768px) {
  .selectbox {
    margin: 0 auto;
  }
  .selectbox ::v-deep .el-input__inner {
    width: 6.3rem;
    height: 0.8rem;
  }
}
</style>
