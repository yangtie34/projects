<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="swiper-slide rxs-slide rxs-abs-bg-lr"  >
      <div class="rxs-pk-tit-bg">
        <div class="rxs-pk-tit rxs-pk-xf-icon">消费</div>
      </div>
      <div class="rxs-pk-left ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"><img src="../images/user-pic.png" class="img-circle" width="50"> </div>
      <div class="rxs-pk-right ani"  swiper-animate-effect="fadeInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"><img src="../images/xueba.png" class="img-circle" width="50"> </div>
      <div class="rxs-pk-list ani" swiper-animate-effect="rollIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">
        <table width="100%" class="rxs-pk-table rxs-pk-table-20" border="0"  >
          <thead>
            <tr>
              <th></th>
              <th></th>
              <th>平均</th>
              <th>最高</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{consumeData.me.sumval}}</td>
              <td><span  class="rxs-pk-label">消费总额</span></td>
              <td>{{consumeData.smart.avg_sum}}</td>
              <td>{{consumeData.smart.max_sum}}</td>
            </tr>
            <tr>
              <td>{{consumeData.me.times}}</td>
              <td><span class="rxs-pk-label">消费次数</span></td>
              <td>{{consumeData.smart.avg_times}}</td>
              <td>{{consumeData.smart.max_times}}</td>
            </tr>
            <tr>
              <td>{{consumeData.me.avgval}}</td>
              <td><span class="rxs-pk-label">日均消费</span></td>
              <td>{{consumeData.smart.avg_day}}</td>
              <td>{{consumeData.smart.max_day}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    