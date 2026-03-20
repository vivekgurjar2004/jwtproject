import axios from 'axios'
import { getToken } from './token'

// If VITE_API_BASE_URL is unset, we rely on Vite's dev-server proxy for `/api`.
const apiBaseUrl = import.meta.env.VITE_API_BASE_URL ?? ''

export const api = axios.create({
  baseURL: apiBaseUrl,
  headers: {
    'Content-Type': 'application/json'
  }
})

api.interceptors.request.use((config) => {
  const token = getToken()
  if (token) {
    config.headers.Authorization = `Bearer ${token}`
  }
  return config
})

export async function register({ username, email, password }) {
  return api.post('/api/auth/register', { username, email, password })
}

export async function login({ username, password }) {
  return api.post('/api/auth/login', { username, password })
}

export async function fetchProfile() {
  return api.get('/api/profile')
}

