<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

 <div class="swiper-slide rxs-slide rxs-blue-bg02">
      <div class="rxs-slide-block  text-center">
        <div class="ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.4s"> <span class=" rxs-class-icon"></span>
          <p class="rxs-ft-16"> 就读专业：{{kc.major}}</p>
        </div>
        <!--row-->
        <div class="row rxs-row">
          <div class=" col-xs-5 xs-position-center ani" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.4s">
            <div class="rxs-cs rxs-cs01">必修课</div>
            <img src="../images/arrow.png" class="rxs-strg-top rxs-abp-01"  width="35"> <img src="../images/arrow.png" class="rxs-strg-btm rxs-abp-02"  width="35"> </div>
          <div class="col-xs-4 xs-position-center ani" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.5s">
            <div class="rxs-abp-03"><span class="rxs-yew-text">{{kc.course_nums}}</span><br>
              门</div>
            <div  class="rxs-abp-04" ><span class="rxs-yew-text">{{kc.period_total}}</span><br>
              课时<img src="../images/arrow.png" class="rxs-strg-top-40 rxs-abp-4-01"  width="35"> <img src="../images/arrow.png" class=" rxs-abp-4-02"  width="35"></div>
          </div>
          <div class="col-xs-3 xs-position-center ani" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.6s">
            <div class="rxs-abp-05"><span class="rxs-yew-text">{{kc.ll}}</span><br>
              理论课</div>
            <div  class="rxs-abp-06"><span class="rxs-yew-text">{{kc.sy}}</span><br>
              其他</div>
          </div>
        </div>
        <div class="row rxs-row">
          <div class=" col-xs-5 xs-position-center ani" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.7s" >
            <div class="rxs-cs rxs-cs02">学分</div>
            <img src="../images/arrow.png" class="rxs-abp-07" width="35"> </div>
          <div class="col-xs-4 xs-position-cente ani" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.8s">
            <div class="rxs-abp-03-single"><span class="rxs-yew-text">{{kc.total_credit}}</span><br>
              学分<img src="../images/arrow.png" class="rxs-strg-top rxs-abp-01-left"  width="35"> <img src="../images/arrow.png" class="rxs-strg-btm rxs-abp-02-left"  width="35"> </div>
          </div>
          <div class="col-xs-3 xs-position-center ani" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.9s">
            <div class="rxs-abp-08"><span class="rxs-yew-text">{{kc.bx_credit}}</span><br>
              必修课</div>
            <div  class="rxs-abp-09"><span class="rxs-yew-text">{{kc.xx_credit}}</span><br>
              选修课</div>
          </div>
        </div>
        <!--row-->
      </div>
    </div>