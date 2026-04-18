package com.Y1S2.OOP.Project.demo.model;

public class VIPSeat extends seat {

    public VIPSeat(String seatNumber) {
        super(seatNumber);
    }

    @Override
    public double calculatePrice() {
        return 1500.00;
    }
}

