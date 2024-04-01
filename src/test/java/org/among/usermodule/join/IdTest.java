package org.among.usermodule.join;

import org.among.usermodule.common.Validation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IdTest {
    private List<String> emailList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        emailList.add("nara999@gmail.com");
        emailList.add("na999@naver.com");
        emailList.add("@gmail.com");
        emailList.add("sdfg@gmail");
        emailList.add("sdfg4d");
    }

    @Test
    void checkEmail() {
        Assertions.assertTrue(Validation.isValidEmail(emailList.get(0)));
        Assertions.assertTrue(Validation.isValidEmail(emailList.get(1)));
        Assertions.assertFalse(Validation.isValidEmail(emailList.get(2)));
        Assertions.assertFalse(Validation.isValidEmail(emailList.get(3)));
        Assertions.assertFalse(Validation.isValidEmail(emailList.get(4)));
    }
}
