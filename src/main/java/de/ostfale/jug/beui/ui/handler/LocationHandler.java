package de.ostfale.jug.beui.ui.handler;

import de.ostfale.jug.beui.controller.location.LocationController;
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
