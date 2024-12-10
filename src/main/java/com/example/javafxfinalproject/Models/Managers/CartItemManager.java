package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.CartItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class CartItemManager extends BaseManager {

    // Get all cart items by cart ID
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

    // Add a new cart item
    public ActionResult<CartItem> addCartItem(int cartId, int productId, int quantity) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cartId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newId = generatedKeys.getInt(1); // Get the generated ID
                        CartItem cartItem = new CartItem(newId, cartId, productId, quantity);
                        return ActionResult.success(cartItem, "Cart item added successfully");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while adding the cart item: " + e.getMessage());
        }
        return ActionResult.error(null, "Could not add cart item, please try again");
    }

    // Update an existing cart item
    public ActionResult<String> updateCartItem(CartItem cartItem) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, cartItem.getQuantity());
            stmt.setInt(2, cartItem.getId()); // Set the cart item ID to identify the record

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return ActionResult.success(null,"Cart item updated successfully");
            } else {
                return ActionResult.error(null, "Could not update cart item, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while updating the cart item: " + e.getMessage());
        }
    }

    // Delete a cart item by its ID
    public ActionResult<String> deleteCartItem(int id) {
        String sql = "DELETE FROM cart_items WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id); // Set the cart item ID to delete

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return ActionResult.success(null,"Cart item deleted successfully");
            } else {
                return ActionResult.error(null, "Could not delete cart item, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while deleting the cart item: " + e.getMessage());
        }
    }
}
