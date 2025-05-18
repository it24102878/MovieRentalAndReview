package com.admincontrolcenter.service;

import com.admincontrolcenter.model.Payment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class PaymentService {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getName());
    
    private final String dataDirectory;
    private final String paymentFilePath;
    private Long nextId = 1L;

    public PaymentService() {
        try {
            // Create paths relative to the current working directory
            this.dataDirectory = "data";
            this.paymentFilePath = dataDirectory + "/payments.txt";
            
            // Ensure data directory exists
            createDirectoryIfNotExists(dataDirectory);
            
            initializePaymentFile();
            loadNextId();
        } catch (Exception e) {
            LOGGER.severe("Failed to initialize PaymentService: " + e.getMessage());
            throw new RuntimeException("Initialization failed", e);
        }
    }
    
    private void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                LOGGER.info("Created directory: " + directoryPath);
            } else {
                LOGGER.warning("Failed to create directory: " + directoryPath);
            }
        }
    }

    private void initializePaymentFile() {
        File file = new File(paymentFilePath);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    LOGGER.info("Created new payments.txt file at: " + file.getAbsolutePath());
                } else {
                    LOGGER.warning("Failed to create payments.txt file at: " + file.getAbsolutePath());
                }
            } else {
                LOGGER.info("Using existing payments.txt file at: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            LOGGER.severe("Error initializing payments file at " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error initializing payments file", e);
        }
    }

    private void loadNextId() {
        List<Payment> payments = getAllPayments();
        if (!payments.isEmpty()) {
            nextId = payments.stream().map(Payment::getId).max(Long::compare).orElse(0L) + 1;
            LOGGER.info("Next payment ID set to: " + nextId);
        }
    }

    public Payment createPayment(Payment payment) {
        payment.setId(nextId++);
        payment.setPaymentDate(LocalDateTime.now());
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(paymentFilePath, true))) {
            writer.write(paymentToCsv(payment));
            writer.newLine();
            LOGGER.info("Created payment: " + payment.getId() + " in file: " + paymentFilePath);
        } catch (IOException e) {
            LOGGER.severe("Error writing to payments file " + paymentFilePath + ": " + e.getMessage());
            throw new RuntimeException("Error writing to payments file", e);
        }
        return payment;
    }

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        File file = new File(paymentFilePath);
        
        // Return empty list if file doesn't exist yet
        if (!file.exists()) {
            LOGGER.info("Payments file does not exist yet at: " + file.getAbsolutePath());
            return payments;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (!line.trim().isEmpty()) {
                    try {
                        payments.add(csvToPayment(line));
                    } catch (IllegalArgumentException e) {
                        LOGGER.warning("Skipping invalid line " + lineNumber + " in " + paymentFilePath + ": " + line + " (" + e.getMessage() + ")");
                    }
                }
            }
            LOGGER.info("Read " + payments.size() + " payments from: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error reading payments file " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error reading payments file", e);
        }
        return payments;
    }

    public Payment getPaymentById(Long id) {
        return getAllPayments().stream()
                .filter(payment -> payment.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> getPaymentsByUserName(String userName) {
        return getAllPayments().stream()
                .filter(payment -> payment.getUserName().equals(userName))
                .toList();
    }

    public List<Payment> getPaymentsByMovieId(Long movieId) {
        return getAllPayments().stream()
                .filter(payment -> payment.getMovieId().equals(movieId))
                .toList();
    }

    public Payment updatePaymentStatus(Long id, String status) {
        List<Payment> payments = getAllPayments();
        Payment updatedPayment = null;
        
        for (Payment payment : payments) {
            if (payment.getId().equals(id)) {
                payment.setPaymentStatus(status);
                updatedPayment = payment;
                break;
            }
        }
        
        if (updatedPayment != null) {
            writeAllPayments(payments);
            LOGGER.info("Updated payment status for ID: " + id + " to " + status);
        }
        
        return updatedPayment;
    }

    private void writeAllPayments(List<Payment> payments) {
        File file = new File(paymentFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Payment payment : payments) {
                writer.write(paymentToCsv(payment));
                writer.newLine();
            }
            LOGGER.info("Wrote " + payments.size() + " payments to file: " + file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.severe("Error writing to payments file " + file.getAbsolutePath() + ": " + e.getMessage());
            throw new RuntimeException("Error writing to payments file", e);
        }
    }

    private String paymentToCsv(Payment payment) {
        return String.format("%d,%s,%d,%.2f,%s,%s,%s",
                payment.getId(),
                escapeCsv(payment.getUserName()),
                payment.getMovieId(),
                payment.getAmount(),
                escapeCsv(payment.getPaymentMethod()),
                escapeCsv(payment.getPaymentStatus()),
                payment.getPaymentDate().toString());
    }

    private Payment csvToPayment(String csv) {
        String[] parts = csv.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        if (parts.length != 7) {
            throw new IllegalArgumentException("Invalid CSV format, expected 7 fields, got " + parts.length + ": " + csv);
        }
        try {
            return new Payment(
                    Long.parseLong(parts[0].trim()),
                    unescapeCsv(parts[1]),
                    Long.parseLong(parts[2].trim()),
                    Double.parseDouble(parts[3].trim()),
                    unescapeCsv(parts[4]),
                    unescapeCsv(parts[5]),
                    LocalDateTime.parse(parts[6].trim())
            );
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data in CSV: " + csv, e);
        }
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private String unescapeCsv(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }
        return value;
    }
}