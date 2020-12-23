package de.ostfale.jug.beui.ui.handler.event;

import de.ostfale.jug.beui.controller.event.EventMasterController;
import de.ostfale.jug.beui.ui.handler.BaseHandler;
import javafx.scene.layout.AnchorPane;

public class EventMasterHandler extends BaseHandler<EventMasterController, AnchorPane> {

    private static final String fxmlPath = "./fxml/event/event_master.fxml";

    public EventMasterHandler() {
        initHandler();
    }

    @Override
    protected String getFXMLPath() {
        return fxmlPath;
    }
}
