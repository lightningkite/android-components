package com.lightningkite.androidcomponents.example;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.adapter.CustomListAdapter;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Used for selecting which activity the user wants to go to.
 * Created by jivie on 6/3/15.
 */
public class SelectorBigView extends BigView implements AdapterView.OnItemClickListener {

    public static final ArrayList<Class<? extends BigView>> EXAMPLES = new ArrayList<>();

    static {
        EXAMPLES.add(ExampleBigView.class);
        EXAMPLES.add(FormBigView.class);
        EXAMPLES.add(RetroRushBigView.class);
        EXAMPLES.add(AutoformBigView.class);
        EXAMPLES.add(MegaAutoformBigView.class);
    }

    private final MyAdapter mAdapter;

    @InjectView(R.id.selector_list)
    ListView mListView;

    public SelectorBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_example_selector, this);
        ButterKnife.inject(this);

        mAdapter = new MyAdapter(mActivity, EXAMPLES);

        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goTo(mAdapter.get(position));
    }


    private class MyAdapter extends CustomListAdapter<Class<? extends BigView>, Holder> {
        public MyAdapter(Context context, List<Class<? extends BigView>> list) {
            super(context, R.layout.row_example, list);
        }

        @Override
        protected Holder makeHolder() {
            return new Holder();
        }

        @Override
        public void updateView(Class<? extends BigView> item, Holder holder, View convertView) {
            holder.mLabelView.setText(item.getSimpleName());
        }
    }

    static public class Holder {
        @InjectView(R.id.example_label)
        TextView mLabelView;
    }
}
