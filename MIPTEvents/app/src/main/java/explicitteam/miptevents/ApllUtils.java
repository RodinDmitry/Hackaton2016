package explicitteam.miptevents;

import java.text.SimpleDateFormat;
import java.util.Date;

import explicitteam.miptevents.Database.DatabasePackage;

/**
 * Created by Anatoly on 20.11.2016.
 */

public class ApllUtils {
    public static String writeDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d MMM h:mm");
        return format.format(date);
    }

    public static String writeTags(DatabasePackage item) {
        return "Event";
    }
}
