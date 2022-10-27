<template>
  <div class="card-filter-between">
    <div style="display: flex; align-items: center;cursor: pointer;" @click="onCollapse()">
      <span class="title">{{ title }}（{{ min }}-{{ max }}）</span>
      <div
        class="collapse-btn"
        :class="{
          'select-drop-down': isCollapse === false,
          'select-drop-up': isCollapse,
        }"
      ></div>
    </div>
    <div v-show="isCollapse" class="card-filter-between-input-box">
      <div class="input-contain">
        <input
          v-model="minValue"
          @keyup.enter="onEnter"
          @input="minValue = minValue.replace(/^\.+|[^\d.]/g, '')"
          placeholder="MIN"
        />
      </div>
      <img class="input-split" src="../assets/images/market/inpbr.png" />
      <div class="input-contain">
        <input
          v-model="maxValue"
          @keyup.enter="onEnter"
          @input="maxValue = maxValue.replace(/^\.+|[^\d.]/g, '')"
          placeholder="MAX"
        />
      </div>
    </div>
    <p v-if="hasError" class="filter-error">{{ errorMessage }}</p>
  </div>
</template>

<script>
export default {
  name: "CardFilterBetween",
  props: {
    title: String,
    min: Number,
    max: Number,
  },
  data() {
    return {
      isCollapse: false,
      minValue: "",
      maxValue: "",
      hasError: false,
      errorMessage: "",
    };
  },
  created() {
    this.$watch(() => this.$route.query,
      () => {
        if (typeof this.title !== 'string') return
        let key = this.title.split('')
        key[0] = key[0].toUpperCase()
        // console.log(key.join(''));
        const target = this.$route.query[key.join('')]
        if (target) {
          const [min, max] = target.split(',');
          this.minValue = min
          this.maxValue = max
          this.isCollapse = true
          this.onEnter()
        }
        // this.current = this.$route.query[this.syncWithUrl] || this.default || -1
        // if (this.current !== -1) {
        //   this.updateVuex()
        // }
      }, { immediate: true }
    )
  },
  methods: {
    onEnter() {
      if (this.maxValue.length === 0) {
        this.hasError = true;
        this.errorMessage = "Please enter the maximum value!";
        return;
      }
      if (this.minValue.length === 0) {
        this.hasError = true;
        this.errorMessage = "Please enter the minimum value!";
        return;
      }
      if (this.maxValue.length > 0 && this.minValue.length > 0) {
        this.closeError();
        this.$emit("change", { min: this.minValue, max: this.maxValue });
      }
    },
    onCollapse() {
      this.isCollapse = !this.isCollapse;
      this.maxValue = "";
      this.minValue = "";
      this.closeError();
    },
    closeError() {
      this.hasError = false;
      this.errorMessage = "";
    },
  },
};
</script>

<style lang="scss" scoped>
.card-filter-between {
  display: flex;
  flex-direction: column;
  margin-top: 10px;
  margin-bottom: 15px;

  .card-filter-between-input-box {
    display: flex;
    margin-top: 20px;
    margin-bottom: 15px;
    align-items: center;

    .input-split {
      width: 20px;
      height: 5px;
      margin-left: 13px;
      margin-right: 13px;
    }

    .input-contain {
      width: 122.78px;
      height: 33.13px;
      /* background-image: url("../assets/images/market/inputbg.png"); */
      /* background-size: 100% 100%; */
      padding-left: 15px;
      padding-right: 15px;

      input {
        width: 100%;
        height: 100%;
        outline: none;
        /* background: none; */
        background-color: rgba(9, 11, 23, 1);
        /* border: none; */
        border: 2px solid #27305d;
        border-radius: 6px;
        text-align: center;
      }
    }
  }
}

.title {
  font-size: 19px;
  font-family: Shentox TRIAL;
  font-weight: 500;
  text-transform: capitalize;
  line-height: 0;
  color: #ffffff;
}

.filter-error {
  color: #ff8700;
  margin-top: 5px;
}

.collapse-btn {
  cursor: pointer;
}

.select-drop-down {
  width: 0;
  height: 0;
  margin-left: 5px;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-top: 16px solid #7b99ff;
}

.select-drop-up {
  width: 0;
  height: 0;
  margin-left: 5px;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-bottom: 16px solid #7b99ff;
}
@media screen and (max-width: 768px) {
  .card-filter-between {
    margin: 0.28rem 0;
    .title {
      font-size: 0.3rem;
    }
    .collapse-btn {
      transform: scale(0.8);
    }
    .card-filter-between-input-box {
      margin: 0.3rem 0;
      justify-content: space-between;
      .input-contain {
        width: 2.5rem;
        height: 0.65rem;
        input {
          font-size: 0.35rem;
        }
      }

      .input-split {
        width: 0.3rem;
        height: 0.1rem;
        margin: 0 0.42rem;
      }
    }
  }
}
</style>
