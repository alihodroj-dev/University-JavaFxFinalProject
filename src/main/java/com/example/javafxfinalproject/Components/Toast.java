package com.example.javafxfinalproject.Components;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Toast {

    public static void showToast(Stage stage, String message) {
        // Create Popup for showing the toast
        Popup popup = new Popup();

        // Create label for the toast with custom styling
        Label toastLabel = new Label(message);
        toastLabel.setStyle("-fx-background-color: #333; -fx-text-fill: white; "
                + "-fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        toastLabel.setOpacity(0);


        popup.getContent().add(toastLabel); // Add the label to the popup

        // Set the position of the toast on the bottom-right of the screen
        popup.setX((stage.getWidth()/2) + (toastLabel.getWidth()/2));
        popup.setY(stage.getHeight() - 20);

        popup.show(stage);

        // Set up fade-in and fade-out animations for the toast
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), toastLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), toastLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeIn.setOnFinished(e -> {
            // Trigger fade-out after the duration
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                fadeOut.play();
            }).start();
        });

        fadeOut.setOnFinished(e -> popup.hide());

        fadeIn.play(); // Start the fade-in animation
    }
}