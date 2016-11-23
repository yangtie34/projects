
/*对数字分数进行转换
 *  eg:
 *  scope.value=Number;
 *  <any cg-score-convert cg-value="value"></any>
 */
jxpg.directive("cgScoreConvert", ["cgScoreConvertService", function(scoreConvertService) {
	return {
		restrict:'AE',
		replace:true,
		templateUrl:base + '/static/app_angular_expand/pc/directives/tpl/cgScoreConvert.html',
		scope:{
			cgValue:'='
		},
		link:function(scope,ele,attrs){
			var score=scope.cgValue;
			scope.order = scoreConvertService.getConvert(score);
			if(scope.order=="优"){
				scope.className="pingjiao-xs-user-pingyou";
			}else if(scope.order=="良"){
				scope.className="pingjiao-xs-user-pingliang";
			}else if(scope.order=="中"){
				scope.className="pingjiao-xs-user-pingzhong";
			}else if(scope.order=="一般"){
				scope.className="pingjiao-xs-user-pingyiban";
			}
		}
	}
} ]);
