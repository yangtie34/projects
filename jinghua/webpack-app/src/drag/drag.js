/**
 * Created by Administrator on 2017-1-11.
 */
var app = angular.module("app",['system']);
require("./service.js")(app)
app.controller("controller",["$scope","service",function($scope,service){
    //拖动指令的测试数据
    $scope.dragList = [{
        id : 1,
        name : "第一个"
    },{
        id : 2,
        name : '第二个'
    }];

    $scope.dragTarList = [{
        id : 3,
        name : "第三个"
    },{
        id : 4,
        name : '第四个'
    }];

    $scope.successHandler = function(data,list){
        list.push(data);
    };

    $scope.dropSuccessHandler = function(pp,list){
        for(var i in list){
            var item = list[i];
            if(item.id == pp.id){
                list.splice(i,1);
                break;
            }
        }
    };
    alert(service.add(1,3))
}])