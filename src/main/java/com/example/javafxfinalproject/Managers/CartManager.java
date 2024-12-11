package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import static java.sql.DriverManager.getConnection;

public class CartManager extends BaseManager {

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

    public ActionResult<String> createCart(User user) {
        String sql = "INSERT INTO carts (user_id) VALUES (?)";
        try (Connection connection =  getConnection(connectionString); // Ensure you have a DatabaseConnection utility
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the user_id value
            preparedStatement.setInt(1, user.getId());

            // Execute the insert operation
            int affectedRows = preparedStatement.executeUpdate();

            // Check if the insert was successful
            if (affectedRows > 0) {
                // Retrieve the generated cart ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String cartId = generatedKeys.getString(1);
                        return ActionResult.success(cartId, "Cart created successfully");
                    }
                }
            }

            return  ActionResult.error(null , "Failed to create cart");

        } catch (SQLException e) {
            e.printStackTrace();
            return new ActionResult<>(Status.ERROR, "Database error: " + e.getMessage(), null);
        }



    }


    public ActionResult<String> checkout(User user , int cartId , String address) {
        Connection connection = null;
        try {
            connection = getConnection(connectionString);
            connection.setAutoCommit(false); // Start a transaction
            CartItemManager cartItemManager = new CartItemManager();
            // Step 1: Retrieve CartItems for the given cartId
            ArrayList<CartItem> cartItems = cartItemManager.getCartItemsByCartId(cartId);
            if (cartItems.isEmpty()) {
                return ActionResult.error(null, "Cart is empty");
            }

            // Step 2: Calculate the total amount
            double totalAmount = 0.0;
            for (CartItem cartItem : cartItems) {
                Product product = new ProductManager().getProductById(cartItem.getProductId());
                totalAmount += product.getPrice() * cartItem.getQuantity();
            }



            // Step 4: Create the new order
            String insertOrderSql = "INSERT INTO orders (user_id, address, total_amount, status) " +
                    "VALUES (?, ?, ?, 'Pending', ?)";
            try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
                orderStmt.setInt(1, user.getId()); // user_id from User object
                orderStmt.setString(2, address); // address_id from User object
                orderStmt.setDouble(3, totalAmount);


                int affectedRows = orderStmt.executeUpdate();
                if (affectedRows == 0) {
                    connection.rollback();
                    return ActionResult.error(null, "Failed to create the order");
                }

                try (ResultSet generatedKeys = orderStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int orderId = generatedKeys.getInt(1);

                        // Step 5: Create OrderItems for each CartItem
                        for (CartItem cartItem : cartItems) {
                            Product product = new ProductManager().getProductById(cartItem.getProductId());

                            String insertOrderItemSql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement orderItemStmt = connection.prepareStatement(insertOrderItemSql)) {
                                orderItemStmt.setInt(1, orderId);
                                orderItemStmt.setInt(2, cartItem.getProductId());
                                orderItemStmt.setInt(3, cartItem.getQuantity());
                                orderItemStmt.setDouble(4, product.getPrice());
                                orderItemStmt.executeUpdate();
                            }
                        }
                        // Step 6: Update Stock
                        String updateStockSql = "UPDATE products SET stock = stock - ? WHERE id = ?";
                        try (PreparedStatement updateStockStmt = connection.prepareStatement(updateStockSql)) {
                            for (CartItem cartItem : cartItems) {
                                // Set the quantity to reduce
                                updateStockStmt.setInt(1, cartItem.getQuantity());
                                // Set the product ID
                                updateStockStmt.setInt(2, cartItem.getProductId());
                                // Add to batch for execution
                                updateStockStmt.addBatch();
                            }
                            updateStockStmt.executeBatch();
                        }

                        // Step 7: Delete CartItems from the cart
                        String deleteCartItemsSql = "DELETE FROM cart_items WHERE cart_id = ?";
                        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteCartItemsSql)) {
                            deleteStmt.setInt(1, cartId);
                            deleteStmt.executeUpdate();
                        }

                        // Commit the transaction
                        connection.commit();
                        return ActionResult.success(null, ""+orderId);
                    }
                } catch (SQLException e) {
                    connection.rollback();
                    e.printStackTrace();
                    return ActionResult.error(null, "Error during order creation");
                }

            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
                return ActionResult.error(null, "Error during checkout");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "Error during checkout");
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Restore auto-commit mode
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


        return ActionResult.error(null, "Error during checkout");
    }
}

