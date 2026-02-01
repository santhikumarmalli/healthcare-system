# ⚡ Quick Start Guide (5 Minutes)

## Prerequisites Check
```bash
java -version    # Should show Java 17+
mvn -version     # Should show Maven 3.9+
node -v          # Should show Node 18+
docker -v        # Should show Docker version
```

## Step 1: Clone & Navigate
```bash
git clone <your-repo-url>
cd healthcare-system
```

## Step 2: Copy Environment File
```bash
cp .env.example .env
```

## Step 3: Start Infrastructure
```bash
docker-compose up -d mysql redis
```

Wait 30 seconds for MySQL to initialize.

## Step 4: Start All Services
```bash
chmod +x scripts/*.sh
./scripts/start-all.sh
```

This will:
- Build all Java services
- Start all microservices
- Start the React frontend

## Step 5: Access the Application

### Frontend
Open browser: http://localhost:3000

### Default Login Credentials
- **Admin**: admin@healthcare.com / Admin@123
- **Doctor**: doctor@healthcare.com / Doctor@123
- **Patient**: patient@healthcare.com / Patient@123

### API Documentation
Swagger UI: http://localhost:8081/swagger-ui.html

## Service Ports

| Service | Port | URL |
|---------|------|-----|
| Frontend | 3000 | http://localhost:3000 |
| Auth Service | 8081 | http://localhost:8081 |
| Appointment Service | 8082 | http://localhost:8082 |
| Notification Service | 8083 | http://localhost:8083 |
| Patient Service | 8084 | http://localhost:8084 |
| MySQL | 3306 | localhost:3306 |
| Redis | 6379 | localhost:6379 |
| Prometheus | 9090 | http://localhost:9090 |
| Grafana | 3001 | http://localhost:3001 |

## Testing the APIs

### 1. Register a New User
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Test",
    "lastName": "User",
    "email": "test@example.com",
    "password": "Test@123",
    "phone": "+1234567890",
    "role": "PATIENT"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test@123"
  }'
```

Save the token from the response.

### 3. Create an Appointment
```bash
curl -X POST http://localhost:8082/api/appointments \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <your-token>" \
  -d '{
    "patientId": 1,
    "doctorId": 2,
    "appointmentDate": "2026-02-15",
    "appointmentTime": "10:00",
    "reason": "Regular checkup"
  }'
```

## Stop All Services
```bash
./scripts/stop-all.sh
```

## Troubleshooting

### Services Won't Start
```bash
# Check if ports are already in use
lsof -i :8081
lsof -i :3000

# Kill processes using these ports if needed
kill -9 <PID>
```

### Database Connection Issues
```bash
# Check if MySQL is running
docker ps | grep mysql

# View MySQL logs
docker logs healthcare-mysql

# Connect to MySQL
docker exec -it healthcare-mysql mysql -u root -proot healthcare
```

### View Service Logs
```bash
# Auth Service
tail -f logs/auth-service.log

# All services
tail -f logs/*.log
```

### Clean Start
```bash
# Stop everything
./scripts/stop-all.sh
docker-compose down -v

# Remove all generated files
rm -rf logs/*.log logs/*.pid

# Start fresh
docker-compose up -d mysql redis
./scripts/start-all.sh
```

## Next Steps

1. **Explore the UI**: Login and navigate through the dashboard
2. **Read API Docs**: Check API.md for complete endpoint documentation
3. **Deploy to AWS**: Follow DEPLOYMENT.md for cloud deployment
4. **Customize**: Modify services to fit your requirements

## Project Structure
```
healthcare-system/
├── services/
│   ├── auth-service/
│   ├── appointment-service/
│   ├── notification-service/
│   └── patient-service/
├── frontend/
├── infra/
│   ├── terraform/
│   └── kubernetes/
├── scripts/
├── docker/
└── docker-compose.yml
```

## Getting Help

- **Full Documentation**: See README.md
- **API Reference**: See API.md
- **Deployment Guide**: See DEPLOYMENT.md
- **Issues**: Create an issue on GitHub

## Development Mode

### Run Individual Service
```bash
cd services/auth-service
mvn spring-boot:run
```

### Build Individual Service
```bash
cd services/auth-service
mvn clean package
```

### Run Frontend Only
```bash
cd frontend
npm install
npm start
```

---

**🎉 You're all set! The system should now be running on your local machine.**
