package com.lightningkite.androidcomponents.validator;

import java.util.ArrayList;

/**
 * Created by jivie on 6/2/15.
 */
public class FormValidator extends Validator {
    static public final int RESULT_PROBLEM = 1;
    private ArrayList<Validator> mValidators = new ArrayList<>();

    public FormValidator() {
    }

    public FormValidator add(Validator validator) {
        mValidators.add(validator);
        return this;
    }

    @Override
    public boolean validate() {
        boolean valid = true;
        for (Validator validator : mValidators) {
            if (!validator.validate()) {
                valid = false;
            }
        }
        result(valid ? RESULT_OK : RESULT_PROBLEM);
        return valid;
    }
}
