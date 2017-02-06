-- 学生工作专题 sql 文件

-- 添加 学生工作者岗位类型 【CODE_CATEGORY = 'XG'】
delete from T_CODE where CODE_TYPE = 'STU_WORKER_CODE'; -- 删除编码，防止重复插入

insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '学生工作领导', 'XG', 'STU_WORKER_CODE', '学生工作者岗位类型', '1', '1');

insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '专职辅导员', 'XG', 'STU_WORKER_CODE', '学生工作者岗位类型', '1', '2');

insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '兼职辅导员', 'XG', 'STU_WORKER_CODE', '学生工作者岗位类型', '1', '3');

insert into T_CODE (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('4', '其他工作者', 'XG', 'STU_WORKER_CODE', '学生工作者岗位类型', '1', '4');

/*==============================================================*/
/* Table: T_TEA_STU_WORKER 教职工-学生工作者                                     */
/*==============================================================*/
create table T_TEA_STU_WORKER  (
   ID                   VARCHAR2(20)                    not null,
   TEA_ID               VARCHAR2(20),
   START_DATE           VARCHAR2(10),
   END_DATE             VARCHAR2(10),
   STU_WORKER_CODE      VARCHAR2(20),
   constraint PK_T_TEA_STU_WORKER primary key (ID)
);

comment on table T_TEA_STU_WORKER is
'教职工-学生工作者';

comment on column T_TEA_STU_WORKER.TEA_ID is
'职工号_ID';

comment on column T_TEA_STU_WORKER.START_DATE is
'开始时间';

comment on column T_TEA_STU_WORKER.END_DATE is
'结束时间';

comment on column T_TEA_STU_WORKER.STU_WORKER_CODE is
'岗位类型_CODE';

-- 辅导员聘任表添加字段  ISFULLTIME（是否专职辅导员）
alter table t_classes_instructor add (ISFULLTIME number(1) );
comment on column T_CLASSES_INSTRUCTOR.ISFULLTIME is '是否专职辅导员';

/*==============================================================*/
/* Table: T_STU_CHANGE    学生-学籍异动                                    */
/*==============================================================*/
-- drop table T_STU_CHANGE;
create table T_STU_CHANGE  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20)                    not null,
   STU_CHANGE_CODE      VARCHAR2(20)                    not null,
   CHANGE_REASON_ID     VARCHAR2(20)                    not null,
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   DATE_                VARCHAR2(10),
   SP_DATE              VARCHAR2(10),
   SPWH                 VARCHAR2(60),
   OLD_SCHOOL           VARCHAR2(60),
   OLD_DEPT_ID          VARCHAR2(20),
   OLD_MAJOR_ID         VARCHAR2(20),
   OLD_CLASS_ID         VARCHAR2(20),
   OLD_GRADE            VARCHAR2(4),
   OLD_XZ               NUMBER(1),
   NOW_SCHOOL           VARCHAR2(60),
   NOW_DEPT_ID          VARCHAR2(20),
   NOW_MAJOR_ID         VARCHAR2(20),
   NOW_CLASS_ID         VARCHAR2(20),
   NOW_GRADE            VARCHAR2(4),
   NOW_XZ               NUMBER(1),
   constraint PK_T_STU_CHANGE primary key (ID)
);
comment on table T_STU_CHANGE is
'学生-学籍异动';

-- 添加 学籍异动类别 编码 
delete from t_code where CODE_TYPE = 'STU_CHANGE_CODE';
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('01', '公派留学', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('02', '留级', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('03', '降级', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '3');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('04', '跳级', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '4');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('05', '试读', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '5');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('06', '延长年限', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '6');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('07', '试读通过', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '7');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('08', '回国复学', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '8');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('11', '休学', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '11');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('12', '复学', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '12');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('13', '停学', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '13');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('14', '保留入学资格', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '14');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('15', '恢复入学资格', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '15');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('16', '取消入学资格', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '16');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('17', '恢复学籍', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '17');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('18', '取消学籍', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '18');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('19', '保留学籍', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '19');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('21', '转学（转出）', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '21');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('22', '转学（转入）', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '22');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('23', '转专业', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '23');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('24', '专升本', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '24');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('25', '本转专', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '25');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('26', '转系', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '26');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('27', '硕转博', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '27');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('28', '博转硕', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '28');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('29', '转班级', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '29');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('31', '退学', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '31');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('42', '开除学籍', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '42');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('51', '死亡', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '51');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('61', '提前毕业', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '61');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('62', '结业', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '62');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('63', '肄业', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '63');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('64', '国内访学', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '64');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('65', '国内访学后返校', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '65');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('99', '其他', 'HB', 'STU_CHANGE_CODE', '学籍异动类别代码', '1', '99');

/*==============================================================*/
/* Table: T_CODE_CHANGE_REASON  学籍异动原因代码                                */
/*==============================================================*/
create table T_CODE_CHANGE_REASON  (
   ID                   VARCHAR2(20)                    not null,
   CODE_                VARCHAR2(60)                    not null,
   NAME_                VARCHAR2(100)                   not null,
   PID                  VARCHAR2(20)                    not null,
   PATH_                VARCHAR2(200),
   LEVEL_               NUMBER(2),
   LEVEL_TYPE           VARCHAR2(20),
   ISTRUE               NUMBER(1),
   ORDER_               NUMBER(4),
   constraint PK_T_CODE_CHANGE_REASON primary key (ID)
);
comment on table T_CODE_CHANGE_REASON is
'学籍异动原因代码';

-- 添加 学籍异动原因 编码
insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('01', '01', '成绩优秀', '-1', '', '2', '', '1', '1');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('10', '10', '疾病', '-1', '', '1', '', '1', '10');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('11', '11', '精神疾病', '10', '', '2', '', '1', '11');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('12', '12', '传染疾病', '10', '', '2', '', '1', '12');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('19', '19', '其他疾病', '10', '', '2', '', '1', '19');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('21', '21', '自动退学', '-1', '', '2', '', '1', '21');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('22', '22', '成绩太差', '-1', '', '2', '', '1', '22');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('23', '23', '触犯刑法', '-1', '', '2', '', '1', '23');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('24', '24', '休学创业', '-1', '', '2', '', '1', '24');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('25', '25', '停学实践（求职）', '-1', '', '2', '', '1', '25');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('26', '26', '家长病重', '-1', '', '2', '', '1', '26');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('27', '27', '贫困', '-1', '', '2', '', '1', '27');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('28', '28', '非留学出国（境）', '-1', '', '2', '', '1', '28');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('29', '29', '自费出国退学', '-1', '', '2', '', '1', '29');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('30', '30', '自费留学', '-1', '', '2', '', '1', '30');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('31', '31', '休学期满未办理复学', '-1', '', '2', '', '1', '31');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('32', '32', '恶意欠缴学费', '-1', '', '2', '', '1', '32');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('33', '33', '超过最长学习期限未完成学业', '-1', '', '2', '', '1', '33');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('34', '34', '应征入伍', '-1', '', '2', '', '1', '34');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('39', '39', '其他原因退学', '-1', '', '2', '', '1', '39');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('40', '40', '事故灾难致死', '-1', '', '1', '', '1', '40');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('41', '41', '溺水死亡', '40', '', '2', '', '1', '41');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('42', '42', '交通事故致死', '40', '', '2', '', '1', '42');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('43', '43', '拥挤踩踏致死', '40', '', '2', '', '1', '43');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('44', '44', '房屋倒塌致死', '40', '', '2', '', '1', '44');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('45', '45', '坠楼坠崖致死', '40', '', '2', '', '1', '45');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('46', '46', '中毒致死', '40', '', '2', '', '1', '46');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('47', '47', '爆炸致死', '40', '', '2', '', '1', '47');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('50', '50', '社会安全事件致死', '-1', '', '1', '', '1', '50');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('51', '51', '打架斗殴致死', '50', '', '2', '', '1', '51');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('52', '52', '校园伤害致死', '50', '', '2', '', '1', '52');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('53', '53', '刑事案件致死', '50', '', '2', '', '1', '53');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('54', '54', '火灾致死', '50', '', '2', '', '1', '54');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('60', '60', '自然灾害致死', '-1', '', '1', '', '1', '60');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('61', '61', '山体滑坡致死', '60', '', '2', '', '1', '61');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('62', '62', '泥石流致死', '60', '', '2', '', '1', '62');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('63', '63', '洪水致死', '60', '', '2', '', '1', '63');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('64', '64', '地震致死', '60', '', '2', '', '1', '64');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('65', '65', '暴雨致死', '60', '', '2', '', '1', '65');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('66', '66', '冰雹致死', '60', '', '2', '', '1', '66');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('67', '67', '雪灾致死', '60', '', '2', '', '1', '67');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('68', '68', '龙卷风致死', '60', '', '2', '', '1', '68');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('70', '70', '因病死亡', '-1', '', '1', '', '1', '70');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('71', '71', '传染病致死', '70', '', '2', '', '1', '71');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('72', '72', '猝死', '70', '', '2', '', '1', '72');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('79', '79', '其他疾病致死', '70', '', '2', '', '1', '79');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('81', '81', '自杀死亡', '-1', '', '1', '', '1', '81');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('89', '89', '其他原因死亡', '-1', '', '1', '', '1', '89');

insert into T_CODE_CHANGE_REASON (ID, CODE_, NAME_, PID, PATH_, LEVEL_, LEVEL_TYPE, ISTRUE, ORDER_)
values ('99', '99', '其他', '-1', '', '1', '', '1', '99');

/*==============================================================*/
/* Table: T_CODE_CAMPUS    校区表                                     */
/*==============================================================*/
create table T_CODE_CAMPUS  (
   ID                   VARCHAR2(20)                    not null,
   NAME_                VARCHAR2(30)                    not null,
   ADDRESS              VARCHAR2(100),
   POSTALCODE           VARCHAR2(20),
   PHONE                VARCHAR2(20),
   FAXES                VARCHAR2(20),
   PRINCIPAL            VARCHAR2(20),
   ISTRUE               NUMBER(1)                       not null,
   ORDER_               NUMBER(4),
   constraint PK_T_CODE_CAMPUS primary key (ID)
);
comment on table T_CODE_CAMPUS is
'学校-校区';
/*==============================================================*/
/* Table: T_CODE_CAMPUS_CLASS      校区-班级表                             */
/*==============================================================*/
create table T_CODE_CAMPUS_CLASS  (
   ID                   VARCHAR2(20)                    not null,
   CAMPUS_ID            VARCHAR2(20)                    not null,
   CLASS_ID             VARCHAR2(20)                    not null,
   constraint PK_T_CODE_CAMPUS_CLASS primary key (ID)
);
comment on table T_CODE_CAMPUS_CLASS is
'学校-校区-班级';

-- 奖学金 修改奖学金代码字段长度
-- alter table T_STU_AWARD modify AWARD_CODE VARCHAR2(20);

-- 助学金 修改助学金代码字段长度
-- alter table T_STU_SUBSIDY modify SUBSIDY_CODE VARCHAR2(20);

/*==============================================================*/
/* Table: T_STU_LOAN      助学贷款表                                      */
/*==============================================================*/
create table T_STU_LOAN  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(9),
   MONEY                NUMBER(8,2),
   LOAN_CODE            VARCHAR2(20),
   DATE_                VARCHAR2(10),
   constraint PK_T_STU_LOAN primary key (ID)
);
comment on table T_STU_LOAN is
'学生-助学贷款';
-- 添加 标准代码 助学贷款
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '国家助学贷款', 'GB', 'LOAN_CODE', '助学贷款类型', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '生源地信用贷款', 'GB', 'LOAN_CODE', '助学贷款类型', '1', '2');

/*==============================================================*/
/* Table: T_STU_JM     减免表                                        */
/*==============================================================*/
create table T_STU_JM  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(9),
   MONEY                NUMBER(8,2),
   JM_CODE              VARCHAR2(20),
   DATE_                VARCHAR2(10),
   constraint PK_T_STU_JM primary key (ID)
);
comment on table T_STU_JM is
'学生-学费减免';
-- 添加 标准代码 学费减免
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '减', 'GB', 'JM_CODE', '减免类型', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '免', 'GB', 'JM_CODE', '减免类型', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '缓', 'GB', 'JM_CODE', '减免类型', '1', '3');
/*==============================================================*/
/* Table: T_STU_WARNING_DEGREE      学生预警-学位预警                            */
/*==============================================================*/
create table T_STU_WARNING_DEGREE  (
   STU_ID               VARCHAR2(20)                    not null,
   CREDITS              NUMBER(8,2),
   constraint PK_T_STU_WARNING_DEGREE primary key (STU_ID)
);
comment on table T_STU_WARNING_DEGREE is
'学生预警-无学位证';
/*==============================================================*/
/* Table: T_STU_WARNING_GRADUATION     学生预警-毕业预警                         */
/*==============================================================*/
create table T_STU_WARNING_GRADUATION  (
   STU_ID               VARCHAR2(20)                    not null,
   CREDITS_ALL          NUMBER(8,2),
   CREDITS_NOT_PASS     NUMBER(8,2),
   constraint PK_T_STU_WARNING_GRADUATION primary key (STU_ID)
);
comment on table T_STU_WARNING_GRADUATION is
'学生预警-无毕业证';
-- 添加编码 费用类型
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '学费', 'XB', 'CHARGE_CODE', '费用类型', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '住宿费', 'XB', 'CHARGE_CODE', '费用类型', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '教材费', 'XB', 'CHARGE_CODE', '费用类型', '1', '3');
-- 更新代码 违纪类别代码
update t_code t set t.codetype_name='违纪类别代码' where t.code_type = 'VIOLATE_CODE';
-- 添加代码 处分类型代码
delete from t_code t where t.code_type = 'PUNISH_CODE';
insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('1', '警告', 'XG', 'PUNISH_CODE', '处分类型代码', '1', '1');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('2', '严重警告', 'XG', 'PUNISH_CODE', '处分类型代码', '1', '2');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('3', '记过', 'XG', 'PUNISH_CODE', '处分类型代码', '1', '3');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('4', '留校察看', 'XG', 'PUNISH_CODE', '处分类型代码', '1', '4');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('5', '自动退学', 'XG', 'PUNISH_CODE', '处分类型代码', '1', '5');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('6', '开除学籍', 'XG', 'PUNISH_CODE', '处分类型代码', '1', '6');

insert into t_code (CODE_, NAME_, CODE_CATEGORY, CODE_TYPE, CODETYPE_NAME, ISTRUE, ORDER_)
values ('9', '其他', 'XG', 'PUNISH_CODE', '处分类型代码', '1', '9');
-- 更新 违纪处分表历史数据（代码根据国标创建）
-- update t_stu_punish t set t.punish_code = substr(t.punish_code,2,1),t.violate_code = substr(t.violate_code,2,1);
/*==============================================================*/
/* Table: T_STU_PUNISH            学生-违纪处分                              */
/*==============================================================*/
create table T_STU_PUNISH  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   VIOLATE_ID           VARCHAR2(20),
   VIOLATE_DATE         VARCHAR2(10),
   VIOLATE_REASON       VARCHAR2(200),
   PUNISH_CODE          VARCHAR2(20),
   PUNISH_DATE          VARCHAR2(10),
   PUNISH_REASON        VARCHAR2(200),
   PUNISH_WH            VARCHAR2(60),
   PUNISH_REVOKE_DATE   VARCHAR2(10),
   PUNISH_REVOKE_WH     VARCHAR2(60),
   constraint PK_T_STU_PUNISH primary key (ID)
);
comment on table T_STU_PUNISH is
'学生-违纪处分';
/*==============================================================*/
/* Table: T_CODE_VIOLATE          违纪类型编码                              */
/*==============================================================*/
create table T_CODE_VIOLATE  (
   ID                   VARCHAR2(20)                    not null,
   CODE_                VARCHAR2(60)                    not null,
   NAME_                VARCHAR2(100)                   not null,
   PID                  VARCHAR2(20)                    not null,
   PATH_                VARCHAR2(200),
   LEVEL_               NUMBER(2),
   LEVEL_TYPE           VARCHAR2(20),
   ISTRUE               NUMBER(1),
   ORDER_               NUMBER(4),
   constraint PK_T_CODE_VIOLATE primary key (ID)
);
comment on table T_CODE_VIOLATE is
'违纪类型编码';
/*==============================================================*/
/* Table: T_STU_WARNING_NOTSTAY             考勤预警-未住宿                    */
/*==============================================================*/
create table T_STU_WARNING_NOTSTAY  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20),
   CLASS_ID             VARCHAR2(20),
   DATE_                VARCHAR2(10),
   SCHOOL_YEAR          VARCHAR2(10),
   TERM_CODE            VARCHAR2(10),
   constraint PK_T_STU_WARNING_NOTSTAY primary key (ID)
);
comment on table T_STU_WARNING_NOTSTAY is
'考勤预警-未住宿';
/*==============================================================*/
/* Table: T_STU_WARNING_SKIP_CLASSES         考勤预警-疑似翘课                   */
/*==============================================================*/
create table T_STU_WARNING_SKIP_CLASSES  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20),
   CLASS_ID             VARCHAR2(20),
   DATE_                VARCHAR2(10),
   COURSE_ARRANGEMENT_ID VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(10),
   TERM_CODE            VARCHAR2(10),
   constraint PK_T_STU_WARNING_SKIP_CLASSES primary key (ID)
);
comment on table T_STU_WARNING_SKIP_CLASSES is
'考勤预警-疑似翘课';
/*==============================================================*/
/* Table: T_STU_WELCOME          新生基本信息                              */
/*==============================================================*/
-- Create table
create table T_STU_WELCOME
(
  ID                  VARCHAR2(20),
  NO_                 VARCHAR2(20) not null,
  EXAMINEE_NO         VARCHAR2(30),
  NAME_               VARCHAR2(60),
  FOMER_NAME          VARCHAR2(60),
  ENGLISH_NAME        VARCHAR2(60),
  BIRTHDAY            VARCHAR2(10),
  IDNO                VARCHAR2(18),
  SEX_CODE            VARCHAR2(10),
  NATION_CODE         VARCHAR2(10),
  POLITICS_CODE       VARCHAR2(10),
  ANMELDEN_CODE       VARCHAR2(10),
  MARRIED             NUMBER(1),
  DEPT_ID             VARCHAR2(20),
  MAJOR_ID            VARCHAR2(20),
  CLASS_ID            VARCHAR2(20),
  LENGTH_SCHOOLING    NUMBER(1) not null,
  BEFORE_EDU_ID       VARCHAR2(20),
  EDU_ID              VARCHAR2(20),
  DEGREE_ID           VARCHAR2(20),
  TRAINING_ID         VARCHAR2(20),
  TRAINING_LEVEL_CODE VARCHAR2(10),
  RECRUIT_CODE        VARCHAR2(10),
  LEARNING_STYLE_CODE VARCHAR2(10),
  STU_FROM_CODE       VARCHAR2(10),
  STU_CATEGORY_ID     VARCHAR2(20),
  ENROLL_DATE         VARCHAR2(10),
  ENROLL_GRADE        VARCHAR2(4) not null,
  STU_STATE_CODE      VARCHAR2(10),
  STU_ROLL_CODE       VARCHAR2(10),
  STU_ORIGIN_ID       VARCHAR2(20),
  PLACE_DOMICILE      VARCHAR2(300),
  SCHOOLTAG           VARCHAR2(300),
  GATQ_CODE           VARCHAR2(10),
  LEARNING_FORM_CODE  VARCHAR2(10),
  ISREGISTER          NUMBER(1)
)
comment on column T_STU_WELCOME.ISREGISTER
  is '1已报到，0未报到';
/*==============================================================*/
/* Table: T_STU_POOR          贫困生信息                              */
/*==============================================================*/
-- Create table
create table T_STU_POOR
(
  ID          VARCHAR2(20) not null,
  STU_ID      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9)
);
comment on table T_STU_POOR is
'贫困生信息';
------------------------------------------------
--------学生信息表(T_STU)插入学习形式字段-----
alter table T_STU add LEARNING_FORM_CODE varchar(10);
/*==============================================================*/
/* Table: T_STU_CHARGE               学生-缴费信息表                           */
/*==============================================================*/
create table T_STU_CHARGE  (
   ID                   VARCHAR2(20)                    not null,
   STU_ID               VARCHAR2(20)                    not null,
   SCHOOL_YEAR          VARCHAR2(9)                     not null,
   TERM_CODE            VARCHAR2(10)                    not null,
   CHARGE_CODE          VARCHAR2(10),
   PAYABLE_MONEY        NUMBER(8,2),
   PAID_IN_MONEY        NUMBER(8,2),
   RETURNS_MONEY        NUMBER(8,2),
   DERATE_MONEY         NUMBER(8,2),
   ARREAR_MONEY         NUMBER(8,2),
   DATE_                VARCHAR2(10),
   constraint PK_T_STU_CHARGE primary key (ID)
);
comment on table T_STU_CHARGE is
'学生-缴费信息表';


/*==============================================================*/
/* Table: T_STU_ABNORMAL_MAIL          高低消费发送信息表                      */
/*==============================================================*/
create table T_STU_ABNORMAL_MAIL
(
  ID          VARCHAR2(20),
  NAME_       VARCHAR2(200),
  SEND_DATE   VARCHAR2(10),
  DEPT_ID     VARCHAR2(20),
  STATUS_     NUMBER(1),
  TYPE_       VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  TERM_CODE   VARCHAR2(10),
  WEEKTH      NUMBER(2),
  WEEK_START  VARCHAR2(10),
  WEEK_END    VARCHAR2(10)
);
-- Add comments to the columns 
comment on column T_STU_ABNORMAL_MAIL.NAME_
  is '邮件名称';
comment on column T_STU_ABNORMAL_MAIL.SEND_DATE
  is '发送日期';
comment on column T_STU_ABNORMAL_MAIL.DEPT_ID
  is '部门id';
comment on column T_STU_ABNORMAL_MAIL.STATUS_
  is '发送状态';
comment on column T_STU_ABNORMAL_MAIL.TYPE_
  is '高消费或低消费';
comment on column T_STU_ABNORMAL_MAIL.SCHOOL_YEAR
  is '学年';
comment on column T_STU_ABNORMAL_MAIL.TERM_CODE
  is '学期';
comment on column T_STU_ABNORMAL_MAIL.WEEKTH
  is '周次';
comment on column T_STU_ABNORMAL_MAIL.WEEK_START
  is '周开始日期';
comment on column T_STU_ABNORMAL_MAIL.WEEK_END
  is '周结束日期';
  ------------------------------------------------
/*==============================================================*/
/* Table: T_STU_ABNORMAL_MEAL          每个学生每餐消费金额表                    */
/*==============================================================*/
  -- Create table
create table T_STU_ABNORMAL_MEAL
(
  ID          VARCHAR2(20),
  STU_ID      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  TERM_CODE   VARCHAR2(10),
  DATE_       VARCHAR2(10),
  MEAL_NAME   VARCHAR2(20),
  SUM_MONEY   NUMBER(10,2)
);
-- Add comments to the columns 
comment on column T_STU_ABNORMAL_MEAL.STU_ID
  is '学生id';
comment on column T_STU_ABNORMAL_MEAL.SCHOOL_YEAR
  is '学年';
comment on column T_STU_ABNORMAL_MEAL.TERM_CODE
  is '学期';
comment on column T_STU_ABNORMAL_MEAL.DATE_
  is '日期';
comment on column T_STU_ABNORMAL_MEAL.MEAL_NAME
  is '餐别名称';
comment on column T_STU_ABNORMAL_MEAL.SUM_MONEY
  is '总消费金额';
    ------------------------------------------------
/*==============================================================*/
/* Table: T_STU_ABNORMAL_TERM          每个学生每学期消费金额表                    */
/*==============================================================*/
  -- Create table
  -- Create table
create table T_STU_ABNORMAL_TERM
(
  ID          VARCHAR2(20),
  STU_ID      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  TERM_CODE   VARCHAR2(10),
  SUM_MONEY   NUMBER(10,2)
);
-- Add comments to the columns 
comment on column T_STU_ABNORMAL_TERM.STU_ID
  is '学生id';
comment on column T_STU_ABNORMAL_TERM.SCHOOL_YEAR
  is '学年';
comment on column T_STU_ABNORMAL_TERM.TERM_CODE
  is '学期';
comment on column T_STU_ABNORMAL_TERM.SUM_MONEY
  is '总消费金额';
    ------------------------------------------------
/*==============================================================*/
/* Table: T_STU_ABNORMAL_WEEK         每个学生每周消费金额表                    */
/*==============================================================*/
create table T_STU_ABNORMAL_WEEK
(
  ID          VARCHAR2(20),
  STU_ID      VARCHAR2(20),
  START_DATE  VARCHAR2(10),
  END_DATE    VARCHAR2(10),
  WEEKTH      NUMBER(2),
  SUM_MONEY   NUMBER(10,2),
  SCHOOL_YEAR VARCHAR2(9),
  TERM_CODE   VARCHAR2(10)
);
-- Add comments to the columns 
comment on column T_STU_ABNORMAL_WEEK.STU_ID
  is '学生ID';
comment on column T_STU_ABNORMAL_WEEK.START_DATE
  is '开始日期';
comment on column T_STU_ABNORMAL_WEEK.END_DATE
  is '结束日期';
comment on column T_STU_ABNORMAL_WEEK.WEEKTH
  is '周次';
comment on column T_STU_ABNORMAL_WEEK.SUM_MONEY
  is '消费金额';
comment on column T_STU_ABNORMAL_WEEK.SCHOOL_YEAR
  is '学年';
comment on column T_STU_ABNORMAL_WEEK.TERM_CODE
  is '学期';
    ------------------------------------------------
/*==============================================================*/
/* Table: T_STU_ABNORMAL_YEAR         每个学生每学年消费金额表                    */
/*==============================================================*/
create table T_STU_ABNORMAL_YEAR
(
  ID          VARCHAR2(20),
  STU_ID      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  SUM_MONEY   NUMBER(10,2)
);
-- Add comments to the columns 
comment on column T_STU_ABNORMAL_YEAR.STU_ID
  is '学生id';
comment on column T_STU_ABNORMAL_YEAR.SCHOOL_YEAR
  is '学年';
comment on column T_STU_ABNORMAL_YEAR.SUM_MONEY
  is '总消费金额';

/*==============================================================*/
/* Table: T_STU_WARNING_MAIL_STATUS        学生考勤预警邮件状态                    */
/*==============================================================*/
create table T_STU_WARNING_MAIL_STATUS
(
  ID      VARCHAR2(20),
  DEPT_ID VARCHAR2(20),
  DATE_   VARCHAR2(10),
  STATUS  NUMBER(1),
  TIME_   VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
/*==============================================================*/
/* Table: T_EMAIL       各单位接受邮件的邮箱信息表                  */
/*==============================================================*/
create table T_EMAIL
(
  ID    VARCHAR2(20),
  EMAIL VARCHAR2(20)
);

-- 添加字段 学期时间表.结束日期字段
alter table t_school_start add (END_DATE varchar2(10) );

/*==============================================================*/
/* Table: T_STU_SCORE_MODE_DATA_BEH       挂科预测页面--用于建模型的原始数据表                 */
/*==============================================================*/
create table T_STU_SCORE_MODE_DATA_BEH
(
  STU_ID      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  TEAM_CODE   VARCHAR2(10),
  MAJOR_ID    VARCHAR2(20),
  COURSE_ID   VARCHAR2(20),
  TK_COUNT    NUMBER(8),
  GRADE_ID    VARCHAR2(8),
  SCORE       NUMBER(8,2),
  DATE_       VARCHAR2(30)
)
/*==============================================================*/
/* Table: T_STU_SCORE_MODE_PREDICT_BEH       挂科预测页面--用于存储模型的表(回归方程表) */
/*==============================================================*/
create table T_STU_SCORE_MODE_PREDICT_BEH
(
  MAJOR_ID           VARCHAR2(20),
  GRADE_ID           VARCHAR2(4),
  COURSE_ID          VARCHAR2(10),
  B_CONSTANT         NUMBER(8,2),
  A_SKIP_CLASS_COUNT NUMBER(8,2),
  A_A2               NUMBER(8,2),
  A_A3               NUMBER(8,2),
  A_A4               NUMBER(8,2),
  A_A5               NUMBER(8,2),
  A_A6               NUMBER(8,2),
  DATE_              VARCHAR2(30)
)
/*==============================================================*/
/* Table: T_STU_SCORE_PREDICT_BEH       挂科预测页面--用于存储预测成绩的表                   */
/*==============================================================*/
create table T_STU_SCORE_PREDICT_BEH
(
  STU_ID        VARCHAR2(20),
  SCHOOL_YEAR   VARCHAR2(9),
  TERM_CODE     VARCHAR2(10),
  PREDICT_SCORE NUMBER(8,2),
  GRADE_ID      VARCHAR2(4),
  COURSE_ID     VARCHAR2(20),
  DATE_         VARCHAR2(30),
  TK_COUNT      NUMBER(8),
  ACCURACY      VARCHAR2(4)
)
/*==============================================================*/
/* Table: T_STU_SCORE_PREDICT_BEH_HIS       挂科预测页面--用于存储以前预测成绩的表(历史预测表)                   */
/*==============================================================*/
create table T_STU_SCORE_PREDICT_BEH_HIS
(
  STU_ID        VARCHAR2(20),
  SCHOOL_YEAR   VARCHAR2(9),
  TERM_CODE     VARCHAR2(10),
  PREDICT_SCORE NUMBER(8,2),
  GRADE_ID      VARCHAR2(4),
  COURSE_ID     VARCHAR2(20),
  DATE_         VARCHAR2(30),
  TK_COUNT      NUMBER(8),
  ACCURACY      VARCHAR2(4)
)
/*==============================================================*/
/* Table: T_STU_ABNORMAL_WEEK_RESULT       高低消费周维度结果表                  */
/*==============================================================*/
-- Create table 添加时间  2016-09-23
create table T_STU_ABNORMAL_WEEK_RESULT
(
  ID          VARCHAR2(20),
  STU_ID      VARCHAR2(20),
  SCHOOL_YEAR VARCHAR2(9),
  TERM_CODE   VARCHAR2(10),
  WEEKTH      NUMBER(10),
  TYPE_       VARCHAR2(40)
);
-- Add comments to the table 
comment on table T_STU_ABNORMAL_WEEK_RESULT
  is '存储学生每周高低消费的人数';

-- 2016-10-14
/*==============================================================*/
/* Table: T_STU_WARNING_STAY_LATE       晚勤晚归                  */
/*==============================================================*/
create table T_STU_WARNING_STAY_LATE
(
  ID          VARCHAR2(20) not null,
  STU_ID      VARCHAR2(20),
  CLASS_ID    VARCHAR2(20),
  DATE_       VARCHAR2(10),
  SCHOOL_YEAR VARCHAR2(10),
  TERM_CODE   VARCHAR2(10),
  BACKTIME    VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_STU_WARNING_STAY_LATE
  add constraint PK_T_STU_WARNING_STAY_LATE primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
/*==============================================================*/
/* Table: T_STU_WARNING_STAY_NOTIN       疑似不在校                  */
/*==============================================================*/
create table T_STU_WARNING_STAY_NOTIN
(
  ID          VARCHAR2(20) not null,
  STU_ID      VARCHAR2(20),
  CLASS_ID    VARCHAR2(20),
  DATE_       VARCHAR2(10),
  SCHOOL_YEAR VARCHAR2(10),
  TERM_CODE   VARCHAR2(10),
  LASTTIME    VARCHAR2(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_STU_WARNING_STAY_NOTIN
  add constraint PK_T_STU_WARNING_STAY_NOTIN primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
/*==============================================================*/
/* Table: T_STU_ABNORMAL_MAIL_MONTH      高低消费-发送信息-月维度          2016-12-08             */
/*==============================================================*/
create table T_STU_ABNORMAL_MAIL_MONTH  (
   ID                   VARCHAR2(20),
   NAME_                VARCHAR2(200),
   SEND_DATE            VARCHAR2(10),
   DEPT_ID              VARCHAR2(20),
   STATUS_              NUMBER(1),
   TYPE_                VARCHAR2(20),
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   MONTH                NUMBER(2)
);
comment on table T_STU_ABNORMAL_MAIL_MONTH is
'存储发送状态，发送日期，发送的文件名等信息';
/*==============================================================*/
/* Table: T_STU_COST_TIME_MONTH          高低消费-月维度-每月时间存储       2016-12-08                     */
/*==============================================================*/
create table T_STU_COST_TIME_MONTH  (
   ID                   VARCHAR2(20),
   START_DATE           VARCHAR2(10),
   END_DATE             VARCHAR2(10),
   MONTH                NUMBER(10),
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10),
   DAYS                 NUMBER(10)
);
comment on table T_STU_COST_TIME_MONTH is
'存储高低消费每月的时间信息';

-- 2016-12-08
/*==============================================================*/
/* Table: T_STU_ABNORMAL_MONTH     高低消费-月维度-学生消费金额                                 */
/*==============================================================*/
create table T_STU_ABNORMAL_MONTH  (
   ID                   VARCHAR2(20),
   STU_ID               VARCHAR2(20),
   MONTH                NUMBER(10),
   SUM_MONEY            NUMBER(10,2),
   SCHOOL_YEAR          VARCHAR2(9),
   TERM_CODE            VARCHAR2(10)
);
comment on table T_STU_ABNORMAL_MONTH is
'存储每月学生消费金额';
/*==============================================================*/
/* Table: T_STU_ABNORMAL_MONTH_RESULT       学生异常行为结果表-月维度                         */
/*==============================================================*/
create table T_STU_ABNORMAL_MONTH_RESULT  (
   ID                   VARCHAR(20),
   STU_ID               VARCHAR(20),
   SCHOOL_YEAR          VARCHAR(9),
   TERM_CODE            VARCHAR(10),
   MONTH                NUMBER(10),
   TYPE_                VARCHAR(40),
   SUM_MONEY            NUMBER(10,2)
);
comment on table T_STU_ABNORMAL_MONTH_RESULT is
'存储学生每月高低消费的人数';
--2016-12-14
/*==============================================================*/
/* Table: T_STU_ABNORMAL_MONTH_RESULT       学生异常行为结果表-月维度                         */
/*==============================================================*/
create table T_STU_COST_STANDARD
(
  SCHOOL_YEAR   VARCHAR2(9),
  TERM_CODE     VARCHAR2(10),
  HIGH_STANDARD NUMBER(10,2),
  LOW_STANDARD  NUMBER(10,2),
  TYPE_         VARCHAR2(20),
  ID            VARCHAR2(20) not null
);
-- Add comments to the table 
comment on table T_STU_COST_STANDARD
  is '存储每学年学期不同维度的高低消费标准';
--2016-12-19
--T_STU_ABNORMAL_TERM表增加字段 
alter table T_STU_ABNORMAL_TERM add BREAKFAST_ NUMBER(10);
alter table T_STU_ABNORMAL_TERM add LUNCH_ NUMBER(10);
alter table T_STU_ABNORMAL_TERM add DINNER_ NUMBER(10);
--T_STU_COST_STANDARD表增加字段
alter table T_STU_COST_STANDARD add START_ONE VARCHAR2(10);
alter table T_STU_COST_STANDARD add END_ONE VARCHAR2(10);
alter table T_STU_COST_STANDARD add START_TWO VARCHAR2(10);
alter table T_STU_COST_STANDARD add END_TWO VARCHAR2(10);
alter table T_STU_COST_STANDARD add HIGH_ALG_NAME VARCHAR2(60);
alter table T_STU_COST_STANDARD add LOW_ALG_NAME VARCHAR2(60);
alter table T_STU_COST_STANDARD add HIGH_ALG VARCHAR2(10);
alter table T_STU_COST_STANDARD add LOW_ALG VARCHAR2(10);
--2016-12-23 
--新增研究生表
/*==============================================================*/
/* Table: T_STU_GRADUATE_STU                         */
/*==============================================================*/
create table T_STU_GRADUATE_STU  (
   NO_                  VARCHAR2(20)                    not null,
   EXAMINEE_NO          VARCHAR2(30),
   NAME_                VARCHAR2(60),
   FOMER_NAME           VARCHAR2(60),
   ENGLISH_NAME         VARCHAR2(60),
   BIRTHDAY             VARCHAR2(10),
   IDNO                 VARCHAR2(18),
   SEX_CODE             VARCHAR2(10),
   NATION_CODE          VARCHAR2(10),
   POLITICS_CODE        VARCHAR2(10),
   ANMELDEN_CODE        VARCHAR2(10),
   MARRIED              NUMBER(1),
   DEPT_ID              VARCHAR2(20),
   MAJOR_ID             VARCHAR2(20),
   CLASS_ID             VARCHAR2(20),
   LENGTH_SCHOOLING     NUMBER(1)                       not null,
   BEFORE_EDU_ID        VARCHAR2(20),
   EDU_ID               VARCHAR2(20),
   DEGREE_ID            VARCHAR2(20),
   TRAINING_ID          VARCHAR2(20),
   TRAINING_LEVEL_CODE  VARCHAR2(10),
   RECRUIT_CODE         VARCHAR2(10),
   STU_FROM_CODE        VARCHAR2(10),
   STU_CATEGORY_ID      VARCHAR2(20),
   ENROLL_DATE          VARCHAR2(10)                    not null,
   ENROLL_GRADE         VARCHAR2(4)                     not null,
   STU_STATE_CODE       VARCHAR2(10),
   STU_ROLL_CODE        VARCHAR2(10),
   STU_ORIGIN_ID        VARCHAR2(20),
   PLACE_DOMICILE       VARCHAR2(300),
   SCHOOLTAG            VARCHAR2(300),
   GATQ_CODE            VARCHAR2(10),
   LEARNING_FORM_CODE   VARCHAR2(10),
   GRADUATE_FOSTER_DIRECTION_CODE VARCHAR2(10),
   GRADUATE_ENROLL_CATEGORY_CODE VARCHAR2(10),
   GRADUATE_LEARNING_STYLE_CODE VARCHAR2(10),
   constraint PK_T_STU_GRADUATE_STU primary key (NO_)
);
-- 疑似逃课表、疑似不在校表 添加字段 学年、学期         20160822
-- alter table T_STU_WARNING_SKIP_CLASSES add (SCHOOL_YEAR VARCHAR2(10) );
-- alter table T_STU_WARNING_SKIP_CLASSES add (TERM_CODE VARCHAR2(10) );
-- alter table T_STU_WARNING_NOTSTAY add (SCHOOL_YEAR VARCHAR2(10) );
-- alter table T_STU_WARNING_NOTSTAY add (TERM_CODE VARCHAR2(10) );
