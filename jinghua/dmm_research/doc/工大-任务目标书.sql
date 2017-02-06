/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016-12-7 10:11:46                           */
/*==============================================================*/


drop table T_RWMBS_KHJG cascade constraints;

drop table T_RWMBS_KHJH cascade constraints;

drop table T_RWMBS_KHJH_KHXM cascade constraints;

drop table T_RWMBS_KHJH_XKPMMB cascade constraints;

drop table T_RWMBS_KHXM cascade constraints;

drop table T_RWMBS_KHZT cascade constraints;

drop table T_RWMBS_XKPM cascade constraints;

/*==============================================================*/
/* Table: T_RWMBS_KHJG                                          */
/*==============================================================*/
create table T_RWMBS_KHJG  (
   ID                   VARCHAR2(20)                    not null,
   KHDW_ID              VARCHAR2(20),
   KHXM_ID              VARCHAR2(20),
   KHNF                 VARCHAR2(20),
   KHJG                 VARCHAR2(20),
   constraint PK_T_RWMBS_KHJG primary key (ID)
);

comment on table T_RWMBS_KHJG is
'任务目标书-考核结果';

comment on column T_RWMBS_KHJG.ID is
'ID';

comment on column T_RWMBS_KHJG.KHDW_ID is
'考核单位ID';

comment on column T_RWMBS_KHJG.KHXM_ID is
'考核项目ID';

comment on column T_RWMBS_KHJG.KHNF is
'考核年份';

comment on column T_RWMBS_KHJG.KHJG is
'结束年份';

/*==============================================================*/
/* Table: T_RWMBS_KHJH                                          */
/*==============================================================*/
create table T_RWMBS_KHJH  (
   ID                   VARCHAR2(20)                    not null,
   JHMC                 VARCHAR2(20),
   KSNF                 VARCHAR2(20),
   JSNF                 VARCHAR2(20),
   constraint PK_T_RWMBS_KHJH primary key (ID)
);

comment on table T_RWMBS_KHJH is
'任务目标书-考核计划';

comment on column T_RWMBS_KHJH.ID is
'ID';

comment on column T_RWMBS_KHJH.JHMC is
'计划名称';

comment on column T_RWMBS_KHJH.KSNF is
'开始年份';

comment on column T_RWMBS_KHJH.JSNF is
'结束年份';

/*==============================================================*/
/* Table: T_RWMBS_KHJH_KHXM                                     */
/*==============================================================*/
create table T_RWMBS_KHJH_KHXM  (
   ID                   VARCHAR2(20)                    not null,
   KHJH_ID              VARCHAR2(20),
   KHDW_ID              VARCHAR2(20),
   KHXM_ID              VARCHAR2(20),
   KHMB                 VARCHAR2(20),
   constraint PK_T_RWMBS_KHJH_KHXM primary key (ID)
);

comment on table T_RWMBS_KHJH_KHXM is
'任务目标书-考核计划-考核项目';

comment on column T_RWMBS_KHJH_KHXM.ID is
'ID';

comment on column T_RWMBS_KHJH_KHXM.KHJH_ID is
'计划ID';

comment on column T_RWMBS_KHJH_KHXM.KHDW_ID is
'考核单位ID';

comment on column T_RWMBS_KHJH_KHXM.KHXM_ID is
'考核项ID';

comment on column T_RWMBS_KHJH_KHXM.KHMB is
'考核目标';

/*==============================================================*/
/* Table: T_RWMBS_KHJH_XKPMMB                                   */
/*==============================================================*/
create table T_RWMBS_KHJH_XKPMMB  (
   ID                   VARCHAR2(20)                    not null,
   KHJH_ID              VARCHAR2(20),
   KHDW_ID              VARCHAR2(20),
   XKMC                 VARCHAR2(100),
   KHMB                 VARCHAR2(20),
   constraint PK_T_RWMBS_KHJH_XKPMMB primary key (ID)
);

comment on table T_RWMBS_KHJH_XKPMMB is
'任务目标书-考核计划-学科排名目标';

comment on column T_RWMBS_KHJH_XKPMMB.ID is
'ID';

comment on column T_RWMBS_KHJH_XKPMMB.KHJH_ID is
'计划ID';

comment on column T_RWMBS_KHJH_XKPMMB.KHDW_ID is
'考核单位ID';

comment on column T_RWMBS_KHJH_XKPMMB.XKMC is
'学科名称';

comment on column T_RWMBS_KHJH_XKPMMB.KHMB is
'考核目标';

/*==============================================================*/
/* Table: T_RWMBS_KHXM                                          */
/*==============================================================*/
create table T_RWMBS_KHXM  (
   ID                   VARCHAR2(20)                    not null,
   KHXM                 VARCHAR2(200),
   KHZT_ID              VARCHAR2(20),
   VAL_TYPE             VARCHAR2(20),
   constraint PK_T_RWMBS_KHXM primary key (ID)
);

comment on table T_RWMBS_KHXM is
'任务目标书-考核项';

comment on column T_RWMBS_KHXM.ID is
'ID';

comment on column T_RWMBS_KHXM.KHXM is
'考核项名称';

comment on column T_RWMBS_KHXM.KHZT_ID is
'考核主题ID';

comment on column T_RWMBS_KHXM.VAL_TYPE is
'值类型';

/*==============================================================*/
/* Table: T_RWMBS_KHZT                                          */
/*==============================================================*/
create table T_RWMBS_KHZT  (
   ID                   VARCHAR2(20)                    not null,
   ZTMC                 VARCHAR2(200),
   constraint PK_T_RWMBS_KHZT primary key (ID)
);

comment on table T_RWMBS_KHZT is
'任务目标书-考核主题';

comment on column T_RWMBS_KHZT.ID is
'ID';

comment on column T_RWMBS_KHZT.ZTMC is
'主题名称';

/*==============================================================*/
/* Table: T_RWMBS_XKPM                                          */
/*==============================================================*/
create table T_RWMBS_XKPM  (
   ID                   VARCHAR2(20)                    not null,
   DEPT_ID              VARCHAR2(20),
   KHNF                 VARCHAR2(20),
   KHJG                 VARCHAR2(20),
   XKMC                 VARCHAR2(100),
   constraint PK_T_RWMBS_XKPM primary key (ID)
);

comment on table T_RWMBS_XKPM is
'任务目标书-学科排名';

comment on column T_RWMBS_XKPM.ID is
'ID';

comment on column T_RWMBS_XKPM.DEPT_ID is
'单位';

comment on column T_RWMBS_XKPM.KHNF is
'考核年份';

comment on column T_RWMBS_XKPM.KHJG is
'考核结果';

comment on column T_RWMBS_XKPM.XKMC is
'学科名称';

/*==============================================================*/
/* data : 编码数据（考核主题）	                                */
/*==============================================================*/
insert into t_rwmbs_khzt (ID, ZTMC)values ('0', '学科排名');

insert into t_rwmbs_khzt (ID, ZTMC)values ('1', '教学(招生)工作');

insert into t_rwmbs_khzt (ID, ZTMC)values ('2', '学科建设');

insert into t_rwmbs_khzt (ID, ZTMC)values ('3', '研究生教育');

insert into t_rwmbs_khzt (ID, ZTMC)values ('4', '科学研究');

insert into t_rwmbs_khzt (ID, ZTMC)values ('5', '师资队伍建设与人事管理');

insert into t_rwmbs_khzt (ID, ZTMC)values ('6', '学生工作');

/*==============================================================*/
/* data : 编码数据（考核项目）	                                */
/*==============================================================*/
insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('1', '专业认证数量', '1', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('2', '前20%专业数', '1', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('3', '前10%专业数', '1', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('4', '省级以上教学改革立项数', '1', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('5', '国家级教学奖', '1', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('6', '国家级质量工程数', '1', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('7', '省级一级学科建设数量', '2', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('8', '省级一级学科通过验收数量', '2', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('9', '省级二级学科建设数量', '2', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('10', '省级二级学科通过验收数量', '2', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('11', '招生率', '3', 'persent');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('12', '英语六级通过率', '3', 'persent');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('13', '学位论文专家评审全A率', '3', 'persent');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('14', '论文重复率学生比例', '3', 'persent');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('15', '校级优秀硕士论文获奖率', '3', 'persent');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('16', '一级学科学位点建设基地数', '3', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('17', '专业领域或类别硕士点基地数', '3', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('18', '在职研究生招生数', '3', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('19', '项目经费', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('20', '国家级项目立项数', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('21', '国家级成果奖励数', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('22', '省部级一等奖', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('23', '论文收录数', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('24', '省部级团队数', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('25', '省部级科技平台', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('26', '获得国家级科技创新团队或国家科技平台', '4', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('27', '省级人才申报数', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('28', '省级人才获批数', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('29', '学科带头人数量', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('30', '引进博士人数', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('31', '海外进修培训数', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('32', '报考博士数', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('33', '近三年院士引进与培育数量', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('34', '申报国家级人才数', '5', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('35', '就业率', '6', 'persent');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('36', '考研录取率', '6', 'persent');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('37', '在校生对外交流项目数', '6', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('38', '在校生海外交流人数', '6', 'number');

insert into t_rwmbs_khxm (ID, KHXM, KHZT_ID, VAL_TYPE)values('39', '在校生海外交流人数比例', '6', 'persent');


