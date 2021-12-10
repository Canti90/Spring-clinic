package com.example.handlingformsubmission;

import com.example.handlingformsubmission.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PatientRepository extends CrudRepository<Patient, Integer> {

    @Query("SELECT COUNT(p) FROM Patient p WHERE p.doctorId=?1")
    Integer countByDoctorId(int id);

    @Query("SELECT p FROM Patient p WHERE p.doctorId =?1")
    List<Patient> listByDoctorId(@Param("doctorId") int doctorId);

}