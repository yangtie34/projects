/**
 * 定义学生评教页面
 */
NS.define('Pages.pj.pjgl.Xspj', {
	extend : 'Template.Page',
	entityName : 'TbPjXspj',
	modelConfig : {
		serviceConfig : {
			queryZzjgMc : 'getMcById',// 根据ID获得名称
			queryXspjHeader : 'base_queryForAddByEntityName', // 学生评教表头数据
			queryXspjData : 'base_queryTableContent',// 学生评教内容数据
			queryTableHeader4PjGrid : 'base_queryForAddByEntityName', // 评教指标grid
			// Header
			queryPjGridData : "base_queryTableContent", // 评教指标grid data
			// queryXspjIndx
			queryPjmxGridData : "queryPjmxData", // 取得教师的评教明细
			initXspjData : 'xspjService?selectXspjByXsxx',// 初始化学生评教学生及待评教师信息
			saveXspjData : "saveXspj",// 保存学生评教
			save : 'base_save', // 保存数据service名
			update : 'base_update'// 更新grid记录数据 service名,
		}
	},
	/**
	 * 初始化
	 */
	init : function() {
		this.initData(); // 初始化数据
	},
	/**
	 * ---------------------------------------------------------------
	 * 初始化数据，包括表头数据或表体数。需要调用后台业务service
	 * ----------------------------------------------------------------
	 */
	initData : function() {
		var me = this;
		var xnId = this.xnId = MainPage.getXnId();// 当前学年ID
		var xqId = this.xqId = MainPage.getXqId(); // 当前学期ID
		var xnMc = this.xnMc = MainPage.getXnMc(); // 学年名称
		var xqMc = this.xqMc = MainPage.getXqMc(); // 学期名称
		// var params = {entityName: this.entityName,'jzgId':this.jzgId};
		var indexParam = {
			entityName : 'TbPjPjmx'
		};
		/** ----------------登录用户数据------------------- */
		var xsId = this.xsId = MainPage.getXsId(); // 学生ID
		var loginName = this.loginName = MainPage.getLoginName(); // 登录用户名
		var userName = this.userName = MainPage.getUserName(); // 用户姓名
		var xs_bjxx = this.bjxx = MainPage.getBjxx(); // 学生所在班级信息

		this.bjid = this.bjxx.id; // 班级ID
		this.bjmc = this.bjxx.mc; // 班级名称
		this.xsXm = this.userName;
		var initParam = {
			xsId : this.xsId,
			start : 0,
			limit : 25
		};
		this.callService([{
			key : 'initXspjData',
			params : {
				xsId : this.xsId,
				xnId : xnId,
				xqId : xqId,
				bjId : this.bjid
			}
		}, {
			key : 'queryZzjgMc',
			params : {
				"bjId" : this.bjid
			}
		},// 取得所在专业、系部
		{
			key : 'queryTableHeader4PjGrid',
			params : indexParam
		}, {
			key : 'queryPjGridData',
			params : indexParam
		}

		], function(data) {
			this.initComponent(data);
		});
	},
	/**
	 * --------------------------------------------------------------------------------
	 * 初始化页面组件
	 * 
	 * @param {}
	 *            tranData
	 * @param {}
	 *            tabledata
	 *            --------------------------------------------------------------------------------
	 */
	initComponent : function(data) {
		/**
		 * var loginUser=this.loginUser=data['loginUser'];
		 * if(this.loginUser.length>0){ this.xsXm=this.loginUser[0].xm; }else{
		 * NS.Msg.info('提示','登录用户异常！');return; }
		 */
		this.initPage(data);
		this.pjGridTranData = data['queryTableHeader4PjGrid'];
		this.pjTeacherData = data['initXspjData'];
		this.pjGridTableData = data['queryPjGridData'];
		this.yxzy = data['queryZzjgMc'];
		this.xspjTranData = data['queryXspjHeader'];
		this.xspjTableData = data['queryXspjData'];
	},
	/** **************获取html文件******************************* */
	initXspjTpl : function(data) {
		NS.loadCss('app/pages/pj/css/studentpj.css');
		// 页面
		NS.loadFile('app/pages/pj/pjgl/template/xspj.html', function(text) {
			this.xspjTpl = new NS.Template(text);
			this.page = new NS.Component({
				data : [],// this.pjTeacherData.data,//待评教师信息
				tpl : this.xspjTpl
			});
			this.component.add(this.page);
			this.initTemplates(data);
			this.addListenerTemplate();
		}, this);
	},
	addListenerTemplate : function() {
		var me = this;
		var commitBtn = this.commitBtn = document.getElementById('xspj_commit'); // 提交
		var teacher = {}; // 待评教师
		var pjStu = {}; // 评教学生
		pjStu.xsId = me.xsId;
		pjStu.xsXm = me.xsXm;
		pjStu.bjId = me.bjid;
		pjStu.bjmc = me.bjmc;
		pjStu.xnId = me.xnId;
		pjStu.xqId = me.xqId;
		// 清空事件
		/**
		 * clearBtn.onclick=function(event){ var
		 * fsValue=document.getElementsByName('xspj_index_pingfen_name');
		 * for(var i=0,len = fsValue.length;i<len;i++){ var obj = {}; var el =
		 * fsValue[i]; if(el.value!=''){ el.value = ''; } } },
		 */
		// “提交”按钮监听事件
		commitBtn.onclick = function(event) {
			var pingfens = document.getElementsByName('xspj_index_pingfen_name');
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
					obj['fz'] = $(el).attr("fz");
					valueObj.push(obj);
				}
			}
			if (me.jzgId == undefined) {
				NS.Msg.info('提示', '还未对待评教师进行评价');
				return;
			} else {
				// var yjjy=document.getElementById("xspj_yjjy").value;
				teacher.jzgId = me.jzgId;
				teacher.jzgXm = me.jsxm;
				teacher.kcmc = me.kcmc;
				// teacher.pjyj=yjjy;
				if (valueObj.length > 0) {
					var params = {
						'indexData' : valueObj,
						'teacher' : teacher,
						'pjStu' : pjStu
					};
					var reg = new RegExp("^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$");
					for (var j = 0; j < valueObj.length; j++) {
						var fz = parseFloat(valueObj[j].fz);
						var recordValue = valueObj[j].value;
						if (recordValue == '' || recordValue == null) {
							NS.Msg.info('提示', '评价得分存在空值！');
							return;
						}
						if (!/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(recordValue)) {
							NS.Msg.info('提示', '只能输入数字');
							return false;
						}
						if (parseFloat(recordValue) < 0) {
							NS.Msg.info('提示', '指标得分数不能为负数');
							return;
						}
						if (parseFloat(recordValue) > parseFloat(fz)) {
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
							key : 'saveXspjData',
							params : params
						}, function(data) {
							if (data.saveXspjData.success == true) {
								NS.Msg.info('提示', '评教成功！');
								this.classStr = 'org';
								me.callService({
									key : 'initXspjData',
									params : {
										xsId : this.xsId,
										xnId : this.xnId,
										xqId : this.xqId,
										bjId : this.bjid
									}
								}, function(retdata) {
									var teacherData = retdata['initXspjData'].data
									this.xspjTeacherCom.refreshTplData(teacherData);
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
		}
	},
	/**
	 * 调用评教明细方法
	 */
	getPjmxGridData : function() {
		var param = this.param = {
			entityName : this.entityName,
			'jzgId' : this.jzgId,
			'xnId' : this.xnId,
			'xqId' : this.xqId,
			'kcmc' : this.kcmc,
			'xsId' : this.xsId
		};
		this.callService({
			key : 'queryPjmxGridData',
			params : param
		}, function(data) {
			this.xspjTableTpl.writeTo(this.xspjTableCom, data['queryPjmxGridData']);
			var el_jsxm = NS.get('xspj_pj_teacher');
			var el_kcmc = NS.get('xspj_pj_kcmc');
			var tpl = new NS.Template("{jzgXm} {kcmc} ");
			tpl.writeTo(el_jsxm, {
				jzgXm : this.jsxm
			});
			tpl.writeTo(el_kcmc, {
				kcmc : this.kcmc
			});
			
			var allSum = 0;
			for(var i=0;i<data['queryPjmxGridData'].length;i++){
				var df = data['queryPjmxGridData'][i]['idxDf'];
				if(df){
					allSum += parseFloat(df);	
				}
			}
			$("#dfSum").html("（" + allSum + "）");

			var oldValue = "";
			$("input[name='xspj_index_pingfen_name']").bind("focus", function() {
				oldValue = $(this).val();
			});
			$("input[name='xspj_index_pingfen_name']").bind("blur", function() {
				var val = $(this).val();
				var fz = $(this).attr("fz");
				
				if(val === "" && oldValue === ""){
					return;
				}
				if (val === "") {
					val = 0;
				}
				
				var zf = $("#dfSum").text();
				if (zf !== "") {
					zf = zf.substring(1, zf.length - 1);
				} else {
					zf = 0;
				}
				
				if (oldValue !== "") {
					zf = parseFloat(zf) - parseFloat(oldValue)
				}
				$("#dfSum").html("（" + zf + "）");

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
				$("#dfSum").html("（" + zf + "）");
			});
			/**
			 * if(this.pjyj == null){this.pjyj=""}; var el_jsxm =
			 * NS.get('xspj_pj_teacher'); var el_kcmc = NS.get('xspj_pj_kcmc');
			 * var yjjy4jsxm = NS.get('yjjy_jsxm'); var yjjy4kcmc =
			 * NS.get('yjjy_kcmc'); var yjjy4Cont = NS.get('xspj_yjjy'); var tpl =
			 * new NS.Template("{jzgXm} {kcmc} {pjyj}");
			 * tpl.writeTo(el_jsxm,{jzgXm:this.jsxm});
			 * tpl.writeTo(el_kcmc,{kcmc:this.kcmc});
			 * tpl.writeTo(yjjy4jsxm,{jzgXm:this.jsxm});
			 * tpl.writeTo(yjjy4kcmc,{kcmc:this.kcmc});
			 * tpl.writeTo(yjjy4Cont,{pjyj:this.pjyj});
			 */
	}, this);
	},

	initTemplates : function(data) {
		// 评教学生信息
		this.xspj_pjxsTpl = new NS.Template(" <h2 class='org rgt-border'>学生评教</h2>" + "<h3 class='blue pad-topa pad-lft'>{xsXm}</h3>"
				+ "<h4 class='pad-topb pad-lft'>{yxMc}</h4>" + "<h4 class='pad-topb pad-lft'>{zyMc}</h4>"
				+ "<h4 class='pad-topb pad-lft'>{bjMc}</h4>" + "<h3 class='pj-right org pad-topa'>{xnMc} {xqMc}</h3>");

		this.pjxsData = {
			xsid : this.xsId,
			xsXm : this.xsXm,
			yxMc : this.yxzy.yxMc,
			zyMc : this.yxzy.zyMc,
			bjMc : this.bjmc,
			xnMc : this.xnMc,
			xqMc : this.xqMc
		};
		this.pjxsCom = new NS.Component({
			tpl : this.xspj_pjxsTpl,
			data : this.pjxsData
		});
		this.pjxsCom.render('xspj_pjxs_show');
		// 待评教师页面
		NS.loadFile('app/pages/pj/pjgl/template/xspj_teacher.html', function(text) {
			var teacherData = this.pjTeacherData.data;
			this.xspjTeacherTpl = new NS.Template(text, {
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
			this.xspjTeacherCom = new NS.Component({
				data : teacherData,
				tpl : this.xspjTeacherTpl
			});
			var el_jzgxm = document.getElementById('xspj_pj_teacher');
			if (teacherData.length > 0) {
				this.jsxm = teacherData[0].jzgXm;
				this.kcmc = teacherData[0].kcmc;
				// this.pjyj=teacherData[0].pjyj;
				this.pjzt = teacherData[0].pjzt;
			} else {
				NS.Msg.info('提示', '待评教师不存在！');
			}
			this.xspjTeacherCom.render('xspj_pjTeacher_show');
			if (this.pjzt == '已评') {
				this.commitBtn.hidden = true;
			} else {
				this.commitBtn.hidden = false;
			}
			// 单击事件
			this.xspjTeacherCom.on('click', function(e, el) {
				if (el.name != 'xspj_teacher_name') {
					return;
				}
				this.jsxm = el.innerHTML;
				var element = NS.get(el);// 取得dom元素对象
				this.jzgId = element.getAttribute('jzgId'); // 教职工ID
				this.kcmc = element.getAttribute('kcmc'); // 课程名称
				var _pjzt = element.getAttribute('pjzt'); // 评教状态
				// this.pjyj=element.getAttribute('pjyj'); //评教意见
				if (_pjzt == '已评') {
					this.commitBtn.hidden = true;
				} else {
					this.commitBtn.hidden = false;
				}
				var el_jsxm = NS.get('xspj_pj_teacher');
				var el_kcmc = NS.get('xspj_pj_kcmc');
				var tpl = new NS.Template("{jzgXm} {kcmc} {pjzt}");
				tpl.writeTo(el_jsxm, {
					jzgXm : this.jsxm
				});
				tpl.writeTo(el_kcmc, {
					kcmc : this.kcmc
				});
				// tpl.writeTo(el_pjzt,{pjzt:this.pjzt});
				/**
				 * if(this.pjyj == null){this.pjyj=""}; var el_jsxm =
				 * NS.get('xspj_pj_teacher'); var el_kcmc =
				 * NS.get('xspj_pj_kcmc'); var yjjy4jsxm = NS.get('yjjy_jsxm');
				 * var yjjy4kcmc = NS.get('yjjy_kcmc'); var yjjy4Cont =
				 * NS.get('xspj_yjjy');
				 * 
				 * var tpl = new NS.Template("{jzgXm} {kcmc}");
				 * tpl.writeTo(el_jsxm,{jzgXm:this.jsxm});
				 * tpl.writeTo(el_kcmc,{kcmc:this.kcmc});
				 * tpl.writeTo(el_pjzt,{pjzt:this.pjzt});
				 * tpl.writeTo(yjjy4jsxm,{jzgXm:this.jsxm});
				 * tpl.writeTo(yjjy4kcmc,{kcmc:this.kcmc});
				 * tpl.writeTo(yjjy4Cont,{pjyj:this.pjyj});
				 */
				// 调用后台服务,取教师的得分明细
				this.getPjmxGridData();
			}, this);

			/**
			 * 评教指标内容
			 */
			NS.loadFile('app/pages/pj/pjgl/template/xspj_index.html', function(text) {
				this.xspjTableTpl = new NS.Template(text);
				this.xspjTableCom = new NS.Component({
					data : [],
					tpl : this.xspjTableTpl
				});
				this.xspjTableCom.render('xspj_table_show');
				var teaData = this.pjTeacherData.data;
				if (teaData.length > 0) {
					this.jzgId = teaData[0]['jzgId'];
					this.kcmc = teaData[0]['kcmc'];
				}
				this.getPjmxGridData();
			}, this);

		}, this);

	},
	/** -----------------初始化页面----------------------- */
	initPage : function(data) {
		var component = this.component = new NS.container.Panel({
			border : false,
			layout : 'fit',
			autoScroll : true
		});
		this.setPageComponent(component);
		this.xspjPage = this.initXspjTpl(data);
	}
})