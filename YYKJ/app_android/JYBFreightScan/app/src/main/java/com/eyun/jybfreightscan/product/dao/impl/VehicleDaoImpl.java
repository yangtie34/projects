package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.jdbc.jdbcUtil.BaseDaoServer;
import com.eyun.jybfreightscan.product.dao.VehicleDao;
import com.eyun.jybfreightscan.product.entity.Vehicle;
import com.eyun.jybfreightscan.product.dao.VehicleDao;

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
        String sqlSelect="Select * from Vehicle where VehNumber="+ vehicleCode;
        List<Vehicle> list= BaseDaoServer.getInstance().query(sqlSelect,Vehicle.class);
        if(list!=null&&list.size()==1)
            return list.get(0);
        return  null;
    }

    @Override
    public String GetSqlUpdate(long vehNumber, int state, String stateStr) {
        return "Update Vehicle set VehState="+state+" where VehNumber="+vehNumber+" and  VehState in("+stateStr+")";
    }

    @Override
    public String GetSqlUpdate_Driver(long vehDrNumber, int state, String stateStr) {
        return "Update VehicleDriver set VehDrState="+state+" where VehDrNumber="+vehDrNumber+" and  VehDrState in("+stateStr+")";
    }
}
