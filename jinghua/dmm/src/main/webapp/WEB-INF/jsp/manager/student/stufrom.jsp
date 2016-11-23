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
				<div class="boxx">
	                <div class="title_left">
	                    <h3>学生生源地统计</h3>
	                    <p>学生来源地及毕业学校统计。展示来我校上学的学生的来源地分布情况统计。切换时间可以统计该时段内的来源地分布情况。分性别、毕业学历等不同角度展示学生的来源地分布。 </p>
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
	            	<div class="year" id="date_div">
					</div>
	            </div>
			</div>
			<!--右侧头部结束-->

			<!--专业及人数开始-->
			<div class="people" id="people"></div>
			<div class="excel_box">
        	<table class="table"  width="90%" border="0" cellpadding="0" cellspacing="0">
              <thead>
                  <tr border="0">
                      <th>来校人数</th>
                      <th>未维护来源地</th>
                      <th>农村户口人数</th>
                      <th>县镇户口人数</th>
                      <th>城市户口人数</th>
                      <th>男生</th>
                      <th>女生</th>
                  </tr>
                  <tr border="0" >
                      <th class="bai"></th>
                      <th class="bai">&nbsp;</th>
                      <th class="bai">&nbsp;</th>
                      <th class="bai">&nbsp;</th>
                      <th class="bai">&nbsp;</th>
                      <th class="bai">&nbsp;</th>
                      <th class="bai">&nbsp;</th>
                  </tr>
              </thead>
              <tbody>
                  <tr>
                      <td id="lxrs">1608</td>
                      <td id="lydrs">1363</td>
                      <td id="ncrs">1471</td>
                      <td id="xzrs">998</td>
                      <td id="csrs">1305</td>
                      <td id="nrs">728</td>
                      <td id="nvrs">1108</td>
                  </tr>
                  <tr >
                      <th class="biao3" id="lxzb">占比100%</th>
                      <th class="biao3" id="lydzb">占比100%</th>
                      <th class="biao3" id="nczb">占比100%</th>
                      <th class="biao3" id="xzzb">占比100%</th>
                      <th class="biao3" id="cszb">占比100%</th>
                      <th class="biao3" id="nzb">占比100%</th>
                      <th class="biao3" id="nvzb">占比100%</th>
                  </tr>
              </tbody>
          </table>
        </div>
			
			
			<div class="zhibiao_box">
				<div id="timeDiv" style="margin-top: 2%">
				</div>
					<div class="zhi_title">
	              		<span>选择指标</span>
	                    <div class="choice">
	                      <select name="select" id="select">
	                        <option>来校人数</option>
	                        <option>助学金</option>
	                        <option>获得</option>
	                        <option>人数</option>
	                      </select>
	                    </div>
	              </div>
	              <div class="zhibiao_tu">
	                <div id="mapDiv" class="zhi_map" style="width: 80%;height: 400px;margin-top: 2%">
	                	
	                </div>
	                <div class="zhi_tj">
	                	<img src="images/zzjd_02.png" alt="" />
	                </div>
	              </div>
	              
	              
	              
	              
				
			</div>
			<ul id="tab">
                <li class="current">地区</li>
                <li>毕业学校</li>
                
            </ul>
            <div id="content">
                <ul style="display:block;">
                   <div class="diqu_bg">
                   		<table class="table"  width="100%" border="0" cellpadding="0" cellspacing="0">
              				<thead>
                              <tr border="0">
                                  <th class="number_b">&nbsp;</th>
                                  <th>地区</th>
                                  <th>来校人数</th>
                                  <th>农村户口人数</th>
                                  <th>县镇户口人数</th>
                                  <th>城市户口人数</th>
                                  <th>男生人数</th>
                                  <th>女生人数 </th>
                                  
                              </tr>
                           	</thead>
                          	<tbody bodyFlag="areaStat">
                                                         
                          </tbody>
                      </table>
                      <div pageFlag="areaStat"></div>
                   </div>
                   
                </ul>
                <ul>
                	<div class="diqu_bg">
                		<table class="table"  width="100%" border="0" cellpadding="0" cellspacing="0">
                			<thead>
                				<tr border="0">
                                  <th class="number_b"> &nbsp;</th>
                                  <th>毕业学校</th>
                                  <th>人数</th>
                              </tr>
                			</thead>
                			<tbody bodyFlag="byxxFlag">
                                                         
                          </tbody>
                		</table>
                		<div pageFlag="byxxFlag"></div>
                	</div>
                   
                </ul>
                
            </div>
            <script>
				$(function(){
					window.onload = function()
					{
						var $li = $('#tab li');
						var $ul = $('#content ul');
									
						$li.mouseover(function(){
							var $this = $(this);
							var $t = $this.index();
							$li.removeClass();
							$this.addClass('current');
							$ul.css('display','none');
							$ul.eq($t).css('display','block');
						})
					}
				});
			</script>
			
		</div>
			
		</div>
		
        	
	
		<script type="text/javascript" src="${ctxStatic }/product/common/mapAndPie.js"></script>
		<script>
		//组织机构滚动回调函数
		var deptId = '${dept_id}';
		var stufrom_myMapChart;
		var stufrom_startDate='2014-01-01',stufrom_endDate='2015-10-10'; //第一次加载时间
		
		var stufrom_leve=0;
		var stufrom_mt="china";
		var stufrom_data=[stufrom_startDate,stufrom_endDate,deptId,false,stufrom_leve,stufrom_mt,'gsn'];
		
		function goMajor(this_deptId){
			deptId=this_deptId;
			loadTitle(deptId,true);
			
			stufrom_data=[stufrom_startDate,stufrom_endDate,deptId,false,stufrom_leve,stufrom_mt,'gsn'];
			reloadMapPie(stufrom_myMapChart,stufrom_data)
			var data_all=[stufrom_startDate,stufrom_endDate,deptId,false];
			ryqk(data_all);
			
			loadAreaData({startIndex:1,numPerPage:10});
			loadLyxxData({startIndex:1,numPerPage:10});
		}
		
		function selected(this_deptId){
			deptId=this_deptId;
			stufrom_data=[stufrom_startDate,stufrom_endDate,deptId,true,stufrom_leve,stufrom_mt,'gsn'];
			reloadMapPie(stufrom_myMapChart,stufrom_data)
			var data_all=[stufrom_startDate,stufrom_endDate,deptId,true];
			ryqk(data_all);
			
			loadAreaData({startIndex:1,numPerPage:10});
			loadLyxxData({startIndex:1,numPerPage:10});
		}
		
		
		$(function() {
			stufrom_init();
			
			loadAreaData({startIndex:1,numPerPage:10});
			loadLyxxData({startIndex:1,numPerPage:10});
			
		});
		
		function stufrom_init(){
			loadTitle(deptId,false);
			stufrom_myMapChart=$.addMapPie("mapDiv",[],function(od){
				stufrom_mt=od.mt;
				stufrom_leve=stufrom_mt=="china"?0:1;
				stufrom_data=[stufrom_startDate,stufrom_endDate,deptId,false,stufrom_leve,stufrom_mt,'gsn'];
				reloadMapPie(stufrom_myMapChart,stufrom_data)
			});
			
			reloadMapPie(stufrom_myMapChart,stufrom_data);
			
			var date_op={pid:"date_div",startDate:stufrom_startDate,endDate:stufrom_endDate};
			$.addDate(date_op,function(date){
				stufrom_startDate=date.start;
				stufrom_endDate=date.end;
				var this_data=[stufrom_startDate,stufrom_endDate,deptId,false,stufrom_leve,stufrom_mt,'gsn'];
				reloadMapPie(stufrom_myMapChart,this_data);
				
				var data_all=[stufrom_startDate,stufrom_endDate,deptId,false];
				ryqk(data_all);
				
				loadAreaData({startIndex:1,numPerPage:10});
				loadLyxxData({startIndex:1,numPerPage:10});
				
			});
			
			var data_all=[stufrom_startDate,stufrom_endDate,deptId,false];
			ryqk(data_all);
			
		}
		
		function ryqk(data_all){
			$.callService("iStusSourceStatisticsService","stusStatisticalInterval",data_all,function (d){
				$("#lxrs").html(d.allStusCounts);
				$("#lydrs").html(d.noMiantainedNums);
				$("#ncrs").html(d.ruralHouseholdCounts);
				$("#xzrs").html(d.countyTownHouseholdCounts);
				$("#csrs").html(d.urbanHouseholdCounts);
				$("#nrs").html(d.schoolBoyCounts);
				$("#nvrs").html(d.schoolGirlCounts);
				
				$("#lxzb").html("占比：100%");
				$("#lydzb").html("占比："+d.noMiantainedNumsRate);
				$("#nczb").html("占比："+d.ruralHouseholdCountsRate);
				$("#xzzb").html("占比："+d.countyTownHouseholdCountsRate);
				$("#cszb").html("占比："+d.urbanHouseholdCountsRate);
				$("#nzb").html("占比："+d.schoolBoyCountsRate);
				$("#nvzb").html("占比："+d.schoolGirlCountsRate);
				
			});
		}
		
		function reloadMapPie(myMapChart,data){
			$.callService("iStusSourceStatisticsService","stusNumsMap",data,function (d){
				 $.reloadMapPie(myMapChart,d);
			});
		}
		
		function loadAreaData(pageParam){
			var area_data=[stufrom_startDate,stufrom_endDate,deptId,false,stufrom_leve,stufrom_mt,'gsn'];
			area_data.push(pageParam.currentPage || 1);
			area_data.push(pageParam.numPerPage || 1);
			$.callService("iStusSourceStatisticsService","stusNumsDistribution",area_data,function (d){
				areaStatTable(d);
			});
			
		}
		
		function loadLyxxData(pageParam){
			var lyxx_data=[stufrom_startDate,stufrom_endDate,deptId,false,stufrom_leve,stufrom_mt,'gsn'];
			lyxx_data.push(pageParam.currentPage || 1);
			lyxx_data.push(pageParam.numPerPage || 1);
			$.callService("iStusSourceStatisticsService","stusSchlloOfGraduation",lyxx_data,function (d){
				lyxxStatTable(d);
			});
		}
		
		function areaStatTable(d){
			var domTable=$("tbody[bodyFlag='areaStat']")[0];
			if(domTable){
				$(domTable).html('');
				var i=1;
				$.each(d.resultList,function(i,o){
					var str="<tr><td>"+(o['NUM'] || i)+"</td><td class='huihui'>"+(o['CITIESNAME'] || 0)+"</td>"
					+"<td class='huihui'>"+(o['ALLCOUNTS'] || 0)+"</td>"//来校人数
					   +"<td class='huihui'>"+(o['COUNTRYSIDES'] || 0)+"</td>"//农村户口人数
					   +"<td class='huihui'>"+(o['TOWNS'] || 0)+"</td>"//县镇户口人数
					   +"<td class='huihui'>"+(o['CITIES'] || 0)+"</td>"//城市户口人数
					   +"<td class='huihui'>"+(o['SCHOOLBOY'] || 0)+"</td>"//男生人数
					   +"<td class='huihui'>"+(o['SCHOOLGIRL'] || 0)+"</td>"//女生人数
					   //+"<td class='huihui'>"+(o['CITIESNAME'] || 0)+"</td>"//初中起点
					   //+"<td class='huihui'>"+(o['CITIESNAME'] || 0)+"</td>"//高中起点
					   +"</tr>";
					
					$(domTable).append(str);
				});
				var page=new dmm.Page(d,loadAreaData,window);
				page.render($("div[pageFlag='areaStat']")[0]);
			}
			
		}
		function lyxxStatTable(d){
			var domTable=$("tbody[bodyFlag='byxxFlag']")[0];
			if(domTable){
				$(domTable).html('');
				var i=1;
				$.each(d.resultList,function(i,o){
					var str="<tr><td>"+(o['NUM'] || i)+"</td><td class='huihui'>"+(o['SCHOLLNAME'] || '未维护')+"</td>"
					+"<td class='huihui'>"+(o['COUNTS'] || 0)+"</td>"//来校人数
					 
					   //+"<td class='huihui'>"+(o['CITIESNAME'] || 0)+"</td>"//初中起点
					   //+"<td class='huihui'>"+(o['CITIESNAME'] || 0)+"</td>"//高中起点
					   +"</tr>";
					
					$(domTable).append(str);
				});
				var page=new dmm.Page(d,loadLyxxData,window);
				page.render($("div[pageFlag='byxxFlag']")[0]);
			}
		}
	</script>
</body>
</html>
