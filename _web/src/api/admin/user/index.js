import { requestGet, requestPost } from '@/api/request'

const queryAllData = data => requestGet('sysUser/listAll', data)
const insert = data => requestPost('sysUser/insert', data)
const update = data => requestPost('sysUser/update', data)
const deleteById = data => requestPost('sysUser/deleteById', data)
const findById = data => requestGet('sysUser/findById', data)
const listAllByUserNameAndDept = data => requestGet('sysUser/listAllByUserNameAndDept', data)

export { queryAllData, insert, update, deleteById, findById, listAllByUserNameAndDept }
