/*==============================================================*/
/* Table: T_RES_XKJS_MX                                         */
/*==============================================================*/
create table T_RES_XKJS_MX  (
   ID                   VARCHAR2(20)                    not null,
   XK_ID                VARCHAR2(20),
   ZB_ID                VARCHAR2(20),
   YEAR_                VARCHAR2(20),
   YWCZBS               VARCHAR2(20),
   WCZBS                VARCHAR2(20),
   LEADER_ID            VARCHAR2(20),
   constraint PK_T_RES_XKJS_MX primary key (ID)
);

comment on table T_RES_XKJS_MX is
'学科建设-明细';

comment on column T_RES_XKJS_MX.ID is
'ID';

comment on column T_RES_XKJS_MX.XK_ID is
'论文ID';

comment on column T_RES_XKJS_MX.ZB_ID is
'项目ID';

comment on column T_RES_XKJS_MX.YEAR_ is
'统计年份';

comment on column T_RES_XKJS_MX.YWCZBS is
'应完成指标数';

comment on column T_RES_XKJS_MX.WCZBS is
'完成指标数';

comment on column T_RES_XKJS_MX.LEADER_ID is
'LEADER_ID';

/*==============================================================*/
/* Table: T_RES_XKJS_XK                                         */
/*==============================================================*/
create table T_RES_XKJS_XK  (
   ID                   VARCHAR2(20)                    not null,
   NAME_                VARCHAR2(60),
   YX_ID                VARCHAR2(20),
   LEVEL_               VARCHAR2(20),
   DESCRIPTION          VARCHAR2(400),
   constraint PK_T_RES_XKJS_XK primary key (ID)
);

comment on table T_RES_XKJS_XK is
'学科建设-学科';

comment on column T_RES_XKJS_XK.ID is
'ID';

comment on column T_RES_XKJS_XK.NAME_ is
'学科名称';

comment on column T_RES_XKJS_XK.YX_ID is
'所属院系';

comment on column T_RES_XKJS_XK.LEVEL_ is
'级别';

comment on column T_RES_XKJS_XK.DESCRIPTION is
'学科描述';


/*==============================================================*/
/* Table: T_RES_XKJS_XKCY                                       */
/*==============================================================*/
create table T_RES_XKJS_XKCY  (
   ID                   VARCHAR2(20)                    not null,
   XK_ID                VARCHAR2(20),
   TEA_NO               VARCHAR2(20),
   IS_LEADER            VARCHAR2(30),
   constraint PK_T_RES_XKJS_XKCY primary key (ID)
);

comment on table T_RES_XKJS_XKCY is
'学科建设-学科成员';

comment on column T_RES_XKJS_XKCY.ID is
'ID';

comment on column T_RES_XKJS_XKCY.XK_ID is
'学科ID';

comment on column T_RES_XKJS_XKCY.TEA_NO is
'职工号';

comment on column T_RES_XKJS_XKCY.IS_LEADER is
'IS_LEADER';
 
/*==============================================================*/
/* Table: T_RES_XKJS_ZB                                         */
/*==============================================================*/
create table T_RES_XKJS_ZB  (
   ID                   VARCHAR2(20)                    not null,
   NAME_                VARCHAR2(120),
   DESCRIPTION          VARCHAR2(400),
   constraint PK_T_RES_XKJS_ZB primary key (ID)
);

comment on table T_RES_XKJS_ZB is
'学科建设-指标';

comment on column T_RES_XKJS_ZB.ID is
'ID';

comment on column T_RES_XKJS_ZB.NAME_ is
'指标名称';

comment on column T_RES_XKJS_ZB.DESCRIPTION is
'指标描述';


/*==============================================================*/
/* Table: T_RES_XKJS_XKCYXX                                     */
/*==============================================================*/
create table T_RES_XKJS_XKCYXX  (
   ID                   VARCHAR2(20)                    not null,
   YEAR_                VARCHAR2(20),
   XK_ID                VARCHAR2(20),
   ZRS                  NUMBER(10),
   BSS                  NUMBER(10),
   GJZCRS               NUMBER(10),
   SDS                  NUMBER(10),
   BDS                  NUMBER(10),
   constraint PK_T_RES_XKJS_XKCYXX primary key (ID)
);

comment on table T_RES_XKJS_XKCYXX is
'学科建设-学科人员汇总信息';

comment on column T_RES_XKJS_XKCYXX.ID is
'ID';

comment on column T_RES_XKJS_XKCYXX.YEAR_ is
'统计年份';

comment on column T_RES_XKJS_XKCYXX.XK_ID is
'学科ID';

comment on column T_RES_XKJS_XKCYXX.ZRS is
'职工号';

comment on column T_RES_XKJS_XKCYXX.BSS is
'博士数';

comment on column T_RES_XKJS_XKCYXX.GJZCRS is
'高级职称人数';

comment on column T_RES_XKJS_XKCYXX.SDS is
'硕导数';

comment on column T_RES_XKJS_XKCYXX.BDS is
'博导数';

