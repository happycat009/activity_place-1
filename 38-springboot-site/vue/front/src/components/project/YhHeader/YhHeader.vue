<template>
  <div class="yh-header">
    <div class="bg" v-show="screenWidth <= 1920" :style="headerBgStyle"></div>
    <img
      :src="images.bg[areaCode]"
      alt=""
      class="img"
      v-show="screenWidth > 1920"
    />

    <div class="main">
      <div class="container">
        <div class="logo-wrapper">
          <img  :src="images.logo[areaCode]" alt="" class="img" @click="handleJumpRoute('/home')" />
        </div>
        <div class="search-wrapper">
          <HmhnHeaderSearch></HmhnHeaderSearch>
        </div>
      </div>
      <div class="nav-bar-wrapper">
        <YhNavBar></YhNavBar>
      </div>
    </div>
  </div>
</template>

<script>
import config from '@/config'

import imageBg511000 from '@/assets/images/home/header/bg-511000.png'
import imageLogo511000 from '@/assets/images/home/header/logo-511000.png'

import HmhnHeaderSearch from '@/components/hmhn-header-search/hmhn-header-search.vue'
import YhNavBar from '@/components/project/YhNavBar/YhNavBar.vue'
import { USER_INFO } from '@/store/constants'
import { mapGetters } from 'vuex'
import {useRouter} from "vue-router";
export default {
  name: 'YhHeader',
  setup() {
    return {}
  },
  data() {
    return {
      // 本地图片
      images: {
        bg: {
          511000: imageBg511000,
        },
        logo: {
          511000: imageLogo511000,
        },
      },
      areaCode: '', // 区域编码
      screenWidth: document.body.clientWidth, // 屏幕宽度
      router:useRouter(),
    }
  },
  created() {
    this.areaCode = config.areaCode
  },
  beforeMount() {},
  mounted() {
    // 监听页面尺寸变化
    window.onresize = () => {
      return (() => {
        window.screenWidth = document.body.clientWidth
        this.screenWidth = window.screenWidth
      })()
    }
  },
  components: { HmhnHeaderSearch, YhNavBar },
  computed: {
    ...mapGetters([USER_INFO]),
    headerBgStyle() {
      let str = ''
      const areaCode = this.areaCode
      const { bg } = this.images
      if (bg[areaCode]) {
        str += `width: 100%; `
        str += `height: 232px; `
        str += `background-image: url(${bg[areaCode]})`
      }
      return str
    },
  },
  methods: {
    getWindowWidth() {},
    handleJumpRoute(path){
      this.$emit('update:show', false)
      this.router.push(path)
    }
  },
}
</script>

<style lang="scss" scoped>
.yh-header {
  width: 100%;
  position: relative;
  .main {
    position: absolute;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: 1;
    padding-top: 38px;
  }
  .bg {
    width: 100%;
    height: 100%;
    background-repeat: no-repeat;
    background-position: center center;
    background-size: 100% 232px;
  }
  .container {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .logo-wrapper {
    width: 334px;
    cursor:pointer
  }
  .nav-bar-wrapper {
    position: absolute;
    width: 100%;
    left: 0;
    bottom: 0;
  }
}
</style>
