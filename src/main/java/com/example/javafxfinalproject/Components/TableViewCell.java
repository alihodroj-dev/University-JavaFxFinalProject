package com.example.javafxfinalproject.Components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TableViewCell extends VBox {
    private final TextField inputField = new TextField();

    public TableViewCell(String labelText, String value) {
        // Create and style the label
        Label label = new Label(labelText);
        label.getStyleClass().add("form-label");

        // Create and style the input field
        inputField.setText(value);
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
