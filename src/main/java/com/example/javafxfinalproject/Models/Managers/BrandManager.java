package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.Brand;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class BrandManager extends BaseManager {
    public ArrayList<Brand> getBrands() {
        ArrayList<Brand> brands = new ArrayList<>();

        String sql = "SELECT id, name FROM brands";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int brandId = rs.getInt("id");
                String brandName = rs.getString("name");

                // Create Brand object and add to the list
                Brand brand = new Brand(brandId, brandName);
                brands.add(brand);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brands;
    }
    public Brand getBrandById(int brandId) {
        Brand brand = null; // Use a single Brand object instead of a list

        String sql = "SELECT id, name FROM brands WHERE id = ? LIMIT 1"; // Correct LIMIT syntax

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, brandId);  // Set the brandId parameter

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String brandName = rs.getString("name");

                // Create the Brand object using the data from the ResultSet
                brand = new Brand(id, brandName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brand; // Return the single Brand object
    }

}
