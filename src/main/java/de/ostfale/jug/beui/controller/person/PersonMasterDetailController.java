package de.ostfale.jug.beui.controller.person;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


/**
 * Layout controller for persons master detail pane
 * Created :  06.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonMasterDetailController {

    @FXML
    private ListView lst_person;

    @FXML
    private TextField txt_firstname;

    @FXML
    private TextField txt_lastname;

    @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_phone;

    @FXML
    private Button btn_refresh;

    @FXML
    private Button btn_new;

    @FXML
    private Button btn_update;

    @FXML
    private Button btn_delete;

}
