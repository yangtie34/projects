/**
 * Created by Administrator on 2017-01-11.
 */
require("angular-route");
require("./css/test.css");
var app = angular.module("app",['ngRoute','system']);
app.config(['$routeProvider',function($routeProvider) {
    $routeProvider
        .when("/test", {
            templateUrl: "tpl/test1.html",
            reloadOnSearch: false,
            controller : "controller"
        })
        .when("/test2", {
            templateUrl: "tpl/test2.html",
            reloadOnSearch: false,
            controller : "test2Controller"
        })
        .otherwise({
            redirectTo : "/test"
        });
}]);
app.controller("controller",["$scope",function(scope){
    scope.stus=[{id:1,name:"tom"},{id:2,name:"jack"},{id:3,name:"rose"}];
    scope.showModel = false;
    scope.toggle = function(){
        console.log(scope.showModel);
        scope.showModel = !scope.showModel;
    };
    scope.clickPie = function(params){
        console.log(params);
    };
    var data = [{name:'一班',value:24},{name:'二班',value:23},{name:'三班',value:30}];
    scope.pie = {
        title: "饼状图示例",
        data : data,
        showLable: false,
        type:"pie"
    };
    scope.source = [{"mc":"今年","value":"2017","start":"2017","end":"2017"},
        {"mc":"去年","value":"2016","start":"2016","end":"2016"},
        {"mc":"近五年","value":"2013-2017","start":"2013","end":"2017"},
        {"mc":"近十年","value":"2008-2017","start":"2008","end":"2017"}];

    scope.result ={};
}]);
app.controller("test2Controller",["$scope",function(scope){
    scope.name = "guanyx";
}]);