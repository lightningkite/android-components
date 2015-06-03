package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.lightningkite.androidcomponents.validator.DecimalValidator;
import com.lightningkite.androidcomponents.validator.EmailValidator;
import com.lightningkite.androidcomponents.validator.FormValidator;
import com.lightningkite.androidcomponents.validator.IntegerValidator;
import com.lightningkite.androidcomponents.validator.NameValidator;
import com.lightningkite.androidcomponents.validator.PasswordValidator;
import com.lightningkite.androidcomponents.validator.TextValidator;
import com.lightningkite.androidcomponents.validator.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jivie on 6/2/15.
 */
public class FormView extends LinearLayout {

    private HashMap<String, FormEntry> mEntries = new HashMap<>();
    private ArrayList<FormEntry> mEntryList = new ArrayList<>();

    private FormValidator mValidator = new FormValidator();
    private Validator.OnResultListener mDefaultResultListener = new Validator.OnResultListener() {
        @Override
        public void onResult(int code, View view) {
            view.setBackgroundColor(code == Validator.RESULT_OK ? 0x0 : 0x80FF0000);
        }
    };
    private int mDividerResource = -1;

    public int getDividerResource() {
        return mDividerResource;
    }

    public FormView setDividerResource(int mDividerResource) {
        this.mDividerResource = mDividerResource;
        return this;
    }

    public FormView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public FormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FormView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //-----------TEXT----------------


    public void setDefaultResultListener(Validator.OnResultListener defaultResultListener) {
        mDefaultResultListener = defaultResultListener;
    }

    private EntryTextView addEntryText(String id, String label, String hint, TextValidator validator) {
        EntryTextView view = new EntryTextView(getContext());
        view.setLabel(label);
        view.setHint(hint);
        addView(view);

        validator.setTextView(view.getTextView());
        validator.setListener(mDefaultResultListener);
        mValidator.add(validator);

        mEntries.put(id, view);
        mEntryList.add(view);

        if (mDividerResource != -1) {
            inflate(getContext(), mDividerResource, this);
        }

        return view;
    }

    public FormView addEntryText(String id, String label, String hint, boolean required) {
        addEntryText(id, label, hint, new TextValidator(null, required));
        return this;
    }

    public FormView addEntryPassword(String id, String label, String hint, int minLength) {
        EntryTextView v = addEntryText(id, label, hint, new PasswordValidator(null, minLength));
        v.getTextView().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return this;
    }

    public FormView addEntryEmail(String id, String label, String hint, boolean required) {
        EntryTextView v = addEntryText(id, label, hint, new EmailValidator(null, required));
        v.getTextView().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        return this;
    }

    public FormView addEntryName(String id, String label, String hint, boolean required) {
        EntryTextView v = addEntryText(id, label, hint, new NameValidator(null, required));
        v.getTextView().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        return this;
    }

    public FormView addEntryInteger(String id, String label, String hint, boolean required) {
        EntryTextView v = addEntryText(id, label, hint, new IntegerValidator(null, required));
        v.getTextView().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        return this;
    }

    public FormView addEntryDecimal(String id, String label, String hint, boolean required) {
        EntryTextView v = addEntryText(id, label, hint, new DecimalValidator(null, required));
        v.getTextView().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return this;
    }

    public FormView start() {
        mEntryList.get(mEntryList.size() - 1).notifyLast();
        return this;
    }

    public void focusOnFirst() {
        mEntryList.get(0).focus();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        for (Map.Entry<String, FormEntry> entry : mEntries.entrySet()) {
            Object data = entry.getValue().getData();
            if (data instanceof Long) {
                bundle.putLong(entry.getKey(), (Long) data);
            } else if (data instanceof String) {
                bundle.putString(entry.getKey(), (String) data);
            } else if (data instanceof Bundle) {
                bundle.putParcelable(entry.getKey(), (Bundle) data);
            } else if (data instanceof Integer) {
                bundle.putInt(entry.getKey(), (Integer) data);
            } else if (data instanceof Boolean) {
                bundle.putBoolean(entry.getKey(), (Boolean) data);
            } else {
                Log.e("FormView", "This data type is not supported at this time: " + data.getClass().getName());
            }
        }
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
        Bundle bundle = (Bundle) state;
        for (String key : bundle.keySet()) {
            FormEntry entry = mEntries.get(key);
            entry.setData(bundle.get(key));
        }
    }

    public void validate() {
        mValidator.validate();
    }
}
