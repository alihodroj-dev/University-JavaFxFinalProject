package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Order;
import com.example.javafxfinalproject.Models.Product;
import com.example.javafxfinalproject.Models.User;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class ProductManager extends BaseManager {


    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();

        // SQL query
        String sql = "SELECT id, brand_id, category_id,name, description,price,stock " + "FROM products";

        try (Connection connection = getConnection(connectionString); Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Iterate through the result set and map data to Product objects
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int brandId = rs.getInt("brand_id");
                int categoryId = rs.getInt("category_id");

                // Create Product object and add it to the list
                Product product = new Product(id, brandId, categoryId, name, description, price, stock);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getProductById(int id) {
        String sql = "SELECT id, brand_id, category_id, name, description, price, stock FROM products WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Set the product ID parameter
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    int productId = rs.getInt("id");
                    int brandId = rs.getInt("brand_id");
                    int categoryId = rs.getInt("category_id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");

                    // Create and return a Product object
                    return new Product(productId, brandId, categoryId, name, description, price, stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;  // Return null if no product was found
    }



    public ActionResult<Product> addProduct(String name, String description, int brandId, int categoryId, double price, int stock) {
        String sql = "INSERT INTO products (name, description, brand_id, category_id, price, stock) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(connectionString); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the values for the SQL statement
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, brandId);
            stmt.setInt(4, categoryId);
            stmt.setDouble(5, price);
            stmt.setInt(6, stock);

            // Execute the update and check affected rows
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows > 0) {
                // Retrieve the generated keys (the new product ID)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newProductId = generatedKeys.getInt(1); // The first generated key is the product id

                        // Create the new Product object with the generated id and other data
                        Product product = new Product(newProductId, brandId, categoryId, name, description, price, stock);
                        return ActionResult.success(product, "Product added successfully");
                    } else {
                        // If no keys are returned, there was an issue with generating the product ID
                        return ActionResult.error(null, "Could not retrieve product ID, please try again");
                    }
                }
            } else {
                // If no rows were affected, something went wrong during insertion
                return ActionResult.error(null, "Could not add product, please try again");
            }

        } catch (SQLException e) {
            // Log and return an error message if an SQLException is thrown
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while adding the product: " + e.getMessage());
        }
    }

    public ActionResult<String> updateProduct(int productId, String name, String description, int brandId, int categoryId, double price, int stock) {
        // SQL query to update the product
        String sql = "UPDATE products SET name = ?, description = ?, brand_id = ?, category_id = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the parameters for the SQL statement
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, brandId);
            stmt.setInt(4, categoryId);
            stmt.setDouble(5, price);
            stmt.setInt(6, stock);
            stmt.setInt(7, productId);  // Specify the product ID to identify which record to update

            // Execute the update and check the affected rows
            int affectedRows = stmt.executeUpdate();

            // If no rows were affected, it means the product wasn't found or updated
            if (affectedRows > 0) {
                return ActionResult.success(null, "Product Updated Successfully");
            } else {
                // If no rows were affected, the update didn't work (maybe invalid product ID)
                return ActionResult.error(null, "Could not update product, please try again");
            }

        } catch (SQLException e) {
            // Log and return an error message if an SQLException is thrown
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while updating the product: " + e.getMessage());
        }
    }

    public ActionResult<String> deleteProduct(int productId) {
        // SQL query to delete a product by its ID
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the product ID parameter for the SQL statement
            stmt.setInt(1, productId);

            // Execute the update and check the affected rows
            int affectedRows = stmt.executeUpdate();

            // If no rows were affected, it means the product wasn't found or deleted
            if (affectedRows > 0) {
                return ActionResult.success(null,"Product deleted successfully");
            } else {
                // If no rows were affected, the deletion didn't work (maybe invalid product ID)
                return ActionResult.error(null, "Could not delete product, please try again");
            }

        } catch (SQLException e) {
            // Log and return an error message if an SQLException is thrown
            e.printStackTrace();
            return ActionResult.error(null, "An error occurred while deleting the product: " + e.getMessage());
        }
    }



}
