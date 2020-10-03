package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.controller.BaseController;
import de.ostfale.jug.beui.domain.Person;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;


public class PersonMasterDetailController extends BaseController implements Initializable {

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
    private final GetPersonService getPersonService = new GetPersonService();
    private final AddPersonTaskService addPersonTaskService = new AddPersonTaskService();
    private final DeletePersonTaskService deletePersonTaskService = new DeletePersonTaskService();
    private final UpdatePersonTaskService updatePersonTaskService = new UpdatePersonTaskService();

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

        processGetServiceResult(getPersonService);
        processAddPersonServiceResult(addPersonTaskService);
        processDeletePersonServiceResult(deletePersonTaskService);
        initListView(lst_person, getPersonService.getSortedList(personList));
    }

    private void initListView(ListView<Person> listView, SortedList<Person> sortedList) {
        listView.setItems(sortedList);
        listView.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> {
                    selectedPerson = newValue; // can be null if nothing is selected
                    modifiedProperty.set(false);
                    if (newValue != null) {
                        txt_firstname.setText(selectedPerson.getFirstName());
                        txt_lastname.setText(selectedPerson.getLastName());
                        txt_email.setText(selectedPerson.getEmail());
                        txt_phone.setText(selectedPerson.getPhone());
                        ta_bio.setText(selectedPerson.getBio());
                    } else {
                        resetTextFields(txt_firstname, txt_lastname, txt_email, txt_phone, ta_bio );
                    }
                }));
        listView.getSelectionModel().selectFirst();  // pre-select first entry
    }

    private void processGetServiceResult(GetPersonService taskService) {
        taskService.startService();
        taskService.updateList(personList);
    }

    private void processAddPersonServiceResult(AddPersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person successfully added...");
            getPersonService.startService();
            lst_person.refresh();
        });
    }

    private void processDeletePersonServiceResult(DeletePersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person has been successfully deleted...");
            getPersonService.startService();
            lst_person.refresh();
        });
    }

    private void processUpdatePersonServiceResult(UpdatePersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person has been successfully been updated...");
            updatePersonTaskService.startService();
            lst_person.refresh();
        });
    }

    @FXML
    private void handleKeyAction() {
        modifiedProperty.set(true);
    }

    @FXML
    private void refreshButtonAction() {
        getPersonService.startService();
    }

    @FXML
    private void addPersonAction() {
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
    private void updatePersonAction() {
        selectedPerson.setFirstName(txt_firstname.getText());
        selectedPerson.setLastName(txt_lastname.getText());
        selectedPerson.setEmail(txt_email.getText());
        selectedPerson.setPhone(txt_phone.getText());
        selectedPerson.setBio(ta_bio.getText());
        updatePersonTaskService.setPerson(selectedPerson);
        updatePersonTaskService.startService();
    }

    @FXML
    private void deletePersonAction() {
        deletePersonTaskService.setPerson(selectedPerson);
        deletePersonTaskService.startService();
    }
}
