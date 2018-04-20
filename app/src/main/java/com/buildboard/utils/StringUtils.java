package com.buildboard.utils;

public class StringUtils {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String NAME_PATTERN = "^[A-Za-z0-9\\s]{1,}[\\.]{0,1}[A-Za-z0-9\\s]{0,}$";
    private static final String NUMBER_PATTERN = "^[0-9]+$";

    public static boolean isValidEmailId(String emailId) {

        return emailId.trim().matches(EMAIL_PATTERN);
    }

    public static boolean isValidName(String name) {

        return name.trim().matches(NAME_PATTERN);
    }

    public static boolean isValidNumber(String number) {

        return number.trim().matches(NUMBER_PATTERN);
    }
}