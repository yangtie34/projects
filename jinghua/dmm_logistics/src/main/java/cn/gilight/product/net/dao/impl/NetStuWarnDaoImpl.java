package cn.gilight.product.net.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.net.dao.NetStuWarnDao;

@Repository("netStuWarnDao")
public class NetStuWarnDaoImpl implements NetStuWarnDao{
	
	@Autowired
	private BaseDao baseDao;
	
	public String getWhere(String startDate,
			String endDate, Map<String, String> deptTeach){
		return SqlUtil.getWhere(startDate,endDate,deptTeach, ShiroTagEnum.NET_STU_WARN.getCode());
	}
	public String[] getZDBylb(String type){
		return SqlUtil.getZDBylb(type);
	}

	@Override
	public Page getNetWarnStus(int currentPage,int numPerPage,int totalRow,String startDate, String endDate,
			Map<String, String> deptTeach, String type, String value) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String sql="SELECT * FROM ( "+        
					"SELECT SUM(T.USE_TIME) ALL_TIME,SUM(T.USE_FLOW) ALL_FLOW,SUM(T.USE_MONEY) ALL_MONEY, T.STU_ID,T.STU_NAME , "+
					"T.DEPT_ID, T.DEPT_NAME ,T.MAJOR_ID   , T.MAJOR_NAME ,T.CLASS_ID   ,T.CLASS_NAME, "+
					"t.SEX_CODE ,t.SEX_NAME ,t.EDU_ID, t.EDU_NAME ,t.NATION_CODE  , t.NATION_NAME "+
					"FROM TL_NET_STU_MONTH T WHERE 1=1 "+tj+
					"GROUP BY T.STU_ID,T.STU_NAME,T.DEPT_ID, T.DEPT_NAME ,T.MAJOR_ID,T.MAJOR_NAME ,T.CLASS_ID,        "+
					"t.CLASS_NAME,t.SEX_CODE ,t.SEX_NAME ,t.EDU_ID, t.EDU_NAME ,t.NATION_CODE,t.NATION_NAME , t.NATION_NAME	 "+   
					")t where STU_ID IS NOT NULL AND T.all_"+type+">="+value+" ORDER BY  all_"+type+" DESC";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}

	@Override
	public List<Map<String, Object>> getNetStuType(String startDate,
			String endDate, Map<String, String> deptTeach, String type,
			String value, String codeType) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String[] zdlb=getZDBylb(codeType);
		String sql="SELECT "+zdlb[0]+", COUNT(DISTINCT T.STU_ID) VALUE FROM ( "+        
				"SELECT SUM(T.USE_TIME) ALL_TIME,SUM(T.USE_FLOW) ALL_FLOW,SUM(T.USE_MONEY) ALL_MONEY, T.STU_ID,T.STU_NAME , "+
				"T.DEPT_ID, T.DEPT_NAME ,T.MAJOR_ID   , T.MAJOR_NAME ,T.CLASS_ID   ,T.CLASS_NAME, "+
				"t.SEX_CODE ,t.SEX_NAME ,t.EDU_ID, t.EDU_NAME ,t.NATION_CODE  , t.NATION_NAME "+
				"FROM TL_NET_STU_MONTH T WHERE 1=1 "+tj+
				"GROUP BY T.STU_ID,T.STU_NAME,T.DEPT_ID, T.DEPT_NAME ,T.MAJOR_ID,T.MAJOR_NAME ,T.CLASS_ID, "+
				"t.CLASS_NAME,t.SEX_CODE ,t.SEX_NAME ,t.EDU_ID, t.EDU_NAME ,t.NATION_CODE,t.NATION_NAME , t.NATION_NAME	 "+   
				")t where STU_ID IS NOT NULL AND T.all_"+type+">="+value+" GROUP BY "+zdlb[1]+" ORDER BY CODE DESC";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page getNetWarnTypeStus(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,
			Map<String, String> deptTeach, String type, String value,
			String codeType, String codeValue) {
		String tj=getWhere(startDate, endDate, deptTeach);
		
		codeType=codeType.toUpperCase();
		switch (codeType) {
		case "XB":
			codeType="SEX_CODE";
			break;
			
		case "XL":
			codeType="EDU_ID";
			break;
		default:
			codeType="NATION_CODE";
			break;
		}
		String sql="SELECT * FROM ( "+        
					"SELECT SUM(T.USE_TIME) ALL_TIME,SUM(T.USE_FLOW) ALL_FLOW,SUM(T.USE_MONEY) ALL_MONEY, T.STU_ID,T.STU_NAME , "+
					"T.DEPT_ID, T.DEPT_NAME ,T.MAJOR_ID   , T.MAJOR_NAME ,T.CLASS_ID   ,T.CLASS_NAME, "+
					"t.SEX_CODE ,t.SEX_NAME ,t.EDU_ID, t.EDU_NAME ,t.NATION_CODE  , t.NATION_NAME "+
					"FROM TL_NET_STU_MONTH T WHERE 1=1 "+tj+
					"GROUP BY T.STU_ID,T.STU_NAME,T.DEPT_ID, T.DEPT_NAME ,T.MAJOR_ID,T.MAJOR_NAME ,T.CLASS_ID,        "+
					"t.CLASS_NAME,t.SEX_CODE ,t.SEX_NAME ,t.EDU_ID, t.EDU_NAME ,t.NATION_CODE,t.NATION_NAME , t.NATION_NAME	 "+   
					")t where STU_ID IS NOT NULL AND T.all_"+type+">="+value+" AND T."+codeType+"='"+codeValue+"' ORDER BY  all_"+type+" DESC";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}
	
}
