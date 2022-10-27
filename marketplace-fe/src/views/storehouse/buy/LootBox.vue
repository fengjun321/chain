<template>
  <div>
    <div class="box-power">COMPUTING POWER:260</div>
    <div class="box-content">
      <span style="color: #ffd800">LEGENDARY</span>
      <span style="color: #8797c1">LV.01</span>
    </div>
    <div class="box-description">
      A light weighted high-speed combat spaceship, with gorgeous camouflage
      light belts like colored glaze in high-speed movement and thus have a
      certain hallucinogenic effect. It features excellent mobile combat
      capability, and is equipped with 14 lightweight modules.
    </div>
  </div>
</template>

<script>
import constant from '@/common/constant';
export default {
  // props: {
  //   data: Object,
  // },
};
</script>

<style lang="scss" scoped>
.box-power {
  color: #ef4b70;
  font-size: 16px;
  line-height: 2.2;
  font-weight: 900;
}
.box-content {
  display: flex;
  align-content: center;
  justify-content: space-between;
  width: 420px;
  font-size: 16px;
  font-weight: 900;
}

.box-description {
  margin-top: 40px;
  font-size: 16px;
  color: #fff;
}

.card-info {
  margin-top: 20px;

  p {
    font-size: 16px;
    color: #7bf5ff;
    margin-bottom: 20px;
  }
  ul {
    width: 400px;
    flex-wrap: wrap;
    flex-direction: row;
    display: flex;
    justify-content: space-between;
    font-size: 34px;

    li {
      display: flex;
      align-items: center;
      width: 30px;
      height: 36px;

      img {
        width: 100%;
      }

      div {
        margin: 5px 0 0 5px;
        color: #6878b3;
        font-family: 'fangsong' !important;
      }
    }
  }
}

@media screen and (max-width: 768px) {
  .box-content {
    display: flex;
    align-content: center;
    justify-content: space-between;
    width: 100%;
    font-size: 16px;
    font-weight: 900;
  }

  .card-info {
    ul {
      width: 100%;
      font-size: 14px;
      justify-content: space-around;

      li {
        width: 15px;
        height: 21px;
      }
    }
  }
}
</style>
