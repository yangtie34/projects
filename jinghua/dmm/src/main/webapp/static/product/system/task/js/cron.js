var cronStr=function(cron){
	var l1=cron.split(" ");
	if(l1.length==6){
		l1[6]=" ";
	}
	var Lmap=[null,'七','一','二','三','四','五','六'];
	var str="";
	for(var k=6;k>=0;k--){
		if(k==5)continue;
		var d=l1[k];
		switch(k){
		case 0:
			if(d=="*"){
			}else if(d.split("-").length>1){
				str+="从"+ d.split("-")[0]+"秒至"+ d.split("-")[1]+"秒";
			}else if(d.split("/").length>1){
				str+="从"+ d.split("-")[0]+"秒开始每"+ d.split("-")[1]+"秒";
			}else if(d.split(",").length>1){
				for(var s=0;s<d.split(",").length;s++){
					str+=(d.split(",")[s])+"秒,";
				}
				str=str.substr(0,str.length-1);
			}else if(d.split(",").length=1){
					str+=(d.split(",")[0])+"秒";
			}
			break;
		case 1:
			if(d=="*"){
			}else if(d.split("-").length>1){
				str+="从"+ d.split("-")[0]+"分至"+ d.split("-")[1]+"分";
			}else if(d.split("/").length>1){
				str+="从"+ d.split("-")[0]+"分开始每"+ d.split("-")[1]+"分钟";
			}else if(d.split(",").length>1){
				for(var s=0;s<d.split(",").length;s++){
					str+=(d.split(",")[s])+"分,";
				}
				str=str.substr(0,str.length-1);
			}else if(d.split(",").length=1){
					str+=(d.split(",")[0])+"分";
			}
			break;
		case 2:
			if(d=="*"){
			}else if(d.split("-").length>1){
				str+="从"+ d.split("-")[0]+"点至"+ d.split("-")[1]+"点";
			}else if(d.split("/").length>1){
				str+="从"+ d.split("-")[0]+"点开始每"+ d.split("-")[1]+"小时";
			}else if(d.split(",").length>1){
				for(var s=0;s<d.split(",").length;s++){
					str+=(d.split(",")[s])+"点,";
				}
				str=str.substr(0,str.length-1);
			}else if(d.split(",").length=1){
					str+=(d.split(",")[0])+"点";
			}
			break;
		case 3:
			if(d=="*"){
				str+="每天";
			}else if(d=="?"){
			}else if(d.split("-").length>1){
				str+="从"+ d.split("-")[0]+"日至"+ d.split("-")[1]+"日";
			}else if(d.split("/").length>1){
				str+="从"+ d.split("-")[0]+"日开始每"+ d.split("-")[1]+"天";
			}else if(d.charAt(d.length-1)=="W"){
				str+=d.charAt(0)+"号最近的那个工作日";
			}else if(d=="L"){
				str+=" 本月最后一天";
			}else if(d.split(",").length>1){
				for(var s=0;s<d.split(",").length;s++){
					str+=(d.split(",")[s])+"号,";
				}
				str=str.substr(0,str.length-1);
			}else if(d.split(",").length=1){
					str+=(d.split(",")[0])+"号";
			}
			break;
		case 4:
			if(d=="*"){
				str+="每月";
			}else if(d=="?"){
			}else if(d.split("-").length>1){
				str+=(d.split("/")[0])+"月"+"至"+(d.split("/")[1])+"月";
			}else if(d.split("/").length>1){
				
			}else if(d.split(",").length>1){
				for(var s=0;s<d.split(",").length;s++){
					str+=(d.split(",")[s])+"月,";
				}
				str=str.substr(0,str.length-1);
			}else if(d.split(",").length=1){
					str+=(d.split(",")[0])+"月";
			}
			d=l1[5];
		case 5:
			if(d=="*"){
				str+="每星期";
			}else if(d=="?"){
			}else if(d.split("/").length>1){
				str+="周"+Lmap[Number(d.split("/")[0])]+"至"+"周"+Lmap[Number(d.split("/")[1])];
			}else if(d.split("-").length>1){
				str+="周"+Lmap[Number(d.split("-")[0])]+"至"+"周"+Lmap[Number(d.split("-")[1])];
			}else if(d.split("#").length>1){
				str+="第"+Lmap[Number(d.split("#")[0])]+"周 的星期"+Lmap[Number(d.split("#")[1])];
			}else if(d.charAt(d.length-1)=="L"){
				str+="最后一个星期"+Lmap[Number(d.charAt(0))];
			}else if(d.split(",").length>1){
				for(var s=0;s<d.split(",").length;s++){
					str+="星期"+Lmap[Number(d.split(",")[s])]+",";
				}
				str=str.substr(0,str.length-1);
			}else if(d.split(",").length=1){
					str+="星期"+Lmap[Number(d.split(",")[0])];
			}
			break;
		case 6:
			if(d=="?"){
				str+="每年";
			}else if(d=="*"){
				str+="每年";
			}else if(d.split("-").length=1){
				str+=d.split("-")[0]+"年";
			}else if(d.split("-").length>1){
				str+=d.split("-")[0]+"年至"+d.split("-")[1]+"年";
			}
			break;
		}
	};
	str=str.replace("每年每月每天","每天"); 
	str=str.replace("每月每天","每天"); 
	str=str.replace("每年每月","每月");  
	return str+"触发";
}