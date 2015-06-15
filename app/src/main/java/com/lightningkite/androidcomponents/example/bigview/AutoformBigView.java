package com.lightningkite.androidcomponents.example.bigview;

import android.os.Bundle;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;
import com.lightningkite.androidcomponents.example.model.User;
import com.lightningkite.androidcomponents.form.FormView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * An example BigView demonstrating the FormView, which automatically creates a form.
 * Created by jivie on 6/2/15.
 */
public class AutoformBigView extends BigView {

    @InjectView(R.id.form_form)
    FormView mFormView;

    public AutoformBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_form_example, this);
        ButterKnife.inject(this);

        mFormView
                .addFromModel(User.class, null)
                .start();
        mFormView.focusOnFirst();
    }

    @OnClick(R.id.form_validate)
    void onValidateClicked() {
        mFormView.validate();
    }
}