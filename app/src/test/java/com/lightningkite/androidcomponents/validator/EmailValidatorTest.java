package com.lightningkite.androidcomponents.validator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by jivie on 6/8/15.
 */
public class EmailValidatorTest {

    private final String[] validEmails = {"shane@gmail.com",
            "srthompson-100@gmail.com", "srthompson.100@gmail.com",
            "srthompson111@srthompson.com", "srthompson-100@srthompson.net",
            "srthompson.100@srthompson.com.au", "srthompson@1.com",
            "srthompson@gmail.com.com", "srthompson+100@gmail.com",
            "srthompson-100@yahoo-test.com"};

    private final String[] invalidEmails = {"srthompson", "srthompson@.com.my",
            "srthompson123@gmail.a", "srthompson123@.com", "srthompson123@.com.com",
            ".srthompson@srthompson.com", "srthompson()*@gmail.com", "srthompson@%*.com",
            "srthompson..2002@gmail.com", "srthompson.@gmail.com",
            "srthompson@srthompson@gmail.com", "srthompson@gmail.com.1a"};

    private EmailValidator mValidator;

    @Test
    public void testValid() {
        mValidator = new EmailValidator(null, false);
        for (String testEmail : validEmails) {
            mValidator.validate(testEmail);
            assertEquals(Validator.RESULT_OK, mValidator.getResult());
        }
    }

    @Test
    public void testInvalid() {
        mValidator = new EmailValidator(null, false);
        for (String testEmail : invalidEmails) {
            mValidator.validate(testEmail);
            assertEquals(TextPatternValidator.RESULT_INVALID, mValidator.getResult());
        }
    }

    @Test
    public void testEmptyOptional() {
        mValidator = new EmailValidator(null, true);
        mValidator.validate("");
        assertEquals(Validator.RESULT_OK, mValidator.getResult());
    }

    @Test
    public void testEmptyRequired() {
        mValidator = new EmailValidator(null, false);
        mValidator.validate("");
        assertNotEquals(Validator.RESULT_OK, mValidator.getResult());
    }
}