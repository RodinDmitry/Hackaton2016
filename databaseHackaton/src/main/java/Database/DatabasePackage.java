package Database;

import java.util.Date;

/**
 * Created by dmitry on 19.11.16.
 */
public class DatabasePackage {

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
}
