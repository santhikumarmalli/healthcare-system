# 🚀 Deployment Guide

## Quick Start (Local Development)

### Prerequisites
- Java 17+
- Maven 3.9+
- Node.js 18+
- Docker Desktop
- MySQL 8.0 (via Docker)

### 1. Start Infrastructure
```bash
docker-compose up -d mysql redis
```

### 2. Build Services
```bash
./scripts/build-all.sh
```

### 3. Start All Services
```bash
./scripts/start-all.sh
```

### 4. Access Application
- Frontend: http://localhost:3000
- Auth API: http://localhost:8081
- Swagger: http://localhost:8081/swagger-ui.html

## Default Credentials

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@healthcare.com | Admin@123 |
| Doctor | doctor@healthcare.com | Doctor@123 |
| Patient | patient@healthcare.com | Patient@123 |

## Docker Deployment

### Build All Images
```bash
docker-compose build
```

### Run Everything
```bash
docker-compose up -d
```

### Stop Everything
```bash
docker-compose down
```

## AWS Cloud Deployment

### 1. Configure AWS CLI
```bash
aws configure
```

### 2. Deploy Infrastructure
```bash
cd infra/terraform
terraform init
terraform plan
terraform apply
```

### 3. Update Environment Variables
Update `.env` with AWS RDS endpoint and SNS topic ARN from Terraform outputs.

### 4. Deploy Services
```bash
# Build and push Docker images to ECR
docker build -t your-registry/auth-service:latest services/auth-service
docker push your-registry/auth-service:latest

# Repeat for all services
```

## Kubernetes Deployment

### 1. Create Namespace
```bash
kubectl create namespace healthcare
```

### 2. Apply Configurations
```bash
kubectl apply -f infra/kubernetes/ -n healthcare
```

### 3. Check Status
```bash
kubectl get pods -n healthcare
kubectl get services -n healthcare
```

## Environment Variables

### Required Variables
```
DB_HOST=<your-mysql-host>
DB_PORT=3306
DB_NAME=healthcare
DB_USER=<username>
DB_PASSWORD=<password>
JWT_SECRET=<256-bit-secret>
```

### Optional AWS Variables
```
AWS_REGION=ap-south-1
SNS_TOPIC_ARN=<arn>
SES_FROM_EMAIL=<email>
```

## Monitoring

### Prometheus
```bash
http://localhost:9090
```

### Grafana
```bash
http://localhost:3001
Login: admin/admin
```

## Troubleshooting

### Service Won't Start
```bash
# Check logs
tail -f logs/auth-service.log

# Check if port is in use
lsof -i :8081
```

### Database Connection Failed
```bash
# Verify MySQL is running
docker ps | grep mysql

# Check connectivity
docker exec -it healthcare-mysql mysql -u root -p
```

### Frontend Can't Connect to Backend
- Verify REACT_APP_API_BASE_URL in `.env`
- Check CORS configuration in SecurityConfig.java
- Ensure all services are running

## Health Checks

### Auth Service
```bash
curl http://localhost:8081/actuator/health
```

### All Services
```bash
for port in 8081 8082 8083 8084; do
  curl http://localhost:$port/actuator/health
done
```

## Backup and Restore

### Backup Database
```bash
docker exec healthcare-mysql mysqldump -u root -proot healthcare > backup.sql
```

### Restore Database
```bash
docker exec -i healthcare-mysql mysql -u root -proot healthcare < backup.sql
```

## Security Checklist

- [ ] Change all default passwords
- [ ] Update JWT_SECRET to a secure 256-bit key
- [ ] Enable HTTPS in production
- [ ] Configure firewall rules
- [ ] Enable database encryption
- [ ] Set up AWS IAM roles
- [ ] Enable CloudWatch logging
- [ ] Configure backup strategy
- [ ] Implement rate limiting
- [ ] Regular security audits

## Performance Tuning

### JVM Options
```bash
JAVA_OPTS="-Xms512m -Xmx2g -XX:+UseG1GC"
```

### Database Optimization
- Add indexes on frequently queried columns
- Enable query caching
- Configure connection pooling

### Application Scaling
- Increase replica count in Kubernetes
- Use load balancer for traffic distribution
- Enable caching with Redis

## Support

For issues and questions:
- GitHub Issues: [repository-url]/issues
- Documentation: [repository-url]/wiki
- Email: support@healthcare.com
