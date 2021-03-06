package de.ostfale.jug.beui.person.services;

import de.ostfale.jug.beui.common.BaseTaskService;
import de.ostfale.jug.beui.person.domain.Person;
import de.ostfale.jug.beui.common.HttpHandler;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;

import static de.ostfale.jug.beui.common.HttpHandler.PERSON_BASE;

public class DeletePersonTaskService extends BaseTaskService<Void> {

    private static final Logger log = LoggerFactory.getLogger(DeletePersonTaskService.class);

    private Person person;

    public DeletePersonTaskService() {
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
                    protected Void call() throws Exception {
                        final HttpResponse<String> result = new HttpHandler().deleteSync(PERSON_BASE + person.getId());
                        log.debug("Delete person {} {} with Response Status: {}", person.getFirstName(), person.getLastName(), result.statusCode());
                        return null;
                    }
                };
            }
        };
    }
}
