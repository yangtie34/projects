package com.eyun.jybStorageScan.product.dao;

import com.eyun.jybStorageScan.product.entity.Vehicle;

public interface VehicleDao {

    /**
     * 根据条码获取车辆信息
     * @return
     */
    Vehicle getVehicle(String vehicleCode);
    
}