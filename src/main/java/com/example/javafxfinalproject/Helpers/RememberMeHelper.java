package com.example.javafxfinalproject.Helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RememberMeHelper {

    private static final String FILE_NAME = "credentials.csv";

    // Save username and password (replace old credentials if they exist)
    public static void saveCredentials(String id, String role) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write(id + "," + role); // Write the username and password
        }
    }

    // Read saved credentials
    public static List<String> readCredentials() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    List<String> credentials = new ArrayList<>();
                    credentials.add(parts[0]); // Id
                    credentials.add(parts[1]); // Role
                    return credentials;
                }
            }
        }
        return new ArrayList<>(); // Return empty list if no valid data
    }

    // Delete saved credentials
    public static void clearCredentials() throws IOException {
        new File(FILE_NAME).delete(); // Delete the credentials file
    }
}
