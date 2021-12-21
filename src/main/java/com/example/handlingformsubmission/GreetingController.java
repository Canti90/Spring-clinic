package com.example.handlingformsubmission;

//import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class GreetingController {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private PatientRepository patientRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private DoctorRepository doctorRepository;


    private static final Logger LOGGER = LogManager.getLogger(GreetingController.class);




    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(User user, Model model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null){
            model.addAttribute("message", "UserExist");
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";

    }



    @GetMapping("/administrator")
    public String adminPage(Model model) {

        List<Doctor> doctors = doctorRepository.findAllByOrderByIdDesc();

        Map<Doctor, Integer> list = new HashMap<>();
        for (Doctor doc: doctors
        ) {
           list.put(doc, patientRepository.countByDoctorId(doc.getId()));
        }
        LOGGER.error("error test message");

        model.addAttribute("list", list);
        model.addAttribute("doctors", doctors);
        model.addAttribute("doctor", new Doctor());
        return "administrator";
    }

    @PostMapping(value = "/administrator", params = "save")
    public String administratorSave(@ModelAttribute Doctor doctor, Model model){


        doctorRepository.save(doctor);

        List<Doctor> doctors = doctorRepository.findAllByOrderByIdDesc();
        Map<Doctor, Integer> list = new HashMap<>();
        for (Doctor doc: doctors
        ) {
            list.put(doc, patientRepository.countByDoctorId(doc.getId()));
        }

        model.addAttribute("list", list);
        model.addAttribute("doctors", doctors);
        model.addAttribute("doctor", new Doctor());
        return "administrator";
    }

    @PostMapping(value = "/administrator", params = "delete")
    public String administratorDelete(@ModelAttribute Doctor doctor, Model model){

        List<Doctor> doctors = doctorRepository.findAllByOrderByIdDesc();
        for (Doctor doc: doctors
             ) {
            if (doc.getName().equals(doctor.getName())) {
                doctorRepository.delete(doc);
            }
        }
        doctors = doctorRepository.findAllByOrderByIdDesc();
        Map<Doctor, Integer> list = new HashMap<>();
        for (Doctor doc: doctors
        ) {
            list.put(doc, patientRepository.countByDoctorId(doc.getId()));
        }

        model.addAttribute("list", list);
        model.addAttribute("doctors", doctors);
        model.addAttribute("doctor", new Doctor());
        return "administrator";
    }


    @GetMapping("/addnewpatient")
    public String addnewpatientForm(Model model) {
        Iterable<Doctor> doctorIterable = doctorRepository.findAll();
        List<Doctor> doctors = new ArrayList<Doctor>();
        for (Doctor doc: doctorIterable
             ) {
            doctors.add(doc);
        }
        model.addAttribute("patient", new Patient());
        model.addAttribute("doctors", doctors);

        return "addnewpatient";
    }

    @PostMapping("/addnewpatient")
    public String addnewpatientSubmit(@ModelAttribute @Valid Patient patient,BindingResult bindingResult,Model model) {

        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.hasErrors());
            System.out.println(patient.getDate());
            System.out.println(patient.getFirstName());
            Iterable<Doctor> doctorIterable = doctorRepository.findAll();
            List<Doctor> doctors = new ArrayList<Doctor>();
            for (Doctor doc: doctorIterable
            ) {
                doctors.add(doc);
            }
            model.addAttribute("doctors", doctors);
            return "addnewpatient";
        }
        model.addAttribute("patient", patient);
        System.out.println(patient.getDate());
        patientRepository.save(patient);
        LOGGER.info("Patient created: " + patient.patientInfo());
        return "result";
    }

    @RequestMapping("/patientByDoctor")
    public String greeting2(@RequestParam String id, Model model) {
        int i = Integer.parseInt(id);
        List<Patient> listByDoctorId = patientRepository.listByDoctorId(i);
        model.addAttribute("listByDoctorId", listByDoctorId);
        return "patientByDoctor";
    }

    @GetMapping("/patientChangeDoctor")
    public String patientChangeDoctor(@RequestParam String id, Model model){
        int i = Integer.parseInt(id);
        Patient p = patientRepository.findById(i).orElse(new Patient());

        Iterable<Doctor> doctorIterable = doctorRepository.findAll();
        List<Doctor> doctors = new ArrayList<Doctor>();
        for (Doctor doc: doctorIterable
        ) {
            doctors.add(doc);
        }
        model.addAttribute("patient", p);
        model.addAttribute("doctors", doctors);

        return "patientChangeDoctor";
    }
    @PostMapping("/patientChangeDoctor")
    public String patientChangeDoctorSubmit(Patient patient, Model model){

        Patient p = patientRepository.findById(patient.getId()).orElse(new Patient());
        LOGGER.info("Changing patient data from: " + p.patientInfo());

        patient.setDate(p.getDate());

        patientRepository.save(patient);

        LOGGER.info("to " + patient.patientInfo());



        Iterable<Doctor> doctorIterable = doctorRepository.findAll();
        List<Doctor> doctors = new ArrayList<Doctor>();
        for (Doctor doc: doctorIterable
        ) {
            doctors.add(doc);
        }
        model.addAttribute("patient", patient);
        model.addAttribute("doctors", doctors);

        return "patientChangeDoctor";
    }

    @GetMapping(path="/all")
    public @ResponseBody
    Iterable<Patient> getAllUsers() {
        // This returns a JSON or XML with the users
        return patientRepository.findAll();
    }

//    @GetMapping(path="/allbydoctor")
//    public @ResponseBody
//    Map<String, List<Patient>> getAllpatients() {
//        // This returns a JSON or XML with the users
//        CollectionFromSQL collectionFromSQL = new CollectionFromSQL();
//
//        return collectionFromSQL.makeMap(doctorRepository,patientRepository);
//    }

}