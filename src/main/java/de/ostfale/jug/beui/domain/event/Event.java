package de.ostfale.jug.beui.domain.event;

import de.ostfale.jug.beui.domain.Location;
import de.ostfale.jug.beui.domain.Person;
import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private final StringProperty id = new SimpleStringProperty(this, "id", null);
    private final StringProperty title = new SimpleStringProperty(this, "title", "title");
    private final StringProperty content = new SimpleStringProperty(this, "content", "content");
    private final StringProperty remark = new SimpleStringProperty(this, "remark", "remark");
    private final ObjectProperty<LocalDateTime> dateTime = new SimpleObjectProperty<>(this, "dateTime", null);
    private final BooleanProperty isOnlineEvent = new SimpleBooleanProperty(this, "isOnlineEvent", true);
    private final BooleanProperty isComplete = new SimpleBooleanProperty(this, "isComplete", false);
    private final ObjectProperty<EventStatus> eventStatus = new SimpleObjectProperty<>(this, "eventStatus", EventStatus.PLANNED);
    private final ObjectProperty<Person> speaker = new SimpleObjectProperty<>(this, "speaker", null);
    private final ObjectProperty<Location> location = new SimpleObjectProperty<>(this, "location", null);

    private final List<Note> history = new ArrayList<>();

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

    public Person getSpeaker() {
        return speaker.get();
    }

    public ObjectProperty<Person> speakerProperty() {
        return speaker;
    }

    public void setSpeaker(Person speaker) {
        this.speaker.set(speaker);
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
}
