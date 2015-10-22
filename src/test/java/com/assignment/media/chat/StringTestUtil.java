package com.assignment.media.chat;

public final class StringTestUtil {

    private static final String CHARACTER = "A";

    private StringTestUtil() {}

    public static String createStringWithLength(final int length) {
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < length; index++) {
            builder.append(CHARACTER);
        }
        return builder.toString();
    }
}
