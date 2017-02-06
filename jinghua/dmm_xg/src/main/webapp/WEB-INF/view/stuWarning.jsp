<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>失联预警</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/stuWarning/controller.js"></script>
<script type="text/javascript" src="product/stuWarning/service.js"></script>
<script type="text/javascript" src="product/stuWarning/constant.js"></script>

</head>
<body ng-controller="controller">
	
<div class="xuegong-zhuti-content" >
<header>
   	<div class="pull-right">
       	<a href="" ng-click="data.advance.show=!data.advance.show"><span></span><p>高级查询</p></a>
           <i></i>
           <a href="" class="tansuo disable"><span></span><p>探索</p></a>
           <i></i>
           <a href="" class="disable"><span></span><p>导出</p></a>
       </div>
       <div class="clearfix"></div>
</header>

<div class="xuegong-zhuti-content-main">

<div ng-show="data.advance.show" class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
	<div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="true"></div>
</div>

<div class="xg-tab-mz-head">
	<a href="" ng-class="{active:obj.id==data.model.mold}" ng-repeat="obj in data.tab" ng-click="changMold(obj.id,obj)">{{obj.mc}}</a>
</div>
<div class="xuegong-zhuti-content-main xg-tab-mz-body">

<section part-modal show-modal="data.grid.mask">
	<div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone">
    	<div class="form-inline attendance-warning">
            <b class="text-blue">失联预警</b>
            <div class="input-group">
                <input type="text" class="form-control" cs-datepicker ng-model="data.abstract_date.date" on-change="changeDate()" click-id="teaching_stuWarning_datepicker"/>
                <span class="input-group-addon" id="teaching_stuWarning_datepicker"><i class="fa fa-calendar"></i></span>
            </div>
            <span href="" class="question-icon pull-right" ng-mouseover="data.tip_show=true" ng-mouseleave="data.tip_show=false">
                <a class="question-img  pull-right"></a>
                <div class="question-area-box" ng-show="data.tip_show">
                    <div class="question-area question-area-widther">
                        <img src="static/resource/images/xues-gognzuozhe/question-k.png">
		                <p><b>疑似逃课：</b>上课期间消费(餐厅、超市、澡堂、茶水房)、门禁出入</p>
		                <p><b>晚寝晚归：</b>每日记录22:30以后刷卡入寝室的学生</p>
		                <p><b>疑似未住宿：</b>【有一卡通消费数据没有门禁刷卡记录】以及【一卡通门禁都有记录但消费记录晚于刷卡记录】的学生</p>
		                <p><b>疑似不在校：</b>三天或三天以上没有一卡通消费数据，门禁数据和上网记录的学生</p>
                    </div>
                </div>
            </span>
        </div>
        <div class="clearfix"></div>
    </div>
    
    <div ng-if="!data.abstract_date.status">{{data.abstract_date.firstLoad==true ? '数据加载中...' : '没有预警数据'}}</div>
    <div class="xuegong-zhuti-content-main-tab" ng-if="data.abstract_date.status">
    	<p>{{data.abstract_date.yesterDay==data.abstract_date.date?'昨天':data.abstract_date.date}}失联预警
    		<a class="text-pink" ng-click="data.abstract_date.count==0 || abstractDetailClick(null,'rc')"><b> {{data.abstract_date.count}} </b></a>人次，
    		相比其上周平均数据环比{{data.abstract_date.amp>0 ? '上升' : '下降'}}
	    	<b ng-class="{'text-green':data.abstract_date.amp<0,'text-pink':data.abstract_date.amp>0,'text-purple':data.abstract_date.amp==0}"> 
	    	{{data.abstract_date.amp>0 ? data.abstract_date.amp : -data.abstract_date.amp}}% </b>。
    	其中，疑似逃课<a href="" class="text-pink" ng-click="data.abstract_date.skipClasses==0 || abstractDetailClick('skipClasses','rc')"><b> {{data.abstract_date.skipClasses}} </b></a>人次，
    	晚寝晚归<a href="" class="text-pink" ng-click="data.abstract_date.stayLate==0 || abstractDetailClick('stayLate','count')"><b> {{data.abstract_date.stayLate}} </b></a>人，
    	疑似未住宿<a href="" class="text-pink" ng-click="data.abstract_date.notStay==0 || abstractDetailClick('notStay','count')"><b> {{data.abstract_date.notStay}} </b></a>人，
    	疑似不在校<a href="" class="text-pink" ng-click="data.abstract_date.stayNotin==0 || abstractDetailClick('stayNotin','count')"><b> {{data.abstract_date.stayNotin}} </b></a>人</p>
    	<table class="table table-bordered xuegong-zhuti-table attendance-warning-tab">
             <thead>
            	<tr>
                	<td rowspan="2">{{data.grid.deptMc}}</td>
                    <td rowspan="2">在校生</td>
                    <td rowspan="2" ng-class="{'xg-bg-org':data.grid.order.all!=null}">预警人次
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.all=='desc'}"><i ng-click="order('all','count','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.all=='asc'}"><i ng-click="order('all','count','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td colspan="3">疑似逃课</td>
                    <td colspan="3">晚寝晚归</td>
                    <td colspan="3">疑似未住宿</td>
                    <td colspan="3">疑似不在校</td>
                    <td rowspan="2"><a href="" class="text-black" ng-class="{'fa-disable':data.grid.count_all==0}"><i title="{{data.grid.count_all==0 ? '没有预警数据，无须发送邮件' : '全部发送'}}" ng-click="data.grid.count_all==0 || sendAll(data.grid.list)" class="fa fa-send text-blue"></i></a></td>
                    <td rowspan="2"><a href="" class="text-black">
<!--                     	<i class="fa fa-upload text-blue"></i> -->
                    </a></td>
                </tr>
                <tr>
                	<td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.skipClasses[0]!=null}">人次
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.skipClasses[0]=='desc'}"><i ng-click="order('skipClasses','count','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.skipClasses[0]=='asc'}"><i ng-click="order('skipClasses','count','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.skipClasses[1]!=null}">占比
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.skipClasses[1]=='desc'}"><i ng-click="order('skipClasses','scale','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.skipClasses[1]=='asc'}"><i ng-click="order('skipClasses','scale','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.skipClasses[2]!=null}">变化
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.skipClasses[2]=='desc'}"><i ng-click="order('skipClasses','amp','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.skipClasses[2]=='asc'}"><i ng-click="order('skipClasses','amp','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.stayLate[0]!=null}">人数
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayLate[0]=='desc'}"><i ng-click="order('stayLate','count','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayLate[0]=='asc'}"><i ng-click="order('stayLate','count','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.stayLate[1]!=null}">占比
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayLate[1]=='desc'}"><i ng-click="order('stayLate','scale','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayLate[1]=='asc'}"><i ng-click="order('stayLate','scale','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.stayLate[2]!=null}">变化
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayLate[2]=='desc'}"><i ng-click="order('stayLate','amp','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayLate[2]=='asc'}"><i ng-click="order('stayLate','amp','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.notStay[0]!=null}">人数
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.notStay[0]=='desc'}"><i ng-click="order('notStay','count','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.notStay[0]=='asc'}"><i ng-click="order('notStay','count','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.notStay[1]!=null}">占比
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.notStay[1]=='desc'}"><i ng-click="order('notStay','scale','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.notStay[1]=='asc'}"><i ng-click="order('notStay','scale','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.notStay[2]!=null}">变化
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.notStay[2]=='desc'}"><i ng-click="order('notStay','amp','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.notStay[2]=='asc'}"><i ng-click="order('notStay','amp','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.stayNotin[0]!=null}">人数
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayNotin[0]=='desc'}"><i ng-click="order('stayNotin','count','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayNotin[0]=='asc'}"><i ng-click="order('stayNotin','count','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold" ng-class="{'xg-bg-org':data.grid.order.stayNotin[1]!=null}">占比
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayNotin[1]=='desc'}"><i ng-click="order('stayNotin','scale','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayNotin[1]=='asc'}"><i ng-click="order('stayNotin','scale','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                    <td class="tab-no-bold td-has-border-r" ng-class="{'xg-bg-org':data.grid.order.stayNotin[2]!=null}">变化
                    	<div class="caret-icon">
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayNotin[2]=='desc'}"><i ng-click="order('stayNotin','amp','asc')" class="fa fa-caret-up"></i></a></p>
                             <p><a href="" class="text-blue" ng-class="{'caret-disable':data.grid.order.stayNotin[2]=='asc'}"><i ng-click="order('stayNotin','amp','desc')" class="fa fa-caret-down"></i></a></p>
                        </div>
                    </td>
                </tr>
            </thead>
            <tbody>
            	<tr ng-repeat="obj in data.grid.list">
                	<td><a class="text-blue">{{obj.name}}</a></td>
                    <td> {{obj.xscount}} </td>
                    <td ng-class="{'xg-bg-org':data.grid.order.all!=null}"><a href="" ng-click="gridDetailClick(null,'count',obj)" class="text-blue">{{obj.count}}</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.skipClasses[0]!=null}"><a href="" ng-click="gridDetailClick('skipClasses','rc',obj)" class="text-blue">{{obj.skipClasses_count}}</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.skipClasses[1]!=null}"><a href="" class="text-blue">{{obj.skipClasses_scale}}%</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.skipClasses[2]!=null}"><a href="" ng-class="{'text-green':obj.skipClasses_amp<0,'text-pink':obj.skipClasses_amp>0,'text-purple':obj.skipClasses_amp==0}">{{obj.skipClasses_amp}}%</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.stayLate[0]!=null}"><a href="" ng-click="gridDetailClick('stayLate','count',obj)" class="text-blue">{{obj.stayLate_count}}</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.stayLate[1]!=null}"><a href="" class="text-blue">{{obj.stayLate_scale}}%</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.stayLate[2]!=null}"><a href="" ng-class="{'text-green':obj.stayLate_amp<0,'text-pink':obj.stayLate_amp>0,'text-purple':obj.stayLate_amp==0}">{{obj.stayLate_amp}}%</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.notStay[0]!=null}"><a href="" ng-click="gridDetailClick('notStay','count',obj)" class="text-blue">{{obj.notStay_count}}</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.notStay[1]!=null}"><a href="" class="text-blue">{{obj.notStay_scale}}%</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.notStay[2]!=null}"><a href="" title="{{obj.notStay_amp>0?'上升':(obj.notStay_amp<0?'下降':'')}}" ng-class="{'text-green':obj.notStay_amp<0,'text-pink':obj.notStay_amp>0,'text-purple':obj.notStay_amp==0}">{{obj.notStay_amp}}%</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.stayNotin[0]!=null}"><a href="" ng-click="gridDetailClick('stayNotin','count',obj)" class="text-blue">{{obj.stayNotin_count}}</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.stayNotin[1]!=null}"><a href="" class="text-blue">{{obj.stayNotin_scale}}%</a></td>
                    <td ng-class="{'xg-bg-org':data.grid.order.stayNotin[2]!=null}"><a href="" title="{{obj.stayNotin_amp>0?'上升':(obj.stayNotin_amp<0?'下降':'')}}" ng-class="{'text-green':obj.stayNotin_amp<0,'text-pink':obj.stayNotin_amp>0,'text-purple':obj.stayNotin_amp==0}">{{obj.stayNotin_amp}}%</a></td>
                    <td><a href="" class="text-black" ng-class="{'send-disable':obj.count==0,'send-succeed':obj.status==1,'send-fail':obj.status==0,'send-wait':obj.count!=0&&obj.status==null}">
                    	<span class="xg-send" style="margin-right:0px;"
                    		ng-click="obj.status==1 || send(obj)" title="{{obj.count==0 ? '没有预警数据，无须发送邮件' : (obj.status==1?'已发送':(obj.status==0?'发送失败，点击发送邮件':'未发送'+'，点击发送邮件'))}}"></span>
                    		<!-- obj.status==1 || obj.count<=0 || send(obj) -->
                    </a></td>
                    <td><a href="" class="text-black" ng-class="{'fa-disable':obj.count==0}"><i title="{{obj.count==0?'没有预警数据，无须导出':'导出'}}" ng-click="obj.count==0 || gridDetailClick(null,'count',obj)" class="fa fa-sign-out text-blue"></i></a></td>
                </tr>
            </tbody>
        </table>
        <p ng-if="data.grid.list.length>0">
        	<a ng-show="data.grid.list.length < data.grid.list_all.length" href="" ng-click="unfoldGrid()" class="text-blue"><span>展开更多</span><i class="fa fa-angle-down"></i></a>
        	<a ng-show="data.grid.list.length == data.grid.list_all.length" href="" ng-click="shrinkGrid()" class="text-blue angle-up"><span>收起</span><i class="fa fa-angle-up"></i></a>
        </p>
    </div>
</section>


<div part-modal show-modal="data.distribution_mask">
<div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone">
	<p class="text-blue"><b>失联预警分析</b></p>
 	<div class="xuegong-zhuti-dropdown-box">
       	<div class="btn-group" cg-select data="data.bzdm_xnxq" on-change="changXnXq($value,$data)" ng-model="data.model.xnxq" value-field="id" display-field="mc"></div>
    </div>
    <!-- <div class="btn-basic di-xiaofei-btn">
        <a class="on btn-blue" href="#">近五年</a>
        <a href="#" class="btn-blue">近十年</a>
    </div> -->
    <div class="btn-group-follow-text" ng-show="data.distribution_info">{{data.distribution_info}}</div>
    <div class="clearfix"></div>
</div>
  
<section class="no-bottom attendance-warning-fenbu xuegong-separate-tb">
	<div class="xuegong-separate-con attendance-warning-fenbu-one" part-modal show-modal="data.distribution_fb_mask">
      	<p class="text-blue">失联预警分布<a href="">
<!--       		<span class="pull-right time-img"></span> --><!-- 历史 -->
      	</a></p>
        <div class="row">
          	<div class="col-md-4">
<!--                 	<div class="img-box"><div class="img-box-only"> -->
<!--                 		<img src="images/attendance-warning/attendance-warning-fenbu-reason.jpg" class="center-block" alt=""> -->
<!--                 	</div></div> -->
				<div class="img-box" echart config="data.type.option"></div>
            </div>
            <div class="col-md-4">
				<div class="img-box" echart config="data.grade.option"></div>
            </div>
            <div class="col-md-4">
				<div class="img-box" echart config="data.sex.option"></div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    <div class="xuegong-separate-con attendance-warning-fenbu-bili">
    	<p class="text-blue">各{{data.dept.deptMc}}预警分布与人数占比<a href="">
<!--    		<span class="pull-right time-img"></span> --><!-- 历史 -->
    	</a></p>
        <div class="img-box" echart config="data.dept.option"></div>
    </div>
    <div class="xuegong-separate-con attendance-warning-fenbu-time">
                	<p class="text-blue">逃课时间分布
<!--                 		<a href="#"><span class="pull-right time-img"></span></a> -->
                	</p>
                    <div class="row">
                    	<div class="col-md-6">
                        	<div class="img-box"><div class="img-box-only" echart config="data.week.option"></div>
                            <p class="text-center" ng-if="data.weekShow"><i class="fa fa-warning fa-warning-normal text-pink"></i><span class="text-pink"><b>{{data.msg1}}</b></span>是逃课高发期，每天<span class="text-pink"><b>{{data.msg2}}</b></span>同样是高发期</p>
                            <p class="text-center" ng-if="!data.weekShow"><i class="fa fa-warning fa-warning-normal text-pink"></i>{{data.msg}}</p>
                            </div>
                        </div>
                        <div class="col-md-6">
                        	<div class="img-box"><div class="img-box-only" echart config="data.clas.option"></div></div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div> 
                
               <div class="xuegong-separate-con attendance-warning-fenbu-bottom">
                    <div class="row">
                    	<div class="col-md-6 attendance-warning-fenbu-position">
                        	<p class="text-blue">逃课学生位置分布
<!--                         		<a href="#"><span class="pull-right time-img"></span></a> -->
                        	</p>
                        	<div class="img-box"><div class="img-box-only" echart config="data.where.option"></div>
                            </div>
                         </div>
                        <div class="col-md-6 attendance-warning-fenbu-probability">
                        	<p class="text-blue">上午逃课学生的下午逃课概率
<!--                         		<a href="#"><span class="pull-right time-img"></span></a> -->
                        	</p>
                        	<div class="img-box"><div class="img-box-only" echart config="data.scls.option" ></div>
                            <p class="text-center" ng-if="data.weekShow"><i class="fa fa-warning fa-warning-normal text-pink"></i>{{data.sclsName}}<span class="text-pink"><b>{{data.sclsValue}}%</b></span>的概率下午逃课</p></div>
                        	<p class="text-center" ng-if="!data.weekShow"><i class="fa fa-warning fa-warning-normal text-pink"></i>{{data.msg}}</p>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div> 
</section>
</div>

</div>
	<%-- 表格 详情 --%>
	<div modal-form config="data.grid_detail.formConfig"></div>
	    
</div>
</div>

</body>
</html>