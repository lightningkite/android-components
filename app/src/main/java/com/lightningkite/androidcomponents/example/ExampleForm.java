package com.lightningkite.androidcomponents.example;

import android.os.Bundle;

import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;
import com.lightningkite.androidcomponents.form.FormView;

/**
 * Created by jivie on 6/2/15.
 */
public class ExampleForm extends BigView {

    FormView mFormView;

    public ExampleForm(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        mFormView = new FormView(getContext())
                .addEntryText("username", "Username", "Enter your username")
                .addEntryText("password", "Password", "Enter your password")
                .addEntryText("fav_color", "Favorite Color", "Enter your favorite color")
                .start();
        addView(mFormView);
    }
}
