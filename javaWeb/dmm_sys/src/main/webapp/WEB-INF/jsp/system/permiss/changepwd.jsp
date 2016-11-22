<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html>
<head>
<title>修改密码</title>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/bootstrap-3.3.4-dist/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/change-password-style.css" />
<script type="text/javascript" src="${ctxStatic}/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" >
$(function(){
	var username=getQueryString("username");
	$("#username").val(username);
});
function next01(){
	$("#updateLi").addClass("active");
	$("#oneLi").removeClass("active");
	$('.change-paword-01').animate({"left":"90%","opacity":"0","z-index":"0"},500);
	$('.change-paword-02').animate({"left":"50%","margin-left":"-210"+"px","opacity":"1","z-index":"1"},500);
}
function next02(){
	$("#succLi").addClass("active");
	$("#updateLi").removeClass("active");
	$('.change-paword-02').animate({"left":"90%","opacity":"0","z-index":"0"},500);
	$('.change-paword-03').animate({"left":"50%","margin-left":"-210"+"px","opacity":"1","z-index":"1"},500);
}
function prev02(){
	$("#oneLi").addClass("active");
	$("#updateLi").removeClass("active");
	$('.change-paword-02').animate({"left":"0","opacity":"0","z-index":"0"},500);
	$('.change-paword-01').animate({"left":"50%","margin-left":"-210"+"px","opacity":"1","z-index":"1"},500);
	clearPwd();
}
var clearPwd=function(){
	$("#oldpwd").val("");
	$("#newpwd1").val("");
	$("#newpwd2").val("");
	$("#oldpwdSpan").css("display","none");
	$("#newpwd1Span").css("display","none");
	$("#newpwd2Span").css("display","none");
};
var checkNewPwd=function(){
	if($("#newpwd1").val()!=""&&$("#newpwd2").val()!=""&&$("#newpwd1").val()!=$("#newpwd2").val()){
		$("#newpwd2Span").html("两次密码不同");
		$("#newpwd2Span").css("display","inline");
	}else{
		$("#newpwd1Span").css("display","none");
		$("#newpwd2Span").css("display","none");
	}
}
var checkOldPwd=function(){
	var errString="该字段不能为空";
	if($("#oldpwd").val()==""){
		$("#oldpwdSpan").html(errString);
		$("#oldpwdSpan").css("display","inline");
		pwdFlag=false;
	}else{
		$("#oldpwdSpan").css("display","none");
	}
}

var chekcUser=function(changeb){
	var chekcUserFlag=true;
	if($("#username").val()==""){
		$("#userspan").html("账号不能为空");
		$("#userspan").css("display","inline");
		chekcUserFlag=false;
	}
	if(chekcUserFlag){
		$("#userspan").css("display","none");
		$(changeb).attr("disabled","disabled")	;
		$.ajax({
			     type: "POST",
			     async:true,
			     url:"${ctx}/user/checkuser",
			     data:{username:$("#username").val()},
			     success: function(data){
			    	$(changeb).removeAttr("disabled");
			    	if(data.isTrue){
			    		next01();
			    	}else{
			    		$("#userspan").html("账号不存在");
			    		$("#userspan").css("display","inline");
			    	}
			     }
		});
	}
}
function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 
var changePwd=function(changeb){
	var pwdFlag=true;
	var errString="该字段不能为空";
	if($("#oldpwd").val()==""){
		$("#oldpwdSpan").html(errString);
		$("#oldpwdSpan").css("display","inline");
		pwdFlag=false;
	}else{
		$("#oldpwdSpan").css("display","none");
	}
	if($("#newpwd1").val()==""){
		$("#newpwd1Span").html(errString);
		$("#newpwd1Span").css("display","inline");
		pwdFlag=false;
	}else{
		$("#newpwd1Span").css("display","none");
	}
	if($("#newpwd2").val()==""){
		$("#newpwd2Span").html(errString);
		$("#newpwd2Span").css("display","inline");
		pwdFlag=false;
	}else{
		$("#newpwd2Span").css("display","none");
	}
	var changing=function(){
		$(changeb).val("正在修改...");
		$(changeb).attr("disabled","disabled")	;
		$(changeb).prev().attr("disabled","disabled")	;
		}
	var nochanging=function(){
		$(changeb).val("修改");
		$(changeb).prev().removeAttr("disabled");
		$(changeb).removeAttr("disabled");
	}
	changing();
	if(pwdFlag){
		if($("#newpwd1").val()!=$("#newpwd2").val()){
			$("#newpwd2Span").css("display","inline");
		}else{
			$("#oldpwdSpan").css("display","none");
			changing();
			$.ajax({
			     type: "POST",
			     async:true,
			     url:"${ctx}/user/changepwd",
			     data:{username:$("#username").val(),oldpwd:$("#oldpwd").val(), newpwd:$("#newpwd1").val()},
			     success: function(data){
			    	if(data.isTrue){
			    		var casLogoutUrl='${casLoginUrl}'+"/logout";
			    		var sec=3;
			    		var reload=function(){
			    			$("#reloadSec").text(sec);
			    			if(sec==0){
			    				window.location.href=casLogoutUrl;	
			    			}else{
			    				sec--;
			    			}
			    		}
			    		setInterval(reload,1000);
			    		$("#loginCas").click(function(){
			    			window.location.href=casLogoutUrl;	
			    		});
			    		next02();
			    		clearPwd();
			    	}else{
			    		nochanging();
			    		$("#oldpwdSpan").html(data.name);
			    		$("#oldpwdSpan").css("display","inline");
			    	}
			    	
			     }
			 });
		}
	}
}
</script>
</head>
<body>
<div class="change-psword-box container">
		<div class="change-psword-head">
			<ol class="list-unstyled">
				<li id="oneLi" class="active"><div>确认<br>用户名</div><span>1</span></li>
				<li id="updateLi"><div>修改<br>新密码</div><span>2</span></li>
				<li id="succLi"><div>完成</div><span>3</span></li>
			</ol>
		</div>
        <div>
		<div class="change-paword-con change-paword-01" style="width: 420px;">
			<form action="">
            	<div class="text-center" style="margin-top:10px;margin-bottom:53px;">
					<span class="user-img"><img src="${ctxStatic}/resource/permiss/images/user-round.jpg" alt=""></span>
                </div>
                <div class="form-group mar-tb-30 clearfix">
                	<div class="wid-20 text-right"><span class="lab-img"></span></div>
                    <div class="wid-80">
                    	<div class="wid-75">
                    		<input type="text" id="username" class="form-control bg-gray-input1" placeholder="请输入您的账号">
                        </div>
                        <div class="color-red" style="display: none" id="userspan"></div>
                    </div>             
                </div>
                <div class="form-group mar-t-58 clearfix">
                	<div class="wid-20"></div>
                    <div class="wid-60"><input type="button" class="btn form-control btn-blue" onClick="chekcUser(this);" value="下一步" /></div>
                    <div class="wid-20"></div>
                </div>
			</form>
		</div>
            <!--第一步 end-->
        <div class="change-paword-con change-paword-02" style="width: 420px;">
            <form action="">
                <div class="form-group mar-tb-30 clearfix">
                	<div class="wid-20 text-right"><span class="lab-img lab-img-two"></span></div>
                    <div class="wid-80">
                    	<div class="wid-75">
                    	<input type="password" style="display:none">
                    		<input type="password" class="form-control bg-gray-input1" id="oldpwd" placeholder="旧密码" onblur="checkOldPwd();" autocomplete="off" />
                        </div>
                        <div class="color-red" style="display: none" id="oldpwdSpan"></div>
                    </div>     
                </div>
                <div class="form-group mar-tb-30 clearfix">
                	<div class="wid-20 text-right"><span class="lab-img lab-img-two new-pawrd"></span></div>
                    <div class="wid-80">
                    	<div class="wid-75">
                    		<input type="password" class="form-control bg-gray-input1" id="newpwd1" placeholder="新密码" onblur="checkNewPwd();" autocomplete="off" />
                        </div>
                        <div class="color-red" display: none;" id="newpwd1Span"></div>
                    </div>    
                </div>
                <div class="form-group mar-tb-30 clearfix">
                	<div class="wid-20 text-right"><span class="lab-img lab-img-two new-pawrd-again"></span></div>
                    <div class="wid-80">
                    	<div class="wid-75">
                    		<input type="password" class="form-control bg-gray-input1" id="newpwd2" placeholder="确认密码" onblur="checkNewPwd();" autocomplete="off" />
                        </div>
                        <div class="color-red" display: none;" id="newpwd2Span"></div>
                    </div>    
                </div>
                <div class="form-group mar-t-58 clearfix">
                	<div class="wid-20"></div>
                    <div class="wid-60">
                    	<input type="button" class="btn form-control btn-blue wid-49 pull-left" onClick="prev02();" value="返回" />
                        <input type="button" class="btn form-control btn-blue wid-49 pull-right" onClick="changePwd(this);" value="修改" />
                    </div>
                    <div class="wid-20"></div>
                </div>
			</form>
		</div>
        <!--第二步 end-->
        <div class="change-paword-con change-paword-03" style="width: 420px;">
            <form action="">
                <div class="text-center" style="margin-top:90px;margin-bottom:108px;"><img src="${ctxStatic}/resource/permiss/images/change-password-ok.png" alt=""></div>
                <div class="form-group mar-t-58 clearfix">
                	<p class="text-center"><span id="reloadSec"></span>秒后，将自动进入跳转页面...</p>
                	<div class="wid-20"></div>
                    <div class="wid-60">	
                    	<input type="button" class="btn form-control btn-blue" id="loginCas" value="立即登录" />
                    </div>
                    <div class="wid-20"></div>
                </div>
			</form>
		</div>
        <!--第三步 end-->
        </div>
	</div>
</body>
</html>