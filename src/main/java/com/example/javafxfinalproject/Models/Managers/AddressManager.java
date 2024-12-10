package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class AddressManager extends BaseManager {

    // Method to get addresses by user ID
    public ArrayList<Address> getAddressesByUserId(int userId) {
        ArrayList<Address> addresses = new ArrayList<>();
        String sql = "SELECT id, user_id, country, city, street, building, postal_code FROM addresses WHERE user_id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the userId parameter for the query
            stmt.setInt(1, userId);

            // Execute the query and process the results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String country = rs.getString("country");
                String city = rs.getString("city");
                String street = rs.getString("street");
                String building = rs.getString("building");
                String postalCode = rs.getString("postal_code");

                // Create an Address object and add it to the list
                Address address = new Address(id, userId, country, city, street, building, postalCode);
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }
}
