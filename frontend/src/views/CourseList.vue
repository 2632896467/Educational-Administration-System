<template>
  <div>
    <el-card>
      <div slot="header" class="toolbar">
        <span>Course List</span>
        <div>
          <el-input
            v-model="semester"
            placeholder="Filter by semester (e.g. 2026-Spring)"
            clearable
            size="small"
            style="width: 260px; margin-right: 8px"
          />
          <el-button size="small" type="primary" @click="handleSearch">Search</el-button>
        </div>
      </div>

      <el-table :data="courses" border>
        <el-table-column prop="code" label="Course Code" />
        <el-table-column prop="name" label="Course Name" />
        <el-table-column prop="semester" label="Semester" />
        <el-table-column prop="teacherId" label="Teacher ID" width="110" />
        <el-table-column prop="capacity" label="Capacity" width="100" />
        <el-table-column prop="selectedCount" label="Selected" width="100" />
        <el-table-column label="Remaining" width="100">
          <template slot-scope="scope">
            {{ scope.row.capacity - scope.row.selectedCount }}
          </template>
        </el-table-column>
        <el-table-column v-if="isStudent" label="Action" width="120">
          <template slot-scope="scope">
            <el-button
              v-if="!findSelectionByCourseId(scope.row.id)"
              size="mini"
              type="success"
              :disabled="scope.row.selectedCount >= scope.row.capacity"
              @click="handleSelect(scope.row)"
            >
              Select
            </el-button>
            <el-button
              v-else
              size="mini"
              type="danger"
              @click="handleDrop(scope.row)"
            >
              Drop
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { dropSelection, getCourses, getSelections, selectCourse } from '@/api/modules'
import { REQUEST_FAILED, requireUserId, safelyExecute } from '@/utils/request-helpers'

export default {
  name: 'CourseListView',
  data() {
    return {
      semester: '',
      courses: [],
      selections: []
    }
  },
  computed: {
    isStudent() {
      return localStorage.getItem('role') === 'STUDENT'
    }
  },
  created() {
    this.initialize()
  },
  methods: {
    async initialize() {
      const courses = await safelyExecute(() => this.loadCourses())
      if (courses === REQUEST_FAILED) return
      if (this.isStudent) {
        await safelyExecute(() => this.loadSelections())
      }
    },
    async loadCourses() {
      this.courses = await getCourses({ semester: this.semester || undefined })
      return this.courses
    },
    async loadSelections() {
      const studentId = requireUserId(this)
      if (!studentId) return []
      this.selections = await getSelections(studentId)
      return this.selections
    },
    async handleSearch() {
      await safelyExecute(() => this.loadCourses())
    },
    findSelectionByCourseId(courseId) {
      return this.selections.find(item => item.courseId === courseId)
    },
    async handleSelect(course) {
      const studentId = requireUserId(this)
      if (!studentId) return

      const selectionId = await safelyExecute(() => selectCourse({
        studentId,
        courseId: course.id
      }))
      if (selectionId === REQUEST_FAILED) return
      this.$message.success('Course selected successfully')
      await safelyExecute(() => this.loadSelections())
      await safelyExecute(() => this.loadCourses())
    },
    async handleDrop(course) {
      const studentId = requireUserId(this)
      if (!studentId) return
      const selection = this.findSelectionByCourseId(course.id)
      if (!selection) return
      const dropped = await safelyExecute(() => dropSelection(selection.id, studentId))
      if (dropped === REQUEST_FAILED) return
      this.$message.success('Course dropped successfully')
      await safelyExecute(() => this.loadSelections())
      await safelyExecute(() => this.loadCourses())
    }
  }
}
</script>

<style scoped>
.toolbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
