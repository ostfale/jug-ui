package de.ostfale.jug.beui.person.controller;

import de.ostfale.jug.beui.common.BaseController;
import de.ostfale.jug.beui.person.domain.Person;
import de.ostfale.jug.beui.person.services.AddPersonTaskService;
import de.ostfale.jug.beui.person.services.DeletePersonTaskService;
import de.ostfale.jug.beui.person.services.GetPersonTaskService;
import de.ostfale.jug.beui.person.services.UpdatePersonTaskService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PersonMasterDetailController extends BaseController<Person> implements Initializable {

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
    private Button btn_save;
    @FXML
    private Button btn_delete;
    @FXML
    private Button btn_refresh;

    // service tasks
    private final GetPersonTaskService getPersonTaskService = new GetPersonTaskService();
    private final AddPersonTaskService addPersonTaskService = new AddPersonTaskService();
    private final DeletePersonTaskService deletePersonTaskService = new DeletePersonTaskService();
    private final UpdatePersonTaskService updatePersonTaskService = new UpdatePersonTaskService();

    private final BooleanProperty modifiedProperty = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonBinding();
        processGetServiceResult(getPersonTaskService);
        processAddPersonServiceResult(addPersonTaskService);
        processDeletePersonServiceResult(deletePersonTaskService);
        processUpdatePersonServiceResult(updatePersonTaskService);
        setChangeListener(personListener);
    }

    @Override
    protected Comparator<Person> getComparator() {
        return (p1, p2) -> {
            int result = p1.getLastName().compareToIgnoreCase(p2.getLastName());
            if (result == 0) {
                result = p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
            }
            return result;
        };
    }

    private void buttonBinding() {
        btn_delete.disableProperty().bind(lst_person.getSelectionModel().selectedItemProperty().isNull());
        btn_new.disableProperty().bind(modifiedProperty);
        btn_save.disableProperty().bind(lst_person.getSelectionModel().selectedItemProperty().isNull()
                .or(modifiedProperty.not())
                .or(txt_firstname.textProperty().isEmpty())
                .or(txt_lastname.textProperty().isEmpty())
                .or(txt_email.textProperty().isEmpty()));
    }

    private void processGetServiceResult(GetPersonTaskService taskService) {
        taskService.startService();
        taskService.getService().setOnSucceeded(e -> {
            var personList = taskService.getService().getValue();
            updateModel(FXCollections.observableArrayList(personList), lst_person);
        });
    }

    private void processAddPersonServiceResult(AddPersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person successfully added...");
            getPersonTaskService.startService();
            lst_person.refresh();
        });
    }

    private void processDeletePersonServiceResult(DeletePersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person has been successfully deleted...");
            getPersonTaskService.startService();
            lst_person.refresh();
        });
    }

    private void processUpdatePersonServiceResult(UpdatePersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Person has been successfully been updated...");
            getPersonTaskService.startService();
            lst_person.refresh();
        });
    }

    @FXML
    private void handleKeyAction() {
        modifiedProperty.set(true);
    }

    @FXML
    private void refreshButtonAction() {
        getPersonTaskService.startService();
    }

    @FXML
    private void addPersonAction() {
        addPersonTaskService.setPerson(new Person());
        addPersonTaskService.startService();
    }

    @FXML
    private void savePerson() {
        log.debug("Save person: {}", dataModel.getCurrentObject().getFirstName());
        updatePersonTaskService.setPerson(dataModel.getCurrentObject());
        updatePersonTaskService.startService();
        modifiedProperty.set(false);
    }

    @FXML
    private void deletePersonAction() {
        deletePersonTaskService.setPerson(dataModel.getCurrentObject());
        deletePersonTaskService.startService();
    }

    private final ChangeListener<Person> personListener = (obs, oldPerson, newPerson) -> {
        if (oldPerson != null) {
            txt_firstname.textProperty().unbindBidirectional(oldPerson.firstNameProperty());
            txt_lastname.textProperty().unbindBidirectional(oldPerson.lastNameProperty());
            txt_email.textProperty().unbindBidirectional(oldPerson.emailProperty());
            txt_phone.textProperty().unbindBidirectional(oldPerson.phoneProperty());
            ta_bio.textProperty().unbindBidirectional(oldPerson.bioProperty());
        }

        if (newPerson == null) {
            txt_firstname.clear();
            txt_lastname.clear();
            txt_email.clear();
            txt_phone.clear();
            ta_bio.clear();
        } else {
            txt_firstname.textProperty().bindBidirectional(newPerson.firstNameProperty());
            txt_lastname.textProperty().bindBidirectional(newPerson.lastNameProperty());
            txt_email.textProperty().bindBidirectional(newPerson.emailProperty());
            txt_phone.textProperty().bindBidirectional(newPerson.phoneProperty());
            ta_bio.textProperty().bindBidirectional(newPerson.bioProperty());
        }
    };
}
