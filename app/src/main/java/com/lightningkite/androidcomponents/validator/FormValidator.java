package com.lightningkite.androidcomponents.validator;

import java.util.ArrayList;

/**
 * Created by jivie on 6/2/15.
 */
public class FormValidator implements Validator {
    private ArrayList<Validator> mValidators;

    public FormValidator() {
    }

    public FormValidator add(Validator validator) {
        mValidators.add(validator);
        return this;
    }

    @Override
    public boolean validate() {
        for (Validator validator : mValidators) {
            if (!validator.validate()) {
                return false;
            }
        }
        return true;
    }
}
