package de.ostfale.jug.beui.controller.location;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.controller.person.AddPersonTaskService;
import de.ostfale.jug.beui.controller.person.GetPersonService;
import de.ostfale.jug.beui.domain.Location;
import de.ostfale.jug.beui.domain.Person;
import de.ostfale.jug.beui.domain.Room;
import de.ostfale.jug.beui.services.location.AddLocationTaskService;
import de.ostfale.jug.beui.services.location.GetLocationService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

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

    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);


    private final ObservableList<Person> personList = FXCollections.observableArrayList();
    private final GetPersonService getPersonService = new GetPersonService();

    private final ObservableList<Location> locationList = FXCollections.observableArrayList();
    private final GetLocationService getLocationService = new GetLocationService();
    private final AddLocationTaskService addLocationTaskService = new AddLocationTaskService();
    private Location selectedLocation;


    private final ObservableList<Room> roomList = FXCollections.observableArrayList();
    private Room selectedRoom;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createBindings();

        processGetServiceResult(getLocationService);
        initPersonListView(cb_contact);
        initLocationListView(lst_location, getLocationService.getSortedList(locationList));
        initRoomListView(lst_room);

        processAddLocationServiceResult(addLocationTaskService);
    }

    private void initRoomListView(ListView<Room> lst_room) {
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
        btn_deleteRoom.disableProperty().bind(lst_room.getSelectionModel().selectedItemProperty().isNull());

        // button
        btn_update.disableProperty().bind((lst_location.getSelectionModel().selectedItemProperty().isNull()));
        btn_delete.disableProperty().bind(lst_location.getSelectionModel().selectedItemProperty().isNull());
        btn_new.disableProperty().bind(lst_location.getSelectionModel().selectedItemProperty().isNotNull()
                .or(tf_name.textProperty().isEmpty())
                .or(tf_country.textProperty().isEmpty())
                .or(tf_city.textProperty().isEmpty())
                .or(tf_postalCode.textProperty().isEmpty())
                .or(tf_streetName.textProperty().isEmpty())
                .or(tf_streetNo.textProperty().isEmpty())
                .or(cb_contact.getSelectionModel().selectedItemProperty().isNull())
        );


    }

    private void updateRoomList(List<Room> aRoomSet) {
        roomList.clear();
        roomList.addAll(aRoomSet);
        lst_room.setItems(roomList);
    }

    private void initLocationListView(ListView<Location> listView, SortedList<Location> sortedList) {
        listView.setItems(sortedList);
        listView.getSelectionModel().selectFirst();

        listView.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    selectedLocation = newValue;
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        tf_name.setText(selectedLocation.getName());
                        tf_country.setText(selectedLocation.getCountry());
                        tf_city.setText(selectedLocation.getCity());
                        tf_postalCode.setText(selectedLocation.getPostalCode());
                        tf_streetName.setText(selectedLocation.getStreetName());
                        tf_streetNo.setText(selectedLocation.getStreetNumber());
                        cb_contact.getSelectionModel().select(selectedLocation.getContact());
                        tf_email.setText(selectedLocation.getContact().getEmail());
                        updateRoomList(selectedLocation.getRooms());
                    } else {
                        resetTextFields(tf_name, tf_country, tf_city, tf_postalCode, tf_streetName, tf_streetNo, tf_email);
                    }
                }
                ));
    }

    private void processGetServiceResult(GetLocationService taskService) {
        taskService.startService();
        taskService.updateList(locationList);
    }

    private void initPersonListView(ComboBox<Person> listView) {
        getPersonService.startService();
        processGetPersonListResult(getPersonService);
        listView.setItems(getPersonService.getSortedList(personList));
    }

    private void processGetPersonListResult(GetPersonService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            final List<Person> resultList = taskService.getService().getValue();
            log.debug("Update PersonList found {} persons", resultList.size());
            personList.clear();
            personList.addAll(resultList);
        });
    }

    @FXML
    private void addLocationAction() {
        Location location = new Location();
        location.setName(tf_name.getText());
        location.setCountry(tf_country.getText());
        location.setCity(tf_city.getText());
        location.setPostalCode(tf_postalCode.getText());
        location.setStreetName(tf_streetName.getText());
        location.setStreetNumber(tf_streetNo.getText());
        location.setContact(cb_contact.getSelectionModel().getSelectedItem());
        location.getRooms().addAll(new HashSet<>(lst_room.getItems()));
        addLocationTaskService.setLocation(location);
        addLocationTaskService.startService();
    }

    private void processAddLocationServiceResult(AddLocationTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Location successfully added...");
            getLocationService.startService();
            lst_location.refresh();
        });
    }

    @FXML
    private void refreshButtonAction() {
        getLocationService.startService();
        cb_contact.getSelectionModel().select(null);
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
}
