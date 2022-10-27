<template>
  <div v-show="visible" class="quick-sell-dialog">
    <div class="container">
      <div class="text">
        <p style="text-align: center">ARE YOU SURE TO CANCEL THE SALE ?</p>
      </div>
      <div class="container-btn">
        <div
          @click="$emit('onClose')"
          class="container-btn-cancel container-btn-box"
        >
          <span> CANCEL </span>
        </div>
        <div
          @click="$emit('onConfirm')"
          class="container-btn-confirm container-btn-box"
        >
          <span> CONFIRM </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import transaction from '@/mixin/transaction';
export default {
  name: 'CancelSaleWrap',
  props: {
    visible: Boolean,
    card: Object,
  },
  // mixins: ['transaction'],
};
</script>

<style lang="scss" scoped>
.quick-sell-dialog {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  z-index: 100;

  & .container {
    width: 50%;
    margin: 0 auto;
    margin-top: 220px;
    background: rgba(255, 255, 255, 0.1);
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    padding-top: 80px;
    padding-bottom: 80px;
    border-radius: 10px;

    & .card-image {
      width: 261px;
      height: 370px;

      & img {
        width: 100%;
        height: 100%;
      }
    }

    & .text {
      padding: 50px 0;
      margin-top: 62px;
      color: white;
      font-size: 40px;
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
        margin-left: 10px;
        background: #34498f;
        border-radius: 6px;
      }

      &-cancel {
        margin-right: 10px;
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
