import { type ReactNode } from 'react'
import { clsx } from 'clsx'

type CardProps = {
  children: ReactNode
  className?: string
  onClick?: () => void
  role?: string
  'aria-label'?: string
}

export const Card = ({
  children,
  className,
  onClick,
  role = 'article',
  'aria-label': ariaLabel,
}: CardProps) => {
  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === 'Enter' || event.key === ' ') {
      event.preventDefault()
      onClick?.()
    }
  }

  return (
    <div
      className={clsx(
        'bg-white rounded-lg shadow-md p-6 transition-shadow hover:shadow-lg',
        onClick && 'cursor-pointer',
        className
      )}
      onClick={onClick}
      onKeyDown={handleKeyDown}
      role={role}
      aria-label={ariaLabel}
      tabIndex={onClick ? 0 : undefined}
    >
      {children}
    </div>
  )
} 