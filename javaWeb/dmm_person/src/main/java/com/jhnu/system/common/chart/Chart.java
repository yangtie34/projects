package com.jhnu.system.common.chart;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jhnu.util.product.EduUtils;

public class Chart {
	private List<String> xAxis;//横坐标轴
	private Set<String> legend;// 图例（分类）
	private Map<String,List<EduUtils.Code>> series;// 针对legend的数据，以legend的值为键
	
	public List<String> getxAxis() {
		return xAxis;
	}
	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}
	public Set<String> getLegend() {
		return legend;
	}
	public void setLegend(Set<String> legend) {
		this.legend = legend;
	}
	public Map<String, List<EduUtils.Code>> getSeries() {
		return series;
	}
	public void setSeries(Map<String, List<EduUtils.Code>> series) {
		this.series = series;
	}
	public Chart() {
		super();
	}
	
}
