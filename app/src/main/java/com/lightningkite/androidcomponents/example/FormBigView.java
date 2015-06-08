package com.lightningkite.androidcomponents.example;

import android.os.Bundle;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;
import com.lightningkite.androidcomponents.form.FormView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by jivie on 6/2/15.
 */
public class FormBigView extends BigView {

    @InjectView(R.id.form_form)
    FormView mFormView;

    public FormBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_form_example, this);
        ButterKnife.inject(this);

        mFormView
                .setDividerResource(R.layout.view_horizontal_line)
                .addEntryEmail("email", "Email", "Enter your email", false)
                .addEntryPassword("password", "Password", "Enter your password", 8)
                .addEntryText("fav_color", "Favorite Color", "Enter your favorite color", false)
                .addEntryInteger("lucky_num", "Lucky Number", "Enter your lucky number", false)
                .addEntryDecimal("miles", "Miles", "Miles traveled (optional)", true)
                .addEntryToggle("roundtrip", "Round Trip", true)
                .start();
        mFormView.focusOnFirst();
    }

    @OnClick(R.id.form_validate)
    void onValidateClicked() {
        mFormView.validate();
    }
}
