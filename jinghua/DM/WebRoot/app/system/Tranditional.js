var treeData ={
    id : 0,
    pid : -1,
    text : '测试系统',
    children : [
        {
            id : 1,
            text : '权限系统',
            pid : 0,
            children: [
                {
                    id : 2,
                    text : '菜单管理',
                    pid : 1,
                    leaf : true,
                    url : 'Pages.permiss.MenuManager'
                },
                {
                    id : 3,
                    text : '角色管理',
                    pid : 1,
                    leaf : true,
                    url : 'Pages.permiss.RoleManager'
                },
                {
                    id : 4,
                    text : '用户管理',
                    pid : 1,
                    leaf : true,
                    url : 'Pages.permiss.UserManager'
                },
                {
                    id : 5,
                    text : '用户组管理',
                    pid : 1,
                    leaf : true,
                    url : 'Pages.permiss.UserGroupManager'
                },
                {
                    id : 6,
                    text : '我所管理的用户组',
                    pid : 1,
                    leaf : true,
                    url : 'Pages.permiss.GroupsManagerByMe'
                }
            ]
        }
    ]
}
NS.define('System.Tranditional',{
    extend : 'NS.mvc.Controller',
    requires : ['System.data.MenuHeaderModel','System.data.TreeMenuModel'
                 ,'System.data.UserInfo','System.fun.Password','System.fun.Announcement','System.fun.PushTip','System.fun.BackLog'],
    tplRequires : [
        {fieldname : 'mainPageLayoutTpl',path : 'app/system/fun/tpl/indexLayout.html'}
    ],
    cssRequires : ['app/system/fun/css/list-style.css','css/index-style.css'],
    mixins : [
              	'System.minx.Utils','System.minx.TaskScheduler'
            ],
      modelConfig : {
          serviceConfig : {
              'treeMenu' : {
                  service : "permiss?queryUserMenuTree",
                  params : {}
              },
              'userInfo' : 'permiss?queryUser',
              'xnxq' : 'base_queryXnxqId',
              'updatePassword' : 'userManager?updateUserPassWord',//修改密码
              /*******任务调度Service*****/
              'ajaxPolling' : {
                  service : 'message_queryMessages'
              },
              top3:'indexShowDataService?getTop3',
              last:'indexShowDataService?getLastAndTry',
              bookReak:'indexShowDataService?getTodayBookReak',
              bookRKE:'indexShowDataService?getTodayBookRKE',
              CardNum:'indexShowDataService?getTodayCardNum',
              CardMoney:'indexShowDataService?getTodayCardMoney'
          }
    },
    pages : {},//页面承载容器
    constructor : function(){
        this.callParent(arguments);
    },
    init : function(){
      this.prepare();//准备工作
      this.addEvents('dataload');
      this.on('dataload',this.initComponent,this);
      this.initData();
    },
    initPage1 : function(){},
    /**
     * 准备工作
     */
    prepare : function(){
      this.msgEvent = new System.fun.PushTip();
    },
    /**
     * 初始化数据
     */
    initData : function(){
        this.callService(['treeMenu','userInfo','xnxq'],function(data){
//             data.treeMenu = treeData;
             this.treeData = data.treeMenu;
             this.userInfo = data.userInfo;
             this.xnxq = data.xnxq;//学年学期
             this.processData(data);//初始化树数据
             this.initComponent(this.getMenuData());
        });
    },
    processData : function(){
        var me = this;
      this.menuModel = new System.data.TreeMenuModel(NS.clone(this.treeData));
      this.userInfo = new System.data.UserInfo(this.userInfo);
        /*************其他数据******/
      this.getMenuData = function(){return NS.clone(me.treeData);};
       /********通过id获取节点**********/
      this.getNodeById = NS.Function.alias(this.menuModel,'getNodeById');
      this.getNodeByClassName = NS.Function.alias(this.menuModel,'getNodeByClassName');
      this.getUserName = NS.Function.alias(this.userInfo,'getUserName');//获取用户名
      this.getLoginName = NS.Function.alias(this.userInfo,'getLoginName');//获取登录名
      this.getBmxx = NS.Function.alias(this.userInfo,'getBmxx');//获取部门信息
      this.getBjxx = NS.Function.alias(this.userInfo,'getBjxx');//获取班级信息
      this.getZgId = NS.Function.alias(this.userInfo,'getZgId');//或者职工id
      this.getXsId = NS.Function.alias(this.userInfo,'getXsId');//获取学生id
      this.getJxzzjgQx = NS.Function.alias(this.userInfo,'getJxzzjgQx');//获取教学组织结构权限
      this.getXzzzjgQx = NS.Function.alias(this.userInfo,'getXzzzjgQx');//获取行政组织结构权限
      this.getRoleIds = NS.Function.alias(this.userInfo,'getRoleIds');//获取角色Id集合

      this.getXnId = function(){return me.xnxq.xnId;};//获取当前学年id
      this.getXqId = function(){return me.xnxq.xqId;};//获取当前学期id
      this.getXnMc = function(){return me.xnxq.xnMc;};//获取当前学年名称
      this.getXqMc = function(){return me.xnxq.xqMc;};//获取当前学期名称
      this.getXnDm = function(){return me.xnxq.xnDm};//获取当前学年代码
      this.getXqDm = function(){return me.xnxq.xqDm};//获取当前学期代码

    },

    /**
     * 初始化组件
     */
    initComponent : function(data){
        this.initNorth();
        this.initWest(data);
        this.initCenter();
        this.initSystemPage();//初始化系统页面
//        this.startTask();
        var body = NS.getBody();
        //console.log(body.getWidth()+"-"+body.getHeight());

    },
    /**
     * 初始化界面视图
     */
    initSystemPage : function(){
        var component = Ext.create('Ext.container.Viewport', {
            layout : 'border',
            padding : 5,
            items : [this.north, this.west, this.center]
        });
        this.setPageComponent(component);
    },
    /**
     * 初始化北部容器
     */
    initNorth : function(){
        var div ='<div class="box"><div class="top"><div class="logo"><iframe id="system_for_download" height="0" width="0" src=""></iframe> </div>'
            +'<div class="right"><table  class="right-top" border="0" cellpadding="0" cellspacing="0">'
            +'<tr>'
            +'<td><img src="css/images/ys1.png" align="absmiddle" /></td><td><a href="#" onclick="return false;" onmouseover="MainPage.personalSetting(event,this)">个人设置</a></td>'
            +'<td><img src="css/images/ys2.png" align="absmiddle"/></td><td><a href="logout.action">退出登录</a></td>'
            +'<td><img src="css/images/ys4.png" align="absmiddle" /></td><td><a href="">帮助</a> </td>'
            +'</tr>'
            +'</table>'
            +'<div class="right-down"> 欢迎您！'+(this.getUserName()||this.getLoginName())+'!<br />'
        '</div></div></div></div>';
        //初始化北部组件
        this.north = Ext.create('Ext.Component',{
            region : 'north',
            layout : 'fit',
            height : '0.1',
            margin : '0 0 5 0',
            listeners : {
                afterrender : function(){
                    this.el.setHTML(div);
                }
            }
        });
    },
    /***
     * 个人设置
     * @param {} event
     * @param {} a
     */
    personalSetting : function(event,a){
        var me = this;
        var rightMenu = this.rightMenu = Ext.create('Ext.menu.Menu', {
            allowOtherMenus : true,
            items : [{
                text : '修改密码',
                iconCls : 'user_change_password',
                handler : function() {
                    var password = new System.fun.Password({autoShow : true,modal : true,controller : me});
                }
            }]
        });
        var ps = Ext.fly(a).getXY();
        rightMenu.showAt(ps[0],ps[1]+20);
    },
    /**
     * 初始化中部容器
     */
    initCenter : function(){
        this.center = Ext.create('Ext.tab.Panel', {
            region : 'center',
            height : '90%',
            activeTab : 0,
            plugins : Ext.create('Ext.ux.TabCloseMenu', {
                closeTabText : '关闭标签页',
                closeOthersTabsText : '关闭其他标签页',
                closeAllTabsText : '关闭所有标签页'
            }),
            border : 1,
            autoWidth : true,
            autoDestroy : true,
            layout : 'fit',
            suspendLayout : true,
            hideMode : 'visibility'
        });
        this.tabMask = new Ext.LoadMask(this.center, {
            msg : "页面加载中,请稍等......"
        });
        this.pageMap = {};
        this.initMainLayout();//初始化主页面布局
//        this.initAnnouncement();//初始化公告页
//        this.initBackLog();//初始化代办事宜
    },
    /**
     * 初始化主页面布局
     */
    
    baseBody: new Ext.Component({
    	tpl:"<div class='index-body' id='base_body'></div>",
    	data:{}
    }),
    bodyHtml:"<div class='index-body'><div class='index-title'> <h3 class='index-h3 '>7日内</h3></div> "+
    "<div class='index-show-content'><ul> "+
    "    <li id='top1'>  <h1>被借图书前三名</h1> <div  class='loading-indicator'>正在加载....</div> </li>"+
    "    <li id='top2'>  <h1>出入图书馆次数前三名学生</h1> <div  class='loading-indicator'>正在加载....</div> </li>"+
    "    <li id='top3'>  <h1>借书前三名学生</h1> <div  class='loading-indicator'>正在加载....</div> </li>"+
    " </ul></div>"+
    "<div class='index-box'><div class='index-img'><div class='index-bg01'></div>"+
    "<div class='index-bg02'></div><div class='index-bg03'></div><div class='index-bg04'></div></div>"+
    " <div class='index-list'><ul>"+
 	" <li id='lastDay'><div class='index-title'><h3 class='index-h3' >昨天</h3></div>"+
    "     <div  class='loading-indicator'>正在加载....</div>"+
    "</li>"+
    "<li id='tryDay'> <div class='index-title'><h3 class='index-h3 index-h3-top' >预计<br /> 今天</h3></div>"+
    "     <div  class='loading-indicator'>正在加载....</div>" +
    "</li>"+
    " <li><div class='index-title'><h3 class='index-h3' >当前</h3></div>"+
	    "<div class='index-list-content' id='t_read'>"+
	    "<div  class='loading-indicator'>正在加载....</div></div>" +
	    "<div class='index-list-content' id='t_rke'>"+
	    "<div  class='loading-indicator'>正在加载....</div></div>"+
	    "<div class='index-list-content' id='t_num'>"+
	    "<div  class='loading-indicator'>正在加载....</div></div>"+
	    "<div class='index-list-content index-list-solid' id='t_money'>"+
	    "<div  class='loading-indicator'>正在加载....</div></div>"+
    "</li>"+
    " </ul></div></div></div>",
    initMainLayout : function(){
        var component = new NS.container.Container({
            html:this.bodyHtml,
            autoScroll:true
        });
        component.getLibComponent().title = "主页";
        var tab = this.center.add(component.getLibComponent());
        this.center.setActiveTab(tab);
        this._selectData();
    },
    _subStr: function (str,num){
    	if(str.length>num){
    		return (str.substr(0,num))+"...";
    	}else {
    		return str;
    	}
    	
    },
    _selectData:function(){
    	var me=this;
    	this.callService({key:'top3',params:{}},function(data){
            var tt = data.top3;
            if(tt.length==0){
            	html="<span style='color : red;'>没有数据</span>";
                Ext.get("top1").dom.innerHTML=" <h1>被借图书前三名</h1> "+html;
    	        Ext.get("top2").dom.innerHTML=" <h1>出入图书馆次数前三名学生</h1> "+html;
    	        Ext.get("top3").dom.innerHTML=" <h1>借书前三名学生</h1> "+html;
            }else{
            	html=" <p class='index-color01'><tushu title='"+tt[0].MC+"'>《"+me._subStr(tt[0].MC,10)+"》</tushu><ci>"+tt[0].SL+"人次</ci></p>"+
            	"<p class='index-color02'><tushu title='"+tt[1].MC+"'>《"+me._subStr(tt[1].MC,10)+"》</tushu><ci>"+tt[1].SL+"人次</ci></p>"+
                "<p class='index-color03'><tushu title='"+tt[2].MC+"'>《"+me._subStr(tt[2].MC,10)+"》</tushu><ci>"+tt[2].SL+"人次</ci></p>";
                
                Ext.get("top1").dom.innerHTML=" <h1>被借图书前三名</h1> "+html;
                
                html=" <p class='index-color01'><ren>"+tt[3].MC+"</ren><yuan title='"+tt[3].YX+"'>"+me._subStr(tt[3].YX,8)+"</yuan><ci>"+tt[3].SL+"次</ci></p>"+
                "<p class='index-color02'><ren>"+tt[4].MC+"</ren><yuan title='"+tt[4].YX+"'>"+me._subStr(tt[4].YX,8)+"</yuan><ci>"+tt[4].SL+"次</ci></p>"+
            	"<p class='index-color03'><ren>"+tt[5].MC+"</ren><yuan title='"+tt[5].YX+"'>"+me._subStr(tt[5].YX,8)+"</yuan><ci>"+tt[5].SL+"次</ci></p>";
                
    	        Ext.get("top2").dom.innerHTML=" <h1>出入图书馆次数前三名学生</h1> "+html;
    	        
    	        html=" <p class='index-color01'><ren>"+tt[6].MC+"</ren><yuan title='"+tt[6].YX+"'>"+me._subStr(tt[6].YX,8)+"</yuan><ci>"+tt[6].SL+"本</ci></p>"+
    	        "<p class='index-color02'><ren>"+tt[7].MC+"</ren><yuan title='"+tt[7].YX+"'>"+me._subStr(tt[7].YX,8)+"</yuan><ci>"+tt[7].SL+"本</ci></p>"+
    	    	"<p class='index-color03'><ren>"+tt[8].MC+"</ren><yuan title='"+tt[8].YX+"'>"+me._subStr(tt[8].YX,8)+"</yuan><ci>"+tt[8].SL+"本</ci></p>";
    	        
    	        Ext.get("top3").dom.innerHTML=" <h1>借书前三名学生</h1> "+html;
            }
            
        },this);
    	
    	this.callService({key:'last',params:{}},function(data){
            var tt = data.last;
            var html="";
            if(tt.length==0){
            	html="<span style='color : red;'>没有数据</span>";
            	Ext.get("lastDay").dom.innerHTML="<div class='index-title'><h3 class='index-h3' >昨天</h3> </div>"+html;
            	Ext.get("tryDay").dom.innerHTML="<div class='index-title'><h3 class='index-h3 index-h3-top' >预计<br /> 今天</h3></div>"+html;
            }else{
	            for(var i=0;i<5;i=i+4){
	            	html="<div class='index-list-content'>"+
	        	    "<h2>图书馆进出：<span class='index-color-ren'>"+tt[i+0].SUMS+"</span>人</h2>"+
	        	    "<p>峰值：<span class='index-color-fengzhi'>"+tt[i+0].PEAK+"</span>人，峰时："+tt[i+0].TIME+"</p></div>" +
	        	    "<div class='index-list-content'>"+
	        	    "<h2>图书借出：<span class='index-color-ce'>"+tt[i+1].SUMS+"</span>册</h2>"+
	        	    "<p>峰值：<span class='index-color-fengzhi'>"+tt[i+1].PEAK+"</span>册，峰时："+tt[i+1].TIME+"</p>"+
	        	    "</div>"+
	        	    "<div class='index-list-content'>"+
	        	    "<h2>一卡通交易：<span class='index-color-bi'>"+tt[i+2].SUMS+"</span>笔</h2>"+
	        	    "<p>峰值：<span class='index-color-fengzhi'>"+tt[i+2].PEAK+"</span>笔，峰时："+tt[i+2].TIME+"</p>"+
	        	    "</div>"+
	        	    "<div class='index-list-content index-list-solid'>"+
	        	    "<h2>一卡通交易：<span class='index-color-yuan'>"+tt[i+3].SUMS+"</span>元</h2>"+
	        	    "<p>峰值：<span class='index-color-fengzhi'>"+tt[i+3].PEAK+"</span>元，峰时："+tt[i+3].TIME+"</p>"+
	        	    "</div>";
	            	if(i==0){
	            		Ext.get("lastDay").dom.innerHTML="<div class='index-title'><h3 class='index-h3' >昨天</h3> </div>"+html;
	            	}else{
	            		Ext.get("tryDay").dom.innerHTML="<div class='index-title'><h3 class='index-h3 index-h3-top' >预计<br /> 今天</h3></div>"+html;
	            	}
	            }
            }
        },this);
        
    	this.callService({key:'bookRKE',params:{}},function(data){
            var tt = data.bookRKE;
            var html="",html1 ="";
            if(tt.length==0){
            	html="<span style='color : red;'>没有数据</span>";
            }else{
            	html1 = tt[0].SUMS!=0?"<h2 id='todayCssH2'>图书馆进出：<span class='index-color-ren'>"+tt[0].SUMS+"</span>人</h2>":
            	"<h2 id='todayCssH2' style='color:red'>门禁数据未上传</h2>";
            	html=html1+
        	    "<p id='todayCssP'>峰值：<span class='index-color-fengzhi'>"+tt[0].PEAK+"</span>人，峰时："+tt[0].PEAKTIME+"</p>"+
        	    "<p id='todayCssP'>统计时间"+tt[0].ADDTIME+"</p>";
            }
            Ext.get("t_read").dom.innerHTML=html;
        },this);
    	
    	this.callService({key:'bookReak',params:{}},function(data){
            var tt = data.bookReak;
            var html="";
            if(tt.length==0){
            	html="<span style='color : red;'>没有数据</span>";
            }else{
            	html="<h2 id='todayCssH2'>图书借出：<span class='index-color-ce'>"+tt[0].SUMS+"</span>册</h2>"+
        	    "<p id='todayCssP'>峰值：<span class='index-color-fengzhi'>"+tt[0].PEAK+"</span>册，峰时："+tt[0].PEAKTIME+"</p>"+
        	    "<p id='todayCssP'>统计时间"+tt[0].ADDTIME+"</p>";
            }
            Ext.get("t_rke").dom.innerHTML=html;
        },this);
    	
    	this.callService({key:'CardNum',params:{}},function(data){
            var tt = data.CardNum;
            var html="";
            if(tt.length==0){
            	html="<span style='color : red;'>没有数据</span>";
            }else{
            	html="<h2 id='todayCssH2'>一卡通交易：<span class='index-color-bi'>"+tt[0].SUMS+"</span>笔</h2>"+
        	    "<p id='todayCssP'>峰值：<span class='index-color-fengzhi'>"+tt[0].PEAK+"</span>笔，峰时："+tt[0].PEAKTIME+"</p>"+
        	    "<p id='todayCssP'>统计时间"+tt[0].ADDTIME+"</p>";
            }
            Ext.get("t_num").dom.innerHTML=html;
        },this);
    	
    	this.callService({key:'CardMoney',params:{}},function(data){
            var tt = data.CardMoney;
            var html="";
            if(tt.length==0){
            	html="<span style='color : red;'>没有数据</span>";
            }else{
            	html="<h2 id='todayCssH2'>一卡通交易：<span class='index-color-yuan'>"+tt[0].SUMS+"</span>元</h2>"+
        	    "<p id='todayCssP'>峰值：<span class='index-color-fengzhi'>"+tt[0].PEAK+"</span>元，峰时："+tt[0].PEAKTIME+"</p>"+
        	    "<p id='todayCssP'>统计时间"+tt[0].ADDTIME+"</p>";
            }
            Ext.get("t_money").dom.innerHTML=html;
        },this);
    	
    },
    
    
    
    /**
     * 初始化公告页
     */
    initAnnouncement : function(){
        var center = this.center;
        var at = new System.fun.Announcement(function(com){
            com.render('page_index_announcement');
        });
    },
    initBackLog : function(){
       var bk = new System.fun.BackLog(function(com){
           com.render('page_index_backlog');
       });
    },
    /**
     * 初始化西部容器
     */
    initWest : function(data){
        this.west = new Ext.panel.Panel({
            region : 'west',
            title : '系统菜单',
            iconCls : 'page-xitong',
            split : true,
            collapsible : true,
            width : 230,
            minWidth : 175,
            maxWidth : 400,
            autoWidth : true,
            layout : 'accordion',
            tbar : [{
                xtype : 'button',
                iconCls : 'page-rss',
                tooltip : 'RSS',
                tooltipType : 'title'
            }, '-', {
                iconCls : 'page-081',
                tooltip : '扩展',
                tooltipType : 'title'
            }, '-', {
                iconCls : 'page-find',
                tooltip : '搜索',
                tooltipType : 'title'
            }, '-', {
                iconCls : 'page-xiuli',
                tooltip : '报修',
                tooltipType : 'title'
            }, '-', {
                iconCls : 'page-help',
                tooltip : '帮助',
                tooltipType : 'title'
            }],
            layoutConfig : {
                hideCollapseTool : false,
                titleCollapse : true,
                activeOnTop : false
            },
            items : this.createMenu(data.children)
        });
    },
    /**
     * 创建menu
     */
    createMenu : function(children) {
        var me = this;
        me.panelList = [];
        var dataArray = children;// nodes.treeMenu.children
        var menus = [];
        var len = dataArray.length;
        for (var i = 0; i < len; i++) {
            var collapsed = true;
            if (i == 0) {
                collapsed = false;
            }
                var currentItem = dataArray[i],
                  menu,store,tree;
                  store = Ext.create('Ext.data.TreeStore', {
                    root : currentItem
                });
                tree = Ext.create('Ext.tree.Panel', {
                    store : store,
                    rootVisible : false,
                    border : false,
                    bodyBorder : false,
                    bodyStyle : {
                        borderWidth : 0
                    },
                    margin : '0 0 0 0',
                    height : '100%',
                    autoHeight : true,
                    draggable : false,
                    lines : false,
                    autoWidth : true,
                    autoScroll : false
                });
                tree.on('itemclick',function(view, record, item, index,e) {
                    var nodeId = record.raw.id,me = this,
                        leaf = record.raw.leaf,
                        classname = NS.String.trim(record.raw.url);
                        if(!classname || leaf != "true"){
                           return;
                        }
                        //classname = 'Pages.jw.test.TestModel';//NS.String.trim(record.raw.url);
                        //classname = "Template.Basic";
                    //classname = "Pages.pj.pjsz.ParamSet2";
                    //classname = "Pages.jw.cjgl.Cjgl";
                    var node = this.getNodeById(nodeId);
                    this.addPage(node,classname);
                },this);
                menu = {
                    id : currentItem.mcpyszm || currentItem.text,
                    title : currentItem.text,
                    xtype : 'panel',
                    border : false,
                    layout : 'fit',
                    collapsed : collapsed,
                    items : tree,
                    iconCls : 'page-' + (currentItem.mcpyszm || currentItem.text)
                }
                menus.push(menu);
        };
        return menus;
    },
    setCurrentTabParams : function(proxyUserId,proxyMenuId){
        Ext.Ajax.on('beforerequest',function(conn,object){
           if(!object.params)object.params = {};
           if(proxyUserId){
               object.params.proxyUserId = proxyUserId;
           }
           if(proxyMenuId){
               object.params.proxyMenuId = proxyMenuId;
           }
        });
        //设定全局当前请求菜单ID，用户ID
        MainPage.proxyMenuId = proxyMenuId;
        MainPage.proxyUserId = proxyUserId;
    },
    /************************动态类加载，以及动态类创建*******************************/
    /**
     * 在tab页上添加新页面
     * @param node 点击的节点数据
     */
    addPage : function(node,classname,classInitCfg){
        if(!classname || classname.length==0 || classname == "null")return;
    	this.setCurrentTabParams(node.proxyUserId,node.proxyMenuId||node.id);
        var me = this,CM = NS.ClassManager;
        if(this.pages[classname]){//针对输出框架的特殊处理
           if(this.pages[classname].node.id == node.id){
               this.center.setActiveTab(this.pages[classname]);
               return;
           }else{
               delete this.center.remove(this.pages[classname]);
               delete this.pages[classname];
           }
        }
        if(classname == "Output.Controller"){//针对输出框架的特殊处理
           if(this.pages[classname+node.id]){
               this.center.setActiveTab(this.pages[classname+node.id]);
               return;
           }
        }
//        if(classname != "Output.Controller"){
//
//
        var cls = CM.get(classname);
            if(cls){
                var req = cls.prototype.requires||[];
                req = req.concat(cls.prototype.mixins);
                var extend = cls.prototype.superclass.$classname;
                CM.unregister(extend);
                for(var i=0;i<req.length;i++){
                    CM.unregister(req[i]);
                }
                NS.ClassManager.unregister(classname);
            }
//        }
        if(NS.ClassManager.get(classname)){
            me.createPage(node,classname,classInitCfg);
        }else{
//            (function(node,classname,classInitCfg){
                    NS.load(classname,function(){
                        me.createPage(node,classname,classInitCfg);
                    });
//                })(node,classname,classInitCfg);

        }
    },
    /**
     * 根据类名--创建菜单页面
     * @param {String} classname 类名
     */
    createPage : function(node,classname,classInitCfg){
        var pageMark = classname;
        if(classname == "Output.Controller"){
           pageMark = pageMark+node.id;
        }
        var container = new Ext.container.Container({
            width : '100%',
            height : '100%',
            closable : true,
            pagename : pageMark,
            title : node.text,
            node : node,
            layout : 'fit',
            listeners : {
                destroy : {
                    scope : this,
                    fn : function(c){
                        delete this.pages[c.pagename];
                    }
                },
                activate : {
                    scope : this,
                    fn : function(component){
                        var node = component.node;
                        this.setCurrentTabParams(node.proxyUserId,node.proxyMenuId||node.id);
                    }
                }
            }
        });
        this.pages[pageMark] = container;//页面标识
        /***添加遮罩*/
        this.tabMask.show();
//        Ext.defer(function(){
//            myMask.hide();
//        },1000);
        var clsInt = classInitCfg||{};
        clsInt.CD_NODE = node;
        var component = NS.create(classname,clsInt);
        component.ownerContainer = container;
//        this.pageMap[nodeId] = component;
        if(component.pageReady){//如果页面准备就绪
           container.add(component.getLibComponent());
           //this.doSetButtonQx(component,node);
           this.center.add(container);
           this.tabMask.hide();
           this.center.setActiveTab(container);
        }else{
            component.on('pageready',function(){
                var c = component.getLibComponent();
                //this.doSetButtonQx(component,node);
                container.add(c);
                this.center.add(container);
                this.tabMask.hide();
                this.center.setActiveTab(container);
            },this);
        }
    },
    /**
     * 设置按钮权限
     * @param obj
     * @param id
     * @param panel
     */
    doSetButtonQx : function(component,node) {
        var json =  eval("("+node['permiss']+")").buttons;
        if(component.getLibComponent().query){
           var buttons = component.getLibComponent().query('button');
        }else{
            return;
        }

        var array = [];
        Ext.each(buttons, function(button) {
            if (button['buttonType']) {
                array.push(button);
                button.setDisabled(true);
            }
        });
        if(json !== undefined){
            for ( var i = 0; i < array.length; i++) {
                var button = array[i];// button
                var qx;// 权限信息
                for ( var j = 0; j < json.length; j++) {
                    qx = json[j];
                    if (button['buttonType'] == qx.type) {
                        button.setDisabled(qx.disable);
                        break;
                    }
                }
            }
        }
    }
});