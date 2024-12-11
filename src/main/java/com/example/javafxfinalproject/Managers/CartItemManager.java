package com.example.javafxfinalproject.Managers;

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
    public ActionResult<String> addCartItem(int cartId, int productId, int quantity) {
        Integer existingQuantity = checkIfCartItemExists(cartId, productId);
        if(existingQuantity != null) {
            addQuantity(existingQuantity + quantity, cartId , productId );
            return ActionResult.success(""+existingQuantity + quantity, "Product addedd to cart");
        }
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

                        return ActionResult.success(""+newId, "Product added to cart");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while adding the cart : " + e.getMessage());
        }
        return ActionResult.error(null, "Could not add product, please try again");
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
            return ActionResult.error(null, "An error occurred while updating the cart : " + e.getMessage());
        }
    }
    public Integer getCount(int cartId) {
        String sql = "SELECT COUNT(id) FROM cart_items WHERE cart_id = ?";

        try(Connection connection = getConnection( connectionString) ; PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1 , cartId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public ActionResult<String> addQuantity(int newQuantity , int cartId , int productid) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE cart_id = ? AND product_id = ? ";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, cartId);
            stmt.setInt(3, productid);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                return ActionResult.success(null,"Added Product");
            } else {
                return ActionResult.error(null, "Could not add product, please try again");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while updating the cart : " + e.getMessage());
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

    private Integer checkIfCartItemExists(int cartId, int productId)  {
        String checkSql = "SELECT quantity FROM cart_items WHERE cart_id = ? AND product_id = ?";

        try (Connection connection = getConnection(connectionString) ;PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, cartId);
            checkStmt.setInt(2, productId);

            try (ResultSet rs = checkStmt.executeQuery()) {
                if(rs.next()) {
                   return rs.getInt("quantity");
                }
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
