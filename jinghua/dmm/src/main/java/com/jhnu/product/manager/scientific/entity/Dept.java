package com.jhnu.product.manager.scientific.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

	@Entity
	@Table(name = "t_code_dept_teach", schema = "DM_MOSDC_HTU")
	public class Dept implements java.io.Serializable {

		@CloumnAs(name = "部门代码")
		private String code;
		@CloumnAs(name = "部门名称")
		private String name;
		public Dept() {
		}
		public Dept(String code, String name) {
			this.code = code;
			this.name = name;
		}
		@Column(name = "CODE", nullable = false, length = 10)
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		@Column(name = "NAME", nullable = false, length = 10)
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
}
