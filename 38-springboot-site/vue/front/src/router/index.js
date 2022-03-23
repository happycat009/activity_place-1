import { USER_INFO } from '@/store/constants'
import { getVuexByLocalStorage } from '@/utils/get'
import { createRouter, createWebHistory } from 'vue-router'
import { requestSaveVisitLog } from '@/service'
import routes from './routes'

const router = createRouter({
  history: createWebHistory(),
  routes,
})

// 路由拦截 拦截未登录访问需登录页面
router.beforeEach((to, from, next) => {
  let user_id = ''
  let orgid = ''
  const userInfo = getVuexByLocalStorage(USER_INFO) //获取localStorage浏览器里的userInfo的值
  console.log("window.location.host=========",window.location.host)
  console.log("to=========",to)

  //统计访问这里不需要
  // requestSaveVisitLog(
  //   {
  //     loginid: userInfo.loginid,    //未登录为空，肯定报错噻
  //     visitdomain: window.location.host,  //主机名+端口号 http://localhost:3000/
  //     visiturl: to.fullPath,    //to的全路径 /home
  //     systemtype: '1', // 网站
  //     visitchanneltype: '1', // 自主访问 TODO 获取访问途径
  //   },
  //   false
  // )

  if (userInfo) {
    user_id = userInfo.user_id
    orgid = userInfo.orgid
  }
  console.log("用户信息-orgid",orgid)

  const { meta, fullPath } = to
  const { title, requireAuth } = meta   //mate 路由的附加信息
  if (title) {
    document.title = title    //设置浏览器的标题title
  }
  // 判断该路由是否需要登录权限 requireAuth 可以在路由元信息指定哪些页面需要登录权限
  if (requireAuth) {
    // 是否已登录
    if (!user_id) {
      //没有用户id就重定向登陆
      next({
        path: '/login',
        query: { redirect: fullPath }, // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
      return
    }

    if (!orgid) {
      //没有orgid就重定向未审核完成界面
      next({
        path: '/401',
        query: { redirect: fullPath }, // 将跳转的路由path作为参数，登录成功后跳转到该路由
      })
      return
    }
    next()
  } else {
    next()
  }
})

export default router
