package com.eyun.framework.moduleview.widget;


import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.entity.NodeEntity;

import java.util.ArrayList;
import java.util.List;

public class TextWheelAdapter extends AbstractWheelTextAdapter {

	private List<NodeEntity> list=new ArrayList<NodeEntity>();


	public TextWheelAdapter(List<NodeEntity> list) {
		super(Scope.activity);
		this.list=list;
	}

	public TextWheelAdapter(int minValue, int maxValue) {
		super(Scope.activity);
		for (int i = minValue; i <=maxValue ; i++) {
			NodeEntity treeEntity=new NodeEntity();
			String value= Integer
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