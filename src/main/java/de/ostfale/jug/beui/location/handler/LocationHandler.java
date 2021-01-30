package de.ostfale.jug.beui.location.handler;

import de.ostfale.jug.beui.location.controller.LocationController;
import de.ostfale.jug.beui.common.BaseHandler;
import javafx.scene.layout.AnchorPane;

public class LocationHandler extends BaseHandler<LocationController, AnchorPane> {

    private static final String fxmlPath = "./fxml/location_master_detail.fxml";

    public LocationHandler() {
        initHandler();
    }

    @Override
    protected String getFXMLPath() {
        return fxmlPath;
    }
}
