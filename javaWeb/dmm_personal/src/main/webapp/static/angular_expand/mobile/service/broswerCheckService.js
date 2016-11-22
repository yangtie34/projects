/**
 * browserCheckService
 * 检测浏览器类型和设备类型
 */

system.factory('browserCheckService',function(){
    return {
		checkDevice : function() {
			if ((navigator.userAgent
					.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
				return 'mobile';
			} else {
				return 'pc';
			}
		},
		checkBrowser : function() {
			var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
			var isOpera = userAgent.indexOf("Opera") > -1;
			if (isOpera) {
				return "Opera"
			}; //判断是否Opera浏览器
			if (userAgent.indexOf("Firefox") > -1) {
				return "FF";
			} //判断是否Firefox浏览器
			if (userAgent.indexOf("Chrome") > -1) {
				return "Chrome";
			}
			if (userAgent.indexOf("Safari") > -1) {
				return "Safari";
			} //判断是否Safari浏览器
			if (userAgent.indexOf("compatible") > -1
					&& userAgent.indexOf("MSIE") > -1
					&& !isOpera) {
				return "IE";
			} //判断是否IE浏览器
		},
		checkIsWeixin : function(){
			var ua = window.navigator.userAgent.toLowerCase();
			if (ua.match(/MicroMessenger/i) == 'micromessenger') {
				return true;
			} else
				return false; 
		}
	};
});