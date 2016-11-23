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
<jsp:include page="../topCssJs.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/manager/teacher/teanum.js"></script>
<link rel="stylesheet" href="${ctxStatic }/css/manager/zengjia.css">
<script type="text/javascript" src="${ctxStatic }/product/common/page.js"></script>
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
					<h3>教职工人数变化趋势</h3>
					<p>逐级下钻的形式，从专业、学科分类、年龄段、民族对历年来教职工的趋势进行，横向与纵向对比统计。</p>
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

			<div class="people" id="people">
				<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
				<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/product/manager/teadept.js"></script>
				<script type="text/javascript">
					function selected(deptId){
						getOtherData(deptId);
					}
				</script>
			</div>

			<!-- 统计图表开始-->
			<div id="timeDiv" style="margin-top: 2%">
				
			</div>
			<div id="mapDiv" style="width: 80%;height: 400px;margin-top: 2%">
				
			</div>
			<div>
                <ul style="display:block;">
                   <div class="diqu_bg">
                   		<table class="table"  width="100%" border="0" cellpadding="0" cellspacing="0">
              				<thead>
                              <tr border="0">
                                  <th class="number_b">&nbsp;</th>
                                  <th>姓名</th>
                                  <th>职工号</th>
                                  <th>所属部门</th>
                                  <th>入职时间</th>
                                  <th>职称</th>
                                  <th>来源</th>
                                  <th>编制类别 </th>
                              </tr>
                           	</thead>
                          	<tbody bodyFlag="tableBody">
                          </tbody>
                      </table>
                      <div pageFlag="tablePage"></div>
                   </div>
                   
                </ul>
                
            </div>
			
		</div>
			

	</div>
		<script>
			//组织机构滚动回调函数
			function goMajor(deptId){
				loadTitle(deptId,true);
				loadData(deptId);
				loadTableData({startIndex:1,numPerPage:10,deptId:deptId});
			}
			function selected(deptId){
				loadData(deptId);
				loadTableData({startIndex:1,numPerPage:10,deptId:deptId});
			}
			
			function loadTableData(pageParam){
				var deptId = pageParam.deptId;//当前的组织机构的id
				var data_table=['2011','2015',deptId,pageParam.currentPage || 1,pageParam.numPerPage || 1];
				$.callService("teachersNumsStatisticsService","teachersClassificationNumsTable",data_table,function (d){
					showTable(d);
				});
			}
			
			function showTable(d){
				var domTable=$("tbody[bodyFlag='tableBody']")[0];
				if(domTable){
					$(domTable).html('');
					var i=1;
					$.each(d.resultList,function(i,o){
						var str="<tr><td>"+(o['NUM'] || i)+"</td>"
						+"<td class='huihui'>"+(o['NAMES'] || '--')+"</td>"//姓名
						   +"<td class='huihui'>"+(o['TEA_NO'] || '--')+"</td>"//职工号
						   +"<td class='huihui'>"+(o['TCDNAME'] || '--')+"</td>"//所属部门
						   +"<td class='huihui'>"+(o['THRDATE'] || '--')+"</td>"//入职时间
						   +"<td class='huihui'>"+(o['TCZNAME'] || '--')+"</td>"//职称
						   +"<td class='huihui'>"+(o['TTSNAME'] || '--')+"</td>"//教职工来源
						   +"<td class='huihui'>"+(o['TCNAME'] || '--')+"</td>"//编制类别
						   //+"<td class='huihui'>"+(o['CITIESNAME'] || 0)+"</td>"//初中起点
						   //+"<td class='huihui'>"+(o['CITIESNAME'] || 0)+"</td>"//高中起点
						   +"</tr>";
						
						$(domTable).append(str);
					});
					var page=new dmm.Page(d,loadTableData,window);
					page.render($("div[pageFlag='tablePage']")[0]);
				}
			}
			function loadData(deptId){
				var data=['2011','2015',deptId,'allTeasCounts'];
				$.callService("teachersNumsStatisticsService","teachersClassificationNums",data,function (d){
					teaNumBarOrline("mapDiv", d);
				});
			}
			
			$(function(deptId) {
				var deptId = '${ids}';//当前的组织机构的id
				loadTeaTitle(deptId,false);
				var myMapChart;
				var leve=0;
				var mt="china";
				loadTableData({startIndex:1,numPerPage:10,deptId:deptId});
				loadData(deptId);
			})
		</script>
</body>
</html>
