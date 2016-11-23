app.service("scoreService",['httpService',function(http){
    return {    
        getScoreClasses :function(xn,xq){
        	return http.post({
        		url : "teacher/score/scoreClasses",
        		data : {school_year:xn,term_code:xq}
        	})
        },
        getCourseScore :function(xn,xq){
        	return http.post({
        		url : "teacher/score/courseScore",
        		data : {school_year:xn,term_code:xq}
        	})
        },
        getStuScore : function(classId,courseId,stuParam,xn,xq,flag){
        	return http.post({
        		url : "teacher/score/stuScore",
        		data : {class_id:classId,course_id:courseId,param:stuParam,school_year:xn,term_code:xq,paramFlag:flag}
        	})
        },
        getStuTotalScore : function(stuId,xn,xq){
        	return http.post({
        		url : "teacher/score/stuTotalScore",
        		data : {stu_id:stuId,school_year:xn,term_code:xq}
        	})
        },
        getStuScoreDetail : function(stuId,xn,xq){
        	return http.post({
        		url : "teacher/score/stuScoreDetail",
        		data : {stu_id:stuId,school_year:xn,term_code:xq}
        	})
        }
    }
}]);
