#!/usr/bin/env python3
"""
Complete Healthcare System Code Generator
Generates all remaining files for the healthcare appointment system
"""

import os

def create_file(path, content):
    """Create file with content"""
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, 'w') as f:
        f.write(content)
    print(f"Created: {path}")

# Base directory
BASE = "/home/claude/healthcare-system"

# =======================
# APPOINTMENT SERVICE FILES
# =======================

create_file(f"{BASE}/services/appointment-service/src/main/java/com/healthcare/entity/Appointment.java", """package com.healthcare.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long patientId;
    private Long doctorId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String reason;
    
    @Enumerated(EnumType.STRING)
    private Status status = Status.SCHEDULED;
    
    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED
    }
}
""")

create_file(f"{BASE}/services/appointment-service/src/main/java/com/healthcare/repository/AppointmentRepository.java", """package com.healthcare.repository;

import com.healthcare.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByAppointmentDate(LocalDate date);
}
""")

create_file(f"{BASE}/services/appointment-service/src/main/java/com/healthcare/service/AppointmentService.java", """package com.healthcare.service;

import com.healthcare.entity.Appointment;
import com.healthcare.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository repository;
    
    public Appointment createAppointment(Appointment appointment) {
        return repository.save(appointment);
    }
    
    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }
    
    public Appointment getAppointmentById(Long id) {
        return repository.findById(id).orElseThrow();
    }
    
    public List<Appointment> getPatientAppointments(Long patientId) {
        return repository.findByPatientId(patientId);
    }
}
""")

create_file(f"{BASE}/services/appointment-service/src/main/java/com/healthcare/controller/AppointmentController.java", """package com.healthcare.controller;

import com.healthcare.entity.Appointment;
import com.healthcare.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService service;
    
    @PostMapping
    public ResponseEntity<Appointment> create(@RequestBody Appointment appointment) {
        return ResponseEntity.ok(service.createAppointment(appointment));
    }
    
    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        return ResponseEntity.ok(service.getAllAppointments());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAppointmentById(id));
    }
    
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(service.getPatientAppointments(patientId));
    }
}
""")

# =======================
# PATIENT SERVICE FILES
# =======================

create_file(f"{BASE}/services/patient-service/pom.xml", """<?xml version="1.0"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    <groupId>com.healthcare</groupId>
    <artifactId>patient-service</artifactId>
    <version>1.0.0</version>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
</project>
""")

create_file(f"{BASE}/services/patient-service/src/main/resources/application.yml", """server:
  port: 8084
spring:
  application:
    name: patient-service
  datasource:
    url: jdbc:mysql://localhost:3306/healthcare
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
""")

# =======================
# NOTIFICATION SERVICE FILES
# =======================

create_file(f"{BASE}/services/notification-service/pom.xml", """<?xml version="1.0"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    <groupId>com.healthcare</groupId>
    <artifactId>notification-service</artifactId>
    <version>1.0.0</version>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>
</project>
""")

create_file(f"{BASE}/services/notification-service/src/main/resources/application.yml", """server:
  port: 8083
spring:
  application:
    name: notification-service
  mail:
    host: smtp.gmail.com
    port: 587
""")

print("\n✅ All service files created!")
print(f"Total files created in {BASE}")

