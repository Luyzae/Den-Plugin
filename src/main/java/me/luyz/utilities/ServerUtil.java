package me.luyz.utilities;

import java.text.*;
import me.luyz.servicies.impl.*;
import java.util.*;

public final class ServerUtil {

    private static final SimpleDateFormat timeDate;
    private static final SimpleDateFormat timeHour;

    public static String getDate() {
        ServerUtil.timeDate.setTimeZone(TimeZone.getTimeZone(ConfigService.TIME_ZONE));
        return ServerUtil.timeDate.format(new Date());
    }

    public static String getHour() {
        ServerUtil.timeHour.setTimeZone(TimeZone.getTimeZone(ConfigService.TIME_ZONE));
        return ServerUtil.timeHour.format(new Date());
    }

    private ServerUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        timeDate = new SimpleDateFormat(ConfigService.TIME_DATE);
        timeHour = new SimpleDateFormat(ConfigService.TIME_HOUR);
    }

}
