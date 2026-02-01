#!/bin/bash

# Healthcare System - Stop All Services Script

echo "🛑 Stopping all services..."

# Stop services using PID files
if [ -f logs/auth-service.pid ]; then
    kill $(cat logs/auth-service.pid) 2>/dev/null
    rm logs/auth-service.pid
    echo "✅ Auth Service stopped"
fi

if [ -f logs/appointment-service.pid ]; then
    kill $(cat logs/appointment-service.pid) 2>/dev/null
    rm logs/appointment-service.pid
    echo "✅ Appointment Service stopped"
fi

if [ -f logs/notification-service.pid ]; then
    kill $(cat logs/notification-service.pid) 2>/dev/null
    rm logs/notification-service.pid
    echo "✅ Notification Service stopped"
fi

if [ -f logs/patient-service.pid ]; then
    kill $(cat logs/patient-service.pid) 2>/dev/null
    rm logs/patient-service.pid
    echo "✅ Patient Service stopped"
fi

if [ -f logs/frontend.pid ]; then
    kill $(cat logs/frontend.pid) 2>/dev/null
    rm logs/frontend.pid
    echo "✅ Frontend stopped"
fi

# Stop Docker containers
docker-compose down

echo "🎉 All services stopped successfully!"
