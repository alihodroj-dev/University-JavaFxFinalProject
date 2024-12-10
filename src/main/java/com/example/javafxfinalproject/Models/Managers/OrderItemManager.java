package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
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
    public ActionResult<OrderItem> addOrderItem(int orderId, int productId, int quantity, double price) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Set the parameters for the SQL statement
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // Get the generated ID of the new OrderItem
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1);  // Get the generated ID
                        OrderItem orderItem = new OrderItem(newId, orderId, productId, quantity, price);
                        return ActionResult.success(orderItem, "Order item added successfully");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while adding the order item: " + e.getMessage());
        }
        return ActionResult.error(null, "Could not add order item, please try again");
    }
    public ActionResult<String> updateOrderItem(int id, int quantity, double price) {
        String sql = "UPDATE order_items SET quantity = ?, price = ? WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the parameters for the SQL statement
            stmt.setInt(1, quantity);
            stmt.setDouble(2, price);
            stmt.setInt(3, id);  // Set the order item ID to identify the record

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return ActionResult.success(null,"Order item updated successfully");
            } else {
                return ActionResult.error(null, "Could not update order item, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while updating the order item: " + e.getMessage());
        }
    }
    public ActionResult<String> deleteOrderItem(int id) {
        String sql = "DELETE FROM order_items WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Set the order item ID to delete

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return ActionResult.success(null,"Order item deleted successfully");
            } else {
                return ActionResult.error(null, "Could not delete order item, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while deleting the order item: " + e.getMessage());
        }
    }



}

