/**
 * 学生考勤选择器
 * 以_开头的方法是私有方法，否则是公共方法，字段都是私有的。
 * @class Business.jw.StudentsSelector
 * @extend Template.Page
 */
NS.define('Business.jw.AbsentStudentsSelector',{
    extend: 'Template.Page',
    
    /**
     * 服务配置
     */
    modelConfig: {
        serviceConfig: {
    		queryStudents: ''
        }
    },
    
    /**
     * 组件参数配置
     */
    windowBodyId: 'jwXskqWindowContent', // 窗口内容ID
    absenceTypes: { // 缺勤类型
    	cd: {mc: '迟到'},
    	kk: {mc: '旷课'},
    	zt: {mc: '早退'},
    	qj: {mc: '请假'},
    	qt: {mc: '其他'}
    },
    
    /**
     * CSS
     */
    cssRequires: ['app/business/jw/template/css/absent-students-selector.css'],
    
    /**
     * 初始化方法，组件入口，类似于构造器。
     * 使用方法：当new该组件的时候调用，即 var qqDialog = new Business.jw.AbsentStudentsSelector(allStudents);
     * @param {} allStudents 一个班级的所有学生。类型是数组，元素格式：{id:10001, mc: '赵匡胤', qqlx: 'cd'}
     * 						id即学生ID（必须），mc即学生姓名（必须），qqlx即考勤类型（非必须）。
     * 						qqlx的值必须为：'cd'（迟到）,'kk'（旷课）,'zt'（早退）,'qj'（请假）,'qt'（其他）。
     */
    init: function(allStudents) {
    	// 设置缺勤学生
    	this._setAbsenceStudents(allStudents);
    	
    	// 创建window
    	if (!this.window) {
    		var params = {title: '学生考勤', width: 500, height: 300, closeAction: 'destroy'};
    		this._createWindow(params);
    	}

    	// 调用父类
        this.callParent(arguments);
    },
    
    /**
     * 私有方法
     * 设置缺勤学生
     * @param {} allStudents
     * @return {}
     */
    _setAbsenceStudents: function(allStudents) {
    	this.allStudents = allStudents||[];
    	var allStudentsSize = this.allStudents.length;
    	this.absenceStudents = {};
    	for (var i = 0; i < allStudentsSize; i++) {
    		var student = this.allStudents[i];
    		if (this.absenceTypes[student.qqlx]) {
    			var studentTemp = [];
    			NS.apply(studentTemp, student);
    			this.absenceStudents[studentTemp.id] = studentTemp;
    		}
    	}
    	
    	return this;
    },
    
    /**
     * 私有方法
     * 创建window
     * @param {} params
     * @return {}
     */
    _createWindow: function(params) {
    	params.html = '<div id="' + this.windowBodyId + '" class="students-absence-grid"></div>';
    	params.bbar = this._createWindowBbar();
    	this.window = new NS.window.Window(params);
		
		return this;
    },
    
    /**
     * 私有方法
     * 创建window底部栏
     * @return {}
     */
    _createWindowBbar: function() {
    	this._compositeWindowBbar();    	
    	this._bindWindowBbarEvents();
    	
    	return this.windowBbar;
    },
    
    /**
     * 私有方法
     */
    _compositeWindowBbar: function() {
    	var ensureBtn = new NS.button.Button({
    		name: 'ensure',
  			text: '确定',
  			margin: 10
		});
		
		var cancelBtn = new NS.button.Button({
    		name: 'cancel',
  			text: '取消'
		});

    	this.windowBbar = new NS.container.Container({
    		margin: '0 10',
    		frame: false,
    		border: 0,
    		layout: {type: 'hbox', pack: 'end', align: 'middle'},
    		items: [ensureBtn, cancelBtn]
    	});
    },
    
    /**
     * 私有方法
     */
    _bindWindowBbarEvents: function() {
    	this.windowBbar.bindItemsEvent({
   			ensure: {event: 'click', fn: this._ensureStudents, scope: this},
            cancel: {event: 'click', fn: this._cancelWindow, scope: this}
        });
        
        return this;
    },
    
    /**
     * 私有方法
     * 确定选定的student
     */
    _ensureStudents: function() {
    	this.close();
    	
    	return this.getAbsenceStudents();
    },
    
    /**
     * 私有方法
   	 * window取消操作
   	 */
   	_cancelWindow: function() {
   		this.absenceStudents = {};
   		this.close();
   		
   		return this;
   	},
    
    /**
     * 私有方法
     * 初始化window
     * @return {}
     */
    _initWindow: function() {
    	this._clearWindowBody()._setWindowGridData();
        
        return this;
    },
    
    /**
     * 私有方法
     * @return {}
     */
    _clearWindowBody: function() {
    	$(this.windowBodyId).empty();
    	
    	return this;
    },
    
    /**
     * 私有方法
     * @return {}
     */
    _setWindowGridData: function() {
		// 创建grid
    	this._createWindowGrid();
        
        // 绑定grid事件
        this._bindWindowGridEvents();
        
        return this;
    },
    
    /**
     * 私有方法
     * 创建Grid
     * @return {}
     */
    _createWindowGrid: function() {
    	var grid = new NS.Component({
            tpl: this._createWindowGridTpl(),
            data: this.allStudents
        });
        grid.render(this.windowBodyId);
        
        return this;
    },
        
    /**
     * 私有方法
     * 创建Grid的Tpl
     * @return {}
     */
    _createWindowGridTpl: function() {
    	var html = '<table class="students-absence-grid-header"><thead><tr><th class="first">序号</th><th>姓名</th>';
    	var keyCount = 0;
    	for (var key in this.absenceTypes) {
    		html += '<th>' + this.absenceTypes[key].mc + '</th>';
    		keyCount++;
    	}
    	html += '</tr></thead></table>';
    	html += '<div class="students-absence-grid-body"><table><tbody><tpl for="."><tr><td class="first">{#}</td><td>{mc}</td>';
    	for (var key in this.absenceTypes) {
    		var type = this.absenceTypes[key];
    		html += '<td><input name="' + type.mc + '" qqlx="' + key + '" studentIndex="{[xindex - 1]}" <tpl if="qqlx==\'' + key + '\'">checked="checked"</tpl> type="checkbox" /></td>';
    	}
		html += '</tr></tpl></tbody></table></div>';
		
		return new NS.Template(html);
    },

    /**
     * 私有方法
     * @return {}
     */
    _bindWindowGridEvents: function() {
    	var context = this;
    	var windowBody = $('#' + this.windowBodyId);
    	
    	// 选择缺勤类型事件
    	var qqCheckboxs = windowBody.find('input[qqlx]');
    	qqCheckboxs.click(function() {
    		var studentIndex = $(this).attr('studentIndex');
    		var sameStudentQqCheckboxs = windowBody.find('input[qqlx][studentIndex="' + studentIndex + '"]');
    		for (var i = 0; i < sameStudentQqCheckboxs.length; i++) {
    			if (sameStudentQqCheckboxs[i] != this) {
    				sameStudentQqCheckboxs[i].checked = false;
    			}
    		}
    		    		
    		var student = context.allStudents[studentIndex];
    		var checked = this.checked;
    		if (checked) {
	    		if (!context.absenceStudents[student.id]) {
	    			context.absenceStudents[student.id] = {};
	    			NS.apply(context.absenceStudents[student.id], student);
	    		}
	    		context.absenceStudents[student.id].qqlx = $(this).attr('qqlx');
    		} else {
    			delete context.absenceStudents[student.id];
    		}
    	});
    	
    	return this;
    },
    
    /**
     * 公共方法
     * 获取缺勤学生列表。
     * 返回数据类型是数组，元素格式：{id:10001, mc: '赵匡胤', qqlx: 'cd'}
     * id即学生ID（必须），mc即学生姓名（必须），qqlx即考勤类型（非必须）。
     * qqlx的值必须为：'cd'（迟到）,'kk'（旷课）,'zt'（早退）,'qj'（请假）,'qt'（其他）。
     * @return {}
     */
    getAbsenceStudents: function() {
    	var resultStudents = [];
    	for (var key in this.absenceStudents) {
    		resultStudents.push(this.absenceStudents[key]);
    	}
    	
    	return resultStudents;
    },    
        
    /**
     * 公共方法
     * @return {}
     */
    show: function() {
    	this.window.show();
    	this._initWindow(); // 初始化window
    	
    	return this;
    },
    
    /**
     * 公共方法
     * @return {}
     */
    close: function() {
    	this.window.close();
    	
    	return this;
    }
});