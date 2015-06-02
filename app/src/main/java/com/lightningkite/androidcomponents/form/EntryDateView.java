package com.lightningkite.androidcomponents.form;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jivie on 5/7/15.
 */
public class EntryDateView extends FrameLayout implements DatePickerDialog.OnDateSetListener, FormEntry {

    private boolean mEditable = true;
    @InjectView(R.id.entry_date_day_of_month)
    TextView mDayOfMonth;
    @InjectView(R.id.entry_date_day_of_week)
    TextView mDayOfWeek;
    @InjectView(R.id.entry_date_month)
    TextView mMonth;

    private Date mDate = new Date();

    public EntryDateView(Context context) {
        super(context);
        init();
    }

    public EntryDateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.EntryDateView,
                0, 0);
        mEditable = a.getBoolean(R.styleable.EntryDateView_editable, true);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.entry_date, this, true);
        ButterKnife.inject(this, this);

        setOnClickListener(mClickListener);
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mEditable) return;
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(mDate);
            DatePickerDialog dialog = new DatePickerDialog(
                    getContext(),
                    EntryDateView.this,
                    cal.get(GregorianCalendar.YEAR),
                    cal.get(GregorianCalendar.MONTH),
                    cal.get(GregorianCalendar.DAY_OF_MONTH)
            );
            dialog.show();
        }
    };

    private static final SimpleDateFormat DAY_OF_MONTH = new SimpleDateFormat("d");
    private static final SimpleDateFormat DAY_OF_WEEK = new SimpleDateFormat("EEEE");
    private static final SimpleDateFormat MONTH = new SimpleDateFormat("MMMM");
    private static final DateFormat TIME = SimpleDateFormat.getTimeInstance();

    public void setDate(Date date) {
        mDate = date;
        mDayOfMonth.setText(DAY_OF_MONTH.format(date));
        mDayOfWeek.setText(DAY_OF_WEEK.format(date).toUpperCase());
        mMonth.setText(MONTH.format(date).toUpperCase());
    }

    public Date getDate() {
        return mDate;
    }

    @Override
    public Object getData() {
        return mDate.getTime();
    }

    @Override
    public void setData(Object object) {
        if (object instanceof Long) {
            setDate(new Date((Long) object));
        } else {
            throw new IllegalArgumentException("Data must be a Long timestamp (milliseconds since Jan 1 1970)");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(GregorianCalendar.YEAR, year);
        cal.set(GregorianCalendar.MONTH, monthOfYear);
        cal.set(GregorianCalendar.DAY_OF_MONTH, dayOfMonth);
        setDate(cal.getTime());
        if (mListener != null) {
            mListener.onDateSet(mDate);
        }
    }

    public interface DateSetListener {
        void onDateSet(Date date);
    }

    private DateSetListener mListener = null;

    public DateSetListener getListener() {
        return mListener;
    }

    public void setListener(DateSetListener mListener) {
        this.mListener = mListener;
    }


    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putLong("time", mDate.getTime());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mDate.setTime(bundle.getLong("time"));
            setDate(mDate);
            state = bundle.getParcelable("instanceState");
        }
        super.onRestoreInstanceState(state);
    }
}
