package com.Components3.component3.controller;

import com.Components3.component3.bean.ShowTime;
import com.Components3.component3.bean.ShowType;
import com.Components3.component3.bean.TheaterHall;
import com.Components3.component3.service.ShowTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/showtime")

public class ShowTimeController {

    @Autowired
    private ShowTimeService showTimeService;

    @GetMapping("/scheduler")
    public String showScheduler(Model model){

        List<ShowTime> showtimes = showTimeService.getAllShowTimes();
        model.addAttribute("showtimes",showtimes);
        model.addAttribute("pageTitle","ShowTimeScheduler");
        return "showtime/scheduler";

    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("showtime",new ShowTime());
        model.addAttribute("halls",showTimeService.getAllHalls());
        model.addAttribute("showTypes", ShowType.values());
        model.addAttribute("pageTitle","Add New Showtime");

        return "showtime/add-form";

    }

    @PostMapping("/add")
    public String addShowtime(@ModelAttribute ShowTime showtime,
                              @RequestParam String hallId,
                              RedirectAttributes redirectAttrs){

        TheaterHall hall = showTimeService.getHallById(hallId);

        if(hall!= null){
            showtime.setHallId(hall.getHallId());
            showtime.setHallName(hall.getHallName());
            showtime.setTotalSeats(hall.getTotalSeats());
        }

        boolean success = showTimeService.addShowTime(showtime);

        if(success){
            redirectAttrs.addFlashAttribute("successMsg",
                    "Showtime added successfully!");
        }
        else{
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Update failed – hall already booked at that time!");
        }
        return "redirect:/showtime/scheduler";

    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model ){
        ShowTime showtime = showTimeService.getShowTimeByID(id);

        if(showtime == null){
            return "redirect:/showtime/scheduler";
        }

        model.addAttribute("showtime",showtime);
        model.addAttribute("halls",showTimeService.getAllHalls());
        model.addAttribute("showTypes",ShowType.values());
        model.addAttribute("pageTitle","Edit Showtime");

        return "showtime/edit-form";
    }

    @PostMapping("/edit")
    public String editShowtime(@ModelAttribute ShowTime showtime,
                                @RequestParam String hallId,
                               RedirectAttributes redirectAttrs) {

        TheaterHall hall = showTimeService.getHallById(hallId);

        if(hall!=null){
            showtime.setHallId(hall.getHallId());
            showtime.setHallName(hall.getHallName());
            showtime.setTotalSeats(hall.getTotalSeats());

        }
        boolean success = showTimeService.UpdateShowTime(showtime);

        if(success){
            redirectAttrs.addFlashAttribute("successMsg",
                    "Showtime updated successfully");
        }else{
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Update failed – hall already booked at that time!");
        }

        return "redirect:/showtime/scheduler";

    }

    @GetMapping("/delete/{id}")
    public String deleteShowtime(@PathVariable String id,
                                 RedirectAttributes redirectAttrs){

        boolean deleted = showTimeService.deleteShowTime(id);

        if(deleted){
            redirectAttrs.addFlashAttribute("successMsg",
                    "Showtime cancelled successfully");
        }
        else{
            redirectAttrs.addFlashAttribute("errorMsg",
                    "Showtime not found!");
        }
        return "redirect:/showtime/scheduler";

    }

    @GetMapping("/schedule")
    public String dailySchedule(@RequestParam (required = false) String date, Model model){

        if(date==null || date.isEmpty()){
            date = LocalDate.now().toString();
        }

        List<ShowTime> availableShowtimes = showTimeService.getAvailableShowtimes(date);
        model.addAttribute("showtimes",availableShowtimes );
        model.addAttribute("searchDate", date);
        model.addAttribute("pageTitle",  "Movies on " + date);
        return "showtime/daily-schedule";


    }
    @GetMapping("/hall-layout/{showtimeID}")
    public String hallLayout(@PathVariable String showtimeID, Model model ){

        ShowTime showtime = showTimeService.getShowTimeByID(showtimeID);

        if(showtime == null){
            return "redirect:/showtime/schedule";
        }

        TheaterHall hall = showTimeService.getHallById(showtime.getHallId());

        model.addAttribute("showtime",showtime);
        model.addAttribute("hall",hall);
        model.addAttribute("pageTitle","Hall Layout - "+showtime.getMovieTitle());

        model.addAttribute("totalSeats",showtime.getTotalSeats());
        model.addAttribute("bookedSeats",showtime.getBookedSeats());
        model.addAttribute("availableSeats",showtime.getAvailableSeats());
        model.addAttribute("finalPrice",showtime.getFinalPrice());

        return "showtime/hall-layout";


    }

}
