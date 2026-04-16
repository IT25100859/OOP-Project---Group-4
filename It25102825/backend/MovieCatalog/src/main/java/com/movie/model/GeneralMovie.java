package com.movie.model;

/**
 * GeneralMovie – subclass of Movie for genres like Drama, Horror, Thriller, etc.
 * Demonstrates: Inheritance (extends Movie)
 *               Polymorphism (@Override displayInfo())
 */
public class GeneralMovie extends Movie {

    public GeneralMovie(String id, String title, String genre, int duration,
                        String language, String rating, String status) {
        super(id, title, genre, duration, language, rating, status);
    }

    // ── Polymorphism: unique displayInfo() for GeneralMovie ───────────────
    @Override
    public String displayInfo() {
        return "[" + getGenre().toUpperCase() + "] " + getTitle() +
               " | " + getDuration() + " min" +
               " | " + getLanguage() +
               " | Status: " + getStatus();
    }

    @Override
    public String getType() {
        return "GENERAL";
    }

    @Override
    public String getExtraAttribute() {
        return "N/A";
    }
}
