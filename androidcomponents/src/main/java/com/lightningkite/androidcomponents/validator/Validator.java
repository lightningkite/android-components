package com.lightningkite.androidcomponents.validator;

import android.view.View;

/**
 * Created by jivie on 6/2/15.
 */
public abstract class Validator {
    public static final int RESULT_OK = 0;
    private OnResultListener mListener;
    protected int mResult = 0;
    protected View mFirstView;

    public void validate() {
        mResult = RESULT_OK;
    }

    public void result(int code, View view) {
        mResult = code;
        mFirstView = view;
        if (mListener != null) {
            mListener.onResult(code, view);
        }
    }

    public void result(int code) {
        mResult = code;
        if (mListener != null) {
            mListener.onResult(code, null);
        }
    }

    public void setListener(OnResultListener listener) {
        mListener = listener;
    }

    public int getResult() {
        return mResult;
    }

    public View getView() {
        return mFirstView;
    }

    public interface OnResultListener {
        void onResult(int code, View view);
    }
}
