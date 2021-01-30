package de.ostfale.jug.beui.common;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextInputControl;

import java.util.Arrays;

abstract public class BaseController {

    protected void resetTextFields(TextInputControl... inputControls) {
        Arrays.stream(inputControls).forEach(tf -> tf.setText(""));
    }

    protected SimpleBooleanProperty allTextFieldsEmpty(TextInputControl... inputControls) {
        var result =  Arrays.stream(inputControls).noneMatch(tf -> tf.textProperty().isEmpty().get());
        return new SimpleBooleanProperty(result);
    }
}
