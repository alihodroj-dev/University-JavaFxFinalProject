package com.example.javafxfinalproject;

import com.example.javafxfinalproject.Stages.AdminStage;
import com.example.javafxfinalproject.Stages.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class MotoCenterApp extends Application {


    @Override
    public void start(Stage stage) {

        // MARK: login
//        LoginStage loginStage = new LoginStage();

        // MARK: admin
        AdminStage adminStage = new AdminStage();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
