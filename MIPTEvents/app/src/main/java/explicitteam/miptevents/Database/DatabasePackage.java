package Database;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by dmitry on 19.11.16.
 */
public class DatabasePackage {

    private long eventId;

    private long ownerId;

    private String eventName;

    private Date eventStartDate;

    private Date eventStartTime;

    private String eventContent;

    private long locationId;

    private HashSet<String> tags;

    public DatabasePackage(long eventId, long ownerId, String eventName,
                           Date eventStartDate, Date eventStartTime, String eventContent,
                           long locationId) {
        this.eventId = eventId;
        this.ownerId = ownerId;
        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventStartTime = eventStartTime;
        this.eventContent = eventContent;
        this.locationId = locationId;
    }

    public long getEventId() {
        return eventId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public String getEventName() {
        return eventName;
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public Date getEventStartTime() {
        return eventStartTime;
    }

    public String getEventContent() {
        return eventContent;
    }

    public long getLocationId() {
        return locationId;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }
}
