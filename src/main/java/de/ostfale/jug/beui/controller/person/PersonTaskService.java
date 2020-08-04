package de.ostfale.jug.beui.controller.person;

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
public class PersonTaskService {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private final Service<List<Person>> service;
    private boolean hasBeenStarted = false;

    public PersonTaskService() {
        service = initService();
    }

    public Service<List<Person>> getService() {
        return service;
    }

    public void startService() {
        if (hasBeenStarted) {
            service.restart();
        } else {
            hasBeenStarted = true;
            service.start();
        }
    }

    private Service<List<Person>> initService() {
        return new Service<>() {
            @Override
            protected Task<List<Person>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Person> call() throws Exception {
                        final CompletableFuture<HttpResponse<String>> asyncGet = new HttpHandler().getAsync(HttpHandler.PERSON_BASE);
                        final String body = asyncGet.get().body();
                        return JsonMapper.jsonToObjectList(body, Person.class);
                    }
                };
            }
        };
    }
}
