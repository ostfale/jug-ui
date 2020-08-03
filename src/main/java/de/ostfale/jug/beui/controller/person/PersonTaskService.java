package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.domain.Person;
import de.ostfale.jug.beui.http.HttpHandler;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Service which provides person data in a concurrent way using a service worker
 * Created :  31.07.2020
 *
 * @author : Uwe Sauerbrei
 */
public class PersonTaskService {

  /*  private final Worker<List<Person>> worker;

    public PersonTaskService() {
        worker = initService();
    }*/

   /* public Worker<List<Person>> getWorker() {
        return worker;
    }*/

   /* private Worker<List<Person>> initService() {
        Service<List<Person>> service = new Service<>() {
            @Override
            protected Task<List<Person>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Person> call() throws Exception {
                        HttpHandler httpHandler = new HttpHandler();
                        final CompletableFuture<String> completableFuture = httpHandler.getAsync("http://localhost:8080/api/v1/person/");
                    }
                }
            }
        }

    }*/
}
