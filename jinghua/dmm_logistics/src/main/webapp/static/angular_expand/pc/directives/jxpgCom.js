/*教学评价封装组件，非原子性组件，组合组件。
 */
/**
 * 教室看统计页面，得分区间展示组合。
 * scope.valueFrom
 * scope.valueTo
 * scope.max
 * scope.bfb
 * scope.num
 * eg:
 * <any jc-score-tj-range cg-value-from="valueFrom"
			cg-value-to="valueTo" cg-max="max"
			cg-bfb="bfb" cg-num="num"></any>
 */
jxpg.directive("jcScoreTjRange", [ function() {
	return {
		restrict : "AE",
		replace : true,
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/jcScoreTjRange.html',
		scope : {
			cgMax : "=",//最大值（星星的个数）
			cgValueFrom : "=", //起始
			cgValueTo:"=",
			cgBfb:"=",//百分比
			cgNum:"="//实际数量
		}
	}
} ]);
