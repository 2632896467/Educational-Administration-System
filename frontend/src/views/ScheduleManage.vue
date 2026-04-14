<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="12">
        <el-card style="margin-bottom: 16px">
          <div slot="header">Create Course</div>
          <el-form ref="courseFormRef" :model="courseForm" :rules="courseRules" label-width="120px">
            <el-form-item label="Course Code" prop="code">
              <el-input v-model="courseForm.code" />
            </el-form-item>
            <el-form-item label="Course Name" prop="name">
              <el-input v-model="courseForm.name" />
            </el-form-item>
            <el-form-item label="Semester" prop="semester">
              <el-input v-model="courseForm.semester" placeholder="e.g. 2026-Spring" />
            </el-form-item>
            <el-form-item label="Teacher ID" prop="teacherId">
              <el-input-number v-model="courseForm.teacherId" :min="1" />
            </el-form-item>
            <el-form-item label="Capacity" prop="capacity">
              <el-input-number v-model="courseForm.capacity" :min="1" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleCreateCourse">Create Course</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card style="margin-bottom: 16px">
          <div slot="header">Create Schedule</div>
          <el-form ref="scheduleFormRef" :model="scheduleForm" :rules="scheduleRules" label-width="120px">
            <el-form-item label="Course" prop="courseId">
              <el-select v-model="scheduleForm.courseId" placeholder="Select course">
                <el-option v-for="item in courses" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
            <el-form-item label="Classroom" prop="classroom">
              <el-input v-model="scheduleForm.classroom" />
            </el-form-item>
            <el-form-item label="Week Day" prop="weekDay">
              <el-input-number v-model="scheduleForm.weekDay" :min="1" :max="7" />
            </el-form-item>
            <el-form-item label="Start Section" prop="startSection">
              <el-input-number v-model="scheduleForm.startSection" :min="1" />
            </el-form-item>
            <el-form-item label="End Section" prop="endSection">
              <el-input-number v-model="scheduleForm.endSection" :min="1" />
            </el-form-item>
            <el-form-item label="Weeks" prop="weeks">
              <el-input v-model="scheduleForm.weeks" placeholder="e.g. 1-16" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handleCreateSchedule">Create Schedule</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <div slot="header" class="schedule-header">
        <span>Schedule Query</span>
        <div>
          <el-select v-model="queryCourseId" placeholder="Select course" size="small" style="width: 220px">
            <el-option v-for="item in courses" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
          <el-button size="small" type="primary" style="margin-left: 8px" @click="handleLoadSchedule">Search</el-button>
        </div>
      </div>
      <el-table :data="schedules" border>
        <el-table-column prop="courseId" label="Course ID" width="100" />
        <el-table-column prop="classroom" label="Classroom" />
        <el-table-column prop="weekDay" label="Week Day" width="100" />
        <el-table-column prop="startSection" label="Start" width="100" />
        <el-table-column prop="endSection" label="End" width="100" />
        <el-table-column prop="weeks" label="Weeks" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { createCourse, createSchedule, getCourses, getScheduleByCourse } from '@/api/modules'
import { REQUEST_FAILED, safelyExecute } from '@/utils/request-helpers'

export default {
  name: 'ScheduleManageView',
  data() {
    return {
      courseForm: {
        code: '',
        name: '',
        semester: '2026-Spring',
        teacherId: 1,
        capacity: 60
      },
      courseRules: {
        code: [{ required: true, message: 'Please input course code', trigger: 'blur' }],
        name: [{ required: true, message: 'Please input course name', trigger: 'blur' }],
        semester: [{ required: true, message: 'Please input semester', trigger: 'blur' }],
        teacherId: [{ required: true, message: 'Please input teacher ID', trigger: 'change' }],
        capacity: [{ required: true, message: 'Please input capacity', trigger: 'change' }]
      },
      scheduleForm: {
        courseId: null,
        classroom: '',
        weekDay: 1,
        startSection: 1,
        endSection: 2,
        weeks: '1-16'
      },
      scheduleRules: {
        courseId: [{ required: true, message: 'Please select course', trigger: 'change' }],
        classroom: [{ required: true, message: 'Please input classroom', trigger: 'blur' }],
        weekDay: [{ required: true, message: 'Please input week day', trigger: 'change' }],
        startSection: [{ required: true, message: 'Please input start section', trigger: 'change' }],
        endSection: [{ required: true, message: 'Please input end section', trigger: 'change' }],
        weeks: [{ required: true, message: 'Please input weeks', trigger: 'blur' }]
      },
      queryCourseId: null,
      courses: [],
      schedules: []
    }
  },
  created() {
    this.initialize()
  },
  methods: {
    async initialize() {
      await safelyExecute(() => this.loadCourses())
    },
    async loadCourses() {
      this.courses = await getCourses()
      if (!this.scheduleForm.courseId && this.courses.length > 0) {
        this.scheduleForm.courseId = this.courses[0].id
      }
      if (!this.queryCourseId && this.courses.length > 0) {
        this.queryCourseId = this.courses[0].id
      }
      return this.courses
    },
    handleCreateCourse() {
      this.$refs.courseFormRef.validate(async valid => {
        if (!valid) return

        const course = await safelyExecute(() => createCourse(this.courseForm))
        if (course === REQUEST_FAILED) return

        this.$message.success('Course created successfully')
        this.courseForm.code = ''
        this.courseForm.name = ''
        await safelyExecute(() => this.loadCourses())
      })
    },
    handleCreateSchedule() {
      this.$refs.scheduleFormRef.validate(async valid => {
        if (!valid) return
        if (this.scheduleForm.endSection < this.scheduleForm.startSection) {
          this.$message.error('End section must be greater than or equal to start section')
          return
        }

        const schedule = await safelyExecute(() => createSchedule(this.scheduleForm))
        if (schedule === REQUEST_FAILED) return

        this.$message.success('Schedule created successfully')
        await safelyExecute(() => this.loadSchedule())
      })
    },
    async loadSchedule() {
      if (!this.queryCourseId) return
      this.schedules = await getScheduleByCourse(this.queryCourseId)
      return this.schedules
    },
    async handleLoadSchedule() {
      await safelyExecute(() => this.loadSchedule())
    }
  }
}
</script>

<style scoped>
.schedule-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
