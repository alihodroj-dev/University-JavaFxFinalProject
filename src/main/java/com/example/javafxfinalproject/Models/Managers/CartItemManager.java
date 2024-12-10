package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;


public class CartItemManager extends BaseManager {
    public ArrayList<CartItem> getCartItemsByCartId(int cartId) {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT ci.id AS cart_item_id, " +
                "       ci.cart_id, " +
                "       ci.product_id, " +
                "       ci.quantity " +
                "FROM cart_items ci " +
                "WHERE ci.cart_id = ?;";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int cartItemId = rs.getInt("cart_item_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");

                // Create a CartItem object and add it to the list
                CartItem cartItem = new CartItem(cartItemId, cartId, productId, quantity);
                cartItems.add(cartItem);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

}
