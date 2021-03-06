package de.ostfale.jug.beui.location.services;

import de.ostfale.jug.beui.common.BaseTaskService;
import de.ostfale.jug.beui.location.domain.Location;
import de.ostfale.jug.beui.common.HttpHandler;
import de.ostfale.jug.beui.common.JsonMapper;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.List;

public class GetLocationTaskService extends BaseTaskService<List<Location>> {

    private static final Logger log = LoggerFactory.getLogger(GetLocationTaskService.class);

    public GetLocationTaskService() {
        service = initService();
    }

    private Service<List<Location>> initService() {
        return new Service<>() {
            @Override
            protected Task<List<Location>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Location> call() throws Exception {
                        log.debug("GET all locations...");
                        final HttpResponse<String> syncGet = new HttpHandler().getSync(HttpHandler.LOCATION_BASE);
                        final String body = syncGet.body();
                        return JsonMapper.jsonToObjectList(body, Location.class);
                    }
                };
            }
        };
    }
}
