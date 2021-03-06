package com.eyun.jybStorageScan.product.service;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybStorageScan.product.entity.Consign;
import com.eyun.jybStorageScan.product.entity.Vehicle;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathService {
    /**
     * 更改车辆调度
     * @param vehicle
     * @param consign
     * @param dispathType
     * @return
     */
    ResultMsg okVehicleDisp(Vehicle vehicle, Consign consign, int dispathType);
}
