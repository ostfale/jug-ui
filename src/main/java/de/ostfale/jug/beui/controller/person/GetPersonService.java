package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.person.Person;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.List;

public class GetPersonService extends BaseTaskService<List<Person>> {

    private static final Logger log = LoggerFactory.getLogger(GetPersonService.class);

    public GetPersonService() {
        service = initService();
    }

    private Service<List<Person>> initService() {
        return new Service<>() {
            @Override
            protected Task<List<Person>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Person> call() throws Exception {
                        log.debug("Start sync service to retrieve list of all persons...");
                        final HttpResponse<String> syncGet = new HttpHandler().getSync(HttpHandler.PERSON_BASE);
                        final String body = syncGet.body();
                        return JsonMapper.jsonToObjectList(body, Person.class);
                    }
                };
            }
        };
    }
}
