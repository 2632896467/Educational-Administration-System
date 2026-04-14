import { Message } from 'element-ui'

export const REQUEST_FAILED = Symbol('REQUEST_FAILED')

export async function safelyExecute(task) {
  try {
    return await task()
  } catch (error) {
    return REQUEST_FAILED
  }
}

export function requireUserId(vm) {
  const rawUserId = localStorage.getItem('userId')
  const userId = Number(rawUserId)

  if (Number.isInteger(userId) && userId > 0) {
    return userId
  }

  Message.error('登录信息已失效，请重新登录')
  localStorage.clear()
  if (vm && vm.$router) {
    vm.$router.replace('/login')
  }
  return null
}
