import { requestGet, requestPost } from '@/api/request'

const queryAllData = data => requestGet('flowLcgl/listAll', data)
const insert = data => requestPost('flowLcgl/insert', data)
const update = data => requestPost('flowLcgl/update', data)
const deleteById = data => requestPost('flowLcgl/deleteById', data)
const findById = data => requestGet('flowLcgl/findById', data)
const updateProcessData = data => requestPost('flowLcgl/updateProcessData', data)
const getApproveNode = data => requestGet('flowLcgl/getApproveNode', data)
const saveApproveUser = data => requestPost('flowLcgl/saveApproveUser', data)
const getApproveNodeDetail = data => requestGet('flowLcgl/getApproveNodeDetail', data)
const getApprovalPending = data => requestGet('flowLcgl/getApprovalPending', data)

export { queryAllData, insert, update, deleteById, findById, updateProcessData, getApproveNode, saveApproveUser, getApproveNodeDetail, getApprovalPending }
