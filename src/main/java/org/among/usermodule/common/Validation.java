package org.among.usermodule.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String EMAIL_FORMAT_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String PASSWORD_FORMAT_REGEX1 = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{12,}$";
    private static final String PASSWORD_FORMAT_REGEX2 = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{12,}$";
    private static final String PASSWORD_FORMAT_REGEX3 = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=]).{12,}$";
    private static final String PASSWORD_FORMAT_REGEX4 = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{12,}$";
    private static final String PASSWORD_FORMAT_REGEX5 = "^(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{12,}$";


    /**
     * 이메일 형식 검사
     * @param email {String}
     * @return 유효(true), 비유효(false) {boolean}
     */
    public static boolean isValidEmail(String email) {
        if (StringUtils.isBlank(email)) return false;

        Pattern pattern = Pattern.compile(EMAIL_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 비밀번호 형식 검사
     * @param password {String}
     * @return 유효(true), 비유효(false) {boolean}
     */
     public static boolean isValidPassword(String password) {
        int countThatConditionsMeet = 0;

        if (password.matches(PASSWORD_FORMAT_REGEX2)) countThatConditionsMeet++;
        if (password.matches(PASSWORD_FORMAT_REGEX3)) countThatConditionsMeet++;
        if (password.matches(PASSWORD_FORMAT_REGEX4)) countThatConditionsMeet++;
        if (password.matches(PASSWORD_FORMAT_REGEX5)) countThatConditionsMeet++;
        if (password.matches(PASSWORD_FORMAT_REGEX1)) {
            countThatConditionsMeet = countThatConditionsMeet - 3;
        }

        return countThatConditionsMeet >= 1;
    }
}
