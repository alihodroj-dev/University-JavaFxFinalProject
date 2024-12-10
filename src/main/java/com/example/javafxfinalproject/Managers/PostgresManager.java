//package com.example.javafxfinalproject.Managers;
//
//import com.example.javafxfinalproject.Models.*;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static java.sql.DriverManager.getConnection;
//
//public class PostgresManager {
//    private final String connectionString = "jdbc:postgresql://ep-lively-rain-a2ixsukt.eu-central-1.aws.neon.tech/javafx-demo-1?user=javafx-demo-1_owner&password=mCU34cBibVpN&sslmode=require";
//
//
//
//
//    // SELECT QUERIES
//    public List<Product> getAllProducts() {
//        List<Product> products = new ArrayList<>();
//
//        // SQL query with joins to fetch products along with their associated brand and category data
//        String sql = "SELECT p.id, p.brand_id, p.category_id, p.name, p.description, p.price, p.stock, " +
//                "b.id AS brandId, b.name AS brand_name, c.id AS categoryId, c.name AS category_name " +
//                "FROM products p " +
//                "JOIN brands b ON p.brand_id = b.id " +
//                "JOIN categories c ON p.category_id = c.id";
//
//        try (Connection connection = getConnection(connectionString);
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            // Iterate through the result set and map data to Product objects
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String description = rs.getString("description");
//                double price = rs.getDouble("price");
//                int stock = rs.getInt("stock");
//
//                int brandId = rs.getInt("brand_id");
//                String brandName = rs.getString("brand_name");
//
//                int categoryId = rs.getInt("category_id");
//                String categoryName = rs.getString("category_name");
//
//                // Create Brand and Category objects
//                Brand brand = new Brand(brandId, brandName);
//                Category category = new Category(categoryId, categoryName);
//
//                // Create Product object and add it to the list
//                Product product = new Product(id, brandId, categoryId, brand, category, name, description, price, stock);
//                products.add(product);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();  // Handle exceptions (consider proper logging in a real project)
//        }
//
//        return products;
//    }
//    public List<Category> getAllCategories() {
//        List<Category> categories = new ArrayList<>();
//
//        String sql = "SELECT id, name FROM categories";
//
//        try (Connection connection = getConnection(connectionString);
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                int categoryId = rs.getInt("id");
//                String categoryName = rs.getString("name");
//
//                // Create Category object and add to the list
//                Category category = new Category(categoryId, categoryName);
//                categories.add(category);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return categories;
//    }
//    public List<Brand> getAllBrands() {
//        List<Brand> brands = new ArrayList<>();
//
//        String sql = "SELECT id, name FROM brands";
//
//        try (Connection connection = getConnection(connectionString);
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                int brandId = rs.getInt("id");
//                String brandName = rs.getString("name");
//
//                // Create Brand object and add to the list
//                Brand brand = new Brand(brandId, brandName);
//                brands.add(brand);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return brands;
//    }
//    public List<User> getAllUsers() {
//        List<User> users = new ArrayList<>();
//
//        String sql = "SELECT id, username, email, phone_number FROM users";
//
//        try (Connection connection = getConnection(connectionString);
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                int userId = rs.getInt("id");
//                String username = rs.getString("username");
//                String email = rs.getString("email");
//                String phoneNumber = rs.getString("phone_number");
//
//                // Create User object and add to the list
//                User user = new User(userId, username, null, email, phoneNumber);
//                users.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return users;
//    }
//    public List<Order> getAllOrders() {
//        List<Order> orders = new ArrayList<>();
//        Map<Integer, Order> orderMap = new HashMap<>(); // Map to track orders by their orderId
//
//        String sql = "SELECT o.id AS order_id, o.user_id, o.address_id, o.total_amount, o.status, " +
//                "o.discount_id, u.id AS user_id, u.username, u.email, u.phone_number, " +
//                "ai.id AS order_item_id, ai.product_id, ai.quantity, ai.price, " +
//                "a.id AS address_id, a.country, a.city, a.street, a.building, a.postal_code, " +
//                "d.id AS discount_id, d.code AS discount_code, d.expiry_date AS discount_expiry_date, d.discounted_amount AS discount_discounted_amount " +
//                "FROM orders o " +
//                "LEFT JOIN users u ON o.user_id = u.id " +
//                "LEFT JOIN order_items ai ON o.id = ai.order_id " +
//                "LEFT JOIN products p ON ai.product_id = p.id " +
//                "LEFT JOIN brands b ON p.brand_id = b.id " +
//                "LEFT JOIN categories c ON p.category_id = c.id " +
//                "LEFT JOIN addresses a ON o.address_id = a.id " +
//                "LEFT JOIN discounts d ON o.discount_id = d.id";
//
//        try (Connection connection = getConnection(connectionString);
//             Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                int orderId = rs.getInt("order_id");
//
//                // Check if the order already exists in the map
//                Order order = orderMap.get(orderId);
//
//                if (order == null) {
//                    int userId = rs.getInt("user_id");
//                    int addressId = rs.getInt("address_id");
//                    double totalAmount = rs.getDouble("total_amount");
//                    String status = rs.getString("status");
//                    int discountId = rs.getInt("discount_id");
//
//                    // Create User object
//                    String username = rs.getString("username");
//                    String email = rs.getString("email");
//                    String phoneNumber = rs.getString("phone_number");
//                    User user = new User(userId, username, null, email, phoneNumber);
//
//                    // Create Address object
//                    String country = rs.getString("country");
//                    String city = rs.getString("city");
//                    String street = rs.getString("street");
//                    String building = rs.getString("building");
//                    String postalCode = rs.getString("postal_code");
//                    Address address = new Address(addressId, userId, country, city, street, building, postalCode);
//
//                    // Create Discount object if discount_id is not null
//                    Discount discount = null;
//                    if (discountId != 0) {
//                        String discountCode = rs.getString("discount_code");
//                        Date discountExpiryDate = rs.getDate("discount_expiry_date");
//                        double discountAmount = rs.getDouble("discount_discounted_amount");
//                        discount = new Discount(discountId, discountCode, discountExpiryDate, discountAmount);
//                    }
//
//                    // Create Order object and associate with User, Address, and Discount
//                    order = new Order(orderId, userId, addressId, totalAmount, status, discountId, discount);
//                    order.setUser(user);
//                    order.setAddress(address);
//                    order.setDiscount(discount);  // Set the discount for the order
//
//                    // Add the newly created order to the map
//                    orderMap.put(orderId, order);
//                }
//
//                // Create OrderItem object for the current row
//                int orderItemId = rs.getInt("order_item_id");
//                int productId = rs.getInt("product_id");
//                int quantity = rs.getInt("quantity");
//                double price = rs.getDouble("price");
//                OrderItem orderItem = new OrderItem(orderItemId, orderId, productId, quantity, price);
//
//                // Add the OrderItem to the corresponding Order
//                order.addOrderItem(orderItem);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // Return the list of all orders with their associated order items and discount
//        return new ArrayList<>(orderMap.values());
//    }
//
//
//
//
//
//
//}