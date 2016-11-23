package com.jhnu.system.common.code.service;

import java.util.List;

import com.jhnu.system.common.code.entity.Code;

public interface CodeService {
	
	/**
	 * 获取Code集合
	 * @param code
	 * @return
	 */
	public List<Code> getCode(Code code);

}
