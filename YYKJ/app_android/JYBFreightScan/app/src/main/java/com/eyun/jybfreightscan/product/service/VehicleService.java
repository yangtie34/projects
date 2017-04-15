package com.eyun.jybfreightscan.product.service;

import com.eyun.jybfreightscan.product.entity.Vehicle;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface VehicleService {

    /**
     * 根据条码获取车辆信息
     * @return
     */
    Vehicle getVehicle(String vehicleCode);

}
