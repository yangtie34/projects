<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ base + "/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学霸</title>
<base href="<%=basePath%>" />
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/smart/controller.js"></script>
<script type="text/javascript" src="product/smart/service.js"></script>
</head>
<body ng-controller="controller">
	<div class="ss-mark-main">
		<div class="header">
		<header class="header-tit"> <b>学霸</b> </header>
		<div ng-show="!data.advance.show" ng-click="advanceShow()" title="高级查询" class="bak-orange" align="center"><a href="" class="sets"></a></div>
        <div ng-show="data.advance.show" class="header-con last-performance" style="margin-top:18px;">
	        <div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="false"></div>
	    </div>
	    <div ng-show="data.advance.show" ng-click="advanceShow()" title="收起" class="bak-orange bak-orange-up" align="center"><a href="" class="sets up"><i class="fa fa-angle-up"></i></a></div>
	</div>
			<div class="ul-no-center xue-ba-li pad-t-15">
				<ul class="list-unstyled top-center">
					<li ng-class="{'has-green':nj.id == data.grade}"
						ng-repeat="nj in data.gradeSelect"><a href=""
						class="bg-green grade"
						ng-click="nj.id == data.grade||selectGrade(nj.id,nj.bs)">{{nj.mc}}</a></li>
					<li>
						<div class="ss-mark-drop xueqi-drop-normal">
							<div class="btn-group" cg-select data="data.yearSelect"
								on-change="selectYear($value,$data)" ng-model="data.yselName"
								value-field="id" display-field="mc"></div>
						</div>
					</li>
					<li>
						<div class="ss-mark-drop ben-zhuan pull-left">
							<div class="btn-group" cg-select data="data.eduSelect"
								on-change="selectEdu($value,$data)" ng-model="data.eduName"
								value-field="id" display-field="mc"></div>
						</div>
					</li>
					<div class="clearfix"></div>
				</ul>
			</div>

		<section class="honor-roll">
		<div class="note">
			<span class="hat"></span> <small>学霸：平均学分绩点达到 {{data.xbgpa}}及以上的学生</small>
		</div>
		<div class="honor-list">
			<div class="hornor-row" ng-repeat="x in data.topGpa">
				<ol class="list-unstyled">
					<li ng-repeat=" eb in x.list" ng-class="{'last-child':$index==4}">
						<div class="member">
							<img ng-if="eb.sex=='男'" src="static/resource/images/boy.png" width="60" height="60"
								class="img-circle center-block" alt=""><img src="static/resource/images/girl.png" width="60" height="60"
								class="img-circle center-block" alt="" ng-if="eb.sex=='女'"> <span
								class="point pull-right">{{eb.gpa}}</span>
							<div class="message">
								<small>{{eb.grade}}级 · <span><a href="" ng-click="stuClick(eb.code,eb.name)"
										class="text-blk">{{eb.name}}</a></span><span
									ng-class="{'honor-sex':eb.sex=='女','honor-sex honor-sex-man':eb.sex=='男'}"></span></small>
								<small>{{eb.yx}}/{{eb.zy}}</small>
							</div>
							<span
								ng-class="{'badge-honor first':eb.r==1,'badge-honor second':eb.r==2,'badge-honor third':eb.r==3,'badge-honor':eb.r>3}">{{eb.r}}</span>
						</div>
					</li>
					<div class="clearfix"></div>
				</ol>
			</div>
		</div>
		<div ng-if="vm1.page.total==0" class="text-center">学霸列表暂无数据</div>
		<div class="honor-more" ng-if="vm1.page.total>10&&vm1.page.index<2">
			<small><a href="" ng-click="vm1.page.total<10||loadMore()">更多<i
					class="fa fa-angle-double-down"></i></a></small>
		</div>
		<div class="honor-page" ng-if="vm1.page.index>1">
			<p class="page">
					<a href=""
						class="prevv"
						ng-click="PageDown1()"></a> <a href=""
						ng-click="vm1.page.index*vm1.page.size>=vm1.page.total||loadMore()"
						ng-class="{'nextt disable':vm1.page.index*vm1.page.size>=vm1.page.total,'nextt':vm1.page.index*vm1.page.size<vm1.page.total}"></a>
				</p>
        </div>
		</section>

		 <section class="bioclock">
		<p class="block-tit">学霸生物钟</p>
		<div class="">
			<ul class="list-unstyled season-section text-center">
				<li ng-class="{'active':!data.season}"><a href="" ng-click="!data.season||changeSeason()">
						<div class="season-img-circle spr">
							<span class="season-img spring"></span>
						</div>
						<div class="season-name">夏季</div>
				</a></li>
				<li ng-class="{'active':data.season}"><a href="" ng-click="data.season||changeSeason()" >
						<div class="season-img-circle sum">
							<span class="season-img summer"></span>
						</div>
						<div class="season-name fall">冬季</div>
				</a></li>
			</ul>
		</div>
		<div class="chart">
			<div class="get-up"></div>
			<!-- <div class="out" ng-if="data.seasonList[0].outdormrke<data.seasonList[0].breakfast&&data.seasonList[0].outdormrke!=''">
				<small class="behavior">宿舍出门</small> <i class="fa fa-map-marker"></i>
				<small>{{data.seasonList[0].outdormrke}}</small>
			</div> -->
			 <div ng-class="{'breakfast':x.change,'out library':!x.change}" ng-repeat=" x in data.seasonList" style ="left:{{x.lv}}%">
				<small ng-if="x.change" class="behavior">{{x.name}}</small><small ng-if="!x.change">{{x.value_}}</small> <i ng-class="{'fa fa-map-marker big':x.change,'fa fa-map-marker biger':!x.change}"></i>
				 <small ng-if="!x.change" class="behavior">{{x.name}}</small><small ng-if="x.change">{{x.value_}}</small>
			</div> 
			<div class="sleep"></div>
		</div>
		</section>
		<div class="bioclock">
			<p class="block-tit">学霸地区分布</p>
			<div class="honor-map">
				<div class="row">
					<div class="col-md-7">
						<a class="go-back text-blue" style="top:3px;right:12%" href="" ng-click="pageUp()"
							ng-if="data.level != null && data.level!='1'"><img
							src="static/resource/css/image/fanhui2.png">&nbsp;返回上一级</a>
						<div  echart config="data.fromCfg"
							ec-click="mapClick(param)"></div>
						<a href="" class="text-blue" ng-click="stuListClick('xsmd',data.mapt+'学霸名单')">查看{{data.mapt}}学霸名单</a>
					</div>
					<div class="col-md-5" part-modal show-modal="data.grid.mask">
						<div echart config="data.radarCfg"></div>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
		</div>

		<section class="ss-mark-tab-box">
		<div class="ss-mark-tab">
			<div class="ss-mark-tab-tit">
				<a href="" ng-class="{'active':y.id==data.selected}"
					ng-repeat="y in data.tabs"
					ng-click="y.id==data.selected||selectTab(y.id,y.mc)">{{y.mc}}<span></span>
					<div class="triangle"></div></a>
			</div>
			<div class="ss-mark-tab-con td-average">
					<div part-modal show-modal="data.grid.mask">
				<table class="table table-bordered">
					<thead>
						<tr>
							<td><span class="two-words">{{data.tselname}}</span></td>
							<td ng-repeat="jb in tabcolumn">
								<div
									ng-class="{'four-words tab-more-wds':$index==(tabcolumn.length-1)}">
									<p>
										<a href=""
											ng-class="{'on':data.columnid==jb.id&&data.asc==true}"
											ng-click="data.columnid==jb.id&&data.asc==true||tabsort(jb.id,true)"><i
											class="fa fa-sort-up"></i></a>
									</p>
									<span>{{jb.mc}}</span>
									<p>
										<a href=""
											ng-class="{'on':data.columnid==jb.id&&data.asc==false}"
											ng-click="data.columnid==jb.id&&data.asc==false||tabsort(jb.id,false)"><i
											class="fa fa-sort-down"></i></a>
									</p>
								</div>
							</td>
						</tr>
					</thead>

					<tbody>
						<tr ng-repeat="tab in data.tabledata">
							<td><span class="badge-order">{{tab.r}}</span>
								<p>{{tab.name}}</p></td>
							<td ng-class="{'td-active':data.columnid=='avg'}">{{tab.avg}}</td>
							<td ng-class="{'td-active':data.columnid=='value'}">{{tab.value}}</td>
							<td ng-class="{'td-active':data.columnid=='lv'}">{{tab.lv}}%</td>
							<td ng-class="{'td-active':data.columnid=='avg1'}">{{tab.avg1}}</td>
						</tr>
					</tbody>
				</table>
				</div>
				<div ng-if="vm2.page.total==0" class="no-date">暂无数据...</div>	
				<p class="page pull-right" ng-if="vm2.page.total>0">
					<a href=""
						ng-class="{'prevv disable':vm2.page.index==1,'prevv':vm2.page.index>1}"
						ng-click="vm2.page.index==1||PageDown2()"></a> <a href=""
						ng-click="vm2.page.index*vm2.page.size>=vm2.page.total||PageUp2()"
						ng-class="{'nextt disable':vm2.page.index*vm2.page.size>=vm2.page.total,'nextt':vm2.page.index*vm2.page.size<vm2.page.total}"></a>
				</p>
				<div class="clearfix"></div>
			</div>
		</div>

		<div class="honor-bili">
			<p class="block-tit">历年学霸成绩与占比</p>
			<div echart config="data.countLineCfg"></div>
		</div>
		</section>

	</div>
	    <%-- 分布 详情 --%>
    <div modal-form config="data.stu_detail.formConfig"></div>
	 <div cs-window show="data.stuQj" autocenter="true" style="padding: 0;width:50%;overflow:auto;max-height:calc(100% - 20px);top:40px;" >
		<div class="" style="margin:0px" part-modal show-modal="data.grid1.mask">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.stuMc}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.stuQj=false">
		        	<%--<img src="static/resource/css/image/popup-form-close.png" alt="">--%>
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="clearfix">
			   <div class="center-block" style ="width:500px;height:400px;margin-left:auto;margin-right:auto;" echart config ="data.radarMeCfg"></div>
			</div>
			<div style="padding:0 100px;text-align:justify;">
			<p ng-if="data.dorm.ly !=null"><b>宿舍:</b>{{data.dorm.ly}}&nbsp;{{data.dorm.ss}}宿舍，{{data.dorm.cw}}号床位；</p>
			
			<p><b>一卡通消费（餐厅，超市）:</b>本学期{{data.stuMc}}总消费<b class="text-green">{{data.cost.me}}</b>元，全校学生平均消费<b class="text-green">{{data.cost.all}}</b>元，
			学霸平均消费<b class="text-green">{{data.cost.xb}}</b>元，在全校排名<b class="text-green">{{data.cost.rank}}</b>，
			超过了<b class="text-green">{{data.cost.highall}}%</b>的在校生，超过了<b class="text-green">{{data.cost.highxb}}%</b>的学霸。</p>
			
			<p><b>图书借阅:</b>本学期{{data.stuMc}}总借书<b class="text-green">{{data.borrow.me}}</b>本，全校学生平均借书<b class="text-green">{{data.borrow.all}}</b>本，
			学霸平均借书<b class="text-green">{{data.borrow.xb}}</b>本，在全校排名<b class="text-green">{{data.borrow.rank}}</b>，
			超过了<b class="text-green">{{data.borrow.highall}}%</b>的在校生，超过了<b class="text-green">{{data.borrow.highxb}}%</b>的学霸。</p>
			
			<p><b>成绩:</b>本学期{{data.stuMc}}平均绩点<b class="text-green">{{data.score.me}}</b>，全校学生平均绩点<b class="text-green">{{data.score.all}}</b>，
			学霸平均绩点<b class="text-green">{{data.score.xb}}</b>，在全校排名<b class="text-green">{{data.score.rank}}</b>，
			超过了<b class="text-green">{{data.score.highall}}%</b>的在校生，超过了<b class="text-green">{{data.score.highxb}}%</b>的学霸。</p></div>
			
			<div style="padding:0 100px;"><p class="text-center" style="font-size:16px;margin-top:25px;"><b>{{data.stuMc}}{{data.yselName}}成绩明细</b></p>
				<table class="table table-bordered" style="margin-bottom:45px;">
					<thead>
						<tr>
							<td>序号</td>
							<td>课程</td>
							<td>成绩</td>
							<td>学分</td>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="mm in data.scoreMx">
							<td>{{$index+1}}</td>
							<td>{{mm.mc}}</td>
							<td>{{mm.cj}}</td>
							<td>{{mm.xf}}</td>
						</tr>
					</tbody>
				</table></div>
		   </div>		
	</div> 
</body>
</html>