insert into TL_NET_TREND  

select b.Year_Month,b.DEPT_ID,b.MAJOR_ID,b.CLASS_ID,b.SEX_CODE,b.SEX_NAME,
b.EDU_ID,b.EDU_NAME,b.NATION_CODE,b.NATION_NAME,b.ON_TYPE_CODE,b.ON_TYPE_NAME,
nvl(b.all_time,0) all_time ,nvl(b.all_flow,0) all_flow,nvl(b.all_counts,0) 
all_counts,nvl(b.use_stu,0) use_stu,a.all_stu from 
( SELECT * FROM TL_STU_NUM_MONTH ) a left join 
( SELECT t.Year_Month,T.DEPT_ID,T.MAJOR_ID,T.CLASS_ID,T.SEX_CODE,T.SEX_NAME,
T.EDU_ID,T.EDU_NAME,T.NATION_CODE,T.NATION_NAME,T.ON_TYPE_CODE,T.ON_TYPE_NAME,
nvl(sum(t.use_time),0) all_time,
nvl(sum(t.use_flow),0) all_flow,
nvl(sum(t.in_counts),0) all_counts,
nvl(count(distinct t.stu_id),0) use_stu
FROM tl_net_stu_month t where t.stu_id is not null 
group by t.Year_Month,T.DEPT_ID,T.MAJOR_ID,T.CLASS_ID,T.SEX_CODE,T.SEX_NAME,
T.EDU_ID,T.EDU_NAME,T.NATION_CODE,T.NATION_NAME,T.ON_TYPE_CODE,T.ON_TYPE_NAME ) b
on a.year_month=b.year_month and a.dept_id=b.dept_id 
and a.major_id=b.major_id and a.class_id=b.class_id 
and a.sex_code=b.sex_code and a.edu_id=b.edu_id and a.nation_code=b.nation_code
where b.year_month is not null ; 

drop table TL_NET_TREND;
create table TL_NET_TREND
(
  YEAR_MONTH   DATE,
  DEPT_ID      VARCHAR2(100),
  MAJOR_ID     VARCHAR2(100),
  CLASS_ID     VARCHAR2(100),
  SEX_CODE     VARCHAR2(100),
  SEX_NAME     VARCHAR2(100),
  EDU_ID       VARCHAR2(100),
  EDU_NAME     VARCHAR2(100),
  NATION_CODE  VARCHAR2(100),
  NATION_NAME  VARCHAR2(100),
  ON_TYPE_CODE VARCHAR2(100),
  ON_TYPE_NAME VARCHAR2(100),
  ALL_TIME     VARCHAR2(100),
  ALL_FLOW     VARCHAR2(100),
  ALL_COUNTS   VARCHAR2(100),
  USE_STU      VARCHAR2(100),
  ALL_STU      VARCHAR2(100)
)
