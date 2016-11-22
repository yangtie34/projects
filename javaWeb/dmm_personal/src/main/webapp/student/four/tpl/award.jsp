<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <div class="swiper-slide section2"> 
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-jiang">
    	<div class="usty-jiang-line">
           <div class="usty-jiang-right ">
              <p class="usty-jiang-textp">获得<span class="usty-jiang-text">{{award.nums}}</span>次奖学金, 共计<span class="usty-jiang-text">{{award.sums}}</span>元 </p>
	          <p class="usty-jiang-textp">本届得奖次数超越人数：<span class="usty-jiang-text">{{award.passNumsPro}}</span> </p>
	          <p class="usty-jiang-textp">获奖金额超越人数：<span class="usty-jiang-text">{{award.passSumsPro}}</span> </p>
           </div>
       </div>
       <hr>
       <div class="usty-jiang-line">
      <div class="usty-chengji02-title usty-jiang02-title">
          <h3>共违纪{{punish.total}}次</h3> 
        </div>
           <div class="usty-jiang-right">
              <p class="usty-jiang02-dl01">记过<span class="usty-jiang-text">{{punish.jiguo}}</span>次 </p>
	          <p class="usty-jiang02-dl02">严重警告<span class="usty-jiang-text">{{punish.yzjg}}</span>次 </p>
	          <p class="usty-jiang02-dl03">留校察看<span class="usty-jiang-text">{{punish.lxck}}</span>次 </p>
	          <p class="usty-jiang02-dl04">警告<span class="usty-jiang-text">{{punish.jg}}</span>次 </p>
           </div>
       </div>
    </div>
  </div>