package com.components3.Movie.ticket.Reservation.Platform.bean;

public enum ShowType {

     STANDARD_2D{

         public double calculatePrice(double base_price){
             return base_price;
         }
         public String getLabel(){
             return "Standard 2D";
         }

     },
    PREMIUM_3D{

         public double calculatePrice(double base_price){
             return base_price * 2.0;
         }
         public String getLabel(){
             return "Premium 3D";
         }
    },
    IMAX{
         public double calculatePrice(double base_price){
             return base_price * 3.0;
         }
         public String getLabel(){
             return "IMAX";
         }
    };

     public abstract double calculatePrice(double base_price);

     public abstract String getLabel();



}
