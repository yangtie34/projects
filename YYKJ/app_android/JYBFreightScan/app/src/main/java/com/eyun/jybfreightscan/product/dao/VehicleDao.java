package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.Vehicle;

public interface VehicleDao {

    /**
     * 根据条码获取车辆信息
     * @return
     */
    Vehicle getVehicle(String vehicleCode);
    
}