package com.example.javafxfinalproject.Components;
import com.example.javafxfinalproject.Managers.CartItemManager;
import com.example.javafxfinalproject.Managers.PhotoManager;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Photo;
import com.example.javafxfinalproject.Models.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static com.example.javafxfinalproject.Components.Toast.showToast;

public class ProductCard extends BorderPane {

    private Product product;
    private Photo photo;


    public ProductCard(Stage stage, Product product , double width , int cartId) {
        this.product = product;
        this.setPrefWidth(width);
        photo = new PhotoManager().getPhotoByProductId(product.getId());

        // Apply card style
        this.getStyleClass().add("card");

        // Create the image view
        ImageView productImage = new ImageView(new Image(photo.getUrl()));
        productImage.setFitWidth(200);
        productImage.setFitHeight(150);
        productImage.setPreserveRatio(true);

        // Product title
        Label productTitle = new Label(product.getName());
        productTitle.getStyleClass().add("card-header");

        // Product description
        Label productDescription = new Label(product.getDescription());
        productDescription.setWrapText(true);
        productDescription.getStyleClass().add("card-description");
        productDescription.setWrapText(true);
        productDescription.setTextAlignment(TextAlignment.CENTER);// Allow multiline descriptions

        // Product price
        Label productPrice = new Label("$" + product.getPrice());
        productPrice.getStyleClass().add("card-price");

        // Add to cart button
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.getStyleClass().add("card-add-to-cart-button");
        addToCartButton.setOnAction(e -> {
            // Handle add to cart functionality here
            ActionResult<String> response = new CartItemManager().addCartItem(cartId , product.getId() , 1);
            showToast(stage , response.getMessage());
        });

        // Layout for the button
        HBox buttonBox = new HBox(addToCartButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        productDescription.setWrapText(true);
        productDescription.setTextAlignment(TextAlignment.CENTER);

        // Add components to the card
        this.setTop(productImage);
        VBox infoContainer = new VBox(productTitle, productDescription, productPrice);
        infoContainer.setAlignment(Pos.CENTER);
        infoContainer.setSpacing(10);
        this.setCenter(infoContainer);
        this.setBottom(buttonBox);
        BorderPane.setAlignment(buttonBox, Pos.CENTER);
        BorderPane.setAlignment(productImage, Pos.CENTER);
        BorderPane.setMargin(infoContainer, new Insets(10, 10, 10, 10));
    }
}