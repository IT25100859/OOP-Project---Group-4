package com.CompleteProject.completeproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Booking – represents a confirmed seat reservation.
 *
 * OOP Concepts:
 *   - Encapsulation : all fields private; accessed only via Lombok-generated
 *                     getters/setters, keeping booking data secure.
 *
 * File format (pipe-separated, 14 columns) in bookings.txt:
 *   bookingId | userId | username | showtimeId | movieTitle | hallName |
 *   showDate  | showTime | showType | seats | seatCount | totalPrice |
 *   status | bookingDate
 *
 * seats      : comma-separated seat labels, e.g. "A1,A2,C3"
 * status     : CONFIRMED | CANCELLED
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private String bookingId;
    private String userId;
    private String username;
    private String showtimeId;
    private String movieTitle;
    private String hallName;
    private String showDate;
    private String showTime;
    private String showType;      // stored as enum name, e.g. "IMAX"
    private String seats;         // "A1,A2,C3"
    private int    seatCount;
    private double totalPrice;
    private String status;        // CONFIRMED | CANCELLED
    private String bookingDate;   // ISO date: "2026-05-02"

    // ── Derived helpers ────────────────────────────────────────────────────

    public boolean isConfirmed()  { return "CONFIRMED".equalsIgnoreCase(status); }
    public boolean isCancelled()  { return "CANCELLED".equalsIgnoreCase(status); }

    /** Human-readable show type label (mirrors ShowType.getLabel()). */
    public String getShowTypeLabel() {
        if (showType == null) return "";
        switch (showType) {
            case "STANDARD_2D": return "Standard 2D";
            case "PREMIUM_3D":  return "Premium 3D";
            case "IMAX":        return "IMAX";
            default:            return showType;
        }
    }

    // ── Serialisation ──────────────────────────────────────────────────────

    public String toFileString() {
        return String.join("|",
                bookingId,
                userId,
                username,
                showtimeId,
                movieTitle,
                hallName,
                showDate,
                showTime,
                showType,
                seats,
                String.valueOf(seatCount),
                String.valueOf(totalPrice),
                status,
                bookingDate);
    }

    public static Booking fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 14) return null;

        Booking b = new Booking();
        b.setBookingId(p[0].trim());
        b.setUserId(p[1].trim());
        b.setUsername(p[2].trim());
        b.setShowtimeId(p[3].trim());
        b.setMovieTitle(p[4].trim());
        b.setHallName(p[5].trim());
        b.setShowDate(p[6].trim());
        b.setShowTime(p[7].trim());
        b.setShowType(p[8].trim());
        b.setSeats(p[9].trim());
        b.setSeatCount(Integer.parseInt(p[10].trim()));
        b.setTotalPrice(Double.parseDouble(p[11].trim()));
        b.setStatus(p[12].trim());
        b.setBookingDate(p[13].trim());
        return b;
    }
}
