package de.ostfale.jug.beui.event.domain;

import de.ostfale.jug.beui.location.domain.Location;
import de.ostfale.jug.beui.location.domain.LocationStatus;
import de.ostfale.jug.beui.person.domain.Person;
import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private static final String DEFAULT_DATE_TIME = "01.01.1970 19:00";

    private final StringProperty id = new SimpleStringProperty(this, "id", null);
    private final StringProperty title = new SimpleStringProperty(this, "title", "title");
    private final StringProperty content = new SimpleStringProperty(this, "content", "content");
    private final StringProperty remark = new SimpleStringProperty(this, "remark", "remark");
    private final ObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>(this, "dateTime", getDefaultDT());
    private final BooleanProperty isOnlineEvent = new SimpleBooleanProperty(this, "isOnlineEvent", true);
    private final BooleanProperty isComplete = new SimpleBooleanProperty(this, "isComplete", false);
    private final ObjectProperty<EventStatus> eventStatus = new SimpleObjectProperty<>(this, "eventStatus", EventStatus.PLANNED);
    private final ObjectProperty<LocationStatus> locationStatus = new SimpleObjectProperty<>(this, "locationStatus", LocationStatus.PLANNED.PLANNED);
    private final ObjectProperty<ScheduleStatus> scheduleStatus = new SimpleObjectProperty<>(this, "scheduleStatus", ScheduleStatus.PLANNED);
    private final ObjectProperty<Location> location = new SimpleObjectProperty<>(this, "location", null);

    private final List<Person> speaker = new ArrayList<>();
    private final List<Note> history = new ArrayList<>();

    public List<Person> getSpeaker() {
        return speaker;
    }




    public LocationStatus getLocationStatus() {
        return locationStatus.get();
    }

    public ObjectProperty<LocationStatus> locationStatusProperty() {
        return locationStatus;
    }

    public void setLocationStatus(LocationStatus locationStatus) {
        this.locationStatus.set(locationStatus);
    }

    public ScheduleStatus getScheduleStatus() {
        return scheduleStatus.get();
    }

    public ObjectProperty<ScheduleStatus> scheduleStatusProperty() {
        return scheduleStatus;
    }

    public void setScheduleStatus(ScheduleStatus scheduleStatus) {
        this.scheduleStatus.set(scheduleStatus);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getRemark() {
        return remark.get();
    }

    public StringProperty remarkProperty() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public LocalDateTime getDateTime() {
        return dateTime.get();
    }

    public ObjectProperty<LocalDateTime> dateTimeProperty() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime.set(dateTime);
    }

    public boolean isIsOnlineEvent() {
        return isOnlineEvent.get();
    }

    public BooleanProperty isOnlineEventProperty() {
        return isOnlineEvent;
    }

    public void setIsOnlineEvent(boolean isOnlineEvent) {
        this.isOnlineEvent.set(isOnlineEvent);
    }

    public boolean isIsComplete() {
        return isComplete.get();
    }

    public BooleanProperty isCompleteProperty() {
        return isComplete;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete.set(isComplete);
    }

    public EventStatus getEventStatus() {
        return eventStatus.get();
    }

    public ObjectProperty<EventStatus> eventStatusProperty() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus.set(eventStatus);
    }

    public Location getLocation() {
        return location.get();
    }

    public ObjectProperty<Location> locationProperty() {
        return location;
    }

    public void setLocation(Location location) {
        this.location.set(location);
    }

    public List<Note> getHistory() {
        return history;
    }

    private LocalDateTime getDefaultDT() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return LocalDateTime.parse(DEFAULT_DATE_TIME, formatter);
    }
}
