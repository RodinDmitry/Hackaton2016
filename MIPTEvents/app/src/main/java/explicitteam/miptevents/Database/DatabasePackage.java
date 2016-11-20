package explicitteam.miptevents.Database;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by dmitry on 19.11.16.
 */
public class DatabasePackage implements Parcelable{

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

    protected DatabasePackage(Parcel in) {
        Bundle bund = in.readBundle();
        eventId = bund.getLong("eventId");
        ownerId = bund.getLong("ownerId");
        eventName = bund.getString("eventName");
        eventContent = bund.getString("eventContent");
        locationId = bund.getLong("locationId");
        eventStartDate = new Date(bund.getLong("dateStart"));
        eventStartTime = new Date(bund.getLong("timeStart"));
        tags = new HashSet<>(bund.getStringArrayList("tags"));
    }

    public static final Creator<DatabasePackage> CREATOR = new Creator<DatabasePackage>() {
        @Override
        public DatabasePackage createFromParcel(Parcel in) {
            return new DatabasePackage(in);
        }

        @Override
        public DatabasePackage[] newArray(int size) {
            return new DatabasePackage[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bund = new Bundle();
        bund.putLong("eventId", eventId);
        bund.putLong("ownerId", ownerId);
        bund.putString("eventName", eventName);
        bund.putString("eventContent", eventContent);
        bund.putLong("locationId", locationId);
        bund.putLong("dateStart", eventStartDate.getTime());
        bund.putLong("timeStart", eventStartTime.getTime());
        bund.putStringArrayList("tags", new ArrayList<String>(tags));
        dest.writeBundle(bund);
    }
}
