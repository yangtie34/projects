package com.chengyi.android.angular.UI.sourcecode.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chengyi.android.angular.core.Scope;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.android.angular.UI.sourcecode.widget.AbstractWheelTextAdapter.TEXT_VIEW_ITEM_RESOURCE;


public class ViewWheelAdapter extends AbstractWheelAdapter {

	private List<View> views=new ArrayList<View>();

	private static final int NO_RESOURCE = 0;
	private LayoutInflater inflater;
	private Context context= Scope.activity;


	public ViewWheelAdapter(List<View> views) {
		this.views=views;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public int getItemsCount() {
		return this.views.size();
	}

	@Override
	public View getItem(int index, View convertView, ViewGroup parent) {
		convertView=null;
		if (index >= 0 && index < getItemsCount()) {
			if (convertView == null) {
				convertView = views.get(index);//getView(index, parent);
			}
			convertView.setPadding(0, 6, 0, 6);
			return convertView;
		}
		return null;
	}
	private View getView(int resource, ViewGroup parent) {
		switch (resource) {
			case NO_RESOURCE:
				return null;
			case TEXT_VIEW_ITEM_RESOURCE:
				return new TextView(context);
			default:
				return inflater.inflate(resource, parent, false);
		}
	}

}