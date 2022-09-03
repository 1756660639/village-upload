import Vue from 'vue'
import Vuex from 'vuex'

// import user from './moduls/user.js' //  用户模块
import process from './moduls/process.js' //  流程模块

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    //
  },
  getters: {

  },
  mutations: {
    //
  },
  actions: {
    //
  },
  modules: {
    // user
    process
  }
})
