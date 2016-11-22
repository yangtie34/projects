function searchCourse(stu_id){
	var ele = $("#kebiao-box .title-bottom-line").find("select").find("option:selected").val();
	coureArrangementByStu(stu_id, ele);
}

// 课表
function coureArrangementByStu(stu_id, zc) {
	var data = [stu_id, zc];
	$.callService('stuAllInfoService', 'getCoureArrangementByStu', data, function(d) {
		var tableInfo = "<tr><td class=\"white-bg\" >&nbsp;</td><td class=\"white-bg\" >&nbsp;</td> <td  class=\"white-bg\" >星期一</td><td  class=\"white-bg\" >星期二</td><td  class=\"white-bg\" >星期三</td><td  class=\"white-bg\" >星期四</td><td  class=\"white-bg\" >星期五</td><td  class=\"white-bg\" >星期六</td><td  class=\"white-bg\" >星期日</td></tr><tr><th class=\"tongji-th-liubai\"  colspan=\"8\"></th></tr>";
		var info = "";
		if (d) {
			if(d.length>0){
				var totalWeek;
				$.callService('stuAllInfoService', 'getCoureArrangementInitByStu', [d[0].SCHOOL_YEAR, d[0].TERM_CODE, zc], function(data) {
					var selectE = "";
					if(data){
						totalWeek = data.length;
						$.each(data, function(i, o){
						selectE = selectE +o;
						});
					}
					$("#kebiao-box .title-bottom-line").find("select").html(selectE)
					
				},'', '', '', false);
				var currentZc = $("#kebiao-box .title-bottom-line").find("select").find("option:selected").val();
				info = info + d[0].SCHOOL_YEAR + "  第" + ((d[0].TERM_CODE=='01') ? '一' : '二')+"学期<span>上课有"+totalWeek+"周</span> <span>第"+currentZc+"周课程活动安排如下：</span>";
			}
			var dayArr = ['上午', '下午', '晚上'];
			$.each(d, function(i, o) {
				tableInfo = tableInfo + "<tr>";
				if ((i % 2) == 0) {
					tableInfo = tableInfo + "<td rowspan=\"2\">"+dayArr[parseInt(i/2)]+"</td>";
				}
				tableInfo = tableInfo + "<td>" + o.PERIOD + "</td><td class=\"" + ((o.星期一 == null) ? "" : "orange-bg") + "\">" + ((o.星期一 == null) ? "" : o.星期一) + "</td><td class=\"" + ((o.星期二 == null) ? "" : "orange-bg") + "\">" + ((o.星期二 == null) ? "" : o.星期二) + "</td><td class=\"" + ((o.星期三 == null) ? "" : "orange-bg") + "\">" + ((o.星期三 == null) ? "" : o.星期三) + "</td><td class=\"" + ((o.星期四 == null) ? "" : "orange-bg") + "\">" + ((o.星期四 == null) ? "" : o.星期四) + "</td><td class=\"" + ((o.星期五 == null) ? "" : "orange-bg") + "\">" + ((o.星期五 == null) ? "" : o.星期五) + "</td><td class=\"" + ((o.星期六 == null) ? "" : "orange-bg") + "\">" + ((o.星期六 == null) ? "" : o.星期六) + "</td><td class=\"" + ((o.星期日 == null) ? "" : "orange-bg") + "\">" + ((o.星期日 == null) ? "" : o.星期日) + "</td></tr>";
			});
		}
		tableInfo = tableInfo  + "<tr><th class=\"tongji-th-liuba\"  colspan=\"9\"></th></tr>";
		// 追加标签
		$("#kebiao-box").find("table").html(tableInfo);
		$("#kebiao-box .title-bottom-line").find("span").html(info);
	});
}

// 请销假
function leaveInfo(stu_id){
	var data = [stu_id];
	// 初始化数据
	$.callService('stuAllInfoService', 'getLeaveInfoByStu', data, function(d) {
		var info; 
		var leavesNum = 0;  // 请假总次数
		var dayCounts = 0;  // 请假总天数
		var leaveInfo ="";
		if(d){
			$.each(d, function(i, o){
				leavesNum = leavesNum + o.LEAVENUM;
				dayCounts = dayCounts + o.DAYCOUNTS;
				leaveInfo = leaveInfo + o.CODENAME + "<em class=\"before-number-ipt\">"+o.DAYCOUNTS+"</em> 天，";
			});
			leaveInfo = leaveInfo.substring(0, leaveInfo.length-1);
			if(d.length>0){
				info = d[0].SCHOOL_YEAR + "学年，第" + ((d[0].TERM_CODE =="01")? "一" : "二") +　"学ff期，" + d[0].STUNAME + "累计请假 <em class=\"before-number-ipt\">"+leavesNum+"</em> 次，累计 <em class=\"before-number-ipt\">"+dayCounts+"</em> 天，其中，"+leaveInfo;
			}
		}
		$("#qingxiaojia-box .before-yellow-text").html(info);
	});
	// 初始化table数据
	$.callService('stuAllInfoService', 'getLeaveByStu', data, function(d) {
		var tableInfo = "";
		if(d){
			$.each(d, function(i, o){
				tableInfo = tableInfo + "<tr><td class=\"tongji-tr-exicon\"></td><td>" + o.START_TIME + " 至 " + o.END_TIME + "</td><td>" + o.DAY + "</td><td>" + o.START_TIME + "</td><td>" + ((o.CANCEL_TIME == null) ? '' : o.CANCEL_TIME) + "</td><td>" + ((o.CANCEL_TIME == null) ? '未返校' : '已返校') + "</td></tr>";
			});
		}
		// 追加标签
		$("#qingxiaojia-box .tongji-table").append(tableInfo);
	});
}

// 借书
function borrowBook(stu_id){
	var data = [stu_id];
	// 初始化table数据
	$.callService('stuAllInfoService', 'getBookBorrowByStu', data, function(d) {
		var tableInfo = "";
		if(d){
			$.each(d, function(i, o){
				tableInfo = tableInfo + "<tr><td class=\"tongji-tr-exicon\"></td><td>"+o.BOOKNAME+"</td><td>"+o.BORROW_TIME+"</td><td>"+o.SHOULD_RETURN_TIME+"</td><td>"+((o.RETURN_TIME==null)?"":o.RETURN_TIME)+"</td><td>"+dateDistance(o.SHOULD_RETURN_TIME, o.RETURN_TIME)+"天</td><td>**</td></tr>";
			});
		}
		// 追加标签
		$("#tushu-box .tongji-table").find("tbody").append(tableInfo);
	});
}

// 判断两个日期相差几天
function dateDistance(dateStr1, dateStr2) {
	var dateArr1 = dateStr1.split("-");
	var dateArr2 = dateStr2.split("-");
	var date1 = new Date(dateArr1[0], dateArr1[1], dateArr1[2]);
	var date2 = new Date(dateArr2[0], dateArr2[1], dateArr2[2]);
	return parseInt(Math.abs(date1.getTime() - date2.getTime()) / 1000 / 60 / 60 / 24);
}
