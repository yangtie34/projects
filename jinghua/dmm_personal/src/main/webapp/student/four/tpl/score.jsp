<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="swiper-slide section6">
      <div class="usty-menu-height"></div>
      <div class="usty-common-auto usty-shu">
        <div class="usty-shu-line usty-chengji-line">
          <article class="usty-shu-left usty-chengji-left">
            <p class="usty-shu-textp usty-chengji-textp">共修学科：<span class="usty-shu-text usty-chengji-text">{{score.total}}</span>门 </p>
            <p class="usty-shu-textp usty-chengji-textp">挂科：<span class="usty-shu-text usty-chengji-text">{{score.notpass}}</span>门 </p>
          </article>
          <article class="usty-shu-right usty-chengji-right">
            <p class="usty-shu-textp usty-chengji-textp">平均成绩：<span class="usty-shu-text usty-chengji-text">{{score.myAvg}}</span>分</p>
            <p class="usty-shu-textp usty-chengji-textp">本专业平均成绩：<span class="usty-shu-text usty-chengji-text">{{score.majorAvg}}</span>分</p>
            <p class="usty-shu-textp usty-chengji-textp">超过了：<span class="usty-shu-text usty-chengji-text">{{score.passpro}}</span>的学生</p>
          </article>
        </div>
        <div  echart config="scoreChart" width="100%"></div>
      </div>
    </div>
  <div class="swiper-slide section15">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-chengji02">
      <div class="usty-chengji02-title">
        <h3>我的最好名次</h3>
        <p>在{{goodScore.school_year}}学年第{{goodScore.term_code}}学期，<br />
          凭借总成绩 <span class="usty-chengji02-span">{{goodScore.total_score}}分</span> 取得了四年来的最好名次：<span class="usty-chengji02-span" >{{goodScore.rn}}名</span></p>
      </div>
      <div class="usty-chengji02-line">
        <article ng-repeat="s in goodScore.scoreList" ng-class="{'usty-chengji02-green02':$index==0 || $index == 6,'usty-chengji02-red01':$index == 1 || $index == 7,
        'usty-chengji02-blue02':$index==2 || $index == 8,'usty-chengji02-red02':$index == 3 || $index == 9,'usty-chengji02-blue01':$index==4 || $index==10,
        'usty-chengji02-green01':$index==5 || $index == 11}" ng-hide = "$index > 11">
          <dl>
            <dt>{{s.score}}分</dt>
            <dd>{{s.course_name}}</dd>
          </dl>
        </article>
      </div>
    </div>
  </div>
  <div class="swiper-slide section16">
      <div class="usty-menu-height"></div>
      <div class="usty-common-auto usty-chengji03">
        <div class="usty-chengji03-title clearfix">
          <div class="usty-chengji03-icon usty-chengji03-float-left">
            <dl>
              <dt>成绩分析</dt>
              <dd></dd>
            </dl>
          </div>
          <ul class="usty-chengji03-ul usty-chengji03-float-left">
            <li class="usty-chengji03-yellow">综合成绩总分--超越 <span class="usty-chengji03-color-yellow">{{score.passtotalpro}} </span>本专业学生</li>
            <li class="usty-chengji03-green">综合平均成绩--超越 <span class="usty-chengji03-color-green">{{score.passpro}} </span>本专业学生</li>
            <li class="usty-chengji03-red">综合绩点--超越 <span class="usty-chengji03-color-red">{{score.passgpapro}} </span>本专业学生</li>
          </ul>
          <div class="usty-chengji03-zong usty-chengji03-float-left">{{score.pjs}}</div>
        </div>
        <div class="usty-chengji03-left">
          <h3 class="usty-chengji03-h3">本专业平均成绩最好的科目</h3>
          <div class="usty-chengji02-line usty-chengji03-line">
            <article ng-class="{'usty-chengji02-green02':$index == 0,'usty-chengji02-red01':$index == 1}" ng-repeat = "c in course.majorList">
              <dl>
                <dt>{{c.avg_score}}分</dt>
                <dd>{{c.course_name}}</dd>
              </dl>
            </article>
          </div>
        </div>
        <div class="usty-chengji03-left">
          <h3 class="usty-chengji03-h3">我的平均成绩最好的科目</h3>
          <div class="usty-chengji02-line usty-chengji03-line">
            <article ng-class="{'usty-chengji02-blue01':$index == 0,'usty-chengji02-green01':$index == 1,'usty-chengji02-red02':$index==2}" ng-repeat = "cou in course.myList">
              <dl>
                <dt>{{cou.score}}分</dt>
                <dd>{{cou.course_name}}</dd>
              </dl>
            </article>
          </div>
        </div>
      </div>
    </div>