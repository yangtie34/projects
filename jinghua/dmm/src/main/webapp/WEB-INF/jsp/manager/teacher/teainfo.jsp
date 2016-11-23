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
<link rel="stylesheet" type="text/css" 　media="screen and (max-device-width: 400px)" 　href="tinyScreen.css" />
<link rel="stylesheet" type="text/css" 　media="screen and (min-width: 400px) and (max-device-width: 600px)" 　href="smallScreen.css" />

<jsp:include page="../topCssJs.jsp"></jsp:include>

<!-- 政治面貌  js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/teacher/dashboard.js"></script>
<script type="text/javascript" src="${ctxStatic }/product/manager/chinaMap.js"></script>
<meta name="description" content="">
<meta name="author" content="">
<title>教职工概况信息</title>
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
					<h3>在校教职工基本概况统计</h3>
					<p>从教职工性别、年龄、民族、政治面貌、学历、类别、学制、户口性质、科类、年级等多方面分析在校教职工分布组成情况</p>
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
				<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
				<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/product/manager/teadept.js"></script>
				<script type="text/javascript">
					function selected(deptId){
						getOtherData(deptId);
					}
				</script>	
			</div>
			<!--专业及人数结束-->

			<!-- 统计图表开始-->
			<div class="soft_con1" style="background:url(${ctxStatic }/images/manager/xtxi_jt1.gif);"></div>
			<!-- 分布表开始-->
			<div class="fenbu_box">
				<div id="xzgl" style="display: block">
					<div class="fenbu">
						<div class="fb">
							<!--性别分布-->
							<div class="fenbu_one">
								<h1 class="sex_title">性别分布</h1>
								<div class="fbbox_1">
									<div class="fbcont">
										<p class="ps">
										</p>
										<div id="main6" style="width: 505px; height: 275px" class="fbcont_img">
										</div>
									</div>
								</div>
							</div>
							<!--年龄分布-->
							<div class="fenbu_one">
								<h1 class="age_title">年龄分布</h1>
								<div class="fbbox_2">
									<div class="fbcont">
										<div id="age_main" style="height: 272px;"></div>
									</div>
								</div>
							</div>
							<!--民族组成-->
							<div class="fenbu_one">
								<h1 class="mzzc_title">民族组成</h1>
								<div class="fbbox_3">
									<div class="fbcont">
										<div id="nation_main" style="height: 272px;"></div>
									</div>
								</div>
							</div>

							<!--政治面貌分布-->
							<div class="fenbu_one">
								<h1 class="zz_title">政治面貌分布</h1>
								<div class="fbbox_4">
									<div class="fbcont">
										<div id="politics_Tea" style="height: 274px; width: 476px;"></div>
									</div>
								</div>
							</div>

							<!--来源地统计-->
							<div class="fenbu_one">
								<h1 class="dl_title">来源地统计</h1>
								<div class="fbbox_5">
									<div class="fbcont">
										<div class="fbcont_img4">
											<div id="mapTea_01" style="height: 218px; width: 472px; background-color: red;"></div>
										</div>
									</div>
								</div>
							</div>
							<!--人员学科组成-->
							<div class="fenbu_one">
								<h1 class="ryxk_title">人员学科组成</h1>
								<div class="fbbox_6">
									<div class="fbcont">
										<div id="bzt_Tea" style="height: 274px; width: 476px;"></div>
										<%--饼状图假数据 --%>
									</div>
								</div>
							</div>
							<!--学历组成-->
							<div class="fenbu_one">
								<h1 class="xl_title">学历组成</h1>
								<div class="fbbox_7">
									<div class="fbcont">
										<div id="tea_education" style="height: 272px;"></div>
									</div>
								</div>
							</div>
							<!--学位组成-->
							<div class="fenbu_one">
								<h1 class="xw_title">学位组成</h1>
								<div class="fbbox_8">
									<div class="fbcont">
										<div id="tea_degree" style="height: 272px;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="crm" style="display: none">
					<div class="fenbu">
						<div class="fb">sss</div>
					</div>
				</div>
				<div id="xmgl" style="display: none">
					<div class="fenbu">
						<div class="fb">5555</div>
					</div>
				</div>
				<!-- 分布表结束-->
			</div>
			<!--右侧内容结束-->
		</div>
		<script>
			//性别分布
			function teaSexDistribute(deptId) {
				var data=[deptId];
				$.callService('teacherEmpInfoService','teachersSexComposition',data,function(d){
					sexTeaConfigration(d);
				});
			}
			//年龄分布
			function teaAgeDistribute(deptId) {
				var data = [deptId];
				$.callService('teacherEmpInfoService', 'teachersAgesComposition', data, function(d) {
					ageTeaConfigration(d);
				});
			}
			//政治面貌
			function politicalDistribute(deptId) {
				var data = [deptId];
				$.callService('teacherEmpInfoService', 'teachersPoliticalStatus', data, function(d) {
					teaDashboard(d);
				});
			}
			//民族组成
			function nationDistribute(deptId) {
				var data = [deptId];
				$.callService('teacherEmpInfoService', 'teachersNationsComposition', data, function(d) {
					teaNationConfigration(d);
				});
			}
			//来源地
			function sourceDistribute(deptId) {
				//全国/全省/全市的教职工生源地(0:全国，1:全省，2:全市)
				var map=null;
				var loadSource=function(params){
					var data=[params.level || 0,deptId,params.sourceId || ''];
					$.callService('teacherEmpInfoService','teachersSourceLand',data,function(d){
						var dataArray=[];
						$.each(d,function(i,o){dataArray.push({name:o.NAME,value:o.COUNTS,id:o.SOURCEID || i})});
						if(map==null){
							map=new $.ChinaMap("mapTea_01",dataArray,loadSource,this);
						}else{
							map.draw(dataArray,params.mapType);
						}
					});
				}
				loadSource(0,'');//页面进来的时候自动加载中国全省
			}
			//人员学科组成
			function personelSubjectDistribute(deptId) {
				var data = [deptId];
				$.callService('teacherEmpInfoService', 'teachersSubjectsComposition', data, function(d) {
					viewTeaBzt(d);
				});
			}
			//学历学位调用同一个  js方法
			//学历组成
			function educationDistribute(deptId) {
				var data = [ deptId];
				$.callService('teacherEmpInfoService', 'teachersEducationalBackground', data, function(d) {
					teaEducationConfigration(d, "tea_education");
				});
			}
			//学位组成
			function degreeDistribute(deptId) {
				var data = [ deptId];
				$.callService('teacherEmpInfoService', 'teachersDegreeComposition', data, function(d) {
					teaEducationConfigration(d, "tea_degree");
				});
			}

			function getOtherData(deptId) {
				teaSexDistribute(deptId);
				teaAgeDistribute(deptId);
				nationDistribute(deptId);
				//sourceDistribute(level,deptId,sexthIdCard);//来源地统计
				sourceDistribute(deptId);//来源地统计
				educationDistribute(deptId);//
				degreeDistribute(deptId);// 学位组成
				politicalDistribute(deptId);//政治面貌
				personelSubjectDistribute(deptId);//人员学科组成
			}

			$(function() {
				var deptId = '${dept_id}';//当前的组织机构的id
				loadTeaTitle(deptId,false);
				//获取其他统计数据
				getOtherData(deptId);
			});
		</script>
</body>
</html>
