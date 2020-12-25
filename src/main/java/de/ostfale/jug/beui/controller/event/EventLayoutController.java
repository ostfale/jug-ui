package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EventLayoutController extends BaseController implements Initializable {

    @FXML
    private EventMasterController eventMasterController;

    @FXML
    private EventDetailController eventDetailsController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventMasterController.setEventDetailController(eventDetailsController);

    }
}
