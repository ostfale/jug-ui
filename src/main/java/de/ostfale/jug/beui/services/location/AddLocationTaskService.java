package de.ostfale.jug.beui.services.location;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.Location;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.http.HttpResponse;


public class AddLocationTaskService extends BaseTaskService<Location> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    private Location location;

    public AddLocationTaskService() {
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
                        log.debug("Start service to add new location...");
                        final String json = JsonMapper.objectToJson(location);
                        HttpResponse<String> response = new HttpHandler().postSync(HttpHandler.LOCATION_BASE, json);
                        if (response.statusCode() == 200) {
                            return JsonMapper.jsonToObject(response.body(), Location.class);
                        }
                        return null;
                    }
                };
            }
        };
    }
}
