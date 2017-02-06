/**
 * 标准代码service
 * 获取系统中的标准代码以及一些常用的代码信息
 */
module.exports = function(system) {
	system.factory('sysCodeService', ['httpService', function (http) {
		return {
			getCodeByCodeType: function (codeType) {
				return http.post({
					url: "business/standardcode/codetype",
					data: {
						codeType: codeType
					}
				});
			},
			getSelfDefinedYearCode: function () {
				return http.post({
					url: "business/selfdefined/year",
					data: {}
				});
			},
			getYears: function () {
				return http.post({
					url: "business/selfdefined/years",
					data: {}
				});
			},
			getCodeSubject: function () {
				return http.post({
					url: "business/code/subject",
					data: {}
				});
			},
			getDeptTree: function (shiroTag) {
				return http.post({
					url: "business/code/depttree",
					data: {
						shiroTag: shiroTag
					}
				})
			},
			getTeachDeptTree: function (shiroTag) {
				return http.post({
					url: "business/code/teachdepttree",
					data: {
						shiroTag: shiroTag
					}
				})
			}
		};
	}]);
}