package com.eyun.framework.moduleview.widget;


import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.TreeEntity;

import java.util.ArrayList;
import java.util.List;

public class TextWheelAdapter extends AbstractWheelTextAdapter {

	private List<TreeEntity> list=new ArrayList<TreeEntity>();


	public TextWheelAdapter(List<TreeEntity> list) {
		super(Scope.activity);
		this.list=list;
	}

	public TextWheelAdapter(int minValue, int maxValue) {
		super(Scope.activity);
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