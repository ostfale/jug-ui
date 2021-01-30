package de.ostfale.jug.beui.event.handler;

import de.ostfale.jug.beui.event.controller.EventDetailController;
import de.ostfale.jug.beui.common.BaseHandler;
import javafx.scene.layout.AnchorPane;

public class EventDetailHandler extends BaseHandler<EventDetailController, AnchorPane> {

    private static final String fxmlPath = "./fxml/event/event_details.fxml";

    public EventDetailHandler() {
        initHandler();
    }

    @Override
    protected String getFXMLPath() {
        return fxmlPath;
    }
}
