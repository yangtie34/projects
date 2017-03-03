package com.eyun.framework.jdbc.jdbcUtil;

import android.animation.TypeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.eyun.framework.jdbc.jdbcUtil.ConnectionPool.PooledConnection;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.framework.util.common.TypeUtil;

/**
 * @author Administrator 基础DAO实现类
 */
public class BaseDaoUtil {

    protected List<Map<String, Object>> getRsList(String sql, Object[] param) {
        List<Map<String, Object>> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PooledConnection con = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.getConnection().prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                pstmt.setString(i + 1, param[i].toString());
            }
            rs = pstmt.executeQuery();
            list = extractData(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt, rs, con);
        }
        return list;
    }

    protected String getRsString(String sql, Object[] param) {
        String str = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PooledConnection con = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.getConnection().prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                pstmt.setString(i + 1, param[i].toString());
            }
            rs = pstmt.executeQuery();
            rs.next();
            str = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt, rs, con);
        }
        return str;
    }

    protected boolean excute(String sql, Object[] param) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PooledConnection con = null;
        try {
            con = DBManager.getConnection();
            pstmt = con.getConnection().prepareStatement(sql);
            for (int i = 0; i < param.length; i++) {
                pstmt.setString(i + 1, param[i].toString());
            }
            return pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt, rs, con);
        }
        return false;
    }

    /**
     * 批量更新或者插入事物控制
     *
     * @param sql
     * @param params
     * @return
     */
    protected boolean excuteForTransaction(String sql, List<Object[]> params) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PooledConnection con = null;
        Connection connection = null;
        int size = 300;//每次执行几条数据
        try {
            con = DBManager.getConnection();
            connection = con.getConnection();
            // 设置不是自动提交
            setAutoCommit(connection);
            long began = System.currentTimeMillis();
            pstmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                Object[] ops = params.get(i);
                for (int j = 0; j < ops.length; j++) {
                    Object o = ops[j];
                    Class<?> type = o.getClass();
                    if (TypeUtil.isInt(type)) {
                        pstmt.setInt(j, TypeConvert.toInteger(o));
                    } else if (TypeUtil.isString(type)) {
                        pstmt.setString(j, TypeConvert.toString(o));
                    } else if (TypeUtil.isLong(type)) {
                        pstmt.setLong(j, TypeConvert.toLong(o));
                    } else if (TypeUtil.isDouble(type)) {
                        pstmt.setDouble(j, TypeConvert.toDouble(o));
                    }
                }
                // preparedStatement.executeQuery();
                // 这儿并不马上执行,积攒到一定数量之后，刷新执行
                pstmt.addBatch();
                if ((i + 1) % size == 0) {
                    pstmt.executeBatch();// 积攒的数据执行
                    pstmt.clearBatch();// 积攒的清楚掉
                }
            }
            // 最后不是300的整数，再执行一次
            if (params.size() % size != 0) {
                pstmt.executeBatch();
                pstmt.clearBatch();
            }

            long end = System.currentTimeMillis();
            System.out.println(end - began);
            // 都成的话，提交事物

            // 都成功提交事物
            commit(connection);
            return true;
        } catch (SQLException e) {
            rollbank(connection);
            e.printStackTrace();
        } finally {
            close(pstmt, rs, con);
        }
        return false;
    }
    /**
     * 批量更新或者插入事物控制
     *
     * @param sqls
     * @return
     */
    protected boolean excuteForTransaction(List<String> sqls) {
        Statement pstmt = null;
        ResultSet rs = null;
        PooledConnection con = null;
        Connection connection = null;
        int size = 300;//每次执行几条数据
        try {
            con = DBManager.getConnection();
            connection = con.getConnection();
            // 设置不是自动提交
            setAutoCommit(connection);
            long began = System.currentTimeMillis();
            pstmt = connection.createStatement();

            for (int i = 0; i < sqls.size(); i++) {
                // 这儿并不马上执行,积攒到一定数量之后，刷新执行
                pstmt.addBatch(sqls.get(i));
                if ((i + 1) % size == 0) {
                    pstmt.executeBatch();// 积攒的数据执行
                    pstmt.clearBatch();// 积攒的清楚掉
                }
            }
            // 最后不是300的整数，再执行一次
            if (sqls.size() % size != 0) {
                pstmt.executeBatch();
                pstmt.clearBatch();
            }

            long end = System.currentTimeMillis();
            System.out.println(end - began);
            // 都成的话，提交事物
            // 都成功提交事物
            commit(connection);
            return true;
        } catch (SQLException e) {
            rollbank(connection);
            e.printStackTrace();
        } finally {
            close(pstmt, rs, con);
        }
        return false;
    }
    protected List<Map<String, Object>> extractData(ResultSet rs) throws SQLException {
        List<Map<String, Object>> listOfRows = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int num = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> mapOfColValues = new LinkedHashMap<String, Object>(num);
            if (num == 1) {
                //listOfRows.add(rs.getString(1));
            } else {
                for (int i = 1; i <= num; i++) {
                    mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
                }
                listOfRows.add(mapOfColValues);
            }
        }
        return listOfRows;
    }


    private void close(PreparedStatement pstmt,
                       ResultSet rs,
                       PooledConnection con) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Statement pstmt,
                       ResultSet rs,
                       PooledConnection con) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 开始事物：取消默认提交
    public void setAutoCommit(Connection connection) {
        if (connection != null) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    // 都成功提交事物
    public void commit(Connection connection) {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // 回滚事物
    public void rollbank(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}