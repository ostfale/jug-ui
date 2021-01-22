package de.ostfale.jug.beui.controller.location;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.services.person.GetPersonTaskService;
import de.ostfale.jug.beui.domain.DataModel;
import de.ostfale.jug.beui.domain.location.Location;
import de.ostfale.jug.beui.domain.location.Room;
import de.ostfale.jug.beui.domain.person.Person;
import de.ostfale.jug.beui.services.location.AddLocationTaskService;
import de.ostfale.jug.beui.services.location.GetLocationTaskService;
import de.ostfale.jug.beui.services.location.UpdateLocationTaskService;
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
import java.util.List;
import java.util.Optional;
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

    // model
    private DataModel<Location> locationModel;

    // service task person
    private final GetPersonTaskService getPersonTaskService = new GetPersonTaskService();


    // service tasks location
    private final GetLocationTaskService getLocationTaskService = new GetLocationTaskService();
    private final AddLocationTaskService addLocationTaskService = new AddLocationTaskService();
    private final UpdateLocationTaskService updateLocationTaskService = new UpdateLocationTaskService();

    @FXML
    private void handleKeyAction() {
        modifiedProperty.set(true);
    }


    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);

    // person
    private Person selectedPerson;
    private final ObservableList<Person> personList = FXCollections.observableArrayList();


    // room
    private Room selectedRoom;
    private final ObservableList<Room> roomList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonBinding();
        createBindings();
        initRoomListView();
        processGetServiceResult(getLocationTaskService);
        processAddLocationServiceResult(addLocationTaskService);
        processUpdateLocationServiceResult(updateLocationTaskService);
        processGetServiceResult(getPersonTaskService);
        initContactList();
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

    private void initContactList() {
        cb_contact.setItems(personList);
        cb_contact.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    selectedPerson = newValue;
                    if (selectedPerson != null) {
                        tf_email.setText(selectedPerson.getEmail());
                    }
                }
                ));
    }

    private void initRoomListView() {
        lst_room.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    selectedRoom = newValue;
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        tf_roomName.setText(selectedRoom.getName());
                        tf_roomCapacity.setText(String.valueOf(selectedRoom.getCapacity()));
                        ta_roomRemark.setText(selectedRoom.getRemark());
                    } else {
                        resetTextFields(tf_roomName, tf_roomCapacity, ta_roomRemark);
                    }
                }
                ));
    }

    private void createBindings() {
        // room
        tf_roomName.disableProperty().bind(lst_room.getSelectionModel().selectedItemProperty().isNull());
        tf_roomCapacity.disableProperty().bind(lst_room.getSelectionModel().selectedItemProperty().isNull());
        ta_roomRemark.disableProperty().bind(lst_room.getSelectionModel().selectedItemProperty().isNull());

        // button
        btn_update.disableProperty().bind((lst_location.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                .or(allTextFieldsEmpty(tf_name, tf_country, tf_city, tf_postalCode, tf_streetName, tf_streetNo))
        ));

    }

    private void updateRoomList(List<Room> aRoomSet) {
        roomList.clear();
        roomList.addAll(aRoomSet);
        lst_room.setItems(roomList);
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

    private void processGetServiceResult(GetPersonTaskService taskService) {
        taskService.startService();
        taskService.getService().setOnSucceeded(e -> {
            var personList = taskService.getService().getValue();
            updatePersonList(FXCollections.observableArrayList(personList));
        });
    }

    private void updatePersonList(ObservableList<Person> aPersonList) {
        personList.clear();
        personList.addAll(aPersonList);
    }

    @FXML
    private void addLocationAction() {
        addLocationTaskService.setLocation(new Location());
        addLocationTaskService.startService();
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
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Location Dialog");
        tid.setHeaderText("Capacity >= 30");
        tid.setContentText("Add room name:");
        Optional<String> result = tid.showAndWait();
        result.ifPresent(name -> {
            Room room = new Room();
            room.setName(name);
            room.setCapacity(30);
            lst_room.getItems().add(room);
        });
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
        }

        if (newLocation == null) {
            tf_name.clear();
            tf_country.clear();
            tf_city.clear();
            tf_postalCode.clear();
            tf_streetName.clear();
            tf_streetNo.clear();
        } else {
            tf_name.textProperty().bindBidirectional(newLocation.nameProperty());
            tf_country.textProperty().bindBidirectional(newLocation.countryProperty());
            tf_city.textProperty().bindBidirectional(newLocation.cityProperty());
            tf_postalCode.textProperty().bindBidirectional(newLocation.postalCodeProperty());
            tf_streetName.textProperty().bindBidirectional(newLocation.streetNameProperty());
            tf_streetNo.textProperty().bindBidirectional(newLocation.streetNumberProperty());
        }
    };
}
