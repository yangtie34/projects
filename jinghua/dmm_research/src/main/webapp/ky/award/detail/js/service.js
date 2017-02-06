app.service("service", [ 'httpService', function(http) {
	return {
		//刷新奖励结果
		refreshPageData : function(year){
			return http.post({
				url : "kyjl/data/refreshresult",
				data : {year:year}
			});
		},
		//查询立项奖名单
		queryAwardList : function(page,condition) {
			return http.post({
				url : "kyjl/detail/setup",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询立项奖项目级别分布
		queryAwardLevel : function(condition) {
			return http.post({
				url : "kyjl/detail/level",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询立项奖项目程度分布
		queryAwardRank : function(condition) {
			return http.post({
				url : "kyjl/detail/rank",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询立项奖获奖人员单位分布
		querySetupPeopleDept : function(condition) {
			return http.post({
				url : "kyjl/detail/setupdept",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询结项奖名单
		queryEndList  : function(page,condition) {
			return http.post({
				url : "kyjl/detail/end",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询结项奖获奖人员单位分布
		queryEndDept : function(condition) {
			return http.post({
				url : "kyjl/detail/enddept",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询论文收录获奖名单
		queryThesisInList  : function(page,condition) {
			return http.post({
				url : "kyjl/detail/inlist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询论文转载获奖名单
		queryThesisReshipList  : function(page,condition) {
			return http.post({
				url : "kyjl/detail/reshiplist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询论文收录期刊分布
		queryThesisIn : function(condition) {
			return http.post({
				url : "kyjl/detail/in",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询论文转载期刊分布
		queryThesisReship : function(condition) {
			return http.post({
				url : "kyjl/detail/reship",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询论文获奖人员单位分布
		queryThesisDept : function(condition) {
			return http.post({
				url : "kyjl/detail/thesisdept",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询专利获奖名单
		queryPatentList : function(page,condition) {
			return http.post({
				url : "kyjl/detail/patentlist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询专利类别分布
		queryPatentType : function(condition) {
			return http.post({
				url : "kyjl/detail/patenttype",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询发明人单位分布
		queryPatentDept : function(condition) {
			return http.post({
				url : "kyjl/detail/patentdept",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询科研经费奖励名单
		queryFundList : function(page,condition) {
			return http.post({
				url : "kyjl/detail/fundlist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询科研经费奖励单位分布
		queryFundDept : function(condition) {
			return http.post({
				url : "kyjl/detail/funddept",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询获奖成果奖励名单
		queryAchievementList : function(page,condition) {
			return http.post({
				url : "kyjl/detail/achievelist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询获奖成果参与获奖奖励名单
		queryAchievementList2 : function(page,condition) {
			return http.post({
				url : "kyjl/detail/achievementlist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询获奖成果单位排名
		queryAchievementDept : function(condition) {
			return http.post({
				url : "kyjl/detail/achievedept",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询成果转化奖奖励名单
		queryTransformList : function(page,condition) {
			return http.post({
				url : "kyjl/detail/transformlist",
				data : {
					pagesize : page.size,
					curpage : page.index,
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		},
		//查询科研成果转化奖获奖人单位分布
		queryTransformDept : function(condition) {
			return http.post({
				url : "kyjl/detail/transformdept",
				data : {
					year : condition.year,
					xkmlid:condition.subject.id,
					zzjgid : condition.dept.id
				}
			});
		}
	}
} ]);
