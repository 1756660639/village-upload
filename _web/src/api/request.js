import request from '@/utils/httpRequest'

function requestGet(url, params) {
  return request({
    url,
    method: 'get',
    params
  })
}

function requestPost(url, data) {
  return request({
    url,
    method: 'post',
    data
  })
}

function downLoadFile(url, params) {
  return request({
    url,
    method: 'get',
    responseType: 'blob',
    headers: { 'Content-Type': 'application/json; application/octet-stream' },
    params
  })
}

export { requestGet, requestPost, downLoadFile }
