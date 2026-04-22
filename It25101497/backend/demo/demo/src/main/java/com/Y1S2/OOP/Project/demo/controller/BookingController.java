package com.Y1S2.OOP.Project.demo.controller;

import com.Y1S2.OOP.Project.demo.model.Booking;
import com.Y1S2.OOP.Project.demo.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller // Marks this class as a Web Controller
public class BookingController {

    @Autowired // Automatically injects the BookingService instance
    private BookingService bookingService;

    /**
     * Display the home page (index.jsp)
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Display the form to add a new booking
     */
    @GetMapping("/add-booking")
    public String showAddForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "add-booking";
    }

    /**
     * Handle the form submission and save the data to the file
     */
    @PostMapping("/save-booking")
    public String saveBooking(@ModelAttribute("booking") Booking booking) {
        // Business Logic: VIP seats are $20, Standard seats are $10
        double pricePerSeat = booking.getSeatType().equalsIgnoreCase("VIP") ? 20.0 : 10.0;

        // Calculate total price based on number of seats
        booking.setTotalPrice(booking.getNumberOfSeats() * pricePerSeat);

        // Generate a unique ID using current timestamp
        booking.setId(String.valueOf(System.currentTimeMillis()));

        // Save the booking object to the text file
        bookingService.saveToFile(booking);

        // Auto-redirect to the bookings table page after success
        return "redirect:/view-bookings";
    }

    /**
     * Read all bookings from the file and display them
     */
    @GetMapping("/view-bookings")
    public String viewBookings(Model model) {
        List<Booking> allBookings = bookingService.readFromFile();
        model.addAttribute("bookings", allBookings);
        return "view-bookings";
    }
    /**
     * Handle the request to delete a booking.
     */
    @GetMapping("/delete-booking")
    public String deleteBooking(@RequestParam("id") String id) {
        // Call service to remove the record from the text file
        bookingService.deleteFromFile(id);

        // Redirect back to the view page to show the updated list
        return "redirect:/view-bookings";
    }
    /**
     * Show the edit form with existing data.
     */
    @GetMapping("/edit-booking")
    public String showEditForm(@RequestParam("id") String id, Model model) {
        Booking booking = bookingService.findById(id);
        model.addAttribute("booking", booking);
        return "edit-booking"; // This will be our new JSP page
    }

    /**
     * Handle the update request.
     */
    @PostMapping("/update-booking")
    public String updateBooking(@ModelAttribute("booking") Booking booking) {
        // Recalculate price in case seats or type changed
        double pricePerSeat = booking.getSeatType().equalsIgnoreCase("VIP") ? 20.0 : 10.0;
        booking.setTotalPrice(booking.getNumberOfSeats() * pricePerSeat);

        bookingService.updateBooking(booking);
        return "redirect:/view-bookings";
    }

}