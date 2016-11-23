/**
 * 教学评估-学校《本科教学质量报告》支撑数据指标比较
 */
NS.define('Pages.sc.leaveSchool.leaveSchool', {
	extend : 'Template.Page',
	modelConfig : {
		serviceConfig : {
			queryGridContent: {
                service:"leaveSchoolService?queryGridContent",
                params:{
                    limit:20,
                    start:0
                }
            },
            deleteStudent:"leaveSchoolService?deleteStudent"
		}
	},
	tplRequires : [],
	cssRequires : [],
	requires : [],
	params : {
		userName : 'user'
	},
	init : function() {
		var me = this;
		var pageTitle = new Exp.component.PageTitle({
			data : {
				pageName : '实习离校学生',
				pageHelpInfo : '实习离校学生统计,导入的excel格式要求：按照模板格式要求，其他列输入信息不做强制格式要求。'
			}
		});
		var containerx = this.mainContainer = this.createMain();

		var container = new NS.container.Container({
			padding : 20,
			autoScroll : true,
			items : [ pageTitle, containerx ]
		});
		this.createGrid();
		this.setPageComponent(container);
	},
	/**
	 * 创建中部容器组件。
	 */
	createMain : function() {
		var me=this;
		var file = new Ext.form.field.File({
			name : 'txtFile',
			fieldLabel: 'excel文件',
			buttonText: '选择文件',
			blankText : "请上传2003版本以下的excel文件...",
			width:400,
			hideLabel:true
		});
		var formPanel = new Ext.form.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			labelWidth : 80,
			defaults : {
				autoWidth : true
			},
			padding : 10,
			frame : false,
			border : false,
			autoScroll : true,
			fileUpload : true,
			items : [file]
		});
		var button=new Ext.Button({
			text: "导入Excel",
			x:10,y:10,
		    listeners: { "click": function () {
		    	var filename=file.getValue();
		    	if(filename.substring(filename.length-4,filename.length)!=".xls"){
		    		Ext.MessageBox.alert("信息", "上传文件以.xls格式的文件结尾");
					return;
		    	}
				if (!formPanel.getForm().isValid()) {
					Ext.MessageBox.alert("信息", "表单输入验证失败，请正确填写完整！");
					return;
				}
				formPanel.form.submit({
					waitMsg : '请等待 ....',
					url : 'LeaveSchool',
					method : "POST",
					success : function(form, action) {
						Ext.Msg.alert('提示', "导入成功");
						me.tplGrid.load(me.params);
					},
					failure : function(form, action) {
						Ext.Msg.alert('提示', "导入失败");

					}
				});
			}
		    }
		});
		var button2=new Ext.Button({
			text: "下载模板",
			x:30,y:10,
		    listeners: { "click": function () {
		    	window.open ('app/pages/sc/leaveSchool/model.xls')
		    	}
		    }
		});
		var containerx = new Ext.container.Container({
			items : [ {
				xtype : 'container',
				layout : {
					type : 'column',
					columns : 1
				},
				items : [ formPanel ,button,button2],

			} ]
		});

		return containerx;
	},
	createGrid:function(){
        var params = {start:0,limit:20};
        Ext.apply(this.params,params);
        this.callService([{key:'queryGridContent',params:this.params}],
            function(respData){
                this.tableData = respData.queryGridContent;
                this.gridFields =["ID","CODE","XM","YX","ZY","BJ","STARTTIME","ENDTIME","ADDTIME","ADDER","CZ"];

                this.tplGrid = this.initXqGrid(this.tableData,this.gridFields,this.convertColumnConfig(),this.params);

                this.mainContainer.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });
                this.tplGrid.bindItemsEvent({
                    'CZ' :{event:'linkclick',fn:this.deleteCode,scope:this}
                });
            });
    },
    /**
     * 初始化Grid
     */
    initXqGrid : function(data,fields,columnConfig,queryParams){
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : 20,
            proxy : this.model,
            serviceKey:{
                key:'queryGridContent',
                params:queryParams
            },
            multiSelect: false,
            lineNumber: true,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: false
        });
        return grid;
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig : function(){
        var arrays = this.gridFields;
        var textarrays = "ID,学号,姓名,院系,专业,班级,离校时间,回校时间,添加时间,添加人,操作".split(",");
        var widtharrays = [90,100,150,150,150,150,150,150,150,100,100];
        var hiddenarrays = [true,false,false,false,false,false,false,false,false,false,false];
        var columns = [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name : arrays[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            switch(arrays[i]){
            case 'CZ':
                Ext.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer:function(data){
                        return '<a href="javascript:void(0);">删除</a>';
                    }
                });
                break;
            default:
                break;
        }
            columns.push(basic);
        }
        return columns;
    },
    deleteCode:function(text){
    	var me=this;
    	this.callService({key:'deleteStudent',params:{lcid:arguments[3].ID}},function(respData){
    		Ext.Msg.alert('提示', "删除成功");
			me.tplGrid.load(me.params);
    	});
    }
});