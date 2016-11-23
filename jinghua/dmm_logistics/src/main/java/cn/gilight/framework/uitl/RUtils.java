package cn.gilight.framework.uitl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import cn.gilight.framework.base.dao.BaseDao;

public class RUtils {
	@Resource
	private BaseDao baseDao;
	public static RConnection r;
	static{
		 try {
			 r = new RConnection();//采用静态代码块的形式连接R语言中的Rserve服务器，
			 					   	//因为连接打开一次就可以了，所以采用静态代码块的方式
		} catch (RserveException e) {
			e.printStackTrace();
		}  
	}
	public static void main(String[] args) throws Exception {
		getEquationData("1.csv","d:/");
	}
	public static List<Map<String,Object>> getEquationData(String inputCSV,String fileName) {
		List<Map<String,Object>> list=null;
		try {
			  r.eval("setwd(\""+fileName+"\")");
			  r.eval("library(nnet)");
			  r.eval("library(dplyr)");
			  r.eval("load(\"nnet.Rdata\")");
			  r.eval("source(\"class_pred.R\")");
			  r.eval("tea_csv<-read.csv(\""+inputCSV+"\")");
			  r.eval("tea_date=class_pred(tea_csv)");
			  r.eval("tea_warn<-data.frame(tea_date[tea_date$class==1,])");
			  REXP rexp=r.eval("tea_warn");
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
