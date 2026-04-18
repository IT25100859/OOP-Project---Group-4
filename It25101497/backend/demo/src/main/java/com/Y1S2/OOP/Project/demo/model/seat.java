package com.Y1S2.OOP.Project.demo.model;

public abstract class seat {


    private String seatNumber;


    public seat(String seatNumber) {
        this.seatNumber = seatNumber;
    }


    public abstract double calculatePrice();


    public String getSeatNumber() {
        return seatNumber;
    }
}
