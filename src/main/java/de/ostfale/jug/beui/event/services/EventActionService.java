package de.ostfale.jug.beui.event.services;

import de.ostfale.jug.beui.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventActionService {

    private static final Logger log = LoggerFactory.getLogger(EventActionService.class);

    // services
    private final AddEventTaskService eventTaskService = new AddEventTaskService();
    private final SaveEventTaskService saveEventTaskService = new SaveEventTaskService();

    public void updateEvent(Event event) {
        log.debug("Update Event with ID: {}", event.getId());
        saveEventTaskService.setEvent(event);
        saveEventTaskService.startService();
    }

    public void addNewEvent() {
        log.debug("Add new default event...");
        Event newEvent = new Event();
        eventTaskService.setEvent(newEvent);
        eventTaskService.startService();
    }

    public void processAddEventServiceResult(GetEventTaskService getEventTaskService) {
        eventTaskService.getService().setOnSucceeded(e -> {
            log.info("Event successfully added...");
            getEventTaskService.startService();
        });
    }

    public void processUpdateEventServiceResult(SaveEventTaskService saveEventTaskService) {
        saveEventTaskService.getService().setOnSucceeded(e -> {
            log.info("Event successfully updated...");
        });
    }
}
