package de.ostfale.jug.beui.services.location;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.location.Location;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;

public class UpdateLocationTaskService extends BaseTaskService<Location> {

    private static final Logger log = LoggerFactory.getLogger(UpdateLocationTaskService.class);

    private Location location;

    public UpdateLocationTaskService() {
        service = initService();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Service<Location> initService() {
        return new Service<>() {
            @Override
            protected Task<Location> createTask() {
                return new Task<>() {
                    @Override
                    protected Location call() throws Exception {
                        final String json = JsonMapper.objectToJson(location);
                        final HttpResponse<String> httpResponse = new HttpHandler().putSync(HttpHandler.LOCATION_BASE + location.getId(), json);
                        log.info("Updated location {} with response: {}", location.getName(), httpResponse.statusCode());
                        return JsonMapper.jsonToObject(httpResponse.body(), Location.class);
                    }
                };
            }
        };
    }
}
