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
<base href="<%=basePath%>" />
<title>新生</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/newStu/controller.js"></script>
<script type="text/javascript" src="product/newStu/service.js"></script>

<style type="text/css">
.inline {
	display: inline;
}

.view-1.ng-enter {
	transition: transform 0.5s;
	transform: translateX(-100%);
}

.view-1.ng-enter.ng-enter-active {
	transform: translateX(0);
}

.view-1.ng-leave {
	transition: transform 0.5s;
	transform: translateX(0);
}

.view-1.ng-leave.ng-leave-active {
	transform: translateX(-100%);
}

.view-2.ng-enter {
	transition: transform 0.5s;
	transform: translateX(100%);
}

.view-2.ng-enter.ng-enter-active {
	transform: translateX(0);
}

.view-2.ng-leave {
	transition: transform 0.5s;
	transform: translateX(0);
}

.view-2.ng-leave.ng-leave-active {
	transform: translateX(100%);
}
</style>

</head>
<body ng-controller="controller">
 <div class="xuegong-zhuti-content">
<div class="xuegong-zhuti-wrapper">
	<div class="xuegong-zhuti-content" >
		<header>
		<div class="pull-right">
			<a href="" ng-click="data.advance.show=!data.advance.show"><span></span>
				<p>高级查询</p></a> <i></i> <a href="" class="tansuo disable"><span></span>
				<p>探索</p></a> <i></i> <a href="" class="disable"><span></span>
				<p>导出</p></a>
		</div>
		<div class="clearfix"></div>
		</header>
		<div class="xuegong-zhuti-content-main">
			<div ng-show="data.advance.show"
				class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
				<div cg-custom-comm source="data.advance.source"
					on-change="advanceChange($data)" expand="true" no-border="true"></div>
			</div>

			<div class="xuegong-separate-tb">

				<div class="xuegong-zhuti-content-main-tit">
					<b class="text-blue">{{data.grade}}年新生入学</b>
				</div>
				<div class="baodao-box">
					<div class="freshman-baodao view-1" ng-if="view.current==1">
						<div class="xuegong-separate-con">
							<div class="row-t">
								<div class="baodao-shifou-box">
									<p class="baodao-shifou">
										<i class="fa fa-check-circle text-greener"></i><b>已报到</b>
									</p>
									<div class="img-box">
										<div style="height: 200px" echart
											config="data.distribute.isRegisterCfg" class="center-block"></div>
									</div>
								</div>
								<div class="zaiji-num freshman-tab">
									<div>
										<table class="table">
											<tbody>
												<tr>
													<td colspan="2">已报到<b class="text-green">&nbsp;{{data.yes}}&nbsp;</b>人
													</td>
													<td title="报到人数除以招生总人数">报到率<b class="text-green">&nbsp;{{data.isrete|number:2}}%</b></td>
												</tr>
												<tr ng-repeat="x in data.ylist"
													ng-class="{'benke-tr':$index==0,'zhuanke-tr':$index==1,'yanjius-tr':$index==2}">
													<td>{{x.name}}</td>
													<td
														ng-class="{'text-green':$index==0,'text-purple':$index==1,'text-blue':$index==2}">{{x.value}}人</td>
													<td title="{{x.name}}报到人数除以{{x.name}}招生总人数"
														ng-class="{'text-green':$index==0,'text-purple':$index==1,'text-blue':$index==2}">报到率：{{x.lv|number:2}}%</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<!-- 	<div class="baodao-text" ng-click="viewClick('2')">
								<p class="baodao-yes-text">
									<b>未报到<b
										class="text-pink">&nbsp;{{data.no}}&nbsp;</b>人，占比<b
										class="text-pink">&nbsp;{{data.notrete}}%</b></b>
								</p>
							</div> -->
								<div class="baodao-text">
									<div>
										<div class="point point-01">
											<a href="" ng-click="viewClick('2')"><span></span></a>
										</div>
										<p class="baodao-yes-text">
											<a href=""></a><b>未报到<a href="" ng-click= "data.no==0||stuDetailClick('register',data.grade+'（未报到）学生名单',0)"ng-class="{'text-pink':data.no>0,'text-pink cursor-default':data.no==0}">&nbsp;{{data.no}}&nbsp;</a>人，占比<b
												class="text-pink">&nbsp;{{data.notrete}}%</b></b>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="freshman-baodao freshman-baodao-no view-2" ng-if="view.current==2">
						<div class="xuegong-separate-con">
							<div class="row-t">
								<!-- 	<div class="baodao-text baodao-text-02" ng-click="viewClick('1')">
								<p class="baodao-no-text text-right">
									<b>已报到<b class="text-green">&nbsp;{{data.yes}}&nbsp;</b>人，占比<b
										class="text-green">&nbsp;{{data.isrete}}%</b></b>
								</p>
							</div> -->
								<div class="baodao-text baodao-text-02">
									<div>
										<p class="baodao-no-text text-right">
											<b>已报到<a href="" ng-click= "data.yes==0||stuDetailClick('register',data.grade+'（已报到）学生名单',1)" ng-class="{'text-green':data.yes>0,'text-green cursor-default':data.yes==0}">&nbsp;{{data.yes}}&nbsp;</a>人，占比<b
												class="text-green">&nbsp;{{data.isrete}}%</b></b><a href=""></a>
										</p>
										<div class="point point-02">
											<a href="" ng-click="viewClick('1')"><span></span></a>
										</div>
									</div>
								</div>
								<div class="zaiji-num freshman-tab freshman-baodao-no-tab">
									<div>
										<table class="table">
											<tbody>
												<tr>
													<td colspan="2">未报到<b class="text-pink">&nbsp;{{data.no}}</b>人
													</td>
													<td title="未报到人数除以招生总人数">未报到率<b class="text-pink">&nbsp;{{data.notrete|number:2}}%</b></td>
												</tr>
												<tr ng-repeat="y in data.nlist" ng-class="{'benke-tr':$index==0,'zhuanke-tr':$index==1,'yanjius-tr':$index==2}">
													<td>{{y.name}}</td>
													<td
														ng-class="{'text-green':$index==0,'text-purple':$index==1,'text-blue':$index==2}">{{y.value}}人</td>
													<td title="{{y.name}}未报到人数除以{{y.name}}招生总人数"
														ng-class="{'text-green':$index==0,'text-purple':$index==1,'text-blue':$index==2}">未报到率：{{y.lv|number:2}}%</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="baodao-shifou-box">
									<p class="baodao-shifou text-right">
										<i class="fa fa-warning text-pink"></i><b>未报到</b>
									</p>
									<div class="img-box">
										<div style="height: 200px" echart config="data.distribute.notRegisterCfg" class="center-block"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="xuegong-separate-tb">
					<div class="zaiji-geyuanxi">
						<div class="xuegong-zhuti-content-main-tit">
							<b class="text-blue">历年新生人数与报到率</b>
							<span class="question-icon pull-right" ng-mouseover="data.tip_show=true" ng-mouseleave="data.tip_show=false">
		                        <a class="question-img  pull-right" style="margin-top:0px"></a>
		                        <div class="question-area-box question-area-box-top" ng-show="data.tip_show">
		                            <div class="question-area">
		                                <img src="static/resource/images/xues-gognzuozhe/question-k.png">
		                                <p>报到率：</p>
		                        		<p>某年的报到人数除以某年招生总人数</p>
		                            </div>
		                        </div>
		                    </span>
						</div>
						<div class="xuegong-separate-con">
							<div class="img-box">
								<div class="img-box-only">
									<div echart config="data.distribute.countAndLvCfg"
										class="center-block"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="xuegong-separate-tb">
					<div class="zaiji-geyuanxi">
						<div class="xuegong-zhuti-content-main-tit">
							<b class="text-blue">近五年各{{data.levelName}}新生平均报到率</b>
							<span class="question-icon pull-right" ng-mouseover="data.tip_show=true" ng-mouseleave="data.tip_show=false">
		                        <a class="question-img  pull-right" style="margin-top:0px"></a>
		                        <div class="question-area-box question-area-box-top" ng-show="data.tip_show">
		                            <div class="question-area">
		                                <img src="static/resource/images/xues-gognzuozhe/question-k.png">
		                                <p>平均报到率：</p>
		                        		<p>每年报到率相加除以总年份</p>
		                            </div>
		                        </div>
		                    </span>
						</div>
						<div class="xuegong-separate-con">
							<div class="img-box">
								<div class="img-box-only">
									<div echart config="data.distribute.deptAvgLvCfg"
										class="center-block"></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<table class="separate-l-r">
					<tbody>
						<tr>
							<td class="freshman-separate-l">
								<div class="xuegong-zhuti-content-main-tit">
									<b class="text-blue">贫困生</b>
								</div>
							</td>
							<td class="tab-cell"></td>
							<td>
								<div class="xuegong-zhuti-content-main-tit">
									<b class="text-blue">{{data.poor.year}}年入学贫困生户口对比</b>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="xuegong-separate-con no-border">
									<small>贫困生共<b class="text-green">&nbsp;{{data.poor.count}}&nbsp;</b>人，较去年{{data.poor.lv >0 ?'增长':'下降'}}
									<b ng-class="{'text-green':data.poor.lv <=0,'text-pink':data.poor.lv >0}">&nbsp;{{data.poor.lv|jdz}}%</b></small>
									<div class="img-box">
										<div class="img-box-only">
											<div echart config="data.distribute.poorCountCfg"
												ec-click="poorClick(param)" class="center-block"></div>
										</div>
									</div>
								</div>
							</td>
							<td class="tab-cell"></td>
							<td class="vertical">
								<div class="xuegong-separate-con  no-border">
									<div class="img-box">
										<div class="img-box-only">
											<div echart config="data.distribute.poorPieCfg"
												class="center-block "></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>



				<table class="separate-l-r">
					<tbody>
						<tr>
							<td class="freshman-separate-l">
								<div class="xuegong-zhuti-content-main-tit">
									<b class="text-blue">助学贷款</b>
								</div>
							</td>
							<td class="tab-cell"></td>
							<td>
								<div class="xuegong-zhuti-content-main-tit">
									<b class="text-blue">{{data.loan.year}}年入学助学贷款户口对比</b>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="xuegong-separate-con no-border">
									<small>助学贷款共<b class="text-green">&nbsp;{{data.loan.count}}&nbsp;</b>人，较去年{{data.loan.lv >0 ?'增长':'下降'}}<b
										ng-class="{'text-green':data.loan.lv <=0,'text-pink':data.loan.lv >0}">&nbsp;{{data.loan.lv|jdz}}%</b></small>
									<div class="img-box">
										<div class="img-box-only">
											<div echart config="data.distribute.loanCountCfg"
												ec-click="loanClick(param)" class="center-block"></div>
										</div>
									</div>
								</div>
							</td>
							<td class="tab-cell"></td>
							<td class="vertical">
								<div class="xuegong-separate-con no-border">
									<div class="img-box">
										<div class="img-box-only">
											<div echart config="data.distribute.loanPieCfg"
												class="center-block "></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<table class="separate-l-r">
					<tbody>
						<tr>
							<td class="freshman-separate-l">
								<div class="xuegong-zhuti-content-main-tit">
									<b class="text-blue">学费减免</b>
								</div>
							</td>
							<td class="tab-cell"></td>
							<td>
								<div class="xuegong-zhuti-content-main-tit">
									<b class="text-blue">{{data.jm.year}}年入学学费减免户口对比</b>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="xuegong-separate-con no-border">
									<small>学费减免共<b class="text-green">&nbsp;{{data.jm.count}}&nbsp;</b>人，较去年{{data.jm.lv >0 ?'增长':'下降'}}<b
										ng-class="{'text-green':data.jm.lv <=0,'text-pink':data.jm.lv >0}">&nbsp;{{data.jm.lv|jdz}}%</b></small>
									<div class="img-box">
										<div class="img-box-only">
											<div echart config="data.distribute.jmCountCfg"
												ec-click="jmClick(param)" class="center-block"></div>
										</div>
									</div>
								</div>
							</td>
							<td class="tab-cell"></td>
							<td class="vertical">
								<div class="xuegong-separate-con no-border">
									<div class="img-box">
										<div class="img-box-only">
											<div echart config="data.distribute.jmPieCfg"
												class="center-block "></div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		</div>
		</div>
		</div>
    <%-- 分布 详情 --%>
    <div modal-form config="data.stu_detail.formConfig"></div>
	 <!-- <div cs-window show="data.stu_detail.detail_show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.stu_detail.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.stu_detail.detail_show=false">
		        	<img src="static/resource/css/image/popup-form-close.png" alt="">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix" part-modal show-modal="data.stu_detail.mask">
		    	<div class="popup-form-con clearfix">
			    	<div class="pull-right">
		            	<a href="" class="popup-form-export btn btn-default" ng-click="stuDetailDown()">
		                	<img src="static/resource/css/image/popup-form-export.png" alt="">
		                    <span>导出</span>
		                </a>
		            </div>
		    	</div>
		    	<div class="table-box">
			    	<table class="table table-bordered popup-form-table">
			        	<thead>
			            	<tr>
			                    <td ng-repeat="val in data.stu_detail.headers">{{val}}</td>
			                </tr>
			            </thead>
			            <tbody>
			            	<tr ng-repeat="obj in data.stu_detail.list">
			                    <td ng-repeat="val in data.stu_detail.fields">{{obj[val]}}</td>
			                </tr>
		                </tbody>
			        </table>
		        </div>
				    <div class="clearfix">
		    		<div class="pull-left" style="line-height: 38px;margin: 5px;">
		    			共{{data.stu_detail.page.pagecount}}页,{{data.stu_detail.page.sumcount}}条记录
		    		</div>
					<div pagination total-items="data.stu_detail.page.sumcount" ng-model="data.stu_detail.page.curpage" 
						items-per-page="data.stu_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
		    	</div>
			</div>
		</div>
	</div>	 -->
</body>
</html>