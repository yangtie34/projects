<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.jhnu.syspermiss.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html>
正在跳转。。。。
<%  
String basePath = request.getContextPath();
int port = request.getServerPort();
String ip = request.getServerName();
String projectPath = "http://"+ip+":"+port+basePath;
/* String username=request.getUserPrincipal().getName();
 GetCachePermiss.init(username,projectPath);  */
response.sendRedirect(projectPath+"/person/index");  
%>  
</html>
<script language="javascript" type="text/javascript"> 
//window.location.href='$(ctx)';
setTimeout("javascript:location.href='${ctx}/person/index'", 300); 
</script>