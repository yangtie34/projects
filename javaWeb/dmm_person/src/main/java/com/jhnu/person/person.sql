mvn install:install-file -DgroupId=com.jhnu -DartifactId=permiss -Dversion=1.0 -Dpackaging=jar -Dfile=d:\permiss.jar
教师 19901023   123456
学生 201105010037   509764
教师 20122036 123456

--学士信息
--[姓名，学士学号，性别，政治面貌，民族，来校时间，出生日期，院系专业，身份证号，
--班级，户籍地址，学习方式，婚姻状况，学籍状态，联系方式]
select t.name_,t.NO_,
tc1.name_,tc4.name_,
tc2.name_,t.ENROLL_DATE,
t.BIRTHDAY,tcdt1.NAME_,
t.IDNO,tcdt2.NAME_,
t.PLACE_DOMICILE,tct.name_,
CASE t.MARRIED
WHEN 1 THEN '已婚'  
WHEN 0 THEN '未婚'  
ELSE '未维护' END MARRIED,tc3.name_,
tsc.tel
from t_stu t
left join t_code tc1 on tc1.code_ = t.sex_code  and tc1.code_type='SEX_CODE' 
left join t_code tc4 on tc4.code_ = t.politics_code and tc4.code_type='POLITICS_CODE'
left join t_code tc2 on tc2.code_ = t.nation_code  and tc2.code_type='NATION_CODE'
left join T_CODE_DEPT_TEACH tcdt1 on tcdt1.code_ = t.MAJOR_ID 
left join T_CODE_DEPT_TEACH tcdt2 on tcdt2.code_ = t.CLASS_ID 
left join T_CODE_TRAINING tct on tct.code_ = t.TRAINING_ID 
left join t_code tc3 on tc3.code_ = t.STU_ROLL_CODE  and tc2.code_type='STU_ROLL_CODE'
left join T_STU_COMM tsc on tsc.STU_ID = t.NO_ 
where t.NO_='19901023'
--荣誉及奖励
--学年，学期，类型，金额，日期 ，备注
select t.SCHOOL_YEAR,tc1.name_,t.BATCH,t.MONEY,t.DATE_,tc2.name_
from T_STU_GLORY t 
left join t_code tc1 on tc1.code_ = t.sex_code  and tc1.code_type='TERM_CODE' 
left join t_code tc2 on tc2.code_ = t.sex_code  and tc1.code_type='GLORY_CODE' 
where t.stu_id=''
--教师信息
--[姓名,职工编号，性别，政治面貌，民族，来校时间，出生日期，入党时间，身份证号，教职工来源，
--户籍地址，教职工类别，婚姻状况，学历，联系方式，学位，现在部门，教职工状态，用人方式，是否双师教师
--]
select t.name_,t.TEA_NO,
tc1.name_,tc4.name_,
tc2.name_,t.IN_DATE,
t.BIRTHDAY,t.JOIN_PARTY_DATE,
t.IDNO,tcts.name_,
t.PLACE_DOMICILE,tc5.name_,
CASE t.MARRIED
WHEN 1 THEN '已婚'  
WHEN 0 THEN '未婚'  
ELSE '未维护' END MARRIED,tce.name_,
tc.TEL,tcd.name_,
tcdt.name_,tc6.name_,
tc3.name_,	CASE t.SFSSJS
			WHEN 1 THEN '是'  
			WHEN 0 THEN '否'  
			ELSE '未维护' END SFSSJS
from t_tea t
left join t_code tc1 on tc1.code_ = t.sex_code  and tc1.code_type='SEX_CODE' 
left join t_code tc2 on tc2.code_ = t.nation_code  and tc2.code_type='NATION_CODE'
left join T_TEA_COMM tc on tc.TEA_ID = t.TEA_NO 
left join T_CODE_DEPT_TEACH tcdt on tcdt.code_ = t.DEPT_ID 
left join t_code tc3 on tc3.code_ = t.BZLB_CODE  and tc3.code_type='BZLB_CODE'
left join t_code tc4 on tc4.code_ = t.politics_code and tc4.code_type='POLITICS_CODE'
left join T_CODE_TEA_SOURCE tcts on tcts.CODE_ = t.TEA_SOURCE_ID 
left join t_code tc5 on tc5.code_ = t.AUTHORIZED_STRENGTH_CODE and tc5.code_type='AUTHORIZED_STRENGTH_CODE'
left join T_CODE_EDUCATION tce on tce.CODE_ = t.FIRST_EDU_ID 
left join T_CODE_DEGREE tcd on tcd.CODE_ = t.FIRST_DEGREE_ID 
left join t_code tc6 on tc6.code_ = t.TEA_STATE_CODE and tc6.code_type='TEA_STATE_CODE'--教职工状态
where t.TEA_NO='19901023'

--教师职称变动
 select tc1.name_,t.date_ from T_TEA_ZCPD t 
left join t_code tc1 on tc1.code_ = t.ZYJSZW_CODE and tc1.code_type='ZYJSZW_CODE'
where t.TEA_ID='' order by t.date_
--教师岗位变动
 select tc1.name_,t.START_DATE from T_TEA_PERSON_CHANGE t 
left join t_code tc1 on tc1.code_ = t.AFTER_GW_CODE and tc1.code_type='AFTER_GW_CODE'
where t.TEA_ID='' order by t.START_DATE

--工资组成
select tc1.name_,t.VALUE_ from T_TEA_SALARY t
left join t_code tc1 on tc1.code_ = t.AFTER_GW_CODE and tc1.code_type='SALARY_CODE'
where t.TEA_ID='' and t.YEAR_||t.MONTH_ between starttime and endtime


--工资变化
select sum(t.VALUE_ ) value,t.YEAR_||'/'||t.MONTH_ field from T_TEA_SALARY t
--left join t_code tc1 on tc1.code_ = t.AFTER_GW_CODE and tc1.code_type='SALARY_CODE'
where t.TEA_ID='' and t.YEAR_||t.MONTH_ between starttime and endtime 
group by t.YEAR_||'/'||t.MONTH_ order by field
--工资条
select tc1.name_,t.VALUE_ ,t.YEAR_||'/'||t.MONTH_ field from T_TEA_SALARY t
left join t_code tc1 on tc1.code_ = t.AFTER_GW_CODE and tc1.code_type='SALARY_CODE'
where t.TEA_ID='' and t.YEAR_||t.MONTH_ between starttime and endtime 
order by field
--推荐图书
select t.BOOK_ID,tb.NAME_ ,count(t.id) count_ from T_BOOK_BORROW t 
left join T_BOOK tb on tb.ID=t.BOOK_ID
where t.BOOK_READER_ID in(
select TEA_NO from t_tea  where DEPT_ID =(select DEPT_ID from t_tea  where TEA_NO='19901023'))
--and t.BORROW_TIME between starttime and endtime 
and rownum<11
group by t.BOOK_ID ,tb.NAME_ order by count_ desc
--科研信息
--项目，开始时间，项目信息，参与人
select * from T_RES_OUTCOME_APPRAISAL t

select * from t_code where  code_type like'%SALARY_CODE'
select * from T_TEA_ZCPD where TEA_NO='19901023'

//教师用户其他身份
select * from (
select tea_no id,idno,id||'教师' name_ from T_TEA
union all 
select t.no_ id,t.idno,tc.name_  from T_STU t
left join T_CODE_STU_CATEGORY tc on tc.CODE_=t.STU_CATEGORY_ID
) t
inner join T_TEA tt on tt.idno=t.idno and tt.TEA_NO='19901023'

//学生用户其他身份
select * from (
select id,idno,id||'教师' name_ from T_TEA
union all 
select t.id,t.idno,tc.name_  from T_STU t
left join T_CODE_STU_CATEGORY tc on tc.CODE_=t.STU_CATEGORY_ID
) t
inner join T_STU tt on tt.idno=t.idno and tt.no_='19901023'
















































