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


    public ActionResult<String> checkout(User user, int cartId, String address) {
        Connection connection = null;

        try {
            connection = getConnection(connectionString);
            connection.setAutoCommit(false); // Start a transaction

            // Step 1: Retrieve Cart Items
            ArrayList<CartItem> cartItems = getCartItems(cartId);
            if (cartItems.isEmpty()) {
                return ActionResult.error(null, "Cart is empty");
            }

            // Step 2: Calculate Total Amount
            double totalAmount = calculateTotalAmount(cartItems);

            // Step 3: Create Order
            int orderId = createOrder(connection, user, address, totalAmount);
            if (orderId == -1) {
                connection.rollback();
                return ActionResult.error(null, "Failed to create the order");
            }

            // Step 4: Add Order Items
            if (!addOrderItems(connection, orderId, cartItems)) {
                connection.rollback();
                return ActionResult.error(null, "Failed to add order items");
            }

            // Step 5: Update Product Stock
            if (!updateStock(connection, cartItems)) {
                connection.rollback();
                return ActionResult.error(null, "Failed to update stock");
            }

            // Step 6: Delete Cart Items
            if (!deleteCartItems(connection, cartId)) {
                connection.rollback();
                return ActionResult.error(null, "Failed to clear cart");
            }

            // Commit transaction
            connection.commit();
            return ActionResult.success(null, "" + orderId);

        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "Error during checkout");
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true); // Restore auto-commit mode
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

// --- Private Helper Methods ---

    private ArrayList<CartItem> getCartItems(int cartId) {
        CartItemManager cartItemManager = new CartItemManager();
        return cartItemManager.getCartItemsByCartId(cartId);
    }

    private double calculateTotalAmount(ArrayList<CartItem> cartItems) {
        double totalAmount = 0.0;
        ProductManager productManager = new ProductManager();
        for (CartItem cartItem : cartItems) {
            Product product = productManager.getProductById(cartItem.getProductId());
            totalAmount += product.getPrice() * cartItem.getQuantity();
        }
        return totalAmount;
    }

    private int createOrder(Connection connection, User user, String address, double totalAmount) {
        String sql = "INSERT INTO orders (user_id, address, total_amount, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, user.getId());
            stmt.setString(2, address);
            stmt.setDouble(3, totalAmount);
            stmt.setString(4, "pending");

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    private boolean addOrderItems(Connection connection, int orderId, ArrayList<CartItem> cartItems) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ProductManager productManager = new ProductManager();
            for (CartItem cartItem : cartItems) {
                Product product = productManager.getProductById(cartItem.getProductId());
                stmt.setInt(1, orderId);
                stmt.setInt(2, cartItem.getProductId());
                stmt.setInt(3, cartItem.getQuantity());
                stmt.setDouble(4, product.getPrice());
                stmt.addBatch();
            }

            stmt.executeBatch();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateStock(Connection connection, ArrayList<CartItem> cartItems) {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (CartItem cartItem : cartItems) {
                stmt.setInt(1, cartItem.getQuantity());
                stmt.setInt(2, cartItem.getProductId());
                stmt.addBatch();
            }

            stmt.executeBatch();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean deleteCartItems(Connection connection, int cartId) {
        String sql = "DELETE FROM cart_items WHERE cart_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

