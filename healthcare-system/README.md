# 🏥 Smart Healthcare Appointment System

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)]()
[![License](https://img.shields.io/badge/license-MIT-blue)]()
[![Java](https://img.shields.io/badge/Java-17-orange)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)]()
[![React](https://img.shields.io/badge/React-18-blue)]()

A cloud-native, production-ready healthcare scheduling platform with role-based access control, real-time notifications, and AWS deployment capabilities.

## 🚀 Quick Start (5 Minutes)

```bash
# 1. Clone the repository
git clone https://github.com/your-org/healthcare-system.git
cd healthcare-system

# 2. Start local infrastructure
docker-compose up -d

# 3. Build and run all services
./scripts/start-all.sh

# 4. Access the application
# Frontend: http://localhost:3000
# Auth Service: http://localhost:8081
# Appointment Service: http://localhost:8082
# Notification Service: http://localhost:8083
# Patient Service: http://localhost:8084
```

## 📋 Table of Contents

- [Features](#-features)
- [Architecture](#-architecture)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Running Services](#-running-services)
- [API Documentation](#-api-documentation)
- [Deployment](#-deployment)
- [Testing](#-testing)
- [Monitoring](#-monitoring)
- [Security](#-security)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

- **Appointment Management**: Book, reschedule, and cancel appointments
- **Role-Based Access Control**: Patient, Doctor, and Admin roles
- **Real-time Notifications**: SMS and Email alerts via AWS SNS/SES
- **Multi-tenant Support**: Support for multiple healthcare providers
- **Audit Trail**: Complete logging of all system activities
- **Responsive UI**: Mobile-friendly React frontend
- **Cloud-Ready**: Designed for AWS deployment
- **Microservices Architecture**: Independently scalable services
- **API-First Design**: RESTful APIs with comprehensive documentation

## 🏗️ Architecture

```
┌─────────────┐
│   Client    │ (Web/Mobile)
└──────┬──────┘
       │
┌──────▼──────┐
│ API Gateway │ (Port 8080)
└──────┬──────┘
       │
┌──────▼───────────────────────────────┐
│        Microservices Layer           │
│  ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐│
│  │ Auth │ │Appt. │ │Notif.│ │Patient││
│  │ 8081 │ │ 8082 │ │ 8083 │ │ 8084 ││
│  └──┬───┘ └──┬───┘ └──┬───┘ └──┬───┘│
└─────┼────────┼────────┼────────┼─────┘
      │        │        │        │
┌─────▼────────▼────────▼────────▼─────┐
│           Database Layer             │
│  ┌─────────┐  ┌─────────┐           │
│  │  MySQL  │  │  Redis  │           │
│  └─────────┘  └─────────┘           │
└──────────────────────────────────────┘
           │
┌──────────▼──────────┐
│   AWS Services      │
│  SNS | SES | RDS    │
└─────────────────────┘
```

## 🛠️ Tech Stack

### Backend
- **Language**: Java 17
- **Framework**: Spring Boot 3.2
- **Security**: Spring Security with JWT
- **Database**: MySQL 8.0 / PostgreSQL
- **ORM**: Spring Data JPA
- **API**: RESTful with OpenAPI 3.0
- **Caching**: Redis

### Frontend
- **Framework**: React 18
- **UI Library**: Material-UI
- **State Management**: Redux Toolkit
- **HTTP Client**: Axios
- **Routing**: React Router v6

### DevOps
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **IaC**: Terraform
- **CI/CD**: GitHub Actions / Jenkins
- **Monitoring**: Prometheus + Grafana
- **Cloud**: AWS (EC2, RDS, SNS, SES, EKS)

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Java 17+** - [Download](https://adoptium.net/)
- **Maven 3.9+** - [Download](https://maven.apache.org/download.cgi)
- **Node.js 18+** - [Download](https://nodejs.org/)
- **Docker Desktop** - [Download](https://www.docker.com/products/docker-desktop)
- **Git** - [Download](https://git-scm.com/downloads)

### Optional (for AWS deployment)
- **AWS CLI** - [Install Guide](https://aws.amazon.com/cli/)
- **Terraform** - [Download](https://www.terraform.io/downloads)
- **kubectl** - [Install Guide](https://kubernetes.io/docs/tasks/tools/)

### Verify Installation

```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.9+
node -v          # Should show Node 18+
docker -v        # Should show Docker version
git --version    # Should show Git version
```

## 🔧 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-org/healthcare-system.git
cd healthcare-system
```

### 2. Start Local Infrastructure

```bash
# Start MySQL and Redis
docker-compose up -d

# Verify containers are running
docker-compose ps
```

### 3. Configure Environment Variables

Create `.env` file in the root directory:

```bash
cp .env.example .env
```

Edit `.env` with your configuration:

```properties
# Database Configuration
DB_HOST=localhost
DB_PORT=3306
DB_NAME=healthcare
DB_USER=root
DB_PASSWORD=root

# JWT Configuration
JWT_SECRET=your-super-secret-key-change-this-in-production
JWT_EXPIRATION=86400000

# AWS Configuration (Optional for local development)
AWS_REGION=ap-south-1
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key
SNS_TOPIC_ARN=arn:aws:sns:region:account:topic
SES_FROM_EMAIL=noreply@healthcare.com

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379
```

### 4. Build All Services

```bash
# Build all microservices
./scripts/build-all.sh

# Or build individually
cd services/auth-service && mvn clean install
cd ../appointment-service && mvn clean install
cd ../notification-service && mvn clean install
cd ../patient-service && mvn clean install
```

### 5. Install Frontend Dependencies

```bash
cd frontend
npm install
```

## 🚀 Running Services

### Option 1: Run All Services (Recommended for Development)

```bash
# From root directory
./scripts/start-all.sh
```

### Option 2: Run Services Individually

```bash
# Terminal 1 - Auth Service
cd services/auth-service
mvn spring-boot:run

# Terminal 2 - Appointment Service
cd services/appointment-service
mvn spring-boot:run

# Terminal 3 - Notification Service
cd services/notification-service
mvn spring-boot:run

# Terminal 4 - Patient Service
cd services/patient-service
mvn spring-boot:run

# Terminal 5 - Frontend
cd frontend
npm start
```

### Option 3: Run with Docker Compose

```bash
# Build and run all services
docker-compose up --build

# Run in detached mode
docker-compose up -d
```

## 🌐 Access Points

Once all services are running:

| Service | URL | Purpose |
|---------|-----|---------|
| Frontend | http://localhost:3000 | Web Application |
| Auth Service | http://localhost:8081 | Authentication & Authorization |
| Appointment Service | http://localhost:8082 | Appointment Management |
| Notification Service | http://localhost:8083 | Email/SMS Notifications |
| Patient Service | http://localhost:8084 | Patient Management |
| Swagger UI | http://localhost:8081/swagger-ui.html | API Documentation |

### Default Credentials

| Role | Username | Password |
|------|----------|----------|
| Admin | admin@healthcare.com | Admin@123 |
| Doctor | doctor@healthcare.com | Doctor@123 |
| Patient | patient@healthcare.com | Patient@123 |

## 📚 API Documentation

### Authentication Endpoints

```bash
# Login
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "email": "patient@healthcare.com",
  "password": "Patient@123"
}

# Register
POST http://localhost:8081/api/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "Password@123",
  "phone": "+1234567890",
  "role": "PATIENT"
}
```

### Appointment Endpoints

```bash
# Book Appointment
POST http://localhost:8082/api/appointments
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "doctorId": 1,
  "patientId": 2,
  "appointmentDate": "2026-02-15",
  "appointmentTime": "10:00",
  "reason": "Regular checkup"
}

# Get Appointments
GET http://localhost:8082/api/appointments
Authorization: Bearer <jwt-token>
```

For complete API documentation, visit: http://localhost:8081/swagger-ui.html

## ☁️ Deployment

### AWS Deployment with Terraform

```bash
# Navigate to terraform directory
cd infra/terraform

# Initialize Terraform
terraform init

# Review planned changes
terraform plan

# Apply infrastructure
terraform apply

# Note the output values (RDS endpoint, Load Balancer URL, etc.)
```

### Kubernetes Deployment

```bash
# Build and push Docker images
./scripts/docker-build-push.sh

# Deploy to Kubernetes
kubectl apply -f infra/kubernetes/

# Check deployment status
kubectl get pods
kubectl get services
```

### Manual Docker Deployment

```bash
# Build images
docker-compose build

# Tag and push to registry
docker tag auth-service:latest your-registry/auth-service:latest
docker push your-registry/auth-service:latest

# Deploy on server
ssh your-server
docker pull your-registry/auth-service:latest
docker run -d -p 8081:8081 your-registry/auth-service:latest
```

## 🧪 Testing

### Run Unit Tests

```bash
# Test all services
./scripts/test-all.sh

# Test individual service
cd services/auth-service
mvn test
```

### Run Integration Tests

```bash
cd services/auth-service
mvn verify
```

### API Testing with Postman

Import the Postman collection:
```bash
postman/Healthcare-API.postman_collection.json
```

## 📊 Monitoring

### Prometheus Metrics

Access metrics at:
- http://localhost:8081/actuator/metrics
- http://localhost:8081/actuator/health

### Grafana Dashboard

1. Start Grafana: `docker-compose up grafana`
2. Access: http://localhost:3001
3. Login: admin/admin
4. Import dashboard from `monitoring/grafana-dashboard.json`

### Application Logs

```bash
# View logs
docker-compose logs -f auth-service

# View specific service logs
tail -f services/auth-service/logs/application.log
```

## 🔒 Security

### Best Practices Implemented

✅ JWT-based authentication
✅ Role-based access control (RBAC)
✅ Password encryption (BCrypt)
✅ HTTPS enforcement in production
✅ SQL injection prevention (JPA)
✅ XSS protection
✅ CSRF protection
✅ Rate limiting
✅ Input validation
✅ Secrets management (AWS Secrets Manager)

### Security Checklist

- [ ] Change default credentials
- [ ] Update JWT secret in production
- [ ] Enable HTTPS
- [ ] Configure firewall rules
- [ ] Enable database encryption
- [ ] Set up AWS IAM roles
- [ ] Enable CloudWatch logging
- [ ] Configure backup strategy
- [ ] Implement rate limiting
- [ ] Regular security audits

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Standards

- Follow Java coding conventions
- Write unit tests for new features
- Update documentation
- Run `mvn checkstyle:check` before committing

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

- **Documentation**: [Wiki](https://github.com/your-org/healthcare-system/wiki)
- **Issues**: [GitHub Issues](https://github.com/your-org/healthcare-system/issues)
- **Email**: support@healthcare.com
- **Slack**: [Join our community](https://healthcare-slack.com)

## 🎯 Roadmap

- [ ] Telemedicine integration
- [ ] Mobile apps (iOS/Android)
- [ ] AI-powered appointment scheduling
- [ ] Multi-language support
- [ ] Payment gateway integration
- [ ] Electronic Health Records (EHR)
- [ ] Prescription management
- [ ] Analytics dashboard

## 🙏 Acknowledgments

- Spring Boot Team
- React Community
- AWS Documentation
- All Contributors

---

**Made with ❤️ for better healthcare**

For detailed service-specific documentation, see:
- [Auth Service README](services/auth-service/README.md)
- [Appointment Service README](services/appointment-service/README.md)
- [Notification Service README](services/notification-service/README.md)
- [Patient Service README](services/patient-service/README.md)
