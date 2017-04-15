package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.jybStorageScan.AppUser;
import com.eyun.jybStorageScan.product.dao.ConsignStateDao;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ConsignStateDaoImpl implements ConsignStateDao {
    private static ConsignStateDaoImpl ConsignStateDao = null;

    public static ConsignStateDaoImpl getInstance() {
        if (ConsignStateDao == null) {
            synchronized (new ConsignStateDaoImpl()) {
                if (ConsignStateDao == null) {
                    ConsignStateDao = new ConsignStateDaoImpl();
                }
            }
        }
        return ConsignStateDao;
    }

    @Override
    public boolean add(String RecNumber, int recState) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("RecNumber", SqlStringUtils.GetQuotedString(RecNumber));
        map.put("RecState", recState);
        map.put("ScanTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComID", AppUser.comId);
        map.put("CreateComBrID", AppUser.comBrId);
        map.put("CreateIp", SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sql = SqlStringUtils.GetConstructionInsert("ConsignState", map);
        return BaseDao.getInstance().excute(sql);
    }
}
