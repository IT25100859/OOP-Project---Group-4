package com.Y1S2.OOP.Project.demo.controller;

import com.Y1S2.OOP.Project.demo.model.Booking;
import com.Y1S2.OOP.Project.demo.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class DummyBookingController {

    // Service instance to handle File I/O
    private BookingService bookingService = new BookingService();

    /**
     * Dashboard view: Displays all bookings.
     * URL: http://localhost:8080/view-bookings
     */
    @GetMapping("/view-bookings")
    public String viewBookingsPage(Model model) {
        System.out.println(">>> Accessing Dashboard <<<");
        List<String> bookings = bookingService.getAllBookings();
        model.addAttribute("allBookings", bookings);
        return "viewBookings";
    }

    @GetMapping("/add-booking-form")
    public String addBookingPage() {
        return "addBooking";
    }

    /**
     * Edit form: Displays the form to update a record.
     * Fixed: Added required=false to prevent 400 error if ID is missing initially.
     */
    @GetMapping("/edit-booking-form")
    public String editBookingPage(@RequestParam(value = "id", required = false) String id, Model model) {
        if (id == null) {
            // Redirect back to dashboard if no ID is provided to avoid errors
            return "redirect:/view-bookings";
        }
        model.addAttribute("bookingId", id);
        return "editBooking";
    }

    /**
     * Handles new bookings.
     */
    @PostMapping("/save-booking")
    public String saveBooking(@RequestParam String name, @RequestParam String movie,
                              @RequestParam String seatNo, @RequestParam String type,
                              @RequestParam String date, @RequestParam String time) {

        double price = type.equalsIgnoreCase("VIP") ? 1500.0 : 780.0;
        String id = "B" + System.currentTimeMillis();
        Booking booking = new Booking(id, movie, name, seatNo, price, date, time);
        bookingService.saveBooking(booking);
        return "redirect:/view-bookings";
    }

    /**
     * Handles updating an existing booking.
     * Fixed: All parameters except ID and Name are optional to handle partial updates.
     */
    @PostMapping("/update-booking")
    public String updateBooking(@RequestParam("id") String id,
                                @RequestParam("name") String name,
                                @RequestParam(value = "movie", required = false) String movie,
                                @RequestParam(value = "date", required = false) String date,
                                @RequestParam(value = "time", required = false) String time) {

        System.out.println("Processing update for ID: " + id);
        bookingService.updateBooking(id, name, movie, date, time);
        return "redirect:/view-bookings";
    }

    @GetMapping("/delete-booking")
    public String deleteBooking(@RequestParam String id) {
        bookingService.deleteBooking(id);
        return "redirect:/view-bookings";
    }
}