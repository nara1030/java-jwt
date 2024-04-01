package org.among.usermodule.join;

import org.among.usermodule.common.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PasswordTest {
    private static final List<String> passwordList = new ArrayList<>();

    @BeforeAll
    static void setUp() {
        // 전체 해당없음
        passwordList.add("비밀번호");
        // 길이 불만족
        passwordList.add("abcd");
        // 1개 이상
        // 대문자
        passwordList.add("AAAABBBBCCCC");
        // 소문자
        passwordList.add("aaaabbbbcccc");
        // 특수문자
        passwordList.add("!!!!@@@@####");
        // 숫자
        passwordList.add("019234515345");
        // 2개 이상
        // 대문자 & 소문자
        passwordList.add("AAAABBBBcccc");
        // 대문자 & 특수문자
        passwordList.add("AAAA@@BB!!!!");
        // 대문자 & 숫자
        passwordList.add("AAAABBBB2345");
        // 소문자 & 특수문자
        passwordList.add("aaaa!!!@cccc");
        // 소문자 & 숫자
        passwordList.add("aaaa12345678");
        // 특수문자 & 숫자
        passwordList.add("5678!!!@1234");
        // 3개 이상
        // 대문자 & 소문자 & 특수문자
        passwordList.add("aAbB!@!%%yuKj");
        // 대문자 & 소문자 & 숫자
        passwordList.add("aAbB3453yuKj");
        // 대문자 & 특수문자 & 숫자
        passwordList.add("1A3B!@!%%45K8");
        // 소문자 & 특수문자 & 숫자
        passwordList.add("asbd3453yu#j");
        // 전체 만족
        passwordList.add("aAbB!@!7yuKj");
    }

    @Test
    void checkPasswordWhenAllConditionsNotMeet() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(0)));
    }

    @Test
    void checkPasswordWhenLengthConditionsNotMeet() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(1)));
    }

    @Test
    void checkPasswordContainsUppercaseLetter() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(2)));
    }

    @Test
    void checkPasswordContainsLowercaseLetter() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(3)));
    }

    @Test
    void checkPasswordContainsSpecialCharacter() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(4)));
    }

    @Test
    void checkPasswordContainsNumber() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(5)));
    }

    @Test
    void checkPasswordContainsUppercaseLetterAndLowercaseLetter() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(6)));
    }

    @Test
    void checkPasswordContainsUppercaseLetterAndSpecialCharacter() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(7)));
    }

    @Test
    void checkPasswordContainsUppercaseLetterAndNumber() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(8)));
    }

    @Test
    void checkPasswordContainsLowercaseLetterAndSpecialCharacter() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(9)));
    }

    @Test
    void checkPasswordContainsLowercaseLetterAndNumber() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(10)));
    }

    @Test
    void checkPasswordContainsSpecialCharacterAndNumber() {
        Assertions.assertFalse(Validation.isValidPassword(passwordList.get(11)));
    }

    @Test
    void checkPasswordContainsUppercaseLetterAndLowercaseLetterAndSpecialCharacter() {
        Assertions.assertTrue(Validation.isValidPassword(passwordList.get(12)));
    }

    @Test
    void checkPasswordContainsUppercaseLetterAndLowercaseLetterAndNumber() {
        Assertions.assertTrue(Validation.isValidPassword(passwordList.get(13)));
    }

    @Test
    void checkPasswordContainsUppercaseLetterAndSpecialCharacterAndNumber() {
        Assertions.assertTrue(Validation.isValidPassword(passwordList.get(14)));
    }

    @Test
    void checkPasswordContainsLowercaseLetterAndSpecialCharacterAndNumber() {
        Assertions.assertTrue(Validation.isValidPassword(passwordList.get(15)));
    }

    @Test
    void checkPasswordWhenAllConditionsMeet() {
        Assertions.assertTrue(Validation.isValidPassword(passwordList.get(16)));
    }
}
