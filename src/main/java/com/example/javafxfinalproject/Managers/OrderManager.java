package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Order;
import com.example.javafxfinalproject.Models.OrderItem;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class OrderManager extends BaseManager {


    public ArrayList<Order> getOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        String sql = "SELECT id AS order_id, user_id, address, total_amount, status, discount_id " +
                "FROM orders";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                String address = rs.getString("address");
                double totalAmount = rs.getDouble("total_amount");
                String status = rs.getString("status");
                Integer discountId = rs.getInt("discount_id");

                // Create and add Order object

                Order order = new Order(orderId, userId, address, totalAmount, status );

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public Order getOrderById(int orderId) {
        String sql = "SELECT id AS order_id, user_id, address, total_amount, status" +
                "FROM orders WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, orderId);  // Set the order ID in the query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("user_id");
                    String address = rs.getString("address");
                    double totalAmount = rs.getDouble("total_amount");
                    String status = rs.getString("status");
                    Integer discountId = rs.getInt("discount_id");

                    // Create and return the Order object
                    Order order = new Order(orderId, userId, address, totalAmount, status);
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if the order wasn't found
    }

    public ActionResult<Order> addOrder(int userId, String address, double totalAmount, String status, int discountId) {
        String sql = "INSERT INTO orders (user_id, address_id, total_amount, status, discount_id) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the parameters for the SQL statement
            stmt.setInt(1, userId);
            stmt.setString(2, address);
            stmt.setDouble(3, totalAmount);
            stmt.setString(4, status);
            stmt.setInt(5, discountId);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newOrderId = generatedKeys.getInt(1);  // Get the new order ID
                        Order order = new Order(newOrderId, userId, address, totalAmount, status);
                        return ActionResult.success(order, "Order added successfully");
                    }
                }
            } else {
                return ActionResult.error(null, "Could not add order, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while adding the order: " + e.getMessage());
        }
        return null; // dead code
    }

    public ActionResult<String> updateOrder(Order order) {
        String sql = "UPDATE orders SET user_id = ?, address = ?, total_amount = ?, status = ?  WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the parameters for the SQL statement
            stmt.setInt(1, order.getUserId());
            stmt.setString(2, order.getAddress());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setString(4, order.getStatus());
            stmt.setInt(5, order.getId());  // Set the order ID to identify the record

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return ActionResult.success(null,"Order updated successfully");
            } else {
                return ActionResult.error(null, "Could not update order, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while updating the order: " + e.getMessage());
        }
    }

    public ActionResult<String> deleteOrder(int orderId) {
        String selectOrderSql = "SELECT status FROM orders WHERE id = ?";
        String deleteOrderSql = "DELETE FROM orders WHERE id = ?";

        // Step 1: Use the OrderItemManager to restore stock and get order items
        OrderItemManager orderItemManager = new OrderItemManager();

        try (Connection connection = getConnection(connectionString)) {
            connection.setAutoCommit(false); // Start transaction

            // Step 2: Check if the order status is "pending"
            try (PreparedStatement selectOrderStmt = connection.prepareStatement(selectOrderSql)) {
                selectOrderStmt.setInt(1, orderId);
                try (ResultSet rs = selectOrderStmt.executeQuery()) {
                    if (rs.next()) {
                        String status = rs.getString("status");
                        if ("delivered".equals(status)) {
                            // If order is delivered, cannot delete it
                            return ActionResult.error(null, "Cannot delete a delivered order");
                        }
                    } else {
                        return ActionResult.error(null, "Order not found");
                    }
                }
            }

            // Step 3: Use OrderItemManager to fetch order items and restore stock
            ArrayList<OrderItem> orderItems = orderItemManager.getOrderItemsByOrderId(orderId);
            for (OrderItem orderItem : orderItems) {
                int productId = orderItem.getProductId();
                int quantity = orderItem.getQuantity();

                // Restore the stock for each product
                String updateStockSql = "UPDATE products SET stock = stock + ? WHERE id = ?";
                try (PreparedStatement updateStockStmt = connection.prepareStatement(updateStockSql)) {
                    updateStockStmt.setInt(1, quantity);  // Restore the stock
                    updateStockStmt.setInt(2, productId); // Set the product ID
                    updateStockStmt.executeUpdate();
                }
            }

            // Step 4: Delete the order
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteOrderSql)) {
                deleteStmt.setInt(1, orderId);
                int affectedRows = deleteStmt.executeUpdate();

                if (affectedRows > 0) {
                    connection.commit(); // Commit transaction
                    return ActionResult.success(null, "Order deleted");
                } else {
                    connection.rollback();
                    return ActionResult.error(null, "Could not delete order, please try again");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while deleting the order: " + e.getMessage());
        }
    }






}

