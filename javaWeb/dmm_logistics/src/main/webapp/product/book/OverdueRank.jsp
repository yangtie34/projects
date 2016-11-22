<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>图书逾期已还排名</title>
</head>
<body ng-controller="OverdueRankController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/book/js/OverdueRank.js"></script>    

        
   <div class="content">
        
        	<div class="xscz-head content_head">
                <h3 class="xscz-fff">图书逾期已还排名</h3> 
                <p class="xscz-default">对逾期书籍和逾期时长进行分析。</p> 
            </div>
            
         <div class="content_main">
            
            	<div class="jieyuepm_section">
                    	<div class="richangpm">
                        	<div class="fr">
                                <img src="${images}/books_images/border_blue_top.png" class="img-top" ng-show="!ndrypm">
                                <img src="${images}/border_15.png" class="img-top" ng-show="ndrypm">
                                <div ng-class="ndrypm?'xuanzhong_no':'xuanzhong'" ng-click="ndrypm=false;numOrDayClick('num');">
                                	<a href=""><div id="liub"><h4>逾期已还书籍</h4></div></a>
                                </div>
                                <img src="${images}/books_images/border_blue_bottom.png" class="img-top" ng-show="!ndrypm">
                                <img src="${images}/border_18.png" class="img-top" ng-show="ndrypm">
                                <p class="triangle_down" ng-show="!ndrypm"></p>
                            </div>      
                        </div>
                        <div class="niandupm">
                        	<div class="fl">
                                 <img src="${images}/books_images/border_blue_top.png" class="img-top" ng-show="ndrypm">
                                <img src="${images}/border_15.png" class="img-top" ng-show="!ndrypm">
                                <div ng-class="!ndrypm?'xuanzhong_no':'xuanzhong'" ng-click="ndrypm=true;numOrDayClick('day');">
                                	<a href=""><div id="liub"><h4>逾期时长</h4></div></a>
                                </div>
                                   <img src="${images}/books_images/border_blue_bottom.png" class="img-top" ng-show="ndrypm">
                                <img src="${images}/border_18.png" class="img-top" ng-show="!ndrypm">
                                 <p class="triangle_down" ng-show="ndrypm"></p>
                            </div>
                        </div>
                    </div>
                    <div><!-- ng-show="!ndrypm"> -->
<div class="booksjy_top_imges">
                	
                    <div class="clearfix"></div>
                     <div cg-combo-nyrtj result="date"></div>
      <!--           	<div class="top_date">
                    	<div id="liub" class="fl"></div>
                    	<div class="fl date_line"></div>
                        <div id="liub_r" class="fr"></div>
                        <div class="date_con">
                        	<a href="" class="date_active">当前</a>
                            <a href="">上月</a>
                            <a href="">近3月</a>
                            <b>起始日<span>-</span>结束日</b>
                        </div>
                        <div class="clearfix"></div>
                    </div> -->
                   
                    <div class="clearfix"></div>
                </div><!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------><div class="fenbu">
                	<div class="tusjy">
                    	<div class="t">
                        	<h5>逾期已还书籍排名</h5>
                        </div>
                        <div class="tusjy_con">
                            <div class="danxuan">
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_4" id="xs_053_4" value="stu" checked="" ng-model="type1">
                                    <label for="xs_053_4" class="fw-nm">
                                      学生情况
                                    </label>
                                </div>
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_4" id="js_053_4" value="tea"ng-model="type1">
                                    <label for="js_053_4" class="fw-nm">
                                      教师情况
                                    </label>
                                </div>
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_4" id="sj_053_4" value="book"ng-model="type1">
                                    <label for="sj_053_4" class="fw-nm">
                                      书籍情况
                                    </label>
                                </div>
                            </div>
                            选择排名：
                                                        <div class="danxuan">
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_41" id="xs_053_41" value=10 checked="" ng-model="rank">
                                    <label for="xs_053_41" class="fw-nm">
                                      top10
                                    </label>
                                </div>
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_41" id="js_053_41" value=20 ng-model="rank">
                                    <label for="js_053_41" class="fw-nm">
                                     top20
                                    </label>
                                </div>
                                <div class="radio radio-inline radio-change radio-blue">
                                    <input type="radio" name="radio053_41" id="sj_053_41" value=100 ng-model="rank">
                                    <label for="sj_053_41" class="fw-nm">
                                      top100
                                    </label>
                                </div>
                            </div>
                            
                            
                            <div class="tab">
                    			
                                <table class="table table-change"> 
                                <div ng-show="type1!='book'" cg-mul-query-comm source="mutiSource" result="page.conditions" noborder="true"></div>	
                                   <%-- <caption>学生所属：<a href="" class="colorr">郑州轻工业学院</a></caption> --%>
                                   <thead>
                                      <tr>
                                        <th>排名</th>
                                         <th ng-show="type1!='book'">{{type1=='stu'?'学':'工'}}号</th>
                                         <th>名字</th>
                                         <th>所属{{type1=='book'?'类型':'学院'}}</th>
                                         <th>{{ndrypm!=true?'逾期次数':'平均逾期天数'}}</th>
                                         <th>历史上榜次数<%-- <a href=""><img src="${images}/books_images/arrow_dowm.png" class="img-top"></a> --%></th>
                                      </tr>
                                   </thead>
                                   <tbody>
                                      <tr class="tab_dashed" ng-repeat="item in vm.items[0]">
                                         <td>{{item.RANK_}}</td>
                                         <td ng-show="type1!='book'">{{item.NO_}}</td>
                                         <td>{{item.NAME}}</td>
                                         <td>{{item.OFNAME}}</td>
                                         <td><a href="" class="colorr" ng-click="getxqById(item.NO_,9);">{{item.OUTTIME_NUM}}</a></td>
                                         <td><a href="" class="colorr" ng-click="getxqById(item.NO_,10);">{{item.TOPNUM}}</a></td>
                                     <!--     <td><a href="" class="colorr">9</a></td>
                                         <td><a href="" class="colorr">5</a></td> -->
                                      </tr>
                             <!--          <tr>
                                         <td>2</td>
                                         <td><a href="" class="colorr">124123</a></td>
                                         <td><a href="" class="colorr">刘佳媛</a></td>
                                         <td>马克思主义学院</td>
                                         <td><a href="" class="colorr">7</a></td>
                                         <td><a href="" class="colorr">3</a></td>
                                      </tr> -->
                                   </tbody>
                                </table>
                            </div>
                                  <!--分页-->
      		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">当前查询：共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="page.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div> 
<!--分页-->
                            <div class="clearfix"></div>
                        </div>
                    </div>
                <!------------------------------------------------- 以上图书借阅Top10 ---------------------------------------------->
                <div class="fenbu pdbtm_box">
                	<div class="t">
            			<h5>{{type1=='stu'?'学生':type1=='tea'?'教师':'书籍'}}借阅逾期分布情况</h5>
                	</div>
                     <div class="fenbu_con rylxfb_con" ng-show="type1!='book'">
                    	<div class="col-md-4" style="width:50%">
                        	<h4>学历对比<span class="icon-time" ng-click="qushiClick(2);"></span></h4>
                        	<div stu-chart config="vm.items[1]" style="height:310px;"class="img-responsive img-top"> </div>
                        	
                        	<%-- <img src="${images}/books_images/rylxfb_lx.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="col-md-4 fenbu_con_m" style="width:50%;border-right: none;">
                        	<h4>性别对比<span class="icon-time" ng-click="qushiClick(4);"></span></h4>
                        	<div stu-chart config="vm.items[3]" style="height:310px;"class="img-responsive img-top"> </div>
                            <%-- <img src="${images}/books_images/rylxfb_xb.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <%-- <div class="col-md-4">
                        	<h4>年级对比<span class="icon-time" ng-click="qushiClick(6);"></span></h4>
                        	<div stu-chart config="vm.items[5]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<img src="${images}/books_images/rylxfb_grade.jpg" class="img-responsive img-top">
                        </div> --%>
                        <div class="clearfix"></div>
                    </div>
                    <div class="fenbu_con rylxfb_con">
                    <h4>所属{{type1=='book'?'类型':deptlname}}对比<span class="icon-time" ng-click="qushiClick(8);"></span></h4>
                    <div stu-chart config="vm.items[7]" style="height:310px; width:100%"class="img_marg img-top"> </div>
                  <%--   <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
                    </div>
                    <div class="clearfix"></div>
                </div><!--------------------------------------------------以上人员（书籍）类型分布情况----------------------------------------------></div>
         </div>
         <!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    -->
         <!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    --><!--  逾期时长    -->
           <div ng-show="fasle"><!-- ndrypm"> -->
<div class="booksjy_top_imges" >
                	
                    <div class="clearfix"></div>
                	<div class="top_date_02" >
                    	<div class="fl select_k" > 
                            <a href="" ng-click="xnulshow=!xnulshow"><i ng-class="xnulshow?'icon-angle-up':'icon-angle-down'"></i>
                            <p>{{schoolYear}}学年</p></a>
                            <ul ng-show="xnulshow">
                            	<li ng-repeat="item in schoolYears"><a href="" ng-click="xnulClick(item);">{{item}}学年</a></li>
                                <!-- <li><a href="">2013-2014学年</a></li>
                                <li><a href="">2012-2013学年</a></li>
                                <li><a href="">2011-2012学年</a></li> -->
                            </ul>
                           <div class="clearfix"></div>
                        </div>
<!--                         <div class="danxuan fr">
                            <div class="radio radio-inline radio-change radio-blue">
                                <input ng-model="type2" type="radio" name="radio053_6" id="xs_053_6" value="stu" checked="">
                                <label for="xs_053_6" class="fw-nm">
                                      学生情况
                                </label>
                            </div>
                            <div class="radio radio-inline radio-change radio-blue">
                                <input ng-model="type2" type="radio" name="radio053_6" id="js_053_6" value="tea">
                                <label for="js_053_6" class="fw-nm">
                                      教师情况
                                </label>
                            </div>
                        </div> -->
                        <div class="clearfix"></div>
                    </div>
                   
                    <div class="clearfix"></div>
                </div><!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------><div class="fenbu">
                	<div class="ndhj">
                    	<div class="t">
                        	<h5>年度获奖情况</h5>
                        </div>
                        <div class="ndhj_con">
                            <div ng-repeat="item in vm.items[9]" ng-class="item.RANK_==1?'guanjun':item.RANK_==2?'yajun':'jijun fr'">
                            	<div class="huoj_head">
                                	<img src="${images}/user.jpg" width="105" height="105" class="img-circle fl img-top">
                                    <div class="fl mz">
                                        <h4>{{item.NAME}}<img src="${images}/books_images/{{item.SEX_NAME=='男'?'nan':'nv'}}.png"></h4>
                                        <h5>{{item.NO_}}</h5>
                                    </div>
                                    <img src="${images}/books_images/{{item.RANK_==1?'guanjun':item.RANK_==2?'yajun':'jijun'}}.png" class="jp img-top">
                                    <div class="clearfix"></div>
                                </div>
                                <p><span class="text-left">{{item.OFNAME}}</span><!-- <span class="text-center">计算机信息管理</span><span class="text-right">信息管理1班</span> --></p>
                                <div class="jieshuk">
                                	<div>
                                    	<div class="jieshuk_k">
                                        <img src="${images}/books_images/jieshuk_top.png" width="299" class="img-top">
                                        <div class="jieshuk_con" align="center">
                                        	<table>
                                            	<tbody>  
                                                    <tr class="tr_border">  
                                                      <td class="td_border">
                                                      	<p>本年借书</p>
                                                        <p class="p_td"><a href="" class="colorr">{{item.YEARNUM}}</a>本</p>
                                                      </td>  
                                                      <td>
                                                      	<p>历史借书</p>
                                                        <p class="p_td"><a href="" class="colorr">{{item.HISTORYNUM}}</a>本</p>
                                                      </td>  
                                                    </tr> 
                                                    <tr>  
                                                      <td class="td_border">
                                                      	<p class="p_td">本年月度上榜</p>
                                                        <p><a href="" class="colorr">{{item.TOPNUM}}</a>次</p>
                                                      </td>  
                                                      <td>
                                                      	<p class="p_td">历史月度上榜</p>
                                                        <p><a href="" class="colorr">{{item.HISTORYTOP}}</a>次</p>
                                                      </td>  
                                                    </tr> 
                                                </tbody> 
                                            </table>
                                        </div>
                                        <img src="${images}/books_images/jieshuk_bottom.png" class="img-top">
                                        </div>
                                    </div>
                                </div>
                            </div>
                     
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div><!------------------------------------------------- 以上年度获奖情况 ----------------------------------------------><div class="jdgj">
                	<div class="t">
            			<h5>季度冠军情况</h5>
                	</div>
                     <div id="jidudiv"class="jidugj">
                     	<div ng-repeat="item in vm.items[10]" ng-class="$index==3?'last_border':''">
                        	<div class="jidugj_l col-md-5">
                            	<div class="fl">
                                    <img src="${images}/books_images/jiduk_top.png" class="img-top">
                                    <div class="jidugj_l_p"><p>{{item.QUARTERNAME}}</p></div>
                                    <img src="${images}/books_images/jiduk_bottom.png" class="img-top">
                                </div>
                                <div class="fl name">
                                    <img src="${images}/user.jpg" width="90" height="90" class="img-circle img-top">
                                    <h4>{{item.NAME}}<img src="${images}/books_images/{{item.SEX_NAME=='男'?'nan':'nv'}}.png" class="img-top"></h4>
                                </div>
                                <div class="fl">
                                	<ul>
                                    	<li>{{item.NO_}}</li>
                                        <li>{{item.OFNAME}}</li>
                                       <!--  <li>计算机信息管理</li>
                                        <li>信息管理2班</li> -->
                                    </ul>
                                </div>
                            </div>
                            <div class="jidugj_m col-md-4">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/jieshu.png" class="img-top">借书</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本季</li>
                                        <li><a href="" class="colorr">{{item.QUARTERNUM}}</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">{{item.YEARNUM}}</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">{{item.HISTORYNUM}}</a>本</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="jidugj_r col-md-3">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/huangguan.jpg" class="img-top">月度上榜</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">{{item.TOPNUM}}</a>次</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">{{item.HISTORYTOP}}</a>次</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        
                       <%-- <div>
                        	<div class="jidugj_l col-md-5">
                            	<div class="fl">
                                    <img src="${images}/books_images/jiduk_top.png" class="img-top">
                                    <div class="jidugj_l_p"><p>第二季度</p></div>
                                    <img src="${images}/books_images/jiduk_bottom.png" class="img-top">
                                </div>
                                <div class="fl name">
                                    <img src="${images}/user.jpg" width="90" height="90" class="img-circle img-top">
                                    <h4>刘新新<img src="${images}/books_images/nan.png" class="img-top"></h4>
                                </div>
                                <div class="fl">
                                	<ul>
                                    	<li>20130120102</li>
                                        <li>历史学院</li>
                                        <li>历史系</li>
                                        <li>历史0901班</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="jidugj_m col-md-4">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/jieshu.png" class="img-top">借书</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本季</li>
                                        <li><a href="" class="colorr">40</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">40</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">120</a>本</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="jidugj_r col-md-3">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/huangguan.jpg" class="img-top">月度上榜</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">10</a>次</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">30</a>次</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        
                        <div>
                        	<div class="jidugj_l col-md-5">
                            	<div class="fl">
                                    <img src="${images}/books_images/jiduk_top.png" class="img-top">
                                    <div class="jidugj_l_p"><p>第三季度</p></div>
                                    <img src="${images}/books_images/jiduk_bottom.png" class="img-top">
                                </div>
                                <div class="fl name">
                                    <img src="${images}/user.jpg" width="90" height="90" class="img-circle img-top">
                                    <h4>秦萌<img src="${images}/books_images/nv.png" class="img-top"></h4>
                                </div>
                                <div class="fl">
                                	<ul>
                                    	<li>20130120102</li>
                                        <li>计算机学院</li>
                                        <li>电子信息管理</li>
                                        <li>电信1210班</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="jidugj_m col-md-4">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/jieshu.png" class="img-top">借书</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本季</li>
                                        <li><a href="" class="colorr">40</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">40</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">120</a>本</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="jidugj_r col-md-3">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/huangguan.jpg" class="img-top">月度上榜</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">10</a>次</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">30</a>次</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        
                        <div class="last_border">
                        	<div class="jidugj_l col-md-5">
                            	<div class="fl">
                                    <img src="${images}/books_images/jiduk_top.png" class="img-top">
                                    <div class="jidugj_l_p"><p>第四季度</p></div>
                                    <img src="${images}/books_images/jiduk_bottom.png" class="img-top">
                                </div>
                                <div class="fl name">
                                    <img src="${images}/user.jpg" width="90" height="90" class="img-circle img-top">
                                    <h4>柴林林<img src="${images}/books_images/nan.png" class="img-top"></h4>
                                </div>
                                <div class="fl">
                                	<ul>
                                    	<li>20130120102</li>
                                        <li>美术与设计学院</li>
                                        <li>艺术设计</li>
                                        <li>环境设计1311班</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="jidugj_m col-md-4">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/jieshu.png" class="img-top">借书</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本季</li>
                                        <li><a href="" class="colorr">40</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">40</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">120</a>本</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="jidugj_r col-md-3">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/huangguan.jpg" class="img-top">月度上榜</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">10</a>次</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">30</a>次</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                        --%>
                     
                     </div>
                      <p class="shou" ng-click="zkygjdiv('jidudiv');">
                        	<b>
                                <i><a href=""><span class="icon-angle-{{jidudiv?'down':'up'}}"></span></a></i>
                                <img src="${images}/books_images/shou.png" class="img-top">
                            </b>
                        </p>
                    <div class="clearfix"></div>
                </div><!--------------------------------------------------以上季度冠军情况----------------------------------------------><div class="jdgj ydgj">
                	<div class="t">
            			<h5>月度冠军情况</h5>
                	</div>
                    <div class="jidugj" id="yuediv">
                        <!-- <div class="last_border">
                        	<p align="center" ng-click="zkygjdiv()" style="cursor: pointer;">{{zkygjtitle}}详情</p>
                        </div> -->
                        <div id="zkygjdiv" ng-repeat="item in vm.items[11]" ng-class="$index==3?'last_border':''">
                        	<div class="jidugj_l col-md-5">
                            	<div class="fl">
                                    <img src="${images}/books_images/jiduk_top.png" class="img-top">
                                    <div class="jidugj_l_p"><p>{{item.MONTH_}}月</p></div>
                                    <img src="${images}/books_images/jiduk_bottom.png" class="img-top">
                                </div>
                                <div class="fl name">
                                    <img src="${images}/user.jpg" width="90" height="90" class="img-circle img-top">
                                    <h4>{{item.NAME}}<img src="${images}/books_images/{{item.SEX_NAME=='男'?'nan':'nv'}}.png" class="img-top"></h4>
                                </div>
                                <div class="fl">
                                	<ul>
                                    	<li>{{item.NO_}}</li>
                                        <li>{{item.OFNAME}}</li>
                                       <!--  <li>计算机信息管理</li>
                                        <li>信息管理2班</li> -->
                                    </ul>
                                </div>
                            </div>
                            <div class="jidugj_m col-md-4">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/jieshu.png" class="img-top">借书</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本季</li>
                                        <li><a href="" class="colorr">{{item.QUARTERNUM}}</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">{{item.YEARNUM}}</a>本</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">{{item.HISTORYNUM}}</a>本</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="jidugj_r col-md-3">
                            	<div class="jidugj_m_top"><h5><img src="${images}/books_images/huangguan.jpg" class="img-top">月度上榜</h5></div>
                                <div class="jidugj_m_bottom">
                                	<ul>
                                        <li>本年</li>
                                        <li><a href="" class="colorr">{{item.TOPNUM}}</a>次</li>
                                    </ul>
                                    <ul>
                                        <li>历史</li>
                                        <li><a href="" class="colorr">{{item.HISTORYTOP}}</a>次</li>
                                    </ul>
                                </div>
                                <div class="clearfix"></div>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                     <p class="shou" ng-click="zkygjdiv('yuediv');">
                        	<b>
                                <i><a href=""><span class="icon-angle-{{yuediv?'down':'up'}}"></span></a></i>
                                <img src="${images}/books_images/shou.png" class="img-top">
                            </b>
                        </p>
                </div><!--------------------------------------------------以上月度冠军情况----------------------------------------------></div>    
            </div>               	
        </div>       
   <div class="clearfix"></div>
	</div>
</body>
 <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
 <div stu-chart config="qushiData" class="qsdivc" ></div>
 </div>
  <div cg-combo-xz data="pagexq" type=''></div>
</html>