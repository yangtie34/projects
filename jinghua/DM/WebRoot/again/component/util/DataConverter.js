/**
 * @class NS.util.DataConverter
 * @extends NS.Base
 * @private
 *    数据转换工具类
 */
NS.define('NS.util.DataConverter',{
    singleton : true,
	/**
	 * 
	 * 编码数据标准格式(暂未用到，保留)
	 * 
	 * @private
	 * @param {Array} conf 待转换的编码数据
	 * @returns {Array}
	 */
	codedataFormat : function(conf) {
		if (conf == null || conf == 'null') return null;
		if (conf instanceof Array) {
			var arr = [];
			for ( var c in conf) {
				//如果可用的话(暂不确定后台数据是否只将可用信息传到前端)
				if(c.sfky=='1') arr.push({name : c.mc, value : c.id });
			}
			if (arr.length == 0) return null;
			return arr;
		}
		return null;//信息错误
	},
	
	/**
	 * 
	 * 数据标准格式
	 * 
	 * @private
	 * @param{Object}
	 *            conf 待转换的数据
	 * @return {Object}
	 */
	dataFormat :  function(conf) {
		var codeData = {entityName : conf.bmst,data : conf.bmsj||[]};//this.codedataFormat(conf.bmsj); 编码数据暂不作转换
		return {
			id 					 : conf.id,// id--ID
			name 				 : conf.sx,// 字段属性名--SX
			nameCh 				 : conf.sxzwm,// 字段属性中文名--SXZWM
			dataType 			 : conf.sxxslx,// 数据类型--SXXSLX
			isAddFormField 		 : conf.sfxszd == 1,// 是否为表单新增字段（先判断新增字段属性再判断此字段）--SFXSZD
			isQuery 			 : conf.sfcxzd == 1,// 是否为查询字段（控制单字段查询、表头查询、高级查询的显示）--SFCXZD
			dbLength 			 : conf.cd,// 数据库字段长度--CD
			entityName 			 : conf.ssstm,// 所属实体名--SSSTM
			codeData    		 : codeData,// 编码数据--BMBSTM+BMBBZDM
			xtype 				 : conf.sxzjlx,// 组件类型--SXZJLX
			drillExp 		  	 : conf.sfxz,// 下钻属性表达式--SFXZ
			regExp 				 : conf.jyzzbds,// 正则表达式--JYZZBDS
			regValidateErrorMesg : conf.zzjycwxx,// 正则表达式校验错误信息--ZZJYCWXX
			validateType 		 : conf.jylx,// 校验类型--JYLX
			fieldsetGroup 		 : conf.fl,// 分组信息--FL<(目前暂时弃用)>
			isColumnShow 		 : conf.sflbxszd == 1,// Grid中是否显示该列（此属性依赖isColumnCreate）--SFLBXSZD
			isColumnCreate 		 : conf.sflbzd == 1,// Grid中是否创建该列--SFLBZD
			columnWidth 		 : conf.lbkd==null? 150:conf.lbkd,// Grid中该列宽度,默认150--LBKD
			isAddField 			 : conf.xzzdsx == '1',// 新增Form组件中，该列是否出现--XZZDSX
			isUpdateField 		 : conf.xgzdsx == '1',// 修改Form组件中，该列是否出现--XGZDSX
			isBlank 			 : conf.sffk == 1,// 可为空的 --SFFK
			editable 			 : conf.sfkbj == 1,// 可编辑的 --SFKBJ,在可编辑grid中生效
			cc 					 : conf.cc// 树形数据中的层次--CC
		};
	},
	/**
	 * 对单一的JSON对象进行转换的方法
	 * @param {Object} json
	 * @return
	 */
	entity2Standard : function(json){
        return this.dataFormat(json);  	
	},
	/**
	 * 
	 * 从实体数据转换为标准数据的标准接口方法
	 * 
	 * @param {Array} dataArr 实体数据集
	 * @returns {Array} 转换后的标准数据集
	 */
	entitysToStandards:function(dataArr){
		if (dataArr instanceof Array) {
			var arr = [];
			for ( var d in dataArr) {
				arr.push(this.dataFormat(dataArr[d]));
			}
			return arr;
		}
		return null;//数据有误时返回
	}
});
(function(){
  var alias = NS.Function.alias;
   /**
     * @member NS
     * @method E2S
     * @inheritdoc NS.util.DataConverter#entitysToStandards
     */
	NS.E2S = alias(NS.util.DataConverter,'entitysToStandards');
})();