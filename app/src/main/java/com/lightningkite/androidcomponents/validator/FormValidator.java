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
    public void validate() {
        int result = RESULT_OK;
        for (Validator validator : mValidators) {
            validator.validate();
            int subresult = validator.getResult();
            if (subresult != 0) {
                result = RESULT_PROBLEM;
                mFirstView = validator.getView();
            }
        }
        result(result);
    }
}
