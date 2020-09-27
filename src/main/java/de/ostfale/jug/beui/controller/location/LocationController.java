package de.ostfale.jug.beui.controller.location;

import de.ostfale.jug.beui.controller.person.GetPersonService;
import de.ostfale.jug.beui.domain.Location;
import de.ostfale.jug.beui.domain.Person;
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

public class LocationController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    @FXML
    private ListView<Location> lst_person;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initPersonListView(cb_contact);
    }

    private void initPersonListView(ComboBox<Person> listView) {
        getPersonService.startService();
        processGetPersonListResult(getPersonService);

        SortedList<Person> sortedList = prepareSortedPersonList(personList);
        listView.setItems(sortedList);
    }

    private void processGetPersonListResult(GetPersonService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            final List<Person> resultList = taskService.getService().getValue();
            log.debug("Update PersonList found {} persons", resultList.size());
            personList.clear();
            personList.addAll(resultList);
        });
    }

    private SortedList<Person> prepareSortedPersonList(ObservableList<Person> personList) {
        SortedList<Person> sortedList = new SortedList<>(personList);  // use sorted list by last and first name
        sortedList.setComparator((p1, p2) -> {
            int result = p1.getLastName().compareToIgnoreCase(p2.getLastName());
            if (result == 0) {
                result = p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
            }
            return result;
        });
        return sortedList;
    }
}
