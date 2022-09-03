<template>
  <div id="login" class="login">
    <el-form v-show="isLogin" ref="loginForm" :model="loginForm" status-icon label-width="100px" class="demo-ruleForm">
      <el-form-item label="账号" prop="account">
        <el-input v-model="loginForm.account" type="text" autocomplete="off" />
      </el-form-item>
      <el-form-item label="密码" prop="pwd">
        <el-input v-model="loginForm.pwd" type="password" autocomplete="off" />
      </el-form-item>
      <el-form-item>
        <el-button v-if="IsPC()" type="primary" @click="useVerify">提交</el-button>
        <el-button v-if="!IsPC()" type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm('loginForm')">重置</el-button>
      </el-form-item>
    </el-form>

    <Verify
      ref="verify"
      :captcha-type="verifyType"
      :img-size="{width:'400px',height:'200px'}"
      @success="submitForm"
    />
    <div id="map" ref="map" style="width:500px;height:500px" />
  </div>
</template>
<script>
import CanvasNest from 'canvas-nest.js'
import request from '@/api/login'
import util from '@/utils/util.js'
import Verify from '@/components/verifition/Verify'
// 配置
const config = {
  color: '64, 158, 255', // 线条颜色
  pointColor: '64, 158, 255', // 节点颜色
  opacity: 0.6, // 线条透明度
  count: 66, // 线条数量
  zIndex: -1 // 画面层级
}
export default {
  name: 'Login',
  components: {
    Verify
  },
  data() {
    return {
      loginForm: { account: '', pwd: '' },
      verifyType: 'blockPuzzle', // 验证码类型
      isLogin: true,
      scene: null
    }
  },
  created() {
    //  绘制背景图
    this.$nextTick(() => {
      const element = document.getElementById('login')
      new CanvasNest(element, config)
    })
    if (this.IsPC()) {
      this.verifyType = 'blockPuzzle'
    } else {
      this.verifyType = 'clickWord'
    }
    this.checkLogin()
  },
  methods: {
    checkLogin() {
      if (util.checkNull(util.getStorage('tokenVal'))) {
        request.checkLogin().then(resp => {
          if (resp.success) {
            this.$router.replace('/file')
          }
        })
      }
    },
    // 显示验证码弹窗
    useVerify() {
      this.$refs.verify.show()
    },
    // 提交登陆信息
    submitForm() {
      request.login(this.loginForm).then(response => {
        if (response.success) {
          util.setStorage('tokenName', response.data.tokenName)
          util.setStorage('tokenVal', response.data.tokenValue)
          this.$router.replace('/file')
        } else {
          util.errorMessage
        }
      })
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    // 判断登陆端设备
    IsPC() {
      var userAgentInfo = navigator.userAgent
      var Agents = ['Android', 'iPhone',
        'SymbianOS', 'Windows Phone',
        'iPad', 'iPod']
      var flag = true
      for (var v = 0; v < Agents.length; v++) {
        if (userAgentInfo.indexOf(Agents[v]) > 0) {
          flag = false
          break
        }
      }
      return flag
    }
  }
}
</script>

<style lang="stylus" scoped>
.login{
  width: 96vw
  height: 96vh
}
</style>
