package de.ostfale.jug.beui.location.controller;

import de.ostfale.jug.beui.common.DataModel;
import de.ostfale.jug.beui.location.domain.Room;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class RoomController {

    private static Logger log = LoggerFactory.getLogger(RoomController.class);

    private DataModel<Room> roomModel;

    private final ListView<Room> lst_room;
    private TextField tf_name;
    private TextField tf_capacity;
    private TextArea ta_remark;

    public DataModel<Room> getRoomModel() {
        return roomModel;
    }

    public RoomController(ListView<Room> room, TextField roomName, TextField roomCapacity, TextArea roomRemark) {
        this.lst_room = room;
        this.tf_name = roomName;
        this.tf_capacity = roomCapacity;
        this.ta_remark = roomRemark;
        tf_name.disableProperty().bind(lst_room.getSelectionModel().selectedItemProperty().isNull());
        tf_capacity.disableProperty().bind(lst_room.getSelectionModel().selectedItemProperty().isNull());
        ta_remark.disableProperty().bind(lst_room.getSelectionModel().selectedItemProperty().isNull());
    }

    public void updateDataModel(ObservableList<Room> roomObservableList) {
        log.trace("Update room list...");
        if (roomModel == null) {
            roomModel = new DataModel<>();
            roomModel.currentObjectProperty().bind(lst_room.getSelectionModel().selectedItemProperty());
            lst_room.setItems(roomModel.getSortedList((l1, l2) -> l1.getName().compareToIgnoreCase(l2.getName())));
            this.roomModel.currentObjectProperty().addListener(roomListener);
        }
        roomModel.setObjectList(roomObservableList);
        if (roomModel.getObjectList().size() > 0) {
            lst_room.getSelectionModel().selectFirst();
        }
    }

    public void addRoom() {
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Location Dialog");
        tid.setHeaderText("Capacity >= 30");
        tid.setContentText("Add room name:");
        Optional<String> result = tid.showAndWait();
        result.ifPresent(name -> {
            Room room = new Room();
            room.setName(name);
            room.setCapacity(30);

            ObservableList<Room> newList = FXCollections.observableArrayList();
            newList.addAll(roomModel.getObjectList());
            newList.add(room);
            updateDataModel(newList);
        });
    }

    private ChangeListener<Room> roomListener = (obs, oldRoom, newRoom) -> {
        if (oldRoom != null) {
            tf_name.textProperty().unbindBidirectional(oldRoom.nameProperty());
            ta_remark.textProperty().unbindBidirectional(oldRoom.remarkProperty());
            tf_capacity.textProperty().unbindBidirectional(oldRoom.capacityProperty());
        }
        if (newRoom == null) {
            tf_name.clear();
            tf_capacity.clear();
            ta_remark.clear();
        } else {
            tf_name.textProperty().bindBidirectional(newRoom.nameProperty());
            ta_remark.textProperty().bindBidirectional(newRoom.remarkProperty());
            tf_capacity.textProperty().bindBidirectional(newRoom.capacityProperty(), new NumberStringConverter());
        }
    };
}
