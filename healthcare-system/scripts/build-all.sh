#!/bin/bash

# Build all services

set -e

echo "🔨 Building all services..."

services=("auth-service" "appointment-service" "notification-service" "patient-service")

for service in "${services[@]}"; do
    echo "Building $service..."
    cd services/$service
    mvn clean package -DskipTests
    cd ../..
    echo "✅ $service built"
done

echo "✅ All services built successfully!"
