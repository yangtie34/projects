package com.jhkj.mosdc.framework.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.json.JSONObject;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.jhkj.mosdc.framework.dao.DeskTopDao;
import com.jhkj.mosdc.framework.dto.DesktopShortCut;

/***
 * 桌面处理实现Dao实现类
 * 
 * @Comments: Company: 河南精华科技有限公司 Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-29
 * @TIME: 上午10:33:03
 */
public class DeskTopDaoImpl extends BaseDaoImpl implements DeskTopDao {

	/***
	 * 根据用户名查询其对应的快捷方式
	 * 
	 * @param userid
	 */
	@SuppressWarnings("unchecked")
	public List<DesktopShortCut> queryShortCutsByUserId(Long userid) {
		return super.getHibernateTemplate().find(
				" from DesktopShortCut  t where t.userId = " + userid
						+ " order by t.sortnumber");
	}

	/***
	 * 根据快捷方式id删除快捷方式
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteShortCutById(Long id) {
		return super.getJdbcTemplate().update(
				" delete from ts_desktop_shortcut t where t.shortcut_id=" + id) == 1;
	}

	/***
	 * 添加用户的快捷方式
	 * 
	 * @throws Exception
	 */
	public void saveShortCutByUser(DesktopShortCut shortcut) throws Exception {
		shortcut.setId(getId());
		int sortnum = super.getJdbcTemplate().queryForInt(
				"select max(t.sortnumber) from ts_desktop_shortcut t where t.user_id="
						+ shortcut.getUserId());
		shortcut.setSortnumber(sortnum + 1);
		super.getHibernateTemplate().save(shortcut);
	}

	/***
	 * 修改系统图标样式
	 * 
	 * @param params
	 * @return
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public boolean updateSystemIcons(Map<String, String> map) throws Exception {
		String updatesql = "update ts_desktop_menu_icon t set  t.icon = ? where t.node_id=?";
		String insertsql = "insert into ts_desktop_menu_icon(id,node_id,icon) values(?,?,?)";
		String querysql = "select count(*) from ts_desktop_menu_icon t where t.node_id=?";
		for (Entry<String, String> entry : map.entrySet()) {
			Long nodeid = Long.valueOf(entry.getKey());
			String icon = entry.getValue();
			int i = super.getJdbcTemplate().queryForInt(querysql,
					new Object[] { nodeid });
			if (i == 0) {
				super.getJdbcTemplate().update(insertsql,
						new Object[] { getId(), nodeid, icon });
			} else if (i > 0) {
				super.getJdbcTemplate().update(updatesql,
						new Object[] { icon, nodeid });
			}
		}
		return true;
	}

	/***
	 * 查询系统菜单图标样式
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> querySystemIcons() {
		String sql = "select * from ts_desktop_menu_icon";
		return (Map<String, String>) super.getJdbcTemplate().query(sql,
				new ResultSetExtractor() {

					@SuppressWarnings("rawtypes")
					@Override
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						Map map = new HashMap();
						while (rs.next()) {
							map.put(rs.getString("node_id"),
									rs.getString("icon"));
						}
						return map;
					}
				});
	}

	/***
	 * 查询用户桌面墙纸
	 * 
	 * @param userId
	 *            用户id
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> queryUserWallpaper(Long userId) {
		String sql = "select * from ts_desktop_wallpaper t where t.user_id="
				+ userId;
		return ((Map<String, String>) super.getJdbcTemplate().query(sql,
				new ResultSetExtractor() {

					@SuppressWarnings("rawtypes")
					@Override
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						Map map = new HashMap();
						while (rs.next()) {
							map.put("img", rs.getString("img_url"));
							map.put("stretch", rs.getInt("stretch") == 1 ? true
									: false);
						}
						return map;
					}
				}));
	}

	/***
	 * 修改用户桌面墙纸
	 * 
	 * @param userId
	 *            用户id
	 * @param wallpaper
	 * @throws Exception
	 * @throws DataAccessException
	 */
	public boolean updateUserWallpaper(Long userId, JSONObject json)
			throws Exception {
		String updatesql = "update ts_desktop_wallpaper t set  t.img_url = ?,stretch = ? where t.user_id=?";
		String insertsql = "insert into ts_desktop_wallpaper(id,user_id,img_url,stretch) values(?,?,?,?)";
		String querysql = "select count(*) from ts_desktop_wallpaper t where t.user_id=?";
		int i = super.getJdbcTemplate().queryForInt(querysql,
				new Object[] { userId });
		if (i == 0) {
			super.getJdbcTemplate().update(
					insertsql,
					new Object[] { getId(), userId, json.get("img"),
							json.getString("stretch") });
		} else if (i > 0) {
			super.getJdbcTemplate().update(
					updatesql,
					new Object[] { json.get("img"), json.getString("stretch"),
							userId });
		}
		return true;
	}

	/***
	 * 查询用户系统风格
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> queryUserSystemPage(Long userId) {
		String sql = "select * from ts_desktop_user_page t where t.user_id="
				+ userId;
		return ((Map<String, String>) super.getJdbcTemplate().query(sql,
				new ResultSetExtractor() {

					@SuppressWarnings("rawtypes")
					@Override
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						Map map = new HashMap();
						while (rs.next()) {
							map.put("login_page", rs.getString("login_page"));
						}
						if (map.size() == 0)
							map.put("login_page", "testPage.jsp");
						return map;
					}
				}));
	}

	/***
	 * 修改用户系统风格
	 * 
	 * @param json
	 *            参数json对象
	 * @throws Exception
	 * @throws DataAccessException
	 */
	public boolean updateUserSystemPage(JSONObject json, Long userId)
			throws Exception {
		String updatesql = "update ts_desktop_user_page t set  t.login_page = ? where t.user_id=?";
		String insertsql = "insert into ts_desktop_user_page(id,user_id,login_page) values(?,?,?)";
		String querysql = "select count(*) from ts_desktop_user_page t where t.user_id=?";
		int i = super.getJdbcTemplate().queryForInt(querysql,
				new Object[] { userId });
		int n = 0;
		if (i == 0) {
			n = super.getJdbcTemplate()
					.update(insertsql,
							new Object[] { getId(), userId,
									json.getString("loginPage") });
		} else if (i > 0) {
			n = super.getJdbcTemplate().update(updatesql,
					new Object[] { json.getString("loginPage"), userId });
		}
		return n > 0;
	}

	/***
	 * 保存上传用户图片
	 * 
	 * @throws Exception
	 */
	public boolean saveImgs(final List<String> saveNames,
			final List<String> imgsUrl, final Long userId) {
		String insert = "insert into ts_desktop_imgs(id,img_url,img_name,user_id)values(?,?,?,?)";
		return super.getJdbcTemplate().batchUpdate(insert,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int i)
							throws SQLException {
						// TODO Auto-generated method stub
						try {
							ps.setLong(1, getId());
							ps.setString(2, saveNames.get(i));
							ps.setString(3, imgsUrl.get(i));
							ps.setLong(4, userId);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public int getBatchSize() {
						// TODO Auto-generated method stub
						return saveNames.size();
					}
				}).length > 0;
	}

	/****
	 * 查询桌面图片
	 */
	@SuppressWarnings("unchecked")
	public List<Map> queryImgs(Long userId) {
		String query = "select * from ts_desktop_imgs t where t.user_id ="
				+ userId;
		return ((List<Map>) super.getJdbcTemplate().query(query,
				new ResultSetExtractor() {

					@Override
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						List<Map> list = new ArrayList<Map>();
						while (rs.next()) {
							Map map = new HashMap();
							map.put("id", rs.getLong("id"));
							map.put("imgUrl", rs.getString("img_url"));
							map.put("imgName", rs.getString("img_name"));
							list.add(map);
						}
						return list;
					}
				}));
	}
	/****
	 * 查询功能菜单头信息
	 * 
	 * @throws Exception
	 */
	public List<Map> queryMenuHeader() {
           String sql = "select * from tb_cdzy_cd_description";
           return queryListMap(sql);
	}
	/****
	 * 保存或者修改菜单页面顶部信息
	 * @throws Exception 
	 * @throws DataAccessException 
	 */
	public boolean updateMenuHeader(List<Map<String,Object>> list) throws Exception {
		String updatesql = "update tb_cdzy_cd_description t set t.desc_info=?,t.help_info=?,icon = ?  where t.cd_id=?";
		String insertsql = "insert into tb_cdzy_cd_description(id,cd_mc,cd_id,desc_info,help_info,icon) values(?,?,?,?,?,?)";
		String querysql = "select count(*) from tb_cdzy_cd_description t where t.cd_id=?";
		for(Map<String,Object>map : list){
				Long cdId = Long.valueOf(map.get("cd_id").toString());
				int i = super.getJdbcTemplate().queryForInt(querysql,
						new Object[] { cdId });
				if (i == 0) {
					super.getJdbcTemplate().update(insertsql,
							new Object[] { getId(), map.get("cd_mc"), 
							               cdId,map.get("desc_info"), 
							               map.get("help_info"),map.get("icon")});
				} else if (i > 0) {
					super.getJdbcTemplate().update(updatesql,
							new Object[] { map.get("desc_info"), map.get("help_info"),map.get("icon"),
							               cdId});
				}
		}
		return true;
	}
	/***
	 * 将sql查询到的数据转换成为List<Map>
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes", "deprecation", "unchecked" })
	public List<Map> queryListMap(String sql) {
		return super.getJdbcTemplate().query(sql, new RowMapper() {
			public ResultSetMetaData rsm = null;
			public int count = 0;

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				if (rsm == null) {
					rsm = rs.getMetaData();
					count = rsm.getColumnCount();
				}
				HashMap map = new HashMap();
				for (int i = 0; i < count; i++) {
					String columnName = rsm.getColumnName((i + 1))
							.toLowerCase();
					int sqlType = rsm.getColumnType(i + 1);
					Object sqlView = rs.getObject(columnName);
					switch (sqlType) {
					case Types.CHAR:
						map.put(columnName, sqlView.toString().trim());
						break;
					case Types.NUMERIC:
						if (sqlView == null)
							sqlView = "";
						map.put(columnName, sqlView.toString());
						break;
					default:
						map.put(columnName,
								sqlView != null ? sqlView.toString() : "");
						break;
					}
				}
				return map;
			}
		});
}
}
