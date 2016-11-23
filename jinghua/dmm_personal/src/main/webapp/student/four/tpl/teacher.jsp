<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <div class="swiper-slide section19"> 
   <div class="usty-menu-height"></div>
   <div class="usty-common-auto usty-renmai02">
   	 <div class="usty-fixed-topt">
   	 	<p class="usty-renmai-textp">我的辅导员</p>
   	 </div>
     <div class="usty-renmai02-line">
        <dl class="usty-renmai02-dl" ng-repeat = "fdy in fdys">
        	<dt>{{fdy.school_year}}学年</dt> 
        	<dd class="usty-renmai02-touxiang usty-renmai02-touxiang-01" ng-show="fdy.wechat_head_img == ''"></dd>
        	<dd class="usty-renmai02-touxiang" ng-show="fdy.wechat_head_img != ''" ><img ng-src="{{fdy.wechat_head_img}}"/></dd>
        	<dd>{{fdy.name_}}</dd> <dd>{{fdy.degree_}}</dd>
       	</dl>
     </div>
    
   </div> 
 </div>
  <div class="swiper-slide section20"> 
	  <div style="position: absolute; left: 50%;top: 60px;margin-left: -100px;">
	     	 <div btn-dropdown source="source" on-change="change($data)" btn-class="btn-default" btn-style="background-color:#FFF;  width: 160px;color:#222;" display-field="mc">
	     	 </div>
	  </div>
      <div class="usty-menu-height">
      </div>
        <p class="usty-renmai-textp usty-renmai03-textp usty-fixed-topt">我的任课老师</p>
      <div class="usty-common-auto usty-renmai03">
      
         
         <div class="usty-renmai03-line clearfix">
           <dl class="usty-renmai03-dl" ng-repeat = "tea in teas" ng-show = "$index < 9">
           	  <dt ng-show = "tea.wechat_head_img == ''" ng-class="{'usty-renmai03-touxiang-04':tea.sex == '女','usty-renmai03-touxiang-06':tea.sex=='男'}"></dt>
           	  <dt class="usty-renmai03-touxiang" ng-show="tea.wechat_head_img!=''"><img ng-src = "{{tea.wechat_head_img}}"/> </dt> 
           	  <dd>{{tea.course_name}}</dd>  
           	  <dd>{{tea.name_}}   {{tea.zyjszw}}</dd>
           </dl>
        </div> 
      </div> 
    </div>