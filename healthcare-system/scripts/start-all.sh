#!/bin/bash

# Healthcare System - Start All Services Script
# This script builds and starts all microservices

set -e

echo "🏥 Healthcare System - Starting All Services"
echo "=============================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo -e "${RED}❌ Docker is not running. Please start Docker first.${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Docker is running${NC}"

# Start infrastructure
echo ""
echo -e "${YELLOW}📦 Starting infrastructure (MySQL, Redis)...${NC}"
docker-compose up -d mysql redis

# Wait for MySQL to be ready
echo -e "${YELLOW}⏳ Waiting for MySQL to be ready...${NC}"
until docker exec healthcare-mysql mysqladmin ping -h localhost --silent; do
    printf '.'
    sleep 2
done
echo -e "${GREEN}✅ MySQL is ready${NC}"

# Build all services
echo ""
echo -e "${YELLOW}🔨 Building all services...${NC}"

services=("auth-service" "appointment-service" "notification-service" "patient-service")

for service in "${services[@]}"; do
    echo -e "${YELLOW}Building $service...${NC}"
    cd services/$service
    mvn clean package -DskipTests
    cd ../..
    echo -e "${GREEN}✅ $service built successfully${NC}"
done

# Start all services in background
echo ""
echo -e "${YELLOW}🚀 Starting microservices...${NC}"

cd services/auth-service
nohup mvn spring-boot:run > ../../logs/auth-service.log 2>&1 &
echo $! > ../../logs/auth-service.pid
cd ../..
echo -e "${GREEN}✅ Auth Service started (PID: $(cat logs/auth-service.pid))${NC}"

sleep 5

cd services/appointment-service
nohup mvn spring-boot:run > ../../logs/appointment-service.log 2>&1 &
echo $! > ../../logs/appointment-service.pid
cd ../..
echo -e "${GREEN}✅ Appointment Service started (PID: $(cat logs/appointment-service.pid))${NC}"

cd services/notification-service
nohup mvn spring-boot:run > ../../logs/notification-service.log 2>&1 &
echo $! > ../../logs/notification-service.pid
cd ../..
echo -e "${GREEN}✅ Notification Service started (PID: $(cat logs/notification-service.pid))${NC}"

cd services/patient-service
nohup mvn spring-boot:run > ../../logs/patient-service.log 2>&1 &
echo $! > ../../logs/patient-service.pid
cd ../..
echo -e "${GREEN}✅ Patient Service started (PID: $(cat logs/patient-service.pid))${NC}"

# Start frontend
echo ""
echo -e "${YELLOW}🎨 Starting frontend...${NC}"
cd frontend
npm install
nohup npm start > ../logs/frontend.log 2>&1 &
echo $! > ../logs/frontend.pid
cd ..
echo -e "${GREEN}✅ Frontend started (PID: $(cat logs/frontend.pid))${NC}"

echo ""
echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}✨ All services started successfully!${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""
echo "📋 Access Points:"
echo "  Frontend:            http://localhost:3000"
echo "  Auth Service:        http://localhost:8081"
echo "  Appointment Service: http://localhost:8082"
echo "  Notification Service: http://localhost:8083"
echo "  Patient Service:     http://localhost:8084"
echo "  Swagger UI:          http://localhost:8081/swagger-ui.html"
echo ""
echo "📝 Logs:"
echo "  View logs: tail -f logs/<service-name>.log"
echo "  Stop services: ./scripts/stop-all.sh"
echo ""
