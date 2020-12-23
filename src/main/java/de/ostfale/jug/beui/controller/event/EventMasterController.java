package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.domain.event.Event;
import de.ostfale.jug.beui.services.event.GetEventTaskService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class EventMasterController extends BaseController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(EventMasterController.class);

 /*   @FXML
    private TableView<Event> tbl_event;

    // field area
    @FXML
    TextField tf_title;

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
    private Event selectedEvent;
    private final ObservableList<Event> eventList = FXCollections.observableArrayList();
    private final GetEventTaskService getEventTaskService = new GetEventTaskService();
    private final EventActionService actionService = new EventActionService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     /*   addSelectionListener();
        new EventTableService(tbl_event).initEventTableView();
        tbl_event.setItems(eventList);

        processGetServiceResult(getEventTaskService);

        SortedList<Event> sortedList = getEventTaskService.getSortedList(eventList);
        eventList.clear();
        eventList.setAll(sortedList);
        System.out.println(sortedList);
        actionService.processAddEventServiceResult(getEventTaskService);*/
    }

 /*   private void addSelectionListener() {
        tbl_event.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Event>() {
            @Override
            public void changed(ObservableValue<? extends Event> observable, Event oldValue, Event newValue) {
                if (newValue != null) {
                    selectedEvent = newValue;
                    updateDataView();
                }
            }
        });
    }

    private void updateDataView() {
        tf_title.setText(selectedEvent.getTitle());
    }

    private void processGetServiceResult(GetEventTaskService taskService) {
        taskService.startService();
        taskService.updateList(eventList);
    }

    @FXML
    private void addNewEventAction() {
        actionService.addNewEvent();
    }*/
}
