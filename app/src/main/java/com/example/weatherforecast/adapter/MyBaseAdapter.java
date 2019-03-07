package com.example.weatherforecast.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by admin on 2016/11/11.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

	public LayoutInflater mInflater;
	public List<T> bs = new ArrayList<>();

	public MyBaseAdapter() {
	}

	public MyBaseAdapter(LayoutInflater mInflater) {
		this.mInflater = mInflater;
	}

	@Override
	public int getCount() {
		return bs.size();
	}

	@Override
	public Object getItem(int position) {
		return bs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public abstract View getView(int position, View convertView,
								 ViewGroup parent);

	public void add(List<T> bses) {
		bs.removeAll(bs);
		if (bses != null && bses.size() > 0) {
			bs.addAll(bses);
		}
		notifyDataSetChanged();
	}

}
