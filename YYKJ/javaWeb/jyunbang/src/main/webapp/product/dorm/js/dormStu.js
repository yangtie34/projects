

app.controller("dormStuController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	var initId=0;
	 var vm = scope.vm = {};
	 vm.items=[];
	  var htt=[];
	  var httservice="dormStuService?"; //学生入住情况
	  var methods=[	
	        	'getDormStuInfoByQuery',		//0通过当前条件获取住宿基本信息
	        	'getDormStuByQueryAndDrom',		//1 通过当前宿舍和条件获取子节点学生住宿情况
	        	'getQueryCode',	//2通过当前条件获取要获取的条件
	               ];
	  var getServiceData=function(){
		  for(var i=0;i<methods.length;i++){
			  htt.push({
				  service:httservice+methods[i],
				  params:[]
			  });
		 }
	};
	getServiceData();
	var getDeptData=function(method){
		http.callService({
			  service:'deptTreeService?get'+method,
			  params:['hq:dorm:dormStu:*']//:'+method+':*']
		  }).success(function(data){
			var getChildren=function(data){
				var item={};
				item.id=data.id;
				item.mc=data.name_;
				item.level=data.level_;
				item.istrue=data.istrue;
				if(data.children!=null){
					item.children=[];
					for(var i=0;i<data.children.length;i++){
						item.children.push(getChildren(data.children[i]));
					}
				}
				return item;
			};
			scope.mutiSource=null;
			var mutisource=[];
			var setCheck=function(item){
				if(item.level!=null)
				item.checked=true;
				return item;
			}
			mutisource.push({
				queryName : '所属部门',
				queryCode : "deptTeachTree",
				queryType : "comboTree",
				items : setCheck(getChildren(data))
			});
			scope.mutiSource=mutisource;
		})
		};
		getDeptData("DeptTeach");
		
		var initlist=function(){
			scope.list=[];item={};
		}
		var addForList=function(ite){
			scope.list.push(ite);
		}
		var updateForList=function(index){
			var l=angular.copy(scope.list);
			scope.list=[];
			for(var i=0;i<=index;i++){
				scope.list.push(l[i]);
			};
		}
		scope.biaoTi=function(ite,index){
			updateForList(index);
			getListByItem(ite);
		};		
		scope.daoHang=function(item){
			addForList(item);
			getListByItem(item);
		}
		scope.preItem=function(){
			var index=scope.list.length-2;
			scope.biaoTi(scope.list[index],index);
		}
		var item={};
		  var getListByItem= function(ite){
			  item=ite;
			  getAllData([1]);
	    }
  	 var getListByStu= function(name){
	        	http.callService({
	        	    	service :scope.service[0],
	        	    	params  :[name]
	    		}).success(function(data){
				scope.yxs = data;
		    });   
  	 }	
		var getvmData=function(i){
			http.callService(htt[i]).success(function(data){	
				if(i==0){
					//alert(data);
				}else if(i==1){
					if(data.length==1&&scope.list.length==0){
	    				addForList(data[0]);
	    				scope.biaoTi(data[0],0);
	    			}else if(data.length==0){
	    				alert("已至底层！");
	    			}else{
	    				scope.yxs = data;
	    			}
				}else if(i==2){
					var filter=[scope.mutiSource[0]];
					for(var j=0;j<data.length;j++){
						var items=[];
						for(var k=0;k<data[j].queryValue.length;k++){
							var item=data[j].queryValue[k];
							items.push({
								id:item.CODE,
								mc:item.NAME+"("+item.VALUE+")"
							});
						}
						data[j].items=items;
						filter.push(data[j]);
					}
					scope.mutiSource=filter;
				}
				 mask.hideLoading(); 
			  })
			};
		var getparams=function(i){
			 var params=[];
			 if(i==0||i==2){
				 params=[scope.filterResult]; 
			 }else if(i==1){
				 scope.dromservice=null;
    			 params=[scope.filterResult,{id:item.CODE||"",level_type:item.LEVEL_TYPE||""}]; 
			 }
			 htt[i].params=params;
			 getvmData(i);
	};
	var getAllData=function(m){
		for(var i=0;i<m.length;i++){
			 mask.showLoading();
			getparams(m[i]);
		}
	};
	
	var m=[0,1],m1=[2];
		/*监控dept*/
		scope.$watch('filterResult',function(val1,val2){
			if(val1==null||val1.length==0)return; 
			 	initlist();
			getAllData(m1);getAllData(m);
		},true);
}]);
