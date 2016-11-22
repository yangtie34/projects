<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="swiper-slide rxs-slide rxs-abs-bg-lr"  >
      <div class="rxs-pk-tit-bg">
        <div class="rxs-pk-tit rxs-pk-can-icon">用餐</div>
      </div>
      <div class="rxs-pk-left ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"><img src="../images/user-pic.png" class="img-circle" width="50"> </div>
      <div class="rxs-pk-right ani"  swiper-animate-effect="fadeInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"><img src="../images/xueba.png" class="img-circle" width="50"> </div>
      <div class="rxs-pk-list ani" swiper-animate-effect="rollIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">
        <table width="100%" class="rxs-pk-table rxs-pk-table-5" border="0"  >
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
              <td>{{dinnerData.me.zao_time}}</td>
              <td><span  class="rxs-pk-label">早餐次数</span></td>
              <td>{{dinnerData.smart.avg_zao_time}}</td>
              <td>{{dinnerData.smart.max_zao_time}}</td>
            </tr>
            <tr>
              <td>{{dinnerData.me.zao_pay}}</td>
              <td><span class="rxs-pk-label">早餐平均消费</span></td>
              <td>{{dinnerData.smart.avg_zao_pay}}</td>
              <td>{{dinnerData.smart.max_zao_pay}}</td>
            </tr>
            <tr>
              <td>{{dinnerData.me.wu_time}}</td>
              <td><span class="rxs-pk-label">午餐次数</span></td>
              <td>{{dinnerData.smart.avg_wu_time}}</td>
              <td>{{dinnerData.smart.max_wu_time}}</td>
            </tr>
            <tr>
              <td>{{dinnerData.me.wu_pay}}</td>
              <td><span class="rxs-pk-label">午餐平均消费</span></td>
              <td>{{dinnerData.smart.avg_wu_pay}}</td>
		      <td>{{dinnerData.smart.max_wu_pay}}</td>
            </tr>
            <tr>
              <td>{{dinnerData.me.wan_time}}</td>
              <td><span class="rxs-pk-label">晚餐次数</span></td>
              <td>{{dinnerData.smart.avg_wan_time}}</td>
		      <td>{{dinnerData.smart.max_wan_time}}</td>
            </tr>
            <tr>
              <td>{{dinnerData.me.wan_pay}}</td>
              <td><span class="rxs-pk-label">晚餐平均消费</span></td>
              <td>{{dinnerData.smart.avg_wan_pay}}</td>
		      <td>{{dinnerData.smart.max_wan_pay}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>