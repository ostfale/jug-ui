package de.ostfale.jug.beui.common;

import de.ostfale.jug.beui.location.handler.LocationHandler;
import de.ostfale.jug.beui.person.handler.PersonHandler;
import de.ostfale.jug.beui.event.handler.EventLayoutHandler;
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
    private final LocationHandler locationHandler = new LocationHandler();
    private final EventLayoutHandler eventLayoutHandler = new EventLayoutHandler();

    public void initLayout(Stage primaryStage) {
        primaryStage.setTitle("JUG HH Event Handler");
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createMenubar());
        borderPane.setCenter(createTabPane());
        Scene scene = new Scene(borderPane, 1300, 700);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setX(50);
        primaryStage.setY(50);
        primaryStage.show();
    }

    private TabPane createTabPane() {
        TabPane tabPane = new TabPane();
        tabPane.setSide(Side.RIGHT);
        tabPane.getTabs().addAll(createEventTab(), createLocationTab(),createPersonTab());
        return tabPane;
    }

    private Tab createEventTab() {
        Tab event = new Tab("Event", eventLayoutHandler.getUiRoot());
        event.setClosable(false);
        return event;
    }

    private Tab createPersonTab() {
        Tab person = new Tab("Person", personHandler.getUiRoot());
        person.setClosable(false);
        return person;
    }

    private Tab createLocationTab() {
        Tab location = new Tab("Location", locationHandler.getUiRoot());
        location.setClosable(false);
        return location;
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
