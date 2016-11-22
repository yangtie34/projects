/**
 * 助学金预选
 */
NS.define('Business.xg.jczd.Yx',{
	extend : 'Business.xg.jczd.JzModel',
	requires:['Business.xg.jczd.yx.Yx_Com'],
    mixins:['Business.xg.jczd.yx.Yx_Edit',
            'Business.xg.jczd.yx.Yx_lsjl',
            'Business.xg.jczd.yx.Yx_Tbar',
            'Business.xg.jczd.yx.YxModel_win',
            'Business.xg.jczd.yx.YxModelCom'
            ],  
	//顶部菜单名称
	cdmc : '助学金预选管理',
	//批次类型切换
	select : 'Y',
	//描述信息展示
	ms:'此学生列表为符合系统设置评选助学金条件的学生',
	//描述
	lsmk : 'TbXgJzZxj',
	//后台服务配置数据
	serviceConfigData1 : {
		//表头数据
        'queryTableHeader' : 'base_queryForAddByEntityName',
        //查询grid数据
        'queryTableData' : 'zxjTmService?saveZxjGridList',
        //查询助学金统计数据
        'queryZxjjgCount' : 'zxjTmService?queryGetCount',
        //根据教学组织机构ID获取班级
        'queryBjByJxzzjgId':'base_queryUserBj',
        //查询院系专业树
        'queryTree':'base_queryUserYxZy',
        //查询入学年级树
        'queryUserRxnj' : 'base_queryUserRxnj',
        //批量设置资料是否齐全
        'setZlsfqq':'zxjTmService?saveZlsfqq',
        'queryStuInfo':'jzShService?queryStuInfoById',//根据学生id查询学生基础信息
        'queryStuTmInfo' :'jzShService?queryStuTmInfoById',//查询学生提名信息
        //查询获奖历史
        'queryStuHistory' :'jzShService?queryStuHistoryBylx',
        //提名
        'saveShInfo':'jzShService?saveShInfo',
        //根据获奖数字获取具体信息
        'queryZxjMx' : 'zxjTmService?queryHisZxjByXsId',
        
        'queryShHistory':'jzShService?queryShHistoryById'//查询审核历史记录
	},
	//页面参数
	gridParams:function(){
    	return {
    		entityName:'TbXgJzYxZxj',
    		start:0,
    		limit:25,
    		lsmk:this.lsmk
    	};
    },
	/**
	 * 重写请求数据
	 */
	initData : function(){
		this.pageParams = this.gridParams();
		this.pageParams.pcId = this.pcId;
		this.pageParams.lxId = this.lxId;
		this.pageParams.pclxId = this.pclxId;
		this.callService([
		       {key:'queryTableHeader',params:{entityName:'TbXgJzYxZxj'}},
		       {key:'queryTableData',params:this.pageParams},
		       {key:'queryZxjjgCount',params:this.pageParams},
		       {key : 'queryTree'},
               {key : 'queryUserRxnj'},
               {key : 'queryZxjjgCount'}
		 ],function(data){
			this.initComponent(data);//初始化组件
		});
	},

	/**
	 * 重写初始化统计组件
	 */
	getCountCom : function(data){
		var zxjCountTpl = new NS.Template(
		'姓名/学号/身份证号：<input class="inputdefault" name="zxjyx_queryStr" id="zxjyx_queryStr" type="text" />&nbsp;&nbsp;&nbsp;&nbsp;'+
		'<tpl for=".">'+
		'<label class="labeldefault zxj-jieguo_margin-leftfif">'+
		'<span class="zxj-jieguo_margin-leftfif">总名额：{zxjzrs}&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">预选：<span class="zxj-orange"><a name="zxjyx_yx" href="javascript:void(0);">{yxrs}</a></span>&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">提名：<span class="zxj-jieguo_orange"><a name="zxjyx_tm" href="javascript:void(0);">{tmrs}</a></span>&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">通过：<span class="zxj-jieguo_orange"><a name="zxjyx_tg" href="javascript:void(0);">{tgrs}</a></span>&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">退回：<span class="zxj-jieguo_orange"><a name="zxjyx_th" href="javascript:void(0);">{thrs}</a></span>&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">未通过：<span class="zxj-jieguo_orange"><a name="zxjyx_wtg" href="javascript:void(0);">{wtg}</a></span>&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
		'<span class="zxj-jieguo_margin-leftfif">未预选：<span class="zxj-jieguo_orange"><a name="zxjyx_wyx" href="javascript:void(0);">{wyx}</a></span>&nbsp;&nbsp;&nbsp;&nbsp;</span>'+
		'</label>'+
		'</tpl>'		
		);
		//统计数据tpl
		this.tplZxjjgCountCom = new NS.Component({
			border : false,
			data : data.queryZxjjgCount,
			tpl : zxjCountTpl
		});
		this.tplZxjjgCountCom.on('click',function(event,target){
			//监听数字下钻事件
			if(target.nodeName == "A"){
				if("zxjyx_yx" == target.name){
					this.pageParams.xszt = '1';
					this.grid.load(this.pageParams);
				}
				if("zxjyx_tm" == target.name){
					this.pageParams.xszt = '2';
					this.grid.load(this.pageParams);
				}
				if("zxjyx_tg" == target.name){
					this.pageParams.xszt = '3';
					this.grid.load(this.pageParams);
				}
				if("zxjyx_th" == target.name){
					this.pageParams.xszt = '9';
					this.grid.load(this.pageParams);
				}
				if("zxjyx_wtg" == target.name){
					this.pageParams.xszt = '5';
					this.grid.load(this.pageParams);
				}
				if("zxjyx_wyx" == target.name){
					this.pageParams.xszt = '0';
					this.grid.load(this.pageParams);
				}
			}
		},this);
		//监听输入框回车事件
		this.tplZxjjgCountCom.on('keydown',function(event,target){
			if(event.getKey() == 13){
				var queryStr = document.getElementById('zxjyx_queryStr').value;
				this.pageParams.queryStr = queryStr;
				this.grid.load(this.pageParams);
			}
		},this);
		return this.tplZxjjgCountCom;
	},
	/**
	 * 重写初始化grid
	 */
	getGrid : function(data){
		var me = this;
		//表头数据转换
		this.tranData = NS.E2S(data.queryTableHeader);
		//表格数据
		this.tableData = data.queryTableData;
		var basic = {
				plugins : [new NS.grid.plugin.HeaderQuery()],//表头查询
				columnData : me.tranData,
				fields:['zt','flag'],
				modelConfig : {
					data : me.tableData
				},				
	            columnConfig : [
		                     {
							    text: '历史获奖',
							    name : 'lsjl',
							    width : 100,
							    xtype : 'linkcolumn',
							    renderer : function(value, rowData){
							    	return '奖<a href="javascript:void(0);" class="zxj-yuxuan_orange" style="color: #FF6600;" name="jxj">'+rowData.jxj+
							    	'</a>助<a href="javascript:void(0);" class="zxj-yuxuan_orange" style="color: #FF6600;" name="zxj" >'+rowData.zxj+
							    	'</a>减<a href="javascript:void(0);" class="zxj-yuxuan_orange" style="color: #FF6600;" name="jm">'
							    	+rowData.jm+'</a>';
							    }
							  },
	    						{
	    						    text: '学生状态',
	    						    name : 'zt',
	    						    align : 'center',
	    						    width:58,
	    						    renderer : function(value){  
	    						      if(value==null || value==0){
	    						    	  return '<span class="zxj-yuxuan_blue">未预选</span>';	
	    						      }
	    						      if(value==1){
	    						    	  return '<span class="zxj-yuxuan_green">已预选</span>';	
	    						      }	  
	    						      if(value==2){
	    						    	  return '<span class="zxj-yuxuan_orange">已提名</span>';	
	    						      }	 
	    						      if(value==3){
	    						    	  return '<span class="zxj-yuxuan_green">已通过</span>';	
	    						      }	 
	    						      if(value==5){
	    						    	  return '<span class="zxj-yuxuan_grey">未通过</span>';	
	    						      }	 
	    						      if(value==9){
	    						    	  return '<span class="zxj-yuxuan_red">退回</span>';	
	    						      }	
	    						    }
	    						  },
	    						{
	    						    text: '操作',
	    						    name : 'flag',
	    						    xtype : 'linkcolumn',
	    						    align : 'center',
	    						    width:130,
	    						    renderer : function(value){   
    						    	 if(value==-1 ){
	    						    	  return '--';	
	    						      }
    							      if(value==0 ){
	    						    	  return '<a  style="color:#009900;"href="javascript:void(0);" id="zxjyx_yx">预选</a>';	
	    						      }
	    						      if(value==1){
	    						    	  return '<a  style="color:#FF6600;" href="javascript:void(0);" id="zxjyx_qxyx">取消预选</a>&nbsp;&nbsp;<a style="color:#009900;" href="javascript:void(0);" id="zxjyx_tm">提名</a>';	
	    						      }	  
	    						      if(value==2){
	    						    	  return '<a  style="color:#009900;" href="javascript:void(0);" id="zxjyx_xq">详情</a>';	
	    						      }	 
	    						      if(value==3){
	    						    	  return '<a style="color:#009900;" href="javascript:void(0);" id="zxjyx_xq">详情</a>';	
	    						      }	
	    						      if(value==5){
	    						    	  return '<a  style="color:#009900;" href="javascript:void(0);" id="zxjyx_xq">详情</a>';	
	    						      }	
	    						      if(value==9){
	    						    	  return '&nbsp;&nbsp;<a  style="color:#009900;"  href="javascript:void(0);" id="zxjyx_tm">提名</a>';	
	    						      }	 
	    						    }
	    						 }
	    	               ],
				serviceKey : {
                    key:'queryTableData',
                    params:this.pageParams
                },
				pageSize : 25,
				proxy : this.model,
				autoScroll : true,
				multiSelect : true,
//				lineNumber : true,
                checked:true,
				border : true
			};
			this.grid = new NS.grid.SimpleGrid(basic);
			this.grid.bindItemsEvent({
				'flag' : {event : 'linkclick',fn:this.getLinkClickData,scope:this},
				'lsjl' : {event : 'linkclick',scope : this,fn : function(txt,rowIndex,colIndex,data,htmlElement){
	                this.doPopDetails("",txt,rowIndex,colIndex,data,$(htmlElement));
	            }},
			});
			return this.grid;
	},
	
	/**
	 * 预选学生
	 */
	addZxjYx : function(xsId){
		this.callService({
			key : 'addZxjYx',
			params : {pclxId:this.pageParams.pclxId,xsId:xsId,lsmk:this.lsmk}
		},function(data){
			if(data.addZxjYx.success){		
				this.refreshDataByPclx();
				NS.Msg.info('预选成功');
			}else{
				NS.Msg.info('预选失败');
			}
		});
	},
	/**
	 * 取消助学金预选
	 */
	deleteZxjYx : function(zxjId){
		this.callService({
			key : 'deleteZxjYx',
			params : {yxId:zxjId.toString(),lsmk:this.lsmk}
		},function(data){
			if(data.deleteZxjYx.success){
				NS.Msg.info('取消预选成功');
				this.refreshDataByPclx();
			}else{
				NS.Msg.info('取消预选失败');
			}
		});
	},
	/**
	 * 助学金提名
	 */
	zxjYxTm : function(pcId,lxId,zxjId,xsId){
		this.callService({
			key : 'zxjYxTm',
			params : {pcId:pcId,lxId:lxId,zxjId:zxjId,lylx:"ZXJYX",xsId:xsId,popType:'yxtm',lsmk:this.lsmk}
		},function(data){
			if(data.zxjYxTm.success){
				NS.Msg.info('提名成功');
				this.refreshDataByPclx();
			}else{
				NS.Msg.info(data.zxjYxTm.info);
			}
		});
	},
	/**
	 * 获取学生助学金详情
	 */
	queryXsZxjxx : function(linkValue,recordIndex,cellIndex,data,eOpts){
		this.createXqWin(data.xsid,this.pclxId,data.id);
	},
	/**
	 * 监听操作栏事件
	 */
	getLinkClickData : function(linkValue,recordIndex,cellIndex,data,eOpts){
		if("预选" == linkValue){
		  this.addZxjYx(data.xsid);		
		}
		if("取消预选" == linkValue){
			this.deleteZxjYx(data.id);
		}
		if("提名" == linkValue){
			this.initEditWin(data.xsid,this.pclxId,data.id);		
		}
		if("详情" == linkValue){
           this.queryXsZxjxx(linkValue, recordIndex, cellIndex, data, eOpts);
		}
	},
	/**
	 * 重写刷新数据
	 */
	refreshDataByPclx : function(){
		this.pageParams.xszt="";
		this.pageParams.pcId = this.pcId;
		this.pageParams.lxId = this.lxId;
		this.pageParams.pclxId = this.pclxId;
		this.callService({key:'queryZxjjgCount',params:this.pageParams}
		        		  ,function(data){   		        			 
		    	this.tplZxjjgCountCom.refreshTplData(data.queryZxjjgCount);		
		});
		 this.grid.load(this.pageParams);	
    }
});