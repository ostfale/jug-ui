package de.ostfale.jug.beui.domain.person;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class PersonModel {

    private final ObservableList<Person> personList = FXCollections.observableArrayList();
    private final ObjectProperty<Person> currentPerson = new SimpleObjectProperty<>();

    public Person getCurrentPerson() {
        return currentPerson.get();
    }

    public ObjectProperty<Person> currentPersonProperty() {
        return currentPerson;
    }

    public void setCurrentPerson(Person currentPerson) {
        this.currentPerson.set(currentPerson);
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(ObservableList<Person> aPersonList) {
        personList.clear();
        personList.addAll(aPersonList);
    }

    public SortedList<Person> getSortedList(ObservableList<Person> personList) {
        SortedList<Person> sortedList = new SortedList<>(personList);
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
