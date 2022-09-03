<template>
  <div class="app-container">
    <el-form ref="recordForm" :model="recordForm" status-icon label-width="100px" class="demo-ruleForm" :disabled="editType()==='detail'?true:false">
      <el-form-item label="类型">
        <el-select v-model="recordForm.type" placeholder="类型" style="width: 100%;">
          <el-option label="日用品" value="日用品" />
          <el-option label="吃饭" value="吃饭" />
          <el-option label="房租" value="房租" />
          <el-option label="游玩门票" value="游玩门票" />
          <el-option label="水电/通讯费" value="水电/通讯费" />
          <el-option label="服饰" value="服饰" />
          <el-option label="养车" value="养车" />
        </el-select>
      </el-form-item>
      <el-form-item label="来源">
        <el-select v-model="recordForm.source" placeholder="来源" style="width: 100%;">
          <el-option label="现金" value="现金" />
          <el-option label="支付宝" value="支付宝" />
          <el-option label="微信" value="微信" />
          <el-option label="云闪付" value="云闪付" />
          <el-option label="银行卡" value="银行卡" />
        </el-select>
      </el-form-item>
      <el-form-item label="金额">
        <el-input v-model="recordForm.amountOfMoney" />
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="recordForm.remark" type="textarea" />
      </el-form-item>
    </el-form>
    <el-button v-if="editType()==='detail'?false:true" type="primary" @click="recordSubmit">提交</el-button>
    <el-button @click="back">返回</el-button>
  </div>
</template>
<script>

import { insert, update, findById } from '@/api/record'
import { successMessage, errorMessage } from '@/utils/util.js'

export default {
  name: 'RecordEdit',
  components: { },
  props: {
    propDataId: {
      type: String,
      default: ''
    },
    propEditType: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      recordForm: { type: '', source: '', amountOfMoney: '', remark: '' }
    }
  },
  created() {
    this.findById()
  },
  methods: {
    recordSubmit() {
      if (this.type === 'insert') {
        insert(this.recordForm).then(response => {
          if (response.success) {
            successMessage('提交成功')
            this.recordForm = { type: '', source: '', amountOfMoney: '', remark: '' }
          } else {
            errorMessage
          }
        })
      } else if (this.type === 'update') {
        update(this.recordForm).then(response => {
          if (response.success) {
            successMessage('提交成功')
            this.recordForm = { type: '', source: '', amountOfMoney: '', remark: '' }
          } else {
            errorMessage
          }
        })
      }
      this.$router.push({ path: '/sysAdmin/approvateUserList', query: { dataId: '566774fe-4d18-11ec-acd0-00163e1668fd', lcid: 'e5854eb62a5a4499b6047d8b4914ae69', type: 'update', backUrl: '/sysAdmin/recordList' }})
    },
    findById() {
      const id = this.isEdit()
      findById({ id }).then((resp) => {
        this.recordForm = resp.data
      })
    },
    isEdit() {
      if ((this.$route.query && (this.$route.query.dataid || this.$route.query.dataId)) || this.propDataId) {
        return this.$route.query.dataid || this.$route.query.dataId || this.propDataId
      }
      return false
    },
    editType() {
      if ((this.$route.query && this.$route.query.type) || this.propEditType) {
        return this.$route.query.type || this.propEditType
      }
      return false
    },
    back() {
      this.$router.push({ path: '/sysAdmin/recordList', query: { }})
    }
  }
}
</script>
<style lang="stylus" scoped>

</style>
