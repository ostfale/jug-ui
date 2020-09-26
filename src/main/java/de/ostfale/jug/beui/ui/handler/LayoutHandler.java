package de.ostfale.jug.beui.ui.handler;

import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class LayoutHandler {

    private final PersonHandler personHandler = new PersonHandler();

    public void initLayout(Stage primaryStage) {
        primaryStage.setTitle("JUG HH Event Handler");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenubar());
        borderPane.setCenter(createTabPane());
        Scene scene = new Scene(borderPane, 960, 600);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setX(50);
        primaryStage.setY(50);
        primaryStage.show();
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.RIGHT);
        Tab location = new Tab("Location");
        location.setClosable(false);

        Tab events = new Tab("Events");
        events.setClosable(false);

        tabPane.getTabs().addAll(events, createPersonTab(), location);
        return tabPane;
    }

    private Tab createPersonTab() {
        Tab person = new Tab("Person", personHandler.getUiRoot());
        person.setClosable(false);
        return person;
    }

    private MenuBar createMenubar() {
        MenuBar menuBar = new MenuBar();
        Menu appMenu = new Menu("App");
        MenuItem exit_item = new MenuItem("Exit");
        exit_item.setOnAction(e -> Platform.exit());
        exit_item.setGraphic(new FontIcon(FontAwesomeSolid.DOOR_OPEN));

        appMenu.getItems().addAll(exit_item);
        menuBar.getMenus().addAll(appMenu);
        return menuBar;
    }
}
