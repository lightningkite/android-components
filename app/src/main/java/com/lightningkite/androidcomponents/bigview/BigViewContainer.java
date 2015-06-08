package com.lightningkite.androidcomponents.bigview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by jivie on 6/2/15.
 */
public interface BigViewContainer {
    Activity getActivity();

    void onFinish(int id);

    void onSetResult(int id, int resultCode, Intent data);

    void onGoTo(int id, Class<? extends BigView> bigViewClass);

    void onGoTo(int id, Class<? extends BigView> bigViewClass, Bundle arguments);

    void onGoTo(int id, Class<? extends BigView> bigViewClass, Bundle arguments, int requestCode);

    void onStartIntentForResult(int id, Intent intent, int requestCode);
}
