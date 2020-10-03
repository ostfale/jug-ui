package de.ostfale.jug.beui.controller;

import javafx.scene.control.TextInputControl;

import java.util.Arrays;

abstract public class BaseController {

    protected void resetTextFields(TextInputControl... inputControls) {
        Arrays.stream(inputControls).forEach(tf -> tf.setText(""));
    }
}
