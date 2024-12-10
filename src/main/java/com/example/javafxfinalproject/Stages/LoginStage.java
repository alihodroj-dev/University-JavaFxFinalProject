package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.FormField;
import com.example.javafxfinalproject.Components.PrimaryButton;
import com.example.javafxfinalproject.Components.SecureFormField;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Managers.AuthManager;
import com.example.javafxfinalproject.Models.Status;
import com.example.javafxfinalproject.Models.User;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginStage extends Stage {
    // view elements
    final private FormField emailField = new FormField("Email", "example@example.com");
    final private SecureFormField passwordField = new SecureFormField("Password", "********");
    final private Label messageLabel = new Label("");
    public LoginStage() {
        // window properties
        final int mainWidth = 700;
        final int mainHeight = 500;

        // main container
        HBox mainContainer = new HBox(0);
        mainContainer.setPrefWidth(mainWidth);
        mainContainer.setPrefHeight(mainHeight);

        // left side cover image
        ImageView coverImage = new ImageView("loginStageCoverImage.jpeg");
        coverImage.setFitWidth((double) mainWidth / 2);
        coverImage.setFitHeight(mainHeight);

        // right side container
        VBox rightSide = new VBox(40);
        rightSide.setPrefWidth((double) mainWidth / 2);
        rightSide.setPrefHeight(mainHeight);
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setStyle("-fx-background-color: #121212;");

        // heading
        Label heading = new Label("Login");
        heading.getStyleClass().add("heading");

        // email field
        this.emailField.setPadding(new Insets(0, 60, 0, 60));
        // password field
        passwordField.setPadding(new Insets(10, 60, 0, 60));

        // form container
        VBox fields = new VBox(10);
        fields.getChildren().addAll(emailField, passwordField);

        // buttons container
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER);

        // creating buttons
        PrimaryButton submitBtn = new PrimaryButton("Submit");
        PrimaryButton registerBtn = new PrimaryButton("Sign Up");

        // setting button actions
        submitBtn.setOnAction(e -> submitButtonAction());

        // adding buttons to parent container
        buttons.getChildren().addAll(submitBtn, registerBtn);

        // adding elements to right container
        rightSide.getChildren().addAll(heading, fields, buttons, messageLabel);

        // adding left and right side to main container
        mainContainer.getChildren().addAll(coverImage, rightSide);

        Scene mainScene = new Scene(mainContainer);
        mainScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        this.setTitle("MotoCenter Dealership - Login");
        this.setScene(mainScene);
        this.setResizable(false);
        this.show();
    }

    private void submitButtonAction() {
        if(emailField.getInputText().isEmpty() || passwordField.getInputText().isEmpty()) {
            // display error message
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Fields should not be empty!");
            // fade in animation
            FadeTransition tIn = new FadeTransition(Duration.millis(500), messageLabel);
            tIn.setFromValue(0);
            tIn.setToValue(1);
            tIn.play();
            // fade out animation
            FadeTransition tOut = new FadeTransition(Duration.millis(500), messageLabel);
            tOut.setFromValue(1);
            tOut.setToValue(0);
            tOut.setDelay(Duration.millis(2000));
            tOut.play();
        }
        else {
            try
            {
                AuthManager auth = new AuthManager();
                String email = emailField.getInputText();
                String password = passwordField.getInputText();

                ActionResult<User> response = auth.login(email , password);

                if(response.getStatus() == Status.ERROR) {
                    // message label here
                }
                else {
                    // navigate to user stage
                }
            }
            catch (Exception e)
            {

            }
        }
    }
}
