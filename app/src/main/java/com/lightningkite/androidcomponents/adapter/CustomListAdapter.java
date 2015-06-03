package com.lightningkite.androidcomponents.adapter;

import android.content.Context;
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
 * Created by jivie on 6/3/15.
 */
public abstract class CustomListAdapter<T, HOLDER> extends BaseAdapter {

    private final int mResource;
    private final LayoutInflater mInflater;
    public List<T> mList;

    public CustomListAdapter(Context context, int rowLayoutResource) {
        mInflater = LayoutInflater.from(context);
        mResource = rowLayoutResource;
        mList = new ArrayList<>();
    }

    public CustomListAdapter(Context context, int rowLayoutResource, List<T> list) {
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

    public List<T> getList() {
        return mList;
    }

    public T get(int position) {
        return mList.get(position);
    }

    public void add(T item) {
        mList.add(item);
    }

    public void addAll(Collection<T> collection) {
        mList.addAll(collection);
    }

    public void addAll(T[] array) {
        Collections.addAll(mList, array);
    }

    public int indexOf(T item) {
        return mList.indexOf(item);
    }

    public boolean contains(T item) {
        return mList.contains(item);
    }

    public void remove(int index) {
        mList.remove(index);
    }

    public void remove(T item) {
        mList.remove(item);
    }

    public void clear() {
        mList.clear();
    }

    abstract protected HOLDER makeHolder();

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
            ButterKnife.inject(holder, convertView);
        } else {
            //noinspection unchecked
            holder = (HOLDER) convertView.getTag();
        }
        updateView(mList.get(position), holder, convertView);
        return convertView;
    }

    abstract public void updateView(T item, HOLDER holder, View convertView);
}
