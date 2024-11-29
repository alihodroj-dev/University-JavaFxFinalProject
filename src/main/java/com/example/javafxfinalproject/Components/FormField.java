package com.example.javafxfinalproject.Components;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FormField extends VBox {

    private TextField inputField;
    private CheckBox checkBox;

    public FormField(String labelText, String placeholder) {
        // Apply spacing
        this.setSpacing(10);

        // Create and style the label
        Label label = new Label(labelText);
        label.getStyleClass().add("form-label");

        // Create and style the input field
        inputField = new TextField();
        inputField.setPromptText(placeholder);
        inputField.getStyleClass().add("input-field");


        // Add components to the container
        this.getChildren().addAll(label, inputField);
    }

    // Getter for the input field text
    public String getInputText() {
        return inputField.getText();
    }

}
