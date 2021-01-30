package de.ostfale.jug.beui.person.services;

import de.ostfale.jug.beui.common.BaseTaskService;
import de.ostfale.jug.beui.location.services.AddLocationTaskService;
import de.ostfale.jug.beui.person.domain.Person;
import de.ostfale.jug.beui.common.HttpHandler;
import de.ostfale.jug.beui.common.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class AddPersonTaskService extends BaseTaskService<Void> {

    private static final Logger log = LoggerFactory.getLogger(AddPersonTaskService.class);

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
