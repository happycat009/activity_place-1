<template>
  <div class="yh-nav-bar">
    <div class="container">
      <ul>
        <template v-for="item in navbarData">
          <li
            :class="[item.checked ? 'active' : '']"
            @click="handleClickItem(item)"
          >
            {{ item.name }}
          </li>
        </template>
      </ul>
      <div class="log-in-register-wrapper" v-if="!userInfo.user_id">
        <div class="login" @click="handleJumpRoute('/login')">登录</div>
        <div class="register" @click="handleJumpRoute('/register')">注册</div>
      </div>
      <div class="log-in-register-wrapper" v-else>
        <el-popover width="400" trigger="hover">
          <template #reference>
            <div class="register">
              {{ (corporateInfo.aab012||'企业信息暂未完成认证') + "-" + userInfo.name }}
            </div>
          </template>
          <el-button style="margin-right: 16px" @click="logOut"
            >退出登录</el-button
          >
        </el-popover>

        <!--<div class="login">当前用户</div>-->
        <!--<div class="register">{{ userInfo.name }}</div>-->
        <!--<div @click="logOut">退出登录</div>-->
      </div>
    </div>
  </div>
</template>

<script>
import {
  USER_INFO,
  CURRENT_MENU_LIST,
  M_UPDATE_CURRENT_MENU_LIST,
  M_UPDATE_USER_INFO,
  CORPORATE_INFO,
} from '@/store/constants'
import { mapGetters } from 'vuex'
export default {
  name: 'YhNavBar',
  setup() {
    return {}
  },
  data() {
    return {
      navbarData: [],
    }
  },

  components: {},
  computed: {
    ...mapGetters([USER_INFO, CURRENT_MENU_LIST, CORPORATE_INFO]),
  },
  methods: {
    // 初始化数据
    initNavbarData(path) {
      const { token } = this.userInfo
      let data = [
        {
          id: '01',
          name: '首页',
          checked: false,
          path: '/home',
        },
        {
          id: '02',
          name: '政策直通车',
          checked: false,
          path: '/policy',
        },
        {
          id: '03',
          name: '甜企云社区',
          checked: false,
          path: '/community',
        },
        {
          id: '04',
          name: '走进内江',
          checked: false,
          path: '/city',
        },
      ]
      // 已登陆
      if (token) {
        data.push({
          id: '05',
          name: ' 企业中心',
          checked: false,
          path: '/enterprises',
        })
      }

      if (path) {
        data.map((item) => {
          item.checked = false
          if (path === item.path) {
            item.checked = true
          }
        })
        this.navbarData = [...data]
      } else {
        this.navbarData = [...data]
      }
    },
    // 点击路由
    handleClickItem(item) {
      const { path, name } = item

      if (path == '/city') {
        window.open('https://www.neijiang.gov.cn/njs/zjnj/zjnj.shtml', '_blank')
      } else {
        this.$store.commit(M_UPDATE_CURRENT_MENU_LIST, [
          {
            name,
            path,
          },
        ])
        this.$router.push(path)
      }
    },
    // 路由跳转
    handleJumpRoute(path) {
      this.$router.push(path)
    },
    logOut() {
      // 退出登录
      this.$store.commit(M_UPDATE_USER_INFO, null)
      this.$message.warning({
        message: '退出登录成功！',
        center: true,
        type: 'warning',
      })
      this.handleJumpRoute("/home")
    },
  },
  watch: {
    currentMenuList: {
      handler(val) {
        if (val) {
          const { path } = val[0]
          this.initNavbarData(path)
        } else {
          this.initNavbarData('home')
        }
      },
      immediate: true,
    },
  },
}
</script>

<style lang="scss" scoped>
.yh-nav-bar {
  // #327ee6
  background-color: rgba(tint($primary, 14%), 0.9);
  user-select: none;
  ul {
    display: flex;
    align-items: center;
    font-size: 17px;
    font-weight: 400;
    line-height: 40px;
    color: #ffffff;

    li {
      // width: 200px;
      width: 200px;
      line-height: 52px;
      text-align: center;
      position: relative;
      transition: all 0.3s;
      cursor: pointer;
      &::after {
        content: '';
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        right: 0;
        width: 1px;
        height: 38px;
        background-color: rgba(#99c4ff, 0.8);
      }
      &:last-child {
        &::after {
          width: 0;
        }
      }
      &.active {
        background-color: var(--color-secondary);
        font-weight: 700;
        &::after {
          width: 0;
        }
      }
      &:hover {
        background-color: var(--color-secondary);
        font-weight: 700;
        &::after {
          width: 0;
        }
      }
    }
  }
  .container {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .log-in-register-wrapper {
    display: flex;
    align-items: center;
    font-size: 16px;
    font-weight: 400;
    line-height: 24px;
    color: #ffffff;
    .login {
      cursor: pointer;
      position: relative;
      padding: 10px 0;
      padding-right: 13px;

      &::after {
        content: '';
        position: absolute;
        top: 50%;
        right: 0;
        width: 2px;
        height: 20px;
        background-color: #fff;
        transform: translateY(-50%);
      }
    }
    .register {
      padding: 10px 0;
      padding-left: 12px;
      cursor: pointer;
    }
  }
}
</style>
