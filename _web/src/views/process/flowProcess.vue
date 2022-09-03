<template>
  <div>
    <el-button type="text" @click="insertBtn">添加</el-button>
    <el-table
      :data="tableData"
      style="width: 100%"
    >
      <el-table-column
        prop="id"
        label="流程ID"
      />
      <el-table-column
        prop="name"
        label="流程名称"
      />
      <el-table-column
        prop="fromUrl"
        label="审批表单Url"
      />
      <el-table-column
        prop="state"
        label="状态"
        :filters="[{ text: '已禁用', value: '0' }, { text: '已启用', value: '1' }]"
        filter-placement="bottom-end"
      >
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.state === '0' ? 'primary' : 'success'"
            disable-transitions
          >{{ scope.row.state ==='0'?'已禁用':'已启用' }}</el-tag>
        </template>
        />
      </el-table-column>
      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="updClick(scope.row.id)">编辑</el-button>
          <span> | </span>
          <el-button type="text" size="small" @click="processDesign(scope.row.id,scope.row.flowData)">设计流程</el-button>
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
          <el-input v-model="insertData.name" />
        </el-form-item>
        <el-form-item label="审批表单url">
          <el-input v-model="insertData.fromUrl" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="insertData.state">
            <el-radio label="1">启用</el-radio>
            <el-radio label="0">禁用</el-radio>
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
import { queryAllData, insert, update, deleteById, findById } from '@/api/process/index.js'
import utils from '@/utils/util.js'
export default {
  name: 'FlowProcess',
  data() {
    return {
      labelPosition: 'right',
      // 表格数据
      tableData: [],
      // 添加dialog弹出框是否显示
      centerDialogVisible: false,
      // diglog 标题
      title: '',
      fromList: [{ name: '1', value: '2' }],
      insertData: {
        fromUrl: '',
        flowData: '',
        state: '',
        name: '',
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
        fromUrl: '',
        flowData: '',
        state: '',
        name: '',
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
              fromUrl: '',
              flowData: '',
              state: '',
              name: '',
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
              fromUrl: '',
              flowData: '',
              state: '',
              name: '',
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
    // 设计流程
    processDesign(id, flowData) {
      if (utils.checkNull(flowData)) {
        flowData = JSON.parse(flowData)
      } else { // 默认开始和结束流程
        flowData = {
          formId: null,
          formName: '未命名表单',
          logo: {
            icon: 'el-icon-eleme',
            background: '#1e90ff'
          },
          settings: {
            commiter: [],
            admin: [],
            sign: false,
            notify: {
              types: ['APP'],
              title: '消息通知标题'
            }
          },
          group: 0,
          formItems: [],
          process: {
            id: 'root',
            parentId: null,
            type: 'ROOT',
            name: '开始',
            desc: '任何人',
            props: {

            },
            children: {
              'id': 'node_end',
              'parentId': 'root',
              'props': { },
              'type': 'END',
              'name': '结束'
            }
          },
          remark: '备注说明'
        }
      }
      this.$store.commit('process/setDesign', flowData)
      this.$router.push({ path: '/sysAdmin/processDesign', query: { id }})
    }
  }
}
</script>
