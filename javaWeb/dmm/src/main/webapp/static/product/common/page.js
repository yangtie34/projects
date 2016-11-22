var dmm= dmm || {};
$(function(){
	dmm.apply= dmm.apply || function(object, config, defaults){
		if (defaults) {
			apply(object, defaults); //
		}
		if (object && config && typeof config === "object") {
			var i;
			for (i in config) {
				object[i] = config[i];
			}
		}
	}
	/**
	 *  var page=new dmm.Page({startIndex:0,numPerPage:20},callback,scope);
	 *  page.render(dom);
	 * @param {} params
	 */
	dmm.Page=dmm.Page || function(pageParam,callback,scope){
		this.pageParam=pageParam;
		this.callback=callback;
		if(scope){
			this.scope=scope;
		}
		this.inputId="page_to_go" + new Date().getTime();
	    this.pageId="pages_" +  new Date().getTime();
	    this.perPageNumId="perPageNumId_"+  new Date().getTime();
	}
	dmm.Page.prototype={
		nextPage:function(){
			var currentPage = this.pageParam.currentPage;
			var totalPage = this.pageParam.totalPages;
		    if (totalPage == 0 || totalPage == 1 || currentPage == totalPage) {
				return;
			} else {  
		        this.pageParam.startIndex=parseInt(this.pageParam.startIndex)+parseInt(this.pageParam.numPerPage);
		        this.pageParam.currentPage=parseInt(currentPage) + 1;
			}
		    this.callback.call(this.scope || this,this.pageParam);
		},
		inputPage:function(index){
			var currentPage = this.pageParam.currentPage;
			var totalPage = this.pageParam.totalPages;
		    if (index>totalPage||index<1) {
		    	alert("输入不正确");
			} else {  
		        this.pageParam.startIndex=parseInt(this.pageParam.startIndex)+parseInt(this.pageParam.numPerPage);
		        this.pageParam.currentPage=index;
			}
		    this.callback.call(this.scope || this,this.pageParam);
		},
		prePage:function(){
			var currentPage = this.pageParam.currentPage;
			var start = parseInt(this.pageParam.startIndex);
		    var limit = parseInt(this.pageParam.numPerPage);
			if (currentPage == 0 || currentPage == 1) {
				return;
			} else {
				dmm.apply(this.pageParam, {
							startIndex : start - limit,
							currentPage : currentPage - 1
						});
			}
		    this.callback.call(this.scope || this,this.pageParam);
		},
		goTo:function(){
			var to_page=$("#"+this.inputId).val();
			to_page=to_page.replace(/\\s/g,"");
			if(isNaN(to_page)){
				alert('请输入数字');
		        	return;
		    }
		    if(to_page!='' && to_page.length>0){
		        var currentPage=Number(to_page);
		        if(currentPage>this.pageParam.totalPages){
		            this.goToTail();
		        }else if(currentPage<=0){
		            this.goToHead();
		        }else{
		        	dmm.apply(this.pageParam,{startIndex:(currentPage-1)*this.pageParam.numPerPage,currentPage:currentPage});
		            this.callback.call(this.scope || this,this.pageParam);
		        }
		        
		    }
		},
		goToHead:function(){
			var currentPage = this.pageParam.currentPage;
			if (currentPage == 0 || currentPage == 1) {
				return;
			} else {
				dmm.apply(this.pageParam, {
							startIndex : 0,
							currentPage : 1
						});
			}
	        if(this.callback){
	        	 this.callback.call(this.scope,this.pageParam);
	        }
		},
		goToTail:function(){
			var totalPage = this.pageParam.totalPages;
			if (totalPage == 0 || totalPage == 1) {
				return;
			} else {
				dmm.apply(this.pageParam, {
							startIndex : this.pageParam.numPerPage
									* (this.pageParam.totalPage - 1),
							currentPage : totalPage
						});
			}
		    this.callback.call(this.scope,this.pageParam);
		},
		refreshCondition:function(condition){
			this.condition=condition;
			this.goToHead();
		},
		addListener:function(){
			var me=this;
			$("#"+this.pageId).find("a").each(function(){
				var c_type=$(this).attr("c_type");
				$(this).click(function(){
					if(!c_type){
						return;
					}
					if(c_type=='go_to_head'){
						me.goToHead();
					}else if(c_type=='go_to_pre'){
						me.prePage();
					}else if(c_type=='go_to_next'){
						me.nextPage();
					}else if(c_type=='go_to_tail'){
						me.goToTail();
					}else if(c_type=='go_to'){
						me.goTo();
					}
				});
			});
			$("#"+this.pageId).find("input").keydown(function(event) {  
                if (event.keyCode == 13) { 
                	var index=parseInt($(this).val());
                	me.inputPage(index);
                }  
            });
		},
		/**
		 * 渲染到页面的某个dom或div
		 * @param {} dom
		 */
		render:function(dom){
			this.dom=dom;
			var html=this.getText();
			if(typeof dom =='string' || typeof dom =='int'){
				$("#"+dom).html(html);
			}else{		
				$(dom).html(html);
			}
			this.addListener();
		},
		refreshPage:function(pageParam){
			this.pageParam=pageParam;
			this.render(dom);
		},
		getText:function(){
			var text = '<style>.page{ margin:10px 0; font-size:13px; text-align:right;}.page input{ width:30px;} .page a{ color:#0099CC;} .page a:hover{ color:#FF6600; text-decoration:none;}</style>'
					+'<p class="page" id="'+this.pageId+'">'
					+this.pageParam.currentPage+'/'+this.pageParam.totalPages
					+'<a c_type="go_to_head" href="javascript:void(0)">首页</a>&nbsp;&nbsp;&nbsp;&nbsp;'
					+'<a href="javascript:void(0)" c_type="go_to_pre">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;'
					+'<a href="javascript:void(0)" c_type="go_to_next">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;跳至'
			        +'<input type="text" value="" style="width:30px;display: inline;" id="'+this.inputId+'"/>页&nbsp;&nbsp;&nbsp;&nbsp;'
			        //+'<a href="#" c_type="go_to">go</a>&nbsp;'
			        +'<a c_type="go_to_tail" href="javascript:void(0)">最后一页</a>'
			        +'</p>';
			return text;
		}
	};
});







