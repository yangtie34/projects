package com.jhnu.product.manager.scientific.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.scientific.dao.TCodeDao;
import com.jhnu.product.manager.scientific.entity.TCode;
import com.jhnu.product.manager.scientific.excuteUtil.Excute;
/**
 * @title 科研录入Dao获取信息类
 * @description 科研信息录入
 * @author Administrator
 * @date 2015/10/13 18:12
 */
@Repository("tCodeDao")
public class TCodeDaoImpl implements TCodeDao{
	@Autowired
	private Excute excute;
	static Map map=new LinkedHashMap();
	//static Map typeNameMap=new LinkedHashMap();
	static{
//作者
		//作者身份：PEOPLE_IDENTITY_CODE
		map.put("ZZSF","PEOPLE_IDENTITY_CODE");//论文
		//项目角色
		map.put("LWJS","THESIS_AUTHOR_ROLE_CODE");//论文
		map.put("ZZJS","WORK_AUTHOR_ROLE_CODE");//著作
		map.put("JDCGJS","OUTCOME_APPRAISAL_AUTH_ROLE_CODE");//鉴定成果
		map.put("HJCGJS","OUTCOME_AWARD_AUTH_ROLE_CODE");//获奖成果
		map.put("ZLJS","PATENT_AUTH_ROLE_CODE");//专利
		map.put("RJZZQJS","SOFT_COPYRIGHT_AUTH_ROLE_CODE");//软件著作权
		map.put("KYXMJS","PROJECT_AUTH_ROLE_CODE");//科研项目
//论文信息获取	
		map.put("LWFBQK","RES_PERIODICAL_CODE");//发表期刊：PERIODICAL
		map.put("LWQKLB","RES_PERIODICAL_TYPE_CODE");//期刊类别-收录类别
//论文收录(SCI/SCIE/EI收录)
		//map.put("SLLB","RES_PERIODICAL_TYPE_CODE");//收录类别
//论文转载
		map.put("LWZZQKLB","RES_THESIS_RESHIP_CODE");//转载期刊类别
//获奖论文
		map.put("LWHJMC","T_RES_THESIS_AWARD_NAME_CODE");//获奖名称：AWARD_NAME
		map.put("SQDW","T_RES_THESIS_AWARD_NAME_CODE");//授权单位：AWARD_DEPT
//著作：T_RES_WORK	
		map.put("ZZCBDW","RES_PRESS_NAME_CODE");//出版单位：PRESS_NAME
//鉴定成果信息获取
		map.put("JDDW","RES_APPRAISAL_DEPT_CODE");//鉴定单位：APPRAISAL_DEPT
		map.put("JDXS","RES_IDENTIFYMODE_CODE");//鉴定形式
		map.put("JDSP","RES_IDENTIFYLEVEL_CODE");//鉴定水平	
//获奖成果信息获取
		map.put("HJJB","RES_AWARD_LEVEL_CODE");//获奖级别
		map.put("HJLB","RES_AWARD_CATEGORY_CODE");//获奖类别
		map.put("HJDJ","RES_AWARD_RANK_CODE");//获奖等级	
//专利信息获取
		map.put("ZLLX","RES_PATENT_TYPE_CODE");//专利类型
		map.put("ZLSSZT","RES_PATENT_STATE_CODE");//专利实施状态
//科研项目
		map.put("XMLB","RES_PROJECT_CATEGORY_CODE");//项目类别
		map.put("XMZT","RES_PROJECT_STATE_CODE");//项目状态
		map.put("XMJB","RES_PROJECT_LEVEL_CODE");//项目等级级别
		map.put("XMDJ","RES_PROJECT_RANK_CODE");//项目程度等级
//计算机软件著作权2：T_RES_SOFT_COPYRIGHT	
		map.put("RJZZQBQLX","RES_SOFT_COPYRIGHT_CODE");//版权类型
		map.put("RJZZQQLQDFS","RES_SOFT_COPYRIGHT_GET_CODE");//权利取得方式
//审核状态T_RES_KYLR_TEMP
		map.put("KYSHZT","T_RES_KYLR_TEMP_FLAG_CODE");//审核状态
		map.put("KYLB","T_RES_KYLR_TEMP_KYLB_CODE");//审核状态
	}
		

	@Override
	public List getType(String kylx) {
		String sql="select code_ ,name_ from t_code where code_type like '"+map.get(kylx)+"'";
		return excute.query(sql, new TCode());
	}
	@Override
	public boolean insertCode(List<TCode> list) {
		return excute.insert(list);
	}
	@Override
	public String getCodeType(String key) {
		return (String) map.get(key);
	}
}
