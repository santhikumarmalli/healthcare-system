package com.healthcare.entity;

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
