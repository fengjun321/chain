<template>
  <div v-show="visible" class="quick-sell-dialog">
    <div class="container">
      <div class="card-image">
        <img :src="image" />
      </div>
      <div class="text">
        <p>
          FOR QUICK SELL, THE MARKETPLACE WILL RECYCLE 40% VALUE OF THE
          MERCHANDISE.
        </p>
        <p style="text-align: center">ARE YOU SURE YOU WANT TO SELL QUICKLY?</p>
      </div>
      <div class="container-btn">
        <div
          @click="$emit('confirm')"
          class="container-btn-confirm container-btn-box"
        >
          <span> CONFIRM </span>
        </div>
        <div
          @click="$emit('cancel')"
          class="container-btn-cancel container-btn-box"
        >
          <span> CANCEL </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'QuickSellDialog',
  props: {
    image: String,
    visible: Boolean,
  },
};
</script>

<style lang="scss" scoped>
.quick-sell-dialog {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 100;

  & .container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    padding-top: 80px;

    & .card-image {
      width: 261px;
      height: 370px;

      & img {
        width: 100%;
        height: 100%;
      }
    }

    & .text {
      margin-top: 62px;
      color: white;
      font-size: 30px;
      font-family: Shentox TRIAL, serif;
      font-weight: 400;
    }

    &-btn {
      margin-top: 62px;
      display: flex;

      &-box {
        width: 370px;
        height: 100px;
        background-size: 100%;

        & span {
          display: flex;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          text-align: center;
          font-size: 41px;
          font-family: Shentox TRIAL serif;
          font-weight: 500;
          height: 100%;
        }
      }

      &-confirm {
        margin-right: 10px;
        background: #34498f;
        border-radius: 6px;
      }

      &-cancel {
        margin-left: 10px;
        background: #34498f;
        border-radius: 6px;
      }
    }
  }

  &::after {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.8);
    z-index: -1;
    backdrop-filter: blur(10px);
  }
}
</style>
