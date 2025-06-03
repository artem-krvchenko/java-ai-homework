import { type User } from '@/lib/api'
import { Card } from './ui/card'

interface UserModalProps {
  user: User | null
  onClose: () => void
}

export const UserModal = ({ user, onClose }: UserModalProps) => {
  if (!user) return null

  const mapUrl = `https://www.google.com/maps?q=${user.address.geo.lat},${user.address.geo.lng}`

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4 z-50">
      <Card className="w-full max-w-2xl max-h-[90vh] overflow-y-auto">
        <div className="p-6">
          <div className="flex justify-between items-start mb-6">
            <h2 className="text-2xl font-bold text-gray-900">{user.name}</h2>
            <button
              onClick={onClose}
              className="text-gray-400 hover:text-gray-500"
              aria-label="Close modal"
            >
              <svg
                className="h-6 w-6"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M6 18L18 6M6 6l12 12"
                />
              </svg>
            </button>
          </div>

          <div className="space-y-6">
            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-2">Contact Information</h3>
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <p className="text-sm text-gray-500">Email</p>
                  <p className="text-sm font-medium">{user.email}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Phone</p>
                  <p className="text-sm font-medium">{user.phone}</p>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Website</p>
                  <a
                    href={`https://${user.website}`}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="text-sm font-medium text-blue-600 hover:text-blue-800"
                  >
                    {user.website}
                  </a>
                </div>
                <div>
                  <p className="text-sm text-gray-500">Username</p>
                  <p className="text-sm font-medium">{user.username}</p>
                </div>
              </div>
            </div>

            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-2">Address</h3>
              <div className="space-y-2">
                <p className="text-sm">
                  {user.address.street}, {user.address.suite}
                </p>
                <p className="text-sm">
                  {user.address.city}, {user.address.zipcode}
                </p>
                <a
                  href={mapUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="text-sm text-blue-600 hover:text-blue-800 inline-flex items-center"
                >
                  <svg
                    className="h-4 w-4 mr-1"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"
                    />
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"
                    />
                  </svg>
                  View on Map
                </a>
              </div>
            </div>

            <div>
              <h3 className="text-lg font-medium text-gray-900 mb-2">Company</h3>
              <div className="space-y-2">
                <p className="text-sm font-medium">{user.company.name}</p>
                <p className="text-sm text-gray-500">{user.company.catchPhrase}</p>
                <p className="text-sm text-gray-500">{user.company.bs}</p>
              </div>
            </div>
          </div>
        </div>
      </Card>
    </div>
  )
} 