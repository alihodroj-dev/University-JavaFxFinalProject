package com.example.javafxfinalproject.Components;

import javafx.scene.control.Button;

public class PrimaryButton extends Button {

    public PrimaryButton(String buttonText) {
        // Set the button text
        this.setText(buttonText);

        // Apply the primary button style
        this.getStyleClass().add("button-primary");

        // Optional event listener
        this.setOnAction(event -> System.out.println(buttonText + " clicked!"));
    }
}
