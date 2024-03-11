package com.example.b3tempo_nawfel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tools {

    // To prevent object instantiation
    private Tools() {
    }
    /*
     * --- Helpers methods ---
     *
     */
    /**
     * getNowDate("yyyy") would return "2024" at the time this comment is written
     *
     * @param pattern : pattern to be used by the date formatter (see {@link SimpleDateFormat}
     * class for date pattern explanations)
     * @return the device locale date at the time of the call. The date is formatted using the
     * given date pattern
     */
    public static String getNowDate(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.FRANCE);
        Date now = new Date();
        return sdf.format(now);
    }

}
