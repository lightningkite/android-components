package com.lightningkite.androidcomponents.example;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jivie on 6/3/15.
 */
public class SelectorBigView extends BigView implements AdapterView.OnItemClickListener {

    public static final ArrayList<Entry> mExamples = new ArrayList<>();

    static {
        mExamples.add(new Entry(ExampleBigView.class));
        mExamples.add(new Entry(FormBigView.class));
        mExamples.add(new Entry(RetroRushBigView.class));
    }

    @InjectView(R.id.selector_list)
    ListView mListView;

    public SelectorBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_example_selector, this);
        ButterKnife.inject(this);

        mListView.setAdapter(new ArrayAdapter<>(mActivity, R.layout.row_example, R.id.example_text, mExamples));
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goTo(mExamples.get(position).mClass);
    }

    static private class Entry {

        private final Class<? extends BigView> mClass;

        public Entry(Class<? extends BigView> clazz) {
            mClass = clazz;
        }

        @Override
        public String toString() {
            return mClass.getSimpleName();
        }
    }
}
