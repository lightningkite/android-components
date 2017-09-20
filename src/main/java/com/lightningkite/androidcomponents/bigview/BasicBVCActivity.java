package com.lightningkite.androidcomponents.bigview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

/**
 * A simple implementation of BigViewContainer.
 * This activity contains only one view, and going to other activities is supported through the
 * creation of an additional instance of this class.
 * This class must be exted to define the default bigview to use if none is specified.
 * Created by jivie on 6/2/15.
 */
public abstract class BasicBVCActivity extends AppCompatActivity implements BigViewContainer {
    
    public static final String EXTRA_BIGVIEW_NAME = "BasicBVCActivity.name";
    public BigView mView;

    /**
     * @return The default BigView class to use if none is specified.
     */
    protected abstract Class<? extends BigView> getDefaultViewClass();

    /**
     * @return The default BigViewContainer class to use in going to new activities.
     */
    protected abstract Class<? extends BasicBVCActivity> getBVCClass();

    protected void overridePendingTransition() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra(EXTRA_BIGVIEW_NAME);
        if (name == null) {
            mView = BigViewUtils.make(
                    getDefaultViewClass(),
                    this,
                    getIntent().getExtras(),
                    0
            );
        } else {
            mView = BigViewUtils.make(
                    name,
                    this,
                    getIntent().getExtras(),
                    0);
        }
        setContentView(mView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mView != null) mView.onResume();
    }

    @Override
    protected void onPause() {
        if (mView != null) mView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mView != null) mView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if (mView != null) mView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onFinish(int mId) {
        finish();
    }

    @Override
    public void onSetResult(int mId, int resultCode, Intent data) {
        setResult(resultCode, data);
    }

    @Override
    public void onGoTo(int id, Class<? extends BigView> bigViewClass) {
        Intent intent = new Intent(this, getBVCClass());
        intent.putExtra(EXTRA_BIGVIEW_NAME, bigViewClass.getName());
        startActivity(intent);
        overridePendingTransition();
    }

    @Override
    public void onGoTo(int id, Class<? extends BigView> bigViewClass, Bundle arguments) {
        Intent intent = new Intent(this, getBVCClass());
        intent.putExtras(arguments);
        intent.putExtra(EXTRA_BIGVIEW_NAME, bigViewClass.getName());
        startActivity(intent);
        overridePendingTransition();
    }

    @Override
    public void onGoTo(int id, Class<? extends BigView> bigViewClass, Bundle arguments, int requestCode) {
        Intent intent = new Intent(this, getBVCClass());
        intent.putExtras(arguments);
        intent.putExtra(EXTRA_BIGVIEW_NAME, bigViewClass.getName());
        startActivityForResult(intent, requestCode);
        overridePendingTransition();
    }

    @Override
    public void onStartIntentForResult(int id, Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mView.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
