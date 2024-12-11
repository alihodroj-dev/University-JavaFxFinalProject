package com.example.javafxfinalproject;

import com.example.javafxfinalproject.Helpers.RememberMeHelper;
import com.example.javafxfinalproject.Managers.UserManager;
import com.example.javafxfinalproject.Models.User;
import com.example.javafxfinalproject.Stages.AdminStage;
import com.example.javafxfinalproject.Stages.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MotoCenterApp extends Application {


    @Override
    public void start(Stage stage) throws IOException {

        List<String> credentials = RememberMeHelper.readCredentials();
        if(!credentials.isEmpty()) {
                User user = new UserManager().getUserById(Integer.parseInt(credentials.getFirst()));
                if(user != null) {
                    if(credentials.getLast().equalsIgnoreCase("admin")) {
                        AdminStage adminStage = new AdminStage(user);
                    }
                    else {
                        // customer stage here
                    }
                }
        }

        // MARK: login
        LoginStage loginStage = new LoginStage();

        // MARK: admin
//        AdminStage adminStage = new AdminStage(new User());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
