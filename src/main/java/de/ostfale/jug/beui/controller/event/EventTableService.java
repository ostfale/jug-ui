package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.domain.event.Event;
import de.ostfale.jug.beui.domain.event.EventStatus;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EventTableService {

    private static final Logger log = LoggerFactory.getLogger(EventTableService.class);

    private final TableView<Event> tableView;

    TableColumn<Event, String> dateColumn = new TableColumn<>("Date");
    TableColumn<Event, String> nameColumn = new TableColumn<>("Title");
    TableColumn<Event, EventStatus> statusColumn = new TableColumn<>("Status");
    TableColumn<Event, String> locationColumn = new TableColumn<>("Location");

    public EventTableService(TableView<Event> tableView) {
        this.tableView = tableView;
    }

    public void initEventTableView() {
        addBinding();
        tableView.getColumns().addAll(dateColumn, nameColumn, statusColumn, locationColumn);
    }

    private void addBinding() {
        dateColumn.setCellValueFactory(cellData -> {
            Event event = cellData.getValue();
            return new ReadOnlyStringWrapper(event.getDateTime().toString());
        });

        PropertyValueFactory<Event, String> titleCellValueFactory = new PropertyValueFactory<>("title");
        nameColumn.setCellValueFactory(titleCellValueFactory);

        PropertyValueFactory<Event, EventStatus> statusCellValueFactory = new PropertyValueFactory<>("eventStatus");
        statusColumn.setCellValueFactory(statusCellValueFactory);

        locationColumn.setCellValueFactory(cellData -> {
            Event event = cellData.getValue();
            return new ReadOnlyStringWrapper(event.getLocation().getName());
        });
    }
}
