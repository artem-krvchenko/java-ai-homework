'use client'

import { useState, useEffect } from 'react'
import { type User, getUsers } from '@/lib/api'
import { UserTable } from '@/components/UserTable'
import { UserModal } from '@/components/UserModal'

export default function Home() {
  const [users, setUsers] = useState<User[]>([])
  const [selectedUser, setSelectedUser] = useState<User | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const data = await getUsers()
        setUsers(data)
      } catch (err) {
        setError('Failed to fetch users. Please try again later.')
      } finally {
        setIsLoading(false)
      }
    }

    fetchUsers()
  }, [])

  const handleUserClick = (user: User) => {
    setSelectedUser(user)
  }

  const handleDeleteUser = (userId: number) => {
    setUsers((prevUsers) => prevUsers.filter((user) => user.id !== userId))
  }

  const handleCloseModal = () => {
    setSelectedUser(null)
  }

  if (isLoading) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="min-h-screen flex items-center justify-center">
        <div className="text-red-600 text-center">
          <p className="text-xl font-semibold mb-2">Error</p>
          <p>{error}</p>
        </div>
      </div>
    )
  }

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-4xl font-bold text-gray-900 mb-8">
        User Directory
      </h1>
      <div className="bg-white rounded-lg shadow">
        <UserTable
          users={users}
          onUserClick={handleUserClick}
          onDeleteUser={handleDeleteUser}
        />
      </div>
      <UserModal user={selectedUser} onClose={handleCloseModal} />
    </div>
  )
} 