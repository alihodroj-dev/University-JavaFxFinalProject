package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.CartCard;
import com.example.javafxfinalproject.Components.PrimaryButton;
import com.example.javafxfinalproject.Components.ProductCard;
import com.example.javafxfinalproject.Helpers.RememberMeHelper;
import com.example.javafxfinalproject.Managers.CartItemManager;
import com.example.javafxfinalproject.Managers.CartManager;
import com.example.javafxfinalproject.Managers.ProductManager;
import com.example.javafxfinalproject.Managers.UserManager;
import com.example.javafxfinalproject.Models.Cart;
import com.example.javafxfinalproject.Models.CartItem;
import com.example.javafxfinalproject.Models.Product;
import com.example.javafxfinalproject.Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CustomerStage extends Stage {
    private boolean isSettingsStageOpen = false;
    private int cartItemCount;
    private int tracker;
    public CustomerStage(User userAccount) {
        // window properties
        final int mainWidth = 1400;
        final int mainHeight = 800;

        // main container
        HBox mainContainer = new HBox(0);
        mainContainer.setPrefWidth(mainWidth);
        mainContainer.setPrefHeight(mainHeight);

        // MARK: SIDEBAR
        // sidebar container
        BorderPane sideBar = new BorderPane();
        sideBar.setPrefWidth(240);
        sideBar.setPrefHeight(800);
        sideBar.getStyleClass().add("sidebar");

        // buttons container
        VBox buttonsContainer = new VBox(10);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setPadding(new Insets(10, 0, 10, 0));

        // shop button
        PrimaryButton shopBtn = new PrimaryButton("Browse Shop");
        shopBtn.setPrefWidth(220);
        shopBtn.getStyleClass().add("sidebar-button");
        shopBtn.setOnAction(e -> {
            mainContainer.getChildren().remove(1);
            mainContainer.getChildren().add(shopView(new UserManager().getUserById(userAccount.getId())));
            this.setTitle("MotoCenter Dealership - App - Shop");
        });

        // cart button
        cartItemCount = new CartItemManager().getCount(new CartManager().getCartByUserId(userAccount.getId()).getId());

        PrimaryButton cartBtn = new PrimaryButton("View Cart" + " - " + cartItemCount);
        cartBtn.prefWidth(220);
        cartBtn.getStyleClass().add("sidebar-button");
        cartBtn.setOnAction(e -> {
            mainContainer.getChildren().remove(1);
            mainContainer.getChildren().add(cartView(new UserManager().getUserById(userAccount.getId())));
            this.setTitle("MotoCenter Dealership - App - Cart");
        });

        // logout button
        PrimaryButton logoutBtn = new PrimaryButton("Logout");
        logoutBtn.setPrefWidth(220);
        logoutBtn.getStyleClass().add("sidebar-button");
        logoutBtn.setOnAction(e -> {
            try {
                RememberMeHelper.clearCredentials();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.close();
            new LoginStage();
        });

        // adding buttons to container
        buttonsContainer.getChildren().addAll(shopBtn, cartBtn, logoutBtn);

        // setting top of sidebar to buttons container
        sideBar.setTop(buttonsContainer);

        // sidebar footer
        BorderPane sideBarFooter = new BorderPane();
        sideBarFooter.setPrefHeight(50);
        sideBarFooter.setBackground(Background.fill(Color.rgb(43, 43, 43)));

        // avatar and user's name
        HBox avatarNameContainer = new HBox(10);
        avatarNameContainer.setAlignment(Pos.CENTER);
        avatarNameContainer.setPadding(new Insets(0, 15, 0, 15));

        ImageView avatar = new ImageView("avatar.png");
        avatar.setFitWidth(35);
        avatar.setFitHeight(35);

        Label userFirstName = new Label("Welcome, " + userAccount.getFirstName());
        userFirstName.setStyle("-fx-font-size: 14px;" +
                "-fx-text-fill: #E0E0E0;");

        avatarNameContainer.getChildren().addAll(avatar, userFirstName);

        HBox settingIconWrapper = new HBox(0);
        settingIconWrapper.setAlignment(Pos.CENTER);
        settingIconWrapper.setPadding(new Insets(0, 10, 0, 10));

        ImageView settingsIcon = new ImageView("settings.png");
        settingsIcon.setFitWidth(25);
        settingsIcon.setFitHeight(25);
        settingsIcon.setOnMousePressed(e -> {
            if(!isSettingsStageOpen) {
                SettingsStage settingsStage = new SettingsStage(new UserManager().getUserById(userAccount.getId()), this);
                this.isSettingsStageOpen = true;
            }
        });

        settingIconWrapper.getChildren().add(settingsIcon);

        sideBarFooter.setLeft(avatarNameContainer);
        sideBarFooter.setRight(settingIconWrapper);

        // setting sidebar footer
        sideBar.setBottom(sideBarFooter);

        mainContainer.getChildren().addAll(sideBar, shopView(new UserManager().getUserById(userAccount.getId())));

        Scene mainScene = new Scene(mainContainer);
        mainScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        this.setTitle("MotoCenter Dealership - App - Shop");
        this.setScene(mainScene);
        this.setResizable(false);
        this.show();
    }

    public void setSettingsStageOpen(boolean settingsStageOpen) {
        isSettingsStageOpen = settingsStageOpen;
    }

    private BorderPane shopView(User user) {
        Integer size = new ProductManager().Size();
        double width = 0;
        tracker = 0;
        Cart userCart = new CartManager().getCartByUserId(user.getId());
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        flowPane.setPrefWrapLength(1000);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(50 , 50 , 50 , 50));
        ArrayList<Product> products = new ProductManager().getProducts(1);
        for(Product product : products) {
            ProductCard productCard = new ProductCard(this , product , this.getWidth() / ((double) products.size() / 2), userCart.getId());
            width = productCard.getWidth();
            flowPane.getChildren().add(productCard);

        }

        Button loadMoreButton = new Button("Load More");
        loadMoreButton.getStyleClass().add("button-primary");

        double finalWidth = width;

        loadMoreButton.setOnAction(e -> {
            tracker += 5;
            ArrayList<Product> newProducts = new ProductManager().getProducts(tracker);
            for(Product product : newProducts) {
                ProductCard productCard = new ProductCard(this , product , finalWidth, userCart.getId());
                flowPane.getChildren().add(productCard);
            }

        });

        ScrollPane container = new ScrollPane(flowPane);
        container.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        container.setPrefSize(1300, 800);

        // Create a BorderPane to organize the layout
        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(container); // Add the ScrollPane to the center
        mainPane.setBottom(loadMoreButton); // Place the "Load More" button at the bottom

        // Align the button in the center at the bottom
        BorderPane.setAlignment(loadMoreButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(loadMoreButton, new Insets(10));

        return mainPane;
    }

    private ScrollPane cartView(User user) {

        Cart cart = new CartManager().getCartByUserId(user.getId());
        ArrayList<CartItem> cartItems = new CartItemManager().getCartItemsByCartId(cart.getId());

        VBox listBox = new VBox(10);
        listBox.setAlignment(Pos.CENTER);

        listBox.setPadding(new Insets(50 , 50 , 50 , 50));

        for(CartItem cartItem : cartItems) {
            CartCard cartCard = new CartCard(this , cartItem);
            listBox.getChildren().add(cartCard);
        }

        ScrollPane scrollPane = new ScrollPane(listBox);

        return scrollPane;
    }
}
