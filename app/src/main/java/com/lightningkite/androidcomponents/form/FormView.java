package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;

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

    private EntryTextBlock addText(String id, String label, String hint, TextValidator validator) {
        EntryTextBlock v = new EntryTextBlock(getContext());
        v.setLabel(label);
        v.setHint(hint);

        if (validator != null) {
            validator.setTextView(v.getTextView());
            validator.setListener(mDefaultResultListener);
            mValidator.add(validator);
        }

        addBlock(id, v, v);

        return v;
    }

    public FormView addText(String id, String label, String hint, boolean optional) {
        addText(id, label, hint, new TextValidator(null, optional));
        return this;
    }

    public FormView addTextPassword(String id, String label, String hint, int minLength) {
        EntryTextBlock v = addText(id, label, hint, new PasswordValidator(null, minLength));
        v.getTextView().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        return this;
    }

    public FormView addTextEmail(String id, String label, String hint, boolean optional) {
        EntryTextBlock v = addText(id, label, hint, new EmailValidator(null, optional));
        v.getTextView().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        return this;
    }

    public FormView addTextName(String id, String label, String hint, boolean optional) {
        EntryTextBlock v = addText(id, label, hint, new NameValidator(null, optional));
        v.getTextView().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        return this;
    }

    public FormView addTextInteger(String id, String label, String hint, boolean optional) {
        EntryTextBlock v = addText(id, label, hint, new IntegerValidator(null, optional));
        v.getTextView().setInputType(InputType.TYPE_CLASS_NUMBER);
        return this;
    }

    public FormView addTextDecimal(String id, String label, String hint, boolean optional) {
        EntryTextBlock v = addText(id, label, hint, new DecimalValidator(null, optional));
        v.getTextView().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return this;
    }

    public FormView addToggle(String id, String label, boolean checked) {
        EntryToggleBlock v = new EntryToggleBlock(getContext());
        v.setChecked(checked);
        v.setLabel(label);

        addBlock(id, v, v);

        return this;
    }

    public FormView addSelect(String id, String label, EntrySelectBlock.EntrySelectListener listener, boolean optional) {
        final EntrySelectBlock v = new EntrySelectBlock(getContext());
        v.setSelectListener(listener);
        v.setLabel(label);

        if (!optional) {
            mValidator.add(new Validator() {
                @Override
                public void validate() {
                    super.validate();
                    if (mResult != RESULT_OK) return;
                    if (v.getSelectedId() == -1) {
                        result(1, v);
                    }
                }
            });
        }

        addBlock(id, v, v);

        return this;
    }

    public FormView addSpinner(String id, String label, SpinnerAdapter adapter) {
        final EntrySpinnerBlock v = new EntrySpinnerBlock(getContext());
        v.setAdapter(adapter);
        v.setLabel(label);

        addBlock(id, v, v);

        return this;
    }

    public FormView start() {
        mEntryList.get(mEntryList.size() - 1).notifyLast();
        return this;
    }

    public void focusOnFirst() {
        mEntryList.get(0).focus();
    }

    public void setData(String id, Object data) {
        mEntries.get(id).setData(data);
    }

    public Object getData(String id) {
        return mEntries.get(id).getData();
    }


    protected void addBlock(String id, View view, FormEntry block) {
        mEntries.put(id, block);
        mEntryList.add(block);
        addView(view);

        if (mDividerResource != -1) {
            inflate(getContext(), mDividerResource, this);
        }
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
        if (mValidator.getResult() != Validator.RESULT_OK) {
            mValidator.getView().requestFocus();
        }
    }
}
