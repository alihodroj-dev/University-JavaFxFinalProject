package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Product;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class ProductManager extends BaseManager {

    public Integer size()
    {
        String sql = "SELECT COUNT(id) FROM products";
        try(Connection connection = getConnection(connectionString); Statement stmt = connection.createStatement())
        {
            ResultSet rs =  stmt.executeQuery(sql);
            if(rs.next())
            {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public ArrayList<Product> getProducts(int tracker , int limit) {
        ArrayList<Product> products = new ArrayList<>();

        // SQL query
        String sql = "SELECT id, brand_id, category_id, name, description, price, stock " +
                "FROM products " +
                "WHERE id > ? " +
                "ORDER BY id  ASC " +
                "LIMIT ? ";


        try (Connection connection = getConnection(connectionString);  ) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, tracker);
            ps.setInt(2, limit);
            try (ResultSet rs = ps.executeQuery()) {

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

    public Integer Size()
    {
        String sql = "SELECT COUNT(id) FROM products";
        try(Connection connection = getConnection(connectionString); Statement stmt = connection.createStatement())
        {
            ResultSet rs =  stmt.executeQuery(sql);
            if(rs.next())
            {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ActionResult<Integer> addProduct(Product product) {
        String sql = "INSERT INTO products (name, description, brand_id, category_id, price, stock) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(connectionString); PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set the values for the SQL statement
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getBrandId());
            stmt.setInt(4, product.getCategoryId());
            stmt.setDouble(5, product.getPrice());
            stmt.setInt(6, product.getStock());

            // Execute the update and check affected rows
            int affectedRows = stmt.executeUpdate();

            // Check if any rows were affected
            if (affectedRows > 0) {
                // Retrieve the generated keys (the new product ID)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newProductId = generatedKeys.getInt(1); // The first generated key is the product id

                        // Create the new Product object with the generated id and other data
                        return ActionResult.success(newProductId, "Product added successfully");
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

    public ActionResult<String> updateProduct(Product product) {
        // SQL query to update the product
        String sql = "UPDATE products SET name = ?, description = ?, brand_id = ?, category_id = ?, price = ?, stock = ? WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            // Set the parameters for the SQL statement
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getBrandId());
            stmt.setInt(4, product.getCategoryId());
            stmt.setDouble(5, product.getPrice());
            stmt.setInt(6, product.getCategoryId());
            stmt.setInt(7, product.getId());  // Specify the product ID to identify which record to update

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
