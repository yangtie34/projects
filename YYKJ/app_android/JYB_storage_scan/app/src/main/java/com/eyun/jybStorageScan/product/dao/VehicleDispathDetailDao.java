package com.eyun.jybStorageScan.product.dao;

import com.eyun.jybStorageScan.product.entity.VehicleDispathDetail;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathDetailDao {
    /**
     * 根据托运单查询VehicleDispathDetail
     */
    VehicleDispathDetail getVehicleDispathDetailByConsignRecNumber(String RecNumber);
}
