package com.example.javafxfinalproject.Components;


import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ProductCards extends GridPane {

    public ProductCards(List<Card> cards) {
        // Set the grid layout properties
        this.setHgap(20); // Horizontal gap between cards
        this.setVgap(20); // Vertical gap between cards
        this.setPrefWidth(800); // Optional: Set a preferred width for the grid

        int row = 0;
        int column = 0;

        // Loop through the list of Card objects
        for (Card card : cards) {
            // Add the Card to the GridPane at the appropriate row and column
            this.add(card, column, row);

            // Increment the column for the next card
            column++;

            // If the column exceeds a certain number (e.g., 3), move to the next row
            if (column > 2) {
                column = 0; // Reset the column to 0
                row++; // Move to the next row
            }
        }
    }
}
