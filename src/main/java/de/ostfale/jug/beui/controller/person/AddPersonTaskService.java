package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.Person;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

/**
 * Add new person to database
 * Created :  06.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class AddPersonTaskService extends BaseTaskService<Void> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Person person;

    public AddPersonTaskService() {
        service = initService();
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Service<Void> initService() {
        return new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        log.debug("Start service to add new person...");
                        try {
                            final String json = JsonMapper.objectToJson(person);
                            new HttpHandler().postSync(HttpHandler.PERSON_BASE, json);
                        } catch (IOException | InterruptedException e) {
                            log.error("Adding new person {} {} failed! Reason: {}", person.getFirstName(), person.getLastName(), e.getMessage());
                        }
                        return null;
                    }
                };
            }
        };
    }
}
