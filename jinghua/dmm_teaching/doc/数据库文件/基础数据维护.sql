
-- 课程库表  新增：2字段
alter table t_course add (COURSE_TYPE_CODE VARCHAR2(10));
-- 开课计划表 删除：2字段；新增：3、4、1字段
alter table T_COURSE_ARRANGEMENT_PLAN drop (COURSE_TYPE_CODE);
alter table T_COURSE_ARRANGEMENT_PLAN add (COURSE_ATTR_CODE VARCHAR2(10));
alter table T_COURSE_ARRANGEMENT_PLAN add (COURSE_NATURE_CODE VARCHAR2(10));
alter table T_COURSE_ARRANGEMENT_PLAN add (COURSE_CATEGORY_CODE VARCHAR2(10));
-- 课程安排结果表 删除：2字段；新增：3、4、1字段
alter table T_COURSE_ARRANGEMENT drop (COURSE_TYPE_CODE);
alter table T_COURSE_ARRANGEMENT add (COURSE_ATTR_CODE VARCHAR2(10));
alter table T_COURSE_ARRANGEMENT add (COURSE_NATURE_CODE VARCHAR2(10));
alter table T_COURSE_ARRANGEMENT add (COURSE_CATEGORY_CODE VARCHAR2(10));
-- 选课信息表 删除：2字段；新增：3、4、1字段
alter table T_STU_COURSE_CHOOSE drop (COURSE_TYPE_CODE);
alter table T_STU_COURSE_CHOOSE add (COURSE_ATTR_CODE VARCHAR2(10));
alter table T_STU_COURSE_CHOOSE add (COURSE_NATURE_CODE VARCHAR2(10));
alter table T_STU_COURSE_CHOOSE add (COURSE_CATEGORY_CODE VARCHAR2(10));

-- 添加课程字段 1、2、3、4编码
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '必修', 'HB', 'COURSE_ATTR_CODE', '课程属性编码', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '限选', 'HB', 'COURSE_ATTR_CODE', '课程属性编码', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '任选', 'HB', 'COURSE_ATTR_CODE', '课程属性编码', '1', '3');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '理论类', 'HB', 'COURSE_CATEGORY_CODE', '课程类别代码', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '语言类', 'HB', 'COURSE_CATEGORY_CODE', '课程类别代码', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '实验（实训）类', 'HB', 'COURSE_CATEGORY_CODE', '课程类别代码', '1', '3');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('4', '体育类', 'HB', 'COURSE_CATEGORY_CODE', '课程类别代码', '1', '4');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('5', '实践类', 'HB', 'COURSE_CATEGORY_CODE', '课程类别代码', '1', '5');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('6', '艺术类', 'HB', 'COURSE_CATEGORY_CODE', '课程类别代码', '1', '6');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('9', '其他类', 'HB', 'COURSE_CATEGORY_CODE', '课程类别代码', '1', '7');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '公共基础课', 'HB', 'COURSE_NATURE_CODE', '课程性质代码', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '学科基础课', 'HB', 'COURSE_NATURE_CODE', '课程性质代码', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '专业课', 'HB', 'COURSE_NATURE_CODE', '课程性质代码', '1', '3');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('11', '本校博士生课', 'HB', 'COURSE_TYPE_CODE', '课程类型代码', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('12', '本校硕士生课', 'HB', 'COURSE_TYPE_CODE', '课程类型代码', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('13', '本校本科生课', 'HB', 'COURSE_TYPE_CODE', '课程类型代码', '1', '3');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('14', '本校专科生课', 'HB', 'COURSE_TYPE_CODE', '课程类型代码', '1', '4');

-- 20160919
-- 重新设计次时间表
drop table T_COURSE_PERIOD_TIME;
-- Create table
create table T_COURSE_PERIOD_TIME
(
  ID         NUMBER(2),
  START_TIME VARCHAR2(5),
  END_TIME   VARCHAR2(5),
  START_DATE VARCHAR2(5),
  END_DATE   VARCHAR2(5)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16
    minextents 1
    maxextents unlimited
  );
-- 添加节次时间数据
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('1', '08:00', '08:50', '10-01', '09-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('2', '09:00', '09:50', '10-01', '09-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('3', '10:10', '11:00', '10-01', '09-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('4', '11:10', '12:00', '10-01', '09-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('5', '14:30', '15:20', '10-01', '04-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('5', '15:00', '15:50', '05-01', '09-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('6', '15:30', '16:20', '10-01', '04-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('6', '16:00', '16:50', '05-01', '09-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('7', '16:30', '17:20', '10-01', '04-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('7', '17:00', '17:50', '05-01', '09-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('8', '17:30', '18:20', '10-01', '04-30');
insert into t_course_period_time (ID, START_TIME, END_TIME, START_DATE, END_DATE)
values ('8', '18:00', '18:50', '05-01', '09-30');

----修改开课计划表（行政班粒度）的学分的数据类型 2016-10-20
alter table t_course_arrangement_plan modify CREDIT NUMBER(4,1);

/*==============================================================*/
/* Table: T_CODE_GRADUATE_DIRECTION   毕业生去向标准编码表                         */
/*==============================================================*/
-- Create table
create table T_CODE_GRADUATE_DIRECTION
(
  ID         VARCHAR2(20) not null,
  CODE_      VARCHAR2(60) not null,
  NAME_      VARCHAR2(100) not null,
  PID        VARCHAR2(20) not null,
  PATH_      VARCHAR2(200),
  LEVEL_     NUMBER(2),
  LEVEL_TYPE VARCHAR2(20),
  ISTRUE     NUMBER(1),
  ORDER_     NUMBER(4)
);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('01', '01', '签约', '-1', '0001', 1, '', 1, 1);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('02', '02', '升造', '-1', '0002', 1, '', 1, 2);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('03', '03', '就业未签约', '-1', '0003', 1, '', 1, 3);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('04', '04', '未就业未签约', '-1', '0004', 1, '', 1, 4);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0201', '0201', '考研', '02', '00020001', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0202', '0202', '保研', '02', '00020002', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0203', '0203', '出国', '02', '00020003', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0101', '0101', '派遣', '01', '00010001', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0102', '0102', '用人单位接收', '01', '00010002', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0401', '0401', '暂缓就业', '04', '00040001', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0301', '0301', '灵活就业', '03', '00030001', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0204', '0204', '定向委培', '02', '00020004', 2, '', 1, null);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('99', '99', '其它', '-1', '0099', 1, '', 1, 5);

insert into t_code_graduate_direction (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('0103', '0103', '国家地方项目', '01', '00010003', 2, '', 1, null);

-----未就业原因 code----------------------------------
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '自身定位过高', 'XB', 'UNOCCUPIED_CODE', '未就业原因', 1, 1);
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '身体健康问题', 'XB', 'UNOCCUPIED_CODE', '未就业原因', 1, 2);
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '心理健康问题', 'XB', 'UNOCCUPIED_CODE', '未就业原因', 1, 3);
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('99', '其它原因', 'XB', 'UNOCCUPIED_CODE', '未就业原因', 1, 99);

-- 20170104
-- 添加标准代码：考试状态
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '缺考', 'XB', 'EXAM_STATUS_CODE', '考试状态', '1', '1');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '缓考', 'XB', 'EXAM_STATUS_CODE', '考试状态', '1', '2');
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '违纪', 'XB', 'EXAM_STATUS_CODE', '考试状态', '1', '3');

-- 3.添加高级查询源    20160706
-- insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('DEPT_TEACH_TEACH_ID', '教学机构', 'common', 'tree', '', 'advancedService?getDeptTeachTeachDataService', '');
-- insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('SCHOOL_YEAR', '学年', 'common', '', '', 'businessService?queryBzdmSchoolYear', '');
-- insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('TERM_CODE', '学期', 'common', '', '', 'businessService?queryBzdmTermCode', '');
-- insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('EDU_ID', '培养层次', 'stu', '', '', 'businessService?queryBzdmStuEducationList', '');
-- insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('GRADE', '年级', 'stu', '', '', 'businessService?queryBzdmNj', '');
-- 4.更新 学生培养层次code_，EDU_ID -> EDU_ID_STU；新增学历编码  20160711
-- update t_service_advanced_source set code_='EDU_ID_STU' where code_='EDU_ID' and group_='stu';
-- update t_service_advanced set code_='EDU_ID_STU' where code_='EDU_ID';
-- insert into t_service_advanced_source (CODE_, NAME_, GROUP_, TYPE_, TABLE_, SERVICE, PARAM)
-- values ('EDU_ID', '学历', 'tea', '', '', 'businessService?queryBzdmTeaEducationList', '');
