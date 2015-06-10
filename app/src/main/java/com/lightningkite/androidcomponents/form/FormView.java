package com.lightningkite.androidcomponents.form;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.validator.DecimalValidator;
import com.lightningkite.androidcomponents.validator.EmailValidator;
import com.lightningkite.androidcomponents.validator.FormValidator;
import com.lightningkite.androidcomponents.validator.IntegerValidator;
import com.lightningkite.androidcomponents.validator.NameValidator;
import com.lightningkite.androidcomponents.validator.PasswordValidator;
import com.lightningkite.androidcomponents.validator.TextValidator;
import com.lightningkite.androidcomponents.validator.Validator;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Can automatically create forms for you!
 * Created by jivie on 6/2/15.
 */
public class FormView extends FrameLayout {

    private HashMap<String, FormEntry> mEntries = new HashMap<>();
    private ArrayList<FormEntry> mEntryList = new ArrayList<>();

    private FormValidator mValidator = new FormValidator();
    private Validator.OnResultListener mDefaultResultListener = new Validator.OnResultListener() {
        @Override
        public void onResult(int code, View view) {
            view.setBackgroundColor(code == Validator.RESULT_OK ? 0x0 : 0x80FF0000);
        }
    };

    static private int mDefaultLayoutRes = R.layout.entry_form;

    public static int getDefaultLayoutRes() {
        return mDefaultLayoutRes;
    }

    public static void setDefaultLayoutRes(int defaultLayoutRes) {
        mDefaultLayoutRes = defaultLayoutRes;
    }

    private int mLayoutRes = mDefaultLayoutRes;

    @InjectView(R.id.form_layout)
    ViewGroup mLayout;
    @Optional
    @InjectView(R.id.form_label)
    TextView mLabelTextView;

    public FormView(Context context) {
        super(context);
        init();
    }

    public FormView(Context context, @LayoutRes int layoutRes) {
        super(context);
        mLayoutRes = layoutRes;
        init();
    }

    public FormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FormView,
                0, 0);
        mLayoutRes = a.getResourceId(R.styleable.FormView_layout, R.layout.entry_form);
        init();
        if (mLabelTextView != null) {
            mLabelTextView.setText(a.getString(R.styleable.FormView_labelText));
        }
    }

    private void init() {
        inflate(getContext(), mLayoutRes, this);
        ButterKnife.inject(this);
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
        mLayout.addView(view);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("superstate", super.onSaveInstanceState());
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
        Bundle bundle = (Bundle) state;
        super.onRestoreInstanceState(bundle.getParcelable("superstate"));
        for (String key : bundle.keySet()) {
            if (key.equals("superstate")) continue;
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

    //REFLECTION

    private List<Field> getOrderedFields(Class modelClass) {
        SparseArray<Field> ordered = new SparseArray<>();
        List<Field> total = new ArrayList<>();
        for (Field field : modelClass.getDeclaredFields()) {
            AutoFormPosition position = field.getAnnotation(AutoFormPosition.class);
            if (position != null) {
                ordered.put(position.value(), field);
            } else {
                total.add(field);
            }
        }
        for (int i = 0; i < ordered.size(); i++) {
            int key = ordered.keyAt(i);
            if (key < total.size()) {
                total.add(key, ordered.get(key));
            } else {
                total.add(ordered.get(key));
            }
        }
        return total;
    }

    private String getFieldName(String prepend, Field field) {
        return prepend + "." + field.getName();
    }

    @SuppressWarnings("unchecked")
    public FormView addFromModel(Class modelClass, @Nullable SpinnerAdapterFetcher fetcher, String prepend) {
        for (Field field : getOrderedFields(modelClass)) {
            if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                if (field.isAnnotationPresent(AutoFormIgnore.class)) continue;
                boolean deep = false;
                if (field.isAnnotationPresent(AutoFormDeep.class)) deep = true;

                String name = getFieldName(prepend, field);
                String properName;
                AutoFormDisplayName displayNameAnnotation = field.getAnnotation(AutoFormDisplayName.class);
                if (displayNameAnnotation != null) {
                    properName = displayNameAnnotation.value();
                } else {
                    properName = toProperName(field.getName());
                }

                Class type = field.getType();
                if (type == String.class) {
                    if (name.toLowerCase().contains("email")) {
                        addTextEmail(name, properName, properName, true);
                    } else if (name.toLowerCase().contains("name")) {
                        addTextName(name, properName, properName, true);
                    } else if (name.toLowerCase().contains("password")) {
                        addTextPassword(name, properName, properName, 8);
                    } else {
                        addText(name, properName, properName, true);
                    }
                } else if (type == int.class || type == long.class) {
                    addTextInteger(name, properName, properName, false);
                } else if (type == Integer.class || type == Long.class) {
                    addTextInteger(name, properName, properName, true);
                } else if (type == Float.class || type == Double.class) {
                    addTextDecimal(name, properName, properName, true);
                } else if (type == float.class || type == double.class) {
                    addTextDecimal(name, properName, properName, true);
                } else if (type == boolean.class) {
                    addToggle(name, properName, false);
                } else if (type == Boolean.class) {
                    addToggle(name, properName, true);
                } else {
                    SpinnerAdapter spinnerAdapter;
                    if (fetcher != null && (spinnerAdapter = fetcher.fetch(type)) != null) {
                        addSpinner(name, properName, spinnerAdapter);
                    } else if (deep) {
                        addFromModel(type, fetcher, name);
                    }
                }

            }
        }
        return this;
    }

    public void loadData(Object object, @Nullable SpinnerAdapterFetcher fetcher, String prepend) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                if (field.isAnnotationPresent(AutoFormIgnore.class)) continue;
                boolean deep = false;
                if (field.isAnnotationPresent(AutoFormDeep.class)) deep = true;
                try {
                    Object fieldData;
                    fieldData = field.get(object);
                    String name = getFieldName(prepend, field);
                    Class type = field.getType();
                    if (type == String.class) {
                        mEntries.get(name).setData(fieldData);
                    } else if (type == Integer.class || type == Long.class) {
                        mEntries.get(name).setData(String.valueOf(fieldData));
                    } else if (type == Float.class || type == Double.class) {
                        mEntries.get(name).setData(String.valueOf(fieldData));
                    } else if (type == Boolean.class) {
                        mEntries.get(name).setData(fieldData);
                    } else {
                        if (fetcher != null && fetcher.fetch(type) != null) {
                            mEntries.get(name).setData(fetcher.getId(type, fieldData));
                        } else if (deep) {
                            loadData(type, fetcher, name);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveData(Object object, @Nullable SpinnerAdapterFetcher fetcher, String prepend) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
                if (field.isAnnotationPresent(AutoFormIgnore.class)) continue;
                boolean deep = false;
                if (field.isAnnotationPresent(AutoFormDeep.class)) deep = true;
                try {
                    String name = getFieldName(prepend, field);
                    Object value = mEntries.get(name);

                    Class type = field.getType();
                    if (type == String.class ||
                            type == Integer.class ||
                            type == Long.class ||
                            type == Boolean.class ||
                            type == Double.class ||
                            type == Float.class) {
                        field.set(object, value);
                    } else if (type == int.class) {
                        field.setInt(object, (Integer) value);
                    } else if (type == long.class) {
                        field.setLong(object, (Long) value);
                    } else if (type == float.class) {
                        field.setFloat(object, (Float) value);
                    } else if (type == double.class) {
                        field.setDouble(object, (Double) value);
                    } else if (type == boolean.class) {
                        field.setBoolean(object, (Boolean) value);
                    } else {
                        SpinnerAdapter spinnerAdapter;
                        if (fetcher != null && (spinnerAdapter = fetcher.fetch(type)) != null) {
                            field.set(object, spinnerAdapter.getItem((Integer) value));
                        } else if (deep) {
                            saveData(type, fetcher, name);
                        }
                    }
                } catch (IllegalAccessException | ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String toProperName(String name) {
        StringBuilder builder = new StringBuilder();
        int camelCaseState = 0;
        int underscoreState = 0;
        boolean firstChar = true;
        for (char c : name.toCharArray()) {
            if (firstChar) {
                firstChar = false;
                if (c >= 'a' && c <= 'z') {
                    builder.append((char) (c - 0x20));
                    continue;
                }
            }
            if (c == '_') {
                builder.append(' ');
                underscoreState = 1;
                continue;
            }
            if (underscoreState == 1) {
                if (c >= 'a' && c <= 'z') {
                    builder.append((char) (c - 0x20));
                    continue;
                }
                underscoreState = 0;
            }
            if (camelCaseState == 0) {
                if (c >= 'a' && c <= 'z') {
                    camelCaseState++;
                } else {
                    camelCaseState = 0;
                }
            } else if (camelCaseState == 1) {
                if (c >= 'A' && c <= 'Z') {
                    builder.append(' ');
                } else {
                    camelCaseState = 0;
                }
            }
            builder.append(c);
        }
        return builder.toString();
    }
}
