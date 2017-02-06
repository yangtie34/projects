/*==============================================================*/
/* 科研奖励办法新增编码  2016-11-15							    */
/*==============================================================*/
--项目等级
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('04', '国家社科基金其他项目', 'XB','RES_PROJECT_RANK_CODE', '项目程度', '1', '');
--获奖成果
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('03', '一级学会奖', 'XB','RES_AWARD_LEVEL_CODE', '科研成果获奖级别', '1', '');
--专利类别
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('01', '国内', 'XB','RES_PATENT_SCOPE_CODE', '科研专利范围代码', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('02', '国际', 'XB','RES_PATENT_SCOPE_CODE', '科研专利范围代码', '1', '');
--论文期刊类别
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('006', 'Nature、Science(学术类)', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('007', 'SCI一区', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('008', 'SCI二区', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('009', 'SCI三区', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('010', 'SCI四区', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('011', 'CSSCI期刊A类', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('012', 'CSSCI期刊B类', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('013', 'CSSCI期刊C类', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('014', 'SSCI', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('015', 'A&HCI收录', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('016', '光明日报', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');
insert into t_code(code_, name_, code_category, code_type, codetype_name, istrue, order_)values('017', '人民日报', 'XB','RES_PERIODICAL_TYPE_CODE', '期刊类别', '1', '');

/*==============================================================*/
/* 科研表结构修改	项目、专利、获奖成果、收录论文、转载论文都添加统计年份、署名单位数、我校排名。
 * 项目添加是否有结项奖，专利添加专利范围code字段，获奖成果添加是否是最高学会奖（专利类别为学会奖的才有）	2016-11-21					        */
/*==============================================================*/
alter table T_RES_PROJECT add (HAS_END_AWARD NUMBER(2),STAT_YEAR VARCHAR2(10),DEPT_NUMS NUMBER(2),RANK_ NUMBER(2));
alter table T_RES_PATENT add (PATENT_SCOPE_CODE VARCHAR2(10),STAT_YEAR VARCHAR2(10),DEPT_NUMS NUMBER(2),RANK_ NUMBER(2));
alter table T_RES_THESIS_IN add (STAT_YEAR VARCHAR2(10));
alter table T_RES_THESIS_RESHIP add (STAT_YEAR VARCHAR2(10));
alter table T_RES_THESIS add (DEPT_NUMS NUMBER(2),RANK_ NUMBER(2),STAT_YEAR VARCHAR2(10));
alter table T_RES_HJCG add (IS_HIGHEST_AWARD NUMBER(2),STAT_YEAR VARCHAR2(10),DEPT_NUMS NUMBER(2),RANK_ NUMBER(2));


/*==============================================================*/
/* 科研奖励办法处理表结构		2016-11-15					        */
/*==============================================================*/
--科研奖励结果表
create table T_RES_AWARD_RESULT  (
   ID                   VARCHAR2(20)                    not null,
   TEA_ID               VARCHAR2(20),
   AWARD_CODE           VARCHAR2(20),
   STANDARD_CODE        VARCHAR2(20),
   FUND                 NUMBER(18,6),
   RES_ID               VARCHAR2(20),
   YEAR_                VARCHAR2(10),
   PROJECT_ID			VARCHAR2(20),
   constraint PK_T_RES_AWARD_RESULT primary key (ID)
);
--科研奖励类别表
create table T_RES_CODE  (
   CODE_                VARCHAR2(20)                    not null,
   NAME_                VARCHAR2(100),
   IS_TRUE              NUMBER(1),
   constraint PK_T_RES_CODE primary key (CODE_)
);
--科研获奖成果奖励标准表
create table T_RES_CODE_ACHIEVEMENT  (
   CODE_                VARCHAR2(20)                    not null,
   AWARD_NAME           VARCHAR2(200),
   AWARD_LEVEL_CODE     VARCHAR2(20),
   AWARD_TYPE_CODE      VARCHAR2(20),
   AWARD_RANK_CODE      VARCHAR2(20),
   JS                   NUMBER(18,6),
   A                    NUMBER(18,6),
   XS                   NUMBER(18,6),
   AWARD_FUND           NUMBER(18,6),
   constraint PK_T_RES_CODE_ACHIEVEMENT primary key (CODE_)
);
--科研获奖成果参与单位数单位排名获奖标准
create table T_RES_CODE_DEPT_RANK  (
   ID                   VARCHAR2(20)                    not null,
   DEPT_NUMS            NUMBER(8),
   DEPT_RANK            NUMBER(8),
   PROP                 NUMBER(18,6),
   constraint PK_T_RES_CODE_DEPT_RANK primary key (ID)
);
--科研奖励专利奖励标准表
create table T_RES_CODE_PATENT  (
   CODE_                VARCHAR2(20)                    not null,
   PATENT_TYPE_CODE     VARCHAR2(20),
   PATENT_SCOPE_CODE    VARCHAR2(20),
   AWARD_FUND           NUMBER(18,6),
   constraint PK_T_RES_CODE_PATENT primary key (CODE_)
);
--科研奖励立项奖标准表
create table T_RES_CODE_PRO_SETUP  (
   CODE_                VARCHAR2(20)                    not null,
   PROJECT_LEVEL_CODE   VARCHAR2(20),
   PROJECT_TYPE         VARCHAR2(200),
   PROJECT_RANK_CODE    VARCHAR2(20),
   AWARD_FUND           NUMBER(18,6),
   constraint PK_T_RES_CODE_PRO_SETUP primary key (CODE_)
);
--科研奖励论文标准表
create table T_RES_CODE_THESIS  (
   CODE_                VARCHAR2(20)                    not null,
   PERIODICAL_TYPE_CODE VARCHAR2(20),
   AWARD_FUND           NUMBER(18,6),
   IS_IN           NUMBER(2),
   constraint PK_T_RES_CODE_THESIS primary key (CODE_)
);
--科研项目经费奖励标准表
create table T_RES_PROJECT_FUND  (
   PROJECT_ID           VARCHAR2(20),
   TIME_                VARCHAR2(10),
   FUND                 NUMBER(18,6)
);
--科研成果转化表
create table T_RES_TRANSFORM
(
  CONTRACT_NO VARCHAR2(20),
  NAME_       VARCHAR2(100),
  OWNER_      VARCHAR2(100),
  DEPT_ID     VARCHAR2(20),
  PRINCIPAL   VARCHAR2(20),
  FUND_       NUMBER(18,6),
  GET_FUND    NUMBER(18,6),
  SIGN_TIME   VARCHAR2(10),
  REMARK      VARCHAR2(200),
  STAT_YEAR   VARCHAR2(10),
  PROJECT_ID  VARCHAR2(20)
)
--科研成果转化作者表
create table T_RES_TRANSFORM_AUTHOR
(
  ID                   VARCHAR2(20),
  TRANS_NO             VARCHAR2(20),
  PEOPLE_ID            VARCHAR2(20),
  ORDER_               NUMBER(2),
  PEOPLE_IDENTITY_CODE VARCHAR2(10),
  TEA_NO               VARCHAR2(20),
  OUTSIDE_AUTHOR       VARCHAR2(60),
  PEOPLE_NAME          VARCHAR2(20)
)
/*==============================================================*/
/* 科研奖励办法标准数据		2016-11-21					        */
/*==============================================================*/
--科研奖励项数据
insert into t_res_code(code_, name_, is_true) values('01', '高层次项目立项奖', '1');
insert into t_res_code(code_, name_, is_true) values('02', '高层次项目结项奖', '1');
insert into t_res_code(code_, name_, is_true) values('03', '高层次研究成果奖', '1');
insert into t_res_code(code_, name_, is_true) values('04', '高层次学术论文奖', '1');
insert into t_res_code(code_, name_, is_true) values('05', '发明专利奖', '1');
insert into t_res_code(code_, name_, is_true) values('06', '科研经费奖', '1');
insert into t_res_code(code_, name_, is_true) values('07', '科研成果转化奖', '1');

--科研专利奖励标准
insert into t_res_code_patent(code_, patent_type_code,patent_scope_code, award_fund) values('01', '04', '01','1');
insert into t_res_code_patent(code_, patent_type_code,patent_scope_code, award_fund) values('02', '04', '02','2');



