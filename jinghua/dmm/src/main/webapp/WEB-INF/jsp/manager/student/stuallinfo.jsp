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
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/zhaosheng.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/xueshengquanjing.css">
<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/common/server.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/echarts.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/echarts-all.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
<!--日期css-->
<link rel="stylesheet" href="${ctxStatic }/css/manager/jquery-ui-1.9.2.custom.css" type="text/css">

<script type="text/javascript" src="${ctxStatic }/product/manager/student/stuallinfo.js"></script>

<script src="${ctxStatic }/product/manager/student/stuallcard.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic }/product/manager/student/stuallinfo_culture_score.js"></script>
<script type="text/javascript" src="${ctxStatic }/product/manager/student/stu_score_major_class.js"></script>
<script type="text/javascript" src="${ctxStatic }/product/manager/student/stu_class_major_ranks.js"></script>
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
		    <h3>学生全景</h3>
		    <p>根据学生姓名、学号查询学生从入学到毕业期间在校的全景信息。包含基本信息、奖惩、社团活动、课程、成绩、职务等一切学生相关的数据。</p>
		  </div>
		  <div class="title_right"> <img class="face" src="${ctxStatic }/images/manager/face.png" alt="face" />
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
		<script type="text/javascript">
				//=点击展开关闭效果=
				function openShutManager(oSourceObj,oTargetObj,shutAble,oOpenTip,oShutTip){
				var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
				var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
				var openTip = oOpenTip || "";
				var shutTip = oShutTip || "";
				if(targetObj.style.display!="none"){
				   if(shutAble) return;
				   targetObj.style.display="none";
				   if(openTip  &&  shutTip){
					sourceObj.innerHTML = shutTip; 
				   }
				} else {
				   targetObj.style.display="block";
				   if(openTip  &&  shutTip){
					sourceObj.innerHTML = openTip; 
				   }
				}
				}
				</script>
		<div class="people">
		  <div class="title_box">
		    <h2 class="title">学籍基本信息</h2>
		    <a class="open" href="###" onClick="openShutManager(this,'box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" /> </a> </div>
		  <!--box-->
		  <div id="box" class="gaikuang-content">
		    <div class="gaikuang-div gaikuang-div-center"><img class="face-bg-default" src="${ctxStatic }/images/manager/face.png">
		      <p><span class="zll-user-name">${stuMap.stu_name }</span> ${stuMap.stu_age }岁</p>
		      <p>${stuMap.stu_dept }</p>
		      <p class="zll-user-ccc">${stuMap.stu_nation }·${stuMap.stu_politics } </p>
		      <p class="zll-user-impt">${stuMap.stu_id }</p>
		    </div>
		    <div class="gaikuang-div">
		      <p>出生日期：${stuMap.stu_birthday }</p>
		      <p>身份证号：${stuMap.stu_idno }</p>
		      <p>户口所在地：${stuMap.place_domicile } </p>
		      <p>生源地：${stuMap.stu_origin }</p>
		      <p>户籍信息：${stuMap.stu_anmelden }</p>
		    </div>
		    <div class="gaikuang-div">
		      <p>学籍状态：${stuMap.stu_roll }</p>
		      <p>学习形式：全日制 </p>
		      <p>招生批次：${stuMap.enroll_grade }秋季</p>
		      <p>入学日期：${stuMap.enroll_date } </p>
		      <p>预计毕业日期：${stuMap.leave_date }</p>
		    </div>
		    <div class="gaikuang-div">
		      <p>学制：${stuMap.length_school }年制</p>
		      <p>班主任：${stuMap.class_tea_name }</p>
		      <p>班级：${stuMap.class_name } </p>
		      <p>宿舍：${stuMap.lou_name }号楼#${stuMap.ceng_name}#${stuMap.dorm_name }房间#${stuMap.berth_name }号床</p>
		      <p>联系电话：${stuMap.stu_tel } <a class="phone-message" href="#">短信</a></p>
		      <p>班主任联系电话：${stuMap.class_tea_tel } <a class="phone-message" href="#">短信</a></p>
		    </div>
		    <div class="gaikuang-div">
		      <p>家庭地址：${stuMap.stu_addr }</p>
		      <p>联系电话：${stuMap.stu_tel } <a class="phone-message" href="#">短信</a></p>
		      <p>班主任联系电话：${stuMap.class_tea_tel } <a class="phone-message" href="#">短信</a></p>
		    </div>
		  </div>
		</div>
		<!--专业及人数结束-->
		<!--荣誉奖励-->
		<div class="people">
		  <div class="title_box zhaosheng-title-bg">
		    <h2 class="title zhaosheng-title">荣誉及奖励 <span class="title-infomation"> <em class="title-information-red"></em> </span> </h2>
		    <a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'rongyu-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" /> </a> </div>
		  <!--box-->
		  <div id="rongyu-box" class="tongji-content">
		    <div class="before-title-info"><em class="before-yellow-text">自入学以来，${stuMap.stu_name }所获得的荣誉及奖助信息：</em>
		    奖学金 <em class="before-number-ipt">${awardNumers }次</em>
		    助学金 <em class="before-number-ipt">${subsidyNumbers}次</em></div>
		    <table class="tongji-table" width="100%" cellpadding="0" cellspacing="0">
		      <tr>
		        <th width="15%">学年</th>
		        <th width="15%">学期</th>
		        <th width="15%">类型</th>
		        <th width="15%">金额</th>
		        <th width="15%">状态</th>
		        <th width="25%">备注</th>
		      </tr>
		      <tr>
		        <th class="tongji-th-liubai"  colspan="6"></th>
		      </tr>
		      <c:forEach var="aas" items="${awardAndSubsidy }">
			      <tr class="tongji-tr-exicon">
			        <td>${aas.school_year }</td>
			        <td>${aas.batch }</td>
			        <td> ${aas.name }</td>
			        <td>${aas.money }</td>
			        <td> --</td>
			        <td>-- </td>
			      </tr>
		      </c:forEach>
		    </table>
		  </div>
		</div>
		<!--荣誉奖励end-->
		<!-- 违纪处分-->
		<div class="people">
		  <div class="title_box zhaosheng-title-bg">
		    <h2 class="title zhaosheng-title">违纪处分<!-- <span class="title-infomation"> ${stuDate} 由于 <em class="title-information-red">${violateName}</em> 被给予<em class="title-information-red"> ${punishName}</em> 处分! </span>  --> </h2>
		    <a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'weiji-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" /> </a> </div>
		  <!--box-->
		  <div id="weiji-box" class="tongji-content">
		    <div class="before-title-info"><em class="before-yellow-text">自入学以来，${stuName}违纪处分信息：</em>
		    <c:forEach items="${violateNumsList}" var="punished">
		   ${punished.violateName} <em class="before-number-ipt">${punished.violateCounts}</em>
		    </c:forEach>
		    </div>
		    <table class="tongji-table" width="100%" cellpadding="0" cellspacing="0">
		      <tr>
		        <th width="20%">学年</th>
		        <th width="20%">学期</th>
		        <th width="20%">日期</th>
		        <th width="20%">违纪类型</th>
		        <th width="20%">处分类型</th>
		      </tr>
		      <tr>
		        <th class="tongji-th-liubai"  colspan="5"></th>
		      </tr>
		      <c:forEach items="${punishList}" var="punishInfo">
		      <tr class="tongji-tr-exicon">
		        <td>${punishInfo.schoolYear}</td>
		        <td>${punishInfo.termName}</td>
		        <td>${punishInfo.stuDate}</td>
		        <td>${punishInfo.violateName}</td>
		        <td>${punishInfo.punishName}</td>
		      </tr>
		      </c:forEach>
		    </table>
		  </div>
		</div>
		<!-- 违纪处分end-->
		<!--请销假-->
			<div class="people">
				<div class="title_box zhaosheng-title-bg">
					<h2 class="title zhaosheng-title">
						请销假 <span class="title-infomation" ></span>
					</h2>
					<a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'qingxiaojia-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" />
					</a>
				</div>
				<!--box-->
				<div id="qingxiaojia-box" class="tongji-content">
					<div class="before-title-info">
						<em class="before-yellow-text">
						</em>
					</div>
					<table class="tongji-table" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<th width="2%"></th>
							<th width="25%">请假时限</th>
							<th width="13%">请假天数</th>
							<th width="20%">实际离校时间</th>
							<th width="20%">实际返校时间</th>
							<th width="20%">当前状态</th>
						</tr>
						<tr>
							<th class="tongji-th-liubai" colspan="6"></th>
						</tr>
					</table>
				</div>
			</div>
			<!--请销假-->
		<!--宿舍-->
		<div class="people">
		  <div class="title_box zhaosheng-title-bg">
		    <h2 class="title zhaosheng-title">宿舍 <span class="title-infomation"> </span> </h2>
		    <a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'sushe-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" /> </a> </div>
		  <!--box-->
		  <div id="sushe-box" class="tongji-content">
		    <div class="before-title-info"><em class="before-yellow-text">现在宿舍：${stuMap.lou_name }号楼#${stuMap.ceng_name}#${stuMap.dorm_name }房间#${stuMap.berth_name }号床。</em><a class="alink-default" href="#"></a> </div>
		    <div class="title-bottom-line">他的室友 </div>
		    <div class="sushe-box">
		    <c:forEach var="roommate" items="${roommate }">
			      <div class="sushe-chuangwei">
			        <h3 class="chuangwei-h3">${roommate.berth_name }号床</h3>
			        <ul class="sushe-chuangwei-ul">
			          <li>
			            <p><span>${roommate.stu_name }</span> <span>${roommate.stu_age }岁</span> <span class="sushe-stu-phone">${roommate.stu_tel }</span> <a class="phone-message sushe-message" href="#">短信</a></p>
			          </li>
			          <li>
			            <p><span>班级：${roommate.class_name }</span> <span>班主任：${roommate.tea_name }</span></p>
			          </li>
			          <li>
			            <p><span>生源地：${roommate.stu_origin }</span> </p>
			          </li>
			        </ul>
			      </div>
		      </c:forEach>
		  </div>
		</div>
		</div>
		<!--文化成绩-->
		<div class="people">
		  <div class="title_box zhaosheng-title-bg">
		    <h2 class="title zhaosheng-title">文化成绩 </h2>
		    <a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'chengji-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" /> </a> </div>
		  <!--box-->
		  <div id="chengji-box" class="tongji-content">
		    <div id="stu_info" class="before-title-info"><!-- <em class="before-yellow-text" style="font-size:28px;">张雯雯 &nbsp; &nbsp;    女     &nbsp; &nbsp; 20120800020</em><a class="alink-default" href="#"> 2012-2013 第一学期 >></a> --></div>
		    <div id="stu_course" class="sushe-box">
		    </div>
		   <p id="min_max_score" class="list-pleft-circle"> <!-- 总成绩：512 &nbsp;&nbsp;  平均分：85.6 &nbsp;&nbsp;    单科成绩最高：语文 90 &nbsp;&nbsp;  最低：数学 80</p> -->
		    <div class="title-bottom-line">单科成绩比较图 </div>
		    <div id="compare_class_major_score" class="tongji-margin-lr" style="width:1500px;height:500px;"><%-- <img src="${ctxStatic }/images/manager/chengji.png"> --%></div>
		    <div class="title-bottom-line">总成绩历史排名
		    </div>
		    <div id="stu_class_major_ranks" class="tongji-margin-lr" style="width:1500px;height:500px;"><%-- <img src="${ctxStatic }/images/manager/paiming.png"> --%></div>
		  </div>
		</div>
		<!--教学课程及课表-->
		<div class="people">
		  <div class="title_box zhaosheng-title-bg">
		    <h2 class="title zhaosheng-title">教学课程及课表</h2>
		    <a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'kebiao-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" /> </a> </div>
		  <!--box-->
		  <div id="kebiao-box" class="tongji-content">
		    <div class="title-bottom-line">
		   	  <span></span>
		      <select style="margin-left:50px; width:205px;" onchange="searchCourse('${stuMap.stu_id }')">
		      </select>
		    </div>
		    <table class="tongji-table kebiao-table" width="100%" cellpadding="0" cellspacing="0">
		    </table>
		  </div>
		</div>
		
		<!--消费-->
		<div class="people">
		  <div class="title_box zhaosheng-title-bg">
		    <h2 class="title zhaosheng-title">消费 </h2>
		    <a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'xiaofei-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" /> </a> </div>
		  <!--box-->
		  <div id="xiaofei-box" class="tongji-content">
		    <div class="tongji-margin-lr" id="cardYear" style="width:500px;height:300px"></div>
		  	</div>
		</div>
		
		<!--图书借阅-->
		<div class="people">
			<div class="title_box zhaosheng-title-bg">
				<h2 class="title zhaosheng-title">
					图书借阅<span class="title-infomation" style="display: none;"> 已还 13 本，仍在借书 5 本 </span>
				</h2>
				<a class="open  zhaosheng-title" href="###" onClick="openShutManager(this,'tushu-box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" />
				</a>
			</div>
			<!--box-->
			<div id="tushu-box" class="tongji-content">
				<table width="100%" cellspacing="0" cellpadding="0" class="tongji-table">
					<tbody>
						<tr>
							<th width="2%"></th>
							<th width="18%">书名</th>
							<th width="15%">借书日期</th>
							<th width="15%">预计还书日期</th>
							<th width="15%">还书日期</th>
							<th width="15%">拖延天数</th>
							<th width="15%">欠费</th>
						</tr>
						<tr>
							<th colspan="7" class="tongji-th-liubai"></th>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	 </div>
		</div>
		<script type="text/javascript">
			function loadScore(stuId){
				var data = [stuId, '02', '2013-2014'];
				$.callService('stuAllInfoService','stusCultureScore',data,function(d){
					cultureScore(d.stuIfo, d.cultureScoreList, d.cultureScoreMap);
				});
			};
			
			// 学生单科成绩在班级和专业中的比较
			function compareMajorClass(stuId) {
				var data = [stuId, '02', '2013-2014'];
				$.callService('stuAllInfoService','oneStuCompareClassAndMajor',data,function(d){
					stuCompareMajorClass(d.stuScores, d.majorList, d.classList);
				});
			}
			
			// 学生在班级和专业中的排名
			function classMajorRanks(stuId) {
				var data = [stuId, '02', '2013-2014'];
				$.callService('stuAllInfoService','stuClassMajorRank',data,function(d){
					stuRanks(d.stuScores, d.classresultList, d.majorresultList);
				});
			}
		</script>
		
		
		<script type="text/javascript">
			$(function() {
				var stuId = '${stuMap.stu_id }';
				loadAllMoney(stuId);
				loadCard(stuId,'2012-2013学年','第二学期');
				leaveInfo(stuId);
				borrowBook(stuId);
				loadScore(stuId);
				compareMajorClass(stuId);
				classMajorRanks(stuId);
				coureArrangementByStu(stuId, ""); // 周次为""时，显示默认的时间的周次
			});
			
		</script>
		</body>
		</html>
		
