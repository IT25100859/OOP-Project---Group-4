package com.Y1S2.OOP.Project.demo.service;

import com.Y1S2.OOP.Project.demo.model.Booking;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class to handle File Read/Write operations for Movie Bookings.
 * Uses a pipe (|) delimiter to allow commas in seat numbers (e.g., A12, A13).
 */
@Service
public class BookingService {

    private final String filePath = "bookings.txt";

    /**
     * Save a single booking object to the text file.
     * Uses '|' to separate fields so that seat numbers can contain commas.
     */
    public void saveToFile(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(formatBookingData(booking));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read all bookings from the text file and return as a List.
     * Splits by the pipe character to ensure data integrity.
     */
    public List<Booking> readFromFile() {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) return bookings;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split by Pipe character instead of Comma
                String[] data = line.split("\\|");
                if (data.length == 8) {
                    Booking booking = new Booking();
                    booking.setId(data[0]);
                    booking.setMovieName(data[1]);
                    booking.setDate(data[2]);
                    booking.setTime(data[3]);
                    booking.setSeatType(data[4]);
                    booking.setSeatNumbers(data[5]); // Now supports "A12, A13"
                    booking.setNumberOfSeats(Integer.parseInt(data[6]));
                    booking.setTotalPrice(Double.parseDouble(data[7]));
                    bookings.add(booking);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    /**
     * Deletes a booking record by filtering the list and rewriting the file.
     */
    public void deleteFromFile(String id) {
        List<Booking> allBookings = readFromFile();
        allBookings.removeIf(booking -> booking.getId().equals(id));
        rewriteFile(allBookings);
    }

    /**
     * Finds a single booking object by its unique ID.
     */
    public Booking findById(String id) {
        return readFromFile().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates an existing booking by finding its position in the list.
     */
    public void updateBooking(Booking updatedBooking) {
        List<Booking> list = readFromFile();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updatedBooking.getId())) {
                list.set(i, updatedBooking);
                break;
            }
        }
        rewriteFile(list);
    }

    /**
     * Overwrites the bookings.txt file with the current list of bookings.
     */
    private void rewriteFile(List<Booking> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Booking b : list) {
                writer.write(formatBookingData(b));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats the Booking object into a pipe-separated string.
     */
    private String formatBookingData(Booking b) {
        return b.getId() + "|" +
                b.getMovieName() + "|" +
                b.getDate() + "|" +
                b.getTime() + "|" +
                b.getSeatType() + "|" +
                b.getSeatNumbers() + "|" +
                b.getNumberOfSeats() + "|" +
                b.getTotalPrice();
    }
}