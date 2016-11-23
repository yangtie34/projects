package com.jhnu.product.four.common.entity;

import java.io.Serializable;

public class FourNot implements Serializable{

	private static final long serialVersionUID = -8649768479839030112L;
	
	private Long id;
	
	private Long userId;
	
	private Long fourMethodId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFourMethodId() {
		return fourMethodId;
	}

	public void setFourMethodId(Long fourMethodId) {
		this.fourMethodId = fourMethodId;
	}
	
}
