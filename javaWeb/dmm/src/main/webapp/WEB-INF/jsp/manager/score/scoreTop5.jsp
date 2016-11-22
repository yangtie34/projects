<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/right.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/xiala.css" />
<!--增加得css-->
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/zengjia.css">
<!--日期css-->
<link rel="stylesheet" href="${ctxStatic }/css/manager/jquery-ui-1.9.2.custom.css" type="text/css">
<script src="${ctxStatic }/js/manager/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic }/js/common/server.js"></script>

<meta name="description" content="">
<meta name="author" content="">
<title>学生系统</title>

</head>
<body>
	<div class="content">
		<!--左侧导航开始-->
		<jsp:include page="../menu.jsp"></jsp:include>
		<!--左侧导航结束-->
		<!--右侧内容开始-->
		<div class="con_right">
			<!--右侧头部开始-->
			<div class="right_title">
                <div class="title_left">
                    <h3>学霸TOP5</h3>
                    <p>大三以上学生，总成绩在3800以上，按综合平均绩点（GPA）从高到低查找学霸TOP5。等级制考试成绩不计入计算！
                <br><span style="margin-left: 10px;">公式一：综合平均绩点的计算公式为：(课程学分1*绩点+课程学分2*绩点+......+课程学分n*绩点)/(课程学分1+课程学分2+......+课程学分n) </span>
                <br><span style="margin-left: 10px;">公式二：每门课的成绩与等级 及 绩点的对应关系是： </span>
                <br><span style="margin-left: 60px;">90-100  =  A  = 4.0</span>
                <br><span style="margin-left: 60px;">85-89   =  A- = 3.7</span>
                <br><span style="margin-left: 60px;">82-84   =  B+ = 3.3</span>
                <br><span style="margin-left: 60px;">78-81   =  B  = 3.0</span>
                <br><span style="margin-left: 60px;">75-77   =  B- = 2.7</span>
                <br><span style="margin-left: 60px;">71-74   =  C+ = 2.3</span>
                <br><span style="margin-left: 60px;">66-70   =  C  = 2.0</span>
                <br><span style="margin-left: 60px;">62-65   =  C- = 1.7</span>
                <br><span style="margin-left: 60px;">60-61   =  D  = 1.3</span>
                <br><span style="margin-left: 60px;">补考60  =  D-  = 1.0</span>
                <br><span style="margin-left: 60px;">60以下  =  F   = 0 </span></p>
                </div>
                <div class="title_right">
                    <img class="face" src="${ctxStatic }/images/manager/face.png" alt="face" />
                    <div class="styled-select">
                      <select name="select" id="select">
                        <option>张广生，艺术学院副院长</option>
                        <option>修改密码</option>
                        <option>注销</option>
                        <option>退出</option>
                      </select>
                    </div>
                </div>
            </div>
      
			<!--右侧头部结束-->
			<!--专业及人数开始-->
			<div class="people" id="people"> 
				<script type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
				
				<script type="text/javascript" src="${ctxStatic }/product/manager/dept.js"></script>
				
				<script>
					function goMajor(deptId){
						loadTitle(deptId,true);
						loadTable(deptId,false);				 
					}
					function selected(deptId){
						loadTable(deptId,true);
					}
				</script>
					
					
			</div>
				<div class="excel_box" id="excel_box"></div>
				</div>
        </div>
			<script type="text/javascript">
				$(function() {
					var deptId = '${dept_id}';//当前的组织机构的id
					loadTitle(deptId,false);
					loadTable(deptId,false);
				});
				function loadTable(deptId,flag){
					var data = [ deptId,flag];
					$.callService('stuTop5Service','getStuTop5ByGpa',data,function(d){
						var html1 = "<table class=\"table\"  width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
								"<thead><tr border=\"0\"><th class=\"number_b\">&nbsp;</th><th>学号</th><th>姓名</th><th>性别"+
		                      "</th><th>专业</th><th>年级</th><th>总成绩</th><th>平均成绩</th><th>综合平均绩点（GPA）</th></tr><thead><tbody>";
		                var html2 = "";
						for(var i=0;i<d.length;i++){
							html2 = html2 + "<tr><td id=\"bai_number\">"+ d[i].RM+"</td><td  class=\"huihui\">"+ d[i].STU_ID +"</td><td>"+ d[i].STU_NAME+"</td><td>"+d[i].STU_SEX+
		                      "</td><td>"+ d[i].MAJOR_NAME+"</td><td>"+ d[i].ENROLL_GRADE+"</td><td>"+ d[i].ZCJ+"</td><td>"+ d[i].PJCJ+"</td><td>"+ d[i].GPA +"</td></tr>";
						}
						
						var html3 = "</tbody></table>";
						var htmlTable = html1+html2+html3;
						$("#excel_box").empty();
						$("#excel_box").append(htmlTable);
					});
					
				}
			</script>
</body>
</html>
