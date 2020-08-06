package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.domain.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for person_details.fxml file
 * Created :  05.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonDetailsController {

    @FXML
    private TextField txt_firstName;

    @FXML
    private TextField txt_lastName;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_phone;

    public Person getPerson() {
        String firstName = txt_firstName.textProperty().getValue();
        String lastName = txt_lastName.textProperty().getValue();
        String email = txt_email.textProperty().getValue();
        String phone = txt_phone.textProperty().getValue();
        return new Person(firstName, lastName, email, phone, "");
    }

    public void setPerson(Person oldPerson, Person newPerson) {
        if (oldPerson != null) {
            txt_firstName.textProperty().unbindBidirectional(oldPerson.firstNameProperty());
            txt_lastName.textProperty().unbindBidirectional(oldPerson.lastNameProperty());
            txt_email.textProperty().unbindBidirectional(oldPerson.emailProperty());
            txt_phone.textProperty().unbindBidirectional(oldPerson.phoneProperty());
        }

        if (newPerson == null) {
            txt_firstName.clear();
            txt_lastName.clear();
            txt_email.clear();
            txt_phone.clear();
        } else {
            txt_firstName.textProperty().bindBidirectional(newPerson.firstNameProperty());
            txt_lastName.textProperty().bindBidirectional(newPerson.lastNameProperty());
            txt_email.textProperty().bindBidirectional(newPerson.emailProperty());
            txt_phone.textProperty().bindBidirectional(newPerson.phoneProperty());
        }
    }
}
