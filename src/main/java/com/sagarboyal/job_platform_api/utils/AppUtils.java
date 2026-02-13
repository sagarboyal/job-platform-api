package com.sagarboyal.job_platform_api.utils;

import java.util.List;

public class AppUtils {

    public static <T> void printList(List<T> list) {
        for (T item : list) {
            System.out.println(item);
        }
    }

}
