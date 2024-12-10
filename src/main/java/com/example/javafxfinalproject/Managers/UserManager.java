package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.User;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class UserManager extends BaseManager {
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT id, first_name , last_name , password, email, phone_number, role FROM users ORDER BY id ASC";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int userId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phoneNumber = rs.getString("phone_number");
                String role = rs.getString("role");

                // Create User object and add to the list
                User user = new User(userId, firstName , lastName, password, email, phoneNumber, role);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }



    public User getUserById(int userId) {
        String sql = "SELECT id, first_name, last_name, email, password, phone_number , role FROM users WHERE id = ? LIMIT 1";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String role = rs.getString("role");

                // Create and return the User object
                return new User(userId, firstName, lastName, password, email, phoneNumber, role);
            } else {
                return null; // No user found with the provided ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }

    public ActionResult<String> updateUser(User user) {
        // Ensure password is retained from the existing user
        User existingUser = getUserById(user.getId());
        if (existingUser == null) {
            return ActionResult.error(null, "User not found");
        }
        user.setPassword(existingUser.getPassword());

        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password = ? WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set parameters for the prepared statement
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getPassword());
            stmt.setInt(6, user.getId());

            // Execute the update
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return ActionResult.success(null,"User updated successfully");
            } else {
                return ActionResult.error(null, "No rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred: " + e.getMessage());
        }
    }

}
