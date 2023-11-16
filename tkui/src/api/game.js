import request from '@/utils/request'
export function getList() {
    return request({
        url: '/files/list',
        method: 'get',
    })
}
export function addGameLoad(data) {
    return request({
        url: '/files',
        method: 'post',
        data: data
    })
}
export function editGameLoad(data) {
    return request({
        url: '/files',
        method: 'put',
        data: data
    })
}
export function deleteGameLoad(id) {
    return request({
        url: '/files/' + id,
        method: 'delete',
    })
}
export function getTanksList(query) {
    return request({
        url: "/tk/list",
        method: 'get',
        params: query
    })
}
export function addtanks(data) {
    return request({
        url: '/tk',
        method: 'post',
        data: data
    })
}
export function edittanks(data) {
    return request({
        url: '/tk',
        method: 'put',
        data: data
    })
}
export function deletetanks(id) {
    return request({
        url: '/tk/' + id,
        method: 'delete',
    })
}