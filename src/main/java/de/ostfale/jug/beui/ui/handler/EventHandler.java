package de.ostfale.jug.beui.ui.handler;

import de.ostfale.jug.beui.controller.event.EventController;
import javafx.scene.layout.AnchorPane;

public class EventHandler extends BaseHandler<EventController, AnchorPane> {

    private static final String fxmlPath = "./fxml/event_master_detail.fxml";

    public EventHandler() {
        initHandler();
    }

    @Override
    protected String getFXMLPath() {
        return fxmlPath;
    }
}
