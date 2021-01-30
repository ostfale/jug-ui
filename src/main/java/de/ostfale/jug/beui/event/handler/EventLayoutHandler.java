package de.ostfale.jug.beui.event.handler;

import de.ostfale.jug.beui.event.controller.EventLayoutController;
import de.ostfale.jug.beui.common.BaseHandler;
import javafx.scene.layout.AnchorPane;

public class EventLayoutHandler extends BaseHandler<EventLayoutController, AnchorPane> {

    private static final String fxmlPath = "./fxml/event/event_layout.fxml";

    public EventLayoutHandler() {
        new EventMasterHandler();
        new EventDetailHandler();
        initHandler();
    }

    @Override
    protected String getFXMLPath() {
        return fxmlPath;
    }
}
