package org.example.utils;

public class Utils {
    public static boolean isValidUUID(String uuidString) {
        return uuidString.matches(Constants.UUID_REGEX);
    }
}
