package de.ostfale.jug.beui.event.services;

import de.ostfale.jug.beui.common.BaseTaskService;
import de.ostfale.jug.beui.event.domain.Event;
import de.ostfale.jug.beui.common.HttpHandler;
import de.ostfale.jug.beui.common.JsonMapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;

public class GetEventTaskService extends BaseTaskService<List<Event>> {

    private static final Logger log = LoggerFactory.getLogger(GetEventTaskService.class);

    public GetEventTaskService() {
        service = initService();
    }

    private Service<List<Event>> initService() {
        return new Service<>() {
            @Override
            protected Task<List<Event>> createTask() {
                return new Task<>() {
                    @Override
                    protected List<Event> call() throws Exception {
                        log.debug("GET all events...");
                        final HttpResponse<String> syncGet = new HttpHandler().getSync(HttpHandler.EVENT_BASE);
                        final String body = syncGet.body();
                        return JsonMapper.jsonToObjectList(body, Event.class);
                    }
                };
            }
        };
    }

    public SortedList<Event> getSortedList(ObservableList<Event> eventList) {
        SortedList<Event> sortedList = new SortedList<>(eventList);
        sortedList.setComparator(Comparator.comparing(Event::getDateTime));
        return sortedList;
    }

    public void updateList(List<Event> aList) {
        service.setOnSucceeded(e-> {
            var eventList = service.getValue();
            log.debug("Update List of events found {} events", eventList.size());
            aList.clear();
            aList.addAll(eventList);
        });
    }
}
