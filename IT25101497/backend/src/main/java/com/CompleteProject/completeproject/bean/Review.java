package com.CompleteProject.completeproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Review – represents a customer's movie review and star rating.
 *
 * OOP Concepts:
 *   - Encapsulation : all fields private; accessed only via Lombok getters/setters.
 *                     Sensitive fields (userId) are hidden from public display

 *

 * status  : APPROVED / REMOVED
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    private String reviewId;    // RV001, RV002 …
    private String movieTitle;  // matches Movie.title exactly (case-sensitive)
    private String userId;
    private String username;
    private int    rating;      // 1 – 5
    private String comment;
    private String reviewDate;  // ISO date: "2026-05-02"
    private String status;      // APPROVED | REMOVED

    // ── Derived helpers

    public boolean isApproved() { return "APPROVED".equalsIgnoreCase(status); }
    public boolean isRemoved()  { return "REMOVED".equalsIgnoreCase(status); }


    public String getStarDisplay() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++)
            sb.append(i <= rating ? "★" : "☆");
        return sb.toString();
    }

    // Serialisation

    public String toFileString() {
        return String.join("|",
                reviewId,
                movieTitle,
                userId,
                username,
                String.valueOf(rating),
                comment.replace("|", "｜"),   // escape pipe chars in comment
                reviewDate,
                status);
    }

    public static Review fromFileString(String line) {
        String[] p = line.split("\\|", 8);   // max 8 — keeps comment intact
        if (p.length < 8) return null;

        Review r = new Review();
        r.setReviewId(  p[0].trim());
        r.setMovieTitle(p[1].trim());
        r.setUserId(    p[2].trim());
        r.setUsername(  p[3].trim());
        try { r.setRating(Integer.parseInt(p[4].trim())); }
        catch (NumberFormatException e) { r.setRating(3); }
        r.setComment(   p[5].trim().replace("｜", "|"));
        r.setReviewDate(p[6].trim());
        r.setStatus(    p[7].trim());
        return r;
    }
}
