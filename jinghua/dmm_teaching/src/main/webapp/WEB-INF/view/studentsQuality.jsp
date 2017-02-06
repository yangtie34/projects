<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>生源质量</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript"
	src="product/studentsQuality/controller.js"></script>
<script type="text/javascript" src="product/studentsQuality/service.js"></script>

</head>
<body ng-controller="controller">
  <div class="ss-mark-main">
    <div class="header">
	  <header class="header-tit"> <b>生源质量</b> </header>
	  <div class="ul-no-center">
		<ul class="list-unstyled top-center clearfix no-m-btm">
		 <li>
		  <div class="ss-mark-drop year-drop">
			<div class="xuegong-zhuti-dropdown-box">
			  <div class="btn-group" cg-select data="cont.yearList" on-change="changeXn($value,$data)" ng-model="cont.yearName"
					 value-field="id" display-field="mc"></div>
			</div>
		  </div>
		 </li>
		 <li class="{{changeCssOfYear[0]}}"><a href="" class="bg-green years" ng-click="changeYear('0')" ng-model="cont.yearName">今年</a></li>
		 <li class="{{changeCssOfYear[1]}}"><a href="" class="bg-green years" ng-click="changeYear('1')" ng-model="cont.lasyear">去年</a></li>
		</ul>
	  </div>
	 </div>
	 <div class="performance-change no-top pad-b-15 postion-r">
		<div class="jx-has-more-r">
		  <div class="more-r jx-more-org">
			 <a href="" style="top: 16px;">更多 >></a>
			 <div class="jx-more-pop-out" style="right: 0px; top: 10px;">
			   <div class="jx-more-pop">
				 <div class="checkbox checkbox-inline bsdt-checkbox" ng-repeat="it in allSub">
				   <input type="checkbox" ng-click="updateChose($event,it.subid)" id="{{it.subid}}" name="{{it.submc}}" ng-checked="isChose(it.subid)" />
				      <label for="{{it.subid}}">{{it.submc}}</label>
				 </div>
			   </div>
			</div>
		 </div>
		 <div class="jx-biye-row-out">
		   <ul class="jx-biye-row list-unstyled clearfix">
			 <li>
               <div>
				 <p>本科生/人</p>
				 <div class="jx-rectangle">
					<span>{{cont.nowYear.sl}}</span>
					<span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[className1]">{{cont.lasYear.rsc}}</span>
				 </div>
			   </div>
			 </li>
			 <li class="jx-biye-zu">
			   <div>
				 <p>平均分/分</p>
				 <div class="jx-rectangle">
				   <span>{{cont.nowYear.pjf}}</span>
				   <span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[className2]">{{cont.lasYear.pjfc}}</span>
				 </div>
		       </div>
			 </li>
			 <li class="jx-biye-zu">
			   <div>
				<p>最高分/分</p>
				 <div class="jx-rectangle">
				   <span>{{cont.nowYear.zgf}}</span>
				   <span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[className3]">{{cont.lasYear.zgfc}}</span>
				 </div>
			   </div>
			 </li>
			 <li class="jx-biye-zu">
				<div>
				  <p>最低分/分</p>
				  <div class="jx-rectangle">
					<span>{{cont.nowYear.zdf}}</span>
					<span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[className4]">{{cont.lasYear.zdfc}}</span>
				  </div>
				</div>
			</li>
		  </ul>
		</div>
		<div class="jx-biye-row-out" ng-repeat="item in detList">
		   <ul ng-class="{true:'jx-biye-row list-unstyled clearfix jx-biye-row-green',false:'jx-biye-row list-unstyled clearfix jx-biye-row-blue'}[item.mask]">
			 <li>
				<div>
				  <p>{{item.mc}}/人</p>
				  <div class="jx-rectangle">
					 <span>{{item.sl}}</span>
					 <span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[item.slcFlag]">{{item.slc}}</span>
				  </div>
				</div>
			 </li>
			 <li class="jx-biye-zu">
				<div>
				  <p>平均分/分</p>
				  <div class="jx-rectangle">
					<span>{{item.pjs}}</span>
					<span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[item.pjscFlag]">{{item.pjsc}}</span>
				  </div>
				</div>
			 </li>
			 <li class="jx-biye-zu">
				<div>
				  <p>最高分/分</p>
				  <div class="jx-rectangle">
				     <span>{{item.zds}}</span>
					 <span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[item.zdscFlag]">{{item.zdsc}}</span>
				  </div>
				</div>
			 </li>
			 <li class="jx-biye-zu">
			   <div>
				 <p>最低分/分</p>
				 <div class="jx-rectangle">
				    <span>{{item.zxs}}</span>
					<span ng-class="{true:'text-greener jx-rectangle-add',false:'text-red jx-rectangle-add'}[item.zxscFlag]">{{item.zxsc}}</span>
				 </div>
			   </div>
			 </li>
		   </ul>
		 </div>
	  </div>
	</div>
	<div class="performance-change no-top pad-b-15">
		<div>
		<span class="jx-note-green" ng-model="cont.yearname">{{cont.yearname}}超出分数线
			<input type="text" class="jx-note-input-sm" ng-model="cont.point" />分及以上的专业有{{cont.sl.jnsl}} 个，相比上一年{{cont.sl.slc}} 个。
		</span> 
		<span class="drop-green-box pull-right" style="position:relative;right:21px;top:13px;">
			<a href=""  class="wen-img"></a>
			<div class="drop-down-out-box" style="right:-7px;">
				<div class="drop-down-box sy" style="right:-10px;">
					<div ng-hide="cont.point==0" class="drop-box-texts" style="margin-top:1px;margin-bottom:2px;">
						<span class="text-green">超出分数线{{cont.point}}分专业</span>
								：即该专业有一个及以上省份的录取分数超出分数线至少{{cont.point}}分
					</div>
					<div ng-show="cont.point==0" class="drop-box-texts" style="margin-top:1px;margin-bottom:2px;">
						<span class="text-green">超出分数线{{cont.point}}分专业</span>
								：即该专业有一个及以上省份的录取分数等于分数线。
					</div>
				</div>
			</div>
		</span>
		</div>
		<div class="ss-mark-tab-con no-top">
			<table class="table table-bordered class-tab">
				<thead>
					<tr>
						<td><span class="two-words">专业</span></td>
						<td>
							<div>
								<p>
									<a href="" ng-click="scoreClick('rsup','0')" class="{{scorecssClick[0]}}"><i class="fa fa-sort-up"></i></a>
								</p>
								<span>人数</span>
								<p>
									<a href="" ng-click="scoreClick('rsdown','1')" class="{{scorecssClick[1]}}"><i class="fa fa-sort-down"></i></a>
								</p>
							</div>
						</td>
						<td>
							<div>
								<p>
									<a href="" ng-click="scoreClick('avgup','2')" class="{{scorecssClick[2]}}"><i class="fa fa-sort-up"></i></a>
								</p>
								<span>平均分</span>
								<p>
									<a href="" ng-click="scoreClick('avgdown','3')" class="{{scorecssClick[3]}}"><i class="fa fa-sort-down"></i></a>
								</p>
							</div>
						</td>
						<td>
							<div>
								<p>
									<a href="" ng-click="scoreClick('maxup','4')" class="{{scorecssClick[4]}}"><i class="fa fa-sort-up"></i></a>
								</p>
								<span>最高分</span>
								<p>
									<a href="" ng-click="scoreClick('maxdown','5')" class="{{scorecssClick[5]}}"><i class="fa fa-sort-down"></i></a>
								</p>
							</div>
						</td>
						<td>
							<div>
								<p>
									<a href="" ng-click="scoreClick('minup','6')" class="{{scorecssClick[6]}}"><i class="fa fa-sort-up"></i></a>
								</p>
								<span>最低分</span>
								<p>
									<a href="" ng-click="scoreClick('mindown','7')" class="{{scorecssClick[7]}}"><i class="fa fa-sort-down"></i></a>
								</p>
							</div>
						</td>
						<td>
							<div>
								<p>
									<a href="" ng-click="scoreClick('moreup','8')" class="{{scorecssClick[8]}}"><i class="fa fa-sort-up"></i></a>
								</p>
								<span>高出分数线最多（分）</span>
								<p>
									<a href="" ng-click="scoreClick('moredown','9')" class="{{scorecssClick[9]}}"><i class="fa fa-sort-down"></i></a>
								</p>
							</div>
						</td>
						<td>
							<div>
								<!--<p><a href="#"><i class="fa fa-sort-up"></i></a></p>-->
								<span>相比上一年平均分</span>
								<!--<p><a href="#"><i class="fa fa-sort-down"></i></a></p>-->
							</div>
						</td>
						<td>
							<div class="four-words">
								<p>
									<a href="" ng-click="scoreClick('lineup','10')" class="{{scorecssClick[10]}}"><i class="fa fa-sort-up"></i></a>
								</p>
								<span>各省分数线</span>
								<p>
									<a href="" ng-click="scoreClick('linedown','11')" class="{{scorecssClick[11]}}"><i class="fa fa-sort-down"></i></a>
								</p>
							</div>
						</td>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in cont.tabList">
						<td><span class="badge-order">{{item.rn}}</span><p>{{item.name_}}</p></td>
						<td>{{item.rs}}</td>
						<td>{{item.pjf}}</td>
						<td>{{item.zgf}}</td>
						<td>{{item.zdf}}</td>
						<td>{{item.fc}}</td>
						<td ng-class="{true:'text-greener',false:'text-red'}[item.mask]">{{item.pjfc}}</td>
						<td><a href="" class="text-blue" ng-click="getScore(item.mid)">详情</a></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div ng-if="cont.sl.jnsl==0" class="no-date">暂无数据...</div>
			<div class="jx-fz-12" ng-hide="cont.sl.jnsl==0">
				<div class="clearfix" ng-hide="cont.score_detail.page.sumcount &lt; 10">
					<div pagination total-items="cont.score_detail.page.sumcount" ng-model="cont.score_detail.page.curpage"
						items-per-page="cont.score_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
					<a href="" class="jx-more-org" ng-click="getscoreInfo()">查看全部</a>
				</div>
			</div>
		</div>
		<div class="performance-change no-top pad-b-15">
			<div class="row">
				<div class="col-md-6">
					<div ng-hide="cont.TJSL.pjtjl=='--'">
						<span class="jx-note-green">{{cont.yearname}}平均调剂率{{cont.TJSL.pjtjl}}
							，{{cont.TJSL.zymc}}专业最高，为 {{cont.TJSL.zgtjl}}</span>
					</div>
					<div ng-show="cont.TJSL.pjtjl=='--'">
						<span class="jx-note-green">{{cont.yearname}}调剂情况暂无数据</span>
					</div>
					<div class="ss-mark-tab-con no-top">
						<table class="table table-bordered class-tab">
							<thead>
								<tr>
									<td><span class="two-words">专业</span></td>
									<td>
										<div>
											<p>
												<a href="" ng-click="adjustClick('rsup','0')" class="{{adjustcssClick[0]}}"><i class="fa fa-sort-up"></i></a>
											</p>
											<span>人数</span>
											<p>
												<a href="" ng-click="adjustClick('rsdown','1')" class="{{adjustcssClick[1]}}"><i class="fa fa-sort-down"></i></a>
											</p>
										</div>
									</td>
									<td>
										<div>
											<p>
												<a href="" ng-click="adjustClick('adjup','2')" class="{{adjustcssClick[2]}}"><i class="fa fa-sort-up"></i></a>
											</p>
											<span>调剂率</span>
											<p>
												<a href="" ng-click="adjustClick('adjdown','3')" class="{{adjustcssClick[3]}}"><i class="fa fa-sort-down"></i></a>
											</p>
										</div>
									</td>
									<td>
										<div class="four-words">
											<p>
												<a href="" ng-click="adjustClick('lastup','4')" class="{{adjustcssClick[4]}}"><i class="fa fa-sort-up"></i></a>
											</p>
											<span>相比上一年</span>
											<p>
												<a href="" ng-click="adjustClick('lastdown','5')" class="{{adjustcssClick[5]}}"><i class="fa fa-sort-down"></i></a>
											</p>
										</div>
									</td>
								</tr>
							</thead>

							<tbody>
								<tr ng-repeat="it in cont.adjList">
									<td><span class="badge-order">{{it.rn}}</span>
										<p>{{it.name_}}</p></td>
									<td>{{it.zrs}}</td>
									<td>{{it.tjl}}</td>
									<td ng-class="{true:'text-greener',false:'text-red'}[it.mask]">{{it.tjlc}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="no-date" ng-if="cont.TJSL.pjtjl=='--'">暂无数据...</div>
					<br />
					<div class="jx-fz-12"
						ng-hide="cont.TJSL.pjtjl=='--'|| cont.adj_detail.page.sumcount &lt; 10">
						<a href="" class="jx-more-org" ng-click="getAdjustInfo()">查看全部</a>
					</div>
				</div>
				<div class="col-md-6">
					<div class="clearfix" ng-hide="cont.LQSL.pjlql=='--'">
						<span class="jx-note-green pull-right">{{cont.yearname}}自主招生平均录取率为{{cont.LQSL.pjlql}}
							，{{cont.LQSL.zymc}}专业录取率最低，为 {{cont.LQSL.zdlql}}</span>
					</div>
					<div class="clearfix" ng-show="cont.LQSL.pjlql=='--'">
						<span class="jx-note-green pull-right"><span class="pull-center">{{cont.yearname}}自主招生录取情况暂无数据</span></span>
					</div>
					<div class="ss-mark-tab-con no-top">
						<table class="table table-bordered class-tab">
							<thead>
								<tr>
									<td><span class="two-words">专业</span></td>
									<td>
										<div>
											<p>
												<a href="" ng-click="enrClick('rsup','0')" class="{{enrcssClick[0]}}"><i class="fa fa-sort-up"></i></a>
											</p>
											<span>人数</span>
											<p>
												<a href="" ng-click="enrClick('rsdown','1')" class="{{enrcssClick[1]}}"><i class="fa fa-sort-down"></i></a>
											</p>
										</div>
									</td>
									<td>
										<div>
											<p>
												<a href="" ng-click="enrClick('lqlup','2')" class="{{enrcssClick[2]}}"><i class="fa fa-sort-up"></i></a>
											</p>
											<span>录取率</span>
											<p>
												<a href="" ng-click="enrClick('lqldown','3')" class="{{enrcssClick[3]}}"><i class="fa fa-sort-down"></i></a>
											</p>
										</div>
									</td>
									<td>
										<div class="four-words">
											<p>
												<a href="" ng-click="enrClick('lastup','4')" class="{{enrcssClick[4]}}"><i class="fa fa-sort-up"></i></a>
											</p>
											<span>相比上一年</span>
											<p>
												<a href="" ng-click="enrClick('lastdown','5')" class="{{enrcssClick[5]}}"><i class="fa fa-sort-down"></i></a>
											</p>
										</div>
									</td>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="it in cont.enrList">
									<td><span class="badge-order">{{it.rn}}</span>
										<p>{{it.zymc}}</p></td>
									<td>{{it.zyrs}}</td>
									<td>{{it.lql}}</td>
									<td ng-class="{true:'text-greener',false:'text-red'}[it.mask]">{{it.lqlc}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<!--end ss-mark-tab-con-->
					<div class="no-date" ng-if="cont.LQSL.pjlql=='--'">暂无数据...</div>
					<br />
					<div class="jx-fz-12" ng-hide="cont.LQSL.pjlql=='--' || cont.enroll_detail.page.sumcount &lt; 10">
						<a href="" class="jx-more-org" ng-click="getEnrollInfo()">查看全部</a>
					</div>
				</div>
			</div>
		</div>
		<div class="separate-lr no-top no-border">
			<div ng-hide="cont.XSBD.bdl=='--'">
				<span class="jx-note-green">{{cont.yearname}}新生报到率
					{{cont.XSBD.bdl}} ，未报到学生 {{cont.XSBD.rs}} 人</span>
			</div>
			<div ng-show="cont.XSBD.bdl=='--'">
				<span class="jx-note-green">{{cont.yearname}}新生报到情况暂无数据</span>
			</div>
			<div class="row clearfix">
				<div class="col-md-6">
					<a class="go-back text-blue" href="" ng-click="pageUp()" ng-if="cont.level!='1'"> 
					<img src="static/resource/css/image/fanhui2.png">&nbsp;返回上一级
					</a>
					<div class="block-tit">
						<p class="dis-inb">{{cont.yearname}}{{cont.mapt}}未报到学生分布</p>
						<a href=""></a>
					</div>
					<div>
						<div echart config="cont.fromCfg" ec-click="mapClick(param)"></div>
						<a href="" class="text-blue" ng-click="stuListClick(param)">查看{{cont.yearname}}{{cont.mapt}}未报到学生</a>
					</div>
				</div>
				<div class="col-md-6 last-child">
					<div class="block-tit">
						<p class="dis-inb">{{cont.yearname}}未报到原因分布</p>
						<a href=""></a>
					</div>
					<div>
						<div echart config="cont.rea" class="center-block"></div>
					</div>
				</div>
			</div>
		</div>
		<%-- 1.各省分数线详情--%>
		<div cs-window show="cont.score_line.detail_show" auto-center="true" style="padding: 0;">
			<!-- click-disappear="true" -->
			<div class="popup-form popup-form-blue" style="margin: 0px">
				<div class="popup-form-head clearfix">
					<h3 class="popup-form-title">{{cont.score_line.title}}</h3>
					<a href="" class="popup-form-close" ng-click="cont.score_line.detail_show=false">
					 <img src="static/resource/css/image/popup-form-close-red.png" alt="">
					</a>
				</div>
				<div class="popup-form-body clearfix" part-modal show-modal="cont.score_line.mask">
					<div class="popup-form-con clearfix">
						<div class="pull-right" ng-click="listDown('1')">
							<a href="" class="popup-form-export btn btn-default"> 
							<img src="static/resource/css/image/popup-form-export.png" alt="">
								<span>导出</span>
							</a>
						</div>
					</div>
					<div class="table-box">
						<table class="table table-bordered popup-form-table">
							<thead>
								<tr>
									<td ng-repeat="val in cont.score_line.headers">{{val}}</td>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="obj in cont.score_line.list">
									<td ng-repeat="val in cont.score_line.fields">{{obj[val]}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div ng-if="cont.score_line.list==''">暂无数据...</div>
					<div class="clearfix">
						<div class="pull-left" style="line-height: 38px; margin: 5px;">
							共{{cont.score_line.page.pagecount}}页,{{cont.score_line.page.sumcount}}条记录
						</div>
						<div pagination total-items="cont.score_line.page.sumcount" ng-model="cont.score_line.page.curpage"
							items-per-page="cont.score_line.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
					</div>
				</div>
			</div>
		</div>
		<%-- 2.今年超出分数线详情 --%>
		<div cs-window show="cont.score_detail.detail_show" auto-center="true" style="padding: 0;">
			<!-- click-disappear="true" -->
			<div class="popup-form popup-form-blue" style="margin: 0px">
				<div class="popup-form-head clearfix">
					<h3 class="popup-form-title">{{cont.score_detail.title}}</h3>
					<a href="" class="popup-form-close" ng-click="cont.score_detail.detail_show=false">
					 <img src="static/resource/css/image/popup-form-close-red.png" alt="">
					</a>
				</div>
				<div class="popup-form-body clearfix" part-modal show-modal="cont.score_detail.mask">
					<div class="popup-form-con clearfix">
						<div class="pull-right" ng-click="listDown('2')">
							<a href="" class="popup-form-export btn btn-default">
							 <img src="static/resource/css/image/popup-form-export.png" alt="">
								<span>导出</span>
							</a>
						</div>
					</div>
					<div class="table-box">
						<table class="table table-bordered popup-form-table">
							<thead>
								<tr>
									<td ng-repeat="val in cont.score_detail.headers">{{val}}</td>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="obj in cont.score_detail.list">
									<td ng-repeat="val in cont.score_detail.fields">{{obj[val]}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="clearfix">
						<div class="pull-left" style="line-height: 38px; margin: 5px;">
							共{{cont.score_detail.page.pagecount}}页,{{cont.score_detail.page.sumcount}}条记录
						</div>
						<div pagination total-items="cont.score_detail.page.sumcount" ng-model="cont.score_detail.page.curpage"
							items-per-page="cont.score_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
					</div>
				</div>
			</div>
		</div>
		<%-- 3.调剂率 --%>
		<div cs-window show="cont.adj_detail.detail_show" auto-center="true" style="padding: 0;">
			<div class="popup-form popup-form-blue" style="margin: 0px">
				<div class="popup-form-head clearfix">
					<h3 class="popup-form-title">{{cont.adj_detail.title}}</h3>
					<a href="" class="popup-form-close" ng-click="cont.adj_detail.detail_show=false">
						<img src="static/resource/css/image/popup-form-close-red.png" alt="">
					</a>
				</div>
				<div class="popup-form-body clearfix" part-modal show-modal="cont.adj_detail.mask">
					<div class="popup-form-con clearfix">
						<div class="pull-right" ng-click="listDown('3')">
							<a href="" class="popup-form-export btn btn-default">
							 <img src="static/resource/css/image/popup-form-export.png" alt="">
								<span>导出</span>
							</a>
						</div>
					</div>
					<div class="table-box">
						<table class="table table-bordered popup-form-table">
							<thead>
								<tr>
									<td ng-repeat="val in cont.adj_detail.headers">{{val}}</td>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="obj in cont.adj_detail.list">
									<td ng-repeat="val in cont.adj_detail.fields">{{obj[val]}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="clearfix">
						<div class="pull-left" style="line-height: 38px; margin: 5px;">
							共{{cont.adj_detail.page.pagecount}}页,{{cont.adj_detail.page.sumcount}}条记录
						</div>
						<div pagination total-items="cont.adj_detail.page.sumcount" ng-model="cont.adj_detail.page.curpage"
							items-per-page="cont.adj_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
					</div>
				</div>
			</div>
		</div>
		<%-- 4.自主招生 --%>
		<div cs-window show="cont.enroll_detail.detail_show" auto-center="true" style="padding: 0;">
			<div class="popup-form popup-form-blue" style="margin: 0px">
				<div class="popup-form-head clearfix">
					<h3 class="popup-form-title">{{cont.enroll_detail.title}}</h3>
					<a href="" class="popup-form-close" ng-click="cont.enroll_detail.detail_show=false"> 
						<img src="static/resource/css/image/popup-form-close-red.png" alt="">
					</a>
				</div>
				<div class="popup-form-body clearfix" part-modal show-modal="cont.enroll_detail.mask">
					<div class="popup-form-con clearfix">
						<div class="pull-right" ng-click="listDown('4')">
							<a href="" class="popup-form-export btn btn-default">
							 <img src="static/resource/css/image/popup-form-export.png" alt="">
								<span>导出</span>
							</a>
						</div>
					</div>
					<div class="table-box">
						<table class="table table-bordered popup-form-table">
							<thead>
								<tr>
									<td ng-repeat="val in cont.enroll_detail.headers">{{val}}</td>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="obj in cont.enroll_detail.list">
									<td ng-repeat="val in cont.enroll_detail.fields">{{obj[val]}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="clearfix">
						<div class="pull-left" style="line-height: 38px; margin: 5px;">
							共{{cont.enroll_detail.page.pagecount}}页,{{cont.enroll_detail.page.sumcount}}条记录
						</div>
						<div pagination total-items="cont.enroll_detail.page.sumcount" ng-model="cont.enroll_detail.page.curpage"
							items-per-page="cont.enroll_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
					</div>
				</div>
			</div>
		</div>
		<%-- 5.未报到学生分布详情 --%>
		<div cs-window show="cont.wbd_detail.detail_show" auto-center="true" style="padding: 0;">
			<div class="popup-form popup-form-blue" style="margin: 0px">
				<div class="popup-form-head clearfix">
					<h3 class="popup-form-title">{{cont.wbd_detail.title}}</h3>
					<a href="" class="popup-form-close" ng-click="cont.wbd_detail.detail_show=false">
						<img src="static/resource/css/image/popup-form-close-red.png" alt="">
					</a>
				</div>
				<div class="popup-form-body clearfix" part-modal show-modal="cont.wbd_detail.mask">
					<div class="popup-form-con clearfix">
						<div class="pull-right" ng-click="listDown('5')">
							<a href="" class="popup-form-export btn btn-default">
							 <img src="static/resource/css/image/popup-form-export.png" alt="">
								<span>导出</span>
							</a>
						</div>
					</div>
					<div class="table-box">
						<table class="table table-bordered popup-form-table">
							<thead>
								<tr>
									<td ng-repeat="val in cont.wbd_detail.headers">{{val}}</td>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="obj in cont.wbd_detail.list">
									<td ng-repeat="val in cont.wbd_detail.fields">{{obj[val]}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div ng-if="cont.wbd_detail.list==''">暂无数据...</div>
					<div class="clearfix">
						<div class="pull-left" style="line-height: 38px; margin: 5px;">
							共{{cont.wbd_detail.page.pagecount}}页,{{cont.wbd_detail.page.sumcount}}条记录
						</div>
						<div pagination total-items="cont.wbd_detail.page.sumcount" ng-model="cont.wbd_detail.page.curpage"
							items-per-page="cont.wbd_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
					</div>
				</div>
			</div>
		</div>
		<!-- 6.未报到原因详情 -->
		<div cs-window show="cont.reason_detail.detail_show" auto-center="true" style="padding: 0;">
			<div class="popup-form popup-form-blue" style="margin: 0px">
				<div class="popup-form-head clearfix">
					<h3 class="popup-form-title">{{cont.reason_detail.title}}</h3>
					<a href="" class="popup-form-close" ng-click="cont.reason_detail.detail_show=false"> 
						<img src="static/resource/css/image/popup-form-close-red.png" alt="">
					</a>
				</div>
				<div class="popup-form-body clearfix" part-modal
					show-modal="cont.reason_detail.mask">
					<div class="popup-form-con clearfix">
						<div class="pull-right" ng-click="listDown('6')">
							<a href="" class="popup-form-export btn btn-default">
							 <img src="static/resource/css/image/popup-form-export.png" alt="">
								<span>导出</span>
							</a>
						</div>
					</div>
					<div class="table-box">
						<table class="table table-bordered popup-form-table">
							<thead>
								<tr>
									<td ng-repeat="val in cont.reason_detail.headers">{{val}}</td>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="obj in cont.reason_detail.list">
									<td ng-repeat="val in cont.reason_detail.fields">{{obj[val]}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="clearfix">
						<div class="pull-left" style="line-height: 38px; margin: 5px;">
							共{{cont.reason_detail.page.pagecount}}页,{{cont.reason_detail.page.sumcount}}条记录
						</div>
						<div pagination total-items="cont.reason_detail.page.sumcount" ng-model="cont.reason_detail.page.curpage"
							items-per-page="cont.reason_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>