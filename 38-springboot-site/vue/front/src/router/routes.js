import notFoundRoute from './404'
import unauthorizedRoute from './401'
import serverInternalErrorRoute from './500'
import demoRoute from './demo'
import cityRoute from './city'
import enterprisesRoute from './enterprises'
import propagandaRoute from './propaganda'
import newsRoute from './news'

import contactRoute from './contact'
import homeRoute from './home'
import loginRoute from './login'
import policyRoute from './policy'
import communityRoute from './community'
import underDevelopmentRoute from './under-development'
import registerRoute from './register'
import applyRoute from './apply'
import resetPasswordRoute from './resetpassword'
import calculatorRoute from './calculator'

const routes = [
  cityRoute,
  demoRoute,
  contactRoute,
  calculatorRoute,
  resetPasswordRoute,
  ...enterprisesRoute,
  propagandaRoute,
  newsRoute,
  ...homeRoute,
  policyRoute,
  serverInternalErrorRoute,
  notFoundRoute,
  unauthorizedRoute,
  communityRoute,
  underDevelopmentRoute,
  loginRoute,
  registerRoute,
  applyRoute,
]
export default routes
