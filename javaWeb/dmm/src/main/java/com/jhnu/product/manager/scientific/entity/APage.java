package com.jhnu.product.manager.scientific.entity;

import java.util.List;
import java.util.Map;

public class APage {
private String counts;//总数
private String lines;//每页显示几行
private String pageNumber;//第几页
private String pageCount;//共几页
private Map map;//显示的对象列表
public String getCounts() {
	return counts;
}
public void setCounts(String counts) {
	this.counts = counts;
}
public String getLines() {
	return lines;
}
public void setLines(String lines) {
	this.lines = lines;
}
public String getPageNumber() {
	return pageNumber;
}
public void setPageNumber(String pageNumber) {
	this.pageNumber = pageNumber;
}
public String getPageCount() {
	return pageCount;
}
public void setPageCount(String pageCount) {
	this.pageCount = pageCount;
}
public Map getMap() {
	return map;
}
public void setMap(Map map) {
	this.map = map;
}
}
