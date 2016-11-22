//定义一个controller
//参数分别是1 模块名 2 控制器名 3 注入对象以及控制器函数
custom.controller('app',"controller",['$scope','$animate',function($scope,$animate){
	$scope.boxSource = {
		  id :1,
		  mc: "全校",
		  children : [
		           {id:2,mc:"计算机与信息工程学院",children:[{id:3,mc:"计算机"},{id:4,mc:"软件工程",checked:true}]},
		           {id:3,mc:"体育学院",children:[{id:3,mc:'健美'},{id:4,mc:'体操'}]},
		           {id:4,mc:"建筑工程工程学院",children:[{id:3,mc:'造价'},{id:4,mc:'城市工程'}]},
		           {id:5,mc:"会计学院",children:[{id:6,mc:'国际贸易'},{id:7,mc:'银行电算'}]}]
	};
	$scope.CheckBoxSource=[
	             	      {
	             		        mc: 'a',
	            /* 		       children: [
	    	             		          {
	    	             		        	 mc: 'a1'
	    	             		          },
	    	             		          {
	    	             		        	 mc: 'a2'
	    	             		          }
	    	             		        ]*/
	             		      },
	             		        {mc:'b',
	             		        children: [
	             		          {
	             		        	 mc: 'b1'
	             		          },
	             		          {
	             		        	 mc: 'b2'
	             		          }
	             		        ]
	             		      }
	             		    ];
	$scope.boxSelect = function(data){
		console.log(data);
	};
	
	$scope.changeBox = function(){
		$scope.boxSource = {
			  id :1,
			  mc: "综合数据",
			  checked:true,
			  children : [
			           {id:2,mc:"计算机与信息工程学院"},
			           {id:3,mc:"体育学院"},
			           {id:4,mc:"建筑工程工程学院"},
			           {id:5,mc:"会计学院"}]
		  };
	};
	
//	ajax test
	 var vm = $scope.vm = {};
	 // 分页模拟数据 
	 vm.page = {
	    size: 10,
	    index: 1
	  };
	  vm.items = [];
	  var MAX_NUM = 100;
	  function rand(min, max) {
	    return min + Math.round(Math.random() * (max-min));
	  }
	  for (var i = 0; i < MAX_NUM; ++i) {
	    var id = rand(0, MAX_NUM);
	    vm.items.push({
	      id: i + 1,
	      name: 'Name' + id, // 字符串类型
	      followers: rand(0, 100 * 1000 * 1000), // 数字类型
	      income: rand(1000, 100000) // 金额类型
	    });
	  }
	  
	  
	  // 查询组件模拟数据
	  $scope.source = [{
			queryName : '收费批次',// 名称
			items : [ {	id : '2015',mc : '2015年收费'}, 
			          {	id : '2014',mc : '2014年收费'},
			          {	id : '2013',mc : '2013年收费'},
			          {	id : '2012',mc : '2012年收费'},
			          {	id : '2011',mc : '2011年收费'},
			          {	id : '2010',mc : '2010年收费'},
			          {	id : '2009',mc : '2009年收费'}
			],// 条件数据
		}, {
			queryName : '性别',// 名称
			items : [{	id : '男',mc : '男'}, 
			          {	id : '女',mc : '女'}
			         ],// 条件数据
		}, {
			queryName : '收费项目',// 名称
			items : [{	id : '2015',mc : '学费'}, 
			          {	id : '2014',mc : '住宿费'},
			          {	id : '2013',mc : '书杂费'},
			          {	id : '2012',mc : '培训费'},
			          {	id : '2011',mc : '其他'},
			          {	id : '2010',mc : '生活费'}
			        ],// 条件数据
		}, {
			queryName : '入学年级',
			items : [{id : '1',	mc : '2015级'},
			         {id : '2', mc : '2014级'},
			         {id : '3',	mc : '2013级'}
			        ],
	  } ];
	  $scope.result = [];
	  
	  
	  // combotree 模拟数据
	  $scope.treeData ={
					  id :1,
					  mc: "全校",
					  children : [
					           {id:2,mc:"计算机与信息工程学院",children:[{id:3,mc:"计算机"},{id:4,mc:"软件工程"}]},
					           {id:3,mc:"体育学院",children:[{id:3,mc:'健美'},{id:4,mc:'体操'}]},
					           {id:4,mc:"建筑工程工程学院",children:[{id:3,mc:'造价'},{id:4,mc:'城市工程'}]},
					           {id:5,mc:"会计学院",children:[{id:6,mc:'国际贸易'},{id:7,mc:'银行电算'}]}]
	  };
	  $scope.treeResult = { };
	  
	  // 复合查询组件模拟数据
	  $scope.mutiSource = [ {
		  queryName : 'select',// 名称
	       queryCode : "comboSelect",
	       queryType : "comboSelect",
			items : [{
				mc:'xiala',
				children:[ 	{ id : '2015',mc : '2015年收费'}, 
				          {	id : '2014',mc : '2014年收费'},
				          {	id : '2013',mc : '2013年收费'},
				          {	id : '2012',mc : '2012年收费'},
				          {	id : '2011',mc : '2011年收费'},
				          {	id : '2010',mc : '2010年收费'},
				          {	id : '2009',mc : '2009年收费'}
				],
			},{
				mc:'xiala1',
				children:[ 	{ id : '2015',mc : '2015年收费'}, 
				          {	id : '2014',mc : '2014年收费'},
				          {	id : '2013',mc : '2013年收费'},
				          {	id : '2012',mc : '2012年收费'},
				          {	id : '2011',mc : '2011年收费'},
				          {	id : '2010',mc : '2010年收费'},
				          {	id : '2009',mc : '2009年收费'}
				],
			}]// 条件数据
   		},{
			queryName : '收费批次',// 名称
			queryCode : "chagePatch",
			items : [ {	id : '2015',mc : '2015年收费'}, 
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
			items : [{	id : '2015',mc : '学费',checked : true}, 
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
	/*  $scope.cgComboSelectClicka=function(st) {
		  alert("");
			 $scope.myVar = !$scope.myVar;
	  };*/
}]);

app.filter('paging', function() {
	  return function (items, index, pageSize) {
	    if (!items)
	      return [];
	    var offset = (index - 1) * pageSize;
	    return items.slice(offset, parseInt(offset) + parseInt(pageSize));
	 };
}
);
