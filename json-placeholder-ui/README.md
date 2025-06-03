# JSON Placeholder UI

A modern, responsive user directory application built with Next.js, TypeScript, and TailwindCSS. This application displays and manages user data from the JSONPlaceholder API with a professional user interface.

## Features

- **User List Display**
  - Responsive table layout
  - Columns for name/email, address, phone, website, and company
  - Modern styling with TailwindCSS
  - Client-side user deletion
  - Interactive row hover effects

- **User Detail Modal**
  - Comprehensive user information display
  - Organized sections for contact, address, and company details
  - Google Maps integration with user coordinates
  - Smooth animations and transitions
  - Keyboard-accessible modal controls

- **Technical Features**
  - Built with Next.js 14 and TypeScript
  - Modern React patterns and hooks
  - Responsive design for all screen sizes
  - Accessibility features (ARIA labels, keyboard navigation)
  - Error handling and loading states

## Tech Stack

- **Framework**: Next.js 14
- **Language**: TypeScript
- **Styling**: TailwindCSS
- **HTTP Client**: Axios
- **API**: JSONPlaceholder

## Getting Started

### Prerequisites

- Node.js 18.0 or later
- npm or yarn package manager

### Installation

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd json-placeholder-ui
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

4. Open [http://localhost:3000](http://localhost:3000) in your browser.

## Project Structure

```
json-placeholder-ui/
├── src/
│   ├── app/              # Next.js app directory
│   ├── components/       # React components
│   │   ├── ui/          # Reusable UI components
│   │   ├── UserTable.tsx
│   │   └── UserModal.tsx
│   └── lib/             # Utility functions and API
│       └── api.ts
├── public/              # Static assets
└── package.json         # Project dependencies
```

## API Integration

The application uses the JSONPlaceholder API to fetch user data. The user object structure includes:

```typescript
interface User {
  id: number;
  name: string;
  username: string;
  email: string;
  address: {
    street: string;
    suite: string;
    city: string;
    zipcode: string;
    geo: {
      lat: string;
      lng: string;
    };
  };
  phone: string;
  website: string;
  company: {
    name: string;
    catchPhrase: string;
    bs: string;
  };
}
```

## Development

- **Development Server**: `npm run dev`
- **Build**: `npm run build`
- **Start Production**: `npm start`
- **Lint**: `npm run lint`

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [JSONPlaceholder](https://jsonplaceholder.typicode.com/) for providing the test API
- [Next.js](https://nextjs.org/) for the React framework
- [TailwindCSS](https://tailwindcss.com/) for the styling framework 