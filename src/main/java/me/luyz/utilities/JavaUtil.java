package me.luyz.utilities;

import java.text.*;
import org.apache.commons.lang.time.*;
import java.util.concurrent.*;
import java.util.regex.*;

public final class JavaUtil {

    public static String HOUR_FORMAT;
    public static String MINUTE_FORMAT;
    public static DecimalFormat SECONDS_FORMATTER;

    public static Integer tryParseInt(final String string) {
        try {
            return Integer.parseInt(string);
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static Boolean tryParseBoolean(final String string) {
        try {
            return Boolean.parseBoolean(string);
        }
        catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public static boolean isAlphanumeric(final String input) {
        return input.matches("[a-zA-Z0-9]+");
    }

    public static String millisToTimer(final long millis) {
        final long seconds = millis / 1000L;
        if (seconds > 3600L) {
            return String.format(JavaUtil.HOUR_FORMAT, seconds / 3600L, seconds % 3600L / 60L, seconds % 60L);
        }
        return String.format(JavaUtil.MINUTE_FORMAT, seconds / 60L, seconds % 60L);
    }

    public static String formatDurationInt(final int input) {
        return DurationFormatUtils.formatDurationWords(input * 1000L, true, true);
    }

    public static String formatDurationLong(final long input) {
        return DurationFormatUtils.formatDurationWords(input, true, true);
    }

    public static String formatLongMin(final long time) {
        final long totalSecs = time / 1000L;
        return String.format("%02d:%02d", totalSecs / 60L, totalSecs % 60L);
    }

    public static String formatLongHour(final long time) {
        final long totalSecs = time / 1000L;
        final long seconds = totalSecs % 60L;
        final long minutes = totalSecs % 3600L / 60L;
        final long hours = totalSecs / 3600L;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static long formatLong(final String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            }
            else {
                final String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convertLong(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static long convertLong(final int value, final char unit) {
        switch (unit) {
            case 'y': {
                return value * TimeUnit.DAYS.toMillis(365L);
            }
            case 'M': {
                return value * TimeUnit.DAYS.toMillis(30L);
            }
            case 'd': {
                return value * TimeUnit.DAYS.toMillis(1L);
            }
            case 'h': {
                return value * TimeUnit.HOURS.toMillis(1L);
            }
            case 'm': {
                return value * TimeUnit.MINUTES.toMillis(1L);
            }
            case 's': {
                return value * TimeUnit.SECONDS.toMillis(1L);
            }
            default: {
                return -1L;
            }
        }
    }

    public static int formatInt(final String input) {
        if (input == null || input.isEmpty()) {
            return -1;
        }
        int result = 0;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            final char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            }
            else {
                final String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convertInt(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    private static int convertInt(final int value, final char unit) {
        switch (unit) {
            case 'd': {
                return value * 60 * 60 * 24;
            }
            case 'h': {
                return value * 60 * 60;
            }
            case 'm': {
                return value * 60;
            }
            case 's': {
                return value;
            }
            default: {
                return -1;
            }
        }
    }

    public static long parseTime(final String time) {
        long totalTime = 0L;
        boolean found = false;
        final Matcher matcher = Pattern.compile("\\d+\\D+").matcher(time);
        while (matcher.find()) {
            final String s = matcher.group();
            final long value = Long.parseLong(s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0]);
            final String s2;
            final String type = s2 = s.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1];
            switch (s2) {
                case "s": {
                    totalTime += value;
                    found = true;
                    continue;
                }
                case "m": {
                    totalTime += value * 60L;
                    found = true;
                    continue;
                }
                case "h": {
                    totalTime += value * 60L * 60L;
                    found = true;
                    continue;
                }
                case "d": {
                    totalTime += value * 60L * 60L * 24L;
                    found = true;
                    continue;
                }
                case "w": {
                    totalTime += value * 60L * 60L * 24L * 7L;
                    found = true;
                    continue;
                }
                case "M": {
                    totalTime += value * 60L * 60L * 24L * 30L;
                    found = true;
                    continue;
                }
                case "y": {
                    totalTime += value * 60L * 60L * 24L * 365L;
                    found = true;
                    continue;
                }
            }
        }
        return found ? (totalTime * 1000L) : -1L;
    }

    private JavaUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        JavaUtil.HOUR_FORMAT = "%02d:%02d:%02d";
        JavaUtil.MINUTE_FORMAT = "%02d:%02d";
        JavaUtil.SECONDS_FORMATTER = new DecimalFormat("#0.0");
    }


}
