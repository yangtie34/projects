app.service("wechatMsgService",['httpService',function(http){
	return {
		sendMassText : function(msg){
			var result = http.post({
				url : "wechat/msg/sendMassText",
				data : {
					message : msg
				}
			});
			return result;
		},
		getUserInfo : function(){
			var result = http.post({
				url : "wechat/user/getBindUserInfo",
				data : {}
			});
			return result;
		},
		getUserInfoByUsername : function(){
			var result = http.post({
				url : "wechat/user/getUserInfoByUsername",
				data : {
					username : "19901009"
				}
			});
			return result;
		}
	};
}]);