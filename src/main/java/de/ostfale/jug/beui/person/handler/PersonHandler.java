package de.ostfale.jug.beui.person.handler;

import de.ostfale.jug.beui.common.BaseHandler;
import de.ostfale.jug.beui.person.controller.PersonMasterDetailController;
import javafx.scene.layout.AnchorPane;

public class PersonHandler extends BaseHandler<PersonMasterDetailController, AnchorPane> {

    private static final String fxmlPath = "fxml/person/person_master_detail.fxml";

    public PersonHandler() {
        initHandler();
    }

    @Override
    protected String getFXMLPath() {
        return fxmlPath;
    }
}
