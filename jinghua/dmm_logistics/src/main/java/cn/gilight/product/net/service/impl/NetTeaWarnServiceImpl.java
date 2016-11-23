package cn.gilight.product.net.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.code.Code;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.CsvUtils;
import cn.gilight.framework.uitl.RListUtil;
import cn.gilight.framework.uitl.RUtils;
import cn.gilight.framework.uitl.common.ListUtil;
import cn.gilight.product.net.dao.NetTeaWarnDao;
import cn.gilight.product.net.service.NetTeaWarnService;

@Service("netTeaWarnService")
public class NetTeaWarnServiceImpl implements NetTeaWarnService{
	
	@Autowired
	private NetTeaWarnDao netTeaWarnDao;
	
	@Override
	public Page getTeaWarn(int currentPage,int numPerPage,int totalRow,String startDate, String endDate, Map<String, String> dept) {
		//获取将要分析的结果集
		Page page=new Page();
		List<Map<String,Object>> teaWarn=netTeaWarnDao.getTeaWarn(startDate, endDate, dept);
		if(teaWarn.size()==0){
			page.setResultList(teaWarn);
			return page;
		}
		List<Map<String,Object>> rList=ListUtil.deepClone(teaWarn);
		for (int i = 0; i < rList.size(); i++) {
			rList.get(i).remove("people_name");
			rList.get(i).remove("sex_code");
			rList.get(i).remove("sex_name");
			rList.get(i).remove("dept_id");
			rList.get(i).remove("dept_name");
		}
		
		//获取R语言代码路径
		String basePath=Code.getKey("rfile.path")+"/net/tea_warn";
		//创建CSV文件
		CsvUtils.createCsvFile(basePath+"/tea_warn.csv",rList);
		//执行R语言获取分析结果
		rList=getWarnData("tea_warn.csv",basePath);
		
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		for (int i = 0; i < teaWarn.size(); i++) {
			for (int j = 0; j < rList.size(); j++) {
				if(teaWarn.get(i).get("people_id").equals(rList.get(j).get("PEOPLE_ID"))){
					map=new HashMap<String, Object>();
					map=teaWarn.get(i);
					map.put("xs", rList.get(j).get("class1_prob"));
					returnList.add(map);
					rList.remove(j);
					break;
				}
			}
		}
		
		//删除CSV文件
		File file=new File(basePath+"/tea_warn.csv");
		file.delete();
		page.setResultList(returnList);
		return page;
	}

	@Override
	public Page getTeaWarnDetil(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, String teaId) {
		return netTeaWarnDao.getTeaWarnDetil(currentPage, numPerPage, totalRow, startDate, endDate, teaId);
	}
	
	private List<Map<String,Object>> getWarnData(String inputCSV,String fileName) {
		List<Map<String,Object>> list=null;
		try {
			  RUtils.r.eval("setwd(\""+fileName+"\")");
			  RUtils.r.eval("library(dplyr)");
			  RUtils.r.eval("load(\"cartfit.Rdata\")");
			  RUtils.r.eval("source(\"class_pred.R\")");
			  RUtils.r.eval("tea_csv<-read.csv(\""+inputCSV+"\")");
			  RUtils.r.eval("tea_date=class_pred(tea_csv)");
			  REXP rexp=RUtils.r.eval("tea_date$class1");
			  RList rlist =rexp.asList();
			  list=RListUtil.toList(rlist,",class1_prob,class2_prob,class3_prob,");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RserveException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}
		return list;
	}

}
