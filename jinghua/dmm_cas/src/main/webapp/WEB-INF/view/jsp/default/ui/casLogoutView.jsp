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
<%@ page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/index/images"/>
<jsp:directive.include file="includes/top.jsp" />
<div class="container">
  <div class="row lgn-header">
    <div class="col-xs-8 text-left"><a class="lgn-br-rt" href="#"><img src="${ctxImg }/logo.png"></a>数据挖掘分析系统 </div>
    <div class="col-xs-4 text-right lgn-lh-40">您好 | <a class="lgn-alink" href="#">帮助</a></div>
  </div>
</div>
  <div id="msg" class="success">
    <h2><spring:message code="screen.logout.header" /></h2>
    <p><spring:message code="screen.logout.success" /></p>
    <p><spring:message code="screen.logout.security" /></p>
  </div>
<jsp:directive.include file="includes/bottom.jsp" />
<%  
String basePath = request.getContextPath();
int port = request.getServerPort();
String ip = request.getServerName();
String projectPath = "http://"+ip+":"+port+basePath;
/* String username=request.getUserPrincipal().getName();
 GetCachePermiss.init(username,projectPath);  */
response.sendRedirect(projectPath);  
%> 
<script language="javascript" type="text/javascript"> 
// ä»¥ä¸æ¹å¼ç´æ¥è·³è½¬
//window.location.href='$(ctx)';
// ä»¥ä¸æ¹å¼å®æ¶è·³è½¬
setTimeout("javascript:location.href='$(ctx)'", 2000); 
</script>
