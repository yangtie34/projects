	function cultureScore(stuInfo, stuCourse, maxMinScore) {
		var html = "";
		var eachHtml = "";
		var maxMinScoreHtml = "";
		html = "<em class='before-yellow-text' style='font-size:28px;'>"+stuInfo.STUNAME+" &nbsp; &nbsp;"+ stuInfo.SEXNAME +"  &nbsp; &nbsp;"+ stuInfo.STUNO +"</em><a class='alink-default' href='#'>"+stuInfo.SCHOOLYEAR+stuInfo.TERMNAME+">></a>";
		$.each(stuCourse,function(i,o){
			eachHtml += "<div class='sushe-chuangwei'> <ul class='sushe-chuangwei-ul'> <li> <p><span>"+o.COURSENAME+"&nbsp;:&nbsp;"+o.CENSCORE+"</span></p></li></ul></div>";
		});
		maxMinScoreHtml = "总成绩：&nbsp;"+maxMinScore.totalScore+"&nbsp;&nbsp; 平均分：&nbsp;"+maxMinScore.avgScore+"&nbsp;&nbsp; 单科成绩最高：&nbsp;"+maxMinScore.oneScoreMaxName+"&nbsp;"+maxMinScore.oneScoreMax+"&nbsp;&nbsp;  最低：&nbsp;"+maxMinScore.oneScoreMinName+"&nbsp;"+maxMinScore.oneScoreMin;
		$("#stu_info").append(html);
		$("#stu_course").append(eachHtml);
		$("#min_max_score").append(maxMinScoreHtml);
	};	