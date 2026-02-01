package com.healthcare.service;

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
