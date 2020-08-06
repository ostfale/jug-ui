package de.ostfale.jug.beui.ui.person;

import de.ostfale.jug.beui.controller.person.PersonController;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

/**
 * Provides toolbar
 * Created :  04.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonToolbar {

    private final Button btn_refresh;
    private final Button btn_addPerson;
    private final Button btn_deletePerson;
    private final Button btn_editPerson;

    private final ToolBar toolBar;
    private final PersonController personController;

    public PersonToolbar(PersonController personController) {
        this.personController = personController;
        btn_refresh = refreshButton();
        btn_addPerson = addPersonButton();
        btn_deletePerson = deletePersonButton();
        btn_editPerson = editPersonButton();
        toolBar = createToolbar();
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    private ToolBar createToolbar() {
        ToolBar toolBar = new ToolBar();
        toolBar.setOrientation(Orientation.HORIZONTAL);
        toolBar.getItems().addAll(btn_refresh, btn_addPerson, btn_editPerson, btn_deletePerson);
        return toolBar;
    }

    private Button refreshButton() {
        Button button = new Button();
        FontIcon icon = new FontIcon(FontAwesomeSolid.SYNC);
        button.setGraphic(icon);
        button.setOnAction(e -> personController.refresh());
        return button;
    }

    private Button addPersonButton() {
        Button button = new Button();
        FontIcon icon = new FontIcon(FontAwesomeSolid.USER_PLUS);
        icon.setIconColor(Color.GREEN);
        button.setGraphic(icon);
        button.setOnAction(e -> personController.addPerson());
        button.setTooltip(new Tooltip("Add new Person"));
        return button;
    }

    private Button deletePersonButton() {
        Button button = new Button();
        FontIcon icon = new FontIcon(FontAwesomeSolid.USER_MINUS);
        icon.setIconColor(Color.RED);
        button.setGraphic(icon);
        button.setTooltip(new Tooltip("Delete selected Person"));
        return button;
    }

    private Button editPersonButton() {
        Button button = new Button();
        FontIcon icon = new FontIcon(FontAwesomeSolid.USER_EDIT);
        icon.setIconColor(Color.ORANGE);
        button.setGraphic(icon);
        button.setTooltip(new Tooltip("Update selected Person"));
        return button;
    }
}
