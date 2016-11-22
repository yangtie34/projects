<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="swiper-slide rxs-slide rxs-pure-bg01" >
      <div class="rxs-slide-block" >
        <div class="ani text-center" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"> 
         <p class="rxs-ft-16 rxs-lei-icon">老乡见老乡，两眼泪汪汪！</p></div>
        <div class="ani rxs-mar-tb" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.4s">
          <div class="media">
            <div class="media-left media-middle"><div class="rxs-lx rxs-lx01">在校生</div></div>
            <div class="media-body media-middle">本校目前在校人数{{stu.stus}}人，其中男生{{stu.boy}}人，女生{{stu.gril}}人。</div>
          </div>
        </div> 
        <div class="ani rxs-mar-tb" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.6s">
          <div class="media">
            <div class="media-left media-middle"><div class="rxs-lx rxs-lx02">同校</div></div>
            <div class="media-body media-middle">有{{tmx.tmx}}人和你毕业自同一个学校，其中有{{tmx.tzy}}人和你就读同一专业，男生{{tmx.boy}}人，女生{{tmx.gril}}人。</div>
          </div>
        </div>
        <div class="ani rxs-mar-tb" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.8s">
          <div class="media">
            <div class="media-left media-middle"><div class="rxs-lx rxs-lx03">同城</div></div>
            <div class="media-body media-middle">有{{tx.tx}}人和你来自同一个城市，其中有{{tx.tzy}}人和你就读同一专业，其中男生{{tx.boy}}人，女生{{tx.gril}}人。</div>
          </div>
        </div> 
       
      </div>
    </div>