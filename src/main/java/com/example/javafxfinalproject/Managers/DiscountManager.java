package com.example.javafxfinalproject.Managers;

import com.example.javafxfinalproject.Managers.BaseManager;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Discount;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class DiscountManager extends BaseManager {

    // Create a new discount
    public ActionResult<Discount> addDiscount(String code, Date expiryDate, double discountedAmount) {
        String sql = "INSERT INTO discounts (code, expiry_date, discounted_amount) VALUES (?, ?, ?)";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, code);
            stmt.setDate(2, expiryDate);
            stmt.setDouble(3, discountedAmount);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int discountId = generatedKeys.getInt(1);
                        Discount discount = new Discount(discountId, code, expiryDate, discountedAmount);
                        return ActionResult.success(discount, "Discount added successfully");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ActionResult.error(null, "Failed to add discount");
    }

    // Get a discount by ID
    public Discount getDiscountById(int discountId) {
        String sql = "SELECT * FROM discounts WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, discountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String code = rs.getString("code");
                Date expiryDate = rs.getDate("expiry_date");
                double discountedAmount = rs.getDouble("discounted_amount");
                return new Discount(discountId, code, expiryDate, discountedAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Get a discount by Discount CODE
    public Discount getDiscountByCode(String discountCode) {
        String sql = "SELECT * FROM discounts WHERE code = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, discountCode);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String code = rs.getString("code");
                Date expiryDate = rs.getDate("expiry_date");
                double discountedAmount = rs.getDouble("discounted_amount");
                int id = rs.getInt("id");
                return new Discount(id , code , expiryDate , discountedAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all discounts
    public ArrayList<Discount> getAllDiscounts() {
        ArrayList<Discount> discounts = new ArrayList<>();
        String sql = "SELECT * FROM discounts";

        try (Connection connection = getConnection(connectionString);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                Date expiryDate = rs.getDate("expiry_date");
                double discountedAmount = rs.getDouble("discounted_amount");
                discounts.add(new Discount(id, code, expiryDate, discountedAmount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }

    // Update an existing discount
    public ActionResult<String> updateDiscount(Discount discount) {
        String sql = "UPDATE discounts SET code = ?, expiry_date = ?, discounted_amount = ? WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, discount.getCode());
            stmt.setDate(2, (Date) discount.getExpiryDate());
            stmt.setDouble(3, discount.getDiscounted_amount());
            stmt.setInt(4, discount.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return ActionResult.success(null, "Discount updated successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ActionResult.error(null, "Failed to update discount");
    }

    // Delete a discount
    public ActionResult<String> deleteDiscount(int discountId) {
        String sql = "DELETE FROM discounts WHERE id = ?";

        try (Connection connection = getConnection(connectionString);
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, discountId);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return ActionResult.success(null, "Discount deleted successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ActionResult.error(null, "Failed to delete discount");
    }
}
