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
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service which provides person data in a concurrent way using a service worker
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class GetPersonsTaskService extends BaseTaskService<List<Person>> {

    private static final Logger log = LoggerFactory.getLogger(GetPersonsTaskService.class);

    public GetPersonsTaskService() {
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