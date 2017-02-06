<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成绩分析</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scoreTea/controller.js"></script>
<script type="text/javascript" src="product/scoreTea/service.js"></script>
<style type="text/css">
.yc {
	font-weight: bold;
}
</style>
</head>
<body ng-controller="controller">
<div class="ss-mark-main" >
    	<div class="header">
            <header class="header-tit">
                <b>成绩分析（任课教师）</b>
            </header>
             	<div ng-show="!data.advance.show" ng-click="advanceShow()"
						title="高级查询" class="bak-orange" align="center">
						<a href="" class="sets"></a>
					</div>

					<div ng-show="data.advance.show"
						class="header-con last-performance" style="margin-top: 18px;">
						<div cg-custom-comm source="data.advance.source"
							on-change="advanceChange($data)" expand="true" noborder="false"></div>
					</div>
					<div ng-show="data.advance.show" ng-click="advanceShow()"
						title="收起" class="bak-orange bak-orange-up" align="center">
						<a href="#" class="sets up"><i class="fa fa-angle-up"></i></a>
					</div>
        </div>
        
        <div class="ss-mark-tab-box no-bottom">
           <div class="cg-select-dropdown btn-group dis-inb mar-r-6" style="width:150px" cg-select data="data.eduList"
			on-change="eduSelect($value)" ng-model="data.edu"
			value-field="id" display-field="mc"></div>
              <div class="jx-fz-12 postion-r jx-mar-b-10">
            	<div class="jx-radio-box jx-has-more-r" >
                    <div ng-class="{'radio radio-inline jx-radio' : x.order == $index,'radio radio-inline jx-radio radio-l-widther': x.order != $index}" ng-repeat = " x in data.timeList" ng-click = "data.time == x.id ||timeSelect(x.id)" >
                        <input ng-if="x.id == data.time"type="radio" name="xueqi" value="" id="{{x.id}}" checked><label for="{{x.id}}">{{x.mc}}</label>
                        <input ng-if="x.id != data.time"type="radio" name="xueqi" value="" id="{{x.id}}" >
                    </div>
                </div>
                <a href="" class="more-r jx-more-org" ng-click = "loadMore()" ng-if="!data.timeshow && data.allTimeList.length > 6">更多 >></a>
             <a href="" class="more-r jx-more-org" ng-click = "loadMore()" ng-if="data.timeshow"><< 收起</a>
            </div>
        </div>
        	
            
        <div class="text-center yc-tab-head"><a ng-repeat = " y in data.courseList" ng-click ="data.course == y.id ||courseSelect(y.id,y.mc)" ng-class= "{'active':data.course == y.id}"href="">{{y.mc}}</a></div>
        <div class="performance-change no-top">
            <div><span class="jx-note-green">我代课的教学班有{{data.classCount}}个 {{data.isgd ?"":"，共"+data.stuCount+"人"}}。</span></div>
            <div class="jx-mar-b-10">
                <span  class="dis-inb mar-r-6 jx-fz-16 jx-color-org">成绩概况</span>
            </div>
            <div class="yc-tab">
            	<div class="clearfix"><div class="yc-tab-head-second pull-left"><a href="" ng-repeat="z in data.natureList" ng-class="{'on':z.id == data.nature}" ng-click = "data.nature == z.id ||natureSelect(z.id,z.mc)">{{z.mc}}</a></div><div class="jx-tab-tr pull-right" ng-if="data.isgd && data.tabList != null && data.tabList.length >0 "><a href="" ng-click="reportClick()" style = "font-size:16px" class="jx-tab-tr-txt text-green">课程考核分析报告
            	</a></div></div>
            	<table class="table table-bordered">
                	<thead>
                    	<tr>
                        	<td rowspan="2">班级</td>
                            <td rowspan="2">{{data.isgd?'上课人数':'人数'}}</td>
                            <td rowspan="2">有成绩人数</td>
                            <td rowspan="2">平均<br>成绩</td>
                            <td colspan="2" ng-repeat ="sb in data.thList">{{sb.mc}}</td>
                        </tr>
                        <tr>
                            <td ng-repeat = " ssjb in data.fthList" >
                            <p ng-if="$index % 2 ==0">人次</p>
                            <p ng-if = "$index % 2 == 1">占比%</p></td>
                        </tr>
                    </thead>
                    <tbody>
                    <tr ng-class="{'yc':jb.mc == '合计'}" ng-repeat= "jb in data.tabList">
                        	<td >{{jb.mc}}</td>
                            <td >
                            <p  ng-if="jb.count>0" >{{jb.count}}</p>
                            <p ng-if="jb.count == 0">{{jb.count}}</p>
                            </td>
                            <td>{{jb.scount}}</td>
                            <td>{{jb.avg}}</td>
                            <td ng-repeat = " ddb in jb.list" >
                            <p  ng-if = "$index % 2 == 0 && ddb.value > 0">{{ddb.value}}</p>
                            <p ng-if = "$index % 2 == 0 && ddb.value == 0" >{{ddb.value}}</p>
                            <p ng-if = "$index % 2 == 1">{{ddb.value|number:2}}%</p></td>
                        </tr>
                    </tbody>
                </table>
            </div><!--yc-tab-->
            <div class="jx-fz-12" ng-if ="data.tabList != null && data.tabList.length >0">同年级同课程属性&nbsp;&nbsp;学生成绩<a href="" class="jx-more-org" ng-click="tbClick()">查看 >></a></div>
        </div><!--performance-change-->
        
        
        <div class="performance-change">
            <div class="yc-tab" style="margin-top:0;">
            	<table class="table table-bordered">
                	<thead>
                    	<tr>
                        	<td>班级</td>
                            <td>{{data.isgd?'上课人数':'人数'}}</td>
                             <td>有成绩人数</td>
                            <td>平均成绩</td>
                            <td>最高成绩</td>
                            <td>最低成绩</td>
                            <td>中位数</td>
                            <td>众数</td>
                            <td>标准差</td>
                            <td>偏态值</td>
                            <td>区分度</td>
                            <td>效度</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-class="{'yc':jb2.mc == '合计'}" ng-repeat= "jb2 in data.tabList">
                        	<td>{{jb2.mc}}</td>
                            <td>{{jb2.count}}</td>
                            <td>{{jb2.scount}}</td>
                            <td>{{jb2.avg}}</td>
                            <td>{{jb2.max}}</td>
                            <td>{{jb2.min}}</td>
                            <td>{{jb2.mid}}</td>
                            <td>{{jb2.mode}}</td>
                            <td>{{jb2.bzc}}</td>
                            <td>{{jb2.ptz}}</td>
                            <td>{{jb2.qfd}}</td>
                            <td>{{jb2.xd}}</td>
                        </tr>
                    </tbody>
                </table>
            </div><!--yc-tab-->
             <div class="jx-fz-12" ng-if ="data.tabList != null && data.tabList.length >0">同年级同课程属性&nbsp;&nbsp;学生指标<a href="" class="jx-more-org" ng-click="tb2Click()">查看 >></a></div>
        </div><!--performance-change-->
        
        
        
        <div class="performance-change no-border">
        	<div class="jx-mar-b-10">
                <span  class="dis-inb mar-r-6 jx-fz-16 jx-color-org">成绩分布</span>
            </div>
            <div class="text-center" echart config ="data.distribute.barCfg"></div>
        </div><!--performance-change-->
    </div>
        <div cs-window show="data.tbshow" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px;width:1000px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">同年级同课程同课程属性学生成绩</h3>
		        <a href="" class="popup-form-close" ng-click="data.tbshow=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix">
		    	<div class="table-box">
			    <table class="table table-bordered popup-form-table">
                	<thead>
                    	<tr>
                        	<td rowspan="2">班级</td>
                            <td rowspan="2">{{data.isgd?'上课人数':'人数'}}</td>
                            <td rowspan="2">有成绩人数</td>
                            <td rowspan="2">平均<br>成绩</td>
                            <td colspan="2" ng-repeat ="dsb in data.thList">{{dsb.mc}}</td>
                        </tr>
                        <tr>
                        	<td ng-repeat = " sjb in data.fthList" >
                        	 <p ng-if = "$index % 2 == 0">人次</p>
                            <p ng-if = "$index % 2 == 1">占比%</p></td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-repeat= "ssb in data.sameList">
                        	<td>{{ssb.mc}}</td>
                            <td>{{ssb.count}}</td>
                            <td>{{ssb.scount}}</td>
                            <td>{{ssb.avg|number:2}}</td>
                            <td ng-repeat = " dddb in ssb.list">
                             <p ng-if = "$index % 2 == 0">{{dddb.value}}</p>
                            <p ng-if = "$index % 2 == 1">{{dddb.value}}%</p></td>
                        </tr>
                    </tbody>
                </table>
		        </div>
			</div>
		</div>
	</div>
	    <div cs-window show="data.tb2show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px;width:1000px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">同年级同课程同课程属性学生成绩</h3>
		        <a href="" class="popup-form-close" ng-click="data.tb2show=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix">
		    	<div class="table-box">
			    	<table class="table table-bordered popup-form-table">
                	<thead>
                    	<tr>
                        	<td>班级</td>
                            <td>{{data.isgd?'上课人数':'人数'}}</td>
                            <td>有成绩人数</td>
                            <td>平均成绩</td>
                            <td>最高成绩</td>
                            <td>最低成绩</td>
                            <td>中位数</td>
                            <td>众数</td>
                            <td>标准差</td>
                            <td>偏态值</td>
                            <td>区分度</td>
                            <td>效度</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr  ng-repeat= "jb3 in data.sameList">
                        	<td>{{jb3.mc}}</td>
                            <td>{{jb3.count}}</td>
                            <td>{{jb3.scount}}</td>
                            <td>{{jb3.avg}}</td>
                            <td>{{jb3.max}}</td>
                            <td>{{jb3.min}}</td>
                            <td>{{jb3.mid}}</td>
                            <td>{{jb3.mode}}</td>
                            <td>{{jb3.bzc}}</td>
                            <td>{{jb3.ptz}}</td>
                            <td>{{jb3.qfd}}</td>
                            <td>{{jb3.xd}}</td>
                        </tr>
                    </tbody>
                </table>
		        </div>
			</div>
		</div>
	</div>
	
	     <div cs-window show="data.reportShow" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px;width:1000px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.courseName}}{{data.nature == null ? "":"（"+data.natureName+"）"}}课程分析报告</h3>
		        <a href="" class="popup-form-close" ng-click="data.reportShow=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix">
		    	<div class="table-box">
            	<table class="table table-bordered td-padlr-10 popup-form-table" >
                	<thead>
                    	<tr>
                        	<td>班级</td>
                            <td ng-if="data.nature != null">课程属性</td>
                            <td>应考人数</td>
                            <td></td>
                            <td></td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-repeat="zx in data.reportList">
                        	<td>{{data.tabList[$index].mc}}</td>
                            <td ng-if="data.nature != null">{{data.natureName}}</td>
                            <td>{{zx.all}}</td>
                            <td><a href="" class="text-blue" ng-click="dyClick(zx.id,zx.mc,true)">打印</a></td>
                            <td><a href="" class="text-green" ng-click="dyClick(zx.id,zx.mc,false)">预览</a></td>
                        </tr>
                    </tbody>
                </table>
		        </div>
			</div>
		</div>
	</div>

<!--  <div class="jx-tc" style="top:500px;width:500px;display:none">
        	<div class="jx-tc-in">
            	<h5 class="jx-fz-16 text-green"></h5>
                
            </div>
        </div> -->
<div cs-window show="data.report2Show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px;width:400px">
			<div class="popup-form-head clearfix">
		    	<h3 style="overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    width: 300px;" class="popup-form-title">{{data.courseName}}（{{data.nature == null ?"":data.natureName+"、"}}{{data.classmc}}）课程分析报告</h3>
		        <a href="" class="popup-form-close" ng-click="data.report2Show=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix" >
		    <div class="table-box"  style="min-width:360px;"><form name="myForm">
		    	<div class="clearfix jx-form-group" style="margin-top:20px">
                	<div class="pull-left wid-100"><label for="">应考人数：</label></div>
                    <div class="pull-left jx-exam-num wid-90">{{data.reportMap.all}}</div>
                </div>
                <div class="clearfix jx-form-group">
                	<div class="pull-left wid-100"><label for="">缺考人数：</label></div>
                    <div class="pull-left wid-90">
                    <input  type="text"  name ="qkr" ng-pattern="/^[0-9]\d*$/" ng-model="data.qkCount" class="jx-input form-control dis-inb input-sm">
<!--                      <input ng-if="data.yzzt"  type="text" ng-model="data.qkCount" class="jx-input form-control dis-inb input-sm" disabled> --></div>
                      <span ng-if="myForm.qkr.$invalid||data.qkCount==''" style="text-align: center; color:#ff0000; margin-left:10px; display:inline-block;">请输入人数</span> 
                </div>
                <div class="clearfix jx-form-group">
                	<div class="pull-left wid-100"><label for="">缓考人数：</label></div>
                    <div class="pull-left wid-90">
                    <input   type="text"  name ="hkr" ng-pattern="/^[0-9]\d*$/" ng-model="data.hkCount" class="jx-input form-control dis-inb input-sm">
<!--                     <input ng-if="data.yzzt"  type="text" ng-model="data.hkCount" class="jx-input form-control dis-inb input-sm" disabled> -->  </div>
                    <div ng-if="myForm.hkr.$invalid||data.hkCount==''" style="text-align: center; color:#ff0000; margin-left:10px; display:inline-block;">请输入人数</div> 
                </div>
                <div class="clearfix jx-form-group">
                	<div class="pull-left wid-100"><label for="">违纪人数：</label></div>
                    <div class="pull-left wid-90"><input type="text"  name = "wjr" ng-pattern="/^[0-9]\d*$/"  ng-model="data.wjCount" class="jx-input form-control dis-inb input-sm">
                    <!--   <input ng-if="data.yzzt"  type="text" ng-model="data.wjCount" class="jx-input form-control dis-inb input-sm" disabled>--></div> 
                       <div ng-if="myForm.wjr.$invalid||data.wjCount==''" style="text-align: center; color:#ff0000; margin-left:10px; display:inline-block;">请输入人数</div> 
                </div>
                <div class="clearfix jx-form-group">
                	<div class="pull-left wid-100"><label for="">题目总数：</label></div>
                    <div class="pull-left wid-90"><input type="text" ng-pattern="/^[1-9][0-9]{0,1}$/" name="tms" ng-model="data.tmCount" class="jx-input form-control dis-inb input-sm"></div>
                     <div ng-if="myForm.tms.$invalid ||data.tmCount == 0||data.tmcount=='' " style="text-align: center; color:#ff0000; margin-left:10px; display:inline-block;">请输入题目数量</div>
                </div>
                <div class="text-center"><a href="" class="zsbm-apply-btn confirm" ng-click="yzClick()">确认</a></div>
                </form>
              <!--   <div ng-if="data.yzjg == 0" style="color: #ff0000;
    margin-bottom: 10px;
    text-align: center;">请输入正确的缺考、缓考、违纪人数！</div> -->
                </div>
			</div>
		</div>
	</div>	
  <div ng-show="data.ylshow" id="dy">
  <div style="width:720px;height:850px;line-height:22px; margin:15px 20px 25px 17px;">
  <br><br>
  <h4 style="text-align:center;">
  	<span style="vertical-align:middle; font-size:22px; margin-left:75px; margin-right:25px;">课程考核分析与总结报告</span></h4>
  <table width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000" >
    <tr height="30">
      <td rowspan="4" height="76" width="72">课<br />
        程<br />
        简<br />
        况</td>
      <td height="30" width="72">课程名称</td>
      <td colspan="3" height="30" width="300">{{data.courseName}}</td>
      <td height="30" width="72">课程代码</td>
      <td height="30" width="150">{{data.course}}</td>
    </tr>
    <tr height="30">
      <td height="30">考试方式</td>
      <td colspan="3" height="30">
        <input style="vertical-align:middle" type="checkbox" ng-checked = "data.kslxS1"/>
        <label style="vertical-align:middle; margin-right:10px;">开卷</label>
        <input type="checkbox" style="vertical-align:middle;" ng-checked = "data.kslxS2" /> 
        <label style="vertical-align:middle;margin-right:10px;" >闭卷</label>
        <label style="vertical-align:middle;">其他：{{data.qt}}</label>
      </td>
      <td height="30" width="72">课程性质</td>
     <td height="30"><input style="vertical-align:middle" type="checkbox" ng-checked = "data.kcxxS1"/>
        <label style="vertical-align:middle;  margin-right:10px;">选修</label>
        <input type="checkbox" style="vertical-align:middle;" ng-checked = "data.kcxxS2" /> 
        <label style="vertical-align:middle;margin-right:10px;">必修</label></td>
    </tr>
    <tr height="30">
      <td height="30" width="72">开课学院</td>
      <td height="30" width="220">{{data.dydata.name.dept}}</td>
      <td height="30" width="40">学分</td>
      <td height="30" width="40">{{data.dydata.xf}}</td>
      <td height="30" width="72">任课教师</td>
      <td height="30">{{data.dydata.tea.mc}}</td>
    </tr>
    <tr height="30">
      <td height="30" width="72">专业班级</td>
      <td height="30" colspan="5">{{data.dydata.name.cname}}</td>
    </tr>
  </table>
  <table width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000" >
    <tr height="30">
      <td rowspan="2" height="38" width="100">考试总体情况</td>
      <td height="30" width="81">应考人数</td>
      <td height="30" width="81">缺考人数</td>
      <td height="30" width="81">缓考人数</td>
      <td height="30" width="81">实考人数</td>
      <td height="30" width="81">违纪人数</td>
      <td height="30" width="81">最高成绩</td>
      <td height="30" width="81">最低成绩</td>
      <td height="30" width="81">平均成绩</td>
    </tr>
    <tr height="30">
      <td height="30">{{data.reportMap.all}}</td>
      <td height="30">{{data.reportMap.qk}}</td>
      <td height="30">{{data.reportMap.hk}}</td>
      <td height="30">{{data.reportMap.all-data.reportMap.qk-data.reportMap.hk-data.reportMap.wj}}</td>
      <td height="30">{{data.reportMap.wj}}</td>
      <td height="30">{{data.dycj.max}}</td>
      <td height="30">{{data.dycj.min}}</td>
      <td height="30">{{data.dycj.avg}}</td>
    </tr>
  </table>
  <table  width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000">
    <tr>
      <td rowspan="3" height="57" width="100">考试成绩分布</td>
      <td height="30" width="100">分数段</td>
      <td height="30" width="92" ng-repeat ="dsb1 in data.thList">{{dsb1.mc}}</td>
    </tr>
    <tr height="30">
      <td height="30" >人数</td>
      <td height="30" ng-repeat="zzz in data.dyCount">{{zzz.value}}</td>
    </tr>
    <tr height="30">
      <td height="30" >所占比例</td>
      <td height="30" ng-repeat="zzzz in data.dyScale">{{zzzz.value|number:2}}</td>
    </tr>
    <tr height="30">
      <td height="30" >标准差</td>
      <td height="30">{{data.dycj.bzc}}</td>
      <td height="30" >偏态值</td>
      <td height="30">{{data.dycj.ptz}}</td>
      <td height="30" >区分度</td>
      <td height="30">{{data.dycj.qfd}}</td>
      <td height="30" >效度</td>
      <td height="30">{{data.dycj.xd}}</td>
    </tr>
  </table>
  <table width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000">
    <tr>
      <td><div style="text-align:left;padding-left:10px; position:relative;">
      		<br>
          <h3 style="font-size: 16px;color: #000;">一、命题分析</h3>
          <p style="margin:3px 0;line-height: 22px; height:150px; text-indent:24px;">{{data.oneS}}</p>
         
          <h3 style="font-size: 16px;color: #000;">二、问卷过程中发现学生答题存在的共性问题与知识掌握情况</h3>
          <p style="margin:3px 0;line-height: 22px;height:150px; text-indent:24px;">{{data.twoS}}</p>
<!--           <p style="margin:3px 0;line-height: 22px; text-indent:24px;">典型出错点分析：选择提第几题正确答案是 ，答题错误绝大数选 ；</p>
          <p style="margin:3px 0;line-height: 22px; text-indent:24px;">知识掌握情况说明：根据试卷难度分析情况和成绩分布，分析学生对应知应会内容掌握程度；针对学生失分和得分较多的题目，分析其失分和得分的原因；</p> -->
          <h3 style="font-size: 16px;color: #000;">三、试卷反映教学中存在的问题及改进措施</h3>
          <p style="margin:3px 0;line-height: 22px;height:210px; text-indent:24px;">{{data.threeS}}</p>
         <p ng-if="!data.fourShow && data.fourS == '' && data.fiveS =='' " style="font-size:16px;position:absolute; bottom:25px; left:25px;">  分析人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	系（教研室）主任签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	填表时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日 </p>          
          </div>
  	  </td> 
    </tr>
  
  </table>
</div><!--打印样式-->
<div ng-if="data.fourShow || data.fourS != '' || data.fiveS !='' " style="width:720px;height:1050px; position:relative; line-height:22px; margin:25px 20px 25px 17px;padding:15px 10px;border:1px solid black;">
  		<h3 style="font-size: 16px;color: #000;">四、其他</h3>
          <p style="text-indent:24px;">例如：课程支撑毕业要求达成度评价 </p>
          <div style="margin:0; padding-left:15px;">
            <div style="padding:10px 0;">1. 课程支撑的毕业要求指标点</div>
             <p style="margin:3px 0;line-height: 22px; text-indent:24px;">{{data.fourS}}</p>
            <div style="padding:10px 0; width:675px;">2. 每道题实际分数和平均得分统计
              <table ng-repeat="xy in data.tmList" width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin:20px 0 0; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000">
                <tr>
                  <td  height="30" width="110">题号</td>
                  <td ng-repeat="yza23 in xy.th.th" width="70">{{yza23}}</td>
                </tr>
                <tr>
                  <td  height="30">题型</td>
                  <td ng-repeat="yzb23 in xy.tx.tx ">{{yzb23.mc}}</td>
                </tr>
                <tr>
                  <td  height="30">题分</td>
                 <td ng-repeat="yzc23 in xy.tf.tf ">{{yzc23.mc}}</td>
                </tr>
                <tr>
                  <td  height="30">平均得分</td>
                  <td ng-repeat="yzd23 in xy.df.df ">{{yzd23.mc}}</td>
                </tr>
              </table>
            </div>
            <div style="padding:10px 0">3. 课程支撑的毕业要求指标点达成度评价
            <div></div>
            </div>
             <p style="margin:3px 0;line-height: 22px; text-indent:24px;">{{data.fiveS}}</p>
          </div>
          <p style="position:absolute; bottom:25px; left:25px;">  分析人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	系（教研室）主任签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	填表时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日 </p>
</div>
</div>

<div cs-window show="data.report3Show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px;width:800px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">课程分析报告</h3>
		        <a href="" class="popup-form-close" ng-click="data.report3Show=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix">
		    	<div class="table-box">
		    	  <div >	
  <div style="width:720px; line-height:22px; margin:15px 20px 25px 17px;">
  <br><br>
  <h4 style="text-align:center;">
  	<span style="vertical-align:middle; font-size:22px; margin-left:75px; margin-right:25px;">课程考核分析与总结报告</span></h4>
  <table width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000" >
    <tr height="30">
      <td rowspan="4" height="76" width="72">课<br />
        程<br />
        简<br />
        况</td>
      <td height="30" width="72">课程名称</td>
      <td colspan="3" height="30" width="300">{{data.courseName}}</td>
      <td height="30" width="72">课程代码</td>
      <td height="30" width="160">{{data.course}}</td>
    </tr>
    <tr height="30">
      <td height="30">考试方式</td>
      <td colspan="3" height="30">
        <input style="vertical-align:middle" ng-checked="data.kslxS1" ng-click="kslxClick()" type="checkbox" />
        <label style="vertical-align:middle; margin-right:10px;">开卷</label>
        <input type="checkbox" style="vertical-align:middle;" ng-checked="data.kslxS2" ng-click="kslxClick1()"/> 
        <label style="vertical-align:middle;margin-right:10px;">闭卷</label>
        <label style="vertical-align:middle;">其他：<input type="text" ng-model = "data.qt" style="vertical-align:middle;width:130px"></label>
      </td>
      <td height="30" width="72">课程性质</td>
     <td  height="30"><input style="vertical-align:middle" type="checkbox" ng-checked="data.kcxxS1"   ng-click="kcxxClick()"/>
        <label style="vertical-align:middle;  margin-right:10px;">选修</label>
        <input type="checkbox" style="vertical-align:middle;" ng-checked="data.kcxxS2"  ng-click="kcxxClick1()"/> 
        <label style="vertical-align:middle;margin-right:10px;">必修</label></td>
    </tr>
    <tr height="30">
      <td height="30" width="72">开课学院</td>
      <td height="30" width="220">{{data.dydata.name.dept}}</td>
      <td height="30" width="40">学分</td>
      <td height="30" width="40">{{data.dydata.xf}}</td>
      <td height="30" width="72">任课教师</td>
      <td height="30">{{data.dydata.tea.mc}}</td>
    </tr>
    <tr height="30">
      <td height="30" width="72">专业班级</td>
      <td height="30" colspan="5">{{data.dydata.name.cname}}</td>
    </tr>
  </table>
  <table width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000" >
    <tr height="30">
      <td rowspan="2" height="38" width="100">考试总体情况</td>
      <td height="30" width="81">应考人数</td>
      <td height="30" width="81">缺考人数</td>
      <td height="30" width="81">缓考人数</td>
      <td height="30" width="81">实考人数</td>
      <td height="30" width="81">违纪人数</td>
      <td height="30" width="81">最高成绩</td>
      <td height="30" width="81">最低成绩</td>
      <td height="30" width="81">平均成绩</td>
    </tr>
    <tr height="30">
      <td height="30">{{data.reportMap.all}}</td>
      <td height="30">{{data.reportMap.qk}}</td>
      <td height="30">{{data.reportMap.hk}}</td>
      <td height="30">{{data.reportMap.all-data.reportMap.qk-data.reportMap.hk-data.reportMap.wj}}</td>
      <td height="30">{{data.reportMap.wj}}</td>
      <td height="30">{{data.dycj.max}}</td>
      <td height="30">{{data.dycj.min}}</td>
      <td height="30">{{data.dycj.avg}}</td>
    </tr>
  </table>
  <table  width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000">
    <tr>
      <td rowspan="3" height="57" width="100">考试成绩分布</td>
      <td height="30" width="100">分数段</td>
      <td height="30" width="92" ng-repeat ="dsb1 in data.thList">{{dsb1.mc}}</td>
    </tr>
    <tr height="30">
      <td height="30" >人数</td>
      <td height="30" ng-repeat="zzz in data.dyCount">{{zzz.value}}</td>
    </tr>
    <tr height="30">
      <td height="30" >所占比例</td>
      <td height="30" ng-repeat="zzzz in data.dyScale">{{zzzz.value|number:2}}</td>
    </tr>
    <tr height="30">
      <td height="30" >标准差</td>
      <td height="30">{{data.dycj.bzc}}</td>
      <td height="30" >偏态值</td>
      <td height="30">{{data.dycj.ptz}}</td>
      <td height="30" >区分度</td>
      <td height="30">{{data.dycj.qfd}}</td>
      <td height="30" >效度</td>
      <td height="30">{{data.dycj.xd}}</td>
    </tr>
  </table>
  <table width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin-top:-1px; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000">
    <tr>
      <td><div style="text-align:left;padding-left:10px;">
      		<br>
          <h3 style="font-size: 16px;color: #000;"> 一、命题分析</h3>
          <textarea style="width:650px;height:200px;line-height: 22px; text-indent:24px;" ng-model="data.oneS" > </textarea> 
          <h3 style="font-size: 16px;color: #000;">二、问卷过程中发现学生答题存在的共性问题与知识掌握情况</h3>
          <textarea style="width:650px;height:180px;line-height: 22px; text-indent:24px; "  ng-model="data.twoS"> </textarea>
          <h3 style="font-size: 16px;color: #000;">三、试卷反映教学中存在的问题及改进措施</h3>
           <textarea style="width:650px;height:100px;line-height: 22px; text-indent:24px;" ng-model="data.threeS"> </textarea>
        </div>
  	  </td> 
    </tr>
  </table>
</div><!--打印样式-->
<div style="width:720px;height:1050px; position:relative; line-height:22px; margin:25px 20px 25px 17px;padding:15px 10px;border:1px solid black;">
  		<h3 style="font-size: 16px;color: #000;">四、其他</h3>
          <p style="text-indent:24px;">例如：课程支撑毕业要求达成度评价 </p>
          <div style="margin:0; padding-left:15px;">
            <div style="padding:10px 0;">1. 课程支撑的毕业要求指标点</div>
             <textarea style="width:650px;height:150px;line-height: 22px; text-indent:24px; " ng-model="data.fourS"> </textarea>
            <div style="padding:10px 0; width:675px;">2. 每道题实际分数和平均得分统计
              <table ng-repeat="xyss in data.tmList" width="100%" border="1" style="border-collapse:collapse; font-size:14px; margin:20px 0 0; text-align:center" cellspacing="0" cellpadding="0" bordercolor="#000">
                <tr>
                  <td  height="30" width="110">题号</td>
                  <td ng-repeat="yza1 in xyss.th.th" width="70">{{yza1}}</td>
                </tr>
                <tr>
                  <td  height="30">题型</td>
                  <td ng-repeat="yzb1 in xyss.tx.tx"><input value = "{{yzb1.mc}}" type="text" name="tx" style="width:70px"></td>
                </tr>
                <tr>
                  <td  height="30">题分</td>
                 <td ng-repeat="yzc1 in xyss.tf.tf"><input value = "{{yzc1.mc}}"  type="text"  name="tf" style="width:70px"></td>
                </tr>
                <tr>
                  <td  height="30">平均得分</td>
                  <td ng-repeat="yzd1 in xyss.df.df"><input value = "{{yzd1.mc}}" type="text" name="df" style="width:70px"></td>
                </tr>
              </table>
            </div>
            <div style="padding:10px 0">3. 课程支撑的毕业要求指标点达成度评价
            <div></div>
           <textarea  style="width:650px;height:200px;line-height: 22px; text-indent:24px;" ng-model="data.fiveS"> </textarea>
            </div>
          </div>
       <p style="position:absolute; bottom:25px; left:25px;">  分析人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	系（教研室）主任签字：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      	填表时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日 </p>
</div>
</div>

<div class="text-center"><a href="" class="zsbm-apply-btn confirm" ng-click="tjClick()">提交</a></div>
		        </div>
			</div>
		</div>
	</div>
</body>
</html>