<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.jhnu.syspermiss.util.SysConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>

<script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/right-common.css" />
    <!--左侧导航开始-->
	<script type="text/javascript">
	var title= document.title;
	Array.prototype.remove = function(dx) 
	{ 
	    if(isNaN(dx)||dx>this.length){return false;} 
	    this.splice(dx,1); 
	} 
	//tree格式化
	var listTree=[];
	var listMap={};
	var getRootTree=function(d,data){
		d.children=[];
		for(var i=0;i<data.length;i++){
			if(data[i].pid==d.id){
				d.children.push(data[i]);
				listMap[d.id]={};
				listMap[data[i].id]={};
				getRootTree(data[i],data);
			}
		}
		if(d.children.length==0)return;
		return d;
	};
	var getTree=function(d){
		for(var i=0;i<d.length;i++){
			if(d[i].resource_type_code=='01'){
				var a=d[i];
				listTree.push(getRootTree(a,d));
			}
		}
		for(var i=0;i<d.length;i++){
			if(!listMap[d[i].id]){
				listTree.push(d[i]);	
			}
		}
		return listTree;
	}
	var level=0;
	var clickUrl=function(a){
		$('ul.navContent a').each(function (i){
			$(this).removeClass("click_this_a");
		});
		$(a).addClass("click_this_a");
	}
	//生成菜单
	var pathname=window.location.pathname;
			var dmm="${ctx }";
			var this_url=pathname.substring(dmm.length,pathname.length);
			var this_path="-1";
			var icoMap={
					//"权限管理":"subNav-icon-xtqx",
					//"一卡通消费统计":"subNav-icon-xtykt",
			}
			var html="";
	var init=function(level,d){
		for(var i=0;i<d.length;i++){
			if(d[i].level_!=level)continue;
			var Divroot=null;
			var sty=""
			if($("#leftDivUl_"+d[i].pid).length==0){
				Divroot=$("#leftDiv");
			}else{
				//sty="margin-left:5px;"
				Divroot=$("#leftDivUl_"+d[i].pid);
			}
			if(d[i].resource_type_code=='01'  ){
				var divClass="",ulStyle="style='margin:0px'";
				if(this_path.indexOf(d[i].path)>=0){
					divClass="currentDd currentDt",ulStyle="style='display:block'";
				}
				html="<div class='line_border' style='background-color: #32323a;"+sty+"'>"+
            			"<div class='subNav "+divClass+"'><em class='"+icoMap[d[i].name_]+"'>"+d[i].name_+"</div>"+
       				    "<ul class='navContent' "+ulStyle+" id='leftDivUl_"+d[i].id+"'></ul></div>";
       				 Divroot.append(html);
       		if(d[i].children.length>0){
       			init(level+1,d[i].children);
       		}
			}else if(d[i].isShow!=0){
			var listyle="";
				if(d[i].name_==title){
					$("#leftDivUl_"+d[i].pid).css("display", "block");
					listyle="background-color: #248b8a;";
				};
			
				html=" <li> <a href=\""+d[i].url_+"\" target=\"view_frame\" onclick='javascript:clickUrl(this)' style='"+listyle+"'>"+d[i].name_+"</a></li>";
				if(level=="${level}"){
					var ul0=$("#ul0");
					if(ul0.length==0)Divroot.append("<ul class='navContent' style='margin-top:10px;display: block;background: none;' id='ul0' ></ul>");
					ul0=$("#ul0");
					ul0.append(html);
					continue;
				}
				Divroot.append(html);
			}
		}
	};
	
	$(function(){
		//var data=[{'istrue':1},1];
		<% String basePath = request.getContextPath();
   int port = request.getServerPort();
   String ip = request.getServerName();
   String projectPath = "http://"+ip+":"+port+basePath;
   //遍历获取request对象参数
%>
	<%-- 	var data=[];
		data.push('<%=request.getUserPrincipal().getName() %>');
		data.push('<%=projectPath %>');
		$.callService('resourcesService','getMenusByUserName',data,function(d){
			init(0,getTree(d));
		},null,null,null,false); --%>
		init(${level},getTree(${res}));
		
 	$(".subNav").click(function(){
				$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
				$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
				
				// 修改数字控制速度， slideUp(500)控制卷起速度
				$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
		});	 
 	var firstA=$("ul.navContent>li>a:eq(0)");
 	$("iframe").attr("src",firstA.attr("href"));
 	firstA.click();
 	$(".subNav:eq(0)").click();
	})
	</script>
   <div class="con_left">
       <div class="subNavBox" id ="leftDiv">
     <!--   用户信息显示 -->
       	<div class="line_border">
        <div class="face-line">
        <div style="display:table-cell; width: 20px;">
        <img alt="" src="<%=SysConfig.instance().getDmmUrl()%>/user/getphoto" class="img-circle" width="50" height="50">
        </div>
          <div class="face-name"><%=request.getUserPrincipal().getName() %></div>
          <div class="face-windows-icon"><a href="" class="face-windows-icon"></a>
            <div class="face-windows-beside" >
              <ul class="face-windows" >
               <li> <a class="face-windows-edit" style="cursor:pointer" href="<%=SysConfig.instance().getDmmUrl()%>/user/changepwd/page?username=<%=request.getUserPrincipal().getName() %>"> 修改密码 </a> </li> <!--  id="changePass" -->
              <!--  <li><a data-toggle="modal" data-target="#myModal1" href="javascript:void(0)">查看个人信息</a></li> -->
                <li> <a  class="face-windows-exit" href="${ctx}/logout">退出</a> </li>
              </ul>
            </div>
          </div>
        </div>
      </div>	
        <!--   用户信息显示结束 -->    
        </div>
    <div class="xscz-swith xscz-swith-on"><a href="javascript:void(0);" onClick="$('.nav').hide(300);$('#main_page').css('padding-left','0px');$('.xscz-swith-off').show(300);"></a></div>
        	
        </div>
		<div class="xscz-swith xscz-swith-off" onClick="$('.nav').show(300);$('#main_page').css('padding-left','260px');$('.xscz-swith-off').hide(300);"><a href=""></a></div>  
