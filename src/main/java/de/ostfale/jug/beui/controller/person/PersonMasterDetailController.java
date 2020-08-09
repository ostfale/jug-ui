package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.domain.Person;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Layout controller for persons master detail pane
 * Created :  06.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonMasterDetailController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(PersonMasterDetailController.class);

    @FXML
    private ListView<Person> lst_person;
    @FXML
    private TextField txt_firstname;
    @FXML
    private TextField txt_lastname;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_phone;
    @FXML
    private TextArea ta_bio;
    @FXML
    private Button btn_new;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;

    private final ObservableList<Person> personList = FXCollections.observableArrayList();
    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);
    private Person selectedPerson;
    private ChangeListener<Person> personChangeListener;
    private final GetPersonsTaskService getPersonsTaskService = new GetPersonsTaskService();
    private final AddPersonTaskService addPersonTaskService = new AddPersonTaskService();
    private final DeletePersonTaskService deletePersonTaskService = new DeletePersonTaskService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btn_delete.disableProperty().bind(lst_person.getSelectionModel().selectedItemProperty().isNull());

        btn_new.disableProperty().bind(lst_person.getSelectionModel().selectedItemProperty().isNotNull()
                .or(txt_firstname.textProperty().isEmpty())
                .or(txt_lastname.textProperty().isEmpty())
                .or(txt_email.textProperty().isEmpty()));

        btn_update.disableProperty().bind(lst_person.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                .or(txt_firstname.textProperty().isEmpty())
                .or(txt_lastname.textProperty().isEmpty())
                .or(txt_email.textProperty().isEmpty()));

        getPersonsTaskService.startService();
        processGetServiceResult(getPersonsTaskService);
        processAddPersonServiceResult(addPersonTaskService);
        processDeletePersonServiceResult(deletePersonTaskService);
        initListView(lst_person);
    }

    private void initListView(ListView<Person> listView) {
        SortedList<Person> sortedList = prepareSortedList(personList);
        listView.setItems(sortedList);

        listView.getSelectionModel().selectedItemProperty().addListener(
                personChangeListener = ((observable, oldValue, newValue) -> {
                    selectedPerson = newValue; // can be null if nothing is selected
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        txt_firstname.setText(selectedPerson.getFirstName());
                        txt_lastname.setText(selectedPerson.getLastName());
                        txt_email.setText(selectedPerson.getEmail());
                        txt_phone.setText(selectedPerson.getPhone());
                        ta_bio.setText(selectedPerson.getBio());
                    } else {
                        txt_firstname.setText("");
                        txt_lastname.setText("");
                        txt_email.setText("");
                        txt_phone.setText("");
                        ta_bio.setText("");
                    }
                }));

        listView.getSelectionModel().selectFirst();  // pre-select first entry
    }

    private SortedList<Person> prepareSortedList(ObservableList<Person> personList) {
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

    private void processGetServiceResult(GetPersonsTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            final List<Person> resultList = taskService.getService().getValue();
            log.debug("Update PersonList found {} persons", resultList.size());
            personList.clear();
            personList.addAll(resultList);
        });
    }

    private void processAddPersonServiceResult(AddPersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person successfully added...");
            getPersonsTaskService.startService();
            lst_person.refresh();
        });
    }

    private void processDeletePersonServiceResult(DeletePersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person has been successfully deleted...");
            getPersonsTaskService.startService();
            lst_person.refresh();
        });
    }

    @FXML
    private void handleKeyAction() {
        modifiedProperty.set(true);
    }

    @FXML
    private void refreshButtonAction(ActionEvent actionEvent) {
        getPersonsTaskService.startService();
    }

    @FXML
    private void addPersonAction(ActionEvent actionEvent) {
        String firstName = txt_firstname.getText();
        String lastName = txt_lastname.getText();
        String email = txt_email.getText();
        String phone = txt_phone.getText();
        String bio = ta_bio.getText();
        Person newPerson = new Person(firstName, lastName, email, phone, bio);
        addPersonTaskService.setPerson(newPerson);
        addPersonTaskService.startService();
    }

    @FXML
    private void deletePersonAction(ActionEvent actionEvent) {
        deletePersonTaskService.setPerson(selectedPerson);
        deletePersonTaskService.startService();
    }
}
