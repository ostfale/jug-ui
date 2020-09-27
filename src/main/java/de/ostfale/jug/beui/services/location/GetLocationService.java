package de.ostfale.jug.beui.services.location;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.Location;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.List;

public class GetLocationService extends BaseTaskService<List<Location>> {

    private static final Logger log = LoggerFactory.getLogger(GetLocationService.class);

    public GetLocationService() {
        initService();
    }

    private Service<List<Location>> initService() {
        return new Service<>() {
            @Override
            protected Task<List<Location>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Location> call() throws Exception {
                        log.debug("Start sync service to retrieve list of all locations...");
                        final HttpResponse<String> syncGet = new HttpHandler().getSync(HttpHandler.LOCATION_BASE);
                        final String body = syncGet.body();
                        return JsonMapper.jsonToObjectList(body, Location.class);
                    }
                };
            }
        };
    }
}
