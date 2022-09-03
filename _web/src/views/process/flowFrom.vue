<template>
  <div>
    <el-table
      :data="tableData"
      style="width: 100%"
      :row-class-name="tableRowClassName"
    >
      <el-table-column
        prop="lcName"
        label="流程名称"
      />
      <el-table-column
        prop="lcfaTime"
        label="发起时间"
      >
        <template slot-scope="scope">
          {{ parseDate(scope.row.lcfaTime,'{y}-{m}-{d} {h}:{i}:{s}') }}
        </template>
      </el-table-column>
      <el-table-column
        prop="userName"
        label="发起人"
      />
      <el-table-column
        prop="lzsj"
        label="流转时间"
      >
        <template slot-scope="scope">
          {{ parseDate(scope.row.lzsj,'{y}-{m}-{d} {h}:{i}:{s}') }}
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="approve(scope.row.id)">审批</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
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
import { getApprovalPending } from '@/api/process/index.js'
import utils from '@/utils/util.js'
export default {
  name: 'FlowFrom',
  data() {
    return {
      // 表格数据
      tableData: []
    }
  },
  created() {
    this.getData()
  },
  methods: {
    // 时间格式化
    parseDate(time, cFormat) {
      if (!utils.checkNull(time)) {
        return ''
      }
      return utils.parseTime(time, cFormat)
    },
    // 获取数据
    getData() {
      getApprovalPending().then(resp => {
        if (resp.success) {
          this.tableData = resp.data
        } else {
          utils.errorMessage()
        }
      })
    },
    // 审批
    approve() {

    }
  }
}
</script>
