package com.eyun.jybfreightscan.product.service;

import com.eyun.jybfreightscan.product.entity.Vehicle;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface VehicleService {

    /**
     * 根据车辆条码获取车辆信息
     * @param vehicleCode
     * @return
     */
    Vehicle LoadInfo(String vehicleCode);

}
