package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.DriverManager.getConnection;

public class CartManager extends  BaseManager {

    public Cart getCartByUserId(int userId) {
        String sql = "SELECT c.id AS cart_id, c.user_id " +
                "FROM carts c " +
                "WHERE c.user_id = ? " +
                "ORDER BY c.created_at DESC;";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int cartId = rs.getInt("cart_id");
                int userIdFromDb = rs.getInt("user_id");
                return new Cart(cartId, userIdFromDb);
            } else {
                return null; // No cart found for the user
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null in case of an error
        }
    }



    }

