package com.jhkj.mosdc.framework.po;

/**
 * @description 查询条件
 * @author Administrator
 * @param conditionName
 *            :查询条件(eg:sex_code)
 * @param dt
 *            :表达式(eg:=/<>)
 * @param conditionValu
 *            :所要查询的值(eg:'1')
 */
public class ConditionsEntity {

	public String getConditionName() {
		return conditionName;
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public String getDt() {
		return dt;
	}

	public void setDt(String dt) {
		this.dt = dt;
	}

	public String getConditionValu() {
		return conditionValu;
	}

	public void setConditionValu(String conditionValu) {
		this.conditionValu = conditionValu;
	}

	private String conditionName;
	private String dt;
	private String conditionValu;

}
