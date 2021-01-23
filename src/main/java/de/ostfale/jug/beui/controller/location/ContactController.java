package de.ostfale.jug.beui.controller.location;

import de.ostfale.jug.beui.domain.DataModel;
import de.ostfale.jug.beui.domain.person.Person;
import de.ostfale.jug.beui.services.person.GetPersonTaskService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    private ComboBox<Person> cb_person;
    private TextField tf_mail;
    private DataModel<Person> personModel;

    // service task person
    private final GetPersonTaskService getPersonTaskService = new GetPersonTaskService();

    public ContactController(ComboBox<Person> cb_person, TextField tf_mail) {
        this.cb_person = cb_person;
        this.tf_mail = tf_mail;
        processGetServiceResult(getPersonTaskService);
    }

    private void processGetServiceResult(GetPersonTaskService taskService) {
        log.debug("Init person contact list for location view");
        taskService.startService();
        taskService.getService().setOnSucceeded(e -> {
            var personList = taskService.getService().getValue();
            updateDataModel(FXCollections.observableArrayList(personList));
        });
    }

    public void updateDataModel(ObservableList<Person> personObservableList) {
        if (personModel == null) {
            personModel = new DataModel<>();
            personModel.currentObjectProperty().bind(cb_person.getSelectionModel().selectedItemProperty());
            cb_person.setItems(personModel.getSortedList((p1, p2) -> {
                int result = p1.getLastName().compareToIgnoreCase(p2.getLastName());
                if (result == 0) {
                    result = p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
                }
                return result;
            }));
            this.personModel.currentObjectProperty().addListener(personListener);
        }
        personModel.setObjectList(personObservableList);
    }

    private final ChangeListener<Person> personListener = (obs, oldPerson, newPerson) -> {
        if (oldPerson != null) {
            tf_mail.textProperty().unbindBidirectional(oldPerson.emailProperty());
        }
        if (newPerson == null) {
            tf_mail.clear();
        } else {
            tf_mail.textProperty().bindBidirectional(newPerson.emailProperty());
        }
    };

    public void selectPerson(Person person) {
        this.cb_person.getSelectionModel().select(person);
    }
}
