import axios from 'axios'
import { Message } from 'element-ui'

const service = axios.create({
  // Default to same-origin requests; in dev this is proxied by vue.config.js.
  baseURL: process.env.VUE_APP_BASE_API || '/',
  timeout: 10000
})

service.interceptors.request.use(config => {
  const token = localStorage.getItem('token')
  if (token) {
    config.headers.Authorization = token
  }
  return config
})

service.interceptors.response.use(
  response => {
    const payload = response.data
    if (payload && payload.code === 0) {
      return payload.data
    }
    Message.error(payload?.message || 'Request failed')
    return Promise.reject(new Error(payload?.message || 'Request failed'))
  },
  error => {
    const isNetworkError = !error?.response && String(error?.message || '').toLowerCase().includes('network')
    const message = isNetworkError
      ? '网络错误：无法连接后端服务，请确认后端已启动（http://127.0.0.1:8080）'
      : (error?.response?.data?.message || error.message || 'Network error')
    Message.error(message)
    return Promise.reject(error)
  }
)

export default service
