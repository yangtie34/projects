<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>课程成绩分析</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scoreByStuLb/controller.js"></script>
<script type="text/javascript" src="product/scoreByStuLb/service.js"></script>

</head>
<body ng-controller="controller">
<!--     <div class="ss-mark-main" part-modal show-modal= "data.mask">
    	<div class="header">
            <header class="header-tit">
                <b>课程成绩分析</b>
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
				<div class="bak-orange bak-orange-up" align="center">
					<a href="#" class="sets up"></a>
				</div>
			</div>
        
        <div class="ss-mark-tab-box no-bottom">
			<div class="cg-select-dropdown btn-group dis-inb mar-r-6" style="width:250px" cg-select data="data.xnxqList"
				on-change="xnxqSelect($value)" ng-model="data.xnxq"
				value-field="id" display-field="mc"></div>
			
			<div class="cg-select-dropdown btn-group dis-inb mar-r-6"  style="width:170px" cg-select data="data.typeList"
				on-change="typeSelect($value)" ng-model="data.type"
				value-field="id" display-field="mc"></div>
				
			<div class="cg-select-dropdown btn-group dis-inb mar-r-6"  style="width:170px" cg-select data="data.categoryList"
				on-change="categorySelect($value)" ng-model="data.category"
				value-field="id" display-field="mc"></div>
				
			<div class="cg-select-dropdown btn-group dis-inb mar-r-6"  style="width:170px" cg-select data="data.attrList"
				on-change="attrSelect($value)" ng-model="data.attr"
				value-field="id" display-field="mc"></div>
				
			<div class="cg-select-dropdown btn-group dis-inb mar-r-6"  style="width:170px" cg-select data="data.natureList"
				on-change="natureSelect($value)" ng-model="data.nature"
				value-field="id" display-field="mc"></div>
				
			<div class="cg-select-dropdown btn-group dis-inb mar-r-6"  style="width:170px" cg-select data="data.courseList"
				on-change="courseSelect($value)" ng-model="data.course"
				value-field="id" display-field="mc"></div>
			
			<div class="cg-select-dropdown btn-group dis-inb mar-r-6"  style="width:130px" cg-select data="data.tagList"
				on-change="tagSelect($value)" ng-model="data.tag"
				value-field="id" display-field="mc"></div>
        </div>
        
        <div class="performance-change">
        	<p class="block-tit">各生源地成绩对比</p>
            <div class="img-box img-one img-right"><div echart config="data.distribute.originCfg"></div></div>
        </div>
        <div class="performance-change">
        	<p class="block-tit">{{data.originMc}}学生成绩分布</p>
            <div class="img-box img-one img-right"><div echart config="data.distribute.originPieCfg"></div></div>
        </div>
        <div class="performance-change">
        	<p class="block-tit">各民族成绩对比</p>
            <div class="img-box img-one img-right"><div echart config="data.distribute.nationCfg"></div></div>
        </div>
        <div class="performance-change">
        	<p class="block-tit">{{data.nationMc}}学生成绩分布</p>
            <div class="img-box img-one img-right"><div echart config="data.distribute.nationPieCfg"></div></div>
        </div>
        <div class="performance-change">
        	<p class="block-tit">各政治面貌成绩对比</p>
            <div class="img-box img-one img-right"><div echart config="data.distribute.zzmmCfg"></div></div>
        </div>
        <div class="performance-change">
        	<p class="block-tit">{{data.zzmmMc}}学生成绩分布</p>
            <div class="img-box img-one img-right"><div echart config="data.distribute.zzmmPieCfg"></div></div>
        </div>
    </div> -->
     <div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>课程成绩分析</b>
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
            <div class="ul-no-center">
                <ul class="list-unstyled top-center clearfix">
                    <li>
	                    <div class="cg-select-dropdown year-drop btn-group " style="width:250px" cg-select data="data.xnxqList"
						on-change="xnxqSelect($value)" ng-model="data.xnxq"
						value-field="id" display-field="mc"></div>
                    </li>
                    <li>
                    <div class="cg-select-dropdown year-drop btn-group "  style="width:170px" cg-select data="data.attrList"
						on-change="attrSelect($value)" ng-model="data.attr"
						value-field="id" display-field="mc"></div>
                    </li>
                    <li>
                    <div class="cg-select-dropdown year-drop btn-group "  style="width:170px" cg-select data="data.natureList"
						on-change="natureSelect($value)" ng-model="data.nature"
						value-field="id" display-field="mc"></div>
                    </li>
                    <li>
                    <div class="cg-select-dropdown year-drop btn-group"  style="width:170px" cg-select data="data.courseList"
						on-change="courseSelect($value)" ng-model="data.course"
						value-field="id" display-field="mc"></div>
                    </li>
                    <li>
                    <div class="cg-select-dropdown year-drop btn-group"  style="width:130px" cg-select data="data.tagList"
						on-change="tagSelect($value)" ng-model="data.tag"
						value-field="id" display-field="mc"></div>
                    </li>
                </ul>
            </div> 
        <section class="pad no-top">
        	<div class="separate-lf-rt has-border-top">
                <div class="sepatate-lf" style="width:61%">
                	<div class="block-tit"><p class="dis-inb">各生源地成绩对比</p></div>
                    <div class="img-box" echart config="data.distribute.originCfg"></div>
                </div>
                <div class="separate-rt">
                	<div class="block-tit"><p class="dis-inb">{{data.originMc}}学生成绩分布</p></div>
                    <div class="img-box" echart config="data.distribute.originPieCfg"></div>
                </div>
            </div>
            <div class="separate-lf-rt">
                <div class="sepatate-lf" style="width:61%">
                	<div class="block-tit"><p class="dis-inb">各民族成绩对比</p></div>
                    <div class="img-box" echart config="data.distribute.nationCfg"></div>
                </div>
                <div class="separate-rt">
                	<div class="block-tit"><p class="dis-inb">{{data.nationMc}}学生成绩分布</p></div>
                    <div class="img-box" echart config="data.distribute.nationPieCfg"></div>
                </div>
            </div>
            <div class="separate-lf-rt no-border">
                <div class="sepatate-lf" style="width:61%">
                	<div class="block-tit"><p class="dis-inb">各政治面貌成绩对比</p></div>
                    <div class="img-box" echart config="data.distribute.zzmmCfg"></div>
                </div>
                <div class="separate-rt">
                	<div class="block-tit"><p class="dis-inb">{{data.zzmmMc}}学生成绩分布</p></div>
                    <div class="img-box" echart config="data.distribute.zzmmPieCfg"></div>
                </div>
            </div>
        </section>
         
    </div>
</body>
</html>