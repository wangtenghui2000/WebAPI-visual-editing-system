package com.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class ServiceActivitiApplication {
    public static void main(String[] args) {

        SpringApplication.run(ServiceActivitiApplication.class, args);

    }


}
