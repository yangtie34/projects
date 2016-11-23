/*
 * 使用案例：$.addDate({pid:'xxx'},function(date){
 * 					alert(date.start)
 * 					});
 * 
 * pid 日期组件存放的位置
 * dateformat是时间格式，默认为"yyyy-mm-dd"。其他格式有"yyyy-mm-dd hh:ii"精确到分钟，"yyyy"精确到年
 * startId 为开始时间框的ID值，默认为start_date
 * endId 为结束时间框的ID值，默认为end_date
 * callbackfn是个一方法，有一个参数startEndDateMap，格式为{start:'2015-02-01',end:'2015-06-09'} 后面的时间格式与参数的format有关
 * startDate '2015-06-01' 时间组件默认的时间
 * endDate '2015-07-01'	      时间组件默认的时间
 * 
 * 
 * 
 */

$(function(){
	 jQuery.addDate=function(options, callbackfn) {
		 var defaults = {
					pid:'',
					dateformat:'yyyy-mm-dd',
					startId:'start_date',
					endId:'end_date',
					startDate:'',
					endDate:''
				};
		 var options = $.extend(defaults, options);
		 var minView =2;
		 var startView =2;
		 if(options.dateformat=="yyyy-mm-dd hh:ii"){
			 minView=0;
		 }else if(options.dateformat=="yyyy-mm"){
			 startView=3;
			 minView=3;
		 }else if(options.dateformat=="yyyy"){
			 startView=4;
			 minView=4;
		 }
		 
		 var html="统计区间：<input type='button' class='blue_year' value='起' /> "+
         "<input type='text' readonly class='form-control' style='width: 8%;display: initial' id='"+options.startId+"' />"+
         "<input type='button' class='blue_year' value='止' />   "+
         "<input type='text' readonly class='form-control' style='width: 8%;display: initial' id='"+options.endId+"' />";
		 $('#'+options.pid).html(html);	
		 var startEndDateMap={};
			
			$('#'+options.startId).datetimepicker({
			    language:  'zh-CN',
			    format: options.dateformat,
			    todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				minView: minView,
				startView: startView
			}).on('changeDate', function(ev){
				$('#'+options.endId).datetimepicker('setStartDate', $('#'+options.startId).val());
				$('#'+options.endId).val("");
			});
			$('#'+options.endId).datetimepicker({
			    language:  'zh-CN',
			    format: options.dateformat,
			    todayBtn:  1,
				autoclose: 1,
				todayHighlight: 1,
				minView: minView,
				startView: startView,
				startDate: options.startDate,
			}).on('changeDate', function(ev){
				if($('#'+options.startId).val()==""){
					alert("请先选择开始时间");
					$('#'+options.endId).val("");
				}else{
					startEndDateMap.start=$('#'+options.startId).val();
					startEndDateMap.end=$('#'+options.endId).val();
					callbackfn.call(null,startEndDateMap);
				}
			}); 
			$('#'+options.startId).val(options.startDate);
			$('#'+options.endId).val(options.endDate);
			
	 };
    
});