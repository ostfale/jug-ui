package de.ostfale.jug.beui.event.controller;

import de.ostfale.jug.beui.common.BaseController;
import de.ostfale.jug.beui.event.services.EventActionService;
import de.ostfale.jug.beui.location.domain.Location;
import de.ostfale.jug.beui.event.domain.Event;
import de.ostfale.jug.beui.event.domain.EventStatus;
import de.ostfale.jug.beui.event.services.GetEventTaskService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EventMasterController extends BaseController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(EventMasterController.class);

    @FXML
    private TableView<Event> tbl_events;

    // controller
    private EventDetailController eventDetailController;

    // events
    private Event selectedEvent;
    private final ObservableList<Event> eventList = FXCollections.observableArrayList();

    private final GetEventTaskService getEventTaskService = new GetEventTaskService();
    private final EventActionService actionService = new EventActionService();

    // columns
    TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
    TableColumn<Event, String> nameColumn = new TableColumn<>("Title");
    TableColumn<Event, EventStatus> statusColumn = new TableColumn<>("Status");
    TableColumn<Event, String> locationColumn = new TableColumn<>("Location");
    TableColumn<Event, String> speakerColumn = new TableColumn<>("Speaker");
    TableColumn<Event, String> communicationColumn = new TableColumn<>("Communication");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.debug("Init event master table view");
        initTable();
        addSelectionListener();
        processGetServiceResult(getEventTaskService);
        SortedList<Event> sortedList = getEventTaskService.getSortedList(eventList);
        eventList.clear();
        eventList.setAll(sortedList);
        actionService.processAddEventServiceResult(getEventTaskService);
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setEventDetailController(EventDetailController anEventDetailController) {
       // this.eventDetailController = anEventDetailController;
    }

    private void initTable() {
        dateColumn.setPrefWidth(150);
        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime scheduledDateTime = cellData.getValue().getDateTime();
            String dateTimeString = scheduledDateTime != null ? scheduledDateTime.toString() : "";
            return new ReadOnlyStringWrapper(dateTimeString);
        });

        PropertyValueFactory<Event, String> titleCellValueFactory = new PropertyValueFactory<>("title");
        nameColumn.setCellValueFactory(titleCellValueFactory);
        nameColumn.setPrefWidth(250);

        PropertyValueFactory<Event, EventStatus> statusCellValueFactory = new PropertyValueFactory<>("eventStatus");
        statusColumn.setCellValueFactory(statusCellValueFactory);

        locationColumn.setPrefWidth(150);
        locationColumn.setCellValueFactory(cellData -> {
            Location eventLocation = cellData.getValue().getLocation();
            String locationName = eventLocation != null ? eventLocation.getName() : "";
            return new ReadOnlyStringWrapper(locationName);
        });

        speakerColumn.setPrefWidth(150);
        speakerColumn.setCellValueFactory(cellData -> {
            var speaker = cellData.getValue().getSpeaker();
            var result = speaker.stream().map(person -> person.getFirstName() + " " + person.getLastName()).collect(Collectors.joining(","));
            return new ReadOnlyStringWrapper(result);
        });

        communicationColumn.setPrefWidth(150);
        communicationColumn.setCellValueFactory(cellData -> {
            Location eventLocation = cellData.getValue().getLocation();
            String locationName = eventLocation != null ? eventLocation.getContactName() : "";
            return new ReadOnlyStringWrapper(locationName);
        });

        tbl_events.getColumns().addAll(dateColumn, nameColumn, speakerColumn, statusColumn, locationColumn, communicationColumn);
        tbl_events.setItems(eventList);
    }

    private void addSelectionListener() {
        tbl_events.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && eventDetailController != null) {
                selectedEvent = newValue;
                eventDetailController.updateData(selectedEvent);
            }
        });
    }

    private void processGetServiceResult(GetEventTaskService taskService) {
        taskService.startService();
        taskService.updateList(eventList);
    }

    public void addEvent() {
        actionService.addNewEvent();
    }

    public void deleteButtonBinding(Button deleteButton) {
        deleteButton.disableProperty().bind(tbl_events.getSelectionModel().selectedItemProperty().isNull());
    }

    public void saveButtonBinding(Button saveButton) {
        saveButton.disableProperty().bind(tbl_events.getSelectionModel().selectedItemProperty().isNull());
    }


}
