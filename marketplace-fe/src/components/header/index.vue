<template>
  <div class="content">
    <div class="header">
      <div class="headerLeft">
        <div class="logo">
          <a href="https://www.firework.games/">
            <img src="../../assets/images/logo.png" />
          </a>
        </div>

        <div class="menu">
          <ul>
            <li v-for="(value, key, index) in menu" :key="index">
              <a
                :href="value.url"
                target="_blank"
                style="text-decoration-line: none"
              >{{ value.name }}</a>
            </li>
          </ul>
        </div>

        <!-- div class="menuIcon">
          <div class="setup">
            <img src="../../assets/images/icon1.png"/>
          </div>

          <div class="home">
            <img src="../../assets/images/icon2.png"/>
          </div>

          <div class="goback">
            <img src="../../assets/images/goback.png"/>
          </div>
        </div-->
      </div>

      <div class="headerRight">
        <div class="language">
          <div>Language</div>
          <Wallet />
        </div>
      </div>
    </div>
    <div class="nav-bar">
      <img src="@/assets/images/navbg.png" alt width="100%" class="harder-bg" />
      <div class="nav-bar-area">
        <div class="hztd">
          <div class="nav-bar-box" v-for="(item, index) in navBar" :key="index">
            <!-- <img :src="flag === index ? imgnoe : imgtow" alt="" /> -->
            <img :src="imgnoe" alt :class="{ isCurrent: flag === index }" />
            <div class="nav-bar-box-item" @click="navBarClick(index)">{{ item }}</div>
          </div>
        </div>

        <div class="nav-bar-top">
          <div class="nav-bar-top-ipt">
            <div class="nav-bar-top-ipt-ss">
              <!-- <img src="@/assets/icon/search.svg" class="icon" /> -->
              <i class="el-icon-search"></i>
            </div>
            <input v-model="searchValue" @keyup.enter="onSearch" type="text" />
          </div>
          <div class="nav-bar-top-right">
            <div @click="onOrderTypeChange" class="nav-bar-top-right-img">
              <img v-if="orderType === 1" src="../../assets/images/navbar-icon4k.png" />
              <img v-else src="../../assets/images/asc1.jpg" alt />
            </div>
            <div>
              <el-dropdown class="dropdown" placement="bottom">
                <span class="el-dropdown-link">
                  SORT BY: &nbsp;&nbsp;
                  <span style="color: #ff8000">{{ chooesActive }}</span>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    @click.native="handleCommand(item)"
                    v-for="(item, index) of dropdownList"
                    :key="index"
                  >{{ item }}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import Wallet from '../../components/Wallet';

export default {
  components: {
    Wallet,
  },
  data() {
    return {
      imgnoe: require('@/assets/images/navitemactive.png'),
      imgtow: require('@/assets/images/navitem.png'),
      menu: [
        { name: 'HOME', url: 'https://www.firework.games/' },
        { name: 'Game', url: '' },
        { name: 'Interstellar Trader', url: '' },
        // { name: 'RECRUIT', url: 'https://intro.firework.games/SparkEra' },
        { name: 'MARKETPLACE', url: 'https://marketplace.firework.games/' },
        { name: 'Storage', url: '' },
        { name: 'Exchange system', url: '' },
        { name: 'Referal', url: '' },
        { name: 'news', url: '' },
        // {
        //   name: 'WORKSHOP',
        //   url: 'https://intro.firework.games/SparkEra/workshop',
        // },
        {
          name: 'WHITEPAPER',
          url: 'https://intro.firework.games/files/Firework_Games_Whitepaper_en.pdf',
        },
      ],
      logoUrl: '',
      navBar: ['TRADING MARKET', 'INFORMATION CENTER', 'STATISTICS SECTION'],
      flag: 0,
      value: '',
      dropdownList: [
        'RECENTLY LISTED',
        'PRICE',
        'MOST VIEWED',
        'MOST FAVORITED',
        'ADDED TIME',
        // 'TOTAL ATTRIBUTES',
      ],
      chooesActive: 'PRICE',
      searchValue: '',
    };
  },
  computed: {
    orderType() {
      return this.$store.state.orderType;
    },
  },
  mounted() {
    this.$watch(() => this.$route.name, () => {
      // console.log("watch: this.$route.name");
      const index = ['market', 'storehouse', 'count',].indexOf(this.$route.name)
      index > -1 && (this.flag = index)
    }, {
      immediate: true
    })
  },
  methods: {
    navBarClick(index) {
      this.flag = index;
      this.$emit('change', index);
      this.$store.commit('clearFilter')
    },
    handleCommand(item) {
      this.chooesActive = item;
      let value = 0;
      switch (item) {
        case 'RECENTLY LISTED':
          value = 1;
          break;
        case 'PRICE':
          value = 0;
          break;
        case 'MOST VIEWED':
          value = 2;
          break;
        case 'MOST FAVORITED':
          value = 3;
          break;
        case 'ADDED TIME':
          value = 4;
          break;
        case 'TOTAL ATTRIBUTES':
          value = 5;
          break;
      }
      this.$store.commit('updateOrderFiled', value);
    },
    onOrderTypeChange() {
      if (this.$store.state.orderType === 1) {
        this.$store.commit('updateOrderType', 0);
      } else {
        this.$store.commit('updateOrderType', 1);
      }
    },
    onSearch() {
      this.$store.commit('updateSearch', this.searchValue);
    },
  },
};
</script>
<style lang="scss" scoped>
.header {
  display: flex;
  align-items: center;
  padding: 20px 51px;
  margin-bottom: 24px;
  justify-content: space-between;
  position: relative;
  z-index: 999;
  img {
    width: 100%;
  }

  .headerLeft {
    display: flex;
    align-items: center;
  }

  .logo {
    width: 46px;
    height: 40px;
  }

  .menu {
    margin-left: 36px;
  }

  .menu ul {
    display: flex;
    padding: 0;
    margin: 0;
    /* border-right: 2px solid #64869a; */
  }

  .menu ul li {
    /* height: 38px; */
    list-style: none;
    padding: 6px 16px;
    font-size: 24px;
    font-family: Shentox TRIAL;
    font-weight: 500;
    /* line-height: 38px; */
    opacity: 1;
    /* border-left: 2px solid #64869a; */
    transition: background 300ms;
    &:not(:last-child) {
      /* margin-right: 8px; */
    }
    a {
      color: #768196;
      transition: color 300ms;
      text-transform: uppercase;
    }
    &:hover {
      background: rgba(94, 113, 177, 0.2);
      a {
        color: #fff;
      }
    }
  }

  .menuIcon {
    display: flex;

    .setup {
      width: 28.28px;
      height: 26.99px;
      margin-left: 37px;
    }

    .home {
      width: 25.63px;
      height: 26.99px;
      margin-left: 39px;
    }

    .goback {
      width: 36.11px;
      height: 23.74px;
      margin-left: 38px;
    }
  }

  .language {
    display: flex;

    div {
      background: #2d3137;
      opacity: 1;
      border-radius: 0px;
      font-size: 20px;
      font-family: Shentox TRIAL;
      font-weight: bold;
      line-height: 34px;
      color: #778288;
      opacity: 1;
      padding: 3px 5px 2px 5px;
    }
  }
}

.category {
  width: 100%;
  height: 150.42px;
  background-image: url(../../assets/images/menubg.png);
  background-size: 100% 100%;
  // background-repeat-y: no-repeat;
  margin-top: -49px;
}

.contentItemBox {
  margin: 14px 30px;
  display: flex;
}

.contentItemCat {
  display: flex;
}

.contentItem {
  margin-top: 26px;
  border: 1px solid red;
}

.card {
  width: 242.79px;
  height: 45.5px;
  line-height: 45.5px;
  text-align: center;
  background-image: url(../../assets/images/card.png);
  font-size: 30px;
  font-family: Shentox TRIAL;
  font-weight: 400;
  color: #ffffff;
  opacity: 1;
}

.spaceship {
  width: 242.82px;
  height: 45.48px;
  line-height: 45.48px;
  text-align: center;
  background-image: url(../../assets/images/spaceship.png);
  font-size: 30px;
  font-family: Shentox TRIAL;
  font-weight: 400;
  color: #ffffff;
  opacity: 1;
  margin-left: 22px;
}

.nav-bar {
  width: 100%;
  @include bgc;
  margin-bottom: 36px;
  margin-top: -70px;
  position: relative;
  .nav-bar-area {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0px;
  }
  .nav-bar-box {
    position: relative;
    display: flex;
    width: 38%;
    overflow: hidden;
    transform: translate(-5.5%, 0);

    img {
      display: block;
      width: 100%;
      opacity: 0.3;
      transition: opacity 500ms;
      &.isCurrent {
        opacity: 1;
      }
    }
    &:hover img {
      opacity: 1;
    }
  }
  .nav-bar-box:nth-child(2) {
    // position: relative;
    transform: translate(-19%, 0) !important;
    // left: -105px;
  }
  .nav-bar-box:nth-child(3) {
    // position: relative;
    // left: -149px;
    transform: translate(-32%, 0) !important;
  }
  &-box {
    width: 330px;
    display: flex;
    align-items: center;
    position: absolute;

    left: -60px;
    position: relative;
    &-item {
      font-size: 18px;
      font-family: Shentox TRIAL;
      font-weight: 500;
      display: flex;
      align-items: center;
      justify-content: center;
      // text-align: center;
      cursor: pointer;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0px;
    }
  }

  &-top {
    width: 40%;
    position: absolute;
    right: 45%;
    height: 70px;
    top: 50%;
    transform: translate(100%, -50%);
    display: flex;
    align-items: center;

    &-ipt {
      // width: 654px;
      width: 65%;
      height: 70px;
      border: 4px solid rgba(94, 113, 177, 0.2);
      border-radius: 12px;
      display: flex;
      align-content: center;
      line-height: 75px;
      font-size: 18px;

      &-ss {
        width: 26px;
        height: 24px;
        @include img;
        margin: 0 20px;
        cursor: pointer;
        i {
          font-family: element-icons !important;
          color: RGBA(82, 102, 155, 1);
          font-size: 30px;
        }
      }

      input {
        width: 100%;
        height: 100%;
        background-color: transparent;
        border: none;
      }
    }

    &-right {
      display: flex;
      align-items: center;
      width: 300px;
      margin-left: 3%;

      .dropdown {
        font-size: 18px;
        margin-left: 10px;
        // padding-bottom: 3px;
        position: relative;

        &::after {
          content: "";
          display: inline-block;
          width: 12px;
          height: 18px;
          background: url(~@/assets/images/iconbottom4k.png);
          @include bgc;
          margin-left: 8px;
          // margin-top: 2px;
          position: absolute;
          top: 0;
          right: -30px;
        }
      }

      &-img {
        width: 30px;
        height: 20px;
        @include img;
        cursor: pointer;
      }
    }
  }
}

.el-dropdown-menu {
  width: 241px !important;
  height: 280px !important;
  background: linear-gradient(
    180deg,
    rgba(19, 197, 234, 0.8) 0%,
    rgba(116, 143, 244, 0.8) 48%,
    rgba(66, 48, 121, 0.8) 100%,
    rgba(1, 81, 98, 0.8) 100%
  ) !important;
  border-radius: 14px !important;
  border: none !important;
  overflow-x: hidden;
  overflow-y: scroll;

  &::-webkit-scrollbar {
    width: 5px;
    background: #10264a;
  }

  &::-webkit-scrollbar-thumb {
    width: 5px;
    background: #d1f7ff;
    border-radius: 5px;
  }

  &::-webkit-scrollbar-button {
    display: none !important;
  }
}
.el-dropdown-menu ::v-deep .el-dropdown-menu__item {
  font-size: 20px !important;
  padding: 10px 0px 10px 20px;

  &:hover {
    background-color: rgba(209, 247, 255, 0.5);
    color: #fff;
  }
}
.hztd {
  display: flex;
  width: 52.3%;
  /* overflow: hidden; */
  align-items: center;
  position: absolute;
  bottom: 2px;
}
@media screen and (min-width: 1930px) {
  .hztd {
    width: 51.3%;
    bottom: 2px;
  }
}

@media screen and (max-width: 768px) {
  .hztd {
    display: flex;
    width: 125%;
    /* overflow: hidden; */
    align-items: center;
    position: static;
    bottom: 0px;
  }
  .box > .header {
    // display: none;
    .content > .header {
      // display: none;
      padding: 0.25rem;
      .menu {
        display: none;
      }
      .logo {
        width: 0.5rem;
        height: 0.5rem;
      }
      .headerRight {
        .language > div {
          height: 0.5rem;
          line-height: 0.5rem;
          font-size: 0.3rem;
          padding: 0 0.15rem;
          text-align: center;
        }
        .wallet {
          margin-left: 0.2rem;
        }
      }
    }
  }
  .nav-bar {
    height: auto;
    margin-top: 0;
    margin-bottom: 0;
    background: none;
    .nav-bar-area {
      position: static;
    }
    .nav-bar-box {
      left: 0;
      right: 0;
      top: 0;
      margin-bottom: 0.3rem;
      // height: .78rem;
      // padding-top: .17rem;
      position: relative;
      // background: url(~@/assets/images/nav-bg.png);
      background-size: cover;
      height: 0.8rem;
      transform: none !important;
      .nav-bar-box-item {
        position: absolute;
        height: 100%;
        line-height: 0.78rem;
        font-size: 0.2rem;
        width: 2.8rem;
        left: 0.1rem;
        // left: 0;
        // background: none;
        // &:nth-child(1) {
        //   padding-left: .2rem;
        //   left: -0.77rem;
        // }
        // &:nth-child(2) {
        //   left: 2rem;
        // }
        // &:nth-child(3) {
        //   left: 4.77rem;
        // }
      }

      &:nth-child(1) {
        transform: none !important;
        left: -0.53rem;
        .nav-bar-box-item {
          padding-left: 0.2rem;
          // left: -0.2rem;
        }
      }
      &:nth-child(2) {
        transform: none !important;
        left: -0.96rem;
        .nav-bar-box-item {
          // left: 2rem;
        }
      }
      &:nth-child(3) {
        transform: none !important;
        left: -1.39rem;
        .nav-bar-box-item {
          // left: 4.77rem;
        }
      }
    }
    .nav-bar-top {
      position: static;
      width: 100vw;
      height: auto;
      transform: none;
      .nav-bar-top-ipt {
        display: none;
      }
      .nav-bar-top-right {
        position: static;
        border: 1px solid #01506b;
        border-radius: 5px;
        margin: 0.34rem 0.5rem;
        padding: 0.17rem 0.4rem;
        width: 100%;
        .nav-bar-top-right-img {
          width: 0.54rem;
          height: 0.35rem;
        }
        .dropdown {
          display: flex;
          align-items: center;
          font-size: 0.4rem;
          > span span {
            padding-left: 0.4rem;
          }
          &::after {
            margin-left: 1rem;
          }
        }
      }
    }
  }
  .el-dropdown-menu {
    overflow-y: auto;
    left: 50% !important;
    transform: translateX(-50%) !important;
  }
  .el-dropdown-menu ::v-deep .el-dropdown-menu__item {
    font-size: 0.4rem !important;
    line-height: 1.4rem;
    padding: 0 0.5rem;
    height: 1.4rem;
  }
}
</style>
<style lang="scss">
// no scoped
@media screen and (max-width: 768px) {
  .box .contentItemCat {
    flex-direction: column;
    padding: 0 1.4rem;
    border-bottom: 2px solid #0a3055;
    .card {
      height: 0.78rem !important;
      line-height: 0.78rem !important;
      width: 100% !important;
      margin-left: 0 !important;
      margin-bottom: 0.4rem;
      font-size: 0.4rem !important;
    }
  }
  .contentItemBox {
    margin: 0 !important;
    padding: 0.45rem 0.5rem !important;
    .contentItemFilter {
      margin-top: 0 !important;
      flex-direction: column;
      .FilterLeft {
        width: 100% !important;
        height: 0.7rem;
        background: linear-gradient(180deg, #07d2fd 0%, #504584 100%);
        border-radius: 5px;
        justify-content: center;
        img {
          width: 0.32rem !important;
          height: 0.24rem !important;
        }
        .filterTit {
          line-height: 100% !important;
          font-size: 0.4rem !important;
          margin-left: 0.3rem !important;
          font-weight: 500;
        }
      }
      .FilterRight {
        flex-direction: column;
        margin-top: 0.18rem;
        margin-left: 0 !important;
        align-items: initial !important;
        .tip {
          margin: 0 !important;
          font-size: 0.3rem !important;
        }
        .card-filter-result {
          flex-wrap: wrap;
          .selitem,
          .clearall {
            font-size: 0.2rem;
            padding: 0.1rem 0.2rem !important;
            display: flex;
            align-items: center;
            justify-content: space-between;
            min-width: 2rem;
            margin: 0.15rem 0.15rem 0 0;
            img {
              width: 0.2rem;
              height: 0.2rem;
              margin-left: 0.2rem;
            }
          }
        }
      }
    }
    .contentItems {
      margin-top: 0.7rem !important;
      .contentItemRight {
        justify-content: space-between;
        .card {
          margin-left: 0;
          margin-bottom: 0.5rem;
          width: 3.1rem;
          height: 6rem;
          position: relative;
          .number {
            font-size: 0.26rem;
          }
          .collect {
            width: 0.3rem;
            height: 0.25rem;
            top: 0.15rem;
            right: 0.15rem;
          }
          .imgDiv {
            width: 2.5rem;
            height: 3.6rem;
            position: static;
            margin: 0.3rem auto 0;
          }
          .card_title {
            position: static;
            font-size: 0.2rem;
            line-height: 0.2rem;
            display: inline-block;
            margin-top: 0.15rem;
            padding-left: 0.1rem;
            width: 55%;
          }
          .card_val {
            position: static;
            font-size: 0.47rem;
            height: 0.48rem;
            width: 45%;
            line-height: 0.48rem;
            margin-left: 0;
            text-shadow: 0.03rem 0.07rem 0px rgba(0, 0, 0, 0.16);
            display: inline-block;
            text-align: center;
          }
          .card_info {
            position: static;
            width: 100%;
            padding: 0.15rem 0.2rem;
            ul {
              li {
                font-size: 0.14rem;
                height: 0.18rem;
                line-height: 0.18rem;
                padding: 0;
                margin-bottom: 0.05rem;
              }
            }
          }
          .coinDiv {
            position: static;
            width: 100%;
            padding: 0 0.2rem;
            > span {
              font-size: 0.4rem;
            }
            .coin_img {
              width: 0.35rem;
              height: 0.4rem;
              margin-left: 0;
            }
            .buynow {
              /* width: 1.45rem; */
              height: 0.4rem;
              padding: 0 0.2rem;
              line-height: 0.4rem;
              font-size: 0.23rem;
              margin-left: 0;
            }
          }
        }
      }
    }
  }
}
</style>
