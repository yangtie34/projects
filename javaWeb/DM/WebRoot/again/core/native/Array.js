/**
 * 自定义array工具类库
 * @class NS.Array
 * @author hawking
 */
NS.Array = {
	/**
	 * 将一个元素插入一个数组中
	 * @param {Array} array 原数组
	 * @param {Number} index 插入位子索引
	 * @param {Object} value 插入的值
     * @return {Array} 新数组
	 */
	insert : function(array, index, value) {
		// this.isArray(array);
		var newArray = [];
		// 这种用法不可多用
		for ( var i = 0; i < array.length; i++) {
			if (i == index) {
				newArray.push(array[i], value);
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 循环遍历方法 fn是个匿名回调函数 包含item（Object） index allItems()三个参数
	 * 
	 * @param {Array} array 所需遍历的数组
	 * @param {Function} fn 回调函数
	 */
	each : function(array, fn) {
		for ( var i = 0; i < array.length; i++) {
			var isreturn = fn(array[i], i);
			// isreturn 如果为undefined时  默认为false
			if (isreturn == false) {
				break;
			}
		}
	},
	/**
	 * 
	 * 替换给定数组的某个下标对应的值，并返回一个新的数组
	 * 
	 * @param {Array} array 原数组
	 * @param {Number} index 索引位置
	 * @param {Object} value 替换值
	 * @return {Array}
	 */
	replace : function(array, index, value) {
		var newArray = new Array();
		for ( var i = 0; i < array.length; i++) {
			if (index == i) {
				newArray.push(value);
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 判断一个数组中是否包含某个元素
	 * 
	 * @param {Array} array 数组
	 * @param {Object}  item 判断的元素
	 * @return {Boolean}
	 */
	contains : function(array, item) {
		for ( var i = 0; i < array.length; i++) {
			var value = array[i];
			if (value == item) {
				return true;
			}
		}
		return false;
	},
	/**
	 * 过滤数组元素中的空元素
	 * 
	 * @param {Array} array 数组
	 * @return {Array}
	 */
	clean : function(array) {
		var results = [], i = 0, ln = array.length, item;
		for (; i < ln; i++) {
			item = array[i];
			if (!this.isEmpty(item)) {
				results.push(item);
			}
		}
		return results;
	},
	/**
	 * 克隆数组
	 * 
	 * @param {Array}  array 原数组
	 * @return {Array} 返回克隆数组
	 */
	clone : function(array) {
		// var newArray = new Array();
		// for ( var i = 0; i < array.length; i++) {
		// newArray.push(array[i]);
		// }
		// return newArray;
		return Array.prototype.slice.call(array);
	},
	/**
	 * 清除对应的数组元素，返回相应的数组
	 * 
	 * @param {Array} array 需要修改的数组
	 * @param {Number} index 数组索引
	 * @param {Number} removeCount 移除的数量（从索引开始计算）
	 * @return {Array}
	 */
	erase : function(array, index, removeCount) {
		var newArray = new Array();
		for ( var i = 0; i < array.length; i++) {
			// index index+removeCount
			if (i >= index && i < (index + removeCount)) {
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 遍历每个值，当有值与scope对象一致的时候，返回true,否则返回false
	 * @private
	 * @param {Array} array
	 * @param {Function} fn
	 * @param {Object} scope
	 * @return {Boolean}
	 */
	every : function(array, fn, scope) {
		for ( var i = 0; i < array.length; i++) {
			var falsy = fn(array[i], i);
			if (falsy == scope) {
				return true;
			}
		}
		return false;
	},
	/**
	 * 
	 * 循环遍历对应的数组
	 * 
	 * @param array 所需遍历的数组
	 * @param fn 回调函数
	 * @param  scope 一般默认this,即函数本身
	 */
	forEach : function(array, fn, scope) {
       for(var i=0,len=array.length;i<len;i++){
    	   var flag = fn(i,array[i],scope||this);
    	   if(flag==false){return;}
       }
	},
	/**
	 * 判断一个元素在数组中的下标，如果数组中没有该元素，返回-1
	 * 
	 * @param {Array} array 传递来的数组
	 * @param {Object} item 匹配的数值
	 * @param {Number} from 从某个下标开始匹配，之前的下标不进行匹配
	 * @return {Number}
	 */
	indexOf : function(array, item, from) {
		var i, ln, newArray = [];
		if (typeof from == 'undefined') {
			for (i = 0, ln = array.length; i < ln; i++) {
				if (item == array[i]) {
					return i;
				}
			}
			return -1;
		} else {
			for (i = 0, ln = array.length; i < ln; i++) {
				if (i >= from) {
					newArray.push(array[i]);
				}
			}
			// get newArray
			for ( var j = 0; j < newArray.length; j++) {
				if (item == newArray[j]) {
					return j;
				}
			}
			return -1;
		}
       return -1;
	},
	/**
	 * 
	 * 找共同项
	 * 
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 * @return {Array}
	 */
	intersect : function(array1, array2, etc) {
		// return this.complexElement(array1, array2, etc);
		var newArray = [];
		for ( var i = 0, len = array1.length; i < len; i++) {
			for ( var j = 0, length = array2.length; j < length; j++) {
				if (array1[i] == array2[j]) {
					newArray.push(array1[i]);
				}
			}
		}
		// 再去重
		var map = {}, result = [];
		for ( var i = 0, len = newArray.length; i < len; i++) {
			if (!map[newArray[i]]) {
				result.push(newArray[i]);
			}
		}
		return result;
	},
	/**
	 * 返回数组内数值最大数
	 * 
	 * @param {Array} array
	 * @return {Number}
	 */
	max : function(array) {
		var max = 0;
		for ( var i = 0; i < array.length; i++) {
			if (array[i] > array[i + 1]) {
				max = array[i];
				array[i] = array[i + i];
				array[i + 1] = max;
			}
		}
		return max;
	},
	/**
	 * 获取数组内最小值
	 * 
	 * @param {Array} array
	 * @return {Number}
	 */
	min : function(array) {
		var min = null;
		for ( var i = 0; i < array.length; i++) {
			if (array[i] < array[i + 1]) {
				min = array[i];
				array[i] = array[i + 1];
				array[i + 1] = array[i];
			}
		}
		return min;
	},
	/**
	 * 计算数组内元素平均值
	 * 
	 * @param {Array} array
	 * @return {Number}
	 */
	mean : function(array) {
		var sum = 0;
		var length = array.length;
		for ( var i = 0; i < length; i++) {
			sum += array[i];
		}
		/*
		 * this.each(array,function(value){ sum+=value; });
		 */
		return parseFloat(sum / length);
	},
	/**
	 * 
	 * 合并多个数组到一个一个新数组,并返回该新数组
	 * 
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 */
	merge : function(array1, array2, etc) {
		// 思路受困
		/*
		 * var args = arguments[0]; var sumArrayLength = args.length; var
		 * newArray = newArray(); for(var i = 0; i<sumArrayLength;i++){ var
		 * Oarray = args[i]; for(var j = 0;j<Oarray.length;j++){
		 * newArray.push(Oarray[j]); } } return newArray;
		 */
		var newArray = new Array();
		if (array1) {
			for ( var i = 0; i < array1.length; i++) {
				newArray.push(array1[i]);
			}
		}
		if (array2) {
			for ( var j = 0; j < array2.length; j++) {
				newArray.push(array2[j]);
			}
		}
		return newArray;
	},
	/**
	 * 
	 * pluck方法 返回对象数组，中具有相同键对应的值的集合。
	 * 
	 * @param {Array} array是json类似的数组集合
	 * @param {Object} propertyName是json的key
	 * @return {Array} 返回的是key对应的值的数组集合
	 */
	pluck : function(array, propertyName) {
		var newArray = [], i, ln;
		for (i = 0, ln = array.length; i < ln; i++) {
			newArray.push(array[i][propertyName]);
		}
		return newArray;
	},
	/**
	 * 移除数组中某个相同的对象，并返回一个新数组
	 * 
	 * @param {Array} array
	 * @param {Object} item 待移除项
	 * @return {Array}
	 */
	remove : function(array, item) {
		var i, ln, newArray = [];
		for (i = 0, ln = array.length; i < ln; i++) {
			if (item != array[i]) {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 提取一个数组中的开始下标和结束下标中间的元素，并构成一个新数组，返回这个新数组
	 * 
	 * @param {Array} array
	 * @param {Number} begin 开始下标
	 * @param {Number}  end 结束下标
	 * @return {Array}
	 */
	slice : function(array, begin, end) {
		var i, ln, newArray = [];
		for (i = 0, ln = array.length; i < ln; i++) {
			if (i >= begin && i <= end) {
				newArray.push(array[i]);
			}
		}
		return newArray;
		//return Array.prototype.slice(array,begin,end);
	},
	/**
	 * 
	 * splice方法
	 * 
	 * @param {Array} array 传递的原数组
	 * @param {Number} index 开始索引
	 * @param {Number} removeCount 从index开始，移除的数量
	 * @param {Object} elements  需要添加的元素
	 * @return {Array}
	 */
	splice : function(array, index, removeCount, elements) {
		var i, ln, newArray = [];
		var j = 0;
		for (i = 0, ln = array.length; i < ln; i++) {

			if (i >= index && i < (index + removeCount)) {
				// 在多个循环内之添加一次
				j++;
				if (typeof elements != 'object'
						&& typeof elements != 'undefined') {
					if (j == 1) {
						newArray.push(elements);
					}
				} else {
					// 暂不处理
				}
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 数组内所有元素总和
	 * 
	 * 		NS.Array.sum([1,2,3]);//6
	 * @param {Array} array number类型元素的数组
	 * @return {Number}
	 */
	sum : function(array) {
		var sum = 0, i = 0, ln = array.length, item;
		for (; i < ln; i++) {
			item = array[i];
			sum += item;
		}
		return sum;
	},
	/**
	 * 将传递来的数组，对应的生成统一的新的数组，且该数组的属性都是唯一的
	 * 
	 * 		var array = NS.Array.union([1,2,3],[2,4,5]);
	 *      console.log(array);//[1,2,3,4,5]
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 * @return {Array}
	 */
	union : function(array1, array2, etc) {
		var array = [];
		if (array1 instanceof Array && array2 instanceof Array) {
			array1.concat(array2);
			array = this.unique(array1);
		}
		return array;
	},
	/**
	 * 返回唯一的的元素(非重复元素)
	 * 
	 * 		NS.Array.unique([1,2,2,3,4,5]);//[1,3,4,5]
	 * @param {Array} array 
	 * @return {Array}
	 */
	unique : function(array) {
		var newArray = [], i, ln, map = {};
		for (i = 0, ln = array.length; i < ln; i++) {
			var oneValue = array[i];
			if (!map[oneValue]) {
				map[oneValue] = 1;
			} else {
				map[oneValue] += 1;
			}
		}
		for ( var i in map) {
			if (map[i] == 1) {
				newArray.push(i);
			}
		}
		return newArray;
	},
	/**
	 * 找到两个数组中重复元素（交集）
	 * 
	 * 		NS.Array.complexElements([1,2,2,3,4,5]);//[2]
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 * @return {Array}
	 */
	complexElements : function(array1, array2, etc) {
		var newArray = [], i, ln, map = {};
		for (i = 0, ln = array.length; i < ln; i++) {
			var oneValue = array[i];
			if (!map[oneValue]) {
				map[oneValue] = 1;
			} else {
				map[oneValue] += 1;
			}
		}
		for ( var i in map) {
			if (map[i] > 1) {
				newArray.push(i);
			}
		}
		return newArray;
	},
    /***
     * 删除一个数组中指定下标的元素
     * @param {Array} array 数组
     * @param {Number} index 数据下标
     * @return {Array}
     */
    removeAt : function(array,index){
        array.splice(index,1);
        return array;
    },
    /**
     * 可以被枚举的对象(数组对象，字符串对象等)
     * @param {Object} iterable 迭代对象
     * @param {Number} start 开始元素个数
     * @param {Number} end 结束元素个数
     * @return
     */
    toArray: function(iterable, start, end){
    	if (!iterable || !iterable.length) {
			return [];
		}
		if (typeof iterable === 'string') {
			iterable = iterable.split('');
		}
		if (true) {
			return Array.prototype.slice.call(iterable, start || 0, end || iterable.length);
		}
		var array = [], i;
		start = start || 0;		end = end ? ((end < 0) ? iterable.length + end : end) : iterable.length;

		for (i = start; i < end; i++) {
			array.push(iterable[i]);
		}
		return array;
    }
    
};
