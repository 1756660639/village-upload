import Vue from 'vue'
import { Message } from 'element-ui'
const Bus = new Vue()

// 校验字符是否为空
const checkNull = (v) => {
  if (v === '' || v === null || v === undefined || v === 'null') {
    return false
  }
  return true
}

// 异常信息
const errorMessage = (v) => {
  if (checkNull(v)) {
    v = '请求服务器失败！'
  }
  Message({
    showClose: true,
    message: v,
    type: 'error'
  })
}

// 成功信息
const successMessage = (value) => {
  Message({
    showClose: true,
    type: 'success',
    message: value
  })
}

// 警告信息
const warnMessage = (value) => {
  Message({
    showClose: true,
    type: 'info',
    message: value
  })
}

// 驼峰命名转中划线字符串
const kebabCase = (str) => {
  return str.replace(/[A-Z]/g, (m) => `-${m.toLowerCase()}`)
}

// 设置缓存
const setStorage = (key, value) => {
  window.localStorage.setItem(key, value)
}

// 获取缓存
const getStorage = (key) => {
  return window.localStorage.getItem(key)
}

// 删除缓存
const delStorage = (key) => {
  window.localStorage.removeItem(key)
}

// 根据路由path获取其组件
const importWorkFlow = () => {
  const requireComponents = require.context('@/router', true, /index\.js$/)
  const formLs = []
  try {
    requireComponents.keys().forEach(fileName => {
      const reqCom = requireComponents(fileName)
      const routers = reqCom.default.options.routes
      routers.forEach((com) => {
        if (com.children) {
          const ls = com.children
          const pUrl = com.path
          ls.forEach(item => {
            formLs.push({ route: pUrl + '/' + item.path, component: item.component })
          })
        } else {
          const item = com
          formLs.push({ route: item.path, component: item.component })
        }
      })
    })
  } catch (error) {
    console.log('工作流导入文件失败', error)
  }

  return {
    formLs
  }
}

// 时间格式化
const parseTime = (time, cFormat) => {
  const format = cFormat || '{y}-{m}-{d}'
  let date
  if (typeof time === 'undefined' || time === null || time === 'null' || time === '/') {
    return ''
  } else if (typeof time === 'object') {
    date = time
  } else {
    if (typeof time === 'string' && /^[0-9]+$/.test(time)) {
      time = parseInt(time)
    }
    if (typeof time === 'number' && time.toString().length === 10) {
      time = time * 1000
    }

    date = new Date(time)

    if (!date.getFullYear()) {
      // ie下兼容
      date = new Date(time.replace(/-/g, '/'))
    }
  }

  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }

  const timeStr = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }

    return value || 0
  })

  return timeStr
}

// 计算时间差 天-时-分-秒
const timeDifference = (endDate, startDate) => {
  var date3 = new Date(endDate).getTime() - new Date(startDate).getTime() // 时间差的毫秒数

  // 计算出相差天数
  var days = Math.floor(date3 / (24 * 3600 * 1000))
  // 计算出小时数
  var leave1 = date3 % (24 * 3600 * 1000) // 计算天数后剩余的毫秒数
  var hours = Math.floor(leave1 / (3600 * 1000))
  // 计算相差分钟数
  var leave2 = leave1 % (3600 * 1000) // 计算小时数后剩余的毫秒数
  var minutes = Math.floor(leave2 / (60 * 1000))
  // 计算相差秒数
  var leave3 = leave2 % (60 * 1000) // 计算分钟数后剩余的毫秒数
  var seconds = Math.round(leave3 / 1000)
  var timeString = ''
  if (days > 0) {
    timeString += days + '天'
  }
  if (hours > 0) {
    timeString += hours + '小时'
  }
  if (minutes > 0) {
    timeString += minutes + '分钟'
  }
  if (minutes > 0) {
    timeString += seconds + '秒'
  }
  return timeString
}

export default {
  checkNull,
  errorMessage,
  successMessage,
  warnMessage,
  kebabCase,
  setStorage,
  getStorage,
  delStorage,
  Bus,
  parseTime,
  timeDifference
}
export {
  checkNull,
  errorMessage,
  successMessage,
  warnMessage,
  kebabCase,
  setStorage,
  getStorage,
  delStorage,
  Bus,
  importWorkFlow,
  parseTime,
  timeDifference
}

/**
 * @param {*} obj 二进制文件流
 * @param {string} name 文件名称
 * @param {string} suffix 文件后缀
 */
export function downloadFile(obj, name, suffix) {
  debugger
  try {
    const fileName = name + '.' + suffix

    if (window.navigator && (window).navigator.msSaveOrOpenBlob) {
      (window).navigator.msSaveOrOpenBlob(obj, fileName)
    } else {
      const url = window.URL.createObjectURL(new Blob([obj]))
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url

      const fileName = name + '.' + suffix
      link.setAttribute('download', fileName)

      document.body.appendChild(link)

      link.click()
      document.body.removeChild(link)
    }
  } catch (e) {
    console.log(e, '----')
  }
}

export function downLoadFiles(data, filename) {
  debugger
  // var blob = new Blob([data], {type: 'application/vnd.ms-excel'})接收的是blob，若接收的是文件流，需要转化一下
  if (typeof window.chrome !== 'undefined') {
    // Chrome version
    const link = document.createElement('a')
    link.href = window.URL.createObjectURL(data)
    link.download = filename
    link.click()
  } else if (typeof (window).navigator.msSaveBlob !== 'undefined') {
    // IE version
    const blob = new Blob([data], { type: 'application/force-download' });
    (window).navigator.msSaveBlob(blob, filename)
  } else {
    // Firefox version
    const file = new File([data], filename, { type: 'application/force-download' })
    window.open(URL.createObjectURL(file))
  }
}

