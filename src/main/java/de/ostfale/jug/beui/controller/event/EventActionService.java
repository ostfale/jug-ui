package de.ostfale.jug.beui.controller.event;

import de.ostfale.jug.beui.domain.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventActionService {

    private static final Logger log = LoggerFactory.getLogger(EventActionService.class);

    // services
    private final AddEventTaskService eventTaskService = new AddEventTaskService();


    public void addNewEvent() {
        log.debug("Add new default event...");
        Event newEvent = new Event();
    }

    private void processAddEventServiceResult(AddEventTaskService taskService) {
        taskService.getService().setOnSucceeded(e -> {
            log.info("Event successfully added...");
        //    getLocationTaskService.startService();
         //   lst_location.refresh();
        });
    }

}
