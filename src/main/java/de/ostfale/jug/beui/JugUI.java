package de.ostfale.jug.beui;

import de.ostfale.jug.beui.ui.handler.LayoutHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class JugUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new LayoutHandler().initLayout(primaryStage);
    }
}
