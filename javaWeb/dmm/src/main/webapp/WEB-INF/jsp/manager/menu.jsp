<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>


<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/xiala.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />

    <!--左侧导航开始-->
	<script type="text/javascript">
	var title= document.title;
	$(function(){
	
		//var data=[{'istrue':1},1];
		var data=[];
		$.callService('resourcesService','getAllResources',data,function(d){
		//$.callService('menuService','getMenuByThis',data,function(d){
			var pathname=window.location.pathname;
			var dmm="${ctx }";
			var this_url=pathname.substring(dmm.length,pathname.length);
			var da=[{'url_':this_url,'istrue':1}];
			var this_path="-1";
			/* $.callService('menuService','getMenuByThis',da,function(dat){
				if(dat.length>0){
					this_path=dat[0].path;
				}
			},null,null,null,false); */
			var ddd=d;
			var icoMap={
					"权限管理":"subNav-icon-xtqx",
					"一卡通消费统计":"subNav-icon-xtykt",
			}
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
                			"<div class='subNav "+divClass+"'><em class='"+icoMap[d[i].name_]+"'>"+d[i].name_+"</div>"+
           				    "<ul class='navContent' "+ulStyle+" id='leftDivUl_"+flag+"'></ul></div>";
           			$("#leftDiv").append(html);
				}else{
				var listyle="";
					if(d[i].name_==title){
						$("#leftDivUl_"+flag).css("display", "block");
						listyle="background-color: #248b8a;";
					};
				
					html=" <li> <a href='${ctx }"+d[i].url_+"' style='"+listyle+"'>"+d[i].name_+"</a></li>";
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
     <!--   用户信息显示 -->
       	<div class="line_border">
        <div class="face-line">
          <div class="face-radius"> 上传 </div>
          <div class="face-name">刘德华</div>
          <div class="face-windows-icon"><a href="#" class="face-windows-icon"></a>
            <div class="face-windows-beside">
              <ul class="face-windows" >
                <li> <a class="face-windows-edit"  href="##">修改密码</a> </li>
                <li> <a  class="face-windows-exit" href="${ctx}/logout">退出</a> </li>
              </ul>
            </div>
          </div>
        </div>
      </div>	
        <!--   用户信息显示结束 -->    
        </div>
    </div>    
    <!--左侧导航结束-->
