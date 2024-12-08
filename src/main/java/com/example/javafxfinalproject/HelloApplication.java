package com.example.javafxfinalproject;

import com.example.javafxfinalproject.Components.FormField;
import com.example.javafxfinalproject.Components.PrimaryButton;
import com.example.javafxfinalproject.Models.ActionResult;
import com.example.javafxfinalproject.Models.Managers.AuthManager;
import com.example.javafxfinalproject.Models.Status;
import com.example.javafxfinalproject.Models.User;
import com.example.javafxfinalproject.Stages.LoginStage;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) {

        // MARK: CODE UPDATE - HODROJ
        LoginStage loginStage = new LoginStage();

//        AuthManager authManager = new AuthManager();
//        FormField usernameField = new FormField("Username", "Enter your username");
//        FormField passwordField = new FormField("Password", "Enter your password");
//        PrimaryButton loginButton = new PrimaryButton("Login");
//        Label errorLabel = new Label();
//        errorLabel.setTextFill(Color.RED);
//
//        // Login logic
//        loginButton.setOnAction(e -> {
//            String username = usernameField.getInputText();
//            String password = passwordField.getInputText();
//            ActionResult<User> login_user = authManager.login(username, password);
//            if (login_user.getStatus() == Status.SUCCESS) {
//                User loggedInUser = login_user.getData();
//                routeToMainStageAsUser(stage ,loggedInUser);
//            } else {
//                errorLabel.setText(login_user.getMessage());
//            }
//        });
//
//        VBox fieldsBox = new VBox();
//        fieldsBox.setSpacing(20);
//        fieldsBox.getChildren().addAll(usernameField, passwordField, errorLabel);
//
//        // Switch to register label
//        Label switchToRegister = new Label("New User? Register now.");
//        switchToRegister.setTextFill(Color.BLUE);
//        switchToRegister.setUnderline(true);
//        switchToRegister.setOnMouseClicked(e -> routeToRegisterStage(stage));
//
//        VBox loginBox = new VBox();
//        loginBox.setSpacing(30);
//        loginBox.setPadding(new Insets(100, 100, 100, 100));
//        loginBox.getChildren().addAll(fieldsBox, loginButton, switchToRegister);
//        loginBox.setAlignment(Pos.CENTER);
//
//        Scene loginScene = new Scene(loginBox);
//        loginScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
//        stage.setTitle("Login");
//        stage.setScene(loginScene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void routeToMainStageAsUser(Stage stage , User loggedInUser) {
        // Set up the dashboard scene
        VBox userDetailsBox = new VBox();
        userDetailsBox.setPadding(new Insets(20));
        userDetailsBox.getChildren().add(new Label("Welcome, " + loggedInUser.getEmail()));

        Scene mainScene = new Scene(userDetailsBox, 300, 200);
        mainScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));

        Stage newStage = new Stage();
        newStage.setScene(mainScene);
        stage.close();
        newStage.show();
    }

    // Switch to the registration scene (on click)
    private void routeToRegisterStage(Stage stage) {
        Stage newStage = new Stage();
        // Create form fields for registration
        FormField usernameField = new FormField("Username", "Enter your username");
        FormField emailField = new FormField("Email", "Enter your email");
        FormField phoneField = new FormField("Phone", "Enter your phone number");
        FormField passwordField = new FormField("Password", "Enter your password");
        PrimaryButton registerButton = new PrimaryButton("Register");

        Label registerErrorLabel = new Label();
        registerErrorLabel.setTextFill(Color.RED);
        VBox registerForm = new VBox();
        registerForm.setSpacing(20);
        registerForm.getChildren().addAll(usernameField, emailField, phoneField, passwordField, registerButton, registerErrorLabel);
        registerForm.setPadding(new Insets(100, 100, 100, 100));

        Scene registerScene = new Scene(registerForm);
        registerScene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        newStage.setScene(registerScene);
        newStage.setTitle("Register");
        stage.close();
        newStage.show();
        registerButton.setOnAction(e -> {
            String username = usernameField.getInputText();
            String email = emailField.getInputText();
            String phone = phoneField.getInputText();
            String password = passwordField.getInputText();

            // Call the register method from AuthManager
            AuthManager authManager = new AuthManager();
            ActionResult<User> registerResult = authManager.register(username, email, phone, password);

            if (registerResult.getStatus() == Status.SUCCESS) {
                // Successfully registered, route to user dashboard
                User registeredUser = registerResult.getData();
                routeToMainStageAsUser(newStage , registeredUser);
            } else {
                // Show error message
                registerErrorLabel.setText(registerResult.getMessage());
            }
        });

        // Create the registration form layout

    }
}
