function loadTeaTitle(deptId,flag){
	
	//获取当前网址，如： http://localhost:8088/test/test.jsp
    var curPath=window.document.location.href;
    //获取主机地址之后的目录，如： test/test.jsp
    var pathName=window.document.location.pathname;
    var pos=curPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8088
    var localhostPaht=curPath.substring(0,pos);
    //获取带"/"的项目名，如：/test
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var path = localhostPaht+projectName;
	var data = [ deptId];
	$.callService('deptService','getTeaDepts',data,function(d){
		var html1 = "<div class='title_box'>"+
		" <h2 class='title'>";
		var deptName = "<a href='' id='refresh' style='color:#fff'>"+d[0].DEPT_NAME+"</a>";
		var html2 = "</h2> "+
		"<a class='open' href='#' onClick=\"openShutManager(this,'box')\">"+
		"<img src='"+ path +"/static/images/manager/xtxi_01.png' alt='点击打开' />"+
		"</a></div>";
		var htmlTitle = html1+deptName+html2;
		$("#people").empty();
		$("#people").append(htmlTitle);
		$.callService('deptService',"getDept",data,function(d){
		var htmlDept1 = "<div id='box'>"+
			"<div class='mr_frbox'>"+
			"<img class='mr_frBtnL prev' src='"+ path +"/static/images/manager/zuo.png' />"+
			"<div class='mr_frUl'> <ul>";

		var htmlDept2 = "";
		for(var i=0;i<d.length;i++){
			htmlDept2 = htmlDept2 + "<li><a href='javascript:selected("+d[i].DEPT_ID+");'>"+
			"<img src ='"+ path +"/static/images/manager/msx.png' />"+
			"<a class='department_name' href='#'>"+ d[i].DEPT_NAME+"<br /> <span class='people_all'> "+d[i].NUMS +" </span> </a></li>";
		}
		var htmlDept3 = "</ul>"+
			"</div>"+
			"<img class='mr_frBtnR next' src='"+ path +"/static/images/manager/you.png'/></div>";
		var htmlDept = htmlDept1+htmlDept2+htmlDept3;
		$("#people").append(htmlDept);
		$(".mr_frUl ul li img").hover(function() {
			$(this).css("border-color", "#A0C0EB");
		}, function() {
			$(this).css("border-color", "#d8d8d8")
		});
		$(".mr_frbox").slide({
			titCell : "",
			mainCell : ".mr_frUl ul",
			autoPage : true,
			effect : "leftLoop",
			autoPlay : true,
			vis : 4
		});
		$("#refresh").click(function(){
			refresh();
		})
	 });
 });
}

	//=点击展开关闭效果=
	function openShutManager(oSourceObj, oTargetObj, shutAble, oOpenTip, oShutTip) {
		var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
		var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
		var openTip = oOpenTip || "";
		var shutTip = oShutTip || "";
		if (targetObj.style.display != "none") {
			if (shutAble)
				return;
			targetObj.style.display = "none";
			if (openTip && shutTip) {
				sourceObj.innerHTML = shutTip;
			}
		} else {
			targetObj.style.display = "block";
			if (openTip && shutTip) {
				sourceObj.innerHTML = openTip;
			}
		}
	}
