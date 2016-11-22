<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="swiper-slide rxs-slide rxs-blue-bg03">
      <div class="rxs-slide-block  text-center">
        <div class="ani" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"> <span class=" rxs-can-icon"></span>
          <p class="rxs-ft-16"> 吃好喝好才能身体好 <br>
            下面是本校就餐人数状元榜</p>
        </div>
        <div class="ani rxs-mar-tb" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.4s"><span class="rxs-can rxs-zao-icon">早餐</span><span class="rxs-to-icon">{{yc.zao}}</span> </div>
        <div class="ani rxs-mar-tb" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.4s"><span class="rxs-can rxs-wu-icon">午餐</span><span class="rxs-to-icon">{{yc.wu}}</span> </div>
        <div class="ani rxs-mar-tb" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.4s"><span class="rxs-can rxs-wan-icon">晚餐</span><span class="rxs-to-icon">{{yc.wan}}</span> </div>
        <div class="rxs-pure-text ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">清真餐厅：{{yc.qz}}</div>
      </div>
      </div>