package com.Y1S2.OOP.Project.demo.model;

/**
 * Model class representing a Movie Booking.
 * Demonstrates Encapsulation using private fields and public getters/setters.
 */
public class Booking {
    private String id;
    private String movieName;
    private String date;
    private String time;
    private String seatType; // VIP or Standard
    private String seatNumbers; // Stores seat IDs like A1, A2
    private int numberOfSeats;
    private double totalPrice;

    // Default Constructor
    public Booking() {}

    // Getters and Setters (Encapsulation)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getSeatType() { return seatType; }
    public void setSeatType(String seatType) { this.seatType = seatType; }

    public String getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(String seatNumbers) { this.seatNumbers = seatNumbers; }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}