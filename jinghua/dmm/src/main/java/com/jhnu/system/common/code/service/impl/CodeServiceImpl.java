package com.jhnu.system.common.code.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.system.common.code.dao.CodeDao;
import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.common.code.service.CodeService;

@Service("codeService")
public class CodeServiceImpl implements CodeService{

	@Autowired
	private CodeDao codeDao;
	
	@Override
	public List<Code> getCode(Code code) {
		return codeDao.getCode(code);
	}
	

}
