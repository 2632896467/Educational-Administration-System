<template>
  <div>
    <el-card>
      <div slot="header">My Scores</div>
      <el-table :data="scores" border>
        <el-table-column prop="courseId" label="Course ID" width="120" />
        <el-table-column prop="score" label="Score" width="120" />
        <el-table-column prop="gradePoint" label="Grade Point" width="130" />
        <el-table-column prop="remark" label="Remark" />
        <el-table-column prop="updatedAt" label="Updated At" width="190" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getMyScores } from '@/api/modules'
import { REQUEST_FAILED, requireUserId, safelyExecute } from '@/utils/request-helpers'

export default {
  name: 'MyScoresView',
  data() {
    return {
      scores: []
    }
  },
  created() {
    this.loadScores()
  },
  methods: {
    async loadScores() {
      const studentId = requireUserId(this)
      if (!studentId) return

      const scores = await safelyExecute(() => getMyScores(studentId))
      if (scores === REQUEST_FAILED) return
      this.scores = scores
    }
  }
}
</script>
