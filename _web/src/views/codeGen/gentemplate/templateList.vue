<template>
  <div class="app-container">
    <el-button type="primary" size="mini" icon="el-icon-plus" style="margin-bottom: 10px;" @click="onGroupAdd">添加模板组</el-button>
    <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
      <el-tab-pane v-for="item in groupData" :key="item.id" :name="`${item.id}`" :label="item.groupName">
        <span slot="label">
          {{ item.groupName }}
          <el-dropdown
            v-show="item.id === currentTab.id"
            trigger="click"
            style="margin-left: 5px;"
          >
            <span class="el-dropdown-link">
              <el-tooltip placement="top" content="更多操作" :open-delay="500">
                <a class="el-icon-setting el-icon--right" />
              </el-tooltip>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-edit" @click.native="onGroupInfoUpdate">修改</el-dropdown-item>
              <el-dropdown-item icon="el-icon-delete" @click.native="onGroupInfoDelete">删除</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </span>
      </el-tab-pane>
      <el-button type="text" size="mini" icon="el-icon-plus" style="margin-bottom: 10px;" @click="onAdd">新增模板</el-button>
      <el-button type="text" size="mini" icon="el-icon-download" style="margin-bottom: 10px;" @click="downloadTemplates">导出模板</el-button>
      <el-table
        :data="tableData"
        border
        highlight-current-row
      >
        <el-table-column
          prop="name"
          label="模板名称"
          width="200"
        />
        <el-table-column
          prop="fileName"
          label="文件名称"
        />
        <el-table-column
          label="操作"
          width="150"
        >
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="onTableUpdate(scope.row)">修改</el-button>
            <el-button type="text" size="mini" @click="onTableDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-tabs>
  </div>
</template>

<script>
import JSZip from 'jszip'
import { saveAs } from 'file-saver'
import { queryTemplateGroupList, queryTemplateList, templateGroupAdd, templateGroupDeleteById, templateGroupUpd, templateDeleteById } from '@/api/codeGen/index.js'
import util from '@/utils/util.js'

export default {
  data() {
    return {
      activeName: '',
      tableData: [],
      groupData: [],
      currentTab: { id: 0 },
      importFiles: []
    }
  },
  created() {
    this.loadGroup()
  },
  methods: {
    // 获取分组
    loadGroup() {
      queryTemplateGroupList().then(response => {
        if (response.success) {
          this.groupData = response.data
          let id = this.currentTab.id
          if (!id && this.groupData.length > 0) {
            id = this.groupData[0].id
          }
          if (id) {
            this.selectTab(id)
          }
        } else {
          util.errorMessage
        }
      })
    },
    selectTab(id) {
      for (const group of this.groupData) {
        if (group.id === id) {
          this.currentTab = group
          break
        }
      }
      this.loadTable(id)
    },
    // 根据分组id获取分组模板数据
    loadTable(groupId) {
      this.activeName = `${groupId}`
      const query = { 'groupId': groupId }
      queryTemplateList(query).then(response => {
        if (response.success) {
          this.tableData = response.data
        } else {
          util.errorMessage
        }
      })
    },
    // 切换模板分组请求
    handleClick(tab) {
      const id = tab.name
      if (id) {
        this.selectTab(id)
      }
    },
    // 编辑模板
    onTableUpdate: function(row) {
      this.$router.push({ path: '/sysAdmin/templateEdit', query: { id: row.id }})
    },
    // 模板删除
    onTableDelete: function(row) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        templateDeleteById(row.id).then(response => {
          util.successMessage('删除成功！')
          this.loadTable(this.activeName)
        })
      }).catch(() => {
        util.warnMessage
      })
    },
    // 新增模板
    onAdd: function() {
      this.$router.push({ path: '/sysAdmin/templateEdit', query: { groupId: this.activeName }})
    },
    // // group
    // 更新模板分组名称
    onGroupInfoUpdate() {
      this.onGroupUpdate(this.currentTab)
    },
    // 删除模板分组
    onGroupInfoDelete() {
      this.onGroupDelete(this.currentTab)
    },
    // 新增模板分组
    onGroupAdd() {
      this.groupTitle = '增加模板组'
      this.$prompt('请输入组名称', '增加模板组', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: '',
        inputPattern: /^.{1,64}$/,
        inputErrorMessage: '不能为空且长度在64以内'
      }).then(({ value }) => {
        const data = {
          groupName: value
        }
        templateGroupAdd(data).then(response => {
          if (response.success) {
            util.successMessage('新建成功！')
            this.currentTab = response.data
            this.loadGroup()
          } else {
            util.errorMessage
          }
        })
      }).catch(() => {
        util.warnMessage
      })
    },
    onGroupUpdate(row) {
      this.$prompt('请输入组名称', '修改模板组', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputValue: row.groupName,
        inputPattern: /^.{1,64}$/,
        inputErrorMessage: '不能为空且长度在64以内'
      }).then(({ value }) => {
        const data = {
          id: row.id,
          groupName: value
        }
        templateGroupUpd(data).then(response => {
          if (response.success) {
            debugger
            util.successMessage('修改成功！')
            this.loadGroup()
          } else {
            util.errorMessage
          }
        })
      }).catch(() => {
        util.warnMessage
      })
    },
    onGroupDelete(row) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        templateGroupDeleteById(row.id).then(response => {
          util.successMessage('删除成功！')
          this.currentTab.id = 0
          this.loadGroup()
        })
      }).catch(() => {
        util.warnMessage
      })
    },
    // saveTemplate(data) {
    //   const uri = '/template/save'
    //   this.post(uri, data)
    // },
    // 导出模板
    downloadTemplates() {
      const folder = this.currentTab.groupName
      const filename = folder + '.zip'
      const zip = new JSZip()
      const folderZip = zip.folder(folder)
      this.tableData.forEach(row => {
        folderZip.file(row.name + '.vm', row.content)
      })
      // 下载
      zip.generateAsync({ type: 'blob' }).then(function(content) {
        // see FileSaver.js
        saveAs(content, filename)
      })
    }
  }
}
</script>

<style lang="stylus" scoped>
.app-container {
    padding: 20px;
}
</style>
