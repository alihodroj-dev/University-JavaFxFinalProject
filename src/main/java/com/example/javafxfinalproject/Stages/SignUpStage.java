package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.FormField;
import com.example.javafxfinalproject.Components.MessageLabel;
import com.example.javafxfinalproject.Components.PrimaryButton;
import com.example.javafxfinalproject.Components.SecureFormField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SignUpStage extends Stage {
    final private FormField firstNameField = new FormField("First Name", "Joseph");
    final private FormField lastNameField = new FormField("Last Name", "Gemayel");
    final private FormField emailField = new FormField("Email", "example@example.com");
    final private SecureFormField passwordField = new SecureFormField("Password", "********");
    final private SecureFormField confirmPasswordField = new SecureFormField("Password", "********");
    final private MessageLabel messageLabel = new MessageLabel("");
    public SignUpStage(LoginStage loginStage) {
        // window properties
        final int mainWidth = 400;
        final int mainHeight = 650;

        // main container
        VBox mainContainer = new VBox(40);
        mainContainer.setPrefWidth(mainWidth);
        mainContainer.setPrefHeight(mainHeight);
        mainContainer.setAlignment(Pos.CENTER);

        // heading
        Label heading = new Label("Sign Up");
        heading.getStyleClass().add("heading");

        // form container
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(0, 50, 0, 50));
        formContainer.getChildren().addAll(firstNameField, lastNameField, emailField, passwordField, confirmPasswordField);

        // buttons container
        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER);

        PrimaryButton backBtn = new PrimaryButton("Back");
        PrimaryButton createBtn = new PrimaryButton("Create");

        backBtn.setOnAction(e -> backButtonAction(loginStage));
        createBtn.setOnAction(e -> createButtonAction());

        buttonsContainer.getChildren().addAll(backBtn, createBtn);

        // adding children to main container
        mainContainer.getChildren().addAll(heading, formContainer, buttonsContainer, messageLabel);

        Scene mainScene = new Scene(mainContainer);
        mainScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        this.setTitle("MotoCenter Dealership - Sign Up");
        this.setScene(mainScene);
        this.setResizable(false);
        this.setOnCloseRequest(e -> { loginStage.setSignUpStageOpen(false);});
        this.show();
    }

    private void createButtonAction() {
        if(emailField.getInputText().isEmpty() || passwordField.getInputText().isEmpty()) {
            // display error message
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Fields should not be empty!");
            messageLabel.playAnimation();
        } else {
            // try catch block with error handling
        }
    }

    private void backButtonAction(LoginStage loginStage) {
        loginStage.setSignUpStageOpen(false);
        this.close();
    }
}