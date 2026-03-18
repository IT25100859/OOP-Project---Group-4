package com.components3.Movie.ticket.Reservation.Platform.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowTime {

    private String showTimeID;
    private String movieName;
    private String hallID;
    private String hallName;
    private String showDate;
    private String showTime;
    private ShowType showType;
    private double basePrice;
    private int totalSeats;
    private int bookedSeats;

    public int getAvailableSeats(){
        return totalSeats-bookedSeats;
    }

    public boolean isAvailable(){
        return getAvailableSeats()>0;
    }
    public double getFinalPrice(){
        return showType.calculatePrice(basePrice);
    }

    public String toFileString(){

        return String.join("|",
                showTimeID,
                movieName,
                hallID,
                hallName,
                showDate,
                showTime,
                String.valueOf(showType),
                String.valueOf(basePrice),
                String.valueOf(totalSeats),
                String.valueOf(bookedSeats));

    }


    public static ShowTime fromFileString(String line){
        String[] parts = line.split("\\|");
        if(parts.length<10){
            return null;
        }
        ShowTime st = new ShowTime();
        st.setShowTime(parts[0]);
        st.setMovieName(parts[1]);
        st.setHallID(parts[2]);
        st.setHallName(parts[3]);
        st.setShowDate(parts[4]);
        st.setShowTime(parts[5]);
        st.setShowType(ShowType.valueOf(parts[6]));
        st.setBasePrice(Double.parseDouble(parts[7]));
        st.setTotalSeats(Integer.parseInt(parts[8]));
        st.setBookedSeats(Integer.parseInt(parts[9]));

        return st;



    }



}
