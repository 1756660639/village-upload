<template>
  <div class="app-container">
    <el-backtop />
    <el-row :gutter="20">
      <el-col :span="16">
        <el-button-group :class="{ 'hasFix': needFix }" style="margin-bottom: 10px;z-index: 999">
          <el-button type="primary" @click="onSave">保 存</el-button>
          <el-button @click="goBack">返 回</el-button>
        </el-button-group>
        <el-form
          ref="dialogForm"
          :rules="formRules"
          :model="formData"
          size="mini"
          label-position="top"
        >
          <el-form-item prop="groupId" label="组名称">
            <el-select
              v-model="formData.groupId"
              placeholder="选择模板所在组"
            >
              <el-option
                v-for="item in groupData"
                :key="item.id"
                :label="item.groupName"
                :value="item.id"
              >
                {{ item.groupName }}
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item prop="name" label="模板名称">
            <el-input v-model="formData.name" show-word-limit maxlength="64" />
          </el-form-item>
          <el-form-item prop="folder" label="文件目录">
            <el-input v-model="formData.folder" placeholder="为空则是模板名" show-word-limit maxlength="64" />
          </el-form-item>
          <el-form-item prop="fileName" label="文件名称">
            <el-input v-model="formData.fileName" placeholder="可使用velocity变量" show-word-limit maxlength="100" />
          </el-form-item>
          <el-form-item prop="content" label="模板内容">
            <!-- <el-link type="primary" :underline="false" href="https://www.cnblogs.com/codingsilence/archive/2011/03/29/2146580.html" target="_blank">Velocity语法</el-link> -->
            <codemirror
              ref="editor"
              v-model="formData.content"
              :options="cmOptions"
            />
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="8">
        <div :class="{ 'hasFix': needFix }">
          <h3>
            Velocity变量
            <span class="velocity-tip">
              点击变量直接插入
            </span>
          </h3>
          <el-tabs v-model="activeName" type="card" @tab-click="onTabClick">
            <el-tab-pane v-for="item in velocityConfig" :key="item.name" :label="item.label" :name="item.name" :content="item" />
          </el-tabs>
          <div class="velocity-var">
            <el-collapse v-model="collapseActiveName" accordion>
              <el-collapse-item v-for="(item, index) in treeData" :key="item.expression" :title="item.expression" :name="`${index}`">
                <li v-for="child in item.children" :key="child.expression">
                  <a @click="onExpressionClick(child.expression)">{{ child.expression }}</a>：{{ child.text }}
                </li>
              </el-collapse-item>
            </el-collapse>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { codemirror } from 'vue-codemirror'
import 'codemirror/theme/neat.css'
require('codemirror/mode/velocity/velocity')

import { queryTemplateById, queryTemplateGroupList, templateAdd, templateUdp } from '@/api/codeGen/index.js'
import util from '@/utils/util.js'
export default {
  components: { codemirror },
  data() {
    return {
      groupData: [],
      formData: {
        id: 0,
        groupId: '',
        groupName: '',
        name: '',
        folder: '',
        fileName: '',
        content: ''
      },
      formRules: {
        groupId: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        fileName: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      },
      cmOptions: {
        value: '',
        mode: 'text/velocity',
        theme: 'neat',
        lineNumbers: true,
        readOnly: false
      },
      isVelocityBarFixed: false,
      // tree
      activeName: 'java',
      collapseActiveName: '0',
      treeData: [],
      velocityConfig: [],
      defaultProps: {
        children: 'children',
        label: 'text'
      },
      needFix: false
    }
  },
  created() {
    const id = this.$route.query.id || 0
    if (id !== 0) {
      queryTemplateById({ 'id': id }).then(resp => {
        this.formData = resp.data
      })
    }
    this.loadVelocityVar()
    this.loadGroups(this.$route.query.groupId)
  },
  mounted() {
    window.addEventListener('scroll', this.handlerScroll)
  },
  methods: {
    handlerScroll() {
      const scrollTop = window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop
      this.needFix = scrollTop > 80
    },
    // Velocity变量数据
    loadVelocityVar() {
      const csharp = require('../static/csharp.json')
      const java = require('../static/java.json')
      this.velocityConfig.push({
        name: 'java',
        label: 'Java变量',
        data: java.data
      })
      this.treeData = java.data
      this.velocityConfig.push({
        name: 'csharp',
        label: 'C#变量',
        data: csharp.data
      })
    },
    // 获取模板分组
    loadGroups(groupId) {
      queryTemplateGroupList().then(resp => {
        this.groupData = resp.data
        if (!groupId && this.groupData.length > 0) {
          groupId = this.groupData[0].id
        }
        if (groupId && !this.formData.groupId) {
          this.formData.groupId = groupId
        }
      })
    },
    // 点击插入表达书
    onExpressionClick(exp) {
      const codemirror = this.$refs.editor.codemirror
      // 插入表达式
      codemirror.replaceSelection(exp)
      // 重新获得光标
      codemirror.focus()
    },
    // 保存模板
    onSave() {
      this.$refs.dialogForm.validate((valid) => {
        if (valid) {
          const opt = this.formData.id ? 'update' : 'add'
          if (opt === 'update') {
            templateUdp(this.formData).then(resp => {
              if (resp.success) {
                util.successMessage('修改成功！')
              }
            })
          } else {
            templateAdd(this.formData).then(resp => {
              if (resp.success) {
                util.successMessage('添加成功！')
                this.formData.id = resp.data.id
              }
            })
          }
        }
      })
    },
    // Velocity变量tab切换方法
    onTabClick(tab) {
      const item = tab.$attrs.content
      this.treeData = item.data
    },
    // 返回列表
    goBack: function(row) {
      this.$router.push({ path: '/sysAdmin/templateList', query: { id: this.formData.groupId }})
    }
  }
}
</script>

<style lang="stylus" scoped>
  .app-container {
    padding: 20px;
    >>> .CodeMirror{
      height: auto
      border: 1px solid #eee
    }
    .el-form-item--mini .el-form-item__content,
    .el-form-item--mini .el-form-item__label,
    .el-form-item__content {
    line-height: 20px;
    }
    .velocity-tip {
      color: #606266;
      font-size: 13px;
      font-weight: normal;
    }
    .velocity-var li {
      font-size: 14px;
      color: #606266;
      line-height: 26px;
    }
    .velocity-var a {
      color: #409EFF;
      font-weight: 500;
    }
    .velocity-var a:hover {
      color: rgba(64, 158, 255, 0.75);
    }
    .hasFix {
      position: fixed;
      top: 0;
    }
  }

</style>
