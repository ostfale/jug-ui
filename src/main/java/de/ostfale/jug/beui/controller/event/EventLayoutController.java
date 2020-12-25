package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.controller.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventLayoutController extends BaseController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(EventLayoutController.class);

    @FXML
    private EventMasterController eventMasterController;

    @FXML
    private EventDetailController eventDetailsController;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_new;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        eventMasterController.setEventDetailController(eventDetailsController);
        addBindings();
    }

    @FXML
    private void addNewEvent() {
        log.info("Create new event");
        eventMasterController.addEvent();
    }

    private void addBindings() {
        eventMasterController.deleteButtonBinding(btn_delete);
    }
}
