$(function(){
	var expotexcel=$("#Modal_excel .modal-body div a");//点击导出
	var excelsub=$("#Modal_excel .modal-footer .btn-primary");//提交按钮
	var excelform=$("#Modal_excel form");//表单
	var excelinput=excelform.find("input");
	
	expotexcel.click(function(){
		var sheetName=$(".noclick").text()+"录入模板";
		var titles="";
		for ( var i=0;i<formObj.ky.length;i++ ) {
			var key =formObj.ky[i].info;
			if(key.type!="xmcc"){
			titles+=key.name+",";
			}
		}
		myUpload({
		    uploadUrl: "/getXls",///dmm/manager/keyan/subExcel
		    titles:titles,
		    sheetName:sheetName,
		});
	});
	excelsub.click(function(){
		var file=excelinput[0].files[0];
		myUpload({
		    uploadUrl: "/subExcel",///dmm/manager/keyan/subExcel
		    file:file,
		    callback: function(data) {
		    	excelinput.val("");
		    	$("#Modal_excel").modal('toggle');
		    	data=JSON.parse(data); 
		    	listAll=[];
		    	for ( var l=0;l<data.length;l++ ) { 	
		    		var kyexmap=data[l];
		    		var kymap={};
		    	for ( var i=0;i<formObj.ky.length;i++ ) {
		    		var key =formObj.ky[i].info;
		    		var value={};
		    		var name="";
		    		for(var kykey in kyexmap ){
		    			if(kykey==key.name){
		    				name=kyexmap[kykey];
		    				break;
		    			}
		    		}
		    		switch (key.type) {
		    		case "xmcc":
		    			value={
		    				code:"",
		    				name:""
		    		};
		    			break;
		    		case "code":
		    		case "entity":
		    			var code="";
		    			for(var k=0;k<formObj.ky[i].data.length;k++){
							if(formObj.ky[i].data[k].name==name){
								code=formObj.ky[i].data[k].code;
							}
						};
		    			value={
		    				code:code,
		    				name:name
		    		};
		    			break;
		    		case "n":
		    			value={
		    				code:name,
		    				name:name
		    		};
		    			break;
		    		case "nyr":
		    		case "text":
		    		case "remark":
		    			value={
		    				code:"",
		    				name:name
		    		};
		    			break;
		    		case "auth":
		    			value={
		    				code:"",
		    				name:"",
		    				data:[]
		    		};
		    			break;
		    		}
		    		kymap[key.code]={};
		    		kymap[key.code]=value;
		    	}
		    	kymap.temp={};
		    	kymap.temp.flagCode="05";
		    	listAll.push(kymap);
		    	}
		    	for(var i=0;i<listAll.length;i++){
		    		listTotable("add");
		    		listTotable(i);
		    	}
		    },
		});
	});
});
var myUpload=function(option){
	 var curPath=window.document.location.href;
	var uploadUrl =curPath.substr(0,curPath.lastIndexOf('/'))+ option.uploadUrl;
	var callback = option.callback;
	var fd = new FormData();
	var  xhr = new XMLHttpRequest();
	if(option.file!=null)fd.append("file", option.file);
	if(option.titles!=null)fd.append("titles", option.titles);
	if(option.sheetName!=null)fd.append("sheetName", option.sheetName);
	if(callback!=null){
     xhr.onreadystatechange = function() {
         if (xhr.readyState == 4 && xhr.status == 200) {
             if(callback instanceof Function){
                 callback(xhr.responseText);
             }
         }
     };
     xhr.open("post", uploadUrl);
     xhr.send(fd);
	}else{
		var url=uploadUrl+"?titles="+option.titles+"&sheetName="+option.sheetName;
		window.open(url);
	}
};