package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class OrderItemManager extends BaseManager {

    public ArrayList<OrderItem> getOrderItemsByOrderId(int orderId) {
        ArrayList<OrderItem> orderItems = new ArrayList<>();

        String sql = "SELECT id, order_id, product_id, quantity, price " +
                "FROM order_items " +
                "WHERE order_id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");

                // Create and add OrderItem object
                OrderItem orderItem = new OrderItem(id, orderId, productId, quantity, price);
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }
}

