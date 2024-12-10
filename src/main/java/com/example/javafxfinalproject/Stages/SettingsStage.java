package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.FormField;
import com.example.javafxfinalproject.Components.MessageLabel;
import com.example.javafxfinalproject.Components.PrimaryButton;
import com.example.javafxfinalproject.Components.SecureFormField;
import com.example.javafxfinalproject.Managers.UserManager;
import com.example.javafxfinalproject.Models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsStage extends Stage {
    final private FormField firstNameField = new FormField("First Name", "Joseph");
    final private FormField lastNameField = new FormField("Last Name", "Gemayel");
    final private FormField phoneNumberField = new FormField("Phone Number", "03 554 042");
    final private FormField emailField = new FormField("Email", "example@example.com");
    final private SecureFormField passwordField = new SecureFormField("Password", "********");
    final private MessageLabel messageLabel = new MessageLabel("");
    public SettingsStage(User userAccount) {
        // window properties
        final int mainWidth = 450;
        final int mainHeight = 700;

        // main container
        VBox mainContainer = new VBox(40);
        mainContainer.setPrefWidth(mainWidth);
        mainContainer.setPrefHeight(mainHeight);
        mainContainer.setAlignment(Pos.CENTER);

        // heading
        Label heading = new Label("Settings");
        heading.getStyleClass().add("heading");

        // form container
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(0, 50, 0, 50));
        formContainer.getChildren().addAll(firstNameField, lastNameField, phoneNumberField, emailField, passwordField);

        // buttons container
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER);

        PrimaryButton backBtn = new PrimaryButton("Back");
        PrimaryButton updateBtn = new PrimaryButton("Update");

        backBtn.setOnAction(e -> { this.close(); });
        updateBtn.setOnAction(e -> updateButtonAction(userAccount));

        buttonsContainer.getChildren().addAll(backBtn, updateBtn);

        // adding children to main container
        mainContainer.getChildren().addAll(heading, formContainer, buttonsContainer, messageLabel);

        // populating fields with old values
        firstNameField.setInputText(userAccount.getFirstName());
        lastNameField.setInputText(userAccount.getLastName());
        phoneNumberField.setInputText(userAccount.getPhoneNumber());
        emailField.setInputText(userAccount.getEmail());
        passwordField.setInputText(userAccount.getPassword());

        Scene mainScene = new Scene(mainContainer);
        mainScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        this.setTitle("MotoCenter Dealership - Settings");
        this.setScene(mainScene);
        this.setResizable(false);
        this.show();
    }

    private void updateButtonAction(User userAccount) {
        User newUser = new User(userAccount.getId(), firstNameField.getInputText(), lastNameField.getInputText(), "", emailField.getInputText(), phoneNumberField.getInputText(), userAccount.getRole());
        new UserManager().updateUser(newUser);
    }
}
