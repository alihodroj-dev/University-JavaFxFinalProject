package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.FormField;
import com.example.javafxfinalproject.Components.MessageLabel;
import com.example.javafxfinalproject.Components.PrimaryButton;
import com.example.javafxfinalproject.Components.SecureFormField;
import com.example.javafxfinalproject.Managers.ProductManager;
import com.example.javafxfinalproject.Models.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddNewProductStage extends Stage {
    final private FormField brandIdField = new FormField("Brand ID", "103937");
    final private FormField categoryIdField = new FormField("Category ID", "683630");
    final private FormField productNameField = new FormField("Product Name", "Yamaha R1");
    final private FormField productDescriptionField = new FormField("Product Description", "1000cc Bike");
    final private FormField productPriceField = new FormField("Product Price", "10,000");
    final private FormField productStockField = new FormField("Product Stock", "8");
    final private MessageLabel messageLabel = new MessageLabel("");
    public AddNewProductStage(AdminStage adminStage) {
        // window properties
        final int mainWidth = 450;
        final int mainHeight = 750;

        // main container
        VBox mainContainer = new VBox(40);
        mainContainer.setPrefWidth(mainWidth);
        mainContainer.setPrefHeight(mainHeight);
        mainContainer.setAlignment(Pos.CENTER);

        // heading
        Label heading = new Label("Add New Product");
        heading.getStyleClass().add("heading");

        // form container
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(0, 50, 0, 50));
        formContainer.getChildren().addAll(brandIdField, categoryIdField, productNameField, productDescriptionField, productPriceField, productStockField);

        // buttons container
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER);

        PrimaryButton backBtn = new PrimaryButton("Back");
        PrimaryButton addBtn = new PrimaryButton("Add");

        backBtn.setOnAction(e -> { adminStage.setAddNewProductStageOpen(false); this.close(); });
        addBtn.setOnAction(e -> addProductButtonAction(adminStage));

        buttonsContainer.getChildren().addAll(backBtn, addBtn);

        // adding children to main container
        mainContainer.getChildren().addAll(heading, formContainer, buttonsContainer, messageLabel);


        Scene mainScene = new Scene(mainContainer);
        mainScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        this.setTitle("MotoCenter Dealership - Add new product");
        this.setScene(mainScene);
        this.setResizable(false);
        this.setOnCloseRequest(e -> {
            adminStage.setAddNewProductStageOpen(false);
        });
        this.show();
    }

    private void addProductButtonAction(AdminStage adminStage) {
        if (brandIdField.getInputText().isEmpty() ||
            categoryIdField.getInputText().isEmpty() ||
            productNameField.getInputText().isEmpty() ||
            productDescriptionField.getInputText().isEmpty() ||
            productPriceField.getInputText().isEmpty() ||
            productStockField.getInputText().isEmpty()) {
            messageLabel.setText("Fields shouldn't be empty!");
            messageLabel.setTextFill(Color.RED);
            messageLabel.playAnimation();
        } else {

            Product productToBeAdded = new Product(0, Integer.parseInt(brandIdField.getInputText()), Integer.parseInt(categoryIdField.getInputText()
            ), productNameField.getInputText(), productDescriptionField.getInputText(), Double.parseDouble(productPriceField.getInputText()), Integer.parseInt(productStockField.getInputText()));
            new ProductManager().addProduct(productToBeAdded);
            adminStage.setAddNewProductStageOpen(false);
            this.close();
        }
    }
}
