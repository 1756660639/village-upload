import { requestGet, requestPost } from '@/api/request'

const queryAllData = data => requestGet('record/listAll', data)
const insert = data => requestPost('record/insert', data)
const update = data => requestPost('record/update', data)
const deleteById = data => requestPost('record/deleteById', data)
const findById = data => requestGet('record/findById', data)

export { queryAllData, insert, update, deleteById, findById }
