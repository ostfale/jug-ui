package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.domain.event.Event;
import de.ostfale.jug.beui.services.event.GetEventTaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventController extends BaseController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(EventController.class);

    @FXML
    private TableView<Event> tbl_event;

    // event
    private Event selectedEvent;
    private final ObservableList<Event> eventList = FXCollections.observableArrayList();
    private final GetEventTaskService getEventTaskService = new GetEventTaskService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        processGetServiceResult(getEventTaskService);

        SortedList<Event> sortedList = getEventTaskService.getSortedList(eventList);
        System.out.println(sortedList);
    }

    private void processGetServiceResult(GetEventTaskService taskService) {
        taskService.startService();
        taskService.updateList(eventList);
    }
}
