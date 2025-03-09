# Flag Explorer Application

**Created by: Liyaqat Mugjenkar**
**Date: March 2024**

## 🌍 Application Overview

Flag Explorer is a comprehensive web application that allows users to browse and explore detailed information about countries from around the world. The application provides an intuitive interface to view country flags and access key information.

## 🏗️ Architecture

### Technology Stack
- **Frontend**: Vue.js 3 with TypeScript
- **Backend**: Spring Boot 3.4.3
- **Build Tool**: Gradle
- **CI/CD**: GitHub Actions
- **Deployment**: Docker

### System Architecture
```
┌─────────────────┐
│   Vue Frontend  │
│  (TypeScript)   │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│ Spring Backend  │
│   (Java 21)     │
└────────┬────────┘
         │
         ▼
┌───────────────────────┐
│    Rest CountriesAPI  │
└───────────────────────┘
```

## 🚀 Local Development Setup

### Prerequisites
- Java 21
- Node.js 18+
- npm 9+
- Gradle

### Local Run (Development Mode)

1. Clone the Repository
```bash
git clone https://github.com/your-username/flag-explorer.git
cd flag-explorer
```

2. Run Backend
```bash
cd backend
./gradlew bootRun
```

3. Run Frontend
```bash
cd frontend
npm run dev
```

4. Access the Application
- Frontend: `http://localhost:5173`
- Backend API: `http://localhost:8080`

## 🐳 Production Deployment

### Build and Deploy Options

#### Docker Deployment
1. Build the application
```bash
./gradlew bootJar
npm run build:springboot
docker build -t flag-explorer .
docker run -p 8080:8080 flag-explorer
```

#### Cloud Deployment
- Supports deployment on:
    - Kubernetes
    - AWS ECS
    - Google Cloud Run
    - Azure App Service

### CI/CD Pipeline
- Automated testing
- Code quality checks
- Docker image build
- Automatic deployment to staging/production

## 🧪 Testing

### Backend Tests
```bash
./gradlew test
```

### Frontend Tests
```bash
cd frontend
npm run test:unit
```

## 🔒 Security
- HTTPS enabled
- Input validation
- Secure API endpoints

## 📝 Configuration
- Environment-specific configurations
- Externalized configuration support


## 📧 Contact
Liyaqat M
- GitHub: @yourgithubhandle
