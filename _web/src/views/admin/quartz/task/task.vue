<template>
  <div>
    <el-button type="text" @click="insertBtn">添加</el-button>
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
        prop="isPause"
        label="状态"
        :filters="[{ text: '已暂停', value: '0' }, { text: '运行中', value: '1' }]"
        :filter-method="filterTag"
        filter-placement="bottom-end"
      >
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.isPause === '0' ? 'primary' : 'success'"
            disable-transitions
          >{{ scope.row.isPause ==='0'?'已暂停':'运行中' }}</el-tag>
        </template>
        />
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updClick(scope.row.id)">编辑</el-button>
          <span> | </span>
          <el-button type="text" size="small" @click="executionClick(scope.row.id)">执行</el-button>
          <span> | </span>
          <el-button type="text" size="small" @click="updateIsPauseClick(scope.row.id)">{{ scope.row.isPause === '0'?'恢复':'停用' }}</el-button>
          <span> | </span>
          <el-button type="text" size="small" @click="delClick(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="title"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <el-form :label-position="labelPosition" label-width="100px" :model="insertData">
        <el-form-item label="任务名称">
          <el-input v-model="insertData.jobName" />
        </el-form-item>
        <el-form-item label="bean名称">
          <el-input v-model="insertData.beanName" />
        </el-form-item>
        <el-form-item label="执行方法">
          <el-input v-model="insertData.methodName" />
        </el-form-item>
        <el-form-item label="cron表达式">
          <el-input v-model="insertData.cronExpression" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="insertData.remark" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="insertData.isPause">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeDialog">取 消</el-button>
        <el-button type="primary" @click="insertFrom">确 定</el-button>
      </span>
    </el-dialog>
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
import { queryAllData, insert, update, deleteById, execution, updateIsPause, findById } from '@/api/admin/task/index.js'
import utils from '@/utils/util.js'
export default {
  data() {
    return {
      labelPosition: 'right',
      // 表格数据
      tableData: [],
      // 添加dialog弹出框是否显示
      centerDialogVisible: false,
      // diglog 标题
      title: '',
      insertData: {
        beanName: '',
        cronExpression: '',
        isPause: '',
        jobName: '',
        methodName: '',
        params: '',
        remark: '',
        id: ''
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    // 获取数据
    getData() {
      queryAllData().then(resp => {
        if (resp.success) {
          this.tableData = resp.data
        } else {
          utils.errorMessage()
        }
      })
    },
    // 新增按钮弹出框显示
    insertBtn() {
      this.centerDialogVisible = true
      this.title = '新增'
    },
    // 关闭弹窗
    closeDialog() {
      this.centerDialogVisible = false
      this.insertData = {
        beanName: '',
        cronExpression: '',
        isPause: '',
        jobName: '',
        methodName: '',
        params: '',
        remark: '',
        id: ''
      }
    },
    // 新增
    insertFrom() {
      this.centerDialogVisible = false
      if (this.insertData.id === '') {
        insert(this.insertData).then(resp => {
          if (resp.success) {
            utils.successMessage('添加成功')
            this.insertData = {
              beanName: '',
              cronExpression: '',
              isPause: '',
              jobName: '',
              methodName: '',
              params: '',
              remark: '',
              id: ''
            }
            this.getData()
          } else {
            utils.errorMessage()
          }
        })
      } else {
        update(this.insertData).then(resp => {
          if (resp.success) {
            utils.successMessage('修改成功')
            this.insertData = {
              beanName: '',
              cronExpression: '',
              isPause: '',
              jobName: '',
              methodName: '',
              params: '',
              remark: '',
              id: ''
            }
            this.getData()
          } else {
            utils.errorMessage()
          }
        })
      }
    },
    // 编辑
    updClick(id) {
      findById({ id }).then(resp => {
        this.centerDialogVisible = true
        this.insertData = resp.data
      })
    },
    // 删除
    delClick(id) {
      deleteById(id).then(resp => {
        if (resp.success) {
          utils.successMessage('删除成功！')
          this.getData()
        }
      })
    },
    // 立即执行
    executionClick(id) {
      execution({ id }).then(resp => {
        if (resp.success) {
          utils.successMessage('执行成功')
        }
      })
    },
    // 修改状态
    updateIsPauseClick(id) {
      updateIsPause({ id }).then(resp => {
        if (resp.success) {
          utils.successMessage('恢复成功')
          this.getData()
        }
      })
    },
    tableRowClassName({ row, rowIndex }) {
      // if (rowIndex === 1) {
      //   return 'warning-row'
      // } else if (rowIndex === 3) {
      //   return 'success-row'
      // }
      return ''
    },
    filterTag(value, row) {
      return row.tag === value
    }
  }
}
</script>
