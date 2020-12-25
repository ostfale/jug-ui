package de.ostfale.jug.beui.services.event;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.event.Event;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;

public class AddEventTaskService extends BaseTaskService<Event> {

    private static final Logger log = LoggerFactory.getLogger(AddEventTaskService.class);

    private Event event;

    public AddEventTaskService() {
        service = initService();
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private Service<Event> initService() {
        return new Service<>() {
            @Override
            protected Task<Event> createTask() {
                return new Task<>() {
                    @Override
                    protected Event call() throws Exception {
                        log.debug("Start service to add new event...");
                        final String json = JsonMapper.objectToJson(event);
                        HttpResponse<String> response = new HttpHandler().postSync(HttpHandler.EVENT_BASE, json);
                        if (response.statusCode() == 200) {
                            return JsonMapper.jsonToObject(response.body(), Event.class);
                        }
                        log.error("Failure creating new event!");
                        return null;
                    }
                };
            }
        };
    }
}
