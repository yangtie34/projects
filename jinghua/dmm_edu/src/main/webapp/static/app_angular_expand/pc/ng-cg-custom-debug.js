
/**
 * 自定义模块，用来引导之前定义的三个模块
 * @type {module|*}
 */
var jxpg = angular.module('jxpg',['services']);
/**
 * highchart service
 * 根据传入的config 装配成highcharts需要的 config
 * renderCommonChart //图表类型(column,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */
jxpg.factory("timeAlert",function(){
	var defalutOption={
		  "closeButton": false,
		  "debug": false,
		  "positionClass": "toast-top-center",
		  "onclick": null,
		  "showDuration": "300",
		  "hideDuration": "1000",
		  "timeOut": "2000",
		  "extendedTimeOut": "1000",
		  "showEasing": "swing",
		  "hideEasing": "linear",
		  "showMethod": "fadeIn",
		  "hideMethod": "fadeOut"
		}
return {
	success:function(massage,title){
		toastr.options=defalutOption;
		toastr['success'](massage, title);
	},
	error:function(massage,title){
		toastr.options=defalutOption;
		toastr['error'](massage, title);
	},
	info:function(massage,title){
		toastr.options=defalutOption;
		toastr['info'](massage, title);
	},
	warning:function(massage,title){
		toastr.options=defalutOption;
		toastr['warning'](massage, title);
	}
}
});
jxpg.factory('highChartService',function(){
	Highcharts.setOptions({
        chart: {
            style: {
                fontFamily:"微软雅黑" 
            }
        }
    });
    return {
        /**
         * @param configs 配置对象
         *
         *  模板 configs = {
             title : "  ",
             yAxis : "件",
             isSort : false,
             data : [{field : '一月',name : '男' ,value : 200 },{field : '二月',name : '男' ,value : 200},
                     {field : '一月',name : '女' ,value : 2020 },{field : '二月',name : '女' ,value : 2020}] ,
             type :"column"   //图表类型(column,line,area,spline,areaspline)
        }
         */
        renderCommonChart : function(configs){
            configs.isSort = configs.isSort || false;
            var isName ={} , isField = {};
            var fields = [],series = [];
            for(var i in configs.data){
                var tar = configs.data[i];
                if(!isName[tar.name]){
                    series.push({name : tar.name,data : []});
                    isName[tar.name] = true;
                }
                if(!isField[tar.field]){
                    fields.push(tar.field);
                    isField[tar.field] = true;
                }
            }
            if(configs.isSort) fields.sort(function(a,b){return a>b?1:-1;});
            var ser,fie,dat;
            for ( var j in series) {
                ser = series[j];
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < configs.data.length; m++){
                        dat = configs.data[m];
                        if(dat.name == ser.name && dat.field == fie){
                            ser.data.push(parseFloat(dat.value));
                        }
                    }
                    if (ser.data.length < k) {
                        ser.data.push(0);
                    }
                }
            }

            type = configs.type || 'column';
            var config = {
                title: {
                    text: configs.title,
                    x: -20 //center
                },
                chart: {
                    type: type
                },
                xAxis: [{
                    categories: fields,
                    crosshair: true
                }],
                yAxis: {
                    title: {
                        text: configs.yAxis
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix:' '+(configs.dw||''),
                    shared: true
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                series:series
            };
            return config;
        },
        /**
         * @param config 配置对象
         *
         * 	config = {
            title: " ",
            data : [{name: '实践课', y: 62.5},{name: '理论课', y: 37.5},{name: '生物', y: 300}],
            showLable: false}
         */
        renderPieChart : function(config){
            config.showLable = (config.showLable == null) ? true : config.showLable;
            var data = config.data;
            var result = {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: config.title,
                },
                tooltip: {
                    pointFormat: ' 数量:<b>{point.y}</b><br/> {series.name}:<b>{point.percentage:.1f}%</b>'
                },
                credits : {// 不显示highchart标记
                    enabled : false
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: config.showLable
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: "占比",
                    data: data
                }]
            };
            return  result;
        }
    };
});
/**
 * 分数到等级的转换：
 * score>=4分：优,
 * 4>score>=3分：良,
 * 3>score>=2分：中,
 * score<2分：一般,
 * 
*/
jxpg.factory("cgScoreConvertService",function(){
	return {
		getConvert:function(score){
			var result;
			if(score>=4){
				result="优";
	  		}else if(score>=3 && score<4){
	  			result="良";
	  		}else if(score>=2 && score<3){
	  			result="中";
	  		}else if(score<2){
	  			result="一般";
	  		}
			return result;
		}
	}
});/**
 * 下拉框组件
 * 
 * <div cg-combo-select source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData =  {
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
   		}
//	  $scope.treeResult = {};
 * 
 */
jxpg.directive('cgComboSelect', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/comboSelect.html',
		scope : {
			source:"=",
			result:"=",
			code : "="
		},
		link : function(scope, element, attrs) {
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			scope.packageSourceData = function(dt){
				for ( var i = 0; i < dt.length; i++) {
					for ( var j = 0; j < dt.children.length; j++) {
						dt.children[i].nodeId = nodeId++;
					}
				}
			};
			scope.selectObj = [];
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点置空
				//scope.result = {};
				angular.copy({},scope.result);
				// 当前查询条件名称
				// scope.queryName = scope.source.queryName;
				
				//选定节点以及其所有的父节点
				//scope.packageSourceData(scope.treeData);
				
				scope.selectObj=scope.treeData;
			},true);
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//设置输出结果为选定节点
				angular.copy(node,scope.result);
				delete scope.result.children;
				scope.result.queryCode = angular.copy(scope.code);
				scope.result.queryType = "comboSelect";
			};
		}
	};
}]);

jxpg.directive('cgComboBox', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/comboBox.html',
		scope : {
			source:"=",
			result:"=",
			inputClass :"@",
			height: "@",
			onSelect : "&"
		},
		link : function(scope, element, attrs) {
			scope.result = {};
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			/*scope.packageSourceData = function(dt){
				dt.nodeId = nodeId++;
				if (dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						dt.children[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.children[i]);
					}
				}
			};*/
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				//选定的节点置空
				angular.copy({},scope.result);
				//选定节点以及其所有的父节点
				//scope.packageSourceData(scope.treeData);
				scope.findeDefaultChecked(scope.treeData);
			},true);
			
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//设置输出结果为选定节点
				angular.copy(node,scope.result);
				delete scope.result.children;
				scope.onSelect({
					$data : scope.result
				});
			};
			
			
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
				var defaultCheckedHasFind = false;
				if(!dt) return;
				if (dt.checked) { 
					defaultCheckedHasFind = true;
					scope.cgComboTreeNodeClick(dt);
				}else if(dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						if (defaultCheckedHasFind) {
							defaultCheckedHasFind = false;
							break;
						}else{
							scope.findeDefaultChecked(dt.children[i]);
						}
					}
				}
			};
			scope.showBoxDetail  = false;
			scope.showDetail = function(e){
				e.stopPropagation();
				var tar = e.target;
				if(scope.showBoxDetail) return;
				scope.showBoxDetail = true;
				var hideDetail = null;
				hideDetail = function(e){
					scope.showBoxDetail = false;
					scope.$digest();
					$("html").not(tar).unbind("click",hideDetail);
				};
				$("html").not(tar).bind('click',hideDetail);
			};
		}
	};
}]);
/**
 * 查询树组件
 * 
 * <div cg-combo-tree source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData = {
//		  id :1,
//		  name: "全校",
//		  children : [
//		           {id:2,name:"计算机与信息工程学院",children:[{id:3,name:"计算机"},{id:4,name:"软件工程"}]},
//		           {id:3,name:"体育学院",children:[{id:3,name:'健美'},{id:4,name:'体操'}]},
//		           {id:4,name:"建筑工程工程学院",children:[{id:3,name:'造价'},{id:4,name:'城市工程'}]},
//		           {id:5,name:"会计学院",children:[{id:6,name:'国际贸易'},{id:7,name:'银行电算'}]}]
//	  }
//	  $scope.treeResult = {};
 * 
 */
jxpg.directive('cgComboTree', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/comboTree.html',
		scope : {
			source:"=",
			result:"=",
			code : "="
		},
		link : function(scope, element, attrs) {
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			scope.packageSourceData = function(dt){
				dt.nodeId = nodeId++;
				if (dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						dt.children[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.children[i]);
					}
				}
			};
			scope.selectTree = [];
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点置空
				//scope.result = {};
				angular.copy({},scope.result);
				// 当前查询条件名称
				// scope.queryName = scope.source.queryName;
				
				//选定节点以及其所有的父节点
				scope.packageSourceData(scope.treeData);
				
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(scope.treeData);
				scope.findeDefaultChecked(scope.treeData);
			},true);
			
			//寻找父节点,找到后将父节点塞进选定节点容器
			scope.findPanrentOfNode = function(node,tree){
				if(node.nodeId != tree.nodeId){
					if (node.parentNodeId == tree.nodeId) {
						scope.selectTree.push(tree);
						scope.findPanrentOfNode(tree,scope.treeData);
					}else{
						if (tree.children) {
							for ( var i = 0; i < tree.children.length; i++) {
								scope.findPanrentOfNode(node,tree.children[i]);
							}
						}
					}
				}
			};
			
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//置空容器
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(node);
				scope.findPanrentOfNode(node,scope.treeData);
				//由于生成的节点容器将树倒置，所以翻转容器
				scope.selectTree.reverse();
				
				//备份数据，并清空树的数据，实现点击后将树隐藏
				var treeDataBak = angular.copy(scope.selectTree);
				scope.selectTree = [];
				$interval(function() {
					//重新给树赋值
					scope.selectTree = treeDataBak;
			     }, 1,1);
				//设置输出结果为选定节点
				angular.copy(node,scope.result);
				delete scope.result.children;
				
				scope.result.queryCode = angular.copy(scope.code);
				scope.result.queryType = "comboTree";
			};
			
			var defaultCheckedHasFind = false;
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
				if (dt.checked) { 
					defaultCheckedHasFind = true;
					scope.cgComboTreeNodeClick(dt);
				}else if(dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						if (defaultCheckedHasFind) {
							defaultCheckedHasFind = false;
							break;
						}else{
							scope.findeDefaultChecked(dt.children[i]);
						}
					}
				}
			};
		}
	};
}]);
/**
 * 查询树组件
 * 
 * <div cg-combo-tree source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData = {
//		  id :1,
//		  name: "全校",
//		  children : [
//		           {id:2,name:"计算机与信息工程学院",children:[{id:3,name:"计算机"},{id:4,name:"软件工程"}]},
//		           {id:3,name:"体育学院",children:[{id:3,name:'健美'},{id:4,name:'体操'}]},
//		           {id:4,name:"建筑工程工程学院",children:[{id:3,name:'造价'},{id:4,name:'城市工程'}]},
//		           {id:5,name:"会计学院",children:[{id:6,name:'国际贸易'},{id:7,name:'银行电算'}]}]
//	  }
//	  $scope.treeResult = {};
 * 
 */
jxpg.directive('cgComboCheckTree', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/comboCheckTree.html',
		scope : {
			source:"=",
			result:"=",
			code : "=",
			treeType:"&",
			checkType:"&"
		},
		link : function(scope, element, attrs) {
			//获取属性
			scope.treeType=attrs.treetype||'comboTree';
			scope.checkType=attrs.checktype||'checkbox';
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			var isHasChecktrue=[];
			  var isHasCheck=function(data){
				  if(data.check==true){
					  isHasChecktrue.push("");
				  }else if(data.children){
					  for(var i=0;i<data.children.length;i++){
						   isHasCheck(data.children[i]);
					  }
				  }
			  };
			scope.packageSourceData = function(dt){
				dt.check=dt.check||false;
				dt.nodeId = nodeId++;
				if(dt.nodeId==0){
					dt.thelast=true;}
				
				if (dt.children&&dt.children.length>0) {
					for ( var i = 0; i < dt.children.length; i++) {
						dt.children[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.children[i]);
					}
					dt.children[dt.children.length-1].thelast=true;
				}
			};
			scope.selectTree = [];
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点置空
				scope.result = [];
				//angular.copy([],scope.result);
				// 当前查询条件名称
				// scope.queryName = scope.source.queryName;
				
				//选定节点以及其所有的父节点
				scope.packageSourceData(scope.treeData);
				if(scope.treeType=="listTree"){
					isHasCheck(scope.treeData);
					if(isHasChecktrue.length==0)scope.treeData.check=true;
				}
				nodeId = 0;isHasChecktrue=[];
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(scope.treeData);
				scope.findeDefaultChecked(scope.treeData);
				scope.cgComboCheckTreeClick(scope.treeData);
			},true);
			
			//寻找父节点,找到后将父节点塞进选定节点容器
			scope.findPanrentOfNode = function(node,tree){
				if(node.nodeId != tree.nodeId){
					if (node.parentNodeId == tree.nodeId) {
						scope.selectTree.push(tree);
						scope.findPanrentOfNode(tree,scope.treeData);
					}else{
						if (tree.children) {
							for ( var i = 0; i < tree.children.length; i++) {
								scope.findPanrentOfNode(node,tree.children[i]);
							}
						}
					}
				}
			};
			scope.switchclick=function($event,node){
				var a=$event.target;
				node.childrenShow=!node.childrenShow;
				//$(a).parent().find(">ul").toggle();
				if($(a).hasClass("root_open")){
					$(a).removeClass("root_open");
					$(a).addClass("root_close");
				}else{
					$(a).removeClass("root_close");
					$(a).addClass("root_open");
				};
			};
			//节点点击事件
			scope.cgComboCheckTreeClick = function(data){
				if(data.check==true){
					var data_=angular.copy(data);
					data_.children=true;
					scope.result.push(data_);}
				if (data.children) { 
				for(var i=0;i<data.children.length;i++){
					scope.cgComboCheckTreeClick (data.children[i]);
				}
				 }
			};
			
			scope.selectRadio=function(node){
				_checkNodeAllC(scope.treeData,false);
				node.check=true;
				scope.result=[];
				scope.cgComboCheckTreeClick(scope.treeData);
			}
			scope.selectBox=function(node){
				var ischeck=true;
				if(node.check){
					ischeck=false;
				}
				_checkNodeAllC(node,ischeck);
				parentChecked(scope.treeData);
				scope.result=[];
				scope.cgComboCheckTreeClick(scope.treeData);
			};
			scope.getParentCheck=function(node){
				return scope.nodeHasCheck(node);
			};
			//子节点是否有选中
			scope.nodeHasCheck=function(dt){
				var check=dt.check;
				if (dt.children) { 
					for(var i=0;i<dt.children.length;i++){
						check=scope.nodeHasCheck(dt.children[i]);
						if(dt.children[i].check)
							check=true;
						if(check)break;
					}
				}
				return check;
			}
			_checkNodeAllC=function(node,ischeck){
				node.check=ischeck;
				if (node.children) {
					for ( var i = 0; i < node.children.length; i++) {
						_checkNodeAllC(node.children[i],ischeck);
					}
				}
			};
			
			var defaultCheckedHasFind = false;
			//节点状态
			parentChecked = function(dt){
				var check=null;
				if (dt.children&&dt.children.length>0) { 
					for(var i=0;i<dt.children.length;i++){
						parentChecked(dt.children[i]);
						dt.children[i].check=dt.children[i].check||false;
						if(!dt.children[i].check)
							check=false;
					}
					if(check==false){dt.check=false}else{dt.check=true};
				}
				
			};
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
				if (dt.checked) { 
					defaultCheckedHasFind = true;
					scope.cgComboTreeNodeClick(dt);
				}else if(dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						if (defaultCheckedHasFind) {
							defaultCheckedHasFind = false;
							break;
						}else{
							scope.findeDefaultChecked(dt.children[i]);
						}
					}
				}
			};
		}
	};
}]);


jxpg.directive('cgMulQueryComm', function() {
	return {
		restrict : 'AE', 
		templateUrl : base
				+ '/static/app_angular_expand/pc/directives/tpl/multiQueryComm.html',
		scope : {
			source : "=",
			result : "=",
			expand : "@",
			noborder : "@"
		},
		link : function(scope, element, attrs) {
			// 当前查询条件
			scope.result = [];
			
			//树的选择结果
			scope.innerTreeResult = {};
			
			//监听树的选择结果
			scope.$watch('innerTreeResult',function(val) {
				if (val.queryCode) {
					var innerTreeHasPushed = false;
					for ( var i = 0; i < scope.result.length; i++) {
						if (scope.result[i].queryCode == scope.innerTreeResult.queryCode) {
							scope.result.splice(i,1,angular.copy(scope.innerTreeResult));
							innerTreeHasPushed = true;
						}
					}
					if (!innerTreeHasPushed) {
						scope.result.push(angular.copy(scope.innerTreeResult));
					}
				}
			}, true);
			
			// 清空查询条件
			scope.cancleQueryAll = function() {
				scope.result = [];
				for ( var i = 0; i < scope.queryArray.length; i++) {
					var item = scope.queryArray[i];
					if(scope.queryArray[i].queryType == "comboTree"){
						scope.queryArray[i].items.dataChangeDate = new Date();
					}else{
						for ( var j = 0; j < item.items.length; j++) {
							item.items[j].checked = false;
						}
					}
				}
				
			};
			scope.cancleQuery = function(obj) {
				for ( var i = 0; i < scope.result.length; i++) {
					if(scope.result[i] == obj){
						scope.result.splice(i,1);
					}
				}
				if(obj.queryType == "comboTree"){
					for ( var i = 0; i < scope.queryArray.length; i++) {
						if(scope.queryArray[i].queryCode == obj.queryCode){
							scope.queryArray[i].items.dataChangeDate = new Date();
						}
					}
				}else{
					for ( var i in scope.queryArray) {
						var item = scope.queryArray[i];
						if(obj.queryName == item.queryName){
							for ( var j = 0; j < item.items.length; j++) {
								item.items[j].checked = false;
							}
						}
					}
				}
			};

			// 查询条件组合
			scope.queryArray = angular.copy(scope.source);

			// 点击更多后，显示某组条件的每一个条件项
			scope.showAll = function(obj) {
				obj.isAll = true;
				for ( var i in obj.items) {
					obj.items[i].show = true;
				}
			};
			// 高级搜索
			scope.isExpandALL = scope.expand ? true : false;
			scope.expandALL = function() {
				scope.isExpandALL = !scope.isExpandALL;
				for ( var i = 1; i < scope.queryArray.length; i++) {
					scope.queryArray[i].isShow = !scope.queryArray[i].isShow;
				}
			};
			// change
			scope.change = function(item, condition) {
				// 改变选中
				for ( var j = 0; j < condition.items.length; j++) {
					condition.items[j].checked = false;
				}
				item.checked = true;
				// 判断item所属的条件组是否有已选条件在result数组中,如果有将新的替换旧的
				var it = {}, itHasPushed = false;
				angular.copy(item, it);
				it.queryName = condition.queryName;
				it.queryCode = condition.queryCode;
				var array = [];
				for ( var i in scope.result) {
					var tt = scope.result[i];
					// 将新的替换旧的
					if (tt.queryCode != it.queryCode) {
						array.push(tt);
					} else {
						array.push(it);
						itHasPushed = true;
					}
				}
				if (!itHasPushed) {
					array.push(it);
				}
				scope.result = array;
			};
			
			scope.$watch('result',function() {
				// 是否页面显示当前查询条件
				scope.isQuery = scope.result.length > 0 ? true : false;
			}, true);

			scope.$watch('source',function() {
				scope.queryArray = angular.copy(scope.source);
				scope.setDefaultChecked();
			}, true);
			
			scope.setDefaultChecked = function(){
				// 遍历条件组合数组，设置默认选中项和默认显示项
				for ( var i in scope.queryArray) {
					var item = scope.queryArray[i];
					// 当条件的组数大于1的时候，默认显示两组，其余的组合点击高级查询显示
					if (i < 1) {
						item.isShow = true;
					} else {
						item.isShow = scope.isExpandALL; 
					}
					// 当某组条件的数量大于5的时候，默认显示5个，其余的点击更多后展示
					item.isAll = item.items.length <= 5 ? true : false;
					if (item.queryType != "comboTree") {
						for ( var j in item.items) {
							var inner = item.items[j];
							inner.show = true;
							/*if (i < 1 && j == 0) {
								inner.checked = true;
								scope.change(inner, item);
							}*///默认第一个选中
							if (inner.checked) {
								scope.change(inner, item);
							}
							if (j >= 5) {
								inner.show = false;
							}
						}
					}
				}
				
				/*if (scope.queryArray && scope.result.length == 0 && scope.queryArray.length > 0) {
					var ot = scope.queryArray[0];
					if(ot.items && ot.items.length > 0){
						ot.items[0].checked = true;
						scope.change(ot.items[0],ot);
					}
				}*/
			};
			scope.setDefaultChecked();
			if(!scope.noborder){
				scope.noborder = true;
			}
		}
	};
});/**
 * 树指令
 * 
//*******************使用方法**********************  
//
//   <ul class="list1">
//	    <li cg-tree="node in items of treeData">
//	      <a ng-click="check(node)">{{node.name}}</a>
//	      <ul> <li cg-treecurse ></li> </ul>
//	    </li>
//	  </ul>
//
 *******************数据格式**********************
 * 
 * $scope.treeData = {
 *		  name: "Root",
 *		  items : [
 *		           {name:2,items:[{name:3},{name:4}]},
 *		           {name:5,items:[{name:6},{name:7}]}]
 * }
 ***********************************************
 */
jxpg.directive('cgTree', ['$log', function($log) {
	return {
	   restrict: 'A',
	   scope: true,
	   controller: ['$scope', '$attrs',
	     function TreeController($scope, $attrs){
		   //***** 检测指令内的参数格式是否正确   ”node in items of treeData”
		   var expression = $attrs.cgTree;
		    var match = expression.match(/^\s*([\$\w]+)\s+in\s+([\S\s]*)\s+of\s+([\S\s]*)$/);
		    if (! match) {
		      throw new Error("Expected cgTree in form of"+ " '_item_ in _collection_ of _root_' but got '" + expression + "'.");
		    }
		    //******检测结束*******
		    
		    
	       var ident = this.ident = {value: match[1],collection: match[2],root: match[3]};
	       //$log.info("Parsed '%s' as %s", $attrs.cgTree, JSON.stringify(this.ident));
	       $scope.$watch(this.ident.root, function(v){
	         $scope[ident.value] = v;
	       });
	     }
	   ],
	   // Get the original element content HTML to use as the recursive template
	   compile: function cgTreecurseCompile(element){
	     var template = element.html();
	     return {
	       // set it in the pre-link so we can use it lower down
	       pre: function cgTreePreLink(scope, iterStartElement, attrs, controller){
	         controller.template = template;
	       }
	     };
	   }
	 };
	}]).directive('cgTreecurse', ['$compile', function($compile){
	return {
	   // 必须被cgTree指令包含
	   require: "^cgTree",
	   link: function cgTreecursePostLink(scope, iterStartElement, attrs, controller) {
	     // 使用父标签传入的参数进行解析，相当于使用递归
	     var build = [
	       '<', iterStartElement.context.tagName, ' ng-repeat="',
	       controller.ident.value, ' in ',
	       controller.ident.value, '.', controller.ident.collection,
	       '">',
	       controller.template,
	       '</', iterStartElement.context.tagName, '>'];
	     var el = angular.element(build.join(''));
	     // We swap out the element for our new one and tell angular to do its
	     // thing with that.
	     iterStartElement.replaceWith(el);
	     $compile(el)(scope);
	   }
	};
}]);/**
 * 拖动指令
 *  首先熟悉drag 事件 (源)dragstart - (目)enter  -  (目)dragover  -  (目)drop/dragleave - (源)dragend
 */
jxpg.directive('cgDrag', [ 'model', '$document', '$rootScope','$parse','$timeout',function(model, $document, $root,$parse,$timeout) {
	return {
		restrict : 'A',
		scope : {
			onDropSuccess : "&",
			drag : "="
		},
		link : function(scope, element, attrs) {

			element.attr("draggable", true);
			var dragElm = element[0];
			dragElm.ondragstart = function(ev) {
				try{
					ev.dataTransfer.setData("Text",ev.target.id);
				}catch(e){}
				ev.dataTransfer.effectAllowed = "move";
				eleDrag = ev.target;
				$root.dragdata = scope.drag;
				return true;
			};
			dragElm.ondragend = function(ev) {
				//ev.dataTransfer.clearData("text");
				eleDrag = null;
				
				if(attrs.onDropSuccess && $root.dropFlag){
					scope.$apply(function(){
						scope.onDropSuccess({
							$data : scope.drag
						});
					});
				}
				$root.dropFlag = false;
				$root.dragdata = null;
				return false;
			};
		}
	};
}]);
/**
 * 接收
 */
jxpg.directive('cgDrop', [ 'model','$rootScope','$timeout', function(model,$root,$timeout) {
	return {
		restrict : 'A',
		scope : {
			onDropSuccess : "&"
		},
		link : function(scope, element, attrs) {
			var dropElm = element[0];
			//进入
			dropElm.ondragenter = function(ev) {
				//element.addClass("");
				return true;
			};
			//出去
			dropElm.ondragover = function(ev) {
				ev.preventDefault();
				return true;
			};
			//放下
			dropElm.ondrop = function(ev) {
				if(attrs.onDropSuccess){
					scope.$apply(function(){
						scope.onDropSuccess({
							$data : angular.copy($root.dragdata)
						});
					});
					$root.dropFlag = true;
				}
				//element.removeClass("");
				return false;
			};
			dropElm.ondragleave = function(ev) {
				return false;
			};
		}
	};
} ]);jxpg.controller('PaginationController', ['$scope', '$attrs', '$parse', function ($scope, $attrs, $parse) {
  var self = this,
      ngModelCtrl = { $setViewValue: angular.noop }, // nullModelCtrl
      setNumPages = $attrs.numPages ? $parse($attrs.numPages).assign : angular.noop;

  this.init = function(ngModelCtrl_, config) {
    ngModelCtrl = ngModelCtrl_;
    this.config = config;

    ngModelCtrl.$render = function() {
      self.render();
    };

    if ($attrs.itemsPerPage) {
      $scope.$parent.$watch($parse($attrs.itemsPerPage), function(value) {
        self.itemsPerPage = parseInt(value, 10);
       // $scope.selectPage(1);
         $scope.totalPages = self.calculateTotalPages();
      });
    } else {
      this.itemsPerPage = config.itemsPerPage;
    }
  };

  this.calculateTotalPages = function() {
    var totalPages = this.itemsPerPage < 1 ? 1 : Math.ceil($scope.totalItems / this.itemsPerPage);
    return Math.max(totalPages || 0, 1);
  };

  this.render = function() {
    $scope.page = parseInt(ngModelCtrl.$viewValue, 10) || 1;
  };

  $scope.selectPage = function(page) {
    if ( $scope.page !== page && page > 0 && page <= $scope.totalPages) {
      ngModelCtrl.$setViewValue(page);
      ngModelCtrl.$render();
    }
  };

  $scope.getText = function( key ) {
    return $scope[key + 'Text'] || self.config[key + 'Text'];
  };
  $scope.noPrevious = function() {
    return $scope.page === 1;
  };
  $scope.noNext = function() {
    return $scope.page === $scope.totalPages;
  };

  $scope.$watch('totalItems', function() {
    $scope.totalPages = self.calculateTotalPages();
  });

  $scope.$watch('totalPages', function(value) {
    setNumPages($scope.$parent, value); // Readonly variable

    if ( $scope.page > value ) {
      $scope.selectPage(value);
    } else {
      ngModelCtrl.$render();
    }
  });
}])

.constant('paginationConfig', {
  itemsPerPage: 10,
  boundaryLinks: false,
  directionLinks: true,
  firstText: '《',
  previousText: '〈',
  nextText: '〉',
  lastText: '》',
  rotate: true
})

.directive('pagination', ['$parse', 'paginationConfig', function($parse, paginationConfig) {
  return {
    restrict: 'EA',
    scope: {
      totalItems: '=',
      firstText: '@',
      previousText: '@',
      nextText: '@',
      lastText: '@'
    },
    require: ['pagination', '?ngModel'],
    controller: 'PaginationController',
    template:  "<ul class=\"pagination\">\n" +
	    "  <li ng-if=\"boundaryLinks\" ng-class=\"{disabled: noPrevious()}\"><a href ng-click=\"selectPage(1)\">{{getText('first')}}</a></li>\n" +
	    "  <li ng-if=\"directionLinks\" ng-class=\"{disabled: noPrevious()}\"><a href ng-click=\"selectPage(page - 1)\">{{getText('previous')}}</a></li>\n" +
	    "  <li ng-repeat=\"page in pages track by $index\" ng-class=\"{active: page.active}\"><a href ng-click=\"selectPage(page.number)\">{{page.text}}</a></li>\n" +
	    "  <li ng-if=\"directionLinks\" ng-class=\"{disabled: noNext()}\"><a href ng-click=\"selectPage(page + 1)\">{{getText('next')}}</a></li>\n" +
	    "  <li ng-if=\"boundaryLinks\" ng-class=\"{disabled: noNext()}\"><a href ng-click=\"selectPage(totalPages)\">{{getText('last')}}</a></li>\n" +
	    "</ul>",
    replace: true,
    link: function(scope, element, attrs, ctrls) {
      var paginationCtrl = ctrls[0], ngModelCtrl = ctrls[1];

      if (!ngModelCtrl) {
         return; // do nothing if no ng-model
      }

      // Setup configuration parameters
      var maxSize = angular.isDefined(attrs.maxSize) ? scope.$parent.$eval(attrs.maxSize) : paginationConfig.maxSize,
          rotate = angular.isDefined(attrs.rotate) ? scope.$parent.$eval(attrs.rotate) : paginationConfig.rotate;
      scope.boundaryLinks = angular.isDefined(attrs.boundaryLinks) ? scope.$parent.$eval(attrs.boundaryLinks) : paginationConfig.boundaryLinks;
      scope.directionLinks = angular.isDefined(attrs.directionLinks) ? scope.$parent.$eval(attrs.directionLinks) : paginationConfig.directionLinks;

      paginationCtrl.init(ngModelCtrl, paginationConfig);

      if (attrs.itemsPerPage) {
        scope.$parent.$watch($parse(attrs.itemsPerPage), function(value) {
        	scope.selectPage(1);
        });
      }

      // Create page object used in template
      function makePage(number, text, isActive) {
        return {
          number: number,
          text: text,
          active: isActive
        };
      }

      function getPages(currentPage, totalPages) {
        var pages = [];
        // Default page limits
        var startPage = 1, endPage = totalPages;
        var isMaxSized = ( angular.isDefined(maxSize) && maxSize < totalPages );

        // recompute if maxSize
        if ( isMaxSized ) {
          if ( rotate ) {
            // Current page is displayed in the middle of the visible ones
            startPage = Math.max(currentPage - Math.floor(maxSize/2), 1);
            endPage   = startPage + maxSize - 1;

            // Adjust if limit is exceeded
            if (endPage > totalPages) {
              endPage   = totalPages;
              startPage = endPage - maxSize + 1;
            }
          } else {
            // Visible pages are paginated with maxSize
            startPage = ((Math.ceil(currentPage / maxSize) - 1) * maxSize) + 1;

            // Adjust last page if limit is exceeded
            endPage = Math.min(startPage + maxSize - 1, totalPages);
          }
        }

        // Add page number links
        for (var number = startPage; number <= endPage; number++) {
          var page = makePage(number, number, number === currentPage);
          pages.push(page);
        }

        // Add links to move between page sets
        if ( isMaxSized && ! rotate ) {
          if ( startPage > 1 ) {
            var previousPageSet = makePage(startPage - 1, '...', false);
            pages.unshift(previousPageSet);
          }

          if ( endPage < totalPages ) {
            var nextPageSet = makePage(endPage + 1, '...', false);
            pages.push(nextPageSet);
          }
        }

        return pages;
      }

      var originalRender = paginationCtrl.render;
      paginationCtrl.render = function() {
        originalRender();
        if (scope.page > 0 && scope.page <= scope.totalPages) {
          scope.pages = getPages(scope.page, scope.totalPages);
        }
      };
    }
  };
}])

.constant('pagerConfig', {
  itemsPerPage: 10,
  previousText: '« Previous',
  nextText: 'Next »',
  align: true
})

.directive('pager', ['pagerConfig', function(pagerConfig) {
  return {
    restrict: 'EA',
    scope: {
      totalItems: '=',
      previousText: '@',
      nextText: '@'
    },
    require: ['pager', '?ngModel'],
    controller: 'PaginationController',
    template: 
    	"<ul class=\"pager\">\n" +
	    "  <li ng-class=\"{disabled: noPrevious(), previous: align}\"><a href ng-click=\"selectPage(page - 1)\">{{getText('previous')}}</a></li>\n" +
	    "  <li ng-class=\"{disabled: noNext(), next: align}\"><a href ng-click=\"selectPage(page + 1)\">{{getText('next')}}</a></li>\n" +
	    "</ul>",
    replace: true,
    link: function(scope, element, attrs, ctrls) {
      var paginationCtrl = ctrls[0], ngModelCtrl = ctrls[1];
      if (!ngModelCtrl) {
         return; // do nothing if no ng-model
      }
      scope.align = angular.isDefined(attrs.align) ? scope.$parent.$eval(attrs.align) : pagerConfig.align;
      paginationCtrl.init(ngModelCtrl, pagerConfig);
    }
  };
}]);

/*星星评分控件。
 *  eg:
 *  scope.value=Number;
 *  scope.max=Number;
 *  <any cg-star-rating cg-value="value" cg-max="max"></any><!--最小使用-->
 *  eg:
 *  <any cg-star-rating cg-value="value" cg-max="max" size="md" isreadonly="true"></any>
 *  ----------
 *  size可用值为：xl, lg, md, sm, or xs. Defaults to xs
 */
jxpg.directive("cgStarRating", [ function() {
	return {
		restrict : "AE",
		replace : true,
		template : "<input type=\"number\" class=\"rating\" />",
		scope : {
			cgMax : "=",//最大值（星星的个数）
			cgValue : "=" //选中的星星的个数
		},
		link : function(scope, element, iAttrs) {
			scope.cgValue = scope.cgValue || 0;//设置默认值为0
			scope.cgMax = scope.cgMax || 5;
			element.val(scope.cgValue);//设置input的值
			//属性默认值
			var readonly = iAttrs.isreadonly || "false";
			var size = iAttrs.size || "xs";
			//生成星星控件
			var readonly_value = readonly == "true" ? true : false;
			var params = {//控制星星的显示属性
				min : 0,
				max : scope.cgMax,
				step : 1,
				size : size,
				stars : scope.cgMax,
				showClear : false,
				showCaption : false,
				readonly : readonly_value
			}
			$(element).rating(params);
			//响应外部scope变化
			scope.$watch("cgValue", function(newVal, oldVal) {
				$(element).rating("update", newVal);//更新值
			}, true)
			scope.$watch("cgMax", function(newVal, oldVal) {
				$(element).rating("refresh", {
					max : newVal,
					stars : newVal
				});//更新控件
			}, true)
			//触发外部scope变化
			$(element).on('rating.change', function() {
				scope.$apply(scope.cgValue = element.val());
			});
		}
	}
} ]);

/*星星评分控件。没有对传入属性进行限制,功能最大。
 * https://github.com/kartik-v/bootstrap-star-rating
 *  eg:
 *  scope.value=Number;
 *  scope.attr={min : 0,
				max : 5,
				step : 1,
				size : "xs",
				stars : 5,
				showClear : false,
				showCaption : false,
				readonly : false};//具体属性，按照https://github.com/kartik-v/bootstrap-star-rating要求为准
 *  <any cg-star-rating-full cg-value="value" cg-attr="attr"></any>
 */
jxpg.directive("cgStarRatingFull", [ function() {
	return {
		restrict : "AE",
		replace : true,
		template : "<input type=\"number\" class=\"rating\" />",
		scope : {
			cgAttr : "=",//星星控件属性配置
			cgValue : "=" //选中的星星的个数
		},
		link : function(scope, element, iAttrs) {
			scope.cgValue = scope.cgValue || 0;//设置默认值为0
			element.val(scope.cgValue);//设置input的值
			var params = {//控制星星的显示属性
				min : 0,
				max : 5,
				step : 1,
				size : "xs",
				stars : 5,
				showClear : false,
				showCaption : false,
				readonly : false
			}
			scope.cgAttr = scope.cgAttr || params;
			$(element).rating(scope.cgAttr);
			//响应外部scope变化
			scope.$watch("cgValue", function(newVal, oldVal) {
				$(element).rating("update", newVal);//更新值
			}, true)
			scope.$watch("cgAttr", function(newVal, oldVal) {
				$(element).rating("refresh", newVal);//属性改变更新控件
			}, true)
			//触发外部scope变化
			$(element).on('rating.change', function() {
				//该函数没有在angular声明周期内被调用，需用$apply通知scope变化。
				scope.$apply(scope.cgValue = element.val());
			});
		}
	}
} ]);/*************************************************
 图表指令
 ************************************************/
jxpg.directive('cgChart', ['highChartService',function (service) {
    return {
        restrict: 'AE',
        scope: {
            config: "="
        },
        link : function(scope, element, attrs) {
            scope.renderChart = function(){
                if(scope.config ){
                    if(scope.config.type){
                        switch (scope.config.type){
                            case 'column' : ;
                            case 'line':;
                            case 'area':;
                            case 'spline':;
                            case 'areaspline':
                                element.highcharts(service.renderCommonChart(scope.config));
                                break;
                            case 'pie' :
                                element.highcharts(service.renderPieChart(scope.config));
                                break;
                            default :
                                break;
                        }
                    } else{
                        scope.config = angular.extend(scope.config,{
                            credits : {         // 不显示highchart标记
                                enabled : false
                            }
                        });
                        element.highcharts(scope.config);
                    }
                }
            };
            scope.$watch("config",function(){
                scope.renderChart();
            });
        }
    };
}]);/*教学评价封装组件，非原子性组件，组合组件。
 */
/**
 * 教室看统计页面，得分区间展示组合。
 * scope.valueFrom
 * scope.valueTo
 * scope.max
 * scope.bfb
 * scope.num
 * eg:
 * <any jc-score-tj-range cg-value-from="valueFrom"
			cg-value-to="valueTo" cg-max="max"
			cg-bfb="bfb" cg-num="num"></any>
 */
jxpg.directive("jcScoreTjRange", [ function() {
	return {
		restrict : "AE",
		replace : true,
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/jcScoreTjRange.html',
		scope : {
			cgMax : "=",//最大值（星星的个数）
			cgValueFrom : "=", //起始
			cgValueTo:"=",
			cgBfb:"=",//百分比
			cgNum:"="//实际数量
		}
	}
} ]);
/**
 * 教学评估系统，头部的封装
 * eg：
 * <any jxpg-title cg-title="我是标题"></any>
 */
jxpg.directive("jxpgTitle",["$window","dialog",function($window,dialog){
	return {
		restrict:'AE',
		replace:true,
		templateUrl:base + '/static/app_angular_expand/pc/directives/tpl/jxpgTitle.html',
		scope:{
			cgTitle:'@'
		},
		link:function(scope,ele,attrs){
			scope.quit=function(){
				dialog.confirm("确定退出吗？",function(){
					var logoutUrl=base+"/loginout";
					$window.location.href=logoutUrl;
				});
			};
			scope.index=base+"/app/jxpg/index.jsp";//首页
			scope.mainSrc=base+"/resource/jxpg/image/home.png";
			scope.quitSrc=base+"/resource/jxpg/image/exit.png";
		}
	}
}]);
/*对数字分数进行转换
 *  eg:
 *  scope.value=Number;
 *  <any cg-score-convert cg-value="value"></any>
 */
jxpg.directive("cgScoreConvert", ["cgScoreConvertService", function(scoreConvertService) {
	return {
		restrict:'AE',
		replace:true,
		templateUrl:base + '/static/app_angular_expand/pc/directives/tpl/cgScoreConvert.html',
		scope:{
			cgValue:'='
		},
		link:function(scope,ele,attrs){
			var score=scope.cgValue;
			scope.order = scoreConvertService.getConvert(score);
			if(scope.order=="优"){
				scope.className="pingjiao-xs-user-pingyou";
			}else if(scope.order=="良"){
				scope.className="pingjiao-xs-user-pingliang";
			}else if(scope.order=="中"){
				scope.className="pingjiao-xs-user-pingzhong";
			}else if(scope.order=="一般"){
				scope.className="pingjiao-xs-user-pingyiban";
			}
		}
	}
}
]);
jxpg.directive("checkBoxSwitch",["$interval","$timeout",function($interval,$timeout){
	return {
		restrict:'AE',
		templateUrl:base + '/static/app_angular_expand/pc/directives/tpl/checkBoxSwitch.html',
		scope:{
			result:'=',
			state:'='
		},
		link:function(scope,ele,attrs){
			var aaa=$(".bootstrap-switch input[name='my-checkbox']")[0];
			$(".bootstrap-switch span").click(function(){
				scope.result=aaa.checked;
				$timeout();
			});
			scope.$watch("state",function(){
				if(scope.state==true&&aaa.checked==false){
				$(".bootstrap-switch span").click();
				}
				if(scope.state==false&&aaa.checked==true){
					$(".bootstrap-switch span")[0].click();
					}
			},true);
			
	}
	};
}]);

jxpg.directive("quartzCron",["$interval","$timeout",function($interval,$timeout){
	return {
		restrict:'AE',
		templateUrl:base + '/static/app_angular_expand/pc/directives/tpl/quartzCron.html',
		scope:{
			result:'=',
			source:'='
		},
		link:function(scope,ele,attrs){
			getlist=function(a,b){
				var list=[];
				for(var i=a;i<=b;i++){
					list.push(i);
				};
				return list;
				};
			scope.SecondDiv=true;
			scope.cron=
			{
					second:{
						che2:{
							inp1:1,
							inp2:2,
						},
						che3:{
							inp1:0,
							inp2:1,
						},
						che4:{
							data:getlist(0,59)
						}
					},
					min:{
						che2:{
							inp1:1,
							inp2:2,
						},
						che3:{
							inp1:0,
							inp2:1,
						},
						che4:{
							data:getlist(0,59)
						}
					},
					hour:{
						che2:{
							inp1:0,
							inp2:2,
						},
						che3:{
							inp1:0,
							inp2:1,
						},
						che4:{
							data:getlist(0,23)
						}
					},
					day:{
						che3:{
							inp1:1,
							inp2:2,
						},
						che4:{
							inp1:1,
							inp2:1,
						},
						che5:{
							inp1:1,
						},
						che7:{
							data:getlist(1,31)
						}
					},
					mouth:{
						che3:{
							inp1:1,
							inp2:2,
						},
						che4:{
							inp1:1,
							inp2:1,
						},
						che5:{
							data:getlist(1,12)
						}
					},
					week:{
						che3:{
							inp1:1,
							inp2:2,
						},
						che4:{
							inp1:1,
							inp2:1,
						},
						che5:{
							inp1:1,
						},
						che6:{
							data:getlist(1,7)
						}
					},
					year:{
						che3:{
							inp1:2013,
							inp2:2014,
						},
					},
			};
			var toorl=['second','min','hour','day','mouth','week','year'];
		
			scope.$watch("source",function(){
				var source=scope.source;
				
				var l1=source.split(" ");
				if(l1.length==6){
					l1[6]=l1[5];
					l1[5]="?";
				}
				for(var k=0;k<toorl.length;k++){
					var d=l1[k];
					switch(k){
					case 0:
					case 1:
					case 2:
						if(d=="*"){
							scope.cron[toorl[k]].che="che1";
						}else if(d.split("-").length>1){
							scope.cron[toorl[k]].che="che2";
							scope.cron[toorl[k]].che2.inp1=d.split("-")[0];
							scope.cron[toorl[k]].che2.inp2=d.split("-")[1];
						}else if(d.split("/").length>1){
							scope.cron[toorl[k]].che="che3";
							scope.cron[toorl[k]].che3.inp1=d.split("/")[0];
							scope.cron[toorl[k]].che3.inp2=d.split("/")[1];
						}else if(d.split(",").length>1){
							scope.cron[toorl[k]].che="che4";
							var l2=d.split(",");
							scope.cron[toorl[k]].che4.chevalue=[];
							for(var i=0;i<l2.length;i++){
								scope.cron[toorl[k]].che4.chevalue[i]=true;
							}
						}else if(d.split("~").length>0){
							scope.cron[toorl[k]].che="che4";
							var l2=d.split("~");
							scope.cron[toorl[k]].che4.chevalue=[];
							if(l2.length==1){
								scope.cron[toorl[k]].che4.chevalue[Number(l2[0])-1]=true;
								break;
							}
							for(var i=l2[0];i<=l2[1];i++){
								scope.cron[toorl[k]].che4.chevalue[i]=true;
							}
						}
						break;
					case 3:
						if(d=="*"){
							scope.cron[toorl[k]].che="che1";
						}else if(d=="?"){
							scope.cron[toorl[k]].che="che2";
						}else if(d.split("-").length>1){
							scope.cron[toorl[k]].che="che3";
							scope.cron[toorl[k]].che3.inp1=d.split("-")[0];
							scope.cron[toorl[k]].che3.inp2=d.split("-")[1];
						}else if(d.split("/").length>1){
							scope.cron[toorl[k]].che="che4";
							scope.cron[toorl[k]].che4.inp1=d.split("/")[0];
							scope.cron[toorl[k]].che4.inp2=d.split("/")[1];
						}else if(d.charAt(d.length-1)=="W"){
							scope.cron[toorl[k]].che="che5";
							scope.cron[toorl[k]].che5.inp1=d;
						}else if(d.charAt(d.length-1)=="L"){
							scope.cron[toorl[k]].che="che6";
							scope.cron[toorl[k]].che6.inp1=d;
						}else if(d.split(",").length>1){
							scope.cron[toorl[k]].che="che7";
							var l2=d.split(",");
							scope.cron[toorl[k]].che4.chevalue=[];
							for(var i=0;i<l2.length;i++){
								scope.cron[toorl[k]].che7.chevalue[i]=true;
							}
						}else if(d.split("~").length>0){
							var l2=d.split("~");
							scope.cron[toorl[k]].che7.chevalue=[];
							if(l2.length==1){
								scope.cron[toorl[k]].che7.chevalue[Number(l2[0])-1]=true;
								break;
							}
							for(var i=l2[0];i<=l2[1];i++){
								scope.cron[toorl[k]].che7.chevalue[i]=true;
							}
						}
						break;
					case 4:
						if(d=="*"){
							scope.cron[toorl[k]].che="che1";
						}else if(d=="?"){
							scope.cron[toorl[k]].che="che2";
						}else if(d.split("-").length>1){
							scope.cron[toorl[k]].che="che3";
							scope.cron[toorl[k]].che3.inp1=d.split("-")[0];
							scope.cron[toorl[k]].che3.inp2=d.split("-")[1];
						}else if(d.split("/").length>1){
							scope.cron[toorl[k]].che="che4";
							scope.cron[toorl[k]].che4.inp1=d.split("/")[0];
							scope.cron[toorl[k]].che4.inp2=d.split("/")[1];
						}else if(d.split(",").length>1){
							scope.cron[toorl[k]].che="che5";
							var l2=d.split(",");
							scope.cron[toorl[k]].che5.chevalue=[];
							for(var i=0;i<l2.length;i++){
								scope.cron[toorl[k]].che5.chevalue[i]=true;
							}
						}else if(d.split("~").length>0){
							scope.cron[toorl[k]].che="che5";
							var l2=d.split("~");
							scope.cron[toorl[k]].che5.chevalue=[];
							if(l2.length==1){
								scope.cron[toorl[k]].che5.chevalue[Number(l2[0])-1]=true;
								break;
							}
							for(var i=l2[0];i<=l2[1];i++){
								scope.cron[toorl[k]].che5.chevalue[i]=true;
							}
						}
						break;
					case 5:
						if(d=="*"){
							scope.cron[toorl[k]].che="che1";
						}else if(d=="?"){
							scope.cron[toorl[k]].che="che2";
						}else if(d.split("/").length>1){
							scope.cron[toorl[k]].che="che3";
							scope.cron[toorl[k]].che3.inp1=d.split("-")[0];
							scope.cron[toorl[k]].che3.inp2=d.split("-")[1];
						}else if(d.split("#").length>1){
							scope.cron[toorl[k]].che="che4";
							scope.cron[toorl[k]].che4.inp1=d.split("/")[0];
							scope.cron[toorl[k]].che4.inp2=d.split("/")[1];
						}else if(d.charAt(d.length-1)=="L"){
							scope.cron[toorl[k]].che="che5";
							scope.cron[toorl[k]].che5.inp1=d;
						}else if(d.split(",").length>1){
							scope.cron[toorl[k]].che="che6";
							var l2=d.split(",");
							scope.cron[toorl[k]].che6.chevalue=[];
							for(var i=0;i<l2.length;i++){
								scope.cron[toorl[k]].che6.chevalue[i]=true;
							}
						}else if(d.split("~").length>0){
							scope.cron[toorl[k]].che="che6";
							var l2=d.split("~");
							scope.cron[toorl[k]].che6.chevalue=[];
							if(l2.length==1){
								scope.cron[toorl[k]].che6.chevalue[Number(l2[0])-1]=true;
								break;
							}
							for(var i=l2[0];i<=l2[1];i++){
								scope.cron[toorl[k]].che6.chevalue[i]=true;
							}
						}
						break;
					case 6:
						if(d=="?"){
							scope.cron[toorl[k]].che="che1";
						}else if(d=="*"){
							scope.cron[toorl[k]].che="che2";
						}else if(d.split("-").length>1){
							scope.cron[toorl[k]].che="che3";
							scope.cron[toorl[k]].che3.inp1=d.split("-")[0];
							scope.cron[toorl[k]].che3.inp2=d.split("-")[1];
						}
						break;
					}
				}
				
			},true);
			var getMapkey=function(map){
				var str="";
				for(var key in map){
					if(map[key])str+=Number(key)+1+",";
				}
				return str.substr(0,str.length-1);
			};
			var initI=0;
			
			scope.$watch("cron",function(){
				var cron1=scope.cron;
				for(var i=0;i<toorl.length;i++){
					cron1[toorl[i]].exp="";
				}
				switch(cron1.second.che){
				case'che1':
					cron1.second.exp='*';
					break;
				case'che2':
					cron1.second.exp=cron1.second.che2.inp1+'-'+cron1.second.che2.inp2;
					break;
				case'che3':
					cron1.second.exp=cron1.second.che3.inp1+'/'+cron1.second.che3.inp2;
					break;
				case'che4':
					cron1.second.exp=getMapkey(cron1.second.che4.chevalue);
					break;
				};
				switch(cron1.min.che){
				case'che1':
					cron1.min.exp='*';
					break;
				case'che2':
					cron1.min.exp=cron1.min.che2.inp1+'-'+cron1.min.che2.inp2;
					break;
				case'che3':
					cron1.min.exp=cron1.min.che3.inp1+'/'+cron1.min.che3.inp2;
					break;
				case'che4':
					cron1.min.exp=getMapkey(cron1.min.che4.chevalue);
					break;
				};
				switch(cron1.hour.che){
				case'che1':
					cron1.hour.exp='*';
					break;
				case'che2':
					cron1.hour.exp=cron1.hour.che2.inp1+'-'+cron1.hour.che2.inp2;
					break;
				case'che3':
					cron1.hour.exp=cron1.hour.che3.inp1+'/'+cron1.hour.che3.inp2;
					break;
				case'che4':
					cron1.hour.exp=getMapkey(cron1.hour.che4.chevalue);
					break;
				};
				
				switch(cron1.day.che){
				case'che1':
					cron1.day.exp='*';
					break;
				case'che2':
					cron1.day.exp='?';
					break;
				case'che3':
					cron1.day.exp=cron1.day.che3.inp1+'-'+cron1.day.che3.inp2;
					break;
				case'che4':
					cron1.day.exp=cron1.day.che4.inp1+'/'+cron1.day.che4.inp2;
					break;
				case'che5':
					cron1.day.exp=cron1.day.che5.inp1+'W';
					break;
				case'che6':
					cron1.day.exp='L';
					break;
				case'che7':
					cron1.day.exp=getMapkey(cron1.day.che7.chevalue);
					break;
				};
				
				switch(cron1.mouth.che){
				case'che1':
					cron1.mouth.exp='*';
					break;
				case'che2':
					cron1.mouth.exp='?';
					break;
				case'che3':
					cron1.mouth.exp=cron1.mouth.che3.inp1+'-'+cron1.mouth.che3.inp2;
					break;
				case'che4':
					cron1.mouth.exp=cron1.mouth.che4.inp1+'/'+cron1.mouth.che4.inp2;
					break;
				case'che5':
					cron1.mouth.exp=getMapkey(cron1.mouth.che5.chevalue);
					break;
				};
				
				switch(cron1.week.che){
				case'che1':
					cron1.week.exp='*';
					break;
				case'che2':
					cron1.week.exp='?';
					break;
				case'che3':
					cron1.week.exp=cron1.week.che3.inp1+'/'+cron1.week.che3.inp2;
					break;
				case'che4':
					cron1.week.exp=cron1.week.che4.inp1+'#'+cron1.week.che4.inp2;
					break;
				case'che5':
					cron1.week.exp=cron1.week.che5.inp1+'L';
					break;
				case'che6':
					cron1.week.exp=getMapkey(cron1.week.che6.chevalue);
					break;
				};
				switch(cron1.year.che){
				case'che1':
					cron1.year.exp='?';
					break;
				case'che2':
					cron1.year.exp='*';
					break;
				case'che3':
					cron1.year.exp=cron1.year.che3.inp1+'-'+cron1.year.che3.inp2;
					break;
				};
				cron1.exp="";
				for(var j=0;j<toorl.length;j++){
					if(initI==0){
						ele.find(".tabs-panels div.panel:eq("+j+")").find("input[type='radio']").get(0).click();
						scope.cron[toorl[j]].che="che1";
					}
					if(j==5&&(cron1[toorl[j]].exp=="?"||"*")){continue;}
					else{
					cron1.exp+=cron1[toorl[j]].exp+" ";
					}
				}
				cron1.exp=cron1.exp.substr(0,cron1.exp.length-1);
				scope.cron1=cron1;
				initI++;
				scope.result=scope.cron1.exp;
			},true);
			
			/*scope.$watch("cron1.exp",function(){
				scope.cron=scope.cron1;
			},true);
			*/
			scope.divshow=function(){
				scope.SecondDiv=false;
				scope.MinuteDiv=false;
				scope.HourDiv=false;
				scope.DayDiv=false;
				scope.MonthDiv=false;
				scope.WeekDiv=false;
				scope.YearDiv=false;
			};
			
	}
	};
}]);
/*<div date-picker result="result" dateFmt="yyyy-MM-dd" double="true" ></div>*/
jxpg.directive("datePicker",["$interval","$timeout","$compile",function($interval,$timeout,$compile){
	return {
		restrict:'AE',
		scope:{
			result:'=',
			dateFmt:'@',
			double:'@'
		},
		link:function(scope,ele,attrs){
			scope.dateFmt = scope.dateFmt || "yyyy-MM-dd";
			scope.double = scope.double!=null || false;
			var fomatDate=function(d){
				var m=d.getMonth();
				if(m<9)m='0'+(m+1);
				var day=d.getDate();
				if(day<10)day='0'+day;
				return d.getFullYear()+"-"+m+"-"+day;
			};
			scope.result={
					startTime:function(){
						var d=new Date();
						d.setMonth(d.getMonth()-3);
						d.setFullYear(2001);
						return fomatDate(d);
					}(),
					endTime:fomatDate(new Date())	
			};
			var html="";
				var id=scope.$id;
				var getResult=function(){
				scope.result=ele.find("#dp000"+id).val();
				}
			if(scope.double==true){
				//var aaa="var d5222=$dp.$('d5222"+id+"');WdatePicker({onpicked:function(){d5222"+id+".focus();},maxDate:'#F{$dp.$D(\'d5222"+id+"\')}'})";
				html+="<input type='text' value='"+scope.result.startTime+"' class='Wdate1 form-control' id='dp000"+id+"'style='width:110px;float:left'> 至 " +
							"<input type='text' value='"+scope.result.endTime+"' class='Wdate1 form-control'  id='dp001"+id+"' style='width:110px;float:left'>";
				ele.append(html);	
				var dp001fouc=function(){
					WdatePicker({el: 'dp001'+id,
						dateFmt:scope.dateFmt,
						startDate:'%y-{%M-3)-%d',
						onpicked:function(){
						scope.resul={};
						scope.resul.startTime=angular.copy(ele.find("#dp000"+id).val());
						scope.resul.endTime=angular.copy(ele.find("#dp001"+id).val());
						scope.result=scope.resul;
						$timeout();
						}});
				};
				ele.find("#dp000"+id).focus(function(){
					var dp000=$dp.$('dp000'+id);
					var dp001=$dp.$('dp001'+id);
					WdatePicker({el: 'dp000'+id,
						dateFmt:scope.dateFmt,
						startDate:'%y-%M-%d',
						onpicked:function(){
						dp001fouc()}});
				});
		
			}else{
				html+="<input type='text' class='Wdate1 form-control' id='dp000"+id+"'>";
				ele.append(html);	
				ele.find("#dp000"+id).focus(function(){
					WdatePicker({el: 'dp000'+id,dateFmt:scope.dateFmt,onpicked:function(){getResult()}});
				});
			}
			
	}
	};
}]);
/*************************************************
图表指令
************************************************/
jxpg.directive('csChart', [function () {
   return {
       restrict: 'AE',
       scope: {
           config: "="
       },
       link : function(scope, element, attrs) {
           scope.renderChart = function(){
               if(scope.config ){
                	   var myChart = echarts.init(element.context,'macarons');
                    	 myChart.setOption(scope.config);   
                     window.addEventListener("resize", function () {
                     myChart.resize();
                 });
               }
           };
           scope.$watch("config",function(){
               scope.renderChart();
           });
       }
   };
}]);
jxpg.directive('cgKcTable', function() {
	return {
		restrict : 'AE', 
		templateUrl : base
				+ '/static/app_angular_expand/pc/directives/tpl/cgKcTable.html',
		scope : {
			source : "=",
		},
		link : function(scope, element, attrs) {
			//课程名，班级名，第几星期，开始节，结束节
			var kbkeys=null;;
			scope.kbsjmap={1:8,3:10,5:14,7:16,9:19};
			var forkb=function(js,ss,data){
				var ss1=ss-4;
				if(ss>9)ss1=8;
				var lists=[];
				for(var jk=0;jk<7;jk++){
					var list=[]
				for(var j=0;j<js;j++){
					list[j]='';
					 for(var k=0;k<data.length;k++){
						 var d=data[k];
						 if(Number(d[kbkeys[2]])-1!=jk)continue;
						 if(Number(d[kbkeys[3]])!=(ss-js+j+1))continue;
						 if(Number(d[kbkeys[3]])<=ss&&Number(d[kbkeys[3]])>ss1){ 
							 list[j]=data[k];list[j].leng=Number(d[kbkeys[4]])-Number(d[kbkeys[3]])+1;
							 for(var ij=Number(d[kbkeys[3]])+1;ij<=Number(d[kbkeys[4]]);ij++){
								 list.push(null);
								 j++; 
							 }
						 }
					 }
					}
					lists.push(list);	
				}
				return lists;
			};
			 scope.$watch("source",function(){
				 var data=scope.source.data;
				 scope.kbkeys=scope.source.kbkeys;
				 kbkeys=scope.kbkeys;
				 var list=[];
					for(var i=0;i<3;i++){
						var lis={};
						lis.list=[];
						if(i==0){
							lis.title='上午';
							lis.list=forkb(4,4,data);
						}else if(i==1){
							lis.title='下午';
							lis.list=forkb(4,8,data);
						}else if(i==2){
							lis.title='晚上';
							lis.list=forkb(2,10,data);
						}
						list[i]=lis;
					}
				 scope.data=list;
	           });	
			
			
		 }
	   };
	});
jxpg.directive('cgComboNftj', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/nftj.html',
		scope : {
			result:"=",
		},
		link : function(scope, element, attrs) {
			 var thisYear=new Date().getFullYear();
			 scope.years=[[],[]];
			 for(var i=thisYear;i>=thisYear-30;i--){
				 scope.years[0].push(i);
				 scope.years[1].push(i);
			 };
			 scope.year=[];
			 scope.cliclickId=function(i){
				 scope.clickId=i;
			 }
			 scope.yearclick=function(index,i){
				 scope.year[index]=i;
				 if(index==0){
					 scope.year[2]=i;
				 }else{
					 scope.clickId=100;
				 }
			 }
			 var getRes=function(){
				 scope.result={start:scope.year[0],end:scope.year[1]};
				 scope.year[2]=null;
			 };
			 scope.$watch("clickId",function(){
				 switch(scope.clickId){
				 case 0:
					 scope.year[0]=thisYear;
					 scope.year[1]=thisYear;
					 break;
				 case 1:
					 scope.year[0]=thisYear-1;
					 scope.year[1]=thisYear-1;
					 break;
				 case 5:
					 scope.year[0]=thisYear-5;
					 scope.year[1]=thisYear;
					 break;
				 case 10:
					 scope.year[0]=thisYear-10;
				 scope.year[1]=thisYear;
					 break;
				 case 20:
					 scope.year[0]=thisYear-20;
					 scope.year[1]=thisYear;
					 break;
				/* default:
					 scope.result={start:year[0]-20;end:year[1]};*/
				 break;
				 }
				 getRes();
			  });	
			 
			 scope.$watch("year[2]",function(){
				 if(scope.year[2]!=null){
					 scope.years[1]=[];
					 for(var i=thisYear;i>=scope.year[0];i--){
						 scope.years[1].push(i);
					 };
					 var div2=element.find("button.dropdown-nftj:eq(1)");
					 setTimeout(function(){ div2.parent().addClass("open");},100);
					 //
					 //.trigger("click");
				 }
				
				 //div2.find(">button:eq(0)").click();
			  });
			 scope.clickId=5;
		}
	};
}]);

















