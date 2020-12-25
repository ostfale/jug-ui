package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.domain.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EventDetailController extends BaseController implements Initializable {

    @FXML
    TextField tf_title;

    @FXML
    DatePicker f_date;

    @FXML
    TextField tf_time;

    @FXML
    TextArea ta_abstract;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updateData(Event selectedEvent) {
        tf_title.setText(selectedEvent.getTitle());

        if (selectedEvent.getDateTime() != null) {
            f_date.setValue(selectedEvent.getDateTime().toLocalDate());
            tf_time.setText(selectedEvent.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
    }
}
