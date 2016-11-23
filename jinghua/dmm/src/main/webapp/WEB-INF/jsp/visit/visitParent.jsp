<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!--增加得css-->
<!--日期css-->
<link rel="stylesheet" href="${ctxStatic }/css/manager/jquery-ui-1.9.2.custom.css" type="text/css">
<link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-datetimepicker.css">
<script src="${ctxStatic }/js/manager/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css" />
<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<title>访问量统计</title>
<style type="text/css">
.boxx{
	overflow:hidden;}
.yearbox{
	float:left;
	width:100%;
	font-size:1em;
	font-family:"微软雅黑";
	color:#5e5e5f;
	font-weight:normal;}

.blue_year{
	background-color:#569fe1;
	height:28px;
	color:#fff;
	font-family:"微软雅黑";
	border:none;
	border-radius: 4px;
	padding-left: 1em;
	padding-right: 1em;
}
.dropdown-menu{
	min-width:126px;
	font-size:12px;
	}
.table-condensed > tbody > tr > td, .table-condensed > tbody > tr > th, .table-condensed > tfoot > tr > td, .table-condensed > tfoot > tr > th, .table-condensed > thead > tr > td, .table-condensed > thead > tr > th{
	padding:1px 0px 2px 2px;
}
.datetimepicker td, .datetimepicker th{
	width:0px;
	height:0px;
}
.left{
	background-color:#C9C9C9;
	height:150px;
}
.right{
	background-color:#C9C9C9;
	margin-left:20px;
	height:150px;
}
</style>
</head>
<body>
<div class="container">
  <div class="yearbox" style="margin-bottom:10px;">
   	<div class="year">
       	统计区间：
           <input type="button" class="blue_year" value="起" />
           <input id="start" type="text" class="ui-datepicker-time"  value="${start }" data-date-format="yyyy-mm-dd"/>
           <input type="button" class="blue_year" value="止" />   
           <input id="end" type="text" class="ui-datepicker-time"  value="${end }" data-date-format="yyyy-mm-dd"/>
           
	</div>
  </div>
  
  <div class="container-fluid" id="mydiv">
  </div>
  </div>
	<script>
		$(function() {
			$('#start').datetimepicker({
				language : 'zh-CN',
				minView : 2,
				autoclose : true,
			    format: 'yyyy-mm-dd'
			    
			});
			$('#end').datetimepicker({
				language : 'zh-CN',
				minView : 2,
				autoclose : true,
			    format: 'yyyy-mm-dd'
			    
			});
			var startDate = $("#start").val();
			var endDate = $("#end").val();
			loadData(startDate,endDate);
		});
		$("#start").on('changeDate', function(ev){
			var startDate = $("#start").val();
			var endDate = $("#end").val();
			loadData(startDate,endDate);		
		});
		$("#end").on('changeDate', function(ev){
			var startDate = $("#start").val();
			var endDate = $("#end").val();
			loadData(startDate,endDate);		
		});
		function loadData(start,end){
			$.ajax({
		        type: "POST",
		        async:true,
		        url:"${ctx}/visit/parent/"+start+"/"+end,
		        success: function(data){
		        	debugger;
		            var map = data.data;
		            
		            var html =" <div class='col-xs-4 left'  ><p style='margin-top:10px;color:#fff;text-align:center;font-size:large'>大学生活访问量："+map.total_counts
		            	+ "</p><div class='col-xs-6' style='height:90px;border-right-style:dashed;border-right-color:#fff;border-right-width:1px;'>"
		            	+"<p style='text-align:center;'><img style='width:35px;height:35px;' src='${ctxStatic }/images/wechat/pc.png'/></p>"
		            	+"<p style='text-align:center;font-size:xx-large;color:#fff;'>"+ map.pc_counts +"</p></div>"
		            	+ "<div class='col-xs-6' style='height:90px;'><p style='text-align:center;'><img style='width:35px;height:35px;' src='${ctxStatic }/images/wechat/mobile.jpg'/></p>"
		            	+ "<p style='text-align:center;font-size:xx-large;color:#fff;'>"+ map.mobel_counts+"</p></div>"  
  	        	        + " </div>"
  	        	        + "<div class='col-xs-4 right'>"
  	        	        + "<p style='margin-top:10px;color:#fff;text-align:center;font-size:large'>家长中心访问量："+ map.total_home_counts +"</p>"
			            + "</p><div class='col-xs-6' style='height:90px;border-right-style:dashed;border-right-color:#fff;border-right-width:1px;'>"
		            	+"<p style='text-align:center;'><img style='width:35px;height:35px;' src='${ctxStatic }/images/wechat/url.jpg'/></p>"
		            	+"<p style='text-align:center;font-size:xx-large;color:#fff;'>"+ map.url_counts +"</p></div>"
		            	+ "<div class='col-xs-6' style='height:90px;'><p style='text-align:center;'><img style='width:35px;height:35px;' src='${ctxStatic }/images/wechat/wechat.jpg'/></p>"
		            	+ "<p style='text-align:center;font-size:xx-large;color:#fff;'>"+ map.wechat_counts+"</p></div>"  
		        	    + " </div>";
  	        	        +"</div>";
		        	$("#mydiv").empty();
		            $("#mydiv").append(html);
		        }
		    });
		}
		
	</script>
</body>
</html>
