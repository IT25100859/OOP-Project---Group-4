package com.Y1S2.OOP.Project.demo.service;

import com.Y1S2.OOP.Project.demo.model.Booking;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private final String FILE_NAME = "bookings.txt";

    // Save a new booking to the file
    public void saveBooking(Booking booking) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(booking.toString());
        } catch (IOException e) {
            System.err.println("Error saving booking: " + e.getMessage());
        }
    }

    // Main update method to handle record changes
    public boolean updateBooking(String id, String newName, String newMovie, String newDate, String newTime) {
        List<String> bookings = getAllBookings();
        boolean isUpdated = false;

        for (int i = 0; i < bookings.size(); i++) {
            String line = bookings.get(i);
            String[] parts = line.split(",");

            // Check if the current line ID matches the target ID (trimmed for safety)
            if (parts.length > 0 && parts[0].trim().equals(id.trim())) {

                /* Logic: If the new value provided is null or empty,
                   keep the existing data from the 'parts' array.
                */
                String movie = (newMovie != null && !newMovie.isEmpty()) ? newMovie : parts[1];
                String name = (newName != null && !newName.isEmpty()) ? newName : parts[2];
                String seat = parts[3]; // Seat and Price are usually not updated in this form
                String price = parts[4];
                String date = (newDate != null && !newDate.isEmpty()) ? newDate : parts[5];
                String time = (newTime != null && !newTime.isEmpty()) ? newTime : parts[6];

                // Reconstruct the comma-separated line
                String updatedLine = String.join(",", id, movie, name, seat, price, date, time);
                bookings.set(i, updatedLine);
                isUpdated = true;
                break; // Exit loop once the record is found and updated
            }
        }

        if (isUpdated) {
            rewriteFile(bookings);
        }
        return isUpdated;
    }

    // Retrieve all records from the text file
    public List<String> getAllBookings() {
        List<String> records = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) return records;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    records.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return records;
    }

    // Delete a record by ID
    public boolean deleteBooking(String id) {
        List<String> records = getAllBookings();
        boolean found = records.removeIf(line -> line.split(",")[0].trim().equals(id.trim()));
        if (found) {
            rewriteFile(records);
        }
        return found;
    }

    // Helper method to overwrite the file with the updated list
    private void rewriteFile(List<String> records) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String r : records) {
                writer.println(r);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}