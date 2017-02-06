package cn.gilight.business.model;

import cn.gilight.framework.uitl.TypeConvert;

public class SelfDefinedYear{  
	private String mc;
	private String value;
	private String start;
	private String end;
	
	public SelfDefinedYear() {
		super();
	}
	
	public SelfDefinedYear(String mc, String value) {
		this.mc = mc;
		this.value = value;
		this.start = value;
		this.end = value;
	}
	
	public SelfDefinedYear(String mc, String start, String end) {
		this.mc = mc;
		this.value = start + "-" + end;
		this.start = start;
		this.end = end;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public static SelfDefinedYear getLastInterval(SelfDefinedYear source){
		SelfDefinedYear result = new SelfDefinedYear();
		if (source.getStart().equals(source.getEnd())) {
			String year = TypeConvert.toString(TypeConvert.toInteger(source.getStart())- 1);
			result.setValue(year);
			result.setStart(year);
			result.setEnd(year);
		}else {
			int start = TypeConvert.toInteger(source.getStart());
			int end = TypeConvert.toInteger(source.getEnd());
			result.setValue((2*start - 1 - end) + "-" + (start-1));
			result.setStart(TypeConvert.toString(2*start - 1 - end));
			result.setEnd(TypeConvert.toString(start-1));
		}
		return result;
	}
}  