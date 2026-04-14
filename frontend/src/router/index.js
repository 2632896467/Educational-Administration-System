import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '@/views/Login.vue'
import Home from '@/views/Home.vue'
import CourseList from '@/views/CourseList.vue'
import MyScores from '@/views/MyScores.vue'
import GradeEntry from '@/views/GradeEntry.vue'
import UserManage from '@/views/UserManage.vue'
import ScheduleManage from '@/views/ScheduleManage.vue'

Vue.use(VueRouter)

const roleRoutes = {
  STUDENT: [
    { path: '/courses', name: 'CourseList', component: CourseList, meta: { title: 'Course Selection' } },
    { path: '/scores', name: 'MyScores', component: MyScores, meta: { title: 'My Scores' } }
  ],
  TEACHER: [
    { path: '/grade-entry', name: 'GradeEntry', component: GradeEntry, meta: { title: 'Grade Entry' } },
    { path: '/courses', name: 'CourseList', component: CourseList, meta: { title: 'Courses' } }
  ],
  ADMIN: [
    { path: '/user-manage', name: 'UserManage', component: UserManage, meta: { title: 'User Management' } },
    { path: '/schedule-manage', name: 'ScheduleManage', component: ScheduleManage, meta: { title: 'Schedule Management' } },
    { path: '/courses', name: 'CourseList', component: CourseList, meta: { title: 'Courses' } }
  ]
}

let dynamicRoutesLoaded = false
let loadedRole = ''

function createRouter() {
  return new VueRouter({
    mode: 'history',
    routes: [{ path: '/login', name: 'Login', component: Login }]
  })
}

const router = createRouter()

export function resetRouter() {
  const freshRouter = createRouter()
  router.matcher = freshRouter.matcher
  dynamicRoutesLoaded = false
  loadedRole = ''
}

export function loadDynamicRoutes(role) {
  if (dynamicRoutesLoaded && loadedRole === role) return
  if (dynamicRoutesLoaded && loadedRole !== role) {
    resetRouter()
  }
  const children = roleRoutes[role] || roleRoutes.STUDENT
  router.addRoutes([
    {
      path: '/',
      component: Home,
      children,
      redirect: children[0]?.path || '/login'
    },
    { path: '*', redirect: '/' }
  ])
  dynamicRoutesLoaded = true
  loadedRole = role
}

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const role = localStorage.getItem('role')

  if (token && role) {
    loadDynamicRoutes(role)
  }

  if (!token && to.path !== '/login') {
    next('/login')
    return
  }

  if (token && to.path === '/login') {
    next('/')
    return
  }

  next()
})

export default router
