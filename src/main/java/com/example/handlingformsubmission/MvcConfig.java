package com.example.handlingformsubmission;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/greeting").setViewName("addnewpatient");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registration").setViewName("registration");
        registry.addViewController("/loginNew").setViewName("loginNew");
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/patientByDoctor").setViewName("patientByDoctor");
        registry.addViewController("/patientChangeDoctor").setViewName("patientChangeDoctor");


    }

}