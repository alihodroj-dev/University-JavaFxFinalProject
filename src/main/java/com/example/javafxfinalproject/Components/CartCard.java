package com.example.javafxfinalproject.Components;

import com.example.javafxfinalproject.Managers.CartItemManager;
import com.example.javafxfinalproject.Managers.ProductManager;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.CartItem;
import com.example.javafxfinalproject.Models.Product;
import com.example.javafxfinalproject.Stages.CustomerStage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import static com.example.javafxfinalproject.Components.Toast.showToast;

public class CartCard extends BorderPane {

    private final CartItem cartItem;

    public CartCard(CustomerStage stage, CartItem cartItem) {
        this.cartItem = cartItem;
        Product product = new ProductManager().getProductById(cartItem.getProductId());

        // Use CSS class "card" for the overall layout
        this.getStyleClass().add("card");
        this.setPadding(new Insets(10)); // Add padding for spacing

        // Create product name label
        Label productName = new Label(product.getName());
        productName.getStyleClass().add("cart-item-name");
        productName.setPrefWidth(300); // Consistent width for all names

        // Create quantity label
        Label quantity = new Label("Quantity: " + cartItem.getQuantity());
        quantity.getStyleClass().add("cart-item-quantity");
        quantity.setPrefWidth(200); // Fixed width for consistent layout

        // Create price label
        Label price = new Label("$" + product.getPrice() * cartItem.getQuantity());
        price.getStyleClass().add("cart-item-price");
        price.setPrefWidth(200); // Ensure alignment with other items

        // Create the remove button
        Button removeButton = new Button("Remove");
        removeButton.getStyleClass().add("cart-item-remove-button");
        removeButton.setOnAction(event -> {
            // Handle item removal
            ActionResult<String> response = new CartItemManager().deleteCartItem(cartItem.getId());
            showToast(stage, response.getMessage());
        });

        // Add elements to the HBox layout
        HBox leftSideWrapper = new HBox(10);
        leftSideWrapper.getChildren().addAll(productName, quantity, price);

        this.setLeft(leftSideWrapper);
        this.setRight(removeButton);

        this.setPrefWidth(900);
    }
}