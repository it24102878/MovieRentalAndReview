package com.admincontrolcenter.service;


import com.admincontrolcenter.model.Feedback;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FeedbackService {
    private static final String FILE_PATH = "src/main/resources/review.txt";
    private static final Logger LOGGER = Logger.getLogger(FeedbackService.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public FeedbackService() {
        objectMapper.registerModule(new JavaTimeModule());
        try {
            List<Feedback> feedbacks = readFeedbacksFromFile();
            if (!feedbacks.isEmpty()) {
                long maxId = feedbacks.stream().mapToLong(Feedback::getId).max().orElse(0);
                idGenerator.set(maxId + 1);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error initializing ID generator: " + e.getMessage(), e);
        }
    }

    public List<Feedback> getAllFeedbacks() {
        try {
            return readFeedbacksFromFile();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error getting all feedbacks: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<Feedback> getFeedbacksByUserId(String userId) {
        try {
            return readFeedbacksFromFile().stream()
                    .filter(f -> userId.equals(f.getUserId()))
                    .toList();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error getting feedbacks for user " + userId + ": " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public Feedback getFeedbackById(Long id) {
        try {
            return readFeedbacksFromFile().stream()
                    .filter(f -> f.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Feedback not found with ID: " + id));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error getting feedback with ID " + id + ": " + e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve feedback with ID: " + id, e);
        }
    }

    public Feedback saveFeedback(Feedback feedback) {
        try {
            List<Feedback> feedbacks = readFeedbacksFromFile();
            feedback.setUpdatedAt(LocalDateTime.now());

            if (feedback.getId() == null) {
                feedback.setId(idGenerator.getAndIncrement());
                feedbacks.add(feedback);
            } else {
                feedbacks.removeIf(f -> f.getId().equals(feedback.getId()));
                feedbacks.add(feedback);
            }

            writeFeedbacksToFile(feedbacks);
            return feedback;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving feedback: " + e.getMessage(), e);
            throw new RuntimeException("Failed to save feedback", e);
        }
    }

    public void deleteFeedback(Long id) {
        try {
            List<Feedback> feedbacks = readFeedbacksFromFile();
            boolean removed = feedbacks.removeIf(f -> f.getId().equals(id));
            if (!removed) {
                throw new IllegalArgumentException("Feedback not found with ID: " + id);
            }
            writeFeedbacksToFile(feedbacks);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error deleting feedback with ID " + id + ": " + e.getMessage(), e);
            throw new RuntimeException("Failed to delete feedback with ID: " + id, e);
        }
    }

    private List<Feedback> readFeedbacksFromFile() throws IOException {
        List<Feedback> feedbacks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return feedbacks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Feedback feedback = objectMapper.readValue(line, Feedback.class);
                    feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }

    private void writeFeedbacksToFile(List<Feedback> feedbacks) throws IOException {
        File directory = new File(FILE_PATH).getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Feedback feedback : feedbacks) {
                writer.write(objectMapper.writeValueAsString(feedback));
                writer.newLine();
            }
        }
    }
}
