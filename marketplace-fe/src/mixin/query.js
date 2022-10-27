import constant from '../common/constant';

export default {
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 15,
      },
      loading: false,
      total: 0,
      filter: [],
      cardList: [],
      currentCardType: constant.cardType.card,
    };
  },
  watch: {
    '$store.state.orderFiled': function () {
      this.load();
    },
    '$store.state.orderType': function () {
      this.load();
    },
    '$store.state.searchValue': function () {
      this.load();
    },
    "$route.query"(val) {
      // console.log("watch:$route.query", val);
      if (this.$route.query.nftType) {
        this.currentCardType = this.$route.query.nftType - 0
      }
      this.pageInfo.pageNum = 1
      this.load();
    }
  },
  created() {
    this.$EventBus.$on("reload", () => {
      this.pageInfo.pageNum = 1
      this.load()
    });
    this.$on("hook:beforeDestroy", () => {
      this.$EventBus.$off("reload", this.load);
    })
  },
  methods: {
    getQueryFilter() {
      let query = {
        pageNumber: this.pageInfo.pageNum,
        pageSize: this.pageInfo.pageSize,
        address: this.$store.state.address,
        // nftType: this.currentCardType,
        nftType: 0,
        // ...this.$route.query,
      };
      // 字段转换
      const object = this.$route.query
      for (const key in object) {
        if (Object.hasOwnProperty.call(object, key)) {
          const element = object[key];
          switch (key) {
            case 'faction':
              query.camp = element
              break;
            default:
              if(object[key]){
                query[key] = element
              }
              break;
          }

          if (typeof element === 'string' && element.includes(',')) {
            const [min, max] = element.split(',');
            query[`min${key}`] = min;
            query[`max${key}`] = max;
          }

        }
      }
      // for (const item in this.$route.query) {
      //   console.log(key);
      //   if (query[key].has(',')) {
      //     console.log('key', query[key].split());
      //   }
      // }

      for (const ele of this.filter) {
        // if (ele.type === 'between') {
        //   let name = `${ele.typeName
        //     .substring(0, 1)
        //     .toUpperCase()}${ele.typeName.substring(1)}`;
        //   query[`min${name}`] = ele.min;
        //   query[`max${name}`] = ele.max;
        // } else 
        if (ele.type === 'spaceshipType') {
          query[`rank`] = ele.name;
        } else if (ele.type === 'spaceshipLevel') {
          query[`level`] = ele.value;
        }
        //  else {
        //   query[ele.type] = ele.value;
        // }
      }
      if (query.hasOwnProperty('pse')) {
        query.type = query.pse;
      }
      if (query.hasOwnProperty('egse')) {
        query.type = query.egse;
      }
      if (this.$store.state.searchValue.length > 0) {
        query.searchKey = this.$store.state.searchValue;
      }
      query.sortFiled = this.$store.state.orderFiled;
      query.sortType = this.$store.state.orderType;

      return query;
    },
    onSelected(e) {
      // let index = this.filter.findIndex(ele => ele.type === e.type);
      // if (this.filter.findIndex(ele => ele.type === e.type) > -1) {
      //   if (e.value === -1) {
      //     this.filter.splice(index, 1);
      //   } else {
      //     this.filter[index] = e;
      //   }
      // } else {
      //   if (e.value !== -1) {
      //     this.filter.push(e);
      //   }
      // }
      // this.pageInfo.pageNum = 1;
      // this.filter = [...this.filter];
      this.load();
    },
    onFilterClear() {
      console.log('f');
      this.$refs.cardFilter.resetFieldAll();
      this.filter = [];
      this.pageInfo.pageNum = 1;
      this.load();
    },
    onFilterDelete(e) {
      let index = this.filter.findIndex(ele => ele.type === e.type);
      this.filter.splice(index, 1);
      this.$refs.cardFilter.resetField(e.type);
      this.pageInfo.pageNum = 1;
      this.load();
    },
    onChangePage() {
      this.load();
    },
    onToggle(value) {
      this.cardList = [];
      // this.currentCardType = value;
      this.filter = [];
      this.pageInfo.pageNum = 1;
      this.load();
    },
  },
};
