<template>
  <div class="page demo">
    <div class="demo-item">
      <div class="demo-item-name">
        element-plus 组件库使用（直接使用，无需引入）
      </div>
      <div class="demo-item-con">
        <el-button type="primary" @click="handleLogin">登录</el-button>
      </div>
    </div>
    <div class="demo-item">
      <div class="demo-item-name">切换主题色</div>
      <div class="demo-item-con">
        <el-button type="primary" @click="handleChangeTheme('green')"
          >green</el-button
        >
      </div>
    </div>
    <div class="demo-item">
      <div class="demo-item-name">vuex 使用</div>
      <div class="demo-item-con">{{ userInfo.sessionId }}</div>
    </div>
    <div class="demo-item">
      <div class="demo-item-name">axios 请求数据示例</div>
      <div class="demo-item-con">{{ axiosData }}</div>
    </div>
  </div>
</template>

<script>
import { requestGetBankInfo } from '@/service'
import { requestGetData } from '@/service'
import { USER_INFO, M_UPDATE_USER_INFO } from '@/store/constants'
import { mapGetters } from 'vuex'
export default {
  name: 'Demo',
  setup() {
    console.log('Demo setup =>')
    return {}
  },
  data() {
    console.log(this.$options.name + ' data =>')
    return {
      axiosData: null,
    }
  },
  // 在实例初始化之后、进行数据侦听和事件/侦听器的配置之前同步调用
  beforeCreate() {
    console.log(this.$options.name + ' beforeCreate =>')
  },
  // 在实例创建完成后被立即同步调用。在这一步中，实例已完成对选项的处理，意味着以下内容已被配置完毕：数据侦听、计算属性、方法、事件/侦听器的回调函数。然而，挂载阶段还没开始，且 $el property 目前尚不可用。
  created() {
    console.log(this.$options.name + ' created =>')
  },
  // 在挂载开始之前被调用：相关的 render 函数首次被调用。
  beforeMount() {
    console.log(this.$options.name + ' beforeMount =>')
  },
  // 在实例挂载完成后被调用，这时候传递给 app.mount 的元素已经被新创建的 vm.$el 替换了。如果根实例被挂载到了一个文档内的元素上，当 mounted 被调用时， vm.$el 也会在文档内。 注意 mounted 不会保证所有的子组件也都被挂载完成。如果你希望等待整个视图都渲染完毕，可以在 mounted 内部使用 vm.$nextTick：
  mounted() {
    this.getServiceData()
    console.log(this.$options.name + ' mounted =>')
  },
  // 被 keep-alive 缓存的组件激活时调用
  activated() {
    console.log(this.$options.name + ' activated =>')
  },
  // 被 keep-alive 缓存的组件失活时调用
  deactivated() {
    console.log(this.$options.name + ' deactivated =>')
  },
  // 在数据发生改变后，DOM 被更新之前被调用。这里适合在现有 DOM 将要被更新之前访问它，比如移除手动添加的事件监听器。
  beforeUpdate() {
    console.log(this.$options.name + ' beforeUpdate =>')
  },
  // 在数据更改导致的虚拟 DOM 重新渲染和更新完毕之后被调用。
  updated() {
    console.log(this.$options.name + ' updated =>')
  },
  // 在卸载组件实例之前调用。在这个阶段，实例仍然是完全正常的
  beforeUnmount() {
    console.log(this.$options.name + ' beforeUnmount =>')
  },
  // 卸载组件实例后调用。调用此钩子时，组件实例的所有指令都被解除绑定，所有事件侦听器都被移除，所有子组件实例被卸载
  unmounted() {
    console.log(this.$options.name + ' unmounted =>')
  },
  // 在捕获一个来自后代组件的错误时被调用。此钩子会收到三个参数：错误对象、发生错误的组件实例以及一个包含错误来源信息的字符串。此钩子可以返回 false 以阻止该错误继续向上传播。
  errorCaptured(err, instance, info) {
    console.log(this.$options.name + ' errorCaptured =>', err, instance, info)
  },
  // 跟踪虚拟 DOM 重新渲染时调用。钩子接收 debugger event 作为参数。此事件告诉你哪个操作跟踪了组件以及该操作的目标对象和键。
  renderTracked({ key, target, type }) {
    console.log(this.$options.name + ' renderTracked =>', key, target, type)
  },
  // 当虚拟 DOM 重新渲染被触发时调用。和 renderTracked 类似，接收 debugger event 作为参数。此事件告诉你是什么操作触发了重新渲染，以及该操作的目标对象和键。
  renderTriggered({ key, target, type }) {
    console.log(this.$options.name + ' renderTriggered =>', key, target, type)
  },
  // 声明一组可用于组件实例中的指令
  directives: {},
  // 声明一组可用于组件实例中的组件
  components: {},
  // 计算属性将被混入到组件实例中
  computed: {
    ...mapGetters([USER_INFO]),
  },
  // methods 将被混入到组件实例中
  methods: {
    // 获取服务器数据
    async getServiceData() {
      // const bankInfo = await requestGetBankInfo('510902199507236534', '曾小明')
      // this.axiosData = JSON.stringify(bankInfo)
      await requestGetData("",{
        paeam: "99955"
      })
    },
    // 测试登录
    handleLogin() {
      console.log('登录')
    },
    // 切换主题色
    handleChangeTheme() {
      console.log('登2录')
    },
  },
}
</script>

<style lang="scss" scoped>
.demo {
  padding: 20px;
  .demo-item {
    margin-bottom: 20px;
  }
  .demo-item-name {
    font-size: 20px;
    line-height: 60px;
    color: #333;
    font-weight: 500;
  }
  .demo-item-con {
    background-color: #eee;
    padding: 20px;
    white-space: normal;
    word-break: break-all;
  }
}
</style>
