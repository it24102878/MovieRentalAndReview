package com.Movie.AdminManagment.util;

import java.io.*;
import java.util.*;

public class FileUtil {

    public static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        try (InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeLines(String filename, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void appendLine(String filename, String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
