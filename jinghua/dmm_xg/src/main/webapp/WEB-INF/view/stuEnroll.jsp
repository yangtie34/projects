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
<title>在籍生基本概况</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/stuEnroll/controller.js"></script>
<script type="text/javascript" src="product/stuEnroll/service.js"></script>

<style type="text/css">
.inline {
	display: inline;
}
</style>

</head>
<body ng-controller="controller">
	<div class="xuegong-zhuti-content">
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
			<div class="xuegong-zhuti-content-main-tit zaiji-num">
				<table>
					<tbody>
						<tr>
							<td style="width: {{data.schwidth}}" class="xuexiao-num">
								<div>
									<table class="table">
										<tbody>
											<tr>
												<td>
													<p>
														{{data.dept}}</p><br>
													<p>	<a href="" ng-class="{'text-green text-sixth':data.deptcount>0,'text-green text-sixth cursor-default':data.deptcount==0}" ng-click="data.deptcount==0||stuDetailClick('dept1',data.dept+'学生名单')">{{data.deptcount}}</a><p class="text-green text-sixth">人</p>
													</p>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
							<td class="cell"></td>
							<td class="xiaoqu-num" style="width: {{data.camwidth}}" ng-if="data.campus.length>1">
								<div>
									<table class="table zaiji-tab">
										<tbody>
											<tr ng-repeat="x in data.campus">
												<td>{{x.name}}</td>
												<td class="text-green text-sixth"><a href="" ng-class="{'text-green text-sixth':x.value>0,'text-green text-sixth cursor-default':x.value==0}" ng-click="x.value==0||stuDetailClick('campus',x.name+'学生名单',x.id)">{{x.value}}</a>人</td>
												<td class="text-gray">占比{{x.value/data.deptcount*100|number:2}}%</td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
							<td ng-if="data.campus.length>1" class="cell"></td>
							<td class="xueli-num" style="width: {{data.eduwidth}}">
								<div>
									<table class="table  zaiji-tab">
										<tbody>
											<tr ng-repeat="y in data.edu">
												<td>{{y.name}}</td>
												<td class="text-green text-sixth"><a href="" ng-class="{'text-green text-sixth':y.value>0,' text-green text-sixth cursor-default':y.value==0}" ng-click= "y.value==0||stuDetailClick('edu',y.name+'学生名单',y.id)">{{y.value}}</a>人</td>
												<td class="text-gray">占比{{y.value/data.deptcount*100|number:2}}%</td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
							<td class="cell"></td>
							<td class="grade-num" style="width: {{data.grawidth}}">
								<div>
									<table class="table  zaiji-tab">
										<tbody>
											<tr ng-repeat="z in data.grade">
												<td>{{z.name}}</td>
												<td class="text-green text-sixth"><a href="" ng-class="{'text-green text-sixth':z.value>0,'text-green text-sixth cursor-default':z.value==0}" ng-click= " z.value==0 ||stuDetailClick('grade',z.name+'学生名单',z.id)">{{z.value}}</a>人</td>
												<td class="text-gray">占比{{z.value/data.deptcount*100|number:2}}%</td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="xuegong-zhuti-content-main-tit zaiji-num" ng-if="data.gtaCount >0">
            	<table>
            		<tbody>
            			<tr>
							<td class="xuexiao-num" style="width: {{data.schwidth}}">
        						<div>
                                	<table class="table">
                                    	<tbody>
                                        <tr>
                                        	<td>
                                    			<p>研究生（硕士）</p><br><p><a href="" class="text-green text-sixth" ng-click="yjsDetailClick('edu','','研究生（硕士）学生名单',data.gtaId,'')">{{data.gtaCount}}</a><p class="text-green text-sixth">人</p></p>
                                    		</td>
                                    	</tr>
                                    	</tbody>
                                    </table>
                                </div>
							</td>
							<td class="cell"></td>
                 			<td class="xiaoqu-num" style="width: {{data.camwidth}}">
                				<div>
                				<table class="table zaiji-tab">
                    				<tbody>
                                        <tr ng-repeat= "cs in data.gtaStyle">
                                            <td style="width:39%">{{cs.mc}}</td>
                                            <td class="text-green text-sixth"><a href="" class="text-green" ng-click="yjsDetailClick('edu','yjsStyle',cs.mc+'学生名单',data.gtaId,cs.id)">{{cs.value}}</a>人</td>
                                            <td class="text-gray">占比{{cs.value/data.gtaCount*100|number:2}}%</td>
                                        </tr>
                                    </tbody>
                    			</table>
                				</div>
                			</td>
                			<td class="cell"></td>
                			<td class="xueli-num" style="width: {{data.eduwidth}}">
                				<div>
                                <table class="table zaiji-tab">
                                    <tbody>
                                        <tr ng-repeat=" co in data.gtaDxjy">
                                            <td>{{co.mc}}</td>
                                            <td class="text-green text-sixth"><a href="" class="text-green" ng-click="yjsDetailClick('edu','dxjy',co.mc+'学生名单',data.gtaId,co.id)">{{co.value}}</a>人</td>
                                            <td class="text-gray">占比{{co.value/data.gtaCount*100|number:2}}%</td>
                                        </tr>
                                    </tbody>
                                </table>
                				</div>
                			</td>
                			<td class="cell"></td>
                			<td class="grade-num" style="width: {{data.grawidth}}">
                				<div>
                                <table class="table  zaiji-tab">
                                    <tbody>
                                        <tr ng-repeat = "gtaGrade in data.gtaGrade">
                                            <td>{{gtaGrade.mc}}</td>
                                            <td class="text-green text-sixth"><a href="" class="text-green" ng-click="yjsDetailClick('edu','ENROLL_GRADE',gtaGrade.mc+'学生名单',data.gtaId,gtaGrade.id)">{{gtaGrade.value}}</a>人</td>
                                            <td class="text-gray">占比{{gtaGrade.value/data.gtaCount*100|number:2}}%</td>
                                        </tr>
                                    </tbody>
                                </table>
                				</div>
                            </td>
                        </tr>
                	</tbody> 
                </table>
            </div>
            <!--硕士end-->
            <div class="xuegong-zhuti-content-main-tit zaiji-num no-border" ng-if="data.bsCount >0">
            	<table style="margin:10px auto;">
            		<tbody>
            			<tr>
							<td class="xuexiao-num" >
        						<div>
                                	<table class="table h-50">
                                    	<tbody>
                                        <tr>
                                        	<td>
                                    			<p>研究生（博士）</p><p>
                                    			<a href="" class="text-green text-sixth" ng-click="yjsDetailClick('edu','','研究生（博士）学生名单',data.bsId,'')"> {{data.bsCount}}</a><p class="text-green text-sixth">人</p></p>
                                    		</td>
                                    	</tr>
                                    	</tbody>
                                    </table>
                                </div>
							</td>
                 			<td class="" ng-repeat = "bsGrade in data.bsGrade">
                				<div>
                				<table class="table zaiji-tab h-50 border-l-dashed">
                    				<tbody>
                                        <tr>
                                            <td>{{bsGrade.mc}}</td>
                                            <td class="text-green text-sixth"><a href="" class="text-green" ng-click="yjsDetailClick('edu','ENROLL_GRADE',bsGrade.mc+'学生名单',data.bsId,bsGrade.id)">{{bsGrade.value}}</a>人</td>
                                            <td class="text-gray">占比{{bsGrade.value/data.gtaCount*100|number:2}}%</td>
                                        </tr>
                                    </tbody>
                    			</table>
                				</div>
                			</td>
                        </tr>
                	</tbody> 
                </table>
            </div>
			<section class="zaiji-separate">
			<table class="separate-l-r">
				<tbody>
					<tr>
						<td class="zaiji-xs-zt">
							<div class="xuegong-zhuti-content-main-tit">
								<b class="text-blue" ng-if="!data.stateshow">学生总数变化趋势</b><b
									class="text-blue" ng-if="data.stateshow">在籍生状态</b> <a href=""
									ng-click="swichState()"><span class="time-img pull-right"></span></a>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td>
							<div class="xuegong-zhuti-content-main-tit">
								<b ng-if="data.fromshow" class="text-blue">在籍生生源地</b> <b
									ng-if="!data.fromshow" class="text-blue">各地区学生总数变化趋势</b><a
									href="" ng-click="swichFrom()"><span
									class="time-img pull-right"></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="xuegong-separate-con">
								<div class="img-box">
									<div class="img-box-only">
										<div style="height: 350px" ng-if="data.stateshow" echart
											config="data.distribute.stateCfg" class="center-block"></div>
										<div style="height: 350px" ng-if="!data.stateshow" echart
											config="data.history.stateLineCfg" class="center-block"></div>
									</div>
								</div>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td class="txt">
							<div class="xuegong-separate-con">
								<small class="txt-s" ng-if="data.fromshow&&(data.gat>0||data.q>0)"> {{data.gat>0?'港澳台学生':''}}<a href =""
										class="text-green text-eighth" ng-click="stuDetailClick('gatq','港澳台学生名单',data.gatcode)" ng-if="data.gat>0">{{data.gat}}</a>{{data.gat>0?'人，':''}}{{data.q>0?'留学生':''}}<a href =""
										class="text-green text-eighth" ng-click="stuDetailClick('gatq','留学生学生名单',data.qcode)"ng-if="data.q>0">{{data.q}}</a>{{data.q>0?'人':''}}
								</small>
								<div class="img-box" >
									<div class="img-box-only" >
										<div ng-if="data.fromshow" echart
											config="data.distribute.fromCfg" class="center-block"></div>
										<div ng-if="!data.fromshow" echart
											config="data.history.fromLineCfg" class="center-block"></div>
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
						<td class="zaiji-xs-zt">
							<div class="xuegong-zhuti-content-main-tit">
								<b ng-if="data.ageshow" class="text-blue">在籍生年龄分布</b> <b
									ng-if="!data.ageshow" class="text-blue">新生入学时平均年龄变化趋势</b><a
									href="" ng-click="swichAge()"><span
									class="time-img pull-right"></span></a>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td>
							<div class="xuegong-zhuti-content-main-tit">
								<b ng-if="data.nationshow" class="text-blue">少数民族组成</b> <b
									ng-if="!data.nationshow" class="text-blue">汉族少数民族学生总数变化趋势</b> <a
									href="" ng-click="swichNation()"><span
									class="time-img pull-right"></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="xuegong-separate-con">
								<div class="img-box">
									<div class="img-box-only">
										<div ng-if="data.ageshow" echart
											config="data.distribute.ageCfg" class="center-block"></div>
										<div ng-if="!data.ageshow" echart
											config="data.history.ageLineCfg" class="center-block"></div>
									</div>
								</div>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td class="txt">
							<div class="xuegong-separate-con">
								<small ng-if="data.nationshow&&data.mzwwh>0" class="txt-s">
									汉族<a href="" class="text-green text-eighth" ng-click="stuDetailClick('nation','汉族学生名单','1')">{{data.han}}</a>人，占比<c
										class="text-green text-eighth">{{data.han/data.deptcount*100|number:2}}%</c>；少数民族<a href=""
										class="text-green text-eighth" ng-click="stuDetailClick('nation','少数民族学生名单','2')">{{data.shaoshu}}</a>人，占比<c
										class="text-green text-eighth">{{data.shaoshu/data.deptcount*100|number:2}}%</c>；未知<a href=""
										class="text-green text-eighth" ng-click="stuDetailClick('nation','民族未知的学生名单','3')">{{data.mzwwh}}</a>人，占比<c
										class="text-green text-eighth">{{data.mzwwh/data.deptcount*100|number:2}}%</c>。
								</small> <small ng-if="data.nationshow&&data.mzwwh==0" class="txt-s">
									汉族<a href="" class="text-green text-eighth" ng-click="stuDetailClick('nation','汉族学生名单','1')">{{data.han}}</a>人，占比<c
										class="text-green text-eighth">{{data.han/data.deptcount*100|number:2}}%</c>；少数民族<a href=""
										class="text-green text-eighth" ng-click="stuDetailClick('nation','少数民族学生名单','2')">{{data.shaoshu}}</a>人，占比<c
										class="text-green text-eighth">{{data.shaoshu/data.deptcount*100|number:2}}%</c>。
								</small>
								<div class="img-box">
									<div class="img-box-only">
										<div ng-if="data.nationshow" echart
											config="data.distribute.nationCfg" class="center-block"></div>
										<div ng-if="!data.nationshow" echart
											config="data.history.nationLineCfg" class="center-block"></div>
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
						<td class="zaiji-xs-zt">
							<div class="xuegong-zhuti-content-main-tit">
								<b ng-if="data.sexshow" class="text-blue">在籍生性别分布</b> <b
									ng-if="!data.sexshow" class="text-blue">各性别学生总数变化趋势</b><a
									href="" ng-click="swichSex()"><span
									class="time-img pull-right"></span></a>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td>
							<div class="xuegong-zhuti-content-main-tit">
								<b ng-if="data.politicsshow" class="text-blue">政治面貌分布</b> <b
									ng-if="!data.politicsshow" class="text-blue">各政治面貌学生总数变化趋势</b><a
									href="" ng-click="swichPolitics()"><span
									class="time-img pull-right"></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="xuegong-separate-con">
								<div class="img-box">
									<div class="img-box-only">
										<div style="height: 350px" ng-if="data.sexshow" echart
											config="data.distribute.sexCfg" class="center-block"></div>
										<div ng-if="!data.sexshow" echart
											config="data.history.sexLineCfg" class="center-block"></div>
									</div>
								</div>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td class="txt">
							<div class="xuegong-separate-con">
								<small class="txt-s" ng-if="data.politicsshow&&data.zzmmwwh>0">
									团员<a href=""class="text-green text-eighth" ng-click="stuDetailClick('politics','团员-学生名单','1')">{{data.tuan}}</a>人，占比<c
										class="text-green text-eighth">{{data.tuan/data.deptcount*100|number:2}}%</c>；除团员以外<a href=""
										class="text-green text-eighth" ng-click="stuDetailClick('politics','非团员-学生名单','2')">{{data.fei}}</a>人，占比<c
										class="text-green text-eighth">{{data.fei/data.deptcount*100|number:2}}%</c>；未知<a href=""
										class="text-green text-eighth" ng-click="stuDetailClick('politics','政治面貌未知-学生名单','3')">{{data.zzmmwwh}}</a>人，占比<c
										class="text-green text-eighth">{{data.zzmmwwh/data.deptcount*100|number:2}}%</c>。
								</small> <small class="txt-s" ng-if="data.politicsshow&&data.zzmmwwh==0">
									团员<a  href="" class="text-green text-eighth" ng-click="stuDetailClick('politics','团员-学生名单','1')">{{data.tuan}}</a>人，占比<c
										class="text-green text-eighth">{{data.tuan/data.deptcount*100|number:2}}%</c>；除团员以外<a href=""
										class="text-green text-eighth" ng-click="stuDetailClick('politics','非团员-学生名单','2')">{{data.fei}}</a>人，占比<c
										class="text-green text-eighth">{{data.fei/data.deptcount*100|number:2}}%</c>。
								</small>
								<div class="img-box">
									<div class="img-box-only">
										<div style="height: 340px" ng-if="data.politicsshow" echart
											config="data.distribute.politicsCfg" class="center-block"></div>
										<div ng-if="!data.politicsshow" echart
											config="data.history.politicsLineCfg" class="center-block"></div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

			<div class="xuegong-separate-tb">
				<div class="zaiji-geyuanxi">
					<div class="xuegong-zhuti-content-main-tit">
						<b ng-if="data.deptshow" class="text-blue">各{{levelName}}学生分布</b> <b
							ng-if="!data.deptshow" class="text-blue">各{{levelName}}学生总数变化趋势</b><a
							href="" ng-click="swichDept()"><span
							class="time-img pull-right"></span></a>
					</div>
					<div class="xuegong-separate-con">
						<div class="img-box">
							<div class="img-box-only">
								<div ng-if="data.deptshow" echart
									config="data.distribute.contrastCfg" class="center-block"></div>
								<div ng-if="!data.deptshow" echart
									config="data.history.deptLineCfg" class="center-block"></div>
							</div>
						</div>
					</div>
				</div>
			</div>

<!-- 			<div class="xuegong-separate-tb">
				<div class="zaiji-geyuanxi">
					<div class="xuegong-zhuti-content-main-tit">
						<b ng-if="data.styleshow" class="text-blue">在籍生学习方式和学习形式</b> <b
							ng-if="!data.styleshow" class="text-blue">各学习方式和学习形式学生总数变化趋势</b><a
							href="" ng-click="swichStyle()"><span
							class="time-img pull-right"></span></a>
					</div>
					<div class="xuegong-separate-con">
						<div class="img-box">
							<div class="img-box-only">
								<div ng-if="data.styleshow">
									<div style="height: 350px" echart
										config="data.distribute.styleCfg" class="center-block"></div>
								</div>
								<div ng-if="!data.styleshow">
									<div echart config="data.history.styleLineCfg"
										class="center-block"></div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</div>
			</div> -->
	<table class="separate-l-r">
				<tbody>
					<tr>
						<td class="zaiji-xs-zt">
							<div class="xuegong-zhuti-content-main-tit">
								<b class="text-blue" ng-if="!data.styleshow">各学习方式学生人数变化趋势</b><b
									class="text-blue" ng-if="data.styleshow">在籍生学习方式分布</b> <a href=""
									ng-click="swichStyle()"><span class="time-img pull-right"></span></a>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td>
							<div class="xuegong-zhuti-content-main-tit">
								<b ng-if="data.fromshow" class="text-blue">在籍生学习形式分布</b> <b
									ng-if="!data.fromshow" class="text-blue">各学习方式学生人数变化趋势</b><a
									href="" ng-click="swichForm()"><span
									class="time-img pull-right"></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="xuegong-separate-con">
								<div class="img-box">
									<div class="img-box-only">
										<div style="height: 350px" ng-if="data.styleshow" echart
											config="data.distribute.styleCfg" class="center-block"></div>
										<div style="height: 350px" ng-if="!data.styleshow" echart
											config="data.history.styleLineCfg" class="center-block"></div>
									</div>
								</div>
							</div>
						</td>
						<td class="tab-cell"></td>
						<td class="txt">
							<div class="xuegong-separate-con">
								<div class="img-box">
									<div class="img-box-only">
										<div style="height: 350px" ng-if="data.formshow" echart
											config="data.distribute.formCfg" class="center-block"></div>
										<div style="height: 350px" ng-if="!data.formshow" echart
											config="data.history.formLineCfg" class="center-block"></div>
									</div>
								</div>
							</div>
						</td>
					</tr>
				</tbody>
			</table>

			</section>
		</div>
	</div>
	    <%-- 分布 详情 --%>
    <div modal-form config="data.stu_detail.formConfig"></div>
        <%-- 分布 详情 --%>
    <div modal-form config="data.history_detail.formConfig"></div>
	<!--  <div cs-window show="data.stu_detail.detail_show" autocenter="true" style="padding: 0;">
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
	</div>
    <div cs-window show="data.history_detail.detail_show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.history_detail.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.history_detail.detail_show=false">
		        	<img src="static/resource/css/image/popup-form-close.png" alt="">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix" part-modal show-modal="data.history_detail.mask">
		    	<div class="popup-form-con clearfix">
			    	<div class="pull-right">
		            	<a href="" class="popup-form-export btn btn-default" ng-click="historyDetailDown()">
		                	<img src="static/resource/css/image/popup-form-export.png" alt="">
		                    <span>导出</span>
		                </a>
		            </div>
		    	</div>
		    	<div class="table-box">
			    	<table class="table table-bordered popup-form-table">
			        	<thead>
			            	<tr>
			                    <td ng-repeat="val in data.history_detail.headers">{{val}}</td>
			                </tr>
			            </thead>
			            <tbody>
			            	<tr ng-repeat="obj in data.history_detail.list">
			                    <td ng-repeat="val in data.history_detail.fields">{{obj[val]}}</td>
			                </tr>
		                </tbody>
			        </table>
		        </div>
                <div class="clearfix">
		    		<div class="pull-left" style="line-height: 38px;margin: 5px;">
		    			共{{data.history_detail.page.pagecount}}页,{{data.history_detail.page.sumcount}}条记录
		    		</div>
					<div pagination total-items="data.history_detail.page.sumcount" ng-model="data.history_detail.page.curpage" 
						items-per-page="data.history_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
		    	</div>
			</div>
		</div>
	</div> -->
</body>
</html>