<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

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
		<div class="right_title">
        	<div class="boxx">
                <div class="title_left">
                    <h3>图书借阅TOP10</h3>
                    <p>图书借阅TOP10 </p>
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
            <div class="yearbox">
            	<div class="year">
                	统计区间：
                    <input type="button" class="blue_year" value="起" />
                    <input type="text" class="ui-datepicker-time" readonly value="03/18/2015" />
                    <input type="button" class="blue_year" value="止" />   
                    <input type="text" class="ui-datepicker-time" readonly value="03/18/2015" />
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
						loadTable(deptId,false,'2015-03-01','2015-03-10');
					}
					function selected(deptId){
						loadTable(deptId,true,'2015-03-01','2015-03-10');
					}
				</script>
					
			</div>
					<div id="boxBook">
					</div>
					<div id="boxStu">
					</div>
			</div>
			</div>
			<script type="text/javascript">
				$(function() {
					var deptId = '${dept_id}';//当前的组织机构的id
					loadTitle(deptId,false);
					var startDate = '2015-03-01';
					var endDate = '2015-05-01';
					loadTable(deptId,false,startDate,endDate);
				});
				function loadTable(deptId,flag,startDate,endDate){
					var data = [startDate,endDate,deptId,flag];
					$.callService('bookBorrowTop10Service','getBookBorrowTop10',data,function(d){
						var html1 = "<table class=\"table\"  width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
								"<thead><tr border=\"0\"><th class=\"number_b\">&nbsp;</th><th>书名</th><th>被借阅次数</th></tr><thead><tbody>";
		                var html2 = "";
						for(var i=0;i<d.length;i++){
							html2 = html2 + "<tr><td id=\"bai_number\">"+ d[i].RM+"</td><td  class=\"huihui\">"
							+ d[i].BOOK_NAME +"</td><td><a href=\"${ctx}/manager/book/borrowTop10/stu?deptId="+deptId+"&isLeaf="+flag+"&bookId="
							+d[i].BOOK_ID+"&bookName="+d[i].BOOK_NAME+"&startDate="
							+startDate+"&endDate="+endDate+"\">"
							+ d[i].SUMS+"</a></td></tr>";
						}
						
						var html3 = "</tbody></table>";
						var htmlTable = html1+html2+html3;
						$("#boxBook").empty();
						$("#boxBook").append(htmlTable);
					});
					$.callService('bookBorrowTop10Service','getBookBorrowStuTop10',data,function(d){
						var html1 = "<table class=\"table\"  width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
								"<thead><tr border=\"0\"><th class=\"number_b\">&nbsp;</th><th>学号</th><th>姓名</th><th>借阅次数</th></tr><thead><tbody>";
		                var html2 = "";
						for(var i=0;i<d.length;i++){
							html2 = html2 + "<tr><td id=\"bai_number\">"+ d[i].RM+"</td><td  class=\"huihui\">"+ d[i].STU_ID +"</td><td><a href=\"${ctx}/manager/stuAll/allinfo/"+d[i].STU_ID+"\">"
							+ d[i].STU_NAME+"</a></td><td><a href=\"${ctx}/manager/book/borrowTop10/book?stuId="+d[i].STU_ID+"&stuName="+d[i].STU_NAME+"&startDate="+startDate+"&endDate="+endDate+"\">"+ d[i].SUMS+"</a></td></tr>";
						}
						
						var html3 = "</tbody></table>";
						var htmlTable = html1+html2+html3;
						$("#boxStu").empty();
						$("#boxStu").append(htmlTable);
					});
				}
			</script>
</body>
</html>
