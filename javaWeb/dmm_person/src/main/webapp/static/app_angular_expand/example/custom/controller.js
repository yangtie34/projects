
/**
 * 定义启动模块
 * @type {module|*}
 */
var app = angular.module('app',['custom','dorm']);
custom.controller('app',"controller",['$scope','$animate',function($scope,$animate){
	  // 复合查询组件模拟数据
	  $scope.mutiSource = [ {
			queryName : '收费批次',// 名称
			queryCode : "chagePatch",
			items : [ {	id : '2015',mc : '2015年收费',checked:true}, 
			          {	id : '2014',mc : '2014年收费'},
			          {	id : '2013',mc : '2013年收费'},
			          {	id : '2012',mc : '2012年收费'},
			          {	id : '2011',mc : '2011年收费'},
			          {	id : '2010',mc : '2010年收费'},
			          {	id : '2009',mc : '2009年收费'}
			],// 条件数据
		}, {
			queryName : '性别',// 名称
			queryCode : "sex",
			items : [{	id : '男',mc : '男'}, 
			          {	id : '女',mc : '女'}
			         ],// 条件数据
		}, {
			queryName : '收费项目',// 名称
			queryCode : "chargeItems",
			items : [{	id : '2015',mc : '学费'}, 
			          {	id : '2014',mc : '住宿费'},
			          {	id : '2013',mc : '书杂费'},
			          {	id : '2012',mc : '培训费'},
			          {	id : '2011',mc : '其他'},
			          {	id : '2010',mc : '生活费'}
			        ],// 条件数据
		}, {
			queryName : '入学年级',
			queryCode : "inSchoolYear",
			items : [{id : '1',	mc : '2015级'},
			         {id : '2', mc : '2014级'},
			         {id : '3',	mc : '2013级'}
			        ],
	  }, {
			queryName : '组织机构',
			queryCode : "comboTree",
			queryType : "comboTree",
			items : {
				  id :1,
				  mc: "全校",
				  children : [
				           {id:2,mc:"计算机与信息工程学院机与信息工程学院",children:[{id:3,mc:"计算机"},{id:4,mc:"软件工程"}]},
				           {id:3,mc:"体育学院",children:[{id:3,mc:'健美'},{id:4,mc:'体操'}]},
				           {id:4,mc:"建筑工程工程学院",children:[{id:3,mc:'造价'},{id:4,mc:'城市工程'}]},
				           {id:5,mc:"会计学院",children:[{id:6,mc:'国际贸易'},{id:7,mc:'银行电算'}]}]
			  }
	  } ];
	  
	  $scope.onChange = function(data){
		  console.log(data);
	  };
}]);