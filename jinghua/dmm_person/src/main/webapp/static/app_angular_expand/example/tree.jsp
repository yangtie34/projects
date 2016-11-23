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
<link rel="shortcut icon" href="<%=projectPath%>/resource/dorm/images/ico3.ico" />
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
<script type="text/javascript" src="js/tree.js"></script>
<script type="text/javascript" src="js/treeData.js"></script>
<script type="text/javascript" src="js/underscore.js"></script>

<link type="text/css" rel="stylesheet" href="css/tree.css">
<style>

</style>
</head>
<body ng-controller="controller">
<div class="tree">
  <ul>
    <li cg-tree="node in children of country" ng-class="{closed:vm.tree.isFolded(country)}">
      <div>
        <input type="checkbox" ng-model="country.checked" ng-change="vm.tree.check(country, country.checked)"
               ng-class="{intermediate: vm.tree.isSemiChecked(country)}"/>
          
          <span ng-click="vm.tree.toggleFold(country)">
            <span class="glyphicon" ng-class="vm.tree.isFolded(country)?'glyphicon-plus':'glyphicon-minus'"></span>
            {{country.mc}}
          </span>
      </div>
      
      <ul ng-class="{hidden: vm.tree.isFolded(country)}">
        <li ng-repeat="province in country.children" ng-class="{closed:vm.tree.isFolded(province)}">
          <li cg-treecurse></li> 
        </li>
      </ul>
    </li>
  </ul>



	<ul>
	    <li cg-tree="node in children of vm.countries" ng-class="{closed:vm.tree.isFolded(vm.countries)}">
	      <a href="javascript:void(0);" ng-class="{'tree-list-leaf-icon': !node.children,'tree-list-folder-icon':node.children.length >0}" 
	     	 ng-click="cgComboTreeNodeClick(node)">
	      	<span title="{{node.mc}}">{{node.mc}}</span>
	      </a>
	      <ul class="tree-list2" ng-show="node.children.length &gt; 0">
	      	<li cg-treecurse></li> 
	      </ul>
	    </li>
		<span ng-show="selectTree.length != ($index+1)">&gt;</span>
    </ul>


</div>
</body>
</html>
