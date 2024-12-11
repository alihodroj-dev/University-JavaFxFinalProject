package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.ProductCard;
import com.example.javafxfinalproject.Managers.CartManager;
import com.example.javafxfinalproject.Managers.ProductManager;
import com.example.javafxfinalproject.Managers.UserManager;
import com.example.javafxfinalproject.Models.Cart;
import com.example.javafxfinalproject.Models.Product;
import com.example.javafxfinalproject.Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestStage extends Stage {
    private int tracker;


    public TestStage(User user) {
        Integer size = new ProductManager().Size();
        double width = 0;
        tracker = 0;
        Cart userCart = new CartManager().getCartByUserId(user.getId());
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setPrefWrapLength(1220);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(20 , 20 , 20 , 20));
        ArrayList<Product> products = new ProductManager().getProducts(1);
        for(Product product : products) {
            ProductCard productCard = new ProductCard(this , product , this.getWidth() / ((double) products.size() / 2), userCart.getId());
            width += productCard.getWidth();
            flowPane.getChildren().add(productCard);

        }
        Button loadMoreButton = new Button("Load More");
        loadMoreButton.getStyleClass().add("button-primary");
        double finalWidth = width;
        loadMoreButton.setOnAction(e -> {
            tracker +=5;
            ArrayList<Product> newProducts = new ProductManager().getProducts(tracker);
            for(Product product : newProducts) {
                ProductCard productCard = new ProductCard(this , product , finalWidth, userCart.getId());
                flowPane.getChildren().add(productCard);
            }

        });

        ScrollPane container = new ScrollPane(flowPane);
        container.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        container.setPrefSize(1440, 920);

      // Create a BorderPane to organize the layout
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(container); // Add the ScrollPane to the center
        mainPane.setBottom(loadMoreButton); // Place the "Load More" button at the bottom

       // Align the button in the center at the bottom
        BorderPane.setAlignment(loadMoreButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(loadMoreButton, new Insets(10)); // Add some padding/margin

        Scene scene = new Scene(mainPane);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        setScene(scene);
        this.show();



    }


}