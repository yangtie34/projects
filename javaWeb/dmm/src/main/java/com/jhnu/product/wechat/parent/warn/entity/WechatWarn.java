package com.jhnu.product.wechat.parent.warn.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.jhnu.util.common.DateUtils;

public class WechatWarn implements Serializable{

	private static final long serialVersionUID = -3879201711925525097L;

	private Long id;
	private String stu_id;
	private String warn_type_code;
	private String warn_level_code;
	private String warn_why;
	private String warn_text;
	private String warn_date;
	private Integer is_read;
	private String exe_time;
	
	private String warnWeak;
	
	
	public WechatWarn() {
		super();
	}
	public WechatWarn(String stu_id) {
		super();
		this.stu_id = stu_id;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getWarn_type_code() {
		return warn_type_code;
	}
	public void setWarn_type_code(String warn_type_code) {
		this.warn_type_code = warn_type_code;
	}
	public String getWarn_level_code() {
		return warn_level_code;
	}
	public void setWarn_level_code(String warn_level_code) {
		this.warn_level_code = warn_level_code;
	}
	public String getWarn_text() {
		return warn_text;
	}
	public void setWarn_text(String warn_text) {
		this.warn_text = warn_text;
	}
	public String getWarn_date() {
		return warn_date;
	}
	public void setWarn_date(String warn_date) {
		this.warn_date = warn_date;
	}
	public Integer getIs_read() {
		return is_read;
	}
	public void setIs_read(Integer is_read) {
		this.is_read = is_read;
	}
	public String getExe_time() {
		return exe_time;
	}
	public void setExe_time(String exe_time) {
		this.exe_time = exe_time;
	}
	public String getWarn_why() {
		return warn_why;
	}
	public void setWarn_why(String warn_why) {
		this.warn_why = warn_why;
	}
	public String getWarnWeak() {
		if(!StringUtils.isEmpty(warn_date)){
			int dateLength=warn_date.length();
			switch (dateLength) {
			case 10:
				warn_date+=" 00:00:00";
				break;
			case 13:
				warn_date+=":00:00";
				break;
			case 16:
				warn_date+=":00";			
				break;
			}
			try {
				Date date=DateUtils.SSS.parse(warn_date);
				warnWeak="星期"+DateUtils.getWeekCn(date)+" "+DateUtils.getTimeBytime(date)+"午";
			} catch (ParseException e) {
				warnWeak=warn_date;
			}
			
		}
		return warnWeak;
	}
	public void setWarnWeak(String warnWeak) {
		this.warnWeak = warnWeak;
	}
	
}
