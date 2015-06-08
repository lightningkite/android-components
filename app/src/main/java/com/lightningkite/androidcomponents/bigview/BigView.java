package com.lightningkite.androidcomponents.bigview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

/**
 * Created by jivie on 6/2/15.
 */
public class BigView extends FrameLayout {
    protected final BigViewContainer mContainer;
    protected final Bundle mArguments;
    protected final Activity mActivity;
    private int mId;

    public BigView(BigViewContainer container, Bundle arguments, int id) {
        super(container.getActivity());
        mContainer = container;
        mArguments = arguments;
        mActivity = container.getActivity();
        mId = id;
    }

    public Activity getActivity() {
        return mActivity;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onDestroy() {
    }

    public void onLowMemory() {
    }

    public void onResult(int requestCode, int resultCode, Intent data) {
    }

    public void goTo(Class<? extends BigView> bigViewClass) {
        mContainer.onGoTo(mId, bigViewClass);
    }

    public void goTo(Class<? extends BigView> bigViewClass, Bundle arguments) {
        mContainer.onGoTo(mId, bigViewClass, arguments);
    }

    public void goTo(Class<? extends BigView> bigViewClass, Bundle arguments, int requestCode) {
        mContainer.onGoTo(mId, bigViewClass, arguments, requestCode);
    }

    public void setResult(int resultCode, Intent data) {
        mContainer.onSetResult(mId, resultCode, data);
    }

    public void startIntentForResult(Intent intent, int requestCode) {
        mContainer.onStartIntentForResult(mId, intent, requestCode);
    }

    public void finish() {
        mContainer.onFinish(mId);
    }
}
