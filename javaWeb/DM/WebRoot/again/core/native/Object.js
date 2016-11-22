/**
 * @class NS.Object
 * @author haw_king
 *    Object 扩展类
 */
NS.Object = (function() {
	var TemplateClass = function() {
	};
	return {
		/**
		 * 返回一个新的对象与给定对象的原型链。
		 * 
		 * @param {Object}
		 *            object 含有新对原型链的对象
		 */
		chain : function(object) {
			TemplateClass.prototype = object;
			var result = new TemplateClass();
			TemplateClass.prototype = null;
			return result;
		},
		/**
		 * 转换成`name` - `value`对到一个数组中的对象支持嵌套结构。构建查询字符串.
		 * 
		 * 
		 * 		var objects = NS.Object.toQueryObjects('hobbies', ['reading',
		 * 'cooking', 'swimming']); // objects then equals: [ { name: 'hobbies',
		 * value: 'reading' }, { name: 'hobbies', value: 'cooking' }, { name:
		 * 'hobbies', value: 'swimming' }, ];
		 * 
		 * 		var objects = NS.Object.toQueryObjects('dateOfBirth', { day: 3,
		 * month: 8, year: 1987, extra: { hour: 4 minute: 30 } }, true); //
		 * Recursive // objects then equals: [ { name: 'dateOfBirth[day]',
		 * value: 3 }, { name: 'dateOfBirth[month]', value: 8 }, { name:
		 * 'dateOfBirth[year]', value: 1987 }, { name:
		 * 'dateOfBirth[extra][hour]', value: 4 }, { name:
		 * 'dateOfBirth[extra][minute]', value: 30 }, ];
		 * 
		 * @param {String}
		 *            name
		 * @param {Object/Array}
		 *            value
		 * @param {Boolean}
		 *            [recursive=false] True to traverse object recursively
		 * @return {Array}
		 */
		toQueryObjects : function(name, value, recursive) {
			var self = ExtObject.toQueryObjects, objects = [], i, ln;

			if (Ext.isArray(value)) {
				for (i = 0, ln = value.length; i < ln; i++) {
					if (recursive) {
						objects = objects.concat(self(name + '[' + i + ']',
								value[i], true));
					} else {
						objects.push({
							name : name,
							value : value[i]
						});
					}
				}
			} else if (Ext.isObject(value)) {
				for (i in value) {
					if (value.hasOwnProperty(i)) {
						if (recursive) {
							objects = objects.concat(self(name + '[' + i + ']',
									value[i], true));
						} else {
							objects.push({
								name : name,
								value : value[i]
							});
						}
					}
				}
			} else {
				objects.push({
					name : name,
					value : value
				});
			}

			return objects;
		},

		/**
		 * 接受一个对象，并且将它转换为一个编码的查询字符串.
		 * 
		 * 
		 * 		NS.Object.toQueryString({foo: 1, bar: 2}); // returns
		 * "foo=1&bar=2" NS.Object.toQueryString({foo: null, bar: 2}); //
		 * returns "foo=&bar=2" NS.Object.toQueryString({'some price':
		 * '$300'}); //returns "some%20price=%24300"
		 * 		NS.Object.toQueryString({date: new Date(2011, 0, 1)}); //
		 * returns "date=%222011-01-01T00%3A00%3A00%22"
		 * 		NS.Object.toQueryString({colors: ['red', 'green', 'blue']}); //
		 * returns "colors=red&colors=green&colors=blue"
		 * 
		 * 
		 * 		NS.Object.toQueryString({ username: 'Jacky', dateOfBirth: { day:
		 * 1, month: 2, year: 1911 }, hobbies: ['coding', 'eating',
		 * 'sleeping',['nested', 'stuff']] }, true); // returns the following
		 * string (broken down and url-decoded for ease of reading purpose): //
		 * username=Jacky //
		 * &dateOfBirth[day]=1&dateOfBirth[month]=2&dateOfBirth[year]=1911 //
		 * &hobbies[0]=coding&hobbies[1]=eating&hobbies[2]=sleeping&hobbies[3][0]=nested&hobbies[3][1]=stuff
		 * 
		 * @param {Object}
		 *            object The object to encode
		 * @param {Boolean}
		 *            [recursive=false] Whether or not to interpret the object
		 *            in recursive format. (PHP / Ruby on Rails servers and
		 *            similar).
		 * @return {String} queryString
		 */
		toQueryString : function(object, recursive) {
			var paramObjects = [], params = [], i, j, ln, paramObject, value;

			for (i in object) {
				if (object.hasOwnProperty(i)) {
					paramObjects = paramObjects.concat(ExtObject
							.toQueryObjects(i, object[i], recursive));
				}
			}

			for (j = 0, ln = paramObjects.length; j < ln; j++) {
				paramObject = paramObjects[j];
				value = paramObject.value;

				if (Ext.isEmpty(value)) {
					value = '';
				} else if (Ext.isDate(value)) {
					value = Ext.Date.toString(value);
				}

				params.push(encodeURIComponent(paramObject.name) + '='
						+ encodeURIComponent(String(value)));
			}

			return params.join('&');
		},

		/**
		 * 
		 * 将一个查询字符串转换回对象.
		 * 
		 * 		NS.Object.fromQueryString("foo=1&bar=2"); // returns {foo: 1,
		 * bar: 2} NS.Object.fromQueryString("foo=&bar=2"); // returns
		 * {foo: null, bar: 2}
		 * 		NS.Object.fromQueryString("some%20price=%24300"); // returns
		 * {'some price': '$300'}
		 * 		NS.Object.fromQueryString("colors=red&colors=green&colors=blue");
		 * //returns {colors: ['red', 'green', 'blue']}
		 * 
		 * 		NS.Object.fromQueryString(
		 * "username=Jacky&"+"dateOfBirth[day]=1&dateOfBirth[month]=2&dateOfBirth[year]=1911&"+"hobbies[0]=coding&hobbies[1]=eating&hobbies[2]=sleeping&"+"hobbies[3][0]=nested&hobbies[3][1]=stuff",
		 * true);// returns { username: 'Jacky', dateOfBirth: { day: '1', month:
		 * '2',year: '1911' }, hobbies: ['coding', 'eating', 'sleeping',
		 * ['nested','stuff']] }
		 * 
		 * @param {String}
		 *            查询字符串查询字符串进行解码
		 * @param {Boolean}
		 *            [recursive=false] 是否要进行转码. 支持此格式的PHP / Ruby on
		 *            Rails的服务器和类似的。
		 * @return {Object}
		 */
		fromQueryString : function(queryString, recursive) {
			var parts = queryString.replace(/^\?/, '').split('&'), object = {}, temp, components, name, value, i, ln, part, j, subLn, matchedKeys, matchedName, keys, key, nextKey;

			for (i = 0, ln = parts.length; i < ln; i++) {
				part = parts[i];

				if (part.length > 0) {
					components = part.split('=');
					name = decodeURIComponent(components[0]);
					value = (components[1] !== undefined) ? decodeURIComponent(components[1])
							: '';

					if (!recursive) {
						if (object.hasOwnProperty(name)) {
							if (!Ext.isArray(object[name])) {
								object[name] = [ object[name] ];
							}

							object[name].push(value);
						} else {
							object[name] = value;
						}
					} else {
						matchedKeys = name.match(/(\[):?([^\]]*)\]/g);
						matchedName = name.match(/^([^\[]+)/);

						// <debug error>
						if (!matchedName) {
							throw new Error(
									'[Ext.Object.fromQueryString] Malformed query string given, failed parsing name from "'
											+ part + '"');
						}
						// </debug>

						name = matchedName[0];
						keys = [];

						if (matchedKeys === null) {
							object[name] = value;
							continue;
						}

						for (j = 0, subLn = matchedKeys.length; j < subLn; j++) {
							key = matchedKeys[j];
							key = (key.length === 2) ? '' : key.substring(1,
									key.length - 1);
							keys.push(key);
						}

						keys.unshift(name);

						temp = object;

						for (j = 0, subLn = keys.length; j < subLn; j++) {
							key = keys[j];

							if (j === subLn - 1) {
								if (Ext.isArray(temp) && key === '') {
									temp.push(value);
								} else {
									temp[key] = value;
								}
							} else {
								if (temp[key] === undefined
										|| typeof temp[key] === 'string') {
									nextKey = keys[j + 1];

									temp[key] = (Ext.isNumeric(nextKey) || nextKey === '') ? []
											: {};
								}

								temp = temp[key];
							}
						}
					}
				}
			}

			return object;
		},
		/**
		 * 迭代遍历对象中的所有元素，可以控制回调函数里返回false，来停止对该对象的遍历。
		 * 
		 * 			 var person = { name: 'Jacky' hairColor: 'black' loves:
		 *          ['food', 'sleeping', 'wife'] };
		 * 
		 * 			NS.Object.each(person, function(key, value, myself) {
		 * console.log(key + ":" + value);
		 * 
		 * if (key === 'hairColor') { return false; // stop the iteration } });
		 * 
		 * @param {Object}
		 *            object 需迭代的对象
		 * @param {Function}
		 *            fn 回调函数
		 * @param {Object}
		 *            [scope] scope默认应是this,即回调函数自身
		 */
		each : function(object, fn, scope) {
			for ( var property in object) {
				if (object.hasOwnProperty(property)) {
					if (fn.call(scope || object, property, object[property],
							object) === false) {
						return;
					}
				}
			}
		},

		/**
		 * 合并source的对象，但不包含其子节点
		 * 
		 * 		var extjs = { companyName: 'Ext JS', products: ['Ext JS', 'Ext GWT',
		 * 'Ext Designer'], isSuperCool: true, office: { size: 2000, location:
		 * 'Palo Alto', isFun: true } };
		 * 
		 * 		var newStuff = { companyName: 'Sencha Inc.', products: ['Ext JS',
		 * 'Ext GWT', 'Ext Designer', 'Sencha Touch', 'Sencha Animator'],
		 * office: { size: 40000, location: 'Redwood City' } };
		 * 
		 *		var sencha = NS.Object.merge(extjs, newStuff); // extjs and
		 * sencha then equals to { companyName: 'Sencha Inc.', products: ['Ext
		 * JS', 'Ext GWT', 'Ext Designer', 'Sencha Touch', 'Sencha Animator'],
		 * isSuperCool: true, office: { size: 40000, location: 'Redwood City'
		 * isFun: true } }
		 * 
		 * @param {Object}
		 *            object 任意数量的对象集合
		 * @return {Object} merged 返回一个合并的所有对象被创建的对象
		 */
		merge : function(source) {
			var i = 1, ln = arguments.length, mergeFn = ExtObject.merge, cloneFn = Ext.clone, object, key, value, sourceKey;

			for (; i < ln; i++) {
				object = arguments[i];

				for (key in object) {
					value = object[key];
					if (value && value.constructor === Object) {
						sourceKey = source[key];
						if (sourceKey && sourceKey.constructor === Object) {
							mergeFn(sourceKey, value);
						} else {
							source[key] = cloneFn(value);
						}
					} else {
						source[key] = value;
					}
				}
			}

			return source;
		},

		/**
		 * @private
		 * @param source
		 */
		mergeIf : function(source) {
			var i = 1, ln = arguments.length, cloneFn = Ext.clone, object, key, value;

			for (; i < ln; i++) {
				object = arguments[i];

				for (key in object) {
					if (!(key in source)) {
						value = object[key];

						if (value && value.constructor === Object) {
							source[key] = cloneFn(value);
						} else {
							source[key] = value;
						}
					}
				}
			}

			return source;
		},

		/**
		 * 通过value查找第一次出现的key,如果该对象中无该值,则返回null
		 * 
		 * 		var person = { name: 'Jacky', loves: 'food' };
		 * 
		 * 		alert(NS.Object.getKey(person, 'food')); // alerts 'loves'
		 * 
		 * @param {Object}
		 *            object
		 * @param {Object}
		 *            value 要找到值
		 */
		getKey : function(object, value) {
			for ( var property in object) {
				if (object.hasOwnProperty(property)
						&& object[property] === value) {
					return property;
				}
			}

			return null;
		},

		/**
		 * 以数组的形式返回该对象里的value集合
		 * 
		 * 		var values = NS.Object.getValues({ name: 'Jacky', loves: 'food'
		 * }); // ['Jacky', 'food']
		 * 
		 * @param {Object}
		 *            object
		 * @return {Array} 返回这个对象的value结合数组
		 */
		getValues : function(object) {
			var values = [], property;

			for (property in object) {
				if (object.hasOwnProperty(property)) {
					values.push(object[property]);
				}
			}

			return values;
		},
		/**
		 * 以数组的形式返回这个对象里的所有key的集合。
		 * 
		 * 		var values = NS.Object.getKeys({ name: 'Jacky', loves: 'food'
		 * }); // ['name', 'loves']
		 * 
		 * @param {Object} object 要操作的对象
		 * @return {String[]} 返回这个对象的键的数组集合
		 * 
		 */
		getKeys : (typeof Object.keys == 'function') ? function(object) {
			if (!object) {
				return [];
			}
			return Object.keys(object);
		} : function(object) {
			var keys = [], property;
			for (property in object) {
				if (object.hasOwnProperty(property)) {
					keys.push(property);
				}
			}

			return keys;
		},

		/**
		 * 得到这个对象的元素长度
		 * 
		 * 		var size = NS.Object.getSize({ name: 'Jacky', loves: 'food' }); //
		 * size 等于 2
		 * 
		 * @param {Object}
		 *            object
		 * @return {Number} 集合长度
		 */
		getSize : function(object) {
			var size = 0, property;

			for (property in object) {
				if (object.hasOwnProperty(property)) {
					size++;
				}
			}

			return size;
		},

		/**
		 * @private
		 */
		classify : function(object) {
			var prototype = object, objectProperties = [], propertyClassesMap = {}, objectClass = function() {
				var i = 0, ln = objectProperties.length, property;

				for (; i < ln; i++) {
					property = objectProperties[i];
					this[property] = new propertyClassesMap[property];
				}
			}, key, value;

			for (key in object) {
				if (object.hasOwnProperty(key)) {
					value = object[key];

					if (value && value.constructor === Object) {
						objectProperties.push(key);
						propertyClassesMap[key] = ExtObject.classify(value);
					}
				}
			}

			objectClass.prototype = prototype;

			return objectClass;
		},
		pluck:function(obj,callback){
			for(var i in obj){
				if(i)callback(i,obj[i],obj);
			}
		}
	};
})();