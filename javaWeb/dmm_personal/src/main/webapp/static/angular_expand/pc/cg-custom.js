
/**
 * 自定义模块，用来引导之前定义的三个模块
 * @type {module|*}
 */
var system = angular.module('system',[]);
/**
 * echartService
 * 根据传入的config 装配成echartService需要的 config
 * renderCommonChart //图表类型(bar,line,area,spline,areaspline)
 * renderPieChart   // 饼状图
 */

system.factory('echartService',function(){
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
            type = configs.type || 'column';
            var isName ={} , isField = {};
            var fields = [],series = [],legendData=[];
            for(var i in configs.data){
                var tar = configs.data[i];
                if(!isName[tar.name]){
                	legendData.push(tar.name);
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
                ser.type = type;
                if(ser.type == 'area'){
                	ser.type = 'line';
                	ser.areaStyle = {normal: {}};
                }
                if(ser.type == 'spline'){
                	ser.type = 'line';
                	ser.smooth = true;
                }
                if(ser.type == 'areaspline'){
                	ser.type = 'line';
                	ser.smooth = true;
                	ser.areaStyle = {normal: {}};
                }
                
                for ( var k = 0; k < fields.length; k++) {
                    fie = fields[k];
                    for(var m = 0; m < configs.data.length; m++){
                        dat = configs.data[m];
                        if(dat.name == ser.name && dat.field == fie){
                            ser.data.push(parseFloat(dat.value));
                        }
                    }
                    if (ser.data.length < k){
                        ser.data.push(0);
                    }
                }
            };
            var axisLabelRotate = 0;
            if(fields.length > 5){
            	axisLabelRotate = 45;
            }
            
            // 指定图表的配置项和数据
            var config = {
                title: {
                    text: configs.title,
                    left : 'center'
                },
                grid: {
                	 top : "40px",
                	 left: '1%',
                     right: '1%',
                     bottom: '30px',
                     containLabel: true
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : { // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    left: 'center',
                    bottom : 'bottom',
                    data:legendData
                },
                xAxis: {
                    data: fields,
                    axisLabel:{
                    	rotate:axisLabelRotate,
                    	interval: 0
                    }
                },
                yAxis : [{
                	name : configs.yAxis,
                    type : 'value'
                }],
                series:  series
            };
            return config;
        },
        /**
         * @param config 配置对象
         *
         * 	config = {
         * 		type : 'pie',
	            title: "饼状图标题",
	            data : [{name: '苹果', value: 30},{name: '橘子', value: 20}]
	        }
         */
        renderPieChart : function(config){
    	   var data = config.data;
    	   config.showLable = (config.showLable == null) ? true : config.showLable;
        	option = {
        		    title : {
        		        text: config.title,
        		        x:'center'
        		    },
        		    grid: {
	                   	top : "middle",
	                   	left: '1%',
                        right: '1%',
                        containLabel: false
                   },
        		    tooltip : {
        		        trigger: 'item',
        		        formatter: "{b} <br/> 数量：{c} &nbsp;&nbsp;(占比{d}%)"
        		    },
        		    label: {
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '20',
                                fontWeight: 'bold'
                            }
                        }
                    },
        		    series : [
        		        {
        		            type: 'pie',
        		            radius : '70%',
        		            center: ['50%', '55%'],
        		            avoidLabelOverlap: false,
        		            selectedMode: 'single',
        		            data: data,
        		            itemStyle: { 
        		                emphasis: {
        		                    shadowBlur: 10,
        		                    shadowOffsetX: 0,
        		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
        		                }
        		            }
        		        }
        		    ]
        		};
        	return option;
        }
    };
});system.directive('cgComboBox', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/comboBox.html',
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
			
			var defaultCheckedHasFind = false;
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
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
system.directive('cgComboTree',['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/comboTree.html',
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
system.directive('cgMulQueryComm', function() {
	return {
		restrict : 'AE', 
		templateUrl : base
				+ '/static/angular_expand/pc/directives/tpl/multiQueryComm.html',
		scope : {
			source : "=",
			result : "=",
			expand : "@",
			noborder : "@",
			onChange : "&"
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
						scope.setCheckedOfTreedata(scope.queryArray[i].items);
						scope.queryArray[i].items.dataChangeDate = new Date();
					}else{
						for ( var j = 0; j < item.items.length; j++) {
							item.items[j].checked = false;
						}
					}
				}
			};
			
			//改变树形数据的checked属性
			scope.setCheckedOfTreedata = function(tree){
				tree.checked = false;
				if(tree.children){
					for(var i = 0; i < tree.children.length; i++) {
						var node = tree.children[i];
						scope.setCheckedOfTreedata(node);
					}
				}
			}
			
			
			scope.cancleQuery = function(obj) {
				for ( var i = 0; i < scope.result.length; i++) {
					if(scope.result[i] == obj){
						scope.result.splice(i,1);
					}
				}
				if(obj.queryType == "comboTree"){
					for ( var i = 0; i < scope.queryArray.length; i++) {
						if(scope.queryArray[i].queryCode == obj.queryCode){
							scope.setCheckedOfTreedata(scope.queryArray[i].items);
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
				scope.onChange({
					$data : scope.result
				});
			}, true);

			scope.$watch('source',function() {
				scope.cancleQueryAll();
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
system.directive('cgTree', ['$log', function($log) {
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
system.directive('cgDrag', [ '$document', '$rootScope','$parse','$timeout',function($document, $root,$parse,$timeout) {
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
				ev.dataTransfer.effectAllowed = "all";
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
}])
/**
 * 接收
 */
.directive('cgDrop', [ '$rootScope','$timeout', function($root,$timeout) {
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
} ]);system.controller('PaginationController', ['$scope', '$attrs', '$parse', function ($scope, $attrs, $parse) {
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

      if (attrs.maxSize) {
        scope.$parent.$watch($parse(attrs.maxSize), function(value) {
          maxSize = parseInt(value, 10);
          paginationCtrl.render();
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
}]);/*************************************************
 echarts图表指令
 功能介绍 ： 
	 	bar，line，area，spline，areaspline 生成柱状图和折线图
	 	数据格式 
	 	config = {
	         title : "图形标题",
	         yAxis : "Y轴单位",
	         data : [{field : '一月',name : '男' ,value : 200 },{field : '二月',name : '男' ,value : 200},
	                 {field : '一月',name : '女' ,value : 2020 },{field : '二月',name : '女' ,value : 2020}] ,
	         type :"bar"   //图表类型(bar,line,area,spline,areaspline)
	    }
	 	
	 	pie 生成饼状图
	 	数据格式 
	 	config = {
	 		type : 'pie',
	 		title: "饼状图标题",
	        data : [{name: '苹果', value: 30},{name: '橘子', value: 20}] }
 	
 height chart的高度，默认为300
 例子见 ： static/angular_expand/example/echart.jsp
 ************************************************/
system.directive('echart', ['echartService',function (service) {
    return {
        restrict: 'AE',
        scope: {
            config: "=",
            height: "@"
        },
        link : function(scope, element, attrs) {
        	if (typeof(scope.height) == "undefined") { 
        		scope.height = 300;
    		}  
    		element.height(scope.height);
            scope.renderChart = function(){
            	// 基于准备好的dom，初始化echarts实例
            	var myChart = echarts.init(element[0]);
                if(scope.config){
                	var options = {};
                    if(scope.config.type){
                        switch (scope.config.type){
                            case 'bar' : ;
                            case 'line':;
                            case 'area':;
                            case 'spline':;
                            case 'areaspline':
                            	options = service.renderCommonChart(scope.config);
                                break;
                            case 'pie' :
                            	options = service.renderPieChart(scope.config);
                            	break;
                            default :
                            	options = scope.config;
                                break;
                        }
                    } else{
                    	options = scope.config;
                    }
                    options.color = [ 
						'#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80',
						'#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
						'#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050',
						'#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
                 ]
                    myChart.setOption(options);
                }
                $(window).resize(function(){
                	myChart.resize();
                });
            };
            scope.$watch("config",function(){
                scope.renderChart();
            });
        }
    };
}]);