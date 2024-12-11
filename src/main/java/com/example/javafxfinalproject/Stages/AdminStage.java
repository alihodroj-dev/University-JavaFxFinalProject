package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.FormField;
import com.example.javafxfinalproject.Components.PrimaryButton;
import com.example.javafxfinalproject.Components.TableViewCell;
import com.example.javafxfinalproject.Components.Toast;
import com.example.javafxfinalproject.Helpers.RememberMeHelper;
import com.example.javafxfinalproject.Managers.OrderManager;
import com.example.javafxfinalproject.Managers.ProductManager;
import com.example.javafxfinalproject.Managers.UserManager;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Order;
import com.example.javafxfinalproject.Models.Product;
import com.example.javafxfinalproject.Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.javafxfinalproject.Components.Toast.showToast;

public class AdminStage extends Stage {
    private boolean isSettingsStageOpen = false;
    private boolean isAddNewProductStageOpen = false;
    public AdminStage(User userAccount) {
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

        // customers button
        PrimaryButton customersBtn = new PrimaryButton("Customers");
        customersBtn.setPrefWidth(220);
        customersBtn.getStyleClass().add("sidebar-button");
        customersBtn.setOnAction(e -> {
            mainContainer.getChildren().remove(1);
            ScrollPane datatable = customersDataTableView(new UserManager().getUsers(), mainContainer);
            datatable.setPadding(new Insets(30));
            mainContainer.getChildren().add(datatable);
            this.setTitle("MotoCenter Dealership - Dashboard - Customers");
        });

        // products button
        PrimaryButton productsBtn = new PrimaryButton("Products");
        productsBtn.setPrefWidth(220);
        productsBtn.getStyleClass().add("sidebar-button");
        productsBtn.setOnAction(e -> {
            mainContainer.getChildren().remove(1);
            ScrollPane datatable = productsDataTableView(new ProductManager().getProducts(0), mainContainer);
            datatable.setPadding(new Insets(30));
            mainContainer.getChildren().add(datatable);
            this.setTitle("MotoCenter Dealership - Dashboard - Products");
        });

        // orders button
        PrimaryButton ordersBtn = new PrimaryButton("Orders");
        ordersBtn.setPrefWidth(220);
        ordersBtn.getStyleClass().add("sidebar-button");
        ordersBtn.setOnAction(e -> {
            mainContainer.getChildren().remove(1);
            ScrollPane datatable = ordersDataTableView(new OrderManager().getOrders(), mainContainer);
            datatable.setPadding(new Insets(30));
            mainContainer.getChildren().add(datatable);
            this.setTitle("MotoCenter Dealership - Dashboard - Orders");
        });

        // add a new product button
        PrimaryButton addNewProductButton = new PrimaryButton("Add a product");
        addNewProductButton.setPrefWidth(220);
        addNewProductButton.getStyleClass().add("sidebar-button");
        addNewProductButton.setOnAction(e -> {
            if(!isAddNewProductStageOpen) {
                AddNewProductStage addNewProductStage = new AddNewProductStage(this);
                this.isAddNewProductStageOpen = true;
            }
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
        buttonsContainer.getChildren().addAll(customersBtn, productsBtn, ordersBtn, addNewProductButton, logoutBtn);

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

        ScrollPane datatable = this.customersDataTableView(new UserManager().getUsers(), mainContainer);
        datatable.setPadding(new Insets(30));

        mainContainer.getChildren().addAll(sideBar, datatable);

        Scene mainScene = new Scene(mainContainer);
        mainScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        this.setTitle("MotoCenter Dealership - Dashboard - Customers");
        this.setScene(mainScene);
        this.setResizable(false);
        this.show();
    }

    private ScrollPane customersDataTableView(ArrayList<User> users, HBox parentContainer) {
        VBox subContainer = new VBox(0);

        for(User user : users) {
            HBox userContainer = new HBox(20);
            // id
            TableViewCell id = new TableViewCell(users.indexOf(user) == 0 ? "ID" : "", "" + user.getId());
            // email
            TableViewCell email = new TableViewCell(users.indexOf(user) == 0 ? "EMAIL" : "", user.getEmail());
            // firstName
            TableViewCell fname = new TableViewCell(users.indexOf(user) == 0 ? "FIRST NAME" : "", user.getFirstName());
            // last name
            TableViewCell lname = new TableViewCell(users.indexOf(user) == 0 ? "LAST NAME" : "", (user.getLastName()));
            // phone number
            TableViewCell phoneNumber = new TableViewCell(users.indexOf(user) == 0 ? "PHONE NUMBER" : "", user.getPhoneNumber());

            // save and delete container
            HBox saveDeleteContainer = new HBox(15);
            saveDeleteContainer.setAlignment(Pos.BOTTOM_CENTER);
            saveDeleteContainer.setPadding(new Insets(0, 0, 4, 0));

            // save icon
            ImageView saveBtn = new ImageView("save.png");
            saveBtn.setFitWidth(25);
            saveBtn.setFitHeight(25);

            saveBtn.setOnMouseClicked(e -> {
                User userToBeSaved = new User(Integer.parseInt(id.getInputText()), fname.getInputText(), lname.getInputText(), "", email.getInputText(), phoneNumber.getInputText(), "");
                ActionResult<String> response = new UserManager().updateUser(userToBeSaved);
                showToast(this , response.getMessage());
            });

            ImageView deleteBtn = new ImageView("delete.png");
            deleteBtn.setFitWidth(25);
            deleteBtn.setFitHeight(25);

            deleteBtn.setOnMousePressed(e -> {
                new UserManager().deleteUser(Integer.parseInt(id.getInputText()));
                parentContainer.getChildren().remove(1);
                ScrollPane datatable = customersDataTableView(new UserManager().getUsers(), parentContainer);
                datatable.setPadding(new Insets(30));
                parentContainer.getChildren().add(datatable);
            });

            saveDeleteContainer.getChildren().addAll(saveBtn, deleteBtn);


            userContainer.getChildren().addAll(id, email, fname, lname, phoneNumber, saveDeleteContainer);
            subContainer.getChildren().add(userContainer);
        }

        ScrollPane mainContainer = new ScrollPane(subContainer);

        return mainContainer;
    }

    private ScrollPane productsDataTableView(ArrayList<Product> products, HBox parentContainer) {
        VBox subContainer = new VBox(0);

        for(Product product : products) {
            HBox productContainer = new HBox(20);
            // id
            TableViewCell id = new TableViewCell(products.indexOf(product) == 0 ? "ID" : "", "" + product.getId());
            // brandId
            TableViewCell brandId = new TableViewCell(products.indexOf(product) == 0 ? "BrandID" : "", "" + product.getBrandId());
            // categoryId
            TableViewCell categoryId = new TableViewCell(products.indexOf(product) == 0 ? "CategoryID" : "", "" + product.getCategoryId());
            // name
            TableViewCell name = new TableViewCell(products.indexOf(product) == 0 ? "NAME" : "", product.getName());
            // description
            TableViewCell description = new TableViewCell(products.indexOf(product) == 0 ? "DESCRIPTION" : "", product.getDescription());
            // price
            TableViewCell price = new TableViewCell(products.indexOf(product) == 0 ? "PRICE" : "", "" + product.getPrice());
            // stock
            TableViewCell stock = new TableViewCell(products.indexOf(product) == 0 ? "STOCK" : "", "" + product.getStock());

            // save and delete container
            HBox saveDeleteContainer = new HBox(15);
            saveDeleteContainer.setAlignment(Pos.BOTTOM_CENTER);
            saveDeleteContainer.setPadding(new Insets(0, 0, 4, 0));

            // save icon
            ImageView saveBtn = new ImageView("save.png");
            saveBtn.setFitWidth(25);
            saveBtn.setFitHeight(25);

            saveBtn.setOnMouseClicked(e -> {
                Product productToBeSaved = new Product(Integer.parseInt(id.getInputText()), Integer.parseInt(brandId.getInputText()), Integer.parseInt(categoryId.getInputText()), name.getInputText(), description.getInputText(), Double.parseDouble(price.getInputText()), Integer.parseInt(stock.getInputText()));
                ActionResult<String> response =  new ProductManager().updateProduct(productToBeSaved);
                showToast(this , response.getMessage());
            });

            ImageView deleteBtn = new ImageView("delete.png");
            deleteBtn.setFitWidth(25);
            deleteBtn.setFitHeight(25);

            deleteBtn.setOnMousePressed(e -> {
                new ProductManager().deleteProduct(Integer.parseInt(id.getInputText()));
                parentContainer.getChildren().remove(1);
                ScrollPane datatable = productsDataTableView(new ProductManager().getProducts(0), parentContainer);
                datatable.setPadding(new Insets(30));
                parentContainer.getChildren().add(datatable);

            });

            saveDeleteContainer.getChildren().addAll(saveBtn, deleteBtn);


            productContainer.getChildren().addAll(id, brandId, categoryId, name, description, price, stock, saveDeleteContainer);
            subContainer.getChildren().add(productContainer);
        }

        ScrollPane mainContainer = new ScrollPane(subContainer);
        mainContainer.getStyleClass().add("scroll-pane");
        return mainContainer;
    }

    private ScrollPane ordersDataTableView(ArrayList<Order> orders, HBox parentContainer) {
        VBox subContainer = new VBox(0);

        for(Order order : orders) {
            HBox orderContainer = new HBox(20);
            // id
            TableViewCell id = new TableViewCell(orders.indexOf(order) == 0 ? "ID" : "", "" + order.getId());
            // userId
            TableViewCell userId = new TableViewCell(orders.indexOf(order) == 0 ? "userId" : "", "" + order.getUserId());
            // addressId
            TableViewCell addressId = new TableViewCell(orders.indexOf(order) == 0 ? "addressId" : "", "" + order.getAddressId());
            // totalAmount
            TableViewCell totalAmount = new TableViewCell(orders.indexOf(order) == 0 ? "totalAmount" : "", "" + order.getTotalAmount());
            // status
            TableViewCell status = new TableViewCell(orders.indexOf(order) == 0 ? "status" : "", order.getStatus());
            // isDiscounted
            TableViewCell isDiscounted = new TableViewCell(orders.indexOf(order) == 0 ? "isDiscounted" : "", "" + order.isDiscounted());
            // discountId
            TableViewCell discountId = new TableViewCell(orders.indexOf(order) == 0 ? "discountId" : "", (order.isDiscounted() ? ("" + order.getDiscountId()) : "N/A"));

            // save and delete container
            HBox saveDeleteContainer = new HBox(15);
            saveDeleteContainer.setAlignment(Pos.BOTTOM_CENTER);
            saveDeleteContainer.setPadding(new Insets(0, 0, 4, 0));

            // save icon
            ImageView saveBtn = new ImageView("save.png");
            saveBtn.setFitWidth(25);
            saveBtn.setFitHeight(25);

            saveBtn.setOnMouseClicked(e -> {
                Order orderToBeSaved = new Order(Integer.parseInt(id.getInputText()), Integer.parseInt(userId.getInputText()), Integer.parseInt(addressId.getInputText()), Integer.parseInt(totalAmount.getInputText()), status.getInputText(), Boolean.parseBoolean(isDiscounted.getInputText()), Integer.parseInt(discountId.getInputText()));
                ActionResult<String> response = new OrderManager().updateOrder(orderToBeSaved);
                showToast(this , response.getMessage());
            });

            ImageView deleteBtn = new ImageView("delete.png");
            deleteBtn.setFitWidth(25);
            deleteBtn.setFitHeight(25);

            deleteBtn.setOnMousePressed(e -> {
                new OrderManager().deleteOrder(Integer.parseInt(id.getInputText()));
                parentContainer.getChildren().remove(1);
                ScrollPane datatable = ordersDataTableView(new OrderManager().getOrders(), parentContainer);
                datatable.setPadding(new Insets(30));
                parentContainer.getChildren().add(datatable);
            });

            saveDeleteContainer.getChildren().addAll(saveBtn, deleteBtn);


            orderContainer.getChildren().addAll(id, userId, addressId, totalAmount, status, isDiscounted, discountId, saveDeleteContainer);
            subContainer.getChildren().add(orderContainer);
        }

        ScrollPane mainContainer = new ScrollPane(subContainer);

        return mainContainer;
    }

    public void setSettingsStageOpen(boolean settingsStageOpen) {
        isSettingsStageOpen = settingsStageOpen;
    }

    public void setAddNewProductStageOpen(boolean addNewProductStageOpen) {
        isAddNewProductStageOpen = addNewProductStageOpen;
    }
}
