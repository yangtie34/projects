<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="swiper-slide rxs-slide rxs-abs-bg-lr"  >
      <div class="rxs-pk-tit-bg">
        <div class="rxs-pk-tit rxs-pk-chengj-icon">学习成绩</div>
      </div>
      <div class="rxs-pk-left ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"><img src="../images/user-pic.png" class="img-circle" width="50"> </div>
      <div class="rxs-pk-right ani"  swiper-animate-effect="fadeInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"><img src="../images/xueba.png" class="img-circle" width="50"> </div>
      <div class="swiper-page-center rxs-pk-list ani" ng-class="{'swiper-no-swiping' : scoreList.length > 8}" style="height:70%;overflow: auto;"  swiper-animate-effect="rollIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">
        <table width="100%" class="rxs-pk-table rxs-pk-table-0" border="0"  >
          <thead>
            <tr>
              <th></th>
              <th></th>
              <th>平均</th>
              <th>最高</th>
            </tr>
          </thead>
          <tbody>
            <tr  ng-repeat='it in scoreList'>
              <td>{{it.score}}</td>
              <td><span  class="rxs-pk-label">{{it.cname}}</span></td>
              <td>{{it.smart_avg}}</td>
		      <td>{{it.smart_max}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>