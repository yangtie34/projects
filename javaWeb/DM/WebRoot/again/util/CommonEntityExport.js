NS.define('NS.util.WindowComponents', {
	/***************************************************************************
	 * 实例化
	 * 
	 * @type Boolean
	 */
	instance : true,
	/***************************************************************************
	 * 显示导出窗口
	 */
	showExportWindow : function(grid,entityname,title) {
		var entityCN = '\"'+title+'\"';//设定标题
		var entityName = '\"'+entityname+'\"';//设定实体名
		var splits = gridzz.getStore().store.proxy.url.split('?');
		    splits.shift();
		var storeP = splits.join();
		var win = Ext.create('Ext.window.Window', {
			width : 100,
			height : 85,
			//margin : '5 10 5 10',
			closable : true,
//			preventHeader : true,
			modal : true,
			autoShow : true,
			autoDestroy : false,
			defaultType : 'radiofield',
			items : [{
						// xtype:'radio',
						name : 'exportType',
						boxLabel : '全部字段',
						inputValue : '1',
						id : 'state'
					}, {
						// xtype:'radio',
						name : 'exportType',
						boxLabel : '显示字段',
						inputValue : '0',
						id : 'state1'
					}],
			defaults : {
				listeners : {
					click : {
						element : 'el',
						fn : function() {
							var type = Ext.getCmp('state').getGroupValue(); // 待改
							var queryCondition = JSON.stringify(gridzz
									.getStore().getExtraParams());
							if ('0' == type) { // 只导出显示列
								var showField = gridzz.getGrid()
										.getShowColumnsData();
								// alert(showField.length)
								var params = [];
								for (var i = 0; i < showField.length; i++) {
									var field = showField[i].dataIndex
											.split(".");
									params.push("\'"+field+"\'");
								}
								var exportFields = '{"fields":['
										+ params.join(",") + ']';
								exportFields += ',"entityName":' + entityName;
								exportFields += ',"entityCN":' + entityCN + '}';

								window.location = 'baseAction.action?exportFields='
										+ exportFields
										+ "&queryCondition="
										+ queryCondition
										+"&"+storeP;
								win.close();
							} else if ('1' == type) { // 导出全部列
								var exportFields = '{';
								exportFields += 'fields:["all"]';
								exportFields += ',entityName:"'
										+ entityName.replace(/\"/g, "") + '"';
								exportFields += ',entityCN:"'
										+ entityCN.replace(/\"/g, "") + '"}';

								window.location = 'baseAction.action?exportFields='
										+ exportFields
										+ "&queryCondition="
										+ queryCondition
										+"&"+storeP;;
								win.close();
							}

						}
					}
				}
			}
		});
	}
});