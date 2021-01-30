package de.ostfale.jug.beui.event.handler;

import de.ostfale.jug.beui.event.controller.EventMasterController;
import de.ostfale.jug.beui.common.BaseHandler;
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
