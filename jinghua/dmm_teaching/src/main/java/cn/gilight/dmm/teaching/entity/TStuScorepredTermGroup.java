package cn.gilight.dmm.teaching.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 成绩预测-分组表
 * 
 * @author xuebl
 * @date 2017年1月8日 下午3:07:30
 */
@Entity
@Table(name="T_STU_SCOREPRED_TERM_GROUP")
public class TStuScorepredTermGroup {

	// 分组
	private String id;
	private String startSchoolyear;
	private String startTermcode;
	private String endSchoolyear;
	private String endTermCode;
	private Integer grade;
	private String deptType;
	private String deptValue;
	private Integer iselective;
	private Integer truth;
	
	// 分组课程  忽略映射字段，应放在get方法上
	private List<TStuScorepredTermGpMd> list = new ArrayList<>();

	public TStuScorepredTermGroup() {
	}
	public TStuScorepredTermGroup(String id, String startSchoolyear, String startTermcode, String endSchoolyear,
			String endTermCode, Integer grade, String deptType, String deptValue, Integer iselective, Integer truth) {
		this.id = id;
		this.startSchoolyear = startSchoolyear;
		this.startTermcode = startTermcode;
		this.endSchoolyear = endSchoolyear;
		this.endTermCode = endTermCode;
		this.grade = grade;
		this.deptType = deptType;
		this.deptValue = deptValue;
		this.iselective = iselective;
		this.truth = truth;
	}
	
	public TStuScorepredTermGroup(String id, String startSchoolyear, String startTermcode, String endSchoolyear,
			String endTermCode, Integer grade, String deptType, String deptValue, Integer iselective, Integer truth,
			List<TStuScorepredTermGpMd> list) {
		this.id = id;
		this.startSchoolyear = startSchoolyear;
		this.startTermcode = startTermcode;
		this.endSchoolyear = endSchoolyear;
		this.endTermCode = endTermCode;
		this.grade = grade;
		this.deptType = deptType;
		this.deptValue = deptValue;
		this.iselective = iselective;
		this.truth = truth;
		this.list = list;
	}


	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 20)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "START_SCHOOLYEAR", length = 10)
	public String getStartSchoolyear() {
		return startSchoolyear;
	}

	public void setStartSchoolyear(String startSchoolyear) {
		this.startSchoolyear = startSchoolyear;
	}

	@Column(name = "START_TERMCODE", length = 10)
	public String getStartTermcode() {
		return startTermcode;
	}

	public void setStartTermcode(String startTermcode) {
		this.startTermcode = startTermcode;
	}

	@Column(name = "END_SCHOOLYEAR", length = 10)
	public String getEndSchoolyear() {
		return endSchoolyear;
	}

	public void setEndSchoolyear(String endSchoolyear) {
		this.endSchoolyear = endSchoolyear;
	}

	@Column(name = "END_TERMCODE", length = 10)
	public String getEndTermCode() {
		return endTermCode;
	}

	public void setEndTermCode(String endTermCode) {
		this.endTermCode = endTermCode;
	}

	@Column(name = "GRADE",  precision = 4)
	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	@Column(name = "DEPT_TYPE", length = 20)
	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	@Column(name = "DEPT_VALUE", length = 200)
	public String getDeptValue() {
		return deptValue;
	}

	public void setDeptValue(String deptValue) {
		this.deptValue = deptValue;
	}

	@Column(name = "ISELECTIVE",  precision = 1)
	public Integer getIselective() {
		return iselective;
	}
	public void setIselective(Integer iselective) {
		this.iselective = iselective;
	}
	
	@Column(name = "TRUTH",  precision = 1)
	public Integer getTruth() {
		return truth;
	}

	public void setTruth(Integer truth) {
		this.truth = truth;
	}
	
	@Transient
	public List<TStuScorepredTermGpMd> getList() {
		return list;
	}

	public void setList(List<TStuScorepredTermGpMd> list) {
		this.list = list;
	}
}