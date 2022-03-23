<template>
  <div class="user-info">
    <span>{{ (corporateInfo.aab012||'企业信息暂未完成认证' )+ "-" + userInfo.name }}</span>
    <span>，欢迎登录“甜成办”！</span>
    <button @click="logOut">退出登录</button>
  </div>
</template>

<script>
  import {CORPORATE_INFO, M_UPDATE_USER_INFO, USER_INFO, M_UPDATE_CURRENT_MENU_LIST} from '@/store/constants'
import { mapGetters } from 'vuex'


export default {
  name: 'HomeUserInfo',
  data() {
    return {}
  },
  components: {},
  mounted() {},
  computed: {
    ...mapGetters([USER_INFO,CORPORATE_INFO]),
  },
  methods: {
    logOut() {
      // 退出登录
      this.$store.commit(M_UPDATE_USER_INFO, null)
      this.$message.warning({
        message: '退出登录成功！',
        center: true,
        type: 'warning',
      })
      this.handleJumpRoute("首页", "/home");
    },
    handleJumpRoute(name, path) {
      this.$store.commit(M_UPDATE_CURRENT_MENU_LIST, [
        {
          name,
          path,
        },
      ])
      this.$router.push(path)
    },
  },
}
</script>

<style lang="scss" scoped>
.user-info {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  font-size: 12px;
  line-height: 32px;
  background-color: rgba(#00156d, 0.9);
  display: flex;
  align-items: center;
  justify-content: flex-end;
  padding-right: 136px;
  font-weight: 700;
  color: #fff;
  button {
    color: var(--color-secondary);
    margin-left: 40px;
  }
}
</style>
