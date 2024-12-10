package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserManager extends BaseManager {
    public ArrayList<User> getProducts() {
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
}
