package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.Vehicle;

public interface VehicleDao {

    /**
     * 根据条码获取车辆信息
     * @return
     */
    Vehicle getVehicle(String vehicleCode);

    /**
     * 更改车辆状态
     * @param vehNumber
     * @param state
     * @param stateStr
     * @return
     */
    String GetSqlUpdate(long vehNumber,int state,String stateStr);


    /**
     * 更改司机状态
     * @param vehDrNumber
     * @param state
     * @param stateStr
     * @return
     */
    String GetSqlUpdate_Driver(long vehDrNumber,int state,String stateStr);
    
}