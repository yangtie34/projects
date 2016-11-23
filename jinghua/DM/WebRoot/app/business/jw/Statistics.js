/**
 * 定义BasePage类。
 * 创建组件时，都一致采用先渲染框架，再加载数据的方式。
 * 
 * 该类的功能：
 * 1.创建一个页面的通用基础框架
 * 2.定义一些常用的工具类
 * 3.配置一些常用的服务
 */
NS.define('Business.jw.Statistics', {
	/******************************** 属性声明 ***************************************/
	extend: 'Business.jw.BasePage', // 继承父类
	
	/* 模块、实体配置 */
	modelName: '统计',
	entityName: null, // 实体名,
	
	/* 页面布局结构ID及CSS记法：#ID，均在在页面初试化的时候调用initPageData方法时，由NS.id()自动生成 */
	statisticsPathComponent: null, // 统计路径组件
	statisticsPathContainerId: null, // 统计路径容器ID
	sidebarComponent: null, // 侧边栏组件
	sidebarContainerId: null, // 侧边栏组件容器ID
	
	/* 缓存数据 */
	currentPageMainGridSearchId: null, // 当前页面主表格查询栏的查询ID
	
	/* Box属性设置 */
	boxTitles: { // Box标题
		mainGrid: '统计路径',
		detail: '详情'
	},
	
	boxTips: { // Box提示
		mainGrid: '点击“单位”的名字，可以下钻到该单位的下一级单位。'
	},

  	/****************************** 方法声明 **********************************/
	
    /**
     * 添加通用CSS文件（需要重载）。
     * 参数是单个文件名或者是包含文件名的一维数组，且参数可变。
     * @Override Business.jw.BasePage
     */
    addCommonCssFiles: function() {
    	this.callParent(['app/business/jw/template/css/statistics.css']);
    },
    
    /**
     * 初始化页面布局配置数据，包括：容器ID的自动生成，ID的CSS记法，用来简化jquery操作
     * @Override Business.jw.BasePage
     */
    initPageLayoutConfigData: function() {
    	this.callParent();
    	
    	this.statisticsPathContainerId = this.pageContainerId + '-statistics-path';
    	this.sidebarContainerId = this.pageContainerId + '-sidebar';
    },
    
    /**
     * 创建页面主题TPL的html
     * @return {string} 返回创建的html
     * @Override Business.jw.BasePage
     */
    createPageMainTplHtml: function() {
		var html = '' +
		'<div class="main-grid box float-left">' +
			'<div class="inner">' +
				'<div class="header"><h3>' + this.boxTitles.mainGrid + '</h3> <div id="' + this.statisticsPathContainerId + '" class="path float-left"></div></div>' +
				'<div class="search round-corner" id="' + this.pageMainGridSearchContainerId + '"></div>' +
				'<div class="main" id="' + this.pageMainGridContainerId + '"></div>' +
				'<div class="tips round-corner footer"><h4>说明：</h4>' + this.boxTips.mainGrid + '<ins></ins></div>' +
			'</div>' +
		'</div>' +
		'<div class="sidebar float-left" id="' + this.sidebarContainerId + '">' +
		'</div>';

		return html;
    },
    
   	/**
     * 创建并渲染页面主题内容（可根据需要继承重写）
     * @Override Business.jw.BasePage
     */
    createPageMainContentAndRender: function () {
    	this.callParent();
    	
    	// 创建统计路径组件，渲染它，初始化，绑定事件
    	this.createStatisticsPathComponentAndRenderAndInitAndBindEvents();
    	
    	// 创建查询栏组件，渲染它，初始化，绑定事件
    	this.createPageMainGridSearchComponentAndRenderAndInitAndBindEvents();
    	
    	// 创建查详情组件，渲染它，初始化，绑定事件
    	this.createSidebarComponentComponentAndRenderAndInitAndBindEvents();
    },
    
    /**
     * 创建统计路径组件，渲染它，初始化统计路径数据，给它绑定事件
     * 统计路径数据，格式：[{id: -1, mc: '首页'}, {id:1, mc:'xxx'}, ... , {id:n, mc:'xxx'}]，初始化值为：[{id: 0, mc: '首页'}]
     */
    createStatisticsPathComponentAndRenderAndInitAndBindEvents: function() {
    	/* 创建统计路径组件 */
    	var initData = [{id: 0, mc: '首页'}];
    	this.statisticsPathComponent = new NS.Component({
    		tpl: this.createStatisticsPathTpl(),
    		data: initData,
    		renderTo: this.statisticsPathContainerId
    	});
    	
    	/* 初始化统计路径数据，存储到统计路径组件属性pathData中，格式：[{id: -1, mc: '首页'}, {id:1, mc:'xxx'}, ... , {id:n, mc:'xxx'}] */
    	this.statisticsPathComponent.pathData = initData;

    	/* 绑定事件 */
    	this.bindStatisticsPathComponentEvents();
    },
    
    /**
     * 创建统计路径TPL
     */
    createStatisticsPathTpl: function() {
    	return new NS.Template('<tpl for="."><a id="{id}" index={[xindex - 1]} href="javascript:void(0);">{mc}</a> <ins class="bar">&gt;&gt;</ins> </tpl>');
    },
    
    /**
     * 创建查询栏组件，渲染它，初始化，绑定事件
     */
    createPageMainGridSearchComponentAndRenderAndInitAndBindEvents: function() {
    	/* 创建组件 */
    	this.pageMainGridSearchComponent = new NS.Component({
    		tpl: new NS.Template(this.createPageMainGridSearchTplHtml()),
    		data: [],
    		renderTo: this.pageMainGridSearchContainerId
    	});
    	
    	/* 创建组件内容 */
    	this.createPageMainGridSearchContentAndRender();
    	
    	/* 初始化数据 */
    	this.initPageMainGridSearchData();
    	
    	/* 定义事件 */
    	this.bindPageMainGridSearchComponentEvents();
    },
    
    /**
     * 创建查询栏组件TPL的HTML
     * @return {}
     */
    createPageMainGridSearchTplHtml: function() {
    	return '';
    },
    
    /**
     * 初始化查询栏组件数据
     */
    initPageMainGridSearchData: NS.emptyFn,  
    
    /**
     * 创建查询栏组件内容，并渲染
     */
    createPageMainGridSearchContentAndRender: NS.emptyFn,
    
    /**
     * 创建查详情组件，渲染它，初始化，绑定事件
     */
    createSidebarComponentComponentAndRenderAndInitAndBindEvents: function() {
    	/* 创建组件 */
    	this.sidebarComponent = new NS.Component({
    		tpl: new NS.Template(this.createSidebarTplHtml()),
    		data: [],
    		renderTo: this.sidebarContainerId
    	});
    	
    	/* 创建侧边栏内容 */
    	this.createSidebarContentAndRender();
    	
    	/* 初始化数据 */
    	this.initSidebarData();
    	
    	/* 定义事件 */
    	this.bindSidebarComponentEvents();
    },
    
    /**
     * 创建侧边栏TPLhtml
     */
    createSidebarTplHtml: function() {
    	return '';
    },
    
    /**
     * 创建侧边栏内容并渲染
     * @type 
     */
    createSidebarContentAndRender: NS.emptyFn,
    
    /**
     * 初始化侧边栏数据
     * @type 
     */
   	initSidebarData: NS.emptyFn,
    
    /********************* 事件 ***********************/
    
    bindPageComponentEvents: function() {
    	this.callParent();
    },
    
    /**
     * 绑定页面主表格事件
     * @Override Business.jw.BasePage
     */
    bindPageMainGridEvents: function() {
    	this.callParent();
    	
    	var context = this;
    	this.pageMainGrid.refreshWithSearchConditions = function(params) {
    		var conditions = {};
    		conditions = context.getPageMainGridSearchConditions();
    		NS.apply(conditions, params);
    		this.load(conditions);
    	};
    },
    
    getPageMainGridSearchConditions: NS.emptyFn,
    
    /**
     * 设置统计路径组件事件
     */
    bindStatisticsPathComponentEvents: function() {
    	var context = this;
    	
    	this.statisticsPathComponent.on('click', function(event, elem) {
    		/* 检测事件源是否是A标签 */
    		if (elem.nodeName != 'A') {
    			return;
    		}
    		
    		/* 删除路径 */
    		this.deletePathsAfter($(elem).attr('index'));
    		
    		/* 刷新grid */
    		context.pageMainGrid.refreshWithSearchConditions({id: $(elem).attr('id')});
    	});
    	
    	/* 添加新路径方法 */
    	this.statisticsPathComponent.addPath = function(newPath) {
    		this.pathData.push(newPath);
    		this.refreshPath();
    	};
    	
    	/* 添加新路径方法 */
    	this.statisticsPathComponent.deletePathsAfter = function(index) {
    		try {
	    		if (typeof index == 'string') {
	    			index = parseInt(index);
	    		}
	    		
	    		for (var i = index + 1, length = this.pathData.length; i < length; i++) {
	    			this.pathData.pop();
	    		}
	    		
	    		this.refreshPath();
    		} catch (error) {
    			this.setError(error, {description: '删除统计路径失败！'});
    			this.doArgumentError(error);
    		}
    	};
    	
    	/* 设置刷新路径方法 */
    	this.statisticsPathComponent.refreshPath = function() {
    		this.refreshTplData(this.pathData);
    	};
    	
    	/* 获取当前路径的方法 */
    	this.statisticsPathComponent.getCurrentPath = function(s) {
    		var lastIndex = this.pathData.length - 1;
    		return this.pathData[lastIndex];
    	};
    	
    },
    
    /**
     * 查询栏事件
     */
    bindPageMainGridSearchComponentEvents: NS.emptyFn,
    
    /**
     * 侧边栏事件
     */
    bindSidebarComponentEvents: NS.emptyFn
    
});