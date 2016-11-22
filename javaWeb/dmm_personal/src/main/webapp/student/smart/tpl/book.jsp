<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 <div class="swiper-slide rxs-slide rxs-abs-bg-lr">
      <div class="rxs-pk-tit-bg">
        <div class="rxs-pk-tit rxs-pk-tushu-icon" style="margin-left:28%;">图书借阅量</div>
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
              <td rowspan="2">{{bookData.me.nums}}</td>
              <td><span  class="rxs-pk-label">同专业</span></td>
              <td>{{bookData.major.avgval}}</td>
              <td>{{bookData.major.maxval}}</td>
            </tr>
            <tr> 
              <td><span class="rxs-pk-label">同年级</span></td>
              <td>{{bookData.grade.avgval}}</td>
              <td>{{bookData.grade.maxval}}</td>
            </tr> 
          </tbody>
        </table>
      </div>
    </div>