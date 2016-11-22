Output = {};
/**
 * 数据请求代理--工具类
 * @calss US.DataRequestProxy
 */
Output.DataRequestProxy = {
	 /****
	  * 
	  **/
	 setRequestParam:function(paramArray,callBack){
	     this.paramArray = paramArray;
		 var me = this;
		 me.requestParamAssemble();
         me.sendRequest(me.paramAssembleArray,callBack);
	 },
      /**
       * 请求参数组装--传来的格式
	    items:[{
	            componentId:id,
				wd:obj,//string
				fw:obj,
				zb:obj
	          },{
			    comoponetId:id,
				wd:obj,
				fw:obb, 
				zb:obj
			  }]
       */
      requestParamAssemble:function(){
		  var me = this,pa = me.paramsArray,_array=[];
		  for(var i=0,len=pa.length;i<len;i++){
		      var newArray = [],obj = {},id,serviceName;
			  id = pa.componentId;//得到组件id
			  serviceName = pa.serviceName||"baseChartService";//默认走统一的chartSevice路径
			  for(var j in pa){
                   obj[j] = pa[j]; 
			  }
			  newArray.push(id,serviceName,"params="+obj);//obj：拼接查询参数->顺带有id属性，另外数组首位id是组件id 这是为了返回数据时辨别是哪个组件的数据
			  _array.push(newArray);
		  }
		  this.paramAssembleArray = _array;//返回的是转换后的数据
      }, 
      /**
       * 发送请求--异步请求
	   * responseParamList:需要的格式-chart_Service是为了分开统一的action  serviceName是单独走的service-默认走的是统一的是为了单独走而预留。 params是统计参数
	   * responseNmae,chart_Service,params=[[componentId1,serviceName,params={zb:zbString,wd:wdString,fw:fwString}],[componentId1,serviceName,params={zb:zbString,fw:fwString,wd:wdString}]]
       */
      sendRequest:function(params,callback,scope){
          var me = this;
          var conn = new US.Connection;//这是原先框架内连接方法
          params.success = function(response){
			     var data = Ext.JSON.decode(response.responseText, true);
//				 callback(JSONDATA);//数据返回的数据格式：
			     callback.call(scope||this,data);
			 };
          params.failure = function(response){
			     var data = Ext.JSON.decode(response.responseText, true);
//				 callback(JSONDATA);//数据返回的数据格式：
			     callback(data);
			 };
          conn.request(params);
      }
 };
(function(){
    var alias = NS.Function.alias;
    US = {};
    /**
     * @member NS
     * @method namespace
     * @inheritdoc NS.ClassManager#namespace
     */
    US.apply = alias(NS,'apply');
})();/**
 * @class US.Connection
 * @description 用于进行Ajax请求数据 Conn为其一个实例
 */
NS.define('US.Connection', {
			/**
			 * @description 初始化构造函数
			 */
			constructor : function() {
				this.initComponent(arguments[0]);// 初始化数据
			},
			/**
			 * 初始化组件
			 * 
			 * @param config
			 *            配置参数
			 */
			initComponent : function(config) {
				this.connection = Ext.create('Ext.data.Connection', config);
			},
			/**
			 * @description 请求数据
			 * @param config
			 *            配置请求
			 */
			request : function(config) {
				config.url = config.url||this.baseUrl;//设置基本请求路径
				config.params = config.params || {};
				config.method = 'post';
				this.connection.request(config);
			},
			baseUrl : 'baseAction!queryByComponents.action',// 基础请求url
			/**
			 * @description 用于获取同步加载数据
			 * @param config 请求配置参数
			 */
			syncGetData : function(config) {
				var me = this;
				config.params = config.params || {};
				config.params['requestType'] = 'query';
				me.connection.request({
							url : this.baseUrl,
							params : config.params,
							method : 'POST',
							async : false,
							success : function(response) {
								// alert(response.responseText);
								me.dataJson = Ext.JSON.decode(
										response.responseText, true);
							},
							failure : function() {
								me.dataJson = Ext.JSON.decode(
										response.responseText, true);
								Ext.Msg.alert('警告', '数据请求失败！');
							}
						});
				return me.dataJson;
			}
		});
/**
 * @description 一个US.Connection 的实例
 * */
Conn = new US.Connection();// connection的一个实例化对象
/*******************************************************************************
 * @class US.CommonUtil
 * @description 通用js工具类
 */
US.CommonUtil = {
	/**
	 * 重写函数,建议只重写生成的对象的函数，而不是类的函数
	 * 
	 * @param obj
	 *            需要重写函数的类
	 * @param initalFun
	 *            需要重写的函数
	 * @param reWriteFun
	 *            替代的函数
	 */
	applyFunction : function(obj, initalFun, reWriteFun) {
		obj[initalFun] = reWriteFun;
	},
	/**
	 * 合并2个js对象
	 * 
	 * @param obj1
	 *            合并到的对象
	 * @param obj2
	 *            被合并的对象
	 */
	mergeObject : function(obj1, obj2) {
		var ev = eval(obj2);
		for (var field in ev) {
			obj1[field] = ev[field];
		}
	},
	/**
	 * 判断函数是否存在，如果存在，则执行(目前暂只能执行一个参数)
	 * 
	 * @param fun
	 *            需要执行的函数
	 * @param args
	 *            函数执行所需要的参数
	 */
	judgeExist : function(fun, args) {
		if (fun) {
			fun(args);
		}
	},
	/**
	 * @description 获取查询参数
	 * @param para
	 *            传递的数组参数
	 */
	generateParams : function(para) {
		var paramy = "{";
		for (var i = 0; i < para.length; i++) {
			var obj = para[i].toString().split(",");
			// 修改组装json数据的先后顺序，把组件名放在第一个位置，请求名放在第二个参数里 modify2011年03月21日修改
			paramy += ("\"" + obj[0] + "\":{\"request\":\"" + obj[1] + "\",\"params\":{");
			for (var j = 2; j < obj.length; j++) {
				paramy += obj[j];
				if ((j + 1) != obj.length) {
					paramy += ",";
				}
			}
			paramy += "}}";
			if ((i + 1) != para.length) {
				paramy += ",";
			}
		}
		paramy += "}";
		return paramy;
	},
	baseUrl : 'baseAction!queryByComponents.action',
	/**
	 * @description 获取请求参数
	 * @param para 参数数组
	 * */
	getParams:function(para){
		var params = {components:US.CommonUtil.generateParams(para)};
		return params;
	},
	/**
	 * @description 获取向后台发送请求的参数---注释 --一般用于同步请求数据使用,如果使用异步，请参考参数对生成的参数做另外处理
	 * @param para 参数数组
	 * */
	getQueryParams:function(para){
		var me = this;
	    var obj = {
	       method:'post',
	       params:me.getParams(para)
	    };
	    return obj;
	},
	/**
	 * @description 获取为grid组装的proxy数据
	 * @param queryTableContentParam 请求的参数
	 * @param param JSON对象
	 * @return proxy  store的proxy属性
	 * */
	getProxyForGrid:function(queryTableContentParam,params){
	   var url = this.baseUrl + "?components="
				+ this.generateParams([queryTableContentParam])
	   var proxy = {
				type : 'ajax',
				url : url,// url
				extraParams : params,
				reader : {
					type : 'json',
					root : 'data',// 读取的主要数据 键
					totalProperty : 'count'
					// 数据总量
				}
			};
	   return proxy;
	},
	getProxyForTree : function(treeparams,params){
		var url = this.baseUrl + "?components="
		+ this.generateParams([treeparams])
		var proxy = {
				type : 'ajax',
				url : url,// url
				extraParams : params,
				reader : {
					type : 'json',
					root : 'root'// 读取的主要数据 键
					// 数据总量
				}
			};
	},
	/**
	 * @description 根据参数数组获取同步数据---注---主要用于同步请求数据
	 * @param para 参数数组
	 * @return jsonData 同步请求的获取的数据
	 * */
	getSyncData : function(para){
	   var conn = new US.Connection();
	   var jsonData = conn.syncGetData(this.getQueryParams(para));
	   return jsonData;
	},
	/**
	 * @description 根据传入参数，生成转换后的所有数据
	 * @param para 
	 * @param proxy grid的代理
	 * @return 返回转换后的数据
	 * */
	getTranData : function(jsonData,proxy){
	    jsonData.proxy = proxy;
	    var tranData = new ModelOne.GridDataTran(jsonData).getGridData();
	    return tranData;
	},
	/***
	 * 将dm转换为id
	 * @param {} sx
	 * @param {} v
	 */
	changeDmToId : function(data,sx,v){
		 var tranData = data; 
	     var CS = tranData.componentsData;
	     for(var i=0;i<CS.length;i++){
	         var C = CS[i];
	         if(C.dataIndex == sx){
	         	var data = C.data;
	         	for(var j=0;j<data.length;j++){
	         	    var D = data[j];
	         	    if(D.dm == v) return D.id;
	         	}
	         }
	     }
	     return 0;
	},
	//
	getTranDataFromParam : function(para,proxy){
       	var tranData = this.getTranData(Conn.syncGetData(this.getQueryParams(para)),proxy);
       	return tranData;
	}
};NS.define('Output.Model',{
   /***
    * 启动函数
    */
   constructor : function(){
       
   },
   /***
    * 请求页面以及组件数据
    */
   pageRequest : function(params,callback,scope){
   	   var array = new Array();
       array.push('pageInitData,commonEntrance,"output":'+JSON.stringify(params));
       var obj = US.CommonUtil.getQueryParams(array);
       this.getProxy().sendRequest(obj,callback,scope);
   },
   /***
    * 弹窗请求表头数据
    */
   gridHeaderRequest : function(entityName,callback){
   	   var entityKV = '"entityName":\"' + entityName + "\"";
       var array = new Array();
       array.push('queryAddFormField,base_queryForAddByEntityName,'+entityKV); 
       var obj = US.CommonUtil.getQueryParams(array);
       this.getProxy().sendRequest(obj,callback);
   },
   /***
    * 反回Connection 连接类单例
    * @return {}
    */
   getProxy : function(){
      return  Output.DataRequestProxy;
   }
});NS.define('Output.View', {
	/***************************************************************************
	 * 启动函数
	 */
	constructor : function() {

	},
	/***************************************************************************
	 * 获取模版组装器
	 */
	createPage : function(body) {
		this.component = Ext.create('Ext.container.Container', {
			autoScroll : true,
			layout : 'fit',
			style : {
				backgroundColor : '#deecfd'
			},
			items : body,
			listeners : {
//				'beforerender' : function() {
//					var window = this.findParentByType('window');
//					if (window) {
//						window.addListener('beforehide', function() {
//									var charts = this.query('outputChart');
//									for (var i = 0, len = charts.length; i < len; i++) {
//										var chart = charts[i];
//										chart.fireEvent('windowhide');
//									}
//								}, this);
//						window.addListener('beforeshow', function() {
//									var charts = this.query('outputChart');
//									for (var i = 0, len = charts.length; i < len; i++) {
//										var chart = charts[i];
//										chart.fireEvent('windowshow');
//									}
//								}, this);
//					}
//				},
				'resize' : function() {
					//在系统界面时
					var window = this.findParentByType('window');
					//在传统界面时
					var tabPanel = this.findParentByType('tabpanel');
					//在普通界面时
					var panel = this.findParentByType('panel');
					if (window || tabPanel || panel) {
						var charts = this.query('outputChart');
						for (var i = 0, len = charts.length; i < len; i++) {
							var chart = charts[i];
							chart.fireEvent('windowlayout');
						}
					}

				}
			}
		});
	},
	/***************************************************************************
	 * 返回用于和底层类系统交互的组件
	 * 
	 * @return {}
	 */
	getLibComponent : function() {
		return this.component;
	}
});/*******************************************************************************
 * 定义统计报表组件
 */
NS.define('Output.Table', {
    /***************************************************************************
     * 构造函数
     */
    constructor: function (config) {
        this.config = config;// 将参数设置为内部变量
        this.dataConvert();// 数据转换
        this.createComponent();
        this.addEvent('statuschange');// 增加状态变化事件
    },
    /***************************************************************************
     * 创建组件对象
     */
    createComponent: function () {
        var me = this;
        var data = this.getTplData();
        var tpl = this.getTpl();
        var basic = {
            tpl: tpl,
            rows: data,
            baseCls: 'statistics_table',
            region: 'south',
            height: '80%',
            autoScroll: true,
            title: "" || this.config.title,
            listeners: {
                'afterrender': function () {
                    this.tpl.overwrite(this.el, this.rows);
                    var el = this.el.first();
                    var width = el.getWidth();
                    var x = el.getXY();
                    var com = this.com = clist[0];//combox组件
                    var cwidth = com.el.getWidth();
                    toolbarContainer.el.dom.style.paddingLeft = (width - cwidth + 25) + 'px';
                }
            }
        };
        var clist = me.getChangeRangeComponents(this.config.displayData.components);
        var toolbarContainer = this.toolbar = Ext.create('Ext.toolbar.Toolbar', {
            baseCls: 'style="margin:0 auto;"',
            height: 25,
            // xtype:'container',
            items: clist
        })
        var tableContainer = this.tableContainer = Ext.create('Ext.container.Container', basic);
        if (clist.length == 0) {
            this.component = tableContainer;
        } else {
            this.component = Ext.create('Ext.container.Container', {
                baseCls: '',
                items: [
                    toolbarContainer,
                    tableContainer

                ]
            });
        }

    },
    /***
     *
     */
    loadData: function (config) {
        this.config = config;//
        this.dataConvert();// 数据转换
        var data = this.getTplData();
        var tel = this.tableContainer.el;
        var table = this.tableContainer;
        table.tpl.overwrite(tel, data);
        var el = table.el.first();
        var width = el.getWidth();
        var x = el.getXY();
        var com = table.com;//combox组件
        var cwidth = com.el.getWidth();
        this.toolbar.el.dom.style.paddingLeft = (width - cwidth + 25) + 'px';
    },
    /**
     * 获取组件类型
     */
    getComponentType: function () {
        return this.config.componentType;
    },
    /***************************************************************************
     * 返回依赖底层类库的组件信息
     *
     * @return {}
     */
    getLibComponent: function () {
        return this.component;
    },
    /***************************************************************************
     * 生成带有层级列数据结构
     *
     * @return {}
     */
    makeColumnData: function (columns) {
        var data = {
            items: columns
        };
        var deep = this.getDeep(data);// 叶子节点的最大深度
        var array = new Array(deep);// 生成带有深度的节点元素
    },
    /***************************************************************************
     * 数据格式转换类
     */
    dataConvert: function () {
        var cfg = this.config;
        this.componentId = cfg.componentId;// 组件id
        var display = cfg.displayData;// 显示数据
        this.initData = TreeData.chartDataConvert(display);
    },
    /***************************************************************************
     * 获取生成tpl所需要的数据
     */
    getTplData: function () {
        var rows = this.initData.rows;// 列数据
        var columns = this.initData.columns;// 行数据
        var util = TreeData;
        var columnlist = util.getColumnsList(columns);// 获取列层级数据
        var colIndexlist = util.getLeafList({
            items: columns
        });// 获取底层列索引数据
        var rowlist = util.getRowList(rows, colIndexlist);// 获取内容层级数据
        var coldeep = util.getDeep({
            items: columns
        });// 获取表头深度
        var rowdeep = util.getDeep({
            items: rows
        });// 获取表体深度
        var data = {
            columns: columnlist,
            rows: rowlist,
            coldeep: coldeep,
            rowdeep: rowdeep - 2
        };
        return data;
    },
    /***************************************************************************
     * 获取table对象的tpl
     */
    getTpl: function () {
        var tpl = new Ext.XTemplate(
            '<table   cellpadding="0" cellspacing="1" border="1" align="center">',
            '<tpl for="columns">',
            '<tr>',
            '<th colspan={parent.rowdeep} ></th>',
            '<tpl for=".">',
            '<th colspan={colspan} rowspan = {rowspan}>{header}</th>',
            '</tpl>',
            '</tr>',
            '</tpl>',
            '<tpl for="rows">',
            '<tr>',
            '<tpl for=".">',
            '<tpl if="items">',
            '<td colspan={rowspan} rowspan = {colspan}>{header}</td>',
            '<tpl else>',
            '<td>{.}</td>',
            '</tpl>',
            '</tpl>',
            '</tr>',
            '</tpl>',
            '</table>');
        return tpl;
    },
    /***************************************************************************
     * 将组件添加到一个dom元素上或者一个id为｛id｝的dom元素
     */
    render: function (id) {
        if (id.nodeType) {
            id.appendChild(this.component.el.dom);
        } else {
            document.getElementById(id).appendChild(this.component.el.dom);
            ;
        }
    },
    setWidthAndHeight: function (width, height) {
        var C = this.component;
        // C.setWidth(width);
        // C.setHeight(height);
    },
    /**
     * 得到选择范围组件
     */
    getChangeRangeComponents: function (components) {
        var componentList = this.componentList = [];
        if (components != null) {
            var me = this, components = components
                || [];
            for (var i = 0; i < components.length; i++) {
                var component = components[i];
                var com = me.createChangeRangeComponent(component)
                if (component.comType == 'combox')
                    this.combox = com;
                componentList.push(com);
            }
        }
        return [this.combox];
    },
    /**
     * 创建选择范围组件：单选组件、复选组件、下拉组件
     *
     * @param {}
     *            obj 传递的对象 obj格式： [ { comType : 'checkboxgroup', valueRange :
	 *            'wd',//数据类型 defaultValue : '值1,值2', data : [ { name : '名称1',//
	 *            显示值 value : '值1'// 实际值 }, { name : '名称2', value : '值2' } ] }, {
	 *            comType : 'radiogroup', valueRange : 'zb',//数据类型 defaultValue :
	 *            '值1', data : [ { name : '名称1',// 显示值 value : '值1'// 实际值 }, {
	 *            name : '名称2', value : '值2' } ] }, { comType : 'combox',
	 *            //defaultValue : '值1', valueRange : 'fw',//数据类型 data : [ {
	 *            name : '名称1',// 显示值 value : '值1'// 实际值 }, { name : '名称2',
	 *            value : '值2' } ] } ]
     */
    createChangeRangeComponent: function (obj) {
        var that = this, type = obj.comType, rangeComponent;// 组件类型，范围组件
        var name = obj.valueRange;// 得到名称
        var defaultValues = obj.defaultValue, defaultValuesList;// 默认值字符串
        if (defaultValues) {
            // defaultValuesList = defaultValues.split(",");//
            // 默认值数组，后台更改为数组形式了故而删除
            defaultValuesList = defaultValues;
        }
        var dataList = obj.data || [];// 数据集
        if (type == 'combox') {
            // 下拉框组件
            var states = Ext.create('Ext.data.Store', {
                fields: ['name', 'value'],
                data: dataList
            });

            var _obj = {
                store: states,
                style: {
                    "padding-top": '3px'
                },
                emptyText: '请选择...',
                queryMode: 'local',
                displayField: 'name',// 显示值
                valueField: 'value',// 实际值
                editable: false,// 不可编辑
                width: 150,// 默认下拉的宽度
                name: name
            };
            if (defaultValues) {
                _obj.value = obj.defaultValue;
            }
//			// 如果是指标且指标数据长度小于2个，则隐藏之。
//			if (name != 'wd' && dataList.length == 1) {
//				_obj.hidden = true;
//			}
            rangeComponent = Ext.create('Ext.form.ComboBox', _obj);

        } else {
            // 数据处理
            var arrItems = [], columns = [], i = 0, width = 0;// 默认宽度0

            for (; i < dataList.length; i++) {
                var _obj = {}, j = 0;
                var boxLabel = _obj.boxLabel = dataList[i].name;
                // 检验字符串内是否包含数字
                // 得到字符 转换为array 根据具体情况做长度
                var k = 0, lablewidth = 16;// 默认的为图标长
                for (var z = 0; z < boxLabel.length; z++) {
                    var str = boxLabel.substring(k, k + 1);

                    if (str.match(/^[\u4e00-\u9fa5]$/)) {
                        lablewidth += 16;// 汉字
                        // lablewidth += 14;
                    } else if (!isNaN(str)) {// 数字
                        lablewidth += 10;
                        // lablewidth += 8;
                    } else {
                        lableWidth += 4;//
                    }
                }
                width += lablewidth;
                columns.push(lablewidth);
                if (defaultValuesList) {
                    for (; j < defaultValuesList.length; j++) {
                        if (dataList[i].value == defaultValuesList[j]) {
                            _obj.checked = true;
                        }
                    }
                }
                _obj.name = name;
                _obj.inputValue = dataList[i].value;
                arrItems.push(_obj);
            }
            var bWidth = 30;// 间隙宽度
            width = width + bWidth;// 相应宽度加上间隙宽度
            columns.push(bWidth);// 添加间隙
            var obj_ = {
                name: name,
                columns: columns,
                width: width,
                items: arrItems
            };
            // 设置是否隐藏
            if (arrItems.length == 1) {
                obj_.hidden = true;
            }
            if (type == 'checkboxgroup') {
                // 创建复选框组件
                rangeComponent = Ext.create('Ext.form.CheckboxGroup', obj_);

            } else if (type == 'radiogroup') {
                // 创建单选组组件
                rangeComponent = Ext.create('Ext.form.RadioGroup', obj_);
            }
            // wdith = 0;
        }
        // 添加监听事件-当值改变时触发
        rangeComponent.addListener({
            change: function (self, newV, oldV, eOpts) {
                var status = {}, i;
                for (i = 0; i < that.componentList.length; i++) {
                    var array = [], component = that.componentList[i], name = component
                        .getName(), values, xtype = component.getXType();// 组件类型
                    if (xtype == 'checkboxgroup') {
                        values = component.getChecked();// 复选组
                        for (var j = 0; j < values.length; j++) {
                            array.push(values[j].inputValue);// 这个应该还取值
                        }
                    } else if (xtype == 'radiogroup') {
                        values = component.getValue();
                        var value = values[name] || "";
                        array.push(value);
                    } else if (xtype == 'combobox') {
                        values = component.getValue();// combobox的取值方法
                        array.push(values);
                    } else {
                    }
                    status[name] = array;
                }
                that.allStatus = status;
                that.fireEvent('statuschange', that.config.componentId);// 触发状态改变事件
            }
        });
        return rangeComponent;// 返回范围组件
    },
    /**
     * 得到组件所有状态ComponentAll
     *
     */
    getStatus: function () {
        return this.allStatus;
    }
});TreeData = {
    getRowList: function (rowdata, colIndexlist) {
        var rowlist = this.getColumnsList(rowdata);
        var datalist = this.getRows(rowlist, colIndexlist);
        return datalist;
    },
    /***
     *获取用于遍历表体内容的集合
     */
    getRows: function (rowlist, columnIndexList) {
        var ln = rowlist.length;
        var cil = columnIndexList;
        var cl = cil.length;
        var rownum = this.getRowNum(rowlist);//获取表内容最大行数
        var datalist = new Array(rownum);//设定长度为行数的数组
        for (var i = 0; i < rownum; i++) {//初始化数据数组
            datalist[i] = [];
        }
        for (var i = 0; i < ln; i++) {
            var data = [];
            var innum = 0;
            var array = rowlist[i];
            for (var j = 0, len = array.length; j < len; j++) {
                var obj = array[j];//获取一层的某个对象
                if (obj.items) {//如果其含有子元素，表明其为数据索引
                    datalist[innum].push(obj);
                    innum += obj.colspan;
                } else {
                    for (var k = 0; k < cl; k++) {
                        datalist[innum].push(obj[cil[k].dataIndex]);
                    }
                    innum += 1;//如果是数据的话每次只增加一
                }
            }
        }
        return datalist;
    },
    /**
     获取所有行数
     **/
    getRowNum: function (rowlist) {
        var rownum = 0;
        var array = rowlist[0];
        var ln = array.length;
        for (var i = 0; i < ln; i++) {
            var obj = array[i];
            rownum += obj.colspan;
        }
        return rownum;
    },
    /****
     获取列读取数据索引
     ****/
    getColumnIndexList: function (columnlist) {
        var indexlist = columnlist[columnlist.length - 1];
        var array = [];
        for (var i = 0, len = indexlist.length; i < len; i++) {
            var column = indexlist[i];
            array.push(column.dataIndex);
        }
        return array;
    },
    /***
     * 获取最终生成的根据层级的列的集合
     * @param {} root
     */
    getColumnsList: function (columns) {
        var data = {
            items: columns
        };
        var deep = this.getDeep(data) - 1;//获取表头树的最大深度
        /*****************设置每个节点的深度***************/
        this.setDeep(data);
        /*****************设置所有的叶子节点******************/
        this.setLeaf(data);
        /*******设置每个节点占行数**********/
        for (var i = 0; i < columns.length; i++) {
            this.setRowSpan(columns[i], deep);
        }
        /*******设置每个节点占列数**********/
        this.setColSpan(data);
        /********************/
        var columns = [];//根据深度生成深度表头数据
        for (var i = 1; i <= deep; i++) {
            columns.push(this.getDeepList(data, i));
        }
        return columns;
    },
    /***
     * 设置表头的行展开项
     */
    setColumsRowSpan: function (columns) {
        var data = {
            items: columns
        };
        var deep = this.getDeep(data);// 叶子节点的最大深度

    },
    /***
     * 设置表头跨列长度
     */
    setColSpan: function (root) {
        if (root.leaf) {
            root.colspan = 1;
        } else {
            root.colspan = this.getLeafNumber(root);
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setColSpan(root.items[i]);
            }
        }
    },
    /***************************************************************************
     * 根据跟节点以及树的深度设置每个节点行展开长度
     *
     * @param {}
     *            root
     * @param {}
     *            deep
     */
    setRowSpan: function (root, deep) {
        if (typeof root.items != 'undefined') {
            root.rowspan = 1;
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setRowSpan(root.items[i], deep);
            }
        } else {
            root.rowspan = (deep - root.deep) + 1;
        }
    },
    /** *获得所有叶子节点的数量*** */
    getLeafNumber: function (root) {// 获得一个节点叶子数
        var num = 0;
        if (root.items) {
            for (var i = 0, len = root.items.length; i < len; i++) {
                var node = root.items[i];
                if (node.items) {
                    num += this.getLeafNumber(node);
                } else {
                    num += 1;
                }
            }
        } else {
            num = 1;
        }
        return num;
    },
    /** *获得一棵树的深度*** */
    getDeep: function (root) {
        var deep = 1;
        if (root.items) {
            var maxdeep = 0;
            for (var i = 0, len = root.items.length; i < len; i++) {
                var d = this.getDeep(root.items[i]);
                maxdeep = d > maxdeep ? d : maxdeep;
            }
            deep += maxdeep;
        }
        return deep;
    },
    /***************************************************************************
     * 设置节点的深度
     *
     * @param {}
     *            root
     * @param {}
     *            deep
     */
    setDeep: function (root, dp) {
        var deep = dp || 0;
        root.deep = deep;// 设定当前节点的深度为deep,如果没有深度则为根节点深度为0
        if (root.items) {
            deep += 1;
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setDeep(root.items[i], deep);
            }
        }
    },
    /***************************************************************************
     * 查询深度为n的所有元素集合
     */
    getDeepList: function (root, deep) {
        var deeplist = [];
        if (root.deep < deep) {
            if (root.items) {
                for (var i = 0, len = root.items.length; i < len; i++) {
                    deeplist = deeplist
                        .concat(this.getDeepList(root.items[i], deep));
                }
            }
            return deeplist;
        } else if (root.deep == deep) {
            deeplist.push(root);
            return deeplist;
        } else {
            return deeplist;
        }
    },
    /***************************************************************************
     * 设置当前节点下的所有叶子节点
     *
     * @param {}
     *            root 需要设置是否为叶子节点的所有节点
     */
    setLeaf: function (root) {
        if (root.items) {
            root.leaf = false;
            for (var i = 0, len = root.items.length; i < len; i++) {
                this.setLeaf(root.items[i]);
            }
        } else {
            root.leaf = true;
        }
    },
    /***************************************************************************
     * 从根节点，获取叶子节点集合
     *
     * @param {}
     *            root
     */
    getLeafList: function (root) {
        var leaflist = [];
        if (root.leaf) {
            leaflist.push(root);
            return leaflist;
        } else {
            for (var i = 0, len = root.items.length; i < len; i++) {
                leaflist = leaflist.concat(this.getLeafList(root.items[i]));
            }
            return leaflist;
        }
    },
    /***
     * 报表数据转换为table数据格式
     */
    chartDataConvert: function (data) {
        var columns = [
        ];
        var rows = [];
        var model = data.model;
        var bodydata = data.data;
        for (var i = 0, len = model.length; i < len; i++) {
            columns.push({header: model[i], dataIndex: model[i]});
        }
        for (var i = 0, len = bodydata.length; i < len; i++) {
            var cdata = bodydata[i];
            var row = {header: cdata.seriesname};
            delete cdata.seriesname;
            row.items = [cdata];
            rows.push(row);
        }
        return {
            rows: rows,
            columns: columns
        };
    }
};/*******************************************************************************
 * 基本组件：文本组件。 根据传递的文本组件配置数据创建组件。
 *
 * @argument cData = { componentType : 'popText', showMaxItems : 2, htmlstring :
 *           "上述变更数据，已同步数据节点 {0} 处，被动同步数据节点数 {1} 处，未完成同步数据节点数 {2} 处。", items : [{
 *           text : '286', params : { name : 'zhangsan', age : 12 } }, { text :
 *           '224', params : { name : 'lisi', age : 12 } }] }
 * @return
 *           @example
 */
NS.define('Output.Text', {
    constructor: function (cData) {
        this.config = cData;// 配置参数
        this.buildDom(cData);
        this.addEvent('pop');// 添加弹窗事件
    },
    /*******************************************************************
     * 创建组件的dom节点
     */
    buildDom: function (cData) {
        var me = this;
        var data = cData.displayData;
        // 创建一个组件内的父节点div，以便挂接到外界的DOM节点上，完成组件的相互组装。
        var interfaceDIV = document.createElement('DIV');
        interfaceDIV.style.width = '90%';
        interfaceDIV.className = 'opTextRoot';
        var itemsContent = data['data'];
        var htmlString = data['htmlStr'];
        // 获取要显示的最大连接数。
        var showMaxItems = this.showMaxItems = cData['showMaxItems'];
        // 正则表达式，分割htmlString字符串。
        var strs = htmlString.split(/\{[0-9]*\}/);
        // 循环数据和分割后得到的字符串数据，来创建div子节点元素。
        for (var index in strs) {
            if (index > showMaxItems) {
                break;
            }
            interfaceDIV.appendChild(document
                .createTextNode(strs[index]));
            // 如果有连接数据，则添加连接元素。
            if (index < itemsContent.length) {
                (function (index, interfaceDIV, item) {
                    var a = document.createElement('A');
                    a.href = "#";
                    a.onclick = function () {
                        var params = item.params;
                        me.fireEvent('pop', params.stsxm, params.id);// 触发弹窗事件
                        // var popWindow = new Output.PopWindow({
                        // id : params.wbid,
                        // entityName : params.stsxm
                        // });
                        return false;
                    };
                    var entityName = item.params.stsxm;
                    if (entityName != null && entityName != "") {
                        a
                            .appendChild(document
                                .createTextNode(itemsContent[index]['text']));
                        interfaceDIV.appendChild(a);
                    } else {
                        var span = document.createElement('span');
                        span.appendChild(document
                            .createTextNode(itemsContent[index]['text']));
                        interfaceDIV.appendChild(span);
                    }
                })(index, interfaceDIV, itemsContent[index]);
            }
        }

        this.myDom = interfaceDIV;
        this.createComponent(cData);
    },
    /*******************************************************************
     * 创建用以反回getLibComponent 方法反回的对象
     */
    createComponent: function (cData) {
        var me = this;
        var basic = {
            baseCls: '',
            xtype: 'container',
            columnWidth: 0.65,
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.myDom);
                    // var div = document.createElement('DIV');
                    // div.style.width = '20%';
                    // this.el.appendChild(div);
                }
            }
        };
        US.apply(basic, {});
        this.textcontainer = new Ext.container.Container(basic);
        this.createXxxxContainer();
        this.component = new Ext.container.Container({
            baseCls: '',
            layout: 'column',
            items: [
                this.textcontainer,
                this.xxxxcontainer
            ]
        });
    },
    /***
     * 创建详细信息容器
     */
    createXxxxContainer: function () {
        this.xxxxcontainer = new Ext.container.Container({
            baseCls: 'opTextRoot_xxxx',
            columnWidth: 0.35,
            listeners: {
                'afterrender': function () {
                    // 创建一个“点击查看详细信息”链接！
//									var xxxx = document.createElement('A');
//									xxxx.href = "#";
//									Ext.fly(xxxx).on('click',function(event){
//									     event.stopEvent();
//									     alert('查看详细信息');
//									});
                    // 创建详细信息节点
                    var span = document.createElement('SPAN');
                    var textNode = document
                        .createTextNode('点击查看详细信息');
//									span.className = 'opTextRoot_xxxx';
                    textNode.onclick = function(){
                        alert(111);
                    };
                    span.appendChild(textNode);
//									xxxx.appendChild(span);
                    this.el.appendChild(span);
                }
            }
        });
    },
    /*******************************************************************
     * 获取并返回当前组件id的状态属性。
     */
    getStatus: function () {
    },
    /*******************************************************************
     * 刷新组件。
     */
    loadData: function (cData) {

    },
    /**
     * 获取组件类型
     */
    getComponentType: function () {
        return this.config.componentType;
    },
    /*******************************************************************
     * 获取用于和底层类库交互的组件对象。
     */
    getLibComponent: function () {
        return this.component;
    },
    /*******************************************************************
     * 展示最大items项目。
     */
    showMaxItems: function () {
        alert('展示最大的items项目');
    },
    /*******************************************************************
     * 将创建的原生dom节点渲染到制定的dom节点上。
     *
     * @param {}
     *            id 该参数可以是dom节点的id，也可以是dom对象。
     */
    render: function (id) {
        this.component.render(id);
    },
    setWidthAndHeight: function (width, height) {
        var C = this.component;
        // C.setWidth(width);
        // C.setHeight(height);
    }
});/**
 * 图形组件基类 可以在添加子类的时候，同时维护此基类，此基类可以单独通用。
 *
 * @author haw_king
 *         @example
 *         var chart = Ext.create('Ext.Chart',{
 *        		title : '标题--名称',
				unit : '只',
				"xaxisname" : "数量",
				"yaxisname" : "种类",
				componentType : 'MSColumn3D',
				model : [ '牛', '羊', '猪', '马' ],// 维度名称-label
				data : [ {
					"牛" : '200',
					"羊" : '230',
					"猪" : '120',
					"马" : '130',
					seriesname : 'M1'
				}, {
					"牛" : '100',
					"羊" : '130',
					"猪" : '190',
					"马" : '30',
					seriesname : 'M2'
				} ]
 *    });
 *    chart.getLibComponent();//放到panel内或者其他容器内即可
 */
Ext.define('Output.Chart', {
    extend: 'Ext.Component',
    baseCls: '',
    frame: false,
    alias: ['widget.outputChart'],
    chartId: Ext.id() + '_chart',
    constructor: function (componentData) {
        this.callParent();
        this.componentData = componentData;
        this.templateType = componentData.templateType;// 模板类型(已经过转换后的类型维护在chartParentComponent里)
        this.createChart();

        this.addEvents('windowshow', 'windowhide', 'windowlayout');// ,
        this.addListener({
            windowshow: this._beforeWindowShow,
            windowhide: this._beforeWindowHide,
            windowlayout: this._whenWindowLayout
        });
    },
    onRender: function () {
        this.callParent(arguments);
        var id = this.el.dom.id;
        this.fusionChart.render(id);
    },
    getLibComponent: function () {
        return this;
    },
    /**
     * 当父类窗口或者tabpanel发生变动布局的时候触发
     */
    _whenWindowLayout: function () {
        this.parentCt._whenWindowLayout(this.parentCt.container);
    },
    /**
     * 窗口显示之前触发 设置该容器的宽高
     */
    _beforeWindowShow: function () {
        this.setHeight(this.el.parent().getHeight());
        this.setWidth(this.el.parent().getWidth());
    },
    /**
     * 窗口隐藏之前出发 将该容器宽高设置为0（用于隐藏）
     */
    _beforeWindowHide: function () {
        this.setHeight(0);
        this.setWidth(0);
    },
    /**
     * 创建chart
     */
    createChart: function () {
        var that = this, jsonData;
        var chartType = that.chartType = that
            .getChartType(that.componentData.style);
        var width = '100%', heigth = '100%';
        if (!this.fusionChart) {
            this.fusionChart = new FusionCharts("FusionCharts/charts/"
                + chartType + ".swf", that.chartId, width, heigth,
                "0", "1");
            // 添加chart错误的汉化提示
            this.fusionChart.configure({
                ParsingDataText: '数据读取中,请稍候...',
                PBarLoadingText: '数据加载中,请稍候...',
                RenderingChartText: '数据正在渲染,请稍候...',
                LoadDataErrorText: '数据加载错误!',
                ChartNoDataText: "无数据可供显示!"
                // add more...-->key:value
            });
        }
        jsonData = this.getChartAllData();
        this.fusionChart.setJSONData(jsonData);

    },
    /**
     * 得到图形类型
     *
     * @param {}
     *            type 图形组件类型
     */
    getChartType: function (type) {
        // 维护的图形组件类型列表
        var chartList = {
            //1、饼形
            Pie2D: "Pie2D",
            Pie3D: "Pie3D",
            //2、线形
            Line: "Line",
            MSLine: "MSLine",
            ScrollLine2D: "ScrollLine2D",
            //3、环形
            Doughnut2D: "Doughnut2D",
            Doughnut3D: "Doughnut3D",
            //4、条形
            Bar2D: "Bar2D",
            MSBar2D: "MSBar2D",
            MSBar3D: "MSBar3D",
            StackedBar2D: "StackedBar2D",
            StackedBar3D: "StackedBar3D",
            //5、柱形
            Column2D: "Column2D",
            Column3D: "Column3D",
            MSColumn2D: "MSColumn2D",
            MSColumn3D: "MSColumn3D",
            ScrollColumn2D: "ScrollColumn2D",// 滚动条的柱形图
            StackedColumn2D: "StackedColumn2D",
            StackedColumn3D: "StackedColumn3D",
            ScrollStackedColumn2D: "ScrollStackedColumn2D",
            //6、区域形
            Area2D: "Area2D",
            ScrollArea2D: "ScrollArea2D",
            StackedArea2D: "StackedArea2D",
            MSArea: "MSArea"
            // 陆续待加...24
        };
        var chartType = chartList[type] || "MSColumn2D";// 如果类型错误 默认MSColumn2D
        return chartType;
    },
    /**
     * 加载数据
     *
     * @param {}
     *            componentData 传入的组件数据
     */
    loadChartData: function (componentData) {
        this.componentData = componentData;
        this.createChart();
    },
    /**
     * 根据模板类型过滤chart配置数据
     *
     * @param {}
     *            chart chart的配置参数
     * @return {}
     */
    filterChart: function (chart) {
        var tT = this.templateType;
        if (tT == 'quarters') {
            chart.showlegend = 0;// 四分屏模板不显示图例
        } else if (tT == 'single') {
            // chart.showlegend = 0;
            chart.legendposition = 'bottom';// 单分屏模板图例位置置于底部
        } else if (tT == 'classical') {
            // 默认
        } else {
            var s = this.chartType;
            chart.showValues = 1;
            if (s != 'Pie2D' && s != 'Pie3D' && s != 'Doughnut2D' && s != 'Doughnut3D')
                chart.showlegend = 0;// 不在维护范围内的模板不显示图例
        }
        return chart;
    },
    /**
     * 得到所有chart数据：样式+data+配置属性以及data的格式转换
     *
     * @return {}
     */
    getChartAllData: function () {
        var that = this, i, j, styles = that.getChartStyles(), chart = this
            .filterChart(that.getChartParams()), dData = that.componentData.displayData == null
            ? {}
            : that.componentData.displayData, dataArray = dData.data
            || [], modelArray = dData.model || [], len = modelArray.length;// 当范围或指标为空的时候,后台返回的是null,在前台的表现为dataArray和modelArray均未定义,因此当未定义时默认为[];

        var chartAllData = {};
        chartAllData.chart = chart;
        if (styles != null) {
            // 样式不为空则为chartAllData添加styles属性
            chartAllData.styles = styles;
        }

        if (that.isSimpleChart(that.chartType)) {
            // 随即产生一个0~modelArray.length之间的一个随即数。 用于随即展开一个扇
            var randomNum = Math.floor(Math.random() * (len - 1) + 0);
            var data = [];// 简单数据格式转换
            for (i = 0; i < len; i++) {
                var obj = {}, mc = modelArray[i];

                obj.label = mc;
                obj.color = that.getChartColor(that.chartType, i) || "";
                if (i == randomNum) {
                    obj.isSliced = '1';// 适用于饼、环的展开属性为1
                }
                obj.value = dataArray[0][mc];
                data.push(obj);
            }
            if (data.length != 0) {
                chartAllData.data = data;
            }
        } else if (that.isComplexChart(that.chartType)) {
            var categoryArray = [];// categories的数据转换
            for (i = 0; i < len; i++) {
                var obj = {};
                obj.label = modelArray[i];
                categoryArray.push(obj);
            }
            var datasetArray = [];// 转换dataset所需数据
            for (i = 0; i < dataArray.length; i++) {
                var obj = {};
                obj.seriesname = dataArray[i].seriesname;
                var _dataArray = [];
                for (j = 0; j < len; j++) {
                    var _obj = {};
                    _obj.value = dataArray[i][modelArray[j]];
                    _dataArray.push(_obj);
                }
                obj.data = _dataArray;
                obj.showvalues = 0,//适用于Stacked类型的图形
                    obj.color = that.getChartColor(that.chartType, i) || "";// 默认颜色为空
                datasetArray.push(obj);
            }
            // 格式转换后为chartAllData动态添加categories和datasheet属性
            if (categoryArray.length != 0 && datasetArray != 0) {
                chartAllData.categories = [
                    {
                        "category": categoryArray
                    }
                ];
                chartAllData.dataset = datasetArray;
            }
        } else {
            // 添加其他可能存在的情况或者子类覆盖此方法
        }
        // 如果数据为空
        if (dataArray.length == 0 || modelArray.length == 0) {
            // chartAllData.chart.bgSWF = './base/output/images/bg-border.gif';
            // 背景图需在同一个目录下
            // chartAllData.chart.canvasbgalpha = 30;
            // chartAllData.chart.fontsize = 40;
            // chartAllData.chart.bgcolor = 'DCD8EB';
            //chartAllData.chart.outCnvBaseFontSize = 40;
            // console.log(chartAllData);
            // 数据为空时设置的属性均不起作用,认为可能因为错误信息不在画布上
        }
        return chartAllData;
    },
    /**
     * 得到图形颜色放方法
     *
     * @param {}
     *            chartType 图形类型 根据类型调整不同的颜色列表
     * @param {}
     *            index 索引序号
     */
    getChartColor: function (chartType, index) {
        // 维护的默认的颜色列表
        var colorList = ["b2d9f8", "f6bc0c", "bbd66b", "f89f5b", "2ca9af",
            "e45f62", "ba84b6", "a1ba78", "ddd856"];
        //覆盖这里index随机取色也可
        try {
            if (index < colorList.length)
                return colorList[index];
            else
                return;
        } catch (e) {
            return;
        }
    },
    /**
     * 是否是简单图形
     *
     * @param {}
     *            chartType 图形类型
     * @return {Boolean} true表示该类型是简单类型
     */
    isSimpleChart: function (chartType) {
        var simpleChart = ["Pie2D", "Pie3D", "Area2D", "Column2D", "Column3D",
            "Line", "Bar2D", "Doughnut2D", "Doughnut3D", "ScrollArea2D"];// 维护的简单图形列表
        return this.isTrue(chartType, simpleChart);
    },
    /**
     * 是否是复杂图形
     *
     * @param {}
     *            chartType 图形类型
     * @return {Boolean} true表示该类型是复杂类型
     */
    isComplexChart: function (chartType) {
        var complexChart = ["MSColumn2D", "MSColumn3D", "MSLine", "MSBar3D",
            "MSBar2D", "MSArea"];// 维护的复杂图形列表
        return this.isTrue(chartType, complexChart);
    },
    /**
     * 判定是否维护在图形类型列表内
     *
     * @param {}
     *            chartType 图形类型
     * @param {}
     *            chartArray 图形维护数组
     * @return {Boolean} true值存在
     */
    isTrue: function (chartType, chartArray) {
        var i = 0, len = chartArray.length;
        for (; i < len; i++) {
            if (chartType == chartArray[i]) {
                return true;
            }
        }
        return false;
    },
    /**
     * 用于得到图形参数统一的参数配置（特殊参数配置放置在每个getChartParams方法内部实现）
     *
     */
    getCommenParamsForChart: function () {
        var caption = null, numbersuffix = null, chartData = this.componentData.displayData;//, yaxisname=null, xaxisname=null
        if (chartData != null) {
            caption = chartData.title;// 标题 caption 标题暂未让使用
            numbersuffix = chartData.unit;// 单位
            yaxisname = chartData.yaxisname;// y轴名称-暂未加入
            xaxisname = chartData.xaxisname;// x轴名称-暂未加入
        }
        /*
         * 关于饼、环形的问题：在fusioncharts中如果一个页面同时出现多个饼图或者环形图，则该图会变小（fusioncharts版本的bug,3.2中已修复）
         */
        allParamObj = {
            numbersuffix: numbersuffix || "",
            // yaxisname : yaxisname || "",
            // xaxisname : xaxisname || "",
            baseFontSize: '12',// 基本字体
            // outCnvBaseFontSize : '12',//画布之外字体
            baseFontColor: '6B6B6B',// 基本字体颜色
            showvalues: '0',// 是否显示值 1 是 0 否
            formatNumberScale: '0',// 是否格式化数字 1 是 会添加k m等英文单位
            showlabels: '0',// 是否显示标签
            legendBgColor: 'FFFFFF',// legend的背景色
            showlegend: '1',// 是否显示legend
            legendposition: 'right',// legend显示位置为右侧(四周均可)
            legendShadow: '1',// legend 的阴影显示
            legendBorderColor: 'FFFFFF',
            legendScrollBgColor: 'DEECFD',
            legendScrollBarColor: 'BAD1EF',
            legendScrollBtnColor: 'BAD1EF',
            // legendborderalpha : "0",// legend的边框宽度
            bgcolor: 'FFFFFF,FFFFFF',// 图形背景色
            showborder: '0',// 图形边框是否显示
            manageLabelOverflow: '1',// 字溢出时会自动隐藏
            useEllipsesWhenOverflow: '1',// 当溢出时,当Label溢出时候使用...将鼠标移至label处会显示
            // showAboutMenuItem : 0,// 是否显示关于fusioncharts
            aboutMenuItemLabel: '河南省精华科技有限公司',// 覆盖目录label
            aboutMenuItemLink: 'n-http://www.gilight.cn',
            zeroPlaneAlpha: '10',// 0线的像素
            use3DLighting: 1,// 是否使用3d光效果
            // defaultAnimation : 1,// 第一次是关闭动态展现
            // 添加下载
            // exportEnabled : 1,
            // exportShowMenuItem : 1,
            // exportFormats : 'PNG=导出png格式图片|JPG=导出jpg格式图片|PDF=导出pdf文件',
            // exportAction : 'download',
            // showExportDialog : 1,
            // exportAtClient : 0,
            // exportTargetWindow : '_slef',
            animation: '1'// 动态展现
        };
        if ('' != caption && null != caption) {
            allParamObj.caption = caption;// 主标题 这里后台不传递caption 其他使用者可以传递
            // 并可显示标题
        }
        return allParamObj;
    },
    /**
     * 子params覆盖父params 生产新的params
     *
     * @param {}
     *            childParam
     * @param {}
     *            parentParam
     * @return {} 返回新的params
     */
    coverChartParams: function (childParam, parentParam) {
        var newParams = null;
        for (var i in childParam) {
            parentParam[i] = childParam[i];
        }
        newParams = parentParam;
        return newParams;
    },
    /**
     * 得到图形配置参数 默认参数配置方法
     *
     * @return {} chart 图形配置参数
     */
    getChartParams: function () {
        var chart = this.getCommenParamsForChart();
        var baseChartParams = {
            canvasBorderThickness: '0',
            canvaspadding: "10"
        };
        for (var i in chart) {
            baseChartParams[i] = chart[i];
        }

        return baseChartParams;
    },
    /**
     * 得到图形样式
     *
     * @return {} styles 图形样式
     */
    getChartStyles: function () {
        var styles = {
            //"visibility": "inherit",
            "definition": [
                {
                    "type": "font",
                    "name": "captionFont",
                    "color": "6B6B6B",
                    "size": "12"
                }
            ],
            "application": [
                {
                    "toobject": "caption",
                    "styles": "captionFont"
                }
            ]
        };
        return styles;
    }
});/**
 * 图形组件父类
 */
Ext.define('Output.ChartParentComponent', {
    extend: 'Ext.Component',
    /**
     * 构造函数
     *
     * @param {}
     *            componentData 所有的组件数据
     */
    constructor: function (componentData) {
        this.callParent();
        this.componentData = componentData;
        var type = componentData.templateType || 'portal';//如果无模板类型 则默认portal类型
        var templateType = this.templateType = this
            .getTemplateType(type);// 得到模板类型
        this.componentData.templateType = templateType;// 将componentData数据内模板类型重置为转换后的类型
        this.componentList = [];// 声明componentList为空数组
        this.createChartComponent();
        this.addEvents('statuschange');
    },
    onRender: function () {
        this.callParent(arguments);
    },
    /**
     * 获取页面模板类型
     */
    getTemplateType: function (typeName) {
        // 类型维护区
        var type = {
            classical: 'classical',// 经典模板
            quarters: 'quarters',// 四分屏模板
            single: 'single'// 单分屏
            , portal: 'portal'//通用模板，该模板会仅仅展示图形 无交互
        };
        return type[typeName];// 默认经典模板
    },
    /**
     * 获取组件类型
     */
    getComponentType: function () {
        return this.componentData.componentType;
    },
    /**
     * 得到该组件
     */
    getLibComponent: function () {
        return this.container;
    },
    /**
     * 渲染方法
     *
     * @param {}
     *            id
     */
    render: function (id) {
        this.container.render(id);
    },
    _whenWindowLayout: function (me) {
        this.setContainerWidthAndHeight(me);
    },
    /**
     * 设置容器宽高
     *
     * @param {}
     *            me 容器本身
     */
    setContainerWidthAndHeight: function (me) {
        var tT = this.templateType;
        var pe = me.el.parent();
        var pwidth = pe.getWidth(), height;
        if (tT == 'quarters') {
            // 四分屏页面下
            pwidth = pe.parent().getWidth() * 0.46;
            height = pwidth * 1 / 2;
        } else if (tT == 'single') {
            // 单分屏下
            pwidth = pwidth * 0.9;
            height = pwidth * 2 / 5;
        } else if (tT == 'classical') {
            // var pheight = pe.getHeight()设置最大宽度 和最小宽度 然后再按照比例计算
            pwidth = pwidth * 0.8 > 900 ? 900 : pwidth * 0.8;
            height = (pwidth * 3) / 8;
        } else {
            //portal或者未知类型 则全部
            pwidth = "100%";
            height = this.componentData.height;
        }
        me.setWidth(pwidth);
        me.setHeight(height);
    },
    /**
     * 设置tbar的宽度（特殊情况也对高度做了调整）
     *
     * @param {}
     *            me
     */
    setTbarWidth: function (me) {
        var width = 0;
        for (var c = 0; c < this.componentList.length; c++) {
            var com = this.componentList[c];
            if (!com.hidden) {
                width += com.getWidth();
            }
        }
        me.setWidth(width);
        // 如果仅仅显示一个下拉框的话,将tbar高度置为27
        if (width == 150) {
            me.setHeight(27);
        }
    },
    /** 创建chart组件* */
    createChartComponent: function () {
        var that = this, obj = {};
        that.componentList = that.getChangeRangeComponents();
        that.getNewChart();
        //portal的模板 不再需要交互
        if (that.componentList.length != 0 && this.templateType != 'portal') {
            obj = {
                layout: 'border',
                border: false,
                xtype: 'container',
                baseCls: 'style="margin-left : 22px;background-color:white;"',
                frame: false,
                items: [
                    {
                        region: 'center',
                        border: false,
                        xtype: 'container',
                        layout: 'fit',
                        baseCls: '',
                        items: that.newChart.getLibComponent()
                    },
                    {
                        region: 'south',
                        border: false,
                        xtype: 'container',
                        items: [
                            {
                                xtype: 'toolbar',
                                baseCls: 'style="margin:0 auto;"',// 选择组件的位置css控制
                                items: that.componentList,
                                listeners: {
                                    afterrender: function () {
                                        that.setTbarWidth(this);
                                    }
                                }
                            }
                        ]
                    }
                ]
            };
        } else {
            obj = {
                layout: 'fit',
                border: false,
                items: that.newChart.getLibComponent()
            };
        }
        that.newChart.parentCt = this;
        this.container = Ext.create('Ext.container.Container', obj);
        this.container.addListener({
            afterrender: function () {
                that.setContainerWidthAndHeight(this);
            }
        });
    },
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        this.newChart = Ext.create('Output.Chart', this.componentData);
    },
    /**
     * 加载组件数据--仅针对图形本身（商议结果）
     *
     * @param {}
     *            chartData chart本身数据
     */
    loadData: function (chartData) {
        this.newChart.loadChartData(chartData);
    },

    /**
     * 得到选择范围组件
     */
    getChangeRangeComponents: function () {
        var componentList = [];
        if (this.componentData != null) {
            var that = this, dData = that.componentData.displayData == null
                ? {}
                : that.componentData.displayData, component = dData.components
                || [];
            for (var i = 0; i < component.length; i++) {
                componentList.push(that
                    .createChangeRangeComponent(component[i]));
            }
        }
        return componentList;
    },
    /**
     * 创建选择范围组件：单选组件、复选组件、下拉组件
     *
     * @param {}
     *            obj 传递的对象 obj格式： [ { comType : 'checkboxgroup', valueRange :
	 *            'wd',//数据类型 defaultValue : '值1,值2', data : [ { name : '名称1',//
	 *            显示值 value : '值1'// 实际值 }, { name : '名称2', value : '值2' } ] }, {
	 *            comType : 'radiogroup', valueRange : 'zb',//数据类型 defaultValue :
	 *            '值1', data : [ { name : '名称1',// 显示值 value : '值1'// 实际值 }, {
	 *            name : '名称2', value : '值2' } ] }, { comType : 'combox',
	 *            //defaultValue : '值1', valueRange : 'fw',//数据类型 data : [ {
	 *            name : '名称1',// 显示值 value : '值1'// 实际值 }, { name : '名称2',
	 *            value : '值2' } ] } ]
     */
    createChangeRangeComponent: function (obj) {
        var that = this, type = obj.comType, rangeComponent;// 组件类型，范围组件
        var name = obj.valueRange;// 得到名称
        var defaultValues = obj.defaultValue, defaultValuesList;// 默认值字符串
        if (defaultValues) {
            // defaultValuesList = defaultValues.split(",");//
            // 默认值数组，后台更改为数组形式了故而删除
            defaultValuesList = defaultValues;
        }
        var dataList = obj.data || [];// 数据集
        if (type == 'combox') {
            // 下拉框组件
            var states = Ext.create('Ext.data.Store', {
                fields: ['name', 'value'],
                data: dataList
            });

            var _obj = {
                store: states,
                style: {
                    "padding-top": '5px'
                },
                emptyText: '请选择...',
                queryMode: 'local',
                displayField: 'name',// 显示值
                valueField: 'value',// 实际值
                editable: false,// 不可编辑
                width: 150,// 默认下拉的宽度
                name: name
            };
            if (defaultValues) {
                _obj.value = obj.defaultValue;
            }
            // 如果是指标且指标数据长度小于2个，则隐藏之。name != 'wd' &&
            if (dataList.length == 1) {
                _obj.hidden = true;
            }
            rangeComponent = Ext.create('Ext.form.ComboBox', _obj);

        } else {
            // 数据处理
            var arrItems = [], columns = [], i = 0, width = 0;// 默认宽度0

            for (; i < dataList.length; i++) {
                var _obj = {}, j = 0;
                var boxLabel = _obj.boxLabel = dataList[i].name;
                // 检验字符串内是否包含数字
                // 得到字符 转换为array 根据具体情况做长度
                var k = 0, lablewidth = 16;// 默认的为图标长
                for (var z = 0; z < boxLabel.length; z++) {
                    var str = boxLabel.substring(k, k + 1);

                    if (str.match(/^[\u4e00-\u9fa5]$/)) {
                        lablewidth += 15;// 汉字
                        // lablewidth += 16;
                    } else if (!isNaN(str)) {// 数字
                        lablewidth += 10;
                        // lablewidth += 8;
                    } else {
                        lableWidth += 5;// 其他
                    }
                }
                width += lablewidth;
                columns.push(lablewidth);
                if (defaultValuesList) {
                    for (; j < defaultValuesList.length; j++) {
                        if (dataList[i].value == defaultValuesList[j]) {
                            _obj.checked = true;
                        }
                    }
                }
                _obj.name = name;
                _obj.inputValue = dataList[i].value;
                arrItems.push(_obj);
            }
            var bWidth = 20;// 间隙宽度
            width += bWidth;// 相应宽度加上间隙宽度
            columns.push(bWidth);// 添加间隙
            var obj_ = {
                name: name,
                columns: columns,
                width: width,
                items: arrItems
            };
            // 设置是否隐藏
            if (arrItems.length == 1) {
                obj_.hidden = true;
            }
            if (type == 'checkboxgroup') {
                // 创建复选框组件
                rangeComponent = Ext.create('Ext.form.CheckboxGroup', obj_);

            } else if (type == 'radiogroup') {
                // 创建单选组组件
                rangeComponent = Ext.create('Ext.form.RadioGroup', obj_);
            }
            // wdith = 0;
        }
        // 添加监听事件-当值改变时触发
        rangeComponent.addListener({
            change: function () {
                var status = {}, i;
                for (i = 0; i < that.componentList.length; i++) {
                    var array = [], component = that.componentList[i], name = component
                        .getName(), values, xtype = component.getXType();// 组件类型
                    if (xtype == 'checkboxgroup') {
                        values = component.getChecked();// 复选组
                        for (var j = 0; j < values.length; j++) {
                            array.push(values[j].inputValue);// 这个应该还取值
                        }
                    } else if (xtype == 'radiogroup') {
                        // 用单选控件,同一页面相同name的话,则相应也会change.所以每个组件在配置的时候请保持单个radiogroup！！
                        values = component.getValue();
                        var value = values[name] || null;
                        array.push(value);
                    } else if (xtype == 'combobox') {
                        values = component.getValue();// combobox的取值方法
                        array.push(values);
                    } else {
                    }
                    status[name] = array;
                }
                that.allStatus = status;
                that.fireEvent('statuschange', that.componentData.componentId);// 触发状态改变事件
            }
        });

        return rangeComponent;// 返回范围组件
    },
    /**
     * 得到组件所有状态ComponentAll
     *
     */
    getStatus: function () {
        return this.allStatus;
    }

});/**
 * 柱状图形
 */
Ext.define('Output.ColumnChart', {
    extend: 'Output.Chart',
    /**
     * 判断是否是Column Chart组件的简单形式
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean ture表示是柱形的简单形式组件
     */
    isSimpleChart: function (chartType) {
        return chartType == "Column2D" || chartType == "Column3D";
    },
    /**
     * 判断是否是Column Chart组件的复杂形式
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean true表示是柱形的复杂图形组件
     */
    isComplexChart: function (chartType) {
        return chartType == "MSColumn2D" || chartType == "MSColumn3D"
            || chartType == "ScrollColumn2D"
            || chartType == "StackedColumn2D"
            || chartType == "StackedColumn3D"
            || chartType == "ScrollStackedColumn2D";
    },
    /**
     * 得到chart的参数配置
     *
     * @return {} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;
        var chart = this.getCommenParamsForChart();

        // 柱状图形共有的属性配置
        var commonParams = {
            labelDisplay: 'AUTO',
            useEllipsesWhenOverflow: '1',
            useroundedges: '1',
            showlabels: '1',
            chartTopMargin: 22,
            chartLeftMargin: 0,
            decimals: '2',// 小数点的精确
            valuePadding: 5
            // 显示值到图形的距离
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Column2D') {
            chartParams = commonParams;
        } else if (chartType == 'Column3D') {
            chartParams = {
                canvasBgColor: 'F5FFFA,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn2D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
                anchorMinRenderDistance: '6',
                drawanchors: "0",
                labelpadding: "10"
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn3D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
//				drawanchors : "0",
                labelpadding: "10",
                canvasBgColor: 'FFFFFF,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'ScrollColumn2D') {
            chartParams = {
                pinLineThicknessDelta: 30
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == "StackedColumn2D"
            || chartType == "ScrollStackedColumn2D") {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
                anchorMinRenderDistance: '6',
                drawanchors: "0",
                showsum: "1",
                useroundedges: "1",
                bgcolor: "FFFFFF,FFFFFF",
                labelpadding: "10"
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else {
            chartParams = {};
            chartParams = this.coverChartParams(chartParams, commonParams);
        }
        return chartParams;
    }
});/**
 * 柱状图形组件
 */
Ext.define('Output.ColumnChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 得到new chart对象
     * 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        var that = this;
        that.newChart = Ext.create('Output.ColumnChart', that.componentData);
    }
});/**
 * 线状图形
 */
Ext.define('Output.LineChart', {
    extend: 'Output.Chart',
    isSimpleChart: function (chartType) {
        return chartType == "Line";
    },
    isComplexChart: function (chartType) {
        return chartType == "MSLine" || chartType == "ScrollLine2D";
    },
    /**
     * 得到chart的参数配置
     *
     * @return {} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;

        var chart = this.getCommenParamsForChart();

        var commonParams = {
            useroundedges: '1',
            legendShadow: '0',
            labelDisplay: 'AUTO',// 自动下移字
            decimals: '2',// 小数点的精确
            showlabels: '1',
            legendBorderColor: '',
            chartTopMargin: 22,
            chartLeftMargin: 0
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Line') {
            chartParams = {
                palette: "2",
                canvasBorderThickness: '0',
                canvasbgAlpha: '10',
                canvaspadding: "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == 'MSLine') {
            chartParams = {
                palette: "2",
                // labelpadding : "10",
//						divlinealpha : "30",
                canvasBorderThickness: '0',//边框款第
                // canvasBorderColor:'FFFFFF',//边框颜色
                canvasBgColor: 'F5FFFA,FFFFFF',// 画布颜色
                canvasbgAlpha: '10',
//						alternatehgridalpha : "20",
                canvaspadding: "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == "ScrollLine2D") {
            chartParams = {
                canvasbgAlpha: '10',
                canvaspadding: "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        }
        return chartParams;
    }
});/**
 * 线状图形组件
 */
Ext.define('Output.LineChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 得到new chart对象
     * 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        var that = this;
        that.newChart = Ext.create('Output.LineChart', that.componentData);
    }
});/**
 * 环状图形
 */
Ext.define('Output.DoughnutChart', {
    extend: 'Output.Chart',
    /**
     * 判断是否是Column Chart组件的简单形式
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean ture表示是柱形的简单形式组件
     */
    isSimpleChart: function (chartType) {
        return chartType == "Doughnut2D" || chartType == "Doughnut3D";
    },
    /**
     * 环形无复杂图形
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {} boolean true表示是柱形的复杂图形组件
     */
    isComplexChart: function (chartType) {
        return false;
    },
    getChartColor: function (chartType, index) {
        var colorList = ['c4d0a2', 'acacac', 'aac165', '7d7d7d'];
        try {
            return colorList[index];
        } catch (e) {
            return;
        }
    },
    /**
     * 得到chart的参数配置
     *
     * @return {} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;
        var chart = this.getCommenParamsForChart();
        var commonParams = {
            skipOverlapLabels: '1',
            legendShadow: '0',// legend 的阴影显示
//					enableRotation:1,//是否开启旋转,这个属性跟点击图形展开冲突（它俩是互斥关系）
            // legendBorderColor : 'FFFFFF',
            showvalues: '1',
            palette: 2,// 内置样式1-5
            // showZeroPies:'0',//是否显示0值
            enableSmartLabels: 0
            // 设置是否连接线
            // chartTopMargin : 0,
            // chartLeftMargin:20,
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Doughnut2D') {
            chartParams = {};
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == 'Doughnut3D') {
            chartParams = {
//						pieYScale:'180'//图立起来的角度,角度越大,图形展示就越大
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        }
        return chartParams;
    }
});/**
 * 柱状图形组件
 */
Ext.define('Output.DoughnutChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 设置容器宽高
     *
     * @param {}
     *            me 容器本身
     */
    setContainerWidthAndHeight: function (me) {
        var templateType = this.componentData.templateType;
        var pe = me.el.parent(), pwidth = pe.getWidth(), height;
        if (templateType == 'single') {
            // 单分屏的容器宽高
            height = pwidth * 2 / 5;
            pwidth = pwidth * 0.9;
        } else if (templateType == 'classical') {
            // 在经典模板内是这种设置。如果换做其他模版的话，再做相应的细节调整
            pwidth = pwidth * 0.4 > 500 ? 500 : pwidth * 0.4;
            height = (pwidth * 4) / 5;// 高宽比 1:1.25
        } else if (templateType == 'quarters') {
            pwidth = pe.parent().getWidth() * 0.46;
            height = pwidth / 2;
        } else {
            // portal或者未知类型 则全部
            pwidth = "100%";
            height = this.componentData.height;
        }

        me.setWidth(pwidth);
        me.setHeight(height);
    },
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        this.newChart = Ext.create('Output.DoughnutChart',
            this.componentData);
    }
});/**
 * 饼状图形
 *
 * @author haw_king
 *         @example
 *         Ext.create('Ext.PieChart',{
 *          title : '标题名称',
			componentType : 'Pie2D',
			unit : '头',
			model : [ '牛', '羊', '猪', '马' ],// 维度名称
			data : [ {
				"牛" : '200',
				"羊" : '230',
				"猪" : '120',
				"马" : '130',
				label : 'hide'//饼图此处隐藏
	} ]
 *      });
 *      chart.getLibComponent();//放到panel内或者其他容器内即可
 */
Ext.define('Output.PieChart', {
    extend: 'Output.Chart',
    /**
     * 判断是否是pieChart组件
     *
     * @param {}
     *            chartType 经转换后于chart图形一一对应的chart组件类型
     * @return {}
     */
    isSimpleChart: function (chartType) {
        return chartType == "Pie2D" || chartType == "Pie3D";
    },
    /**
     * 饼图无复杂图形 所有均返回false
     *
     * @return {Boolean}
     */
    isComplexChart: function () {
        return false;
    },
    getChartColor: function (chartType, index) {
        var colorListFor2D = ['b2d9f8', 'f6bc0c', 'bbd66b', 'cb97fe', 'ff94b2'];
        var colorListFor3D = ['b2d9f8', 'f6bc0c', 'bbd66b', 'f89f5b', '2ca9af',
            'e45f62', 'ba84b6', 'a1ba78', 'ddd856'];
        try {
            if (chartType == 'Pie2D') {
                return colorListFor2D[index];
            } else if (chartType == 'Pie3D') {
                return colorListFor3D[index];
            } else {
                return;
            }
        } catch (e) {
            return;// 数组越界的时候返回（颜色则自定义）
        }
    },
    /**
     * 得到chart图形的参数配置
     *
     * @return {} chart参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;

        var chart = this.getCommenParamsForChart();
        var commonParams = {
            enableSmartLabels: 0,
//			enableRotation:1,//是否开启旋转,这个属性跟点击图形展开冲突（它俩是互斥关系）
            // showZeroPies:'0',//是否显示0的饼 默认1
            skipOverlapLabels: '1',
            legendShadow: '0',// legend 的阴影显示
            // legendBorderColor : 'FFFFFF',
            palette: 2,// 内置样式1-5
            showvalues: '1'
            // ,
            // captionPadding : '10'// 标题到画布的距离
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Pie2D') {
            chartParams = {
                bgalpha: "60"
                // ,pieRadius : '100'// 饼图的半径
                // bgratio : "100"//背景比例
            };
            chartParams = this.coverChartParams(chartParams, commonParams);
        } else if (chartType == 'Pie3D') {
            chartParams = commonParams;
        }
        return chartParams;
    },
    /**
     * 得到chart类型配置
     *
     * @return {} 暂不设置其样式数据
     */
    getChartStyles: function () {
        var styles = {
            "definition": [
                {
                    "type": "font",
                    "name": "captionFont",
                    "color": "6B6B6B",
                    "size": "12"
                }
            ],
            "application": [
                {
                    "toobject": "caption",
                    "styles": "captionFont"
                }
            ]
        };
        return styles;
    }
});/**
 * 饼状图形组件
 */
Ext.define('Output.PieChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 设置容器宽高
     *
     * @param {}
     *            me 容器本身
     */
    setContainerWidthAndHeight: function (me) {
        var templateType = this.componentData.templateType;
        var pe = me.el.parent(), pwidth = pe.getWidth(), height;
        if (templateType == 'single') {
            // 单分屏的容器宽高
            height = pwidth * 2 / 5;
            pwidth = pwidth * 0.9;
        } else if (templateType == 'classical') {
            // 在经典模板内是这种设置。如果换做其他模版的话，再做相应的细节调整
            pwidth = pwidth * 0.4 > 500 ? 500 : pwidth * 0.4;
            height = (pwidth * 4) / 5;// 高宽比 1:1.25
        } else if (templateType == 'quarters') {
            pwidth = pe.parent().getWidth() * 0.46;
            height = pwidth / 2;
        } else {
            // portal或者未知类型 则全部
            pwidth = "100%";
            height = this.componentData.height;
        }
        me.setWidth(pwidth);
        me.setHeight(height);
    },
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        var that = this;
        that.newChart = Ext.create('Output.PieChart',
            that.componentData);
    }
});/**
 * @class Output.BarChart
 * bar图形
 */
Ext.define('Output.BarChart', {
	extend : 'Output.Chart',
	isSimpleChart : function(chartType) {
		return chartType == "Bar2D";
	},
	isComplexChart : function(chartType) {
		return chartType == "MSBar2D" || chartType == "MSBar3D"
				|| chartType == "StackedBar2D" || chartType == "StackedBar3D";
	},
	/**
	 * 得到chart的参数配置
	 * 
	 * @return {} chart 参数配置
	 */
	getChartParams : function() {
		var chartType = this.chartType, chartParams = null;
		var chart = this.getCommenParamsForChart();
		var commonParams = {
			useroundedges : '1',
			legendShadow : '0',
			labelDisplay : 'AUTO',// 自动下移字
			decimals : '2',// 小数点的精确
			showlabels : '1',
			chartTopMargin : 22,
			chartRightMargin : 22,
			chartLeftMargin : 0
		};
		commonParams = this.coverChartParams(commonParams, chart);
		if (chartType == 'Bar2D') {
			chartParams = {};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else if (chartType == 'MSBar2D') {
			chartParams = {
				legendShadow : '1'
			};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else if (chartType == 'MSBar3D') {
			chartParams = {};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else if (chartType == 'StackedBar2D') {
			chartParams = {
				legendShadow : '1',
				showsum : "1"// 显示总和
			};
			chartParams = this.coverChartParams(chartParams, commonParams);
		} else {
			chartParams = {};
			chartParams = this.coverChartParams(chartParams, commonParams);
		}
		return chartParams;
	}
});/**
 * bar图形组件
 */
Ext.define('Output.BarChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 设置容器宽高
     *
     * @param {}
     *            me 容器本身
     */
    setContainerWidthAndHeight: function (me) {
        var templateType = this.componentData.templateType;
        var pe = me.el.parent(), pwidth = pe.getWidth(), height;
        if (templateType == 'single') {
            // 单分屏的容器宽高
            height = pwidth * 2 / 5;
            pwidth = pwidth * 0.8;
        } else if (templateType == 'classical') {
            // 在经典模板内是这种设置。如果换做其他模版的话，再做相应的细节调整
            pwidth = pwidth * 0.5 > 700 ? 700 : pwidth * 0.5;
            height = (pwidth * 2) / 3;// 高宽比 1:1.5
        } else if (templateType == 'quarters') {
            pwidth = pe.parent().getWidth() * 0.46;
            height = pwidth / 1.6;
        } else {
            // portal或者未知类型 则全部
            pwidth = "100%";
            height = this.componentData.height;
        }
        me.setWidth(pwidth);
        me.setHeight(height);
    },
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        this.newChart = Ext.create('Output.BarChart',
            this.componentData);
    }
});/**
 *@class Output.AreaChart
 */
Ext.define('Output.AreaChart', {
    extend: 'Output.Chart',
    isSimpleChart: function (chartType) {
        return chartType == "Area2D";
    },
    isComplexChart: function (chartType) {
        return chartType == "MSArea" || chartType == "StackedArea2D"
            || chartType == "ScrollArea2D";
    },
    /**
     * 得到chart的参数配置
     *
     * @return {} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType, chartParams = null;
        var chart = this.getCommenParamsForChart();
        var commonParams = {
            useroundedges: '1',
            legendShadow: '0',
            labelDisplay: 'AUTO',// 自动下移字
            decimals: '2',// 小数点的精确
            legendBorderColor: '',
            canvasBorderThickness: '0',
            canvaspadding: "10",
            showlabels: '1',
            chartLeftMargin: 0
        };
        commonParams = this.coverChartParams(commonParams, chart);
        if (chartType == 'Area2D') {
            chartParams = commonParams;// 样式
        } else if (chartType == 'MSArea') {
            chartParams = {
//					palette : "2",
                canvasBorderThickness: '0',
                canvasBgColor: 'F5FFFA,FFFFFF',// 画布颜色
                canvasbgAlpha: '10'
//					canvaspadding : "40"
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else if (chartType == "ScrollArea2D") {
            chartParams = commonParams;
        } else if (chartType == "StackedArea2D") {
            chartParams = {
                showsum: "1"// 显示总和
            };
            chartParams = this.coverChartParams(chartParams,
                commonParams);
        } else {
            chartParams = commonParams;
        }
        return chartParams;
    }
});/**
 * @class Output.AreaChartComponent
 * area图形组件
 */
Ext.define('Output.AreaChartComponent', {
    extend: 'Output.ChartParentComponent',
    /**
     * 得到new chart对象 该方法是为了chart组件的重写
     */
    getNewChart: function () {
        this.newChart = Ext.create('Output.AreaChart',
            this.componentData);
    }
});/*******************************************************************************
 * 联动组件。
 *
 * @param {}
 *            cData={ type:'linkage', items:[ {'全部 ',' 本日',' 本周',' 本月',' 本年','
 *            自定義' text:'全部', params:{ beginDate:'', endDate:"" } },{ text:'本日',
 *            params:{ } },{ text:'本周', params:{ } } ] }
 *
 */
NS.define('Output.DateLinkage', {
    /*******************************************************************
     * 构造函数
     */
    constructor: function (config) {
        this.config = config;// 将参数设置为内部变量
        US.apply(this, config);
        this.buildDom();
    },
    buildDom: function () {
        // 创建一个组件内的父节点div，以便挂接到外界的DOM节点上，完成组件的相互组装。
        var interfaceDIV = document.createElement('DIV');
        interfaceDIV.className = 'gn-space';
        var itemsContent = this.config.displayData.data['idos'];
        for (var index in itemsContent) {
            (function (_index) {
                var aLink = document.createElement('A');
                aLink.href = '#';
                aLink.onclick = function () {
                    var params = itemsContent[_index].params;
                    /*将当前被点击连接的，文本和参数缓存起来*/
                    this.params = params;
                    this.text = itemsContent[_index].text;
                    /*触发事件*/
                    me.fireEvent('datelinkage', this.config.componentId);
                }
                aLink.appendChild(document.createTextNode(itemsContent[_index].text));
                interfaceDIV.appendChild(aLink);
            })(index);
        }
        this.myDom = interfaceDIV;
        this.createComponent();
    },
    /***
     * 创建用以反回getLibComponent 方法反回的对象
     **/
    createComponent: function () {
        var me = this;
        var basic = {
            baseCls: '',
            xtype: 'container',
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.myDom);
                }
            }
        };
        US.apply(basic, {});
        this.component = new Ext.container.Container(basic);
    },
    /*******************************************************************
     * 获取并返回当前组件id的状态属性。
     */
    getStatus: function () {
        var status = {},
            value = new Array();
        value.push(/*当前被选中的值*/);
        status['statusText'] = this.text;
        ;
        status['statusParams'] = this.params;
        return status;
    },
    /*******************************************************************
     * 刷新组件。
     */
    loadData: function (cData) {

    },
    /*******************************************************************
     * 获取用于和底层类库交互的组件对象。
     */
    getLibComponent: function () {
        return this.component;
    },
    /*******************************************************************
     * 将创建的原生dom节点渲染到制定的dom节点上。
     *
     * @param {}
     *            id 该参数可以是dom节点的id，也可以是dom对象。
     */
    renderTo: function (id) {
        if (id.nodeType = 1) {
            id.appendChild(this.myDom);
        } else {
            document.getElementById(id).appendChild(this.myDom);
        }
    },
    /**
     *获取组件类型
     */
    getComponentType: function () {
        return this.config.componentType;
    },
    setWidthAndHeight: function (width, height) {
        var C = this.component;
//				C.setWidth(width);
//				C.setHeight(height);
    }
});
/*******************************************************************************
 * 定义树形联动组件
 */
NS.define('Output.TreeLinkage', {
    /*******************************************************************
     * 初始化构造函数
     */
    constructor: function () {
        this.initData.apply(this, arguments);
        this.initComponent();
        this.addEvents('linkage');
    },
    /*******************************************************************
     * 初始化数据
     */
    initData: function (config) {
        this.config = config;
        US.apply(this, config);
        this.root = this.displayData.menuTree;
        this.root.text = '全校';
        this.createNodeMap(this.root);// 创建节点hash表
    },
    /*******************************************************************
     * 初始化组件
     *
     * @return {}
     */
    initComponent: function () {
        this.initDom();
        this.createComponent();
    },
    /*******************************************************************
     * 获取用于和底层类库进行交互的组件
     */
    getLibComponent: function () {
        return this.component;
    },
    /***
     * 获取组件类型
     * @return {}
     */
    getComponentType: function () {
        return this.componentType;
    },
    /***
     * 获取组件状态
     * @return
     */
    getStatus: function () {
        return this.status;
    },
    /*******************************************************************
     * 创建用于和底层类库进行交互的组件
     */
    createComponent: function () {
        var me = this;
        var basic = {
            baseCls: '',
            listeners: {
                'afterrender': function () {
                    this.el.appendChild(me.pdiv);
                }
            }
        };
        US.apply(basic, {});
        this.component = new Ext.container.Container(basic);
    },
    /***
     * 初始化dom
     * @param {} root
     */
    initDom: function () {
        this.spanArray = [];
        this.pdiv = document.createElement('DIV');
        this.titleDiv = document.createElement('DIV');
        this.titleDiv.appendChild(this.createIndexSpan(this.root));
        this.pdiv.appendChild(this.titleDiv);
    },
    // 移除标题容器中的span
    removeSpan: function (nodeid) {
        if (this.root.id == nodeid && this.spanArray.length == 1)
            return;
        var flag = false;
        var bak = []
        for (var i = 0; i < this.spanArray.length; i++) {
            var obj = this.spanArray[i];
            if (!flag) {
                bak.push(obj);
                if (obj.id == nodeid) {
                    flag = true;
                    continue;
                }
            }
            if (flag) {
                this.titleDiv.removeChild(obj.span);
            }
        }
        this.spanArray = bak;
    },

    // 创建索引span
    createIndexSpan: function (node) {

        var me = this;
        return (function (node) {
            var span = document.createElement('span');
            var obj = {};
            obj.id = node.id;
            obj.span = span;
            me.spanArray.push(obj);

            var text1 = document.createTextNode('>>');
            var text2 = document.createTextNode(node.text);
            var a = document.createElement('a');
            a.href = '#';
            a.appendChild(text2);
            if (node !== me.root)
                span.appendChild(text1);
            span.appendChild(a);
            a.onclick = function () {
                me.status = {nodeId: node.id};
                me.fireEvent('linkage');
                me.removeSpan(node.id);
                if (node.children.length != 0) {
                    me.removeChildDiv();
                    var cdiv = me.cdiv = me.createDiv(node.children);
                    me.pdiv.appendChild(cdiv);

                }
                return false;
            };
            return span;
        })(node);
    },
    // 创建A节点
    createA: function (node) {
        var me = this;
        return (function (node, me) {
            var a = document.createElement('A');
            a.href = '#';
            a.appendChild(document.createTextNode(node['text']));
            a.onclick = function () {
                if (node.children.length != 0) {
                    me.status = {nodeId: node.id};
                    me.fireEvent('linkage');
                    me.removeChildDiv();
                    var cdiv = me.cdiv = me.createDiv(node.children);
                    me.titleDiv.appendChild(me.createIndexSpan(node));
                    me.pdiv.appendChild(me.cdiv);
                }
                return false;
            };
            return a;
        })(node, me);
    },
    // 创建子div
    createDiv: function (nodes) {
        var div = document.createElement('DIV');
        for (var i = 0, len = nodes.length; i < len; i++) {
            var node = nodes[i];
            div.appendChild(this.createA(node));
            div.appendChild(this.createText());
        }
        return div;
    },
    // 删除DIV子节点
    removeChildDiv: function () {
        if (this.cdiv)
            this.pdiv.removeChild(this.cdiv);
    },

    // 创建空白text节点
    createText: function () {
        return document.createTextNode("   ");
    },
    // 创建节点map,以id为键，节点为值
    createNodeMap: function (root) {
        this.nodeMap = {};
        this.nodeMap[root.id] = root;
        var iterator = root.children;
        var biter = [];
        while (iterator.length != 0) {
            for (var i = 0, len = iterator.length; i < len; i++) {
                var node = iterator[i];
                this.nodeMap[node.id] = node;
                biter = biter.concat(node.children);
            }
            iterator = biter;
            biter = [];
        }
    }
});/***
 * 定义输出组件窗体
 */
NS.define('Output.PopWindow', {
    /***
     * 构造函数
     */
    constructor: function () {
        this.initData.apply(this, arguments);
    },
    /***
     * 初始化组件数据
     */
    initData: function (config) {
        var me = this;
        me.config = config;
        Output.Model.prototype.gridHeaderRequest(config.entityName, function (data) {
            me.config.data = data;
            me.dataConvert();
            me.initComponent();
        });
    },
    /***
     * 初始化组件
     */
    initComponent: function () {
        this.initView();
    },
    /**
     * 初始化数据
     */
    dataConvert: function () {
        var data = this.config.data;
        var id = this.config.id;
        var queryTableContentParam = 'queryTableContent,queryGridResult,"id":' + id;
        var proxy = US.CommonUtil.getProxyForGrid([queryTableContentParam]);
        proxy.reader = {
            type: 'json',
            root: 'queryTableContent.data',// 读取的主要数据 键
            totalProperty: 'queryTableContent.totalCount'
            // 数据总量
        };
        this.tranData = US.CommonUtil.getTranData(data, proxy);
    },
    initView: function () {
        var grid = this.getGrid();
        var body = Ext.getBody();
        this.window = Ext.create('Ext.window.Window', {
            width: body.getWidth() - 200,
            height: body.getHeight() - 40,
            modal: true,
            layout: 'fit',
            maximizable: true,
            minimizable: true,
            autoShow: true,
            items: grid,
            tbar: this.getTbar()
        });
    },
    /***
     * 获取gridpanel
     * @return {}
     */
    getGrid: function () {
        var gridzz = this.gridzz = new ModelOne.GridZz({
            componentData: this.tranData
        });
        gridzz.initComponent();
        return gridzz.getGrid().grid;
    },
    getTbar: function () {
        var tbar = new Ext.toolbar.Toolbar({border: true});
        var single = this.getSingleField().getFieldSet();
        var senior = this.getSeniorQuery();
        tbar.add(single, '->', senior);
        return tbar;
    },
    getSingleField: function () {
        var me = this;
        var singleField = new ModelOne.SingleFieldQuery({
            tranData: this.tranData
            // 转换后的数据
        });
        singleField.addUSListener('doQuery', function () {// 所需要执行的查询操作
            var params = singleField.getParams();// 获得查询参数
            me.gridzz.getStore().load(params);// 根据传递的参数加载数据
        });
        singleField.initComponent();
        return singleField;
    },
    getSeniorQuery: function () {
        var me = this;
        var tranData = me.tranData;// 转换的数据
        var data = {
            fieldsStoreData: tranData.fieldsStoreData,
            componentsData: tranData.componentsData,
            container: this.gridContainer
        };
        var seniorQuery = new ModelOne.SeniorQuery(data);// 创建高级查询组件
        seniorQuery.addUSListener('doQuery', function (params) {
            me.gridzz.getStore().load(params);
        });
        var seniorButton = {
            xtype: 'button',
            iconCls: 'page-xtsz',
            text: '高级查询',
            handler: function () {
                seniorQuery.initComponent();// 高级查询组件初始化();// 通过点击创建高级查询组件
            }
        };
        return seniorButton;
    }
});NS.define('Output.PageBuilder', {
    constructor: function (data) {
        this.data = data;
        this.createPage(data);
    },
    /***
     * 创建页面
     * @param {} data
     */
    createPage: function (data) {
        var pageId = this.pageId = data.pageId;// 获取页面id
        var title = data.title;// 页面标题
        var items = data.items;
        var tarray = [];
        for (var i = 0; i < items.length; i++) {
            tarray.push(this.createTemplate(items[i]).getLibComponent());
        }
        if (tarray.length == 1) {
            this.component = Ext.create('Ext.container.Container', {
                title: data.title,
                autoScroll: true,
                items: tarray
            });
        }
    },
    /***
     * 获取页面Id
     * @return {}
     */
    getPageId: function () {
        return this.pageId;
    },
    /***
     * 获取基于底层类库交互用的组件
     * @return {}
     */
    getLibComponent: function () {
        return this.component;
    },
    /***************************************************************************
     * 创建模版对象
     * @param templateData 模版数据
     */
    createTemplate: function (templateData) {
        var componentManager = this.createComponentManager(templateData);//创建组件管理器
        this.bindListeners(componentManager);//绑定监听事件
        var body = this.getTemplateBody(componentManager, templateData);//获得模版组装对象(用于生成模版)
        var template = Output.TemplateBuilder.createTemplate(body);//创建模版
        return template;
    },
    /***
     * 创建组件管理器
     * @param {Object} templateData
     */
    createComponentManager: function (templateData) {
        var CM = new Output.ComponentManager(templateData.components);//创建组件管理器
        return CM;
    },
    /***
     * 绑定监听事件
     */
    bindListeners: function (componentManager) {
        var bindManager = Output.EventBindManager;//事件绑定管理器
        bindManager.bindEvents(componentManager.getComponents());//绑定事件
    },
    /***
     * 将组件管理器以及模版数据进行模版对象体的组装
     * @param {} componentManager
     * @param {} templateData
     * @return Components{Array}
     */
    getTemplateBody: function (componentManager, templateData) {
        var builder = Output.TemplatePackager;//模版组装工具
        templateData.componentManager = componentManager;
        var body = builder.getTemplateBody(templateData);//组装模版
        return body;
    }
});/****
 * 模版构造器（类）
 */
NS.define('Output.TemplateBuilder', {
    /***
     * 实例化工具类
     * @type Boolean
     */
    singleton: true,
    typeTemplateMap: {
        'quarters': 'Output.QuartersTemplate',//四分屏
        'classical': 'Output.ClassicalTemplate',//经典模版
        'single': 'Output.SingleTemplate',//单分平模版
        'embedded': Output.Embedded//内嵌式模版
    },
    /****
     * 根据模版配置数据以及组件管理器，创建模版信息
     * @param {} templateconfig
     * @param {} componentManager
     * @return  Template
     */
    createTemplate: function (tbody) {
        var Class = NS.ClassManager.getClassByNameSpace(this.typeTemplateMap[tbody.templateType]);//个人意见：所有的创建方法均改造为构造函数写法~
        return new Class(tbody);
    }
    //模版可放置任意地点
});/****
 * 组件管理器（类）
 */
NS.define('Output.ComponentManager', {
    typeComponentMap: {
        'pie': 'Output.PieChartComponent',
        'doughnut': 'Output.DoughnutChartComponent',
        'line': 'Output.LineChartComponent',
        'column': 'Output.ColumnChartComponent',
        'bar': 'Output.BarChartComponent',
        'area': 'Output.AreaChartComponent',
        'text': 'Output.Text',
        'table': 'Output.Table',
        'datelinkage': 'Output.DateLinkage',
        'treelinkage': 'Output.TreeLinkage'//树形联动组件
    },
    /***
     * 构造函数
     * @param {} componentsData
     */
    constructor: function (componentsData, templateType) {
        this.components = {};//声明组件集合
        this.comArray = [];//组件数组
        this.templateType = templateType;
        this.createComponents(componentsData);
    },
    /***
     * 通过组件数据集合创建组件对象
     * @param componentsData 组件数据集合
     */
    createComponents: function (componentsData) {
        for (var i = 0, len = componentsData.length; i < len; i++) {
            this.createComponent(componentsData[i]);
        }
    },
    /****
     * 创建组件 根据传递的数据创建对应的组件
     * @param cdata 组件数据 参照定义组件数据格式
     * @return Component
     */
    createComponent: function (cdata) {
        var type = cdata.componentType;
        cdata.templateType = this.templateType,classname = this.typeComponentMap[type];//组件创建时添加-模版类型-树形
        var Class = NS.ClassManager.get(classname)||NS.ClassManager.getClassByNameSpace(classname);//根据组件类型获取对应类
        if (!Class)return null;
        var component = new Class(cdata);//不用构造函数的原因？
        if (type == 'treelinkage' || type == 'datelinkage') {
            this.linkage = component;
        }
        this.register(cdata.componentId, component);
    },
    /****
     * 注册，将对象的注册进入组件管理器中
     * @param {String/Number}id 数据库组件标识Id
     * @param {Object}status 状态对象
     */
    register: function (id, component) {
        this.components[id] = component;
        this.comArray.push(component);
    },
    /***
     * 根据组件的数据库组件标识id获取组件
     * @param {String/Number}id 数据库组件标识Id
     * @return Component
     */
    getComponentById: function (id) {
        return this.components[id];
    },
    /**
     * 联动组件状态--每个组件事件触发的时候都调用它
     */
    getLinkComStatus: function (id) {
        //通过id得到该联动组件  调用其方法即可
        if (this.linkage) {
            return this.linkage.getStatus();
        } else {
            return null;
        }
    },
    /***
     * 获取组件的状态
     */
    getComponentStatus: function (componentId) {
        var linkstatus = this.getLinkComStatus();//获取联动组件状态
        var comstatus = this.getComponentById(componentId).getStatus();//获取组件的状态
        var params = {
            pageId: this.getPageId(),//页面id
            templateId: this.getTemplateId(),//获取模版id
            requestType: 'linkage',//联动请求类型,
            components: [
                {
                    componentId: componentId,
                    params: comstatus
                }
            ]
        };
        if (linkstatus)
            US.apply(params, {globalParams: linkstatus});
        else
            US.apply(params, {globalParams: {}});
        return params;
    },
    /**获取组件管理器管理的所有组件**/
    getComponents: function () {
        return this.comArray;
    },
//--------------------------------------------------
    /**
     * 得到所有组件的状态--联动组件触发的时候获取页面其他组件的所有状态
     */
    getAllStatus: function () {
        //应该是模版获取的吧？？？？？？
        var components = [];
        var comArray = this.comArray;
        var linkage = this.linkage;
        for (var i = 0, len = comArray.length; i < len; i++) {
            var com = comArray[i];
            if (com == linkage) continue;
            components.push(com.getStatus());
        }
        var status = {//所获取的状态
            pageId: this.getPageId(),
            templateId: this.getTemplateId(),
            components: components
        };
        if (linkage) {
            status.global = linkage.getStatus();
        }
        return status;
    },
    /**
     * @param data:[{id1:{数据}},{id2:{数据2}}]
     *
     */
    loadData: function (pageData) {
        var data = pageData.pageInitData.templates[0].components;
        for (var i = 0, len = data.length; i < len; i++) {
//		   for(var param in data[i]){
//		    this.getComponentById(param).loadData(data[param]);
//		    }
            var C = data[i];
            this.getComponentById(C.componentId).loadData(C);
        }
    },
    /***
     * 获取页面id
     */
    getPageId: function () {
        return this.pageId;
    },
    /***
     * 设定组件管理器页面id
     * @param {} pageId
     */
    setPageId: function (pageId) {
        this.pageId = pageId;
    },
    /***
     * 获取模版id
     * @return {}
     */
    getTemplateId: function () {
        return this.templateId;
    },
    /***
     * 设置组件管理器模版id
     * @param {} templateId
     */
    setTemplateId: function (templateId) {
        this.templateId = templateId;
    },
    /**
     * 添加组件监听
     */
    addComListener: function (id, eventName, listener) {

    }
});/***
 * 事件绑定器（类）
 */
NS.define('Output.EventBindManager', {
    /***
     * 实例化类
     */
    /***
     * 构造函数
     */
    constructor: function () {
        var me = this;
        me.listeners = {
            'statuschange': me.statusChange,//状态改变事件
            'pop': me.doPopfun,//弹窗事件
            'linkage': me.linkage//联动
        };
        me.componentListeners = {
            'text': ['pop'],
            'datelinkage': ['linkage'],
            'treelinkage': ['linkage'],
            'table': ['statuschange'],
            'column': ['statuschange'],
            'doughnut': ['statuschange'],
            'pie': ['statuschange'],
            'bar': ['statuschange'],
            'area': ['statuschange'],
            'line': ['statuschange']
        };
    },
    /****
     * 根据组件以及传递的组件的事件名，对该组件该事件进行绑定
     * @param {} component
     * @param {} event
     */
    bindEvent: function (component) {
        var me = this;
        var ctype = component.getComponentType();
        var listeners = me.componentListeners[ctype];
        for (var i = 0, ln = listeners.length; i < ln; i++) {
            var event = listeners[i];
            component.addListener(event, function (componentId) {
                me.listeners[event].apply(this, arguments);
            }, me);
        }
    },
    /****
     *为组件绑定对应的事件
     */
    bindEvents: function () {
        var components = this.CM.getComponents();//获取组件管理器组件列表
        for (var i = 0, len = components.length; i < len; i++) {
            this.bindEvent(components[i]);
        }
    },
    /**
     * 弹窗事件
     *@param entityName 实体属性表实体名
     *@param id 统计功能id
     */
    doPopfun: function (entityName, id) {
        //通过弹窗组件id 得到弹窗组件    params为相应的查询参数
        //需不需要格式转换--假设需要：paramsArray==包含id
        if (entityName != null && entityName != ""){
             var window = new Ext.window.Window({
                title:'请输入查询密码',
                width:240,
                autoShow:true,
                modal:true,
                layout:'hbox',
                items:[
                    new Ext.form.field.Text({width:110,inputType:'password'})
                ]
            });
            var button = new Ext.button.Button({
                text : '确定'
            });
            var button1 = new Ext.button.Button({
                text : '取消'
            });
            button.on('click',function(){
                window.hide();
                NS.Msg.error('密码输入错误','密码输入错误，请重新输入尝试！');
            });
            button1.on('click',function(){
                window.hide();
            });
            window.insert(1,button);
            window.insert(2,button1);
        }

            /*var popWindow = new Output.PopWindow({
                entityName: entityName,//请求表头数据
                id: id//请求数据id
            });*/
    },
    /**
     * 状态改变事件
     * @param compontentObj 组件对象
     * @param componentId 组件对象id
     */
    statusChange: function (componentId) {
        //通过id得到组件 组件提供的getStatus方法即可得到状态 -->给数据请求代理
        var params = this.getCM().getComponentStatus(componentId);//获取当前组件的状态
        this.getModel().pageRequest(params, function (data) {
            this.getCM().loadData(data);
        }, this);
    },
    /**
     * 联动事件~~
     * @param componentIdList 组件id列表
     * @param linkValueObj 联动值--格式是？{startDate:2012-01-29,endDate:2012-10-02,bjid:1112020101013}
     */
    linkage: function () {
        var params = this.getCM().getAllStatus();
        this.getModel().pageRequest(params, function (data) {
            this.getCM().loadData(data);
        }, this);
    },
    /***
     * 设置实体模型属性
     * @param {} model
     */
    setModel: function (model) {
        this.model = model;
    },
    /***
     * 获取数据层对象
     * @return {}
     */
    getModel: function () {
        return this.model;
    },
    /***
     * 设置组件管理器
     */
    setCM: function (componentManager) {
        this.CM = componentManager;
    },
    /***
     * 获取组件管理器
     */
    getCM: function () {
        return this.CM;
    }
});NS.define('Output.TemplatePackager', {
    singleton: true,
    constructor: function () {
        // 模板池
        this.templateBuilders = {
            'classical': this.classicBodyBuilder,
            'quarters': this.quartersBodyBuilder,
            'single': this.singleBodyBuilder
        };
    },
    getTemplateBody: function (config) {
        var builder = this.templateBuilders[config.templateType];
        if (builder)
            return builder.call(this, config);
        else
            console.log('没有定义的模版:' + config.templateType);
    },
    /***************************************************************************
     * 单分平模版构建
     */
    singleBodyBuilder: function (config) {
        var pd = config.positionData;// 位置数据
        var cm = config.componentManager;// 组件管理器
        var body = {
            title: config.title,
            templateType: "classical",
            items: []
        };
        var fd = pd[0];
        var comIds = fd.comps;// 组件Id数组
        var cl = new Ext.container.Container({// 功能区容器
            xtype: 'container',
            baseCls: 'fun_area',
            layout: 'fit',
            columnWidth: .5
            // height : 30
        });
        var titleContainer = new Ext.container.Container({
            baseCls: '',
            region: 'north',
            layout: 'column',
            items: [new Output.FunAreaTitle({//
                funtitle: fd.funAreaTitle,
                maxWidth: 255,
                minWidth: 150
            }).getLibComponent(), new Output.FunAreaHelp({
                help: fd.helpInfo
            }).getLibComponent()]
        });
        var items = [];
        items.push(titleContainer);
        for (var j = 0; j < comIds.length; j++) {
            var cc = comIds[j];// 获取组件数据
            var com = cm.getComponentById(cc.componentId);// 获取组件
            items.push(com.getLibComponent());// 将组件添加到容器中
        }
        cl.add(items);
        // var container = new Ext.container.Container({columnWidth : .5,items :
        // cl});
        body.items.push(cl);
        return body;
    },
    /***************************************************************************
     * 四分平模版体构建
     *
     * @param {}
     *            config
     */
    quartersBodyBuilder: function (config) {
        var pd = config.positionData;// 位置数据
        var cm = config.componentManager;// 组件管理器
        var body = {
            title: config.title,
            items: []
        };
        for (var i = 0, ln = pd.length; i < ln; i++) {
            var fd = pd[i];
            var comIds = fd.comps;// 组件Id数组
            var cl = new Ext.container.Container({// 功能区容器
                xtype: 'container',
                layout: 'fit',
                columnWidth: .5
            });
            var titleContainer = new Ext.container.Container({
                baseCls: '',
                layout: 'column',
                items: [new Output.FunAreaTitle({//
                    funtitle: fd.funAreaTitle,
                    maxWidth: 255,
                    minWidth: 150
                }).getLibComponent(), new Output.FunAreaHelp({
                    help: fd.helpInfo
                }).getLibComponent()]
            });
            var items = [];
            items.push(titleContainer);
            for (var j = 0; j < comIds.length; j++) {
                var cc = comIds[j];// 获取组件数据
                var com = cm.getComponentById(cc.componentId);// 获取组件
                items.push(com.getLibComponent());// 将组件添加到容器中
            }
            cl.add(items);
            body.items.push(cl);
        }
        body.title = config.title;
        body.templateType = "quarters";
        if (body.items.length == 4) {
            var array = body.items;
            body.items = [
                {
                    xtype: 'container',
                    width: '100%',
                    height: '100%',
                    layout: 'column',
                    baseCls: 'fun_area',
                    style: {
                        marginTop: '0px',
                        paddingTop: '19px',
                        backgroundImage: 'url(base/output/images/bg-border.gif)',
                        backgroundRepeat: 'repeat-y',
                        backgroundPosition: '50% 0'
                    },
                    items: [array[0], array[1]]
                },
                {
                    xtype: 'container',
                    width: '100%',
                    height: '100%',
                    layout: 'column',
                    baseCls: 'fun_area classTemplate_bottom',
                    style: {
                        marginTop: '0px',
                        paddingTop: '19px',
                        backgroundImage: 'url(base/output/images/bg-border.gif)',
                        backgroundRepeat: 'repeat-y',
                        backgroundPosition: '50% 0'
                    },
                    items: [array[2], array[3]]
                }
            ];
        }
        // body.items = A;
        return body;
    },
    /***************************************************************************
     *
     * 经典模版体构建
     */
    classicBodyBuilder: function (config) {
        var pd = config.positionData;// 位置数据
        var cm = config.componentManager;// 组件管理器
        var body = {
            title: config.title,
            items: []
        };
        for (var i = 0, ln = pd.length; i < ln; i++) {
            var fd = pd[i];// 功能区数据
            var comIds = fd.comps;// 组件Id数组
            var cl = new Ext.container.Container({// 功能区容器
                xtype: 'container',
                layout: 'fit',
                baseCls: 'fun_area'
                // height : 30
            });
            if (i == pd.length - 1)
                cl.baseCls = 'fun_area fun_area_bottom classTemplate_bottom';
            var titleContainer = new Ext.container.Container({
                baseCls: '',
                layout: 'column',
                items: [new Output.FunAreaTitle({//
                    funtitle: fd.funAreaTitle,
                    maxWidth: 255,
                    minWidth: 150
                    // columnWidth : 0.2
                }).getLibComponent(), new Output.FunAreaHelp({
                    // columnWidth : 0.75
                    help: fd.helpInfo
                }).getLibComponent(),
                    new Output.FunAreaButton({
                        fatherContainer: cl
                        // columnWidth : 0.05
                    }).getLibComponent()]
            });
            var items = [];
            items.push(titleContainer);
            for (var j = 0; j < comIds.length; j++) {
                var cc = comIds[j];// 获取组件数据
                var com = cm.getComponentById(cc.componentId);// 获取组件
                if (com.componentType == 'datelinkage') {
                    titleContainer.getComponent(0).columnWidth = 0.2;
                    titleContainer.getComponent(1).columnWidth = 0.05;
                    var insert = com.getLibComponent();
                    insert.columnWidth = 0.7;
                    titleContainer.insert(2, insert);
                    continue;
                }
                items.push(com.getLibComponent());// 将组件添加到容器中
            }
            cl.add(items);
            body.items.push(cl);
        }
        body.title = config.title;
        body.templateType = "classical";
        return body;
    }
});

/*******************************************************************************
 * 功能区标题容器
 */
NS.define('Output.FunAreaTitle', {
    /***************************************************************************
     * 构造方法
     */
    constructor: function (config) {
        US.apply(this, config);
        this.initComponent();
    },
    /***************************************************************************
     * 初始化组件
     */
    initComponent: function () {
        this.createComponent();
    },
    /***************************************************************************
     * 创建组件
     */
    createComponent: function () {
        var me = this;
        this.component = new Ext.container.Container({
            baseCls: 'fun_area_title',
            columnWidth: me.columnWidth,
            listeners: {
                'afterrender': function () {
                    var textNode = document.createTextNode(me.funtitle);// 创建标题dom节点
                    var span = document.createElement('span');
                    span.appendChild(textNode);
                    this.el.appendChild(span);
                }
            }
        });
    },
    getLibComponent: function () {
        return this.component;
    }
});
/*******************************************************************************
 * 功能区帮助容器
 */
NS.define('Output.FunAreaHelp', {
    /***************************************************************************
     * 构造方法
     */
    constructor: function (config) {
        US.apply(this, config);
        this.initComponent();
    },
    /***************************************************************************
     * 初始化组件
     */
    initComponent: function () {
        this.createComponent();
    },
    /***************************************************************************
     * 创建组件
     */
    createComponent: function () {
        var me = this;
        this.component = new Ext.container.Container({
            baseCls: 'opTextRoot_bzxx',
            columnWidth: me.columnWidth,
            listeners: {
                'afterrender': function () {
                    var img = document.createElement('IMG');
                    img.src = 'app/output/images/qs.gif';
                    var a = document.createElement('A');
                    a.appendChild(img);
                    a.href = '#';
                    // a.title='帮助信息';
                    Ext.fly(a).on('click', function (event) {// mouseover click
                        event.stopEvent();
                        if (typeof this.help == 'undefined'
                            || this.help == '' || this.help == null) {
                            this.help = '帮助信息为空！请联系相关人员维护！';
                        }
                        var tip = Ext.create('Ext.tip.ToolTip', {
                            width: 200,
//									autoWidth:true,
                            // minHeight : 80,
//									maxHeight : 120,
                            autoHide: false,
                            // hideDelay:20,
                            shadow: false,
                            bodyBorder: false,
                            // title:'帮助信息',
                            // closable : true,
                            // floating : true,
                            // draggable : true,
                            frameHeader: false,
                            hideCollapseTool: true,
                            overlapHeader: true,
                            // frame:true,
                            bodyBorder: false,
                            bodyStyle: 'background:#f0f0f0;',
                            html: '<div style="margin:8px 15px 11px 15px;"><p style="color:#666;"><b>帮助信息</b></p>'
                                + '<div style="line-height:18px; padding-top:10px; color:#808080">'
                                + this.help + '</div></div>'

                        });
                        var ae = Ext.fly(a);
                        tip.showAt([ae.getX() + 30, ae.getY() - 4]);
//								setTimeout(function(){
//									tip.close();
//								},1000*(this.help.length/4));
                        return false;
                    }, me);
                    this.el.appendChild(a);
                }
            }
        });
    },
    getLibComponent: function () {
        return this.component;
    }
});
/*******************************************************************************
 * 功能区收缩按钮容器
 */
NS.define('Output.FunAreaButton', {
    /***************************************************************************
     * 构造方法
     */
    constructor: function (config) {
        US.apply(this, config);
        this.initComponent();
    },
    /***************************************************************************
     * 初始化组件
     */
    initComponent: function () {
        this.createComponent();
    },
    /***************************************************************************
     * 创建组件
     */
    createComponent: function () {
        var me = this;
        this.component = new Ext.container.Container({
            baseCls: '',
            columnWidth: me.columnWidth,
            listeners: {
                'afterrender': function () {
                    var button = this.expandButton = document
                        .createElement('input');
                    button.className = 'fun_area_button_shrink';
                    button.type = 'button';
                    button.onclick = function () {
                        if (button.className == 'fun_area_button_shrink')
                            button.className = 'fun_area_button_expand';
                        else
                            button.className = 'fun_area_button_shrink';
                        var items = me.fatherContainer.items.items;
                        for (var i = 1; i < items.length; i++) {
                            if (!items[i].isHidden())
                                items[i].hide();
                            else
                                items[i].show();
                        }
                    };
                    this.el.appendChild(button);
                    this.el.setStyle('styleFloat', 'right');
                    this.el.setStyle('cssFloat', 'right');
                }
            }
        });
    },
    getLibComponent: function () {
        return this.component;
    }
});/*******************************************************************************
 * 定义四分平模版 数据格式参照经典模版
 */
NS.define('Output.QuartersTemplate', {
	        /***
	         * 初始化构造函数
	         * @param {} config 配置参数
	         */
            constructor : function(config) {
				 config.autoScroll = true;
				 config.style = {
							backgroundColor : 'white'
						};
				config.baseCls = 'quatersTemplate';
				var container = new Ext.container.Container(config);
				this.component = Ext.create('Ext.container.Container', {
							items : container
						});
			},
			/*******************************************************************
			 * 获取封装组件
			 */
			getLibComponent : function() {
				return this.component;
			},
			render : function(id) {
				this.component.render(id);
			}
		});/**
 * 经典模板。
 * 
 * @argument jsonData = { templateType :'classicalTemplate', compDatas :[ {
 *           compId:1818181 compData:{ 存放该组件对应的数据格式。 } }, { compId:1818182
 *           compData:{ 存放该组件对应的数据格式。 } } ], positionData : [ { compIds:[
 *           1818181,1818182 ] },{ compIds:[ 1818181,1818182 ] },{ compIds:[
 *           1818181,1818182 ] } ] }
 * @return
 *           @example
 */
NS.define('Output.ClassicalTemplate', {
			constructor : function(config) {
			   config.autoScroll = true;
			   config.style = {
							backgroundColor : 'white'
						};
			   config.baseCls = 'classTemplate';
			   var container = new Ext.container.Container(config);
			   this.component = Ext.create('Ext.container.Container',{
			   			items:container
			   });
			},
		    /***
			  获取封装组件
			**/
            getLibComponent : function(){
			   return this.component;
			},
			render : function(id){
			   this.component.render(id);
			}
		});/*******************************************************************************
 * 定义四分平模版 数据格式参照经典模版
 */
NS.define('Output.SingleTemplate', {
	        /***
	         * 初始化构造函数
	         * @param {} config 配置参数
	         */
            constructor : function(config) {
				 config.autoScroll = true;
				 config.style = {
							backgroundColor : 'white'
						};
				config.layout  = 'fit';
//				config.baseCls = 'classTemplate';
//				var container = new Ext.container.Container(config);
//				this.component = Ext.create('Ext.container.Container', {
//							items : container,
//							layout : 'fit'
//						});
				this.component = new Ext.container.Container(config);
			},
			/*******************************************************************
			 * 获取封装组件
			 */
			getLibComponent : function() {
				return this.component;
			},
			render : function(id) {
				this.component.render(id);
			}
		});