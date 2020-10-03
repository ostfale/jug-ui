package de.ostfale.jug.beui.controller.location;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.controller.person.GetPersonService;
import de.ostfale.jug.beui.domain.Location;
import de.ostfale.jug.beui.domain.Person;
import de.ostfale.jug.beui.services.location.GetLocationService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LocationController extends BaseController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    @FXML
    private ListView<Location> lst_location;
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
    private ComboBox<Person> cb_contact;
    @FXML
    private Button btn_new;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;

    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);


    private final ObservableList<Person> personList = FXCollections.observableArrayList();
    private final GetPersonService getPersonService = new GetPersonService();

    private final ObservableList<Location> locationList = FXCollections.observableArrayList();
    private final GetLocationService getLocationService = new GetLocationService();
    private Location selectedLocation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // bindings
        btn_delete.disableProperty().bind(lst_location.getSelectionModel().selectedItemProperty().isNull());

        processGetServiceResult(getLocationService);
        initPersonListView(cb_contact);
        initLocationListView(lst_location, getLocationService.getSortedList(locationList));
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
}
