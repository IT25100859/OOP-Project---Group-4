package com.Y1S2.OOP.Project.demo.model;


public class StandardSeat extends seat {

    public StandardSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double calculatePrice() {
        return 800.00; // Standard seat price
    }
}

