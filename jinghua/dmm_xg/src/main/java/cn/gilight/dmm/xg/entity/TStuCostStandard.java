package cn.gilight.dmm.xg.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_STU_COST_STANDARD")
public class TStuCostStandard implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String school_year;
	private String term_code;
	private Double high_standard;
	private Double low_standard;
	private String type_;
	private String start_one;
	private String end_one;
	private String high_alg_name;
	private String start_two;
	private String end_two;
	private String low_alg_name;
	private String high_alg;
	private String low_alg;
	
	public TStuCostStandard() {
		super();
	}
	
	public TStuCostStandard(String id,String school_year,String term_code,Double high_standard,Double low_standard,String type_,
			String start_one,String end_one,String high_alg_name,String start_two,String end_two,String low_alg_name,String high_alg,String low_alg){
		super();
		this.id = id;
		this.school_year = school_year;
		this.term_code = term_code;
		this.high_standard = high_standard;
		this.low_standard = low_standard;
		this.type_ = type_;
		this.start_one = start_one;
		this.end_one = end_one;
		this.high_alg_name = high_alg_name;
		this.start_two = start_two;
		this.end_two = end_two;
		this.low_alg_name = low_alg_name;
		this.high_alg = high_alg;
		this.low_alg = low_alg;
	}
	
	public TStuCostStandard(String school_year,String term_code,Double high_standard,Double low_standard,String type_,
			String start_one,String end_one,String high_alg_name,String start_two,String end_two,String low_alg_name,String high_alg,String low_alg){
		super();
		this.school_year = school_year;
		this.term_code = term_code;
		this.high_standard = high_standard;
		this.low_standard = low_standard;
		this.type_ = type_;
		this.start_one = start_one;
		this.end_one = end_one;
		this.high_alg_name = high_alg_name;
		this.start_two = start_two;
		this.end_two = end_two;
		this.low_alg_name = low_alg_name;
		this.high_alg = high_alg;
		this.low_alg = low_alg;
	}
	
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 20)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "SCHOOL_YEAR", length = 9)
	public String getSchool_year() {
		return school_year;
	}
	public void setSchool_year(String school_year) {
		this.school_year = school_year;
	}
	
	@Column(name = "TERM_CODE", length = 10)
	public String getTerm_code() {
		return term_code;
	}
	public void setTerm_code(String term_code) {
		this.term_code = term_code;
	}
	
	@Column(name = "HIGH_STANDARD", precision = 10, scale = 2)
	public Double getHigh_standard() {
		return high_standard;
	}
	public void setHigh_standard(Double high_standard) {
		this.high_standard = high_standard;
	}
	
	@Column(name = "LOW_STANDARD", precision = 10, scale = 2)
	public Double getLow_standard() {
		return low_standard;
	}
	public void setLow_standard(Double low_standard) {
		this.low_standard = low_standard;
	}
	
	@Column(name = "TYPE_", length = 20)
	public String getType_() {
		return type_;
	}
	public void setType_(String type_) {
		this.type_ = type_;
	}
	@Column(name = "START_ONE", length = 10)
	public String getStart_one() {
		return start_one;
	}

	public void setStart_one(String start_one) {
		this.start_one = start_one;
	}
	@Column(name = "END_ONE", length = 10)
	public String getEnd_one() {
		return end_one;
	}

	public void setEnd_one(String end_one) {
		this.end_one = end_one;
	}
	@Column(name = "HIGH_ALG_NAME", length = 60)
	public String getHigh_alg_name() {
		return high_alg_name;
	}

	public void setHigh_alg_name(String high_alg_name) {
		this.high_alg_name = high_alg_name;
	}

	@Column(name = "START_TWO", length = 10)
	public String getStart_two() {
		return start_two;
	}

	public void setStart_two(String start_two) {
		this.start_two = start_two;
	}

	@Column(name = "END_TWO", length = 10)
	public String getEnd_two() {
		return end_two;
	}

	public void setEnd_two(String end_two) {
		this.end_two = end_two;
	}
	@Column(name = "LOW_ALG_NAME", length = 60)
	public String getLow_alg_name() {
		return low_alg_name;
	}

	public void setLow_alg_name(String low_alg_name) {
		this.low_alg_name = low_alg_name;
	}
	@Column(name = "HIGH_ALG", length = 10)
	public String getHigh_alg() {
		return high_alg;
	}

	public void setHigh_alg(String high_alg) {
		this.high_alg = high_alg;
	}
	@Column(name = "LOW_ALG", length = 10)
	public String getLow_alg() {
		return low_alg;
	}

	public void setLow_alg(String low_alg) {
		this.low_alg = low_alg;
	}
}
