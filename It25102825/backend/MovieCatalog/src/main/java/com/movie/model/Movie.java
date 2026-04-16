package com.movie.model;

/**
 * Abstract base class for all movie types.
 * Demonstrates: Encapsulation (private fields + getters/setters)
 *               Polymorphism (abstract displayInfo() method)
 */
public abstract class Movie {

    // ── Encapsulation: all fields are private ──────────────────────────────
    private String id;
    private String title;
    private String genre;
    private int    duration;   // in minutes
    private String language;
    private String rating;     // G, PG, PG-13, R
    private String status;     // Now Showing | Coming Soon | No Longer Showing

    // ── Constructor ────────────────────────────────────────────────────────
    public Movie(String id, String title, String genre, int duration,
                 String language, String rating, String status) {
        this.id       = id;
        this.title    = title;
        this.genre    = genre;
        this.duration = duration;
        this.language = language;
        this.rating   = rating;
        this.status   = status;
    }

    // ── Getters & Setters (Encapsulation) ──────────────────────────────────
    public String getId()                  { return id; }
    public void   setId(String id)         { this.id = id; }

    public String getTitle()               { return title; }
    public void   setTitle(String title)   { this.title = title; }

    public String getGenre()               { return genre; }
    public void   setGenre(String genre)   { this.genre = genre; }

    public int    getDuration()            { return duration; }
    public void   setDuration(int d)       { this.duration = d; }

    public String getLanguage()            { return language; }
    public void   setLanguage(String l)    { this.language = l; }

    public String getRating()              { return rating; }
    public void   setRating(String r)      { this.rating = r; }

    public String getStatus()              { return status; }
    public void   setStatus(String s)      { this.status = s; }

    // ── Abstract methods – subclasses MUST implement (Polymorphism) ────────
    public abstract String displayInfo();       // Different output per type
    public abstract String getType();           // "ACTION", "COMEDY", "GENERAL"
    public abstract String getExtraAttribute(); // Subclass-specific attribute

    // ── Common method: serialize to a pipe-delimited line for movies.txt ───
    public String toFileString() {
        return getType()           + "|" +
               id                  + "|" +
               title               + "|" +
               genre               + "|" +
               duration            + "|" +
               language            + "|" +
               rating              + "|" +
               status              + "|" +
               getExtraAttribute();
    }

    @Override
    public String toString() {
        return displayInfo();
    }
}
