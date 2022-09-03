<template>
  <div id="background" class="background">
    <!--顶部按钮start-->
    <div class="button">
      <el-button type="primary" size="medium" @click="addFilePath">新建文件夹<i class="el-icon-folder-add el-icon--right" /></el-button>
      <el-button type="primary" size="medium" @click="uploads(0)">上传<i class="el-icon-upload el-icon--right" /></el-button>
      <el-button type="danger" size="medium" @click="quit">退出登录<i class="el-icon-circle-close el-icon--right" /></el-button>

      <router-link :to="{name:'FlowProcess'}">map</router-link>
    </div>
    <!-- 面包屑导航 -->
    <el-breadcrumb separator=">">
      <el-breadcrumb-item v-for="(item,index) in breadcrumb" :key="index" :to="{}" @click.native="breadcrumbClick(item,index)">{{ item.name }}</el-breadcrumb-item>
    </el-breadcrumb>
    <!--顶部按钮end-->
    <global-uploader :relative-path="filePath" :parent-id="parentId" @query-file="queryFile" />

    <!--数据展示-->
    <meshing-list :file-data-list="fileData" @file-open="fileOpen" />
  </div>
</template>
<script>

import request from '@/api/file'
import login from '@/api/login'
import globalUploader from './component/GlobalUploader.vue'
import util from '@/utils/util.js'
import meshingList from './component/meshingList.vue'

export default {
  name: 'FileIndex',
  components: {
    globalUploader,
    meshingList
  },
  data() {
    return {
      filePath: '/', // 当前文件路径
      parentId: '-1', // 当前的父id
      fileData: [],
      breadcrumb: [{ parentId: '-1', name: '首页' }] // 面包屑导航
    }
  },
  created() {
    // 查询个人文件
    this.queryFile()
  },
  methods: {
    // 打开选择文件框
    uploads(type) {
      util.Bus.$emit('openUploader', { type: 0 })
    },
    // 根据用户查询文件
    queryFile() {
      // debugger
      request.queryFile({ 'parentId': this.parentId }).then(response => {
        if (response.success) {
          if (response.data.length !== 0) {
            this.parentId = response.data[0].parentId
            this.filePath = response.data[0].relativePath
          }
          this.fileData = response.data
        } else {
          util.errorMessage
        }
      })
    },
    // 添加文件夹显示
    addFilePath() {
      const _this = this
      this.$prompt('请输入文件夹名称', '添加', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        // 文件夹名称不能为空判断
        if (!util.checkNull(value)) {
          util.warnMessage('请输入文件夹名称')
          return
        }
        // 添加的信息
        const data = {
          'parentId': _this.parentId,
          'relativePath': _this.filePath,
          'name': value
        }
        request.addFilePath(data).then(response => {
          if (response.success) {
            util.successMessage('新建' + value + '成功')
            this.queryFile()
          } else {
            util.errorMessage
          }
        })
      }).catch(() => {
        util.warnMessage('取消输入')
      })
    },
    /**
     * 双击打开文件夹/文件
     * file 点击的文件信息
     */
    fileOpen(file) {
      file = file.file
      this.parentId = file.id

      // debugger
      // 文件夹则进入文件夹
      if (file.isFile === 0) {
        // 放进面包屑导航中
        this.breadcrumb.push({
          parentId: file.id,
          name: file.oldName
        })
        // 重新获取数据
        this.queryFile()
        // 拼接当前相对路径
        this.filePath = this.filePath + '/' + file.oldName
      } else {
        const { href } = this.$router.resolve({
          name: 'Onlyoffice',
          query: {
            id: file.id,
            ot: 'detail'
          }
        })
        window.open(href, '_blank')
      }
    },
    /**
     * 点击面包屑导航刷新文件
     * data面包屑数据
     * index点击的第几个的索引
     */
    breadcrumbClick(data, index) {
      this.parentId = data.parentId
      this.breadcrumb.splice(index + 1)
      this.queryFile()
    },
    /** 退出登录 */
    async quit() {
      this.$router.replace('/login')
      await login.quitLogin().then(response => {
      })
      util.delStorage('tokenVal')
    }
  }
}
</script>

<style lang="stylus" scoped>
.background{
  width: 96vw;
  margin: 0 auto;
}
.button{
  margin-top: 20px;
}
</style>
