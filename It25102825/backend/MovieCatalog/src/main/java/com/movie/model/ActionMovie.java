package com.movie.model;

/**
 * ActionMovie – subclass of Movie.
 * Demonstrates: Inheritance (extends Movie)
 *               Polymorphism (@Override displayInfo())
 *
 * Extra attribute: actionIntensity (Low | Medium | High | Extreme)
 */
public class ActionMovie extends Movie {

    private String actionIntensity;  // Subclass-specific field

    // ── Constructor calls super() to initialise base fields ───────────────
    public ActionMovie(String id, String title, String genre, int duration,
                       String language, String rating, String status,
                       String actionIntensity) {
        super(id, title, genre, duration, language, rating, status);
        this.actionIntensity = actionIntensity;
    }

    // ── Getter & Setter ────────────────────────────────────────────────────
    public String getActionIntensity()              { return actionIntensity; }
    public void   setActionIntensity(String val)    { this.actionIntensity = val; }

    // ── Polymorphism: unique displayInfo() for ActionMovie ─────────────────
    @Override
    public String displayInfo() {
        return "[ACTION] " + getTitle() +
               " | " + getDuration() + " min" +
               " | Intensity: " + actionIntensity +
               " | " + getLanguage() +
               " | Status: " + getStatus();
    }

    @Override
    public String getType() {
        return "ACTION";
    }

    @Override
    public String getExtraAttribute() {
        return actionIntensity;
    }
}
