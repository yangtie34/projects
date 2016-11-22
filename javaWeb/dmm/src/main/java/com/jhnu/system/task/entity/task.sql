--任务调度
--业务
    create table "T_SYS_SCHEDULE_WORK"(
        "ID" VARCHAR2(20) not null,
       "NAME_" VARCHAR2(20) ,--业务名称
		"GROUP_" VARCHAR2(20) ,--分组业务系统
		"SERVICE" VARCHAR2(40) ,--业务系统url地址
      	 "DESC_" VARCHAR2(40) ,--是
		"ISTRUE" NUMBER(1) ,--0 否 1 是
        constraint "T_SYS_SCHEDULE_WORK" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_WORK" on "T_SYS_SCHEDULE_WORK"("ID");
--验证
    create table "T_SYS_SCHEDULE_VERIFY"(
        "ID" VARCHAR2(20) not null,
       "NAME_" VARCHAR2(20) ,--验证名称
		"GROUP_" VARCHAR2(20) ,--分组验证系统
		"SERVICE" VARCHAR2(40) ,--系统url地址
      	 "DESC_" VARCHAR2(40) ,--是
		"ISTRUE" NUMBER(1) ,--0 否 1 是
        constraint "T_SYS_SCHEDULE_VERIFY" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_VERIFY" on "T_SYS_SCHEDULE_VERIFY"("ID");
--计划
    create table "T_SYS_SCHEDULE_PLAN"(
        "ID" VARCHAR2(20) not null,
       "NAME_" VARCHAR2(20) ,--计划名称
		"GROUP_" VARCHAR2(20) ,--分组业务系统
		"CRON_EXPRESSION" VARCHAR2(20) ,--表达式
       "DESC_" VARCHAR2(200) ,--是
		"ISTRUE" NUMBER(1) ,--0 否 1 是
        constraint "T_SYS_SCHEDULE_PLAN" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_PLAN" on "T_SYS_SCHEDULE_PLAN"("ID");
--业务-验证
 create table "T_SYS_SCHEDULE_WORK_VERIFY"(
        "ID" VARCHAR2(20) not null,
       "WORKID" VARCHAR2(20) ,--业务id
		"VERIFYID" VARCHAR2(20) ,--验证id
		"RULE" NUMBER(1) ,--验证规则 必须非必须
		"ORDER_" VARCHAR2(20) ,--执行顺序、排序
        constraint "T_SYS_SCHEDULE_WORK_VERIFY" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_WORK_VERIFY" on "T_SYS_SCHEDULE_WORK_VERIFY"("ID");
--计划-业务
 create table "T_SYS_SCHEDULE_PLAN_WORK"(
        "ID" VARCHAR2(20) not null,
        "PLANID" VARCHAR2(20) ,--计划id
       	"WORKID" VARCHAR2(20) ,--业务id
		"ORDER_" VARCHAR2(20) ,--执行顺序、排序
        constraint "T_SYS_SCHEDULE_PLAN_WORK" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_PLAN_WORK" on "T_SYS_SCHEDULE_PLAN_WORK"("ID");
--计划-日志
 create table "T_SYS_SCHEDULE_PLAN_LOG"(
        "ID" VARCHAR2(20) not null,
        "PLANID" VARCHAR2(20) ,--计划id
		"ISYES" NUMBER(1) ,--0 否 1 是 执行结果
		"STARTTIME" VARCHAR2(20) not null ,--开始时间
		"ENDTIME" VARCHAR2(20) not null ,--结束时间
        constraint "T_SYS_SCHEDULE_PLAN_LOG" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_PLAN_LOG" on "T_SYS_SCHEDULE_PLAN_LOG"("ID");
	
 create table "T_SYS_SCHEDULE_PLAN_LOG_DETAIL"( --计划详情日志
        "ID" VARCHAR2(20) not null,
        "PLANLOGID" VARCHAR2(20) ,--计划日志id
		"LOGTYPEID" VARCHAR2(20) ,--日志类型的执行id
		"ISYES" NUMBER(1) ,--0 否 1 是 执行结果
		"RESULT_DESC" VARCHAR2(100),--执行描述
		"LOGTYPE" VARCHAR2(20) ,--日志类型 业务、验证
		"STARTTIME" VARCHAR2(20) not null ,--开始时间
		"ENDTIME" VARCHAR2(20) not null ,--结束时间
        constraint "T_SYS_SCHEDULE_PLAN_LOG_DETAIL" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_PLAN_LOG_DETAIL" on "T_SYS_SCHEDULE_PLAN_LOG_DETAIL"("ID");
--业务系统group地址
	  create table "T_SYS_SCHEDULE_GROUP"(
        "ID" VARCHAR2(20) not null,
       "URL_" VARCHAR2(40) ,--验证名称
        constraint "T_SYS_SCHEDULE_GROUP" primary key ("ID")
    );
	create unique index "T_SYS_SCHEDULE_GROUP" on "T_SYS_SCHEDULE_GROUP"("ID");
	select * from t_code where code_type='SCHEDULE_GROUP_CODE'
	--增加code表group code
	INSERT INTO t_code VALUES ('01','学工系统','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);
	INSERT INTO t_code VALUES ('02','教务系统','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);
	INSERT INTO t_code VALUES ('03','人事系统','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);
	INSERT INTO t_code VALUES ('04','科研系统','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);
	INSERT INTO t_code VALUES ('05','后勤系统','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);
	INSERT INTO t_code VALUES ('06','综合系统','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);
	INSERT INTO t_code VALUES ('07','权限系统','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);
	INSERT INTO t_code VALUES ('08','统一门户','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null);