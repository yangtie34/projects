<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>


<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />

    <!--左侧导航开始-->
	<script type="text/javascript">
	var title= document.title;
	<%-- var url="<%=projectPath %>";
	alert(url); --%>
	Array.prototype.remove = function(dx) 
	{ 
	    if(isNaN(dx)||dx>this.length){return false;} 
	    this.splice(dx,1); 
	} 
	//tree格式化
	var listTree=[];
	var listMap={};
	var getRootTree=function(d,data){
		d.children=[];
		for(var i=0;i<data.length;i++){
			if(data[i].pid==d.id){
				d.children.push(data[i]);
				listMap[d.id]={};
				listMap[data[i].id]={};
				getRootTree(data[i],data);
			}
		}
		if(d.children.length==0)return;
		return d;
	};
	var getTree=function(d){
		for(var i=0;i<d.length;i++){
			if(d[i].resource_type_code=='01'){
				var a=d[i];
				listTree.push(getRootTree(a,d));
			}
		}
		for(var i=0;i<d.length;i++){
			if(!listMap[d[i].id]){
				listTree.push(d[i]);	
			}
		}
		return listTree;
	}
	var level=0;
	//生成菜单
	var pathname=window.location.pathname;
			var dmm="${ctx }";
			var this_url=pathname.substring(dmm.length,pathname.length);
			var this_path="-1";
			var icoMap={
					//"权限管理":"subNav-icon-xtqx",
					//"一卡通消费统计":"subNav-icon-xtykt",
			}
			var html="";
	var init=function(level,d){
		for(var i=0;i<d.length;i++){
			if(d[i]==null)continue;
			var Divroot=null;
			var sty=""
			if(level==0){
				Divroot=$("#leftDiv");
			}else{
				//sty="margin-left:5px;"
				Divroot=$("#leftDivUl_"+d[i].pid);
			}
			if(d[i].resource_type_code=='01'  ){
				var divClass="",ulStyle="style='margin:0px'";
				if(this_path.indexOf(d[i].path)>=0){
					divClass="currentDd currentDt",ulStyle="style='display:block'";
				}
				html="<div class='line_border' style='background-color: #32323a;"+sty+"'>"+
            			"<div class='subNav "+divClass+"'><em class='"+icoMap[d[i].name_]+"'>"+d[i].name_+"</div>"+
       				    "<ul class='navContent' "+ulStyle+" id='leftDivUl_"+d[i].id+"'></ul></div>";
       				 Divroot.append(html);
       		if(d[i].children.length>0){
       			init(level+1,d[i].children);
       		}
			}else if(d[i].isShow!=0){
			var listyle="";
				if(d[i].name_==title){
					$("#leftDivUl_"+d[i].pid).css("display", "block");
					listyle="background-color: #248b8a;";
				};
			
				html=" <li> <a href='${ctx }"+d[i].url_+"' style='"+listyle+"'>"+d[i].name_+"</a></li>";
				Divroot.append(html);
			}
		}
	};
	
	$(function(){
		//var data=[{'istrue':1},1];
		<% String basePath = request.getContextPath();
   int port = request.getServerPort();
   String ip = request.getServerName();
   String projectPath = "http://"+ip+":"+port+basePath;
   //遍历获取request对象参数
%>
		var data=[];
		data.push('<%=request.getUserPrincipal().getName() %>');
		data.push('<%=projectPath %>');
		$.callService('resourcesService','getMenusByUserName',data,function(d){
			init(0,getTree(d));
		},null,null,null,false);
	$(".subNav").click(function(){
				$(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd");
				$(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt");
				
				// 修改数字控制速度， slideUp(500)控制卷起速度
				$(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
		});	
	})
	</script>
   <div class="con_left">
       <div class="subNavBox" id ="leftDiv">
     <!--   用户信息显示 -->
       	<div class="line_border">
        <div class="face-line">
        <div style="display:table-cell; width: 20px;">
        <img alt="" src="${ctx}/user/getphoto" class="img-circle" width="50" height="50">
        </div>
          
          <div class="face-name"><%=request.getUserPrincipal().getName() %></div>
          <div class="face-windows-icon"><a href="" class="face-windows-icon"></a>
            <div class="face-windows-beside" >
              <ul class="face-windows" >
               <li> <a class="face-windows-edit" style="cursor:pointer" href="${ctx}/user/changepwd/page?username=<%=request.getUserPrincipal().getName() %>"> 修改密码 </a> </li> <!--  id="changePass" -->
              <!--  <li><a data-toggle="modal" data-target="#myModal1" href="javascript:void(0)">查看个人信息</a></li> -->
                <li> <a  class="face-windows-exit" href="${ctx}/logout">退出</a> </li>
              </ul>
            </div>
          </div>
        </div>
      </div>	
        <!--   用户信息显示结束 -->    
        </div>
    </div>    
    <!--左侧导航结束-->
  <%--   <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">个人信息</h4>
      </div>
      <div class="modal-body">
        	姓名：${ue.userName}<br/>
        	单位：${ue.deptName}
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
      </div>
    </div>
  </div>
</div> --%>
    
   <style>
   .menupassxf{
       border: 1px solid #ddd;
    top: 30%;
    position: fixed;
    left: 40%;
    z-index:100;
    width:400px;
    background: #FFF;
        border-radius: 5px;
    -webkit-box-shadow: 0 5px 15px rgba(0,0,0,.5);
    }
    </style>
<script type="text/javascript">
var deOption={
			  "closeButton": false,
			  "debug": false,
			  "positionClass": "toast-top-center",
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "2000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			}
toastr.options=deOption;
	$(document).ready(function(){
		var error = true;
		//创建遮罩
		var body = $("body");//获取body元素
	    var mask = $('<div class="cs-window-background" id="passbg"></div>');
	    mask.hide();
	    
	    body.append(mask);
		$("#oldpass").blur(function(){
			var oldpass = $("#oldpass").val();
			if(oldpass =='') {
				showError('oldpass', '密码不能为空');
				error = true;
				return;
			}else{
				$("#oldpass").css({"border-color":"green"});
				$("#oldpassTip").css({"display":"none"});error = false;	
			}

		});
		$("#newpass").blur(function(){
			var newpass = $("#newpass").val();
			if(newpass == '') {
				showError('newpass', '新密码不能为空');
				error = true;
			}
			else {
				$("#newpass").css({"border-color":"green"});
				$("#newpassTip").css({"display":"none"});error = false;
			}
		});

		$("#newpassAgain").blur(function(){
			var newpass = $("#newpass").val();
			if(newpass == '') {
				showError('newpass', '新密码不能为空');
				error = true;
				return;
			}

			var newpassAgain = $("#newpassAgain").val();
			if(newpassAgain != newpass) {
				showError('newpassAgain', '与输入的新密码不一致');
				error = true;
			}
			else {
				$("#newpassAgain").css({"border-color":"green"});
				$("#newpassAgainTip").css({"display":"none"});error = false;
			}
		});
		
		$("#submit").click(function(event){
			$("#oldpass").blur();
			$("#newpass").blur();
			$("#newpassAgain").blur();
			if(!error) {
				var oldpass = $("#oldpass").val();			
				var newpass = $("#newpass").val();
				$.ajax({
			     type: "POST",
			     async:true,
			     url:"${ctx}/user/changepwd",
			     data:{username:'<%=request.getUserPrincipal().getName() %>',oldpwd:oldpass, newpwd:newpass},
			     success: function(data){
			    	if(data.isTrue){
			    		toastr.success("修改成功");
			    		passxfhide();
			    	}else{
			    		showError('oldpass', '旧密码错误');
			    	}
			    	
			     }
			 });
			}

			event.preventDefault();
			return false;
		});
		
		$("#changePass").click(function(){
			mask.show();
			$("#menupassxf").show();
			
		});
		var passxfhide=function(){
			$("#menupassxf").hide();
			mask.hide();
			$("#oldpass").val("");
			$("#newpass").val("");
			$("#newpassAgain").val("");
		}
		$("#quxiaopass").click(function(){
			passxfhide();
		});
		passxfhide();
		
	});

	function showError(formSpan, errorText) {
		$("#" + formSpan).css({"border-color":"red"});
		$("#" + formSpan + "Tip").empty();
		$("#" + formSpan + "Tip").append(errorText);;
		$("#" + formSpan + "Tip").css({"display":"inline"});
	}
</script>
  <div data-example-id="basic-forms" class="bs-example menupassxf" id="menupassxf" style="display:none">
  <div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" id="quxiaopass"><span class="aclose">×</span></button>
  <h4 style="margin:0px;line-height:1;">修改密码</h4>
  </div>
  <br>
   <form class="form-horizontal" role="form">

	  <div class="form-group">
	    <label for="oldpass" class="col-sm-4 control-label">旧密码</label>
	    <div class="col-sm-6">
	      <input type="password" class="form-control" style="width:200px;" id="oldpass" placeholder=""><span id="oldpassTip" style="display:none;color:red;"></span>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="newpass" class="col-sm-4 control-label">新密码</label>
	    <div class="col-sm-6">
	      <input type="password" class="form-control" style="width:200px;" id="newpass" placeholder=""><span id="newpassTip" style="display:none;color:red;"></span>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="newpassAgain" class="col-sm-4 control-label">确认密码</label>
	    <div class="col-sm-6">
	      <input type="password" class="form-control" style="width:200px;" id="newpassAgain" placeholder=""><span id="newpassAgainTip" style="display:none;color:red;"></span>
	    </div>
	  </div>
	  <div class="form-group">
	    <label class="col-sm-5 control-label">  </label>
	 	 <button type="button" class="btn btn-primary" id="submit" style="text-align:center;">确认修改</button>
	 	 <!-- <button type="button" class="btn" id="quxiaopass" style="text-align:center;">取消</button> -->
	  </div>
	</form>
</div>