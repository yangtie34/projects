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
<title>生源地分析</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/stuFrom/controller.js"></script>
<script type="text/javascript" src="product/stuFrom/service.js"></script>

<style type="text/css">
.inline {
	display: inline;
}
</style>

</head>
<body ng-controller="controller">
	<div class="xuegong-zhuti-content" >
		<header>
		<div class="pull-right">
			<a href="" ng-click="data.advance.show=!data.advance.show"><span></span><p>高级查询</p></a><i></i>
			<a href="" class="tansuo disable"><span></span><p>探索</p></a> <i></i> 
			<a href="" class="disable"><span></span><p>导出</p></a>
		</div>
		<div class="clearfix"></div>
		</header>
		<div class="xuegong-zhuti-content-main">
			<div ng-show="data.advance.show"
				class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
				<div cg-custom-comm source="data.advance.source"
					on-change="advanceChange($data)" expand="true" no-border="true"></div>
			</div>

			<div
				class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source">
				<div class="xuegong-zhuti-dropdown-box">
					<div class="btn-group" cg-select data="selval"
						on-change="fselect($value,$data)" ng-model="fSelVal"
						value-field="id" display-field="mc"></div>
				</div>
				<b class="text-gray"></b>
				<div class="xuegong-zhuti-dropdown-box">
					<div class="btn-group" cg-select data="selval1"
						on-change="tselect($value,$data)" ng-model="tSelVal"
						value-field="id" display-field="mc"></div>
				</div>
				<div class="btn-basic">
					<a href="" ng-if="yearLen>=1" ng-click="data.show1||yearClick('1')"
						ng-class="{'btn-blue': !data.show1, 'on btn-blue cursor-default': data.show1}">今年</a>
					<a href="" ng-if="yearLen>=2" ng-click="data.show4||yearClick('4')"
						ng-class="{'btn-blue': !data.show4, 'on btn-blue cursor-default': data.show4}">去年</a>
					<a href="" ng-if="yearLen>=5" ng-click="data.show2||yearClick('2')"
						ng-class="{'btn-blue': !data.show2, 'on btn-blue cursor-default': data.show2}">近五年</a>
					<a href="" ng-if="yearLen>=10" ng-click="data.show3||yearClick('3')"
						ng-class="{'btn-blue': !data.show3, 'on btn-blue cursor-default': data.show3}">近十年</a>
				</div>
				<div class="clearfix"></div>
			</div>
			<section class="xuegong-neirong no-bottom student-source-con">
			<div class="student-source-con-main">
				<div class="student-source-con-main-top-sections">
					<a href="" ng-class="{'on':eduid==x.id}" ng-repeat="x in data.edu"
						ng-click="eduSet(x.id)">{{x.mc}}</a> <span class="pull-right"></span>
				</div>
				<div class="student-source-con-main-content">
					<div class="student-source-map-main">
						<div class="clearfix">
							<p ng-if="(tSelVal-fSelVal+1)>1" class="inline-block">
								近{{tSelVal-fSelVal+1}}年，在{{data.mapt}}共招学生<b><a href = "" ng-class="{'text-green':data.fromsum>0,'text-green cursor-default':data.fromsum==0}" ng-click="data.fromsum==0||stuListClick('xsmd',data.mapt+'学生名单')">{{data.fromsum}}</a></b>人，<u ng-if="data.sxq > 0">其中市辖区<a href="" ng-click="stuListClick('xzqh',data.mapt+'市辖区学生名单',data.sxqid)" class="text-green">{{data.sxq}}</a>人</u>{{data.sxq >0?'，':''}}{{tSelVal}}年招生数较{{fSelVal}}年增长<b><b
									class="text-green">{{data.fromsub}}</b></b>人，年均增长率是<b><b
									class="text-green">{{data.fromavg}}%</b></b>。
							</p>
							<p ng-if="(tSelVal-fSelVal+1)==1">
								{{tSelVal}}年，在{{data.mapt}}共招学生<b><a href = ""class="text-green" ng-click="stuListClick('xsmd',data.mapt+'学生名单')">{{data.fromsum}}</a></b>人，<u ng-if="data.sxq > 0">其中市辖区<a href="" class="text-green" ng-click="stuListClick('xzqh',data.mapt+'市辖区学生名单',data.sxqid)">{{data.sxq}}</a>人</u>{{data.sxq >0?'，':''}}<span ng-if ="data.fromavg1 < 0 ">总人数比去年减少 <b class="text-pink">{{data.fromavg}}%</b>。</span>
								<span ng-if ="data.fromavg1 >= 0 ">总人数比去年增长<b class="text-green">{{data.fromavg}}%</b>。</span>
								
							</p>
							<span ng-if="(tSelVal-fSelVal+1)>1" class="message-icon">
								<a href="" class="message-img"></a>
								<div class="question-area-box question-area-box-r message-box question-area-box-top">
									<div class="question-area message-area-width">
										<img
											src="static/resource/images/xues-gognzuozhe/question-k-r.png">
										<p><b>年均增长率：</b><br/>((第b年人数-第(b-1)年人数)/第(b-1)年人数)+(第b-1年人数-第(b-2)年人数)/第(b-2)年人数)+...+(第a年人数-第(a-1)年人数)/第(a-1)年人数))/总年数。<br/>(a为开始年，b为结束年，总年数为(b-a+1)年)</p>
									</div>
								</div>
							</span>
						</div>
						<div>
							<div class="pull-left student-source-map-l">
								<div class="img-box">
									<div class="img-box-only">
										<a class="go-back text-blue" style="right:90px;top:3px" href="" ng-click="pageUp()"
											ng-if="data.level!='1'"><img
											src="static/resource/css/image/fanhui2.png">&nbsp;返回上一级</a>
										<div echart config="data.distribute.fromCfg"
											ec-click="mapClick(param)" class="center-block"></div>
									</div>
								</div>
							</div>
							<div class="pull-right student-source-map">
								<div class="img-box">
									<div class="img-box-only">
										<div style="height: 350px" echart
											config="data.distribute.fromPieCfg" class="center-block"></div>
									</div>
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					<div class="student-source-paiming">
						<div class="sf-renshu-paiming pull-left">
							<div class="sf-renshu-paiming-tit">
								<b ng-if="data.level=='1'" class="text-blue">各省份人数排名</b> <b
									ng-if="data.level=='2'" class="text-blue">各地区人数排名</b> <b
									ng-if="data.level=='3'" class="text-blue">各县镇人数排名</b> <span
									class="pull-right"> 
									<a href="" class="text-blue"  ng-click="getExportData('from')"><i class="xg-daochu"></i>导出</a>
									
									<a href="" ng-class="{'text-blue':vm1.page.index>1,'text-hui':vm1.page.index==1}"
									ng-click="vm1.page.index==1||PageUp1()"><i class="fa fa-angle-left fa-2x"></i></a>
									
									<a href=""ng-click="(vm1.page.total <= vm1.page.index*vm1.page.size)||PageDown1()"
									ng-class="{'text-blue':(vm1.page.total > vm1.page.index*vm1.page.size),'text-hui':(vm1.page.total <= vm1.page.index*vm1.page.size)}"><i
									class="fa fa-angle-right fa-2x"></i></a>
								</span>
								<div class="clearfix"></div>
							</div>
							<div class="paiming-tab">
								<table class="table">
									<thead>
										<tr>
											<td>&nbsp;</td>
											<td ng-if="data.level=='1'" class="text-blue-purple">省份</td>
											<td ng-if="data.level=='2'" class="text-blue-purple">地区</td>
											<td ng-if="data.level=='3'" class="text-blue-purple">县镇</td>
											<td class="text-blue-purple">人数</td>
											<td class="text-blue-purple">占比</td>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="zl in vm1.items">
											<td><b>{{zl.r}}</b></td>
											<td>{{zl.name}}</td>
											<td>{{zl.value}}人</td>
											<td >
											<a  href=""class="text-green" title="点击查看历年{{zl.name}}来校人数变化" ng-click="lvClick(zl.id,zl.name)">{{zl.value/data.fromsum*100|number:2}}%</a></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div ng-if="vm1.items.length==0" class="no-date">暂无数据...</div>
						</div>
						<div class="zengfu-paiming pull-left">
							<div class="sf-renshu-paiming-tit">
								<b ng-if="data.level=='1'" class="text-blue">各省份平均增幅排名</b> <b
									ng-if="data.level=='2'" class="text-blue">各地区平均增幅排名</b> <b
									ng-if="data.level=='3'" class="text-blue">各县镇平均增幅排名</b> <span
									class="pull-right"> 
									<a href="" class="text-blue" ng-click="getExportData('other')"><i class="xg-daochu"></i>导出</a>
									<a href=""
									ng-class="{'text-blue':vm2.page.index>1,'text-hui':vm2.page.index==1}"
									ng-click="vm2.page.index==1||PageUp2()"><i
										class="fa fa-angle-left fa-2x"></i></a> <a href=""
									ng-click="(vm2.page.total <= vm2.page.index*vm2.page.size)||PageDown2()"
									ng-class="{'text-blue':(vm2.page.total > vm2.page.index*vm2.page.size),'text-hui':(vm2.page.total <= vm2.page.index*vm2.page.size)}"><i
										class="fa fa-angle-right fa-2x"></i></a>
								</span>
								<div class="clearfix"></div>
							</div>
							<div class="paiming-tab">
								<table class="table">
									<thead>
										<tr>
											<td>&nbsp;</td>
											<td ng-if="data.level=='1'" class="text-blue-purple">省份</td>
											<td ng-if="data.level=='2'" class="text-blue-purple">地区</td>
											<td ng-if="data.level=='3'" class="text-blue-purple">县镇</td>
											<td class="text-blue-purple">平均增长<span
												class="message-icon"> <a href="" class="message-img"></a>
													<div
														class="question-area-box message-box question-area-box-top">
														<div class="question-area message-area-width">
															<img src="static/resource/images/xues-gognzuozhe/question-k.png">
															<p><b>平均增长：</b><br/>((第b年人数-第(b-1)年人数)+(第(b-1)年人数-第(b-2)年人数)+..+(第a年人数-第(a-1)年人数))/总年数。<br/>(a为开始年，b为结束年，总年数为(b-a+1)年)</p>
														</div>
													</div>
											</span></td>
											<td class="text-blue-purple">平均增幅<span
												class="message-icon"> <a href="" class="message-img"></a>
													<div
														class="question-area-box message-box question-area-box-top">
														<div class="question-area message-area-width">
															<img src="static/resource/images/xues-gognzuozhe/question-k.png">
															<p><b>平均增幅：</b><br/>((第b年人数-第(b-1)年人数)/第(b-1)年人数)+(第b-1年人数-第(b-2)年人数)/第(b-2)年人数)+...+(第a年人数-第(a-1)年人数)/第(a-1)年人数))/总年数。<br/>(a为开始年，b为结束年，总年数为(b-a+1)年)</p>
														</div>
													</div>
											</span></td>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="zzl in data.sydlist">
											<td><b>{{((vm2.page.index-1)*vm2.page.size)+$index+1}}</b></td>
											<td>{{zzl.name}}</td>
											<td>{{zzl.val1|number:0}}人</td>
											<td ng-if="zzl.bs==1"><a href=""
												class="text-green" ng-click="showLine(zzl.id,zzl.name,zzl.bs)"title="点击查看{{zzl.name}}历年人数变化">{{zzl.val2}}%</a></td>
											<td ng-if="zzl.bs==0"><a href=""
												class="text-green" title="点击查看{{zzl.name}}历年人数变化" ng-click="showLine(zzl.id,zzl.name,zzl.bs)">数据异常</a></td>
										</tr>
									</tbody>
								</table>
							</div>
							<div ng-if="vm2.page.total==0" class="no-date">暂无数据...</div>
						</div>
						<div class="clearfix"></div>
					</div>
					<div class="linian-num-change">
						<div class="linian-num-change-box pull-left">
							<div class="sf-renshu-paiming-tit">
								<b class="text-blue">历年{{data.mapt}}同比增长和同比增幅变化趋势</b> <span
									class="message-icon pull-right rig-top"> <a href=""
									class="message-img"></a>
									<div
										class="question-area-box message-box question-area-box-top">
										<div class="question-area message-area-width">
											<img
												src="static/resource/images/xues-gognzuozhe/question-k.png">
											<p><b>同比增长：</b><br/>第n年人数-第(n-1)年人数。<br/><b>同比增幅：</b><br/>(第n年人数-第(n-1)年人数)/(第(n-1)年人数)。</p>
										</div>
									</div>
								</span>

							</div>
							<div echart config="data.distribute.fromLineCfg" class="img-box">
							</div>
						</div>
						<div class="biye-yuanxiao pull-left">
							<div class="sf-renshu-paiming-tit">
								<b class="text-blue">毕业学校</b> <span class="pull-right"> <a
									href=""
									ng-class="{'text-blue':vm3.page.index>1,'text-hui':vm3.page.index==1}"
									ng-click="vm3.page.index==1||PageUp3()"><i
										class="fa fa-angle-left fa-2x"></i></a> <a href=""
									ng-click="(vm3.page.total <= vm3.page.index*vm3.page.size)||PageDown3()"
									ng-class="{'text-blue':(vm3.page.total > vm3.page.index*vm3.page.size),'text-hui':(vm3.page.total <= vm3.page.index*vm3.page.size)}"><i
										class="fa fa-angle-right fa-2x"></i></a>
								</span>
								<div class="clearfix"></div>
							</div>
							<table
								class="table table-bordered xuegong-zhuti-table biyeyx-table">
								<thead>
									<tr ng-class="{'last-child':vm3.items.length==0}">
										<td>毕业学校</td>
										<td>人数
											<div class="caret-icon">
												<p>
													<a href="" ng-click="tabSort(true)" ng-if="!order"
														class="text-blue"><i class="fa fa-caret-up"></i></a>
												</p>
												<p>
													<a href="" ng-click="tabSort(false)" ng-if="order"
														class="text-blue"><i class="fa fa-caret-down"></i></a>
												</p>
											</div>
										</td>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="xs in vm3.items">
										<td>{{xs.name}}</td>
										<td>{{xs.value}}</td>
										<!-- 	<td>580</td> -->
									</tr>
								</tbody>
							</table>
							<div ng-if="vm3.items.length==0" class="no-date">暂无数据...</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			</section>
		</div>
	</div>
	<div cs-window show="data.lineshow" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.linename}}历年来校人数变化</h3>
		        <a href="" class="popup-form-close" ng-click="data.lineshow=false">
		        	<!-- <img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <b style="margin-left:28px" ng-if="data.bs==0">数据异常：</b>{{data.bs==0?'统计周期内，如果有某一年该地区的来校人数为0，则无法计算平均增幅，即数据异常。':''}}
		 	<div echart config="data.distribute.countLineCfg"
					style="height: 400px; width: 500px; margin: 0 auto"></div>
		</div>
	</div>
	<div cs-window show="data.lvShow" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">历年{{data.lvName}}来校人数变化</h3>
		        <a href="" class="popup-form-close" ng-click="data.lvShow=false">
		        	<!-- <img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		 	<div echart config="data.countLvCfg"
					style="height: 400px; width: 500px; margin: 0 auto"></div>
		</div>
	</div>
	<!-- <div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">{{data.linename}}历年人数变化</h4>
				</div>
				<div echart config="data.distribute.countLineCfg"
					style="height: 400px; width: 500px; margin: 0 auto"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div> -->
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
		            	<a href="" class="popup-form-export btn btn-default" ng-click="stuListDown()">
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
	</div> -->
</body>
</html>