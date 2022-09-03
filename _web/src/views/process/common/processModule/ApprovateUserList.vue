<template>
  <div class="">
    <!-- 表单 -->
    <component :is="currentComponent" :prop-data-id="dataId()" prop-edit-type="detail" />
    <!-- 步骤 -->
    <el-timeline>
      <el-timeline-item v-for="(item,index) in approveNode" :key="index" :timestamp="item.nodeName" placement="top">
        <!-- 发起审批设置审批人显示 -->
        <el-card v-if="editType() === 'update'">
          <span>审批人:</span><span>{{ item.approveName }}</span><span class="ml100">部门:</span><span>{{ item.approveDept }}</span>
          <el-popover
            placement="left"
            title="选择审批人"
            width="400"
            trigger="click"
          >
            <el-select
              v-model="item.userSelectValue"
              :multiple="true"
              filterable
              remote
              reserve-keyword
              placeholder="请输入姓名查询"
              :remote-method="remoteMethod"
              :loading="loading"
              @change="selectUser($event,item)"
            >
              <el-option
                v-for="itemUser in optionsUser"
                :key="itemUser.value"
                :label="itemUser.label"
                :value="itemUser"
              />
            </el-select>
            <el-button slot="reference" class="flr" size="mini" type="primary" icon="el-icon-edit">调整审批人</el-button>
          </el-popover>

        </el-card>
        <!-- 查看详情时显示详细信息 -->
        <el-card v-if="editType() === 'detail'">
          <table>
            <tr>
              <td><i class="el-icon-user" />审批人</td>
              <td>{{ item.approveName }}<span class="tag">{{ item.approveDept }}</span></td>
            </tr>
            <tr>
              <td><i class="el-icon-date" />接收时间</td>
              <td>{{ parseDate(item.startDate,'{y}-{m}-{d} {h}:{i}:{s}') }}</td>
            </tr>
            <tr>
              <td><i class="el-icon-date" />处理时间</td>
              <td>{{ parseDate(item.endDate,'{y}-{m}-{d} {h}:{i}:{s}') }}</td>
            </tr>
            <tr>
              <td><i class="el-icon-time" />耗时</td>
              <td>{{ timeUse(item.startDate,item.endDate) }}</td>
            </tr>
            <tr>
              <td><i class="el-icon-document-checked" />审批结果</td>
              <td>{{ state(item.state) }}</td>
            </tr>
            <tr>
              <td><i class="el-icon-edit-outline" />审批意见</td>
              <td>{{ item.idea }}</td>
            </tr>

          </table>

        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-button v-if="editType()==='detail'?false:true" type="primary" @click="saveApproveUser">提交</el-button>
    <el-button @click="back()">返回</el-button>
  </div>
</template>

<script>
import { listAllByUserNameAndDept } from '@/api/admin/user/index.js'
import { getApproveNode, saveApproveUser, findById, getApproveNodeDetail } from '@/api/process/index.js'
import { errorMessage, importWorkFlow, parseTime, checkNull, timeDifference } from '@/utils/util.js'
export default {
  name: 'ApprovateUserList',
  props: {

  },
  data() {
    return {
      // 节点信息
      approveNode: [],
      // 审批人信息
      optionsUser: [],
      // 查询审批人loading
      loading: false,
      // 当前组件实例
      currentComponent: null,
      // 当前组件url
      currentComUrl: ''
    }
  },
  created() {
    // this.getNode()
    this.fromComponent()
  },
  methods: {
    // 时间格式化
    parseDate(time, cFormat) {
      if (!checkNull(time)) {
        return ''
      }
      return parseTime(time, cFormat)
    },
    // 耗时格式化显示天-时-分-秒
    timeUse(start, end) {
      if (!checkNull(start) || !checkNull(end)) {
        return ''
      }
      return timeDifference(end, start)
    },
    // 审批状态
    state(key) {
      debugger
      switch (key) {
        case '1': return '审批中'
        case '2': return '未流转到'
        case '3': return '已审批'
        case '4': return '同意'
        case '5': return '不同意'
      }
    },
    // 组件注册
    fromComponent() {
      const { formLs } = importWorkFlow()
      if (this.editType() === 'update' || this.editType() === 'detail') {
        findById({ id: this.lcid() }).then(resp => {
        // 找表单url对应的组件渲染
          this.currentComUrl = resp.data.fromUrl
          formLs.forEach(fm => {
            if (fm.route === this.currentComUrl) {
              this.currentComponent = fm.component
            }
          })
        })
      }

      this.getNode()
    },
    // 获取审批节点
    getNode() {
      const from = {
        lcid: this.lcid(),
        dataId: this.dataId()
      }
      // 发起审批，设置审批人
      if (this.editType() === 'update') {
        getApproveNode(from).then(resp => {
          if (resp.success) {
            this.approveNode = resp.data
          } else {
            errorMessage
          }
        })
      }
      // 查看详情数据
      if (this.editType() === 'detail') {
        getApproveNodeDetail(from).then(resp => {
          if (resp.success) {
            this.approveNode = resp.data
          } else {
            errorMessage
          }
        })
      }
    },
    // 动态查询审批人
    remoteMethod(query) {
      this.loading = true
      if (query !== '') {
        listAllByUserNameAndDept({ userName: query }).then(resp => {
          if (resp.success) {
            this.optionsUser = resp.data
          } else {
            errorMessage()
          }
        })
      }
      this.loading = false
    },
    // 选择审批人
    selectUser(val, item) {
      let approveId = ''
      let approveName = ''
      let approveDept = ''
      debugger
      val.map(m => {
        approveId = approveId + m.id + ','
        approveName = approveName + m.userName + ','
        approveDept = approveDept + m.approveId + ','
      })
      item.approveId = approveId.substring(0, approveId.length - 1)
      item.approveName = approveName.substring(0, approveName.length - 1)
      item.approveDept = approveDept.substring(0, approveDept.length - 1)
    },
    // 保存审批人
    saveApproveUser() {
      const from = {
        lcglId: this.lcid(),
        dataId: this.dataId(),
        state: '1',
        flowLcsprEntities: this.approveNode
      }
      saveApproveUser(from).then(resp => {
        if (resp.success) {
          console.log('审批流发起成功')
        } else {
          console.log('审批流发起失败')
        }
      })
    },
    editType() {
      if ((this.$route.query && this.$route.query.type) || this.propEditType) {
        return this.$route.query.type || this.propEditType
      }
      return false
    },
    lcid() {
      if ((this.$route.query && (this.$route.query.lcid || this.$route.query.lcId))) {
        return this.$route.query.lcid || this.$route.query.lcId
      }
      return false
    },
    dataId() {
      if ((this.$route.query && (this.$route.query.dataid || this.$route.query.dataId))) {
        return this.$route.query.dataid || this.$route.query.dataId
      }
      return false
    },
    backUrl() {
      if ((this.$route.query && (this.$route.query.backurl || this.$route.query.backUrl))) {
        return this.$route.query.backurl || this.$route.query.backUrl
      }
      return false
    },
    back() {
      this.$router.push({ path: this.backUrl(), query: { }})
    }
  }
}
</script>

<style lang="stylus" scoped>
.ml100{
  margin-left: 100px
}
.flr{
  float: right
}
table, table tr td{
  border: 1px solid #E4E7ED
  padding: 0px
  border-collapse: collapse
  color: #909399
  width: 33em
  height: 3em
}
table tr td{
  border: 1px solid #9c9fa5
  padding: 0px
  border-collapse: collapse
  color: #585a5f
  width: 33em
  height: 3em
  padding-left: 1em
}
table tr :first-child{
  background-color: #E4E7ED
}
.tag{
  padding: 1px 3px
  background-color: #ecf5ff
  color: #409eff
  border-radius: 8px
  border: 1px solid #d9ecff;
}
</style>
