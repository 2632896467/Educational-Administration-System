<template>
  <div>
    <el-card style="margin-bottom: 16px">
      <div slot="header">Create User</div>
      <el-form ref="userFormRef" :model="form" :rules="rules" inline>
        <el-form-item label="Username" prop="username">
          <el-input v-model="form.username" />
        </el-form-item>
        <el-form-item label="Password" prop="password">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="Real Name" prop="realName">
          <el-input v-model="form.realName" />
        </el-form-item>
        <el-form-item label="Role" prop="role">
          <el-select v-model="form.role" placeholder="Select role">
            <el-option label="Student" value="STUDENT" />
            <el-option label="Teacher" value="TEACHER" />
            <el-option label="Admin" value="ADMIN" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleCreate">Create</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card>
      <div slot="header">User List</div>
      <el-table :data="users" border>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="Username" />
        <el-table-column prop="realName" label="Real Name" />
        <el-table-column prop="role" label="Role" width="120" />
        <el-table-column prop="createdAt" label="Created At" width="190" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { createUser, getUsers } from '@/api/modules'
import { REQUEST_FAILED, safelyExecute } from '@/utils/request-helpers'

export default {
  name: 'UserManageView',
  data() {
    return {
      form: {
        username: '',
        password: '',
        realName: '',
        role: 'STUDENT'
      },
      rules: {
        username: [{ required: true, message: 'Please input username', trigger: 'blur' }],
        password: [{ required: true, message: 'Please input password', trigger: 'blur' }],
        realName: [{ required: true, message: 'Please input real name', trigger: 'blur' }],
        role: [{ required: true, message: 'Please select role', trigger: 'change' }]
      },
      users: []
    }
  },
  created() {
    this.initialize()
  },
  methods: {
    async initialize() {
      await safelyExecute(() => this.loadUsers())
    },
    async loadUsers() {
      this.users = await getUsers()
      return this.users
    },
    handleCreate() {
      this.$refs.userFormRef.validate(async valid => {
        if (!valid) return

        const user = await safelyExecute(() => createUser(this.form))
        if (user === REQUEST_FAILED) return

        this.$message.success('User created successfully')
        this.form.username = ''
        this.form.password = ''
        this.form.realName = ''
        this.form.role = 'STUDENT'
        await safelyExecute(() => this.loadUsers())
      })
    }
  }
}
</script>
