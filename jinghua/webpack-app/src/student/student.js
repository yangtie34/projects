/**
 * Created by Administrator on 2017-1-10.
 */
var app = angular.module("app",['system']);
app.controller("controller",["$scope",function(scope){
    scope.person={
        name : "this is student page"
    }
    console.log(14)
}])