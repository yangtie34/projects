<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>
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
<jsp:directive.include file="includes/top.jsp" />
<div class="container">
  <div class="row lgn-header">
    <div class="col-xs-8 text-left"><a class="lgn-br-rt" href="#"><img src="${ctxImg }/logo.png"></a>数据挖掘分析系统 </div>
    <div class="col-xs-4 text-right lgn-lh-40">您好,${user.username} | <a class="lgn-alink" href="${ctx }/logout">退出</a> | <a class="lgn-alink" href="#">帮助</a></div>
  </div>
</div>
<div id="msg" class="warn">
  <h2>Authentication Succeeded with Warnings</h2>
<c:forEach items="${messages}" var="message">
  <p class="message">${message.text}</p>
</c:forEach>

</div>

<div id="big-buttons">
 <a class="button" href="login?execution=${flowExecutionKey}&_eventId=proceed">Continue</a>
</div>

<jsp:directive.include file="includes/bottom.jsp" />
