package explicitteam.miptevents;

import java.text.SimpleDateFormat;
import java.util.Date;

import explicitteam.miptevents.Database.DatabasePackage;

/**
 * Created by Anatoly on 20.11.2016.
 */

public class ApllUtils {
    public static String writeDate(Date date, Date time) {
        SimpleDateFormat format = new SimpleDateFormat("d MMM");
        String result = format.format(date);
        format = new SimpleDateFormat("h mm");
        result += ' ' + format.format(time);
        return result;
    }

    public static String writeTags(DatabasePackage item) {
        StringBuilder str = new StringBuilder();
        for (String tag: item.getTags()) {
            str.append(tag).append(' ');
        }
        return str.toString();
    }

    public static String getLoginString() {
        return "VasyaPuk";
    }
}
