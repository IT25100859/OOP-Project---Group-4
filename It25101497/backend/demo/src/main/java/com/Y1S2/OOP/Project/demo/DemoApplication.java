package com.Y1S2.OOP.Project.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Spring Boot application.
 * We use excludeName to disable MongoDB auto_configuration by its class name
 * to avoid compilation errors if the specific class is not found in the classpath.
 */
@SpringBootApplication(excludeName = "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration")
public class DemoApplication {

    public static void main(String[] args) {
        /*
         * Set the JSP development property to true.
         * This helps IntelliJ to pick up JSP changes immediately during development.
         * It must be set BEFORE the SpringApplication.run() call.
         */
        System.setProperty("server.servlet.jsp.init-parameters.development", "true");

        // Launches the Spring Boot application.
        // Ensure there is only ONE run call here.
        SpringApplication.run(DemoApplication.class, args);
    }
}