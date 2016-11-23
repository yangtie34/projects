var listJava=[];
var pageFlag="";
$(function () {
	//隐藏button
	//--toor
	var toolAdd=$("#listBox #modalInput");//添加
	var toolView=$("#listBox .toolView");//查看
	var toolEdit=$("#listBox .toolEdit");//修改
	var toolRemove=$("#listBox .toolRemove");//删除
	var toolExcel=$("#listBox .toolExcel");//导出excel
	//--form
	var inputSubForm=$(".modal-footer .subForm");//确定
	var inputAlterForm=$(".modal-footer .alterForm");//修改
	var viewSubForm=$(".modal-footer .viewSubForm");//提交
	var checkYesForm=$(".modal-footer .checkYesForm");//审核通过
	var checkNoForm=$(".modal-footer .checkNoForm");//不通过
	//页面button
	//页面button
	var inputSub=$("#listBox div.submit button.inputSub");//提交
	var inputAll=$("#listBox div.submit button.inputAll");//全部提交
	var checkedSub=$("#listBox div.submit button.checkedSub");//审核通过
	var checkedNo=$("#listBox div.submit button.checkedNo");//不通过
	inputSub.hide();
	inputAll.hide();
	checkedSub.hide();
	checkedNo.hide();
	
	toolAdd.hide();
	//toolView.hide();
	toolExcel.hide();
	//toolRemove.hide();
	inputSubForm.hide();
	inputAlterForm.hide();
	viewSubForm.hide();
	checkYesForm.hide();
	checkNoForm.hide();
	//查看点击
	toolView.click(function(){
		var table0=$("#listBox .view-body>div:eq(0) tbody");
		var table1=$("#listBox .view-body>div:eq(1) tbody");
		switch(table0.find("input:checked").length){
		case 0:
			alert("请选择查看对象！");
			break;
		case 1:
			var index=table0.find("input:checked").parent().parent().parent().index();
			var alterbutton=$("#Modal_input button.alterForm");
			alterbutton.prev().hide();
			//viewSubForm.show();
			editIndex=index;
			alterForm(index);
			$("#Modal_input *").attr("disabled",true);
			$("#Modal_input a").hide();
			$("#Modal_input #thList").prev().hide();
			$("#Modal_input #xmccAlter").hide();
			$("#Modal_input #thList").prev().parent().prev().height("50px");
			$(".modal-footer .btn-default").attr("disabled",false);
			$(".modal-footer .btn-default").click(function(){
				$("#Modal_input *").attr("disabled",false);
				$("#Modal_input a").show();
			});
			break;	
		default:
			alert("只能选择一个修改对象！");
			break;
		}
	});
	//加载列名后
	$(".tableBody:eq(0) tr").bind("DOMNodeInserted",function(){
		$("#listBox .view-body>div:eq(1) tbody").html("<span class='navbar-brand'><span class='navbar-brand'>请选择状态类别</span></span>");
		if($(".container-fluid ul.nav>li").length>2){}else{
		var flags=[];
			for(var i=0;i<formObj.kyTemp.length;i++){
				if(formObj.kyTemp[i].info.code=="flagCode"){
					flags=formObj.kyTemp[i].data;
				}
			}
			var flagcolorscls=new Array("btn-success","btn-info","btn-danger");
			var flagcolors=new Array("#419641","#28a4c9","#b92c28");
			var menubuttonhtml="";
			
			for(var i=0;i<flags.length;i++){
				var flagmap={};
				flagmap.code=flags[i].code;
				flagmap.color=flagcolors[i];
				flagmap.name=flags[i].name;
				flagsMap.push(flagmap);
				menubuttonhtml+="<div class='btn-group' style='margin-left:25px'>"+
		      "<button code='"+flags[i].code+"' type='button' class='btn "+flagcolorscls[i]+
		      " dropdown-toggle'  aria-haspopup='true' aria-expanded='false'>"+
		      flags[i].name+"</button></div>";
			}
			menubuttonhtml="<li style='padding-top: 8px;padding-left: 50px;padding-bottom: 15px;'>"+menubuttonhtml+"</li>";
			$(".container-fluid ul.nav").append(menubuttonhtml);
			$(".container-fluid ul.nav>li>div.btn-group").click(function(){
				$("#listBox .view-body>div tbody").html("");;
				var flag=$(this).find("button").attr("code");
				getlistJava(flag);
			});
		}
	});
	//修改
	toolEdit.click(function(){
		if(pageFlag!="02"){
			var table0=$("#listBox .view-body>div:eq(0) tbody");
			var table1=$("#listBox .view-body>div:eq(1) tbody");
			switch(table0.find("input:checked").length){
			case 0:
				alert("请选择修改对象！");
				break;
			case 1:
				var index=table0.find("input:checked").parent().parent().parent().index();
				var alterbutton=$("#Modal_input button.alterForm");
				alterbutton.prev().hide();
				viewSubForm.show();
				editIndex=index;
				alterForm(index);
				break;	
			default:
				alert("只能选择一个修改对象！");
				break;
			}
		}else{
			alert("已审核通过不可修改！");
		}
	});
	
	toolRemove.click(function(){
		var table0=$("#listBox .view-body>div:eq(0) tbody");
		var table1=$("#listBox .view-body>div:eq(1) tbody");
		var inde=[];
		var listviewsub=[];
		table0.find("input").each(function(index){
			if ($(this).is(":checked")) {
				inde.push(index);
				listviewsub.push(listAll[index]);
			 }
		}); 
		if(inde.length<1){
			alert("没有选择删除的数据！");
		}else if(confirm("确定要删除数据吗？"))
		   {
		if(viewdelete(listJsToJava(listviewsub))){
		for(var i=inde.length-1;i>=0;i--){
			listAll.splice(inde[i],1);  
			table0.find("tr:eq("+inde[i]+")").remove();
			table1.find("tr:eq("+inde[i]+")").remove();
		}
		$("#listBox .view-body>div:eq(0) tbody input").prop("checked",false);
		tablenodechange();
		}else{
			alert("删除失败");
		}
		   }
	});
	viewSubForm.click(function(){
		getForm();
		$("#Modal_input").modal('toggle');
		getFormHtml(formObj);
		authList=[];
		var index=$("#listBox .view-body>div:eq(0) tbody input:checked").parent().parent().parent().index();
		listAll[index].temp.flagCode="01";
		listAll[index]=kyMap;
		var listviewsub=[];
		listviewsub.push(kyMap);
		if(viewSub(listJsToJava(listviewsub))){
			alertTotable(index);
		}else{
			alert("修改失败");
		};
	
	});
	
});
//
//获取id
var getAId=function(a,b,c,d){
	//"ky",i,"auth",k
	var id="";
	if(c==""){
		id=listJava[b].ky.id;
	}else{
		var authslength=listJava[b].auth.length;
		if(authslength-1<d){
			id=getId();
		}else{
			id=listJava[b].auth[d].id;
		}	
	}
	return id;
};
//获取列表
var getlistJava=function(flag){
	$.callService(
			'scientificService',
			'view',
			[clas,flag],
			function(data) {
				listJava=data;
				if(data.length==0){
					alert("无数据");
				}else{
					listJavaToJs(data);
					listTotable();
				};
			});	
};
//数据显示
var listTotable = function() {
	var tableHtml0="";var tableHtml1="";
	var tbody0=$("#listBox .view-body>div:eq(0) tbody");
	var tbody1=$("#listBox .view-body>div:eq(1) tbody");
	for(var i=0;i<listAll.length;i++){
		tableHtml0+="<tr class='tableRow' style='height: 25px;'>"+
		"<td class='td-rownumber'><div class='cell-rownumber'>"+(i+1)+"</div></td>"+
		"<td field='ck'><div style='text-align:left;height:auto;' class='cell-check '>"+
		"<input name='kyck' type='checkbox'>"+
		"</div></td>"+
		"</tr>";
		tableHtml1+="<tr class='tableRow' style='height: 25px;'>"+function(){
			var tdhtml="";
		for(var key in listAll[i]){
			if(key!="temp"){if(listAll[i][key].name==null)listAll[i][key].name="";
			tdhtml+="<td field="+key+"><div style='width:80px;text-align:left;height:auto;' class='cell'>"+listAll[i][key].name+"</div></td>";
			}else{
				var code=listAll[i][key].flagCode;
				var flag={};
				for(var k=0;k<flagsMap.length;k++){
					if(flagsMap[k].code==code){
						flag=flagsMap[k];
					}
				}
				tdhtml+="<td field="+key+"><div style='width:80px;text-align:left;height:auto;background-color: "+flag.color+";' class='cell'>"+flag.name+"</div></td>";
			}
		}
		return tdhtml;
		}()+"</tr>";
	}
	tbody1.html(tableHtml1);	
	tbody0.html(tableHtml0);
};
//数据修改
var alertTotable = function(index) {
	var tableHtml0="";var tableHtml1="";
	var tbody0=$("#listBox .view-body>div:eq(0) tbody");
	var tbody1=$("#listBox .view-body>div:eq(1) tbody");
		tableHtml0+="<tr class='tableRow' style='height: 25px;'>"+
		"<td class='td-rownumber'><div class='cell-rownumber'>"+(index+1)+"</div></td>"+
		"<td field='ck'><div style='text-align:left;height:auto;' class='cell-check '>"+
		"<input name='kyck' type='checkbox'>"+
		"</div></td>"+
		"</tr>";
		tableHtml1+="<tr class='tableRow' style='height: 25px;'>"+function(){
			var tdhtml="";
		for(var key in listAll[index]){
			if(key!="temp"){if(listAll[index][key].name==null)listAll[index][key].name="";
			tdhtml+="<td field="+key+"><div style='width:80px;text-align:left;height:auto;' class='cell'>"+listAll[index][key].name+"</div></td>";
			}else{
				var code=listAll[index][key].flagCode;
				var flag={};
				for(var k=0;k<flagsMap.length;k++){
					if(flagsMap[k].code==code){
						flag=flagsMap[k];
					}
				}
				tdhtml+="<td field="+key+"><div style='width:80px;text-align:left;height:auto;background-color: "+flag.color+";' class='cell'>"+flag.name+"</div></td>";
			}
		}
		return tdhtml;
		}()+"</tr>";
		tbody1.find("tr:eq("+index+")").replaceWith(tableHtml1);
};
var viewSub=function(list){
		var map={
				list:list,
				clas:clas
		};
		var result=false;
		$.callService(
				'scientificService',
				'update',
				[JSON.stringify(map)],
				function(data) {
					result=data;
				},null,null,null,false);
		return result;
	};
	//删除
var viewdelete=function(list){
		var map={
				list:list,
				clas:clas
		};
		var result=false;
		$.callService(
				'scientificService',
				'delete',
				[JSON.stringify(map)],
				function(data) {
					result=data;
				},null,null,null,false);
		return result;
	};