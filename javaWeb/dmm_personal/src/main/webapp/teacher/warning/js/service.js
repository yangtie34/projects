app.service("warningService",['httpService',function(http){
    return {
    	/*==================学生异常主页===================*/
    	//查询消费异常人数
    	queryConsumeNums : function(months){
        	return http.post({
        		url : "/teacher/warning/queryConsumeNums",
        		data : {
        			months : months
        		}
        	});
        },
        //查询住宿异常人数
        queryStayNums : function(){
        	return http.post({
        		url : "/teacher/warning/queryStayNums",
        		data : {}
        	});
        },
        //查询学业异常班级人数列表
        queryStudyNums : function(xnxq){
        	return http.post({
        		url : "/teacher/warning/queryStudyNums",
        		data : {
        			xn : xnxq.xn,
        			xq : xnxq.xq
        		}
        	});
        },
        
        /*==================消费异常页面===================*/
        queryAvgDayConsume : function(){
        	return http.post({
        		url : "/teacher/warning/queryAvgDayConsume",
        		data : {}
        	});
        },
        queryXfycStudentsList : function(months,type){
        	return http.post({
        		url : "/teacher/warning/queryXfycStudentsList",
        		data : {
        			months : months,
        			type : type
        		}
        	});
        },
        
        /*==================住宿异常页面===================*/
        queryLateStudentsList : function(){
        	return http.post({
        		url : "/teacher/warning/queryLateStudentsList",
        		data : {}
        	});
        },
        queryOutStudentList : function(){
        	return http.post({
        		url : "/teacher/warning/queryOutStudentList",
        		data : {}
        	});
        },
        
        /*==================住宿异常页面===================*/
        queryCourseFailStudents : function(xn,xq,bjid){
        	return http.post({
        		url : "/teacher/warning/queryCourseFailStudents",
        		data : {
        			xn : xn,
        			xq : xq,
        			bjid : bjid
        		}
        	});
        }
    }
}]);
