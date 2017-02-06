/*************************************************
 弹出层form 
 功能介绍 ： 
	  弹出form，分页显示数据
	  
	  config : {
	  	title : "",
		show : false,
		url : "",
		exportUrl : "",// 为空则不显示导出按钮
		heads : [],
		fields : [],
		params : {
			
		}
	  }
 例子见 ：static/angular_expand/example/modalForm.jsp
 ************************************************/
module.exports = function(system) {
	var tpl = require("./tpl/modalForm.html")
	system.directive('modalForm', ["httpService", 'toastrService', function (http, toastr) {
		return {
			restrict: 'AE',
			template:tpl,
			scope: {
				config: "="
			},
			link: function (scope, element, attrs) {
				//隐藏form弹出
				scope.hideModalForm = function () {
					scope.config.show = false;
				}
				//空白地方点击隐藏form
				element.click(function (e) {
					scope.hideModalForm();
					scope.$apply();
				}).find(".modal-dialog").click(function (e) {
					e.stopPropagation();
				});

				//监听显示控制，重置分页参数
				scope.$watch("config.show", function (newval) {
					if (newval == true) {
						element.find(".fade").show().scrollTop(0).addClass("in");
						scope.vm.curpage = 1;
						scope.vm.rows = [];
						scope.vm.total = 0;

					} else {
						element.find(".fade").removeClass("in").fadeOut('fast');
						scope.vm.curpage = 0;
					}
				});
				//分页参数
				scope.vm = {
					showModal: false,
					pagesize: 10,
					curpage: 0,
					rows: [],
					total: 0,
					pageTotal: 0
				};
				//监听分页参数，请求后台数据
				scope.$watch("vm.curpage", function (newval, oldval) {
					if (newval != 0) {
						scope.vm.showModal = true;
						scope.queryPageItems(scope.config.params).then(function (data) {
							scope.vm.showModal = false;
							scope.vm.rows = data.result.rows;
							scope.vm.total = data.result.total;
							scope.vm.pageTotal = parseInt(scope.vm.total % scope.vm.pagesize == 0 ? scope.vm.total / scope.vm.pagesize : scope.vm.total / scope.vm.pagesize + 1);
						})
					}
				});
				//请求数据方法
				scope.queryPageItems = function (params) {
					return http.post({
						url: scope.config.url,
						data: angular.extend({
							curpage: scope.vm.curpage,
							pagesize: scope.vm.pagesize,
							sortColumn: scope.sortColumn,
							sortOrder: scope.sortOrder
						}, params)
					});
				}
				//下载方法
				scope.download = function () {
					$.fileDownload(base + scope.config.exportUrl, {
						data: angular.extend({
							curpage: 1,
							pagesize: scope.vm.total,
							heads: angular.toJson(scope.config.heads),
							fields: angular.toJson(scope.config.fields),
							filename: scope.config.title
						}, scope.config.params),
						failCallback: function (html, url) {
							if (html.indexOf("您没有该页面的访问权限") != -1) {
								toastr.error("您没有该项权限！");
							}
						}
					});
				}
				//列排序方法
				scope.orderByColumn = function (field) {
					if (scope.sortColumn == field) {
						if (scope.sortOrder == 'asc')
							scope.sortOrder = 'desc';
						else if (scope.sortOrder == 'desc') {
							scope.sortColumn = '';
						}
					} else {
						scope.sortOrder = 'asc';
						scope.sortColumn = field;
					}
					//请求数据
					scope.vm.showModal = true;
					scope.queryPageItems(scope.config.params).then(function (data) {
						scope.vm.showModal = false;
						scope.vm.rows = data.result.rows;
					});
				}
			}
		};
	}]);
}