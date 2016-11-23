/**
 * ext扩展组件
 */
Ext.define('Ext.form.field.ForumSearch',{
	extend:'Ext.form.ComboBox',
	allowBlank : false,
	typeAhead : false,
	hideTrigger : true,
	labelWidth : 70,
    width : 280,
    pageSize : 10,
    queryDelay:500,//默认延时
    queryCaching : false,//查询缓存禁用
    queryParam:'mc',//查询参数
	minChars:3,//最小触发查询数
	displayField:'mc',//默认显示(值)名称
	valueField:'id',//默认的实际值
    
	initComponent:function(){
		var cfg = this.initialConfig;//初始参数
		
		this.callParent();
		this.addEvents('beforeload');
		
		this.initStore(cfg.service,cfg.fields,typeof(cfg.pageSize)!='undefined'?cfg.pageSize:this.pageSize);
		var getInnerTpl = cfg.getInnerTpl;
		//删除Store里的配置属性
		delete cfg.url;
		delete cfg.fields;
		if(getInnerTpl)delete cfg.getInnerTpl;
		
		this.listConfig = {
			loadingText : '请稍后...',
			emptyText : '无查询结果',
			getInnerTpl : getInnerTpl || function() {
				return '<a class="search-item" id={id}>名称：<b>{mc}</b></a>';
			}
		};
		
	},
	_baseAction:'baseAction!queryByComponents.action',//同样可以覆盖,暂不保留覆盖接口
	/**
	 * 初始化Store
	 * @private
	 * @param {Object} service 链接路径 {
	 *                                  serviceName:'',
	 *                                  params:{}
	 *                                }
	 * @param {Array} fields 域配置属性 name:'',mapping:'',type:'',format:'',以及其他配置信息
	 * @param {Number} pageSize 分页条数
	 */
	initStore:function(service,fields,pageSize){
        service = service||{};
		var params = service.params||{};
		    params.singleReturnNoComponent = 1;//加上该属性 用于后台判断返回值时无key属性
		    params = JSON.stringify(params);
       	    
		var url =  this._baseAction+'?components={"queryTableContent": {"request": "'+service.serviceName+'","params":'+params+' }}';	

		Ext.define("model", {
	        extend: 'Ext.data.Model',
	        proxy: {
	            type: 'ajax',
	            url : url,
	            reader: {
	            	type : 'json',
					root : 'data',
					totalProperty : 'count'
	            },
	            doRequest : function(operation, callback, scope) {
                var writer  = this.getWriter(),
                    request = this.buildRequest(operation, callback, scope);

                if (operation.allowWrite()) {
                    request = writer.write(request);
                }

                Ext.apply(request, {
                    headers       : this.headers,
                    timeout       : this.timeout,
                    scope         : this,
                    callback      : this.createRequestCallback(request, operation, callback, scope),
                    method        : "post",
                    disableCaching: false // explicitly set it to false, ServerProxy handles caching
                });

                Ext.Ajax.request(request);

                return request;
            }
	        },
	        fields: fields||["id","mc"]
            
	    });
	
		this.store =  Ext.create('Ext.data.Store', {
	        pageSize: pageSize||10,
	        model: 'model'
	    });
		this.store.addListener('beforeload',function(store,operation,eOpts){
				return this.fireEvent('beforeload',operation['params'],eOpts);
		},this);
	},
    /**
     * 设置查询参数
     * @param params
     */
    setQueryParams : function(params){
        for(var i in params){
            this.store.getProxy().setExtraParam(i,params[i]);
        }
    }
});
