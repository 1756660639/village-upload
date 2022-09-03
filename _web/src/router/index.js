import Vue from 'vue'
import VueRouter from 'vue-router'
import util from '@/utils/util.js'

const originalPush = VueRouter.prototype.push

VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(VueRouter)

const routes = [
  { path: '*', redirect: '/login' }, // 默认路由跳转到首页
  {
    path: '/', /* 默认进入路由（写在第一个）*/
    redirect: '/login'/* 重定向*/
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue')
  },
  {
    path: '/file',
    name: 'File',
    component: () => import('@/views/file/index.vue')
  },
  {
    path: '/onlyoffice',
    name: 'Onlyoffice',
    component: () => import('@/views/onlyOffice/index.vue')
  },
  {
    path: '/sysAdmin',
    name: 'SysAdmin',
    component: () => import('@/views/admin/index.vue'),
    children: [
      {
        path: 'menu',
        name: 'SysAdminMenu',
        component: () => import('@/views/admin/menu/index.vue')
      },
      {
        path: 'config',
        name: 'Config',
        component: () => import('@/views/codeGen/generate/index.vue') // 生成代码
      },
      {
        path: 'result',
        name: 'Result',
        component: () => import('@/views/codeGen/generate/result.vue') // 生成结果
      },
      {
        path: 'templateList',
        name: 'TemplateList',
        component: () => import('@/views/codeGen/gentemplate/templateList.vue') // 模板管理
      },
      {
        path: 'templateEdit',
        name: 'TemplateEdit',
        component: () => import('@/views/codeGen/gentemplate/templateEdit.vue') // 编辑模板
      },
      {
        path: 'quartz',
        name: 'Quartz',
        component: () => import('@/views/admin/quartz/index.vue') // 定时任务
      },
      {
        path: 'flowFrom',
        name: 'FlowFrom',
        component: () => import('@/views/process/flowFrom.vue') // 表单管理
      },
      {
        path: 'flowProcess',
        name: 'FlowProcess',
        component: () => import('@/views/process/flowProcess.vue') // 流程管理
      },
      {
        path: 'processDesign',
        name: 'ProcessDesign',
        component: () => import('@/views/process/ProcessDesign.vue') // 流程设计
      },
      {
        path: 'approvateUserList',
        name: 'ApprovateUserList',
        component: () => import('@/views/process/common/processModule/ApprovateUserList.vue') // 发起审批
      },
      {
        path: 'recordEdit',
        name: 'RecordEdit',
        component: () => import('@/views/admin/record/recordEdit.vue') // 费用审批编辑
      },
      {
        path: 'recordList',
        name: 'RecordList',
        component: () => import('@/views/admin/record/recordList.vue') // 费用审批台账
      },
      {
        path: 'tts',
        name: 'Tts',
        component: () => import('@/views/admin/tts/index.vue') // 文字转语音
      }
    ]
  },
  {
    path: '/map',
    name: 'Map',
    component: () => import('@/views/admin/index.vue'),
    children: [
      {
        path: 'trajectory',
        name: 'Trajectory',
        component: () => import('@/views/map/trajectory.vue') // 生成代码
      }
    ]
  }
]

const router = new VueRouter({
  // mode: 'history',
  routes
})

/** 导航守卫，如果缓存中不存在token则直接跳转登陆页面 */
router.beforeEach(function(to, from, next) {
  if (!util.getStorage('tokenVal')) {
    if (to.path !== '/login') {
      return next('/login')
    }
  }
  next()
})

export default router
