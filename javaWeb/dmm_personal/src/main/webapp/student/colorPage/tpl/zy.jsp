<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="swiper-slide rxs-slide rxs-pure-bg">
      <div class="rxs-slide-block  text-center">
        <div class="ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"> <span class=" rxs-fen-icon"></span>
         <p class="rxs-ft-16"> 告别单身狗 <br> 我来给你指引方向</p>
         <div class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">
          <table class="rxs-sex-table" width="100%" border="0" cellspacing="0" cellpadding="0">
               <tr>
                <td>&nbsp;</td>
                <td><img src="../images/boy.png" width="20"></td>
                <td>VS</td>
                <td><img src="../images/girl.png" width="16"></td>
              </tr>
              <tr>
                <td>本专业</td>
                <td class="rxs-yew-cor">{{major.myMajor.boy}}</td>
                <td>：</td>
                <td class="rxs-yew-cor">{{major.myMajor.gril}}</td>
              </tr>  
              <tr>
                <td>{{major.boyMajor.major}}</td>
                <td class="rxs-yew-cor">{{major.boyMajor.boy}}</td>
                <td>：</td>
                <td class="rxs-yew-cor">{{major.boyMajor.gril}}</td>
              </tr>  
              <tr>
                <td>{{major.grilMajor.major}}</td>
                <td class="rxs-yew-cor">{{major.grilMajor.boy}}</td>
                <td>：</td>
                <td class="rxs-yew-cor">{{major.grilMajor.gril}}</td>
              </tr> 
           </table> 
         </div>
        </div>
      </div>
    </div>