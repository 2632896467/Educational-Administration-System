<template>
  <el-container style="min-height: 100vh">
    <el-aside width="220px" style="background: #1f2d3d">
      <div class="brand">Edu Admin</div>
      <el-menu
        :default-active="$route.path"
        background-color="#1f2d3d"
        text-color="#d4dbe5"
        active-text-color="#409EFF"
        router
      >
        <el-menu-item v-for="item in menus" :key="item.path" :index="item.path">
          {{ item.title }}
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span>{{ userInfo.realName }} ({{ userInfo.role }})</span>
        <el-button type="text" @click="logout">Logout</el-button>
      </el-header>
      <el-main style="background: #f5f7fa">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import { resetRouter } from '@/router'

export default {
  name: 'HomeView',
  computed: {
    userInfo() {
      return {
        realName: localStorage.getItem('realName') || 'Unknown',
        role: localStorage.getItem('role') || ''
      }
    },
    menus() {
      const role = localStorage.getItem('role')
      if (role === 'ADMIN') {
        return [
          { path: '/user-manage', title: 'User Management' },
          { path: '/schedule-manage', title: 'Schedule Management' },
          { path: '/courses', title: 'Course List' }
        ]
      }
      if (role === 'TEACHER') {
        return [
          { path: '/grade-entry', title: 'Grade Entry' },
          { path: '/courses', title: 'Course List' }
        ]
      }
      return [
        { path: '/courses', title: 'Course Selection' },
        { path: '/scores', title: 'My Scores' }
      ]
    }
  },
  methods: {
    logout() {
      localStorage.clear()
      resetRouter()
      this.$router.replace('/login')
    }
  }
}
</script>

<style scoped>
.brand {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  border-bottom: 1px solid #2d3e50;
}

.header {
  background: #fff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #ebeef5;
}
</style>
