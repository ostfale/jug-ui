package de.ostfale.jug.beui.ui.person;

import de.ostfale.jug.beui.controller.person.PersonController;
import de.ostfale.jug.beui.domain.Person;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.MasterDetailPane;


/**
 * Create main layout for person handler
 * Created :  04.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonLayoutHandler {

    private final Node layoutControl;
    private final PersonController personController;

    public PersonLayoutHandler() {
        personController = new PersonController();
        layoutControl = initLayout();
    }

    public Node getLayoutControl() {
        return layoutControl;
    }

    private Node initLayout() {
        BorderPane borderPane = new BorderPane();
        MasterDetailPane masterDetailPane = new MasterDetailPane();
        masterDetailPane.setMasterNode(createTableView());
        masterDetailPane.setShowDetailNode(true);
        masterDetailPane.setDetailSide(Side.BOTTOM);
        borderPane.setCenter(masterDetailPane);
        borderPane.setBottom(new PersonToolbar(personController).getToolBar());
        return borderPane;
    }

    private TableView<Person> createTableView() {
        TableView<Person> tableView = new TableView<>();
        tableView.setItems(personController.getList());
        createColumnBinding(tableView);
        return tableView;
    }

    private void createColumnBinding(TableView<Person> tableView) {
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        TableColumn<Person, String> emailNameCol = new TableColumn<>("Email");
        emailNameCol.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        TableColumn<Person, String> phoneNameCol = new TableColumn<>("Phone");
        phoneNameCol.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());

        tableView.getColumns().addAll(firstNameCol, lastNameCol, emailNameCol, phoneNameCol);
    }

}
