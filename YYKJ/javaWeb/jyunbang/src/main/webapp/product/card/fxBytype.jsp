<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
  <head>
  <%--   <base href="<%=basePath%>"> --%>
    <jsp:include page="../../static/base.jsp"></jsp:include>
    <title>学生按类型消费习惯分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body ng-controller="fxBytypeController" ng-init="upDown=[true,false,false];">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/card/js/fxBytype.js"></script>   
  <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">学生按类型消费习惯分析</h3>
    <p class="xscz-default">对学校学生消费习惯进行分析。</p>
  </div>
 
 <div class="xscz-box"style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
  <div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	
   
    
    <div class="xscz-nav">
      <!-- /* 统计*/-->
      
      <div class="xscz-tj">
      	<div class="dis-tab">
        	<div class="dis-tab-cell wid-15"></div>
        	<div class="dis-tab-cell wid-6 r-20">
                <div class="xscz-cit text-center">
                    <span ng-class="csjecode=='cs'?active:'normal'" ng-click="csje('cs');"><a href="">刷卡次数</a></span>
                    <span ng-class="csjecode!='cs'?active:'normal'" ng-click="csje('je');"><a href="">消费金额</a></span>
                </div>
            </div>
            <div class="dis-tab-cell wid-35 l-20">
                <div class="xscz-cit text-center">
                    <span>刷卡就餐人次组成</span>
                </div>
            </div>
        </div>
       <!-- 以上标题-->
       <!-- -------------all--------------->

               <div class="dis-tab mar-btm-40 text-center" ng-repeat="(k,v) in vm.items[0].all" ng-show="k=='all'">
        	<div class="dis-tab-cell ver-m wid-15">
            	<div class="tit-vertical">            	
            	<span><img ng-src="${images}/gender-{{ico_title.all.all.ico}}.png" alt=""></span>
                    <span>{{ico_title.all.all.name}}</span>
            	</div>
                   
            </div>
            
            <div class="dis-tab-cell wid-6 r-20" >
            	<div class="dis-tab">
                	<div class="dis-tab-cell wid-5 border-r-dash r-20">
                    	<p class="xscz-ft-14">分时段统计刷卡{{csjecode=='cs'?'次数':'消费'}}</p>
                        <div>
                         <div stu-chart config="v['fsd'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<%-- <img src="${images}/one-card-02.png" alt=""> --%>
                        </div>
                    </div>
                    <div class="dis-tab-cell wid-5 ver-b l-20">
                    	<div>
                    	  <div stu-chart config="v['zzw'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<%-- <img src="${images}/one-card-01.jpg" alt=""> --%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dis-tab-cell wid-35 ver-b  l-20">
             <div stu-chart config="v['yczc']" style="height:310px;"class="img-responsive img-top"> </div>
            	<%-- <div><img src="${images}/img-41.png" alt=""></div> --%>
            </div>
       </div> 
       <!-- -------------all--------------->
       
         <!-- -------------xb--------------->
                  <div >
            <div class="dis-tab  pad-btm-40">
                <div class="dis-tab-cell wid-15"></div>
                <div class="dis-tab-cell ver-m show-text text-center xscz-ft-16">
                    <a href="" class="xscz-normal"ng-click="upDownClick('xb');">通过 <span style="font-size: 18px;font-weight: bold;color:red;"> {{ico_title.xb.name}} </span>查看消费习惯<img ng-src="${images}/arrow-{{upDown.xb==true?'up':'down'}}-block.png" alt="" ></a>
                </div>
            </div>
            <!--查看-->
        <div class="dis-tab mar-btm-40 text-center" ng-show="upDown.xb&&k!='name'&&ico_title.xb[k]&&v.fsd!=null" ng-repeat="(k,v) in vm.items[0].xb">
        	<div class="dis-tab-cell ver-m wid-15">
            	<div class="tit-vertical">
            	<span><img ng-src="${images}/gender-{{ico_title.xb[k].ico}}.png" alt=""></span>
                    <span>{{ico_title.xb[k].name}}</span>    
                </div>
            </div>

            <div class="dis-tab-cell wid-6 r-20" >
            	<div class="dis-tab">
                	<div class="dis-tab-cell wid-5 border-r-dash r-20">
                    	<p class="xscz-ft-14">分时段统计刷卡{{csjecode=='cs'?'次数':'消费'}}</p>
                        <div>
                         <div stu-chart config="v['fsd'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<%-- <img src="${images}/one-card-02.png" alt=""> --%>
                        </div>
                    </div>
                    <div class="dis-tab-cell wid-5 ver-b l-20">
                    	<div>
                    	  <div stu-chart config="v['zzw'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<%-- <img src="${images}/one-card-01.jpg" alt=""> --%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dis-tab-cell wid-35 ver-b  l-20">
             <div stu-chart config="v['yczc']" style="height:310px;"class="img-responsive img-top"> </div>
            	<%-- <div><img src="${images}/img-41.png" alt=""></div> --%>
            </div>
       </div>
       </div>
           <!-- -------------xb--------------->
           
             <!-- -------------xl--------------->
               <div >
            <div class="dis-tab  pad-btm-40">
                <div class="dis-tab-cell wid-15"></div>
                <div class="dis-tab-cell ver-m show-text text-center xscz-ft-16">
                    <a href="" class="xscz-normal"ng-click="upDownClick('xl');">通过 <span style="font-size: 18px;font-weight: bold;color:red;"> {{ico_title.xl.name}} </span>查看消费习惯<img ng-src="${images}/arrow-{{upDown.xl==true?'up':'down'}}-block.png" alt="" ></a>
                </div>
            </div>
            <!--查看-->
        <div class="dis-tab mar-btm-40 text-center" ng-show="upDown.xl&&k!='name'&&ico_title.xl[k]!=null&&v.fsd!=null" ng-repeat="(k,v) in vm.items[0].xl" >
        	<div class="dis-tab-cell ver-m wid-15">
            	<div class="tit-vertical">
            	<span><img ng-src="${images}/gender-{{ico_title.xl[k].ico}}.png" alt=""></span>
                    <span>{{ico_title.xl[k].name}}</span>    
                </div>
            </div>

            <div class="dis-tab-cell wid-6 r-20" >
            	<div class="dis-tab">
                	<div class="dis-tab-cell wid-5 border-r-dash r-20">
                    	<p class="xscz-ft-14">分时段统计刷卡{{csjecode=='cs'?'次数':'消费'}}</p>
                        <div>
                         <div stu-chart config="v['fsd'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<%-- <img src="${images}/one-card-02.png" alt=""> --%>
                        </div>
                    </div>
                    <div class="dis-tab-cell wid-5 ver-b l-20">
                    	<div>
                    	  <div stu-chart config="v['zzw'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<%-- <img src="${images}/one-card-01.jpg" alt=""> --%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dis-tab-cell wid-35 ver-b  l-20">
             <div stu-chart config="v['yczc']" style="height:310px;"class="img-responsive img-top"> </div>
            	<%-- <div><img src="${images}/img-41.png" alt=""></div> --%>
            </div>
       </div>
       </div>
               <!-- -------------xl--------------->
               
                 <!-- -------------dqmz--------------->
                 
        
        <div ng-repeat="key in ['dq','mz']" >
            <div class="dis-tab  pad-btm-40" >
                <div class="dis-tab-cell wid-15"></div>
                <div class="dis-tab-cell ver-m show-text text-center xscz-ft-16">
                    <a href="" class="xscz-normal"ng-click="upDownClick(key);">通过<span  style="font-size: 18px;font-weight: bold;color:red;"> {{ico_title[key].name}} </span>查看消费习惯<img ng-src="${images}/arrow-{{upDown[key]==true?'up':'down'}}-block.png" alt="" ></a>
                </div>
            </div>
            <!--查看-->
        <div class="dis-tab mar-btm-40 text-center" ng-show="upDown[key]&&dqmz[key][i]!=null" ng-repeat="i in [0,1]" >
        	<div class="dis-tab-cell ver-m wid-15">
            	<div class="tit-vertical">
                     <select autocomplete="off" class="form-control input-sm" ng-model="dqmz[key][i]"style="width: inherit;">
          					<option  ng-repeat="(code,val) in vm.items[0][key]" value="{{code}}" >{{val.name}}</option>
        			</select> 
                </div>
            </div>
            <div class="dis-tab-cell wid-6 r-20" >
            	<div class="dis-tab">
                	<div class="dis-tab-cell wid-5 border-r-dash r-20">
                    	<p class="xscz-ft-14">分时段统计刷卡{{csjecode=='cs'?'次数':'消费'}}</p>
                        <div>
                         <div stu-chart config="vm.items[0][key][dqmz[key][i]]['fsd'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        </div>
                    </div>
                    <div class="dis-tab-cell wid-5 ver-b l-20">
                    	<div>
                    	  <div stu-chart config="vm.items[0][key][dqmz[key][i]]['zzw'][csjecode]" style="height:310px;"class="img-responsive img-top"> </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="dis-tab-cell wid-35 ver-b  l-20">
             <div stu-chart config="vm.items[0][key][dqmz[key][i]]['yczc']" style="height:310px;"class="img-responsive img-top"> </div>
            </div>
     
        </div>
       </div> 
  <!-- -------------dqmz--------------->
      </div>
      <!-- /* 统计*/ end-->
    </div>
  </div>
 </div>
</div> 
  </body>
  <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
	<div stu-chart config="qushiData" class="qsdivc" ></div>
</div>
</html>
