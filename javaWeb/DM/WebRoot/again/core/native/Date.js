/**
 * 
 * 日期工具类
 * 
 * @author haw_king
 * @class  NS.Date
 */
NS.Date = {
	/**
	 * 返回当前时间戳(毫秒)
	 * 
	 * @return {Number} 当前时间戳
	 */
	now : Date.now || function() {
		return +new Date();
	},
	/**
	 * 
	 * 转换为字符串
	 * 
	 * @param {Date} date
	 * @returns {String}
	 */
	toString : function(date) {
		if (date instanceof Date) {
			return date.toString();
		} else if (date instanceof String) {
			return date;
		}else{
			return new String(date);
		}
	},
	/**
	 * 
	 * 得到与过去时间差
	 * 
	 */
	getElapsed : function() {

	},
	/**
	 * 
	 * 得到今天日期（Y-m-d）
	 * 
	 *    var today = NS.Date.getToday();//当前日期
	 *     
	 * @returns {String} 返回当前日期的字符串表示 默认无参数 
	 */
	getToday : function() {
		var arg = arguments[0], year, month, _date;
		if (arg) {
			year = arg.year;
			month = arg.month;
			_date = arg.date;
		} else {
			var d = new Date();
			year = d.getYear() + 1900;
			month = d.getMonth() + 1;
			_date = d.getDate();
		}
		month = month < 10 ? ('0' + month) : month;
		_date = _date < 10 ? ('0' + _date) : _date;
		return year + '-' + month + '-' + _date;
	},
	/**
	 * 
	 * 得到指定或当前日期的前后相距天数的日期
	 * 
	 *     NS.Date.beforeOrAfterDate(-1,'2013-01-31');//'2013-01-30'
	 *     NS.Date.beforeOrAfterDate(1,'2013-01-31');//'2013-02-01'
	 *     
	 * @param {Number} numberDays 
	 *            天数(-31~31)如果超过这个范围 暂不考虑
	 *            正数指向后相距的天数，负数指向前相距的天数
	 * @param {String} date 
	 *            日期 暂定格式'Y-m-d';
	 * @returns {String} 返回指定日期的前后指定天数的日期，默认不填写日期为当前日期
	 */
	beforeOrAfterDate : function(numberDays, date) {
		var year, month, _date;
		if (!date) {
			date = new Date();
			year = date.getYear() + 1900;
			month = date.getMonth() + 1;
			_date = date.getDate();
		} else {
			year = this.getYear(date);
			month = this.getMonth(date);
			_date = this.getDate(date);
		}

		if (-31 < numberDays && numberDays < 0) {
			// 负数则指向前numberDays天
			_date = _date + numberDays;// 如果为负数 则加上 上一个月份的天数（判断平年或者闰年）以及月份减1
			// 如果月份减1后小于0 则加上12 ， 年份减一
			if (_date <= 0) {
				_date = 12 + _date;
				year = year - 1;
			}
		} else if (numberDays >= 0 && numberDays < 31) {
			// numberDays大于等于0时
			// 包含 年份的更改 月份的更改 月份日期的更改
			// 判断月份先 如果大约这个月的天数则月份加1 再判断月份是否大于12 大于则年份加1
			_date = _date + numberDays;
			var monthDays = this.getMonthDays(date);
			if (_date > monthDays) {
				_date = _date - monthDays;
				month += 1;
				if (month > 12) {
					year += 1;
				}
			}
		} else {
			// 不符合本方法范围内
			return null;
		}
		// 根据指定日期 找出其之前的日期
		return this.getToday({
			year : year,
			month : month,
			date : _date
		});
	},
	/**
	 * 
	 * 是否是闰年
	 * 
	 * 			NS.Date.isLeapYear('2013-01-10');//false
	 *          NS.Date.isLeapYear('2012-02-29');//true 
	 * @param {String} date 
	 *            格式要求尽量是 'Y-m-d'
	 * @returns {Boolean} true表示为闰年，flase则表示为平年
	 */
	isLeapYear : function(date) {
		if (!date) {
			date = new Date();
		}
		var year = this.getYear(date);
		return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);// 根据闰年的算法所得
	},
	/**
	 * 
	 * 得到所在日期的年份
	 * 
	 *     NS.Date.getYear();//如当前年份为2013
	 * @param {String} date
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回所在日期的年份，默认返回当前年份
	 */
	getYear : function(date) {
		if (!date) {
			return (new Date()).getYear() + 1900;
		}
		return (new Date(date)).getYear() + 1900;
	},
	/**
	 * 
	 * 得到所在日期的月份
	 * 
	 *     NS.Date.getMonth();//如当前月份为 1
	 * @param {String} date
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回所传递的日期的月份，默认返回当前日期的月份
	 */
	getMonth : function(date) {
		if (!date) {
			return (new Date()).getMonth() + 1;
		}
		return Number((new Date(date)).getMonth()) + 1;
	},
	/**
	 * 
	 * 得到日期
	 *  
	 *     NS.Date.getDate();//如当前日期为30
	 * @param {String}  date
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回指定日期的所在月份的日期，默认返回当前月的日期
	 */
	getDate : function(date) {
		if (date) {
			return (new Date(date)).getDate();
		}
		return (new Date()).getDate();
	},
	/**
	 * 
	 * 得到日期下的周几
	 *  
	 *     NS.Date.getDay();//如当前为周三 即返回3
	 * @param {String} date
	 *            指定日期
	 * @returns {Number} 返回周几，默认当前周数
	 */
	getDay : function(date) {
		if (!date) {
			return (new Date()).getDay();
		}
		return (new Date(date)).getDay();
	},
	/**
	 * 
	 * 得到日期月份下的月份日期数
	 *  
	 *     NS.Date.getMonthDays();//如当前月份的天数为31
	 * @param {String} date 
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回指定日期的月份，默认当前月份下的天数
	 */
	getMonthDays : function(date) {
		if (!date) {
			date = this.getToday();
		}
		var _monthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];// 月份天数维护
		if (this.isLeapYear(date)) {
			_monthDays[1] = 29;
		}
		return _monthDays[this.getMonth(date) - 1];
	},
    /**
     *
     * @param {Date} date
     * @param {String} format Y-m-d : H:i:s
     * @returns {String|*}
     */
    format : function(date,format){
        return Ext.Date.format(date,format);
    }
};
