package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.domain.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EventDetailController extends BaseController implements Initializable {

    @FXML
    TextField tf_title;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updateData(Event selectedEvent) {
        tf_title.setText(selectedEvent.getTitle());
    }
}
