<template>
  <div class="home">
    <header class="header">
      <div class="bg">
        <div class="color-box"></div>
        <img src="~assets/images/home/bg-header.png" alt="" />
      </div>
      <div class="con">
        <YhLogo noPadding></YhLogo>
        <div class="right">
          <Query></Query>
          <div class="login-wrapper">
            <button
              v-if="userInfo.user_id"
              @click="handleJumpRoute('/enterprises')"
            >
              企业中心
            </button>
            <div class="login" v-else>
              <span class="log-in" @click="handleJumpRoute('/login')"
                >登录</span
              >
              <span>|</span>
              <span class="log-up" @click="handleJumpRoute('/register')"
                >注册</span
              >
            </div>
          </div>
        </div>
      </div>
      <Statistics></Statistics>
    </header>
    <div class="content" :style="contentStyle">
      <Navbar></Navbar>
      <EchartsMap></EchartsMap>
      <HomeFooter></HomeFooter>
    </div>

    <AsideFixed></AsideFixed>
    <UserInfo v-if="userInfo.user_id"></UserInfo>
    <LoginReminder
      v-show="isShowLoginReminder"
      :loadMessage="loadMessage"
      @handleCloseModal="handleCloseModal"
      @showMe="showLoginReminder"
    ></LoginReminder>
  </div>
</template>

<script>
import imageBgHeader from '@/assets/images/home/bg-header.png'
import imageBgContent from '@/assets/images/home/bg-content.png'

import YhLogo from '@/components/project/YhLogo/YhLogo.vue'

import Query from './Query.vue'
import Statistics from './Statistics.vue'
import Navbar from './Navbar.vue'
import HomeFooter from './HomeFooter.vue'
import UserInfo from './UserInfo.vue'
import EchartsMap from './EchartsMap.vue'
import AsideFixed from './AsideFixed.vue'
import LoginReminder from './LoginReminder.vue'

import {USER_INFO, M_UPDATE_USER_INFO, M_UPDATE_CURRENT_MENU_LIST} from '@/store/constants'
import { mapGetters } from 'vuex'

export default {
  name: 'Home',
  data() {
    return {
      imageBgHeader,
      imageBgContent,
      isShowLoginReminder: false, // 是否显示
      loadMessage: false
    }
  },
  components: {
    YhLogo,
    Query,
    Statistics,
    UserInfo,
    HomeFooter,
    Navbar,
    EchartsMap,
    LoginReminder,
    AsideFixed,
  },
  mounted() {
    var showLoginReminder = sessionStorage.getItem('showLoginReminder')
    if (showLoginReminder && showLoginReminder == 'true') {
      this.loadMessage = true
      sessionStorage.removeItem('showLoginReminder')
    }
  },
  methods: {
    // 初始化数据
    async initData() {},
    handleJumpRoute(path) {
      // if (path == '/city') {
      //   window.open('https://www.neijiang.gov.cn/njs/zjnj/zjnj.shtml', '_blank')
      // } else {
      //   this.$store.commit(M_UPDATE_CURRENT_MENU_LIST, [
      //     {
      //       name,
      //       path,
      //     },
      //   ])
      //   this.$router.push(path)
      // }
      this.$store.commit(M_UPDATE_CURRENT_MENU_LIST, [
        {
          name,
          path,
        },
      ])
      this.$router.push(path)
    },
    handleCloseModal() {
      this.isShowLoginReminder = false
    },
    showLoginReminder() {
      this.isShowLoginReminder = true
    }
  },
  computed: {
    ...mapGetters([USER_INFO]),
    headerStyle() {
      let str = ''
      const imageBgHeader = this.imageBgHeader
      str += `background-image: url(${imageBgHeader})`
      return str
    },
    contentStyle() {
      let str = ''
      const imageBgContent = this.imageBgContent
      str += `background-image: url(${imageBgContent})`
      return str
    },
  },
}
</script>

<style lang="scss" scoped>
.home {
  width: 100%;
  min-width: 1000px;
  position: relative;
  min-height: 100%;
  min-height: 100vh;
  background-color: #eee;
  overflow: hidden;
  display: flex;
  flex-direction: column;

  .header {
    width: 100%;
    position: relative;

    .bg {
      position: relative;
      .color-box {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #022d9c;
        opacity: 0.8;
      }
    }

    .con {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      display: flex;
      align-content: center;
      justify-content: space-between;
      padding: 0 124px;
      padding: 0 6.458333%;
      padding-top: 50px;
      padding-top: 2.604167%;
      .right {
        width: 54%;
        display: flex;
        align-items: center;
      }
      @media screen and (max-width: 1500px) {
        padding: 2.604167% 40px;
      }
    }
  }
  .content {
    flex: 1;
    overflow: hidden;
    width: 100%;
    display: flex;
    background-repeat: no-repeat;
    background-position: center center;
    background-size: 100% 100%;
    position: relative;
    padding-bottom: 84px;
  }
}

.login-wrapper {
  font-size: 20px;
  font-weight: 700;
  line-height: 46px;
  color: #ffffff;
  padding-left: 32px;
  .log-in {
    padding-right: 4px;
  }
  .log-up {
    padding-left: 4px;
  }
  .log-in,
  .log-up {
    cursor: pointer;
    transition: all 0.3s;
    &:hover {
      text-decoration: underline;
    }
  }
  button {
    width: 130px;
    background-image: linear-gradient(0deg, #006cff 0%, #00c0ff 100%);
    box-shadow: 0px 30px 28px 4px rgba(0, 15, 217, 0.17);
    border-radius: 2px;
    border: solid 1px #7bdbff;
    color: #fff;
    font-size: 18px;
    font-weight: 700;
  }
}
</style>
