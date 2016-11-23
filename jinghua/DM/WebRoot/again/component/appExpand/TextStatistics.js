/**
 * 文本统计组件。
 * 
 * @class NS.appExpand.TextStatistics
	
	cData = {
				height:"60",
				width:"80%",
				htmlstring :"上述变更数据，已同步数据节点 {0} 处，被动同步数据节点数 {1} 处，未完成同步数据节点数 {2} 处。",
				showMaxItems:0, 
	            items : [
	            	{ text : '286', params : { name : 'zhangsan', age : 12 } }, 
	            	{ text :'224', params : { name : 'lisi', age : 12 } }
	            ] 
            }
 */
NS.define("NS.appExpand.TextStatistics", {
			extend : 'NS.Component',
			initComponent : function(obj) {
				this.config = obj;
				this.callParent();
				this.createComponent(obj);
			},
			initConfigMapping : function() {
				this.callParent();
				this.addConfigMapping(
		            { htmlstring : {name : 'htmlstring'},
		              items:{name:'items'}
		            }
		        );
			},
			initEvents : function() {
				this.callParent();
				/*this.addEvents(
				*//**
				 * @click
				 *//*
				'linkclick'
				);*/
			},
			/**
			 * @private
			 * @param {Object}
			 *            cData
			 */
			createDom : function(cData) {
				var me = this,
                    doc = document;
				// 创建一个组件内的父节点div，以便挂接到外界的DOM节点上，完成组件的相互组装。
				var interfaceDIV = doc.createElement('DIV');
				interfaceDIV.style.width = '90%';
				interfaceDIV.className = 'opTextRoot';
				var itemsContent = cData.items;
				var htmlString = cData['htmlstring'];
				// 获取要显示的最大连接数。
				var showMaxItems = this.showMaxItems = cData['showMaxItems']||itemsContent.length;
				// 正则表达式，分割htmlString字符串。
				var strs = htmlString.split(/\{[0-9]*\}/);
				// 循环数据和分割后得到的字符串数据，来创建div子节点元素。
				for (var index in strs) {
					if (index > showMaxItems) {
						break;
					}
					interfaceDIV.appendChild(doc.createTextNode(strs[index]));
					// 如果有连接数据，则添加连接元素。
					if (index < itemsContent.length) {
						(function(index, interfaceDIV, item) {
								var a = document.createElement('A');
								a.href = "#";
								a.id ="statistics"+index+id;
                                if(!item.noEvent){
                                    a.onclick = function() {
                                        me.fireEvent('linkclick', item.params,item.text);// 触发弹窗事件
                                        return false;
                                    };
                                }
								a.appendChild(document.createTextNode(itemsContent[index]['text']));
								interfaceDIV.appendChild(a);
							})(index, interfaceDIV, itemsContent[index]);
					}
				}
				this.myDom = interfaceDIV;
			},
			/**
			 * 创建容器组件，将createDOM中创建的节点放到这个容器中。
			 * @param {Object} obj
			 */
			createComponent : function(obj){
				var me = this;
				this.createDom(obj);
				var basic = {
					width:obj.width||'100%',
					height:obj.height||'100%',
					listeners : {
						'afterrender' : function() {
							this.el.appendChild(me.myDom);
						}
					}
				};
				this.component = new Ext.container.Container(basic);
			},
			/**
			 * 将创建的原生dom节点渲染到制定的dom节点上。
			 * 
			 * @param {}
			 *            id 该参数可以是dom节点的id，也可以是dom对象。
			 */
			render : function(id) {
				this.component.render(id);
			},
			/**
			 * 刷新相应下标连接上的显示数据。
			 * @param {Object} obj
			 * 			被刷新数据
			 
			 	var obj = [{
			 		index : 0,
			 		text :"289"
			 	}]
			 */
			refreshByIndex : function(obj){
				if(obj instanceof Array){
					for(var i=0;i<obj.length;i++){
						var domid = "statistics"+obj[i].index+id;
						var extDom = Ext.get(domid);
						extDom.dom.innerHTML = obj[i].text;
						this.config.items[obj[i].index].text = obj[i].text;
					}
				}
				
			},
			/**
			 * 重新刷新组件
			 * @param {Object} obj
			 * 				使用数据刷新组件
			 */
			refresh : function(obj){
				Ext.fly(this.myDom).remove();
				this.createDom(obj);
				this.component.el.appendChild(this.myDom);
                this.config = obj;
			},
            /**
             * 根据下标获取值。
             * @param index
             * @return {String}
             */
            getDataByIndex : function(index){
                return this.config.items[index].text;
            }
		});