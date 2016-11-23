var app = angular.module('app', ['ngRoute','system']);
app.controller("controller",['$scope','smartStudentService','dialog','$interval',function(scope,service,dialog,$interval){
	//初始化 swiper
	scope.initSwiper = function(){
		scope.mySwiper = new Swiper('.swiper-container', {
			keyboardControl:true,
			mousewheelControl:true,
			pagination : '.swiper-pagination',
			paginationClickable : true,
			direction : 'vertical',
			onInit : function(swiper) { // Swiper2.x的初始化是onFirstInit
				swiperAnimateCache(swiper); // 隐藏动画元素
				swiperAnimate(swiper); //初始化完成开始动画
			},
			onSlideChangeEnd : function(swiper) {
				swiperAnimate(swiper); //每个slide切换结束时也运行当前slide动画
			}
		})
	}
	
	dialog.showLoading();
	//请求消费数据
	service.compareConsume().then(function(data){
		scope.consumeData = data;
		dialog.hideLoading();
		scope.initSwiper();
	});
	//请求用餐数据
	service.compareDinner().then(function(data){
		scope.dinnerData = data;
	});
	//请求借阅信息
	service.compareBook().then(function(data){
		scope.bookData = data;
	});
	//请求阅读偏好
	/*service.compareBookType().then(function(data){
		console.log(data);
	});*/
	//请求成绩信息
	service.compareScore().then(function(data){
		scope.scoreList = data;
	});
	
}]);	