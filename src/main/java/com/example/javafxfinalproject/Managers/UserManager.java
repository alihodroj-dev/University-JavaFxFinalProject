package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.User;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class UserManager extends BaseManager {
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        String sql = "SELECT id, first_name , last_name , email, phone_number FROM users";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int userId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");

                // Create User object and add to the list
                User user = new User(userId, firstName , lastName, null, email, phoneNumber);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
    public User getUserById(int userId) {
        String sql = "SELECT id, first_name, last_name, email, phone_number FROM users WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");

                // Create and return the User object
                return new User(userId, firstName, lastName, null, email, phoneNumber);
            } else {
                return null; // No user found with the provided ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }
}
