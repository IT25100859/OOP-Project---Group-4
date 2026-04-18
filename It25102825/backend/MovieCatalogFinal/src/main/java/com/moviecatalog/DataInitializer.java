package com.moviecatalog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.*;

@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String dataDir  = System.getProperty("user.home") + File.separator + "MovieCatalogData";
        String filePath = dataDir + File.separator + "movies.txt";

        File targetFile = new File(filePath);

        // Only copy sample data if movies.txt does not already exist
        if (!targetFile.exists()) {
            new File(dataDir).mkdirs();

            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream("movies.txt");

            if (input != null) {
                Files.copy(input, targetFile.toPath());
                System.out.println("✔ movies.txt loaded from resources.");
                input.close();
            } else {
                System.out.println("⚠ movies.txt not found in resources — starting empty.");
            }
        } else {
            System.out.println("✔ movies.txt already exists — skipping auto-load.");
        }
    }
}
