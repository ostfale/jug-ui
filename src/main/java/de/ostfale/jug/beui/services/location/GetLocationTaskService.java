package de.ostfale.jug.beui.services.location;

import de.ostfale.jug.beui.controller.BaseTaskService;
import de.ostfale.jug.beui.domain.location.Location;
import de.ostfale.jug.beui.http.HttpHandler;
import de.ostfale.jug.beui.http.JsonMapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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

    public SortedList<Location> getSortedList(ObservableList<Location> locationList) {
        SortedList<Location> sortedList = new SortedList<>(locationList);
        sortedList.setComparator((l1, l2) -> l1.getName().compareToIgnoreCase(l2.getName()));
        return sortedList;
    }

    public void updateList(List<Location> aList) {
        service.setOnSucceeded(e -> {
            var locationList = service.getValue();
            log.debug("Update List of Locations found {} locations", locationList.size());
            aList.clear();
            aList.addAll(locationList);
        });
    }
}
