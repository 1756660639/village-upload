import { requestGet } from '@/api/request'

const queryAllMenu = data => requestGet('menu/queryAllMenu', data)

export { queryAllMenu }
