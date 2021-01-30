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


public class AddLocationTaskService extends BaseTaskService<Void> {

    private static final Logger log = LoggerFactory.getLogger(AddLocationTaskService.class);

    private Location location;

    public AddLocationTaskService() {
        service = initService();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Service<Void> initService() {
        return new Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        log.debug("Start service to add new location...");
                        try {
                            final String json = JsonMapper.objectToJson(location);
                            HttpResponse<String> response = new HttpHandler().postSync(HttpHandler.LOCATION_BASE, json);
                            if (response.statusCode() == 201) {
                                log.info("Added new location successfully...");
                            }
                        } catch (Exception e) {
                            log.error("Adding new Location failed! Reason: {}", e.getMessage());
                        }
                        return null;
                    }
                };
            }
        };
    }
}
