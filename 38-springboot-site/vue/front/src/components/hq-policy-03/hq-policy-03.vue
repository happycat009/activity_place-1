<template>
  <div class="hq-policy-03-wrapper">
    <div class="bg">
      <img :src="imageBg" alt="" />
    </div>
    <div class="content">
      <img :src="imageText" alt="" />
      <button>立即使用</button>
    </div>
  </div>
</template>

<script>
import imageBg from './images/bg.png'
import imageText from './images/text.png'
export default {
  name: 'hqPolicy-03',
  props: {},
  setup() {
    return {}
  },
  data() {
    return {
      imageBg,
      imageText,
    }
  },
  created() {},
  mounted() {},
  components: {},
  computed: {},
  methods: {},
}
</script>

<style lang="scss" scoped>
.hq-policy-03-wrapper {
  width: 100%;
  position: relative;
  cursor: pointer;
  .bg {
    width: 100%;
  }
  .content {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    z-index: 1;
    padding: 42px 44px;
    img {
      width: 224px;
    }
    button {
      color: #ffffff;
      font-size: 22px;
      padding: 10px 12px;
      border-radius: 50px;
      background-image: linear-gradient(2deg, #ffae00 0%, #ffcc00 100%),
        linear-gradient(#ffae00, #ffae00);
      line-height: 1;
      margin-top: $spacing;
      transition: all 0.3s;
      cursor: pointer;
      user-select: none;
      font-weight: 500;
      &:hover {
        opacity: 0.8;
      }
    }
  }
}
</style>
