app.service("researchService",['httpService',function(http){
    return {
         getResearchCounts : function(){
        	return http.post({
        		url : "teacher/research/researchCounts",
        		data : []
        	})
        },
        getThesisCounts : function(){
        	return http.post({
        		url : "teacher/research/thesisCounts",
        		data : []
        	})
        },
        getThesises : function(type){
        	return http.post({
        		url : "teacher/research/thesises",
        		data : {flag:type}
        	})
        },
        getProjectCounts : function(){
        	return http.post({
        		url : "teacher/research/projectCounts",
        		data : []
        	})
        },
        getProjects : function(param){
        	return http.post({
        		url : "teacher/research/projects",
        		data : {flag:param}
        	})
        },
        getWorks : function(type){
        	return http.post({
        		url : "teacher/research/works",
        		data : {flag:type}
        	})
        },
        getWorksCounts : function(type){
        	return http.post({
        		url : "teacher/research/worksCounts",
        		data : []
        	})
        },
        getPatentCounts : function(){
        	return http.post({
        		url : "teacher/research/patentCounts",
        		data : []
        	})
        },
        getPatents : function(type){
        	return http.post({
        		url : "teacher/research/patents",
        		data : {flag:type}
        	})
        },
         getOutcomeCounts : function(){
        	return http.post({
        		url : "teacher/research/outcomeCounts",
        		data : []
        	})
        },
        getOutcomes : function(type){
        	return http.post({
        		url : "teacher/research/outcomes",
        		data : {flag:type}
        	})
        },
        getSofts : function(type){
        	return http.post({
        		url : "teacher/research/softs",
        		data : []
        	})
        }
    }
}]);
