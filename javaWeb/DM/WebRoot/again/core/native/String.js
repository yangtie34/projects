/**
 * @class NS.String
 * 字符串操作扩展工具类。 注意：此类并非扩展自原生String的prototype属性。
 * @author zhangzg
 */
NS.String = (function() {
	var trimReg 		= 	/(^\s*)|(\s*$)/g,
		formatRe      	=   /\{(\d+)\}/g;
	return {
		/**
		 * 去除字符串的前后空格。
		 * @method trim
		 * @param {String} str 要被操作的字符串。
		 * @return {String} 去除前后空格后的字符串。
		 */
		trim : function(str) {
			return str.replace(trimReg, "");
		},
		/**
		 * 根据指定的CSS样式生成相应样式的文字。
		 * @method createByStyle
         * @param {String} str 需要被加上样式的字符串。
		 * @param {String} style 样式的字符串形式。
		 * @return {String} 以span标签包括的具有指定样式的字符串。
		 */
		createByStyle : function(str, style) {
			return "<span style=\"".concat(style, "\">", str, "</span>");
		},
		/**
		 * 比较2个字符串是否相等，忽略大小写。
		 * @method equals
		 * @param {String} str1 第一个字符串。
		 * @param {String} str2 第二个字符串。
		 * @return {Boolean} 相等返回true，不相等返回false。
		 */
		equals : function(str1, str2) {
			return str1.toLowerCase() == str2.toLowerCase();
		},
		/**
		 * 比较2个字符串的大小，区分大小写。
         *      第一个字符串小于第二个字符串返回 -1
         *      第一个字符串等于第二个字符串返回 0
         *      第一个字符串大于第二个字符串返回 1
		 * @method compare
		 * @param {String} str1 第一个字符串。
		 * @param {String} str2 第二个字符串。
		 * @return {Number}
		 */
		compare : function(str1, str2) {
			if (str1 < str2) {
				return -1;
			} else if (str1 == str2) {
				return 0;
			} else {
				return 1;
			}
		},
		/**
		 * 比较2个字符串的大小，不区分大小写。
		 * @method compareByLowercase
         * 第一个字符串小于第二个字符串返回 -1
         * 第一个字符串等于第二个字符串返回 0
         * 第一个字符串大于第二个字符串返回 1
		 * @param {String} str1 第一个字符串。
		 * @param {String} str2 第二个字符串。
		 * @return {Number}
		 */
		compareByLowercase : function(str1, str2) {
			return this.compare(str1.toLowerCase(), str2.toLowerCase());
		},
		/**
         * 在指定的字符串左侧追加指定的字符串。追加的几个指定的字符取决于最后的字符串长度。
         * 
         * 		var str ='the string will be added..';
         * 		NS.String.leftPad(str,28,'a');
         * 		此时str的值为aathe string will be added..
         * 
         * @param {String} string  被追加的字符串
         * @param {Number} size 返回字符串的总长度
         * @param {String} character 追加字符串，不传递该参数则为" "字符串。
         * @return {String} 经过追加后的字符串。
         */
		leftPad: function(string, size, character) {
            var result = String(string);
            character = character || " ";
            while (result.length < size) {
                result = character + result;
            }
            return result;
        },
        /**
		 * 在url字符串后追加字符串。
		 * 
		 * 		var url ='http://localhost:9000/MOSDC';
		 * 		url = NS.String.urlAppend(url,'aaa=101');
		 * 		此时url的值为 http://localhost:9000/MOSDC?aaa=101
		 * 
		 * @param {String} url 要被追加的url字符串。
		 * @param {String} string 追加到url后的字符串。
		 * @return {String} 结果字符串。
		 */
		urlAppend : function(url, string) {
			if (!NS.isEmpty(string)) {
				return url + (url.indexOf('?') === -1 ? '?' : '&') + string;
			}
			return url;
		},
        /**
         * 以首字母大写转换给定字符串。
         * @param {String} string 指定字符串。
         * @return {String} 大写开头的字符串。
         */
        capitalize: function(string) {
            return string.charAt(0).toUpperCase() + string.substr(1);
        },
        /**
         * 首字母小写转换给定字符串。
         * @param {String} string 指定字符串。
         * @return {String} 小写开头的字符串。
         */
        uncapitalize: function(string) {
            return string.charAt(0).toLowerCase() + string.substr(1);
        },
        /**
         * 如果字符串的长度超过了指定的长度，则用省略号替换后三个字符。
         * @param {String} value 要被转换的字符串。
         * @param {Number} len 允许的最大长度。
         * @param {Boolean} word 如果值为true则从断句标点符号开始补省略号。
         * @return {String} 被转化的字符串。
         */
        ellipsis: function(value, len, word) {
            if (value && value.length > len) {
                if (word) {
                    var vs = value.substr(0, len - 2),
                    index = Math.max(vs.lastIndexOf(' '), vs.lastIndexOf('.'), vs.lastIndexOf('!'), vs.lastIndexOf('?'));
                    if (index !== -1 && index >= (len - 15)) {
                        return vs.substr(0, index) + "...";
                    }
                }
                return value.substr(0, len - 3) + "...";
            }
            return value;
        },
        /**
         * Allows you to define a tokenized string and pass an arbitrary number of arguments to replace the tokens.  Each
         * token must be unique, and must increment in the format {0}, {1}, etc.  Example usage:
         * 
			    var cls = 'my-class', text = 'Some text';
			    var s = Ext.String.format('&lt;div class="{0}">{1}&lt;/div>', cls, text);
    			// s now contains the string: '&lt;div class="my-class">Some text&lt;/div>'
    	 *
         * @param {String} string The tokenized string to be formatted
         * @param {String} value1 The value to replace token {0}
         * @param {String} value2 Etc...
         * @return {String} The formatted string
         */
        format: function(format) {
            var args = NS.Array.toArray(arguments, 1);
            return format.replace(formatRe, function(m, i) {
                return args[i];
            });
        },
        /**
         * 对format的扩展(本想更改原生方法,但后来想想就不动了)
         * 更改办法:
         *    var args_ = arguments[1];//但如果里面包含数组和字符对象的话,就不好了
         *    if(args_不是数组){
         *       args_ = NS.Array.toArray(arguments,1);
         *    }
         * @param {String} str 待转换字符串
         * @param {Array} arr 数组对象
         * @return {String} 转换后字符串
         */
        format2:function(str,arr){
        	return str.replace(formatRe, function(m, i) {
                return arr[i];
            });
        },
		/**
		 * 是否字符串以指定字符串开始。
		 * @method isStartWith
		 * @param {String} str 被判断的字符串。
		 * @param {String} subStr 判断字符串。
		 * @return {Boolean} 以指定字符串开头则返回true 否则返回false
		 */
		isStartWith : function(str, subStr) {
			return str.substr(0, subStr.length) == subStr;
		},
		/**
		 * 是否字符串以指定字符串结束。
		 * @method isEndWith
		 * @param {String} str 被判断的字符串。
		 * @param {String} subStr 判断字符串。
		 * @return {Boolean} 以指定字符串结尾则返回true 否则返回false
		 */
		isEndWidth : function(str, subStr) {
			return str.substr(str.length - subStr.length) == subStr;
		},
        /***************************************************************************
         * 移除字符串中的HTML标签,并返回新的字符串
         *
         * @param {String} str 包含html标签的字符串
         * @return {String}
         */
        delHtmlTag : function(str) {
            return str.replace(/<\/?.+?>/g, "");// 去掉所有的html标记
        },
        /**
         * 首字母转换大写方法
         * @param str
         * @return
         */
        upFirstWord:function(str){
        	 return str.substr(0,1).toUpperCase().concat(str.substr(1,str.length-1));
        }
	}
})();