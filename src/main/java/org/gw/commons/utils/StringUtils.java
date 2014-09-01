package org.gw.commons.utils;

public class StringUtils {

    public static String convertMillisToString(long millis,
                                               boolean showSeconds, boolean showMillis) {
        if (millis <= 0) {
            return "0ms";
        }

        long millisInSecond = 1000;
        long millisInMinute = millisInSecond * 60;
        long millisInHour = millisInMinute * 60;
        long millisInDay = millisInHour * 24;

        int days = (int) Math.floor(millis / millisInDay);
        int hours = (int) Math.floor((millis - (days * millisInDay))
                / millisInHour);
        int mins = (int) Math
                .floor((millis - (days * millisInDay) - (hours * millisInHour))
                        / millisInMinute);
        int secs = (int) Math.floor((millis - (days * millisInDay)
                - (hours * millisInHour) - (mins * millisInMinute))
                / millisInSecond);
        int mils = (int) Math
                .floor((millis - (days * millisInDay) - (hours * millisInHour)
                        - (mins * millisInMinute) - (secs * millisInSecond)));
        StringBuffer runningTime = new StringBuffer();
        if (days > 0) {
            runningTime.append(days);
            runningTime.append("d ");
        }
        if (hours > 0) {
            runningTime.append(hours);
            runningTime.append("h ");
        }
        if (mins > 0) {
            runningTime.append(mins);
            runningTime.append("m ");
        }
        if (showSeconds && secs > 0) {
            runningTime.append(secs);
            runningTime.append("s ");

            if (showMillis) {
                runningTime.append(mils);
                runningTime.append("ms ");
            }
        }

        // Remove the last space
        if (runningTime.charAt(runningTime.length() - 1) == ' ') {
            runningTime.setLength(runningTime.length() - 1);
        }
        return runningTime.toString();
    }

    public static String convertMillisToString(long millis, boolean showSeconds) {
        return convertMillisToString(millis, showSeconds, false);
    }

    public static String convertMillisToString(long millis) {
        return convertMillisToString(millis, true);
    }

}
