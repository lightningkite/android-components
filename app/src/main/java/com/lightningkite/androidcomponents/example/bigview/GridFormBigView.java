package com.lightningkite.androidcomponents.example.bigview;

import android.os.Bundle;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;
import com.lightningkite.androidcomponents.example.model.CurrentWeather;
import com.lightningkite.androidcomponents.form.FormView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by jivie on 6/10/15.
 */
public class GridFormBigView extends BigView {

    @InjectView(R.id.form_form)
    FormView mFormView;

    public GridFormBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_grid_form_example, this);
        ButterKnife.inject(this);

        mFormView
                .addFromModel(CurrentWeather.class, null, "currentWeather")
                .start();
        mFormView.focusOnFirst();
    }

    @OnClick(R.id.form_validate)
    void onValidateClicked() {
        mFormView.validate();
    }
}
