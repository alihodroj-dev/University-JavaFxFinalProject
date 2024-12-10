//package com.example.javafxfinalproject.Managers;
//
//import com.example.javafxfinalproject.Models.ActionResult;
//import com.example.javafxfinalproject.Models.User;
//
//import java.sql.*;
//import static java.sql.DriverManager.getConnection;
//
//public class AuthManager {
//
//    private final String connectionString = "jdbc:postgresql://ep-lively-rain-a2ixsukt.eu-central-1.aws.neon.tech/javafx-demo-1?user=javafx-demo-1_owner&password=mCU34cBibVpN&sslmode=require";
//
//    public ActionResult<User> login(String username, String password) {
//        String sql = "SELECT id, username, email, phone_number, password FROM users WHERE username = ?";
//
//        try (Connection connection = getConnection(connectionString);
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//
//            stmt.setString(1, username);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    String storedPassword = rs.getString("password");
//
//                    // Compare the input password with the stored password hash (no hashing for now)
//                    if (password.equals(storedPassword)) {  // In real cases, compare hashed passwords
//                        // Return the authenticated user
//                        int userId = rs.getInt("id");
//                        String email = rs.getString("email");
//                        String phoneNumber = rs.getString("phone_number");
//                        User user = new User(userId, username, null, email, phoneNumber);
//                        return ActionResult.success(user , "logged In Successfully");
//                    } else {
//                        return ActionResult.error(null, "Invalid Credentials");
//                    }
//                } else {
//                    return ActionResult.error(null , "Invalid Credentials");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return ActionResult.error(null, "An Error Occurred, please try again later.");
//        }
//    }
//
//    // Method to handle user registration
//    public ActionResult<User> register(String username, String email, String phoneNumber, String password) {
//        String sql = "INSERT INTO users (username, email, phone_number, password) VALUES (?, ?, ?, ?)";
//
//        try (Connection connection = getConnection(connectionString);
//             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Request generated keys
//
//            // Set parameters for the SQL query
//            stmt.setString(1, username);
//            stmt.setString(2, email);
//            stmt.setString(3, phoneNumber);
//            stmt.setString(4, password);
//
//            // Execute the insert operation
//            int affectedRows = stmt.executeUpdate();
//
//            if (affectedRows > 0) {
//                // Get the generated keys (the new user's id)
//                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
//                    if (generatedKeys.next()) {
//                        int newUserId = generatedKeys.getInt(1); // The first generated key is the id
//
//                        // Create a new User object with the generated id and other data
//                        User newUser = new User(newUserId, username , password, email, phoneNumber );
//                        return ActionResult.success(newUser, "Registration successful");
//                    } else {
//                        return ActionResult.error(null, "Registration failed");
//                    }
//                }
//            } else {
//                return ActionResult.error(null, "Registration failed");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return ActionResult.error(null, "Database error: " + e.getMessage());
//        }
//    }
//
//}
