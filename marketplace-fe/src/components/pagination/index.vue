<template>
  <div class="ng-pagination">
    <slot name="prefix"></slot>
    <el-pagination
      background
      :current-page.sync="privatePageNo"
      :page-size.sync="privatePageSize"
      :total.sync="privateTotalCount"
      @current-change="handleCurrentChange"
      class="el-pagination__colorful"
      layout="prev, pager, next"
    />
    <slot name="after"></slot>
  </div>
</template>

<script>
export default {
  name: "NG-pagination",
  props: {
    pageNo: {
      type: Number,
      default: 0,
    },
    pageSize: {
      type: Number,
      default: 0,
    },
    totalCount: {
      type: Number,
      default: 0,
    },
    // pageSizes: {
    //   type: Array,
    //   default: () => [5, 10, 15, 20, 25, 30],
    // },
  },
  computed: {
    privatePageNo: {
      get() {
        return this.$props.pageNo;
      },
      set(val) {
        // console.log('privatePageNo set', val);
        this.$emit("update:pageNo", val);
      },
    },
    privatePageSize: {
      get() {
        return this.$props.pageSize;
      },
      set(val) {
        this.$emit("update:pageSize", val);
      },
    },
    privateTotalCount: {
      get() {
        return this.$props.totalCount;
      },
      set(val) {
        this.$emit("update:totalCount", val);
      },
    },
  },
  methods: {
    handleSizeChange(val) {
      this.$emit("on-size-change", val);
      this.$emit("on-change", {
        pageNo: this.privatePageNo,
        pageSize: this.privatePageSize,
        totalCount: this.privateTotalCount,
      });
    },
    handleCurrentChange(val) {
      this.$emit("on-current-change", val);
      this.$emit("on-change", {
        pageNo: this.privatePageNo,
        pageSize: this.privatePageSize,
        totalCount: this.privateTotalCount,
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.ng-pagination {
  @include flex;
}

.ng-pagination ::v-deep .el-icon-arrow-left {
  display: none !important;
}

.ng-pagination ::v-deep .el-icon-arrow-right {
  display: none !important;
}
.ng-pagination ::v-deep .btn-prev {
  background: url(~@/assets/images/iconleft4k.png) no-repeat;
  background-size: 100%;
  background-position: center center;
  margin-right: 15px;
  width: 44px;

  height: 34px;
}

.ng-pagination ::v-deep .btn-next {
  background: url(~@/assets/images/iconright4k.png) no-repeat;
  background-size: 100%;
  background-position: center center;
  margin-left: 15px;
  width: 44px;
  height: 34px;
}
.ng-pagination ::v-deep .number {
  background-color: rgba(7, 211, 254, 0.1) !important;
  border-radius: 10px !important;
  color: #fff !important;
  font-family: Shentox TRIAL !important;
  font-weight: 500;
  width: 33px;
  height: 33px;
  line-height: 33px;
}
.ng-pagination ::v-deep .active {
  transition: background 150ms;
  /* background: linear-gradient(180deg, #07d3fe 0%, #504483 100%) !important; */
  background-color: RGBA(52, 73, 143, 1) !important;
  border-radius: 10px !important;
  line-height: 0.74667rem !important;
  font-family: Shentox TRIAL !important;
  font-weight: 500;
  width: 33px;
  height: 33px;
  line-height: 33px !important;
}
.ng-pagination ::v-deep .more {
  background-color: rgba(7, 211, 254, 0.1) !important;
  border-radius: 10px !important;
  color: #fff !important;
  font-weight: 500;
  width: 33px;
  height: 33px;
  line-height: 33px;
}
</style>
