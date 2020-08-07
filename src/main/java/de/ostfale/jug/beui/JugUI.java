package de.ostfale.jug.beui;

import de.ostfale.jug.beui.ui.person.PersonLayout;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

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
        final Pane root = new PersonLayout().getLayoutPane();
        AnchorPane ap = new AnchorPane();
        AnchorPane.setTopAnchor(root, 5.0);
        AnchorPane.setLeftAnchor(root, 5.0);
        AnchorPane.setBottomAnchor(root, 5.0);
        AnchorPane.setRightAnchor(root, 5.0);
        ap.getChildren().add(root);
        Tab person = new Tab("Person", ap);
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
