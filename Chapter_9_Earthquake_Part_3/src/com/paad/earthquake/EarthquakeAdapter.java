package com.paad.earthquake;

import java.util.concurrent.ConcurrentHashMap;

import android.R.integer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class EarthquakeAdapter extends BaseAdapter {

	private ConcurrentHashMap<Integer, Quake> mQuakeMap = new ConcurrentHashMap<Integer, Quake>();

	public EarthquakeAdapter() {
		super();
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
