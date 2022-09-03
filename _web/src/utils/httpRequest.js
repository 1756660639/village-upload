import axios from 'axios'
import basePath from '@/config/index'
import { Message } from 'element-ui'
import util from '@/utils/util.js'
import router from '@/router'

// create an axios instance
const service = axios.create({
  baseURL: basePath.baseUrl.baseApiPrefix, // url = base url + request url
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true, // send cookies when cross-domain requests
  timeout: 50000 // request timeout
})

// request interceptor
service.interceptors.request.use(config => {
  const tokenVal = util.getStorage('tokenVal')
  config.headers.villageToken = tokenVal
  return config
}, error => {
  // Do something with request error
  console.log(error) // for debug
}
)

// response interceptor
service.interceptors.response.use(
  response => {
    if (response.data.code === 4001) {
      util.delStorage('tokenVal')
      util.warnMessage('您已掉线，请重新登陆')
      router.replace('/login')
    }
    return response.data
  },
  error => {
    // console.log(error); // for debug
    Message({
      showClose: true,
      message: '请求服务器失败！',
      type: 'error'
    })
    return Promise.reject(error)
  }
)

export default service
