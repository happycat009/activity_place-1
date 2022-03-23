import Vue from 'vue'
import App from './App.vue'

//2、导入router文件
import router from "./router";
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
