-----------------------------------------------
-- Export file for user TEST                 --
-- Created by zhaoyx on 2016/10/12, 16:16:13 --
-----------------------------------------------

create table T_CLASSES
(
  ID               VARCHAR2(20),
  NO_              VARCHAR2(20) not null,
  NAME_            VARCHAR2(60),
  TEACH_DEPT_ID    VARCHAR2(20),
  LENGTH_SCHOOLING NUMBER(1),
  ISLIVE           NUMBER(1),
  GRADE            NUMBER(4)
)
;

create table T_CODE
(
  CODE_         VARCHAR2(10) not null,
  NAME_         VARCHAR2(60),
  CODE_CATEGORY VARCHAR2(3),
  CODE_TYPE     VARCHAR2(32) not null,
  CODETYPE_NAME VARCHAR2(100),
  ISTRUE        NUMBER(1),
  ORDER_        NUMBER(4)
)
;
comment on table T_CODE
  is '通用标准代码表：里面放置一些非树形结构的编码数据，例如性别代码表、政治面貌代码表等';
comment on column T_CODE.CODE_
  is '代码';
comment on column T_CODE.NAME_
  is '名称';
comment on column T_CODE.CODE_CATEGORY
  is 'GB,HB,XB三类';
comment on column T_CODE.CODE_TYPE
  is '代码类型';
comment on column T_CODE.CODETYPE_NAME
  is '代码类型名称';
comment on column T_CODE.ISTRUE
  is '是否可用';
comment on column T_CODE.ORDER_
  is '排序';
alter table T_CODE
  add primary key (CODE_, CODE_TYPE);


create table T_CODE_DEPT
(
  ID                 VARCHAR2(20) not null,
  CODE_              VARCHAR2(60) not null,
  NAME_              VARCHAR2(100) not null,
  PID                VARCHAR2(20) not null,
  PATH_              VARCHAR2(200) not null,
  LEVEL_             NUMBER(2),
  LEVEL_TYPE         VARCHAR2(20),
  ISTRUE             NUMBER(1),
  ORDER_             NUMBER(4),
  DEPT_CATEGORY_CODE VARCHAR2(10)
)
;
comment on table T_CODE_DEPT
  is '行政组织结构';
comment on column T_CODE_DEPT.CODE_
  is '代码';
comment on column T_CODE_DEPT.NAME_
  is '名称';
comment on column T_CODE_DEPT.PID
  is '父节点';
comment on column T_CODE_DEPT.PATH_
  is '全析码';
comment on column T_CODE_DEPT.LEVEL_
  is '层次';
comment on column T_CODE_DEPT.LEVEL_TYPE
  is '层次类型';
comment on column T_CODE_DEPT.ISTRUE
  is '是否可用';
comment on column T_CODE_DEPT.ORDER_
  is '序号';
alter table T_CODE_DEPT
  add primary key (ID);


create table T_CODE_DEPT_TEACH
(
  ID         VARCHAR2(20) not null,
  CODE_      VARCHAR2(60) not null,
  NAME_      VARCHAR2(100) not null,
  PID        VARCHAR2(20) not null,
  PATH_      VARCHAR2(200),
  LEVEL_     NUMBER(2),
  LEVEL_TYPE VARCHAR2(20),
  ISTRUE     NUMBER(1),
  ORDER_     NUMBER(4),
  SUBJECT_ID VARCHAR2(20)
)
;
comment on table T_CODE_DEPT_TEACH
  is '教学组织机构';
comment on column T_CODE_DEPT_TEACH.CODE_
  is '代码';
comment on column T_CODE_DEPT_TEACH.NAME_
  is '名称';
comment on column T_CODE_DEPT_TEACH.PID
  is '父节点';
comment on column T_CODE_DEPT_TEACH.PATH_
  is '全析码';
comment on column T_CODE_DEPT_TEACH.LEVEL_
  is '层次';
comment on column T_CODE_DEPT_TEACH.LEVEL_TYPE
  is '层次类型';
comment on column T_CODE_DEPT_TEACH.ISTRUE
  is '是否可用';
comment on column T_CODE_DEPT_TEACH.ORDER_
  is '序号';
comment on column T_CODE_DEPT_TEACH.SUBJECT_ID
  is '专业学科门类ID';
alter table T_CODE_DEPT_TEACH
  add primary key (ID);


create table T_SYS_DATA_SERVICE
(
  ID          NUMBER(16) not null,
  NAME_       VARCHAR2(30) not null,
  SERVICENAME VARCHAR2(100) not null
)
;
comment on table T_SYS_DATA_SERVICE
  is '数据范围服务表';
comment on column T_SYS_DATA_SERVICE.NAME_
  is '名称';
comment on column T_SYS_DATA_SERVICE.SERVICENAME
  is '服务配置';
alter table T_SYS_DATA_SERVICE
  add primary key (ID);


create table T_SYS_OPERATE
(
  ID          NUMBER(16) not null,
  NAME_       VARCHAR2(20) not null,
  DESCRIPTION VARCHAR2(40) not null
)
;
alter table T_SYS_OPERATE
  add primary key (ID);


create table T_SYS_RESOURCES
(
  ID                 NUMBER(16) not null,
  NAME_              VARCHAR2(30) not null,
  URL_               VARCHAR2(60),
  DESCRIPTION        VARCHAR2(100),
  PID                NUMBER(16) not null,
  LEVEL_             NUMBER(2),
  PATH_              VARCHAR2(500) not null,
  ISTRUE             NUMBER(1) not null,
  ORDER_             NUMBER(4) not null,
  KEYWORD            VARCHAR2(100),
  RESOURCE_TYPE_CODE VARCHAR2(10),
  SHIRO_TAG          VARCHAR2(100) not null,
  SYSGROUP_          VARCHAR2(10),
  ISSHOW             NUMBER(1)
)
;
comment on table T_SYS_RESOURCES
  is '系统菜单';
comment on column T_SYS_RESOURCES.NAME_
  is '名称';
comment on column T_SYS_RESOURCES.URL_
  is '路径';
comment on column T_SYS_RESOURCES.DESCRIPTION
  is 'm描述';
comment on column T_SYS_RESOURCES.PID
  is '父节点';
comment on column T_SYS_RESOURCES.LEVEL_
  is '层次';
comment on column T_SYS_RESOURCES.PATH_
  is '全息码';
comment on column T_SYS_RESOURCES.ISTRUE
  is '是否可用';
comment on column T_SYS_RESOURCES.ORDER_
  is '排序';
comment on column T_SYS_RESOURCES.KEYWORD
  is '关键字';
comment on column T_SYS_RESOURCES.RESOURCE_TYPE_CODE
  is '资源类型';
comment on column T_SYS_RESOURCES.SHIRO_TAG
  is 'shiro资源标示';


create table T_SYS_ROLE
(
  ID             NUMBER(16) not null,
  NAME_          VARCHAR2(20),
  DESCRIPTION    VARCHAR2(40),
  ISTRUE         NUMBER(1),
  ROLE_TYPE_CODE VARCHAR2(10),
  ISMAIN         NUMBER(1),
  RESOURCEID     NUMBER(16)
)
;
comment on table T_SYS_ROLE
  is '系统角色';
comment on column T_SYS_ROLE.NAME_
  is '名称';
comment on column T_SYS_ROLE.DESCRIPTION
  is '描述';
comment on column T_SYS_ROLE.ISTRUE
  is '是否可用';
comment on column T_SYS_ROLE.ROLE_TYPE_CODE
  is '角色类型';
comment on column T_SYS_ROLE.ISMAIN
  is '是否基本角色';
comment on column T_SYS_ROLE.RESOURCEID
  is '资源ID';


create table T_SYS_ROLE_DEPT
(
  ID           NUMBER(16) not null,
  ROLE_PERM_ID NUMBER(16) not null,
  DEPT_ID      VARCHAR2(4000) not null,
  LEVEL_       VARCHAR2(2)
)
;
comment on table T_SYS_ROLE_DEPT
  is '角色-数据权限-行政';
comment on column T_SYS_ROLE_DEPT.ROLE_PERM_ID
  is '角色-权限ID';
comment on column T_SYS_ROLE_DEPT.DEPT_ID
  is '组织机构ID';
comment on column T_SYS_ROLE_DEPT.LEVEL_
  is '数据层次';


create table T_SYS_ROLE_DEPTTEACH
(
  ID            NUMBER(16) not null,
  ROLE_PERM_ID  NUMBER(16) not null,
  DEPT_TEACH_ID VARCHAR2(4000) not null,
  LEVEL_        VARCHAR2(2)
)
;
comment on table T_SYS_ROLE_DEPTTEACH
  is '角色-数据权限-教学';
comment on column T_SYS_ROLE_DEPTTEACH.ROLE_PERM_ID
  is '角色-权限ID';
comment on column T_SYS_ROLE_DEPTTEACH.DEPT_TEACH_ID
  is '教学组织机构ID';
comment on column T_SYS_ROLE_DEPTTEACH.LEVEL_
  is '数据层次';


create table T_SYS_ROLE_PERM
(
  ID              NUMBER(16) not null,
  ROLE_ID         NUMBER(16) not null,
  RESOURCE_ID     NUMBER(16),
  OPERATE_ID      NUMBER(16),
  DATA_SERVICE_ID NUMBER(16),
  WIRLDCARD       VARCHAR2(200) not null
)
;
comment on table T_SYS_ROLE_PERM
  is '系统角色-权限';
comment on column T_SYS_ROLE_PERM.ROLE_ID
  is '角色ID';
comment on column T_SYS_ROLE_PERM.RESOURCE_ID
  is '系统菜单ID';
comment on column T_SYS_ROLE_PERM.OPERATE_ID
  is '资源操作ID';
comment on column T_SYS_ROLE_PERM.DATA_SERVICE_ID
  is '数据权限ID';
comment on column T_SYS_ROLE_PERM.WIRLDCARD
  is 'shiro权限通配';


create table T_SYS_SCHEDULE_GROUP
(
  ID   VARCHAR2(20) not null,
  URL_ VARCHAR2(40)
)
;
comment on table T_SYS_SCHEDULE_GROUP
  is '系统-业务分组';
comment on column T_SYS_SCHEDULE_GROUP.URL_
  is 'url';


create table T_SYS_SCHEDULE_PLAN
(
  ID              VARCHAR2(20) not null,
  NAME_           VARCHAR2(20),
  GROUP_          VARCHAR2(20),
  CRON_EXPRESSION VARCHAR2(20),
  DESC_           VARCHAR2(200),
  ISTRUE          NUMBER(1)
)
;
comment on table T_SYS_SCHEDULE_PLAN
  is '计划';
comment on column T_SYS_SCHEDULE_PLAN.NAME_
  is '计划名称';
comment on column T_SYS_SCHEDULE_PLAN.GROUP_
  is '分组业务系统';
comment on column T_SYS_SCHEDULE_PLAN.CRON_EXPRESSION
  is '表达式';
comment on column T_SYS_SCHEDULE_PLAN.DESC_
  is '描述';
comment on column T_SYS_SCHEDULE_PLAN.ISTRUE
  is '是否启用';


create table T_SYS_SCHEDULE_PLAN_LOG
(
  ID        VARCHAR2(20) not null,
  PLANID    VARCHAR2(20),
  ISYES     NUMBER(1),
  STARTTIME VARCHAR2(20) not null,
  ENDTIME   VARCHAR2(200)
)
;
comment on table T_SYS_SCHEDULE_PLAN_LOG
  is '计划-日志';
comment on column T_SYS_SCHEDULE_PLAN_LOG.PLANID
  is '计划ID';
comment on column T_SYS_SCHEDULE_PLAN_LOG.ISYES
  is '执行结果';
comment on column T_SYS_SCHEDULE_PLAN_LOG.STARTTIME
  is '开始时间';
comment on column T_SYS_SCHEDULE_PLAN_LOG.ENDTIME
  is '结束时间';


create table T_SYS_SCHEDULE_PLAN_LOG_DETAIL
(
  ID          VARCHAR2(20) not null,
  PLANLOGID   VARCHAR2(20),
  LOGTYPEID   VARCHAR2(20),
  ISYES       NUMBER(1),
  RESULT_DESC VARCHAR2(1200),
  LOGTYPE     VARCHAR2(20),
  STARTTIME   VARCHAR2(20),
  ENDTIME     VARCHAR2(20),
  CHECK_      VARCHAR2(200)
)
;
comment on table T_SYS_SCHEDULE_PLAN_LOG_DETAIL
  is '计划详情日志';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.PLANLOGID
  is '计划日志ID';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.LOGTYPEID
  is '日志类型的执行ID';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.ISYES
  is '执行结果';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.RESULT_DESC
  is '执行描述';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.LOGTYPE
  is '日志类型';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.STARTTIME
  is '开始时间';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.ENDTIME
  is '结束时间';
comment on column T_SYS_SCHEDULE_PLAN_LOG_DETAIL.CHECK_
  is '检查';


create table T_SYS_SCHEDULE_PLAN_WORK
(
  ID     VARCHAR2(20) not null,
  PLANID VARCHAR2(20),
  WORKID VARCHAR2(20),
  ORDER_ VARCHAR2(20)
)
;
comment on table T_SYS_SCHEDULE_PLAN_WORK
  is '计划-业务';
comment on column T_SYS_SCHEDULE_PLAN_WORK.PLANID
  is '计划ID';
comment on column T_SYS_SCHEDULE_PLAN_WORK.WORKID
  is '业务ID';
comment on column T_SYS_SCHEDULE_PLAN_WORK.ORDER_
  is '排序';


create table T_SYS_SCHEDULE_VERIFY
(
  ID      VARCHAR2(20) not null,
  NAME_   VARCHAR2(20),
  GROUP_  VARCHAR2(20),
  SERVICE VARCHAR2(40),
  DESC_   VARCHAR2(40),
  ISTRUE  NUMBER(1),
  ISMUST  NUMBER(1)
)
;
comment on table T_SYS_SCHEDULE_VERIFY
  is '计划-验证';
comment on column T_SYS_SCHEDULE_VERIFY.NAME_
  is '名称';
comment on column T_SYS_SCHEDULE_VERIFY.GROUP_
  is '分组业务系统';
comment on column T_SYS_SCHEDULE_VERIFY.SERVICE
  is '业务系统url地址';
comment on column T_SYS_SCHEDULE_VERIFY.DESC_
  is '描述';
comment on column T_SYS_SCHEDULE_VERIFY.ISTRUE
  is '是否启用';
comment on column T_SYS_SCHEDULE_VERIFY.ISMUST
  is '是否必须';


create table T_SYS_SCHEDULE_WORK
(
  ID      VARCHAR2(20) not null,
  NAME_   VARCHAR2(20),
  GROUP_  VARCHAR2(20),
  SERVICE VARCHAR2(40),
  DESC_   VARCHAR2(40),
  ISTRUE  NUMBER(1)
)
;
comment on table T_SYS_SCHEDULE_WORK
  is '业务';
comment on column T_SYS_SCHEDULE_WORK.NAME_
  is '名称';
comment on column T_SYS_SCHEDULE_WORK.GROUP_
  is '分组业务系统';
comment on column T_SYS_SCHEDULE_WORK.SERVICE
  is '业务系统url地址';
comment on column T_SYS_SCHEDULE_WORK.DESC_
  is '描述';
comment on column T_SYS_SCHEDULE_WORK.ISTRUE
  is '是否启用';


create table T_SYS_SCHEDULE_WORK_VERIFY
(
  ID       VARCHAR2(20) not null,
  WORKID   VARCHAR2(20),
  VERIFYID VARCHAR2(20),
  RULE     NUMBER(1),
  ORDER_   VARCHAR2(20)
)
;
comment on table T_SYS_SCHEDULE_WORK_VERIFY
  is '业务-验证';
comment on column T_SYS_SCHEDULE_WORK_VERIFY.WORKID
  is '业务ID';
comment on column T_SYS_SCHEDULE_WORK_VERIFY.VERIFYID
  is '验证ID';
comment on column T_SYS_SCHEDULE_WORK_VERIFY.RULE
  is '验证规则必须非必须';
comment on column T_SYS_SCHEDULE_WORK_VERIFY.ORDER_
  is '执行顺序 排序';


create table T_SYS_USER
(
  ID                 NUMBER(16) not null,
  USERNAME           VARCHAR2(20),
  PASSWORD           VARCHAR2(60),
  REAL_NAME          VARCHAR2(60),
  SALT               VARCHAR2(60),
  ISTRUE             NUMBER(1),
  CREATE_TIME        VARCHAR2(20),
  UPDATE_TIME        VARCHAR2(20),
  DEPT_ID            VARCHAR2(20),
  DEPT_CATEGORY_CODE VARCHAR2(2),
  ID_NO              VARCHAR2(100)
)
;
comment on table T_SYS_USER
  is '在登录名与是否锁定上创建索引以提高用户检索效率  系统用户';
comment on column T_SYS_USER.USERNAME
  is '登陆名';
comment on column T_SYS_USER.PASSWORD
  is '密码';
comment on column T_SYS_USER.REAL_NAME
  is '真实姓名';
comment on column T_SYS_USER.SALT
  is '安全代码';
comment on column T_SYS_USER.ISTRUE
  is '是否有效';
comment on column T_SYS_USER.CREATE_TIME
  is '创建时间';
comment on column T_SYS_USER.UPDATE_TIME
  is '更新时间';
comment on column T_SYS_USER.DEPT_ID
  is '所属组织机构机构';
comment on column T_SYS_USER.ID_NO
  is '身份证号';


create table T_SYS_USER_DEPT
(
  ID           NUMBER(16) not null,
  USER_PERM_ID NUMBER(16) not null,
  DEPT_ID      VARCHAR2(4000) not null,
  LEVEL_       VARCHAR2(2)
)
;
comment on table T_SYS_USER_DEPT
  is '用户-组织机构';


create table T_SYS_USER_DEPTTEACH
(
  ID            NUMBER(16) not null,
  USER_PERM_ID  NUMBER(16) not null,
  DEPT_TEACH_ID VARCHAR2(4000) not null,
  LEVEL_        VARCHAR2(2)
)
;
comment on table T_SYS_USER_DEPTTEACH
  is '用户-数据权限-教学';
comment on column T_SYS_USER_DEPTTEACH.USER_PERM_ID
  is '用户权限表ID';
comment on column T_SYS_USER_DEPTTEACH.DEPT_TEACH_ID
  is '教学组织机构ID';
comment on column T_SYS_USER_DEPTTEACH.LEVEL_
  is '数据层次';


create table T_SYS_USER_LOGGING
(
  ID         NUMBER(16) not null,
  USERNAME   VARCHAR2(20),
  LOGIN_DATE VARCHAR2(20),
  LOGIN_WAY  VARCHAR2(20)
)
;


create table T_SYS_USER_PERM
(
  ID              NUMBER(16) not null,
  USER_ID         NUMBER(16) not null,
  RESOURCE_ID     NUMBER(16),
  OPERATE_ID      NUMBER(16),
  DATA_SERVICE_ID NUMBER(16) not null,
  WIRLDCARD       VARCHAR2(200) not null
)
;
comment on table T_SYS_USER_PERM
  is '用户-特定权限';
comment on column T_SYS_USER_PERM.USER_ID
  is '用户ID';
comment on column T_SYS_USER_PERM.RESOURCE_ID
  is '资源ID';
comment on column T_SYS_USER_PERM.OPERATE_ID
  is '操作类型ID';
comment on column T_SYS_USER_PERM.DATA_SERVICE_ID
  is '数据权限';
comment on column T_SYS_USER_PERM.WIRLDCARD
  is 'shiro权限通配';


create table T_SYS_USER_ROLE
(
  ID      NUMBER(16) not null,
  USER_ID NUMBER(16),
  ROLE_ID NUMBER(16)
)
;
comment on table T_SYS_USER_ROLE
  is '用户-角色表';
comment on column T_SYS_USER_ROLE.USER_ID
  is '用户id';
comment on column T_SYS_USER_ROLE.ROLE_ID
  is '角色ID';

---seq创建
create sequence ID_SEQ
minvalue 1
maxvalue 9999999999
start with 100
increment by 1
nocache;



--插入数据
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '学生', 'SYS', 'ROLE_TYPE_CODE', '角色类型代码', 1, 1);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '教职工', 'SYS', 'ROLE_TYPE_CODE', '角色类型代码', 1, 2);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '其他', 'SYS', 'ROLE_TYPE_CODE', '角色类型代码', 1, 3);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '功能模块', 'SYS', 'RESOURCE_TYPE', '资源类型', 1, 1);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '菜单页面', 'SYS', 'RESOURCE_TYPE', '资源类型', 1, 2);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '功能按钮', 'SYS', 'RESOURCE_TYPE', '资源类型', 1, 3);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '自定义组件', 'SYS', 'RESOURCE_TYPE', '资源类型', 1, 4);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('05', '字段属性', 'SYS', 'RESOURCE_TYPE', '资源类型', 1, 5);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '管理员', 'SYS', 'ROLE_TYPE_CODE', '角色类型代码', 1, 4);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('06', '业务系统', 'SYS', 'RESOURCE_TYPE', '资源类型', 1, 6);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '中国共产党党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '中国共产党预备党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '中国共青团团员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '中国民革会员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('05', '中国民主同盟盟员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('06', '中国民主建国会会员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('07', '中国民主促进会会员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('08', '中国农工民主党党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('09', '中国致公党党员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('10', '九三学社社员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('11', '台湾民主自治同盟盟员', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('12', '无党派民主人士', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('13', '群众', 'GB', 'POLITICS_CODE', '政治面貌代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '汉族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '蒙古族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '回族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '藏族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('05', '维吾尔族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('06', '苗族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('07', '彝族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('08', '壮族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('09', '布依族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('10', '朝鲜族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('11', '满族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('12', '侗族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('13', '瑶族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('14', '白族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('15', '土家族　', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('16', '哈尼族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('17', '哈萨克族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('18', '傣族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('19', '黎族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('20', '傈僳族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('21', '佤族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('22', '畲族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('23', '高山族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('24', '拉祜族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('25', '水族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('26', '东乡族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('27', '纳西族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('28', '景颇族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('29', '柯尔克孜族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('30', '土族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('31', '达斡尔族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('32', '仫佬族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('33', '羌族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('34', '布朗族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('35', '撒拉族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('36', '毛难族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('37', '仡佬族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '必修课', 'SYS', 'COURSE_TYPE_CODE', '课程类型代码', 1, 1);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '教职工', 'SYS', 'AFTER_GW_CODE', '岗位变动代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('38', '锡伯族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('39', '阿昌族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('40', '普米族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('41', '塔吉克族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('42', '怒族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('43', '乌孜别克族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('44', '俄罗斯族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('45', '鄂温克族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('46', '德昂族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('47', '保安族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('48', '裕固族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('49', '京族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('50', '塔塔尔族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('51', '独龙族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('52', '鄂伦春族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('53', '赫哲族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('54', '门巴族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('55', '珞巴族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('56', '基诺族', 'GB', 'NATION_CODE', '中国各民族名称的罗马字母拼写法和代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '党群', 'XB', 'DEPT_CATEGORY_CODE', '组织机构的单位类别代码', 1, 1);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '行政', 'XB', 'DEPT_CATEGORY_CODE', '组织机构的单位类别代码', 1, 2);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '教学单位', 'XB', 'DEPT_CATEGORY_CODE', '组织机构的单位类别代码', 1, 3);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '教辅', 'XB', 'DEPT_CATEGORY_CODE', '组织机构的单位类别代码', 1, 4);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '男', 'GB', 'SEX_CODE', '性别代码', 1, null);
insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '女', 'GB', 'SEX_CODE', '性别代码', 1, null);
insert into T_SYS_DATA_SERVICE (ID, NAME_, SERVICENAME)
values (4, '教职工所在的单位', 'deptPermissionService?getMyDeptSqlbyDataServe');
insert into T_SYS_DATA_SERVICE (ID, NAME_, SERVICENAME)
values (1, '自定义教学组织机构', 'deptPermissionService?getDeptTeachIdSqlbyDataServe');
insert into T_SYS_DATA_SERVICE (ID, NAME_, SERVICENAME)
values (2, '自定义行政组织机构', 'deptPermissionService?getDeptIdSqlbyDataServe');
insert into T_SYS_DATA_SERVICE (ID, NAME_, SERVICENAME)
values (3, '辅导员负责的班级', 'deptPermissionService?getDeptClassIdSqlbyDataServe');
commit;
insert into T_SYS_OPERATE (ID, NAME_, DESCRIPTION)
values (1, 'view', '查看');
insert into T_SYS_OPERATE (ID, NAME_, DESCRIPTION)
values (2, 'add', '添加');
insert into T_SYS_OPERATE (ID, NAME_, DESCRIPTION)
values (3, 'update', '更新');
insert into T_SYS_OPERATE (ID, NAME_, DESCRIPTION)
values (4, 'delete', '删除');
insert into T_SYS_OPERATE (ID, NAME_, DESCRIPTION)
values (5, 'print', '打印');
insert into T_SYS_OPERATE (ID, NAME_, DESCRIPTION)
values (6, '*', '全部');
commit;

insert into T_SYS_USER (ID, USERNAME, PASSWORD, REAL_NAME, SALT, ISTRUE, CREATE_TIME, UPDATE_TIME, DEPT_ID, DEPT_CATEGORY_CODE, ID_NO)
values (1, 'admin', '518f1de10142208410de88f3d14c8aa3', 'admin', '6e2cd3ddaed8e27462c367939bbc8758', 1, null, '2016-09-22 10:21:56', null, null, null);
commit;

insert into T_SYS_USER_ROLE (ID, USER_ID, ROLE_ID)
values (1, 1, 1);
commit;


insert into t_sys_role (ID, NAME_, DESCRIPTION, ISTRUE, ROLE_TYPE_CODE, ISMAIN, RESOURCEID)
values (1, 'admin', 'admin', 1, '04', 1, 1000110757);

insert into t_sys_role (ID, NAME_, DESCRIPTION, ISTRUE, ROLE_TYPE_CODE, ISMAIN, RESOURCEID)
values (2, 'teacher', '教师', 1, '02', 1, 1000110757);

insert into t_sys_role (ID, NAME_, DESCRIPTION, ISTRUE, ROLE_TYPE_CODE, ISMAIN, RESOURCEID)
values (3, 'student', '学生', 1, '01', 1, 1000110618);

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('0', '资源', null, '资源', '-1', '1', '0000', '1', '1', '1', '01', 'zy', null, '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000152313', '后台系统', 'http://192.168.30.31:8088/sys', '后台系统', '0', '2', '00001000152313', '1', '2', '后台', '06', 'ht', '0', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000110756', '权限管理', null, '权限管理', '1000152313', '3', '000010001523131000110756', '1', '1', '用户管理', '01', 'qx', '1000152313', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000110757', '用户管理', '/user/list', '用户管理', '1000110756', '4', '0000100015231310001107561000110757', '1', '1', '用户管理', '02', 'qx:yh', '1000152313', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000110758', '角色管理', '/system/role/getrole', '角色管理', '1000110756', '4', '0000100015231310001107561000110758', '1', '2', '角色管理', '02', 'qx:js', '1000152313', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000110759', '资源管理', '/system/user/resource', '资源管理', '1000110756', '4', '0000100015231310001107561000110759', '1', '3', '资源管理', '02', 'qx:zy', '1000152313', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000114547', '任务调度', null, '任务调度', '1000152313', '3', '000010001523131000114547', '1', '2', '任务调度', '01', 'dd', '1000152313', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000114548', '计划管理', '/system/task/plan', '计划管理', '1000114547', '4', '0000100015231310001145471000114548', '1', '1', '计划管理', '02', 'dd:jh', '1000152313', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000114549', '业务管理', '/system/task/work', '业务管理', '1000114547', '4', '0000100015231310001145471000114549', '1', '2', '业务管理', '02', 'dd:yw', '1000152313', '1');

insert into t_sys_resources (ID, NAME_, URL_, DESCRIPTION, PID, LEVEL_, PATH_, ISTRUE, ORDER_, KEYWORD, RESOURCE_TYPE_CODE, SHIRO_TAG, SYSGROUP_, ISSHOW)
values ('1000114550', '验证管理', '/system/task/verify', '验证管理', '1000114547', '4', '0000100015231310001145471000114550', '1', '3', '验证管理', '02', 'dd:yz', '1000152313', '1');

commit;