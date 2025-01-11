package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.*;
import com.example.javafxfinalproject.Managers.BrandManager;
import com.example.javafxfinalproject.Managers.CategoryManager;
import com.example.javafxfinalproject.Managers.PhotoManager;
import com.example.javafxfinalproject.Managers.ProductManager;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Brand;
import com.example.javafxfinalproject.Models.Category;
import com.example.javafxfinalproject.Models.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.javafxfinalproject.Components.Toast.showToast;

public class AddNewProductStage extends Stage {
    private CustomComboBox brandIdField = new CustomComboBox();
    private CustomComboBox categoryIdField = new CustomComboBox();
    final private FormField productNameField = new FormField("Product Name", "Yamaha R1");
    final private FormField productDescriptionField = new FormField("Product Description", "1000cc Bike");
    final private FormField productPriceField = new FormField("Product Price", "10,000");
    final private FormField productStockField = new FormField("Product Stock", "8");
    final private FormField productUrlField = new FormField("Product URL", "https://example_url.com");
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

        // getting data for brandID
        ArrayList<Brand> brands = new BrandManager().getBrands();
        ArrayList<String> brandNames = new ArrayList<>();

        for(Brand brand : brands) {
            brandNames.add(brand.getName());
        }

        brandIdField = new CustomComboBox("Brand", brandNames);

        // getting data for categoryID
        ArrayList<Category> categories = new CategoryManager().getCategories();
        ArrayList<String> categoryNames = new ArrayList<>();

        for(Category category : categories) {
            categoryNames.add(category.getName());
        }

        categoryIdField = new CustomComboBox("Category", categoryNames);

        formContainer.getChildren().addAll(brandIdField, categoryIdField, productNameField, productDescriptionField, productPriceField, productStockField, productUrlField);

        // buttons container
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER);

        PrimaryButton backBtn = new PrimaryButton("Back");
        PrimaryButton addBtn = new PrimaryButton("Add");

        backBtn.setOnAction(e -> { adminStage.setAddNewProductStageOpen(false); this.close(); });
        addBtn.setOnAction(e -> addProductButtonAction(adminStage, brands, categories));

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

    private void addProductButtonAction(AdminStage adminStage, ArrayList<Brand> brands, ArrayList<Category> categories) {
        if (brandIdField.getSelectedItem().isEmpty() ||
            categoryIdField.getSelectedItem().isEmpty() ||
            productNameField.getInputText().isEmpty() ||
            productDescriptionField.getInputText().isEmpty() ||
            productPriceField.getInputText().isEmpty() ||
            productStockField.getInputText().isEmpty()) {
            messageLabel.setText("Fields shouldn't be empty!");
            messageLabel.setTextFill(Color.RED);
            messageLabel.playAnimation();
        } else {
            // getting brand id
            int brandId = Objects.requireNonNull(brands.stream().filter(brand -> {
                return brand.getName().equalsIgnoreCase(brandIdField.getSelectedItem());
            }).findFirst().orElse(null)).getId();

            // getting category id
            int categoryId = Objects.requireNonNull(categories.stream().filter(category -> {
                return category.getName().equalsIgnoreCase(categoryIdField.getSelectedItem());
            }).findFirst().orElse(null)).getId();

            Product productToBeAdded = new Product(0, brandId, categoryId, productNameField.getInputText(), productDescriptionField.getInputText(), Double.parseDouble(productPriceField.getInputText()), Integer.parseInt(productStockField.getInputText()));
            ActionResult<Integer> response =  new ProductManager().addProduct(productToBeAdded);
            new PhotoManager().addPhoto(productUrlField.getInputText(), response.getData());
            showToast(this , response.getMessage());

            adminStage.setAddNewProductStageOpen(false);
            this.close();
        }
    }
}
