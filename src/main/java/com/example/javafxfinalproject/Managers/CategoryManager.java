package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.Category;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class CategoryManager extends BaseManager {
    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();

        String sql = "SELECT id, name FROM categories";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int categoryId = rs.getInt("id");
                String categoryName = rs.getString("name");

                // Create Category object and add to the list
                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public Category getCategoryById(int categoryId) {
        Category category = null; // Use a single Category object

        String sql = "SELECT id, name FROM categories WHERE id = ? LIMIT 1"; // Correct LIMIT syntax

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);  // Set the categoryId parameter

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String categoryName = rs.getString("name");

                // Create the Category object using the data from the ResultSet
                category = new Category(id, categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category; // Return the single Category object
    }


}
