package com.example.handlingformsubmission.controller;

import com.example.handlingformsubmission.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Patient> getAllUsers() {
        // This returns a JSON or XML with the users
        return patientRepository.findAll();
    }

    @GetMapping(path="/allbydoctor")
    public @ResponseBody
    Map<String, List<Patient>> getAllpatients() {
        Map<String, List<Patient>> result = new HashMap<>();
        Iterable<Doctor> doctorIterable = doctorRepository.findAll();
        List<Doctor> doctors = new ArrayList<>();
        for (Doctor doc: doctorIterable
        ) {
            doctors.add(doc);
        }
        for (Doctor doctor: doctors
        ) { result.put(doctor.getName(),patientRepository.listByDoctorId(doctor.getId()));

        }

        return result;
    }
    @GetMapping("/doctors/{doctorId}")
    public @ResponseBody
    List<Patient> getAllpatientsByDoctorId(@PathVariable String doctorId){
        int id = Integer.parseInt(doctorId);
        return patientRepository.listByDoctorId(id);
    }

    @GetMapping("/patients/{patientId}")
    public @ResponseBody Patient getPatientById(@PathVariable String patientId) {
        int id = Integer.parseInt(patientId);
        return patientRepository.findById(id).orElse(new Patient());
    }





}
