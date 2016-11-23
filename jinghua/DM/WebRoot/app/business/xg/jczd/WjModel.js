/**
 * 违纪公用部分
 */
NS.define('Business.xg.jczd.WjModel', {
	extend : 'Template.Page',
	requires:['Business.component.PageHeaderXqXq','Business.component.PageBottom'],
	//预加载tpl
	tplRequires : [
       {fieldname : 'tplZxjModel',path : 'app/business/xg/jczd/template/zxjModel.html'}
	 ],
	 //css预加载
	 cssRequires : ['app/business/xg/jczd/template/style/zxj.css',
	                'app/business/xg/jczd/template/style/zxj-common.css',
                    'app/business/xg/jczd/template/style/zxj-yuxuan.css',
                    'app/business/xg/jczd/template/style/zxjyx-common.css',
                    'app/business/xg/jczd/template/style/zxjyx-infor.css'
	                ],
//	/**
//	 * 请求后台服务配置
//	 */
//	modelConfig : {
//		serviceConfig : {
//			'queryJzPcLx' :'jzShService?queryJzPcLx'//查询批次类型数据
//		}
//	},
	//顶部菜单名称
	cdmc:'',
	//底部描述
	ms:'',
	//批次类型切换
	select :'',
	//后台服务配置数据
    serviceConfigData:{},
    //后台服务配置数据1
    serviceConfigData1:{},
    //是否直接通过
    sftg:'0',
    //隶属模块
	lsmk : '',
    sfqy:'1',
	//当前类型名称
	
	/**
	 * 入口
	 */
	init: function () {
        //增加服务方法
        this.updateServiceConfig(this.serviceConfigData);
        this.updateServiceConfig(this.serviceConfigData1);
		this.tplData = {};
		if(this.cdmc!=''){
			this.tplData.zxj_top=this.zxj_top=this.getDivId('zxj_top');
		}
		if(this.ms!=''){
			this.tplData.zxj_bottom=this.zxj_bottom=this.getDivId('zxj_bottom');
		}
//		if(this.select !=''){
//			this.tplData.zxj_select=this.zxj_select=this.getDivId('zxj_select');
//			this.initPclxData();
//		}
		else{
            this.initData();
        }

	},
    /**
     * 请求数据[重写]
     */
    initData : function(){

    },
//	/**
//	 * 初始化批次类型数据
//	 */
//	initPclxData : function (){
//	this.callService({key:'queryJzPcLx',params:{sftg:this.sftg,lsmk:this.lsmk,sfqy:this.sfqy}},function(data){
//				if(data.queryJzPcLx.success){
//					var pcData = {};
//					pcData.pcLxData = data.queryJzPcLx.data;
//					if(data.queryJzPcLx.count > 0){
//						this.pclxId = data.queryJzPcLx.data[0].pclxid;
//	                    this.pcId = data.queryJzPcLx.data[0].pcid;
//	                    this.lxId = data.queryJzPcLx.data[0].lxid;
//						pcData.pcMc=this.pcMc = data.queryJzPcLx.data[0].pcmc;
//						pcData.lxMc=this.lxMc = data.queryJzPcLx.data[0].lxmc;
//						pcData.sfqy = this.sfqy = data.queryJzPcLx.data[0].sfqy;
//						this.initSelectCom(pcData);
//					}else{
//						 var page= new NS.Component({
//							    
//						   		html:"<style> body{ background:#ececec}.style-lly{ width:300px; margin:0 auto; margin-top:20%; " +
//						   			"background:url(app/business/xg/jczd/template/image/wupici.png) no-repeat left top; padding-left:150px; padding-top:10px; height:120px; font:bold 24px 'microsoft yahei'; color:#ff6600; text-shadow:1px 1px 1px #fff;}</style>"
//						   			+"<div class='style-lly'>当前没有可以使用的批次！</div>"
//						
//							});
//						
//						this.setPageComponent(page);
//					}
//					
//				}
//			});
//	},
//	
//	/**
//	 * 初始化选择区域页面
//	 */
//	initSelectCom : function(data){
//		var mc = "";
//		switch(this.lsmk){
//        case "TbXgJzZxj" : mc="助学金";
//            break;
//        case "TbXgJzJm" : mc="学费减免";
//            break;
//        case "TbXgJzJxj" : mc="奖学金";
//            break;
//        case "TbXgJzRych" : mc="荣誉称号";
//            break;
//    }
//		this.zxj_pclxlbs = this.getDivId('zxj_pclxlbs');
//    	data.zxj_pclx=this.zxj_pclx = this.getDivId('zxj_pclx');
//    	data.zxj_pclxlbs = this.zxj_pclxlbs;
//    	var stpl= new NS.Template('<h2>'+
//	 								'<span class="zxj_title ">当前'+mc+'批次:</span> '+
//	 									'<span id={zxj_pclx} class="zxj_orange zxj_textbold zxj_fontSize">{pcMc}['+
//	 									'<span class="zxj_blue zxj_textbold zxj_fontSize">{lxMc}</span>]'+
//	 								'</span>'+
//	 								'<span class="zxj_history"><a class="zxj_linkchange" onmouseover=\'document.getElementById("{zxj_pclxlbs}").style.display="block"\' ' +
//	 								'onmouseout=\'document.getElementById("{zxj_pclxlbs}").style.display="none"\'  href="#">切换>></a>'+
//	 								
//	 								'<div id="{zxj_pclxlbs}" class="zxj_history-beside"'+
//	 									' onmouseover=\'document.getElementById("{zxj_pclxlbs}").style.display="block"\' ' +
//	 									'onmouseout=\'document.getElementById("{zxj_pclxlbs}").style.display="none"\'>'+
//	 								'</div>'+
//	 								
//	 								'</span>'+
//	 							'</h2>');
//    	this.selectCom = new NS.Component({
//	        tpl : stpl,
//	        data : data
//	    });
//		this.selectCom.on('click',function(event,target){
//			if(target.nodeName == 'A'){
//                if('zxj_lb'== target.name){
//                      var pclx = target.id;
//                      this.pclxId = pclx.split(',')[0];
//                      this.pcId = pclx.split(',')[1];
//                      this.lxId = pclx.split(",")[2];
//                      var pcMc=this.pcMc = pclx.split(",")[3];
//                      var lxMc=this.lxMc = pclx.split(",")[4];
//                      this.sfqy = pclx.split(",")[5];
//					$('#'+this.zxj_pclx).html(pcMc+'[<span class="zxj_blue zxj_textbold zxj_fontSize">'+lxMc+'</span>]');
//                      this.refreshDataByPclx();//刷新数据
//                }
//			}
//		},this);
//		
//		this.initSelectLbCom(data.pcLxData);
//		
//	},
//	/**
//	 * 批次类型列表组件
//	 */
//	initSelectLbCom : function (data){
//        var lbtpl=new NS.Template('<div class="zxj_history-hover">'+
//        							'<tpl for=".">'+
//        								'<h3 class="zxj_history-title">'+
//        									'<span><a id="{pclxid},{pcid},{lxid},{pcmc},{lxmc},{sfqy}" name="zxj_lb" style="cursor: pointer;">{pcmc}[{lxmc}]' +
//            '                               <tpl if="sfqy==1">启用</tpl><tpl if="sfqy==0">停用</tpl></a></span>'+
//        								'</h3>'+
//        							'<tpl>'+
//	   			  				'</div>');
//		this.selectlbCom = new NS.Component({
//			border : false,
//			data : data,
//			tpl : lbtpl
//		});
//        this.initData();
//	},
	/**
	 * 初始化组件[子类调用]
	 */
	initComponent : function(data){
		if(this.cdmc!=''){
			//初始化顶部页面
			 this.initTopCom();
		}
		//初始化grid
		this.grid = this.getGrid(data);
		//得到tbar
		this.tbar=this.getTbar(data);
		//初始化统计区页面
		this.countCom = this.getCountCom(data);
		
		//初始化底部页面
		if(this.ms!=''){
			this.initBottomCom();
		}
		//渲染页面
		this.initPage();
		//遮罩
		this.initMask();
	},
	/**
	 * 初始化顶部组件
	 */
	initTopCom : function(){
		this.topCom = Business.component.PageHeader.getInstance(this.cdmc);
	},
	/**
	 * 初始化tbar[重写]
	 */
	getTbar : function(data){
		
	},
	/**
	 * 初始化统计组件[重写]
	 */
	getCountCom : function(data){
		
	},
	/**
	 * 初始化grid[重写]
	 */
	getGrid : function(data){
		
	},
    /**
     * 刷新数据[重写]
     */
    refreshDataByPclx : function(){

    },
    /**
     * 加载[重写]
     */
    pageAfterRender : function(){
    	
    },
    
	/**
	 * 初始化底部
	 */
	initBottomCom : function(){
		this.bottomCom = Business.component.PageBottom.getInstance(this.ms);
	},
	/**
	 *  渲染主页面
	 */
	initPage : function(){
        if(NS.isDefined(this.tbar)){
            this.tplData.zxj_tbar=this.zxj_tbar=this.getDivId('zxj_tbar');
        }
        if(NS.isDefined(this.countCom)){
            this.tplData.zxj_tj=this.zxj_tj = this.getDivId('zxj_tj');
        }
        if(NS.isDefined(this.grid)){
            this.tplData.zxj_grid=this.zxj_grid = this.getDivId('zxj_grid');
        }
		//主页面com
		var tplPageCom = new NS.Component({
				 border : false,
				 data : this.tplData,
				 tpl:this.tplZxjModel
			});
		this.page = new NS.container.Container(
					{ 
						items:tplPageCom,
						autoScroll: true
					});
			
		this.page.on('afterrender',function(){
				if(this.cdmc!=''){
					this.topCom.render(this.zxj_top);//顶部
				}
//				if(this.select!=''){
//					this.selectCom.render(this.zxj_select);//切换选择
//					this.selectlbCom.render(this.zxj_pclxlbs);//批次类型下拉列表
//				}
				if(NS.isDefined(this.tbar)){
					this.tbar.render(this.zxj_tbar);//tbar
				}
				if(NS.isDefined(this.countCom)){
					this.countCom.render(this.zxj_tj);//统计区
				}
				if(NS.isDefined(this.grid)){
					this.grid.render(this.zxj_grid);//grid
				}
				if(this.ms!=''){
					this.bottomCom.render(this.zxj_bottom);//底部
				}
				
				this.pageAfterRender();
				
			},this);	
		this.setPageComponent(this.page);
	},
	/**
	 * 遮罩
	 */
	initMask : function(){
		this.mask = new NS.mask.LoadMask({
	         target : this.page,
	         msg : '数据加载中,请稍候...'
	     });
	},
	/**
	 * 生成div的id
	 * @param id
	 * @return
	 */
	getDivId  : function(id){
		return id+NS.id();
	}
});