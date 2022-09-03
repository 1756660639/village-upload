import { requestGet, requestPost } from '@/api/request'

const queryAllData = data => requestGet('scheduletask/listAll', data)
const insert = data => requestPost('scheduletask/insert', data)
const update = data => requestPost('scheduletask/update', data)
const deleteById = data => requestPost('scheduletask/deleteById', data)
const execution = data => requestGet('scheduletask/execution', data)
const updateIsPause = data => requestGet('scheduletask/updateIsPause', data)
const findById = data => requestGet('scheduletask/findById', data)
const queryAllLogData = data => requestGet('tst02Scheduletasklog/listAll', data)
export { queryAllData, insert, update, deleteById, execution, updateIsPause, findById, queryAllLogData }
