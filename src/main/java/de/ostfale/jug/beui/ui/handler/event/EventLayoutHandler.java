package de.ostfale.jug.beui.ui.handler.event;

import de.ostfale.jug.beui.controller.event.EventLayoutController;
import de.ostfale.jug.beui.ui.handler.BaseHandler;
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
