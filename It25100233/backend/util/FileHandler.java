package com.example.movieticket.util;

import com.example.movieticket.model.Payment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * FileHandler utility class for managing file-based database operations
 * Handles CRUD operations on payments.txt file
 * File format: paymentId|bookingId|amount|paymentMethod|status
 */
@Component
public class FileHandler {
    
    // Path to the data file location
    private static final String DATA_DIR = "src/main/resources/data";
    private static final String FILE_NAME = "payments.txt";
    private static final String FILE_PATH = DATA_DIR + File.separator + FILE_NAME;
    
    /**
     * Initialize data directory and file if they don't exist
     */
    public FileHandler() {
        try {
            initializeDataFile();
        } catch (IOException e) {
            System.err.println("Error initializing data file: " + e.getMessage());
        }
    }
    
    /**
     * Ensure data directory and file exist
     */
    private void initializeDataFile() throws IOException {
        Path dataDir = Paths.get(DATA_DIR);
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
        }
        
        Path filePath = Paths.get(FILE_PATH);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }
    
    /**
     * Read all payments from file
     * @return List of all payments
     */
    public synchronized List<Payment> readAllPayments() {
        List<Payment> payments = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Payment payment = parsePaymentLine(line);
                    if (payment != null) {
                        payments.add(payment);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading payments file: " + e.getMessage());
        }
        
        return payments;
    }
    
    /**
     * Read payment by booking ID
     * @param bookingId - booking ID to search
     * @return Payment object if found, null otherwise
     */
    public synchronized Payment readPaymentByBookingId(String bookingId) {
        List<Payment> payments = readAllPayments();
        return payments.stream()
            .filter(p -> p.getBookingId().equals(bookingId))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Read payment by payment ID
     * @param paymentId - payment ID to search
     * @return Payment object if found, null otherwise
     */
    public synchronized Payment readPaymentById(String paymentId) {
        List<Payment> payments = readAllPayments();
        return payments.stream()
            .filter(p -> p.getPaymentId().equals(paymentId))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Create/Write a new payment to file
     * @param payment - Payment object to save
     * @return true if successful, false otherwise
     */
    public synchronized boolean createPayment(Payment payment) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            
            bufferedWriter.write(payment.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            return true;
            
        } catch (IOException e) {
            System.err.println("Error creating payment: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update an existing payment in the file
     * @param payment - Updated Payment object
     * @return true if successful, false otherwise
     */
    public synchronized boolean updatePayment(Payment payment) {
        List<Payment> payments = readAllPayments();
        
        boolean found = false;
        for (int i = 0; i < payments.size(); i++) {
            if (payments.get(i).getPaymentId().equals(payment.getPaymentId())) {
                payments.set(i, payment);
                found = true;
                break;
            }
        }
        
        if (!found) {
            return false;
        }
        
        // Write all payments back to file
        return writeAllPayments(payments);
    }
    
    /**
     * Delete a payment from file
     * @param paymentId - Payment ID to delete
     * @return true if successful, false otherwise
     */
    public synchronized boolean deletePayment(String paymentId) {
        List<Payment> payments = readAllPayments();
        
        boolean removed = payments.removeIf(p -> p.getPaymentId().equals(paymentId));
        
        if (!removed) {
            return false;
        }
        
        // Write remaining payments back to file
        return writeAllPayments(payments);
    }
    
    /**
     * Write all payments to file (overwrites existing file)
     * @param payments - List of payments to write
     * @return true if successful, false otherwise
     */
    private synchronized boolean writeAllPayments(List<Payment> payments) {
        try (FileWriter writer = new FileWriter(FILE_PATH);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            
            for (Payment payment : payments) {
                bufferedWriter.write(payment.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            return true;
            
        } catch (IOException e) {
            System.err.println("Error writing payments: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Parse a line from the file to create a Payment object
     * Format: paymentId|bookingId|amount|paymentMethod|status
     * @param line - Line from file
     * @return Payment object or null if parsing fails
     */
    private Payment parsePaymentLine(String line) {
        try {
            String[] parts = line.split("\\|");
            if (parts.length < 5) {
                return null;
            }
            
            String paymentId = parts[0].trim();
            String bookingId = parts[1].trim();
            double amount = Double.parseDouble(parts[2].trim());
            String paymentMethod = parts[3].trim();
            String status = parts[4].trim();
            
            return new Payment(paymentId, bookingId, amount, paymentMethod, status);
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error parsing payment line: " + line);
            return null;
        }
    }
    
    /**
     * Get the next payment ID (auto-increment)
     * @return Next payment ID in format P00X
     */
    public synchronized String getNextPaymentId() {
        List<Payment> payments = readAllPayments();
        int maxId = 0;
        
        for (Payment payment : payments) {
            try {
                String id = payment.getPaymentId();
                if (id.startsWith("P")) {
                    int num = Integer.parseInt(id.substring(1));
                    maxId = Math.max(maxId, num);
                }
            } catch (NumberFormatException e) {
                // Skip invalid IDs
            }
        }
        
        return String.format("P%03d", maxId + 1);
    }
}
