package de.ostfale.jug.beui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * UI for REST backend handling JUG events
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class JugUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JUG HH Event Handler");
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setX(50);
        primaryStage.setY(50);
        primaryStage.show();
    }
}
