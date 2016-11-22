<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.jhnu.syspermiss.*,com.jhnu.syspermiss.permiss.entity.User,com.alibaba.fastjson.JSON,cn.gilight.framework.uitl.common.PropertiesUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	var base = "${ctx }/";
</script>
<script type="text/javascript" src="${ctx }/static/echarts/echartsUtil.js"></script>
<script type="text/javascript" src="${ctx }/static/index.js"></script>
<script type="text/javascript" src="${ctx }/static/echarts/common.js"></script>
<script type="text/javascript" src="${ctx }/product/main/js/mainController.js"></script>
<link href="${ctx }/resource/css/font-awesome.min.css" rel="stylesheet">
<link href="${ctx }/resource/css/yiqisbguoqiqkfx.css" rel="stylesheet">
<link href="${ctx }/resource/css/leftnav.css" rel="stylesheet">
</head>
<%
String username=request.getUserPrincipal().getName();
%>
		<div class="con_left" ng-controller="mainController">
       <div class="subNavBox" id ="leftDiv" >
     <!--   用户信息显示 -->
       	<div class="line_border">
        <div class="face-line">
        <div style="display:table-cell; width: 20px;">
        <img alt="" src="${ctx}/getphoto" class="img-circle" width="50" height="50">
        </div>
          
          <div class="face-name"><%=request.getUserPrincipal().getName() %></div>
          <div class="face-windows-icon"><a href="" class="face-windows-icon"></a>
            <div class="face-windows-beside" >
              <ul class="face-windows" >
               <li> <a class="face-windows-edit" style="cursor:pointer" href="<%=PropertiesUtils.getProperties("/permiss/sysConfig.properties","sys.dmmUrl")%>/user/changepwd/page?username=<%=username %>"> 修改密码 </a> </li> <!--  id="changePass" -->
                <li> <a  class="face-windows-exit" href="${ctx}/logout">退出</a> </li>
              </ul>
            </div>
          </div>
        </div>
      </div>	
        <!--   用户信息显示结束 -->
	        <div class="line_border" style="background-color: #32323a;" ng-repeat="value in resList">
		        <div ng-click="showddClick(value.id);" ng-class="showdd[value.id]==true?'subNav  currentDd currentDt':'subNav'">{{value.name_}}</div>
		        <ul class="navContent" style="margin: 0px; display: block;" ng-repeat="item in value.children" cg-slide-show time="500" isshow="showdd[value.id]"> <!-- ng-show="showdd[value.id]" -->
		        <li> 
		        <a ng-show="curPath==item.url_" class="click_this_a">{{item.name_}}</a>
                <a ng-click="curPathClick(item.url_);" ng-show="curPath!=item.url_"
                href="${ctx }{{item.url_}}" target="right">{{item.name_}}</a></li> 
		        </ul>
	        </div> 
           
        </div>
        <div class="xscz-swith xscz-swith-on"><a href="javascript:void(0);" onClick="$('.con_left').hide(300);$('#main_page').css('padding-left','0px');$('.xscz-swith-off').show(300);"></a></div>
    </div> 
    <div class="xscz-swith xscz-swith-off" onClick="$('.con_left').show(300);$('#main_page').css('padding-left','260px');$('.xscz-swith-off').hide(300);"><a href=""></a></div>

<script>
var resList=<%=JSON.toJSON(GetCachePermiss.getSysMenusByUserName(username)).toString()%>;
var resList1=[];
var mapr={};
for(var i=0;i<resList.length;i++){
	if(resList[i].level_==3&&resList[i].isShow!=0){
		mapr[resList[i].id]=resList1.length;
		resList[i].children=[];
		resList1.push(resList[i]);
	}
}
for(var i=0;i<resList.length;i++){
	if(resList[i].level_==4&&resList[i].isShow!=0){
		resList1[mapr[resList[i].pid]].children.push(resList[i]);
	}
}
</script>

