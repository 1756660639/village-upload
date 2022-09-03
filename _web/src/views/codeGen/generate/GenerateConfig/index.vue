<template>
  <div class="code-gen">
    <el-form ref="genForm" class="gen-form" :model="clientParam" size="mini" label-width="150px">
      <el-form-item label="选择数据源" prop="datasourceConfigId" :rules="{required: true, message: '请选择数据源'}">
        <el-select
          v-model="clientParam.datasourceConfigId"
          placeholder="选择数据源"
          @change="onDataSourceChange"
        >
          <el-option
            v-for="item in datasourceConfigList"
            :key="item.id"
            :label="getDatasourceLabel(item)"
            :value="item.id"
          >
            <span style="float: left">{{ getDatasourceLabel(item) }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              <el-tooltip placement="top" content="Duplicate">
                <el-link type="primary" icon="el-icon-document-copy" style="margin-right: 20px;" @click.stop="onDataSourceDuplicate(item)" />
              </el-tooltip>
              <el-link type="primary" icon="el-icon-edit" style="margin-right: 20px;" @click.stop="onDataSourceUpdate(item)" />
              <el-link type="danger" icon="el-icon-delete" @click.stop="onDataSourceDelete(item)" />
            </span>
          </el-option>
        </el-select>
        <el-button type="text" @click="onDataSourceAdd">新建数据源</el-button>
      </el-form-item>
      <el-form-item v-show="showTable" label="包名（package）">
        <el-input v-model="clientParam.packageName" placeholder="可选，如：cn.studyjava.xxx" show-word-limit maxlength="100" />
      </el-form-item>
      <el-form-item v-show="showTable" label="删除前缀">
        <el-input v-model="clientParam.delPrefix" placeholder="可选，如：sys_user对应Java类为User(多前缀逗号隔开)" show-word-limit maxlength="100" />
      </el-form-item>
      <el-form-item v-show="showTable" label="作者名">
        <el-input v-model="clientParam.author" placeholder="可选，如：author" show-word-limit maxlength="100" />
      </el-form-item>
    </el-form>
    <el-row v-show="showTable" :gutter="20">
      <el-col :span="12">
        <h4>选择表</h4>
        <el-input
          v-model="tableSearch"
          prefix-icon="el-icon-search"
          clearable
          size="mini"
          placeholder="过滤表"
          style="margin-bottom: 10px;width: 100%;"
        />
        <el-table
          :data="tableListData"
          border
          :cell-style="cellStyleSmall()"
          :header-cell-style="headCellStyleSmall()"
          :row-class-name="tableRowClassName"
          @selection-change="onTableListSelect"
        >
          <el-table-column
            type="selection"
          />
          <el-table-column
            prop="tableName"
            label="表名"
          />
        </el-table>
      </el-col>
      <el-col id="templateSelect" :span="12">
        <h4>选择模板</h4>
        <el-select
          v-model="groupId"
          placeholder="选择模板所在组"
          size="mini"
          style="margin-bottom: 10px; width: 100%;"
          @change="templateGroupChange"
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
        <el-table
          :data="templateListData"
          border
          :cell-style="cellStyleSmall()"
          :header-cell-style="headCellStyleSmall()"
          :row-class-name="templateTableRowClassName"
          @selection-change="onTemplateListSelect"
        >
          <el-table-column
            type="selection"
          />
          <el-table-column
            prop="name"
            label="模板名称"
          >
            <span slot-scope="scope">
              <!--              {{scope.row.groupName}}-{{scope.row.name}}-->
              {{ scope.row.name }}
            </span>
          </el-table-column>
        </el-table>
        <el-button v-show="showTable" type="primary" @click="onGenerate">生成代码</el-button>
      </el-col>
    </el-row>

    <el-dialog
      :title="datasourceTitle"
      :visible.sync="datasourceDlgShow"
      :close-on-press-escape="false"
      :close-on-click-modal="false"
    >
      <el-form
        ref="datasourceForm"
        :model="datasourceFormData"
        :rules="datasourceRule"
        size="mini"
        label-width="120px"
      >
        <el-form-item label="数据库类型">
          <el-select
            v-model="datasourceFormData.dbType"
            filterable
            default-first-option
          >
            <el-option
              v-for="item in dbTypeConfig"
              :key="item.dbType"
              :label="item.label"
              :value="item.dbType"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="Host" prop="host">
          <el-input v-model="datasourceFormData.host" placeholder="地址" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item label="Port" prop="port">
          <el-input v-model="datasourceFormData.port" placeholder="端口" show-word-limit maxlength="10" />
        </el-form-item>
        <el-form-item :label="dbNamePlaceholder" prop="dbName">
          <el-input v-model="datasourceFormData.dbName" :placeholder="dbNamePlaceholder" show-word-limit maxlength="64" />
        </el-form-item>
        <el-form-item v-show="showOracleFields" label="连接类型">
          <el-select v-model="datasourceFormData.oracleConnType">
            <el-option
              v-for="item in oracleConnTypeList"
              :key="item.val"
              :label="item.lab"
              :value="item.val"
            />
          </el-select>
        </el-form-item>
        <el-form-item v-show="showPgSqlSchema" :label="schemaPlaceholder" :prop="schemaPlaceholder">
          <el-input v-model="datasourceFormData.schemaName" :placeholder="schemaPlaceholder+',如SCOTT'" show-word-limit maxlength="64" />
        </el-form-item>
        <el-form-item label="Username" prop="username">
          <el-input v-model="datasourceFormData.username" placeholder="用户名" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item v-show="showOracleFields" label="角色">
          <el-select v-model="datasourceFormData.oracleRole" clearable>
            <el-option
              v-for="item in oracleRoleList"
              :key="item.val"
              :label="item.lab"
              :value="item.val"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="datasourceFormData.password" type="password" placeholder="密码" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item label="包名" prop="packageName">
          <el-input v-model="datasourceFormData.packageName" placeholder="包名（package）" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item label="删除前缀" prop="delPrefix">
          <el-input v-model="datasourceFormData.delPrefix" placeholder="删除前缀（表名sys_user删除前缀sys_对应bean为User）多前缀逗号隔开" show-word-limit maxlength="200" />
        </el-form-item>
        <el-form-item label="作者名" prop="author">
          <el-input v-model="datasourceFormData.author" placeholder="作者名" show-word-limit maxlength="100" />
        </el-form-item>
        <el-form-item label="代码生成器模板" prop="delPrefix">
          <el-select
            v-model="datasourceFormData.groupId"
            placeholder="选择模板所在组"
            size="mini"
            style="margin-bottom: 10px; width: 100%;"
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
        <el-form-item>
          <el-button type="success" @click="onDatasourceTest">测试连接</el-button>
          <el-button type="primary" @click="onDatasourceSave">保存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import utils from '@/utils/util.js'
import { queryDataSourceList, queryDataTableList, queryTemplateList, queryDBtypeList, queryTemplateGroupList, dataSourceConfig, dataSourceConfigAdd, dataSourceConfigUpd, dataSourceConfigDel } from '@/api/codeGen/index.js'

const current_datasource_id_key = 'gen-datasource-id'
const DB_TYPE = {
  MySQL: 1,
  Oracle: 2,
  SQL_Server: 3,
  PostgreSQL: 4
}
export default {
  name: 'GenerateConfig',
  data() {
    return {
      groupId: '',
      groupData: [],
      showTable: false,
      clientParam: {
        datasourceConfigId: '',
        tableNames: [],
        templateConfigIdList: [],
        packageName: '',
        delPrefix: '',
        groupId: '',
        author: ''
      },
      tableSearch: '',
      datasourceConfigList: [],
      tableListData: [],
      templateListData: [],
      // add datasource
      datasourceTitle: '新建连接',
      datasourceDlgShow: false,
      datasourceFormData: {
        id: 0,
        dbType: 1,
        host: '',
        port: '',
        username: '',
        password: '',
        dbName: '',
        oracleConnType: '',
        oracleRole: '',
        schemaName: '',
        packageName: '',
        delPrefix: '',
        groupId: '',
        author: ''
      },
      dbTypeConfig: [],
      oracleConnTypeList: [{
        lab: '服务名',
        val: 1
      }, {
        lab: 'SID',
        val: 2
      }],
      oracleRoleList: [{
        lab: 'default',
        val: ''
      }, {
        lab: 'SYSDBA',
        val: 1
      }, {
        lab: 'SYSOPER',
        val: 2
      }],
      datasourceRule: {
        host: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        port: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ],
        dbName: [
          { required: true, message: '不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    showPgSqlSchema() {
      return this.datasourceFormData.dbType === DB_TYPE.PostgreSQL || this.datasourceFormData.dbType === DB_TYPE.Oracle
    },
    dbNamePlaceholder() {
      if (this.datasourceFormData.dbType === DB_TYPE.Oracle) {
        return '服务名'
      } else {
        return '数据库'
      }
    },
    schemaPlaceholder() {
      if (this.datasourceFormData.dbType === DB_TYPE.Oracle) {
        return '数据库'
      } else {
        return 'schema'
      }
    },
    showOracleFields() {
      return this.datasourceFormData.dbType === DB_TYPE.Oracle
    }
  },
  watch: {
    // 监听属性变化
    'clientParam.datasourceConfigId': function(id) {
      for (const item of this.datasourceConfigList) {
        if (item.id === id) {
          this.groupId = item.groupId
          Object.assign(this.clientParam, {
            packageName: item.packageName,
            delPrefix: item.delPrefix,
            groupId: item.groupId,
            author: item.author
          })
          break
        }
      }
    }
  },
  created() {
    this.loadDataSource()
    // this.loadTemplate()
    this.loadDbType()
    this.loadGroups()
  },
  methods: {
    tableRowClassName: function({ row, index }) {
      // console.log("tablerow="+row.tableName+","+index)
      row.hidden = false
      if (this.tableSearch.length === 0) {
        return ''
      }
      // console.log("tablerow="+row.tableName +","+ row.tableName.indexOf(this.tableName)+","+(!(row.tableName && row.tableName.indexOf(this.tableSearch) > -1)))
      if (!(row.tableName && row.tableName.toLowerCase().indexOf(this.tableSearch.toLowerCase()) > -1)) {
        row.hidden = true
        return 'hidden-row'
      }
      return ''
    },
    templateTableRowClassName: function({ row, index }) {
      // console.log("temprow="+row.id+",rowGroupId="+row.groupId+", this.groupId="+ this.groupId)
      row.hidden = false
      if (this.groupId === '' || this.groupId <= 0) {
        return ''
      }
      // console.log("temprow="+row.groupId +","+(row.groupId && row.groupId == this.groupId))
      if (row.groupId && row.groupId === this.groupId) {
        return ''
      }
      row.hidden = true
      return 'hidden-row'
    },
    getDatasourceLabel(item) {
      const schema = item.schemaName ? `/${item.schemaName}` : ''
      return `${item.dbName}${schema} (${item.host}) - ${item.username}`
    },
    // 查询模板分组
    loadGroups() {
      queryTemplateGroupList().then(resp => {
        this.groupData = resp.data
        this.groupId = resp.data[0].id
        this.loadTemplate(resp.data[0].id)
      })
    },
    // 模板分组切换查询
    templateGroupChange(id) {
      this.loadTemplate(id)
    },
    // 获取数据源配置
    loadDataSource() {
      const cacheId = this.getCurrentDataSourceId()
      queryDataSourceList().then(resp => {
        let id
        const list = resp.data
        this.datasourceConfigList = list
        for (const item of list) {
          // 缓存id是否有效
          if (item.id === cacheId) {
            id = item.id
            break
          }
        }
        if (!id && list.length > 0) {
          id = list[0].id
        }
        if (id) {
          this.onDataSourceChange(id)
        }
      })
    },
    // 模板查询
    loadTemplate(groupId) {
      queryTemplateList({ 'groupId': groupId }).then(resp => {
        this.templateListData = resp.data
      })
    },
    // 查询数据库类型
    loadDbType() {
      queryDBtypeList().then(resp => {
        this.dbTypeConfig = resp.data
      })
    },
    // 设置缓存id
    setCurrentDataSourceId(id) {
      utils.setStorage(current_datasource_id_key, id)
    },
    // 获取缓存id
    getCurrentDataSourceId() {
      const id = utils.getStorage(current_datasource_id_key)
      return id || ''
    },
    packageOracleFields() {
      // 处理oracle连接数据
      if (this.datasourceFormData.dbType === DB_TYPE.Oracle) {
        // 处理连接类型
        if (this.datasourceFormData.oracleConnType === 1) {
          this.datasourceFormData.dbName = '/' + this.datasourceFormData.dbName
        } else if (this.datasourceFormData.oracleConnType === 2) {
          this.datasourceFormData.dbName = ':' + this.datasourceFormData.dbName
        }
        // 处理账号角色
        if (this.datasourceFormData.oracleRole === 1) {
          this.datasourceFormData.username += ' AS SYSDBA'
        } else if (this.datasourceFormData.oracleRole === 2) {
          this.datasourceFormData.username += ' AS SYSOPER'
        }
      }
    },
    unPackOracleFields(item) {
      // 处理oracle属性 拆包
      if (item.dbType === DB_TYPE.Oracle) {
        // 处理连接类型
        if (item.dbName.startsWith('/')) {
          item.oracleConnType = 1
          item.dbName = item.dbName.replace('/', '')
        } else if (item.dbName.startsWith(':')) {
          item.oracleConnType = 2
          item.dbName = item.dbName.replace(':', '')
        }
        // 处理账号角色
        if (item.username.includes('AS SYSDBA')) {
          item.oracleRole = 1
          item.username = item.username.replace(' AS SYSDBA', '')
        } else if (item.username.includes('AS SYSOPER')) {
          item.oracleRole = 2
          item.username = item.username.replace(' AS SYSOPER', '')
        }
      }
    },
    // 新建数据源
    onDataSourceAdd() {
      this.datasourceTitle = '新建连接'
      Object.keys(this.datasourceFormData).forEach(key => {
        this.datasourceFormData[key] = ''
      })
      this.datasourceFormData.id = 0
      this.datasourceFormData.dbType = 1
      this.datasourceDlgShow = true
      this.$nextTick(() => {
        if (this.groupData.length > 0) {
          this.datasourceFormData.groupId = this.groupData[0].id
        }
      })
    },
    onTableListSelect(selectedRows) {
      this.clientParam.tableNames = selectedRows
        .filter(row => row.hidden === undefined || row.hidden === false)
        .map(row => row.tableName)
    },
    onTemplateListSelect(selectedRows) {
      this.clientParam.templateConfigIdList = selectedRows
        .filter(row => row.hidden === undefined || row.hidden === false)
        .map(row => row.id)
    },
    // 获取数据库表
    onDataSourceChange(datasourceConfigId) {
      this.setCurrentDataSourceId(datasourceConfigId)
      this.clientParam.datasourceConfigId = datasourceConfigId
      queryDataTableList({ 'id': datasourceConfigId }).then(resp => {
        this.showTable = true
        this.tableListData = resp.data
      })
    },
    onDataSourceUpdate(item) {
      this.datasourceTitle = '修改连接'
      this.unPackOracleFields(item)
      Object.assign(this.datasourceFormData, item)
      this.datasourceDlgShow = true
    },
    onDataSourceDuplicate(item) {
      this.datasourceTitle = `${item.host} Copy`
      Object.assign(this.datasourceFormData, item)
      this.datasourceFormData.id = 0
      this.datasourceDlgShow = true
    },
    onDataSourceDelete(row) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const data = {
          id: row.id
        }
        dataSourceConfigDel(row.id).then(resp => {
          location.reload()
        })
      }).catch(() => {
        utils.warnMessage
      })
    },
    // 生成代码
    onGenerate() {
      this.$refs.genForm.validate((valid) => {
        if (valid) {
          if (this.clientParam.tableNames.length === 0) {
            this.tip('请勾选表', 'error')
            return
          }
          if (this.clientParam.templateConfigIdList.length === 0) {
            this.tip('请勾选模板', 'error')
            return
          }
          const config = JSON.stringify(this.clientParam)
          this.$router.push({ path: '/sysAdmin/result', query: { config }})
        }
      })
    },
    // 测试连接
    onDatasourceTest() {
      this.packageOracleFields()
      this.$refs.datasourceForm.validate((valid) => {
        if (valid) {
          dataSourceConfig(this.datasourceFormData).then(resp => {
            if (resp.success) {
              if (this.datasourceFormData.dbType === DB_TYPE.Oracle) {
              // 处理连接类型
                if (this.datasourceFormData.oracleConnType === 1) {
                  this.datasourceFormData.dbName = this.datasourceFormData.dbName.replace('/', '')
                } else if (this.datasourceFormData.oracleConnType === 2) {
                  this.datasourceFormData.dbName = this.datasourceFormData.dbName.replace(':', '')
                }
                // 处理账号角色
                if (this.datasourceFormData.oracleRole === 1) {
                  this.datasourceFormData.username = this.datasourceFormData.username.replace(' AS SYSDBA', '')
                } else {
                  this.datasourceFormData.username = this.datasourceFormData.username.replace(' AS SYSOPER', '')
                }
              }
              utils.successMessage('连接成功')
            } else {
              utils.errorMessage('连接失败')
            }
          }, this.unPackOracleFields(this.datasourceFormData))
        }
      })
    },
    // 新增数据源
    onDatasourceSave() {
      this.packageOracleFields()
      this.$refs.datasourceForm.validate((valid) => {
        if (valid) {
          dataSourceConfig(this.datasourceFormData).then(resp => {
            if (this.datasourceFormData.id) {
              dataSourceConfigUpd(this.datasourceFormData).then(resp => {
                location.reload()
              })
            } else {
              dataSourceConfigAdd(this.datasourceFormData).then(resp => {
                utils.successMessage('添加成功')
                this.loadDataSource()
                this.datasourceDlgShow = false
              })
            }
          })
        }
      })
    },
    cellStyleSmall: function() {
      return { padding: '5px 0' }
    },
    headCellStyleSmall: function() {
      return { padding: '5px 0' }
    }
  }
}
</script>

<style lang="stylus" scoped>
  .code-gen {
    margin: 0 auto;
    width: 70%;
      .el-input { width: 450px;}
      .el-row h4 {
        text-align: center;
      }
      .el-row .el-button {
        margin-top: 20px;
      }
  }
  .el-table .hidden-row {
    display: none;
  }
  #templateSelect {
    .el-input { width: 100%;}
  }
</style>
