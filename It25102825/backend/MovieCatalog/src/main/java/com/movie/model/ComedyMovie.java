package com.movie.model;

/**
 * ComedyMovie – subclass of Movie.
 * Demonstrates: Inheritance (extends Movie)
 *               Polymorphism (@Override displayInfo())
 *
 * Extra attribute: humorStyle (Slapstick | Satire | Romantic | Dark)
 */
public class ComedyMovie extends Movie {

    private String humorStyle;  // Subclass-specific field

    // ── Constructor calls super() to initialise base fields ───────────────
    public ComedyMovie(String id, String title, String genre, int duration,
                       String language, String rating, String status,
                       String humorStyle) {
        super(id, title, genre, duration, language, rating, status);
        this.humorStyle = humorStyle;
    }

    // ── Getter & Setter ────────────────────────────────────────────────────
    public String getHumorStyle()            { return humorStyle; }
    public void   setHumorStyle(String val)  { this.humorStyle = val; }

    // ── Polymorphism: unique displayInfo() for ComedyMovie ─────────────────
    @Override
    public String displayInfo() {
        return "[COMEDY] " + getTitle() +
               " | " + getDuration() + " min" +
               " | Humor Style: " + humorStyle +
               " | " + getLanguage() +
               " | Status: " + getStatus();
    }

    @Override
    public String getType() {
        return "COMEDY";
    }

    @Override
    public String getExtraAttribute() {
        return humorStyle;
    }
}
