<template>
  <div>
    <el-card>
      <div slot="header">Teacher Grade Entry</div>
      <el-form ref="scoreForm" :model="form" :rules="rules" label-width="120px" style="max-width: 520px">
        <el-form-item label="Student ID" prop="studentId">
          <el-input-number v-model="form.studentId" :min="1" />
        </el-form-item>
        <el-form-item label="Course ID" prop="courseId">
          <el-input-number v-model="form.courseId" :min="1" />
        </el-form-item>
        <el-form-item label="Score" prop="score">
          <el-input-number v-model="form.score" :min="0" :max="100" :precision="2" />
        </el-form-item>
        <el-form-item label="Remark">
          <el-input v-model="form.remark" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitScore">Submit</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { upsertScore } from '@/api/modules'
import { REQUEST_FAILED, requireUserId, safelyExecute } from '@/utils/request-helpers'

export default {
  name: 'GradeEntryView',
  data() {
    return {
      form: {
        studentId: null,
        courseId: null,
        teacherId: null,
        score: 0,
        remark: ''
      },
      rules: {
        studentId: [{ required: true, message: 'Please input student ID', trigger: 'change' }],
        courseId: [{ required: true, message: 'Please input course ID', trigger: 'change' }],
        score: [{ required: true, message: 'Please input score', trigger: 'change' }]
      }
    }
  },
  created() {
    this.form.teacherId = requireUserId(this)
  },
  methods: {
    submitScore() {
      this.$refs.scoreForm.validate(async valid => {
        if (!valid) return

        const teacherId = requireUserId(this)
        if (!teacherId) return

        const result = await safelyExecute(() => upsertScore({
          ...this.form,
          teacherId
        }))
        if (result === REQUEST_FAILED) return

        this.$message.success('Score submitted successfully')
        this.form.studentId = null
        this.form.courseId = null
        this.form.teacherId = teacherId
        this.form.score = 0
        this.form.remark = ''
      })
    }
  }
}
</script>
