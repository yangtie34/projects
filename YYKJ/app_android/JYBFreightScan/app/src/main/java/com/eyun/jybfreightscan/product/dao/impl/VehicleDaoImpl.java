package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybfreightscan.product.dao.VehicleDao;
import com.eyun.jybfreightscan.product.entity.Vehicle;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class VehicleDaoImpl implements VehicleDao {

    private static VehicleDaoImpl vehicleDao = null;

    public static VehicleDaoImpl getInstance() {
        if (vehicleDao == null) {
            synchronized (new VehicleDaoImpl()) {
                if (vehicleDao == null) {
                    vehicleDao = new VehicleDaoImpl();
                }
            }
        }
        return vehicleDao;
    }
    @Override
    public Vehicle getVehicle(String vehicleCode) {
        String sqlSelect="Select * from Vehicle where VehNumber="+ SqlStringUtils.GetQuotedString(vehicleCode);
        List<Vehicle> list= BaseDao.getInstance().query(sqlSelect,Vehicle.class);
        if(list!=null&&list.size()==1)
            return list.get(0);
        return  null;
    }
}
