-- 完善没有维护的基础数据

-- 学位表 设置第一层的排序号 （博士、硕士、学士）
update t_code_degree t set t.order_ = t.code_ where level_ = 1;

-- 专业技术职务 设置排序号 （教授、副教授） TODO talk
update t_code_zyjszw t set t.order_ = t.code_;

-- 修正 学生状态 代码类型
update t_code t set t.code_type = 'STU_STATE_CODE' where t.code_type = 'STU_STATUS_CODE';

-- 更新 行政班历史数据的 年级（入学年级）字段【如果 no_字段的前四位是年级的话】
update t_classes t set t.grade=substr(no_,1,4) where t.grade is null;

-- 更新 行政组织机构表 LEVEL_TYPE（维护与教学机构表相同ID的数据的层次类型）
update t_code_dept t set t.level_type = (select t2.level_type from t_code_dept_teach t2 where t.id = t2.id)
where exists (select 0 from t_code_dept_teach t2 where t.id = t2.id and t.level_type is null)


-- 插入高级查询服务-源 数据
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('DEPT_ID', '行政机构', 'common', 'tree', '', 'advancedService?getDeptDataService', '');
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('DEPT_TEACH_ID', '教学机构', 'common', 'tree', '', 'advancedService?getDeptTeachDataService', '');
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('DEPT_TEACH_TEACH_ID', '教学机构', 'common', 'tree', '', 'advancedService?getDeptTeachTeachDataService', '');
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SCHOOL_YEAR', '学年', 'common', '', '', 'businessService?queryBzdmSchoolYear', '');
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SEX_CODE', '性别', 'common', '', '', '', '');
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('TERM_CODE', '学期', 'common', '', '', 'businessService?queryBzdmTermCode', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('EDU_ID_STU', '培养层次', 'stu', '', '', 'businessService?queryBzdmStuEducationList', '');
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('GRADE', '年级', 'stu', '', '', 'businessService?queryBzdmNj', '');
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('STU_ROLL_CODE', '学籍状态', 'stu', '', '', '', '');

-- 插入学习形式代码----
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '高等教育自学考试', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '夜大学', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '职工大学', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('05', '电视大学', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('06', '业余大学', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('07', '函授', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('08', '研修班', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('09', '高等学校进修', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('10', '党校', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('11', '社会主义学院', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('15', '培训', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('99', '其它', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '普通高等学校', 'GB', 'LEARNING_FORM_CODE', '学习形式代码', 1, null);
-----------------------------------------------------------
--重新插入学习方式代码-------------
delete from t_code where code_type='LEARNING_STYLE_CODE';

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '脱产', 'HB', 'LEARNING_STYLE_CODE', '学习方式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '半脱产', 'HB', 'LEARNING_STYLE_CODE', '学习方式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '业余', 'HB', 'LEARNING_STYLE_CODE', '学习方式代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('4', '全日制', 'HB', 'LEARNING_STYLE_CODE', '学习方式代码', 1, null);
---------------------------------------------------------------
--重新插入学历编码表---------------------------
delete from t_code_education;

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('10', '10', '研究生', '-1', '', 1, '', 1, 3);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('17', '17', '研究生班毕业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('18', '18', '研究生班结业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('19', '19', '研究生班肄业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('28', '28', '大学普通班毕业', '20', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('30', '30', '专科生', '-1', '', 1, '', 1, 2);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('40', '40', '中等职业教育', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('46', '46', '职业高中肄业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('47', '47', '技工学校毕业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('48', '48', '技工学校结业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('49', '49', '技工学校肄业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('60', '60', '普通高级中学教育', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('70', '70', '初级中学教育', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('80', '80', '小学教育', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('90', '90', '其他', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('01', '01', '博士', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('02', '02', '博士毕业', '01', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('03', '03', '相当博士毕业', '01', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('04', '04', '博士结业', '01', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('05', '05', '博士肄业', '01', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('11', '11', '博士研究生毕业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('12', '12', '博士研究生结业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('13', '13', '博士研究生肄业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('14', '14', '硕士研究生毕业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('15', '15', '硕士研究生结业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('16', '16', '硕士研究生肄业', '10', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('20', '20', '本科生', '-1', '', 1, '', 1, 1);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('21', '21', '大学本科毕业', '20', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('22', '22', '大学本科结业', '20', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('23', '23', '大学本科肄业', '20', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('24', '24', '相当大学毕业', '21', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('25', '25', '大学结业', '21', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('26', '26', '大学肄业', '21', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('31', '31', '大学专科毕业', '30', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('32', '32', '大学专科结业', '30', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('33', '33', '大学专科肄业', '30', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('34', '34', '专科结业', '31', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('35', '35', '专科肄业', '31', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('41', '41', '中等专科毕业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('42', '42', '中等专科结业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('43', '43', '中等专科肄业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('44', '44', '职业高中毕业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('45', '45', '职业高中结业', '40', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('51', '51', '技工学校', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('52', '52', '技工学校毕业', '51', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('53', '53', '相当技工学校毕业', '51', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('54', '54', '技工学校结业', '51', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('55', '55', '技工学校肄业', '51', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('61', '61', '普通高中毕业', '60', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('62', '62', '普通高中结业', '60', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('63', '63', '普通高中肄业', '60', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('64', '64', '农业高中毕业', '61', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('65', '65', '相当高中毕业', '61', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('66', '66', '高中结业', '61', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('67', '67', '高中肄业', '61', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('71', '71', '初中毕业', '70', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('72', '72', '初中毕业', '71', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('73', '73', '初中肄业', '70', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('74', '74', '农业初中毕业', '71', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('75', '75', '相当初中毕业', '71', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('76', '76', '初中结业', '71', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('77', '77', '初中肄业', '71', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('81', '81', '小学毕业', '80', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('82', '82', '小学毕业', '81', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('83', '83', '小学肄业', '80', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('84', '84', '小学结业', '81', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('85', '85', '小学肄业', '81', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('91', '91', '半文盲', '-1', '', 1, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('92', '92', '文盲', '91', '', 2, '', 1, null);

insert into t_code_education (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('99', '99', '未说明', '-1', '', 1, '', 1, null);
---------------------------------------------------
-----政治面貌代码名称缩短，删除重新添加---------------
delete from t_code where code_type = 'POLITICS_CODE';

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, 5);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '预备党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, 4);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '团员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, 1);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '民革会员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('05', '民主同盟盟员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('06', '建国会会员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('07', '促进会会员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('08', '民主党党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('09', '致公党党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('10', '九三学社社员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('11', '台湾自治同盟盟员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('12', '无党派人士', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, 3);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('13', '群众', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, 2);
-----------------------------------------------------------------------------
-----------------------行政区划(T_CODE_ADMINI_DIV)增加根节点，并修改各省的父节点-----------------------
delete from t_code_admini_div where  level_ < 2; 
insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('450000', '450000', '广西壮族自治区', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('150000', '150000', '内蒙古自治区', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('210000', '210000', '辽宁省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('110000', '110000', '北京市', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('120000', '120000', '天津市', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('130000', '130000', '河北省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('220000', '220000', '吉林省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('230000', '230000', '黑龙江省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('310000', '310000', '上海市', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('320000', '320000', '江苏省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('140000', '140000', '山西省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('370000', '370000', '山东省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('340000', '340000', '安徽省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('350000', '350000', '福建省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('430000', '430000', '湖南省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('440000', '440000', '广东省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('360000', '360000', '江西省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('530000', '530000', '云南省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('630000', '630000', '青海省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('640000', '640000', '宁夏回族自治区', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('710000', '710000', '台湾省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('810000', '810000', '香港特别行政区', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('820000', '820000', '澳门特别行政区', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('460000', '460000', '海南省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('500000', '500000', '重庆市', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('510000', '510000', '四川省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('540000', '540000', '西藏自治区', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('620000', '620000', '甘肃省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('520000', '520000', '贵州省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('650000', '650000', '新疆维吾尔自治区', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('610000', '610000', '陕西省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('410000', '410000', '河南省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('420000', '420000', '湖北省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('330000', '330000', '浙江省', '0', '', 1, '', 1, null);

insert into t_code_admini_div (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0', '000000', '中国', '-1', '', 0, '', 1, null);

-- 培养层次代码
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '博士', 'HB', 'TRAINING_LEVEL_CODE', '培养层次代码', '1', '1');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '硕士', 'HB', 'TRAINING_LEVEL_CODE', '培养层次代码', '1', '2');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '本科', 'HB', 'TRAINING_LEVEL_CODE', '培养层次代码', '1', '3');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('4', '专科', 'HB', 'TRAINING_LEVEL_CODE', '培养层次代码', '1', '4');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('5', '其他', 'HB', 'TRAINING_LEVEL_CODE', '培养层次代码', '1', '5');

-- 2016-12-15
-- 研究生培养方向代码
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '学术性', 'XB', 'GRADUATE_FOSTER_DIRECTION_CODE', '研究生培养方向代码', '1', '1');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '专业性', 'XB', 'GRADUATE_FOSTER_DIRECTION_CODE', '研究生培养方向代码', '1', '2');
-- 研究生录取类别代码
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '定向就业', 'XB', 'GRADUATE_ENROLL_CATEGORY_CODE', '研究生录取类别代码', '1', '1');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '非定向就业', 'XB', 'GRADUATE_ENROLL_CATEGORY_CODE', '研究生录取类别代码', '1', '2');
--2016-12-20
--研究生学习方式代码
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '全日制', 'XB', 'GRADUATE_LEARNING_STYLE_CODE', '研究生学习方式代码', '1', '1');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '非全日制', 'XB', 'GRADUATE_LEARNING_STYLE_CODE', '研究生学习方式代码', '1', '2');

-- 1.修改 及 处理 教学机构与行政机构的服务源 （仅用于已执行以上sql的数据库 执行1、2）		20160630
-- delete from t_Service_Advanced_Source where code_ = 'DEPT_ID';
-- insert into t_Service_Advanced_Source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('DEPT_TEACH_ID', '教学机构', 'common', 'tree', '', 'advancedService?getDeptTeachDataService', '');
-- insert into t_Service_Advanced_Source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('DEPT_ID', '行政机构', 'common', 'tree', '', 'advancedService?getDeptDataService', '');
-- 2.修改已初始化的高级查询配置		20160630
-- alter table t_service_advanced add (ISTRUE number(1) );
-- update t_service_advanced set ISTRUE = 1;
-- update t_service_advanced set CODE_ = 'DEPT_TEACH_ID' WHERE CODE_ = 'DEPT_ID';
--3.修改t_service_advanced_source的部分字段数据类型长度      2016-10-19
-- alter table t_service_advanced_source modify CODE_ VARCHAR2(60);
-- alter table t_service_advanced_source modify SERVICE VARCHAR2(100);
-- alter table t_service_advanced_source modify PARAM VARCHAR2(100);