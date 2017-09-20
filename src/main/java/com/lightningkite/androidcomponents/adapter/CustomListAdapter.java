package com.lightningkite.androidcomponents.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;

/**
 * A base adapter for quickly building an adapter with a customized look.
 * @param <ITEM> The type of item to display.
 * @param <HOLDER> The view holder class for retaining references to view's components.
 */
public abstract class CustomListAdapter<ITEM, HOLDER> extends BaseAdapter {

    private final int mResource;
    private final LayoutInflater mInflater;
    public List<ITEM> mList;

    /**
     * Makes a new adapter with an empty array list for its data.
     *
     * @param context           The context to construct views with.
     * @param rowLayoutResource The layout resource to use for each row.
     */
    public CustomListAdapter(Context context, @LayoutRes int rowLayoutResource) {
        mInflater = LayoutInflater.from(context);
        mResource = rowLayoutResource;
        mList = new ArrayList<>();
    }

    /**
     * Makes a new adapter using the specified list BY REFERENCE.  This means that the list you pass
     * in will be used, and thus if you update the list you need to call "notifyDataSetChanged".
     *
     * @param context           The context to construct views with.
     * @param rowLayoutResource The layout resource to use for each row.
     * @param list              The list object to use for data.
     */
    public CustomListAdapter(Context context, @LayoutRes int rowLayoutResource, List<ITEM> list) {
        mInflater = LayoutInflater.from(context);
        mResource = rowLayoutResource;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    public List<ITEM> getList() {
        return mList;
    }

    public void setList(List<ITEM> list) {
        mList = list;
    }

    public ITEM get(int position) {
        return mList.get(position);
    }

    public void add(ITEM item) {
        mList.add(item);
    }

    public void addAll(Collection<ITEM> collection) {
        mList.addAll(collection);
    }

    public void addAll(ITEM[] array) {
        Collections.addAll(mList, array);
    }

    public int indexOf(ITEM item) {
        return mList.indexOf(item);
    }

    public boolean contains(ITEM item) {
        return mList.contains(item);
    }

    public void remove(int index) {
        mList.remove(index);
    }

    public void remove(ITEM item) {
        mList.remove(item);
    }

    public void clear() {
        mList.clear();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HOLDER holder;
        if (convertView == null) {
            convertView = mInflater.inflate(mResource, parent, false);
            convertView.setTag(holder = makeHolder());
            ButterKnife.bind(holder, convertView);
        } else {
            //noinspection unchecked
            holder = (HOLDER) convertView.getTag();
        }
        updateView(mList.get(position), holder, convertView);
        return convertView;
    }

    /**
     * Should return a new empty copy of the holder class, set up to use ButterKnife injects.
     *
     * @return A new empty copy of the holder class.
     */
    abstract protected HOLDER makeHolder();

    /**
     * Should update the views' components to reflect the data found in "item".  Views can and will
     * be reused, so reset every property in this function.
     *
     * @param item        The item to display.
     * @param holder      The view holder used to access the view's specific components.
     * @param convertView The view to be edited.
     */
    abstract public void updateView(ITEM item, HOLDER holder, View convertView);
}
