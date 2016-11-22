package com.jhnu.product.manager.scientific.dao.impl;

import java.util.LinkedHashMap;
import java.util.Map;

public class KyArray {
	public static Map<String, String> codeMap=new LinkedHashMap();
	public static String[] tableClass={
			"TResThesis,TResThesisIn,TResThesisIstp,TResThesisCpcis,TResThesisReship,TResThesisAward",//论文
			"TResWork",//著作
			"TResOutcomeAppraisal",//鉴定成果
			"TResOutcomeAward",//获奖成果
			"TResPatent",//专利
			"TResSoftCopyright",//软件著作权
			"TResProject"//科研项目
	};
	public static String[] kyTable={
		"TResThesis",//论文
		"TResWork",//著作
		"TResOutcomeAppraisal",//鉴定成果
		"TResOutcomeAward",//获奖成果
		"TResPatent",//专利
		"TResSoftCopyright",//软件著作权
		"TResProject"//科研项目
};
	public static String[] kyTableTitle={
		"title",//论文
		"title",//著作
		"name",//鉴定成果
		"name",//获奖成果
		"name",//专利
		"softName",//软件著作权
		"name"//科研项目
};
	public static String[] tableas={
			"论文,论文收录(SCI/SCIE/EI收录),论文收录(ISTP收录),论文收录(CPCI-S收录),论文转载,获奖论文",//论文
			"著作",//著作
			"鉴定成果",//鉴定成果
			"获奖成果",
			"专利",//专利
			"软件著作权",//软件著作权
			"科研项目"//科研项目
	};
	public static String[] zzClass={
			"TResThesisAuthor",//论文
			"TResWorkAuthor",//著作
			"TResOutcomeAppraisalAuth",//鉴定成果
			"TResOutcomeAwardAuth",//获奖成果
			"TResPatentAuth",//专利
			"TResSoftCopyrightAuth",//软件著作权
			"TResProjectAuth"//科研项目
	};
	public static String[] tableAuths={
			"authors",
			"prizewinner",
			"inventors",
			"owner",
			"compere"
	};
	public static String[] nyrdate={//表中哪些字段需要日期组件
			";;conferenceTime;conferenceTime;;awardTime",//论文
			"pressTime",//著作
			"time",//鉴定成果
			"awardTime",//获奖成果
			"dispatchTime,acceptTime,accreditTime",//专利
			"completeTime,dispatchTime,registTime",//软件著作权
			"startTime,endTime"//科研项目
	};
	public static String[] ndate={//表中哪些字段需要日期组件
			"year;year;year;year;year;",//论文
			"",//著作
			"",//鉴定成果
			"",//获奖成果
			"",//专利
			"",//软件著作权
			"setupYear"//科研项目
	};
	static{
		//作者
		//作者身份：PEOPLE_IDENTITY_CODE
		codeMap.put("peopleIdentityCode","ZZSF");//论文
		//项目角色
		codeMap.put("thesisAuthorRoleCode","LWJS");//论文
		codeMap.put("workAuthRoleCode","ZZJS");//著作
		codeMap.put("appraisalAuthRoleCode","JDCGJS");//鉴定成果
		codeMap.put("awardAuthRoleCode","HJCGJS");//获奖成果
		codeMap.put("patentAuthRoleCode","ZLJS");//专利
		codeMap.put("copyrightAuthRoleCode","RJZZQJS");//软件著作权
		codeMap.put("projectAuthRoleCode","KYXMJS");//科研项目
//论文信息获取	
		codeMap.put("periodical","LWFBQK");//发表期刊：PERIODICAL
		codeMap.put("periodicalTypeCode","LWQKLB");//期刊类别-收录类别
//论文收录(SCI/SCIE/EI收录)
		//codeMap.put("","SLLB","RES_PERIODICAL_TYPE_CODE");//收录类别
//论文转载
		codeMap.put("thesisReshipCode","LWZZQKLB");//转载期刊类别
//获奖论文
		codeMap.put("awardName","LWHJMC");//获奖名称：AWARD_NAME
		codeMap.put("awardDept","SQDW");//授权单位：AWARD_DEPT
		codeMap.put("awardLevelCode","HJJB");//获奖级别
		codeMap.put("awardRankCode","HJDJ");//获奖等级	
//著作：T_RES_WORK	
		codeMap.put("pressName","ZZCBDW");//出版单位：PRESS_NAME
//鉴定成果信息获取
		codeMap.put("appraisalDept","JDDW");//鉴定单位：APPRAISAL_DEPT
		codeMap.put("identifymodeCode","JDXS");//鉴定形式
		codeMap.put("identifylevelCode","JDSP");//鉴定水平	
//获奖成果信息获取
		codeMap.put("levelCode","HJJB");//获奖级别
		codeMap.put("categoryCode","HJLB");//获奖类别
		codeMap.put("rankCode","HJDJ");//获奖等级	
//专利信息获取
		codeMap.put("patentTypeCode","ZLLX");//专利类型
		codeMap.put("patentStateCode","ZLSSZT");//专利实施状态
//科研项目
		codeMap.put("category","XMLB");//项目类别
		codeMap.put("stateCode","XMZT");//项目状态
		codeMap.put("levelCode","XMJB");//项目等级级别
		codeMap.put("rankCode","XMDJ");//项目程度等级
//计算机软件著作权2：T_RES_SOFT_COPYRIGHT	
		codeMap.put("copyrightCode","RJZZQBQLX");//版权类型
		codeMap.put("getCode","RJZZQQLQDFS");//权利取得方式
//审核状态T_RES_KYLR_TEMP
		codeMap.put("flagCode","KYSHZT");//审核状态
		codeMap.put("kylbCode","KYLB");//科研类别代码
	}
}
