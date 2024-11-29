package com.example.javafxfinalproject.Components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Alert extends HBox {

    public Alert(String message, String alertType) {
        // Apply the alert style
        this.getStyleClass().add(alertType);

        // Create the alert message label
        Label alertMessage = new Label(message);

        // Add the message to the alert container
        this.getChildren().add(alertMessage);
    }
}

