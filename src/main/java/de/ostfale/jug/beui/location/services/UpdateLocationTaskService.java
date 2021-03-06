package de.ostfale.jug.beui.location.services;

import de.ostfale.jug.beui.common.BaseTaskService;
import de.ostfale.jug.beui.common.HttpHandler;
import de.ostfale.jug.beui.common.JsonMapper;
import de.ostfale.jug.beui.location.domain.Location;
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
                        final HttpResponse<String> httpResponse = new HttpHandler().putSync(HttpHandler.LOCATION_PATH + location.getId(), json);
                        log.info("Updated location {} with response: {}", location.getName(), httpResponse.statusCode());
                        return JsonMapper.jsonToObject(httpResponse.body(), Location.class);
                    }
                };
            }
        };
    }
}
