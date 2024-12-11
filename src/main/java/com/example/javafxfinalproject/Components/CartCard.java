package com.example.javafxfinalproject.Components;

import com.example.javafxfinalproject.Managers.CartItemManager;
import com.example.javafxfinalproject.Managers.ProductManager;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.CartItem;
import com.example.javafxfinalproject.Models.Product;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static com.example.javafxfinalproject.Components.Toast.showToast;

public class CartCard extends HBox {

    private final CartItem cartItem;

    public CartCard(Stage stage, CartItem cartItem) {
        this.cartItem = cartItem;
        Product product = new ProductManager().getProductById(cartItem.getProductId());

        // Use CSS class "card" for the overall layout
        this.getStyleClass().add("card");
        this.setPadding(new Insets(10)); // Add padding for spacing
        this.setSpacing(20); // Horizontal spacing between elements

        // Create product name label
        Label productName = new Label(product.getName());
        productName.getStyleClass().add("cart-item-name");
        productName.setPrefWidth(200); // Consistent width for all names

        // Create quantity label
        Label quantity = new Label("Quantity: " + cartItem.getQuantity());
        quantity.getStyleClass().add("cart-item-quantity");
        quantity.setPrefWidth(100); // Fixed width for consistent layout

        // Create price label
        Label price = new Label("$" + product.getPrice() * cartItem.getQuantity());
        price.getStyleClass().add("cart-item-price");
        price.setPrefWidth(100); // Ensure alignment with other items

        // Create the remove button
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("cart-item-remove-button");
        removeButton.setOnAction(event -> {
            // Handle item removal
            ActionResult<String> response = new CartItemManager().deleteCartItem(cartItem.getId());
            showToast(stage, response.getMessage());
        });

        // Add elements to the HBox layout
        this.getChildren().addAll(productName, quantity, price, removeButton);
    }
}