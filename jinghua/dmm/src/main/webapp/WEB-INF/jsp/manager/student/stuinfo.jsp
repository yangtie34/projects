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
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/map3.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/ring.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/stu_sex.js"></script>

<!-- 政治面貌  js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/dashboard.js"></script>

<!-- 来源地统计 js -->
<%-- <script type="text/javascript"
	src="${ctxStatic }/product/manager/student/mapStu.js"></script> --%>
<!-- 人员学科组成  js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/bingzhuangtu.js"></script>
<!-- 引入学历组成js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/stu_education.js"></script>
<!-- 引入民族组成js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/stu_nation.js"></script>
<!-- 引入年龄分布js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/stu_age.js"></script>


<script type="text/javascript" src="${ctxStatic }/product/manager/MapData.js"></script>
<script type="text/javascript" src="${ctxStatic }/product/manager/chinaMap.js"></script>
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
					<h3>在校生基本概况统计</h3>
					<p>从学生性别、年龄、民族、政治面貌、学历、类别、学制、户口性质、科类、年级等多方面分析在校生分布组成情况</p>
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
				<script type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
				
				<script type="text/javascript" src="${ctxStatic }/product/manager/dept.js"></script>
				
				<script>
					function goMajor(deptId){
						loadTitle(deptId,true);
						yxEducationDistribute(deptId);
						educationNumsDistribute(deptId);			 
					}
					function selected(deptId){
						yxEducationDistribute(deptId);
						educationNumsDistribute(deptId);
					}
				</script>
					
			</div>
			<!--专业及人数结束-->

			<div class="tongji_box">
				<!--对比统计-->
				<div class="tongji_1">
					<h3 class="tongji_title">周口师范学院 - 艺术学院美术系专业学生类别对比统计</h3>
					<div class="tongjik_tu">
						<div id="main3" class="tongji_form" style="width: 800px; height: 400px">
						</div>
					</div>
				</div>
				<!--学历对比-->

				<!--专科-->
				<div class="soft_con_icon" id="xlfb"></div>


			</div>
			<!-- 统计图表结束-->

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
											<%-- <span><img src="${ctxStatic }/images/manager/icon_04.png" alt="专科" />专科</span> <span><img src="${ctxStatic }/images/manager/icon_05.png" alt="本科" />本科</span> --%>
										</p>
										<div id="main6" style="width: 505px; height: 275px" class="fbcont_img">
											<!-- <h2 class="img_title">
												女生208人 <br />占总人数的80%
											</h2> -->
											<%--  <img class="img_tu" src="${ctxStatic }/images/manager/xtxi_sex.png" alt="sex" /> --%>
										</div>
										<!-- <p class="ps_1">艺术学院美术类专业数据</p> -->
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
										<div id="politics_Stu" style="height: 274px; width: 476px;"></div>
									</div>
								</div>
							</div>

							<!--来源地统计-->
							<div class="fenbu_one">
								<h1 class="dl_title">来源地统计</h1>
								<div class="fbbox_5">
									<div class="fbcont">
										<div class="fbcont_img4">
											<div id="mapStu_01" style="height: 218px; width: 472px; background-color: red;"></div>
										</div>
									</div>
								</div>
							</div>

							<!--人员学科组成-->
							<div class="fenbu_one">
								<h1 class="ryxk_title">人员学科组成</h1>
								<div class="fbbox_6">
									<div class="fbcont">
										<div id="bzt_Stu" style="height: 274px; width: 476px;"></div>
										<%--饼状图假数据 --%>
									</div>
								</div>
							</div>

							<!--学历组成-->
							<div class="fenbu_one">
								<h1 class="xl_title">学历组成</h1>
								<div class="fbbox_7">
									<div class="fbcont">
										<div id="stu_education" style="height: 272px;"></div>
									</div>
								</div>
							</div>

							<!--学位组成-->
							<div class="fenbu_one">
								<h1 class="xw_title">学位组成</h1>
								<div class="fbbox_8">
									<div class="fbcont">
										<div id="stu_degree" style="height: 272px;"></div>
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
			//柱状图那一行
			function yxEducationDistribute(deptId) {

				var data = [ deptId, false ];

				$.callService('internalStuInfoService', 'stusOfNumsContrastStatistics', data, function(d) {
					showMap3(d[0]);
				});
			};

			//学历人数分布图表
			function educationNumsDistribute(deptId) {
				//获取组织机构各学历的人数和学校的总人数，生成各学历人数与总人数比的饼状图，获取后，取第一个学历，再获取第一个学历的各个图表展示
				var data = [ deptId, false ];
				addXlfb("xlfb", data,function(eduId){
					getOtherData(deptId,eduId);
					$(".soft_con1").css({
						"background" : " url(${ctxStatic }/images/manager/xtxi_jt"+i+".gif)"
					});
				});

			};

			//性别分布
			function stuSexDistribute(deptId, eduId) {
				
				var data=[eduId,deptId,false];
				$.callService('internalStuInfoService','stusSexComposition',data,function(d){
					sexConfigration(d);
				});

			}
			//年龄分布
			function stuAgeDistribute(deptId, eduId) {
				var data = [ eduId, deptId, false ];
				$.callService('internalStuInfoService', 'stusAgesComposition', data, function(d) {
					ageConfigration(d);
				});
			}
			//政治面貌
			function politicalDistribute(eduId, deptId) {
				var data = [ eduId, deptId, false ];
				$.callService('internalStuInfoService', 'stuPoliticalStatus', data, function(d) {
					viewDashboard(d);
				});
			}
			//民族组成
			function nationDistribute(deptId, eduId) {
				var data = [ eduId, deptId, false ];
				$.callService('internalStuInfoService', 'stusNationsComposition', data, function(d) {
					nationConfigration(d);
				});
			}
			
			//来源地
			function sourceDistribute(deptId, eduId,isLeaf) {
				var map=null;
				var loadSource=function(params){
					var data=[params.level || 0,eduId,deptId,isLeaf || false,params.sourceId || ''];
					$.callService('internalStuInfoService','stuSourceLand',data,function(d){
						var dataArray=[];
						$.each(d,function(i,o){dataArray.push({name:o.NAME,value:o.COUNTS})});
						if(map==null){
							map=new jQuery.ChinaMap("mapStu_01",dataArray,loadSource,this);
						}else{
							map.draw(dataArray,params.mapType);
						}
					});
				}
				loadSource(0,'');
			}
			//人员学科组成
			function personelSubjectDistribute(eduId, deptId) {
				var data = [ eduId, deptId, false ];
				$.callService('internalStuInfoService', 'stuSubjectsComposition', data, function(d) {
					viewBzt(d);
				});
			}

			//学历组成
			function educationDistribute(deptId) {
				var data = [ deptId, false ];
				$.callService('internalStuInfoService', 'stuEducationalBackground', data, function(d) {
					educationConfigration(d, "stu_education");
				});
			}

			//学位组成
			function degreeDistribute(deptId) {
				var data = [ deptId, false ];
				$.callService('internalStuInfoService', 'stuDegreeComposition', data, function(d) {
					educationConfigration(d, "stu_degree");
				});
			}
			
			function hoverData(eduId,deptId,i){
				getOtherData(deptId,eduId);
				$(".soft_con1").css({
					"background" : " url(${ctxStatic }/images/manager/xtxi_jt"+i+".gif)"
				});
			}

			function getOtherData(deptId, eduId) {
				politicalDistribute(eduId, deptId);
				stuSexDistribute(deptId, eduId);
				stuAgeDistribute(deptId, eduId);
				nationDistribute(deptId, eduId);
				sourceDistribute(deptId, eduId);//来源地统计
				personelSubjectDistribute(eduId, deptId);//人员学科组成
				educationDistribute(deptId);
				degreeDistribute(deptId);
			}

			$(function() {
				var deptId = '${dept_id}';//当前的组织机构的id
				loadTitle(deptId,false);
				
				yxEducationDistribute(deptId);
				
				educationNumsDistribute(deptId);
				
			});
		</script>
		</div>
</body>
</html>
