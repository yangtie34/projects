<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>全周期学生成绩分析</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/bysScore/controller.js"></script>
<script type="text/javascript" src="product/bysScore/service.js"></script>

</head>
<body ng-controller="controller">
    <div class="ss-mark-main" >
    	<div class="header">
            <header class="header-tit">
                <b>全周期学生成绩分析</b>
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
			<!-- 	<div class="bak-orange bak-orange-up" align="center">
					<a href="#" class="sets up"></a>
				</div> -->
			</div>
        
        <div class="ss-mark-tab-box no-bottom">
        	<span class="dis-inb mar-r-6">学 历</span>
							<div class="cg-select-dropdown btn-group dis-inb mar-r-6" style="width:130px" cg-select data="edulist"
								on-change="eduSelect($value)" ng-model="edu"
								value-field="id" display-field="mc"></div>
            <span  class="dis-inb mar-r-6">学 制</span>
							<div class="cg-select-dropdown btn-group dis-inb mar-r-6"  style="width:130px" cg-select data="xzlist"
								on-change="xzSelect($value)" ng-model="xz"
								value-field="id" display-field="mc"></div>
        </div>
        
        <div class="performance-change">
        	<p class="block-tit">各学期成绩变化</p>
         	 <div style="margin-top:25px;" class="pull-right">
	        		    <div tool-tip placement="left" class="text-green">
			       		<p>平均数:一组数据中所有数据之和再除以这组数据的个数</p>
						<p> 中位数:把所有数据高低排序后正中间的一个为中位数，一种衡量集中趋势的指标;</p>
						<p>众数:一组数据中出现次数最多的数值叫众数，就是一组数据中占比例最多的那个数;</p>
						<p>方差:各个数据分别与其平均数之差的平方的和的平均数，方差不仅仅表达了样本偏离均值的程度，更是揭示了样本内部彼此波动的程度;</p>
						<p>标准差:标准差是方差的算术平方根，标准差能反映一个数据集的离散程度;</p>
	        	    </div></div> 
            <div class="ul-no-center pad-lr-n">
            	<ul class="list-unstyled top-center">
                	<li ng-class="{'has-green':x.id == date}" ng-repeat="x in datelist"><a href="" class="bg-green years" ng-click="x.id == date||dateSelect(x.id)">{{x.mc}}</a></li>
                    <li>
                        <span  class="dis-inb mar-r-6">成绩类型</span>
                        <div class="cg-select-dropdown btn-group dis-inb" style="width:100px">
                             <div class="btn-group" cg-select data="scoreTypelist"
								on-change="scoreTypeSelect($value)" ng-model="scoreType"
								value-field="id" display-field="mc"></div>
                        </div>
                    </li>
                     <li>
                    	<span class="dis-inb mar-r-6">成绩指标</span>
                        <div class="cg-select-dropdown btn-group dis-inb" style="width:130px">
	                       	<div class="btn-group" cg-select data="targetlist"
									on-change="targetSelect($value)" ng-model="target"
									value-field="id" display-field="mc"></div>
                        </div>
                    </li>
                   
                    <div class="clearfix"></div>
            	</ul>
            </div>
            <div class="img-box img-one img-right"><div echart config="data.distribute.lineCfg"></div></div>
        </div>
        <div class="performance-change" style="height:700px">
        	<div class="block-tit clearfix"><p class="pull-left">各学期成绩分布&nbsp;</p>
        		<div style="margin-top:5px;">
        		<div tool-tip placement="right" class="text-green">
		       		<p ng-repeat="jra in scoreGroup">{{jra.mc}}:{{jra.mc1}}</p>
	        	</div></div>
        	</div>
        	 
            <div class="ul-no-center pad-lr-n">
            	<ul class="list-unstyled top-center">
                    <li>
                        <span  class="dis-inb mar-r-6">届 别</span>
                        <div class="cg-select-dropdown btn-group dis-inb" style="width:100px">
	                        <div class="btn-group cg-select-dropdown dis-inb" cg-select data="periodlist" style = "width:100px"
									on-change="periodSelect($value)" ng-model="period"
									value-field="id" display-field="mc"></div>
                        </div>
                    </li>
                    <div class="clearfix"></div>
            	</ul>
            </div>
            <div class="img-box img-one img-right"><div echart config="data.distribute.fbCfg"></div></div>
        </div>
        
        
        
    </div><!--end ss-mark-main-->
</body>
</html>