package com.lightningkite.androidcomponents.example.bigview;

import android.os.Bundle;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A very simple example of a BigView.
 * Created by jivie on 6/2/15.
 */
public class ExampleBigView extends BigView {

    @InjectView(R.id.example_label)
    TextView mTextView;

    public ExampleBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_example, this);
        ButterKnife.inject(this);
        mTextView.setText(String.valueOf(UUID.randomUUID().hashCode() % 9999));
    }

    @OnClick(R.id.example_label)
    void onTextClick() {
        goTo(ExampleBigView.class);
    }
}
