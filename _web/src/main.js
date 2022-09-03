import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from '@/store/index.js'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
/**
 * vue-simple-uploader 中文官方文档 https://github.com/simple-uploader/vue-uploader/blob/master/README_zh-CN.md
 * simple-uploader.js 中文官方文档 https://github.com/simple-uploader/Uploader/blob/develop/README_zh-CN.md
 */
import uploader from 'vue-simple-uploader'
import $ from 'jquery'
Vue.config.productionTip = false
Vue.use($)
Vue.use(ElementUI)
Vue.use(uploader)
/* 百度地图 */
/* import BaiduMap from 'vue-baidu-map'

Vue.use(BaiduMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key
  ak: 'YOUR_APP_KEY'
}) */

/* Echarts图表 */
import echarts from 'echarts'
import 'echarts/extension/bmap/bmap'
Vue.prototype.$echarts = echarts

/* 代码编辑 */
import VueCodeMirror from 'vue-codemirror'
import 'codemirror/lib/codemirror.css'
Vue.use(VueCodeMirror)

/** 图片查看 */
import duoImageViewer from '@/components/imgView/index.js'
Vue.use(duoImageViewer)

/** vue-video-player */

import VideoPlayer from 'vue-video-player'
import 'vue-video-player/src/custom-theme.css'
import 'video.js/dist/video-js.css'
Vue.use(VideoPlayer)

Vue.prototype.$deepCopy = function(obj) { return JSON.parse(JSON.stringify(obj)) }

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
