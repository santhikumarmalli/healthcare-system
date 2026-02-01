-- Healthcare System Database Initialization Script

CREATE DATABASE IF NOT EXISTS healthcare;
USE healthcare;

-- Create default users
INSERT INTO users (first_name, last_name, email, password, phone, role, active, email_verified, created_at, updated_at)
VALUES 
  ('Admin', 'User', 'admin@healthcare.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '+1234567890', 'ADMIN', true, true, NOW(), NOW()),
  ('Doctor', 'Smith', 'doctor@healthcare.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '+1234567891', 'DOCTOR', true, true, NOW(), NOW()),
  ('Patient', 'John', 'patient@healthcare.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '+1234567892', 'PATIENT', true, true, NOW(), NOW())
ON DUPLICATE KEY UPDATE email=email;

-- Password for all default users: Admin@123, Doctor@123, Patient@123
