package com.example.javafxfinalproject;

import com.example.javafxfinalproject.Stages.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) {
        LoginStage loginStage = new LoginStage();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
