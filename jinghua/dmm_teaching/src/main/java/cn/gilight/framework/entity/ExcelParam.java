package cn.gilight.framework.entity;

import java.io.Serializable;
/**
 * excel表格需要传入的数据
 * @author liyintao
 *
 */
public class ExcelParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String CL1;
	private String CL2;
	private String CL3;
	private String CL4;
	private String CL5;
	private String CL6;
	private String CL7;
	private String CL8;
	public ExcelParam(String cL1, String cL2, String cL3, String cL4, String cL5) {
		CL1 = cL1;
		CL2 = cL2;
		CL3 = cL3;
		CL4 = cL4;
		CL5 = cL5;
	}
	
	public ExcelParam() {
	}

	public String getCL1() {
		return CL1;
	}
	public void setCL1(String cL1) {
		CL1 = cL1;
	}
	public String getCL2() {
		return CL2;
	}
	public void setCL2(String cL2) {
		CL2 = cL2;
	}
	public String getCL3() {
		return CL3;
	}
	public void setCL3(String cL3) {
		CL3 = cL3;
	}
	public String getCL4() {
		return CL4;
	}
	public void setCL4(String cL4) {
		CL4 = cL4;
	}
	public String getCL5() {
		return CL5;
	}
	public void setCL5(String cL5) {
		CL5 = cL5;
	}

	public String getCL6() {
		return CL6;
	}

	public void setCL6(String cL6) {
		CL6 = cL6;
	}

	public String getCL7() {
		return CL7;
	}

	public void setCL7(String cL7) {
		CL7 = cL7;
	}

	public String getCL8() {
		return CL8;
	}

	public void setCL8(String cL8) {
		CL8 = cL8;
	}
	
}
