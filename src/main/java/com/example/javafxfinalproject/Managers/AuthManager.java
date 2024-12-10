package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Helpers.LogFileHelper;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.User;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class AuthManager extends BaseManager {


    public ActionResult<User> login(String email, String password) {
        String sql = "SELECT id, first_name , last_name , email, phone_number, password FROM users WHERE username = ?";

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
                        User user = new User(userId, firstName , lastName , null, email, phoneNumber);
                        return ActionResult.success(user, "logged In Successfully");
                    } else {
                        return ActionResult.error(null, "Invalid Credentials");
                    }
                } else {
                    return ActionResult.error(null, "Invalid Credentials");
                }
            }
        } catch (SQLException e) {
            LogFileHelper.log(e);
            return ActionResult.error(null, "An Error Occurred, please try again later.");
        }
    }

    // Method to handle user registration
    public ActionResult<User> signUp(String firstname, String lastname , String email, String phoneNumber, String password) {
        String sql = "INSERT INTO users (first_name , last_name , email, phone_number, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Request generated keys

            // Set parameters for the SQL query
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, email);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, password);

            // Execute the insert operation
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated keys (the new user's id)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newUserId = generatedKeys.getInt(1); // The first generated key is the id

                        // Create a new User object with the generated id and other data
                        User newUser = new User(newUserId, firstname, lastname, password, email, phoneNumber);
                        return ActionResult.success(newUser, "Registration successful");
                    } else {
                        return ActionResult.error(null, "Registration failed");
                    }
                }
            } else {
                return ActionResult.error(null, "Registration failed");
            }
        } catch (SQLException e) {
            LogFileHelper.log(e);
            return ActionResult.error(null, "Database error: " + e.getMessage());
        }
    }

}
