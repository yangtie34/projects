package com.eyun.jybStorageScan.product.service;

import com.eyun.jybStorageScan.product.entity.StorageLocation;
import com.eyun.jybStorageScan.product.entity.Vehicle;

import java.util.List;
import java.util.Map;

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
