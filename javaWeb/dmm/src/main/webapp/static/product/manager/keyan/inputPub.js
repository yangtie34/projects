var formObj=null;
var listAll=[];
var kyMap={};
var authList=[];
var clas="";
var flagsMap=[];
function tableshow(pages){
	var table0=$("#listBox .view-body>div:eq(0) tbody");
	var table1=$("#listBox .view-body>div:eq(1) tbody");
	table0.find("tr").hide();table1.find("tr").hide();
	var pageMain=$("div.view-pager.pagination tr");
	var pagenum=Number(pageMain.find(".pagination-page-list").val());
	for(var i=0;i<pagenum;i++){
		table0.find("tr:eq("+((pages-1)*pagenum+i)+")").show();
		table1.find("tr:eq("+((pages-1)*pagenum+i)+")").show();
	}
	pageMain.find(".pagination-num").val(pages);
	if(pages==1){pageMain.find("td:eq(2)").hide();pageMain.find("td:eq(3)").hide();}else{
		pageMain.find("td:eq(2)").show();pageMain.find("td:eq(3)").show();
	}
	if(pages==Number($("div.view-pager.pagination tr .allpagenums").text())){
		pageMain.find("td:eq(9)").hide();pageMain.find("td:eq(10)").hide();}else{
			pageMain.find("td:eq(9)").show();pageMain.find("td:eq(10)").show();
		}
}
function tablenodechange() { 
	var tbody=$("#listBox .view-body>div:eq(1) tbody");
	var counts=tbody.find("tr").length;
	var table0=$("#listBox .view-body>div:eq(0) tbody");
	table0.find("tr:gt("+(counts-1)+")").each(function(){$(this).remove();});
	tbody.find("tr").each(function(index){
		$("#listBox .view-body>div:eq(0) tbody tr:eq("+index+") td:eq(0) div").text((index+1));
		var trs0=$("#listBox .view-body div.body-inner:eq(0) tbody tr:eq("+index+")");
		var trs1=$("#listBox .view-body div.body-inner:eq(1) tbody tr:eq("+index+")");
		if(index%2==1){
			trs0.removeClass("tableRow");
			trs0.addClass("row-alt");
			trs1.removeClass("tableRow");
			trs1.addClass("row-alt");
		}else{
			trs0.removeClass("row-alt");
			trs0.addClass("tableRow");
			trs1.removeClass("row-alt");
			trs1.addClass("tableRow");
		}
	});
	var pageMain=$("div.view-pager.pagination tr");
	var pagenum=pageMain.find(".pagination-page-list").val();//每页多少个
	var countsDiv=$("div.view-pager.pagination .pagination-info");
	countsDiv.text("共"+counts+"条记录");
	var pages=null;
	if(counts%pagenum==0){pages=parseInt(counts/pagenum);}else{pages=parseInt(counts/pagenum)+1;};
	if(pages==0)pages=1;
	pageMain.find(".pagination-num").val(pages);
	pageMain.find(".allpagenums").text(pages);
	tableshow(pages);
};
$(function() {
	$(".submit button").attr("style","padding:6px 12px;margin-left:50px");
	$("#index").show();
	$("#listBox").hide();
	//表单取消
	var formCancel=$("#Modal_input .modal-footer .btn-default");
	formCancel.click(function(){
		$("#Modal_input").modal('toggle');
		getFormHtml(formObj);
	});
	//主表分页功能
	tableshow(1);
	//分页区变化
	$("div.view-pager.pagination tr").find("td:eq(2) a").click(function(){
		$("#listBox .view-body>div:eq(0) tbody input").prop("checked",false);
		tableshow(1);});
	$("div.view-pager.pagination tr").find("td:eq(3) a").click(function(){
		$("#listBox .view-body>div:eq(0) tbody input").prop("checked",false);
		var pagenum=Number($("div.view-pager.pagination tr .pagination-num").val());
		tableshow(pagenum-1);});
	$("div.view-pager.pagination tr").find("td:eq(9) a").click(function(){
		$("#listBox .view-body>div:eq(0) tbody input").prop("checked",false);
		var pagenum=Number($("div.view-pager.pagination tr .pagination-num").val());
		tableshow(pagenum+1);});
	$("div.view-pager.pagination tr").find("td:eq(10) a").click(function(){
		$("#listBox .view-body>div:eq(0) tbody input").prop("checked",false);
		var pagenum=Number($("div.view-pager.pagination tr .allpagenums").text());
		tableshow(pagenum);});
	//页面条数变化
	$("div.view-pager.pagination tr .pagination-page-list").change(function() {
		$("#listBox .view-body>div:eq(0) tbody input").prop("checked",false);
		var table=$("#listBox .view-body>div:eq(1) tbody");
		var counts=table.find("tr").length;
		var pagenum=$(this).val();//每页多少个
		var pages=null;
		if(counts%pagenum==0){pages=parseInt(counts/pagenum);}else{pages=parseInt(counts/pagenum)+1;};if(pages==0)pages=1;
		$("div.view-pager.pagination tr").find(".allpagenums").text(pages);
		tableshow(1);});
	//列表变化分页功能
	$("#listBox .view-body>div:eq(1) tbody").bind("DOMNodeInserted",tablenodechange);
	$(".header-inner .header-check input").click(function(){
		if(this.checked){
			$("#listBox .view-body>div:eq(0) tbody input").prop("checked",true);
		}else{
			$("#listBox .view-body>div:eq(0) tbody input").prop("checked",false);
		
		}
	});
	$(".table").click(
					function() {
						$("#Modal_input .modal-content .modal-header button").hide();
						var tableName = $(this).attr("tableName");
						clas=tableName;
						if ($(".noclick").text() != $(this).text()) {
							 formObj=null;
							 listAll=[];
							 kyMap={};
							 authList=[];
							 $("#listBox .view-body>div tbody").html("");
							$(".noclick").text($(this).text());
							$(".noclick").attr({"tableFather":$(this).attr("tableFather"),"tableFatherName":$(this).attr("tableFatherName")});
							$.callService(
											'scientificService',
											'getCloumn',
											[ tableName ],
											function(d) {
												formObj=d;
												var header = "";
												for ( var i=0;i<d.ky.length;i++ ) {
													var key =d.ky[i].info;
													header += "<td field='"
															+ key.code
															+ "' style=''>"
															+"<div class='cell' style='width:80px; text-align: center;'>"
															+ "<span>"
															+ key.name
															+ "</span><span class='sort-icon'>&nbsp;</span>"
															+ "</div></td>";
												}
												header += "<td field='checkSub' style=''>"
														+"<div class='cell' style='width:80px; text-align: center;'>"
														+ "<span>状态</span><span class='sort-icon'>&nbsp;</span>"
														+ "</div></td>";
												$(".tableBody:eq(0) tr").html("");$(".tableBody:eq(0) tr").append(header);
												getFormHtml(formObj);
												$("#index").hide();
												$("#listBox").show();
											});
						}
						;
					});
});

var form = "";

/* 获取表单html */
var formSelect = function(a) {
	if (a.selectedIndex != 0) {
		$(a).next().hide();
	} else {
		$(a).next().show();
	};
};
var formSelectInput = function(a) {
	var option0 = $(a).prev().children().eq(0);
	var valselect0 = $(a).val();
	if (valselect0 == "") {
		valselect0 = "--请输入--";
	}
	option0.text($(a).val()); // 更改对应的值
};

var getFormHtml = function(obj) {
	var idindex=0;
	form="";
	for ( var i=0;i<obj.ky.length;i++ ) {
		var key =obj.ky[i];

		var formDiv = "<div style='margin-top:5px;' class='control-group'><label class='control-label' style='width:130px;float: left;padding-right: 0px;line-height: 5px;' field='"
				+ key.info.code + "'>" + key.info.name + "：</label>";
		var inp = "";
		var xmcc=""; 
		switch (key.info.type) {
		case "xmcc":
			 var find={
				 text:"项目ID",
				 items:[
				         {text:"项目名称",type:"text"},
				         ],
				 button:{text:"查询",type:"TResProject"},
				 submit:{text:"确定"}
		 };
			inp="<label style='padding-top: 0px; 'class='checkbox-inline'><input type='checkbox' id='inlineCheckbox1' value='option1'>&nbsp</label></DIV><DIV id='xmccxq'>" +findlist(find)+"<HR/>";
			idindex=1;
			break;
		case "code":
			var val0 = 0;
			var val00 = "new";
			for (var j = 0; j < key.data.length; j++) {
				if (j == 0) {
					if (key.data[j].code.indexOf("0")>=0) {
						val00 += "0";
					}
				}
				inp += " <option value='" + key.data[j].code + "'>"
						+ key.data[j].name + "</option>";
				if (val0 < Number(key.data[j].code)) {
					val0 = Number(key.data[j].code);
				}
			}
			inp = "<select style='width:180px;height:21px' class='input-xlarge  formSelect' onchange='javascript:formSelect(this);'>"
					+ " <option value='"
					+ val00
					+ (val0 + 1)
					+ "'>--请输入--</option>" + inp;
			inp += "</select><input style='position:relative;left:-180px' class='input-xlarge formSelectInput' placeholder='请输入' onchange='javascript:formSelectInput(this);' type='text' value=''>";
			break;
		case "entity":
			for (var j = 0; j < key.data.length; j++) {
				inp += " <option value='" + key.data[j].code + "'>"
						+ key.data[j].name + "</option>";
			}
			inp = "<select style='width:180px;height:21px' class='input-xlarge  formSelect' onchange='javascript:formSelect(this);'>"
				+inp;
			inp += "</select>";
			break;
		case "nyr":
			inp = "<input type='text' value='' id='datetimepicker'>";
			break;
		case "n":
			inp = "<select class='input-xlarge'>";
			for (var j = new Date().getFullYear(); j > 1990; j--) {
				inp += "<option value="+j+">" + j + "</option>";
			}
			inp += "</select>";
			break;
		case "remark":
			inp = "<div class='textarea'><textarea type='' class=''> </textarea></div>";
			break;
		case "text":
			inp = "<input type='text' placeholder='请输入' class='input-xlarge'>";
			break;
		
		case "auth":
			 var peopleIdentityCodeData=null;
			 var RoleCodeData=null;
			 var roleCode=null;
			 for (var j = 0; j < obj.kyAuth.length; j++) {
					if (obj.kyAuth[j].info.code== "peopleIdentityCode") {
						peopleIdentityCodeData=obj.kyAuth[j].data;
					}
					if (obj.kyAuth[j].info.code.indexOf("RoleCode")>0) {
						RoleCodeData=obj.kyAuth[j].data;
						roleCode=obj.kyAuth[j].info.code
						
					}
			 }
			 var find={
				 text:"",
				 items:[
				         {text:"作者",type:"text",code:"peopleId"},
				         {text:"身份",type:"select",data:peopleIdentityCodeData,code:"peopleIdentityCode"},
				         {text:"导师",type:"text",code:"teaNo"},
				         {text:"角色",type:"select",data:RoleCodeData,code:roleCode},
				         ],
				 button:{text:"添加",type:"auth"},
				 submit:{text:"确定"}
		 };
			 inp=findlist(find);
					break;
		}
		
		form += formDiv + "<div class='controls'>" + inp + "</div></div>";
	}
	$("#Modal_input #myModalLabel").text($(".noclick").text() + "详单");
	$("#Modal_excel #myModalLabel").text($(".noclick").text() + "导入");
	$("#Modal_input fieldset").html(form);
	$("#thList").parent().prev().css({
		height : "90px"
	});
	$(".list-table").hide();
	$("#xmccxq .list-table").show();
	$("#xmccxq").hide();
	$("#Modal_input .checkbox-inline").click(function(){
		$("#xmccxq").toggle();
	});
	$("#datetimepicker").datetimepicker({
		 language: 'zh-CN', //汉化 
		  format:"yyyy-mm-dd",      //格式化日期
		  minView: "month", //选择日期后，不会再跳转去选择时分秒 
		   autoclose:true, //选择日期后自动关闭 
		   todayBtn: 1,
           minuteStep: 5
	 });
	$("#Modal_input fieldset>div:eq("+0+")").hide();
/*父级table查询*/
	 if($(".noclick").attr("tableFather")!=""){
		/* $("#Modal_input fieldset>div:gt("+(idindex+1)+")").hide();
		 $("#Modal_input fieldset>div:eq("+(idindex+1)+") input").attr({placeholder:"",readOnly:"true"});*/
		 $("#Modal_input fieldset>div:gt(0)").hide();
		 var find={
				 text:"请选择" + $(".noclick").attr("tableFatherName"),
				 items:[
				         {text:"名称",type:"text"},
				         ],
				 button:{text:"查询",type:$(".noclick").attr("tableFather")},
				 submit:{text:"确定"}
		 };
		
		 
		 var kyIdFind=findlist(find);
			$("#Modal_input fieldset").append(kyIdFind);
	 };
	$(".list-table .page").hide(); 
//作者列表处操作	
	if($("fieldset #thList").length>0){
		$("fieldset #thList").prev().remove();
		$("fieldset #thList").parent().append("<br><a id='thListToor' style='font-size:10px;float: right;'>查询作者与导师</a>");
		$("fieldset #thList").prev().find("input[onclick]").attr("onclick","");
		$("fieldset #thList").prev().height("40px");
		$("fieldset #thList").prev().find(">div:eq(0)>input,>div:eq(2)>input").prop("disabled","disabled");
		$("fieldset #thList").prev().find(">div:eq(1) select").change(function(){
			if($(this).find("option:selected").attr("code")=="03"){
				$(this).parent().prev().find("input").removeAttr("disabled");
			}else{
				$(this).parent().prev().find("input").attr("disabled","disabled");
			}
		});
//作者点击添加
		$("fieldset #thList").prev().find("input[onclick]").click(function(){
			var key1=$(this).parent().find(">div:eq(0) label").attr("field");
			var val1={code:$(this).parent().find(">div:eq(0) input").attr("code"),
					name:$(this).parent().find(">div:eq(0) input").val()};
			var key2=$(this).parent().find(">div:eq(1) label").attr("field");
			var option=$(this).parent().find(">div:eq(1) select").find("option:selected");
			var val2={
					code:option.attr("code"),
					name:option.text()
			};
			var key3=$(this).parent().find(">div:eq(2) label").attr("field");
			var val3={code:$(this).parent().find(">div:eq(2) input").attr("code"),
					name:$(this).parent().find(">div:eq(2) input").val()};
			var key4=$(this).parent().find(">div:eq(3) label").attr("field");
			option=$(this).parent().find(">div:eq(3) select").find("option:selected");
			var val4={
					code:option.attr("code"),
					name:option.text()
			};
			var auth={};auth[key1]=val1;auth[key2]=val2;auth[key3]=val3;auth[key4]=val4;
			authList.push(auth);
		option = $("<option>").val(1).text(val2.name+"_"+val1.name+"_"+val4.name+"_导师："+val3.name);
				$(this).parent().parent().find("select#thList").append(option);
			});
		
		//生成查询人员工号
		var find={
				 text:"查询工号",
				 items:[
				         {text:"姓名",type:"text"},
				         {text:"身份",type:"select",data:[{code:'01',name:'学生'},{code:'02',name:'教师'}]},
				         ],
				 button:{text:"查询",type:"auth"},
				 submit:{text:"添加到作者" }
		 };
		
		 $("fieldset #thList").parent().append(findlist(find));
		 $("fieldset #thListToor").next().css('background-color','#F2F2F2');
		 $("fieldset #thListToor").next().hide();
		 $("fieldset #thListToor").next().find(".page").hide();
		 var page=$("fieldset #thListToor").next().find(".page");
		 page.height("35px");
		 
		 //添加导师工号点击事件
		 page.find(">span:eq(1)").append("<input class='btn btn-default addteano' type='button' style='height: 20px;' value='添加到导师'/>");
		 page.find(">span:eq(1) .addteano").click(function(){
			 var list=$(this).parent().parent().prev();
			 var id=list.find("tbody tr:eq("+list.find("input[checked='checked']").index()+") td:eq(1)").text();
			 var name=list.find("tbody tr:eq("+list.find("input[checked='checked']").index()+") td:eq(2)").text();
			 var teano=page.parent().parent().parent().parent().find(".form-inline>div:eq(2) input");
			 teano.val(name);
			 teano.attr("code",id);
			 list.parent().parent().prev().parent().hide();
		 });
		 
		 
		 $("fieldset #thListToor").next().find("label").text("");
		 $("fieldset #thListToor").click(function(){
			 $(this).next().toggle();
		 });
	}
	
	//查询列表确定操作
	$(".list-table .page button").click(function(){
		var list=$(this).parent().parent().prev();
		var lable=list.parent().parent().prev();
		var id=list.find("tbody tr:eq("+list.find("input[checked='checked']").index()+") td:eq(1)").text();
		var name=list.find("tbody tr:eq("+list.find("input[checked='checked']").index()+") td:eq(2)").text();
		lable.attr("value",id);
		if(lable.text()=="项目ID："){
			lable.next().text(name);
			lable.next().attr("code",id);
		}else if(lable.parent().parent().find("#thList").length>0){
			var sf=list.parent().prev().find("div :eq(1) select").val();//身份
			lable.parent().parent().find(".form-inline div:eq(0) input").val(name);
			lable.parent().parent().find(".form-inline div:eq(0) input").attr("code",id);
			lable.parent().parent().find(".form-inline div:eq(1) select").val(sf);
			lable.parent().hide();
		}else{
			 $("#Modal_input fieldset>div:last").remove();
			 $("#Modal_input fieldset>div:gt(0)").show();
			 $("#Modal_input fieldset>div:eq(1) label").attr("value",id);
			 $("#Modal_input fieldset>div:eq(1) input").attr("value",id);
		}
	}); 
	//作者列表小红叉操作
	$(".controls a.glyphicon-remove").click(function(){
		authList.splice($(this).prev().find("option:selected").index(),1);  
		$(this).prev().find("option:selected").remove();
	});
};

//生成查询html
function findlist(find){
	//find {lable："",chaxun:[{lable:"",type:""},{}]}
	 var findDiv1 = "<div style='margin-top:5px;' class='control-group'><label class='control-label' style='width:130px;float: left;padding-right: 0px;line-height: 5px;' field=''>" +
	 	find.text+"：</label>"+
		 "<div class='controls'>" ;
	 var findDiv2 ="<div class='form-inline'  id='auth-group'>"+
		function() {
			var list = find.items;
			var html = "";
			for (var i = 0; i < list.length; i++) {
				html += "<div class='control-group' style='float:left;padding-right:10px;'>"
						+ " <label class='control-label' field="+list[i].code+">"
						+ list[i].text + ":</label>";
				switch(list[i].type){
				case "text":
					html += "<input style='width:50px;' type='text' class='input-xlarge'>";
					break;
				case "select":
					html += " <select  class='input-xlarge'>"+
						function() {
					var option="";
					for(var j=0;j<list[i].data.length;j++){
						option+=" <option code="+list[i].data[j].code+">"+list[i].data[j].name+"</option>";
					}
					return option;
				}()
						+ "</select>";
					break;
				}
				html += "</div>";
				if(i==1&&list.length>3){
					html+="<br>";
				}
			}
			return html;
		}

		() + "<input type='button' id='kyIdFind' onclick=findOut('"+find.button.type+"',this); value='"+find.button.text+"'></div>";
		var findDiv3 =function() {
			var html = "<div class='list-table'>"
				+ "<table class='table table-striped'>"
				+ " <thead>" 
				+ "<tr>" 
				+ " </tr>"
				+ " </thead>" 
				+ "<tbody>" 
				+ " </table>" 
				+ "<div class='page' style='height: 30px;color: #999;padding: 6px 0;overflow: hidden;zoom: 1;border-top: 1px dashed #ddd;'>" 
				+ "<span style='float: left;'>" 
				+ "<a class='prev'>上一页</a>&nbsp&nbsp " 
				+ "<a class='next'>下一页</a>&nbsp&nbsp " 
				+ "<span>当前第<span id='pageId'>1</span>页，每页<span id='pageNum'>10</span>条，共<span id='pageCounts'>1</span>条</span>" 
				+ "</span>" 
				+ "<span style='float: right;'>" 
				+"<button class='btn btn-default' type='button' style='height: 20px;'>"+find.submit.text+"</button>" 
				+ "</span>" 
				+ "</div>" 
				+" </div>";
		return html;
	}() ;
	var findDiv4 = "</div>";
	findDiv=findDiv1+findDiv2+findDiv3+ "</div>"+findDiv4;	
	if(find.button.text=="添加"){
		var authlist="<select style='width:350px;' id='thList'class='input-xlarge authList' multiple='multiple'>"
			+ "</select>"
			+"<a class='glyphicon glyphicon-remove' aria-hidden='true' style='color: red;float: none;'></a>";
		findDiv=findDiv2+findDiv3+authlist+ "</div>";	
	}
	return findDiv;
}
//点击查询执行方法
function findOut(type,btn){
var str=null;
switch(type){
case "auth":
	str={
		type:"auth",
		items:{
			name:$(btn).parent().find("div :eq(0) input").val(),
			type:$(btn).parent().find("div :eq(1) select").val(),
		},
};

	break;
	default:
		str={
			type:"ky",
			items:{
				name:$(btn).prev().find("input").val(),
				type:type,
			},
	};
		break;
}
$(btn).parent().next().find("thead tr th,tbody *,.page").hide();
$.callService(
		'scientificService',
		'findOut',
		[JSON.stringify(str)],
		function(data) {
			var thead=data.field.split(" ");
			var tbody=data.list;
			var theadHtml="<th></th> <th>序号</th>";
			for(var i=0;i<thead.length;i++){
				theadHtml+="<th>"+thead[i]+"</th>";
			}
			var tbodyHtml="";
			for(var i=0;i<tbody.length;i++){
				tbodyHtml+="<tr><td><input type='radio' name='inlineRadioOptions' id='inlineRadio1' value='option1'></td> <th scope='row' style='width:30px'>"+(i+1)+"</th> "+function(){
					var tbodytr="";
					for(var key in tbody[i]){
						tbodytr+="<td>"+tbody[i][key]+"</td>";
					}
					return tbodytr;
				}()+"</tr>";
			}
			if(tbodyHtml==""){
				tbodyHtml="<tr><td></td><td>无数据</td></tr>";
			}
			$(btn).parent().next().find("thead tr").html(theadHtml);
			$(btn).parent().next().find("tbody").html(tbodyHtml);
			if($(btn).parent().parent().find("select#thList").length>0){
				if(tbody.length==0){
					alert("无数据！");
				}else{
				$(btn).parent().parent().find("select#thList").hide();
				$(btn).parent().parent().find("a.glyphicon-remove").hide();
				$(btn).parent().next().show();}
			}
			//查询分页
			var page=$(btn).parent().next().find(".page");
			page.show();
			if(tbodyHtml=="<tr><td></td><td>无数据</td></tr>"){page.hide();}
			$(btn).parent().next().find("tbody tr:eq(0) input").attr('checked', 'checked');
			page.find(".prev").hide();
			page.find("#pageCounts").text(tbody.length);//pageId pageNum  pageCounts
			if(tbody.length<=Number(page.find("#pageNum").text())){
				page.find(".next").hide();
			}
			var num=0;
			page.find("#pageId").text(1);
			$(btn).parent().next().find("tbody tr:gt("+num+9+")").hide();
			page.find(".next").click(function(){
				num+=10;
				page.find("#pageId").text(Number(page.find("#pageId").text())+1);
				$(btn).parent().next().find("tbody tr:visible").hide();
				for(var i=0;i<10;i++){
					$(btn).parent().next().find("tbody tr:eq("+(num+i)+")").show();
				}
				page.find(".prev").show();
				if((num+10)>=Number(page.find("#pageCounts").text())){
					page.find(".next").hide();
				};
			});
			page.find(".prev").click(function(){
				num-=10;
				page.find("#pageId").text(Number(page.find("#pageId").text())-1);
				$(btn).parent().next().find("tbody tr:visible").hide();
				for(var i=0;i<10;i++){
					$(btn).parent().next().find("tbody tr:eq("+(num+i)+")").show();
				}
				page.find(".next").show();
				if(num<=9){
					page.find(".prev").hide();
				};
			});
		});
};
var alterForm= function(index) {
	kyMap=listAll[index];
	$('#Modal_input').modal({backdrop: 'static', keyboard: false});
	var formbody=$('#Modal_input fieldset');
	for ( var i=0;i<formObj.ky.length;i++ ) {
		var key =formObj.ky[i];
		var lable=formbody.find("label[field="+key.info.code+"]");
		if(lable.text()!="项目ID："&&lable.parent().parent().find("#thList").length==0&&i==0){
			 $("#Modal_input fieldset>div:last").remove();
			 $("#Modal_input fieldset>div:gt(0)").show();
		}
		var value=null;
		switch (key.info.type) {
		case "xmcc":
			if(kyMap.xmcc.name!=null&&kyMap.xmcc.name.length>0){
				lable.next().find("input").attr('checked', 'checked');
				$("<div class='controls' code="+kyMap.xmcc.code+">"+kyMap.xmcc.name+"</div>")
				.insertAfter(lable.next().next().find("label:eq(0)")); 
				 var htmlalter="<span id='xmccAlter' style='color: red;'>修改</span>";
				lable.next().find("label").parent().append(htmlalter);
				lable.next().next().show();
				lable.next().next().find(">div>div:eq(1)").hide();
				 lable.next().find("label").parent().find("#xmccAlter").click(function(){
					 $(this).parent().next().find(">div>div:eq(1)").show();
					 $(this).parent().next().find(">div>div:eq(0)").remove();
					 $(this).remove();
				 });
			}
			break;
		case "code":
		case "entity":
		case "n":
			lable.next().find("select option[value="+kyMap[key.info.code].code+"]").attr("selected",true);
			lable.next().find("select").next().hide();
			if(kyMap[key.info.code].code.indexOf("new")>=0){
				lable.next().find("select option[value='"+kyMap[key.info.code].code+"']").text(kyMap[key.info.code].name);
				lable.next().find("select").next().show();
				lable.next().find("select").next().val(kyMap[key.info.code].code);
			}
			break;
		case "nyr":
		case "text":
			lable.next().find("input").val(kyMap[key.info.code].name);
			break;
		case "remark":
			lable.next().find("textarea").val(kyMap[key.info.code].name);
			break;
		case "auth":
			authList=kyMap[key.info.code].data;
			for(var l=0;l<authList.length;l++){
				var j=1;
				var h1="";var h2="";var h3="";var h4="";
			for(var k in authList[l]){
				switch(j){
				case 1:h1=authList[l][k].name;
					break;
				case 2:h2=authList[l][k].name;
					break;
				case 3:h3=authList[l][k].name;
					break;
				case 4:h4=authList[l][k].name;
					break;
				}
				j++;
			}
				option = $("<option>").val(1).text(h2+"_"+h1+"_"+h4+"_导师："+h3);
				formbody.find("select#thList").append(option);
			}
			
			break;
		}
	}	
};
//获取表单
var getForm = function() {
	var fmObj=$("#Modal_input fieldset");
	fmObj.find("#thListToor").next().remove();
	
	//获取值
	for ( var i=0;i<formObj.ky.length;i++ ) {
		var key =formObj.ky[i];
		var lable=fmObj.find("label[field="+key.info.code+"]");
		var value=null;
		switch (key.info.type) {
		case "xmcc":
			var val="";
			var code="";
			if(lable.next().find("input").is(":checked")){
				val=lable.next().next().find("label").next().text();
				code=lable.next().next().find("label").next().attr("code");
			}
			value={ code:code,
					name:val
					};
			break;
		case "code":
		case "entity":
		case "n":
			var option=lable.next().find("select").find("option:selected");
			if(option.text()=="--请输入--"){
				alert("请确定输入值！");
				throw "";
			}
			value={ code:option.attr("value"),
					name:option.text()
					};
			break;
		case "nyr":
		case "text":
			var name=lable.next().find("input").val();
			value={ code:"",
				name:name
				};
			break;
		case "remark":
			value={ code:"",
				name:lable.next().find("textarea").val()
				};
			break;
		case "auth":
			value={ code:"",
				name:function(){
					var name="";
					for(var k=0;k<authList.length;k++){
						name+=authList[k].peopleId.name+",";
					}
					return name;
				}(),
				data:authList
				};
			break;
		}
		kyMap[key.info.code]=value;
	}
	kyMap.temp={};
	for(var i=0;i<formObj.kyTemp.length;i++){
		if(formObj.kyTemp[i].info.code=="kylbCode"){
			for(var j=0;j<formObj.kyTemp[i].data.length;j++){
				if(formObj.kyTemp[i].data[j].name==$(".noclick").text()){
					kyMap.temp.kylbCode=formObj.kyTemp[i].data[j].code;
					break;
				}
			}
			break;
		}
	}
	kyMap.temp.flagCode="01";//未审核
};
var listJsToJava=function(listJs){
	var listJava=[];
	
	for(var i=0;i<listJs.length;i++){
		var objMap={};
		var mapky={};
		for ( var j=0;j<formObj.ky.length;j++ ) {
			var key =formObj.ky[j];
			var maptmp={};
			switch (key.info.type) {
			case "xmcc":
				mapky[key.info.code]=listJs[i][key.info.code].code;
				break;
			case "code":
			case "entity":
			case "n":
				var val=listJs[i][key.info.code].code;
				if(val.indexOf("new")>=0){
					val+=","+listJs[i][key.info.code].name;
				}
				mapky[key.info.code]=val;
				break;
			case "nyr":
			case "text":
			case "remark":
				mapky[key.info.code]=listJs[i][key.info.code].name;
				break;
			case "auth":	
				var zzList=listJs[i][key.info.code].data;
				mapky[key.info.code]=function(){
					var names="";
					for(var l=0;l<zzList.length;l++){
						names+=zzList[l].peopleId.name+",";
					}
					return names;
				}();
				var listMap=[];
				for ( var k=0;k<listJs[i][key.info.code].data.length;k++ ) {
					var mapzzjs=listJs[i][key.info.code].data[k];
					var mapzz={};
					var id=getAId("ky",i,"auth",k);
					mapzz.id=id;
					var xwname="";//校外作者名称
					if(mapzzjs.peopleId.code==null){
						xwname=mapzzjs.peopleId.name;
					}
					for(var keyau in mapzzjs){
						mapzz[keyau]=mapzzjs[keyau].code;
						if(mapzzjs[keyau].code==null){
							mapzz[keyau]="";
						}
					}
					for(var ke=0; ke<formObj.kyAuth.length;ke++){
						var keyau=formObj.kyAuth[ke].info.code;
						if(mapzz[keyau]==null){
							if(keyau=="order"){
								mapzz[keyau]=k+1;	
							}else if(keyau=="peopleName"){
								mapzz[keyau]=xwname;	
							}else{
								mapzz[keyau]=listJs[i].id.name;
							}
						}
					}
					listMap.push(mapzz);
				}
				objMap.auth=listMap;
				break;
			}
		}
		objMap.ky=mapky;
		objMap.temp=listJs[i].temp;
		if(objMap.temp.id==null){
			objMap.temp.id=objMap.ky.id;
			objMap.temp.kyId=objMap.ky.id;
			objMap.temp.peopleId="";
			objMap.temp.deptId="";
		}
		listJava.push(objMap);
	}
	for(var i=0;i<listJava.length;i++){
		if(listJava[i].auth==null)listJava[i].auth=[];	
	}
	return listJava;
};
var listJavaToJs=function(listJava){
	var listJs=[];
	for(var i=0;i<listJava.length;i++){
		var mapky={};
		for ( var j=0;j<formObj.ky.length;j++ ) {
			var key =formObj.ky[j];
			var maptmp={};
			mapky[key.info.code]={};
			switch (key.info.type) {
			case "xmcc":
				mapky[key.info.code].code=listJava[i].ky[key.info.code];
				if(mapky[key.info.code].code!=null){
				var str={id:mapky[key.info.code].code,
						type:"项目"};
				$.callService(
						'scientificService',
						'viewFindName',
						[JSON.stringify(str)],
						function(data) {
							mapky[key.info.code].name=data;
						},null,null,null,false);
				}
				break;
			case "code":
			case "entity":
				mapky[key.info.code].code=listJava[i].ky[key.info.code];
				for(var k=0;k<key.data.length;k++){
					if(key.data[k].code==mapky[key.info.code].code){
						mapky[key.info.code].name=key.data[k].name;	
					}
				};
				break;
			case "n":
			case "nyr":
			case "text":
			case "remark":
				mapky[key.info.code].code=listJava[i].ky[key.info.code];
				mapky[key.info.code].name=listJava[i].ky[key.info.code];
				break;
			case "auth":
				var names="";
					var data=[];
				for ( var k=0;k<listJava[i].auth.length;k++ ) {
					var authjava=listJava[i].auth[k];
					var authjs={};
					for(var keyauth in authjava){
						if (keyauth== "peopleIdentityCode"||keyauth.indexOf("RoleCode")>0) {
							authjs[keyauth]={};
							authjs[keyauth].code=authjava[keyauth];
							for(var l=0;l<formObj.kyAuth.length;l++ ){
								if(keyauth==formObj.kyAuth[l].info.code){
									var d=formObj.kyAuth[l].data;
									for(var h=0;h<d.length;h++){
										if(authjs[keyauth].code==d[h].code){
											authjs[keyauth].name=d[h].name;	
											break;
										}
									}
									break;
								}
							}
							break;
						}
					}
					for(var keyauth in authjava){
						if(keyauth== "peopleId"){
							authjs[keyauth]={};
							authjs[keyauth].code=authjava[keyauth];
							var str={id:authjs[keyauth].code,
									type:null}
							if(authjs.peopleIdentityCode.code=="03"){
								authjs[keyauth].name=authjava.peopleName;
							}else{
							switch(authjs.peopleIdentityCode.code){
							case "01":str.type="学生";
								break;
							case "02":str.type="教师";
								break;
							}
							$.callService(
									'scientificService',
									'viewFindName',
									[JSON.stringify(str)],
									function(data) {
										authjs[keyauth].name=data;
									},null,null,null,false);
							}
							names+=authjs[keyauth].name+",";
						}else if(keyauth== "teaNo"){authjs[keyauth]={};
							authjs[keyauth].code=authjava[keyauth];
							authjs[keyauth].name=authjava[keyauth];
						}
					}
					data.push(authjs);
				}
				
				mapky[key.info.code].code="";
				mapky[key.info.code].name=names;
				mapky[key.info.code].data=data;
				break;
			}
		}
		maptmp=listJava[i].temp;
		mapky.temp=maptmp;
		listJs.push(mapky);
	}
	listAll=listJs;
};
var getId=function(){
	var id="";
	$.callService(
			'scientificService',
			'selectAId',
			[""],
			function(data) {
				id=data;
			},null,null,null,false);
	return id;
};