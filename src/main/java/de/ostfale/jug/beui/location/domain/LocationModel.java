package de.ostfale.jug.beui.location.domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

public class LocationModel {

    private final ObservableList<Location> locationList = FXCollections.observableArrayList();
    private final ObjectProperty<Location> currentLocation = new SimpleObjectProperty<>();

    public void setLocationList(ObservableList<Location> aLocationList) {
        locationList.clear();
        locationList.addAll(aLocationList);
    }

    public SortedList<Location> getSortedList(ObservableList<Location> locationList) {
        SortedList<Location> sortedList = new SortedList<>(locationList);
        sortedList.setComparator((p1, p2) -> {
            return p1.getName().compareToIgnoreCase(p2.getName());
        });
        return sortedList;
    }

    public Location getCurrentLocation() {
        return currentLocation.get();
    }

    public ObjectProperty<Location> currentLocationProperty() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation.set(currentLocation);
    }

    public ObservableList<Location> getLocationList() {
        return locationList;
    }
}
