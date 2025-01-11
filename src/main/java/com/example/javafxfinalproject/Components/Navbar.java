package com.example.javafxfinalproject.Components;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Navbar extends HBox {

    public Navbar(String... links) {
        // Apply the navbar style
        this.getStyleClass().add("navbar");

        // Spacing between items
        this.setSpacing(20);

        // Create each link and style it
        for (String linkText : links) {
            Label link = new Label(linkText);
            link.getStyleClass().add("navbar-link");
            this.getChildren().add(link);
        }
    }
}
