import request from './request'

export function login(data) {
  return request({
    url: '/api/auth/login',
    method: 'post',
    data
  })
}

export function getCourses(params) {
  return request({
    url: '/api/courses',
    method: 'get',
    params
  })
}

export function createCourse(data) {
  return request({
    url: '/api/admin/courses',
    method: 'post',
    data
  })
}

export function selectCourse(data) {
  return request({
    url: '/api/selections',
    method: 'post',
    data
  })
}

export function getSelections(studentId) {
  return request({
    url: '/api/selections',
    method: 'get',
    params: { studentId }
  })
}

export function dropSelection(selectionId, studentId) {
  return request({
    url: `/api/selections/${selectionId}`,
    method: 'delete',
    params: { studentId }
  })
}

export function upsertScore(data) {
  return request({
    url: '/api/scores',
    method: 'post',
    data
  })
}

export function getMyScores(studentId) {
  return request({
    url: '/api/scores/me',
    method: 'get',
    params: { studentId }
  })
}

export function createUser(data) {
  return request({
    url: '/api/admin/users',
    method: 'post',
    data
  })
}

export function getUsers() {
  return request({
    url: '/api/admin/users',
    method: 'get'
  })
}

export function createSchedule(data) {
  return request({
    url: '/api/admin/schedules',
    method: 'post',
    data
  })
}

export function getScheduleByCourse(courseId) {
  return request({
    url: `/api/admin/schedules/course/${courseId}`,
    method: 'get'
  })
}

