import { requestGet, requestPost } from '@/api/request'

const queryTemplateGroupList = data => requestGet('template/listAll', data) // 模板分组

const templateGroupAdd = data => requestPost('template/insert', data) // 模板分组添加

const templateGroupUpd = data => requestPost('template/update', data) // 模板分组编辑

const templateGroupDeleteById = data => requestPost('template/deleteById', data) // 模板分组删除

const queryTemplateList = data => requestGet('genTemplateConfig/listAll', data) // 根据模板分组id查数据

const templateAdd = data => requestPost('genTemplateConfig/insert', data) // 模板添加

const templateUdp = data => requestPost('genTemplateConfig/update', data) // 模板修改

const queryTemplateById = data => requestGet('genTemplateConfig/queryById', data) // 根据id查询

const templateDeleteById = data => requestPost('genTemplateConfig/deleteById', data) // 模板删除

const queryDataSourceList = data => requestGet('genDatasourceConfig/listAll', data) // 数据源查询

const queryDataTableList = data => requestGet('genDatasourceConfig/table', data) // 查询数据库表

const queryDBtypeList = data => requestGet('genDatasourceConfig/dbtype', data) // 查询数据库类型列表

const dataSourceConfig = data => requestPost('genDatasourceConfig/test', data) // 测试连接

const dataSourceConfigAdd = data => requestPost('genDatasourceConfig/insert', data) // 新增数据源连接

const dataSourceConfigUpd = data => requestPost('genDatasourceConfig/update', data) // 修改数据源连接

const dataSourceConfigDel = data => requestPost('genDatasourceConfig/deleteById', data) // 删除连接

const generateCode = data => requestPost('generate/code', data) // 生成代码

const queryHistoryList = data => requestGet('genGenerateHistory/listAll', data) // 查询生成历史

export { queryTemplateGroupList,
  templateGroupAdd,
  templateGroupUpd,
  templateGroupDeleteById,
  queryTemplateList,
  templateDeleteById,
  queryTemplateById,
  templateUdp,
  templateAdd,
  queryDataSourceList,
  queryDataTableList,
  queryDBtypeList,
  dataSourceConfig,
  dataSourceConfigAdd,
  dataSourceConfigUpd,
  dataSourceConfigDel,
  generateCode,
  queryHistoryList
}
