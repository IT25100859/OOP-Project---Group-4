package com.moviecatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.moviecatalog", "com.movie.servlet"})  // ← fix
public class MovieCatalogFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCatalogFinalApplication.class, args);
    }
}