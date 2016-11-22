/**
 * 学生满意度测评页面
 */
NS.define('Pages.pj.myd.MydCp', {
	extend : 'Template.Page',
	entityName : 'TbPjMydXscp',
	modelConfig : {
		serviceConfig : {
			queryZzjgMc : 'getMcById',// 根据ID获得名称
			initTeacherData : 'initTeacher4Myd',// 初始化学生评教学生及待评教师信息
			queryTableHeader4MydGrid : "base_queryForAddByEntityName",// 查询Grid表头数据service名
			queryGridData : "base_queryTableContent", // 查询Grid数据service名
			queryCpmxGridData : "queryMydCpmxData", // 取得教师的满意度明细
			saveMydTestData : 'saveMydCpData', // 保存数据service名
			queryMydCpzt : 'queryMydCpzt', // 查询测评状态
			update : 'base_update'// 更新grid记录数据 service名
		}
	},
	// 初始化
	init : function() {
		this.initData(); // 初始化数据
	},
	// 初始化组件数据
	initData : function() {
		var me = this;
		var params = {
			entityName : 'TbPjMydCpxm'
		};
		/** ----------------登录用户数据------------------- */
		var xsId = this.xsId = MainPage.getXsId(); // 学生ID
		var loginName = this.loginName = MainPage.getLoginName(); // 登录用户名
		var userName = this.userName = MainPage.getUserName(); // 用户姓名
		var xnId = this.xnId = MainPage.getXnId();// 当前学年ID
		var xqId = this.xqId = MainPage.getXqId(); // 当前学期ID
		var xnMc = this.xnMc = MainPage.getXnMc(); // 学年名称
		var xqMc = this.xqMc = MainPage.getXqMc(); // 学期名称
		var xs_bjxx = this.bjxx = MainPage.getBjxx(); // 学生所在班级信息,为啥得不到信息？

		this.bjid = this.bjxx.id; // 班级ID
		this.bjmc = this.bjxx.mc; // 班级名称
		this.callService([{
			key : 'queryZzjgMc',
			params : {
				"bjId" : this.bjid
			}
		},// 取得所在专业、系部
		{
			key : 'initTeacherData',
			params : {
				"xsId" : this.xsId,
				"xnId" : this.xnId,
				"xqId" : this.xqId
			}
		}, {
			key : 'queryTableHeader4MydGrid',
			params : {
				entityName : 'TbPjMydCpxm'
			}
		}, {
			key : 'queryGridData',
			params : params
		}], function(data) {
			this.initComponent(data);
		});
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
		this.mydGridTranData = data['queryTableHeader4MydGrid'];
		this.mydTeacherData = data['initTeacherData'];
		this.mydGridTableData = data['queryMydGridData'];
		this.yxzy = data['queryZzjgMc'];
		this.month = this.getCurrentMonth(); // 当前月份
	},
	/**
	 * 调用测评明细方法
	 */
	getCpmxGridData : function() {
		var param = this.param = {
			entityName : this.entityName,
			'jzgId' : this.jzgId,
			'xnId' : this.xnId,
			'xqId' : this.xqId,
			'jzgh' : this.jzgh,
			'xsId' : this.xsId
		};
		this.callService({
			key : 'queryCpmxGridData',
			params : param
		}, function(data) {
			var mydData = this.mydData = data['queryCpmxGridData'].data;
			this.mydTableTpl.writeTo(this.mydTableCom, this.mydData);
			var el_jsxm = NS.get('myd_cp_teacher');
			var tpl = new NS.Template("{jzgXm}");
			tpl.writeTo(el_jsxm, {
				jzgXm : this.jsxm
			});
			var mydNames = document.getElementsByName("myd_table_td");
			for (var i = 0, len = mydNames.length; i < len; i++) {
				var tds = mydNames[i];
				var myds = tds.getElementsByTagName('input');
				for (var j = 0, leng = myds.length; j < leng; j++) {
					var myd = myds[j];
					if (myd.value == tds.id) {
						myd.checked = true;
					}
				}
			}
		}, this);
	},

	// 模板文件渲染
	initMydTpl : function(data) {
		NS.loadCss('app/pages/pj/css/studentpj.css');
		// 页面
		NS.loadFile('app/pages/pj/myd/tpl/mydTest.html', function(text) {
			this.mydTpl = new NS.Template(text);
			this.page = new NS.Component({
				data : [],// this.pjTeacherData.data,//待评教师信息
				tpl : this.mydTpl
			});
			this.component.add(this.page);
			this.initTemplates(data); // 初始化模板
			this.addListenerTemplate(); // 添加模板监听
		}, this);
	},
	initTemplates : function(data) {
		// 评教学生信息
		this.myd_pjxsTpl = new NS.Template(" <h2 class='org rgt-border'>学生满意度测评</h2>" + "<h3 class='blue pad-topa pad-lft'>{xsXm}</h3>"
				+ "<h4 class='pad-topb pad-lft'>{yxMc}-></h4>" + "<h4 class='pad-topb pad-lft'>{zyMc}-></h4>"
				+ "<h4 class='pad-topb pad-lft'>{bjMc} </h4>" + "<h3 class='pj-right org pad-topa'>{xnMc} {xqMc}-{month}月</h3>");

		this.pjxsData = {
			xsid : this.xsId,
			xsXm : this.xsXm,
			yxMc : this.yxzy.yxMc,
			zyMc : this.yxzy.zyMc,
			bjMc : this.bjmc,
			xnMc : this.xnMc,
			xqMc : this.xqMc,
			month : this.month
		};
		this.pjxsCom = new NS.Component({
			tpl : this.myd_pjxsTpl,
			data : this.pjxsData
		});
		this.pjxsCom.render('myd_pjxs_show');

		// 待评教师页面
		NS.loadFile('app/pages/pj/myd/tpl/myd_teacher.html', function(text) {
			var teacherData = this.mydTeacherData.data;
			this.testTeacherTpl = new NS.Template(text, {
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
			this.mydTeacherCom = new NS.Component({
				data : teacherData,
				tpl : this.testTeacherTpl
			});
			var el_jzgxm = document.getElementById('myd_cp_teacher');
			if (teacherData.length > 0) {
				this.jsxm = teacherData[0].jzgXm;
				this.jzgh = teacherData[0].jzgh;
				this.pjzt = teacherData[0].pjzt;
			} else {
				NS.Msg.info('提示', '待评教师不存在！');
			}
			if (this.pjzt == '已评') {
				this.commitBtn.hidden = true;
			} else {
				this.commitBtn.hidden = false;
			}
			this.mydTeacherCom.render('myd_cpTeacher_show');
			// 单击事件
			this.mydTeacherCom.on('click', function(e, el) {
				if (el.name != 'myd_teacher_name') {
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
				var el_jsxm = NS.get('myd_cp_teacher');
				var tpl = new NS.Template("{jzgXm}");
				tpl.writeTo(el_jsxm, {
					jzgXm : this.jsxm
				});

				// 调用后台服务,取教师满意度
				this.getCpmxGridData();
			}, this);

			// 测评项目
			NS.loadFile('app/pages/pj/myd/tpl/myd_table.html', function(text) {
				this.mydTableTpl = new NS.Template(text, {
					getValueByMyd : function(myd) {
						return myd;
					}
				});
				this.mydTableCom = new NS.Component({
					data : [],
					tpl : this.mydTableTpl
				});
				this.mydTableCom.render('myd_table_show');
				if (teacherData.length > 0) {
					var params = teacherData[0];
					this.jzgId = teacherData[0]['jzgId'];
				}
				this.getCpmxGridData();
			}, this);
		}, this);
	},

	// 添加模板监听
	addListenerTemplate : function() {
		var me = this;
		var commitBtn = this.commitBtn = document.getElementById('myd_commit'); // 提交
		var teacher = {}; // 待评教师
		var pjStu = {}; // 评教学生
		pjStu.xsId = me.xsId;
		pjStu.xsXm = me.userName;
		pjStu.bjId = me.bjid;
		pjStu.bjmc = me.bjmc;
		pjStu.xnId = me.xnId;
		pjStu.xqId = me.xqId;
		pjStu.jzgh = me.jzgh;
		pjStu.month = me.month;
		// “提交”按钮监听事件
		commitBtn.onclick = function(event) {
			var valueObj = new Array(); // 指标得分数据

			var rows = $("tr[name='mydRow']");
			for(var i=0;i<rows.length;i++){
				var cpxm = "";
				var radios = $(rows[i]).find("input[type='radio']");
				for(var j=0;j<radios.length;j++){
					var radio = radios[j];
					cpxm = $(radio).attr("cpxm");
					if (radio.checked == true) {
						var obj = {};
						obj['id'] = radio.id;
						obj['value'] = radio.value;
						obj['checked'] = radio.checked;
						valueObj.push(obj);
					}
				}
				if (valueObj.length < (i + 1)) {
					NS.Msg.info('提示', '还未对待评教师【<font color="red">' + cpxm + '</font>】进行测评');
					return;
				}
			}
			teacher.jzgId = me.jzgId;
			teacher.jzgXm = me.jsxm;
			teacher.jzgh = me.jzgh;
			var ztParam = {
				xnId : me.xnId,
				xqId : me.xqId,
				jzgId : me.jzgId,
				xsId : me.xsId
			};
			me.callService({
				key : 'queryMydCpzt',
				params : ztParam
			}, function(retData) {
				if (retData['queryMydCpzt'].success == true) {
					NS.Msg.info('提示', '该教师已测评');
					return;
				} else {
					var params = {
						'indexData' : valueObj,
						'teacher' : teacher,
						'pjStu' : pjStu
					};
					// 调用服务
					NS.Msg.changeTip('提示', '确定要提交评教结果吗?', function() {
						me.callService({
							key : 'saveMydTestData',
							params : params
						}, function(data) {
							if (data['saveMydTestData'].success == true) {
								NS.Msg.info('提示', '测评成功！');
								this.classStr = 'org';
								me.callService({
									key : 'initTeacherData',
									params : {
										"xsId" : this.xsId,
										"xnId" : this.xnId,
										"xqId" : this.xqId
									}
								}, function(retdata) {
									var teacherData = retdata['initTeacherData'].data
									this.mydTeacherCom.refreshTplData(teacherData);
								}, this)
								commitBtn.hidden = true;
							}
						})
					}, this)
				}
				this
			})
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
		this.mydPage = this.initMydTpl(data);
	}
})