package de.ostfale.jug.beui.person.domain;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.util.Comparator;
import java.util.Objects;

public class Person implements Comparator<Person> {

    private final StringProperty id = new SimpleStringProperty(this, "id", null);
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName", "Aaron");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName", "Mustermann");
    private final StringProperty email = new SimpleStringProperty(this, "email", "mm@mail.de");
    private final StringProperty phone = new SimpleStringProperty(this, "phone", "123");
    private final StringProperty bio = new SimpleStringProperty(this, "bio", "");

    public Person() {
    }


    @Override
    public String toString() {
        return firstName.getValue() + " " + lastName.getValue();
    }

    public Person(String firstName, String lastName, String email, String phone, String bio) {
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.email.set(email);
        this.phone.set(phone);
        this.bio.set(bio);
    }

    @Override
    public int compare(Person p1, Person p2) {
        int result = p1.getLastName().compareToIgnoreCase(p2.getLastName());
        if (result == 0) {
            result = p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
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

    public String getBio() {
        return bio.get();
    }

    public StringProperty bioProperty() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio.set(bio);
    }
}
