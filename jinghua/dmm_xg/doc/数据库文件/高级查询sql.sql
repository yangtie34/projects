/*==============================================================*/
/* Table: T_SERVICE_ADVANCED_SOURCE     服务-高级查询-源 表                        */
/*==============================================================*/
create table T_SERVICE_ADVANCED_SOURCE  (
   CODE_                VARCHAR2(60)                    not null,
   NAME_                VARCHAR2(20),
   GROUP_               VARCHAR2(20)                    not null,
   TYPE_                VARCHAR2(20),
   TABLE_               VARCHAR2(60),
   SERVICE              VARCHAR2(100),
   PARAM                VARCHAR2(100),
   constraint PK_T_SERVICE_ADVANCED_SOURCE primary key (CODE_)
);
comment on table T_SERVICE_ADVANCED_SOURCE is
'服务-高级查询-源';
/*==============================================================*/
/* Table: T_SERVICE_ADVANCED             服务-高级查询 表                       */
/*==============================================================*/
create table T_SERVICE_ADVANCED  (
   TAG                  VARCHAR2(60),
   CODE_                VARCHAR2(60),
   ISALL                NUMBER(1),
   ORDER_               NUMBER(4),
   SERVICE              VARCHAR2(60),
   PARAM                VARCHAR2(60),
   ISTRUE               NUMBER(1),
   constraint PK_T_SERVICE_ADVANCED primary key (TAG,CODE_)
);
comment on table T_SERVICE_ADVANCED is
'服务-高级查询';

-- 2016-10-19
---- t_Service_Advanced_Source 数据重新添加  
-------------- 删除语句  ----------
delete from t_Service_Advanced_Source;
-------------- 插入语句  ----------
-- 高级查询条件（公共类：教师、学生通用）
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('DEPT_TEACH_TEACH_ID', '教学机构', 'common', 'tree', '', 'advancedService?getDeptTeachTeachDataService', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SCHOOL_YEAR', '学年', 'common', '', '', 'businessService?queryBzdmSchoolYear', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('DEPT_TEACH_ID', '教学机构', 'common', 'tree', '', 'advancedService?getDeptTeachDataService', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('TERM_CODE', '学期', 'common', '', '', 'businessService?queryBzdmTermCode', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SEX_CODE', '性别', 'common', '', '', '', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('DEPT_ID', '行政机构', 'common', 'tree', '', 'advancedService?getDeptDataService', '');

-- 高级查询条件（学生类）
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SUBJECT_STU', '学科', 'stu', '', '', 'businessService?queryBzdmSubjectList', 'stu');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('STU_ROLL_CODE', '学籍状态', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,STU_ROLL_CODE,STU_ROLL_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('GRADE', '年级', 'stu', '', '', 'businessService?queryBzdmNj', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('EDU_ID_STU', '培养层次', 'stu', '', '', 'businessService?queryBzdmStuEduList', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('LENGTH_SCHOOLING', '学制', 'stu', '', '', 'businessService?queryBzdmLengthSchooling', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('STU_STATE_CODE', '学生状态', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,STU_STATE_CODE,STU_STATE_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('POLITICS_CODE_STU', '政治面貌', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,POLITICS_CODE,POLITICS_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('NATION_CODE_STU', '民族', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,NATION_CODE,NATION_CODE,count');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('LEARNING_FORM_CODE', '学习形式', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,LEARNING_FORM_CODE,LEARNING_FORM_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('STU_FROM_CODE', '生源类别', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,STU_FROM_CODE,STU_FROM_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('RECRUIT_CODE', '招生类别', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,RECRUIT_CODE,RECRUIT_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('LEARNING_STYLE_CODE', '学习方式', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,LEARNING_STYLE_CODE,LEARNING_STYLE_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('ANMELDEN_CODE_STU', '户口性质', 'stu', '', '', 'businessService?queryBzdmByTCodeIsUse', 'stu,ANMELDEN_CODE,ANMELDEN_CODE,other');

-- 高级查询条件（教师类）
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('DEGREE_ID', '学位', 'tea', '', '', 'businessService?queryBzdmTeaDegreeList', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('EDU_ID', '学历', 'tea', '', '', 'businessService?queryBzdmTeaEduList', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('NATION_CODE_TEA', '民族', 'tea', '', '', 'businessService?queryBzdmByTCodeIsUse', 'tea,NATION_CODE,NATION_CODE,count');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('ZYJSZW_JB_CODE', '专业技术职务等级', 'tea', '', '', 'businessService?queryBzdmZyjszwJbList', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('ZYJSZW_ID', '专业技术职务', 'tea', '', '', 'businessService?queryBzdmZyjszwList', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('AUTHORIZED_STRENGTH_ID', '教职工类型', 'tea', '', '', 'businessService?queryBzdmTeaType', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('POLITICS_CODE_TEA', '政治面貌', 'tea', '', '', 'businessService?queryBzdmByTCodeIsUse', 'tea,POLITICS_CODE,POLITICS_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SUBJECT_TEA', '学科', 'tea', '', '', 'businessService?queryBzdmSubjectList', 'tea');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('ANMELDEN_CODE_TEA', '户口性质', 'tea', '', '', 'businessService?queryBzdmByTCodeIsUse', 'tea,ANMELDEN_CODE,ANMELDEN_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SKILL_MOVES_CODE', '工人技术等级', 'tea', '', '', 'businessService?queryBzdmByTCodeIsUse', 'tea,SKILL_MOVES_CODE,SKILL_MOVES_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SFSSJS', '是否双师教师', 'tea', '', '', 'businessService?queryBzdmSsjsList', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('BZLB_CODE', '编制类别', 'tea', '', '', 'businessService?queryBzdmByTCodeIsUse', 'tea,BZLB_CODE,BZLB_CODE,other');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('TEA_SOURCE_ID', '教职工来源', 'tea', '', '', 'businessService?queryBzdmTeaSource', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('TEA_STATUS_CODE', '教职工状态', 'tea', '', '', 'businessService?queryBzdmByTCodeIsUse', 'tea,TEA_STATUS_CODE,TEA_STATUS_CODE,other');

-- 高级查询条件（业务类：各功能自己解析）
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('SCORE_TARGET', '成绩指标', 'business', '', '', 'businessService?queryBzdmScoreTarget', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('COURSE_NATURE_CODE', '课程性质', 'business', '', '', '', '');

insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('COURSE_ATTR_CODE', '课程属性', 'business', '', '', '', '');


-- 2016-11-14
-------插入各页面高级查询参数数据
----------------删除语句----
delete from t_Service_Advanced;
--------------  插入语句-----------
-- 插入 在籍生概况 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'DEPT_TEACH_ID', null, 1, '', 'xg:stuEnroll', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'EDU_ID_STU', '', 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'LENGTH_SCHOOLING', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'LEARNING_STYLE_CODE', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuEnroll', 'LEARNING_FORM_CODE', 1, 10, '', '', '1');

-- 插入 学生工作者 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'DEPT_TEACH_ID', '', 1, '', 'xg:worker', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'EDU_ID', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'DEGREE_ID', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'SUBJECT_TEA', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'SEX_CODE', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'NATION_CODE_TEA', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'POLITICS_CODE_TEA', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'TEA_STATUS_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'ZYJSZW_ID', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'ZYJSZW_JB_CODE', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'BZLB_CODE', 1, 11, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_worker', 'AUTHORIZED_STRENGTH_ID', 1, 12, '', '', '1');

-- 插入 生源地 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'DEPT_TEACH_ID', null, 1, '', 'xg:stuFrom', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'NATION_CODE_STU', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'POLITICS_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'STU_STATE_CODE', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'LENGTH_SCHOOLING', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'LEARNING_STYLE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuFrom', 'LEARNING_FORM_CODE', 1, 8, '', '', '1');
-- 插入 新生报到 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'DEPT_TEACH_ID', null, 1, '', 'xg:newStu', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'SEX_CODE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'NATION_CODE_STU', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'POLITICS_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'EDU_ID_STU', '', 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'LENGTH_SCHOOLING', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'LEARNING_STYLE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_newStu', 'LEARNING_FORM_CODE', 1, 8, '', '', '1');
-- 插入 学籍异动 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'DEPT_TEACH_ID', null, 1, '', 'xg:change', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'SCHOOL_YEAR', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'GRADE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'SEX_CODE', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'NATION_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'POLITICS_CODE_STU', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'STU_STATE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'STU_ROLL_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'EDU_ID_STU', '', 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'LENGTH_SCHOOLING', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'LEARNING_STYLE_CODE', 1, 11, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_change', 'LEARNING_FORM_CODE', 1, 12, '', '', '1');
-- 插入 学籍不良异动 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'DEPT_TEACH_ID', null, 1, '', 'xg:changeBad', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'SCHOOL_YEAR', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'GRADE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'SEX_CODE', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'NATION_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'POLITICS_CODE_STU', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'STU_STATE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'STU_ROLL_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'EDU_ID_STU', '', 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'LENGTH_SCHOOLING', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'LEARNING_STYLE_CODE', 1, 11, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_changeBad', 'LEARNING_FORM_CODE', 1, 12, '', '', '1');


-- 插入 违纪处分 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'DEPT_TEACH_ID', null, 1, '', 'xg:punish', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'LENGTH_SCHOOLING', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'LEARNING_STYLE_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_punish', 'LEARNING_FORM_CODE', 1, 9, '', '', '1');
-- 插入 奖学金 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'DEPT_TEACH_ID', null, 1, '', 'xg:scholarship', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'LENGTH_SCHOOLING', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'LEARNING_STYLE_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_scholarship', 'LEARNING_FORM_CODE', 1, 9, '', '', '1');
-- 插入 助学金 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'DEPT_TEACH_ID', null, 1, '', 'xg:subsidy', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'LENGTH_SCHOOLING', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'LEARNING_STYLE_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_subsidy', 'LEARNING_FORM_CODE', 1, 9, '', '', '1');
-- 插入 助学贷款 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'DEPT_TEACH_ID', null, 1, '', 'xg:studentLoans', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'LENGTH_SCHOOLING', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'LEARNING_STYLE_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_studentLoans', 'LEARNING_FORM_CODE', 1, 9, '', '', '1');
-- 插入 学费减免 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'DEPT_TEACH_ID', null, 1, '', 'xg:feeRemission', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'LENGTH_SCHOOLING', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'LEARNING_STYLE_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeRemission', 'LEARNING_FORM_CODE', 1, 9, '', '', '1');
-- 插入  学位预警  高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'DEPT_TEACH_ID', null, 1, '', 'xg:notGradDegree', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'EDU_ID_STU', '', 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'LENGTH_SCHOOLING', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'LEARNING_STYLE_CODE', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_notGradDegree', 'LEARNING_FORM_CODE', 1, 10, '', '', '1');
-- 插入  欠费预警  高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'DEPT_TEACH_ID', null, 1, '', 'xg:feeWarning', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'SCHOOL_YEAR', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'SEX_CODE', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'NATION_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'POLITICS_CODE_STU', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'STU_STATE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'LENGTH_SCHOOLING', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'LEARNING_STYLE_CODE', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_feeWarning', 'LEARNING_FORM_CODE', 1, 10, '', '', '1');
-- 插入  挂科预测  高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'DEPT_TEACH_ID', null, 1, '', 'xg:failExamPredict', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'NATION_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'POLITICS_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'EDU_ID_STU', '', 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'LENGTH_SCHOOLING', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'LEARNING_STYLE_CODE', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_failExamPredict', 'LEARNING_FORM_CODE', 1, 10, '', '', '1');
-- 插入  高消费分析 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'DEPT_TEACH_ID', null, 1, '', 'xg:stuHighCost', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'SEX_CODE', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'NATION_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'POLITICS_CODE_STU', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'STU_STATE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'EDU_ID_STU', '', 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'LENGTH_SCHOOLING', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'LEARNING_STYLE_CODE', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuHighCost', 'LEARNING_FORM_CODE', 1, 11, '', '', '1');
-- 插入  低消费分析 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'DEPT_TEACH_ID', null, 1, '', 'xg:stuLowCost', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'SEX_CODE', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'NATION_CODE_STU', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'POLITICS_CODE_STU', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'STU_STATE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'EDU_ID_STU', '', 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'LENGTH_SCHOOLING', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'LEARNING_STYLE_CODE', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Xg_stuLowCost', 'LEARNING_FORM_CODE', 1, 11, '', '', '1');

-- 插入  师资队伍 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'DEPT_ID', '', '1', '', 'teaching:teacherGroup', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'EDU_ID', '1', '2', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'DEGREE_ID', '1', '3', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'SEX_CODE', '1', '4', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'SUBJECT_TEA', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'NATION_CODE_TEA', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'POLITICS_CODE_TEA', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'ZYJSZW_ID', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'ZYJSZW_JB_CODE', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'BZLB_CODE', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_teacherGroup', 'AUTHORIZED_STRENGTH_ID', 1, 11, '', '', '1');
-- 高级查询-成绩
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'DEPT_TEACH_ID', '', '1', '', 'teaching:score', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'COURSE_ATTR_CODE', '1', '2', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'COURSE_NATURE_CODE', '1', '3', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'SEX_CODE', '1', '4', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'GRADE', '1', '5', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'NATION_CODE_STU', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'POLITICS_CODE_STU', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'STU_STATE_CODE', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'LENGTH_SCHOOLING', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'LEARNING_STYLE_CODE', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_score', 'LEARNING_FORM_CODE', 1, 11, '', '', '1');

-- 高级查询-学霸
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'DEPT_TEACH_ID', '', '1', '', 'teaching:smart', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'SEX_CODE', '1', '2', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'NATION_CODE_STU', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'POLITICS_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'STU_STATE_CODE', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'LENGTH_SCHOOLING', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'LEARNING_STYLE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_smart', 'LEARNING_FORM_CODE', 1, 8, '', '', '1');

-- 高级查询-成绩预测（辅导员）
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredict', 'SEX_CODE', '1', '1', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredict', 'NATION_CODE_STU', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredict', 'POLITICS_CODE_STU', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredict', 'STU_STATE_CODE', 1, 4, '', '', '1');

-- 高级查询-成绩预测（任课教师）
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredictTea', 'SEX_CODE', '1', '1', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredictTea', 'NATION_CODE_STU', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredictTea', 'POLITICS_CODE_STU', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scorePredictTea', 'STU_STATE_CODE', 1, 4, '', '', '1');

-- 高级查询-全周期成绩分析
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysScore', 'DEPT_TEACH_ID', '', '1', '', 'teaching:bysScore', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysScore', 'SEX_CODE', '1', '2', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysScore', 'NATION_CODE_STU', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysScore', 'POLITICS_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysScore', 'LEARNING_STYLE_CODE', 1, 5, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysScore', 'LEARNING_FORM_CODE', 1, 6, '', '', '1');

-- 高级查询-成绩历史
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'DEPT_TEACH_ID', '', '1', '', 'teaching:scoreHistory', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'EDU_ID_STU', '', '2', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'SCORE_TARGET', '', '3', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'COURSE_ATTR_CODE', '1', '4', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'COURSE_NATURE_CODE', '1', '5', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'SEX_CODE', '1', '6', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'SCHOOL_YEAR', '', '7', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'TERM_CODE', '1', '8', '', '', '');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'NATION_CODE_STU', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'POLITICS_CODE_STU', 1, 10, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'LEARNING_STYLE_CODE', 1, 11, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreHistory', 'LEARNING_FORM_CODE', 1, 12, '', '', '1');
-- 高级查询-毕业及学位授予分析
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'DEPT_TEACH_ID', '', '1', '', 'teaching:bysDegree', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'SEX_CODE', '1', '2', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'NATION_CODE_STU', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'POLITICS_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'EDU_ID_STU', '', '5', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'LENGTH_SCHOOLING', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'LEARNING_STYLE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysDegree', 'LEARNING_FORM_CODE', 1, 8, '', '', '1');
-- 高级查询-毕业生去向
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'DEPT_TEACH_ID', '', '1', '', 'teaching:bysQx', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'SEX_CODE', '1', '2', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'NATION_CODE_STU', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'POLITICS_CODE_STU', 1, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'EDU_ID_STU', '', '5', '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'LENGTH_SCHOOLING', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'LEARNING_STYLE_CODE', 1, 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_bysQx', 'LEARNING_FORM_CODE', 1, 8, '', '', '1');

-- 2016-11-30
----新增生源地高级查询参数
insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
values ('STU_ORIGIN_ID', '生源地', 'business', 'tree', '', 'advancedService?getOriginDataService', '');

-- 插入 课程成绩分析 高级查询服务 数据
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'DEPT_TEACH_ID', null, 1, '', 'teaching:scoreByStuLb', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'GRADE', 1, 2, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'SEX_CODE', 1, 3, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'STU_ORIGIN_ID', null, 4, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'STU_STATE_CODE', 1, 6, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'EDU_ID_STU', '', 7, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'LENGTH_SCHOOLING', 1, 8, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'LEARNING_STYLE_CODE', 1, 9, '', '', '1');
insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
values ('Teaching_scoreByStuLb', 'LEARNING_FORM_CODE', 1, 10, '', '', '1');


--学生工作者高级查询参数
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'EDU_ID', 1, 2, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'DEGREE_ID', 1, 3, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'SUBJECT_TEA', 1, 4, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'SEX_CODE', 1, 5, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'NATION_CODE_TEA', 1, 6, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'POLITICS_CODE_TEA', 1, 7, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'TEA_STATUS_CODE', 1, 8, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'ZYJSZW_ID', 1, 9, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'ZYJSZW_JB_CODE', 1, 10, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'BZLB_CODE', 1, 11, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Xg_worker', 'AUTHORIZED_STRENGTH_ID', 1, 12, '', '', '1');
-- 高级查询-挂科补考分析
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'DEPT_TEACH_ID', '', '1', '', 'teaching:failExamination', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'SEX_CODE', '1', '2', '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'NATION_CODE_STU', 1, 3, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'POLITICS_CODE_STU', 1, 4, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'STU_STATE_CODE', 1, 5, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'LENGTH_SCHOOLING', 1, 6, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'LEARNING_STYLE_CODE', 1, 7, '', '', '1');
--insert into t_service_advanced (TAG, CODE_, ISALL, ORDER_, SERVICE, PARAM, ISTRUE)
--values ('Teaching_failExamination', 'LEARNING_FORM_CODE', 1, 8, '', '', '1');