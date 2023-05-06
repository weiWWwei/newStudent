import request from '@/utils/request'

export function login(data) {
    return request({
        url: '/user/login',
        method: 'post',
        data
    })
}

export default {
    getStudentList(searchModel) {
        return request({
            url: '/student/list',
            method: 'get',
            params: {
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                number: searchModel.number,
                uid: searchModel.uid
            }
        })
    },
    getStudent(searchModel) {
        return request({
            url: '/student/get',
            method: 'get',
            params: {
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                number: searchModel.number,
                uid: searchModel.uid
            }
        })
    },
    addStudent(student) {
        return request({
            url: '/student/add',
            method: 'post',
            data: student
        })
    },
    saveStudent(student) {
        if (student.id == null && student.id == undefined) {
            return this.addUser(student);
        }
        return this.updateUser(student);
    },
    updateUser(student) {
        return request({
            url: '/student',
            method: 'put',
            data: student
        });
    },
    getStudentById(id) {
        return request({
            //url: '/user/' + id,
            url: `/student/${id}`,
            method: 'get'
        });
    },
}