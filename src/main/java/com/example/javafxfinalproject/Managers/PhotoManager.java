package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class PhotoManager extends BaseManager {

    // Method to get photo by product ID
    public Photo getPhotoByProductId(int productId) {
        String sql = "SELECT id, product_id, url FROM photos WHERE product_id = ? LIMIT ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the productId parameter for the query
            stmt.setInt(1, productId);
            stmt.setInt(2, 1);

            // Execute the query and process the results
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String url = rs.getString("url");

                // Create a Photo object and add it to the list
                return new Photo(id, productId, url);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Method to add a new Photo
    public ActionResult<String> addPhoto(String url, int productId) {
        String sql = "INSERT INTO photos (url, product_id) VALUES (?, ?)";

        try(Connection connection = getConnection(connectionString) ; PreparedStatement stmt = connection.prepareStatement(sql))
        {
           stmt.setString(1, url);
           stmt.setInt(2, productId);
           int affectedRows = stmt.executeUpdate();
           if(affectedRows > 0)
           {
               return ActionResult.success(null, "Photo added successfully");
           }
           else
           {
               return ActionResult.error(null, "Could not add photo, please try again");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to update the URL of a Photo
    public ActionResult<String> updatePhotoUrl(int productId, String newUrl) {
        String sql = "UPDATE photos SET url = ? WHERE product_id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the parameters for the query
            stmt.setString(1, newUrl);
            stmt.setInt(2, productId);

            // Execute the update
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return ActionResult.success(null, "Photo URL updated successfully");
            } else {
                return ActionResult.error(null, "No photo found with the given ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while updating the photo URL");
        }
    }
}
