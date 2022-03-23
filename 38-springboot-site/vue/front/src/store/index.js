import config from '@/config'
import { LOCAL_STORAGE_KEY_HMHN } from '@/config/constants'
import { createStore, createLogger } from 'vuex'
import createPersistedState from 'vuex-persistedstate'

import state from './state/index.js'
import mutations from './mutations/index.js'
import getters from './getters/index.js'
import actions from './actions/index.js'
import modules from './modules/index.js'

const debug = process.env.NODE_ENV !== 'production'

const store = createStore({
  state,
  mutations,
  getters,
  actions,
  modules,
  plugins: debug
    ? [
        createLogger(),
        createPersistedState({
          key: LOCAL_STORAGE_KEY_HMHN,
          storage: window.localStorage,
        }),
      ]
    : [
        createPersistedState({
          key: LOCAL_STORAGE_KEY_HMHN,
          storage: window.localStorage,
        }),
      ],
})
export default store
