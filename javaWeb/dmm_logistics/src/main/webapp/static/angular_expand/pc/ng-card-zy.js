 
jxpg.directive('cgReportTable', ['$interval',"$timeout","$compile",'mask',function($interval,$timeout,$compile,mask) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/reportTable.html',
		scope : {
			resource:"=",
		},
		link : function(scope, ele, attrs) {
			 
			var getClomns=function(){
				var codes=angular.copy(scope.resource.titles.code).split(",");
				var names=angular.copy(scope.resource.titles.name).split(",");
				var clos=[];
				for(var i=0;i<codes.length;i++){
					clos.push({
						field:codes[i],title:names[i],align:"center",valign:"middle",sortable:"true"
					});
				}
				return clos;
			}
			 scope.$watch("resource",function(){
				 if(!scope.resource)return;
				 ele.find('#reportTableDiv').html("<table id='reportTable' class='xscz-ft-14'></table>");
				 ele.find('#reportTable').bootstrapTable({
						method: 'get',
						cache: false,
						height: 400,
						striped: true,
						pagination: true,
						pageSize: scope.resource.pageSize||10,
						pageNumber:1,
						pageList: [10, 20, 50, 100, 200, 500],
						search: true,
						showColumns: true,
						showRefresh: true,
						showExport: true,
						exportTypes: ['csv','txt','xml','Ms-Excel','PDF'],
						search: true,
						clickToSelect: false,
						columns:getClomns(),
						data : scope.resource.data,
						onPageChange: function (size, number) {
							//$("#pageSizeInput").val(size);
							//$("#pageNumberInput").val(number);
							
							//var form = $('#tableForm');
							//form.action= '${base}/showReport';
							//form.submit();
		                },
						//onSort: function (name, order) {
		               // },
						//formatShowingRows: function (pageFrom, pageTo, totalRows) {
						//	return '';
		               // },
						//formatRecordsPerPage: function () {
						//	return '';
		              //  },
		                formatNoMatches: function(){
		                	return '无符合条件的记录';
		                }
					});
				 $(window).resize(function () {
					 ele.find('#reportTable').bootstrapTable('resetView');
					});
				 ele.find("#exportButton").click(function(){
					 scope.resource.func();
				 });
			  });	
			
		}
	};
}]);
