
select distinct ts.titleid id,
tct.name_ name,
tct.ISCODE, 
CASE tct.ISCODE WHEN 0 THEN ts.value_ ELSE tc.name_ END as value
from  T_EDU_SCHOOL_DETAILS ts
 left join t_edu_code_title tct on tct.code_=ts.titleid 
 left join t_edu_code tc on tc.code_=ts.value_ and tc.code_type=tct.code_type
where ts.schoolid='1001565467';

select * from  t_edu_schools;


select  distinct t.* from t_edu_schools t "
				 left join T_EDU_SCHOOL_DETAILS ts on ts.SCHOOLId=t.id 
			 left join t_edu_code_title tct on tct.code_=ts.titleid 
			 where t.ch_name like'%"+schoolname+"%' 
			 
			select *  from  T_EDU_SCHOOL_DETAILS where titleid= '1211231' and  schoolid='1001565467';