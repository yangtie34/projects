package cn.gilight.personal.social.lost.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.social.lost.service.MineService;

@Service("amineService")
public class MineServiceImpl implements MineService{
	
	@Resource
	private BaseDao baseDao;
	
	/**
	 * 根据username查询文章(我的文章)
	 */
	
	@Override
	public List<Map<String, Object>> queryMyTopic(String username) {
		String sql="select t.id,t.username,ts.name_,t.lostfound_type_code,t.message,t.tel_,t.image_url,t.creat_time,t.is_solve "
				  +" from t_lostfound t "
				  +" left join t_stu ts on ts.no_=t.username "
				  +" where username='"+username+"'order by creat_time desc";
		return baseDao.queryListInLowerKey(sql);
	}
	
	/**
	 * 根据id删除文章
	 */
	@Override
	public void deleteTopic(String id) {
		String sql ="delete from t_lostfound where id='"+id+"'";
		baseDao.delete(sql);
	}

	/**
	 * 修改状态
	 */
	@Override
	public void modifyStatus(String id) {
		String sql="update t_lostfound  set is_solve ='1' where id = '"+id+"'";
		baseDao.update(sql);
	}
}
