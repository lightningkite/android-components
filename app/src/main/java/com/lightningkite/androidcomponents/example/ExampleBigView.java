package com.lightningkite.androidcomponents.example;

import android.os.Bundle;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnLongClick;

/**
 * Created by jivie on 6/2/15.
 */
public class ExampleBigView extends BigView {

    @InjectView(R.id.example_text)
    TextView mTextView;

    public ExampleBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mContainer.getContext(), R.layout.bigview_example, this);
        ButterKnife.inject(this);
        mTextView.setText(String.valueOf(UUID.randomUUID().hashCode() % 9999));
    }

    @OnLongClick(R.id.example_text)
    boolean onTextLongClick() {
        goTo(ExampleForm.class);
        return true;
    }
}
