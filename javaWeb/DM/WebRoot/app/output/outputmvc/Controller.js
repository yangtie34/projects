NS.define('Output.Controller', {
			extend : 'NS.mvc.Controller',
			/*******************************************************************
			 * 继承类需要重写此方法,在此方法内完成所有业务
			 */
            bindNode : function(node) {
				this.addEvent('pageready');//定义组件创建完毕事件
				this.initModel();
				this.initView();
				this.process(node.id);//处理生成页面
                this.callParent(arguments);
			},
			/***
			 * 处理业务流程
			 */
			process : function(pageId){
				var me = this;
				this.loadData(pageId,function(data){
				     var pagebody = me.buildPageBody(data);
				     me.view.createPage(pagebody);
				     me.fireEvent('pageready');//触发组件创建完毕事件
				});
			},
			/***
			 * 通过模型层获取后台数据
			 * @param {} pageId
			 * @param {} callback
			 */
			loadData : function(pageId,callback){
				var pageP = {pageId : pageId};
			    this.model.pageRequest(pageP,callback);
			},
			/***
			 * 获取用于和底层类库进行交互的组件
			 * @return {}
			 */
			getLibComponent : function(){
                return this.view.getLibComponent();			
			},
			/*******************************************************************
			 * 初始化模型层对象
			 */
			initModel : function() {
				this.model = new Output.Model();
			},
			/*******************************************************************
			 * 初始化视图层对象
			 */
			initView : function() {
				this.view = new Output.View();
			},
			/****
			 * 创建页面body
			 */
			buildPageBody : function(pageData){
				var data = pageData.pageInitData;
			    var pageId = this.pageId = data.id;// 获取页面id
				var title = data.title;// 页面标题
				var templates = data.templates;//模版键
				var tarray = [];
				for(var i=0;i<templates.length;i++){
				    tarray.push(this.createTemplate(templates[i]).getLibComponent());
				}
				return tarray;
			},
			/*******************************************************************
			 * 创建模版对象
			 * 
			 * @param templateData
			 *            模版数据
			 */
			createTemplate : function(templateData) {
				var componentManager = this
						.createComponentManager(templateData);// 创建组件管理器
				this.bindListeners(componentManager);// 绑定监听事件
				var body = this.getTemplateBody(componentManager, templateData);// 获得模版组装对象(用于生成模版)
				var template = Output.TemplateBuilder.createTemplate(body);// 创建模版
				return template;
			},
			/*******************************************************************
			 * 创建组件管理器
			 * 
			 * @param {Object}
			 *            templateData
			 */
			createComponentManager : function(templateData) {
				var CM = new Output.ComponentManager(templateData.components,templateData.templateType);// 创建组件管理器
				CM.setPageId(this.pageId);//设定页面id
				CM.setTemplateId(templateData.templateId);//设定模版Id
				return CM;
			},
			/*******************************************************************************
			 * 绑定监听事件
			 */
			bindListeners : function(componentManager) {
				var bindManager = new Output.EventBindManager();// 事件绑定管理器
				bindManager.setModel(this.model);//设置数据层
				bindManager.setCM(componentManager);//设置组件管理器
				bindManager.bindEvents();// 绑定事件
			},
			/*******************************************************************
			 * 将组件管理器以及模版数据进行模版对象体的组装
			 * 
			 * @param {}
			 *            componentManager
			 * @param {}
			 *            templateData
			 * @return Components{Array}
			 */
			getTemplateBody : function(componentManager, templateData) {
				var builder = Output.TemplatePackager;// 模版组装工具
				templateData.componentManager = componentManager;
				var body = builder.getTemplateBody(templateData);// 组装模版
				return body;
			}
		});