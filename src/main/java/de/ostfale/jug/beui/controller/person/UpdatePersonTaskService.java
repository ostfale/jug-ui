package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.person.Person;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;

public class UpdatePersonTaskService extends BaseTaskService<Person> {

    private static final Logger log = LoggerFactory.getLogger(UpdatePersonTaskService.class);

    private Person person;

    public UpdatePersonTaskService() {
        service = initService();
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Service<Person> initService() {
        return new Service<>() {
            @Override
            protected Task<Person> createTask() {
                return new Task<Person>() {
                    @Override
                    protected Person call() throws Exception {
                        final String json = JsonMapper.objectToJson(person);
                        final HttpResponse<String> httpResponse = new HttpHandler().putSync(HttpHandler.PERSON_BASE + person.getId(), json);
                        log.info("Updated person {} {} with response: {}", person.getFirstName(), person.getLastName(), httpResponse.statusCode());
                        return JsonMapper.jsonToObject(httpResponse.body(), Person.class);
                    }
                };
            }
        };
    }
}
