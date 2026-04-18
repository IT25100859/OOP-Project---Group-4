package com.Y1S2.OOP.Project.demo.model;

public class Booking {
    private String bookingID;
    private String movieTitle;
    private String customerName;
    private String seatNumber;
    private double price;
    private String date; // New field
    private String time; // New field

    public Booking(String bookingID, String movieTitle, String customerName, String seatNumber, double price, String date, String time) {
        this.bookingID = bookingID;
        this.movieTitle = movieTitle;
        this.customerName = customerName;
        this.seatNumber = seatNumber;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    // Getters and Setters
    public String getBookingID() { return bookingID; }
    public String getMovieTitle() { return movieTitle; }
    public String getCustomerName() { return customerName; }
    public String getSeatNumber() { return seatNumber; }
    public double getPrice() { return price; }
    public String getDate() { return date; }
    public String getTime() { return time; }

    // This helps to write the record to the text file in a clear format
    @Override
    public String toString() {
        return bookingID + "," + movieTitle + "," + customerName + "," + seatNumber + "," + price + "," + date + "," + time;
    }
}