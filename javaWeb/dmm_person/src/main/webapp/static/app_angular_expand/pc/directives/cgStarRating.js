

/*星星评分控件。
 *  eg:
 *  scope.value=Number;
 *  scope.max=Number;
 *  <any cg-star-rating cg-value="value" cg-max="max"></any><!--最小使用-->
 *  eg:
 *  <any cg-star-rating cg-value="value" cg-max="max" size="md" isreadonly="true"></any>
 *  ----------
 *  size可用值为：xl, lg, md, sm, or xs. Defaults to xs
 */
jxpg.directive("cgStarRating", [ function() {
	return {
		restrict : "AE",
		replace : true,
		template : "<input type=\"number\" class=\"rating\" />",
		scope : {
			cgMax : "=",//最大值（星星的个数）
			cgValue : "=" //选中的星星的个数
		},
		link : function(scope, element, iAttrs) {
			scope.cgValue = scope.cgValue || 0;//设置默认值为0
			scope.cgMax = scope.cgMax || 5;
			element.val(scope.cgValue);//设置input的值
			//属性默认值
			var readonly = iAttrs.isreadonly || "false";
			var size = iAttrs.size || "xs";
			//生成星星控件
			var readonly_value = readonly == "true" ? true : false;
			var params = {//控制星星的显示属性
				min : 0,
				max : scope.cgMax,
				step : 1,
				size : size,
				stars : scope.cgMax,
				showClear : false,
				showCaption : false,
				readonly : readonly_value
			}
			$(element).rating(params);
			//响应外部scope变化
			scope.$watch("cgValue", function(newVal, oldVal) {
				$(element).rating("update", newVal);//更新值
			}, true)
			scope.$watch("cgMax", function(newVal, oldVal) {
				$(element).rating("refresh", {
					max : newVal,
					stars : newVal
				});//更新控件
			}, true)
			//触发外部scope变化
			$(element).on('rating.change', function() {
				scope.$apply(scope.cgValue = element.val());
			});
		}
	}
} ]);

/*星星评分控件。没有对传入属性进行限制,功能最大。
 * https://github.com/kartik-v/bootstrap-star-rating
 *  eg:
 *  scope.value=Number;
 *  scope.attr={min : 0,
				max : 5,
				step : 1,
				size : "xs",
				stars : 5,
				showClear : false,
				showCaption : false,
				readonly : false};//具体属性，按照https://github.com/kartik-v/bootstrap-star-rating要求为准
 *  <any cg-star-rating-full cg-value="value" cg-attr="attr"></any>
 */
jxpg.directive("cgStarRatingFull", [ function() {
	return {
		restrict : "AE",
		replace : true,
		template : "<input type=\"number\" class=\"rating\" />",
		scope : {
			cgAttr : "=",//星星控件属性配置
			cgValue : "=" //选中的星星的个数
		},
		link : function(scope, element, iAttrs) {
			scope.cgValue = scope.cgValue || 0;//设置默认值为0
			element.val(scope.cgValue);//设置input的值
			var params = {//控制星星的显示属性
				min : 0,
				max : 5,
				step : 1,
				size : "xs",
				stars : 5,
				showClear : false,
				showCaption : false,
				readonly : false
			}
			scope.cgAttr = scope.cgAttr || params;
			$(element).rating(scope.cgAttr);
			//响应外部scope变化
			scope.$watch("cgValue", function(newVal, oldVal) {
				$(element).rating("update", newVal);//更新值
			}, true)
			scope.$watch("cgAttr", function(newVal, oldVal) {
				$(element).rating("refresh", newVal);//属性改变更新控件
			}, true)
			//触发外部scope变化
			$(element).on('rating.change', function() {
				//该函数没有在angular声明周期内被调用，需用$apply通知scope变化。
				scope.$apply(scope.cgValue = element.val());
			});
		}
	}
} ]);