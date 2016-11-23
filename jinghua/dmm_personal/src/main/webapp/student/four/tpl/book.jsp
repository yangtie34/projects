<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
  <div class="swiper-slide section3">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-shu">
      <div class="usty-shu-line">
        <article class="usty-shu-left">
          <p class="usty-shu-textp">共借图书：<span class="usty-shu-text">{{borrowBooks.js}}</span>本 </p>
          <p class="usty-shu-textp">本届人均借书：<span class="usty-shu-text">{{borrowBooks.avg}}</span>本 </p>
          <p class="usty-shu-textp">超越了<span class="usty-shu-text">{{borrowBooks.numLess}}</span>的本届学生</p>
          <p class="usty-shu-textp">借书频率最高的书：<span class="usty-shu-text">{{borrowBooks.sm}}</span>，借了<span class="usty-shu-text">{{borrowBooks.sl}}</span>次 </p>
        </article>
      </div>
      <div echart config="myBorrow" width="100%"></div>
    </div>
  </div>
  <div class="swiper-slide section14">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-shu">
      <div class="usty-shu-line0">
        <article class="usty-shu02-left"> <img src="../images/tushu-ceng.png"  /> </article>
        <article class="usty-shu02-right ">
          <p class="usty-shu02-textp ">共进出图书馆：<span class="usty-shu02-text"> {{inOutLibr.pass}} </span> 次 </p>
          <p class="usty-shu02-textp ">超越了 <span class="usty-shu02-text  ">{{inOutLibr.nums}} </span>的本届学生 </p>
          <p class="usty-shu02-textp ">进出图书馆高峰期：<span class="usty-shu02-text  "> {{inOutLibr.hot}}</span> </p>
        </article>
      </div>
      <div echart config="myLibrs" width="100%"></div>
    </div>
  </div>