package com.components3.Movie.ticket.Reservation.Platform.service;

import com.components3.Movie.ticket.Reservation.Platform.bean.ShowTime;

import com.components3.Movie.ticket.Reservation.Platform.bean.TheaterHall;
import com.components3.Movie.ticket.Reservation.Platform.repository.ShowTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowTimeServiceImpl implements ShowTimeService {

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Override
    public boolean addShowTime(ShowTime showTime) {
        if(!isHallAvailable(showTime.getHallID(),showTime.getShowDate(),showTime.getShowTime(),null)){
            return false;
        }

        showTime.setShowTimeID(showTimeRepository.generateNextID());

        showTime.setBookedSeats(0);
        showTimeRepository.save(showTime);
        return true;
    }

    @Override
    public ShowTime getShowTimeByID(String showtimeID){
        return showTimeRepository.findbyID(showtimeID);
    }

    @Override
    public List<ShowTime> getAllShowTimes() {
        return showTimeRepository.readAll();
    }

    @Override
    public List<ShowTime> getShowTimesByDate(String date) {
        return showTimeRepository.findByDate(date);
    }

    @Override
    public List<ShowTime> getShowTimesByHall(String hallID) {
        return showTimeRepository.findByHall(hallID);
    }

    @Override
    public boolean UpdateShowTime(ShowTime showTime) {

        if(!isHallAvailable(showTime.getHallID(),
                showTime.getShowDate(),
                showTime.getShowTime(),
                showTime.getShowTimeID())){
            return false;
        }
        return showTimeRepository.Update(showTime);
    }

    @Override
    public boolean deleteShowTime(String showTimeID) {
        return showTimeRepository.delete(showTimeID);
    }

    @Override
    public boolean isHallAvailable(String hallID, String date, String showtime, String excludeID) {

        List<ShowTime> existingShows = showTimeRepository.readAll();


        for(ShowTime s: existingShows){
            if(excludeID != null && s.getShowTimeID().equals(excludeID)){
                continue;
            }
            if(s.getHallID().equals(hallID)
                    && s.getShowDate().equals(date)
                    && s.getShowTime().equals(showtime)){

                return false;
            }

        }
        return true;
    }

    @Override
    public List<ShowTime> getAvailableShowtimes(String date) {

        return showTimeRepository.findByDate(date)
                .stream()
                .filter(ShowTime::isAvailable)
                .collect(Collectors.toList());
    }

    @Override
    public List<TheaterHall> getAllHalls() {

        return showTimeRepository.readAllHalls();
    }

    @Override
    public TheaterHall getHallById(String hallId) {
        return showTimeRepository.findHallById(hallId);
    }

    private List<ShowTime> sortByTime(List<ShowTime> showList){
        showList.sort((a,b)->a.getShowTime().compareTo(b.getShowTime()));
        return showList;
    }
}
