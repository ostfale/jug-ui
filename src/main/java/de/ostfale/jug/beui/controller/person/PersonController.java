package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.domain.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Data handler vor person entity. Retrieves and pushes data to an from person UI
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private final PersonTaskService taskService;
    private final ObservableList<Person> list = FXCollections.observableArrayList();

    public PersonController() {
        taskService = new PersonTaskService();
        processServiceResult(taskService);
    }

    public void refresh() {
        taskService.startService();
    }

    public ObservableList<Person> getList() {
        return list;
    }

    private void processServiceResult(PersonTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            final List<Person> personList = taskService.getService().getValue();
            log.debug("Update PersonList found {} persons", personList.size());
            list.clear();
            list.addAll(personList);
        });
    }
}
