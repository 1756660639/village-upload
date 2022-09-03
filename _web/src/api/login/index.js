import { requestGet, requestPost } from '@/api/request'

// 获取验证图片  以及token
const captchaGet = data => requestPost('captcha/get', data)

// 滑动或者点选验证
const captchaCheck = data => requestPost('captcha/check', data)

// 登陆
const login = data => requestPost('login', data)

const quitLogin = data => requestGet('login/quitLogin')

const record = data => requestPost('login/record', data)

const checkLogin = data => requestGet('login/checkLogin', data)

export default { captchaGet, captchaCheck, login, quitLogin, record, checkLogin }
