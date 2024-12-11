package com.example.javafxfinalproject.Stages;

import com.example.javafxfinalproject.Components.CartCard;
import com.example.javafxfinalproject.Managers.CartItemManager;
import com.example.javafxfinalproject.Managers.CartManager;
import com.example.javafxfinalproject.Models.Cart;
import com.example.javafxfinalproject.Models.CartItem;
import com.example.javafxfinalproject.Models.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TestStage2 extends Stage {

    public TestStage2(User user) {
        Cart cart = new CartManager().getCartByUserId(user.getId());
        ArrayList<CartItem> cartItems = new CartItemManager().getCartItemsByCartId(cart.getId());

        VBox listBox = new VBox(10);


        for(CartItem cartItem : cartItems) {
            CartCard cartCard = new CartCard(this , cartItem);
            listBox.getChildren().add(cartCard);
        }
        Scene scene = new Scene(listBox);
        scene.getStylesheets().add(String.valueOf(getClass().getResource("/styles.css")));
        this.setScene(scene);
        this.show();

    }
}
