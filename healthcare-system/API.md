# 📚 API Documentation

## Base URLs

- Auth Service: `http://localhost:8081`
- Appointment Service: `http://localhost:8082`
- Notification Service: `http://localhost:8083`
- Patient Service: `http://localhost:8084`

## Authentication

All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <jwt-token>
```

---

## Auth Service APIs

### 1. Register User
**POST** `/api/auth/register`

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "Password@123",
  "phone": "+1234567890",
  "role": "PATIENT"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "PATIENT",
  "active": true
}
```

### 2. Login
**POST** `/api/auth/login`

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "Password@123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "PATIENT",
  "active": true
}
```

### 3. Get Profile
**GET** `/api/auth/profile`

**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
{
  "id": 1,
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "role": "PATIENT",
  "active": true,
  "profileImage": null
}
```

### 4. Change Password
**PUT** `/api/auth/change-password`

**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "currentPassword": "Password@123",
  "newPassword": "NewPassword@456"
}
```

**Response:**
```
Password changed successfully
```

### 5. Get All Users (Admin Only)
**GET** `/api/auth/users`

**Headers:** `Authorization: Bearer <admin-token>`

**Response:**
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "+1234567890",
    "role": "PATIENT",
    "active": true
  }
]
```

### 6. Get Users by Role (Admin Only)
**GET** `/api/auth/users/role/{role}`

**Parameters:**
- `role`: PATIENT, DOCTOR, or ADMIN

**Headers:** `Authorization: Bearer <admin-token>`

### 7. Deactivate User (Admin Only)
**PUT** `/api/auth/users/{userId}/deactivate`

**Headers:** `Authorization: Bearer <admin-token>`

### 8. Activate User (Admin Only)
**PUT** `/api/auth/users/{userId}/activate`

**Headers:** `Authorization: Bearer <admin-token>`

---

## Appointment Service APIs

### 1. Create Appointment
**POST** `/api/appointments`

**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "patientId": 1,
  "doctorId": 2,
  "appointmentDate": "2026-02-15",
  "appointmentTime": "10:00",
  "reason": "Regular checkup"
}
```

**Response:**
```json
{
  "id": 1,
  "patientId": 1,
  "doctorId": 2,
  "appointmentDate": "2026-02-15",
  "appointmentTime": "10:00",
  "reason": "Regular checkup",
  "status": "SCHEDULED"
}
```

### 2. Get All Appointments
**GET** `/api/appointments`

**Headers:** `Authorization: Bearer <token>`

**Response:**
```json
[
  {
    "id": 1,
    "patientId": 1,
    "doctorId": 2,
    "appointmentDate": "2026-02-15",
    "appointmentTime": "10:00",
    "reason": "Regular checkup",
    "status": "SCHEDULED"
  }
]
```

### 3. Get Appointment by ID
**GET** `/api/appointments/{id}`

**Headers:** `Authorization: Bearer <token>`

### 4. Get Patient Appointments
**GET** `/api/appointments/patient/{patientId}`

**Headers:** `Authorization: Bearer <token>`

### 5. Get Doctor Appointments
**GET** `/api/appointments/doctor/{doctorId}`

**Headers:** `Authorization: Bearer <token>`

### 6. Update Appointment
**PUT** `/api/appointments/{id}`

**Headers:** `Authorization: Bearer <token>`

**Request Body:**
```json
{
  "appointmentDate": "2026-02-16",
  "appointmentTime": "14:00",
  "reason": "Follow-up checkup",
  "status": "SCHEDULED"
}
```

### 7. Cancel Appointment
**PUT** `/api/appointments/{id}/cancel`

**Headers:** `Authorization: Bearer <token>`

### 8. Complete Appointment
**PUT** `/api/appointments/{id}/complete`

**Headers:** `Authorization: Bearer <token>`

---

## Error Responses

### 400 Bad Request
```json
{
  "status": 400,
  "message": "Invalid input data",
  "timestamp": "2026-02-01T10:00:00"
}
```

### 401 Unauthorized
```json
{
  "status": 401,
  "message": "Invalid credentials",
  "timestamp": "2026-02-01T10:00:00"
}
```

### 403 Forbidden
```json
{
  "status": 403,
  "message": "Access denied",
  "timestamp": "2026-02-01T10:00:00"
}
```

### 404 Not Found
```json
{
  "status": 404,
  "message": "Resource not found",
  "timestamp": "2026-02-01T10:00:00"
}
```

### 500 Internal Server Error
```json
{
  "status": 500,
  "message": "An error occurred: ...",
  "timestamp": "2026-02-01T10:00:00"
}
```

---

## Swagger/OpenAPI Documentation

Access interactive API documentation at:
```
http://localhost:8081/swagger-ui.html
```

## Postman Collection

Import the Postman collection from:
```
postman/Healthcare-API.postman_collection.json
```

## Rate Limiting

API endpoints are rate-limited to:
- 100 requests per minute for authenticated users
- 20 requests per minute for unauthenticated endpoints

## Versioning

Current API version: v1

APIs are versioned in the URL path: `/api/v1/...`
