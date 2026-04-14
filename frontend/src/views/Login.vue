<template>
  <div class="login-page">
    <el-card class="login-card">
      <h2>Educational Administration System</h2>
      <el-form :model="form" :rules="rules" ref="loginForm" label-position="top">
        <el-form-item label="Username" prop="username">
          <el-input v-model="form.username" placeholder="Enter username" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="form.password" type="password" placeholder="Enter password" show-password />
        </el-form-item>
        <el-button type="primary" style="width: 100%" @click="handleLogin">Login</el-button>
      </el-form>
      <div class="hint">Default demo account: admin / teacher01 / student01 (password: 123456)</div>
    </el-card>
  </div>
</template>

<script>
import { login } from '@/api/modules'
import { loadDynamicRoutes } from '@/router'
import { REQUEST_FAILED, safelyExecute } from '@/utils/request-helpers'

export default {
  name: 'LoginView',
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      rules: {
        username: [{ required: true, message: 'Please input username', trigger: 'blur' }],
        password: [{ required: true, message: 'Please input password', trigger: 'blur' }]
      }
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(async valid => {
        if (!valid) return
        const data = await safelyExecute(() => login(this.form))
        if (data === REQUEST_FAILED) return
        localStorage.setItem('token', data.token)
        localStorage.setItem('userId', String(data.userId))
        localStorage.setItem('username', data.username)
        localStorage.setItem('realName', data.realName)
        localStorage.setItem('role', data.role)

        loadDynamicRoutes(data.role)
        this.$router.replace('/')
      })
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #f0f4ff 0%, #e6f7ff 100%);
}

.login-card {
  width: 420px;
}

.hint {
  margin-top: 12px;
  color: #909399;
  font-size: 12px;
}
</style>
