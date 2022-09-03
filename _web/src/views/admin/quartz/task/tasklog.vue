<template>
  <el-table
    :data="tableData"
    style="width: 100%"
    :row-class-name="tableRowClassName"
  >
    <el-table-column
      prop="jobName"
      label="任务名称"
    />
    <el-table-column
      prop="beanName"
      label="bean名称"
    />
    <el-table-column
      prop="methodName"
      label="执行方法"
    />
    <el-table-column
      prop="cronExpression"
      label="cron表达式"
    />
    <el-table-column
      prop="time"
      label="执行时间"
    />
    <el-table-column
      prop="exceptionDetail"
      label="错误信息"
    />
  </el-table>
</template>

<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>

<script>
import { queryAllLogData } from '@/api/admin/task/index.js'
export default {
  data() {
    return {
      tableData: []
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      queryAllLogData().then(resp => {
        if (resp.success) {
          this.tableData = resp.data
        }
      })
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.isSuccess === '0') {
        return 'warning-row'
      }
      return ''
    }
  }
}
</script>
