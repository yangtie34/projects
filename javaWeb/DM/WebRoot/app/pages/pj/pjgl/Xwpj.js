/**
 * 学委周评教师
 */
NS.define('Pages.pj.pjgl.Xwpj', {
	extend : 'Template.Page',
	entityName : 'TbPjXwpj',
	modelConfig : {
		serviceConfig : {
			queryZzjgMc : 'getMcById',// 根据ID获得名称
			initXwpjData : 'initXwpjData',// 初始化学生评教学生及待评教师信息
			initJzglst : 'xwpjService?queryJzgList', // 查询教职工列表
			getPjzc : 'getPjzc',// 评教周次
			queryTableHeader4XwpjGrid : 'base_queryForAddByEntityName', // 评教指标grid
																		// Header
			queryPjGridData : "base_queryTableContent", // 评教指标grid data
														// queryXspjIndx
			queryPjmxGridData : "queryXwpjMxData", // 取得教师的评教明细
			saveXwpjData : 'saveXwpjData', // 保存数据service名
			update : 'base_update',// 更新grid记录数据 service名
			getBestJzg : 'xwpjService?getBestJzg'// 取得最优教师
		}
	},
	// 初始化
	init : function() {
		this.initData(); // 初始化数据
	},
	// 初始化组件数据
	initData : function() {
		var me = this;
		var indexParam = {
			entityName : 'TbPjPjmx'
		};
		/** ----------------登录用户数据------------------- */
		var xsId = this.xsId = MainPage.getXsId(); // 学生ID
		var loginName = this.loginName = MainPage.getLoginName(); // 登录用户名
		var userName = this.userName = MainPage.getUserName(); // 用户姓名
		var xs_bjxx = this.bjxx = MainPage.getBjxx(); // 学生所在班级信息,为啥得不到信息？
		var xnId = this.xnId = MainPage.getXnId();// 当前学年ID
		var xqId = this.xqId = MainPage.getXqId(); // 当前学期ID
		var xnMc = this.xnMc = MainPage.getXnMc(); // 学年名称
		var xqMc = this.xqMc = MainPage.getXqMc(); // 学期名称

		this.xsXm = this.userName; // 学生姓名
		this.bjid = this.bjxx.id; // 班级ID
		this.bjmc = this.bjxx.mc; // 班级名称

		var param = {
			xnId : xnId,
			'xqId' : xqId,
			'xsId' : xsId
		}
		this.callService([{
			key : 'queryZzjgMc',
			params : {
				"bjId" : this.bjid
			}
		},// 取得所在专业、系部
		{
			key : 'initXwpjData',
			params : {
				"xsId" : this.xsId,
				"xnId" : this.xnId,
				"xqId" : this.xqId
			}
		}, {
			key : 'queryTableHeader4XwpjGrid',
			params : indexParam
		}, {
			key : 'queryPjGridData',
			params : indexParam
		}, {
			key : 'getPjzc',
			params : {
				curDate : new Date()
			}
		}, {
			key : 'initJzglst',
			params : param
		}, {
			key : 'getBestJzg',
			params : param
		}], function(data) {
			this.initComponent(data);
		}, this);
	},
	// 取得当前月份
	getCurrentMonth : function() {
		var me = this;
		var currentDate = "";
		var now = new Date();
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		if (month.toString().length == 1) {
			month = "0" + month;
		} else {
			month = month;
		}
		return month;
	},
	// 初始化组件
	initComponent : function(data) {
		this.initAndDoLayoutPage();
		this.month = this.getCurrentMonth(); // 当前月份
		this.week = data['getPjzc'];// 评教周次
		this.pjGridTranData = data['queryTableHeader4XwpjGrid'];
		this.pjTeacherData = data['initTeacherData'];
		this.pjGridTableData = data['queryPjGridData'];
		this.xwpjTeacherData = data['initXwpjData'];
		this.yxzy = data['queryZzjgMc'];
		this.jzgList = data['initJzglst'];
		this.jzgComCbx = this.initJzgCombox(this.jzgList.data);
		var bestJzg = this.bestJzg = data['getBestJzg'];
		if (bestJzg.jzgXm == 'null') {
			bestJzg.jzgXm = '';
		}else{
			this.jzgComCbx.component.select(bestJzg.jzgId);// 最优教师
			this.jzgComCbx.setReadOnly(true);// 不可编辑
		}
		// this.jzgComCbx.setValue(bestJzg.jzgId);
	},
	// 调用评教明细方法 'kcmc':this.kcmc,
	getPjmxGridData : function() {
		var param = this.param = {
			entityName : this.entityName,
			'jzgId' : this.jzgId,
			'xnId' : this.xnId,
			'xqId' : this.xqId,
			'xsId' : this.xsId
		};
		this.callService({
			key : 'queryPjmxGridData',
			params : param
		}, function(data) {
			this.xwpjTableTpl.writeTo(this.xwpjTableCom, data['queryPjmxGridData']);
			var el_jsxm = NS.get('xwpj_current_teacher');
			// var el_kcmc = NS.get('xwpj_pj_kcmc');
			var tpl = new NS.Template("{jzgXm}");
			tpl.writeTo(el_jsxm, {
				jzgXm : this.jsxm
			});
			// tpl.writeTo(el_kcmc,{kcmc:this.kcmc});

			var allSum = 0;
			for(var i=0;i<data['queryPjmxGridData'].length;i++){
				var df = data['queryPjmxGridData'][i]['idxDf'];
				if(df){
					allSum += parseFloat(df);	
				}
			}
			$("#xwpj_dfSum").html("（" + allSum + "）");
			
			var oldValue = "";
			$("input[name='xwpj_index_pingfen_name']").bind("focus", function() {
				oldValue = $(this).val();
			});
			$("input[name='xwpj_index_pingfen_name']").bind("blur", function() {
				var val = $(this).val();
				var fz = $(this).attr("fz");

				if (val === "" && oldValue === "") {
					return;
				}
				if (val === "") {
					val = 0;
				}

				var zf = $("#xwpj_dfSum").text();
				if (zf !== "") {
					zf = zf.substring(1, zf.length - 1);
				} else {
					zf = 0;
				}

				if (oldValue !== "") {
					zf = parseFloat(zf) - parseFloat(oldValue)
				}
				$("#xwpj_dfSum").html("（" + zf + "）");

				if (!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(val)) {
					NS.Msg.info('提示', '只能输入数字');
					$(this).val("");
					$(this).focus();
					return;
				}
				if (parseFloat(val) < 0) {
					NS.Msg.info('提示', '指标得分数不能为负数');
					$(this).val("");
					$(this).focus();
					return;
				}
				if (parseFloat(val) > parseFloat(fz)) {
					NS.Msg.info('提示', '指标得分数不能超过指标分值');
					$(this).val("");
					$(this).focus();
					return;
				}

				$(this).val(parseFloat(val));
				zf = parseFloat(zf) + parseFloat(val);
				$("#xwpj_dfSum").html("（" + zf + "）");
			});
		}, this);
	},

	// 模板文件渲染
	initXwpjTpl : function(data) {
		NS.loadCss('app/pages/pj/css/studentpj.css');
		// 页面
		NS.loadFile('app/pages/pj/pjgl/xwpjTpl/xwpj_main.html', function(text) {
			this.xwpjTpl = new NS.Template(text);
			this.page = new NS.Component({
				data : [],// 待评教师信息
				tpl : this.xwpjTpl
			});
			this.component.add(this.page);
			this.initTemplates(data); // 初始化模板
			this.addListenerTemplate(); // 添加模板监听
		}, this);

	},
	// 自定义下拉组件
	initJzgCombox : function(data) {
		this.jzgCbx = new NS.form.field.ComboBox({
			// data:config.data
			name : 'jzgh',
			width : 270,
			labelWidth : 150,
			fieldLabel : '你评选的最优教师是',
			queryMode : 'local'
		});
		this.jzgCbx.loadData(data);
		return this.jzgCbx;
	},
	// 初始化模板
	initTemplates : function(data) {
		// 学习委员信息
		this.xwpj_pjxsTpl = new NS.Template(" <h2 class='org rgt-border'>学委评教</h2>" + "<h3 class='blue pad-topa pad-lft'>{xsXm}</h3>"
				+ "<h4 class='pad-topb pad-lft'>{yxMc}-></h4>" + "<h4 class='pad-topb pad-lft'>{zyMc}-></h4>"
				+ "<h4 class='pad-topb pad-lft'>{bjMc }</h4>" + "<h3 class='pj-right org pad-topa'>{xnMc} {xqMc}-{month}月- 第{week}周</h3>");

		this.pjxsData = {
			xsid : this.xsId,
			xsXm : this.xsXm,
			yxMc : this.yxzy.yxMc,
			zyMc : this.yxzy.zyMc,
			bjMc : this.bjmc,
			xnMc : this.xnMc,
			xqMc : this.xqMc,
			month : this.month,
			week : this.week.pjzc
		};
		this.pjxsCom = new NS.Component({
			tpl : this.xwpj_pjxsTpl,
			data : this.pjxsData
		});
		this.pjxsCom.render('xwpj_pjxs_show');

		// 待评教师页面
		NS.loadFile('app/pages/pj/pjgl/xwpjTpl/xwpj_teacher.html', function(text) {
			var teacherData = this.xwpjTeacherData.data;
			this.xwpjTeacherTpl = new NS.Template(text, {
				getClassByPjzt : function(pjzt) {
					this.classStr = ''
					this.pjzt = pjzt;
					if (this.pjzt == '已评') {
						this.classStr = 'org';
					} else {
						this.classStr = 'blue';
					}
					return this.classStr;
				}
			});
			this.xwpjTeacherCom = new NS.Component({
				data : teacherData,
				tpl : this.xwpjTeacherTpl
			});
			var el_jzgxm = $("#xwpj_current_teacher");
			if (teacherData.length > 0) {
				this.jsxm = teacherData[0].jzgXm;
				this.jzgh = teacherData[0].jzgh;
				this.pjzt = teacherData[0].pjzt;
				// this.kcmc=teacherData[0].kcmc;
			} else {
				NS.Msg.info('提示', '待评教师不存在！');
			}
			if (this.pjzt == '已评') {
				this.commitBtn.hidden = true;
			} else {
				this.commitBtn.hidden = false;
			}
			this.xwpjTeacherCom.render('xwpj_pjTeacher_show');
			// 单击事件
			this.xwpjTeacherCom.on('click', function(e, el) {
				if (el.name != 'xwpj_teacher_name') {
					return;
				}
				this.jsxm = el.innerHTML;
				var element = NS.get(el);// 取得dom元素对象
				this.cpId = element.getAttribute('id');
				this.jzgId = element.getAttribute('jzgId'); // 教职工ID
				this.jzgh = element.getAttribute('jzgh');// 教职工号
				this.pjzt = element.getAttribute('pjzt'); // 测评状态

				if (this.pjzt == '已评') {
					this.commitBtn.hidden = true;
				} else {
					this.commitBtn.hidden = false;
				}
				var el_jsxm = NS.get('xwpj_current_teacher');
				var tpl = new NS.Template("{jzgXm}");
				tpl.writeTo(el_jsxm, {
					jzgXm : this.jsxm
				});
				// 调用评教明细
				this.getPjmxGridData();
			}, this);

			// 评教项目
			NS.loadFile('app/pages/pj/pjgl/xwpjTpl/xwpj_table.html', function(text) {
				this.xwpjTableTpl = new NS.Template(text, {
					getValueByXwpj : function(pj) {
						return pj;
					}
				});
				this.xwpjTableCom = new NS.Component({
					data : [],
					tpl : this.xwpjTableTpl
				});
				this.xwpjTableCom.render('xwpj_table_show');
				if (teacherData.length > 0) {
					var params = teacherData[0];
					this.jzgId = teacherData[0]['jzgId'];
				}
				this.getPjmxGridData();
			}, this);
		}, this);
	},
	// 添加模板监听
	addListenerTemplate : function() {
		var me = this;
		var commitBtn = this.commitBtn = document.getElementById("xwpj_commit");// $("#xwpj_commit");//提交按钮
		var teacher = {}; // 待评教师
		var pjStu = {}; // 评教学生
		pjStu.xsId = me.xsId;
		pjStu.xsXm = me.xsXm;
		pjStu.bjId = me.bjid;
		pjStu.bjmc = me.bjmc;
		pjStu.xnId = me.xnId;
		pjStu.xqId = me.xqId;
		// pjStu.jzgh=me.jzgh;
		pjStu.month = me.month;
		this.jzgComCbx.render('bestTeacherCbx');
		// “提交”按钮监听事件
		commitBtn.onclick = function(event) {
			var pingfens = document.getElementsByName('xwpj_index_pingfen_name');
			var bestJzg = me.jzgComCbx.getValue();// 取得最优教师id值
			var valueObj = []; // 指标得分数据
			var pingfenLen = pingfens.length;
			for (var i = 0, len = pingfenLen; i < len; i++) {
				var obj = {};
				var el = pingfens[i];
				if (el.value == '') {
					break;
				} else {
					obj['id'] = el.id;
					obj['value'] = el.value;
					valueObj.push(obj);
				}
			}
			if (me.jzgId == undefined) {
				NS.Msg.info('提示', '还未对待评教师进行评价');
				return;
			} else {
				teacher.jzgId = me.jzgId;
				teacher.jzgXm = me.jsxm;
				teacher.jzgh = me.jzgh;
				// teacher.kcmc=me.kcmc;
				if (valueObj.length > 0) {
					var letters = "1234567890";
					var params = {
						'indexData' : valueObj,
						'teacher' : teacher,
						'pjStu' : pjStu,
						'bestJzgId' : bestJzg
					};// 'bestTea':bestTeacher
					for (var j = 0; j < valueObj.length; j++) {
						var recordValue = valueObj[j].value;
						if (recordValue == '' || recordValue == null) {
							NS.Msg.info('提示', '评价得分存在空值！');
							return;
						}
						if (letters.indexOf(recordValue) == -1 && recordValue.length == 1) {
							NS.Msg.info('提示', '只能输入数字');
							return false;
						}
						if (recordValue > 20) {
							NS.Msg.info('提示', '指标得分数不能超过指标分值');
							return;
						}
					}
					if (valueObj.length < pingfenLen) {
						NS.Msg.info('提示', '有未评分的指标项！');
						return;
					}
					// 调用服务
					NS.Msg.changeTip('提示', '确定要提交评教结果吗?', function() {
						me.callService({
							key : 'saveXwpjData',
							params : params
						}, function(data) {
							if (data.saveXwpjData.success == true) {
								NS.Msg.info('提示', '评教成功！');
								me.jzgComCbx.setReadOnly(true);// 不可编辑
								this.classStr = 'org';
								me.callService({
									key : 'initXwpjData',
									params : {
										"xsId" : this.xsId,
										"xnId" : this.xnId,
										"xqId" : this.xqId
									}
								}, function(retdata) {
									var teacherData = retdata['initXwpjData'].data
									this.xwpjTeacherCom.refreshTplData(teacherData);
								}, this)
								commitBtn.hidden = true;
							};
						})
					}, this)
				} else {
					NS.Msg.info('提示', '评价得分不能为空！');
					return;
				}
			}
			if (bestJzg == null) {
				NS.Msg.info('提示', '请选择最优教师！');
				return;
			}
		}
	},
	// 初始化页面
	initAndDoLayoutPage : function(data) {
		var component = this.component = new NS.container.Panel({
			layout : 'fit',
			border : false,
			autoScroll : true
		});
		this.setPageComponent(component);
		this.xwpjPage = this.initXwpjTpl(data);
	}
});