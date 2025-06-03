import { type User } from '@/lib/api'
import { Card } from './ui/card'

interface UserTableProps {
  users: User[]
  onUserClick: (user: User) => void
  onDeleteUser: (userId: number) => void
}

export const UserTable = ({ users, onUserClick, onDeleteUser }: UserTableProps) => {
  const handleDeleteClick = (event: React.MouseEvent, userId: number) => {
    event.stopPropagation()
    onDeleteUser(userId)
  }

  return (
    <div className="overflow-x-auto">
      <table className="w-full border-collapse">
        <thead>
          <tr className="bg-gray-50">
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Name / Email
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Address
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Phone
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Website
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Company
            </th>
            <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
              Actions
            </th>
          </tr>
        </thead>
        <tbody className="bg-white divide-y divide-gray-200">
          {users.map((user) => (
            <tr
              key={user.id}
              onClick={() => onUserClick(user)}
              className="hover:bg-gray-50 cursor-pointer transition-colors"
            >
              <td className="px-6 py-4 whitespace-nowrap">
                <div className="text-sm font-medium text-gray-900">{user.name}</div>
                <div className="text-sm text-gray-500">{user.email}</div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <div className="text-sm text-gray-900">
                  {user.address.street}, {user.address.suite}
                </div>
                <div className="text-sm text-gray-500">
                  {user.address.city}, {user.address.zipcode}
                </div>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                {user.phone}
              </td>
              <td className="px-6 py-4 whitespace-nowrap">
                <a
                  href={`https://${user.website}`}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-sm text-blue-600 hover:text-blue-800"
                  onClick={(e) => e.stopPropagation()}
                >
                  {user.website}
                </a>
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                {user.company.name}
              </td>
              <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                <button
                  onClick={(e) => handleDeleteClick(e, user.id)}
                  className="text-red-600 hover:text-red-900"
                >
                  Delete
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
} 