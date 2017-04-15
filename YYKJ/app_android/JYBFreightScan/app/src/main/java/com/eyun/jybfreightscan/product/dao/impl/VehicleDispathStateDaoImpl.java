package com.eyun.jybfreightscan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.product.dao.VehicleDispathStateDao;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/21.
 */

public class VehicleDispathStateDaoImpl implements VehicleDispathStateDao {
    private static VehicleDispathStateDaoImpl vehicleDispathStateDao = null;

    public static VehicleDispathStateDaoImpl getInstance() {
        if (vehicleDispathStateDao == null) {
            synchronized (new VehicleDispathStateDaoImpl()) {
                if (vehicleDispathStateDao == null) {
                    vehicleDispathStateDao = new VehicleDispathStateDaoImpl();
                }
            }
        }
        return vehicleDispathStateDao;
    }

    @Override
    public boolean add(String RecNumber, int recState) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("VehDispNumber", SqlStringUtils.GetQuotedString(RecNumber));
        map.put("VehDispState", recState);
        map.put("CreateUserID", AppUser.userId);
        map.put("CreateComID", AppUser.comId);
        map.put("CreateComBrID", AppUser.comBrId);
        map.put("CreateIp", SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sql = SqlStringUtils.GetConstructionInsert("VehicleDispathState", map);
        return BaseDao.getInstance().excute(sql);
    }
}
