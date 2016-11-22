<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>


<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/right.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/xiala.css" />

    <!--左侧导航开始-->
	<script type="text/javascript">
	
	$(function(){
	
		var data=[{'istrue':1},1];
		$.callService('menuService','getMenuByThis',data,function(d){
			var pathname=window.location.pathname;
			var dmm="${ctx }";
			var this_url=pathname.substring(dmm.length,pathname.length);
			var da=[{'url_':this_url,'istrue':1}];
			var this_path="-1";
			$.callService('menuService','getMenuByThis',da,function(dat){
				if(dat.length>0){
					this_path=dat[0].path;
				}
			},null,null,null,false);
			var flag=0;
			var html="";
			for(var i=0;i<d.length;i++){
				if(d[i].url_==null || d[i].url_=="" ){
					flag=i;
					var divClass="",ulStyle="";
					if(this_path.indexOf(d[i].path)>=0){
						divClass="currentDd currentDt",ulStyle="style='display:block'";
					}
					html="<div class='line_border'>"+
                			"<div class='subNav "+divClass+"'>"+d[i].name_+"</div>"+
           				    "<ul class='navContent' "+ulStyle+" id='leftDivUl_"+flag+"'></ul></div>";
           			$("#leftDiv").append(html);
				}else{
					html=" <li> <div class='Btn' onMouseOver='this.className=\"Btn_Hover\"' "+
					"onMouseOut='this.className=\"Btn\"'><a href='${ctx }"+d[i].url_+"'>"+d[i].name_+"</a></div></li>";
					$("#leftDivUl_"+flag).append(html);
				}
			}  
		},null,null,null,false);
	$(".subNav").click(function(){
				$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
				$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
				
				// 修改数字控制速度， slideUp(500)控制卷起速度
				$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
		});	
	})
	</script>
   <div class="con_left">
       <div class="subNavBox" id ="leftDiv">
       		
            
        </div>
    </div>    
    <!--左侧导航结束-->
