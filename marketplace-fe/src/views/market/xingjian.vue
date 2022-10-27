<template>
  <div class="content">
    <div class="contentItemBox">


      <div class="contentItemFilter">
        <div class="FilterLeft">
          <img class="filterImg" src="@/assets/images/market/filter.png"/>
          <div class="filterTit">FILTER</div>
        </div>

        <div class="FilterRight">
          <div class="tip">* 241 CARDS FOUNDED:</div>
          <div class="selitem">
            <span>SCIENTIST</span>
            <img class="del" src="@/assets/images/market/del.png"/>
          </div>

          <div class="selitem">
            <span>LV：4</span>
            <img class="del" src="@/assets/images/market/del.png"/>
          </div>

          <div class="selitem">
            <span>PSIONICIST</span>
            <img class="del" src="@/assets/images/market/del.png"/>
          </div>

          <div class="clearall">
            <span>CLEAR ALL</span>
            <img
                class="clearall_inco"
                src="@/assets/images/market/clearall.png"
            />
          </div>
        </div>

      </div>

      <div class="contentItems">
        <div class="contentItemLeft">
          <div class="leftsellist">

            <!-- <div class="left_selitem_box">
              <div class="left_selitem_tit">* 241 CARDS FOUNDED</div>
              <div class="left_selitem">
                <div class="selitem act">
                  <span>SCIENTIST</span>
                  <img class="del" src="@/assets/images/market/del.png" />
                </div>

                <div class="selitem act">
                  <span>LV：4</span>
                  <img class="del" src="@/assets/images/market/del.png" />
                </div>


                <div class="selitem clearall">
                  <span>CLEAR ALL</span>
                  <img
                    class="clearall_inco"
                    src="@/assets/images/market/clearall.png"
                  />
                </div>
              </div>
            </div> -->
            <div class="left_selitem_box">
              <div class="left_selitem_tit">FACTION:</div>
              <div class="left_selitem">
                <span class="act">ALL</span>
                <span>ASMAN SERG</span>
                <span>EMPIRE OF AMDA</span>
                <span>CONFEDERATION</span>
                <span>ETERNAL PROTOSS</span>
              </div>
            </div>


            <div class="left_selitem_box">
              <div class="left_selitem_tit">CARD LEVEL:</div>
              <div class="left_selitem_level">
                <span>1</span>
                <span class="act">2</span>
                <span>3</span>
                <span>4</span>
                <span>5</span>
              </div>
            </div>


          </div>
        </div>
        <div class="contentItemRight">
          <div class="items" v-for="(value, key, index) in goods"
               :key="index">
            <div class="goodsbg">
              <div class="goods">
                <img :src="value['image']"/>
              </div>

              <div class="tit">{{ value['title'] }}</div>
              <div class="level">
                <span>LV:{{ value['level'] }}</span>
                <div class="star">
                  <img src="@/assets/images/market/star.png"/>
                  <img src="@/assets/images/market/star.png"/>
                  <img src="@/assets/images/market/star.png"/>
                  <img src="@/assets/images/market/star.png"/>
                </div>
              </div>
              <div class="info">
                {{ value['info'] }}
              </div>

              <div class="val">
                {{ value['val'] }}
              </div>
              <div class="remark">{{ value['remark'] }}</div>

              <div class="coinbox">
                <span>{{ value['coin'] }}</span>
                <img src="@/assets/images/market/priceicon4k.png"/>
                <div class="buynow"><span>BUY NOW</span></div>
              </div>
            </div>
          </div>


        </div>
      </div>
    </div>
    <div class="page">
      <pagination
          :page-no.sync="pageInfo.pageNum"
          :page-size.sync="pageInfo.pageSize"
          :total-count="total"
          @on-change="handleSizeChange"
      />
    </div>
  </div>
</template>
<script>
import pagination from "@/components/pagination/index";

export default {
  components: {
    pagination,
  },
  data() {
    return {
      pageInfo: {
        pageNum: 1,
        pageSize: 1,
      },
      total: 46,
      goods: [
        {
          id: 1,
          title: "LEAP",
          image: require("@/assets/images/market/goodimg1.png"),
          level: '04',
          info: 'Combat Effectiveness',
          val: 16622,
          remark: 'A rare interstellar battle ship.',
          coin: 95,
        },
        {
          id: 2,
          title: "VALOR",
          image: require("@/assets/images/market/goodimg2.png"),
          level: '04',
          info: 'Combat Effectiveness',
          val: 17443,
          remark: 'A rare interstellar battle ship.',
          coin: 104,
        },
        {
          id: 1,
          title: "LEAP",
          image: require("@/assets/images/market/goodimg3.png"),
          level: '04',
          info: 'Combat Effectiveness',
          val: 16622,
          remark: 'A rare interstellar battle ship.',
          coin: 95,
        },
        {
          id: 2,
          title: "VALOR",
          image: require("@/assets/images/market/goodimg4.png"),
          level: '04',
          info: 'Combat Effectiveness',
          val: 17443,
          remark: 'A rare interstellar battle ship.',
          coin: 104,
        },

      ],
    };
  },

  mounted() {
  },
  methods: {
    handleSizeChange() {
    },
  },
};
</script>
<style lang="scss" scoped>
.act {
  background: linear-gradient(180deg, #07d4ff 0%, #514483 100%) !important;
}

.contentItemBox {
  margin: 14px 30px;


  .contentItemFilter {
    margin-top: 32px;
    display: flex;

    .FilterLeft {
      width: 385.61px;

      display: flex;
      align-items: center;

      .filterImg {
        width: 33px;
        height: 24px;
      }

      .filterTit {
        font-size: 30px;
        font-family: Shentox TRIAL;
        font-weight: bold;
        line-height: 0px;
        color: #ffffff;
        margin-left: 6px;
      }
    }

    .FilterRight {
      display: flex;
      align-items: center;
      margin-left: 30px;
      color: #ffffff;

      .tip {
        margin-right: 30px;
        font-size: 30px;
        font-family: Shentox TRIAL;
        font-weight: 400;
      }

      .selitem {
        padding: 5px 13px 5px 13px;
        background: linear-gradient(180deg, #07d4ff 0%, #514483 100%);
        opacity: 1;
        text-align: center;
        border-radius: 6px;
        font-size: 20px;
        font-family: Shentox TRIAL;
        font-weight: 400;
        color: #ffffff;
        margin-right: 22px;

        .del {
          margin-left: 22px;
          width: 13px;
          height: 13px;
        }
      }

      .clearall {
        padding: 5px 13px 5px 13px;
        background: linear-gradient(
                180deg,
                rgba(7, 212, 255, 0.15),
                rgba(81, 68, 131, 0.15)
        );

        opacity: 1;
        text-align: center;
        border-radius: 6px;
        font-size: 20px;
        font-family: Shentox TRIAL;
        font-weight: 400;
        color: #ffffff;
        margin-right: 22px;

        .clearall_inco {
          margin-left: 22px;
          width: 13px;
          height: 13px;
        }
      }
    }


  }

  .contentItems {
    margin-top: 26px;
    display: flex;

    .contentItemLeft {
      width: 379.61px;
      border-radius: 11px;
      border: 3px solid rgba(144, 170, 246, 0.3);
      background-color: rgba(41, 50, 103, 0.3);
      // padding:45px 35px;

      .leftsellist {
        margin: 45px 35px;

        .left_selitem_box {
          .left_selitem_tit {
            align-items: center;
            display: flex;
            font-size: 20px;
            font-family: Shentox TRIAL;
            font-weight: 400;
            color: #ffffff;
            margin-bottom: 5px;
          }


          .left_selitem {
            display: flex;
            flex-wrap: wrap;
            flex-direction: row;
            justify-content: space-between;
            margin-bottom: 25px;

            span {
              width: 140px;
              height: 33px;
              line-height: 33px;
              cursor: pointer;

              background: linear-gradient(
                      180deg,
                      rgba(7, 212, 255, 0.15),
                      rgba(81, 68, 131, 0.15)
              );
              opacity: 1;
              text-align: center;
              border-radius: 6px;
              font-size: 14px;
              font-family: Shentox TRIAL;
              font-weight: 400;
              color: #ffffff;
              margin-top: 12px;

              &:nth-child(even) {
                margin-left: 14px;
              }
            }

            div {
              width: 140px;
              height: 33px;
              line-height: 33px;
              display: flex;
              align-items: center;
              justify-content: space-between;
              background: linear-gradient(
                      180deg,
                      rgba(7, 212, 255, 0.15),
                      rgba(81, 68, 131, 0.15)
              );
              opacity: 1;
              text-align: center;
              border-radius: 6px;
              font-size: 14px;
              font-family: Shentox TRIAL;
              font-weight: 400;
              color: #ffffff;
              margin-top: 12px;

              &:nth-child(even) {
                margin-left: 14px;
              }

              span {
                background: none;
                margin-top: 0;
              }

              img {
                margin-right: 7px;
              }
            }


          }

          .left_selitem_level {
            display: flex;
            flex-wrap: wrap;
            flex-direction: row;
            justify-content: space-between;
            margin-bottom: 25px;

            span {
              flex: 1;
              width: 33px;
              height: 33px;
              line-height: 33px;

              background: linear-gradient(
                      180deg,
                      rgba(7, 212, 255, 0.15),
                      rgba(81, 68, 131, 0.15)
              );
              opacity: 1;
              text-align: center;
              border-radius: 6px;
              font-size: 14px;
              font-family: Shentox TRIAL;
              font-weight: 400;
              color: #ffffff;
              margin-top: 12px;
              margin-left: 36px;

              &:nth-child(1) {
                margin-left: 0px;
              }
            }
          }


        }
      }
    }

    .contentItemRight {
      flex: 1;
      display: flex;
      flex-wrap: wrap;
      flex-direction: row;
      margin-left: 44px;
      margin-right: 92px;


      .items {
        width: 618px;
        height: 640px;
        opacity: 1;
        border-radius: 10px;
        background-image: url(~@/assets/images/market/xingjianbg.png);
        background-size: 100% 100%;
        margin-bottom: 48px;
        position: relative;

        &:nth-child(even) {
          margin-left: 3%;
        }

        &:nth-child(3), &:nth-child(4) {
          margin-bottom: 0px;
        }

        .goodsbg {
          width: 100%;
          height: 409.83px;
          // background-image: url(~@/assets/images/market/xingjiangoodbg.png);
          background-size: 100% 100%;

          .goods {
            width: 492px;
            height: 241px;
            position: absolute;
            top: 39px;
            left: 71px;

            img {
              width: 100%;
            }

            img:hover {
              // transform: scale(1.1);
            }
          }

          .tit {
            font-size: 31px;
            font-family: Shentox TRIAL;
            font-weight: bold;
            line-height: 0px;
            color: #FFFFFF;
            position: absolute;
            top: 311px;
            left: 48px;
          }


          .level {
            font-size: 19px;
            font-family: Shentox TRIAL;
            font-weight: bold;
            line-height: 0px;
            color: #B6B6B6 !important;
            position: absolute;
            top: 367px;
            left: 48px;
            display: flex;
            align-items: center;
            font-style: italic;

            .star {
              margin-left: 29px;

              img {
                width: 26.63px;
                height: 25.8px;
                margin-right: 6px;
              }
            }
          }

          .info {
            position: absolute;
            top: 407px;
            left: 48px;
            font-size: 21px;
            font-family: Shentox TRIAL;
            font-weight: 400;
            height: 25px;
            line-height: 25px;
            color: #FFFFFF;
            text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
          }

          .val {
            position: absolute;
            top: 385px;
            right: 91px;
            height: 41px;
            font-size: 34px;
            font-family: Shentox TRIAL;
            font-weight: 400;
            line-height: 41px;
            color: #FFFFFF;
            text-shadow: 3px 7px 0px rgba(0, 0, 0, 0.16);
          }

          .remark {
            position: absolute;
            top: 471px;
            left: 48px;
            height: 18px;
            font-size: 15px;
            font-family: Shentox TRIAL;
            font-weight: 400;
            line-height: 18px;
            color: #FFFFFF;
          }


          .coinbox {
            position: absolute;
            top: 546px;
            left: 48px;
            display: flex;
            align-items: center;

            span {
              height: 61px;
              font-size: 51px;
              font-family: Shentox TRIAL;
              font-weight: 500;
              line-height: 61px;
              color: #FFFFFF;
            }

            img {
              width: 48px;
              height: 41px;
              margin: 10px 0 10px 18px;
            }

            .buynow {
              // position:absolute;
              // top:553px;
              // right:41px;
              margin-left: 207px;
              /* background-image: url(~@/assets/images/market/buynow.png);
              background-size: 100% 100%; */
              background: #34498F;
              border-radius: 6px;
              width: 191.55px;
              height: 53.47px;
              text-align: center;

              span {
                height: 42px;
                font-size: 35px;
                font-family: Shentox TRIAL;
                font-weight: 500;
                line-height: 53.47px;
                color: #FFFFFF;
                text-align: center;
              }
            }
          }
        }


      }

    }
  }
}

.page {
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
