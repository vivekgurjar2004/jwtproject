import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import { login } from '../lib/apiClient'
import { setToken } from '../lib/token'

export default function LoginPage() {
  const navigate = useNavigate()

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  async function onSubmit(e) {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      const res = await login({ username, password })
      setToken(res.data.token)
      navigate('/profile')
    } catch (err) {
      const msg =
        err?.response?.data?.error ||
        err?.response?.data?.message ||
        'Login failed'
      setError(msg)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="card">
      <h1 className="title">Login</h1>
      <form onSubmit={onSubmit}>
        <div className="field">
          <label>Username</label>
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            autoComplete="username"
          />
        </div>
        <div className="field">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            autoComplete="current-password"
          />
        </div>

        {error ? <div className="error">{error}</div> : null}

        <div className="field" style={{ marginTop: 14 }}>
          <button type="submit" disabled={loading}>
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </div>
      </form>

      <div style={{ marginTop: 14 }}>
        No account? <Link to="/register">Register</Link>
      </div>
    </div>
  )
}

