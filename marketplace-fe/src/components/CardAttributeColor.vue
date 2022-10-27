<template>
  <span :style="color">{{ number }}</span>
</template>

<script>
export default {
  name: "CardAttributeColor",
  props: {
    number: Number
  },
  computed: {
    color() {
      if (this.number > 0 && this.number <= 30) return {
        color: '#FFFFFF'
      }
      else if (this.number > 30 && this.number <= 60) return {
        color: '#1C86EF'
      }
      else if (this.number > 60 && this.number <= 85) return {
        color: '#1C86EF'
      }
      else return {
          color: '#FF8000'
        }
    }
  }
}
</script>

<style scoped>

</style>
