app.service("service", [ 'httpService', function(http) {
	return {
		queryRyzc : function(condition) {
			return http.post({
				url : "xkjs/query/xkryzc",
				data : {
					year : condition.year
				}
			});
		},
		queryZblist : function(condition) {
			return http.post({
				url : "xkjs/query/zblist",
				data : {
				}
			});
		},
		queryZbjz : function(condition) {
			return http.post({
				url : "xkjs/query/zbjz",
				data : {
					year : condition.year
				}
			});
		},
		queryGxkzbjz : function(condition) {
			return http.post({
				url : "xkjs/query/gxkzbjz",
				data : {
					year : condition.year,
					zbid : condition.zbid
				}
			});
		},
		queryYwczb : function(condition) {
			return http.post({
				url : "xkjs/query/ywczb",
				data : {
					year : condition.year
				}
			});
		},
		queryZbwcl : function(condition) {
			return http.post({
				url : "xkjs/query/zbwcl",
				data : {
					year : condition.year
				}
			});
		},
		refreshPageData : function(){
			return http.post({
				url : "xkjs/data/xkryxx",
				data : {}
			}).then(function(){
				return http.post({
					url : "xkjs/data/zbwcsj",
					data : {}
				})
			});
			
		},
		queryGrzbwcl :function(condition){
			return http.post({
				url : "xkjs/query/grzbwcl",
				data : {
					year : condition.year,
					 zgh : condition.zgh,
					xkid : condition.xkid
				}
			})
		},
		queryGrzgh : function(id){
			return http.post({
				url : "xkjs/query/grzgh",
				data : {
					xkid:id
				}
			});
		},
		queryXkmc : function(condition){
			return http.post({
				url : "xkjs/query/xkmc",
				data : {
				}
			});
		}
	}
} ]);
