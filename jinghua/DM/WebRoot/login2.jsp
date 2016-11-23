<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>  
<%@ page import="com.jhkj.mosdc.framework.util.*"%>
<%@ page import="org.apache.struts2.*"%> 
<%@ page import="com.opensymphony.xwork2.ActionContext"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%   
    response.setHeader("Cache-Control","no-cache");   
    response.setHeader("Expires","Thu,01 Jan 1970 00:00:01 GMT");   
    //String checkCode = StringTool.randomChars(4)生成验证码 StringTool.randomChars(4)方法在下面的例子中能找到.   
    //session.setAttribute("c",checkCode);//验证码存入session中   
    long t = (new Date()).getTime();//确保每次刷新都生成新的验证码图片   
	String path = request.getContextPath();
    ActionContext ac = ServletActionContext.getContext();
    Object user = ac.getSession().get(SysConstants.SESSION_USERINFO);
    String loginName = request.getParameter("loginName");
    String errorMsg = "";
    if(loginName != null && loginName.length()!=0){
    	errorMsg = "用户名、密码错误或者用户被管理员禁用!";
    }else{
    	loginName = "";
    }
    if(user!=null){
    	response.sendRedirect("main.jsp");
    }
%>
<html>
<head>
<title>登录页面</title>
<link rel="shortcut icon" href="css/icon.ico">
<style type="text/css">
    body {

    }
    a {
    color:#647997;
    font-size:12px;
    text-decoration:none;
    }
    img, a{margin:0; padding:0}
    a:hover {
    color:#647997;
    font-size:12px;
    text-decoration:underline;
    }
    .beside-absolute{ position:absolute; width:1280px; height:520px; margin-top:-280px; top:50%; margin-left:-640px; left:50%; background:url(css/hg/images/bg-tu-1280.jpg) center 0 no-repeat;}
    .bg {
    height:303px;
    position:relative;
    }
    .login {
    position:absolute;
    left:0;
    top:0;
    width:649px;
    }
    .bg-all {
    border:0;
    width:123px;
    padding:11px 0 8px;
    padding-left:40px;
    outline:none;
    }
    .bg-user {
    background:url(css/hg/images/user.png) 0 -1px no-repeat;
    color:#333;
    }
    .bg-password {
    background:url(css/hg/images/password.png) 0 -1px no-repeat;
    color:#333;
    }
    .bg-yanzhengma {
    background:url(css/hg/images/yanzhengma.png) 0 -1px no-repeat;
    color:#333;
    vertical-align:middle;
    }
    input.bg-enter {
    background:url(css/hg/images/enter.png) 0 0 no-repeat;
    border:0;
    width:164px;
    height:36px;
    }
    input.bg-enter:hover {
    background:url(css/hg/images/enter-hover.png) 0 0 no-repeat;
    border:0;
    }
    input.bg-enter:active {
    background:url(css/hg/images/enter-hover.png) 1px 1px no-repeat;
    border:0;
    }
    .footsty {
    color:#626262;
    font-size:13px;
    }
    .index-login-info {
    color:#647997;
    font-size:12px;
    width:1090px;
    line-height:22px;
    }
    .index-login-shenfen {
    color:#d60000;
    }
    img.denglukuang {
    width: 649px;
    }
    .position-style {
    margin: 0 auto;
    display: table;
    }
    .shenfenzheng {
    padding-left:10px;
    }
    .index-shuoming {
    white-space:nowrap;
    }
    .luanma{ display:inline; padding-top:5px; width:80px; height:30px;}
    .index-login-red{font-size:13px; color:#ff0000;}
    </style>
    <script src="app/pages/jw/sxgl/template/js/jquery-1.9.1.min.js"></script>
<script>
	String.prototype.trim=function(){ 
　　    	return this.replace(/(^\s*)|(\s*$)/g, "");
　　 }
			function check(){
				var nameObj=document.getElementById("loginName");
				var passwordObj=document.getElementById("password");
				var imageCodeObj= document.getElementById("imagecode");
				nameObj.value = nameObj.value.trim();
				passwordObj.value = passwordObj.value.trim();
				
				if(nameObj.value==""){
					alert("请输入用户名!");
					nameObj.focus();
					return false;
				}else if(passwordObj.value==""){
				    alert("请输入密码!");
				    passwordObj.focus();
				    return false;
				}
				/**else  if(imageCodeObj.value =="") {
					alert("请输入验证码!");
					imageCodeObj.focus();
				    return false;
				}
				*/else{
					return true;
				}
			}
		</script>
</head>
<body class="body">
    <s:form method="post" action="login.action" name = "submit1" onsubmit="return check();" theme="simple">
    <div class="beside-absolute">
    <table  width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" >
    <tr>
    <td width="27%"   rowspan="3">&nbsp;</td>
    <td  width="46%" height="130" valign="middle" align="center"><img src="css/hg/images/logo-1280.png" alt="logo" width="343" height="71" /></td>
    <td width="27%"   rowspan="3" valign="bottom" align="center" style="fontsize:14px;line-height:30px"></td>
    </tr>
    <tr>
    <td height="324" class="bg" valign="middle"><div class="position-style">
    <div class="bg"><img class="denglukuang" src="css/hg/images/denglu-kuang.png" />
    <div class="login">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td rowspan="6" width="345">&nbsp;</td>
    <td height="70"></td>
    </tr>
    <tr>
    <td  height="45" valign="middle"   ><input class="bg-user bg-all" type="text" name="loginName" id="loginName"  width="110"  value="<%=loginName%>" minlength="1" />
    </td>
    </tr>
    <tr>
    <td  height="45" valign="middle" ><input class="bg-password bg-all" type="password" name="password" id="password" width="110" value="<s:property value="password" />" maxLength=20 minlength="1" /></td>
    </tr>
    <%--<tr>--%>
    <%--<td height="45" valign="middle"><input class="bg-yanzhengma bg-all" name="" id = 'yanzhengma'type="text" value="验证码" /><img width="80" height="32" style="vertical-align:middle;padding-left:5px;" id="random" src="validateImgAction.action"  /> </td>--%>
    <%--</tr>--%>
    <tr>
    <td height="50" valign="middle"><input class="bg-enter" name="" type="submit" id="ann"  value="" /></td>
    </tr>
    <tr>
    <td height="20" valign="middle"><span class="index-login-red"><%=errorMsg %>  </span></td>
    </tr>
    </table>
    </div>
    </div>
    </div></td>
    </tr>
    <tr>
    <td class="footsty"  valign="bottom" height="35" align="center" ></td>
    </tr>
    <tr>
    <td colspan="3" align="center"><table  class="index-login-info"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td width="8%" class="index-shuoming" align="left">系统使用说明：</td>
    <td align="left" width="2%">1、</td>
    <td  align="left" width="67%">用户名说明：教职工工号。</td>
    <!--<td width="18%" rowspan="2"><img class="shenfenzheng" src="css/hg/images/shenfenzheng.jpg" /></td>-->
    <td align="left" width="18%" rowspan="2"></td>

    </tr>
    <tr>
    <td align="left">&nbsp;</td>
    <td  align="left" valign="top">2、</td>
    <!--<td>密码说明：初始密码与个人身份证号相关，15位的取最后6位作为密码，18位的取12-17位作为密码，如果身份证号信息为空或者位数不对，
    则以学号或者工号作为初始密码。如尝试以上三种密码均不能登录，请联系管理员。<span class="index-login-shenfen">18位身份证截取如右图示例：</span></td>-->
    <td align="left">密码说明:如果您不记得密码，可以通过门户系统中的导航直接免登陆进入。</td>
    </tr>

    </table></td>
    </tr>
    </table>
    </div>
    </s:form>

</body>
<script>
    function inputValue(){
        var user = document.getElementById('loginName');
        var password = document.getElementById('password');
        var validate = document.getElementById('yanzhengma');
        user.onblur = function(){
             if(user.value == ''){
                user.value = '请输入用户名';
             }
        }
        user.onfocus = function(){
            if(user.value == '请输入用户名'){
                user.value = '';
            }
        }
        password.onblur = function(){
            if(password.value == ''){
                password.type = 'text';
                password.value = '请输入密码';
            }
        }
        password.onfocus = function(){
            if(password.value == '请输入密码'){
                password.type = 'password';
                password.value = '';
            }
        }
        validate.onblur = function(){
            if(validate.value == ''){
                validate.value = '请输入验证码';
            }
        }
        validate.onfocus = function(){
            if(validate.value == '请输入验证码'){
                validate.value = '';
            }
        }
        if(!Boolean(user.value)) {
        	user.value = '请输入用户名';
            password.value = '请输入密码';
            validate.value = '请输入验证码';
            password.type = 'text';
        }
    }
    inputValue();
    function checkBrowserIsFireFox(){
    var nav = navigator.userAgent.toLowerCase();
    var docMode = document.documentMode;
    var check = function(regex){
    return regex.test(nav);
    },
    version = function (is, regex) {
    var m;
    return (is && (m = regex.exec(nav))) ? parseFloat(m[1]) : 0;
    },
    isChrome = check(/\bchrome\b/),
    isOpera = check(/opera/),
    isWebKit = check(/webkit/),
    isGecko = !isWebKit && check(/gecko/),
    isGecko3 = isGecko && check(/rv:1\.9/),
    isIE = !isOpera && check(/msie/),
    isIE7 = isIE && ((check(/msie 7/) && docMode != 8 && docMode != 9) || docMode == 7),
    isIE8 = isIE && ((check(/msie 8/) && docMode != 7 && docMode != 9) || docMode == 8),
    isIE9 = isIE && ((check(/msie 9/) && docMode != 7 && docMode != 8) || docMode == 9),
    isFF3_0 = isGecko3 && check(/rv:1\.9\.0/),
    isFF3_5 = isGecko3 && check(/rv:1\.9\.1/),
    isFF3_6 = isGecko3 && check(/rv:1\.9\.2/),
    isLinux = check(/linux/),
    isWindows = check(/windows|win32/),
    isMac = check(/macintosh|mac os x/),
    chromeVersion = version(true, /\bchrome\/(\d+\.\d+)/),
    firefoxVersion = version(true, /\bfirefox\/(\d+\.\d+)/),
    ieVersion = version(isIE, /msie (\d+\.\d+)/);
    if(firefoxVersion == 0 && chromeVersion ==0){
    <%--var name=document.getElementById("loginName");--%>
    <%--var password=document.getElementById("password");--%>
    <%--var ann = document.getElementById("ann");--%>
        document.body.removeChild(document.getElementById('loginAction'));
        document.body.className = '';
        document.body.innerHTML = '<div id="main"> <header id="header">  <h1> </h1> </header>  <div id="content"> '+
            '   <h2>为了更好地使用系统，建议您使用火狐浏览器!</h2> '+
    '   <p>火狐浏览器下载地址：<a href="http://www.firefox.com.cn/download/"  target="_black">http://www.firefox.com.cn/download/</a></p> '+
    '<p>备用下载地址(<span style="color: red;">鉴于网速原因，推荐使用</span>)：<iframe id="iframeForDownLoad" height="0" width="0" style="display: none;" src=""></iframe><a href="javascript:void();" id="downloadFireFox" style="color: red;">点击下载</a></p></div> </div> </div>';
        var openUrl = "loadFile.action?loadFilePath=soft/Firefox-full-latest.exe&loadFileName=火狐浏览器.exe";
        var a  = document.getElementById("downloadFireFox");
        var f = document.getElementById("iframeForDownLoad");
        a.onclick = function(){
            f.src = openUrl;
        }

    }else{
    document.getElementById('loginName').focus();
    }
    }
    //checkBrowserIsFireFox();
</script>
</html>


 

