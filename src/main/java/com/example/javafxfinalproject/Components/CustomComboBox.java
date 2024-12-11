package com.example.javafxfinalproject.Components;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Objects;

public class CustomComboBox extends BorderPane {
    ComboBox comboBox;

    public CustomComboBox() {}

    public CustomComboBox(String labelText, ArrayList<String> dataItems) {
        this.setPadding(new Insets(5, 0, 5, 0));
        // label
        Label label = new Label(labelText);
        label.getStyleClass().add("form-label");

        // setting data
        comboBox = new ComboBox<>(FXCollections.observableArrayList(dataItems));

        // setting prompt text
        comboBox.setPromptText("Yamaha");

        this.setLeft(label);
        this.setRight(comboBox);
    }

    public String getSelectedItem() {
        Object selectedItem = this.comboBox.getSelectionModel().getSelectedItem();
        return selectedItem == null ? "" : selectedItem.toString();
    }
}
