package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.Person;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

/**
 * Add new person to database
 * Created :  06.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class AddPersonTaskService extends BaseTaskService<Void> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private  Person person;

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
                        final Optional<String> optJson = JsonMapper.objectToJson(person);
                        optJson.ifPresent(s -> new HttpHandler().postAsync(HttpHandler.PERSON_BASE, optJson.get()));
                        return null;
                    }
                };
            }
        };
    }
}
