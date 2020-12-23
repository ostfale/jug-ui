package de.ostfale.jug.beui.ui.handler.event;

import de.ostfale.jug.beui.controller.event.EventDetailController;
import de.ostfale.jug.beui.ui.handler.BaseHandler;
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
