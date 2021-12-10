//package com.example.handlingformsubmission;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class CollectionFromSQL {
//
//
//
////    Map<String, List<List<String>>> customerCardsImproved = new HashMap<>();
//    // List = 0 - name, 1 - Surname, 2 - date
//    public Map<String, List<Patient>> makeMap (DoctorRepository doctorRepository, PatientRepository patientRepository){
//
//        Iterable<Doctor> doctorIterable = doctorRepository.findAll();
//        List<Doctor> doctors = new ArrayList<Doctor>();
//        Iterable<Patient> patientIterable = patientRepository.findAll();
//        List<Patient> patients = new ArrayList<Patient>();
//        Map<String, List<Patient>> customerCardsImproved = new HashMap<String, List<Patient>>();
//
//        for (Doctor doc: doctorIterable
//        ) {
//            doctors.add(doc);
//        }
//
//        for (Patient patient: patientIterable
//        ) {
//            patients.add(patient);
//        }
//
//        for (Doctor doc: doctors
//             ) {
//            List<Patient> pat = new ArrayList<>();
//            for (Patient patient: patients
//                 ) {
//                if (doc.getId() == patient.getDoctorId()){
//                    pat.add(patient);
//                }
//            }
//            customerCardsImproved.put(doc.getName(),pat);
//        }
//
//        return customerCardsImproved;
//    }
//
//
//
//
//}
