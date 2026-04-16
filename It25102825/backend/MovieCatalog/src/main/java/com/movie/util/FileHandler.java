package com.movie.util;

import com.movie.model.ActionMovie;
import com.movie.model.ComedyMovie;
import com.movie.model.GeneralMovie;
import com.movie.model.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileHandler – handles all Read/Write operations on movies.txt
 *
 * File format (pipe-separated):
 *   TYPE|ID|TITLE|GENRE|DURATION|LANGUAGE|RATING|STATUS|EXTRA_ATTRIBUTE
 *
 * Example lines:
 *   ACTION|MOV001|Mad Max|Action|120|English|PG-13|Now Showing|High
 *   COMEDY|MOV002|The Mask|Comedy|101|English|PG|Coming Soon|Slapstick
 *   GENERAL|MOV003|Inception|Thriller|148|English|PG-13|Now Showing|N/A
 */
public class FileHandler {

    // ── File path: stored in user's home directory ─────────────────────────
    private static final String DATA_DIR;
    private static final String FILE_PATH;

    static {
        DATA_DIR  = System.getProperty("user.home") + File.separator + "MovieCatalogData";
        FILE_PATH = DATA_DIR + File.separator + "movies.txt";
        new File(DATA_DIR).mkdirs(); // create folder if it doesn't exist
    }

    // ── READ: Get all movies ───────────────────────────────────────────────
    public static List<Movie> getAllMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return movies;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    Movie m = parseLine(line);
                    if (m != null) movies.add(m);
                }
            }
        }
        return movies;
    }

    // ── READ: Find a movie by ID ───────────────────────────────────────────
    public static Movie findById(String id) throws IOException {
        for (Movie m : getAllMovies()) {
            if (m.getId().equals(id)) return m;
        }
        return null;
    }

    // ── READ: Search by genre ──────────────────────────────────────────────
    public static List<Movie> searchByGenre(String genre) throws IOException {
        List<Movie> result = new ArrayList<>();
        for (Movie m : getAllMovies()) {
            if (m.getGenre().equalsIgnoreCase(genre)) result.add(m);
        }
        return result;
    }

    // ── READ: Search by language ───────────────────────────────────────────
    public static List<Movie> searchByLanguage(String language) throws IOException {
        List<Movie> result = new ArrayList<>();
        for (Movie m : getAllMovies()) {
            if (m.getLanguage().equalsIgnoreCase(language)) result.add(m);
        }
        return result;
    }

    // ── READ: Search by title (partial, case-insensitive) ─────────────────
    public static List<Movie> searchByTitle(String keyword) throws IOException {
        List<Movie> result = new ArrayList<>();
        for (Movie m : getAllMovies()) {
            if (m.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(m);
            }
        }
        return result;
    }

    // ── CREATE: Append a new movie to file ────────────────────────────────
    public static void addMovie(Movie movie) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(movie.toFileString());
            writer.newLine();
        }
    }

    // ── UPDATE: Replace a movie's line by ID ──────────────────────────────
    public static boolean updateMovie(Movie updated) throws IOException {
        List<Movie> movies = getAllMovies();
        boolean found = false;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getId().equals(updated.getId())) {
                movies.set(i, updated);
                found = true;
                break;
            }
        }
        if (found) saveAllMovies(movies);
        return found;
    }

    // ── DELETE: Remove a movie by ID ──────────────────────────────────────
    public static boolean deleteMovie(String id) throws IOException {
        List<Movie> movies = getAllMovies();
        boolean removed = movies.removeIf(m -> m.getId().equals(id));
        if (removed) saveAllMovies(movies);
        return removed;
    }

    // ── UTIL: Generate next available ID ──────────────────────────────────
    public static String generateId() throws IOException {
        List<Movie> movies = getAllMovies();
        int next = movies.size() + 1;
        // ensure uniqueness even if records were deleted
        while (idExists("MOV" + String.format("%03d", next), movies)) {
            next++;
        }
        return "MOV" + String.format("%03d", next);
    }

    private static boolean idExists(String id, List<Movie> movies) {
        for (Movie m : movies) if (m.getId().equals(id)) return true;
        return false;
    }

    // ── PRIVATE: Write entire list back to file ────────────────────────────
    private static void saveAllMovies(List<Movie> movies) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Movie m : movies) {
                writer.write(m.toFileString());
                writer.newLine();
            }
        }
    }

    // ── PRIVATE: Parse one line into the correct Movie subclass ───────────
    private static Movie parseLine(String line) {
        String[] p = line.split("\\|");
        if (p.length < 9) return null;

        String type     = p[0];
        String id       = p[1];
        String title    = p[2];
        String genre    = p[3];
        int    duration;
        try { duration  = Integer.parseInt(p[4]); }
        catch (NumberFormatException e) { duration = 0; }
        String language = p[5];
        String rating   = p[6];
        String status   = p[7];
        String extra    = p[8];

        switch (type) {
            case "ACTION":  return new ActionMovie(id, title, genre, duration, language, rating, status, extra);
            case "COMEDY":  return new ComedyMovie(id, title, genre, duration, language, rating, status, extra);
            default:        return new GeneralMovie(id, title, genre, duration, language, rating, status);
        }
    }
}
