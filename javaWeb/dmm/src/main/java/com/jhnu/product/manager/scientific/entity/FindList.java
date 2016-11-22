package com.jhnu.product.manager.scientific.entity;


	import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

		public class FindList implements java.io.Serializable {
			//作者或者科研信息
			private String id;
			@CloumnAs(name = "名称")
			private String name;
			public FindList() {
			}
			public FindList(String id,String name) {
				this.id = id;
				this.name = name;
			}
			@Column(name = "ID", nullable = false, length = 30)
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			@Column(name = "NAME", nullable = false, length = 30)
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}

}
