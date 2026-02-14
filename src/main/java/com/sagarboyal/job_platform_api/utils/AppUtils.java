package com.sagarboyal.job_platform_api.utils;

import java.util.List;

public class AppUtils {

    public static <T> void printList(List<T> list) {
        for (T item : list) {
            System.out.println(item);
        }
    }

    public static <T> T keepOldIfUnchanged(T oldValue, T newValue) {
        if (newValue == null) {
            return oldValue;
        }

        // Special handling for String
        if (newValue instanceof String str) {
            if (str.isBlank() || str.equals(oldValue)) {
                return oldValue;
            }
        } else if (newValue.equals(oldValue)) {
            return oldValue;
        }

        return newValue;
    }
}
