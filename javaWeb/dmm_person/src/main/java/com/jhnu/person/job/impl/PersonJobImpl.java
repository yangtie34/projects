package com.jhnu.person.job.impl;

import java.util.Date;

import com.jhnu.framework.entity.JobResultBean;
import com.jhnu.person.job.PersonJob;
import com.jhnu.util.product.EduUtils;


public class PersonJobImpl implements PersonJob{
	
public JobResultBean yktxffxJob(){
	//每天执行 如果表不存在 创建表 如果存在 插入今天统计数据
	//TODO 消费分析排名
			String sql=" create or replace table xffxpm as  "
			 +" select t.* from(  "
			 +" SELECT tc.people_id,SUBSTR(TCP.TIME_, 0, 7) time,SUM(TCP.PAY_MONEY) VALUE "
			  +"  FROM T_CARD_PAY TCP "
			  +"  left join T_CARD TC on tc.CARD_NO = TCP.CARD_ID "
			  +" WHERE SUBSTR(TCP.TIME_, 0, 7) BETWEEN '2001-02-16' and '2016-05-16' "
			   +"  group by tc.people_id )t order by value ";
	return null;
	
}

@Override
public JobResultBean swsjJob() {
	// TODO Auto-generated method stub
	return null;
}
}
