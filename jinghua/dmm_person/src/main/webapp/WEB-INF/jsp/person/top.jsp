<%@ page language="java"
	import="com.jhnu.syspermiss.*,com.jhnu.syspermiss.permiss.entity.User,java.util.*,com.jhnu.person.sys.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="ctxImg"
	value="${pageContext.request.contextPath}/static/resource/person/images" />
<%
	String userName = request.getUserPrincipal().getName();//得到用户名
	String rootRole = GetCachePermiss.getUserRootRole(userName);//request.getAttribute("rootRole").toString();//得到用户的角色
	User user = GetCachePermiss.findByUsername(userName);
	String qj = null;
	String qjUrl = null;
	String grUrl =null;
	Map menuMap = new LinkedHashMap();
	String[] otherUrl={"person/tea/TeaInfoAll","person/stu/StuInfoAll"};
	if(rootRole.equalsIgnoreCase("teacher")){
		qj="教职工全景";
		qjUrl="/tea/TeaQj";
		grUrl="/tea/TeaInfo";
		menuMap.put("校园生活", "/tea/TeaSchLife");
		menuMap.put("职工科研", "/tea/TeaKy");
		menuMap.put("教学活动", "/tea/TeaTeach");
		menuMap.put("学生管理", "/tea/TeaStu");
		//如果此教师是管理员
		if(false){
	       menuMap.put("教师管理统计", "/tea/manage/teaStatistics");	
	       menuMap.put("学生管理统计", "/tea/manage/stuStatistics");	
	       //menuMap.put("高基报表", "/tea/manage/Report");	
	}
	}else{
		qj="学生全景";
		qjUrl="/stu/StuQj";
		grUrl="/stu/StuInfo";
		menuMap.put("校园生活", "/stu/StuSchLife");
		menuMap.put("我的学业", "/stu/StuStudy");
	}
	
	
	String cPath = request.getRequestURL().toString();
	boolean hasOther=PersonInfo.hasOther(userName);
%>
<script>

	//var userId ="201320010119";
	//var userId ="20122036";
	var userId ="<%=user.getUsername()%>";
</script>
<link rel="stylesheet" type="text/css"
	href="${ctxStatic}/resource/person/css/jiaozhi-common.css">
<nav class="jiaozhi-header navbar-default">
	<div class="container">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${ctx}<%=qjUrl%>"><%=qj%></a>
		</div>
		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<!-- <li><a href="#">返回矿大首页</a></li> -->
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a>你好， <%=user.getReal_name()%></a></li>
				<li><a href="${ctx }/person/logout">退出</a></li>
				<!--         <li><a href="#">安全中心</a></li>
        <li><a href="#">快速注册</a></li>
        <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">修改信息 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">修改密码</a></li>
            <li><a href="#">修改个人信息</a></li>
          </ul>
        </li> -->
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>

<div class="jiaozhi-nav-bg">
	<div class="container ">
		<div class="media">
			<div class="media-left media-middle">
				<a href="${ctx}<%=grUrl%>"> <img
					src="<%=IDStarUserUtil.getPhotoByUserName(userName) %>"
					alt="姓名" class="img-circle pull-left">
				</a>
			</div>
			<div class="media-body">
				<ul class="jiaozhi-menu">
					<%
						Iterator<Map.Entry<String, String>> entries = menuMap.entrySet().iterator(); 
														boolean otherCla=false;
														for(int i=0;i<otherUrl.length;i++){
															if (cPath.indexOf(otherUrl[i])!=-1){
																otherCla=true;
															}
														}
													for (;entries.hasNext();) {  
														Map.Entry<String, String> entry = entries.next();
									if (cPath.indexOf(entry.getValue())!=-1){
					%><li role="presentation" class='menu-current'><a href='#'><%=entry.getKey()%></a>
						<%
							}else{
						
								%><li role="presentation"><a href='${ctx}<%=entry.getValue() %>'><%=entry.getKey()%></a><%
						
							}
						%></li>
					<%
						}
													/* if(cPath.indexOf(qjUrl)!=-1||cPath.indexOf(grUrl)!=-1){
														otherCla=false;
													} */
													if(hasOther){
														List<Map> listOther=PersonInfo.getOther(userName);
														if(otherCla==true){
					%>
					<li class="jiaozhi-second-hover  menu-current" role="presentation">
						<script>
							userId ="<%=request.getAttribute("id").toString()%>";
						</script> <%
 	} else {
 %>
					
					<li class="jiaozhi-second-hover" role="presentation">
						<%
							}
						%><a href="#">其他身份</a>
						<ul class="nav nav-pills nav-stacked jiaozhi-second-menu">
							<%
								for (int i = 0; i < listOther.size(); i++) {
							%>
							<li role="presentation"><a
								href="${ctx}/person/other/<%=listOther.get(i).get("ID")%>"><%=listOther.get(i).get("NAME_")%></a></li>
							<%
								}
							%>
						</ul>
					</li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
	</div>
</div>