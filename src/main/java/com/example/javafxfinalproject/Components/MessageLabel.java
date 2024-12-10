package com.example.javafxfinalproject.Components;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class MessageLabel extends Label {
    private final FadeTransition fadeInAnimation = new FadeTransition(Duration.millis(500), this);
    private final FadeTransition fadeOutAnimation = new FadeTransition(Duration.millis(500), this);
    public MessageLabel(String message) {
        fadeInAnimation.setFromValue(0);
        fadeInAnimation.setToValue(1);
        // fade out animation
        fadeOutAnimation.setFromValue(1);
        fadeOutAnimation.setToValue(0);
        fadeOutAnimation.setDelay(Duration.millis(2000));
    }

    public FadeTransition getFadeInAnimation() {
        return fadeInAnimation;
    }

    public FadeTransition getFadeOutAnimation() {
        return fadeOutAnimation;
    }

    public void playAnimation() {
        fadeInAnimation.play();
        fadeOutAnimation.play();
    }
}
