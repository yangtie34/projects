<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <div class="swiper-slide section8">
      <div class="usty-menu-height"></div>
      <div class="usty-common-auto usty-firstxf">
        <div class="usty-firstxf-line">
          <h3 class="usty-firstxf-h3">第一次消费</h3>
          <ul>
            <li>
              <dl>
                <dt  class="usty-firstxf-dt01">时间</dt>
                <dd>{{first.cardMap.time_}} </dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt  class="usty-firstxf-dt02">金额</dt>
                <dd>{{first.cardMap.pay_money}}元</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt  class="usty-firstxf-dt03">消费类型</dt>
                <dd>{{first.cardMap.card_deal}} </dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt  class="usty-firstxf-dt04">地点</dt>
                <dd>{{first.cardMap.card_win}}</dd>
              </dl>
            </li>
          </ul>
      </div>
    </div>
    </div>
  <div class="swiper-slide section11">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-firstxf-two">
      <div ng-class="{'usty-firstxf-two-img' : first.notPassMap != null || first.punishMap != null,
      'usty-firstxf-two-img1' : first.notPassMap == null && first.punishMap == null}"> </div>
      <div class="usty-firstxf-two-list">
        <dl>
          <dt>第一次挂科</dt>
          <dd ng-show = "first.notPassMap != null">科目：{{first.notPassMap.course_name}} &nbsp;&nbsp;  时间：{{first.notPassMap.school_year}}学年{{first.notPassMap.term_code}}学期</dd>
          <dd ng-show = "first.notPassMap == null">没有挂科！</dd>
        </dl>
        <dl>
          <dt class="blue-bg">第一次违纪</dt>
          <dd class="blue-font"  ng-show = "first.punishMap != null"> 时间：{{first.punishMap.date_}} &nbsp;&nbsp;  处罚：{{first.punishMap.name_}}</dd>
          <dd ng-show = "first.punishMap == null">没有违纪！</dd>
        </dl>
      </div>
    </div>
  </div>
  <div class="swiper-slide section12">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-firstxf-three">
      <article class="usty-firstxf-three-list01" >
        <dl>
          <dt>第一次获得奖学金 </dt>
          <dd class="usty-firstxf-three-padright" ng-show = "first.awardMap == null">没有获得过奖学金</dd>
          <dd class="usty-firstxf-three-padright" ng-show = "first.awardMap != null">金额：{{first.awardMap.money}}元 &nbsp;&nbsp;  时间：{{first.awardMap.school_year}}学年</dd>
        </dl>
      </article>
      <article class="usty-firstxf-three-list03">
        <dl>
          <dt class="heng-3color">第一次获得助学金 </dt>
          <dd class="usty-firstxf-three-padright" ng-show = "first.subsidyMap == null">没有获得过助学金</dd>
          <dd  class="usty-firstxf-three-padright"  ng-show = "first.subsidyMap != null">金额：{{first.subsidyMap.money}}元 &nbsp;&nbsp;  时间：{{first.subsidyMap.school_year}}学年</dd>
        </dl>
      </article>
    </div>
  </div>
  <div class="swiper-slide section13">
      <div class="usty-menu-height"></div>
      <div class="usty-common-auto usty-firstxf-four">
        <div  class="usty-firstxf-four-list01">
          <article>
            <dl>
              <dt>刷卡</dt>
              <dt>踏入图书馆 </dt><br>
              <dd class="text-center"> <span class="color-time-imp"> 时间：</span> </dd>
              <dd class="text-align-right"> {{first.bookRkeMap.date_}} </dd>
              <dd class="text-align-right-indent"> {{first.bookRkeMap.time_}}</dd>
            </dl>
            <dl style="padding-left:15px;">
              <dt>借书 </dt>
              <dd class=" usty-dd-rgt"> <span class="color-time-imp"> 书名：</span><span style="width:200px;overflow: hide">{{first.bookMap.book_name}}</span></dd>
              <dd class=" text-align-float"  > <span class="color-time-imp"> 时间：</span></dd>
              <dd class=" text-align-center-float" > {{first.bookMap.borrow_date}} <br />
                {{first.bookMap.borrow_time}}</dd>
            </dl>
          </article>
          <div class="usty-firstxf-four-bottom">第一次</div>
        </div>
      </div>
    </div>
  