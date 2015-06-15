package com.lightningkite.androidcomponents.ui;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ListAdapter;

import java.util.LinkedList;

/**
 * A grid layout that can use an adapter.
 * Created by Joseph on 3/9/2015.
 */
public class AdapterGridLayout extends GridLayout {
    private ListAdapter mAdapter;

    public AdapterGridLayout(Context context) {
        super(context);
    }

    public AdapterGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdapterGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setAdapter(ListAdapter listAdapter) {
        mAdapter = listAdapter;
        mAdapter.registerDataSetObserver(observer);
        notifyDataSetChanged();
    }

    public ListAdapter getAdapter() {
        return mAdapter;
    }

    private void notifyDataSetChanged() {
        removeAllViews();
        for (int i = 0; i < mAdapter.getCount(); i++) {
            addView(mAdapter.getView(i, null, this));
        }
        for (int i = 0; i < mFooterViews.size(); i++) {
            addView(mFooterViews.get(i));
        }
    }

    DataSetObserver observer = new DataSetObserver() {
        @Override
        public void onInvalidated() {
            notifyDataSetChanged();
        }

        @Override
        public void onChanged() {
            notifyDataSetChanged();
        }
    };

    private LinkedList<View> mFooterViews = new LinkedList<>();

    public void addFooterView(View footerView) {
        mFooterViews.add(footerView);
        addView(footerView);
    }

    public void removeFooterView(View footerView) {
        mFooterViews.remove(footerView);
        removeView(footerView);
    }
}