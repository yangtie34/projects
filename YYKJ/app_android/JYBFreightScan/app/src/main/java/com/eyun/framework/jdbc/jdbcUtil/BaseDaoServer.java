package com.eyun.framework.jdbc.jdbcUtil;

import com.eyun.framework.util.FastJsonTools;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/13.
 */

public class BaseDaoServer extends BaseDaoServerUtil {

    private BaseDaoServer() {

    }

    private static BaseDaoServer baseDaoServer = null;
    public static BaseDaoServer getInstance() {
        if (baseDaoServer == null) {
            synchronized (new String()) {
                if (baseDaoServer == null) {
                    baseDaoServer = new BaseDaoServer();
                }
            }
        }
        return baseDaoServer;
    }

    /**
     * 根据sql查询list数据
     * @param sql
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql) {
        return getRsList(sql, new Object[]{});
    }

    /**
     * 根据sql和sql参数 查询list数据
     * @param sql
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] param) {
        return getRsList(sql, param);
    }

    public <T> List<T> query(String sql, Class<T> clazz, Object[] param) {
        List<Map<String, Object>> l = queryForList(sql, param);
        List<T> list = FastJsonTools.createJsonToListBean(FastJsonTools.createJsonString(l), clazz);
        return list;
    }

    public <T> List<T> query(String sql, Class<T> clazz, Object param) {
        return query(sql, clazz, new Object[]{param});
    }

    public <T> List<T> query(String sql, Class<T> clazz) {
        return query(sql, clazz, new Object[]{});
    }


    public String queryForString(String sql, Object[] objects) {
        String str = getRsString(sql, objects);
        return str;
    }

    public boolean excute(String sql) {
        return excute(sql, new Object[]{});
    }

    public boolean excuteTransaction(List<String> list) {
        return excuteForTransaction(list);
    }
}


