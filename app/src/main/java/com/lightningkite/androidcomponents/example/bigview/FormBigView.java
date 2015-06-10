package com.lightningkite.androidcomponents.example.bigview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;
import com.lightningkite.androidcomponents.form.EntrySelectBlock;
import com.lightningkite.androidcomponents.form.FormView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * An example BigView demonstrating the FormView, which automatically creates a form.
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
                .addTextEmail("email", "Email", "Enter your email", false)
                .addTextPassword("password", "Password", "Enter your password", 8)
                .addText("fav_color", "Favorite Color", "Enter your favorite color", false)
                .addTextInteger("lucky_num", "Lucky Number", "Enter your lucky number", false)
                .addTextDecimal("miles", "Miles", "Miles traveled (optional)", true)
                .addToggle("roundtrip", "Round Trip", true)
                .addSelect("testSelect", "Test Select", new EntrySelectBlock.EntrySelectListener() {
                    @Override
                    public void onSelect(EntrySelectBlock v) {
                        Log.d("FormBigView", "Test Select hit!");
                    }
                }, true)
                .addSpinner("fav_fruit",
                        "Favorite Fruit",
                        new ArrayAdapter<>(
                                getContext(),
                                R.layout.row_text,
                                new String[]{
                                        "Apple",
                                        "Pear",
                                        "Orange",
                                        "Banana",
                                        "Grapes",
                                        "Watermelon"
                                }
                        )
                )
                .start();
        mFormView.focusOnFirst();
    }

    @OnClick(R.id.form_validate)
    void onValidateClicked() {
        mFormView.validate();
    }
}
