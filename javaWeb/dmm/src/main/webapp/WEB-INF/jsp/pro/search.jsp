<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
	<title>数据挖掘分析系-搜索结果</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
</head>
<body>
<%@ include file="../layouts/top.jsp"%>
	<div style="margin-left: 25%; margin-top: 15%;"  class="container">
	<table>
		<c:forEach var="list" items="${page.solrList }">
			<tr>
				<td>
					<a href="${ctx}/product/${list.id }" target="_blank">${list.name }</a><br/>
					关键字：${list.keyword } <br/>
					内容：${list.desc }
				</td>
			</tr>
		</c:forEach>
	</table>
	<nav>
	  <ul class="pagination">
	  <li>
	  	共${page.totalRows }条，每页${page.numPerPage }条
	  </li>
	  	<c:choose>  
		   <c:when test="${1 == page.currentPage}">
		   	<li class="disabled">
		        <span aria-hidden="true">&laquo;</span>
		    </li>
		   </c:when>  
		   <c:otherwise> 
		   	<li>
		      <a href="${ctx}/searchmenu/${q}/${page.currentPage-1 }" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		      </a>
		    </li>  
		   </c:otherwise>  
		</c:choose>  
	    
	    <c:forEach var="list" begin="1" end="${page.totalPages }" varStatus="status">
	    	<c:if test="${status.index == page.currentPage}">
	    		<li class="active"><a href="javascript:void(0)">${status.index}</a></li>
	    	</c:if>
	    	<c:if test="${status.index != page.currentPage}">
	    		<li><a href="${ctx}/searchmenu/${q}/${status.index}">${status.index}</a></li>
	    	</c:if >
	    </c:forEach>
	    <c:choose>  
		   <c:when test="${page.totalPages == page.currentPage}">
		   	<li class="disabled">
		        <span aria-hidden="true">&raquo;</span>
		    </li>
		   </c:when>  
		   <c:otherwise> 
		   	<li>
		      <a href="${ctx}/searchmenu/${q}/${page.currentPage+1 }" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		      </a>
		    </li>  
		   </c:otherwise>
		</c:choose> 
	  </ul>
	</nav>
	
</div>
	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
	<%@ include file="../layouts/end.jsp"%>
</body>
</html>