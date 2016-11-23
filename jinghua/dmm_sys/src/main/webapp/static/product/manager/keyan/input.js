
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
	var inputSub=$("#listBox div.submit button.inputSub");//提交
	var inputAll=$("#listBox div.submit button.inputAll");//全部提交
	var checkedSub=$("#listBox div.submit button.checkedSub");//审核通过
	var checkedNo=$("#listBox div.submit button.checkedNo");//不通过
	checkedSub.hide();
	checkedNo.hide();
	
	toolView.hide();
	inputAlterForm.hide();
	viewSubForm.hide();
	checkYesForm.hide();
	checkNoForm.hide();
	
	var flagmap={};
	var flagcolors=new Array("#419641","#b92c28","red");
	flagmap.code="01";
	flagmap.color=flagcolors[1];
	flagmap.name="未提交";
	flagsMap.push(flagmap);
	flagmap={};
	flagmap.code="10";
	flagmap.color=flagcolors[0];
	flagmap.name="已提交";
	flagsMap.push(flagmap);
	flagmap={};
	flagmap.code="05";
	flagmap.color=flagcolors[2];
	flagmap.name="请修改";
	flagsMap.push(flagmap);
	//隐藏第一列
	$(".tableBody").bind("DOMNodeInserted", function() { 
		$(".tableBody>tr").each(function(){
			$(this).find("td:eq(0)").hide();
		});
	});
	//添加
	toolAdd.click(function(){
		var alterbutton=$("#Modal_input button.alterForm");
		alterbutton.prev().show();
		alterbutton.hide();
	$('#Modal_input').modal({backdrop: 'static', keyboard: false});
	});
	//导入excel
	toolExcel.click(function(){
		$('#Modal_excel').modal({backdrop: 'static', keyboard: false});
	});
	//toor删除click
	toolRemove.click(function(){
		var table0=$("#listBox .view-body>div:eq(0) tbody");
		var table1=$("#listBox .view-body>div:eq(1) tbody");
		var inde=[];
		table0.find("input").each(function(index){
			if ($(this).is(":checked")) {
				inde.push(index);
				$(this).attr("checked",false);
			 }
		}); 
		for(var i=inde.length-1;i>=0;i--){
			listAll.splice(inde[i],1);  
			table0.find("tr:eq("+inde[i]+")").remove();
			table1.find("tr:eq("+inde[i]+")").remove();
		}
		tablenodechange();
	});
	//toor修改click
	toolEdit.click(function(){
		var table0=$("#listBox .view-body>div:eq(0) tbody");
		var table1=$("#listBox .view-body>div:eq(1) tbody");
		switch(table0.find("input:checked").length){
		case 0:
			alert("请选择修改对象！");
			break;
		case 1:
			var index=table0.find("input:checked").parent().parent().parent().index();
			if(filterByFlag([listAll[index]]).length>0){
			var alterbutton=$("#Modal_input button.alterForm");
			alterbutton.prev().hide();
			alterbutton.show();
			alterForm(index);
			}else{
				alert("对象已提交！不能在此页面修改！");
			}
			break;	
		default:
			alert("只能选择一个修改对象！");
			break;
		}
		if(table0.find("input").length){
			
		}
	});
	//提交点击
	inputSub.click(function(){
		var checkedtr=$("#listBox .view-body>div:eq(0) tbody input:checked").parent().parent().parent();
		var list=[];
		checkedtr.each(function(index){
			list.push(listAll[index]);
		});
		var listjs=filterByFlag(list);
		if(listjs.length>0){
			if(insertList(listJsToJava(listjs))){
				checkedtr.each(function(index){
					listAll[index].temp.flagCode="10";
					listTotable(index);
				});
			}else{
				alert("提交失败！");
			};
			
		}else{
			alert("无需提交对象！");
		}
	});
	//全部提交点击
	inputAll.click(function(){
		var listjs=filterByFlag(listAll);
		if(listjs.length>0){
			insertList(listJsToJava(listjs));
			for(var i=0;i<listAll.length;i++){
				listAll[i].temp.flagCode="10";
				listTotable(i);
			}
		}else{
			alert("无需提交对象！");
		}
	});
//表单确定
	inputSubForm.click(function(){
		getForm();
		authList=[];
		kyMap.id.name=getAId("ky","","auth","");
		listAll.push(kyMap);
		listTotable("add");
	$("#Modal_input").modal('toggle');
	getFormHtml(formObj);
});
//表单修改
	inputAlterForm.click(function(){
	getForm();
	$("#Modal_input").modal('toggle');
	getFormHtml(formObj);
	authList=[];
	var index=$("#listBox .view-body>div:eq(0) tbody input:checked").parent().parent().parent().index();
	listAll[index]=kyMap;
	listTotable(index);
});
});

//添加一条数据
var listTotable = function(index) {
	var tableHtml0="";var tableHtml1="";
	var tbody0=$("#listBox .view-body>div:eq(0) tbody");
	var tbody1=$("#listBox .view-body>div:eq(1) tbody");
	var i =0;
	if(index=="add"){
		i=listAll.length-1;
	}else{
		i=index;
	}
		tableHtml0+="<tr class='tableRow' style='height: 25px;'>"+
		"<td class='td-rownumber'><div class='cell-rownumber'>"+(i+1)+"</div></td>"+
		"<td field='ck'><div style='text-align:left;height:auto;' class='cell-check '>"+
		"<input name='kyck' type='checkbox'>"+
		"</div></td>"+
		"</tr>";
		tableHtml1+="<tr class='tableRow' style='height: 25px;'>"+function(){
			var tdhtml="";
		for(var key in listAll[i]){
			if(key!="temp"){
				if(listAll[i][key].name==null)listAll[i][key].name="";
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
	if(index=="add"){
	tbody1.append(tableHtml1);
	tbody0.append(tableHtml0);
	}else{
		tbody1.find("tr:eq("+index+")").replaceWith(tableHtml1);
	}
};
//数据提交
var insertList=function(list){
	var map={
			list:list,
			clas:clas
	};
	var result=false;
	$.callService(
			'scientificService',
			'insert',
			[JSON.stringify(map)],
			function(data) {
					result=data;
			},null,null,null,false);
	return result;
};

var getAId=function(a,b,c,d){
	return getId();
};
var filterByFlag=function(listall){
	var list=[];
	for(var i=0;i<listall.length;i++){
		if(listall[i].temp.flagCode=="01"){
			list.push(listall[i]);
		}
	}
	return list;
};