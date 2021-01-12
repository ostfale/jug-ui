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

public class SaveEventTaskService extends BaseTaskService<Void> {

    private static final Logger log = LoggerFactory.getLogger(SaveEventTaskService.class);

    private Event event;

    public SaveEventTaskService() {
        service = initService();
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private Service<Void> initService() {
        return new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        log.debug("Start service to save an event...");
                        final String json = JsonMapper.objectToJson(event);
                        String targetUrl = HttpHandler.EVENT_BASE + "/" + event.getId();
                        HttpResponse<String> response = new HttpHandler().putSync(targetUrl, json);
                        return null;
                    }
                };
            }
        };
    }
}
