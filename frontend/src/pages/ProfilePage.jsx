import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { fetchProfile } from '../lib/apiClient'
import { clearToken } from '../lib/token'

export default function ProfilePage() {
  const navigate = useNavigate()

  const [profile, setProfile] = useState(null)
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    let active = true

    async function load() {
      setLoading(true)
      setError('')
      try {
        const res = await fetchProfile()
        if (!active) return
        setProfile(res.data)
      } catch (err) {
        const status = err?.response?.status
        if (status === 401) {
          clearToken()
          navigate('/login')
          return
        }
        const msg =
          err?.response?.data?.error ||
          err?.response?.data?.message ||
          'Failed to load profile'
        if (!active) return
        setError(msg)
      } finally {
        if (!active) return
        setLoading(false)
      }
    }

    load()
    return () => {
      active = false
    }
  }, [navigate])

  function onLogout() {
    clearToken()
    navigate('/login')
  }

  return (
    <div className="card">
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <h1 className="title" style={{ marginBottom: 0 }}>
          Profile
        </h1>
        <button className="secondary" onClick={onLogout} type="button">
          Logout
        </button>
      </div>

      {loading ? <div style={{ marginTop: 12 }}>Loading...</div> : null}
      {error ? <div className="error">{error}</div> : null}

      {profile && !loading ? (
        <div style={{ marginTop: 12 }}>
          <div>
            <strong>Username:</strong> {profile.username || '-'}
          </div>
          <div style={{ marginTop: 8 }}>
            <strong>Email:</strong> {profile.email || '-'}
          </div>
          <div style={{ marginTop: 8 }}>
            <strong>Roles:</strong> {Array.isArray(profile.roles) ? profile.roles.join(', ') : ''}
          </div>
        </div>
      ) : null}
    </div>
  )
}

