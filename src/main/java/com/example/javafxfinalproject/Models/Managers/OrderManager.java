package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.*;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class OrderManager extends BaseManager {


    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT id AS order_id, user_id, address_id, total_amount, status, discount_id " +
                "FROM orders";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                int addressId = rs.getInt("address_id");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                int discountId = rs.getInt("discount_id");

                // Create and add Order object

                Order order = new Order(orderId, userId, addressId, totalAmount, status );

                if(discountId > 0) {
                    order.setDiscountId(discountId);
                    order.setDiscounted(true);
                }

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    }

