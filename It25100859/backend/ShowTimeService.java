package com.components3.Movie.ticket.Reservation.Platform.service;
import com.components3.Movie.ticket.Reservation.Platform.bean.ShowTime;
import com.components3.Movie.ticket.Reservation.Platform.bean.TheaterHall;

import java.util.List;

public interface ShowTimeService {

    boolean addShowTime(ShowTime showTime);

    ShowTime getShowTimeByID(String showtimeID);

    List<ShowTime> getAllShowTimes();

    List<ShowTime> getShowTimesByDate(String date);

    List<ShowTime> getShowTimesByHall(String hallID);

    boolean UpdateShowTime(ShowTime showTime);

    boolean deleteShowTime(String showTimeID);

    boolean isHallAvailable(String hallID,String date,String showtime, String excludeID);

    List<ShowTime> getAvailableShowtimes(String date);

    List<TheaterHall> getAllHalls();

    TheaterHall getHallById(String hallId);

}
