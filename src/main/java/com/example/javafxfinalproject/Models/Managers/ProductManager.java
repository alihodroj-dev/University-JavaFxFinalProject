package com.example.javafxfinalproject.Models.Managers;

import com.example.javafxfinalproject.Models.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class ProductManager extends  BaseManager {





    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();

        // SQL query
        String sql = "SELECT id, brand_id, category_id,name, description,price,stock, " +
                "FROM products ";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
}
