package cn.gilight.dmm.teaching.util;
/**
 * 此文件专门用于存放R函数
 * @author lijun
 *
 */
public class RConstant {
//fore参数说明:x为训练集（第一列代表上一届学生学号，第2到第【length(y)】列为第一学期课程，【length[y]+1:length(x)】列为第二学期课程成绩
//              y为测试集（第一列代表预测学生学号，第2列到第【length(y)】列为第一学期课程。K是将训练集分类的数目
//目的是找出与测试集相似的学生人群
public static String fore="fore=function(x,y,k){ "
      + " ds=matrix(nrow=length(x[,1]),ncol=length(y[,1])); "// #distance(行：训练集的每个人（14级），列：测试集的每个人（15级) 
      + "   dc=matrix(nrow=1,ncol=length(y[,1])); "
      + "   dr=dc;  "
      + "   yy=y[,2:length(y)];  "
      + "   xx=x[,2:length(y)];  "// #训练集的学生的第一学期每个人的每门课程,
      + "   xxscale=scale(xx);   "
      + "   xxdist=dist(xxscale);  "
      + "   kclusterward=hclust(xxdist,\"ward.D\");  "
      + "   kclusterid=cutree(kclusterward,k);  "
      + "  x.new=cbind(x,kclusterid);  "
      + "   library(MASS);  "
      + "   xxlda=lda(xx,kclusterid);  "
      + "  ypredictlda=predict(xxlda,yy);  "
      + "   yclusterid=ypredictlda$class;  "
      + "   yclusterid=as.matrix(yclusterid,nrow=1);  "
      + "   dc=yclusterid;  "
      + "   for(j in 1:ncol(ds)){ "
      + "           for(i in 1:nrow(ds)){ "
      + "                   ds[i,j]=sqrt(sum((x[i,][2:length(y)]-y[j,][2:length(y)])^2)); "
      + "           } "
      + "   };  "
      + "   for(i in 1:ncol(dr)){ "
      + "           table=x.new[x.new[length(x.new)]==dc[i],];  "
      + "           mm=c();  "
      + "           for(j in 1:length(table[,1])){ "
      + "                   mm[j]=sqrt(sum((table[j,][2:length(y)]-y[i,][2:length(y)])^2));  "
      + "           }; "
      + "           dr[1,i]=mean(mm);  "
      + "   };  "
      + "   gg=list();  "
      + "   hh=list();  "
      + "   z=x[,c((length(y)+1):length(x))];  "
      + "   for(i in 1:length(z)){ "
      + "           for(j in 1:length(y[,1])){ "
      + "                   x.new1=cbind(x[,c(2:length(y),(length(y)+i))],ds[,j]);  "
      + "                   gg[j]=subset(x.new1,subset=(x.new1[,(length(x.new1))]<=dr[1,j]),select=c(length(x.new1)-1));  "
      + "           }; "
      + "           names(gg)=as.character(y$studentid);  "//#（学号）
      + "           hh=cbind(hh,gg);  "
      + "   };  "
      + "   hh=as.data.frame(hh);  "
      + "   names(hh)=names(z);  "
      + "   studentid=row.names(hh); "
      + "   hh=cbind(studentid,hh); "
      + "   row.names(hh)=1:length(hh[,1]); "
      + "   return(hh); "
      + " }; "
      + " ";


/***根据成绩预测方法一########（类似用众数）**********/

public static String knnpredmethod1 =" knnpredmethod1=function(x){ "
        + " rownames1<-c(rownames(x)); "
        + " colnames1<-c(colnames(x)); "
        + " z1=length(rownames1); "
        + " z2=length(colnames1)-1; "
        + " scorepredict1=matrix(nrow=z1,ncol=(z2)); "
        + " for (j in 2:length(colnames1)) "
        + " {  for(i in 1:z1) "
        + " {tt=t.test(x[[i,j]]); "
        + " tn=density(x[[i,j]]); "
        + " scorepredict1[i,j-1]=tn$x[tn$y==max(tn$y)]; "
        + " } "
        + " }; "
        + " scorepredict1=as.data.frame(scorepredict1); "
        + " scorepredict1=cbind(x[,1],scorepredict1); "
        + " colnames(scorepredict1)=colnames(x); "
        + " rownames(scorepredict1)=rownames1; "
        + " return(scorepredict1); "
        + " }; "
        + " ";
/***根据成绩预测方法二########用群均值**************/

public static String knnpredmethod2=" knnpredmethod2=function(x){ "
        + " rownames1<-c(rownames(x)); "
        + " colnames1<-c(colnames(x)); "
        + " z1=length(rownames1); "
        + " z2=length(colnames1)-1; "
        + " scorepredict1=matrix(nrow=z1,ncol=(z2)); "
        + " for (j in 2:length(colnames1)) "
        + " {  for(i in 1:z1) "
        + " {tt=t.test(x[[i,j]]); "
        + " tn=density(x[[i,j]]); "
        + " scorepredict1[i,j-1]=mean(tn$x); "
        + " } "
        + " }; "
        + " scorepredict1=as.data.frame(scorepredict1); "
        + " scorepredict1=cbind(x[,1],scorepredict1); "
        + " colnames(scorepredict1)=colnames(x); "
        + " rownames(scorepredict1)=rownames1; "
        + " return(scorepredict1); "
        + " }; "
        + " ";

/***根据成绩预测方法三#########用群中值*************/

public static String knnpredmethod3=" knnpredmethod3=function(x){ "
        + " rownames1<-c(rownames(x)); "
        + " colnames1<-c(colnames(x)); "
        + " z1=length(rownames1); "
        + " z2=length(colnames1)-1; "
        + " scorepredict1=matrix(nrow=z1,ncol=(z2)); "
        + " for (j in 2:length(colnames1)){ "
        + "         for(i in 1:z1){ "
        + "                 tt=t.test(x[[i,j]]); "
        + "                 tn=density(x[[i,j]]); "
        + "                 scorepredict1[i,j-1]=median(tn$x); "
        + "         } "
        + " }; "
        + " scorepredict1=as.data.frame(scorepredict1); "
        + " scorepredict1=cbind(x[,1],scorepredict1); "
        + "  colnames(scorepredict1)=colnames(x); "
        + " rownames(scorepredict1)=rownames1; "
        + " return(scorepredict1); "
        + " }; "
        + " ";
/**
 * fitevaluations,yfitres这两个函数是用于分析用成绩预测的三个方法中哪一个最优
 */
//#yfit 是拟合值矩阵，y是真实值矩阵，每行代表一个学生，每列代表一门课程
public static String fitevaluations="fitevaluations = function (yfit,y){ "
	    + " evaluations=matrix(nrow=3,ncol=(length(y[1,])-1)); " 
	    + "         mae=matrix(nrow=1,ncol=(length(y[1,])-1)); "
	    + "         mse=matrix(nrow=1,ncol=(length(y[1,])-1)); "
	    + "         nmse=matrix(nrow=1,ncol=(length(y[1,])-1)); "
	    + " for (j in 2:(length(yfit[1,])))  "
	    + "      { "
	    + "    mae[1,j-1]=mean(abs(yfit[,j]-y[,j]) ); "
	    + "    mse[1,j-1]=mean((yfit[,j]-y[,j])^2); "
	    + "    nmse[1,j-1]=mean((yfit[,j]-y[,j])^2)/mean((mean(y[,j])-y[,j])^2); "
	    + " }; "
	    + "         evaluations=rbind( mae,mse,nmse); "
	    + "         colnames(evaluations)=colnames(yfit[,2:(length(yfit[1,]))]); "
	    + "         rownames(evaluations)=c(\"mae\",\"mse\",\"nmse\"); "
	    + " return (evaluations); "
	    + " }; ";


//#残差
public static String yfitres=" yfitres=function (yfit,y){ "
    + " yfitres=matrix(nrow=length(yfit[,1]),ncol=length(yfit[1,])-1); "
    + " for (j in 2:(length(yfit[1,]))) { "
    + "         yfitres[,j-1]=yfit[,j]-y[,j];  "      
    + " }; "
    + " colnames(yfitres)=colnames(yfit[,2:length(yfit[1,])]); "
    + " rownames(yfitres)=rownames(yfit[,1]); "
    + " return (yfitres); "
    + " };";




}
