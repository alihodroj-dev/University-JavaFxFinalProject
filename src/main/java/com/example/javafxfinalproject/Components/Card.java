package com.example.javafxfinalproject.Components;

import com.example.javafxfinalproject.Models.Product;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Card extends VBox {

    public Card(Product product) {
        // Apply the card style
        this.getStyleClass().add("card");

        // Create the card header (Product name)
        Label header = new Label(product.getName());
        header.getStyleClass().add("card-header");

        // Product description under the name (lighter font)
        Label description = new Label(product.getDescription());
        description.getStyleClass().add("card-description");

        // Create a horizontal box for category and brand (same line)
        HBox categoryBrandBox = new HBox();
        categoryBrandBox.setSpacing(10); // Space between category and brand
        categoryBrandBox.getStyleClass().add("card-category-brand");

        Label category = new Label(product.getCategory().getName());
        category.getStyleClass().add("card-category");

        Label brand = new Label(product.getBrand().getName());
        brand.getStyleClass().add("card-brand");

        categoryBrandBox.getChildren().addAll(category, brand);

        // Create a label for price with a bigger font
        Label price = new Label("$" + product.getPrice());
        price.getStyleClass().add("card-price");

        // Buttons for "Add to Cart" and "View"
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10); // Space between the buttons
        buttonBox.getStyleClass().add("card-button-box");

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.getStyleClass().add("card-add-to-cart-button");

        Button viewButton = new Button("View");
        viewButton.getStyleClass().add("card-view-button");

        buttonBox.getChildren().addAll(addToCartButton, viewButton);

        // Create the card content
        VBox content = new VBox();
        content.setSpacing(10);
        content.getStyleClass().add("card-content");

        // Add the category/brand, description, price, and buttons to the content
        content.getChildren().addAll(description, categoryBrandBox, price, buttonBox);

        // Add header and content to the card
        this.getChildren().addAll(header, content);
    }
}
