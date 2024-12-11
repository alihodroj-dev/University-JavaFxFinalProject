package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.User;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class AuthManager extends BaseManager {


    public ActionResult<User> login(String email, String password) {
        String sql = "SELECT id, first_name , last_name , email, phone_number, password, role FROM users WHERE email = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password");

                    // Compare the input password with the stored password hash (no hashing for now)
                    if (password.equals(storedPassword)) {  // In real cases, compare hashed passwords
                        // Return the authenticated user
                        int userId = rs.getInt("id");
                        email = rs.getString("email");
                        String phoneNumber = rs.getString("phone_number");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        String role = rs.getString("role");
                        User user = new User(userId, firstName , lastName , password, email, phoneNumber, role);
                        return ActionResult.success(user, "logged In Successfully");
                    } else {
                        return ActionResult.error(null, "Invalid Credentials");
                    }
                } else {
                    return ActionResult.error(null, "Invalid Credentials");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An Error Occurred, please try again later.");
        }
    }

    // Method to handle user registration
    public ActionResult<User> signUp(String firstname, String lastname , String email, String phoneNumber, String password) {

        if(doesEmailExist(email)) {
            return ActionResult.error(null, "Email already exists");
        }


        String sql = "INSERT INTO users (first_name , last_name , email, phone_number, password, role) VALUES (?, ?, ?, ?, ? , ?::user_roles)";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Request generated keys

            // Set parameters for the SQL query
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, password);
            stmt.setString(6, "customer");

            // Execute the insert operation
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated keys (the new user's id)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newUserId = generatedKeys.getInt(1); // The first generated key is the id

                        // Create a new User object with the generated id and other data
                        User newUser = new User(newUserId, firstname, lastname, password, email, phoneNumber, "customer");
                        return ActionResult.success(newUser, "Registration successful");
                    } else {
                        return ActionResult.error(null, "Registration failed");
                    }
                }
            } else {
                return ActionResult.error(null, "Registration failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "Database error: " + e.getMessage());
        }
    }

    private boolean doesEmailExist(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1"; // Query to check if email exists

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the email parameter in the query
            stmt.setString(1, email);

            // Execute the query
            try (ResultSet resultSet = stmt.executeQuery()) {
                // If the query returns a result, the email already exists
                return resultSet.next();
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log the error for debugging
            return false; // Optionally, return false or rethrow the exception
        }
    }

}
