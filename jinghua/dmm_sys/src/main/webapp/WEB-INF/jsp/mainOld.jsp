<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>数据挖掘分析系统</title>
	<link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${ctxStatic }/sticky/sticky.css">
</head>
<body>
	<%@ include file="layouts/top.jsp"%>
	<div style="margin-left: 25%; margin-top: 15%;">
		<div>
			<div class="row" style="margin-right:0">
				<div class="col-xs-6 col-md-8 ">
					<input type="text" class="form-control" id="sousuo" placeholder="搜索">
				</div>
				<button class="btn btn-info" type="submit" style="width: 40px;" onclick="searchMenu();">搜</button>
			</div>
		</div>
		<div style="margin-top: 5%">
			<div class="row" style="margin-right:0">
				<c:forEach var="menu" items="${menus }">
					<div class="col-md-4">
						<div class="accordion-group">
							<div class="accordion-heading">
								<a href="javascript:void(0)" class="collapseMenus" data-toggle="collapse" data-target="#collapse${menu.id }" 
								   aria-expanded="false" aria-controls="collapse${menu.id }" onclick="clickMenu(this);">
									${menu.name_ }
								</a>
							</div>
							<div class="collapse" id="collapse${menu.id }" aria-expanded="false" aria-controls="collapse${menu.id }">
								<div class="accordion-inner sonMenus" id="${menu.path }" style="height: 50PX;]">
									<ul>
										<li>123</li>
										<li>456</li>
										<li>789</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxStatic }/sticky/sticky.min.js"></script>
    <script type="text/javascript">
    	var baseDom=null;
    	var clickMenu=function(me){
	   		baseDom=me;
	       	var divMenus=$(".collapseMenus");
			for(var i=0;i<divMenus.length;i++){
                var thisTarget=$(divMenus[i]).data("target");
          		//执行同行的节点
          		if($(me).data("target")!=thisTarget&&$(me).offset().top==$(divMenus[i]).offset().top){
                    if(isOpen(me)){
                        $(thisTarget).collapse('hide');
                    }else{
                        $(thisTarget).collapse('show');
                	}
          		}
          		//下面这句话表示只能打开一行
          		if($(me).offset().top!=$(divMenus[i]).offset().top&&isOpen(divMenus[i])){
                   $(thisTarget).collapse('hide');
          		}
          	}
    	}
    	var changeWindow=function(){
    		if(baseDom!=null){
    			var baseTop=$(baseDom).offset().top;
        		var nowMenuAlist=$(".collapseMenus");
        		for(var i=0;i<nowMenuAlist.length;i++){
        			var $nowMenuA=$(nowMenuAlist[i]);
                    var thisTarget=$nowMenuA.data("target");
       				if(baseTop!=$nowMenuA.offset().top&&isOpen(nowMenuAlist[i])){
       					$(thisTarget).collapse('hide');
       				}else if(baseTop==$nowMenuA.offset().top&&(isOpen(baseDom)!=isOpen(nowMenuAlist[i]))){
                        if(isOpen(baseDom)){
                            $(thisTarget).collapse('show');
                        }else{
                            $(thisTarget).collapse('hide');
                        }
       				}
        		}
    		}
    	}
    	var isOpen=function(me){
    		if($(me).attr("aria-expanded")==undefined||$(me).attr("aria-expanded")=="false"){
    			return false;
    		}else{
    			return true;
    		}
    	}
    	
    	$(window).resize(function() {
    		changeWindow();
    	});
    	var searchMenu=function(){
    		var q=$("#sousuo").val();
    		window.open("${ctx}/searchmenu/"+q+"/1");
    	}
	    $(function(){
	    	var initSonMenus=function(){
	    		var sonMenus=$(".sonMenus");
	    		for(var i=0;i<sonMenus.length;i++){
	    			var path=sonMenus[i].id;
	    			$.ajax({
	         		     type: "POST",
	         		     async:true,
	         		     url:"${ctx}/main/"+path,
	         		     success: function(data){
	         		    	var html="<ul>";
	         		    	var pDom;
	         		    	for(var i=0;i<data.object.length;i++){
	         		    		var sonDom=data.object[i];
	         		    		pDom=$("#"+sonDom.path.substr(0,8));
	         		    		html+="<li><a href='${ctx}"+sonDom.url_+"' target='_blank'>"+sonDom.name_+"<a></li>";
	         		    	}
	         		    	html+="</ul>";
	         		    	pDom.html(html);
	         		     }
	         		 });
	    		}
	    	}
	    	initSonMenus();
	    	$("#sousuo").keypress(function(event){
	    		if("Enter"==event.key){
	    			searchMenu();
	    		}
	    	});
	    });
    </script>
    <%@ include file="layouts/end.jsp"%>
</body>
</html>