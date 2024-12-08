package com.example.javafxfinalproject.Components;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class SecureFormField extends VBox {

    private final PasswordField inputField = new PasswordField();

    public SecureFormField(String labelText, String placeholder) {
        // Create and style the label
        Label label = new Label(labelText);
        label.getStyleClass().add("form-label");

        // Create and style the input field
        inputField.setPromptText(placeholder);
        inputField.getStyleClass().add("input-field");

        // Apply spacing
        this.setSpacing(10);

        // Add components to the container
        this.getChildren().addAll(label, inputField);
    }

    // Getter for the input field text
    public String getInputText() {
        return inputField.getText();
    }

}
