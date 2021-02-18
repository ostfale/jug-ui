package de.ostfale.jug.beui.event.controller;

import de.ostfale.jug.beui.common.BaseController;
import de.ostfale.jug.beui.location.domain.Location;
import de.ostfale.jug.beui.location.domain.LocationStatus;
import de.ostfale.jug.beui.person.domain.Person;
import de.ostfale.jug.beui.event.domain.ScheduleStatus;
import de.ostfale.jug.beui.event.domain.Event;
import de.ostfale.jug.beui.event.domain.EventStatus;
import de.ostfale.jug.beui.location.services.GetLocationTaskService;
import de.ostfale.jug.beui.person.services.GetPersonTaskService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EventDetailController extends BaseController<Void> implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(EventDetailController.class);

    @FXML
    TextField tf_title;

    @FXML
    DatePicker f_date;

    @FXML
    TextField tf_time;

    @FXML
    TextArea ta_abstract;

    @FXML
    TextArea ta_speaker;

    @FXML
    ChoiceBox<Location> cb_location;

    @FXML
    ComboBox<EventStatus> cb_eventStatus;

    @FXML
    ComboBox<ScheduleStatus> cb_scheduleStatus;

    @FXML
    ComboBox<LocationStatus> cb_locationStatus;

    @FXML
    ListView<Person> lst_speaker;

    @FXML
    CheckBox check_online;

    @FXML
    CheckBox check_complete;

    @FXML
    ContextMenu ct_speaker;

    @FXML
    GridPane grid_detail;

    private final GetLocationTaskService getLocationTaskService = new GetLocationTaskService();
    private final GetPersonTaskService getPersonTaskService = new GetPersonTaskService();
    private Event selectedEvent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cb_eventStatus.getItems().setAll(EventStatus.values());
        cb_scheduleStatus.getItems().setAll(ScheduleStatus.values());
        cb_locationStatus.getItems().setAll(LocationStatus.values());
        processGetServiceResult(getLocationTaskService);
        processGetServiceResult(getPersonTaskService);
    }

    public void updateData(Event aSelectedEvent) {
        this.selectedEvent = aSelectedEvent;
        tf_title.setText(selectedEvent.getTitle());

        if (selectedEvent.getDateTime() != null) {
            f_date.setValue(selectedEvent.getDateTime().toLocalDate());
            tf_time.setText(selectedEvent.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            cb_eventStatus.setValue(selectedEvent.getEventStatus());
            cb_locationStatus.setValue(selectedEvent.getLocationStatus());
            cb_scheduleStatus.setValue(selectedEvent.getScheduleStatus());
            cb_location.setValue(selectedEvent.getLocation());
            ta_abstract.setText(selectedEvent.getContent());
        }
    }


    private void processGetServiceResult(GetLocationTaskService taskService) {
        taskService.startService();
        taskService.getService().setOnSucceeded(e -> {
            var locationList = taskService.getService().getValue();
            cb_location.setItems(FXCollections.observableArrayList(locationList));
        });
    }

    private void processGetServiceResult(GetPersonTaskService taskService) {
        taskService.startService();
        taskService.getService().setOnSucceeded(e -> {
            var personList = taskService.getService().getValue();
            lst_speaker.getItems().addAll(personList);
        });
    }

    public void save(Event selectedEvent) {
        if (selectedEvent != null) {
            log.debug("Save Event");
            selectedEvent.setTitle(tf_title.getText());
        }
    }

    private void setEventParameter(Event targetEvent) {

    }

    private void setUIParameter(Event sourceEvent) {

    }
}
