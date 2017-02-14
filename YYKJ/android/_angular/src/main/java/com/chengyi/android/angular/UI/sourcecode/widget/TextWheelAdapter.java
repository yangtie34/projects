package com.chengyi.android.angular.UI.sourcecode.widget;


import com.chengyi.android.angular.entity.TreeEntity;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.android.angular.core.Scope.activity;

public class TextWheelAdapter extends AbstractWheelTextAdapter {

	private List<TreeEntity> list=new ArrayList<TreeEntity>();


	public TextWheelAdapter(List<TreeEntity> list) {
		super(activity);
		this.list=list;
	}

	public TextWheelAdapter(int minValue, int maxValue) {
		super(activity);
		for (int i = minValue; i <=maxValue ; i++) {
			TreeEntity treeEntity=new TreeEntity();
			String value=Integer
					.toString(i);
			treeEntity.setId(value);
			treeEntity.setName(value);
			list.add(treeEntity);
		}
	}

	@Override
	public CharSequence getItemText(int index) {
		if (index >= 0 && index < getItemsCount()) {
			return list.get(index).getName();
		}
		return null;
	}

	public int getItemsCount() {
		return list.size();
	}
}