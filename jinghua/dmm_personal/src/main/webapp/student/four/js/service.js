app.service("fourService",['httpService',function(http){
    return {
    	//请求辅导员信息
    	getFdys : function(){
    		return http.post({
    			url : "student/four/fdys",
    			data : {}
    		});
    	},
    	//请求我的入学日期和学校
    	getMySchool : function(){
    		return http.post({
    			url : "student/four/mySchool",
    			data : {}
    		});
    	},
    	//请求授课老师
    	queryTea : function(xnxq){
    		return http.post({
    			url : "student/four/teas",
    			data : {
        			xn : xnxq.xn,
        			xq : xnxq.xq
        		}
    		});
    	},
    	//请求学生获奖
    	getAward : function(){
    		return http.post({
    			url : "student/four/award",
    			data : {}
    		});
    	},
		//请求学生借书
		queryBorrowBooks:function (){
			return http.post({
				url : "student/four/borrowbooks",
				data : {}
			});
	   },
	   //本人借书与同届人均借书比较
	   querymyBorrowBooks:function(){
		   return http.post({
			   url:"student/four/myBorrow",
			   data:{}
		   });
	   },
	   //进出图书馆
	   queryInOutLibr:function(){
		   return http.post({
			   url:"student/four/inOutLibr",
			   data:{}
		   });
	   },
	   queryMyLibrs:function(){
		   return http.post({
			   url:"student/four/myLibrs",
			   data:{}
		   });
	   },
    	//请求学生违纪
    	getPunish : function(){
    		return http.post({
    			url : "student/four/punish",
    			data : {}
    		});
    	},
    	//请求学生刷卡次数及总额
    	getCardMap : function(){
    		return http.post({
    			url : "student/four/card",
    			data : {}
    		});
    	},
    	//请求学生年消费及同届平均年消费
    	getCardChart : function(){
    		return http.post({
    			url : "student/four/cardChart",
    			data : {}
    		});
    	},
    	//请求学生餐厅和超市消费
    	getCardPie : function(){
    		return http.post({
    			url : "student/four/cardPie",
    			data : {}
    		});
    	},
    	//请求学生早餐次数，满两餐天数，满三餐天数
    	getCardMeal : function(){
    		return http.post({
    			url : "student/four/cardMeal",
    			data : {}
    		});
    	},
    	//请求学生早餐,午餐，晚餐平均消费
    	getCardMealPayAvg : function(){
    		return http.post({
    			url : "student/four/cardMealPayAvg",
    			data : {}
    		});
    	},
    	//请求学生最喜欢的餐厅窗口
    	getCardWin : function(){
    		return http.post({
    			url : "student/four/cardWin",
    			data : {}
    		});
    	},
    	//请求学生超市消费
    	getShopCard : function(){
    		return http.post({
    			url : "student/four/shopCard",
    			data : {}
    		});
    	},
    	//请求学生淋浴支出
    	getWashCard : function(){
    		return http.post({
    			url : "student/four/washCard",
    			data : {}
    		});
    	},
    	//请求学生第一次
    	getFirst : function(){
    		return http.post({
    			url : "student/four/first",
    			data : {}
    		});
    	},
    	//请求学生成绩
    	getScoreMap : function(){
    		return http.post({
    			url : "student/four/scoreMap",
    			data : {}
    		});
    	},
    	//请求学生成绩曲线图
    	getScoreChart : function(){
    		return http.post({
    			url : "student/four/scoreChart",
    			data : {}
    		});
    	},
    	//请求学生最好的成绩
    	getGoodScore : function(){
    		return http.post({
    			url : "student/four/goodScore",
    			data : {}
    		});
    	},
    	//请求学生成绩最好的课
    	getScoreCourse : function(){
    		return http.post({
    			url : "student/four/scoreCourse",
    			data : {}
    		});
    	},
    	//请求标签墙
    	getWall : function(){
    		return http.post({
    			url : "student/four/wall",
    			data : {}
    		});
    	}
    }
}]);
