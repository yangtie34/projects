<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户验证</title>
</head>
<body >
	<c:if test="${success==false}">
		<span style="color: red;">
			<c:out value="${message}"></c:out>
		</span>
	</c:if>
</body>
</html>