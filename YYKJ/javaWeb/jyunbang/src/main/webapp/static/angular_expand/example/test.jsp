<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收费管理系统-查询</title>
<%
	String basePath = request.getContextPath();
    int port = request.getServerPort();
    String ip = request.getServerName();
    String projectPath = "http://"+ip+":"+port+basePath;
%>
<meta charset="UTF-8">
<link rel="shortcut icon" href="<%=projectPath%>/resource/basic/pc/image/ico3.ico" />
<!-- 导入angualr基础配置JSP -->
<jsp:include page="testTpl.jsp"></jsp:include>
<!-- import css -->
<script>
    // 其他非法路径-返回首页或指定url
    function ToHome(url){
        window.location.href = httpConfig.basePath+'/'+ (url ? url : 'index.html');
    }
    // 访问的数据不合法
    function Wrongful(){
    	alert('您访问的数据不合法！');
    }
    var base = "<%=projectPath %>";
</script>

<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/controller.js"></script>

<link type="text/css" rel="stylesheet" href="css/example.css">
<style>

</style>
</head>
<body ng-controller="controller">
<div cg-check-box-tree source="CheckBoxSource" ><!-- /comboTree -->

</div>
	<div cg-combo-box source="boxSource" result="boxResult" height="32" on-select="boxSelect($data)"></div>
	<button ng-click="changeBox()">haha</button>
	<br />
	<br />
	<br /> 
	<div id="pagition_test" style="width: 800px;">
		<table class="common_table">
			<thead>
				<tr> <th>ID</th> <th>NAME</th> <th>FLOWERS</th> <th>INCOME</th> </tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm.items | paging:vm.page.index:vm.page.size">
					<td> {{item.id}} </td>
					<td> {{item.name}} </td>
					<td> {{item.followers|number}} 	</td>
					<td> {{item.income|currency}} </td>
				</tr>
			</tbody>
		</table>
		<div style="float: right;">
			 <div pagination total-items="vm.items.length" ng-model="vm.page.index"
				max-size="10" items-per-page="vm.page.size"
				class="pagination-sm pull-right" boundary-links="true"></div>
		</div>
		<div style="float: right;padding-top: 23px;">
			 <select ng-model="vm.page.size" style="border: 1px solid #DDD;"><option value="5">5</option><option value="10">10</option><option value="20">20</option><option value="50">50</option> </select> / 每页
		</div>
		<div style="clear: both;"></div>
	</div>
	
	<hr style="margin: 20px 0px;border: 2px solid #DDD;">
	
	<div style="width: 800px;">
		<div cg-query-comm source="source" result="result">
		</div>	
	</div>
	<div style="border: 1px solid #ddd;line-height: 30px;width: 800px;margin-top: 30px;color:#666">
		<font color="gray" size="5">已选择的条件</font><br>
		{{result|json}} 
	</div>
	
		<hr style="margin: 30px 0px;border: 2px solid #DDD;">
123	
	<div style="width: 800px;"> 
		<div cg-combo-check-tree source="treeData" result="treeResult" code="'code'" treeType='zTree' checkType="checkbox" ><!-- code 是条件对应的字段代码 -->
		</div>
		{{treeResult}}
	</div>

		<hr style="margin: 30px 0px;border: 2px solid #DDD;">
		
	<div style="width: 800px;">
		<div cg-mul-query-comm source="mutiSource" result="multResult">
		</div>	
	</div>
	<div style="border: 1px solid #ddd;line-height: 30px;width: 800px;margin-top: 30px;color:#666">
		<font color="gray" size="5">已选择的条件</font><br>
		{{multResult|json}} 
	</div>
	
	
	<div style="width: 800px;">
		<div cg-custom-comm source="mutiSource" expand="true" >
		</div>	
	</div>
	
	<div style="height: 300px;"></div>
</body>
</html>
