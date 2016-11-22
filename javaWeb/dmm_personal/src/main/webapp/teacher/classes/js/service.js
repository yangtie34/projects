app.service("classService",['httpService',function(http){
    return {
        //查询管理的班级汇总信息
    	queryClassTotalInfo : function(){
        	return http.post({
        		url : "teacher/classes/queryClassesTotalInfo" 
        	});
        },
        //查询管理的班级列表
        queryClassList : function(){
        	return http.post({
        		url : "teacher/classes/queryClassList" 
        	});
        },
        //查询班级信息
        queryClassInfo : function(bjid){
        	return http.post({
        		url : "teacher/classes/queryClassInfo", 
        		data : {classId : bjid}
        	});
        },
        //查询班级学生列表
        queryStudentsListOfClass : function(bjid){
        	return http.post({
        		url : "teacher/classes/queryStudentsListOfClass", 
        		data : {classId : bjid}
        	});
        },
        //询所有学生信息
        queryStudentsList : function(){
        	return http.post({
        		url : "teacher/classes/queryStudentsList", 
        		data : {}
        	});
        }
    }
}]);
