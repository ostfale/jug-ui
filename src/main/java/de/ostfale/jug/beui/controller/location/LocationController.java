package de.ostfale.jug.beui.controller.location;

import de.ostfale.jug.beui.domain.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class LocationController implements Initializable {

    private static final Logger log = LoggerFactory.getLogger(LocationController.class);

    @FXML
    private ListView<Location> lst_person;
    @FXML
    private Button btn_new;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
