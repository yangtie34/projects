<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <div class="swiper-slide section6">
      <div class="usty-menu-height"></div>
      <div class="usty-common-auto usty-xiaofei">
        <div class="usty-xiaofei-line">
          <ul>
            <li>
              <dl>
                <dt>消费共计</dt>
                <dd><span class="usty-xiaofei-lispan"> {{card.sums}} </span>元，</dd>
                <dd><span class="usty-xiaofei-lispan"> {{card.nums}} </span>笔</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>日均消费</dt>
                <dd><span class="usty-xiaofei-lispan"> {{card.day_avg}} </span>元</dd>
              </dl>
            </li>
            <li>
              <dl>
                <dt>消费共计</dt>
                <dd>超越了<span class="usty-xiaofei-lispan"> {{card.passNumPro}} </span></dd>
                <dd>的同届学生</dd>
              </dl>
            </li>
          </ul>
        </div>
        <div echart config="cardChart" width="100%"></div>
      </div>
    </div>
  <div class="swiper-slide section10">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-xiaofei-two">
      <div class="usty-xiaofei-two-line">
        <ul class="usty-xiaofei-two-right">
          <li>
            <h3 class="usty-xiaofei-two-h3">最爱这里就餐</h3>
            <dl  class="usty-xiaofei-two-dl01">
              <dt> </dt>
              <dd ng-repeat="ca in cardWins"><span>{{ca.name_}}</span> <span>{{ca.sums}} 元/{{ca.nums}}次</span> <span>{{ca.pro}} </span></dd>
            </dl>
          </li>
          <li>
            <dl class="usty-xiaofei-two-dl02">
              <dt > </dt>
              <dd><span class="usty-xiaofei-two-imp">{{shopCard.pro}} <span class="usty-xiaofei-two-bold">￥{{shopCard.sums}}</span></span></dd>
            </dl>
          </li>
        </ul>
        <div echart config="cardPie" width="100%" style="margin-top:10px;"></div>
      </div>
    </div>
  </div>
  <div class="swiper-slide section17">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-chengji03">
      <div class="usty-chengji03-title clearfix">
        <div class="usty-chengji03-icon usty-xiaofei03-icon usty-chengji03-float-left">
          <dl>
            <dt>洗浴</dt>
            <dd></dd>
          </dl>
        </div>
        <ul class="usty-chengji03-ul usty-xiaofei03-ul usty-chengji03-float-left">
          <li class="usty-chengji03-yellow">我的四年洗浴支出共 <span class="usty-chengji03-color-yellow">{{washCard.my_sums}}元</span></li>
          <li class="usty-chengji03-green">洗浴支出最多的{{washCard.sex}}生为 <span class="usty-chengji03-color-green">{{washCard.max_sums}}元</span> </li>
          <li class="usty-chengji03-red">我超越了 <span class="usty-chengji03-color-red">{{washCard.passPro}}</span> 的{{washCard.sex}}生</li>
        </ul>
      </div>
    </div>
  </div>
  <div class="swiper-slide section17">
      <div class="usty-menu-height"></div>
      <div class="usty-common-auto usty-chengji03">
        <div class="usty-chengji03-title usty-xiaofei04-title clearfix">
          <div class="usty-chengji03-icon usty-xiaofei04-icon usty-chengji03-float-left">
            <dl>
              <dt>餐饮分析</dt>
              <dd></dd>
            </dl>
          </div>
          <ul class="usty-chengji03-ul usty-xiaofei04-ul  usty-chengji03-float-left">
            <li class="usty-chengji03-yellow">吃早餐次数{{meals.zccs}}超越<span class="usty-chengji03-color-yellow"> {{meals.zccsPro}} </span>的{{meals.sex}}生 </li>
            <li class="usty-chengji03-green">吃满两餐的天数{{meals.mlcts}}超越 <span class="usty-chengji03-color-green">{{meals.mlctsPro}}</span>的{{meals.sex}}生 </li>
            <li class="usty-chengji03-red">吃满三餐的天数{{meals.mccts}}超越 <span class="usty-chengji03-color-red">{{meals.mcctsPro}}</span>的{{meals.sex}}生</li>
          </ul>
        </div>
        <article class="usty-xiaofei04-fenxi">
          <section class="usty-xiaofei04-section">
            <h3>餐饮消费平均值</h3>
          </section>
          <div echart config="cardPieChart" width="100%"></div>
        </article>
         </div>
      </div>