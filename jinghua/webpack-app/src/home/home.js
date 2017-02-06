require("angular-route");
require("./css/home.css");
var app = angular.module("app",['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
    $routeProvider
        .when("/home", {
            templateUrl: "tpl/home.html",
            reloadOnSearch: false,
            controller : "routeController"
        })
        .otherwise({
            redirectTo : "/home"
        });
}]);
app.controller("controller",["$scope","toastrService","echartService","dialog",function(scope,toast,echart,dialog){
    scope.person = {
        name : "sunweiguang",
        age : 27
    };

    var data = [
        {field : '一月',name : '男' ,value : 200 },
        {field : '二月',name : '男' ,value : 1400},
        {field : '三月',name : '男' ,value : 120 },
        {field : '四月',name : '男' ,value : 1230 },
        {field : '五月',name : '男' ,value : 120 },
        {field : '一月',name : '女' ,value : 320 },
        {field : '二月',name : '女' ,value : 10},
        {field : '三月',name : '女' ,value : 5220},
        {field : '四月',name : '女' ,value : 5320},
        {field : '五月',name : '女' ,value : 5230},
        {field : '一月',name : '变态' ,value : 30 },
        {field : '二月',name : '变态' ,value : 100},
        {field : '三月',name : '变态' ,value : 230},
        {field : '四月',name : '变态' ,value : 690},
        {field : '五月',name : '变态' ,value : 510},
    ] ;

    scope.columnChart =  {
        title : "柱状图",
        isSort : false,
        yAxis : "人",
        data : data,
        type :"bar"  //图表类型(bar,line,area,spline)
    };
    scope.columnClick = function(params){
        toastr.info( params);
        console.log(params)
    };
    dialog.showLoading();
    dialog.hideLoading();
    scope.partshow = false;
}]);

app.controller("routeController",["$scope","toastrService",function(scope,toastr){
    toastr.info("this is in the route controller")
}]);