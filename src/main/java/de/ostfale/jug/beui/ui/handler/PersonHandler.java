package de.ostfale.jug.beui.ui.handler;

import de.ostfale.jug.beui.controller.person.PersonMasterDetailController;
import javafx.scene.layout.AnchorPane;

public class PersonHandler extends BaseHandler<PersonMasterDetailController, AnchorPane> {

    private static final String fxmlPath = "./fxml/person_master_detail.fxml";

    public PersonHandler() {
        initHandler();
    }

    @Override
    protected String getFXMLPath() {
        return fxmlPath;
    }
}
