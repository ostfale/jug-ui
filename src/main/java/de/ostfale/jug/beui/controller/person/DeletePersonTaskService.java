package de.ostfale.jug.beui.controller.person;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.Person;
import de.ostfale.jug.beui.http.HttpHandler;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import static de.ostfale.jug.beui.http.HttpHandler.PERSON_BASE;

/**
 * Service task to delete a person from database
 * Created :  07.08.2020
 *
 * @author : Uwe Sauerbrei
 */
public class DeletePersonTaskService extends BaseTaskService<HttpResponse<String>> {

    private static final Logger log = LoggerFactory.getLogger(DeletePersonTaskService.class);

    private Person person;

    public DeletePersonTaskService() {
        service = initService();
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    private Service<HttpResponse<String>> initService() {
        return new Service<>() {
            @Override
            protected Task<HttpResponse<String>> createTask() {
                return new Task<>() {
                    @Override
                    protected HttpResponse<String> call() throws Exception {
                        log.debug("Delete person {} {}", person.getFirstName(), person.getLastName());
                        final CompletableFuture<HttpResponse<String>> completableFuture = new HttpHandler().deleteAsync(PERSON_BASE + person.getId());
                        HttpResponse<String> response = completableFuture.get();
                        log.debug("Response for deletion: {}", response.statusCode());
                        return response;
                    }
                };
            }
        };
    }
}
