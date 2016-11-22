package com.jhnu.system.menu.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.menu.dao.MenuDao;
import com.jhnu.system.menu.entity.Menu;

@Repository("menuDao")
public class MenuDaoImpl implements MenuDao{
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Menu> getMenuByThis(Menu menu) {
		String sql="select * from t_sys_menu where 1=1 ";
		if(menu != null){
			if(menu.getId() != null){
				sql+=" and id="+menu.getId();
			}
			if(menu.getUrl_() != null){
				sql+=" and url_='"+menu.getUrl_()+"'";
			}
			if(menu.getName_()!=null && !menu.getName_().equals("")){
				sql+=" and name_ like '%"+menu.getName_()+"%' ";
			}
			if(menu.getIstrue()!=null){
				sql+=" and istrue="+menu.getIstrue();
			}
			if(menu.getKeyWord()!=null && !menu.getKeyWord().equals("")){
				//TODO 关键字筛选没有完成
			}
			if(menu.getLevel_()!=null){
				sql+=" and level_="+menu.getLevel_();
			}
			if(menu.getPid()!=null){
				sql+=" and pid="+menu.getPid();
			}
			if(menu.getCode()!=null){
				sql+=" and code='"+menu.getCode()+"' ";
			}
			if(menu.getPath()!=null && !menu.getPath().equals("")){
				sql+=" and path like '"+menu.getPath()+"%' ";
			}
		}
		sql+=" order by order_";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper) new BeanPropertyRowMapper(Menu.class));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Menu> getMenuByThis(Menu menu, Long userId) {
		String sql="select * from t_sys_menu where id in ( "+
					"select distinct id from ( "+
					"select m.id from t_sys_menu m  "+
					"inner join t_sys_menu_perm mp on m.id=mp.menu_id "+
					"inner join t_sys_perm p on mp.perm_id=p.id "+
					"inner join t_sys_role_perm rp on rp.perm_id=p.id "+
					"inner join t_sys_role r on rp.role_id=r.id "+
					"inner join t_sys_user_role ur on ur.role_id=r.id "+
					"where p.istrue=1 and r.istrue=1 and ur.user_id='"+userId+"'  "+
					"union all "+
					"select m.id from t_sys_menu m "+
					"inner join t_sys_menu_perm mp on m.id=mp.menu_id "+
					"inner join t_sys_perm p on mp.perm_id=p.id "+
					"inner join t_sys_user_perm up on up.perm_id=p.id "+
					"where p.istrue=1 and up.user_id='"+userId+"' "+
					") ) ";
		//TODO 测试阶段先查询所有，把权限补全后更改
		sql="select * from t_sys_menu where 1=1 ";
		if(menu != null){
			if(menu.getId() != null){
				sql+=" and id="+menu.getId();
			}
			if(menu.getUrl_() != null && !menu.getUrl_().equals("")){
				sql+=" and url_='"+menu.getUrl_()+"'";
			}
			if(menu.getName_()!=null && !menu.getName_().equals("")){
				sql+=" and name_ like '%"+menu.getName_()+"%' ";
			}
			if(menu.getIstrue()!=null){
				sql+=" and istrue="+menu.getIstrue();
			}
			if(menu.getKeyWord()!=null && !menu.getKeyWord().equals("")){
				//TODO 关键字筛选没有完成
			}
			if(menu.getLevel_()!=null){
				sql+=" and level_="+menu.getLevel_();
			}
			if(menu.getPid()!=null){
				sql+=" and pid="+menu.getPid();
			}
			if(menu.getCode()!=null && !menu.getCode().equals("")){
				sql+=" and code='"+menu.getCode()+"' ";
			}
			if(menu.getPath()!=null && !menu.getPath().equals("")){
				sql+=" and path like '"+menu.getPath()+"%' ";
			}
		} 
		sql+=" and id <>0 connect by pid=prior id start with id=0 order siblings by order_ ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper<Menu>)new BeanPropertyRowMapper(Menu.class));
	}

	@Override
	public Menu saveMenu(Menu menu) {
		//TODO 保存菜单
		return menu;
	}

	@Override
	public Menu updateMenu(Menu menu) {
		//TODO 更新菜单
		return menu;
	}

	@Override
	public void deleteMenuById(Long id) {
		String sql="delete t_sys_menu where id=? ";
		baseDao.getBaseDao().getJdbcTemplate().update(sql,id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Menu getOneMenuByThis(Menu menu) {
		String sql="select * from t_sys_menu where 1=1 ";
		if(menu != null){
			if(menu.getId() != null){
				sql+=" and id="+menu.getId();
			}
			if(menu.getUrl_() != null && !menu.getUrl_().equals("")){
				sql+=" and url_='"+menu.getUrl_()+"'";
			}
			if(menu.getIstrue()!=null){
				sql+=" and istrue="+menu.getIstrue();
			}
			if(menu.getLevel_()!=null){
				sql+=" and level_="+menu.getLevel_();
			}
			if(menu.getCode()!=null && !menu.getCode().equals("")){
				sql+=" and code='"+menu.getCode()+"' ";
			}
		}
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, (RowMapper<Menu>)new BeanPropertyRowMapper(Menu.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Menu> getAllMenus() {
		String sql="select * from t_sys_menu start with id=0 connect by prior id=pid ";
		return baseDao.getBaseDao().getJdbcTemplate().query(sql, (RowMapper<Menu>)new BeanPropertyRowMapper(Menu.class));
	}
	
}
