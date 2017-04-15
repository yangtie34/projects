package com.eyun.jybfreightscan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybfreightscan.product.dao.VehicleDispathDao;
import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class VehicleDispathDaoImpl implements VehicleDispathDao {
    private static VehicleDispathDaoImpl vehicleDispathDao = null;

    public static VehicleDispathDaoImpl getInstance() {
        if (vehicleDispathDao == null) {
            synchronized (new VehicleDispathDaoImpl()) {
                if (vehicleDispathDao == null) {
                    vehicleDispathDao = new VehicleDispathDaoImpl();
                }
            }
        }
        return vehicleDispathDao;
    }

    @Override
    public boolean updateVehicleDispath(String recNumber, int RecState, int RecForwardedState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("VehDispState",RecState);
        map.put("VehDispForwardedState",RecForwardedState);
        String where=" where VehDispNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        String sqlUpdate= SqlStringUtils.GetConstructionUpdate("VehicleDispath",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }

    @Override
    public boolean hasThisDispath(String recNumber, long VehNumber) {
        String sqlSelect="Select * from  VehicleDispath where VehNumber="+VehNumber +
                " and VehDispNumber="+ SqlStringUtils.GetQuotedString(recNumber);

        List<VehicleDispathDetail> list= BaseDao.getInstance().query(sqlSelect,VehicleDispathDetail.class);
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }
}
