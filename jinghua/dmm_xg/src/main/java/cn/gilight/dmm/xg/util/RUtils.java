package cn.gilight.dmm.xg.util;



import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.junit.Test;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPFactor;
import org.rosuda.REngine.REXPGenericVector;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import cn.gilight.framework.base.dao.BaseDao;

public class RUtils {
	@Resource
	private BaseDao baseDao;
	static DecimalFormat  df   =new DecimalFormat("#.00");  
	static RConnection c;
	static{
		 try {
			 c = new RConnection();//采用静态代码块的形式连接R语言中的Rserve服务器，
			 					   	//因为连接打开一次就可以了，所以采用静态代码块的方式
			 RUtils.loadRFunction();//提前加载所需函数
		} catch (RserveException e) {
			e.printStackTrace();
		}  
	}
	public static void main(String[] args) throws Exception {
		  String y1 = c.eval("getwd()").asString();//c.eval("R语言函数")是调用R语言函数的方法，
		  				//本次使用是得到R语言服务器的工作目录，相当于在R语言客户端中直接执行getwd()得到的结果是一样的
		  System.out.println(y1);
	}
	public static double[] getEquationData(String inputCSV,String fileName) {
		  double[] radio = new double[7];
		try {
			c.eval("setwd(\""+fileName+"\")");//设置R工作路径 相对于直接在R客户端中执行setwd("设置新工作路径")
			  String y1 = c.eval("getwd()").asString();//得到R语言服务器的工作目录，相当于在R语言客户端中直接执行getwd()得到的结果是一样的
			  System.out.println(y1);
			  c.eval("s1<-read.csv(\""+inputCSV+"\")");//读csv文件，相当于直接在R客户端中执行s1<-read.csv("xx.csv")
			  c.eval("head(s1)");//得到csv文件中 的前几行，相当于直接在R客户端中执行head(s1)
			  c.eval("lm(y~x1,data=s1)");//调用R函数lm用来进行回归方程分析
			  c.eval("lm.sov<-lm(y~x1,data=s1)");//将得到的lm分析的结果赋值给lm.sov
			  c.eval("summary(lm.sov)");//可以查看对象的基本信息
			  c.eval("ls<-summary(lm.sov)");//将查到的基本信息赋值给ls
			  c.eval("ls[[4]][,1]");//得到所需的回归方程的返回结果，以便于后面的分析
			  double[] xlist = c.eval("ls[[4]][,1]").asDoubles();//得到回归方程系数 [常数项(b),变量(a1,a2,..)]
			  double[] pdlist = c.eval("ls[[4]][,4]").asDoubles();//判断回归方程系数 [大于0.05的舍弃]
			  int j = 0;
			  for(int i=0;i<pdlist.length;i++){
				 if(pdlist[i]<0.05){
					 radio[j++]=Double.valueOf(df.format(xlist[i]));
				 }
			  }
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RserveException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}
		return radio;
	}
	/*****************************************根据成绩预测*******************************************************/
	/**
	 * 集中加载成绩预测所需函数
	 */
	public static void loadRFunction(){
		try {
			String setwd="setwd(\"E:/predictData\")";
			c.eval(setwd);//设置R工作路径 相对于直接在R客户端中执行setwd("设置新工作路径")
//			  String y1 = c.eval("getwd()").asString();//得到R语言服务器的工作目录，相当于在R语言客户端中直接执行getwd()得到的结果是一样的
			  String fore=RConstant.fore;
			  String knnpredmethod1=RConstant.knnpredmethod1;
			  String knnpredmethod2=RConstant.knnpredmethod2;
			  String knnpredmethod3=RConstant.knnpredmethod3;
			  String fitevaluations=RConstant.fitevaluations;//对比按成绩预测三种方法的好坏所需函数
			  c.eval(fore);c.eval(knnpredmethod1); 
			  c.eval(knnpredmethod2);c.eval(knnpredmethod3);
			  c.eval(fitevaluations);
		} catch (RserveException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 根据学生课程成绩进行预测
	 */
	@Test
	public void predictByStuScore(){
//		RUtils.loadRFunction();//提前加载所需函数
		 try {
			String y1 = c.eval("getwd()").asString();//得到R语言服务器的工作目录，相当于在R语言客户端中直接执行getwd()得到的结果是一样的
			System.out.println(y1);
			String[] knnpredmethodresult={"knnpredmethod1result","knnpredmethod2result","knnpredmethod3result"};
			String[] knnpredmethod={"knnpredmethod1","knnpredmethod2","knnpredmethod3"};
			Object[] res=new Object[3],result = new Object[3];
			c.eval("law14<-read.csv(\"E:/predictData/f14.csv\",header=T)");
			c.eval("law15<-read.csv(\"E:/predictData/f15.csv\",header=T)");
			c.eval("xlaw14<-law14[,3:length(law14)]");
			c.eval("ylaw15<-law15[,3:(length(law15)-2)]");
			for(int k=0;k<knnpredmethodresult.length;k++){
			c.eval(knnpredmethodresult[k]+"<-"+knnpredmethod[k]+"(fore(xlaw14,ylaw15,4))");
			 res[k]= (Object[]) c.eval(knnpredmethodresult[k]).asList().names.toArray();
			 result[k]= c.eval(knnpredmethodresult[k]).asList().toArray();
			for(int i=0;i<((Object[])(result[k])).length;i++){
				String[] no_=((REXPFactor)(((Object[])(result[k]))[0])).asStrings();
				if(i!=0){
				for(int j=0;j<no_.length;j++){
					                 //学号---课程id---预测成绩-----方法几
					System.out.println(no_[j]+"---"+((Object[])(res[k]))[i]+"----"+df.format((((REXPDouble)((Object[])(result[k]))[i]).asDoubles())[j])+"-----"+k);
				}
			 }
		  }
		}
			contrastMethodByStuScore(knnpredmethodresult);
//			c.eval("knnpredmethod1result<-knnpredmethod1(fore(xlaw14,ylaw15,4))");
//			c.eval("knnpredmethod2result<-knnpredmethod2(fore(xlaw14,ylaw15,4))");
//			c.eval("knnpredmethod3result<-knnpredmethod3(fore(xlaw14,ylaw15,4))");
//			Object[] res1= c.eval("knnpredmethod1result").asList().names.toArray();
//			Object[] result1= c.eval("knnpredmethod1result").asList().toArray();
//			for(int i=0;i<result1.length;i++){
//				String[] no_=((REXPFactor)result1[0]).asStrings();
//				if(i!=0){
//				for(int j=0;j<no_.length;j++){
//					                 //学号---课程id---预测成绩
//					System.out.println(no_[j]+"---"+res1[i]+"----"+df.format((((REXPDouble)result1[i]).asDoubles())[j]));
//				}
//			 }
//		  }
		 } catch (RserveException e) {
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 对比用成绩来预测学生成绩中的四种方法的优越性，取最优，然后存储到表中，方便后期的预测
	 */
	public void contrastMethodByStuScore(String[] knnpredmethodresult){
			try {
				c.eval("y2law15<-law15[,c(3,(length(law15)-1):(length(law15)))]");
				String[] kcid;int index = 0;double mse;// =((double[]) (c.eval("fitevaluations("+knnpredmethodresult[0]+",y2law15)").asDoubleMatrix())[1])[0];
				Object[] zmmn=  ((REXPGenericVector) ((c.eval("fitevaluations("+knnpredmethodresult[0]+",y2law15)")._attr()).asList().toArray())[1]).asList().toArray();
				kcid=((REXPString) zmmn[1]).asStrings();//得到预测的课程id
				for(int k=0;k<kcid.length;k++){
					mse =((double[]) (c.eval("fitevaluations("+knnpredmethodresult[0]+",y2law15)").asDoubleMatrix())[1])[k];
					index = 0;
					for(int i=0;i<knnpredmethodresult.length;i++){
				double[][] mmn=c.eval("fitevaluations("+knnpredmethodresult[i]+",y2law15)").asDoubleMatrix();	
			     //double mae=mmn[0][k];
			    if(mmn[1][k]<mse){//比较mse的大小，值越小，说明该函数越适用于此课程
			    	mse=mmn[1][k];
			    	index=i;
			      }
			   }
				System.out.println(kcid[k]+"---"+index);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
}
