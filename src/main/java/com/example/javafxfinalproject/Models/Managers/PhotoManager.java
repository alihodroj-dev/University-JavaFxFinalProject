package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.Photo;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class PhotoManager extends BaseManager {

    // Method to get photos by product ID
    public ArrayList<Photo> getPhotosByProductId(int productId) {
        ArrayList<Photo> photos = new ArrayList<>();
        String sql = "SELECT id, product_id, url, is_main FROM photos WHERE product_id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the productId parameter for the query
            stmt.setInt(1, productId);

            // Execute the query and process the results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("url");
                boolean isMain = rs.getBoolean("is_main");

                // Create a Photo object and add it to the list
                Photo photo = new Photo(id, productId, url, isMain);
                photos.add(photo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return photos;
    }
}
