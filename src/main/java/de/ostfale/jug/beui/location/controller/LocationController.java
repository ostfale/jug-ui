package de.ostfale.jug.beui.location.controller;

import de.ostfale.jug.beui.common.BaseController;
import de.ostfale.jug.beui.common.DataModel;
import de.ostfale.jug.beui.location.domain.Location;
import de.ostfale.jug.beui.location.domain.Room;
import de.ostfale.jug.beui.person.domain.Person;
import de.ostfale.jug.beui.location.services.AddLocationTaskService;
import de.ostfale.jug.beui.location.services.GetLocationTaskService;
import de.ostfale.jug.beui.location.services.UpdateLocationTaskService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationController extends BaseController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    @FXML
    private ListView<Location> lst_location;
    @FXML
    private ListView<Room> lst_room;
    @FXML
    private TextField tf_name;
    @FXML
    private TextField tf_country;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_postalCode;
    @FXML
    private TextField tf_streetName;
    @FXML
    private TextField tf_streetNo;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_roomName;
    @FXML
    private TextField tf_roomCapacity;
    @FXML
    private TextArea ta_roomRemark;
    @FXML
    private ComboBox<Person> cb_contact;
    @FXML
    private Button btn_new;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_addRoom;
    @FXML
    private Button btn_deleteRoom;

    // sub controller
    private ContactController contactController;
    private RoomController roomController;


    // model
    private DataModel<Location> locationModel;

    // service tasks location
    private final GetLocationTaskService getLocationTaskService = new GetLocationTaskService();
    private final AddLocationTaskService addLocationTaskService = new AddLocationTaskService();
    private final UpdateLocationTaskService updateLocationTaskService = new UpdateLocationTaskService();

    @FXML
    private void handleKeyAction() {
        modifiedProperty.set(true);
    }


    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);

    // room
    private Room selectedRoom;
    private final ObservableList<Room> roomList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonBinding();
        createBindings();
        processGetServiceResult(getLocationTaskService);
        processAddLocationServiceResult(addLocationTaskService);
        processUpdateLocationServiceResult(updateLocationTaskService);
        contactController = new ContactController(cb_contact, tf_email);
        roomController = new RoomController(lst_room, btn_addRoom,btn_deleteRoom,tf_roomName,tf_roomCapacity,ta_roomRemark);
    }

    public void updateDataModel(ObservableList<Location> locationList) {
        if (locationModel == null) {
            locationModel = new DataModel<>();
            locationModel.currentObjectProperty().bind(lst_location.getSelectionModel().selectedItemProperty());
            lst_location.setItems(locationModel.getSortedList((l1, l2) -> l1.getName().compareToIgnoreCase(l2.getName())));
            locationModel.currentObjectProperty().addListener(locationListener);
        }
        locationModel.setObjectList(locationList);
        lst_location.getSelectionModel().selectFirst();
    }

    private void createBindings() {
        btn_update.disableProperty().bind((lst_location.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                .or(allTextFieldsEmpty(tf_name, tf_country, tf_city, tf_postalCode, tf_streetName, tf_streetNo))
        ));
    }

    private void processUpdateLocationServiceResult(UpdateLocationTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Location has been successfully been updated...");
            updateLocationTaskService.startService();
            lst_location.refresh();
        });
    }

    private void processGetServiceResult(GetLocationTaskService taskService) {
        taskService.startService();
        taskService.getService().setOnSucceeded(e -> {
            var locationList = taskService.getService().getValue();
            updateDataModel(FXCollections.observableArrayList(locationList));
        });
    }

    @FXML
    private void addLocationAction() {
        addLocationTaskService.setLocation(new Location());
        addLocationTaskService.startService();
        getLocationTaskService.startService();
    }

    private void processAddLocationServiceResult(AddLocationTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Location successfully added...");
            getLocationTaskService.startService();
            lst_location.refresh();
        });
    }

    @FXML
    private void refreshButtonAction() {
        getLocationTaskService.startService();
        lst_room.setItems(FXCollections.emptyObservableList());
    }

    @FXML
    private void showRoomPopup() {
       roomController.addRoom();
    }

    @FXML
    private void updateLocationAction() {
        log.trace("Update location parameter...");
        updateLocationTaskService.setLocation(locationModel.getCurrentObject());
        getLocationTaskService.startService();
    }

    private void buttonBinding() {
        btn_delete.disableProperty().bind(lst_location.getSelectionModel().selectedItemProperty().isNull());
        btn_new.disableProperty().bind(modifiedProperty);
    }

    private final ChangeListener<Location> locationListener = (obs, oldLocation, newLocation) -> {
        if (oldLocation != null) {
            tf_name.textProperty().unbindBidirectional(oldLocation.nameProperty());
            tf_country.textProperty().unbindBidirectional(oldLocation.countryProperty());
            tf_city.textProperty().unbindBidirectional(oldLocation.cityProperty());
            tf_postalCode.textProperty().unbindBidirectional(oldLocation.postalCodeProperty());
            tf_streetName.textProperty().unbindBidirectional(oldLocation.streetNameProperty());
            tf_streetNo.textProperty().unbindBidirectional(oldLocation.streetNumberProperty());
            cb_contact.valueProperty().unbindBidirectional(oldLocation.contactProperty());
        }

        if (newLocation == null) {
            tf_name.clear();
            tf_country.clear();
            tf_city.clear();
            tf_postalCode.clear();
            tf_streetName.clear();
            tf_streetNo.clear();
            cb_contact.getSelectionModel().clearSelection();
        } else {
            tf_name.textProperty().bindBidirectional(newLocation.nameProperty());
            tf_country.textProperty().bindBidirectional(newLocation.countryProperty());
            tf_city.textProperty().bindBidirectional(newLocation.cityProperty());
            tf_postalCode.textProperty().bindBidirectional(newLocation.postalCodeProperty());
            tf_streetName.textProperty().bindBidirectional(newLocation.streetNameProperty());
            tf_streetNo.textProperty().bindBidirectional(newLocation.streetNumberProperty());
            cb_contact.valueProperty().bindBidirectional(newLocation.contactProperty());
            roomController.updateDataModel(FXCollections.observableArrayList(newLocation.getRooms()));
        }
    };
}
