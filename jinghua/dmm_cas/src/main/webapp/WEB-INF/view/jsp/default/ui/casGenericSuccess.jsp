<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page session="false" %>
<%@ page language="java" import="com.jhnu.util.common.PropertiesUtils" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/login/img"/>
<c:set var="sysSize"  value="${fn:length(sys)}"/>
<c:set var="sysrows" value="${sysSize%4>0?sysSize/4:sysSize/4-1}"/>
<jsp:directive.include file="includes/top.jsp" />

<nav class="navbar navbar-default ">
  <div class="container">
    <header class="login-header">
      <h3><a href="#"><img src="${ctxImg }/logo.png"></a></h3>
      <a class="login-header-infor" href="#">数据挖掘分析系统</a>
      <nav>
      <a href="#">你好，${user }</a> 
      <span style="color:#999;">|</span> 
      <a href="<%=PropertiesUtils.getDBPropertiesByName("sys.dmmUrl")%>/user/changepwd/page?username=${user }">修改密码</a>
      <span style="color:#999;">|</span>
      <a href="${ctx }/logout">退出</a></nav>
    </header>
  </div>
</nav>
<div class="container">
<div class="mu-list">
   <c:forEach  varStatus="index" begin="0" end="${sysrows}">
   <div class="row">
  <c:forEach var="sy" items="${sys}" varStatus="status" begin="${index.index*4}" end="${index.index*4+3}">
    <%-- <div class="lgn-ap lgn-ap-0${status.index+1}"><a class="lgn-whlink" href="${sy.url_}" target="_blank">${sy.name}</a></div> --%>
    <div class="col-xs-3">
    <c:if test="${sy.istrue eq '1'}"> 
     <a href="${ fn:split(sy.url_,';')[0]}" target="_blank"><div class="mu-img-bg mu-img-${sy.shiroTag}">${sy.name}</div></a>
	</c:if> 
        <c:if test="${sy.istrue eq '0'}"> 
    <div class="mu-img-bg mu-img-${sy.shiroTag}">${sy.name}<span style="color:red;font-size: smaller;">(未启用)</span></div>
	</c:if>
      </div>
  </c:forEach>
   </div>
  </c:forEach>
  </div>
</div>
