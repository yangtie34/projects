
-- 2017-01-09
/*==============================================================*/
/* Table: T_STU_SCOREPRED_TERM_MOLD             学生成绩预测（学期）-模型表                */
/*==============================================================*/
drop table T_STU_SCOREPRED_TERM_MOLD cascade constraints;
create table T_STU_SCOREPRED_TERM_MOLD  (
   ID                   VARCHAR2(20)                    not null,
   CODE                 VARCHAR2(20),
   DATE_                VARCHAR2(10),
   ISTRUE               NUMBER(1),
   constraint PK_T_STU_SCOREPRED_TERM_MOLD primary key (ID)
);
comment on table T_STU_SCOREPRED_TERM_MOLD is
'学生成绩预测（学期）-模型表';

/*==============================================================*/
/* Table: T_STU_SCOREPRED_TERM_GROUP                学生成绩预测（学期）-分组表            */
/*==============================================================*/
drop table T_STU_SCOREPRED_TERM_GROUP cascade constraints;
create table T_STU_SCOREPRED_TERM_GROUP  (
   ID                   VARCHAR2(20)                    not null,
   START_SCHOOLYEAR     VARCHAR2(9),
   START_TERMCODE       VARCHAR2(10),
   END_SCHOOLYEAR       VARCHAR2(9),
   END_TERMCODE         VARCHAR2(10),
   GRADE                NUMBER(4),
   DEPT_TYPE            VARCHAR2(20),
   DEPT_VALUE           VARCHAR2(2000),
   ISELECTIVE           NUMBER(1)                       not null,
   TRUTH                NUMBER(1)                       not null,
   constraint PK_T_STU_SCOREPRED_TERM_GROUP primary key (ID)
);
comment on table T_STU_SCOREPRED_TERM_GROUP is
'学生成绩预测（学期）-分组表';

/*==============================================================*/
/* Table: T_STU_SCOREPRED_TERM_GP_MD              学生成绩预测（学期）-分组模型表              */
/*==============================================================*/
drop table T_STU_SCOREPRED_TERM_GP_MD cascade constraints;
create table T_STU_SCOREPRED_TERM_GP_MD  (
   ID                   VARCHAR2(20)                    not null,
   GROUP_ID             VARCHAR2(20),
   COURSE_ID            VARCHAR2(20),
   COURSE_ATTR_CODE     VARCHAR2(10),
   COURSE_NATURE_CODE   VARCHAR2(10),
   COURSE_CATEGORY_CODE VARCHAR2(10),
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   ORDER_               NUMBER(2),
   ISPREDICT            NUMBER(1),
   ISSAVE               NUMBER(1),
   MOLD_ID              VARCHAR2(20),
   constraint PK_T_STU_SCOREPRED_TERM_GP_MD primary key (ID)
);
comment on table T_STU_SCOREPRED_TERM_GP_MD is
'学生成绩预测（学期）-分组模型表';

/*==============================================================*/
/* Table: T_STU_SCOREPRED_TERM_GP_CJ             学生成绩预测（学期）-分组成绩表               */
/*==============================================================*/
drop table T_STU_SCOREPRED_TERM_GP_CJ cascade constraints;
create table T_STU_SCOREPRED_TERM_GP_CJ  (
   ID                   VARCHAR2(20)                    not null,
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   GRADE                NUMBER(4),
   STU_ID               VARCHAR2(20),
   COURSE_ID            VARCHAR2(20),
   PREDICT_SCORE        NUMBER(8,2),
   GROUP_ID             VARCHAR2(20),
   MOLD_ID              VARCHAR2(20),
   DATE_                VARCHAR2(10),
   constraint PK_T_STU_SCOREPRED_TERM_GP_CJ primary key (ID)
);
comment on table T_STU_SCOREPRED_TERM_GP_CJ is
'学生成绩预测（学期）-分组成绩表';

/*==============================================================*/
/* Table: T_STU_SCOREPRED_TERM                学生成绩预测（学期）表                  */
/*==============================================================*/
drop table T_STU_SCOREPRED_TERM cascade constraints;
create table T_STU_SCOREPRED_TERM  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20)                    not null,
   SCHOOL_YEAR          VARCHAR2(9)                     not null,
   TERM_CODE            VARCHAR2(10)                    not null,
   GRADE                NUMBER(4)                       not null,
   COURSE_ID            VARCHAR2(20)                    not null,
   PREDICT_SCORE        NUMBER(8,2)                     not null,
   DATE_                VARCHAR2(10)                    not null,
   ISTRUE               NUMBER(1)                       not null,
   ISEXACT              NUMBER(1),
   constraint PK_T_STU_SCOREPRED_TERM primary key (ID)
);
comment on table T_STU_SCOREPRED_TERM is
'学生成绩预测（学期）表';

/*==============================================================*/
/* Table: T_STU_SCOREPRED_TERM_HIS         学生成绩预测（学期）-历次预测成绩表                     */
/*==============================================================*/
drop table T_STU_SCOREPRED_TERM_HIS cascade constraints;
create table T_STU_SCOREPRED_TERM_HIS  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20)                    not null,
   SCHOOL_YEAR          VARCHAR2(9)                     not null,
   TERM_CODE            VARCHAR2(10)                    not null,
   GRADE                NUMBER(4)                       not null,
   COURSE_ID            VARCHAR2(20)                    not null,
   PREDICT_SCORE        NUMBER(8,2)                     not null,
   DATE_                VARCHAR2(10)                    not null,
   ISTRUE               NUMBER(1)                       not null,
   ISEXACT              NUMBER(1),
   constraint PK_T_STU_SCOREPRED_TERM_HIS primary key (ID)
);
comment on table T_STU_SCOREPRED_TERM_HIS is
'学生成绩预测（学期）-历次预测成绩表';