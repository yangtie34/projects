var app = angular.module('app', ['system'])
.controller("controller",['$scope','httpService','locationService',
    function(scope,http,location){
	var position = {};
	http.post({
		url : "wechat/signature",
		data : {
			url : window.location.href
		}
	}).then(function(data){
		data.debug = false;
		data.jsApiList=['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ',
		                'onMenuShareWeibo','onMenuShareQZone','startRecord','stopRecord',
		                'onVoiceRecordEnd','playVoice','pauseVoice','stopVoice','onVoicePlayEnd',
		                'uploadVoice','downloadVoice','chooseImage','previewImage','uploadImage',
		                'downloadImage','translateVoice','getNetworkType','openLocation',
		                'getLocation','hideOptionMenu','showOptionMenu','hideMenuItems',
		                'showMenuItems','hideAllNonBaseMenuItem','showAllNonBaseMenuItem',
		                'closeWindow','scanQRCode','chooseWXPay','openProductSpecificView',
		                'addCard','chooseCard','openCard']
		position.latitude = data.latitude; // 纬度，浮点数，范围为90 ~ -90
		position.longitude = data.longitude; // 经度，浮点数，范围为180 ~ -180。
		position.name = data.schoolName;
		position.address = data.schoolRoad;
		wx.config(data);
	});
	wx.ready(function(){
		wx.openLocation(position);
	});
}]);