<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>违纪处分</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/punish/controller.js"></script>
<script type="text/javascript" src="product/punish/service.js"></script>
</head>
<body ng-controller="controller">

<div class="xuegong-zhuti-content">
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
	<!-- 导航 -->
	<div ng-show="data.advance.show" class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
		<div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" no-border="true"></div>
	</div>
	<!-- 导航2 -->
	<div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone scholarship">
		<!-- 学年 -->
		<div class="xuegong-zhuti-dropdown-box">
			<div class="btn-group" cg-select data="data.bzdm_xn" on-change="changXn($value,$data)" ng-model="data.value_year" value-field="id" display-field="mc"></div>
		</div>
		<!-- 学历 -->
		<div class="xuegong-zhuti-dropdown-box">
			<div class="btn-group" cg-select data="data.bzdm_edu" on-change="changEdu($value,$data)" ng-model="data.value_edu"></div>
		</div>
		<div class="btn-basic">
			<a href="" ng-click="changXn(data.thisYear)" ng-class="{on:data.thisYear==data.value_year}" class="btn-blue">今年</a>
           	<a href="" ng-click="changXn(data.lastYear)" ng-class="{on:data.lastYear==data.value_year}" class="btn-blue">去年</a>
           	<a href="" ng-click="chang5Year()" ng-class="{on:data.fiveYear}" class="btn-blue">近五年</a>
           	<a href="" ng-click="chang10Year()" ng-class="{on:data.tenYear}" class="btn-blue">近十年</a>
		</div> 
		<div class="clearfix"></div>
	</div>
	
	<!-- 违纪处分分布 -->
	<div class="xuegong-border weiji-chufen-top-overview">
		<p>{{data.dateName}}违纪<b><a class="text-pink" href="" ng-click="getAllStuDetail('violate')"> {{data.abstract_data.violate}} </a></b>人次，处分<b class="text-pink"><a class="text-pink"  href="" ng-click="getAllStuDetail('punish')"> {{data.abstract_data.punish}} </a></b>人次，处分率<b class="text-pink"> {{data.abstract_data.scale}}%</b></p>
		<div class="row">
			<div class="col-md-5">
 				<div class="img-box no-top no-bottom"><div echart config="data.distribution.option_volate" class="center-block vertical-center"></div></div> 
				<p class="text-center"><b class="text-pink">违纪</b></p>
			</div>
			<div class="col-md-2">
				<div><p class="text-center text-pink"><b class="text-pink">处分率{{data.abstract_data.scale}}%</b></p></div>
			</div>
			<div class="col-md-5">
 				<div class="img-box no-top"><div echart config="data.distribution.option_punish" class="center-block"></div></div> 
				<p class="text-center"><b class="text-pink">处分</b></p>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	
	<div class="xuegong-separate-tb scholarship-fenbu weiji-chufen-fenbu">
       	<div class="xuegong-zhuti-content-main-tit">
        	<b class="text-blue">违纪处分{{data.deptData.deptMc}}分布</b>
             <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.deptData.bzdm_target" on-change="changeDeptTarget($value,$data)" ng-model="data.deptData.id_target"></div>
            </div>
            <div ng-repeat="obj in data.deptData.bzdm_type" class="radio radio-inline radio-xuegong-change radio-xuegong-blue">
           		<input type="radio" name="weiji-chufen" id="id_bzdm_type_{{$index}}" ng-click="changeDeptType(obj.id)" ng-disabled="data.deptData.id_type==obj.id" ng-checked="data.deptData.id_type==obj.id" />
           		<label for="id_bzdm_type_{{$index}}" class="xuegong">{{obj.mc}}</label>
            </div>
        </div>
        <div class="xuegong-separate-con"><div class="img-box">
      		<div class="img-box-only" part-modal show-modal="data.dataShow.show">
      			<div class="center-block" echart config="data.deptData.option"></div> 
      		</div>
        </div></div>
	</div> 
       <div class="xuegong-separate-tb weiji-chufen-bili">
            	<div class="xuegong-zhuti-content-main-tit">
                	<b class="text-blue">违纪处分概率分析</b>
                </div>
                <div class="xuegong-separate-con">
                	<div class="weiji-section-tags pull-left mCustomScrollbar" style="height:390px">
                    	<ul class="list-unstyled text-center">
                       <li ng-class="{active:obj.flag}" ng-repeat="obj in data.dataLab.labLi"><a href="" ng-click="obj.flag || changeLab(obj,data.dataLab.labLi)">{{obj.mc}}</a></li>
                        </ul>
                    </div>
                    <div class="weiji-section-con pull-left" part-modal show-modal="data.add.show">
                    	<div class="row">
                        	<div class="col-md-4" style="width:45%">
                            	<p class="text-center"><b>违纪比例</b></p>
                                <div class="img-box no-top" echart config="data.vioTree.option"></div>
                            </div>
                            <div class="col-md-4" style="width:55%">
                            	<p class="text-center"><b>违纪分布</b></p>
                                <div class="img-box no-top" echart config="data.vioCir.option"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        <div class="row">
                        	<div class="col-md-4" style="width:45%">
                            	<p class="text-center" style="heigth:2px"><b>处分比例</b></p>
                                <div class="img-box no-top" echart config="data.punTree.option"></div>
                            </div>
                            <div class="col-md-4" style="width:55%">
                            	<p class="text-center"><b>处分分布</b></p>
                                <div class="img-box no-top" echart config="data.punCir.option"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
            </div>
	<div class="xuegong-separate-tb weiji-chufen-month-fenbu">
       	<div  class="xuegong-zhuti-content-main-tit">
        	<b class="text-blue">违纪处分月份分布</b>
        </div>
        <div class="xuegong-separate-con"><div class="img-box">
        	<div class="img-box-only">
         		<div class="center-block" echart config="data.month.option"></div> 
       		</div>
        </div></div>
	</div>
	
	<table  class="separate-l-r"><tbody>
        <tr>
            <td  class="weiji-chufen-grade-fenbu">
                <div class="xuegong-zhuti-content-main-tit">
                    <b class="text-blue">违纪处分年级分布</b>
                    <a href=""><span class="time-img pull-right"></span></a>
                </div>
            </td>
            <td class="tab-cell"></td>
            <td>
                <div class="xuegong-zhuti-content-main-tit">
                    <b class="text-blue">违纪处分年龄分布</b>
                    <a href=""><span class="time-img pull-right"></span></a>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div class="xuegong-separate-con">
                	<div class="img-box">
                    	<div class="img-box-only">
                     		<div class="center-block" echart config="data.grade.option"></div> 
                    	</div>
                    </div>
                </div>
            </td>
            <td class="tab-cell"></td>
            <td>
                <div class="xuegong-separate-con"><div class="img-box">
                   	<div class="img-box-only">
                    		<div class="center-block" echart config="data.age.option"></div> 
                   	</div>
                </div></div>
            </td>
        </tr>
    </tbody></table>
    
	<div class="xuegong-separate-tb scholarship-historical-change">
       	<div class="xuegong-zhuti-content-main-tit">
           	<b class="text-blue">处分学生二次处分比例</b>
        </div>
        <div class="xuegong-separate-con"><div class="img-box">
        	<div class="img-box-only">
            		<div class="center-block" echart config="data.again.option"></div> 
           	</div>
        </div></div>
	</div>

</div>
</div>
	    <div modal-form config="data.stu_detail.formConfig"></div>
</body>

</html>