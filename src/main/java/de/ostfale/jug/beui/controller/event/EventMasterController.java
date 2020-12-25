package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.domain.Location;
import de.ostfale.jug.beui.domain.event.Event;
import de.ostfale.jug.beui.domain.event.EventStatus;
import de.ostfale.jug.beui.services.event.GetEventTaskService;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class EventMasterController extends BaseController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(EventMasterController.class);

    @FXML
    private TableView<Event> tbl_events;

    // controller
    private EventDetailController eventDetailController;

    // events
    private Event selectedEvent;
    private final ObservableList<Event> eventList = FXCollections.observableArrayList();

 /*

    // button area
    @FXML
    Button btn_refresh;
    @FXML
    Button btn_new;
    @FXML
    Button btn_save;
    @FXML
    Button btn_delete;*/

    // event

    private final GetEventTaskService getEventTaskService = new GetEventTaskService();
    private final EventActionService actionService = new EventActionService();

    // columns
    TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
    TableColumn<Event, String> nameColumn = new TableColumn<>("Title");
    TableColumn<Event, EventStatus> statusColumn = new TableColumn<>("Status");
    TableColumn<Event, String> locationColumn = new TableColumn<>("Location");


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

    public void setEventDetailController(EventDetailController anEventDetailController) {
        this.eventDetailController = anEventDetailController;
    }

    private void initTable() {
        dateColumn.setCellValueFactory(cellData -> {
            LocalDateTime scheduledDateTime = cellData.getValue().getDateTime();
            String dateTimeString = scheduledDateTime != null ? scheduledDateTime.toString() : "";
            return new ReadOnlyStringWrapper(dateTimeString);
        });

        PropertyValueFactory<Event, String> titleCellValueFactory = new PropertyValueFactory<>("title");
        nameColumn.setCellValueFactory(titleCellValueFactory);

        PropertyValueFactory<Event, EventStatus> statusCellValueFactory = new PropertyValueFactory<>("eventStatus");
        statusColumn.setCellValueFactory(statusCellValueFactory);

        locationColumn.setCellValueFactory(cellData -> {
            Location eventLocation = cellData.getValue().getLocation();
            String locationName = eventLocation != null ? eventLocation.getName() : "";
            return new ReadOnlyStringWrapper(locationName);
        });

        tbl_events.getColumns().addAll(dateColumn, nameColumn, statusColumn, locationColumn);
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

 /*   @FXML
    private void addNewEventAction() {
        actionService.addNewEvent();
    }*/
}
