/*==============================================================*/
/* DBMS name:      ORACLE Version 10gR2                         */
/* Created on:     2016-7-29 18:09:53                           */
/*==============================================================*/


drop table T_COMMODITY cascade constraints;

drop table T_COMMODITY_IMAGE cascade constraints;

drop table T_LIAO_RELATION cascade constraints;

drop table T_LIAO_RELATION_APPLY cascade constraints;

drop table T_LIAO_RELATION_LOG cascade constraints;

drop table T_LIAO_TOPIC cascade constraints;

drop table T_LIAO_TOPIC_IMAGE cascade constraints;

drop table T_LIAO_TOPIC_REPLY cascade constraints;

drop table T_LOSTFOUND cascade constraints;

drop table T_LIAO_TOPIC_REPLY_ANSWER cascade constraints;

/*==============================================================*/
/* Table: T_COMMODITY                                           */
/*==============================================================*/
create table T_COMMODITY  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20),
   NAME_                VARCHAR2(100),
   KEYWORD              VARCHAR2(20),
   DESC_                VARCHAR2(2000),
   COMMODITY_TYPE_CODE  VARCHAR2(10),
   USE_TIME             VARCHAR2(20),
   PRICE                NUMBER(8,2),
   STU_NAME             VARCHAR2(20),
   TEL                  VARCHAR2(20),
   IS_SOLD              NUMBER(1),
   CREATE_TIME          VARCHAR2(20),
   SOLD_TIME            varchar2(20),
   constraint PK_T_COMMODITY primary key (ID)
);

comment on table T_COMMODITY is
'二手商品表';

comment on column T_COMMODITY.ID is
'ID';

comment on column T_COMMODITY.STU_ID is
'学生ID';

comment on column T_COMMODITY.NAME_ is
'商品名称';

comment on column T_COMMODITY.KEYWORD is
'关键字';

comment on column T_COMMODITY.DESC_ is
'商品描述';

comment on column T_COMMODITY.COMMODITY_TYPE_CODE is
'商品类型';

comment on column T_COMMODITY.USE_TIME is
'使用期限';

comment on column T_COMMODITY.PRICE is
'价格';

comment on column T_COMMODITY.STU_NAME is
'联系人';

comment on column T_COMMODITY.TEL is
'联系电话';

comment on column T_COMMODITY.IS_SOLD is
'是否已售';

comment on column T_COMMODITY.CREATE_TIME is
'发布时间';

comment on column T_COMMODITY.SOLD_TIME is
'交易完成时间';

/*==============================================================*/
/* Table: T_COMMODITY_IMAGE                                     */
/*==============================================================*/
create table T_COMMODITY_IMAGE  (
   ID                   VARCHAR2(20)                    not null,
   COMMODITY_ID         VARCHAR2(20),
   IMAGE_URL            VARCHAR2(200),
   ORDER_               NUMBER(2),
   constraint PK_T_COMMODITY_IMAGE primary key (ID)
);

comment on table T_COMMODITY_IMAGE is
'二手商品图片表';

comment on column T_COMMODITY_IMAGE.ID is
'ID';

comment on column T_COMMODITY_IMAGE.COMMODITY_ID is
'商品ID';

comment on column T_COMMODITY_IMAGE.IMAGE_URL is
'图片地址';

comment on column T_COMMODITY_IMAGE.ORDER_ is
'顺序';


/*==============================================================*/
/* Table: T_LIAO_RELATION                                       */
/*==============================================================*/
create table T_LIAO_RELATION  (
   ID                   VARCHAR2(20)                    not null,
   USERNAMEA            VARCHAR2(50),
   USERNAMEB            VARCHAR2(50),
   APPLY_ID             VARCHAR2(20),
   CREATE_TIME          TIMESTAMP                      default SYSDATE,
   constraint PK_T_LIAO_RELATION primary key (ID)
);

comment on table T_LIAO_RELATION is
'聊吧-好友关系';

comment on column T_LIAO_RELATION.ID is
'ID';

comment on column T_LIAO_RELATION.USERNAMEA is
'用户A';

comment on column T_LIAO_RELATION.USERNAMEB is
'用户B';

comment on column T_LIAO_RELATION.APPLY_ID is
'好友申请ID';

comment on column T_LIAO_RELATION.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: T_LIAO_RELATION_APPLY                                 */
/*==============================================================*/
create table T_LIAO_RELATION_APPLY  (
   ID                   VARCHAR2(20)                    not null,
   USERNAME             VARCHAR2(50),
   TARGET_USERNAME      VARCHAR2(50),
   STATE                NUMBER(1),
   CREATE_TIME          TIMESTAMP                      default SYSDATE,
   constraint PK_T_LIAO_RELATION_APPLY primary key (ID)
);

comment on table T_LIAO_RELATION_APPLY is
'聊吧-好友申请';

comment on column T_LIAO_RELATION_APPLY.ID is
'ID';

comment on column T_LIAO_RELATION_APPLY.USERNAME is
'发起人';

comment on column T_LIAO_RELATION_APPLY.TARGET_USERNAME is
'对象';

comment on column T_LIAO_RELATION_APPLY.STATE is
'状态（0：初始，1：通过，2：拒绝，3完结）';

comment on column T_LIAO_RELATION_APPLY.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: T_LIAO_RELATION_LOG                                   */
/*==============================================================*/
create table T_LIAO_RELATION_LOG  (
   ID                   VARCHAR2(20)                    not null,
   USERNAMEA            VARCHAR2(50),
   USERNAMEB            VARCHAR2(50),
   OPERATION            VARCHAR2(20),
   CREATE_TIME          TIMESTAMP                      default SYSDATE,
   constraint PK_T_LIAO_RELATION_LOG primary key (ID)
);

comment on table T_LIAO_RELATION_LOG is
'聊吧-好友关系日志表';

comment on column T_LIAO_RELATION_LOG.ID is
'ID';

comment on column T_LIAO_RELATION_LOG.USERNAMEA is
'用户A';

comment on column T_LIAO_RELATION_LOG.USERNAMEB is
'用户B';

comment on column T_LIAO_RELATION_LOG.OPERATION is
'动作';

comment on column T_LIAO_RELATION_LOG.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: T_LIAO_TOPIC                                          */
/*==============================================================*/
create table T_LIAO_TOPIC  (
   ID                   VARCHAR2(20)                    not null,
   USERNAME             VARCHAR2(50),
   CONTENT              VARCHAR2(3000),
   STATE                NUMBER(1),
   CREATE_TIME          TIMESTAMP                      default SYSDATE,
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_LIAO_TOPIC primary key (ID)
);

comment on table T_LIAO_TOPIC is
'聊吧-帖子信息';

comment on column T_LIAO_TOPIC.ID is
'ID';

comment on column T_LIAO_TOPIC.USERNAME is
'发表人';

comment on column T_LIAO_TOPIC.CONTENT is
'内容';

comment on column T_LIAO_TOPIC.STATE is
'状态';

comment on column T_LIAO_TOPIC.CREATE_TIME is
'创建时间';

comment on column T_LIAO_TOPIC.UPDATE_TIME is
'修改时间(删除时间)';

/*==============================================================*/
/* Table: T_LIAO_TOPIC_IMAGE                                    */
/*==============================================================*/
create table T_LIAO_TOPIC_IMAGE  (
   ID                   VARCHAR2(20)                    not null,
   TOPIC_ID             VARCHAR2(50),
   IMG_URL              VARCHAR2(3000),
   CREATE_TIME          TIMESTAMP                      default SYSDATE,
   constraint PK_T_LIAO_TOPIC_IMAGE primary key (ID)
);

comment on table T_LIAO_TOPIC_IMAGE is
'聊吧-帖子图片';

comment on column T_LIAO_TOPIC_IMAGE.ID is
'ID';

comment on column T_LIAO_TOPIC_IMAGE.TOPIC_ID is
'帖子ID';

comment on column T_LIAO_TOPIC_IMAGE.IMG_URL is
'图片地址';

comment on column T_LIAO_TOPIC_IMAGE.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: T_LIAO_TOPIC_REPLY                                    */
/*==============================================================*/
create table T_LIAO_TOPIC_REPLY  (
   ID                   VARCHAR2(20)                    not null,
   USERNAME             VARCHAR2(50),
   TOPIC_ID             VARCHAR2(20),
   CONTENT              VARCHAR2(3000),
   STATE                NUMBER(1),
   CREATE_TIME          TIMESTAMP                      default SYSDATE,
   constraint PK_T_LIAO_TOPIC_REPLY primary key (ID)
);

comment on table T_LIAO_TOPIC_REPLY is
'聊吧-帖子回复';

comment on column T_LIAO_TOPIC_REPLY.ID is
'ID';

comment on column T_LIAO_TOPIC_REPLY.USERNAME is
'发表人';

comment on column T_LIAO_TOPIC_REPLY.TOPIC_ID is
'帖子ID';

comment on column T_LIAO_TOPIC_REPLY.CONTENT is
'内容';

comment on column T_LIAO_TOPIC_REPLY.STATE is
'状态';

comment on column T_LIAO_TOPIC_REPLY.CREATE_TIME is
'创建时间';

/*==============================================================*/
/* Table: T_LIAO_TOPIC_REPLY_ANSWER                             */
/*==============================================================*/
create table T_LIAO_TOPIC_REPLY_ANSWER  (
   ID                   VARCHAR2(20)                    not null,
   USERNAME             VARCHAR2(50),
   TO_USERNAME          VARCHAR2(50),
   REPLY_ID             VARCHAR2(20),
   CONTENT              VARCHAR2(3000),
   STATE                NUMBER(1),
   CREATE_TIME          TIMESTAMP                      default SYSDATE,
   constraint PK_T_LIAO_TOPIC_REPLY_ANSWER primary key (ID)
);

comment on table T_LIAO_TOPIC_REPLY_ANSWER is
'聊吧-帖子回复';

comment on column T_LIAO_TOPIC_REPLY_ANSWER.ID is
'ID';

comment on column T_LIAO_TOPIC_REPLY_ANSWER.USERNAME is
'发表人';

comment on column T_LIAO_TOPIC_REPLY_ANSWER.TO_USERNAME is
'被评人';

comment on column T_LIAO_TOPIC_REPLY_ANSWER.REPLY_ID is
'回复ID';

comment on column T_LIAO_TOPIC_REPLY_ANSWER.CONTENT is
'内容';

comment on column T_LIAO_TOPIC_REPLY_ANSWER.STATE is
'状态';

comment on column T_LIAO_TOPIC_REPLY_ANSWER.CREATE_TIME is
'创建时间';
 
/*==============================================================*/
/* Table: T_LOSTFOUND                                           */
/*==============================================================*/
create table T_LOSTFOUND  (
   ID                   VARCHAR2(20)                    not null,
   USERNAME             VARCHAR2(50),
   LOSTFOUND_TYPE_CODE  VARCHAR2(10),
   MESSAGE              VARCHAR2(2000),
   TEL_                 VARCHAR2(20),
   IMAGE_URL            VARCHAR2(200),
   CREAT_TIME           VARCHAR2(20),
   IS_SOLVE             NUMBER(1),
   SOLVE_TIME           VARCHAR2(20),
   constraint PK_T_LOSTFOUND primary key (ID)
);

comment on table T_LOSTFOUND is
'失而复得';

comment on column T_LOSTFOUND.ID is
'ID';

comment on column T_LOSTFOUND.USERNAME is
'用户名';

comment on column T_LOSTFOUND.LOSTFOUND_TYPE_CODE is
'发文类型';

comment on column T_LOSTFOUND.MESSAGE is
'发布内容';

comment on column T_LOSTFOUND.TEL_ is
'联系方式';

comment on column T_LOSTFOUND.IMAGE_URL is
'图片地址';

comment on column T_LOSTFOUND.CREAT_TIME is
'发布时间';

comment on column T_LOSTFOUND.IS_SOLVE is
'是否解决';

comment on column T_LOSTFOUND.SOLVE_TIME is
'解决时间';
