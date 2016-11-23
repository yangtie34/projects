<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<script type="text/javascript">
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
var changePwd=function(){
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
	if(pwdFlag){
		if($("#newpwd1").val()!=$("#newpwd2").val()){
			$("#newpwd2Span").css("display","inline");
		}else{
			$("#oldpwdSpan").css("display","none");
			$.ajax({
			     type: "POST",
			     async:true,
			     url:"${ctx}/user/changepwd/"+$("#oldpwd").val()+"/"+$("#newpwd1").val(),
			     success: function(data){
			    	if(data.isTrue){
			    		$.sticky("密码修改成功&hellip;", {autoclose : 1500, position: "top-center" , type: "st-success" });
			    		$("#pwdDiv").modal('hide');
			    		$("#oldpwdSpan").css("display","none");
			    		clearPwd();
			    	}else{
			    		$("#oldpwdSpan").html("密码错误");
			    		$("#oldpwdSpan").css("display","inline");
			    	}
			    	
			     }
			 });
		}
	}
}
</script>