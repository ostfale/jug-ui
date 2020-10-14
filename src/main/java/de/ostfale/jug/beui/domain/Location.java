package de.ostfale.jug.beui.domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private final StringProperty id = new SimpleStringProperty(this, "id", null);
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty country = new SimpleStringProperty(this, "country", "DEU");
    private final StringProperty city = new SimpleStringProperty(this, "city", "Hamburg");
    private final StringProperty postalCode = new SimpleStringProperty(this, "postalCode", "");
    private final StringProperty streetName = new SimpleStringProperty(this, "streetName", "");
    private final StringProperty streetNumber = new SimpleStringProperty(this, "streetNumber", "");

    private final ObjectProperty<Person> contact = new SimpleObjectProperty<>(this, "contact", null);

    private final List<Room> rooms = new ArrayList<>();


    public Location() {
    }


    public List<Room> getRooms() {
        return rooms;
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

    public Person getContact() {
        return contact.get();
    }

    public ObjectProperty<Person> contactProperty() {
        return contact;
    }

    public void setContact(Person contact) {
        this.contact.set(contact);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getStreetName() {
        return streetName.get();
    }

    public StringProperty streetNameProperty() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName.set(streetName);
    }

    public String getStreetNumber() {
        return streetNumber.get();
    }

    public StringProperty streetNumberProperty() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber.set(streetNumber);
    }

    @Override
    public String toString() {
        return getName();
    }

}
