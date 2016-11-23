/**
 * @class Format
 * 格式化工具类。提供字符转换、设置默认值、截取字符串等功能。
 * @author zhangzg
 */
NS.util.Format = (function() {
	var stripTagsRE    = /<\/?[^>]+>/gi,
        stripScriptsRe = /(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)/ig,
        nl2brRe        = /\r?\n/g;
	return {
		/**
		 * 检查一个引用是否为空，如果为空将它转换成一个空字符串。
		 * @param {Object} value Reference to check
		 * @return {Object} Empty string if converted, otherwise the original value
		 */
		undef : function(value) {
			return value !== undefined ? value : "";
		},

		/**
		 * 检查引用是否为空或者未定义，如果为空或未定义则给他一个默认值。
		 * @param {Object} value 被检测的引用。
		 * @param {String} defaultValue 默认值，这个默认值一般为空字符串''。
		 * @return {String}
		 */
		defaultValue : function(value, defaultValue) {
			return value !== undefined && value !== '' ? value : defaultValue;
		},

		/**
		 * 按照指定的开始位置和长度截取字符串。截取的字符串包含起始处的字符串。
		 * @param {String} value 被截取的字符串。
		 * @param {Number} start 起始位置
		 * @param {Number} length 截取的长度
		 * @return {String} 子字符串。
		 */
		substr : function(value, start, length) {
			return String(value).substr(start, length);
		},

		/**
		 * 将传递给方法的字符串中每一个字符转成它的小写形式。
		 * @param {String} value 要被转换的字符串
		 * @return {String} result 转换后的字符串
		 */
		lowercase : function(value) {
			return String(value).toLowerCase();
		},

		/**
		 * 将传递给方法的字符串中每一个字符转成它的大写形式。
		 * @param {String} value 要被转换的字符串
		 * @return {String} The 转换后的字符串
		 */
		uppercase : function(value) {
			return String(value).toUpperCase();
		},
		/**
         * 截取所有HTML标签。
         *
         * 		var string ='<html>testStripTags</html>';
         * 		string = NS.util.format.stripTags(string);
         * 		此时string的值为testStripTags。
         * 
         * @param {Object} value 要被截取的字符串。
         * @return {String} 截取后的字符串。
         */
        stripTags : function(v) {
            return !v ? v : String(v).replace(stripTagsRE, "");
        },

        /**
         * 截取所有的&lt;Stript *>标签。
         *  
         * 		var string ='Test<script src=''>stripScripts</script>result';
         * 		string = NS.util.format.stripTags(string);
         * 		此时string的值为Testresult。
         * 
         * @param {Object} value 要被截取的字符串。
         * @return {String} 截取后的字符串。
         */
        stripScripts : function(v) {
            return !v ? v : String(v).replace(stripScriptsRe, "");
        },
        /**
         * 将给定值中的回车符换成&lt;br/>html标签。
         * @param {String} 需要被格式化的字符串。
         * @return {String} 结果字符串。
         */
        nl2br : function(v) {
            return Ext.isEmpty(v) ? '' : v.replace(nl2brRe, '<br/>');
        }
	}

})();