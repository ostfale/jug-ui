package de.ostfale.jug.beui.domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {

    private final StringProperty id = new SimpleStringProperty(this, "id", null);
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty country = new SimpleStringProperty(this, "country", "DEU");
    private final StringProperty city = new SimpleStringProperty(this, "city", "Hamburg");
    private final StringProperty postalCode = new SimpleStringProperty(this, "postalCode", "");
    private final StringProperty streetName = new SimpleStringProperty(this, "streetName", "");
    private final StringProperty streetNumber = new SimpleStringProperty(this, "streetNumber", "");
}
