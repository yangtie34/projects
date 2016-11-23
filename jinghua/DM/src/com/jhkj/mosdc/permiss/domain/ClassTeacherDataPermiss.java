package com.jhkj.mosdc.permiss.domain;

import java.util.ArrayList;
import java.util.List;

import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;

/**
 * 班主任数据权限
 * 
 * @author Administrator
 * 
 */
public class ClassTeacherDataPermiss {
	private Base base;
	
	private Boolean classTeacher;// 是否为班主任

	private List<Long> bjIds = new ArrayList<Long>();// 班级ID集合

	private String sqlDataPermiss;

	private String hqlDataPermiss;

	public void initDataPermiss(Long id) {
		// 是否班主任
		TbXxzyBjxxb tb = new TbXxzyBjxxb();
		if (id == null)
			return;
		tb.setBzrId(id);
		List<TbXxzyBjxxb> list = (List<TbXxzyBjxxb>) base.getEntityList(tb);
		List<Long> bjIdList = new ArrayList<Long>();
		if (list.size() != 0) {
			classTeacher = true;// 设定当前用户是班主任
			for (TbXxzyBjxxb t : list) {
				bjIds.add(t.getId());
			}
			sqlDataPermiss = "select t.id from Tb_Xxzy_Bjxxb t where t.bzr_id = "
					+ id;
			hqlDataPermiss = "select t.id from TbXxzyBjxxb t,TbXxzyBjxxb bj where t.bjId = bj.Id and bj.sfky = 1  and t.bzrId ="
					+ id;
		}
	}

	public Boolean isClassTeacher() {
		return classTeacher;
	}

	public List<Long> getBjIds() {
		return bjIds;
	}

	public String getSqlDataPermiss() {
		return sqlDataPermiss;
	}

	public String getHqlDataPermiss() {
		return hqlDataPermiss;
	}

	public void setBase(Base base) {
		this.base = base;
	}
	

}
