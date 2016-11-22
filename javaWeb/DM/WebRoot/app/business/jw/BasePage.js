/**
 * 定义BasePage类。
 * 创建组件时，都一致采用先渲染框架，再加载数据的方式。
 * 
 * 该类的功能：
 * 1.创建一个页面的通用基础框架
 * 2.定义一些常用的工具类
 * 3.配置一些常用的服务
 */
NS.define('Business.jw.BasePage', {
	
	/******************************** 属性声明 ***************************************/
	
	extend: 'Template.Page', // 继承父类
	
	/* 组件、CSS文件 */
	requires: ['Business.component.PageHeader'], // 组件调用，页面头部
	cssRequires : [], // CSS文件，路径格式 'app/xxx/../xxx.css'
	commonCssFiles: ['app/business/jw/template/css/base-page.css'], // 基本、通用CSS
	pageCssFiles: ['app/business/jw/jxjd/template/css/jxjd.css'], // 页面特定CSS
	
	/* 模块、实体配置 */
	modelName: '', // 模块名称
	entityName: null, // 实体名,
	
	/* 页面布局结构组件及其ID和CSS记法(#ID)，均在在页面初试化的时候调用initPageData方法时，由NS.id()自动生成 */
	pageComponent: null, // 页面组件，是顶层组件
	pageLayoutHtml: null, // 页面布局HTML
	pageContainerId: null,	// 页面容器ID
	pageContainerIdCssHandle: null, // 页面容器ID的CSS记法
	pageHeaderContainerId: null, // 页面头部容器ID
	pageHeaderContainerIdCssHandle: null, // 页面主题容器ID的CSS记法
	pageMainContainerId: null, // 页面主题容器ID
	pageMainContainerIdCssHandle: null, // 页面主题容器ID的CSS记法
	pageFooterContainerId: null, // 页面底部容器ID
	pageFooterContainerIdCssHandle: null, // 页面底部容器ID
	pageMainGridContainerId: null, // 页面主表格容器ID
	pageMainGridContainerIdCssHandle: null, // 页面主表格容器ID的CSS记法
	pageMainGrid: null, // 页面主表格组件，在创建页面主题内容时，由createGrid方法创建
	pageMainGridSearchContainerId: null, // 页面主表格查询容器ID
	pageMainGridSearchContainerIdCssHandle: null, // 页面主表格查询容器ID的CSS记法
	pageMainGridSearchComponent: null, // 页面主表格查询容器组件
   	
  	/* 数据库操作错误代码 */
   	dbConnectionError: '数据库连接失败！请稍后重试，或者联系系统管理员！', // 数据库连接错误
   	dbSearchError: '数据库查询失败！请稍后重试，或者联系系统管理员！', // 数据库查询错误
   	dbUpdateError: '数据库更新失败！请稍后重试，或者联系系统管理员！', // 数据库处理错误
   
   	/* 操作结果代码 */
   	ok: 0, // 成功
   	failed: 1, // 失败
   	error: 2, // 错误
		
    /** 
     * 模型配置。包括一些页面常用的请求服务。
     * 若要更改请求服务配置，比如：添加请求服务或者修改请求服务，则需要在子类中重写updateServiceConfig()方法，
     * 在该方法中添加或者修改请求服务。updateServiceConfig重写格式如下：
     * updateServiceConfig: function() {
     *	  this.callParent();
     *	  this.model.updateServiceConfig({
	 *		  queryMainGridContentData: 'xxmodel_queryGridData' // 查询主表格内容数据 
     *	  });
   	 * }
     */
	modelConfig: {
		serviceConfig: {
			queryXnXqs: 'listJsrkapXnXq', // 学年学期
			queryJxzzjgs: 'listBm', // 查询教学组织结构
			queryBjs: 'listBj', // 查询班级
			queryMainGridHeaderData: 'base_queryForAddByEntityName', // 查询主表格表头数据 
			queryMainGridContentData: '' // 查询主表格内容数据
        }
    },
    
    /* 数据加载配置 */
    lazyLoad: {
    	queryMainGridContentData: false // 查询主表格内容数据，true表示与表头数据同时加载，false表示在表头数据加载完毕后加载
    },
    
    /****************************** 页面构筑 **********************************/
   
    /**
     * 初始化方法(NS.mvc.Contorller类中执行的,必须要有的方法)
     * @Override NS.mvc.Controller
     */
    init: function () {
    	this.loadCommonCssFiles(); // 第一步：加载通用CSS文件，包括页面基本的样式表。由于样式需要跟服务器交互，属于异步操作，所以应先调用
    	this.loadPageCssFiles(); // 第二步：加载页面特定CSS文件。
    	this.initPageConfigData(); // 第三步：初始化页面配置数据
    	this.createAndReanderPageComponent(); // 第四步：创建并渲染页面组件，但不加载数据
    	this.pageComponent.on('afterrender', this.createPageContentAndRender, this); // 第五步：在页面组件被渲染后，创建并渲染页面内容，包括：页面头部、主题和底部。
    	this.bindPageComponentEvents(); // 第六步：定义页面组件事件
    },
    
    /**
     * 加载通用css文件，包括页面基本的样式表
     */
    loadCommonCssFiles: function() {
    	this.setCommonCssFiles(); // 添加通用CSS文件
    	this.loadCssFiles(this.commonCssFiles); // 加载通用CSS文件
    },
    
    /**
     * 设置通用CSS文件，包括：添加、删除CSS文件
     */
    setCommonCssFiles: function() {
    	this.deleteCommonCssFiles();
    	this.addCommonCssFiles();
    },
    
    /**
     * 删除通用CSS文件（可根据需要继承重写，重写时要利用this.callParent()传参调用）。
     * 参数是单个文件名或者是包含文件名的一维数组，且参数可变。
     */
    deleteCommonCssFiles: function() {
    	for (var i = 0, length1 = arguments.length; i < length1; i++) {
    		var arg = arguments[i];
    		if (!arg) {
    			continue;
    		}
    		if (NS.isArray(arg)) {
    			for (var j = 0, length2 = arg.length; j < length2; j++) {
    				arguments.callee(arg[j]);
    			}
    		} else {
	    		for (var j = 0, length2 = this.commonCssFiles.length; j < length2; j++) {
	    			if (arg == this.commonCssFiles[j]) {
	    				delete this.commonCssFiles[j];
	    			}
	    		}
    		}
    	}
    },
    
    /**
     * 添加通用CSS文件（可根据需要继承重写，重写时要利用this.callParent()传参调用）。
     * 参数是单个文件名或者是包含文件名的一维数组，且参数可变。
     */
    addCommonCssFiles: function() {
    	for (var i = 0, length1 = arguments.length; i < length1; i++) {
    		var arg = arguments[i];
    		if (!arg) {
    			continue;
    		}
    		
    		if (NS.isArray(arg)) {
    			for (var j = 0, length2 = arg.length; j < length2; j++) {
    				arguments.callee(arg[j]);
    			}
    		} else {
    			this.commonCssFiles.push(arg);
    		}    		
    	}
    },
    
    /**
     * 加载当前页特定的css文件
     */
    loadPageCssFiles: function() {
    	this.loadCssFiles(this.pageCssFiles);
    },
    
    /**
     * 初始化页面配置数据，包括：面布局配置数据，请求服务配置数据
     */
    initPageConfigData: function() {
    	this.initPageLayoutConfigData(); // 页面布局
    	this.updateServiceConfig(); // 修改请求服务配置
   	},
    
    /**
     * 初始化页面布局配置数据，包括：容器ID的自动生成，ID的CSS记法，用来简化jquery操作
     */
    initPageLayoutConfigData: function() {
    	var pageId = NS.id();
    	
    	this.pageContainerId = pageId; // 页面容器ID
    	this.pageContainerIdCssHandle = '#' + this.pageContainerId; // 页面容器ID的CSS记法
    	
    	this.pageHeaderContainerId = pageId + '-header'; // 页面头部容器ID
    	this.pageHeaderContainerIdCssHandle = '#' + this.pageHeaderContainerId; // 页面头部容器ID的CSS记法
    	
    	this.pageMainContainerId = pageId + '-main'; // 页面主题容器ID
    	this.pageMainContainerIdCssHandle = '#' + this.pageMainContainerId; // 页面主题容器ID的CSS记法	
    	
		this.pageMainGridContainerId = pageId + '-main-grid'; // 页面主表格容器ID
		this.pageMainGridContainerIdCssHandle = '#' + this.pageMainGridContainerId; // 页面主表格容器ID的CSS记法
  		
		this.pageFooterContainerId = pageId + '-footer'; // 页面底部容器ID
		this.pageFooterContainerIdCssHandle = '#' + this.pageFooterContainerId; // 页面底部容器ID的CSS记法

		this.pageMainGridSearchContainerId = pageId + '-main-grid-search'; // 页面主表格查询容器ID
		this.pageMainGridSearchContainerIdCssHandle = '#' + this.pageMainGridSearchContainerId; // 页面主表格查询容器ID的CSS记法
    },
    
    /**
     * 修改请求服务配置（可根据需要继承重写）
     */
    updateServiceConfig: NS.emptyFn,
    
    /**
     * 创建并渲染页面组件，但不加载数据。
     * 页面由一个div容器包围，内容被分成：页面头部、页面主题和页面底部三部分
     */
    createAndReanderPageComponent: function() {
    	/* 创建页面布局HTML */
    	this.createPageLayoutHtml();
    	
    	/* 创建页面组件 */
    	this.pageComponent = new NS.Component({
        	autoScroll: true,
        	html: this.pageLayoutHtml
       	});
       	
       	/* 渲染页面组件，继承自NS.mvc.Controller，pageCompnent是每个类的顶层组件 */
        this.setPageComponent(this.pageComponent);
    },
    
    /**
     * 创建页面布局HTML
     */
    createPageLayoutHtml: function() {
    	this.pageLayoutHtml = '' +
		'<div class="page-container" id="' + this.pageContainerId + '">' +
			'<div class="page-header" id="' + this.pageHeaderContainerId + '"></div>' +
			'<div class="page-main" id="' + this.pageMainContainerId + '"></div>' +
			'<div class="page-footer" id="' + this.pageFooterContainerId + '"></div>' +
		'</div>';
    },
    
    /**
     * 创建并渲染页面内容，包括：页面头部、主题和底部，在页面组件渲染后才能调用该方法。
     */
    createPageContentAndRender: function() {
    	this.createPageHeaderAndRenderToPageComponent(); // 创建页面头部并渲染到page组件中
    	this.createPageMainAndRenderToPageComponent(); // 创建页面主题并渲染到page组件中
    	this.createPageFooterAndRenderToPageComponent(); // 创建页面底部并渲染到page组件中
    },
    
    /**
     * 创建页面头部并渲染到page组件中
     */
    createPageHeaderAndRenderToPageComponent: function() {
   		Business.component.PageHeader.getInstance(this.modelName).render(this.pageHeaderContainerId);
    },
    
    /**
     * 创建页面主题并渲染到page组件中
     */
    createPageMainAndRenderToPageComponent: function() {
		this.createPageMainTpl().writeTo(this.pageMainContainerId); // 利用TPL构建页面主题架构
		this.createPageMainContentAndRender(); // 创建并渲染页面主题内容
    },
    
    /**
     * 创建页面主题TPL
     * @return {NS.Template} 返回创建的tpl
     */
    createPageMainTpl: function() {
    	return new NS.Template(this.createPageMainTplHtml());
    },
    
    /**
     * 创建页面主题TPL的html（需要继承重写）
     * @return {string} 返回创建的html
     */
    createPageMainTplHtml: function() {
    	return '';
    },
    
    /**
     * 创建并渲染页面主题内容（可根据需要继承重写）
     */
    createPageMainContentAndRender: function () {
		this.initPageMainGridDataAndCreatePageMainGridAndRenderAndBindEvents(); // 初始化页面主表格数据，创建页面主表格，渲染页面主表格，设置主表格事件
    },
    
    /**
     * 初始化页面主表格数据，创建页面主表格，渲染页面主表格，设置主表格事件
     */
    initPageMainGridDataAndCreatePageMainGridAndRenderAndBindEvents: function() {
    	this.initPageMainGridHeaderDataAndPageMainGridContentData(this.createPageMainGridWithResponseAndRenderAndBindEvents);
    },
    
    /**
     * 初始化表头和表格内容，并在初始化调用回调函数
     * @param {function} callback
     */
    initPageMainGridHeaderDataAndPageMainGridContentData: function(callback) {
    	var keyAndParams = [
    		{key: 'queryMainGridHeaderData', params: {entityName: this.entityName}}, // 查询表头服务
    		{key: 'queryMainGridContentData'} // 查询表格内容数据
    	];
    	
    	this.callServices(keyAndParams, callback);
    },
    
    /**
     * 创建页面主表格且渲染之，并设置其事件
     * @param {Object} response 包含queryMainGridHeaderData和queryMainGridContentData的相应数据
     */
    createPageMainGridWithResponseAndRenderAndBindEvents: function(response) {
    	var standardGridHeaderData = NS.E2S(response.queryMainGridHeaderData); // 将从响应的表头数据转换为表格使用的标准表头数据
    	var gridContentData = response.queryMainGridContentData;
    	var gridConfig = this.defineGridConig();
    	this.createGridAndRender(standardGridHeaderData, gridContentData, 'queryMainGridContentData', gridConfig);
    	
    	this.bindPageMainGridEvents(); // 设置主表格事件
    },
    
    /**
     * 创建并渲染表格
     * @param {object} standardGridHeaderData 标准表头数据
     * @param {object} gridContentData 表格内容数据，格式必须是：{data:[],success:true,count:10}
     * @param {string} queryGridContentDataService 表格内容数据查询服务
     * @param {object} customGridCofig 自定义表格配置数据，最好包含列属性配置（columConfig）
     */
    createGridAndRender: function(standardGridHeaderData, gridContentData, queryGridContentDataService, customGridCofig) {
    	this.pageMainGrid = this.createGrid(standardGridHeaderData, gridContentData, queryGridContentDataService, customGridCofig);
    	this.pageMainGrid.render(this.pageMainGridContainerId);
    },
    
    /**
     * 创建表格
     * @param {object} standardGridHeaderData 标准表头数据
     * @param {object} gridContentData 表格内容数据，格式必须是：{data:[],success:true,count:10}
     * @param {string} queryGridContentDataService 表格内容数据查询服务
     * @param {object} customGridCofig 自定义表格配置数据，最好包含列属性配置（columConfig）
     */
    createGrid: function(standardGridHeaderData, gridContentData, queryGridContentDataService, customGridCofig) {
    	/* 表格基本配置 */
        var basicConfig = {
        	proxy: this.model, // grid的数据加载器,即{NS.mvc.Model}的一个实例        	
            serviceKey: queryGridContentDataService, // 查询表格内容数据的服务
			columnData: standardGridHeaderData, // 表头数据
            data: gridContentData, // 表格内容数据
            columnConfig: null,  // 列属性配置
            border: false, // 边框宽度为0
            checked: false, // 不出现选择框
            autoScroll: true, // 允许出现滚动条
            multiSelect: false, // 不允许选中多行
            lineNumber: true // 显示表格行数
        };
        
        /* 将自定义配置覆盖基本配置 */
        NS.apply(basicConfig, customGridCofig);
        
        /* 根据配置属性生成表格 */
        return new NS.grid.SimpleGrid(basicConfig);
    },
    
    /**
     * 定义表格属性（可根据需要继承重写，一般不用重写），默认只定义表格列属性
     * @return {object}
     */
    defineGridConig: function() {
    	var columnConfig = this.defineGridColumnConfig(); // 定义表格列属性
    	return {columnConfig: columnConfig};
    },
    
    /**
     * 定义表格列属性（需要继承重写）
     * @return {array}
     */
    defineGridColumnConfig: function() {
    	return [];
    },
    
    /**
     * 定义连接列属性
     * @param {string} columnName 必选，列名
     * @param {function} renderer 可选，渲染函数，其返回值决定列的最终渲染。renderer的第一个参数表示列对应的值，第二个参数表示一行对应的值
     */
    defineLinkColumnConfig: function(columnName, renderer) {
    	renderer = renderer ? renderer : function(columnValue, rowData) {
		    return '<a href="javascript:void(0);">' + columnValue + '</a>';
		};
		
    	return {
    		name: columnName,
    		xtype: 'linkcolumn',
			renderer: renderer
    	};
    },
    
    /**
     * 定义连接列属性
     * @param {{name: '列名', renderer: function(value, data){}}} columnNameAndRenderers 必选，包含列名和渲染函数的数组
     * renderer是渲染函数，其返回值决定列的最终渲染。的第一个参数表示列对应的值，第二个参数表示一行对应的值
     */
    defineMoreLinkColumnConfigs: function(columnNameAndRenderers) {
    	try {
    		var configs = [];
    		for (var i = 0, length = columnNameAndRenderers.length; i < length; i++) {
    			var params = columnNameAndRenderers[i];
    			configs.push(this.defineLinkColumnConfig(params.name, params.renderer));
    		}

    		return configs;
    	} catch (error) {
    		this.setError(error, {description: '表格连接列属性配置失败！'});
    		this.doArgumentError(error);
    	}
    },
    
    /**
     * 创建页面底部并渲染到page组件中
     */
    createPageFooterAndRenderToPageComponent: function() {
    	
    },
    
    /********************* 事件 ***********************/

    /**
     * 绑定页面事件。所有事件定义都在这里
     */
    bindPageComponentEvents: function() {
		this.pageComponent.on('click', this.pageComponentClickEventsHandle, this);
		this.pageComponent.on('change', this.pageComponentChangeEventsHandle, this);
    },
    
    /**
     * 页面单击事件处理器。所有单击事件处理都在这里，通过子类继承重写该方法。子类重写格式如下：
     * pageComponentClickEventsHandle: function(event, elem) {
     * 	   this.callParent();
     *     // define subclass events handle here
     * },
     */
    pageComponentClickEventsHandle: function(event, elem) {
    	
    },
    
    /**
     * 页面组件值改变事件处理器。所有组件值改变事件处理都在这里，通过子类继承重写该方法。子类重写格式如下：
     * pageComponentChangeEventsHandle: function() {
     * 	   this.callParent();
     *     // define subclass events handle here
     * },
     */
    pageComponentChangeEventsHandle: function(event, elem) {
    	
    },
    
    /**
     * 绑定页面主表格事件（需要继承重写）
     */
    bindPageMainGridEvents: function() {
    	this.pageMainGrid.bindItemsEvent({});
    },
    
    
    
	/***************** 工具方法 *****************/

    /**
     * 加载多个CSS文件
     */
    loadCssFiles: function() {
    	for (var i = 0, length = arguments.length; i < length; i++) {
    		var arg = arguments[i];
    		if (!arg) {
    			continue;
    		}
    		
    		if (NS.isArray(arg)) {
    			for (var j = 0, size = arg.length; j < size; j++) {
    				arguments.callee(arg[j]);
    			}
    		} else {
    			NS.loadCss(arg);
    		}
    	}
    },
    
    /**
     * 递归调用，在params上进行递归调用
     * @param {object} params 函数的arguments
     * @param {function} proccesser
     * @param {object} context
     */
    recursiveCall: function(params, proccesser, context) {
    	for (var i = 0, length = params.length; i < length; i++) {
    		var param = params[i];
    		if (!param) {
    			continue;
    		}
    		
    		if (NS.isArray(param)) {
    			for (var j = 0, size = param.length; j < size; j++) {
    				params.callee(param[j]);
    			}
    		} else {
    			proccesser.call(context, param);
    		}
    	}
    },
    
    /**
     * 设置错误信息的参数
     * @param {Object} options 错误消息的参数：description，错误描述
     */
    setError: function(error, options) {
    	error.description = error.description||options.description;
    },
    
    /**
     * 处理函数参数错误
     * @param {} error
     */
    doArgumentError: function(error) {
    	NS.Msg.error(error.description);
    },
    
   	/**
   	 * 统一调用服务接口
   	 * @param {[{key: '', params: {}}, {key: '', params: {}}, ...]} keyAndParams
   	 * @param {function} callback
   	 */
	callServices: function(keyAndParams, callback) {
		/* 给定键参数必须是数组，否则要转换为数组 */
		if (!NS.isArray(keyAndParams)) {
			keyAndParams = [keyAndParams];
		}
		
		/* 检测参数合法性 */
		var length = keyAndParams.length;
		for (var i = 0; i < length; i++) {
			var keyAndParam = keyAndParams[i];
			if (!keyAndParam.key) {
				NS.Msg.error('您正在请求的服务不存在!');
				return;
			}
		}
		
		/* Ajax调用服务 */
   		this.showPageComponentMask();
   		this.callService(keyAndParams, function(response) {
   			this.hidePageComponentMask();
   			
     		if (!response) {
     			NS.Msg.error(this.dbConnectionError);
     			return;
     		}
     		
     		for (var i = 0; i < length; i++) {
	     		if (!response[keyAndParams[i].key]) {
	     			NS.Msg.error(this.dbConnectionError);
     				return;
	     		}
     		}
     		
     		for (var i = 0; i < length; i++) {
     			var keyResponse = response[keyAndParams[i].key];
     			if ((typeof keyResponse.success != 'undefined')&&!keyResponse.success) {
		     		NS.Msg.error(this.dbSearchError);
     			}
	     	}
     		
     		callback.call(this, response);
 		}, this);
   	},
    
    /**
     * 显示遮罩
     * @param {} msg
     */
    showPageComponentMask: function(msg) {
    	if (!this.mask) {
			this.mask = new NS.mask.LoadMask({
				target : this.pageComponent,
				msg : msg||'数据加载中'
			});
    	}
   		
        this.mask.show();
	},
	
	/**
	 * 关闭遮罩
	 */
	hidePageComponentMask: function() {
		if (this.mask) {
			this.mask.hide();
		}
	},
	
	/**
	 * 过滤特殊字符
	 * @param {} text
	 */
	filterCharacters: function(text) {
		return text.replace(/(^\s*)|(\s*$)/g, '').replace(new RegExp('"', 'g'),'\\\"');
	},
	
	/**
	 * 设置单元格的title属性
	 * @param {} selector
	 */
	setTdTitles: function(selector) {
    	$(selector).find('td').bind('mouseover', function() {
    		$(this).attr('title', $(this).text());
    	});
    },
    
    /**
     * 只查找本页面内的元素，防止命名冲突
     */
    $$: function(path) {
    	return $(this.pageContainerIdCssHandle).find(path);
    },
    
	/**
   	 * 找到对应grid的id数据
   	 * 
   	 * @param {} gridData
   	 * @param {} id
   	 * @return {}
   	 */
   	findDataById: function(gridData, id) {
   		for (var i = 0; i < gridData.length; i++) {
   			if (gridData[i].id == id) {
   				return gridData[i];
   			}
   		}
   		
   		return null;
   	},
   	
   	/***************** select组件 操作 *****************/
     /**
      * 初始化列表控件
      * @param {} select 可以是tag，id，class，dom元素，jquery元素
      * @param {} data
      * @param {} promptTag
      * @param {} defaultValue
      */
	initSelect: function(select, data, promptTag, defaultValue) {
		if (!data) {
    		return;
    	}
    	
    	var selects = $(this.pageContainerIdCssHandle).find(select);
    	if (!selects||selects.lengh) {
    		return;
    	}
    		
    	selects.html('');
    	if (promptTag) {
    		selects.append('<option value="none">--请选择--</option>');
    	}
    	
    	for (var i = 0; i < data.length; i++) {
    		var id = data[i].id;
    		var mc = data[i].mc
    		var option = '<option value="' + id + '">' + mc + '</option>';
    		selects.append(option);
    	}
    	
    	if (defaultValue) {
    		selects.val(defaultValue);
    	}
    },
        
    /**
     * 获取列表选中值
     * @return {}
     */
    getSelectValue: function(selector) {
    	try {
    		return $(selector).val();
		} catch (error) {
			this.setError(error, {description: '获取列表值失败！'});
			return null;
    	}
    },
    
    /**
     * 获取学年学期的值
     * @return {}
     */
    getXnXqSelectValue: function(selector) {
    	try {
			var store = $(selector).val();
			var value = store.split('-');
			var xnId = value[0];
			var xqId = value[1];
			
			return {xnId: xnId, xqId: xqId};
    	} catch (error) {
    		this.setError(error, {description: '获取学年学期失败！'});
    		return null;
    	}
    },
    
    /**
     * 获取列表选中文本
     * @return {}
     */
    getSelectText: function(selector) {
    	return $(selector).find("option:selected").text();
    },
    
    /**
     * 初始化select
     * @param {} select
     * @param {} data
     * @param {} promptTag
     * @param {} defaultValue
     */
    initSelectTree: function(select, data, promptTag, defaultValue) {
    	if (!data) {
    		return;
    	}
    	
    	var selects = $(this.pageContainerIdCssHandle).find(select);
    	if (!selects||selects.lengh) {
    		return;
    	}
    	
    	selects.html('');
    	if (promptTag) {
    		selects.append('<option value="none">--请选择--</option>');
    	}

    	var root = this.findTreeRoot(data);
    	if (root) {
    		var id = root.id;
    		var mc = root.mc;
			var option = '<option value="' + id + '">' + mc + '</option>';
			selects.append(option);
    		this.initSelectTreeRecursion(selects, data, id, 1);
    	}
    	
    	if (defaultValue) {
    		selects.val(defaultValue);
    	}
    },
    
    findTreeRoot: function(data) {
    	for (var i = 0; i < data.length; i++) {
    		var fjdId = data[i].fjdId;
    		var hasParent = false;
    		for (var j = 0; j < data.length; j++) {
    			if (data[j].id == fjdId) {
    				hasParent = true;
    			}
    		}
    		
    		if (!hasParent) {
    			return data[i];
    		}
    	}
    	
    	return null;
    },
    
    initSelectTreeRecursion: function(selects, data, fjdId, cc) {
    	for (var i = 0; i < data.length; i++) {
    		if (data[i].fjdId == fjdId) {
    			var id = data[i].id;
    			var mc = data[i].mc;
    			var space = '&nbsp;&nbsp;';
    			for (var c = 1; c <= cc; c++) {
    				mc = space + mc;
    			}
    			var option = '<option value="' + id + '">' + mc + '</option>';
    			selects.append(option);
    			this.initSelectTreeRecursion(selects, data, id, cc + 1);
    		}
    	}
    }

});