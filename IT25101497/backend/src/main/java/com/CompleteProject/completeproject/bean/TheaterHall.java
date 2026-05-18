package com.CompleteProject.completeproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheaterHall {

    private String hallId;
    private String hallName;
    private int rows;
    private int columns;
    private String HallType;

    public int getTotalSeats(){
        return rows*columns;
    }

    public String toFileString(){

        return String.join("|",hallId,hallName,String.valueOf(rows),String.valueOf(columns),HallType);
    }

    public static TheaterHall fromFileString(String line){

        String[] parts = line.split("\\|");
        return new TheaterHall(parts[0],parts[1],Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]),parts[4]);


    }
}
