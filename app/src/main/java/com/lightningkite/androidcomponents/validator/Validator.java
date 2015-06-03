package com.lightningkite.androidcomponents.validator;

import android.view.View;

/**
 * Created by jivie on 6/2/15.
 */
public abstract class Validator {
    public static final int RESULT_OK = 0;
    private OnResultListener mListener;

    public abstract boolean validate();

    public void result(int code, View view) {
        if (mListener != null) {
            mListener.onResult(code, view);
        }
    }

    public void result(int code) {
        if (mListener != null) {
            mListener.onResult(code, null);
        }
    }

    public void setListener(OnResultListener listener) {
        mListener = listener;
    }

    public interface OnResultListener {
        void onResult(int code, View view);
    }
}
