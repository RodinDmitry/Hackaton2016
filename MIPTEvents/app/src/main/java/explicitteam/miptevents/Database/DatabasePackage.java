package explicitteam.miptevents.Database;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by dmitry on 19.11.16.
 */
public class DatabasePackage implements Parcelable {

    private int id;

    private String title;

    private String description;

    private String place;

    private Date date;

    private int tagType;

    private int tagTheme;

    private int tagDepartment;

    private String reference;

    public DatabasePackage (int id,String title, String description, String place, Date date,
                            int tagType, int tagTheme, int tagDepartment,
                            String reference) {
        this.title = title;
        this.description = description;
        this.place = place;
        this.date = date;
        this.tagType = tagType;
        this.tagTheme = tagTheme;
        this.tagDepartment = tagDepartment;
        this.reference = reference;
    }

    protected DatabasePackage(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        place = in.readString();
        tagType = in.readInt();
        tagTheme = in.readInt();
        tagDepartment = in.readInt();
        reference = in.readString();
    }

    public static final Creator<DatabasePackage> CREATOR = new Creator<DatabasePackage>() {
        @Override
        public DatabasePackage createFromParcel(Parcel in) {
                Bundle bundle = in.readBundle();
                return new DatabasePackage(bundle.getInt("id"),
            bundle.getString("title"),
            bundle.getString("description"),
            bundle.getString("place"),
            new Date(bundle.getLong("date")), 1, 1, 1, "lol");
        }

        @Override
        public DatabasePackage[] newArray(int size) {
            return new DatabasePackage[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public  String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public int getTagType() {
        return tagType;
    }

    public int getTagTheme() {
        return tagTheme;
    }

    public int getTagDepartment() {
        return tagDepartment;
    }

    public String getPlace() {

        return place;
    }

    public String getReference() {
        return reference;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putString("place", place);
        bundle.putLong("date", date.getTime());
        dest.writeBundle(bundle);
    }
}
