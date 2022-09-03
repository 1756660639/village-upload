import { requestGet, requestPost, downLoadFile } from '@/api/request'

// 合并文件
const merageFile = data => requestPost('file/merageFile', data)

// 查询文件
const queryFile = data => requestGet('file/queryFile', data)

// 添加文件夹
const addFilePath = data => requestPost('file/addFilePath', data)

// 获取文件信息
const findById = data => requestGet('file/findById', data)

// 下载文件
const download = data => downLoadFile('file/download', data)

// 文件回调
const callBack = data => requestPost('file/callBack', data)

// 重命名
const rename = data => requestPost('file/rename', data)

// 删除文件
const delFile = data => requestGet('/file/delFile', data)

// 文件预览
const filePreview = data => requestGet('/file/filePreview', data)

export default { merageFile, queryFile, addFilePath, findById, download, callBack, filePreview }

export { merageFile, queryFile, addFilePath, findById, download, callBack, rename, delFile, filePreview }
