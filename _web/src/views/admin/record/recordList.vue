<template>
  <div class="app-container">
    <el-button type="text" @click="insertClick">添加</el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="type"
        label="类型"
      />
      <el-table-column
        prop="source"
        label="来源"
      />
      <el-table-column
        prop="amountOfMoney"
        label="金额"
      />
      <el-table-column
        prop="remark"
        label="备注"
      />
      <el-table-column
        prop="createTime"
        label="创建时间"
      />
      <el-table-column
        prop="approve"
        label="审批状态"
      />

      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updClick(scope.row.id)">编辑</el-button>
          <span> | </span>
          <el-button type="text" size="small" @click="delClick(scope.row.id)">删除</el-button>
          <span> | </span>
          <el-button type="text" size="small" @click="detailClick(scope.row.id)">详情</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { queryAllData, deleteById } from '@/api/record'
import { errorMessage, successMessage } from '@/utils/util.js'
export default {
  name: 'RecordList',
  components: { },
  data() {
    return {
      tableData: [] // 全部数据
    }
  },
  created() {
    this.queryAll()
  },
  methods: {
    queryAll() {
      queryAllData({}).then(response => {
        if (response.success) {
          this.tableData = response.data
        } else {
          errorMessage()
        }
      })
    },
    // 添加
    insertClick(id) {
      this.$router.push({ path: '/sysAdmin/recordEdit', query: { 'type': 'update' }})
    },
    // 编辑
    updClick(id) {
      this.$router.push({ path: '/sysAdmin/recordEdit', query: { 'dataId': id, 'type': 'update' }})
    },
    // 编辑
    detailClick(id) {
      this.$router.push({ path: '/sysAdmin/approvateUserList', query: { dataId: id, lcid: 'e5854eb62a5a4499b6047d8b4914ae69', type: 'detail', backUrl: '/sysAdmin/recordList' }})
    },
    // 删除
    delClick(id) {
      deleteById(id).then(resp => {
        if (resp.success) {
          successMessage('删除成功！')
          this.queryAll()
        }
      })
    }
  }
}
</script>
<style lang="stylus" scoped>

</style>
