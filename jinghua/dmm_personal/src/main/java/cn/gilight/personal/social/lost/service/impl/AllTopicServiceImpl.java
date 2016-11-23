package cn.gilight.personal.social.lost.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.personal.social.lost.service.AllTopicService;

@Service("allTopicService")
public class AllTopicServiceImpl implements AllTopicService{

	@Resource
	private BaseDao baseDao;
	
	//查询所有发文
	@Override
	public Page queryAllTopicByPage(Page page,String keyword,String typeCode) {
		String sql1 = "";
		if(StringUtils.hasText(keyword)){
			sql1 = "and t.message like '%"+keyword+"%'";
		};
		String sql2 = "";
		if(StringUtils.hasText(typeCode)){
			sql2 = "and T.LOSTFOUND_TYPE_CODE = '"+typeCode+"'";
		}
		String sql="SELECT T.USERNAME,TS.NAME_,T.LOSTFOUND_TYPE_CODE,T.MESSAGE,TEL_,T.IMAGE_URL,T.CREAT_TIME,"
				  +" T.IS_SOLVE FROM T_LOSTFOUND T "
				  +" LEFT JOIN T_STU TS ON TS.NO_=T.USERNAME "
				  +" WHERE 1=1"+sql1+sql2+" order by t.creat_time desc";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
}
