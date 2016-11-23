/**
 * 参数格式{start:0,limit:20,recordCount:200}
 * var page=new Pages.sc.common.Pages({start:0,limit:20,recordCount:200},callBack).getComponent();
 * page.render(divId || div);
 */
NS.define('Pages.sc.common.Pages', {
    extend : 'Template.Page',
    cssRequires:['app/pages/sc/common/page.css'],
	init : function() {
		/*
		 * limit : 15, start : 0, recordCount : 0,
		 * page=start/limit+start%limit>0?1:0
		 * totalPage=recordCount/limit+recordCount%limit>0?1:0
		 */
        
        NS.apply(this,args[0]);
        this.inputId="page_to_go" + NS.id();
        this.pageId="pages_" + NS.id();
		this.tplTpl = this.getTplText();
        this.initComponent();
	},
	initComponent : function() {
        var start = parseInt(this.pageParam.start);
		var limit = parseInt(this.pageParam.limit);
		var recordCount = parseInt(this.pageParam.recordCount);
        if(recordCount>0){
            this.pageParam.page = parseInt(start / limit) + (start % limit > 0 ? 1 : 0)+1;
        }else{
            this.pageParam.page=0;
        }        
		this.pageParam.totalPage = parseInt(recordCount / limit) + parseInt(recordCount % limit > 0
				? 1
				: 0);        
        var tpl = new NS.Template(this.tplTpl); 
		this.component = new NS.Component({
					data : this.pageParam,
					tpl : tpl
				});
		this.component.on('click', function(event, target) {
					this.handlePageClick(target);
				}, this);
		
		this.component.on('afterrender',function(){
			
         	this.initPageSizeEvents();
         	this.initPageNumEvents();
		},this); 
	},
	getComponent : function() {
		return this.component;
	},
	
	initPageSizeEvents:function()
	{
    	var me = this;
    	var inputId = "#page_size_" + this.pageId;
    	
		$(inputId).on("blur",function(){
			var pageSize =$(this).val();
			if(me.inputVal != pageSize)
				me.changePageSize(pageSize);
			
		});
		$(inputId).on('keyup', function(event){
			var keyCode = event.which;
			if(keyCode == 13) //enter
				$(this).blur();
		});
		$(inputId).on('focus', function(event){
			me.inputVal = $(this).val();
		});

	},

	
	initPageNumEvents:function()
	{
    	var me = this;
    	var inputId = "#" + this.inputId;
    	
		$(inputId).on("blur",function(){
			var pageSize =$(this).val();
			if(me.inputVal != pageSize)
				me.go_to();
			
		});
		$(inputId).on('keyup', function(event){
			var keyCode = event.which;
			if(keyCode == 13) //enter
				$(this).blur();
		});
		$(inputId).on('focus', function(event){
			me.inputVal = $(this).val();
		});

	},

	
	handlePageClick : function(target) {
		var c_type = $(target).attr('c_type');
		if (c_type == 'go_to_head') {
			this.go_to_head();
		} else if (c_type == 'go_to_pre') {
			this.go_to_pre();
		} else if (c_type == 'go_to') {
			this.go_to();
		} else if (c_type == 'go_to_next') {
			this.go_to_next();
		} else if (c_type == 'go_to_tail') {
			this.go_to_tail();
		}

	},
	
	changePageSize:function(pageSize)
	{ //第一页
        var size = pageSize.replace(/\\s/g,"");
        if(isNaN(size)){
        	NS.Msg.error('请输入数字');
        	return;
        }
        if(size < 1)
    	{
        	NS.Msg.error('请输入>0的正整数字');
        	return;
    	}
		NS.apply(this.pageParam, {
			start : 0,
			page : 1,
			limit : size
		});
		this.callBack.call(this.scope,this.pageParam);
	},
	
	go_to_head : function() {
		var page = this.pageParam.page;
		if (page == 0 || page == 1) {
			return;
		} else {
			NS.apply(this.pageParam, {
						start : 0,
						page : 1
					});
		}
        this.callBack.call(this.scope,this.pageParam);
	},
	go_to_pre : function() {
		var page = this.pageParam.page;
		var start = parseInt(this.pageParam.start);
        var limit = parseInt(this.pageParam.limit);
		if (page == 0 || page == 1) {
			return;
		} else {
			NS.apply(this.pageParam, {
						start : start - limit,
						page : page - 1
					});
		}
        this.callBack.call(this.scope,this.pageParam);
	},
	go_to : function() {
        var to_page=$("#"+this.inputId).val();
        to_page=to_page.replace(/\\s/g,"");
        if(isNaN(to_page)){
        	NS.Msg.error('请输入数字');
        	return;
        }
        if(to_page!='' && to_page.length>0){
            var page=Number(to_page);
            if(page>this.pageParam.totalPage){
                this.go_to_tail();
            }else if(page<=0){
                this.go_to_head();
            }else{
            	
                NS.apply(this.pageParam,{start:(page-1)*this.pageParam.limit,page:page})
                this.callBack.call(this.scope,this.pageParam);
            }
            
        }
        
	},
	go_to_next : function() {
		var page = this.pageParam.page;
		var totalPage = this.pageParam.totalPage;
        if (totalPage == 0 || totalPage == 1 || page == totalPage) {
			return;
		} else {  
            this.pageParam.start=parseInt(this.pageParam.start)+parseInt(this.pageParam.limit);
            this.pageParam.page=parseInt(page) + 1;
		}
        this.callBack.call(this.scope,this.pageParam);
	},
	go_to_tail : function() {
		var totalPage = this.pageParam.totalPage;
		if (totalPage == 0 || totalPage == 1) {
			return;
		} else {
			NS.apply(this.pageParam, {
						start : this.pageParam.limit
								* (this.pageParam.totalPage - 1),
						page : totalPage
					});
		}
        this.callBack.call(this.scope,this.pageParam);
	},
	getTplText : function() {
		var text = '<table id="page_table_'+this.pageId+'" width="100%" border="0" cellspacing="0" cellpadding="0"><tr>'
				+ '<td>'
				+ '<div class="pages-lj"><tpl if="recordCount"><span class="spage">共<tpl if="recordCount">{recordCount}<tpl else>0</tpl>条记录</span></tpl>'
				+ '<span class="spage">{page}/{totalPage}页</span>'
				+ '<span class="spage"><input id="page_size_'+this.pageId+'" c_type="page_size" class="pages-txt" value="{limit}" type="text" />条/页</span>'
				+ '<tpl if="page !=1 "><span class="spage"><a c_type="go_to_head" href="#">首页</a></span>'
				+ '<span class="spage"><a c_type="go_to_pre" href="#">上一页</a> </span></tpl>'
				+ '<span class="spage">跳转到<input id="'+this.inputId+'" c_type="page_num" class="pages-txt" name="" type="text" /><a c_type="go_to" href="#">GO</a></span>'
				+ '<tpl if="page !=totalPage "><span class="spage"><a c_type="go_to_next" href="#">下一页</a></span>'
				+ '<span class="spage"><a c_type="go_to_tail" href="#">最后一页</a></span></tpl>'
				+ '</div>' + '</td>' + '</tr></table>';
		return text;
	}

});