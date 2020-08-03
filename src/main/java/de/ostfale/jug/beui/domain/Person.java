package de.ostfale.jug.beui.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Domain class for person object
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class Person {

    private final StringProperty id = new SimpleStringProperty(this, "id", "");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName", "");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName", "");
    private final StringProperty email = new SimpleStringProperty(this, "email", "");
    private final StringProperty phone = new SimpleStringProperty(this, "phone", "");

    public Person() {
    }

    public Person(String id, String firstName, String lastName, String email, String phone) {
        this.id.setValue(id);
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.email.setValue(email);
        this.phone.setValue(phone);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }
}
