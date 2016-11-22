/**
 * 该对象描述标准数据的格式。对象中的属性在前台均被使用。
 * @type 
 */
DataFormat = {

		id 				: 1000000000021391,//--ID
		name 			: 'zt',// 字段属性名。--SX
		nameCh 			: '状态',// 字段属性中文名。--SXZWM
		dataType 		: 'Long',// 数据类型。--SXXSLX
		isAddFormField 	: 1,// 是否为表单新增字段（先判断新增字段属性再判断此字段）。--SFXSZD
		isQuery 		: 1,// 是否为查询字段（控制单字段查询、表头查询、高级查询的显示）。--SFCXZD
		dbLength 		: 20,// 数据库字段长度。--CD
		entityName 		: 'TbJwJshp',// 所属实体名。--SSSTM
		codeData 		: {},// 编码数据。--BMBSTM+BMBBZDM // 编码表标准代码的含义会依据编码表实体的类型不同而不同。
		xtype 			: 'combobox',// 组件类型。--XSZJLX
		drillExp 		: '0',// 下钻属性表达式。--SFXZ
		regExp 			: '',// 正则表达式。--JYZZBDS
		regValidateErrorMesg : '',// 正则表达式校验错误信息。--ZZJYCWXX
		validateType 	: '',// 校验类型。--JYLX
		fieldsetGroup 	: '',// 分组信息。--FL
		isColumnShow 	: 1,// Grid中是否显示该列（此属性依赖isColumnCreate）。--SFLBXSZD
		isColumnCreate 	: 1,// Grid中是否创建该列。--SFLBZD
		columnWidth 	: 100,// Grid中该列宽度。--LBKD
		isAddField 		: 1,// 新增Form组件中，该列是否出现。--XZZDSX
		isUpdateField 	: 1,// 修改Form组件中，该列是否出现。--XGZDSX
		isBlank 		: 0,// 可为空的。--SFFK
		editable 		: 1,// 可编辑的。--SFKBJ
		cc 				: '',// 树形数据中的层次。--CC
		codeEntityName    : '',//树形数据的编码实体名
		
	
}