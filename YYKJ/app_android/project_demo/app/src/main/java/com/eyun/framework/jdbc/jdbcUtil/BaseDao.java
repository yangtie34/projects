package com.eyun.framework.jdbc.jdbcUtil;

import com.eyun.framework.util.FastJsonTools;
import com.eyun.framework.util.common.T;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * @author Administrator 基础DAO实现类
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class BaseDao extends BaseDaoUtil {

    private BaseDao() {

    }

    private static BaseDao baseDao = null;

    public static BaseDao getInstance() {
        if (baseDao == null) {
            synchronized (new String()) {
                if (baseDao == null) {
                    baseDao = new BaseDao();
                }
            }
        }
        return baseDao;
    }

    /**
     * 根据sql查询list数据
     * @param sql
     * @return
     */
    public List<Map<String,Object>> queryForList(String sql) {
        return getRsList(sql, new Object[]{});
    }
    /**
     * 根据sql和sql参数 查询list数据
     * @param sql
     * @return
     */
    public List<Map<String,Object>> queryForList(String sql, Object[] param) {
        return getRsList(sql, param);
    }

    public List<T> query(String sql, Class<T> clazz, Object[] param) {
        List<Map<String,Object>> l = queryForList(sql, param);
        List<T> list =FastJsonTools.createJsonToListBean(FastJsonTools.createJsonString(l), clazz);
        return list;
    }

    public List<T> query(String sql, Class<T>  clazz, Object param) {
        return query(sql, clazz, new Object[]{param});
    }

    public List<T> query(String sql, Class<T>  clazz) {
        return query(sql, clazz, new Object[]{});
    }


    public String queryForString(String sql, Object[] objects) {
        String str = getRsString(sql,objects);
        return str;
    }

    public boolean excute(String sql) {
        return excute(sql, new Object[]{});
    }

}