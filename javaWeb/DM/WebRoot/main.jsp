<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Date"%>  
<%@ page import="com.jhkj.mosdc.framework.util.*"%>
<%@ page import="org.apache.struts2.*"%> 
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%
   String openPage = null;
   String openPageParams = null;
   if(session != null){
	   openPage = session.getAttribute("openPage") == null ? null : session.getAttribute("openPage").toString();
	   openPageParams = session.getAttribute("openPageParams") == null ? null : session.getAttribute("openPageParams").toString();
   }
%>
<!DOCTYPE html
        PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>数据挖掘分析系统</title>
    <link rel="shortcut icon" href="css/icon.ico"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="css/index-loading.css" />
    <link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
    <!-- 封装extjs下拉列表css -->
    <link rel="stylesheet" type="text/css" href="extjs/resources/css/BoxSelect.css"/>
    <!-- end -->
    <link rel="stylesheet" type="text/css" href="again/resources/css/btn.css" />
    <link rel="stylesheet" type="text/css" href="css/css.css" />
    <link rel="stylesheet" type="text/css" href="css/main.css" />
    <link rel="stylesheet" type="text/css" href="app/output/css/output.css"/>
    <link rel="stylesheet" type="text/css" href="again/resources/css/all.css"/>
    <link rel="stylesheet" type="text/css" href="css/xg/common.css"/>
    <link rel="stylesheet" type="text/css" href="app/pages/sc/base/map/jqvmap.css"/>
    <link rel="stylesheet" type="text/css" href="app/pages/sc/base/component/css/exp-component.css"/>

   	
    <script src="again/jquery-1.9.1.min.js"></script>
    <script src="app/pages/sc/base/map/jquery.vmap.js"></script>
    <script src="app/pages/sc/base/map/jquery.vmap.henan.js"></script>
    <script src="app/pages/sc/base/map/jquery.vmap.china_zh.js"></script>
    <script src="FusionCharts/fusionJS/FusionCharts.js"></script>
    <script src="again/LAB.min.js"></script>
    <script src="extjs/ext-all-debug.js"></script>
    <!-- 封装extjs下列列表js -->
	<script src="extjs/ux/form/BoxSelect.js"></script>
	<script src="extjs/ux/form/CheckCombo.js"></script>
	<!-- end -->
	
    <!-- 国际化要放到最后。。。。xxx -->
    <script type="text/javascript" src="extjs/ext-lang-zh_CN.js"></script>
    <script src="again/ns-debug.js"></script>
    <script src="app/pages/sc/base/exp-debug.js"></script>
    <!--[if gte IE 9]><!-->
     <script src="again/d3.js"></script>
    <!--<![endif]-->
    <script src="app/output/output-debug.js"></script>
    <script src="app/pages/sc/base/highcharts.js"></script>
    <script src="app/pages/sc/base/highcharts-more.js"></script>
    <script src="app/pages/sc/base/echarts/source/echarts-all.js"></script>
    
    <!-- highchart 导出js -->
<!--     <script src="app/pages/sc/base/modules/exporting.js"></script> -->
    
    <!--[if IE 8]> 
    <style type="text/css">
	.x-form-checkbox {
	    background: url("extjs/resources/themes/images/default/form/checkbox.gif") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
	    border: 0 none;
	    height: 13px;
	    overflow: hidden;
	    padding: 0;
	    vertical-align: middle;
	    width: 13px;
    </style>
    <![endif]-->
    
    <!--<script src="app/system/Tranditional.js"></script>-->
    <script type="text/javascript">
        Ext.onReady(function(){
            Ext.Loader.setConfig({'enabled' : true,
                paths : {'Ext' : 'extjs/'}});
            System = {};
            NS.setLoadPath('System' , 'app/system/');
            NS.setLoadPath('Pages' , 'app/pages/');
            NS.setLoadPath('Template' , 'app/template/');
            NS.setLoadPath('Output' , 'app/output/');
            NS.setLoadPath('Manager' , 'app/manager/');
            NS.setLoadPath('Business' , 'app/business/');
            NS.load('System.Tranditional',function(){
                MainPage = new System.Tranditional();
                MainPage.on('pageready',function(){
                    Ext.get('loading').remove();
                    Ext.get('loading-mask').fadeOut({remove:true});
                    MainPage.openPage('<%=openPage%>',NS.decode('<%=openPageParams%>'),true);
                });
            });
//          NS.onReady(function(){
//             // var a = new System.Tranditional();
//          })
        });
    </script>
</head>
<body>
    <div id="loading">
        <div  class="loading-indicator">
            <img src="images/extanim32.gif" alt="" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>
            正在加载,请稍候......
        </div>
    </div>
<div id="loading-mask">
</div>
</body>
</html>
