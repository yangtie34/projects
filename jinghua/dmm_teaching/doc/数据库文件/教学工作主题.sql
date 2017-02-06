-- 教学工作专题 sql 文件

/*==============================================================*/
/* Table: T_STU_SCORE_HISTORY     学生成绩-历史表                              */
/*==============================================================*/
create table T_STU_SCORE_HISTORY  (
   ID                   VARCHAR2(20),
   STU_ID               VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   COURSE_CODE          VARCHAR2(20),
   CENTESIMAL_SCORE     NUMBER(8,2),
   HIERARCHICAL_SCORE_CODE VARCHAR2(10),
   CS                   NUMBER(1),
   SCHOOL_YEAR_EXAM     VARCHAR2(9),
   TERM_CODE_EXAM       VARCHAR2(10),
   constraint PK_T_STU_SCORE_HISTORY primary key (ID)
);
comment on table T_STU_SCORE_HISTORY is
'学生-综合成绩表-历史表';
/*==============================================================*/
/* Table: T_STU_GPA                                             */
/*==============================================================*/
create table T_STU_GPA  (
   ID                   VARCHAR2(20),
   NAME_                VARCHAR2(60),
   START_               VARCHAR2(20),
   END_                 VARCHAR2(20),
   GPA                  NUMBER(8,2),
   CATEGORY_CODE        VARCHAR2(10),
   ISTRUE               NUMBER(1)
);
comment on table T_STU_GPA is
'学生-GPA规则';
/*==============================================================*/
/* Table: T_STU_SCORE_GPA                                       */
/*==============================================================*/
create table T_STU_SCORE_GPA  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   GPA                  NUMBER(8,2),
   GPA_CODE             VARCHAR2(20),
   constraint PK_T_STU_SCORE_GPA primary key (ID)
);
comment on table T_STU_SCORE_GPA is
'学生-成绩-综合绩点';

-- 插入学生绩点规则
insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', '90', '100', '4', '1', '1');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', '80', '89.9', '3', '1', '1');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', '70', '79.9', '2', '1', '1');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', '60', '69.9', '1', '1', '1');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', '0', '59.9', '0', '1', '1');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', 'A', '', '4', '2', '');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', 'B', '', '3', '2', '');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', 'C', '', '2', '2', '');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', 'D', '', '1', '2', '');

insert into T_STU_GPA (ID, NAME_, START_, END_, GPA, CATEGORY_CODE, ISTRUE)
values ('1', '标准4.0算法', 'E', '', '0', '2', '');

----------------------------创建学生行为临时表 t_stu_behavior----------------
-- Create table
create table T_STU_BEHAVIOR
(
  ID          VARCHAR2(20) not null,
  STU_ID      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  TERM_CODE   VARCHAR2(10),
  VALUE_      NUMBER(8,2),
  TYPE_       VARCHAR2(20)
);
-- Add comments to the columns 
comment on column T_STU_BEHAVIOR.STU_ID
  is '学号';
comment on column T_STU_BEHAVIOR.SCHOOL_YEAR
  is '学年';
comment on column T_STU_BEHAVIOR.TERM_CODE
  is '学期';
comment on column T_STU_BEHAVIOR.VALUE_
  is '行为值';
comment on column T_STU_BEHAVIOR.TYPE_
  is '行为类型';
--------------------------------------------------------
-- Create table---------学生行为-时间维度 ------------  
create table T_STU_BEHAVIOR_TIME
(
  ID          VARCHAR2(20),
  VALUE_      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  TERM_CODE   VARCHAR2(10),
  TYPE_       VARCHAR2(20),
  STU_TYPE    VARCHAR2(20),
  SEASON      VARCHAR2(20)
);
-- Add comments to the columns 
comment on column T_STU_BEHAVIOR_TIME.VALUE_
  is '行为值';
comment on column T_STU_BEHAVIOR_TIME.SCHOOL_YEAR
  is '学年';
comment on column T_STU_BEHAVIOR_TIME.TERM_CODE
  is '学期';
comment on column T_STU_BEHAVIOR_TIME.TYPE_
  is '行为类型';
comment on column T_STU_BEHAVIOR_TIME.STU_TYPE
  is '学生类型';
  comment on column T_STU_BEHAVIOR_TIME.SEASON
  is '季节';
----------------------------------------------------------
----创建表t_stu_behavior_daily 每个学生每天的行为记录表  ----
  -- Create table
create table T_STU_BEHAVIOR_DAILY
(
  ID            VARCHAR2(20),
  STU_ID        VARCHAR2(20),
  DATE_         VARCHAR2(20),
  BREAKFAST_    VARCHAR2(10),
  LUNCH_        VARCHAR2(10),
  DINNER_       VARCHAR2(10),
  FIRST_DORMRKE VARCHAR2(10),
  LAST_DORMRKE  VARCHAR2(10)
);
-- Add comments to the columns 
comment on column T_STU_BEHAVIOR_DAILY.STU_ID
  is '学号';
comment on column T_STU_BEHAVIOR_DAILY.DATE_
  is '行为日期';
comment on column T_STU_BEHAVIOR_DAILY.BREAKFAST_
  is '早餐时间';
comment on column T_STU_BEHAVIOR_DAILY.LUNCH_
  is '午餐时间';
comment on column T_STU_BEHAVIOR_DAILY.DINNER_
  is '晚餐时间';
comment on column T_STU_BEHAVIOR_DAILY.FIRST_DORMRKE
  is '早上出宿舍时间';
comment on column T_STU_BEHAVIOR_DAILY.LAST_DORMRKE
  is '晚上回宿舍时间';
 ----创建表t_failExamination 历年学生挂科记录表  ----
  -- Create table 
CREATE TABLE t_failExamination(
STU_ID VARCHAR2(20),
SCHOOL_YEAR VARCHAR2(9),
TERM_CODE   VARCHAR2(10),
COURSE_CODE VARCHAR2(20)
)

  
/*==============================================================*/
/* Table: T_STU_SCORE_RESULT_DEPT      学生-成绩-结果数据-组织机构表 2016-09-29                         */
/*==============================================================*/
create table T_STU_SCORE_RESULT_DEPT  (
   ID                   VARCHAR2(20)                    not null,
   DEPT_ID              VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(20),
   TERM_CODE            VARCHAR2(20),
   GPA_AVG              NUMBER(8,2),
   GPA_MIDDLE           NUMBER(8,2),
   GPA_MODE             NUMBER(8,2),
   GPA_FC               NUMBER(8,2),
   GPA_BZC              NUMBER(8,2),
   BETTER               NUMBER(8,2),
   FAIL                 NUMBER(8,2),
   REBUILD              NUMBER(8,2),
   UNDER                NUMBER(8,2),
   SCORE_AVG            NUMBER(8,2),
   SCORE_MIDDLE         NUMBER(8,2),
   SCORE_MODE           NUMBER(8,2),
   SCORE_FC             NUMBER(8,2),
   SCORE_BZC            NUMBER(8,2),
   GPA_BEFORE           NUMBER(8,2),
   SMART_COUNT          NUMBER(8),
   SMART_SCALE          NUMBER(8,2),
   constraint PK_T_STU_SCORE_RESULT_DEPT primary key (ID)
);

comment on table T_STU_SCORE_RESULT_DEPT is
'学生-成绩-结果数据-组织机构表';


/*==============================================================*/
/* Table: T_STU_SCORE_RESULT_SUBJECT      学生-成绩-结果数据-学科 2016-09-29                      */
/*==============================================================*/
create table T_STU_SCORE_RESULT_SUBJECT  (
   ID                   VARCHAR2(20)                    not null,
   SUBJECT_DEGREE_ID    VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(20),
   TERM_CODE            VARCHAR2(20),
   GPA_AVG              NUMBER(8,2),
   GPA_MIDDLE           NUMBER(8,2),
   GPA_MODE             NUMBER(8,2),
   GPA_FC               NUMBER(8,2),
   GPA_BZC              NUMBER(8,2),
   BETTER               NUMBER(8,2),
   FAIL                 NUMBER(8,2),
   REBUILD              NUMBER(8,2),
   UNDER                NUMBER(8,2),
   SCORE_AVG            NUMBER(8,2),
   SCORE_MIDDLE         NUMBER(8,2),
   SCORE_MODE           NUMBER(8,2),
   SCORE_FC             NUMBER(8,2),
   SCORE_BZC            NUMBER(8,2),
   GPA_BEFORE           NUMBER(8,2),
   SMART_COUNT          NUMBER(8),
   SMART_SCALE          NUMBER(8,2),
   constraint PK_T_STU_SCORE_RESULT_SUBJECT primary key (ID)
);

comment on table T_STU_SCORE_RESULT_SUBJECT is
'学生-成绩-结果数据-学科';


/*==============================================================*/
/* Table: T_STU_SCORE_RESULT_COURSE  学生-成绩-结果数据-课程 2016-09-29                           */
/*==============================================================*/
create table T_STU_SCORE_RESULT_COURSE  (
   ID                   VARCHAR2(20)                    not null,
   COURSE_ID            VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(20),
   TERM_CODE            VARCHAR2(20),
   GPA_AVG              NUMBER(8,2),
   GPA_MIDDLE           NUMBER(8,2),
   GPA_MODE             NUMBER(8,2),
   GPA_FC               NUMBER(8,2),
   GPA_BZC              NUMBER(8,2),
   BETTER               NUMBER(8,2),
   FAIL                 NUMBER(8,2),
   REBUILD              NUMBER(8,2),
   UNDER                NUMBER(8,2),
   SCORE_AVG            NUMBER(8,2),
   SCORE_MIDDLE         NUMBER(8,2),
   SCORE_MODE           NUMBER(8,2),
   SCORE_FC             NUMBER(8,2),
   SCORE_BZC            NUMBER(8,2),
   GPA_BEFORE           NUMBER(8,2),
   SMART_COUNT          NUMBER(8),
   SMART_SCALE          NUMBER(8,2),
   constraint PK_T_STU_SCORE_RESULT_COURSE primary key (ID)
);

comment on table T_STU_SCORE_RESULT_COURSE is
'学生-成绩-结果数据-课程';

drop table T_STU_SCORE_RESULT_TEACHER cascade constraints;

/*==============================================================*/
/* Table: T_STU_SCORE_RESULT_TEACHER     学生-成绩-结果数据-教师2016-09-29                       */
/*==============================================================*/
create table T_STU_SCORE_RESULT_TEACHER  (
   ID                   VARCHAR2(20)                    not null,
   TEA_ID               VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(20),
   TERM_CODE            VARCHAR2(20),
   GPA_AVG              NUMBER(8,2),
   GPA_MIDDLE           NUMBER(8,2),
   GPA_MODE             NUMBER(8,2),
   GPA_FC               NUMBER(8,2),
   GPA_BZC              NUMBER(8,2),
   BETTER               NUMBER(8,2),
   FAIL                 NUMBER(8,2),
   REBUILD              NUMBER(8,2),
   UNDER                NUMBER(8,2),
   SCORE_AVG            NUMBER(8,2),
   SCORE_MIDDLE         NUMBER(8,2),
   SCORE_MODE           NUMBER(8,2),
   SCORE_FC             NUMBER(8,2),
   SCORE_BZC            NUMBER(8,2),
   GPA_BEFORE           NUMBER(8,2),
   SMART_COUNT          NUMBER(8),
   SMART_SCALE          NUMBER(8,2),
   constraint PK_T_STU_SCORE_RESULT_TEACHER primary key (ID)
);

comment on table T_STU_SCORE_RESULT_TEACHER is
'学生-成绩-结果数据-教师';

/*==============================================================*/
/* Table: T_STU_SCORE_AVG   2016-10-13 每学期学生平均成绩                                    */
/*==============================================================*/
create table T_STU_SCORE_AVG  (
   ID                   varchar(20),
   STU_ID               varchar(20),
   SCHOOL_YEAR          varchar(9),
   TERM_CODE            varchar(10),
   WEIGHT_AVG           number(8,2),
   SCORE_AVG            number(8,2)
);

comment on table T_STU_SCORE_AVG is
'存储每学期每个学生的平均成绩';


-- 2016-11-01 专业开设
/*==============================================================*/
/* Table: T_MAJOR_SCORE                                         */
/*==============================================================*/
create table T_MAJOR_SCORE 
(
   ID                   VARCHAR2(20),
   MAJOR_ID             VARCHAR2(10)                   null,
   SCHOOL_YEAR          VARCHAR2(10)                   null,
   COURSE_ATTR_CODE     VARCHAR2(10)                   null,
   SCORE_AVG            NUMBER(10,2)                   null,
   RANKING              NUMBER(4)                      null
);
comment on table T_MAJOR_SCORE is '专业-成绩';
/*==============================================================*/
/* Table: T_MAJOR_FAIL_CLASS                                    */
/*==============================================================*/
create table T_MAJOR_FAIL_CLASS 
(
   ID                   VARCHAR2(20),
   MAJOR_ID             VARCHAR2(10)                   null,
   SCHOOL_YEAR          VARCHAR2(9)                   null,
   COURSE_ATTR_CODE     VARCHAR2(10)                   null,
   FAIL_CLASS_RATE      NUMBER(10,2)                   null,
   RANKING              NUMBER(4)                      null
);
comment on table T_MAJOR_FAIL_CLASS is '专业-挂科';
/*==============================================================*/
/* Table: T_MAJOR_EVALUATE_TEACHING                             */
/*==============================================================*/
create table T_MAJOR_EVALUATE_TEACHING 
(
   ID                   VARCHAR2(20),
   MAJOR_ID             VARCHAR2(10)                   null,
   SCHOOL_YEAR          VARCHAR2(9)                    null,
   COURSE_ATTR_CODE     VARCHAR2(10)                   null,
   EVALUATE_TEACHING_SCORE_AVG NUMBER(10,2)                   null,
   RANKING              NUMBER(4)                      null
);
comment on table T_MAJOR_EVALUATE_TEACHING is '专业-评教';
/*==============================================================*/
/* Table: T_MAJOR_GRADUATION                                    */
/*==============================================================*/
create table T_MAJOR_GRADUATION 
(
   ID                   VARCHAR2(20),
   MAJOR_ID             VARCHAR2(10)                   null,
   SCHOOL_YEAR          VARCHAR2(9)                    null,
   GRADUATION_RATE      NUMBER(10,2)                   null,
   RANKING              NUMBER(4)                      null
);
comment on table T_MAJOR_GRADUATION is '专业-毕业';
/*==============================================================*/
/* Table: T_MAJOR_EMPLOYMENT                                    */
/*==============================================================*/
create table T_MAJOR_EMPLOYMENT 
(
   ID                   VARCHAR2(20),
   MAJOR_ID             VARCHAR2(10)                   null,
   SCHOOL_YEAR          VARCHAR2(9)                    null,
   EMPLOYMENT_RATE      NUMBER(10,2)                   null,
   RANKING              NUMBER(4)                      null
);
comment on table T_MAJOR_EMPLOYMENT is '专业-就业';

-- 2016-11-08
/*==============================================================*/
/* Table: T_PREDICT_SCORE_RESULT_FDY                                   */
/*==============================================================*/
create table T_PREDICT_SCORE_RESULT_FDY
(
  STU_ID             VARCHAR2(20),
  SCHOOL_YEAR        VARCHAR2(9),
  TERM_CODE          VARCHAR2(10),
  PREDICT_SCORE      NUMBER(8,2),
  GRADE_ID           VARCHAR2(4),
  COURSE_ID          VARCHAR2(20),
  DATE_              VARCHAR2(30),
  TK_COUNT           NUMBER(8),
  ACCURACY           NUMBER(4),
  CLASS_ID           VARCHAR2(20),
  MAJOR_ID           VARCHAR2(20),
  NAME_              VARCHAR2(100),
  COURSEMC           VARCHAR2(100),
  COURSE_NATURE_CODE VARCHAR2(10),
  COURSETYPEMC       VARCHAR2(60)
)
comment on table T_PREDICT_SCORE_RESULT_FDY is '成绩预测（辅导员） 结果表';

/*==============================================================*/
/* Table: T_TEACHING_FUNDS                                   */
/*==============================================================*/
--教学经费表
create table T_TEACHING_FUNDS
(
  ID        VARCHAR2(20) not null,
  FUND_CODE  VARCHAR2(10),
  YEAR_      VARCHAR2(4),
  OUTLAY_VAL NUMBER(20,2),
  DEPT_ID    VARCHAR2(20),
  constraint PK_T_TEACHING_FUNDS primary key (ID)
)
--增加经费编码
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '本科日常运行支出', 'XB', 'FUND_CODE', '教学经费代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '本科专项教学支出', 'XB', 'FUND_CODE', '教学经费代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '本科实验经费支出', 'XB', 'FUND_CODE', '教学经费代码', 1, null);

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '本科实习经费支出', 'XB', 'FUND_CODE', '教学经费代码', 1, null);

/*==============================================================*/
/* Table: T_STU_GRADUATE  毕业生基本信息表                                     */
/*==============================================================*/
create table T_STU_GRADUATE  (
   ID                   VARCHAR2(20),
   STU_ID               VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(9),
   GRADUATED            NUMBER(1),
   DEGREE_GRANT         NUMBER(1),
   LATE_GRADUATED       NUMBER(1),
   GET_DIPLOMA_DATE     VARCHAR2(20),
   GET_DEGREE_DATE      VARCHAR2(20),
   NOT_GRADUATE_CODE    VARCHAR2(20)
);
/*==============================================================*/
/* Table: T_STU_GRADUATE_DIRECTION  毕业生去向信息表                            */
/*==============================================================*/
create table T_STU_GRADUATE_DIRECTION  (
   STU_ID               VARCHAR2(20),
   DIRECTION_ID         VARCHAR2(20),
   UNOCCUPIED_CODE      VARCHAR2(20)
);

comment on table T_STU_GRADUATE_DIRECTION is
'毕业生去向信息表';
/*==============================================================*/
/* Table: T_STU_GRADUATE_RESULT_SUBJECT  毕业生-毕业，学位授予率-结果数据-学科                       */
/*==============================================================*/
create table T_STU_GRADUATE_RESULT_SUBJECT  (
   ID                   VARCHAR2(20),
   SUBJECT_ID           VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(20),
   SUM_COUNT            NUMBER(10),
   REL_COUNT            NUMBER(10),
   GRADUATION_SCALE     NUMBER(4,1),
   LAST_GRADUATION_SCALE NUMBER(4,1),
   DEGREE_COUNT         NUMBER(10),
   DEGREE_SCALE         NUMBER(4,1),
   LAST_DEGREE_SCALE    NUMBER(4,1)
);
/*==============================================================*/
/* Table: T_STU_GRADUATE_RESULT_DEPT   毕业生-毕业-结果数据-组织机构表                         */
/*==============================================================*/
create table T_STU_GRADUATE_RESULT_DEPT  (
   ID                   VARCHAR2(20),
   DEPT_ID              VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(20),
   SUM_COUNT            NUMBER(10),
   REL_COUNT            NUMBER(10),
   GRADUATION_SCALE     NUMBER(4,1),
   LAST_GRADUATION_SCALE NUMBER(4,1),
   DEGREE_COUNT         NUMBER(10),
   DEGREE_SCALE         NUMBER(4,1),
   LAST_DEGREE_SCALE    NUMBER(4,1)
);
/*==============================================================*/
/* Table: T_STU_ENCRUIT          新生报到-生源质量-招生表         2016-11-23      */
/*==============================================================*/
create table T_STU_ENCRUIT 
(
   YEAR                 NUMBER(4)                      null,
   ZBBH                 VARCHAR2(60)                   null,
   EXAMINEE_NO          VARCHAR2(60)                   null,
   NAME_                VARCHAR2(60)                   null,
   SF_ID                VARCHAR2(10)                   null,
   S_ID                 VARCHAR2(10)                   null,
   X_ID                 VARCHAR2(10)                   null,
   IDNO                 VARCHAR2(20)                   null,
   BIRTHDAY             VARCHAR2(10)                   null,
   SEX_CODE             VARCHAR2(10)                   null,
   NATION_CODE          VARCHAR2(10)                   null,
   POLITICS_CODE        VARCHAR2(10)                   null,
   ANMELDEN_CODE        VARCHAR2(10)                   null,
   MIDDLE_SCHOOL_ID     VARCHAR2(20)                   null,
   FOREIGN_LANGUAGE_ID  VARCHAR2(20)                   null,
   EXAMINEE_CATEGORY_ID VARCHAR2(20)                   null,
   ENROLL_BATCH_ID      VARCHAR2(20)                   null,
   ENROLL_CATEGORY_ID   VARCHAR2(20)                   null,
   DEPT_ID              VARCHAR2(20)                   null,
   MAJOR_ID             VARCHAR2(20)                   null,
   SUBJECT_KIND_ID      VARCHAR2(20)                   null,
   CAMPUS_ID            VARCHAR2(20)                   null,
   SCORE                NUMBER(8,2)                    null,
   IS_ADJUST            NUMBER(1)                      null,
   MAJOR_SOURCE_ID      VARCHAR2(20)                   null,
   IS_REPORT            NUMBER(1)                      null,
   NOT_REPORT_REASON_ID VARCHAR2(20)                   null,
   IS_RETREAT_ARCHIVE   NUMBER(1)                      null,
   RETREAT_ARCHIVE_REASON_ID VARCHAR2(20)              null,
   OFFER_ADDRESS        VARCHAR2(200)                  null,
   OFFER_POSTCODE       VARCHAR2(20)                   null,
   OFFER_PHONE          VARCHAR2(60)                   null,
   OFFER_RECIPIENTS     VARCHAR2(60)                   null
);
comment on table T_STU_ENCRUIT is '招生表';

/*==============================================================*/
/* Table: T_CODE_MIDDLE_SCHOOL       新生报到-生源质量-中学代码表         2016-11-23        */
/*==============================================================*/
create table T_CODE_MIDDLE_SCHOOL 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_MIDDLE_SCHOOL primary key  (ID)
);
comment on table T_CODE_MIDDLE_SCHOOL is '代码-中学代码';

/*==============================================================*/
/* Table: T_CODE_SUBJECT_KIND        新生报到-生源质量-科类代码表         2016-11-23     */
/*==============================================================*/
create table T_CODE_SUBJECT_KIND 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_SUBJECT_KIND primary key  (ID)
);

comment on table T_CODE_SUBJECT_KIND is '代码-科类代码';

/*==============================================================*/
/* Table: T_CODE_EXAMINEE_CATEGORY       新生报到-生源质量-考生类别代码表         2016-11-23     */
/*==============================================================*/
create table T_CODE_EXAMINEE_CATEGORY 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_EXAMINEE_CATEGORY primary key  (ID)
);

comment on table T_CODE_EXAMINEE_CATEGORY is '代码-考生类别代码';

/*==============================================================*/
/* Table: T_CODE_ENROLL_BATCH       新生报到-生源质量-录取批次代码表         2016-11-23          */
/*==============================================================*/
create table T_CODE_ENROLL_BATCH 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_ENROLL_BATCH primary key  (ID)
);

comment on table T_CODE_ENROLL_BATCH is '代码-录取批次代码';

/*==============================================================*/
/* Table: T_CODE_ENROLL_CATEGORY         新生报到-生源质量-录取类别代码表         2016-11-23  */
/*==============================================================*/
create table T_CODE_ENROLL_CATEGORY 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_ENROLL_CATEGORY primary key  (ID)
);

comment on table T_CODE_ENROLL_CATEGORY is '代码-录取类别代码';

/*==============================================================*/
/* Table: T_CODE_FOREIGN_LANGUAGE     新生报到-生源质量-外语语种代码表         2016-11-23 */
/*==============================================================*/
create table T_CODE_FOREIGN_LANGUAGE 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_FOREIGN_LANGUAGE primary key (ID)
);

comment on table T_CODE_FOREIGN_LANGUAGE is '代码-外语语种代码';

/*==============================================================*/
/* Table: T_CODE_NOT_REPORT_REASON      新生报到-生源质量-未报到原因代码表         2016-11-23 */
/*==============================================================*/
create table T_CODE_NOT_REPORT_REASON 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_NOT_REPORT_REASON primary key  (ID)
);

comment on table T_CODE_NOT_REPORT_REASON is '代码-未报道原因代码';

/*==============================================================*/
/* Table: T_CODE_RETREAT_ARCHIVE_REASON     新生报到-生源质量-退档原因代码表         2016-11-23*/
/*==============================================================*/
create table T_CODE_RETREAT_ARCHIVE_REASON 
(
   ID                   VARCHAR2(20)                   not null,
   CODE_                VARCHAR2(60)                   not null,
   NAME_                VARCHAR2(100)                  not null,
   PID                  VARCHAR2(20)                   not null,
   PATH_                VARCHAR2(200)                  null,
   LEVEL_               NUMBER(2)                      null,
   LEVEL_TYPE           VARCHAR2(20)                   null,
   ISTRUE               NUMBER(1)                      null,
   ORDER_               NUMBER(4)                      null,
   constraint PK_T_CODE_RETREAT_ARCHIVE_REAS primary key  (ID)
);

comment on table T_CODE_RETREAT_ARCHIVE_REASON is '代码-退档原因代码';

/*==============================================================*/
/* Table: T_STU_ENCRUIT_PLAN         新生报到-生源质量-招生计划表         2016-11-23*/
/*==============================================================*/
create table T_STU_ENCRUIT_PLAN 
(
   YEAR                 NUMBER(4)                      null,
   MAJOR_ID             VARCHAR2(10)                   null,
   PROVINCE_ID          VARCHAR2(10)                   null,
   ENROLL_CATEGORY_ID   VARCHAR2(20)                   null,
   PLAN_COUNT           NUMBER(10)                     null,
   ENROLL_COUNT         NUMBER(10)                     null,
   PLAN_SCORE           NUMBER(10,2)                   null,
   ENROLL_SCORE         NUMBER(10,2)                   null
);

comment on table T_STU_ENCRUIT_PLAN is '招生-计划表';

-- 2016-12-8
-- 删除之前创建错误的表 
drop table T_PREDICT_SCORE_RESULT_FDY;
/*==============================================================*/
/* Table: T_STU_PREDICT_SCORE_RESULT_FDY         成绩预测辅导员表       2016-12-8 */
/*==============================================================*/
create table T_STU_PREDICT_SCORE_RESULT_FDY
(
  STU_ID             VARCHAR2(20),
  SCHOOL_YEAR        VARCHAR2(9),
  TERM_CODE          VARCHAR2(10),
  PREDICT_SCORE      NUMBER(8,2),
  GRADE_ID           VARCHAR2(4),
  COURSE_ID          VARCHAR2(20),
  DATE_              VARCHAR2(30),
  TK_COUNT           NUMBER(8),
  ACCURACY           NUMBER(4),
  CLASS_ID           VARCHAR2(20),
  MAJOR_ID           VARCHAR2(20),
  NAME_              VARCHAR2(100),
  COURSEMC           VARCHAR2(100),
  COURSE_NATURE_CODE VARCHAR2(10),
  COURSETYPEMC       VARCHAR2(60)
)
comment on table T_STU_ENCRUIT_PLAN is '成绩预测-辅导员表';

-- 20161209 
-- Create table  毕业生去向编码表  
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
)
--2017-01-06
--学生成绩历史表（T_STU_SCORE_HISTORY）增加字段
alter table T_STU_SCORE_HISTORY add EXAM_STATUS_CODE varchar(20);
--2017-01-20
--学生成绩表（T_STU_SCORE）学生成绩历史表（T_STU_SCORE_HISTORY）增加字段
alter table T_STU_SCORE add SCORE NUMBER(8,2);
alter table T_STU_SCORE add CREDIT NUMBER(8,2);
alter table T_STU_SCORE_HISTORY add SCORE NUMBER(8,2);
alter table T_STU_SCORE_HISTORY add EXAM_STATUS_CODE NUMBER(8,2);