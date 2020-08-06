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

    private final GetPersonsTaskService getPersonsTaskService;
    private final AddPersonTaskService addPersonTaskService;
    private final PersonDetailsController detailsController;
    private final ObservableList<Person> list = FXCollections.observableArrayList();

    public PersonController(PersonDetailsController aDetailsController) {
        detailsController = aDetailsController;
        getPersonsTaskService = new GetPersonsTaskService();
        addPersonTaskService = new AddPersonTaskService();
        processGetServiceResult(getPersonsTaskService);
        processAddServiceResult(addPersonTaskService);
    }

    public void refresh() {
        log.debug("Refresh list of persons");
        getPersonsTaskService.startService();
    }

    public void addPerson() {
        final Person person = detailsController.getPerson();
        if (person != null) {
            log.info("Add person with name {} {}", person.getFirstName(), person.getLastName());
            addPersonTaskService.setPerson(detailsController.getPerson());
            addPersonTaskService.startService();
        }
    }

    public ObservableList<Person> getList() {
        return list;
    }

    private void processAddServiceResult(AddPersonTaskService addPersonTaskService) {
        addPersonTaskService.getService().setOnSucceeded(e -> {
           log.info("Person successfully created...");
           refresh();
        });
    }

    private void processGetServiceResult(GetPersonsTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            final List<Person> personList = taskService.getService().getValue();
            log.debug("Update PersonList found {} persons", personList.size());
            list.clear();
            list.addAll(personList);
        });
    }
}
