package com.jhnu.product.wechat.parent.menu.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.menu.dao.WechatMenuDao;
import com.jhnu.product.wechat.parent.menu.entity.WechatMenu;

@Repository("wechatMenuDao")
public class WechatMenuDaoImpl implements WechatMenuDao{
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<WechatMenu> getWechatMenuByThis(WechatMenu wechatMenu) {
		String sql="select * from t_wechat_menu where 1=1 ";
		if(wechatMenu != null){
			if(wechatMenu.getId() != null){
				sql+=" and id="+wechatMenu.getId();
			}
			if(wechatMenu.getUrl_() != null){
				sql+=" and url_='"+wechatMenu.getUrl_()+"'";
			}
			if(wechatMenu.getName_()!=null && !wechatMenu.getName_().equals("")){
				sql+=" and name_ like '%"+wechatMenu.getName_()+"%' ";
			}
			if(wechatMenu.getIstrue()!=null){
				sql+=" and istrue="+wechatMenu.getIstrue();
			}
			if(wechatMenu.getKeyWord()!=null && !wechatMenu.getKeyWord().equals("")){
				//TODO 关键字筛选没有完成
			}
			if(wechatMenu.getLevel_()!=null){
				sql+=" and level_="+wechatMenu.getLevel_();
			}
			if(wechatMenu.getPid()!=null){
				sql+=" and pid="+wechatMenu.getPid();
			}
			if(wechatMenu.getCode()!=null){
				sql+=" and code='"+wechatMenu.getCode()+"' ";
			}
			if(wechatMenu.getPath()!=null && !wechatMenu.getPath().equals("")){
				sql+=" and path like '"+wechatMenu.getPath()+"%' ";
			}
		}
		sql+=" order by order_";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(WechatMenu.class));
	}


	@Override
	public WechatMenu saveWechatMenu(WechatMenu wechatMenu) {
		//TODO 保存菜单
		return wechatMenu;
	}

	@Override
	public WechatMenu updateWechatMenu(WechatMenu wechatMenu) {
		//TODO 更新菜单
		return wechatMenu;
	}

	@Override
	public void deleteWechatMenuById(Long id) {
		String sql="delete t_wechat_menu where id=? ";
		baseDao.getBaseDao().getJdbcTemplate().update(sql,id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public WechatMenu getOneWechatMenuByThis(WechatMenu wechatMenu) {
		String sql="select * from t_wechat_menu where 1=1 ";
		if(wechatMenu != null){
			if(wechatMenu.getId() != null){
				sql+=" and id="+wechatMenu.getId();
			}
			if(wechatMenu.getUrl_() != null && !wechatMenu.getUrl_().equals("")){
				sql+=" and url_='"+wechatMenu.getUrl_()+"'";
			}
			if(wechatMenu.getIstrue()!=null){
				sql+=" and istrue="+wechatMenu.getIstrue();
			}
			if(wechatMenu.getLevel_()!=null){
				sql+=" and level_="+wechatMenu.getLevel_();
			}
			if(wechatMenu.getCode()!=null && !wechatMenu.getCode().equals("")){
				sql+=" and code='"+wechatMenu.getCode()+"' ";
			}
		}
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, (RowMapper<WechatMenu>)new BeanPropertyRowMapper(WechatMenu.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatMenu> getAllWechatMenus() {
		String sql="select * from t_wechat_menu start with id=0 connect by prior id=pid ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper<WechatMenu>)new BeanPropertyRowMapper(WechatMenu.class));
	}
	
}
