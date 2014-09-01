package org.gw.commons.utils;

import junit.framework.TestCase;
import org.junit.Assert;

public class StringUtilsTest extends TestCase {

    private static long millisInSecond = 1000;
    private static long millisInMinute = millisInSecond * 60;
    private static long millisInHour = millisInMinute * 60;
    private static long millisInDay = millisInHour * 24;

    private static long millis = 100 + 10 * millisInSecond + 30 * millisInMinute + 2 * millisInHour + 2 * millisInDay;

    public void testConvertMillisToStringWithSecondsWithMillis() throws Exception {
        // Given
        String expected = "2d 2h 30m 10s 100ms";

        // When
        String result = StringUtils.convertMillisToString(millis, true, true);

        // Then
        Assert.assertEquals(expected, result);
    }

    public void testConvertMillisToStringWithSecondsNoMillis() throws Exception {
        // Given
        String expected = "2d 2h 30m 10s";

        // When
        String result = StringUtils.convertMillisToString(millis, true, false);

        // Then
        Assert.assertEquals(expected, result);

    }

    public void testConvertMillisToStringNoSeconds() throws Exception {
        // Given
        String expected = "2d 2h 30m";

        // When
        String result = StringUtils.convertMillisToString(millis, false, false);

        // Then
        Assert.assertEquals(expected, result);

    }

    public void testConvertMillisToStringNoSecondsWithMillis() throws Exception {
        // Given
        String expected = "2d 2h 30m";

        // When
        String result = StringUtils.convertMillisToString(millis, false, true);

        // Then
        Assert.assertEquals(expected, result);

    }

}