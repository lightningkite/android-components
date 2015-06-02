package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jivie on 6/2/15.
 */
public class FormView extends LinearLayout {

    private HashMap<String, FormEntry> mEntries = new HashMap<>();

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

    public FormView addEntryText(String id, String label, String hint) {
        EntryTextView view = new EntryTextView(getContext());
        view.setLabel(label);
        view.setHint(hint);
        addView(view);
        mEntries.put(id, view);
        return this;
    }

    public FormView start() {
        return this;
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
}
