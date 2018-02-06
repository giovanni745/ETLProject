package com.etl;
 
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null ? true : str.length() == 0;
    }
}
