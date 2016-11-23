<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>数据挖掘分析系统</title>
</head>
<div>
 <h3>此用户无此功能，请重新登陆...</h3>
</div>
</html>

<script language="javascript" type="text/javascript"> 
//window.location.href='$(ctx)';
setTimeout("javascript:location.href='${ctx}/logout'", 2000); 
</script>