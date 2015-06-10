package com.lightningkite.androidcomponents.example;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BasicBVCActivity;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.example.bigview.SelectorBigView;

/**
 * An example implementation of BasicBVCActivity.
 * Created by jivie on 6/2/15.
 */
public class ExampleBasicBVCActivity extends BasicBVCActivity {

    @Override
    protected Class<? extends BigView> getDefaultViewClass() {
        return SelectorBigView.class;
    }

    @Override
    protected Class<? extends BasicBVCActivity> getBVCClass() {
        return ExampleBasicBVCActivity.class;
    }

    @Override
    protected void overridePendingTransition() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
}
