<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教学经费分析</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/TeachingFunds/controller.js"></script>
<script type="text/javascript" src="product/TeachingFunds/service.js"></script>
</head>
<body ng-controller="controller">
<div class="ss-mark-wrapper" >
<div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>教学经费分析</b>
            </header>
            <div class="ul-no-center">
                <ul class="list-unstyled top-center clearfix no-m-btm">
                    <li>
                    	<div ng-if=!data.SelectDownShow ><span>暂无教学经费数据</span></div>
                    	<div ng-if=data.SelectDownShow class="xuegong-zhuti-dropdown-box">
						<div class="btn-group" cg-select data="data.bzdm_xn" on-change="changXn($value,$data)" ng-model="data.value_year" value-field="id" display-field="mc"></div>
						</div>
                    </li>
                    <li ng-if=data.thisBtnShow ng-class="{'has-green':data.thisYear==data.value_year}"><a href="" ng-click="changXn(data.thisYear)"  class="bg-green years">今年</a></li>
                    <li ng-if=data.lastBtnShow ng-class="{'has-green':data.lastYear==data.value_year}"><a href="" ng-click="changXn(data.lastYear)"  class="bg-green years">去年</a></li>
                </ul>
            </div>  
        </div>
        <div class="separate-lr no-top">
        	<div class="jx-mar-b-10"><span class="jx-note-green">
        	<span ng-if="data.thisYear==data.value_year">今年</span>
        	<span ng-if="data.lastYear==data.value_year">去年</span>
        	<span ng-if="!(data.lastYear==data.value_year) && !(data.thisYear==data.value_year)">{{data.value_year}}年</span>教学经费共支出 {{data.nowData.count}} 万元，同比
        	<span ng-if="(data.nowData.count-data.lastData.count)>=0">增长 {{data.nowData.count-data.lastData.count | number: 2}}</span>
        	<span ng-if="!((data.nowData.count-data.lastData.count)>=0)">减少 {{data.lastData.count-data.nowData.count | number: 2}}</span>
        	 万元；生均 {{data.nowData.avg}} 元，同比<span ng-if="(data.nowData.avg-data.lastData.avg)>=0">增长 {{data.nowData.avg-data.lastData.avg | number:2}}</span>
        	 <span ng-if="!((data.nowData.avg-data.lastData.avg)>=0)">减少 {{data.lastData.avg-data.nowData.avg | number:2}}</span>
        	 元</span></div>
        	<div class="row">
            	<div class="col-md-6">
                	<div class="block-tit"><p class="dis-inb">教学经费分布</p></div>
                    <div class="img-box">
                    	<div ng-if="data.styshow" echart
							config="data.distribute.fundsCfg" class="center-block"></div>
                    </div>
                </div>
                <div class="col-md-6 last-child">
                	<div class="block-tit"><p class="dis-inb">教学经费支出趋势（近五年）</p></div>
                    <div class="img-box">
                    	<div ng-if="data.lineshow" echart config="data.history.fundsLineCfg" class="center-block"></div>
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        
        <div class="performance-change">
        	<div class="block-tit">
            	<p class="dis-inb">历年教学经费分析</p>
                <div class="btn-group" cg-select data="data.sty" on-change="changSty($value,$data)" ng-model="data.value_sty" value-field="id" display-field="mc"></div>
            </div>
            <div class="img-box">
            	<div ng-if="data.value_sty==2 && data.countshow" echart
											config="data.distribute.fundsCountCfg" class="center-block"></div>
				<div ng-if="data.value_sty==1 && data.zbshow" echart
											config="data.distribute.fundsZBCfg" class="center-block"></div>
            </div>
        </div>
        
        <div class="performance-change">
        	<div class="block-tit">
            	<p class="dis-inb">生均教学经费分析</p>
            </div>
            <div class="img-box">
            	<div ng-if="data.sjshow" echart config="chartCfg" class="center-block"></div>
            </div>
        </div>
        
        <div class="performance-change no-border">
        	<div class="block-tit">
            	<p class="dis-inb">各{{data.distribute.title}}教学经费</p>
            </div>
            <div class="img-box">
            	<div ng-if="data.collegeshow" echart config="data.distribute.fundsBarCfg" class="center-block"></div>
            </div>
        </div>
        
    </div><!--end ss-mark-main-->
   <!-------------------------------------------------------以上为主要内容区------------------------------------------------>
</div>
</body>
</html>
