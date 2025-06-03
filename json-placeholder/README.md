# JSON Placeholder API Implementation

This project is a Spring Boot implementation of the JSON Placeholder API with additional features like authentication and security headers.

## Features

- RESTful API endpoints for users, posts, comments, and todos
- JWT-based authentication
- Security headers for enhanced protection
- Environment-specific configurations
- Comprehensive logging
- Docker support for easy deployment

## Prerequisites

- Java 21 or higher
- Maven 3.8 or higher
- PostgreSQL 12 or higher
- Docker and Docker Compose (optional)

## Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/json-placeholder.git
cd json-placeholder
```

2. Configure the database:
- Create a PostgreSQL database
- Update the database configuration in `src/main/resources/application-dev.properties` or use environment variables in production

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
# Development
mvn spring-boot:run -Dspring.profiles.active=dev

# Production
mvn spring-boot:run -Dspring.profiles.active=prod
```

## Docker Deployment

1. Build the Docker image:
```bash
docker build -t json-placeholder .
```

2. Run with Docker Compose:
```bash
docker-compose up -d
```

## API Documentation

Once the application is running, you can access the API documentation at:
```
http://localhost:8080/api/swagger-ui.html
```

## Security

The API is protected with JWT authentication. To access protected endpoints:

1. Register a new user:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

2. Login to get a JWT token:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'
```

3. Use the token in subsequent requests:
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Security Headers

The following security headers are enabled:
- Content Security Policy (CSP)
- X-Frame-Options
- X-Content-Type-Options
- X-XSS-Protection
- Strict-Transport-Security (HSTS)
- Referrer-Policy
- Permissions-Policy

## Logging

Logs are written to both console and file:
- Console: Colored output for better readability
- File: Rolling file logs in the `logs` directory

## Testing

Run the test suite:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 
